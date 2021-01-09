import React, { useEffect, useState } from 'react';
import styles from "../suggest/suggest.module.scss"
import recruitmentApi from "../../api/recruitmentApi";
import suggestApi from "../../api/suggestApi";
import { useTranslation } from 'react-i18next';
import { EyeOutlined } from '@ant-design/icons';
import { UserOutlined } from '@ant-design/icons';
import { Row, Col, Typography, Avatar, Button, PageHeader, Select, message, Spin } from 'antd';

const { Title } = Typography;
const { Option } = Select;

function Suggest() {

  const [student, setStudent] = useState<any>([]);
  const [recruitment, setRecruitment] = useState<any>([]);
  const [loading, setLoading] = useState(false);

  const { t } = useTranslation("common");

  const onChange = async (value) => {
    setLoading(true);
    console.log(`selected ${value}`);
    await suggestApi.getSuggestRecruitment(value)
      .then(response => {
        if (response === undefined) {
          message.warning(t("suggest.warning"));
        }
        else {
          setLoading(false);
          console.log(response.data);
          setStudent(response.data);
        }
      })
  }

  useEffect(() => {
    const getRecruitment = async () => {
      const response = await recruitmentApi.getAll();
      setRecruitment(response.data.content);
    }

    getRecruitment();
  }, [])

  return (
    <div>
      <div id={styles.wrapper}>
        <div id={styles.dialog}>
          <PageHeader
            title=""
            subTitle={t("suggest.recruitment")}
          >
            <Select
              showSearch
              style={{ width: 400 }}
              placeholder={t("suggest.selectRecruitment")}
              optionFilterProp="children"
              onChange={onChange}
              filterOption={(input, option) =>
                option?.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
              }
            >
              {recruitment.map((values, index) => {
                return (
                  <Option value={values.id}>{values.title}</Option>
                )
              })}
            </Select>
          </PageHeader>
        </div>
      </div>
      <Spin spinning={loading}>
        {student.map((account, index) => (
          <div key={index} id={styles.wrapper}>
            <div id={styles.dialog} style={{ paddingTop: 20, paddingRight: 35, paddingLeft: 35, paddingBottom: 20 }}>
              <Row gutter={{ xs: 8, sm: 16, md: 24, lg: 32 }}>
                <Col span={7}>
                  <Row >
                    <Col style={{ paddingRight: 10 }}>
                      {account?.imageEntity?.length > 0 ? <Avatar src={account?.imageEntity} /> : <Avatar size={56} icon={<UserOutlined />} />}
                    </Col>
                    <Col>
                      <Row style={{ padding: 0, marginBottom: 0 }}>
                        <Title level={5} style={{ padding: 0, marginBottom: 10, marginTop: 0 }}>{account?.fullName}</Title>
                      </Row>
                      <Row style={{ padding: 0 }}>
                        <p>{account?.email}</p>
                      </Row>
                    </Col>
                  </Row>
                </Col>
                <Col span={7}>
                  <Row style={{ padding: 0 }}>
                    <p><i>{t("suggest.yearStudent")}:</i> {account?.schoolYear}</p>
                  </Row>
                  {/* <Row style={{ padding: 0 }}>
                  <p><i>MSSV:</i> {account?.studentCode}</p>
                </Row>
                <Row style={{ padding: 0 }}>
                  <p><i>Khoa:</i> {account?.department}</p>
                </Row>
                <Row style={{ padding: 0 }}>
                  <p><i>Số điện thoại:</i> {account?.phoneNumber}</p>
                </Row>
                <Row style={{ padding: 0 }}>
                  <p><i>Năm Sinh:</i> {account?.birthDate}</p>
                </Row>
                <Row style={{ padding: 0 }}>
                  {account?.gender === true ? <p><i>Giới tính:</i> Nam</p> : <p><i>Giới tính:</i> Nữ</p>}
                </Row> */}
                </Col>
                <Col span={6}>

                </Col>
                <Col style={{ textAlign: "center" }} span={4}>
                  <div style={{ borderLeft: '1px solid #D3D3D3', height: '100%', float: "left", marginRight: 20 }}></div>
                  <div className={styles.wrapButton}>
                    <Row justify="center" >
                      <Button shape="round" style={{ marginTop: 8, width: 100, backgroundColor: '#d4380d', color: "#FFFFFF" }} icon={<EyeOutlined />}>
                        {t("view")}
                  </Button>
                    </Row>
                  </div>
                </Col>
              </Row>
            </div>
          </div>
        ))
        }
      </Spin>
    </div >
  )
}

export default Suggest;



