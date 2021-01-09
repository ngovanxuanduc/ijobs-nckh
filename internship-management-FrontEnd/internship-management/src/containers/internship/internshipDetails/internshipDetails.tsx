import React, { useState, useEffect } from "react";
import styles from "../internshipDetails/internshipDetails.module.scss";
import statisticalApi from "../../../api/statisticalApi";
import { Card, Table, Tag } from 'antd';
import { useParams } from "react-router-dom";
import { useTranslation } from "react-i18next";

function StatisticalDetails() {

    let { id }: any = useParams();
    const [internship, setInternship] = useState<any>([]);

    const { t } = useTranslation("common");

    const columns = [
        {
            title: t("internship.companyName"),
            dataIndex: 'companyName',
            key: 'companyName',
        },
        {
            title: t("internship.name"),
            dataIndex: 'fullName',
            key: 'fullName',
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
            title: t("internship.birthDay"),
            dataIndex: 'birthDay',
            key: 'birthDay',
        },
        {
            title: t("internship.address"),
            dataIndex: 'address',
            key: 'address',
        },
        {
            title: t("internship.position"),
            dataIndex: 'position',
            key: 'position',
            render: (text, record) => (
                <>
                    {
                        <Tag color={record.active === true ? 'geekblue' : 'green'} >
                            {text.toLocaleUpperCase()}
                        </Tag>
                    }
                </>
            ),
        },
    ];

    //Lấy toàn bộ dữ liệu thực tập sinh
    const fetchInternship = async () => {
        try {
            const response = await statisticalApi.getStatisticalById(id);
            setInternship(response.data);
            console.log(response.data);
        } catch (error) {
            throw error;
        }
    }

    useEffect(() => {

        fetchInternship();
    }, [])

    return (
        <div id={styles.container}>
            <Card title={t("internship.managingTrainees")} bordered={false} >
                <Table columns={columns} dataSource={internship} />
            </Card>
        </div>
    )
}

export default StatisticalDetails;