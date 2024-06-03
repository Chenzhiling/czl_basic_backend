import {createRouter, createWebHashHistory, RouteRecordRaw, RouterOptions} from "vue-router"
import NProgress from 'nprogress'
import 'nprogress/nprogress.css' // progress bar style

/**
 * 路由实体类
 * @param {string} title 标题
 * @param {string} locale i18n 配置的属性，在language文件夹
 * @param {string} icon 图标
 * @param {string} url 外链
 * @param {string} iframeUrl 内嵌网页
 * @param {string} iframeData 内嵌网页attr
 * @param {boolean} breadcrumb 是否不展示在 “面包屑”组件上展示
 * @param {number} sort 动态添加排序
 */
export type Routers = RouteRecordRaw & {
    meta: {
        title: string
        icon?: string
        id?: number | string
        locale?: string
        breadcrumb?: boolean
        url?: string
        iframeUrl?: string
        iframeData?: any
    }
    hidden?: boolean
    sort?: number
}
export const addRouter: Routers[] =[]

//创建路由
const router = createRouter({
    history: createWebHashHistory(),
    routes: [
        {
            path: '/',
            name:'login',
            meta:{
                title: '登录'
            },
            component:()=> import('../views/login/Login.vue')
        },
        {
            path: '/login',
            redirect: '/'
        },
        {
            path: '/index',
            name:'home',
            meta:{
                title: '首页'
            },
            component:()=> import('../views/login/Index.vue'),
            children: [
                {
                    path: 'user',
                    name: 'user',
                    meta: {
                        title:'用户管理',
                    },
                    component:()=>import('../views/system/User.vue')
                },
                {
                    path: 'role',
                    name: 'role',
                    meta: {
                        title:'角色管理',
                    },
                    component:()=>import('../views/system/Role.vue')
                },
                {
                    path: 'backendLog',
                    name: 'backendLog',
                    meta: {
                        title:'后台日志',
                    },
                    component:()=>import('../views/log/BackendLog.vue')
                },                {
                    path: 'operateLog',
                    name: 'operateLog',
                    meta: {
                        title:'接口日志',
                    },
                    component:()=>import('../views/log/OperateLog.vue')
                }
            ]
        }
    ]
} as RouterOptions)

router.beforeEach((to, from,next) => {
    NProgress.start()
    next()
})

router.afterEach((to,from, next) => {
    if (to.meta && to.meta.title) {
        document.title = to.meta.title
    }
    NProgress.done()
})

export default router
