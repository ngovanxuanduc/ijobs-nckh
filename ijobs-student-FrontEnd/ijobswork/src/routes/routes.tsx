import React from "react"
import Home from '../pages/home/home';
import NotFound from '../components/NotFound';
import Login from '../pages/account/login/login';
import PublicRoute from '../components/PublicRoute';
import PrivateRoute from '../components/PrivateRoute';
import Footer from '../components/layout/footer/footer';
import Header from '../components/layout/header/header';
import Discussion from '../pages/discussion/discussion';
import Recruitment from '../pages/recruitment/recruitment';
import DiscussionDetails from '../pages/discussion/disscussionDetails/discussionDetails';
import DetailsRecruitment from '../pages/recruitment/detailsRecruitment/detailsRecruitment';

import { Layout } from 'antd';
import { withRouter } from "react-router";
import { HOME, LOGIN } from '../settings/constants';
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

import Profile from "../pages/profile/profile";

const RouterURL = withRouter(({ location }) => {

    const checkAuth = () => {
        console.log(localStorage.getItem('student'));
        if (localStorage.getItem('student') === null || undefined)
            return true;
        return false;
    }

    const PrivateContainer = () => (
        <div>
            <Layout style={{ minHeight: '100vh' }}>
                <Layout style={{ display: 'flex' }}>
                    <Header />
                    <PrivateRoute exact path="/discussion">
                        <Discussion />
                    </PrivateRoute>
                    <PrivateRoute path="/discussion-details/:id">
                        <DiscussionDetails />
                    </PrivateRoute>
                    <PrivateRoute path="/profile">
                        <Profile />
                    </PrivateRoute>
                    <Layout>
                        <Footer />
                    </Layout>
                </Layout>
            </Layout>
        </div>
    )

    const PublicContainer = () => (
        <div>
            <Layout style={{ minHeight: '100vh' }}>
                <Layout style={{ display: 'flex' }}>
                    <Header />
                    <Route exact path="/">
                        <Home />
                    </Route>
                    <Route exact path={HOME}>
                        <Home />
                    </Route>
                    <Route exact path="/recruitment">
                        <Recruitment />
                    </Route>
                    <Route path="/details-recruitment/:id">
                        <DetailsRecruitment />
                    </Route>
                    <Layout>
                        <Footer />
                    </Layout>
                </Layout>
            </Layout>
        </div>
    )

    const LoginContainer = () => (
        <div>
            <Layout style={{ minHeight: '100vh' }}>
                <Layout style={{ display: 'flex' }}>
                    <PublicRoute exact path="/login">
                        <Login />
                    </PublicRoute>
                </Layout>
            </Layout>
        </div>
    )

    return (
        <div>
            <Router>
                <Switch>
                    <Route exact path="/login">
                        <LoginContainer />
                    </Route>
                    <Route exact path="/">
                        <PublicContainer />
                    </Route>
                    <Route exact path={HOME}>
                        <PublicContainer />
                    </Route>
                    <Route exact path="/recruitment">
                        <PublicContainer />
                    </Route>
                    <Route path="/details-recruitment/:id">
                        <PublicContainer />
                    </Route>
                    <Route exact path="/discussion">
                        <PrivateContainer />
                    </Route>
                    <Route exact path="/discussion-details/:id">
                        <PrivateContainer />
                    </Route>
                    <Route exact path="/profile">
                        <PrivateContainer />
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
