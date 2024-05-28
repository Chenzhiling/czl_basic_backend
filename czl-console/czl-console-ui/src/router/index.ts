import {createRouter, createWebHashHistory, RouterOptions} from "vue-router"


const router = createRouter({
    history: createWebHashHistory(),
    routes: [
        {
            path: '/',
            component:()=> import('../views/login/Login.vue')
        },
        {
            path: '/login',
            redirect: '/'
        },
        {
            path: '/index',
            component:()=> import('../views/login/Index.vue')
        }
    ]
} as RouterOptions)

export default router