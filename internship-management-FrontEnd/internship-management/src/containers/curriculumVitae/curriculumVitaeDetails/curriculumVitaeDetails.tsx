import React, { useEffect } from "react";
import styles from "../curriculumVitaeDetails/curriculumVitaeDetails.module.scss";
import cvApi from "../../../api/cvApi";
import { renderToString } from 'react-dom/server';
import { Row, Col, Button } from 'antd';
import jsPDF from "jspdf";

function CurriculumVitaeDetails() {

    //get toàn bộ data dơn xin việc của sinh viên
    const fetchCurriculumVitae = async () => {
        try {
            const response = await cvApi.getAllCV();
            console.log(response.data);
        } catch (error) {
            throw error;
        }
    }

    const Prints = () => {
        return (
            <div id={styles.container}>
                <div id={styles.dialog}>
                    <div style={{ padding: 20 }}>
                        <Row>
                            <Col span={6}>
                                {/* <img style={{ width: 180, height: 180 }} src={avatar} alt="Margus Lillemagi" title="See olen mina" /> */}
                            </Col>
                            <Col span={10}>
                                <div>
                                    <h1 className={styles.text}>Hi<br></br>Varvas<span>&nbsp;&nbsp;19. veebruar 1917</span></h1>
                                </div>
                            </Col>
                            <Col span={8}>
                            </Col>
                        </Row>
                        <Row style={{ marginTop: 10 }}>
                            <Col span={6}>
                                <h1>Profiil</h1>
                            </Col>
                            <Col span={18}>
                                <div>
                                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                                </div>
                            </Col>
                        </Row>
                        <Row style={{ marginTop: 10 }}>
                            <Col span={6}>
                                <h1>Teadmised oskused</h1>
                            </Col>
                            <Col span={18}>
                                <ul >
                                    <li>Oskan hästi</li>
                                    <li><i aria-hidden="true"></i>&nbsp;HTML, CSS, JS</li>
                                    <li><i aria-hidden="true"></i>&nbsp;Bootstrap</li>
                                    <li><i aria-hidden="true"></i>&nbsp;React</li>

                                    {/* <li>Oskan rahuldavalt</li>
                                <li><i aria-hidden="true"></i>&nbsp;PHP, Python</li>
                                <li><i aria-hidden="true"></i>&nbsp;Django</li>
                                <li><i aria-hidden="true"></i>&nbsp;SQL, MySQL</li>

                                <li>Täiendan</li>
                                <li><i aria-hidden="true"></i>&nbsp;Node.js, Go</li>
                                <li><i aria-hidden="true"></i>&nbsp;Docks</li>
                                <li><i aria-hidden="true"></i>&nbsp;Webpack</li> */}

                                </ul>
                            </Col>
                        </Row>
                        <Row style={{ marginTop: 10 }}>
                            <Col span={6}>
                                <h1>Teadmised oskused</h1>
                            </Col>
                            <Col span={18}>
                                <ul >
                                    <li>Oskan hästi</li>
                                    <li><i aria-hidden="true"></i>&nbsp;HTML, CSS, JS</li>
                                    <li><i aria-hidden="true"></i>&nbsp;Bootstrap</li>
                                    <li><i aria-hidden="true"></i>&nbsp;React</li>

                                    {/* <li>Oskan rahuldavalt</li>
                                <li><i aria-hidden="true"></i>&nbsp;PHP, Python</li>
                                <li><i aria-hidden="true"></i>&nbsp;Django</li>
                                <li><i aria-hidden="true"></i>&nbsp;SQL, MySQL</li>

                                <li>Täiendan</li>
                                <li><i aria-hidden="true"></i>&nbsp;Node.js, Go</li>
                                <li><i aria-hidden="true"></i>&nbsp;Docks</li>
                                <li><i aria-hidden="true"></i>&nbsp;Webpack</li> */}

                                </ul>
                            </Col>
                        </Row>
                        <Row style={{ marginTop: 10 }}>
                            <Col span={6}>
                                <h1>Teadmised oskused</h1>
                            </Col>
                            <Col span={18}>
                                <ul >
                                    <li>Oskan hästi</li>
                                    <li><i aria-hidden="true"></i>&nbsp;HTML, CSS, JS</li>
                                    <li><i aria-hidden="true"></i>&nbsp;Bootstrap</li>
                                    <li><i aria-hidden="true"></i>&nbsp;React</li>

                                    {/* <li>Oskan rahuldavalt</li>
                                <li><i aria-hidden="true"></i>&nbsp;PHP, Python</li>
                                <li><i aria-hidden="true"></i>&nbsp;Django</li>
                                <li><i aria-hidden="true"></i>&nbsp;SQL, MySQL</li>

                                <li>Täiendan</li>
                                <li><i aria-hidden="true"></i>&nbsp;Node.js, Go</li>
                                <li><i aria-hidden="true"></i>&nbsp;Docks</li>
                                <li><i aria-hidden="true"></i>&nbsp;Webpack</li> */}

                                </ul>
                            </Col>
                        </Row>
                        <Row style={{ marginTop: 10 }}>
                            <Col span={6}>
                                <h1>Teadmised oskused</h1>
                            </Col>
                            <Col span={18}>
                                <ul >
                                    <li>Oskan hästi</li>
                                    <li><i aria-hidden="true"></i>&nbsp;HTML, CSS, JS</li>
                                    <li><i aria-hidden="true"></i>&nbsp;Bootstrap</li>
                                    <li><i aria-hidden="true"></i>&nbsp;React</li>

                                    {/* <li>Oskan rahuldavalt</li>
                                <li><i aria-hidden="true"></i>&nbsp;PHP, Python</li>
                                <li><i aria-hidden="true"></i>&nbsp;Django</li>
                                <li><i aria-hidden="true"></i>&nbsp;SQL, MySQL</li>

                                <li>Täiendan</li>
                                <li><i aria-hidden="true"></i>&nbsp;Node.js, Go</li>
                                <li><i aria-hidden="true"></i>&nbsp;Docks</li>
                                <li><i aria-hidden="true"></i>&nbsp;Webpack</li> */}
                                </ul>
                            </Col>
                        </Row>

                    </div>
                </div>
            </div >
        )
    }

    const print = () => {
        const string = renderToString(<Prints />);
        console.log(string);
        const doc = new jsPDF();
        doc.fromHTML(string);
        doc.save('pdf')

    }

    useEffect(() => {
        fetchCurriculumVitae();
    }, [])

    return (
        <div id={styles.container}>
            <h1 style={{ marginTop: 50, marginBottom: 0, padding: 15, background: "linear-gradient(-135deg,#1de9b6,#1dc4e9)", textAlign: "right" }}>
                <Button style={{ background: "#FF8000", color: "#FFFFFF" }} onClick={print}>DOWNLOAD</Button>
            </h1>
            <div id={styles.dialog}>
                <div style={{ padding: 20 }}>
                    <Row>
                        <Col span={6}>
                            <img style={{ width: 180, height: 180 }} src="https://visualangle.ee/delivery/temp_image/icon.png" alt="Margus Lillemagi" title="See olen mina" />
                        </Col>
                        <Col span={10}>
                            <div>
                                <h1>Jüri<br></br>Varvas<span>&nbsp;&nbsp;19. veebruar 1917</span></h1>
                            </div>
                        </Col>
                        <Col span={8}>
                        </Col>
                    </Row>
                    <Row style={{ marginTop: 10 }}>
                        <Col span={6}>
                            <h1>Profiil</h1>
                        </Col>
                        <Col span={18}>
                            <div>
                                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                            </div>
                        </Col>
                    </Row>
                    <Row style={{ marginTop: 10 }}>
                        <Col span={6}>
                            <h1>Teadmised oskused</h1>
                        </Col>
                        <Col span={18}>
                            <ul >
                                <li>Oskan hästi</li>
                                <li><i aria-hidden="true"></i>&nbsp;HTML, CSS, JS</li>
                                <li><i aria-hidden="true"></i>&nbsp;Bootstrap</li>
                                <li><i aria-hidden="true"></i>&nbsp;React</li>

                                {/* <li>Oskan rahuldavalt</li>
                                <li><i aria-hidden="true"></i>&nbsp;PHP, Python</li>
                                <li><i aria-hidden="true"></i>&nbsp;Django</li>
                                <li><i aria-hidden="true"></i>&nbsp;SQL, MySQL</li>

                                <li>Täiendan</li>
                                <li><i aria-hidden="true"></i>&nbsp;Node.js, Go</li>
                                <li><i aria-hidden="true"></i>&nbsp;Docks</li>
                                <li><i aria-hidden="true"></i>&nbsp;Webpack</li> */}

                            </ul>
                        </Col>
                    </Row>
                    <Row style={{ marginTop: 10 }}>
                        <Col span={6}>
                            <h1>Teadmised oskused</h1>
                        </Col>
                        <Col span={18}>
                            <ul >
                                <li>Oskan hästi</li>
                                <li><i aria-hidden="true"></i>&nbsp;HTML, CSS, JS</li>
                                <li><i aria-hidden="true"></i>&nbsp;Bootstrap</li>
                                <li><i aria-hidden="true"></i>&nbsp;React</li>

                                {/* <li>Oskan rahuldavalt</li>
                                <li><i aria-hidden="true"></i>&nbsp;PHP, Python</li>
                                <li><i aria-hidden="true"></i>&nbsp;Django</li>
                                <li><i aria-hidden="true"></i>&nbsp;SQL, MySQL</li>

                                <li>Täiendan</li>
                                <li><i aria-hidden="true"></i>&nbsp;Node.js, Go</li>
                                <li><i aria-hidden="true"></i>&nbsp;Docks</li>
                                <li><i aria-hidden="true"></i>&nbsp;Webpack</li> */}

                            </ul>
                        </Col>
                    </Row>
                    <Row style={{ marginTop: 10 }}>
                        <Col span={6}>
                            <h1>Teadmised oskused</h1>
                        </Col>
                        <Col span={18}>
                            <ul >
                                <li>Oskan hästi</li>
                                <li><i aria-hidden="true"></i>&nbsp;HTML, CSS, JS</li>
                                <li><i aria-hidden="true"></i>&nbsp;Bootstrap</li>
                                <li><i aria-hidden="true"></i>&nbsp;React</li>

                                {/* <li>Oskan rahuldavalt</li>
                                <li><i aria-hidden="true"></i>&nbsp;PHP, Python</li>
                                <li><i aria-hidden="true"></i>&nbsp;Django</li>
                                <li><i aria-hidden="true"></i>&nbsp;SQL, MySQL</li>

                                <li>Täiendan</li>
                                <li><i aria-hidden="true"></i>&nbsp;Node.js, Go</li>
                                <li><i aria-hidden="true"></i>&nbsp;Docks</li>
                                <li><i aria-hidden="true"></i>&nbsp;Webpack</li> */}

                            </ul>
                        </Col>
                    </Row>
                    <Row style={{ marginTop: 10 }}>
                        <Col span={6}>
                            <h1>Teadmised oskused</h1>
                        </Col>
                        <Col span={18}>
                            <ul >
                                <li>Oskan hästi</li>
                                <li><i aria-hidden="true"></i>&nbsp;HTML, CSS, JS</li>
                                <li><i aria-hidden="true"></i>&nbsp;Bootstrap</li>
                                <li><i aria-hidden="true"></i>&nbsp;React</li>

                                {/* <li>Oskan rahuldavalt</li>
                                <li><i aria-hidden="true"></i>&nbsp;PHP, Python</li>
                                <li><i aria-hidden="true"></i>&nbsp;Django</li>
                                <li><i aria-hidden="true"></i>&nbsp;SQL, MySQL</li>

                                <li>Täiendan</li>
                                <li><i aria-hidden="true"></i>&nbsp;Node.js, Go</li>
                                <li><i aria-hidden="true"></i>&nbsp;Docks</li>
                                <li><i aria-hidden="true"></i>&nbsp;Webpack</li> */}

                            </ul>
                        </Col>
                    </Row>

                </div>
            </div>
        </div >
    )
}

export default CurriculumVitaeDetails;