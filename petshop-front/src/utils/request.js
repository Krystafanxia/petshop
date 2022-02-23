import request from 'axios';
import qs from 'qs';
import store from '@/store';
import Const from '@/utils/Const';
import { ErrMsg } from "@/utils/message";
const service = request.create({
  baseURL: process.env.VUE_APP_BASE_API,
  withCredentials: true,
  timeout: 30 * 1000
});

service.interceptors.request.use(
  config => {
    // config.headers['Content-Type'] = 'application/x-www-form-urlencoded';
    if (config.ignoreHeader) {
      return config;
    }
    if (store.getters.token) {
      config.headers.token = store.getters.token;
    }
    if (store.getters.userId) {
      config.headers.userId = store.getters.userId;
    }
    return config;
  }
);

service.interceptors.response.use(
  response => {
    const res = response.data || {};
    const config = response.config || {};
    const { status, data, code, message } = res;
    if (code !== Const.STATUS.SUCCESS_CODE && message !== 'success') {
      if (!config.ignoreMsg){
        ErrMsg(message || '系统错误！');
      }
      return Promise.reject(res);
    }
    return data;
  },
  handError
);
export function handError(response) {
  if (typeof response === 'string') {
    ErrMsg(response || '系统错误！');
    return Promise.reject(response);
  }
  const res = response.data || {};
  const config = response.config || {};
  const { request, status, response: dataResponse } = response;
  let message = response.description || '网络错误！';
  if (status === 401 || (dataResponse || {}).status === 401) {
    ErrMsg('请登陆！');
    return store.dispatch("user/logout").then(() => {
      window.location.href = '#/login';
    });
  }
  if ((request || {}).response) {
    try {
      message = JSON.parse(request.response).message;
    } catch (e) {}
  }
  if (!config.ignoreMsg){
    ErrMsg(message || '系统错误！');
  }
  return Promise.reject(res);
}
export function fetch() {
  return new Promise((resolve, reject) => {
    let config = arguments[0] || {};
    if (typeof config === 'string') {
      config = { url: config, method: 'get' };
    }
    if (!config.method) {
      config.method = 'post';
    }
    if (config.method.toLocaleLowerCase() === 'get') {
      config.params = config.params || config.data;
    }
    // if (config.data && config.method.toLocaleLowerCase() === 'post') {
    //   config.data = qs.stringify(config.data);
    // }
    service(config).then(resolve).catch(reject);
  });
}
