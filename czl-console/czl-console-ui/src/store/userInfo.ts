import {login, logout} from '../api/login'
import {defineStore} from "pinia";
import {encrypt} from "../utils/encrypt";
import {reactive, ref} from 'vue'
import Cookies from 'js-cookie'
import router, {addRouter, Routers} from "../router/index";
import {getMenus} from "../api/menu";
import {RouteRecordName} from "vue-router";
//后端接口返回的路由表数据
export type rolesValueItemType = {
    id: number
    parentId: number
    name: string
    icon?: string
    hidden: number
    level: number
    sort?: number
    title?: string
}

//后端接口返回的用户数据
export type userInfo = {
    id: number
    roles: string[]
    username: string
}

//默认用户信息
const defaultUserInfo: userInfo = {id: 0, roles: [], username: '' }

export type Tags = {
    path: string
    name?: RouteRecordName | null
    meta?: {
        title: string
        icon?: string
        locale?: string
        breadcrumb?: boolean
        url?: string
        iframeUrl?: string
        iframeData?: any
    }
    remove?: boolean
    query?: {}
    params?: {}
}

//存放用户全局信息
export const useUserStore = defineStore('user', () => {

    //定义token
    const vToken = ref(Cookies.get('vToken'))
    //默认用户信息
    const userInfo = reactive({ ...defaultUserInfo })
    //根据用户返回菜单
    const menus = ref<Routers[]>([])
    const tags = ref<Tags[]>([
        {
            path: '/',
            name: 'home',
            meta: {
                locale: 'home',
                title: '首页',
            },
            remove: true,
        },
    ])

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
                    //和超亿不同
                    router.push({ path: '/' })
                    resolve(res.data)
                })
                .catch((err: { data: string }) => {
                    reject(err.data)
            })
        })
    }

    /**
     * 获取用户信息
     */
    const userInfoRequest = () => {
        return new Promise<typeof menus.value>((resolve, reject) => {
            getMenus({ token: vToken.value })
                .then(async (res: any) => {
                    menus.value = []
                    Object.assign(userInfo, res.data.data)
                    // menusFilter(res.data.data)
                    resolve(menus.value)
                })
                .catch((err) => {
                    outLogin()
                    reject(err)
                })
        })
    }


    /**
     * 踢出
     */
    const outLogin = () => {
        logout().then(() => {
            vToken.value = ''
            Object.assign(userInfo, defaultUserInfo)
            menus.value = []
            router.push('/login')
            Cookies.remove('vToken')
        })
    }

    type Tag = {
        to?: Tags
        removeIndex?: number[]
        name?: string
    }
    /**
     * 标签数据操作
     * @param {Tag} tag
     */
    const tagsOperate = (tag: Tag) => {
        if (tag.removeIndex !== undefined || tag.name !== undefined) {
            if (tag.name) {
                const names = [tag.name, 'home']
                tags.value = tags.value.filter(
                    (item) => names.indexOf(item.name as string) !== -1,
                )
                return
            }
            tag.removeIndex &&
            tags.value.splice(tag.removeIndex[0], tag.removeIndex[1])
        } else {
            try {
                tags.value.push(tag.to!)
            } catch (err) {
                console.error(err)
            }
        }
    }

    return {
        vToken,
        userInfo,
        menus,
        tags,
        loginRequest,
        userInfoRequest,
        outLogin,
        tagsOperate,
    }
})




/**
 * 格式数据
 * @param {rolesValueItemType[]} router 表配置的路由（router.ts 中配置的动态路由）
 * @param {Routers} item 当前路由
 */
function addRouterFun(
    router: rolesValueItemType[],
    item: Routers,
): Routers | undefined {
    let each: rolesValueItemType
    for (each of router) {
        if (item.hidden) {
            item.sort = 0
            return item
        }

        if (item.name == each.name && each.hidden == 1) {
            item.meta.id = each.id
            if (each.title) {
                item.meta.title = each.title
            }
            if (each.icon) {
                item.meta.icon = each.icon
            }

            item.sort = each.sort
            return item
        }
    }
}

function _sort(arr: Routers[]) {
    arr.sort((a: Routers, b: Routers) => {
        return (b as { sort: number }).sort - (a as { sort: number }).sort
    })
}

/**
 * 递归菜单 查询子集
 * @param {Routers} each 当前菜单
 * @param {rolesValueItemType[]} child 当前菜单下子集
 */
function recursion(each: Routers, child: rolesValueItemType[]) {
    // 所有子集
    let ids: rolesValueItemType[] = []
    if (!each.children) {
        return []
    } else {
        if (each.meta && each.meta.id) {
            ids = child.filter((c) => each.meta.id == c.parentId)
        }

        if (ids.length > 0) {
            const children: Routers[] = []
            for (const childrenItem of each.children) {
                const arr = addRouterFun(ids, childrenItem as Routers)
                if (arr) {
                    children.push(arr)
                    recursion(arr, child)
                }
            }
            _sort(children)
            return children
        } else {
            // 没有则删除
            return []
        }
    }
}