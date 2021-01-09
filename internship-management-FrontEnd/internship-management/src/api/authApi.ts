import axiosClient from "./axiosClient";
import Cookies from 'universal-cookie';

const authApi  = {
    login (username, password, systemType) {
        const url = '/auth/login';
        return  axiosClient
            .post(url, {
                username,
                password,
                systemType,
            })
            .then  (response => {
                if (response) {
                    const cookies = new Cookies();
                    cookies.set('cookie', response.data , { path: '/' });
                    localStorage.setItem("token", response.data);
                }
                return response;
            });
    }
}

export default authApi;