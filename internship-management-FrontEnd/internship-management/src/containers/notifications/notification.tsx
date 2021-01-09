import React, { useEffect, useState } from "react";
import axiosClient from "../../api/axiosClient";
import notificationApi from "../../api/notification";
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import styles from "../notifications/notification.module.scss";
import { Button, notification, Card, Form, Input, Select, Spin } from 'antd';
import { CKEditor } from '@ckeditor/ckeditor5-react';
import { useTranslation } from "react-i18next";

const { Option } = Select;

const Notification = () => {

    const [form] = Form.useForm();
    const { t } = useTranslation("common");

    const [replyContent, setReplyContent] = useState<any>([]);
    const [sender, setSender] = useState<any>([]);
    const [loading, setLoading] = useState(false);

    const createNotification = async (values) => {
        setLoading(true);
        let bitOr = values.sender.reduce(function (a, b) {
            return a | b;
        }, 0);
        try {
            const formatData = {
                "title": values.title,
                "overView": replyContent,
                "groupId": bitOr
            }
            await axiosClient.post("http://ijobs-env.eba-8gqm58cd.ap-southeast-1.elasticbeanstalk.com/api/notification/create", formatData)
                .then(response => {
                    if (response === undefined) {
                        setLoading(false);
                        notification["error"]({
                            message: t("notification.messageNotification"),
                            description:
                                t("notification.descriptionNotificationFailure"),

                        });
                    }
                    else {
                        setLoading(false);
                        notification["success"]({
                            message: t("notification.messageNotification"),
                            description:
                                t("notification.descriptionNotificationSuccess"),

                        });
                        form.resetFields();
                    }
                }
                );
        } catch (error) {
            throw error;
        }
    }

    useEffect(() => {
        const getGroup = async () => {
            const response = await notificationApi.getGroupNotification();
            setSender(response.data);
        }

        getGroup();
    }, [])

    return (
        <div id={styles.container}>
            <h1 style={{ marginTop: 50, marginBottom: 0, padding: 15, color: "#FFFFFF", background: "linear-gradient(-135deg,#1de9b6,#1dc4e9)" }}>{t("notification.createNotification")}</h1>
            <div className={styles.dialog} >
                <Spin spinning={loading}>
                    <Card>
                        <div>
                            <Form
                                form={form}
                                name="register"
                                onFinish={createNotification}
                                scrollToFirstError
                            >

                                <Form.Item
                                    name="title"
                                    rules={[
                                        {
                                            required: true,
                                            message: t("notification.inputTitle"),
                                        },
                                    ]}
                                    hasFeedback
                                >
                                    <Input placeholder={t("notification.inputTitle")} />
                                </Form.Item>

                                <Form.Item
                                    name="sender"
                                    rules={[
                                        {
                                            required: true,
                                            message: t("notification.inputSender"),
                                        },
                                    ]}
                                    hasFeedback
                                    style={{ marginBottom: 30 }}
                                >
                                    <Select
                                        showSearch
                                        mode="multiple"
                                        style={{ width: '100%' }}
                                        tokenSeparators={[',']}
                                        placeholder={t("notification.inputSender")}
                                        optionFilterProp="children"
                                        filterOption={(input, option) =>
                                            option?.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                        }
                                    >
                                        {sender.map((item, index) => {
                                            return (
                                                <Option value={item.id} key={index} label="sender">
                                                    {item.description.toUpperCase()}
                                                </Option>
                                            )
                                        })}
                                    </Select>
                                </Form.Item>

                                <Form.Item
                                    name="overview"
                                    rules={[
                                        {
                                            required: true,
                                            message: t("notification.inputOverView"),
                                        },
                                    ]}

                                    style={{ marginBottom: 30 }}>

                                    <CKEditor
                                        editor={ClassicEditor}
                                        data=""
                                        onReady={editor => {
                                            // You can store the "editor" and use when it is needed.
                                            console.log('Editor is ready to use!', editor);
                                        }}
                                        onChange={(event, editor) => {
                                            const data = editor.getData();
                                            setReplyContent(data);
                                        }}
                                    />
                                </Form.Item>

                                <Form.Item >
                                    <Button style={{ background: "#FF8000", color: '#FFFFFF', float: 'right', marginTop: 20 }} htmlType="submit">
                                        {t("notification.confirm")}
                                    </Button>
                                </Form.Item>
                            </Form>
                        </div>
                    </Card>
                </Spin>
            </div>
        </div>
    )
}

export default Notification;