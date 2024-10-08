import axios from 'axios';

axios.defaults.baseURL = 'http://localhost:8080/';

const requestMap = new Map();
// Add a request interceptor
axios.interceptors.request.use(
    function (config) {
        try {
            requestMap.set(config.url, new Date().getTime());
        } catch (e) {}
        let tn = localStorage.getItem('tokenName');
        config.headers[tn] = localStorage.getItem('tokenValue');
        return config;
    },
    function (error) {
        // Do something with request error
        return Promise.reject(error);
    },
);

// Add a response interceptor
axios.interceptors.response.use(
    function (response) {
        try {
            let number = new Date().getTime() - requestMap.get(response.config.url);
            console.log(response.config.url, ' , run time : ', number, ' ms');
        } catch (e) {}
        return response.data;
    },
    function (error) {
        // Do something with response error
        return Promise.reject(error);
    },
);

export default axios;
