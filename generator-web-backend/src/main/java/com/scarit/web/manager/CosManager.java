package com.scarit.web.manager;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.*;
import com.qcloud.cos.transfer.Download;
import com.qcloud.cos.transfer.TransferManager;
import com.scarit.web.config.CosClientConfig;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Cos 对象存储操作
 *
 * @author ADI
 */
@Component
public class CosManager {

    @Resource
    private CosClientConfig cosClientConfig;

    @Resource
    private COSClient cosClient;

    // 复用对象
    private TransferManager transferManager;


    // 在bean加载完成后执行
    @PostConstruct
    public void init() {
        // 自定义线程池大小，建议在客户端与 COS 网络充足（例如使用腾讯云的 CVM，同地域上传 COS）的情况下，设置成16或32即可，可较充分的利用网络资源
        // 对于使用公网传输且网络带宽质量不高的情况，建议减小该值，避免因网速过慢，造成请求超时。
        ExecutorService threadPool = Executors.newFixedThreadPool(32);
        
        // 传入一个 threadpool, 若不传入线程池，默认 TransferManager 中会生成一个单线程的线程池。
        transferManager = new TransferManager(cosClient, threadPool);
        

    }

    /**
     * 上传对象
     *
     * @param key           唯一键
     * @param localFilePath 本地文件路径
     * @return
     */
    public PutObjectResult putObject(String key, String localFilePath) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(cosClientConfig.getBucket(), key,
                new File(localFilePath));
        return cosClient.putObject(putObjectRequest);
    }

    /**
     * 上传对象
     *
     * @param key  唯一键
     * @param file 文件
     * @return
     */
    public PutObjectResult putObject(String key, File file) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(cosClientConfig.getBucket(), key,
                file);
        return cosClient.putObject(putObjectRequest);
    }

    /**
     * 下载对象
     * @param key  唯一键
     * @return
     */
    public COSObject getObject(String key) {
        GetObjectRequest getObjectRequest = new GetObjectRequest(cosClientConfig.getBucket(), key);
        return cosClient.getObject(getObjectRequest);
    }

    /**
     * 下载对象到本地
     * @param key 唯一键
     * @param localFilePath 文件本地存储路径
     * @return download
     * @throws InterruptedException
     */
    public Download download(String key,String localFilePath) throws InterruptedException {
        File downloadFile = new File(localFilePath);
        GetObjectRequest getObjectRequest = new GetObjectRequest(cosClientConfig.getBucket(), key);
        Download download = transferManager.download(getObjectRequest, downloadFile);
        // 同步等待下完完成
        download.waitForCompletion();
        return download;
    }


    /**
     * 删除文件
     * @param key 唯一键
     */
    public void deleteObject(String key){
        cosClient.deleteObject(cosClientConfig.getBucket(), key);
    }

    /**
     * 批量删除文件
     * @param keyList 文件key列表
     */
    public DeleteObjectsResult deleteObjects(List<String> keyList){

        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(cosClientConfig.getBucket());

        // 设置要删除的key列表, 最多一次删除1000个
        ArrayList<DeleteObjectsRequest.KeyVersion> keyVersions = new ArrayList<>();
        // 传入要删除的文件名
        // 注意文件名不允许以正斜线/或者反斜线\开头，例如：
        // 存储桶目录下有a/b/c.txt文件，如果要删除，只能是 keyList.add(new KeyVersion("a/b/c.txt")), 若使用 keyList.add(new KeyVersion("/a/b/c.txt"))会导致删除不成功
        for (String key : keyList) {
            // 去除文件开头的 "/" "\"，以防删除不成功
            if (StrUtil.startWith(key, "\\") || StrUtil.startWith(key, "/")) {
                key = key.substring(1, key.length());
            }

            keyVersions.add(new DeleteObjectsRequest.KeyVersion(key));
        }
        deleteObjectsRequest.setKeys(keyVersions);

        DeleteObjectsResult deleteObjectsResult = cosClient.deleteObjects(deleteObjectsRequest);
        return deleteObjectsResult;

    }

    /**
     * 删除目录
     * @param  delPrifix  删除目录前缀
     */
    public void deleteDir(String delPrifix) {

        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
        // 设置 bucket 名称
        listObjectsRequest.setBucketName(cosClientConfig.getBucket());
        // prefix 表示列出的对象名以 prefix 为前缀
        // 这里填要列出的目录的相对 bucket 的路径
        // 预防没加 "/" 后缀导致误删除，
        delPrifix = StrUtil.appendIfMissing(delPrifix, "/");
        listObjectsRequest.setPrefix(delPrifix);
        // 设置最大遍历出多少个对象, 一次 listobject 最大支持1000
        listObjectsRequest.setMaxKeys(1000);

        // 保存每次列出的结果
        ObjectListing objectListing = null;

        do {

            objectListing = cosClient.listObjects(listObjectsRequest);
            // 这里保存列出的对象列表
            List<COSObjectSummary> cosObjectSummaries = objectListing.getObjectSummaries();
            if (CollUtil.isEmpty(cosObjectSummaries)) {
                break;
            }

            ArrayList<DeleteObjectsRequest.KeyVersion> delObjects = new ArrayList<DeleteObjectsRequest.KeyVersion>();
            for (COSObjectSummary cosObjectSummary : cosObjectSummaries) {

                delObjects.add(new DeleteObjectsRequest.KeyVersion(cosObjectSummary.getKey()));

            }
            DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(cosClientConfig.getBucket());
            deleteObjectsRequest.setKeys(delObjects);
            cosClient.deleteObjects(deleteObjectsRequest);



            // 标记下一次开始的位置
            String nextMarker = objectListing.getNextMarker();
            listObjectsRequest.setMarker(nextMarker);
        } while (objectListing.isTruncated());

    }
}
