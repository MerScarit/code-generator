import { Button, Card, Form, FormListFieldData, Input, Space } from 'antd';
import { CloseOutlined } from '@ant-design/icons';
import ModelConfig from '@/pages/Generator/Detail/components/ModelConfig';

interface Props{
  formRef: any;
  oldData?: any;
}

export default (props:Props) => {
  const { formRef, oldData } = props;

  /**
   * 单个字段表单视图
   * @param field
   * @param remove
   */
  const singleFieldFormView = (
    field: FormListFieldData,
    remove: (index: number | number[]) => void) => {
    return (
      <Space key={field.key}>
        <Form.Item label='字段名称' name={[field.name, 'fieldName']}>
          <Input />
        </Form.Item>
        <Form.Item label='字段描述' name={[field.name, 'description']}>
          <Input />
        </Form.Item>
        <Form.Item label='类型' name={[field.name, 'type']}>
          <Input />
        </Form.Item>
        <Form.Item label='缩写' name={[field.name, 'abbr']}>
          <Input />
        </Form.Item>
        {remove && (
          <Button type='primary' danger onClick={() => remove(field.name)}>
            删除
          </Button>
        )
        }
      </Space>
    );
  };


  return (
    <Form.List name={['modelConfig', 'models']}>
      {(fields, { add, remove }) => {
        return (
          <div style={{ display: 'flex', rowGap: 16, flexDirection: 'column' }}>
            {fields.map((field) => {
              const modelConfig = formRef?.current?.getFieldValue()?.modelConfig ?? oldData.modelConfig;
              const groupKey = modelConfig.models?.[field.name]?.groupKey;
              return (
                < Card
                  size='small'
                  title={groupKey ? '分组字段' : '无分组字段'}
                  key={field.key}
                  extra={
                    <CloseOutlined
                      onClick={() => {
                        remove(field.name);
                      }}
                    />
                  }
                >
                  {groupKey ?
                    (
                      <Space>
                        <Form.Item label='分组Key' name={[field.name, 'groupKey']}>
                          <Input />
                        </Form.Item>
                        <Form.Item label='分组名称' name={[field.name, 'grourpName']}>
                          <Input />
                        </Form.Item>
                        <Form.Item label='类型' name={[field.name, 'type']}>
                          <Input />
                        </Form.Item>
                        <Form.Item label='条件' name={[field.name, 'condition']}>
                          <Input />
                        </Form.Item>
                      </Space>
                    ) : (
                      /*引入单表单字段列表试图*/
                      singleFieldFormView(field, remove)
                    )};


                  {/* Nest Form.List */}
                  {groupKey && (
                    <Form.Item label='subList'>
                      <Form.List name={[field.name, 'models']}>
                        {(subFields, subOpt) => (
                          <div style={{ display: 'flex', flexDirection: 'column', rowGap: 16 }}>
                            {subFields.map((subField) => (
                              singleFieldFormView(subField, subOpt.remove)
                            ))}
                            <Button type='dashed' onClick={() => subOpt.add()} block>
                              添加组内字段
                            </Button>
                          </div>
                        )}
                      </Form.List>
                    </Form.Item>
                  )}
                </Card>
              );

            })}

            <Button type='dashed' onClick={() => add()}>
              添加单个字段
            </Button>
            <Button type='dashed' onClick={() => add(
              {
                groupKey: '分组Key',
                groupName: '分组名称',
              },
            )}>
              添加分组字段
            </Button>
          </div>
        );
      }}
    </Form.List>
  );
};
