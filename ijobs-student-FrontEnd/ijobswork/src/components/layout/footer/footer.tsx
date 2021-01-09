import React from 'react';
import { Layout } from 'antd';
import { Col, Row, Divider } from "antd";
const { Footer } = Layout;

function _Footer() {
    return (

        <Footer style={{ borderTop: '1px solid #778899' }}>
            <Row>
                <Col span={4} style={{ marginLeft: 100 }}>
                    <p>Sơ đồ Trang Thông tin</p>
                    <p>Liên hệ</p>
                </Col>
                <Col span={6}>
                    <p>Hội Đồng Quản Trị & Ban Giám Hiệu</p>
                    <p>Phòng Ban Chức Năng</p>
                    <p>Tham Quan Duy Tân</p>
                </Col>
                <Col span={6}>
                    <p>Trung Tâm</p>
                    <p>Giáo Dục & Đào Tạo</p>
                    <p>Đào Tạo Phụ Cận & Liên Kết</p>
                    <p>Tổ chức đoàn thể</p>
                </Col>
                <Col span={6}>
                    <p>Ứng dụng di động</p>
                    <Row>
                        <Col span={12}>
                            <img src="https://www.upsieutoc.com/images/2020/10/10/android.png" style={{ width: '90%', height: 40 }}></img>
                        </Col>
                        <Col span={12}>
                            <img src="https://www.upsieutoc.com/images/2020/10/10/ios.png" style={{ width: '90%', height: 40, marginLeft: 8 }}></img>
                        </Col>
                    </Row>

                </Col>
            </Row>
            <div style={{ textAlign: 'center' }}>
                <Divider style={{ padding: 0 }} />
                <p>iJobs ©2020 Created by Immortal Team</p>
                <p>Địa chỉ: 254 Nguyễn Văn Linh, Quận Thanh Khê - Tp. Đà Nẵng</p>
                <p>Điện thoại: (+84) 236.3650403 - (+84) 236.3827111</p>
            </div>

        </Footer>
    );
}

export default _Footer;