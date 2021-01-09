import axiosClient from "./axiosClient";

const recruitmentApi = {
    getAll: () => {
        const url = 'recruitments/sbp';
        return axiosClient.get(url);
    },
    getPage: (params) => {
        const url = '/recruitments/sbp';
        return axiosClient.get(url, {params});
    },
    getRecruitmentId: (id) => {
        const url = '/recruitments/'+id;
        return axiosClient.get(url);
    },
    deleteRecruitment: (params) => {
        const url = `/recuritments/${params}`;
        console.log(url);
        return axiosClient.delete(url);
    },
    getSkills: () => {
        const url = '/skills';
        return axiosClient.get(url);
    },
    postRecruitment: (data) => {
        const url = '/recruitments/create';
        return axiosClient.post(url, data);
    }
}

export default recruitmentApi;