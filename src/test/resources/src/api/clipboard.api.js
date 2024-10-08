import axios from "axios";

export const clipboardAddApi = (content) => {
    return axios.post('clipboard/add', {content})
}
