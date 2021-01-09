import React, { useState, useEffect, useMemo } from "react";
import styles from "../recruitment/recruitment.module.scss";
import recruitmentApi from "../../api/recruitmentApi";
import { DateTime } from "../../utils/dateTime";
import { Col, Row, Typography, Tag, Spin, Pagination, Input, Affix, Menu, Dropdown, Skeleton, notification } from "antd";
import { useTranslation } from "react-i18next";
import { DownOutlined } from "@ant-design/icons";
import { UserOutlined, HomeOutlined, FieldTimeOutlined } from '@ant-design/icons';
import location from "../../assets/icon/position.svg";
import { useParams, useHistory } from 'react-router-dom';
import axiosClient from "../../api/axiosClient";

const { Title } = Typography;

const Recruitment: React.FC = () => {

    const history = useHistory();
    let { id }: any = useParams();

    const [recruitment, setRecruitment] = useState<any>([]);
    const [loading, setLoading] = useState(true);
    const [totalRecruitment, setTotalRecruitment] = useState(Number);
    const imageRandom = [
        "https://images.vietnamworks.com/logo/500x600_114029.jpg",
        "https://images.vietnamworks.com/logo/500x600_113854.png",
        "https://images.vietnamworks.com/logo/500x600_114129.jpg",
        "https://images.vietnamworks.com/logo/500x600_113990.jpg",
        "https://images.vietnamworks.com/logo/500x600 (updated 2)_113957.png",
        "https://images.vietnamworks.com/logo/500x600_113868.jpg",
        "https://images.vietnamworks.com/logo/500x600_113996.png",
        "https://images.vietnamworks.com/logo/500x600_113984.png",
        "https://images.vietnamworks.com/logo/500x600_113994.jpg",
        "https://images.vietnamworks.com/logo/500x600_113990.jpg",
        "https://images.vietnamworks.com/logo/500x600_113992.jpg",
        "https://images.vietnamworks.com/logo/500x600_114010.png",
        "https://images.vietnamworks.com/logo/500x600_114012.jpg",
        "https://images.vietnamworks.com/logo/500x600_114017.png",
        "https://images.vietnamworks.com/logo/500x600_114100.png",
        "https://images.vietnamworks.com/logo/500x600_114088.jpg",
        "https://images.vietnamworks.com/logo/500x600_114146.jpg"
    ];

    var item = imageRandom[Math.floor(Math.random() * imageRandom.length)];
    var item2 = imageRandom[Math.floor(Math.random() * imageRandom.length)];
    var item3 = imageRandom[Math.floor(Math.random() * imageRandom.length)];

    const checkAuth = () => {
        if (localStorage.getItem('student') !== null) {
            return true;
        } else {
            return false;
        }
    }

    //phân trang tin tuyển
    const handlePagePublic = async (pageNumber: any) => {
        window.scrollTo(0, 0);
        try {
            const params = {
                page: pageNumber,
                pageSize: 8
            }
            const response = await recruitmentApi.getPage(params)
            setRecruitment(response.data.content)
            setTotalRecruitment(response.data.totalElements);
            console.log(response.data.content)
        } catch (error) {
            throw (error);
        }
    }

    //phân trang tin tuyển
    const handlePagePrivate = async (pageNumber: any) => {
        window.scrollTo(0, 0);
        try {
            const params = {
                page: pageNumber,
                pageSize: 8
            }
            const response = await recruitmentApi.getPagePrivate(params);
            setRecruitment(response.data.content)
            setTotalRecruitment(response.data.totalElements);
            console.log(response.data.content)
        } catch (error) {
            throw (error);
        }
    }

    const handleDetailRecruitment = (id: string) => {
        history.push("/details-recruitment/" + id)
    }

    const bookmark = async (id: string, number: any) => {
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
            { checkAuth() ? handlePagePrivate(1) : handlePagePublic(1) }

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
            { checkAuth() ? handlePagePrivate(1) : handlePagePublic(1) }
        }

    }


    useEffect(() => {
        { checkAuth() ? handlePagePrivate(1) : handlePagePublic(1) }
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
        <div id={styles.home}>
            <Affix>
                <div id={styles.wrapper}>
                    <div id={styles.dialog} >
                        <Input.Group compact >
                            <div style={{ marginBottom: 20, marginTop: 20, width: '100%' }}>
                                <Input size="large" placeholder="Tìm kiếm việc làm, công ty, kỹ năng" style={{ width: '70%' }}></Input>
                                <Input.Search size="large" style={{ width: '30%' }} placeholder="Tất cả kỹ năng" />
                            </div>
                        </Input.Group>
                    </div>
                </div>
            </Affix>
            <div id={styles.filter} >
                <Row justify="start" style={{ paddingBottom: 8, paddingTop: 8, paddingLeft: 14 }}>
                    <Dropdown overlay={menu} trigger={['click']} >
                        <a className="ant-dropdown-link" style={{ color: '#696969', fontSize: 15 }} onClick={e => e.preventDefault()}>
                            Tất cả ngôn ngữ <DownOutlined />
                        </a>
                    </Dropdown>
                    <Dropdown overlay={menu} trigger={['click']} >
                        <a className="ant-dropdown-link" style={{ color: '#696969', fontSize: 15, marginLeft: 14 }} onClick={e => e.preventDefault()}>
                            Tất cả cấp bật <DownOutlined />
                        </a>
                    </Dropdown>
                    <Dropdown overlay={menu} trigger={['click']} >
                        <a className="ant-dropdown-link" style={{ color: '#696969', fontSize: 15, marginLeft: 14 }} onClick={e => e.preventDefault()}>
                            Tất cả mức lương <DownOutlined />
                        </a>
                    </Dropdown>
                </Row>
            </div>

            <div id={styles.row}>

                <Skeleton loading={loading} active title paragraph>
                    <Spin spinning={loading}>
                        <div id={styles.filterCount} >
                            <p style={{ marginBottom: 0, paddingBottom: 5, paddingTop: 5, paddingLeft: 14, fontSize: 15, color: '#696969' }}>{totalRecruitment + " việc làm phù hợp"}</p>
                        </div>
                        {recruitment.map((recruitmentDetail: any, index: any) => {
                            return (
                                <div id={styles.main} key={recruitment.id}>
                                    <div key={index} style={{ padding: 10 }}>
                                        <Row gutter={{ xs: 8, sm: 16, md: 24, lg: 32 }} style={{ width: 810 }}>
                                            <Col span={8} >
                                                {recruitmentDetail?.imageUrl?.length > 0 ? <img alt="" src={recruitmentDetail.imageUrl} style={{ height: 150, width: '100%', textAlign: "center" }}></img> : <img alt="" style={{ height: 150, width: 150 }} src='https://s3-us-west-2.amazonaws.com/s.cdpn.io/169963/photo-1429043794791-eb8f26f44081.jpeg' ></img>}
                                            </Col>
                                            <Col span={11} onClick={() => handleDetailRecruitment(recruitmentDetail.id)}>
                                                <Title style={{ marginTop: 10, marginBottom: 0, color: '#d4380d' }} level={5} >{recruitmentDetail.title.toUpperCase()}<sup> (MỚI)</sup></Title>
                                                <p style={{ marginBottom: 3, fontSize: 14 }}>
                                                    {recruitmentDetail.createdBy?.firstName ? <UserOutlined /> : <HomeOutlined />}
                                                    <u style={{ paddingLeft: 5 }}>
                                                        {recruitmentDetail.shortInfoAccount?.fullName}
                                                    </u>
                                                </p>
                                                <p style={{ margin: 0, fontSize: 14 }}><img alt="" src={location} style={{ width: 18, height: 'auto' }}></img>{recruitmentDetail.locationDescription}</p>
                                                <p style={{ marginBottom: 3, fontSize: 14 }}><FieldTimeOutlined /> {DateTime(recruitmentDetail.createdAt)}</p>
                                                {recruitmentDetail.skills.map((skills: any, index: any) => {
                                                    return (
                                                        <Tag key={index} style={{ marginRight: 5, marginTop: 8, fontSize: 13 }}>{skills.skillName.toUpperCase()}</Tag>
                                                    )
                                                }
                                                )}
                                            </Col>
                                            <Col span={5}>
                                                <Title style={{ marginTop: 10, marginBottom: 0, color: '#FF8C00', float: 'right', marginRight: 20, fontSize: 14 }} level={5}>Thương lượng</Title>
                                                {
                                                    recruitmentDetail.studentBookMarkApply === 2 || recruitmentDetail.studentBookMarkApply === 0 ? <p onClick={() => bookmark(recruitmentDetail.id, recruitmentDetail.studentBookMarkApply)}>
                                                        <svg fill="#000000" style={{ marginTop: 90, marginBottom: 0, color: '#FF8C00', float: 'right', marginRight: 20, fontSize: 14 }} xmlns="http://www.w3.org/2000/svg" viewBox="0 0 50 50" width="20px" height="20px" ><path fill="#ff9100" d="M 25 47.300781 L 24.359375 46.769531 C 23.144531 45.753906 21.5 44.652344 19.59375 43.378906 C 12.167969 38.40625 2 31.601563 2 20 C 2 12.832031 7.832031 7 15 7 C 18.894531 7 22.542969 8.734375 25 11.699219 C 27.457031 8.734375 31.105469 7 35 7 C 42.167969 7 48 12.832031 48 20 C 48 31.601563 37.832031 38.40625 30.40625 43.378906 C 28.5 44.652344 26.855469 45.753906 25.640625 46.769531 Z"></path></svg>
                                                    </p> :
                                                        <p onClick={() => bookmark(recruitmentDetail.id, recruitmentDetail.studentBookMarkApply)}>
                                                            <svg fill="#000000" style={{ marginTop: 90, marginBottom: 0, color: '#FF8C00', float: 'right', marginRight: 20, fontSize: 14 }} xmlns="http://www.w3.org/2000/svg" viewBox="0 0 50 50" width="20px" height="20px" ><path d="M 15 7 C 7.832031 7 2 12.832031 2 20 C 2 34.761719 18.695313 42.046875 24.375 46.78125 L 25 47.3125 L 25.625 46.78125 C 31.304688 42.046875 48 34.761719 48 20 C 48 12.832031 42.167969 7 35 7 C 30.945313 7 27.382813 8.925781 25 11.84375 C 22.617188 8.925781 19.054688 7 15 7 Z M 15 9 C 18.835938 9 22.1875 10.96875 24.15625 13.9375 L 25 15.1875 L 25.84375 13.9375 C 27.8125 10.96875 31.164063 9 35 9 C 41.085938 9 46 13.914063 46 20 C 46 32.898438 31.59375 39.574219 25 44.78125 C 18.40625 39.574219 4 32.898438 4 20 C 4 13.914063 8.914063 9 15 9 Z"></path></svg>
                                                        </p>
                                                }
                                            </Col>
                                        </Row>
                                    </div>
                                </div>
                            )
                        })
                        }
                        <Pagination style={{ textAlign: "center", marginBottom: 30, marginTop: 30 }} defaultCurrent={1} total={totalRecruitment} onChange={handlePagePublic}></Pagination>
                    </Spin>
                    <div style={{ width: 10, height: 160 }}>
                        <img alt=" tuyển dụng - Tìm việc mới nhất, lương thưởng hấp dẫn." src={item} className={styles.imgFluid} style={{ padding: 20, width: 360 }}></img>
                        <img alt=" tuyển dụng - Tìm việc mới nhất, lương thưởng hấp dẫn." src={item2} className={styles.imgFluid} style={{ padding: 20, width: 360 }}></img>
                        <img alt=" tuyển dụng - Tìm việc mới nhất, lương thưởng hấp dẫn." src={item3} className={styles.imgFluid} style={{ padding: 20, width: 360 }}></img>

                    </div>
                </Skeleton>
            </div>
        </div>
    );
};

export default Recruitment;
