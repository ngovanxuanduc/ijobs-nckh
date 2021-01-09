import React, { useEffect, useState } from 'react';
import { accountApi } from "../../api/accountApi";
import { Avatar, Dropdown, Row } from 'antd';
import { Menu } from 'antd';
import { UserOutlined, SettingOutlined, LogoutOutlined } from '@ant-design/icons';
import { useHistory } from "react-router-dom";
import { useTranslation } from 'react-i18next';
import styles from '../headerDropdown/DropdownAvater.module.scss';

function DropdownAvatar() {

  let history = useHistory();
  const [userData, setUserData] = useState<any>([])

  const { t } = useTranslation("common");

  const checkAuth = () => {
    let author = localStorage.getItem('student');
    if (author)
      return true;
    return false;
  }

  const Logout = async () => {
    await accountApi.logout();
    localStorage.removeItem("student");
    history.push("/home");
    window.location.reload(false);
  }

  const Login = () => {
    history.push("/login");
  }

  const handleRouter = (link: string) => {
    history.push(link);
  }

  useEffect(() => {
    const getUserCurrent = async () => {
      const dataUser = await accountApi.afterLogin();
      setUserData(dataUser.data.inf);
    }
    if (checkAuth()) {
      getUserCurrent();
    }
  }, [])

  const avatarPrivate = (
    <Menu>
      <Menu.Item icon={<UserOutlined />}  >
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

  const avatarPublic = (
    <Menu>
      <Menu.Item icon={<UserOutlined />} >
        <a target="_blank" rel="noopener noreferrer" onClick={Login}>
          {t("login")}
        </a>
      </Menu.Item>
    </Menu>
  )


  return (

    <Dropdown key="avatar" placement="bottomCenter" overlay={() => checkAuth() ? avatarPrivate : avatarPublic} arrow>
      <Row
        style={{
          paddingLeft: 5, paddingRight: 8, cursor: 'pointer'
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
          <p style={{ padding: 0, margin: 0, textTransform: 'capitalize', color: '#FFFFFF' }} >
            {userData.lastName ? userData?.lastName + " " + userData?.firstName : t("login")}
          </p>
        </div>
      </Row>
    </Dropdown>
  );
};

export default DropdownAvatar;