import axiosClient from "./axiosClient";

const suggestApi = {
    getSuggestRecruitment: (id) => {
        const url = '/cv/getSuggestCVList/'+id;
        return axiosClient.get(url);
    }
}

export default suggestApi;