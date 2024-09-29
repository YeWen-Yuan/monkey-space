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

export const intoWorkSpaceCheckApi = (link, code) => {
    return axios.post('workspace/into', {link, code})
}

export const isValidInvitationCodeApi = (code) => {
    return axios.post('code/invitation', {code})
}

export const checkLoginKeyApi = (link, key) => {
    return axios.post('workspace/checkLoginKey', {link, key})
}
