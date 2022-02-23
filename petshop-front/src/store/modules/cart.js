import {login} from "@/api/user";

export default {
  namespaced: true,
  state: {
    list: getCacheList()
  },
  mutations: {
    ADD_TO_CART(state, id) {
      const existItem = state.list.find(item => item.id === id);
      if (!existItem) {
        state.list.push({ id, count: 1 });
      } else {
        existItem.count += 1;
      }
    },
    DEL_FROM_CART(state, delId) {
      const existItem = state.list.find(item => item.id === delId);
      if (existItem) {
        existItem.count = Math.max(0, existItem.count - 1);
      }
      if (existItem.count === 0) {
        state.list.splice(state.list.findIndex(item => item.id === existItem.id), 1);
      }
      state.list = state.list.filter(id => id !== delId);
    },
    CLEAN_PET_FROM_CART(state, delId) {
      const existItem = state.list.find(item => item.id === delId);
      if (existItem) {
        state.list.splice(state.list.findIndex(item => item.id === existItem.id), 1);
      }
    }
  },
  actions: {
    cleanFromCart({commit}, id) {
      cleanPetFromCart(id);
      commit('CLEAN_PET_FROM_CART', id);
    },
    addPetToCart({commit}, id) {
      cacheCart(id);
      commit('ADD_TO_CART', id);
    },
    delFromCart({commit}, id) {
      delCacheCart(id);
      commit('DEL_FROM_CART', id);
    },
  }
};

function cacheCart(id) {
  try {
    const list = getCacheList();
    const existItem = list.find(item => item.id === id);
    if (!existItem) {
      list.push({ id, count: 1 });
    } else {
      existItem.count += 1;
    }
    sessionStorage.setItem(getCartKey(), JSON.stringify(list));
  } catch (e) {
  }
}
function getCartKey() {
  try {
    return `cart_${JSON.parse(sessionStorage.getItem('userInfo')).id}`;
  } catch (e) {
    return 'cart';
  }
}
function cleanPetFromCart(delId) {
  try {
    const list = getCacheList();
    const existItem = list.find(item => item.id === delId);
    if (existItem) {
      list.splice(list.findIndex(item => item.id === existItem.id), 1);
    }
    sessionStorage.setItem(getCartKey(), JSON.stringify(list));
  } catch (e) {
  }
}
function delCacheCart(delId) {
  try {
    const list = getCacheList();
    const existItem = list.find(item => item.id === delId);
    if (existItem) {
      existItem.count = Math.max(0, existItem.count - 1);
    }
    if (existItem.count === 0) {
      list.splice(list.findIndex(item => item.id === existItem.id), 1);
    }
    sessionStorage.setItem(getCartKey(), JSON.stringify(list));
  } catch (e) {
  }
}
function getCacheList() {
  try {
    return JSON.parse(sessionStorage.getItem(getCartKey()) || '[]') || [];
  } catch (e) {
    return {};
  }
}

