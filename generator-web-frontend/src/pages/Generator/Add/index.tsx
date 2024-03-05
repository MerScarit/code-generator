import {
  ProCard,
  ProFormItem,
  ProFormSelect,
  ProFormText,
  ProFormTextArea,
  StepsForm,
} from '@ant-design/pro-components';
import type {ProFormInstance} from '@ant-design/pro-components';
import { Button, message } from 'antd';
import React, { useRef } from 'react';
import PictureUploader from '@/components/PictureUploader';
import FileUploader from '@/components/FileUploader';
import { addGeneratorUsingPost } from '@/services/backend/generatorController';
import { history } from '@umijs/max';



/**
 * 创建生成器页面
 * @constructor
 */
const GeneratorAddPage : React.FC = () => {
  const formRef = useRef<ProFormInstance>();


  const doSubmit = async (values: API.GeneratorAddRequest) => {
    // 数据转换
    if (!values.fileConfig) {
      values.fileConfig = {};
    }
    if (!values.modelConfig) {
      values.modelConfig = {};
    }

    // 文件列表转换成url
    if (values.distPath && values.distPath.length > 0) {
      // @ts-ignore
      values.distPath = values.distPath[0].response;
    }

    try{
      const res = await addGeneratorUsingPost(values);
      if (res.data) {
        message.success('创建成功');
        history.push(`/generator/detail/${res.data}`);
      }
    } catch (error: any){
      message.error('创建失败' + error.message);
    }
  };

  return (
    <ProCard>
      <StepsForm<API.GeneratorAddRequest> formRef={formRef} onFinish={doSubmit}>
        <StepsForm.StepForm
          name="base"
          title="基本信息"
          onFinish={ async ()=>{
            console.log(formRef.current?.getFieldsValue());
            return true;
          }}
        >
          <ProFormText
            name="name"
            label="名称"
            placeholder="请输入名称"
          />
          <ProFormTextArea
            name="description"
            label="描述"
            placeholder="请输入描述"
          />
          <ProFormText
            name="basePackage"
            label="基础包"
            placeholder="请输入基础包"
          />
          <ProFormText
            name="version"
            label="版本"
            placeholder="请输入版本"
          />
          <ProFormText
            name="author"
            label="作者"
            placeholder="请输入作者"
          />
          <ProFormSelect
            label= "标签"
            mode="tags"
            name="tags"
            placeholder="请输入标签"
          />
          <ProFormItem label= "图片" name="picture">
            <PictureUploader biz= "generator_picture" />
          </ProFormItem>
        </StepsForm.StepForm>
        <StepsForm.StepForm
          name="fileConfig"
          title="文件配置">
          {/*todo 待办*/}
        </StepsForm.StepForm>
        <StepsForm.StepForm
          name="modelConfig"
          title="模型配置">
          {/*todo 待办*/}
        </StepsForm.StepForm>
        <StepsForm.StepForm name="dist" title="生成器文件">
          <ProFormItem label= "产物包" name="distPath">
            <FileUploader biz= "generator_dist" description="请上传生成器文件压缩包+" />
          </ProFormItem>
        </StepsForm.StepForm>
      </StepsForm>
    </ProCard>
  );
};
export default GeneratorAddPage;

