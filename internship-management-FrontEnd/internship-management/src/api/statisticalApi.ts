import axiosClient from "./axiosClient";

const statisticalApi = {
    getStatistical: () => {
        const url = "/statistical/getStatistical";
        return axiosClient.get(url);
    },
    getStatisticalById: (id: string) => {
        const url = "/statistical/getAllInternStudentByCompanyId/"+id;
        return axiosClient.get(url);
    }
}

export default statisticalApi;