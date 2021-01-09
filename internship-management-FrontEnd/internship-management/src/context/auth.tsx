import React from 'react';

type AuthProps = {
    isAuthenticated: boolean;
    signout: Function;
};

export const AuthContext = React.createContext({} as AuthProps);

const isValidToken = () => {
    const token = localStorage.getItem('token');
    // JWT decode & check token validity & expiration.
    if (token) return true;
    return false;
};

const AuthProvider = (props: any) => {
    const [isAuthenticated, makeAuthenticated] = React.useState(isValidToken());
   
    function signout(cb) {
        makeAuthenticated(false);
        localStorage.removeItem('token');
    }
    return (
        <AuthContext.Provider
            value={{
                isAuthenticated,
                signout,
            }}
        >
            <>{props.children}</>
        </AuthContext.Provider>
    );
};

export default AuthProvider;
