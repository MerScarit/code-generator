import React, { useEffect, useState } from 'react';
import { Link, useParams } from '@@/exports';
import {
  downloadGeneratorByIdUsingGet,
  getGeneratorVoByIdUsingGet,
  useGeneratorUsingPost,
} from '@/services/backend/generatorController';
import {
  Button,
  Card,
  Col,
  Collapse,
  Divider,
  Form,
  Image,
  Input,
  message, Radio,
  Row,
  Space,
  Tabs,
  Tag,
  Typography,
} from 'antd';
import { PageContainer } from '@ant-design/pro-components';
import moment from 'moment';
import { saveAs } from 'file-saver';
import { useModel } from '@@/plugin-model';
import { DownloadOutlined, EditOutlined } from '@ant-design/icons';

const GeneratorUsePage : React.FC = () => {
  const { id } = useParams();
  const [ data, setData ] = useState<API.GeneratorVO>({});
  const [ loading, setLoading ] = useState(false);
  const [ downloading, setDownloading ] = useState<boolean>(false);
  const { initialState } = useModel('@@initialState');
  const { currentUser } = initialState ?? {};
  const [form] = Form.useForm();
  const models = data?.modelConfig?.models ?? [];

  /**
   * 加载数据
   * @param values
   */
  const loadData = async () => {
    if (!id) {
      return;
    }
    setLoading(true);
    try {
      const res = await getGeneratorVoByIdUsingGet({
        id
      })
      setData(res.data || {});
    }
    catch (error: any) {
      message.error('文件详情获取失败:' + error.message);
    }
    setLoading(false);
  }
  /**
   * 页面初始化钩子函数
   */
  useEffect(() => {
    if (id) {
      loadData();
    }
  }, [id]);


  /**
   * 下载代码按钮
   */
  const downloadButton = data.distPath && currentUser &&
    (
      <Button
        type='primary'
        icon={<DownloadOutlined />}
        loading={downloading}
        onClick={async () => {
          setDownloading(true);
          const values = form.getFieldsValue();

          const blob = await useGeneratorUsingPost(
            {
              id: data.id,
              dataModel: values,
            }, {
              responseType: 'blob',
            },
          );
          // 使用 file-saver 来保存文件
          const fullpath = data.distPath || '';
          saveAs(blob, fullpath.substring(fullpath.lastIndexOf('/') + 1));
          setDownloading(false);
        }}>
        生成代码
      </Button>
    );


  return (
    <PageContainer loading={loading}>
      <Card>
        <Row justify='space-between' gutter={[32, 32]}>
          <Col flex='auto'>
            <Space size='large' align='center'>
              <Typography.Title level={4}>
                {data.name}
              </Typography.Title>
            </Space>
            <Typography.Paragraph>{data.description}</Typography.Paragraph>
            <Divider />
            <Form form={form}>
              {models.map((model, index) => {
                  // 是分组
                  if (model.groupKey) {
                    if (!model.models) {
                      return <></>;
                    }
                    return (
                      <Collapse
                        key={index}
                        style={{ marginBottom: 24 }}
                        items={[
                          {
                            key: index,
                            label: model.groupName + '分组',
                            children: model.models?.map((subModel, index) => {
                              return (
                                <Form.Item key={index} name={[model.groupKey, subModel.fieldName]}
                                           label={subModel.fieldName}>

                                  <Input placeholder={subModel.description} />
                                </Form.Item>
                              );
                            }),
                          },
                        ]}
                        bordered={false}
                        defaultActiveKey={[index]}
                      />
                    );
                  }

                return (
                  <Form.Item key={index} name={model.fieldName} label={model.fieldName}>
                    <Input placeholder={model.description} />
                  </Form.Item>
                )
              })}
            </Form>
            <div style={{ marginBottom: 24}} />
            <Space size='middle'>
              {downloadButton}
              <Link to={`generator/detail/${id}`} >
                <Button>查看详情</Button>
              </Link>
            </Space>
          </Col>
          <Col flex='320px'>
            <Image src={data.picture} />
          </Col>
        </Row>
      </Card>
      <div style={{ marginBottom: 24 }} />
    </PageContainer>
  );
}

export default GeneratorUsePage;
