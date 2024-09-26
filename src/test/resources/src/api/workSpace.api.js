import axios from "@/config/axios.config.js";

export const createApi = (iCode, code) => {
    return axios.post('workspace/create', {
        invitationCode: iCode,
        spaceCode: code
    })
}

export const delApi = () => {
    return axios.delete('workspace/delete')
}

export const getApi = () => {
    return axios.get('workspace/get')
}

export const intoWorkSpaceCheckApi = (link,code) => {
    return axios.post('workspace/into',{link, code})
}

export const isLoginApi = () => {
    return axios.post('workspace/isLogin')
}

export const isValidInvitationCodeApi = (code) => {
    return axios.post('code/invitation', {code})
}
