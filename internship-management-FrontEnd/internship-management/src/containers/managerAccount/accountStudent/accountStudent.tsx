import React, { useEffect, useState } from 'react';
import axiosClient from "../../../api/axiosClient";
import styles from "../accountStudent/accountStudent.module.scss"
import { accountApi } from "../../../api/accountApi"
import {
  Row, Col, Tag, Typography, Avatar, Button, Drawer, PageHeader, Form, Card,
  Input, Select, Spin, Pagination, notification, DatePicker, Popconfirm
} from 'antd';
import { UserOutlined } from '@ant-design/icons';
import { EditOutlined, PlusOutlined, DeleteOutlined } from '@ant-design/icons';
import { useTranslation } from 'react-i18next';

const dateFormat = 'YYYY/MM/DD';
const { Title } = Typography;
const { Option } = Select;

function AccountTeacher() {

  const [accountData, setAccountData] = useState<any>([]);
  const [totalPage, setTotalPage] = useState<any>();
  const [visible, setVisible] = useState(false);
  const [visibleEdit, setVisibleEdit] = useState(false);
  const [loading, setLoading] = useState(true);
  const [loadingEdit, setLoadingEdit] = useState(true);

  const [form] = Form.useForm();
  const [form1] = Form.useForm();

  const { t } = useTranslation("common");

  const createRecruitment = async (values) => {
    setLoading(true);
    try {
      const formatData = {
        "username": values.username,
        "password": values.password,
        "roles": [1],
        "imageLogoURL": "",
        "imageBackgroundURL": "",
        "studentCode": values.studentCode,
        "schoolYear": values.schoolYear,
        "birthDate": "2020-11-06T09:57:17.358Z",
        "gender": values.gender,
        "firstName": values.firstName,
        "lastName": values.lastName,
        "email": values.email,
        "phoneNumber": values.phoneNumber,
        "address": values.address,
        "department": values.department,
        "klass": values.klass
      }
      await axiosClient.post("http://ijobs-env.eba-8gqm58cd.ap-southeast-1.elasticbeanstalk.com/api/accounts/createStudentAcc", formatData).then(response => {
        if (response === undefined) {
          notification["error"]({
            message: `Thông báo`,
            description:
              'Tạo tài khoản thất bại',

          });
        }
        else {
          notification["success"]({
            message: `Thông báo`,
            description:
              'Tạo tài khoản thành công',
          });
          setTimeout(() =>
            handlePage(1, 10), 500);
        };
      });;
      setLoading(false);
      setVisible((visible) => !visible)
    } catch (error) {
      throw error;
    }
    setTimeout(function () {
      setLoading(false);
    }, 1000);
    handlePage(1, 10);
  }

  const updateRecruitment = async (values) => {
    setLoadingEdit(true);
    try {
      const formatData = {
        "studentCode": values.studentCode,
        "schoolYear": values.schoolYear,
        // "birthDate": "2020-11-06T09:57:17.358Z",
        // "gender": values.gender,
        "firstName": values.firstName,
        "lastName": values.lastName,
        "email": values.email,
        "phoneNumber": values.phoneNumber,
        "address": values.address,
        "department": values.department,
        "klass": values.klass
      }
      await axiosClient.put("http://ijobs-env.eba-8gqm58cd.ap-southeast-1.elasticbeanstalk.com/api/accounts/updateStudentAcc/" + values.accountId, formatData).then(response => {
        if (response === undefined) {
          notification["error"]({
            message: `Thông báo`,
            description:
              'Cập nhật tài khoản thất bại',

          });
        }
        else {
          notification["success"]({
            message: `Thông báo`,
            description:
              'Cập nhật tài khoản thành công',
          });
          setTimeout(() =>
            handlePage(1, 10), 500);
        };
      });;
      setLoadingEdit(false);
      setVisibleEdit((visibleEdit) => !visibleEdit)
    } catch (error) {
      throw error;
    }
    setTimeout(function () {
      setLoading(false);
    }, 1000);
    handlePage(1, 10);
  }

  const handleDelete = async (id) => {
    try {
      await accountApi.deleteAccount(id).then(response => {
        if (response === undefined) {
          notification["error"]({
            message: `Thông báo`,
            description:
              'Xóa tài khoản thất bại',

          });
        }
        else {
          handlePage(1, 10);
          notification["success"]({
            message: `Thông báo`,
            description:
              'Xóa tài khoản thành công',
          });
        };
      });;
    } catch (error) {
      console.log(error);
    }
  }

  const handleModal = async () => {
    setLoading(false);
    await setVisible((visible) => !visible)
    form.resetFields();
  }

  const handleModalEdit = async () => {
    setLoadingEdit(false);
    await setVisibleEdit((visibleEdit) => !visibleEdit);
    form1.resetFields();
  }

  const handleVisibleEdit = async (id: string) => {
    try {
      const response = await accountApi.getAccountStudentById(id);
      form1.setFieldsValue(response.data);
      console.log(response.data)
    } catch (error) {
      throw error;
    }
    setLoadingEdit(false);
    console.log(id);
    setVisibleEdit((visibleEdit) => !visibleEdit);
  }

  const handlePage = async (pageNumber, pageSizes) => {
    window.scrollTo(0, 0);
    try {
      const pageformat = {
        page: pageNumber,
        pageSize: pageSizes
      }
      const response = await accountApi.getAccountStudent(pageformat);
      setAccountData(response.data.content);
      setTotalPage(response.data.totalPages);
      console.log(response.data.content);
    } catch (error) {
      throw error;
    }
  }

  useEffect(() => {
    window.scrollTo(0, 0);
    handlePage(1, 10);
  }, [])

  return (
    <div>
      <div id={styles.wrapper}>
        <div id={styles.dialog}>
          <PageHeader
            title=""
            subTitle=""
          >
            <Button icon={<PlusOutlined />} shape="round" size="middle" style={{ marginBottom: 5, backgroundColor: '#d4380d', color: "#FFFFFF" }} onClick={handleModal}>{t("create")}</Button>
            <Drawer
              visible={visible}
              title="Tạo tài khoản sinh viên"
              width={460}
              onClose={handleModal}
              footer={
                <div
                  style={{
                    textAlign: "right"
                  }}
                >
                  <Button onClick={handleModal} style={{ marginRight: 8 }}>
                    Cancel
                          </Button>
                  <Button onClick={() => form.validateFields()
                    .then((values) => {
                      setLoading(true);
                      form.resetFields();
                      createRecruitment(values);
                    })
                    .catch((o) => {
                      console.log('Validate Failed:', o);
                    })} type="primary">
                    Submit
                          </Button>
                </div>
              }
            >
              <Spin spinning={loading}>
                <Form
                  form={form}
                  name="createRecuriment"
                  layout="vertical"
                >
                  <Form.Item
                    name="username"
                    label="Tên tài khoản"
                    rules={[
                      {
                        required: true,
                        message: 'Please input your user name!',
                      },
                    ]}
                    style={{ marginBottom: 10 }}
                  >
                    <Input placeholder="Tài khoản" />
                  </Form.Item>

                  <Form.Item
                    name="password"
                    label="Mật khẩu"
                    rules={[
                      {
                        required: true,
                        message: 'Please input your password!',
                      },
                    ]}
                    style={{ marginBottom: 10 }}
                  >
                    <Input.Password placeholder="Mật khẩu" />
                  </Form.Item>

                  <Form.Item
                    name="firstName"
                    label="Họ"
                    rules={[
                      {
                        required: true,
                        message: 'Please input your company name!',
                      },
                    ]}
                    style={{ marginBottom: 10 }}
                  >
                    <Input placeholder="Họ" />
                  </Form.Item><Form.Item
                    name="lastName"
                    label="Tên"
                    rules={[
                      {
                        required: true,
                        message: 'Please input your company name!',
                      },
                    ]}
                    style={{ marginBottom: 10 }}
                  >
                    <Input placeholder="Tên" />
                  </Form.Item>

                  <Form.Item
                    name="email"
                    label="Email"
                    rules={[
                      {
                        required: true,
                        message: 'Please input your company o!',
                      },
                    ]}
                    style={{ marginBottom: 10 }}
                  >
                    <Input placeholder="Email" />
                  </Form.Item>

                  <Form.Item
                    name="studentCode"
                    label="Mã số sinh viên"
                    rules={[
                      {
                        required: true,
                        message: 'Please input your ID!',
                      },
                    ]}
                    style={{ marginBottom: 10 }}
                  >
                    <Input placeholder="Mã số sinh viên" />
                  </Form.Item>

                  <Form.Item
                    name="schoolYear"
                    label="Sinh viên năm"
                    rules={[
                      {
                        required: true,
                        message: 'Please input your school year!',
                      },
                    ]}
                    style={{ marginBottom: 10 }}
                  >
                    <Input placeholder="Sinh viên năm" />
                  </Form.Item>

                  <Form.Item
                    name="birthDate"
                    label="Năm Sinh"
                    rules={[
                      {
                        required: true,
                        message: 'Please input your birth date!',
                      },
                    ]}
                    style={{ marginBottom: 10 }}
                  >
                    <DatePicker format={dateFormat} />
                  </Form.Item>


                  <Form.Item
                    name="klass"
                    label="Lớp"
                    rules={[
                      {
                        required: true,
                        message: 'Please input your klass!',
                      },
                    ]}
                    style={{ marginBottom: 10 }}
                  >
                    <Input placeholder="Lớp" />
                  </Form.Item>

                  <Form.Item
                    name="phoneNumber"
                    label="Số điện thoại"
                    rules={[
                      {
                        required: true,
                        message: 'Please input your last name!',
                      },
                    ]}
                    style={{ marginBottom: 10 }}
                  >
                    <Input placeholder="Số điện thoại" />
                  </Form.Item>

                  <Form.Item
                    name="address"
                    label="Địa chỉ"
                    rules={[
                      {
                        required: true,
                        message: 'Please input your phoneNumber!',
                      },
                    ]}
                    style={{ marginBottom: 10 }}
                  >
                    <Input placeholder="Địa chỉ" />
                  </Form.Item>

                  <Form.Item
                    name="gender"
                    label="Giới tính"
                    rules={[
                      {
                        required: true,
                        message: 'Please input your email!',
                      },
                    ]}
                    style={{ marginBottom: 10 }}
                  >
                    <Select placeholder="Giới tính" style={{ width: '100%' }} >
                      <Option value="true">Nam</Option>
                      <Option value="false">Nữ</Option>
                    </Select>
                  </Form.Item>

                  <Form.Item
                    name="department"
                    label="Khoa"
                    rules={[
                      {
                        required: true,
                        message: 'Please input your address!',
                      },
                    ]}
                    style={{ marginBottom: 10 }}
                  >
                    <Input placeholder="Khoa" />
                  </Form.Item>
                </Form>
              </Spin>
            </Drawer>

            <Drawer
              visible={visibleEdit}
              title="Tạo tài khoản sinh viên"
              width={460}
              onClose={handleModalEdit}
              footer={
                <div
                  style={{
                    textAlign: "right"
                  }}
                >
                  <Button onClick={handleModalEdit} style={{ marginRight: 8 }}>
                    Cancel
                          </Button>
                  <Button onClick={() => form1.validateFields()
                    .then((values) => {
                      setLoadingEdit(true);
                      form1.resetFields();
                      updateRecruitment(values);
                    })
                    .catch((o) => {
                      console.log('Validate Failed:', o);
                    })} type="primary">
                    Submit
                          </Button>
                </div>
              }
            >
              <Spin spinning={loadingEdit}>
                <Form
                  form={form1}
                  name="createRecuriment"
                  layout="vertical"
                  scrollToFirstError
                >
                  <Form.Item
                    name="accountId"
                    label="ID"
                    rules={[
                      {
                        required: true,
                        message: 'Please input your company name!',
                      },
                      {
                        max: 255,
                        message: "Value should be less than 255 character",
                      },
                    ]}
                    style={{ marginBottom: 10 }}
                  >
                    <Input placeholder="Họ" disabled={true} />
                  </Form.Item>

                  <Form.Item
                    name="firstName"
                    label="Họ"
                    rules={[
                      {
                        required: true,
                        message: 'Please input your company name!',
                      },
                    ]}
                    style={{ marginBottom: 10 }}
                  >
                    <Input placeholder="Họ" />
                  </Form.Item>

                  <Form.Item
                    name="lastName"
                    label="Tên"
                    rules={[
                      {
                        required: true,
                        message: 'Please input your company name!',
                      },
                    ]}
                    style={{ marginBottom: 10 }}
                  >
                    <Input placeholder="Tên" />
                  </Form.Item>

                  <Form.Item
                    name="email"
                    label="Email"
                    rules={[
                      {
                        required: true,
                        message: 'Please input your company o!',
                      },
                    ]}
                    style={{ marginBottom: 10 }}
                  >
                    <Input placeholder="Email" />
                  </Form.Item>

                  <Form.Item
                    name="studentCode"
                    label="Mã số sinh viên"
                    rules={[
                      {
                        required: true,
                        message: 'Please input your ID!',
                      },
                    ]}
                    style={{ marginBottom: 10 }}
                  >
                    <Input placeholder="Mã số sinh viên" />
                  </Form.Item>

                  <Form.Item
                    name="schoolYear"
                    label="Sinh viên năm"
                    rules={[
                      {
                        required: true,
                        message: 'Please input your school year!',
                      },
                    ]}
                    style={{ marginBottom: 10 }}
                  >
                    <Input placeholder="Sinh viên năm" />
                  </Form.Item>

                  {/* <Form.Item
                    name="birthDate"
                    label="Năm Sinh"
                    rules={[
                      {
                        required: true,
                        message: 'Please input your birth date!',
                      },
                    ]}
                    style={{ marginBottom: 10 }}
                  >
                    <DatePicker format={dateFormat} />
                  </Form.Item> */}


                  <Form.Item
                    name="klass"
                    label="Lớp"
                    rules={[
                      {
                        required: true,
                        message: 'Please input your klass!',
                      },
                    ]}
                    style={{ marginBottom: 10 }}
                  >
                    <Input placeholder="Lớp" />
                  </Form.Item>

                  <Form.Item
                    name="phoneNumber"
                    label="Số điện thoại"
                    rules={[
                      {
                        required: true,
                        message: 'Please input your last name!',
                      },
                    ]}
                    style={{ marginBottom: 10 }}
                  >
                    <Input placeholder="Số điện thoại" />
                  </Form.Item>

                  <Form.Item
                    name="address"
                    label="Địa chỉ"
                    rules={[
                      {
                        required: true,
                        message: 'Please input your phoneNumber!',
                      },
                    ]}
                    style={{ marginBottom: 10 }}
                  >
                    <Input placeholder="Địa chỉ" />
                  </Form.Item>

                  <Form.Item
                    name="department"
                    label="Khoa"
                    rules={[
                      {
                        required: true,
                        message: 'Please input your address!',
                      },
                    ]}
                    style={{ marginBottom: 10 }}
                  >
                    <Input placeholder="Khoa" />
                  </Form.Item>
                </Form>
              </Spin>
            </Drawer>
          </PageHeader>
        </div>
      </div>
      {accountData.map((account, index) => (
        <div key={index} id={styles.wrapper}>
          <div id={styles.dialog} style={{ paddingTop: 20, paddingRight: 35, paddingLeft: 35, paddingBottom: 20 }}>
            <Card bordered={false} style={{ borderRadius: 6 }}>
              <Row gutter={{ xs: 8, sm: 16, md: 24, lg: 32 }}>
                <Col span={7}>
                  <Row >
                    <Col style={{ paddingRight: 10 }}>
                      {account?.imageEntity?.length > 0 ? <Avatar src={account?.imageEntity} /> : <Avatar size={56} icon={<UserOutlined />} />}
                    </Col>
                    <Col>
                      <Row style={{ padding: 0, marginBottom: 0 }}>
                        <Title level={5} style={{ padding: 0, marginBottom: 10, marginTop: 0 }}>{account?.firstName?.length > 0 ? account.firstName + " " + account?.lastName : account?.name}</Title>
                      </Row>
                      <Row style={{ padding: 0 }}>
                        <p>{account?.email}</p>
                      </Row>
                    </Col>
                  </Row>
                </Col>
                <Col span={7}>
                  <Row style={{ padding: 0 }}>
                    <p><i>{t("accountStudent.StudentCode")}:</i> {account?.studentCode}</p>
                  </Row>
                  <Row style={{ padding: 0 }}>
                    <p><i>{t("accountStudent.facultyOfTraining")}:</i> {account?.department}</p>
                  </Row>
                  <Row style={{ padding: 0 }}>
                    <p><i>{t("accountStudent.phoneNumber")}:</i> {account?.phoneNumber}</p>
                  </Row>
                  <Row style={{ padding: 0 }}>
                    <p><i>{t("accountStudent.YearOfBirth")}:</i> {account?.birthDate}</p>
                  </Row>
                  <Row style={{ padding: 0 }}>
                    {account?.gender === true ? <p><i>{t("accountStudent.sex")}:</i> {t("Female")}</p> : <p><i> {t("accountStudent.sex")}: {t("Male")}</i></p>}
                  </Row>
                </Col>
                <Col span={6}>
                  <Row style={{ padding: 0, marginBottom: 8 }}>
                    <p style={{ padding: 0, margin: 0, marginRight: 8, width: 100 }}><i>{t("creator")}:</i></p>
                    <Tag color="red">{account?.createBy}</Tag>
                  </Row>
                  {account?.updatedBy?.length > 0 ? <Row style={{ padding: 0, marginBottom: 8 }}>
                    <p style={{ padding: 0, margin: 0, marginRight: 8, width: 100 }}><i>{t("updatedBy")}:</i></p>
                    <Tag color="geekblue">{account?.updatedBy}</Tag>
                  </Row> : <Row style={{ padding: 0, marginBottom: 8 }}>
                      <p style={{ padding: 0, margin: 0, marginRight: 8, width: 100 }}><i>{t("updatedBy")}:</i></p>
                      <Tag color="geekblue" style={{ height: 25 }}>{t("NotUpdate")}</Tag>
                    </Row>}
                  <Row style={{ padding: 0, marginBottom: 8 }}>
                    <p style={{ padding: 0, margin: 0, marginRight: 8, width: 100 }}><i>{t("AccountType")}:</i></p>
                    <Tag color="geekblue">{account?.roleName}</Tag>
                  </Row>
                </Col>
                <Col style={{ textAlign: "center" }} span={4}>
                  <div style={{ borderLeft: '1px solid #D3D3D3', height: '100%', float: "left", marginRight: 20 }}></div>
                  <div className={styles.wrapButton}>
                    <Row justify="center" >
                      <Button shape="round" style={{ marginTop: 8, width: 100, backgroundColor: '#d4380d', color: "#FFFFFF" }} icon={<EditOutlined />} onClick={() => handleVisibleEdit(account.accountId)}>
                        {t("edit")}
                      </Button>
                      <Popconfirm
                        title={t("confirmDelete")}
                        onConfirm={() => handleDelete(account.accountId)}
                        okText={t("yes")}
                        cancelText={t("no")}
                      >
                        <Button shape="round" style={{ marginTop: 8, width: 100, backgroundColor: '#d4380d', color: "#FFFFFF" }} icon={<DeleteOutlined />}>
                          {t("delete")}
                        </Button>
                      </Popconfirm>
                    </Row>
                  </div>
                </Col>
              </Row>
            </Card>
          </div>
        </div>
      ))
      }
      <Pagination style={{ textAlign: "center" }} defaultCurrent={1} total={totalPage} onChange={handlePage}></Pagination>
    </div >
  )
}

export default AccountTeacher;



