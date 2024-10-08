import axios from "@/config/axios.config.js";

export const fileUploadApi = (file) => {
    let config = {
        headers: {'Content-Type': 'multipart/form-data'},
    };
    let formData = new FormData();
    formData.append('file', file);
    return axios.post('file/upload', formData, config)
}
