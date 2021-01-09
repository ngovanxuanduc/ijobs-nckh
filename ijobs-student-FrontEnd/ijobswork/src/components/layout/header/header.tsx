/* eslint-disable jsx-a11y/anchor-is-valid */
/* eslint-disable jsx-a11y/alt-text */
import React, { useEffect, useState } from 'react';
import { Layout, Avatar, Dropdown, Button, Menu, Badge, Row, Col, List, Popover, Modal } from 'antd';
import { TranslationOutlined, BellOutlined, NotificationTwoTone } from '@ant-design/icons';

import en from "../../../assets/image/en.png";
import vn from "../../../assets/image/vn.png";
import jp from "../../../assets/image/jp.png";
import logo from "../../../assets/image/logo-dtu.png";
import DropdownAvatar from "../../../pages/headerDropdown/DropdownAvatar";
import i18next from 'i18next';

import styles from "../header/header.module.scss";
import { useHistory, useLocation } from "react-router-dom";
import { accountApi } from "../../../api/accountApi";


const { Header } = Layout;
function Topbar() {

  const history = useHistory();

  const [countNotification, setCountNotification] = useState<any>(0);
  const [notification, setNotification] = useState<any>([]);
  const [visible, setVisible] = useState(false);
  const [visiblePopover, setVisiblePopover] = useState(false);
  const [titleNotification, setTitleNotification] = useState('');
  const [contentNotification, setContentNotification] = useState('');

  const checkAuth = () => {
    let author = localStorage.getItem('student');
    if (author)
      return true;
    return false;
  }

  const handleChangeLanguage = (language: any) => {
    i18next.changeLanguage(language);
    console.log(language);
  }

  const menu = (
    <Menu>
      <Menu.Item key="1" style={{ display: "flex", alignItems: 'center' }} onClick={() => handleChangeLanguage('vn')}>
        <img style={{ background: "#FFFFFF", float: 'left', width: 35, height: 18, marginRight: 8 }} src={vn} />
        <a target="_blank" rel="noopener noreferrer">
          VIỆT NAM
      </a>
      </Menu.Item>
      <Menu.Item key="2" style={{ display: "flex", alignItems: 'center' }} onClick={() => handleChangeLanguage('en')} >
        <img style={{ background: "#FFFFFF", float: 'left', width: 35, height: 18, marginRight: 8 }} src={en} />
        <a target="_blank" rel="noopener noreferrer" >
          ENGLISH
      </a>
      </Menu.Item>
      <Menu.Item key="3" style={{ display: "flex", alignItems: 'center' }} onClick={() => handleChangeLanguage('jp')}>
        <img style={{ background: "#FFFFFF", float: 'left', width: 35, height: 18, marginRight: 8 }} src={jp} />
        <a target="_blank" rel="noopener noreferrer" >
          JAPAN
      </a>
      </Menu.Item>
    </Menu>
  );



  const DropdownMenu = () => {
    return (
      <Dropdown key="more" overlay={menu} placement="bottomCenter" arrow>
        <Button
          style={{
            border: 'none',
            padding: 0,
            background: "#FF0000"
          }}
        >
          <TranslationOutlined style={{ color: "#FFFFFF" }} />
        </Button>
      </Dropdown>
    );
  };

  const handleLink = (link: string) => {
    history.push(link);
  }

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

  const handleNotification = (valuesContent, valuesTitile) => {
    setVisible(true);
    setVisiblePopover(visible !== visible)
    setContentNotification(valuesContent);
    setTitleNotification(valuesTitile);
  }

  const handleVisibleChange = (visible) => {
    setVisiblePopover(visible);
  };

  const handleOk = () => {
    setVisible(false);
  }

  useEffect(() => {
    if (checkAuth()) {
      const getDataUser = async () => {
        const dataUser = await accountApi.afterLogin();
        setCountNotification(dataUser.data.notificationLogList.length);
        setNotification(dataUser.data.notificationLogList);
      }
      getDataUser();
    }

  }, [])

  return (
    <Header
      style={{ background: "#FF0000" }}

    >
      <Row>
        <Col span={10}>
          <div style={{ position: 'relative', display: 'flex', alignItems: "center" }}>
            <Row>
              <Col style={{ marginRight: 50 }}>
                <img style={{ fontSize: 15, width: 100, height: 'auto', cursor: "pointer" }} src={logo} onClick={() => handleLink("/home")}></img>
              </Col>

              <Col style={{ marginRight: 50 }}>
                <p style={{ fontSize: 16, color: "#FFFFFF", cursor: 'pointer' }} className={styles.hoverLink} onClick={() => handleLink("/recruitment")}>Việc làm</p>
              </Col>
              {checkAuth() ? <Col style={{ marginRight: 50 }}>
                <p style={{ fontSize: 16, color: "#FFFFFF" }} className={styles.hoverLink} onClick={() => handleLink("/discussion")}>Phỏng vấn</p>
              </Col> : ""}


              {/* <Col style={{ marginRight: 50 }}>
                <p style={{ fontSize: 16, color: "#FFFFFF" }} className={styles.hoverLink}>Công ty</p>
              </Col> */}
            </Row>
          </div>
        </Col>
        <Col span={6} offset={8}>
          <div style={{ position: 'relative', display: 'flex', float: 'right', alignItems: "center" }}>
            <Row>
              <span className={styles.container} style={{ marginRight: 15 }} >
                <Popover placement="bottomRight" title="Thông Báo" content={content} visible={visiblePopover} onVisibleChange={handleVisibleChange} trigger="click">
                  <Badge count={countNotification} >
                    <Avatar style={{ backgroundColor: "#FFFFFF", marginLeft: 5, marginRight: 5, cursor: "pointer" }} icon={<BellOutlined style={{ fontSize: '18px', color: '#000000' }} />} />
                  </Badge>
                </Popover>
              </span>
            </Row>
            <Row>
              <DropdownAvatar key="avatar" />
            </Row>
            <Row className={styles.container}>
              <DropdownMenu key="more" />
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
        </Col>
      </Row>
    </Header >
  );
}

export default Topbar;