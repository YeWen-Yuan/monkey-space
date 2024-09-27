import {useRoute, useRouter} from "vue-router";

export function showDialog() {
    let isLogin = localStorage.getItem("hasLogin");
    let key = localStorage.getItem("key");
    return isLogin !== 'true' || !key;
}

export function urlCheck() {
    let route = useRoute();
    let data = route.query.id;
    return !!data;
}

