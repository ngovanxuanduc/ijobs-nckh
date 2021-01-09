import React from 'react';
import { Route, Redirect } from 'react-router-dom';

const PublicRoute = ({ children, ...rest }) => {

    const checkAuth = () => {
        console.log(localStorage.getItem('token'));
        if (localStorage.getItem('token') !== null) return false;
        return true;
    }

    return (
        <Route
            {...rest}
            render={({ location }) =>
                checkAuth() ? (
                    children
                ) : (
                        <Redirect
                            to={{
                                pathname: "/home",
                                state: { from: location }
                            }}
                        />
                    )
            }
        />
    );
}

export default PublicRoute;