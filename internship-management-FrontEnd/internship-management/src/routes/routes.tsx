import React from "react";
import { Suspense, lazy } from 'react';
import Footer from '../components/layout/footer/footer';
import Header from '../components/layout/header/header';
import {
    BrowserRouter as Router,
    Switch,
    Route,
} from "react-router-dom";
import {
    HOME,
    LOGIN
} from '../settings/constants';
import NotFound from '../components/notFound/notFound';
import Sidebar from '../components/layout/sidebar/sidebar';
import LoadingScreen from '../components/loading/loadingScreen';

import PrivateRoute from '../components/PrivateRoute';
import PublicRoute from '../components/PublicRoute';

import { withRouter } from "react-router";
import { parseJwt } from '../utils/common';

import { Layout } from 'antd';

const { Content } = Layout;

// set timeout rendering component login

const Suggest = lazy(() => {
    return Promise.all([
        import('../containers/suggest/suggest'),
        new Promise(resolve => setTimeout(resolve, 1000))
    ])
        .then(([moduleExports]) => moduleExports);
});

const Login = lazy(() => {
    return Promise.all([
        import('../containers/login/login'),
        new Promise(resolve => setTimeout(resolve, 1000))
    ])
        .then(([moduleExports]) => moduleExports);
});

const Profile = lazy(() => {
    return Promise.all([
        import('../containers/profile/profile'),
        new Promise(resolve => setTimeout(resolve, 1000))
    ])
        .then(([moduleExports]) => moduleExports);
});

const Home = lazy(() => {
    return Promise.all([
        import('../containers/home/home'),
        new Promise(resolve => setTimeout(resolve, 800))
    ])
        .then(([moduleExports]) => moduleExports);
});

const Discussion = lazy(() => {
    return Promise.all([
        import('../containers/discussion/discussion'),
        new Promise(resolve => setTimeout(resolve, 800))
    ])
        .then(([moduleExports]) => moduleExports);
});

const AccountTeacher = lazy(() => {
    return Promise.all([
        import('../containers/managerAccount/accountTeacher/accountTeacher'),
        new Promise(resolve => setTimeout(resolve, 800))
    ])
        .then(([moduleExports]) => moduleExports);
});

const AccountStudent = lazy(() => {
    return Promise.all([
        import('../containers/managerAccount/accountStudent/accountStudent'),
        new Promise(resolve => setTimeout(resolve, 800))
    ])
        .then(([moduleExports]) => moduleExports);
});

const AccountCompany = lazy(() => {
    return Promise.all([
        import('../containers/managerAccount/accountCompany/accountCompany'),
        new Promise(resolve => setTimeout(resolve, 800))
    ])
        .then(([moduleExports]) => moduleExports);
});

const CurriculumVitae = lazy(() => {
    return Promise.all([
        import('../containers/curriculumVitae/curriculumVitae'),
        new Promise(resolve => setTimeout(resolve, 800))
    ])
        .then(([moduleExports]) => moduleExports);
});

const ApplyStudent = lazy(() => {
    return Promise.all([
        import('../containers/managerApplyCV/ApplyStudent'),
        new Promise(resolve => setTimeout(resolve, 800))
    ])
        .then(([moduleExports]) => moduleExports);
});

const DiscussionDetails = lazy(() => {
    return Promise.all([
        import('../containers/discussion/disscussionDetails/discussionDetails'),
        new Promise(resolve => setTimeout(resolve, 800))
    ])
        .then(([moduleExports]) => moduleExports);
});

const DetailsRecruitment = lazy(() => {
    return Promise.all([
        import('../containers/home/detailsRecruitment/detailsRecruitment'),
        new Promise(resolve => setTimeout(resolve, 800))
    ])
        .then(([moduleExports]) => moduleExports);
});

const Notification = lazy(() => {
    return Promise.all([
        import('../containers/notifications/notification'),
        new Promise(resolve => setTimeout(resolve, 800))
    ])
        .then(([moduleExports]) => moduleExports);
});

const CreateRecruitment = lazy(() => {
    return Promise.all([
        import('../containers/home/createRecuritment/createRecruitment'),
        new Promise(resolve => setTimeout(resolve, 800))
    ])
        .then(([moduleExports]) => moduleExports);
});

