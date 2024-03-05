import { listGeneratorVoByPageUsingPost } from '@/services/backend/generatorController';
import { UserAddOutlined, UserOutlined } from '@ant-design/icons';
import { PageContainer, ProFormSelect, ProFormText, QueryFilter, DefaultFooter } from '@ant-design/pro-components';
import { Avatar, Card, Flex, Image, Input, List, message, Tabs, Tag, Typography } from 'antd';
import moment from 'moment';
import React, { useEffect, useState } from 'react';
import FileUploader from '@/components/FileUploader';
import PictureUploader from '@/components/PictureUploader';

/**
 * 主页
 * @constructor
 */

/**
 * 设置默认分页参数
 * @returns
 */
const DEFAULT_PAGE_PARAMS: PageRequest = {
  current: 1,
  pageSize: 4,
  sortField: 'createTime',
  sortOrder: 'descent',
};

const IndexPage: React.FC = () => {

  const [loading, setLoading] = useState<boolean>(false);

  const [dataList, setDataList] = useState<API.GeneratorVO[]>([]);

  const [total, setTotal] = useState<number>(0);

  const [searchParams, setSearchParams] = useState<API.GeneratorQueryRequest>({
    ...DEFAULT_PAGE_PARAMS
  });


  const doSearch = async () => {
    try {
      setLoading(true);
      const res = await listGeneratorVoByPageUsingPost(searchParams);
      setDataList(res.data?.records ?? []);
      setTotal(Number(res.data?.total) ?? 0);
    } catch (error: any) {
      message.error('获取数据失败' + error.message);
    }
    setLoading(false);
  };


  useEffect(() => {
    doSearch();
  }, [searchParams]);

  /**
   *
   * @param tags 标签列表
   * @returns
   */
  const tagListView = (tags?: string[]) => {
    if (!tags) {
      return <></>;
    }

    return (
      <div style={{ marginBottom: 8 }}>
        {tags.map((tag) => (
          <Tag key={tag}>{tag}</Tag>
        ))}
      </div>
    );
  };


  return (<PageContainer title={<></>}>
    <Flex justify='center'>
      <Input.Search
        style={{
          width: '40vw',
          minWidth: 320,
        }}
        placeholder="请输入搜索的生成器"
        enterButton="搜索"
        allowClear
        size='large'
        onChange={(e) => {
          searchParams.searchText = e.target.value;
        }}
        onSearch={(value: string) => {
          setSearchParams({
            ...DEFAULT_PAGE_PARAMS,
            searchText: value
          })

        }}
      />
    </Flex>
    <div style={{ marginBottom: 16 }} />

    <Tabs
      size='large'
      defaultActiveKey={'latest'}
      onChange={() => { }}
      items={[
        {
          key: 'latest',
          label: '最新',
        },
        {
          key: 'recommend',
          label: '推荐',
        }
      ]}
    />
    <QueryFilter
      defaultCollapsed={false}
      span={12}
      labelWidth="auto"
      labelAlign='left'
      style={{ padding: '16px ' }}
      onChange={() => { }}
      onFinish={async (values: API.GeneratorQueryRequest) => {
        setSearchParams({
          ...DEFAULT_PAGE_PARAMS,
          // @ts-ignore
          ...values,
          searchText: searchParams.searchText,
        });
      }}
    >
      <ProFormSelect name="tags" label="标签" mode='tags' />
      <ProFormText name="name" label="名称" />
      <ProFormText name="description" label="描述" />
    </QueryFilter>
    <div style={{ marginBottom: 24 }} />


    <List<API.GeneratorVO>
      rowKey="id"
      loading={loading}
      pagination={{
        current: searchParams.current,
        pageSize: searchParams.pageSize,
        total,
        onChange(current, pageSize) {
          setSearchParams({
            ...searchParams,
            current,
            pageSize,
          });
        },
      }}
      grid={{
        gutter: 16,
        xs: 1,
        sm: 2,
        md: 3,
        lg: 3,
        xl: 4,
        xxl: 4,
      }}
      dataSource={dataList}
      renderItem={(data: API.GeneratorVO) => (
        <List.Item>
          <Card hoverable cover={<Image alt={data.name} src={data.picture} />}>
            <Card.Meta
              title={<a>{data.name}</a>}
              description={
                <Typography.Paragraph ellipsis={{ row: 2 }} style={{ height: 44}}>
                  {data.description}
                </Typography.Paragraph>
              }
            />
            {tagListView(data.tags)}
            <Flex justify='space-between' align='center'>
              <Typography.Paragraph type='secondary' style={{ fontSize: 12 }}>{moment(data.createTime).fromNow()}</Typography.Paragraph>
              <div>
                <Avatar icon={data.user?.userAvatar ?? <UserOutlined />} />
              </div>
            </Flex>
          </Card>
        </List.Item>
      )}
    />
  </PageContainer>
  );

};

export default IndexPage;
