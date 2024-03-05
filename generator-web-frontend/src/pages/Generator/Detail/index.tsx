import React, { useEffect, useState } from 'react';
import { useParams } from '@@/exports';
import { getGeneratorVoByIdUsingGet } from '@/services/backend/generatorController';
import { COS_HOST } from '@/constants';
import { UploadFile } from 'antd/lib';
import { Button, Card, Col, Image, message, Row, Space, Tag, Typography } from 'antd';
import { ProCard } from '@ant-design/pro-components';
import moment from 'moment';

const GeneratorDetailPage : React.FC = () => {
  const { id } = useParams();
  const [ data, setData ] = useState<API.GeneratorVO>({});
  const [ loading, seLoading ] = useState(false);

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
      setData(res.data);
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

  const tagsListView = (tags: string) => {
    if (!tags) {
      return <></>;
    }
    return (
      <div>
        {tags.map((tag: string) => {
          return <Tag key={tag}>{tag}</Tag>;
        })}
      </div>
    );
  };

  return (

    <ProCard loading={loading}>
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
            <Space size='middle'>
              <Button type='primary'>立即使用</Button>
            </Space>
          </Col>
          <Col flex='320px'>
            <Image src={data.picture}></Image>
          </Col>
        </Row>
      </Card>

    </ProCard>
  );


}

export default GeneratorDetailPage;