const EditRecruitment = lazy(() => {
    return Promise.all([
        import('../containers/home/editRecruitment/editRecruitment'),
        new Promise(resolve => setTimeout(resolve, 800))
    ])
        .then(([moduleExports]) => moduleExports);
});

const Internship = lazy(() => {
    return Promise.all([
        import('../containers/internship/internship'),
        new Promise(resolve => setTimeout(resolve, 800))
    ])
        .then(([moduleExports]) => moduleExports);
});

const InternshipDetails = lazy(() => {
    return Promise.all([
        import('../containers/internship/internshipDetails/internshipDetails'),
        new Promise(resolve => setTimeout(resolve, 800))
    ])
        .then(([moduleExports]) => moduleExports);
});

const Statistic = lazy(() => {
    return Promise.all([
        import('../containers/statistic/statistic'),
        new Promise(resolve => setTimeout(resolve, 800))
    ])
        .then(([moduleExports]) => moduleExports);
});

const CurriculumVitaeDetails = lazy(() => {
    return Promise.all([
        import('../containers/curriculumVitae/curriculumVitaeDetails/curriculumVitaeDetails'),
        new Promise(resolve => setTimeout(resolve, 800))
    ])
        .then(([moduleExports]) => moduleExports);
});

const RouterURL = withRouter(({ location }) => {

    const checkAuth = () => {
        if (localStorage.getItem('token') !== null) {
            let author = parseJwt(localStorage.getItem('token'))
            let _author = author.role;
            if (_author[0].authority === "ROLE_ADMIN")
                return true;
            return false;
        } else {
            return false;
        }
    }

    const LoginContainer = () => (
        <div className="container">
            <PublicRoute exact path="/">
                <Suspense fallback={<LoadingScreen />}>
                    <Login />
                </Suspense>
            </PublicRoute>
            <PublicRoute exact path={LOGIN}>
                <Login />
            </PublicRoute>
        </div>
    )

    const DefaultContainer = () => (
        <PrivateRoute>
            <Layout>
                <Header />
                <Layout >
                    <Sidebar />
                    <Layout>
                        <Content style={{ marginLeft: 230, width: 'calc(100% - 230px)' }}>
                            <PrivateRoute exact path={HOME}>
                                <Suspense fallback={<LoadingScreen />}>
                                    <Home />
                                </Suspense>
                            </PrivateRoute>

                            {checkAuth() ? <PrivateRoute path="/account-student">
                                <Suspense fallback={<LoadingScreen />}>
                                    <AccountStudent />
                                </Suspense>
                            </PrivateRoute>
                                : <PrivateRoute exact path="/notfound">
                                    <NotFound />
                                </PrivateRoute>}
                            {checkAuth() ? <PrivateRoute exact path="/account-company">
                                <Suspense fallback={<LoadingScreen />}>
                                    <AccountCompany />
                                </Suspense>
                            </PrivateRoute>
                                : <PrivateRoute path="/notfound">
                                    <NotFound />
                                </PrivateRoute>}
                            {checkAuth() ? <PrivateRoute exact path="/curriculum-vitae">
                                <Suspense fallback={<LoadingScreen />}>
                                    <CurriculumVitae />
                                </Suspense>
                            </PrivateRoute>
                                : <PrivateRoute path="/notfound">
                                    <NotFound />
                                </PrivateRoute>}

                            {checkAuth() ? <PrivateRoute path="/account-school">
                                <Suspense fallback={<LoadingScreen />}>
                                    <AccountTeacher />
                                </Suspense>
                            </PrivateRoute>
                                : <PrivateRoute path="/notfound">
                                    <NotFound />
                                </PrivateRoute>}
                            <PrivateRoute path="/apply-student/:id">
                                <Suspense fallback={<LoadingScreen />}>
                                    <ApplyStudent />
                                </Suspense>
                            </PrivateRoute>
                            <PrivateRoute exact path="/discussion">
                                <Suspense fallback={<LoadingScreen />}>
                                    <Discussion />
                                </Suspense>
                            </PrivateRoute>
                            <PrivateRoute path="/discussion-details/:id">
                                <Suspense fallback={<LoadingScreen />}>
                                    <DiscussionDetails />
                                </Suspense>
                            </PrivateRoute>
                            <PrivateRoute path="/details-recruitment/:id">
                                <Suspense fallback={<LoadingScreen />}>
                                    <DetailsRecruitment />
                                </Suspense>
                            </PrivateRoute>
                            <PrivateRoute path="/curriculumVitae-details/:id">
                                <Suspense fallback={<LoadingScreen />}>
                                    <CurriculumVitaeDetails />
                                </Suspense>
                            </PrivateRoute>
                            <PrivateRoute path="/profile">
                                <Suspense fallback={<LoadingScreen />}>
                                    <Profile />
                                </Suspense>
                            </PrivateRoute>
                            <PrivateRoute path="/notification">
                                <Suspense fallback={<LoadingScreen />}>
                                    <Notification />
                                </Suspense>
                            </PrivateRoute>
                            <PrivateRoute path="/create-recruitment">
                                <Suspense fallback={<LoadingScreen />}>
                                    <CreateRecruitment />
                                </Suspense>
                            </PrivateRoute>
                            <PrivateRoute path="/edit-recruitment/:id">
                                <Suspense fallback={<LoadingScreen />}>
                                    <EditRecruitment />
                                </Suspense>
                            </PrivateRoute>
                            <PrivateRoute path="/suggest">
                                <Suspense fallback={<LoadingScreen />}>
                                    <Suggest />
                                </Suspense>
                            </PrivateRoute>
                            <PrivateRoute path="/internship">
                                <Suspense fallback={<LoadingScreen />}>
                                    <Internship />
                                </Suspense>
                            </PrivateRoute>
                            <PrivateRoute path="/details-internship/:id">
                                <Suspense fallback={<LoadingScreen />}>
                                    <InternshipDetails />
                                </Suspense>
                            </PrivateRoute>
                            <PrivateRoute path="/statistic">
                                <Suspense fallback={<LoadingScreen />}>
                                    <Statistic />
                                </Suspense>
                            </PrivateRoute>
                        </Content>
                        <Footer />
                    </Layout>
                </Layout>
            </Layout>
        </PrivateRoute>
    )

    return (
        <div>
            <Router>
                <Switch>
                    <Route exact path="/">
                        <LoginContainer />
                    </Route>
                    <Route exact path={HOME}>
                        <DefaultContainer />
                    </Route>
                    <Route exact path="/account">
                        <DefaultContainer />
                    </Route>
                    <Route exact path="/account-student">
                        <DefaultContainer />
                    </Route>
                    <Route exact path="/account-company">
                        <DefaultContainer />
                    </Route>
                    <Route exact path="/account-school">
                        <DefaultContainer />
                    </Route>
                    <Route exact path="/curriculum-vitae">
                        <DefaultContainer />
                    </Route>
                    <Route exact path="/discussion">
                        <DefaultContainer />
                    </Route>
                    <Route exact path="/discussion-details/:id">
                        <DefaultContainer />
                    </Route>
                    <Route exact path="/apply-student/:id">
                        <DefaultContainer />
                    </Route>
                    <Route path="/details-recruitment/:id">
                        <DefaultContainer />
                    </Route>
                    <Route path="/profile">
                        <DefaultContainer />
                    </Route>
                    <Route path="/notification">
                        <DefaultContainer />
                    </Route>
                    <Route path="/create-recruitment">
                        <DefaultContainer />
                    </Route>
                    <Route path="/edit-recruitment/:id">
                        <DefaultContainer />
                    </Route>
                    <Route path="/suggest">
                        <DefaultContainer />
                    </Route>
                    <Route path="/internship">
                        <DefaultContainer />
                    </Route>
                    <Route path="/details-internship/:id">
                        <DefaultContainer />
                    </Route>
                    <Route path="/statistic">
                        <DefaultContainer />
                    </Route>
                    <Route path="/curriculumVitae-details/:id">
                        <DefaultContainer />
                    </Route>
                    <Route>
                        <NotFound />
                    </Route>
                </Switch>
            </Router>
        </div>
    )
})

export default RouterURL;
