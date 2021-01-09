import axiosClient from "./axiosClient";

const notificationApi = {
    createNotification: () => {
        const url = '/notification/create';
        return axiosClient.post(url);
    },
    getGroupNotification: () => {
        const url = '/notification/group';
        return axiosClient.get(url);
    }
}

export default notificationApi;