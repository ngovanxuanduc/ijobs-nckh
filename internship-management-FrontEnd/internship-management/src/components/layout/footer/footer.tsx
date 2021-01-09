import React from 'react';
import { Layout } from 'antd';
import { useTranslation } from 'react-i18next';

const { Footer} = Layout;

function _Footer(){

    const { t } = useTranslation("common");

    return(
        <Footer style={{
            marginLeft: 230,
            textAlign: 'center'}}>
            {t("footer")}
        </Footer>
    );
}

export default _Footer;