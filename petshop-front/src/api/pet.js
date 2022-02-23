import { fetch } from '@/utils/request';

/**
 *
 * @param data
 * {
 *   "petname":"Fangfang",
 *   "story":"beautiful dog",
 *   "price":108,
 *   "kind":"04"
 *"  status":"00"
 *  }
 * @returns {Promise<unknown>}
 */
export function addPet(data) {
  return fetch({
  url: '/addPet',
  data
});
}


export function queryPets(data = {}) {
  return fetch({
    url: '/queryPets',
    data
  });
}


export function dropPet(data) {
  return fetch({
    url: '/dropPet',
    data
  });
}

export function modifyPet(data) {
  return fetch({
    url: '/modifyPet',
    data
  });
}

/**
 * 浏览某个宠物
 * @param data {{ userid,  petid}}
 *
 * @returns {Promise<unknown>}
 */
export function viewPet(data) {
  return fetch({
    url: '/viewPet',
    data
  });
}

/**
 * 获取用户浏览宠物的list（从高到底排序）
 * @param data
 * {
 *   "userid":"1"
 * }
 * @returns {Promise<unknown>}
 */
export function getViewPets(data) {
  return fetch({
    url: '/getViewPets',
    data
  });
}
