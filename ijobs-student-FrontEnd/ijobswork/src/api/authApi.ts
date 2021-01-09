import axiosClient from "./axiosClient";

const authApi = {
    login(username: string, password: string, systemType: number) {
        const url = '/auth/login';
        return axiosClient
            .post(url, {
                username,
                password,
                systemType
            })
            .then(response => {
                if (response) {
                    localStorage.setItem("student", response.data);
                }
                return response;
            });
    }
}

export default authApi;