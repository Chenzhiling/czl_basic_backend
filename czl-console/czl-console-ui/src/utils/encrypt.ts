import JSEncrypt from 'jsencrypt/bin/jsencrypt.min'
/**
 * Author: CHEN ZHI LING
 * Date: 2024/05/28
 * Description: 加密模块
 */
const publicKey =
    'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDhv43ZwlvFbw1k/2t1NWchQ2JpRUwdBfoJIHn6KSnkZ1jrOEaRlxclJ5YWlyJVaS2J0LC73CUiWW9G9SGBCSfvd5jdrRdAc6eUIXL8+dT0jJuwhJI/vZcFtU7FJxSJAZisAumFMt6uMiljOJivn80Sz7W2pjiZTITRu37k4ubADQIDAQAB'

export function encrypt(password: string) {
    const encryptor = new JSEncrypt()
    encryptor.setPublicKey(publicKey)
    return encryptor.encrypt(password)
}
