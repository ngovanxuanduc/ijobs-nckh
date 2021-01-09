import axiosClient from "./axiosClient";

const DiscussionApi = {
    getAllDiscussion: () => {
        const url = "/discuss/all";
        return axiosClient.get(url);
    },
    getDetailsDiscussion: (id: string) => {
        const url = "/discuss/" + id;
        return axiosClient.get(url);
    },
    approveCV: (params) => {
        const url = "/cv/approveCV/" + params;
        return axiosClient.get(url);
    },
    UnapprovedCV: (params) => {
        const url = "/cv/cancelCV/" + params;
        return axiosClient.get(url);
    },
    DiscussionReply: (params) => {
        const url = "/discuss/reply"+ params;
        return axiosClient.get(url);
    }
}

export default DiscussionApi;