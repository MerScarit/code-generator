import React, { useState } from 'react';
import { message, UploadFile, UploadProps } from 'antd';
import { uploadFileUsingPost } from '@/services/backend/fileController';
import Dragger from 'antd/es/upload/Dragger';
import { InboxOutlined } from '@ant-design/icons';


interface Props{
  biz: string;
  onChange?: (fileList: UploadFile[]) => void;
  value?: UploadFile[];
  description?: string;
}


/**
 * 文件上传组件
 * @param props
 * @constructor
 */
const FileUploader: React.FC<Props> = (props) => {
  const { biz, value, description, onChange } = props;
  const [loading, setLoading] = useState(false);



  const uploadProps: UploadProps = {
    name: 'file',
    listType: 'text',
    multiple: false,
    maxCount: 1,
    fileList: value,
    disabled: loading,
    customRequest: async (fileObj: any) => {
      setLoading(true);
      try {
        const res = await uploadFileUsingPost({
          biz,
        },
          {},
          fileObj.file
        );
        fileObj.onSuccess(res.data);
      } catch (e: any) {
        message.error('上传失败' + e.message);
        fileObj.onError(e);
      }
      setLoading(false);
    },
  };

  return (
    <Dragger {...uploadProps}>
      <p className="ant-upload-drag-icon">
        <InboxOutlined />
      </p>
      <p className="ant-upload-text">点击或拖摘文件上传</p>
      <p className="ant-upload-hint">{description}</p>
    </Dragger>
  );
};
export default FileUploader;