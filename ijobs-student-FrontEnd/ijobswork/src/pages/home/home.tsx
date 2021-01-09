import React, { useState, useEffect } from "react";
import styles from "../home/home.module.scss";
import recruitmentApi from "../../api/recruitmentApi";
import { Col, Row, Typography, Tag, Button, Form, Pagination, Divider, Carousel, Card } from "antd";
import { useTranslation } from "react-i18next";
import location from "../../assets/icon/position.svg";
import { useHistory } from 'react-router-dom';

const { Title } = Typography;

const Home: React.FC = () => {

  const [recruitment, setRecruitment] = useState<any>([]);
  const [loading, setLoading] = useState(true);
  const [totalRecruitment, setTotalRecruitment] = useState(Number);

  const { t } = useTranslation("test");
  const history = useHistory();

  //phân trang tin tuyển
  const handlePage = async (pageNumber: any) => {
    try {
      const pageformat = {
        page: pageNumber,
        pageSize: 8
      }
      const response = await recruitmentApi.getPage(pageformat);
      console.log(response.data.content);
      setRecruitment(response.data.content)
      setTotalRecruitment(response.data.totalElements);

    } catch (error) {
      throw (error);
    }
  }

  const handleDetailRecruitment = (id: string) => {
    history.push("/details-recruitment/" + id)
  }

  useEffect(() => {
    handlePage(1);
    setTimeout(function () {
      setLoading(false);
    }, 500);
  }, [])

  return (
    <div>
      <Carousel autoplay >
        <div>
          <h3 style={{
            width: '100%',
            height: 'auto',
            color: '#fff',
            lineHeight: '160px',
            textAlign: 'center',
            background: '#364d79',
            marginBottom: '0em'
          }}> <img style={{ maxHeight: 550, width: '100%' }} src={"https://images.vietnamworks.com/logo/PremiumBannerNestleresized_109574.jpg"} alt=""/></h3>
        </div>
        <div>
          <h3 style={{
            width: '100%',
            height: 'auto',
            color: '#fff',
            lineHeight: '160px',
            textAlign: 'center',
            background: '#364d79',
            marginBottom: '0em'
          }}> <img style={{ maxHeight: 550, width: '100%' }} src={"https://images.vietnamworks.com/logo/fo_113111.jpg"} /> </h3>
        </div>
        <div>
          <h3 style={{
            height: '400px',
            color: '#fff',
            lineHeight: '160px',
            textAlign: 'center',
            background: '#364d79',
            marginBottom: '0em'
          }}> <img style={{ maxHeight: 550, width: '100%' }} src={"https://images.vietnamworks.com/logo/11_102737.jpg"} alt="" /></h3>
        </div>
        <div>
          <h3 style={{
            height: '400px',
            color: '#fff',
            lineHeight: '160px',
            textAlign: 'center',
            background: '#364d79',
            marginBottom: '0em'
          }}> <img style={{ maxHeight: 550, width: '100%' }} src={"https://images.vietnamworks.com/logo/Coc_104935.jpg"} alt="" /></h3>
        </div>
      </Carousel>
      <div className={styles.wrapper}>
        <div id={styles.dialog} >
          <Row justify="center">
            <p style={{ fontSize: 25, marginTop: 30, fontFamily: 'unset' }}>CÁC CÔNG TY HÀNG ĐẦU</p>
          </Row>
          <Row justify="center"  >
            <Card bordered={false} style={{ borderRadius: 6 }}>
              <Col className={styles.card}  >
                <img style={{ width: '100%', height: '100%', padding: 20 }} src={"https://www.upsieutoc.com/images/2020/10/10/images-170e1a62c02e0eed9.png"} alt=""></img>
                <p style={{ marginTop: 15, fontSize: 15, textAlign: "center", cursor: "pointer", color: "#00B9F2" }}>GOLDEN GATE GROUP</p>
              </Col>
            </Card>

            <Card bordered={false} style={{ borderRadius: 6 }}>
              <Col className={styles.card} >
                <img style={{ width: '100%', height: '100%', padding: 20 }} src={"https://www.upsieutoc.com/images/2020/10/10/logo_hpg_cu_6112017-1.png"} alt=""></img>
                <p style={{ marginTop: 15, fontSize: 15, textAlign: "center", cursor: "pointer", color: "#00B9F2" }}>GOLDEN GATE GROUP</p>
              </Col>
            </Card>
            <Card bordered={false} style={{ borderRadius: 6 }}>
              <Col className={styles.card} >
                <img style={{ width: '100%', height: '100%', padding: 20 }} src={"https://www.upsieutoc.com/images/2020/10/10/logo-vingroup-1.png"} alt=""></img>
                <p style={{ marginTop: 15, fontSize: 15, textAlign: "center", cursor: "pointer", color: "#00B9F2" }}>GOLDEN GATE GROUP</p>
              </Col>
            </Card>
            <Card bordered={false} style={{ borderRadius: 6 }}>
              <Col className={styles.card}>
                <img style={{ width: '100%', height: '100%', padding: 20 }} src={"https://www.upsieutoc.com/images/2020/11/08/FPT_Software_Logo.png"} alt=""></img>
                <p style={{ marginTop: 15, fontSize: 15, textAlign: "center", cursor: "pointer", color: "#00B9F2" }}>GOLDEN GATE GROUP</p>
              </Col>
            </Card>
            <Card bordered={false} style={{ borderRadius: 6 }}>
              <Col className={styles.card}>
                <img style={{ width: 160, height: '100%', padding: 20 }} src={"https://www.upsieutoc.com/images/2020/10/10/logo_hpg_cu_6112017-1.png"} alt=""></img>
                <p style={{ marginTop: 15, fontSize: 15, textAlign: "center", cursor: "pointer", color: "#00B9F2" }}>GOLDEN GATE GROUP</p>
              </Col>
            </Card>
            <Card bordered={false} style={{ borderRadius: 6 }}>
              <Col className={styles.card} >
                <img style={{ width: 160, height: '100%', padding: 20 }} src={"https://www.upsieutoc.com/images/2020/10/10/images-170e1a62c02e0eed9.png"} alt=""></img>
                <p style={{ marginTop: 15, fontSize: 15, textAlign: "center", cursor: "pointer", color: "#00B9F2" }}>GOLDEN GATE GROUP</p>
              </Col>
            </Card>
          </Row>
          <Row justify="center">
            <p style={{ fontSize: 25, marginTop: 80, fontFamily: 'unset' }}>VIỆC LÀM TỐT NHẤT</p>
          </Row>
          <Row justify="space-around" className={styles.wrapPage}>
            {recruitment.map((recruitmentDetail: any, index: any) => {
              return (
                <Col style={{ padding: 0 }}>
                  <div key={index}>
                    <Row style={{ width: 600 }} >
                      <Col span={4} >
                        {recruitmentDetail?.imageUrl?.length > 0 ? <img src={recruitmentDetail.imageUrl} style={{ height: 70, width: '100%' }} alt=""></img> : <img alt="" style={{ height: 'auto', width: '100%' }} src='https://s3-us-west-2.amazonaws.com/s.cdpn.io/169963/photo-1429043794791-eb8f26f44081.jpeg' ></img>}
                      </Col>
                      <Col style={{ paddingLeft: 18 }} span={13}>
                        <p className={styles.title} onClick={() => handleDetailRecruitment(recruitmentDetail.id)} >{recruitmentDetail.title.toUpperCase()}</p>
                        <p style={{ margin: 0, fontSize: 14 }}><img src={location} style={{ width: 18, height: 'auto' }} alt=""></img>{recruitmentDetail.locationDescription}</p>
                      </Col>
                      <Col span={6}>
                        <Tag color="#FF5661" style={{ fontSize: 15, marginTop: 10, float: "right" }}>HOT</Tag>
                      </Col>
                    </Row>
                    <Divider style={{ padding: 0, margin: 15 }} />
                  </div>
                </Col>
              )
            })
            }
          </Row>
          <Pagination style={{ textAlign: "center", marginBottom: 30, marginTop: 30 }} defaultCurrent={1} total={totalRecruitment} onChange={handlePage}></Pagination>
          <Row style={{ margin: 0, padding: 0}} >
            <Col ><img style={{ width: 800, height: 480 }} className={styles.imageFluid} alt=""></img></Col>
            <Col className={styles.imageLeft}>
              <p style={{ fontSize: 30, marginTop: '40%', marginLeft: 20 }}>Get ready to move up in tech career!</p>
              <Button type="primary" shape="round" size={"large"} style={{ marginLeft: 20, width: 140 }}> Explore </Button>
            </Col>
          </Row>
          <Row justify="center">
            <p style={{ fontSize: 25, marginTop: 40, fontFamily: 'unset' }}>VIỆC LÀM GỢI Ý</p>
          </Row>
          <Row justify="space-around" className={styles.wrapPage} style={{paddingBottom: 60}}>
            {recruitment.map((recruitmentDetail: any, index: any) => {
              return (
                <Col style={{ padding: 0 }}>
                  <div key={index}>
                    <Row style={{ width: 600 }} >
                      <Col span={4} >
                        {recruitmentDetail?.imageUrl?.length > 0 ? <img src={recruitmentDetail.imageUrl} style={{ height: 70, width: '100%' }} alt=""></img> : <img alt=""style={{ height: 'auto', width: '100%' }} src='https://s3-us-west-2.amazonaws.com/s.cdpn.io/169963/photo-1429043794791-eb8f26f44081.jpeg' ></img>}
                      </Col>
                      <Col style={{ paddingLeft: 18 }} span={13}>
                        <p className={styles.title} onClick={() => handleDetailRecruitment(recruitmentDetail.id)} >{recruitmentDetail.title.toUpperCase()}</p>
                        <p style={{ margin: 0, fontSize: 14 }}><img src={location} style={{ width: 18, height: 'auto' }}alt=""></img>{recruitmentDetail.locationDescription}</p>
                      </Col>
                      <Col span={6}>
                        <Tag color="#FF5661" style={{ fontSize: 15, marginTop: 10, float: "right" }}>HOT</Tag>
                      </Col>
                    </Row>
                    <Divider style={{ padding: 0, margin: 10 }} />
                  </div>
                </Col>
              )
            })
            }
          </Row>
        </div>
      </div>
    </div>
  );
};

export default Home;
