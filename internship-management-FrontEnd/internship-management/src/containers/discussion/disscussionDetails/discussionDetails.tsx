import React, { useEffect, useState } from "react";
import axiosClient from "../../../api/axiosClient";
import Discussion from "../../../api/discussionApi";
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import styles from "../../curriculumVitae/curriculumVitae.module.scss";
import { useParams } from "react-router-dom";
import { Empty, notification, Avatar, Spin, Col, Row, Button, Card } from 'antd';
import { CKEditor } from '@ckeditor/ckeditor5-react';
import { DateTime } from "../../../utils/dateTime";
import { useTranslation } from "react-i18next";

const DiscussionDetails = () => {

    let { id }: any = useParams();
    const { t } = useTranslation("common");

    const [discussion, setDiscussion] = useState<any>([]);
    const [contentDiscussion, setContentDiscussion] = useState<any>([]);
    const [userReceiver, setUserReceiver] = useState<any>([]);
    const [replyContent, setReplyContent] = useState<any>([]);
    const [loading, setLoading] = useState(false);

    const handleReply = async () => {
        setLoading(true);
        let responseContent = await Discussion.getDetailsDiscussion(id);
        try {
            const formatData = {
                "discussID": discussion.id,
                "content": replyContent
            }
            await axiosClient.post("http://ijobs-env.eba-8gqm58cd.ap-southeast-1.elasticbeanstalk.com/api/discuss/reply", formatData)
                .then(response => {
                    if (response === undefined) {
                        setLoading(false);
                        notification["error"]({
                            message: (t("discussion.notification")),
                            description:
                                (t("discussion.replyFailure")),

                        });
                    }
                    else {
                        setLoading(false);
                        notification["success"]({
                            message: (t("discussion.notification")),
                            description:
                                (t("discussion.replySuccess")),

                        });
                        setDiscussion(responseContent.data.discuss)
                        setContentDiscussion(responseContent.data.content);
                        console.log(response.data.content)
                    }
                }
                );
        } catch (error) {
            throw error;
        }

        let response = await Discussion.getDetailsDiscussion(id);
        setDiscussion(response.data.discuss)
        setContentDiscussion(response.data.content);
        console.log(response.data.content)
    }

    const handleSolveCase = async () => {
        try {
            const formatData = {
                "stId": userReceiver.accountId,
                "reId": discussion.recruitmentID
            }
            await axiosClient.post("http://ijobs-env.eba-8gqm58cd.ap-southeast-1.elasticbeanstalk.com/api/cv/acceptCV", formatData)
                .then(response => {
                    if (response === undefined) {
                        setLoading(false);
                        notification["error"]({
                            message: `Thông báo`,
                            description:
                                'Trả lời thất bại',

                        });
                    }
                    else {
                        setLoading(false);
                        notification["success"]({
                            message: `Thông báo`,
                            description:
                                'Trả lời thành công',

                        });
                    }
                }
                );
        } catch (error) {
            throw error;
        }
    }

    useEffect(() => {
        const getDetailDiscussion = async () => {
            let response = await Discussion.getDetailsDiscussion(id);
            setDiscussion(response.data.discuss)
            setContentDiscussion(response.data.content);
            console.log(response);
        }

        getDetailDiscussion();
    }, [])

    return (
        <div id={styles.container}>
            <div className={styles.dialog}>
                <Card>
                    <Row>
                        <Col span={12}>
                            <h1>{t("discussion.seeCase")} {discussion.id}</h1>
                        </Col>
                        <Col span={12}>
                            <Button style={{ background: "#FF8000", color: '#FFFFFF', float: 'right' }} onClick={handleSolveCase}>{t("discussion.solveCase")}</Button>
                        </Col>
                    </Row>
                </Card>
            </div>
            <h1 style={{ marginTop: 50, marginBottom: 0, padding: 15, color: "#FFFFFF", background: "linear-gradient(-135deg,#1de9b6,#1dc4e9)" }}>{t("discussion.caseDetails")}</h1>
            <div className={styles.dialog} >
                <Card>
                    <Row>
                        <Col span={12} style={{ borderRight: '2px solid red' }}>
                            <h1>{t("discussion.dateCreated")}</h1>
                            <h1>{DateTime(discussion.createdAt)}</h1>
                            <h1 style={{ marginTop: 20 }}>{t("discussion.recruitment")}</h1>
                            <h1>{discussion.recruitmentName}</h1>
                        </Col>
                        <Col span={12} style={{ paddingLeft: 20 }}>
                            <h1>{t("discussion.name")}</h1>
                            <h1>{discussion.receiverName}</h1>
                            <h1 style={{ marginTop: 20 }}>{t("discussion.email")}</h1>
                            <h1>{discussion.studentEmail}</h1>
                            <h1 style={{ marginTop: 20 }}>{t("discussion.phoneNumber")}</h1>
                            <h1>{discussion.studentPhone}</h1>
                        </Col>
                    </Row>
                </Card>
            </div>
            <h1 style={{ marginTop: 50, marginBottom: 0, padding: 15, color: "#FFFFFF", background: "linear-gradient(-135deg,#1de9b6,#1dc4e9)" }}>{t("discussion.correspondence")}</h1>

            <Spin spinning={loading} >
                <div className={styles.dialog} >
                    <Card>
                        {contentDiscussion.length > 0 ?
                            <div>
                                {contentDiscussion.map(function (data, index) {
                                    return (
                                        <div>
                                            <Row >
                                                <Col span={6} style={{ padding: 10 }}>
                                                    <Avatar size={50} src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png" />
                                                    <p>{data.owner}</p>
                                                    <p>{DateTime(data.createdAt)}</p>
                                                </Col>
                                                <Col span={18} style={{ padding: 10 }}>
                                                    <div style={{ background: "#eef0f3", padding: 20, borderRadius: 4 }} key={index} dangerouslySetInnerHTML={{ __html: data.content }}>
                                                    </div>
                                                </Col>
                                            </Row>
                                        </div>
                                    )
                                })}
                            </div>
                            : <Empty image={Empty.PRESENTED_IMAGE_SIMPLE} />}
                    </Card>
                </div>
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
                <div className={styles.dialog}>
                    <Card>
                        <Row>
                            <Col span={12}>

                            </Col>
                            <Col span={12}>
                                <Button style={{ background: "#FF8000", color: '#FFFFFF', float: 'right' }} onClick={handleReply}>{t("discussion.reply")}</Button>
                            </Col>
                        </Row>
                    </Card>
                </div>
            </Spin>
        </div>
    )
}

export default DiscussionDetails;