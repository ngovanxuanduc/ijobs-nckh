import React, { useState, useEffect } from 'react';
import styles from "../home/home.module.scss";
import recruitmentApi from "../../api/recruitmentApi";
import location from "../../assets/icon/position.svg";
import { DateTime } from "../../utils/dateTime";
import {
    Col, Row, Typography, Tag, Button, PageHeader, Card,
    Form, Pagination, Switch, Popconfirm, notification, BackTop
} from 'antd';
import { useTranslation } from 'react-i18next';
import { EditOutlined, DeleteOutlined, PlusOutlined, EyeOutlined, CalendarOutlined, UserOutlined, TeamOutlined, HomeOutlined } from '@ant-design/icons';
import axiosClient from "../../api/axiosClient";
import { parseJwt } from '../../utils/common';
import { useHistory } from 'react-router-dom';

const { Title } = Typography;
const DATE_TIME_FORMAT = "YYYY/MM/DD";

const Home = () => {

    const [recruitment, setRecruitment] = useState<any>([]);
    const [loading, setLoading] = useState(true);
    const [form] = Form.useForm();
    const [totalRecruitment, setTotalRecruitment] = useState(Number);

    const history = useHistory();

    const { t } = useTranslation("common");

    //check role
    const checkAuth = () => {
        let author = parseJwt(localStorage.getItem('token'))
        let _author = author.role;
        if (_author[0].authority === "ROLE_ADMIN") {
            return true;
        } else {
            return false;
        }
    }

    const handleDetaisRecruitment = (id: string) => {
        history.push("/apply-student/" + id)
    }

    const handleDetaisView = (id: string) => {
        history.push("/details-recruitment/" + id)
    }

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

    //approve tin tuyển dụng
    const handleApprove = (id: string) => {
        const url = "http://ijobs-env.eba-8gqm58cd.ap-southeast-1.elasticbeanstalk.com/api/recruitments/approve/" + id;
        try {
            axiosClient.put(url);
        } catch (error) {
            throw error;
        }
    }

    //get toàn bộ data tin tuyển dụng
    const fetchRecruitment = async () => {
        try {
            const response = await recruitmentApi.getAll();
            setRecruitment(response.data.content);
            setTotalRecruitment(response.data.totalElements);
            console.log(response.data.content);
        } catch (error) {
            throw error;
        }
    }

    //phân trang tin tuyển
    const handlePage = async (page, size) => {
        window.scrollTo(0, 0);
        try {
            const pageformat = {
                page: page,
                pageSize: size
            }
            const response = await recruitmentApi.getPage(pageformat)
            setRecruitment(response.data.content)
        } catch (error) {
            throw (error);
        }
    }

    const handleModal = () => {
        history.push("/create-recruitment")
        form.resetFields();
    }

    const editRecruitment = (id: string = "") => {
        history.push("/edit-recruitment/" + id);
    }

    useEffect(() => {
        fetchRecruitment();
        setTimeout(function () {
            setLoading(false);
        }, 500);
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

                    </PageHeader>
                </div>
            </div>
            {recruitment.map((recruitmentDetail, index) => {
                return (
                    <div key={index}>
                        <div id={styles.wrapper}>
                            <div id={styles.dialog} style={{ padding: 12 }}>
                                <Card bordered={false} style={{ borderRadius: 6 }}>
                                    <Row gutter={{ xs: 8, sm: 16, md: 24, lg: 32 }}>
                                        <Col style={{ paddingRight: 0 }} span={6}>
                                            {recruitmentDetail?.imageUrl?.length > 0 ? <img src={recruitmentDetail.imageUrl} alt="" style={{ height: 150, width: '100%' }}></img> : <img style={{ height: 150, width: '100%' }} alt="" src='https://s3-us-west-2.amazonaws.com/s.cdpn.io/169963/photo-1429043794791-eb8f26f44081.jpeg' ></img>}
                                        </Col>
                                        <Col style={{ paddingRight: 0 }} span={7}>
                                            <Title style={{ marginBottom: 0, color: '#d4380d' }} level={5}>{recruitmentDetail.title.toUpperCase()}</Title>
                                            <p style={{ margin: 0, fontSize: 14 }}>{recruitmentDetail.createdBy?.firstName ? <UserOutlined /> : <HomeOutlined />}
                                                <u style={{ paddingLeft: 5 }}>
                                                    {recruitmentDetail.createdBy?.firstName ? recruitmentDetail.createdBy?.firstName + " " + recruitmentDetail.createdBy?.lastName : recruitmentDetail.createdBy?.companyInfo}
                                                </u>
                                            </p>
                                            {recruitmentDetail.skills.map((skills, index) => {
                                                return (
                                                    <Tag key={index} style={{ marginRight: 5, marginTop: 8, fontSize: 13 }}>{skills.skillName.toUpperCase()}</Tag>
                                                )
                                            }
                                            )}
                                            <p style={{ marginTop: 5, marginBottom: 0, fontSize: 14 }}><img src={location} alt="" style={{ width: 18, height: 'auto' }}></img> {recruitmentDetail.locationDescription}</p>
                                            <p style={{ marginTop: 5, fontSize: 14, marginBottom: 0 }}><CalendarOutlined />  {DateTime(recruitmentDetail.startTime, DATE_TIME_FORMAT)} - {DateTime(recruitmentDetail.endTime, DATE_TIME_FORMAT)}</p>
                                            <p style={{ padding: 0, fontSize: 14 }} onClick={() => handleDetaisRecruitment(recruitmentDetail.id)}><TeamOutlined /> Số SV ứng tuyển: {recruitmentDetail.numberOfStudentApply}</p>

                                        </Col>
                                        <Col style={{ paddingLeft: 0, paddingRight: 0 }} span={6}>
                                            <p style={{ margin: 0, fontSize: 14 }} className={styles.fixLine} dangerouslySetInnerHTML={{ __html: recruitmentDetail.description }}></p>
                                        </Col>
                                        <Col style={{ paddingLeft: 0, paddingRight: 0, width: 10 }} span={0}>
                                        </Col>
                                        <Col style={{ textAlign: "center", float: "left" }} span={5}>
                                            <div style={{ borderLeft: '1px solid #D3D3D3', height: '100%', float: "left", paddingRight: 2 }}></div>
                                            <div className={styles.wrapButton}>
                                                <Row
                                                    justify="center"
                                                >
                                                    <Button
                                                        shape="round"
                                                        style={{ marginRight: 60, marginLeft: 60, width: '100%', backgroundColor: '#d4380d', color: "#FFFFFF" }}
                                                        icon={<EyeOutlined />}
                                                        onClick={() => handleDetaisView(recruitmentDetail.id)}
                                                    >{t("view")}
                                                    </Button>
                                                    <Button
                                                        shape="round"
                                                        style={{ marginTop: 8, marginRight: 60, marginLeft: 60, width: '100%', backgroundColor: '#d4380d', color: "#FFFFFF" }}
                                                        icon={<EditOutlined />} onClick={() => editRecruitment(recruitmentDetail.id)}>{t("edit")}
                                                    </Button>
                                                    <Popconfirm
                                                        title="Are you sure delete this recruitment?"
                                                        onConfirm={() => handleDelete(recruitmentDetail.id)}
                                                        okText="Yes"
                                                        cancelText="No"
                                                    >
                                                        <Button
                                                            shape="round"
                                                            style={{ marginTop: 8, marginRight: 60, marginLeft: 60, width: '100%', backgroundColor: '#d4380d', color: "#FFFFFF" }}
                                                            icon={<DeleteOutlined />}>{t("delete")}
                                                        </Button>
                                                    </Popconfirm>
                                                    {checkAuth() ? <div>
                                                        {recruitmentDetail.status !== 2 ? <Switch style={{ marginTop: 10 }} size="default"
                                                            onChange={() => handleApprove(recruitmentDetail.id)}
                                                            unCheckedChildren /> : <Switch style={{ marginTop: 10 }} size="default"
                                                                onChange={() => handleApprove(recruitmentDetail.id)}
                                                                defaultChecked />}
                                                    </div> : ""}
                                                </Row>
                                            </div>
                                        </Col>
                                    </Row>
                                </Card>
                            </div>
                        </div>
                    </div>
                )
            })
            }
            <Pagination style={{ textAlign: "center" }} defaultCurrent={1} total={totalRecruitment} onChange={handlePage}></Pagination>
            <BackTop style={{ textAlign: 'right' }} />
        </div>
    )
}

export default Home;