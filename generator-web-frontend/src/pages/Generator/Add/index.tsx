import {
  ProCard,
  ProFormItem,
  ProFormSelect,
  ProFormText,
  ProFormTextArea,
  StepsForm,
} from '@ant-design/pro-components';
import type {ProFormInstance} from '@ant-design/pro-components';
import { message, UploadFile } from 'antd';
import React, { useEffect, useRef, useState } from 'react';
import PictureUploader from '@/components/PictureUploader';
import FileUploader from '@/components/FileUploader';
import {
  addGeneratorUsingPost,
  editGeneratorUsingPost,
  getGeneratorVoByIdUsingGet,
} from '@/services/backend/generatorController';
import { history } from '@umijs/max';
import { useSearchParams } from '@@/exports';
import { COS_HOST } from '@/constants';
import ModelConfigForm from '@/pages/Generator/Add/components/ModelConfigForm';


/**
 * 创建生成器页面
 * @constructor
 */
const GeneratorAddPage : React.FC = () => {
  const formRef = useRef<ProFormInstance>();
  const [searchParams] = useSearchParams();
  const id = searchParams.get('id');
  const [oldData, setOldData] = useState<API.GeneratorEditRequest>();


  /**
   * 加载数据
   * @param values
   */
  const loadData = async () => {
    // if (!id) {
    //   return;
    // }
    try {
      const res = await getGeneratorVoByIdUsingGet({
        id,
      });

      // 处理文件路径
      if (res.data) {
        const { distPath } = res.data ?? {};
        if (distPath) {
          //@ts-ignore
          res.data.distPath = [
            {
              uid: id,
              name: '文件' + id,
              status: 'done',
              url: COS_HOST + distPath,
              response: distPath,
            } as UploadFile,
          ];
        }
        setOldData(res.data);
      }
    } catch (error: any) {
      message.error('文件更新失败:' + error.message);
    }
  };

  /**
   * 页面初始化钩子函数
   */
  useEffect(() => {
    if (id) {
      loadData();
    }
  }, [id]);


  /**
   * 新增生成器
   * @param GeneratorAddRequest
   */
  const doAdd = async (values: API.GeneratorAddRequest) => {
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
  /**
   * 更新生成器
   * @param GeneratorEditRequest
   */
  const doUpdate = async (values: API.GeneratorEditRequest) => {
    try{
      const res = await editGeneratorUsingPost(values);
      if (res.data) {
        message.success('更新成功');
        history.push(`/generator/detail/${id}`);
      }
    } catch (error: any){
      message.error('更新失败' + error.message);
    }
  };


  /**
   * 提交请求
   * @param GeneratorAddRequest
   */
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

    if (id) {
      await doUpdate({
        id,
        ...values,
      });
    } else {
      await doAdd(values);
    }
    console.log("values"+values.distPath);


  };

  return (
    <ProCard>
      {/*// 创建或者已加载要更新的数据时，才渲染表单，并顺利填充默认值*/}
      {(!id || oldData) && (
        <StepsForm<API.GeneratorAddRequest | API.GeneratorEditRequest> formRef={formRef} onFinish={doSubmit} formProps={{ initialValues: oldData }}>
          <StepsForm.StepForm
            name='base'
            title='基本信息'
            onFinish={async () => {
              console.log(formRef.current?.getFieldsValue());
              return true;
            }}
          >
            <ProFormText
              name='name'
              label='名称'
              placeholder='请输入名称'
            />
            <ProFormTextArea
              name='description'
              label='描述'
              placeholder='请输入描述'
            />
            <ProFormText
              name='basePackage'
              label='基础包'
              placeholder='请输入基础包'
            />
            <ProFormText
              name='version'
              label='版本'
              placeholder='请输入版本'
            />
            <ProFormText
              name='author'
              label='作者'
              placeholder='请输入作者'
            />
            <ProFormSelect
              label='标签'
              mode='tags'
              name='tags'
              placeholder='请输入标签'
            />
            <ProFormItem label='图片' name='picture'>
              <PictureUploader biz='generator_picture' />
            </ProFormItem>
          </StepsForm.StepForm>
          <StepsForm.StepForm
            name='modelConfig'
            title='模型配置'onFinish={async (values) => {
            console.log(values);
            return false}} >
            <ModelConfigForm formRef={formRef}/>
          </StepsForm.StepForm>
          <StepsForm.StepForm
            name='fileConfig'
            title='文件配置'>
            {/*todo 待办*/}
          </StepsForm.StepForm>
          <StepsForm.StepForm name='dist' title='生成器文件'>
            <ProFormItem label='产物包' name='distPath'>
              <FileUploader
                biz='generator_dist'
                description='请上传生成器文件压缩包+'
              />
            </ProFormItem>
          </StepsForm.StepForm>
        </StepsForm>
      )}
    </ProCard>
  );
};
export default GeneratorAddPage;

