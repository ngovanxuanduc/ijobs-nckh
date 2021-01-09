import React, { useState, useEffect } from "react";
import styles from "../curriculumVitae/curriculumVitae.module.scss";
import cvApi from "../../api/cvApi";
import { useHistory } from "react-router-dom";
import { useTranslation } from 'react-i18next';
import { Card, Table, Space, Tag, message } from 'antd';

function CurriculumVitae() {

    const [cv, setCV] = useState<any>([]);
    const history = useHistory();

    const { t } = useTranslation("common");

    const columns = [
        {
            title: t("curriculumVitae.lastName"),
            dataIndex: 'firstName',
            key: 'firstName',
        },
        {
            title: t("curriculumVitae.firstName"),
            dataIndex: 'lastName',
            key: 'lastName',
        },
        {
            title: t("curriculumVitae.StudentCode"),
            dataIndex: 'studentCode',
            key: 'studentCode',
        },
        {
            title: t("curriculumVitae.yearStudent"),
            dataIndex: 'schoolYear',
            key: 'schoolYear',
        },
        {
            title: t("curriculumVitae.status"),
            dataIndex: 'active',
            key: 'active',
            render: (text, record) => (
                <>
                    {
                        <Tag color={record.active === true ? 'geekblue' : 'green'} >
                            {record.active === true ? t("approval") : t("unApproval")}
                        </Tag>
                    }
                </>
            ),
        },
        {
            title: '',
            key: 'accountId',
            render: (text, record) => (
                <Space size="middle">
                    <a  onClick={() => handleViewCV(record.accountId)}>{t("view")} </a>
                    <a onClick={() => handleApproveCV(record.accountId)}>{t("approval")} </a>
                    <a onClick={() => handleUnApproveCV(record.accountId)}>{t("reject")}</a>
                </Space>
            ),
        },
    ];

    const handleViewCV = (id: string) => {
        history.push("/curriculumVitae-details/" + id)
    }

    const handleApproveCV = async (id: string) => {
        try {
            await cvApi.approveCV(id).then(response => {
                if (response === undefined) {
                    message.warning(t("curriculumVitae.approveFailure"));
                }
                else {
                    message.warning(t("curriculumVitae.approveSuccess"));
                    fetchCurriculumVitae();
                }
            })
        } catch (error) {
            throw error;
        }
    }

    const handleUnApproveCV = async (id: string) => {
        try {
            await cvApi.UnapprovedCV(id).then(response => {
                if (response === undefined) {
                    message.warning(t("curriculumVitae.unApproveFailure"));
                }
                else {
                    message.warning(t("curriculumVitae.unApproveSuccess"));
                    fetchCurriculumVitae();
                }
            })
        } catch (error) {
            throw error;
        }
    }

    //Lấy toàn bộ dữ liệu dơn xin việc của sinh viên
    const fetchCurriculumVitae = async () => {
        try {
            const response = await cvApi.getAllCV();
            setCV(response.data);
            console.log(response.data);
        } catch (error) {
            throw error;
        }
    }

    useEffect(() => {
        fetchCurriculumVitae();
    }, [])

    return (
        <div id={styles.container}>
            <Card title={t("curriculumVitae.curriculumVitaeManagement")} bordered={false} >
                <Table columns={columns} dataSource={cv} />
            </Card>
        </div>
    )
}

export default CurriculumVitae;