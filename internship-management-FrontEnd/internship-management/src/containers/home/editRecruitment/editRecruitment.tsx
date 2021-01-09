import React, { useState, useEffect } from "react";
import recruitmentApi from "../../../api/recruitmentApi";
import axiosClient from "../../../api/axiosClient";
import styles from "../createRecuritment/createRecuritment.module.scss";
import { CKEditor } from '@ckeditor/ckeditor5-react';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import { useHistory, useParams } from 'react-router-dom';
import {
    Button, Form, message,
    Input, Select, DatePicker, Upload, Spin, notification
} from 'antd';

import { PlusOutlined, LoadingOutlined } from '@ant-design/icons';

const { Option } = Select;
const { RangePicker } = DatePicker;


const EditRecruitment = () => {

    const [skills, setSkills] = useState<any>([]);
    const [loading, setLoading] = useState(true);
    const [form] = Form.useForm();
    const [imageUrl, setImageUrl] = useState<any>([]);
    const [description ,setDescription] = useState<any>([]);
    const [recruitment, setRecruitment] = useState<any>([]);

    const history = useHistory();
    const { id } = useParams();

    const uploadButton = (
        <div>
            {loading ? <LoadingOutlined /> : <PlusOutlined />}
            <div style={{ marginTop: 8 }}>Upload</div>
        </div>
    );

    const createRecruitment = async (values) => {
        try {
            const formatData = {
                "title": values.title,
                "location": values.location,
                "locationDescription": values.locationDescription,
                "startTime": values.rangePicker[0].format('YYYY-MM-DD'),
                "endTime": values.rangePicker[1].format('YYYY-MM-DD'),
                "campaignName": "Mặc định",
                "image": imageUrl.split(",")[1],
                "description": description,
                "skills": values.skill
            }
            await axiosClient.post("http://ijobs-env.eba-8gqm58cd.ap-southeast-1.elasticbeanstalk.com/api/recruitments/create", formatData)
                .then(response => {
                    if (response === undefined) {
                        notification["error"]({
                            message: `Thông báo`,
                            description:
                                'Tạo tin tuyển dụng thất bại',

                        });
                    }
                    else {
                        notification["success"]({
                            message: `Thông báo`,
                            description:
                                'Tạo tin tuyển dụng thành công',

                        });
                        history.push("/home");
                    }
                }
                );
        } catch (error) {
            throw error;
        }
        setTimeout(function () {
            setLoading(false);
        }, 1000);
    }

    const CancelcreateRecruitment = () =>{
        history.push("/home");
    }

    const beforeUpload = (file) => {
        const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
        if (!isJpgOrPng) {
            message.error('You can only upload JPG/PNG file!');
        }
        const isLt2M = file.size / 1024 / 1024 < 2;
        if (!isLt2M) {
            message.error('Image must smaller than 2MB!');
        }
        return isJpgOrPng && isLt2M;
    }

    const getBase64 = (img, callback) => {
        const reader = new FileReader();
        reader.addEventListener('load', () => callback(reader.result));
        reader.readAsDataURL(img);
    }

    const handleChange = (info) => {
        getBase64(info.file.originFileObj, imageUrl =>
            setImageUrl(imageUrl)
        );
    }

    const normFile = (e) => {
        console.log('Upload event:', e);

        if (Array.isArray(e)) {
            return e;
        }

        return e && e.fileList;
    };

    //get toàn bộ data tin tuyển dụng
    const fetchRecruitment = async () => {
        try {
            const response = await recruitmentApi.getRecruitmentId(id);
            setRecruitment(response.data.campaignRecruitment);
            setSkills(response.data.campaignRecruitment.skills);
            form.setFieldsValue(response.data.campaignRecruitment);
            console.log(response.data.campaignRecruitment);
        } catch (error) {
            throw error;
        }
    }

    useEffect(() => {
        fetchRecruitment();
    }, [])

    return (


        <div id={styles.container}>
            <h1 style={{ borderRadius: 1 ,marginLeft: 80, marginRight: 80, marginTop: 40, marginBottom: 0, padding: 15, color: "#FFFFFF", background: "linear-gradient(-135deg,#1de9b6,#1dc4e9)" }}>Tạo tin tuyển dụng</h1>
            <div id={styles.dialog}
            // title="Tạo chiến dịch"
            // width={480}
            // footer={
            //     <div
            //         style={{
            //             textAlign: "right"
            //         }}
            //     >
            //         <Button  style={{ marginRight: 8 }}>
            //             Cancel
            //               </Button>
            //         <Button onClick={() => form.validateFields()
            //             .then((values) => {
            //                 setLoading(true);
            //                 form.resetFields();
            //                 createRecruitment(values);
            //             })
            //             .catch((info) => {
            //                 console.log('Validate Failed:', info);
            //             })} type="primary">
            //             Submit
            //               </Button>
            //     </div>
            // }
            >
                <Spin spinning={false}>
                    <Form
                        form={form}
                        onFinish={createRecruitment}
                        name="createRecruitment"
                        layout="vertical"
                        initialValues={{
                            residence: ['zhejiang', 'hangzhou', 'xihu'],
                            prefix: '86',
                        }}
                        scrollToFirstError
                    >
                        <Form.Item
                            name="title"
                            label="Tên chiến dịch"
                            rules={[
                                {
                                    required: true,
                                    message: 'Please input your title!',
                                },
                            ]}
                            style={{ marginBottom: 10 }}
                        >
                            <Input placeholder="Tên chiến dịch" />
                        </Form.Item>

                        <Form.Item
                            name="location"
                            label="Địa điểm"
                            rules={[
                                {
                                    required: true,
                                    message: 'Please input your location!',
                                },
                            ]}
                            style={{ marginBottom: 10 }}
                        >
                            <Select placeholder="Địa điểm" style={{ width: '100%' }} >
                                <Option value="true">Tại Duy Tân University</Option>
                                <Option value="false">Tại Công Ty</Option>
                            </Select>
                        </Form.Item>

                        <Form.Item
                            name="locationDescription"
                            label="Mô tả địa điểm"
                            rules={[
                                {
                                    required: true,
                                    message: 'Please input your location description!',
                                },
                            ]}
                            style={{ marginBottom: 10 }}
                        >
                            <Input placeholder="Mô tả địa điểm" />
                        </Form.Item>

                        <Form.Item
                            name="rangePicker"
                            label="Start Time - End Time"
                            rules={[
                                {
                                    required: true,
                                    message: 'Please input your Start Time - End Time!',
                                },
                            ]}
                            style={{ marginBottom: 10 }}
                        >
                            <RangePicker />
                        </Form.Item>

                        <Form.Item
                            name="skill"
                            label="skill"
                            rules={[
                                {
                                    required: true,
                                    message: 'Please input your skill!',
                                },
                            ]}
                            style={{ marginBottom: 10 }}
                        >
                            <Select mode="tags" style={{ width: '100%' }} tokenSeparators={[',']}>
                                {skills.map((item, index) => {
                                    return (
                                        <Option value={item.id} key={index} label="Japan">
                                            {item.skillName.toUpperCase()}
                                        </Option>
                                    )
                                })}
                            </Select>
                        </Form.Item>

                        <Form.Item
                            name="upload"
                            label="Upload"
                            valuePropName="fileList"
                            getValueFromEvent={normFile}
                            rules={[
                                {
                                    required: true,
                                    message: 'Please input your image!',
                                },
                            ]}
                            style={{ marginBottom: 10 }}
                        >
                            <Upload
                                listType="picture-card"
                                name="logo"
                                showUploadList={false}
                                beforeUpload={beforeUpload}
                                onChange={handleChange}
                            >
                                {imageUrl ? <img src={imageUrl} alt="avatar" style={{ width: '100%' }} /> : uploadButton}
                            </Upload>
                        </Form.Item>
                        <Form.Item
                            name="desRecruitment"
                            label="Description Recruitment"
                            rules={[
                                {
                                    required: true,
                                    message: 'Please input your Description Recruitment!',
                                },
                            ]}
                            style={{ marginBottom: 10 }}
                        >
                            {/* <TextArea rows={4} /> */}
                            <CKEditor
                                    editor={ClassicEditor}
                                    data=""
                                    onReady={editor => {
                                        // You can store the "editor" and use when it is needed.
                                        console.log('Editor is ready to use!', editor);
                                    }}
                                    onChange={(event, editor) => {
                                        const data = editor.getData();
                                        setDescription(data);
                                        console.log({ description });
                                    }}
                                />
                        </Form.Item>

                        <Form.Item >
                            <Button style={{ background: "#FF8000", color: '#FFFFFF', float: 'right', marginTop: 20, marginLeft: 8 }} htmlType="submit">
                                Tạo tin
                            </Button>
                            <Button style={{ background: "#FF8000", color: '#FFFFFF', float: 'right', marginTop: 20 }} onClick={CancelcreateRecruitment}>
                                Hủy
                            </Button>
                        </Form.Item>
                    </Form>
                </Spin>
            </div>
        </div>
    )
}

export default EditRecruitment;