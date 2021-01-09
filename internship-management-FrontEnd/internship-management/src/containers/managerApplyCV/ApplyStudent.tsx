import React, { useState, useEffect } from 'react';
import styles from "../managerApplyCV/ApplyStudent.module.scss";
import recruitmentApi from "../../api/recruitmentApi";
import {
    Col, Row, Typography, Button, PageHeader, Card, Avatar, Pagination, notification, BackTop
} from 'antd';
import { EditOutlined, DeleteOutlined, UserOutlined } from '@ant-design/icons';
import axiosClient from "../../api/axiosClient";
import { useParams, useHistory } from 'react-router-dom';

const { Title } = Typography;

function ApplyStudent() {

    const [recruitment, setRecruitment] = useState<any>([]);

    const history = useHistory();
    let { id }: any = useParams();

    // xóa tin tuyển dụng
    const handleDelete = (id: string) => {
        const deleteRecruitment = async () => {
            try {
                await axiosClient.delete("http://ijobs-env.eba-8gqm58cd.ap-southeast-1.elasticbeanstalk.com/api/recruitments/" + id)
                    .then(response => {
                        if (response === undefined) {
                            notification["error"]({
                                message: `Thông báo`,
                                description:
                                    'Xóa tin tuyển dụng thất bại',

                            });
                        }
                        else {
                            notification["success"]({
                                message: `Thông báo`,
                                description:
                                    'Xóa tin tuyển dụng thành công',

                            });
                            setTimeout(() =>
                                fetchRecruitment(), 500);
                        };
                    });
            } catch (error) {
                throw error;
            }
        }
        deleteRecruitment();
    }

    //get toàn bộ data sinh viên apply
    const fetchRecruitment = async () => {
        try {
            const response = await recruitmentApi.getRecruitmentId(id);
            setRecruitment(response.data.students);
            console.log(response);
        } catch (error) {
            throw error;
        }
    }

    //tạo discussion
    const handleCreateDiscussion = async (receiver) => {
        try {
            const formatData = {
                recruitmentID: id,
                receiver: receiver
            }
            await axiosClient.post("http://ijobs-env.eba-8gqm58cd.ap-southeast-1.elasticbeanstalk.com/api/discuss/create", formatData).then(response => {
                history.push("/discussion-details/" + response.data.id)
            })
        } catch (error) {
            throw error;
        }
    }

    //từ chối cv
    const handleRejectApply = async (studentId: any) => {
        console.log("student" + studentId);
        console.log("recruitment" +id);
        try {
            const formatData = {
                reId: id,
                stId: studentId
            }
            await axiosClient.post("http://ijobs-env.eba-8gqm58cd.ap-southeast-1.elasticbeanstalk.com/api/cv/rejectCV", formatData).then(response => {
                if (response === undefined) {
                    notification["error"]({
                        message: `Thông báo`,
                        description:
                            'Xóa thất bại',

                    });
                }
                else {
                    notification["success"]({
                        message: `Thông báo`,
                        description:
                            'Xóa thành công',

                    });
                    setTimeout(() =>
                        fetchRecruitment(), 500);
                };
            })
        } catch (error) {
            throw error;
        }
    }


    useEffect(() => {
        fetchRecruitment();
    }, [])

    return (
        <div className={styles.wrapper}>
            <div id={styles.wrapper}>
                <div id={styles.dialog}>
                    <PageHeader
                        title=""
                        subTitle=""
                    >
                        <p style={{ fontWeight: 'bolder', fontSize: 17 }}>Danh Sách Sinh Viên Ứng Tuyển</p>
                    </PageHeader>
                </div>
            </div>
            {recruitment.map((account, index) => (
                <div key={index} id={styles.wrapper}>
                    <div id={styles.dialog} style={{ paddingTop: 20, paddingRight: 35, paddingLeft: 35, paddingBottom: 20 }}>
                        <Card bordered={false} style={{ borderRadius: 6 }}>
                            <Row gutter={{ xs: 8, sm: 16, md: 24, lg: 32 }}>
                                <Col span={18}>
                                    <Row >
                                        <Col style={{ paddingRight: 10 }}>
                                            {account?.imageEntity?.length > 0 ? <Avatar src={account?.imageEntity} /> : <Avatar size={56} icon={<UserOutlined />} />}
                                        </Col>
                                        <Col>
                                            <Row style={{ padding: 0, marginBottom: 0 }}>
                                                <Title level={5} style={{ padding: 0, marginBottom: 10, marginTop: 0 }}>{account?.fullName}</Title>
                                            </Row>
                                            <Row style={{ padding: 0 }}>
                                                <p>{account?.id}</p>
                                            </Row>
                                        </Col>
                                    </Row>
                                </Col>
                                <Col style={{ textAlign: "center" }} span={4}>
                                    <div style={{ borderLeft: '1px solid #D3D3D3', height: '100%', float: "left", marginRight: 20 }}></div>
                                    <div className={styles.wrapButton}>
                                        <Row justify="center" >
                                            <Button shape="round" style={{ marginTop: 8, marginLeft: 10, width: '100%', backgroundColor: '#d4380d', color: "#FFFFFF" }} icon={<EditOutlined />} onClick={() => handleCreateDiscussion(account.id)}>
                                                Interview
                                            </Button>
                                            <Button shape="round" style={{ marginTop: 8, marginLeft: 10, width: '100%', backgroundColor: '#d4380d', color: "#FFFFFF" }} icon={<DeleteOutlined />} onClick={() => handleRejectApply(account.id)}>
                                                Reject
                                            </Button>
                                        </Row>
                                    </div>
                                </Col>
                            </Row>
                        </Card>
                    </div>
                </div>
            ))
            }
            <Pagination style={{ textAlign: "center" }} defaultCurrent={1} total={5} ></Pagination>
            <BackTop style={{ textAlign: 'right' }} />
        </div >
    )
}

export default ApplyStudent;