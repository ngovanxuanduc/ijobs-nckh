import React, { useEffect, useState } from "react";
import styles from './profile.module.scss';
import { accountApi } from '../../api/accountApi';
import { Card,  Row, Avatar, Tabs, Form, Input, Button, notification, Select, Spin } from 'antd';
import backgroundDTU from "../../assets/image/backgroundDTU.jpg";
import { parseJwt } from "../../utils/common";
import axiosClient from "../../api/axiosClient";

const { TabPane } = Tabs;

const Profile = () => {

    const [form] = Form.useForm();
    const [form1] = Form.useForm();
    const [loading, setLoading] = useState(false);
    const [profile, setProfile] = useState<any>([]);

    const onFinish = async (values) => {
        setLoading(true);
        try {
            const formatData = {
                "oldPassword": values.oldpassword,
                "newPassword": values.password
            }
            await axiosClient.post("http://ijobs-env.eba-8gqm58cd.ap-southeast-1.elasticbeanstalk.com/api/auth/changePassword", formatData)
                .then(response => {
                    if (response === undefined) {
                        setLoading(false);
                        notification["error"]({
                            message: `Thông báo`,
                            description:
                                'Thay Đổi Mật Khẩu Thất Bại',

                        });
                    }
                    else {
                        setLoading(false);
                        notification["success"]({
                            message: `Thông báo`,
                            description:
                                'Thay Đổi Mật Khẩu Thành Công',

                        });
                    }
                }
                );
        } catch (error) {
            throw error;
        }
    };

    const handleAddSkills = async (values) => {
        console.log(values);
        try {
            const formatData = {
                "skills": values.skills
            }
            await axiosClient.post("http://ijobs-env.eba-8gqm58cd.ap-southeast-1.elasticbeanstalk.com/api/skills/create", formatData)
                .then(response => {
                    if (response === undefined) {
                        notification["error"]({
                            message: `Thông báo`,
                            description:
                                'Thêm kỹ năng thất bại',

                        });
                    }
                    else {
                        notification["success"]({
                            message: `Thông báo`,
                            description:
                                'Thêm kỹ năng thành công',
                        });
                        form.resetFields();
                    }
                }
                );
        } catch (error) {
            throw error;
        }
    };

    const checkAuth = () => {
        let author = parseJwt(localStorage.getItem('token'))
        let _author = author.role;
        if (_author[0].authority === "ROLE_ADMIN")
            return true;
        return false;
    }

    useEffect(() => {
        const getUserCurrent = async () => {
            const dataUser = await accountApi.afterLogin();
            setProfile(dataUser.data.inf);
            console.log(dataUser.data.inf)
        }
        getUserCurrent();
    }, [])

    return (
        <div>
            <Tabs className={styles.container} style={{ margin: 40, padding: 15 }} tabPosition="left">
                <TabPane tab="Thông Tin Cá Nhân" key="1" >
                    {
                        checkAuth() ? <div >
                            <img style={{ width: '100%', height: 330, zIndex: 1 }} src={backgroundDTU} alt="" ></img>
                            <Row justify="center" style={{ marginTop: -130, paddingBottom: 50 }}>
                                <Card bordered={false} className={styles.card}>
                                    <div style={{ textAlign: 'center', width: 300 }}>
                                        <Avatar size={{
                                            xs: 24,
                                            sm: 32,
                                            md: 40,
                                            lg: 64,
                                            xl: 80,
                                            xxl: 100,
                                        }}
                                            src="https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png" />
                                        <h1 style={{ paddingTop: 10 }}>{profile.firstName + " " + profile.lastName}</h1>
                                    </div>
                                    <div className={styles.profile}>
                                        <h1>Địa chỉ: {profile.address}</h1>
                                        <h1>Khoa: {profile.department}</h1>
                                        <h1>Email: {profile.email}</h1>
                                        <h1>giới tính: {profile.gender === true ? "Nam" : "Nữ"}</h1>
                                        <h1>Số điện thoại: {profile.phoneNumber}</h1>
                                    </div>
                                </Card>
                            </Row>
                        </div> : <div id={styles.container}>
                                <img style={{ width: '100%', height: 350, zIndex: 1 }} src={backgroundDTU} alt=""></img>
                                <Row justify="center" style={{ marginTop: -130, paddingBottom: 50 }}>
                                    <Card bordered={false} className={styles.card}>
                                        <div style={{ textAlign: 'center', width: 300 }}>
                                            <Avatar size={{
                                                xs: 24,
                                                sm: 32,
                                                md: 40,
                                                lg: 64,
                                                xl: 80,
                                                xxl: 100,
                                            }}
                                                src="https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png" />
                                            <h1 style={{ paddingTop: 10 }}>{profile.name}</h1>
                                        </div>
                                        <div className={styles.profile}>
                                            <h1>Thông tin công ty: {profile.companyInfo}</h1>
                                            <h1>Email: {profile.email}</h1>
                                            <h1>Số điện thoại: {profile.phoneNumber}</h1>
                                            <h1>Địa chỉ: {profile.location}</h1>
                                            <h1>Quốc gia: {profile.country}</h1>
                                        </div>
                                    </Card>
                                </Row>
                            </div>
                    }
                </TabPane>
                <TabPane tab="Thay Đổi Mật Khẩu" key="2">
                    <div >
                        <img style={{ width: '100%', height: 330, zIndex: 1 }} src={backgroundDTU} alt=""></img>
                        <Spin spinning={loading} >
                            <Row justify="center" style={{ marginTop: -170, paddingBottom: 50 }}>
                                <Card bordered={false} className={styles.card} style={{ width: 450, height: 350, paddingTop: 40 }}>
                                    <Form
                                        form={form}
                                        name="register"
                                        onFinish={onFinish}
                                        scrollToFirstError
                                    >
                                        <Form.Item
                                            name="oldpassword"
                                            rules={[
                                                {
                                                    required: true,
                                                    message: 'Please input your old password!',
                                                },
                                            ]}
                                            hasFeedback
                                        >
                                            <Input.Password placeholder="Please input your old password!" />
                                        </Form.Item>

                                        <Form.Item
                                            name="password"
                                            rules={[
                                                {
                                                    required: true,
                                                    message: 'Please input your password!',
                                                },
                                            ]}
                                            hasFeedback
                                        >
                                            <Input.Password placeholder="Please input your new password!" />
                                        </Form.Item>
                                        <Form.Item
                                            name="confirm"
                                            dependencies={['password']}
                                            hasFeedback
                                            rules={[
                                                {
                                                    required: true,
                                                    message: 'Please confirm your new password!',
                                                },
                                                ({ getFieldValue }) => ({
                                                    validator(rule, value) {
                                                        if (!value || getFieldValue('password') === value) {
                                                            return Promise.resolve();
                                                        }

                                                        return Promise.reject('The two passwords that you entered do not match!');
                                                    },
                                                }),
                                            ]}
                                        >
                                            <Input.Password placeholder="Please confirm your password!" />
                                        </Form.Item>

                                        <Form.Item >
                                            <Button type="primary" htmlType="submit" style={{ width: '100%' }}>
                                                Confirm
                                        </Button>
                                        </Form.Item>
                                    </Form>
                                </Card>
                            </Row>
                        </Spin>
                    </div>
                </TabPane>
                <TabPane tab="Tạo Kỹ Năng" key="3">
                    <div >
                        <img style={{ width: '100%', height: 330, zIndex: 1 }} src={backgroundDTU} alt=""></img>
                        <Spin spinning={loading}>
                            <Row justify="center" style={{ marginTop: -170, paddingBottom: 50 }}>
                                <Card bordered={false} className={styles.card} style={{ width: 450, height: 350, paddingTop: 40 }}>
                                    <Form
                                        form={form1}
                                        name="skills"
                                        onFinish={handleAddSkills}
                                        scrollToFirstError
                                    >
                                        <p style={{ fontSize: 15 }}>Create Skills</p>
                                        <Form.Item
                                            name="skills"
                                            rules={[
                                                {
                                                    required: true,
                                                    message: 'Please input your skills!',
                                                },
                                            ]}
                                            hasFeedback
                                        >
                                            <Select mode="tags" style={{ width: '100%' }} placeholder="Please input your skills">

                                            </Select>
                                        </Form.Item>

                                        <Form.Item >
                                            <Button type="primary" htmlType="submit" style={{ width: '100%' }}>
                                                Confirm
                                            </Button>
                                        </Form.Item>
                                    </Form>
                                </Card>
                            </Row>
                        </Spin>
                    </div>
                </TabPane>
            </Tabs>

        </div>
    )
}

export default Profile;