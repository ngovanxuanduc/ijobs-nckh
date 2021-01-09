import React, { useState, useEffect } from "react";
import styles from "../internship/internship.module.scss";
import statisticalApi from "../../api/statisticalApi";
import { useHistory } from 'react-router-dom';
import { Card, Table, Space } from 'antd';
import { useTranslation } from 'react-i18next';

function Internship() {

    const history = useHistory();
    const [internship, setInternship] = useState<any>([]);

    const { t } = useTranslation("common");

    const columns = [
        {
            title: t("internship.companyName"),
            dataIndex: 'companyName',
            key: 'companyName',
        },
        {
            title: t("internship.totalStudents"),
            dataIndex: 'numOfStudent',
            key: 'numOfStudent',
        },
        {
            title: t("internship.email"),
            dataIndex: 'email',
            key: 'email',
        },
        {
            title: t("internship.phoneNumber"),
            dataIndex: 'phoneNumber',
            key: 'phoneNumber',
        },
        {
            title: t("internship.address"),
            dataIndex: 'location',
            key: 'location',
        },
        {
            title: '',
            key: 'companyId',
            render: (text, record) => (
                <Space size="middle">
                    <a href="#" onClick={() => handleViewIntership(record.companyId)}>{t("internship.view")}</a>
                </Space>
            ),
        },
    ];

    //Lấy toàn bộ dữ liệu thực tập sinh
    const fetchAllIntership = async () => {
        try {
            const response = await statisticalApi.getStatistical();
            setInternship(response.data.details);
        } catch (error) {
            throw error;
        }
    }

    const handleViewIntership = async (id: string) => {
        history.push("/details-internship/" + id);
    }

    useEffect(() => {
        fetchAllIntership();
    }, [])

    return (
        <div id={styles.container}>
            <Card title={t("internship.managingTrainees")} bordered={false} >
                <Table columns={columns} dataSource={internship} />
            </Card>
        </div>
    )
}

export default Internship;