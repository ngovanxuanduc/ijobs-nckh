import React, { useEffect, useState } from 'react';
import i18next from 'i18next';
import en from "../../../assets/image/en.png";
import vn from "../../../assets/image/vn.png";
import jp from "../../../assets/image/jp.png";
import styles from "../header/header.module.scss";
import logo from "../../../assets/image/logo-dtu.png";
import DropdownAvatar from "../../../containers/headerDropdown/DropdownAvatar";
import { Layout, Dropdown, Menu, Badge, Row, Col, Popover, Modal, List, Avatar,} from 'antd';
import { TranslationOutlined, BellOutlined, NotificationTwoTone } from '@ant-design/icons';
import { useTranslation } from 'react-i18next';
import { accountApi } from "../../../api/accountApi";

const { Header } = Layout;

function Topbar() {

  const [countNotification, setCountNotification] = useState<any>(0);
  const [notification, setNotification] = useState<any>([]);
  const [visible, setVisible] = useState(false);
  const [visiblePopover, setVisiblePopover] = useState(false);
  const [titleNotification, setTitleNotification] = useState('');
  const [contentNotification, setContentNotification] = useState('');

  const { t } = useTranslation("common");

  const handleChangeLanguage = (language) => {
    i18next.changeLanguage(language);
  }

  const menu = (
    <Menu>
      <Menu.Item key="1" style={{ display: "flex", alignItems: 'center' }} onClick={() => handleChangeLanguage('vn')}>
        <img alt="" style={{ background: "#FFFFFF", float: 'left', width: 35, height: 18, marginRight: 8 }} src={vn} />
        <a target="_blank" rel="noopener noreferrer">
          VIỆT NAM
      </a>
      </Menu.Item>
      <Menu.Item key="2" style={{ display: "flex", alignItems: 'center' }} onClick={() => handleChangeLanguage('en')} >
        <img alt="" style={{ background: "#FFFFFF", float: 'left', width: 35, height: 18, marginRight: 8 }} src={en} />
        <a target="_blank" rel="noopener noreferrer" >
          ENGLISH
      </a>
      </Menu.Item>
      <Menu.Item key="3" style={{ display: "flex", alignItems: 'center' }} onClick={() => handleChangeLanguage('jp')}>
        <img alt="" style={{ background: "#FFFFFF", float: 'left', width: 35, height: 18, marginRight: 8 }} src={jp} />
        <a target="_blank" rel="noopener noreferrer" >
          JAPAN
      </a>
      </Menu.Item>
    </Menu>
  );


  const DropdownMenu = () => {
    return (
      <Dropdown key="more" overlay={menu} placement="bottomCenter" arrow >
        <div
          style={{
            paddingLeft: 10,
            paddingRight: 10,
            cursor: "pointer"
          }}
          className={styles.container}
        >
          <TranslationOutlined />
        </div>
      </Dropdown>
    );
  };

  const handleNotification = (valuesContent, valuesTitile) => {
    setVisible(true);
    setVisiblePopover(visible !== visible)
    setContentNotification(valuesContent);
    setTitleNotification(valuesTitile);
  }

  const handleOk = () => {
    setVisible(false);
  }

  const handleVisibleChange = (visible) => {
    setVisiblePopover(visible);
  };

  const content = (
    <div>
      {notification.map((values, index) => {
        return (
          <div>
            <List.Item style={{ padding: 0, margin: 0 }}>
              <List.Item.Meta
                style={{ width: 250, margin: 0 }}
                avatar={<NotificationTwoTone style={{ fontSize: '20px', color: '#08c' }} />}
                title={<a onClick={() => handleNotification(values.content, values.title)}>{values.title}</a>}
                description={<p className={styles.fixLine} dangerouslySetInnerHTML={{ __html: values.content }}></p>}
              />
            </List.Item>
          </div>
        )
      })}
    </div>
  );

  useEffect(() => {
    const getDataUser = async () => {
      const dataUser = await accountApi.afterLogin();
      setCountNotification(dataUser.data.notificationLogList.length);
      setNotification(dataUser.data.notificationLogList);
    }
    getDataUser();

  }, [])

  return (
    <Header
      className={styles.header}
      style={{ background: "#FFFFF" }}
    >
      <div >
        <Row className={styles.header} style={{ background: "#FFFFFF", position: 'fixed', top: 0, left: 0, display: 'flex', width: '100%', padding: 0, zIndex: 2 }}>
          <Col span={10}>
            <div style={{ position: 'relative', display: 'flex', alignItems: "center", marginLeft: 8 }}>
              <Row
                justify="center"
              >
                <Col style={{ paddingLeft: 20 }}>
                  <a href="https://duytan.edu.vn/">
                    <img alt="" className={styles.image} src={logo} />
                  </a>
                </Col>
              </Row>
            </div>
          </Col>
          <Col span={6} offset={8}>
            <div style={{ position: 'relative', display: 'flex', float: 'right', alignItems: "center", marginRight: 48 }}>
              <Row>
                <span className={styles.container} style={{ marginRight: 15 }} >
                  <Popover placement="bottomRight" title={t("discussion.notification")} content={content} visible={visiblePopover} onVisibleChange={handleVisibleChange} trigger="click">
                    <Badge count={countNotification} >
                      <Avatar style={{ backgroundColor: "#FFFFFF", marginLeft: 5, marginRight: 5, cursor: "pointer" }} icon={<BellOutlined style={{ fontSize: '18px', color: '#000000' }} />} />
                    </Badge>
                  </Popover>
                </span>
              </Row>
              <Row>
                <DropdownAvatar key="avatar" />
              </Row>
              <Row>
                <div className={styles.container}>
                  <DropdownMenu key="more" />
                </div>
              </Row>
            </div>
          </Col>
        </Row>
        <Modal
          title={titleNotification}
          visible={visible}
          onOk={handleOk}
          onCancel={handleOk}
          cancelButtonProps={{ style: { display: 'none' } }}
        >
          <p dangerouslySetInnerHTML={{ __html: contentNotification }} ></p>
        </Modal>
      </div>
    </Header >
  );
}

export default Topbar;