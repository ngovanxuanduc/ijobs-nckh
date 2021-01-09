import React, { useState } from 'react';
import authApi from "../../api/authApi";
import styles from "../login/login.module.scss";
import { Form, Input, Button, Card, Divider, Alert } from 'antd';
import { UserOutlined, LockOutlined } from '@ant-design/icons';
import { useHistory } from "react-router-dom";
import logo from "../../assets/image/logoDTU.svg";

const Login = () => {

  const [isLogin, setLogin] = useState(true);

  let history = useHistory();

  const onFinish = values => {
    authApi.login(values.username, values.password, "5")
      .then(function (response) {
        if (response === undefined) {
          setLogin(false);
        }
        else {
          history.push("/home");
        }
      })
      .catch(error => {
        console.log("email or password error" + error)
      });
  }

  return (
    <div className={styles.imageBackground}>
      <div id={styles.wrapper} >
        <Card id={styles.dialog}>
          <Form
            style={{ width: 400, marginBottom: 8 }}
            name="normal_login"
            className={styles.loginform}
            initialValues={{
              remember: true,
            }}
            onFinish={onFinish}
          >
            <Form.Item style={{ marginBottom: 0 }}>
              <img className={styles.image} alt="" src={logo} />
            </Form.Item>
            <Form.Item style={{ marginBottom: 3 }}>

              <Divider style={{ marginBottom: 5, fontSize: 19 }} orientation="center">International School <br></br>Jobs Seek management</Divider>
            </Form.Item>
            <Form.Item style={{ marginBottom: 16 }}>
              <p className={styles.text}> Đăng nhập để vào hệ thống quản lý</p>
            </Form.Item>
            <>
              {isLogin === false ?
                <Form.Item style={{ marginBottom: 16 }}>
                  <Alert
                    message="Tài khoản hoặc mật khẩu sai"
                    type="error"
                    showIcon
                  />

                </Form.Item>
                : ""}
            </>
            <Form.Item
              style={{ marginBottom: 20 }}
              name="username"
              rules={[
                {
                  required: true,
                  message: 'Please input your Username!',
                },
              ]}
            >
              <Input prefix={<UserOutlined className={styles.siteformitemicon} />} placeholder="Username" />
            </Form.Item >
            <Form.Item
              style={{ marginBottom: 8 }}
              name="password"
              rules={[
                {
                  required: true,
                  message: 'Please input your Password!',
                },
              ]}
            >
              <Input
                prefix={<LockOutlined className={styles.siteformitemicon} />}
                type="password"
                placeholder="Password"
              />
            </Form.Item>
            <Form.Item style={{ marginBottom: 15 }}>
              <a href="/#" className={styles.loginformforgot}>
                Forgot password
            </a>
            </Form.Item>

            <Form.Item style={{ marginBottom: 18 }}>
              <Button className={styles.loginformbutton} type="primary" htmlType="submit"  >
                Log in
          </Button>
            </Form.Item>
          </Form>
        </Card>
      </div>
    </div>
  );
};

export default Login;



