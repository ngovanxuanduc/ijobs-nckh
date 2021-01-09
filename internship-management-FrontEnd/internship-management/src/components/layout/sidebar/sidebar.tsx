import React, { useEffect } from 'react';
import { Layout, Menu } from 'antd';
import { UserOutlined, SolutionOutlined, StockOutlined, DashboardOutlined, FileProtectOutlined, NotificationOutlined, IdcardOutlined, FundOutlined } from '@ant-design/icons';
import styles from "../sidebar/sidebar.module.scss";
import { useTranslation } from 'react-i18next';
import { useHistory, useLocation } from "react-router-dom";
import { parseJwt } from '../../../utils/common';

const { SubMenu } = Menu;
const { Sider } = Layout;

function Sidebar() {

  const { t } = useTranslation("common");

  const history = useHistory();
  const location = useLocation();

  const checkAuth = () => {
    let author = parseJwt(localStorage.getItem('token'))
    let _author = author.role;
    if (_author[0].authority === "ROLE_ADMIN" || _author[0].authority === "ROLE_TEACHER") return true;
    return false;
  }

  const menuSidebarAdmin = [
    {
      key: "statistic",
      title: t("sideBar.statistic"),
      link: "/statistic",
      icon: <StockOutlined />
    },
    {
      key: "suggest",
      title: t("sideBar.DashBoard"),
      link: "/suggest",
      icon: <DashboardOutlined />
    },
    {
      key: "home",
      title: t('sideBar.Recruitment'),
      link: "/home",
      icon: <SolutionOutlined />
    },
    // {
    //   key: "list-profile",
    //   title: t("sideBar.profileManagement"),
    //   link: "/list-profile",
    //   icon: <ProfileOutlined />
    // },
    {
      key: "curriculum-vitae",
      title: t("sideBar.curriculumVitae"),
      link: "/curriculum-vitae",
      icon: <FileProtectOutlined />
    },
    {
      key: "internship",
      title: t("sideBar.studentManagementInternships"),
      link: "/internship",
      icon: <IdcardOutlined />
    },
    {
      key: "discussion",
      title: t("sideBar.discussionManagement"),
      link: "/discussion",
      icon: <FundOutlined />
    },
    {
      key: "notification",
      title: t("sideBar.notification"),
      link: "/notification",
      icon: <NotificationOutlined />
    }
  ];

  const menuSubMenuAdmin = [
    {
      key: "account-student",
      title: t("sideBar.studentManagement"),
      link: "/account-student",
      icon: <UserOutlined />
    },
    {
      key: "account-company",
      title: t("sideBar.companyManagement"),
      link: "/account-company",
      icon: <UserOutlined />
    },
    {
      key: "account-school",
      title: t("sideBar.schoolManagement"),
      link: "/account-school",
      icon: <UserOutlined />
    }
  ];

  const menuSubMenuEmployer = [
    {
      key: "suggest",
      title: t("sideBar.DashBoard"),
      link: "/suggest",
      icon: <DashboardOutlined />
    },
    {
      key: "home",
      title:  t('sideBar.Recruitment'),
      link: "/home",
      icon: <SolutionOutlined />
    },
    {
      key: "internship",
      title: t("sideBar.studentManagementInternships"),
      link: "/cv",
      icon: <IdcardOutlined />
    }, {
      key: "discussion",
      title: t("sideBar.discussionManagement"),
      link: "/discussion",
      icon: <FundOutlined />
    }
  ];

const navigate = (link, key) => {
  history.push(link);
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
      position: "fixed",
      top: 60,
      height: '100%',
      left: 0,
      padding: 0,
      zIndex: 1,
      marginTop: 0,
      backgroundColor: "#FFFFFF",
      boxShadow: " 0 2px 4px 0 rgba(0,0,0,0.2)"
    }}
  >

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
        < SubMenu key="sub1" icon={<UserOutlined />} title={t("sideBar.accountManagement")}>
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