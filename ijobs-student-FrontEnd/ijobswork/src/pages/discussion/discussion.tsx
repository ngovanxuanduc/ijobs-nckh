import React, { useState, useEffect } from "react";
import DiscussionApi from "../../api/discussionApi";
import styles from "../discussion/discussion.module.scss";
import { DateTime } from "../../utils/dateTime";
import { Card, Table, Space } from 'antd';
import { useHistory } from 'react-router-dom';
import { useTranslation } from 'react-i18next';

function Discussion() {

    const { t } = useTranslation("common");
    const history = useHistory();
    
    const [cv, setCV] = useState<any>([]);

    const columns = [
        {
            title: t("discussion.name"),
            dataIndex: 'ownerName',
            key: 'ownerName',
        },
        {
            title: t("discussion.dateCreated"),
            dataIndex: 'createdAt',
            key: 'createdAt',
            render: (text, record) => (
                <Space size="middle">
                    <p>{DateTime(text)}</p>
                </Space>
            ),
        },
        {
            title: t("discussion.recruitment"),
            dataIndex: 'recruitmentName',
            key: 'recruitmentName',
        },
        {
            title: '',
            key: 'id',
            render: (text, record) => (
                <Space size="middle">
                    <a  onClick={() => handleViewDiscussion(record.id)}>{t("discussion.view")}</a>
                </Space>
            ),
        },
    ];


    const handleViewDiscussion = async (id: string) => {
        try {
            history.push("/discussion-details/" + id);
        } catch (error) {
            throw error;
        }
    }

    //Lấy toàn bộ dữ liệu discussion
    const fetchAllDiscussion = async () => {
        window.scrollTo(0,0);
        try {
            await DiscussionApi.getAllDiscussion().then(response => {
                setCV(response.data);
                console.log(response.data);
            })
        } catch (error) {
            throw error;
        }
    }

    useEffect(() => {
        fetchAllDiscussion();
    }, [])

    return (
        <div id={styles.container}>
            <Card title={t("discussion.discussionList")} bordered={false} >
                <Table columns={columns} dataSource={cv} />
            </Card>
        </div>
    )
}

export default Discussion;