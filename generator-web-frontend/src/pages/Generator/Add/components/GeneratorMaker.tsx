import React from 'react';
import { Alert, Button, Card, Collapse, Form, FormListFieldData, Input, message, Select, Space } from 'antd';
import { ProForm, ProFormItem } from '@ant-design/pro-components';
import { makeGeneratorUsingPost } from '@/services/backend/generatorController';
import { saveAs } from 'file-saver';
import FileUploader from '@/components/FileUploader';

interface Props {
  meta: API.GeneratorAddRequest | API.GeneratorEditRequest
}

export default (props: Props) => {
  const [form] = Form.useForm();
  const { meta } = props;

  /**
   * 提交请求
   * @param GeneratorAddRequest
   */
  const doSubmit = async (values: API.GeneratorMakeRequest) => {
    // 校验
    if (!meta.name) {
      message.error('请填写名称');
      return;
    }

    const zipFilePath = values.zipFilePath;
    if (!zipFilePath || zipFilePath.length < 1) {
      message.error('请上传模板文件压缩包');
      return;
    }

    // 文件列表转换成url
    // @ts-ignore
    values.zipFilePath = zipFilePath[0].response;

    try {
      const blob = await makeGeneratorUsingPost(
        {
          meta,
          zipFilePath: values.zipFilePath,
        },
        {
          responseType: 'blob',
        },
      );
      // 使用 file-saver 保存文件
      saveAs(blob, meta.name + '.zip');
    } catch (error:any) {
      message.error('下载失败' + error.message);
    }
  };

  /**
   * 表单视图
   */
  const fromView = (
    <ProForm
      form={form}
      submitter={{
        searchConfig: {
          submitText: '制作',
        },
        resetButtonProps: {
          hidden: true,
        },
      }}
      onFinish={doSubmit}
    >
      <ProFormItem label={'模版文件'} name={'zipFilePath'}>
        <FileUploader biz={'generator_make_template'} description={'请上传压缩包，打包时不要添加最外层目录！'} />
      </ProFormItem>
    </ProForm>
  );


  return (
    <Collapse
      style={{ marginBottom : 24 }}
      items={[
        {
          key: 'maker',
          label: '生成器制作工具',
          children: fromView,
        },
      ]}
    />
  );
};


