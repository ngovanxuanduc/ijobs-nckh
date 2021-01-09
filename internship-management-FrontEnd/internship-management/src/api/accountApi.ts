import axiosClient from "./axiosClient";

export const accountApi = {
    getAccount: () => {
        const url = "/accounts/";
        return axiosClient.get(url);
    },
    getAccountStudent: (params) => {
        const url = "/accounts/getAllStudentProfile";
        return axiosClient.get(url, {params});
    },
    getAccountStudentById: (id: string) => {
        const url = "/accounts/getStudentProfile/" + id;
        return axiosClient.get(url);
    },
    getAccountTeacherById: (id: string) => {
        const url = "/accounts/getTeacherProfile/" + id;
        return axiosClient.get(url);
    },
    getAccountCompanyById: (id: string) => {
        const url = "/accounts/getCompanyProfile/" + id;
        return axiosClient.get(url);
    },
    getAccountCompany: (params) => {
        const url = "/accounts/getAllCompanyProfile";
        return axiosClient.get(url, {params});
    },
    getAccountTeacher: (params) => {
        const url = "/accounts/getAllTeacherProfile";
        return axiosClient.get(url, {params});
    },
    getPage: (params) => {
        const url = "/accounts/sbp";
        return axiosClient.get(url, {params})
    },
    createAccount: (params) => {
        const url = "/accounts/create";
        return axiosClient.post(url, params);
    },
    deleteAccount: (params) => {
        const url = "/accounts/deleteAcc/"+params;
        return axiosClient.delete(url)
    },
    deleteAccountTeacher: (params) => {
        const url = `/recuritments/${params}`;
        return axiosClient.delete(url);
    },
    afterLogin: () =>{
        const url = '/afterLogin/';
        return axiosClient.get(url);
    },
    logout: () => {
        const url = "/auth/logoutWeb";
        return axiosClient.get(url);
    },
    changePassword: () => {
        const url ="/auth/changePassword";
        return axiosClient.post(url);
    }
}
