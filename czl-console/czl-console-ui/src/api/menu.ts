//对应后端菜单表
import request from "../utils/request";

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
export const getMenus = (params?: any) => request({ url: '/api/menu/listAll' })