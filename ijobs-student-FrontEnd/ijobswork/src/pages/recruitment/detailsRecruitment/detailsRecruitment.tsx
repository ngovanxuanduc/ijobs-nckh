import React, { useState, useEffect } from "react";
import styles from "../detailsRecruitment/recruitment.module.scss";
import { DateTime } from "../../../utils/dateTime";
import { Col, Row, Tag, Button, Spin, Menu, notification } from "antd";
import { useTranslation } from "react-i18next";
import axiosClient from "../../../api/axiosClient";
import { useHistory, useParams } from "react-router-dom";

const DetailsRecruitment: React.FC = () => {

    const [recruitment, setRecruitment] = useState<any>([]);
    const [loading, setLoading] = useState(true);
    const [skills, setSkills] = useState<any>([]);

    const { t } = useTranslation("test");
    let { id }: any = useParams();

    const history = useHistory();

    const checkAuth = () => {
        if (localStorage.getItem('student') !== null) {
            return true;
        } else {
            return false;
        }
    }

    //phân trang tin tuyển
    const recruitmentDetailPublic = async () => {
        const url = 'http://ijobs-env.eba-8gqm58cd.ap-southeast-1.elasticbeanstalk.com/api/afterLogin/getRecruitmentIdPublic/' + id;
        console.log(url)
        try {
            const response = await axiosClient.get(url);
            console.log(response);
            setRecruitment(response.data);
            setSkills(response.data.skills);
        } catch (error) {
            throw (error);
        }
    }

    //phân trang tin tuyển
    const recruitmentDetailPrivate = async () => {
        const url = 'http://ijobs-env.eba-8gqm58cd.ap-southeast-1.elasticbeanstalk.com/api/recruitments/' + id;
        console.log(url)
        try {
            const response = await axiosClient.get(url);
            console.log(response);
            setRecruitment(response.data.campaignRecruitment);
            setSkills(response.data.campaignRecruitment.skills);
        } catch (error) {
            throw (error);
        }
    }

    const approveRecruitment = async (number: any) => {
        if (number === 1 || number === 2) {
            const url = 'http://ijobs-env.eba-8gqm58cd.ap-southeast-1.elasticbeanstalk.com/api/student/apply/' + id;
            try {
                await axiosClient.delete(url).then(response => {
                    if (response === undefined) {
                        history.push("/login");
                        // notification["error"]({
                        //     message: `Thông báo`,
                        //     description:
                        //         'Ứng tuyển thất bại',

                        // });
                    }
                    else {
                        notification["success"]({
                            message: `Thông báo`,
                            description:
                                'Hủy Ứng tuyển thành công',

                        });
                    };
                });

            } catch (error) {
                throw (error);
            }
            { checkAuth() ? recruitmentDetailPrivate() : recruitmentDetailPublic() }
        } else {
            const url = 'http://ijobs-env.eba-8gqm58cd.ap-southeast-1.elasticbeanstalk.com/api/student/apply/' + id;
            try {
                await axiosClient.post(url).then(response => {
                    if (response === undefined) {
                        history.push("/login");
                        // notification["error"]({
                        //     message: `Thông báo`,
                        //     description:
                        //         'Ứng tuyển thất bại',

                        // });
                    }
                    else {
                        notification["success"]({
                            message: `Thông báo`,
                            description:
                                'Ứng tuyển thành công',

                        });
                    };
                });

            } catch (error) {
                throw (error);
            }
        }
        { checkAuth() ? recruitmentDetailPrivate() : recruitmentDetailPublic() }
    }

    const bookmark = async (number: any) => {
        if (number === 0 || number === 2) {
            const url = 'http://ijobs-env.eba-8gqm58cd.ap-southeast-1.elasticbeanstalk.com/api/student/bookmark/' + id;
            try {
                await axiosClient.delete(url).then(response => {
                    if (response === undefined) {
                        history.push("/login");
                        // notification["error"]({
                        //     message: `Thông báo`,
                        //     description:
                        //         'Quan tâm thất bại',

                        // });
                    }
                    else {
                        notification["success"]({
                            message: `Thông báo`,
                            description:
                                'Hủy Quan tâm thành công',

                        });
                    };
                });

            } catch (error) {
                throw (error);
            }
            { checkAuth() ? recruitmentDetailPrivate() : recruitmentDetailPublic() }

        } else {
            const url = 'http://ijobs-env.eba-8gqm58cd.ap-southeast-1.elasticbeanstalk.com/api/student/bookmark/' + id;
            try {
                await axiosClient.post(url).then(response => {
                    if (response === undefined) {
                        history.push("/login");
                        // notification["error"]({
                        //     message: `Thông báo`,
                        //     description:
                        //         'Quan tâm thất bại',

                        // });
                    }
                    else {
                        notification["success"]({
                            message: `Thông báo`,
                            description:
                                'Quan tâm thành công',

                        });
                    };
                });

            } catch (error) {
                throw (error);
            }
            { checkAuth() ? recruitmentDetailPrivate() : recruitmentDetailPublic() }
        }

    }

    useEffect(() => {
        { checkAuth() ? recruitmentDetailPrivate() : recruitmentDetailPublic() }
        window.scrollTo(0, 0);
        setTimeout(function () {
            setLoading(false);
        }, 500);
    }, [])

    const menu = (
        <Menu>
            <Menu.Item key="0">
                <a >1st menu item</a>
            </Menu.Item>
            <Menu.Item key="1">
                <a >2nd menu item</a>
            </Menu.Item>
            <Menu.Divider />
            <Menu.Item key="3">3rd menu item</Menu.Item>
        </Menu>
    );

    return (
        <div>
            <div style={{ width: '100%', height: 200, background: "#E42800" }}>
            </div>
            <div id={styles.home}>
                <div id={styles.fixBackground}>
                    <div id={styles.wrapper}>
                        <div id={styles.dialog} >
                            <Row style={{ padding: 30 }}>
                                <Col span={8}>
                                    <img src={recruitment.imageUrl} style={{ width: '100%', height: 150, padding: 0 }}></img>
                                </Col>
                                <Col span={11}>
                                    <p style={{ fontSize: 25, marginLeft: 20, marginBottom: 0 }}>{recruitment.title} </p>
                                    <p style={{ fontSize: 15, marginLeft: 20, marginBottom: 0 }}>{recruitment.locationDescription} </p>
                                    <p style={{ fontSize: 15, marginLeft: 20, marginBottom: 0 }}>Thương lượng </p>
                                    <p style={{ fontSize: 15, marginLeft: 20, marginBottom: 0 }}>{" From: " + DateTime(recruitment.startTime) + " To: " + DateTime(recruitment.endTime)} </p>
                                </Col>
                                <Col span={5} style={{ textAlign: 'right' }}>
                                    {recruitment.studentBookMarkApply === 2 || recruitment.studentBookMarkApply === 1 ? <Button size="large" style={{ width: '100%', background: 'rgb(255, 128, 0)', color: '#FFFFFF' }} onClick={() => approveRecruitment(recruitment.studentBookMarkApply)}>
                                        {recruitment.studentBookMarkApply === 2 || recruitment.studentBookMarkApply === 1 ? "ĐÃ NỘP ĐƠN" : "NỘP ĐƠN"}
                                    </Button> : <Button type="primary" size="large" style={{ width: '100%' }} onClick={() => approveRecruitment(recruitment.studentBookMarkApply)}>
                                            {recruitment.studentBookMarkApply === 2 || recruitment.studentBookMarkApply === 1 ? "ĐÃ NỘP ĐƠN" : "NỘP ĐƠN"}
                                        </Button>}

                                    {recruitment.studentBookMarkApply === 0 || recruitment.studentBookMarkApply === 2 ? <Button style={{ marginTop: 10, width: '100%', background: 'rgb(255, 128, 0)', color: '#FFFFFF' }} size="large" onClick={() => bookmark(recruitment.studentBookMarkApply)}>
                                        {recruitment.studentBookMarkApply === 0 || recruitment.studentBookMarkApply === 2 ? "ĐÃ QUAN TÂM" : "QUAN TÂM"}
                                    </Button> : <Button style={{ marginTop: 10, width: '100%' }} type="primary" size="large" onClick={() => bookmark(recruitment.studentBookMarkApply)}>
                                            {recruitment.studentBookMarkApply === 0 || recruitment.studentBookMarkApply === 2 ? "ĐÃ QUAN TÂM" : "QUAN TÂM"}
                                        </Button>}


                                </Col>
                            </Row>
                        </div>
                    </div>

                    <div id={styles.filterCount} >
                        <div style={{ padding: 20 }}>
                            <p style={{ fontSize: 16, marginBottom: 5, fontWeight: 'bold' }}>Mô tả tuyển dụng: </p>
                            <p style={{ fontSize: 15, marginBottom: 5 }} dangerouslySetInnerHTML={{ __html: recruitment.description }} ></p>
                            <p style={{ fontSize: 16, marginBottom: 5, fontWeight: 'bold' }}>Skills: </p>
                            {skills.map((skills: any, index: any) => {
                                return (
                                    <Tag key={index} style={{ marginLeft: 2, marginTop: 8, fontSize: 13 }}>{skills.skillName.toUpperCase()}</Tag>
                                )
                            }
                            )}
                        </div>
                    </div>
                </div>
                <Spin spinning={loading}>
                </Spin>
            </div>
        </div>
    );
};

export default DetailsRecruitment;
