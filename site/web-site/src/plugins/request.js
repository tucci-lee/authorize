import axios from "@/plugins/axios";

export function getRegion(parentId) {
    return axios.get('/open/region?parentId=' + parentId);
}

export function sendSmsCaptcha(body) {
    return axios.post('/captcha/sms', body);
}

export function sendEmailCaptcha(body) {
    return axios.post('/captcha/email', body);
}

export function smsSignin(body) {
    return axios.post('/signin/sms', body);
}

export function accountSignin(body) {
    return axios.post('/signin/account', body);
}

export function verify(){
    return axios.get('/verify');
}

export function signout() {
    return axios.post('/signout');
}

export function getProfile() {
    return axios.get('/user/profile');
}

export function updateAvatar(formData) {
    return axios.put('/user/profile/update_avatar', formData);
}

export function updateProfile(body) {
    return axios.put('/user/profile/update', body);
}

export function getAccount(){
    return axios.get('/user/account');
}

export function sendAccountSmsCaptcha(body){
    return axios.post('/user/account/captcha/sms', body);
}

export function phoneVerify(body){
    return axios.post('/user/account/phone_verify', body);
}

export function updateEmail(body){
    return axios.put('/user/account/update_email', body);
}

export function updatePhone(body){
    return axios.put('/user/account/update_phone', body);
}

export function updatePassword(body){
    return axios.put('/user/account/update_password', body);
}
