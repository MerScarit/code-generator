import React, { useState } from 'react';
import { message, Upload, UploadProps } from 'antd';
import { uploadFileUsingPost } from '@/services/backend/fileController';
import Dragger from 'antd/es/upload/Dragger';
import { InboxOutlined, LoadingOutlined, PlusOutlined } from '@ant-design/icons';
import { COS_HOST } from '@/constants';

interface Props{
  biz: string,
  onChange?:(url:string) =>void,
  value?: string;
};

/**
 * 图片上传组件
 * @param props
 * @constructor
 */
const PictureUploader: React.FC<Props> = (props) =>{

  const { biz, value, onChange } = props;
  const [loading, setLoading] = useState(false);

  const uploadProps: UploadProps = {
    name: 'file',
    listType: 'picture-card',
    multiple: false,
    maxCount: 1,
    showUploadList: false,
    customRequest: async (fileObj: any) => {
      setLoading(true);
      try {
        const res = await uploadFileUsingPost({
            biz,
          },
          {},
          fileObj.file
        );
        // 拼接图片完整路径
        const fullpath = COS_HOST + res.data;
        onChange?.(fullpath ?? '');
        fileObj.onSuccess(res.data);
      } catch (e: any) {
        message.error('上传失败' + e.message);
        fileObj.onError(e);
      }
      setLoading(false);
    },
  };

  const uploadButton = (
    <div>
      {loading? <LoadingOutlined/> : <PlusOutlined/>}
      <div style={{ marginTop: 8 }} >上传</div>
    </div>
  );

  return (
    <Upload {...uploadProps}>
      {value ? <img src={value} alt='picture' style={{ width: '100%' }} /> : uploadButton}
    </Upload>
  );
}
export default PictureUploader;
