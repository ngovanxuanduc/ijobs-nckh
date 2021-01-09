import React, { useState, useEffect } from 'react';
import { Layout, Menu } from 'antd';
import {
  UserOutlined, SolutionOutlined, ProfileOutlined, DashboardOutlined,
  FileProtectOutlined, HomeOutlined, IdcardOutlined, FundOutlined
} from '@ant-design/icons';

import styles from "../sidebar/sidebar.module.scss";
import logo from "../../../assets/image/logo-dtu.png";
import { useTranslation } from 'react-i18next';
import { useHistory, useLocation } from "react-router-dom";
import { parseJwt } from '../../../utils/common';

const { SubMenu } = Menu;
const { Sider } = Layout;

function Sidebar() {

  const { t } = useTranslation("test");

  const history = useHistory();
  const location = useLocation();

  const [linkArr, setLinkArr] = useState([]);

  const checkAuth = () => {
    let author = parseJwt(localStorage.getItem('token'))
    let _author = author.role;
    if (_author[0].authority === "ROLE_ADMIN") return true;
    return false;
  }

  const menuSidebarAdmin = [
    {
      key: "dashBoard",
      title: "DashBoard",
      link: "/profile",
      icon: <DashboardOutlined />
    },
    {
      key: "home",
      title: t('recruitment.title'),
      link: "/home",
      icon: <SolutionOutlined />
    },
    {
      key: "profile",
      title: "Quản Lý Profile",
      link: "/profile",
      icon: <ProfileOutlined />
    },
    {
      key: "CV",
      title: "Quản Lý CV",
      link: "/cv",
      icon: <FileProtectOutlined />
    },
    {
      key: "university",
      title: "Quản Lý Tin Nhà Trường",
      link: "/cv",
      icon: <HomeOutlined />
    },
    {
      key: "internship",
      title: "Quản Lý SV Thực Tập",
      link: "/cv",
      icon: <IdcardOutlined />
    },
    {
      key: "feedback",
      title: "Quản Lý Feedback",
      link: "/cv",
      icon: <FundOutlined />
    }
  ];

  const menuSubMenuAdmin = [
    {
      key: "account-student",
      title: "Tài Khoản Sinh Viên",
      link: "/account-student",
      icon: <UserOutlined />
    },
    {
      key: "account-company",
      title: "Tài Khoản Công Ty",
      link: "/account-company",
      icon: <UserOutlined />
    },
    {
      key: "account-school",
      title: "Tài Khoản Nhà Trường",
      link: "/account-school",
      icon: <UserOutlined />
    }
  ];

  const menuSubMenuEmployer = [
    {
      key: "home",
      title: t('recruitment.title'),
      link: "/home",
      icon: <SolutionOutlined />
    },
    {
      key: "internship",
      title: "Quản Lý SV Thực Tập",
      link: "/cv",
      icon: <IdcardOutlined />
    },
    {
      key: "feedback",
      title: "Quản Lý Feedback",
      link: "/cv",
      icon: <FundOutlined />
    }
  ];

  const navigate = (link:any, key: any) => {
    history.push(link);
    setLinkArr(key);
  }

  useEffect(() => {
    const token = localStorage.getItem('token');
    localStorage.setItem("roles", parseJwt(token));
  })

  return (
    <Sider
      className={'ant-layout-sider-trigger'}
      width={230}
      style={{
        height: "100vh",
        position: "sticky",
        top: 0,
        left: 0,
        backgroundColor: "#FFFFFF",
        boxShadow: " 0 2px 4px 0 rgba(0,0,0,0.2)"
      }}
    >
      <a href="https://duytan.edu.vn/">
        <img className={styles.image} src={logo} />
      </a>
      <Menu
        mode="inline"
        selectedKeys={location.pathname.split("/")}
        defaultOpenKeys={['account']}
        style={{ height: '100%', borderRight: 0, backgroundColor: "#fafafa" }}
      >

        {checkAuth() ?
          menuSidebarAdmin.map((map) => (
            <Menu.Item
              onClick={() => navigate(map.link, map.key)}
              key={map.key}
              icon={map.icon}
              className={styles.customeClass}
            >
              {map.title}
            </Menu.Item>
          )) : menuSubMenuEmployer.map((map) => (
            <Menu.Item
              onClick={() => navigate(map.link, map.key)}
              key={map.key}
              icon={map.icon}
              className={styles.customeClass}
            >
              {map.title}
            </Menu.Item>
          ))
        }
        {checkAuth() ?
          < SubMenu key="sub1" icon={<UserOutlined />} title="Quản Lý Tài Khoản">
            {menuSubMenuAdmin.map((map) => (
              <Menu.Item
                onClick={() => navigate(map.link, map.key)}
                key={map.key}
                icon={map.icon}
                className={styles.customeClass}
              >
                {map.title}
              </Menu.Item>
            ))}
          </SubMenu> : ""}
      </Menu>
    </Sider >
  );
}

export default Sidebar;