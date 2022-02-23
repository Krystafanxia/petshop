import { fetch } from '@/utils/request';

export function login(data) {
  return fetch({
    url: `/login?name=${data.name}&password=${data.password}`,
    data
  });
}

export function users() {
  return fetch({
    url: `/users`
  });
}
export function deleteUser(id) {
  return fetch({
    url: `/deleteUser/${id}`
  });
}

export function userInfo(id) {
  return fetch({
    url: `/user/${id}`
  });
}

export function addUser(data) {
  return fetch({
    url: `/addUser`,
    data
  });
}

export function modifyUse(data) {
  return fetch({
    url: `/modifyUser`,
    data
  });
}


/**
 * add friend
 * @param data {{ userid, friendid }}
 * @returns {Promise | Promise<unknown>}
 */
export function addFriend(data) {
  return fetch({
    url: `/addFriend`,
    data
  });
}
export function getFriends(id) {
  return fetch({
    url: `/getFriends?userid=${id}`
  });
}

export function delFriend(id) {
  return fetch({
    url: `/delFriend`,
    data: { id }
  });
}

