import axios from "@/config/axios.config.js";

export const fileUploadApi = (file) => {
    let config = {
        headers: {'Content-Type': 'multipart/form-data'},
    };
    let formData = new FormData();
    formData.append('file', file);
    return axios.post('file/upload', formData, config)
}

export const deleteFileApi = (file) => {
    return axios.post("file/delete", {
        uuid: file.uuid,
        fileName: file.fileName
    })
}

export const downloadFileApi = (file) => {
     axios.post("file/download",file,{
        responseType: 'blob'
    }).then(res=>{
        // 模拟下载
        let bob = new Blob([res], { type: 'application/octet-stream;charset=utf-8' });
        let downloadElement = document.createElement('a');
        let url = window.URL.createObjectURL(bob);
        downloadElement.href = url;
        downloadElement.download = file.fileName;
        document.body.appendChild(downloadElement);
        downloadElement.click(); //点击下载
        document.body.removeChild(downloadElement);
        window.URL.revokeObjectURL(url);
    })
}
