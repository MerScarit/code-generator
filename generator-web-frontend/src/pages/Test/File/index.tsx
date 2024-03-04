import { testDownloadFileUsingGet, testUploadFileUsingPost } from '@/services/backend/fileController';
import { listGeneratorVoByPage } from '@/services/backend/generatorController';
import { InboxOutlined, UserAddOutlined, UserOutlined } from '@ant-design/icons';
import { PageContainer, ProFormSelect, ProFormText, QueryFilter, DefaultFooter } from '@ant-design/pro-components';
import { Avatar, Button, Card, Flex, Image, Input, List, message, Tabs, Tag, Typography, UploadProps } from 'antd';
import Dragger from 'antd/es/upload/Dragger';
import { saveAs } from 'file-saver';
import { set } from 'lodash';
import moment from 'moment';
import { Divider } from 'rc-menu';
import React, { useEffect, useState } from 'react';

/**
 * 测试文件上传、下载
 * @constructor
 */

/**
 * COS 访问地址
 */
export const COS_HOST = "https://generator-1316765566.cos.ap-guangzhou.myqcloud.com";

const [value, setValue] = useState<string>();

const TestFilePage: React.FC = () => {

  const props: UploadProps = {
    name: 'file',
    multiple: false,
    maxCount: 1,
    customRequest: async (fileObj: any) => {
      try {
        const res = await testUploadFileUsingPost({}, fileObj.file);
        fileObj.onSuccess(res.data);
        setValue(res.data);
      } catch (e: any) {
        message.error('上传失败' + e.message);
        fileObj.onError(e);
      }
    },
    onRemove() {
      setValue(undefined);
    }
  };

  return (
    <Flex gap={16}>
      <Card title="文件上传">
        <Dragger {...props}>
          <p className="ant-upload-drag-icon">
            <InboxOutlined />
          </p>
          <p className="ant-upload-text">Click or drag file to this area to upload</p>
          <p className="ant-upload-hint">
            Support for a single or bulk upload. Strictly prohibited from uploading company data or other
            banned files.
          </p>
        </Dragger>
      </Card>

      <Card title="文件下载" loading={!value}>
        <div>文件下载地址：{COS_HOST + value}</div>
        <Divider />
        <img src={COS_HOST + value} height={280}></img>
        <Divider />
        <Button onClick={async () => {
          const blob = await testDownloadFileUsingGet(
            {
              filepath: value
            }, {
            responseType: 'blob'
          }
          );
          // 使用 file-saver 来保存文件
          const fullpath = COS_HOST + value;
          saveAs(blob, fullpath.substring(fullpath.lastIndexOf("/") + 1));
        }}>
          点击下载文件
        </Button>
      </Card>
    </Flex>
  )

};
export default TestFilePage;
