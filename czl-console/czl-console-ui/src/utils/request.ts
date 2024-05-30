import axios, {CreateAxiosDefaults} from 'axios'
import {useUserStore} from "../store/userInfo";

/**
 * Author: CHEN ZHI LING
 * Date: 2024/05/28
 * Description: 请求模块
 */
const ENV = (import.meta as any).env

/*初始化一个axios对象*/
const service = axios.create({
    baseURL: ENV.VITE_BASE_URL,
    timeout: ENV.VITE_TIMEOUT,
} as CreateAxiosDefaults)

/*设置请求头*/
service.defaults.headers['content-type'] = 'application/json'

/*添加请求拦截器*/
service.interceptors.request.use(
    (config) => {
        const userStore = useUserStore()
        // 在发送请求之前做些什么
        if (userStore.vToken) {
            config.headers['Authorization'] = userStore.vToken
        }

        return config
    },
    function (error) {
        // 对请求错误做些什么
        return Promise.reject(error)
    }
)

export default service