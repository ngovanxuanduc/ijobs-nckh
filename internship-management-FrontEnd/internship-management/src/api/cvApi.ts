import axiosClient from "./axiosClient";

const cvApi = {
    getAllCV: () => {
        const url = "/cv/getStudentList";
        return axiosClient.get(url);
    },
    approveCV: (id: string) => {
        const url = "/cv/approveCV/" + id;
        return axiosClient.post(url);
    },
    UnapprovedCV: (id: string) => {
        const url = "/cv/cancelCV/" + id;
        return axiosClient.post(url);
    }
}

export default cvApi;