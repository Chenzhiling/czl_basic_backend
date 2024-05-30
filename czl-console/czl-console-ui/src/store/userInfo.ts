import {login} from '../api/login'
import {defineStore} from "pinia";
import {encrypt} from "../utils/encrypt";
import {ref} from 'vue'
import Cookies from 'js-cookie'
import router from "../router/index";
import {resultProps} from "element-plus";

//存放用户全局信息
export const useUserStore = defineStore('user', () => {

    //定义token
    const vToken = ref(Cookies.get('vToken'))

    type result = { data: { tokenHead: string; token: string }; result: String; message: String }
    type User = { username: string; password: string }
    //登录请求
    const loginRequest = (user: User) => {
        return new Promise<result>((resolve, reject) => {
            const users = {
                password: encrypt(user.password),
                username: user.username,
            }
            login<result>(users)
                .then((res: any) => {
                    vToken.value = res.data.token
                    Cookies.set('vToken', res.data.token)
                    router.push({ path: '/' })
                    resolve(res.data)
                })
                .catch((err: { data: string }) => {
                    reject(err.data)
                })
        })
    }
    return {
        vToken,
        loginRequest
    }
})