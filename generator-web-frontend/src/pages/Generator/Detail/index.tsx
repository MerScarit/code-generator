import React, { useEffect, useState } from 'react';
import { Link, useParams } from '@@/exports';
import { downloadGeneratorByIdUsingGet, getGeneratorVoByIdUsingGet } from '@/services/backend/generatorController';
import { Button, Card, Col, Image, message, Row, Space, Tabs, Tag, Typography } from 'antd';
import { PageContainer } from '@ant-design/pro-components';
import moment from 'moment';
import { saveAs } from 'file-saver';
import { useModel } from '@@/plugin-model';
import { DownloadOutlined, EditOutlined } from '@ant-design/icons';
import FileConfig from '@/pages/Generator/Detail/components/FileConfig';
import ModelConfig from '@/pages/Generator/Detail/components/ModelConfig';
import AuthorInfo from '@/pages/Generator/Detail/components/AuthorInfo';

const GeneratorDetailPage : React.FC = () => {
  const { id } = useParams();
  const [ data, setData ] = useState<API.GeneratorVO>({});
  const [ loading, seLoading ] = useState(false);
  const { initialState } = useModel('@@initialState');
  const { currentUser } = initialState ?? {};
  const my  = currentUser?.id === data?.userId;

  /**
   * 加载数据
   * @param values
   */
  const loadData = async () => {
    if (!id) {
      return;
    }
    seLoading(true);
    try {
      const res = await getGeneratorVoByIdUsingGet({
        id,
      })
      setData(res.data || {});
    }
    catch (error: any) {
      message.error('文件详情获取失败:' + error.message);
    }
    seLoading(false);
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
   * 标签列表
   * @param tags
   */
  const tagsListView = (tags: string) => {
    if (!tags) {
      return <></>;
    }
    return (
      <div style={{ marginBottom: 8 }}>
        {tags.map((tag: string) => {
          return <Tag key={tag}>{tag}</Tag>;
        })}
      </div>
    );
  };


  /**
   * 下载按钮
   */
  const downloadButton = data.distPath && currentUser &&
    (
      <Button
        icon={<DownloadOutlined />}
        onClick={async () => {
          const blob = await downloadGeneratorByIdUsingGet(
            {
              id: data.id,
            }, {
              responseType: 'blob',
            },
          );
          // 使用 file-saver 来保存文件
          const fullpath = data.distPath || '';
          saveAs(blob, fullpath.substring(fullpath.lastIndexOf('/') + 1));
        }}>
        下载
      </Button>
    );

  const updateButton = my && (
    <Link to={`/generator/update?id=${data.id}`}>
      <Button icon={<EditOutlined />}>编辑</Button>
    </Link>
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
              {tagsListView(data.tags)}
            </Space>
            <Typography.Paragraph>{data.description}</Typography.Paragraph>
            <Typography.Paragraph
              type='secondary'>创建时间：{moment(data.createTime).format('YYYY-MM-DD HH:mm:ss')}</Typography.Paragraph>
            <Typography.Paragraph>基础包：{data.basePackage}</Typography.Paragraph>
            <Typography.Paragraph>版本：{data.version}</Typography.Paragraph>
            <Typography.Paragraph>作者：{data.author}</Typography.Paragraph>
            <div style={{ marginBottom: 24 }} />
            <Space size="middle">
              <Button type="primary">立即使用</Button>
              {downloadButton}
              {updateButton}
            </Space>
          </Col>
          <Col flex='320px'>
            <Image src={data.picture} />
          </Col>
        </Row>
      </Card>
      <div style={{ marginBottom: 24 }} />
      <Card>
        <Tabs size='large'
              defaultActiveKey={'fileConfig'}
              onChange={() => {}}
              items={[
                {
                  key:'fileConfig',
                  label: '文件配置',
                  children: <FileConfig data={data} />,
                },
                {
                  key:'modelConfig',
                  label: '模型配置',
                  children: <ModelConfig data={data} />,
                },
                {
                  key:'userInfo',
                  label: '作者信息',
                  children: <AuthorInfo data={data} />,
                },
              ]}
        />
      </Card>
    </PageContainer>
  );


}

export default GeneratorDetailPage;
