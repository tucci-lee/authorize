import axios from "axios";
import {ElMessage} from "element-plus";
import 'element-plus/dist/index.css';
import {html} from "@/plugins/urls";
import router from "@/router";

axios.defaults.withCredentials = true;
// axios.defaults.baseURL = "http://localhost:7500"; // 开发使用
axios.defaults.baseURL = "https://passport.2cci.cn/api/"; // 线上

/**
 * axios拦截器，错误直接提示
 */
axios.interceptors.response.use(
    resp => {
        if (resp.data.code === 10201) {
            if(router.currentRoute.value.path !== html.signinHtml) {
                router.push(html.signinHtml);
            }
            return resp.data;
        }
        if (!resp.data.status) {
            ElMessage.error(resp.data.message);
        }
        return resp.data;
    },
    err => {
        let resp = err.response;
        if (resp) {
            ElMessage.error(resp.data.message)
        } else {
            ElMessage.error("网络异常")
        }
        return Promise.reject(err);
    });

export default axios;