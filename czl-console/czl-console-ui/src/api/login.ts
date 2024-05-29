import request from '../utils/request'
/**
 * Author: CHEN ZHI LING
 * Date: 2024/05/28
 * Description: 请求接口
 */
export const login = <T>(data: any) => request.post<T>('/api/auth/login', data)

export const logout = () => request.delete('/api/auth/logout')
