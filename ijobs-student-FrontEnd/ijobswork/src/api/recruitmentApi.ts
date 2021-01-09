import axiosClient from "./axiosClient";

const recruitmentsApi = {
    getAll: () => {
        const url = '/auth/public';
        return axiosClient.get(url);
    },
    getPage: (params: any) => {
        const url = '/auth/public';
        return axiosClient.get(url, {params});
    },
    getPagePrivate: (params: any) => {
        const url = '/recruitments/sbp';
        return axiosClient.get(url, {params});
    },
    deleteRecuritment: (params: any) => {
        const url = `/recuritments/${params}`;
        console.log(url);
        return axiosClient.delete(url);
    },
    getSkills: () => {
        const url = '/skills';
        return axiosClient.get(url);
    },
    postRecuritment: (data: any) => {
        const url = '/recruitments/create';
        return axiosClient.post(url, data);
    }

}

export default recruitmentsApi;