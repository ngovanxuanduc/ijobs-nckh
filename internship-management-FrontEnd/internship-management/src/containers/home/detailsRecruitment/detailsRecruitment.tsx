import React, { useState, useEffect } from "react";
import styles from "../detailsRecruitment/recruitment.module.scss";
import { DateTime } from "../../../utils/dateTime";
import { Col, Row, Tag, Spin } from "antd";
import axiosClient from "../../../api/axiosClient";
import { useParams } from "react-router-dom";

const DetailsRecruitment: React.FC = () => {

    const [recruitment, setRecruitment] = useState<any>([]);
    const [loading, setLoading] = useState(true);
    const [skills, setSkills] = useState<any>([]);

    let { id }: any = useParams();

    useEffect(() => {
        const handlePage = async () => {
            const url = 'http://ijobs-env.eba-8gqm58cd.ap-southeast-1.elasticbeanstalk.com/api/recruitments/' + id;
            console.log(url)
            try {
                const response = await axiosClient.get(url);
                console.log(response);
                setRecruitment(response.data.campaignRecruitment)
                setSkills(response.data.campaignRecruitment.skills)
            } catch (error) {
                throw (error);
            }
        }
        handlePage();
        setTimeout(function () {
            setLoading(false);
        }, 500);
    }, [])

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
                                    <p style={{ fontSize: 15, marginLeft: 20, marginBottom: 0 }}>{recruitment.campaignName} </p>
                                    <p style={{ fontSize: 15, marginLeft: 20, marginBottom: 0 }}>{recruitment.locationDescription} </p>
                                    <p style={{ fontSize: 15, marginLeft: 20, marginBottom: 0 }}>Thương lượng </p>
                                    <p style={{ fontSize: 15, marginLeft: 20, marginBottom: 0 }}>{" From: " + DateTime(recruitment.startTime) + " To: " + DateTime(recruitment.endTime)} </p>
                                </Col>
                                <Col span={5} style={{ textAlign: 'right' }}>

                                </Col>
                            </Row>
                        </div>
                    </div>

                    <div id={styles.filterCount} >
                        <div style={{ padding: 20 }}>
                            <p style={{ fontSize: 16, marginBottom: 5, fontWeight: 'bold' }}>Mô tả tuyển dụng: </p>
                            <p style={{ fontSize: 15, marginBottom: 5 }} dangerouslySetInnerHTML={{ __html: recruitment.description }}></p>
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
