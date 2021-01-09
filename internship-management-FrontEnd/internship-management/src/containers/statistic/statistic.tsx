import React, { useState, useEffect } from "react";
import statisticalApi from "../../api/statisticalApi";
import styles from "../statistic/statistic.module.scss";
import { useTranslation } from 'react-i18next';
import { accountApi } from "../../api/accountApi";
import { Typography, Row, Col, Card, Statistic, Progress } from 'antd';
import { ComposedChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend } from 'recharts';

const { Title } = Typography;


function StatisticManagement() {

    const [internship, setInternship] = useState<any>([]);
    const [student, setStudent] = useState<any>([]);
    const [company, setCompany] = useState<any>([]);
    const [teacher, setTeacher] = useState<any>([]);

    const { t } = useTranslation("common");

    //get toàn bộ data thực tập sinh
    const fetchStatistical = async () => {
        const params = {
            "page": 1,
            "size": 100000
        }
        try {
            const response = await statisticalApi.getStatistical();
            setInternship(response.data.details);
            const student = await accountApi.getAccountStudent(params);
            setStudent(student.data.totalElements)
            const company = await accountApi.getAccountCompany(params);
            setCompany(company.data.totalElements);
            const teacher = await accountApi.getAccountTeacher(params);
            setTeacher(teacher.data.totalElements);
            console.log(response.data);
        } catch (error) {
            throw error;
        }
    }
    useEffect(() => {
        fetchStatistical();
    }, [])

    return (
        <div>
            <div className={styles.container}>
                <div className={styles.siteCardWrapper}>
                    <Row gutter={16}>
                        <Col span={8}>
                            <Card title={t("statistic.totalCompany")} bordered={false}>
                                <Statistic value={company} />
                                <Progress
                                    strokeColor={{
                                        '0%': '#108ee9',
                                        '100%': '#87d068',
                                    }}
                                    showInfo={false}
                                    percent={company/2}
                                />
                            </Card>
                        </Col>
                        <Col span={8}>
                            <Card title={t("statistic.totalStudent")} bordered={false}>
                                <Statistic value={student} />
                                <Progress
                                    strokeColor={{
                                        '0%': '#899fd4',
                                        '100%': '#a389d4',
                                    }}
                                    showInfo={false}
                                    percent={student/2}
                                />
                            </Card>
                        </Col>
                        <Col span={8}>
                            <Card title={t("statistic.totalTeacher")} bordered={false}>
                                <Statistic value={teacher} />
                                <Progress
                                    strokeColor={{
                                        '0%': '#108ee9',
                                        '100%': '#87d068',
                                    }}
                                    showInfo={false}
                                    percent={teacher/2}
                                />
                            </Card>
                        </Col>
                    </Row>
                </div>
            </div>
            <div className={styles.container}>
                <Title level={5} id={styles.dialog}>{t("statistic.studentInternStatistics")}</Title>
                <Row>
                    <Col span={12}>

                        <ComposedChart
                            layout="vertical"
                            width={600}
                            height={400}
                            data={internship}
                            margin={{
                                top: 20, right: 20, bottom: 20, left: 40,
                            }}
                        >
                            <CartesianGrid stroke="#f5f5f5" />
                            <XAxis type="number" />
                            <YAxis dataKey="companyName" type="category" />
                            <Tooltip />
                            <Legend />
                            <Bar dataKey="numOfStudent" barSize={20} fill="#413ea0" />
                        </ComposedChart>
                    </Col>
                    <Col span={12}>

                        <ComposedChart
                            layout="vertical"
                            width={600}
                            height={400}
                            data={internship}
                            margin={{
                                top: 20, right: 20, bottom: 20, left: 40,
                            }}
                        >
                            <CartesianGrid stroke="#f5f5f5" />
                            <XAxis type="number" />
                            <YAxis dataKey="companyName" type="category" />
                            <Tooltip />
                            <Legend />
                            <Bar dataKey="numOfStudent" barSize={20} fill="#413ea0" />
                        </ComposedChart>
                    </Col>
                </Row>
            </div>
        </div>

    )
}

export default StatisticManagement;