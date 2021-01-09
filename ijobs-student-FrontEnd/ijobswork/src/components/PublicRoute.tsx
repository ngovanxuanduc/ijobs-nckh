import React from 'react';
import { Route, Redirect, RouteProps } from 'react-router-dom';


const PublicRoute: React.FC<RouteProps> = ({ children, ...rest }) => {

    const checkAuth = () => {
        if (localStorage.getItem('student') !== null)
            return false;
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