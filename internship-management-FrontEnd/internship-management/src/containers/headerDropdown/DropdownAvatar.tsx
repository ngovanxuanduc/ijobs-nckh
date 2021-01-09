import React, { useState, useEffect } from 'react';
import styles from '../headerDropdown/DropdownAvatar.module.scss';
import { useHistory } from "react-router-dom";
import { useTranslation } from 'react-i18next';
import { accountApi } from "../../api/accountApi";
import { Avatar, Dropdown, Row, Menu } from 'antd';
import { UserOutlined, SettingOutlined, LogoutOutlined } from '@ant-design/icons';

function DropdownAvatar() {

  let history = useHistory();
  const { t } = useTranslation("common");

  const [userData, setUserData] = useState<any>([])

  const Logout = async () => {
    await accountApi.logout();
    localStorage.removeItem("token");
    history.push("/");
  }

  useEffect(() => {

    if (localStorage.getItem('token') !== null) {
      const getUserCurrent = async () => {
        const dataUser = await accountApi.afterLogin();
        setUserData(dataUser.data.inf);
      }
      getUserCurrent();
    }
  }, [])

  const handleRouter = (link: string) => {
    history.push(link);
  }

  const avatar = (
    <Menu>
      <Menu.Item icon={<UserOutlined />} >
        <a target="_blank" rel="noopener noreferrer" onClick={() => handleRouter("/profile")}>
          {t("information")}
        </a>
      </Menu.Item>
      <Menu.Item icon={<SettingOutlined />} >
        <a target="_blank" rel="noopener noreferrer" >
          {t("setting")}
        </a>
      </Menu.Item>
      <Menu.Item key="3" icon={<LogoutOutlined />} onClick={Logout}  >
        <a target="_blank" rel="noopener noreferrer" >
          {t("logout")}
        </a>
      </Menu.Item>
    </Menu>
  );


  return (
    <Dropdown key="avatar" placement="bottomCenter" overlay={avatar} arrow>
      <Row
        style={{
          paddingLeft: 5, paddingRight: 5, cursor: 'pointer'
        }}
        className={styles.container}
      >
        <div style={{ display: 'flex', alignItems: "center", justifyContent: "center" }}>
          <div style={{ paddingRight: 10 }}>
            <Avatar
              style={{
                outline: 'none',
              }}
              src="https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png"
            />
          </div>
          <p style={{ padding: 0, margin: 0, textTransform: 'capitalize' }} >
            {userData.lastName ? userData?.lastName + " " + userData?.firstName : userData?.name}
          </p>
        </div>
      </Row>
    </Dropdown>
  );
};

export default DropdownAvatar;