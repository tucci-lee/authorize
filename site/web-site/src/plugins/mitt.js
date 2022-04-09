// 消息总线，组件通信
import mitt from "mitt"

const mittObj = mitt();

/**
 * 发布头像地址
 * @param avatar
 */
export function pubAvatar(avatar) {
    mittObj.emit("avatar", avatar);
}

/**
 * 监听头像修改
 * @param fn
 */
export function subAvatar(fn) {
    mittObj.on('avatar', data => fn(data));
}

/**
 * 发布获取账号信息
 */
export function pubGetAccount() {
    mittObj.emit("getAccount", true);
}

/**
 * 订阅获取账号信息
 * @param fn
 */
export function subGetAccount(fn) {
    mittObj.on('getAccount', data => fn(data));
}