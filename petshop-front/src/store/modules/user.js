export default {
  namespaced: true,
  state: {
    info: getCacheUserInfo()
  },
  mutations: {
    SET_USER_INFO(state, value) {
      state.info = value || {};
    },
    CHANGE_AVATAR(state, value) {
      if (state.info && state.info.avatarFile) {
        state.info.avatarFile.filename = value;
        cacheUserInfo(state.info);
      }
    }
  },
  actions: {
    logout({commit}) {
      cacheUserInfo({});
      commit('SET_USER_INFO', {});
    },
    setUserInfo({commit}, value) {
      cacheUserInfo(value);
      commit('SET_USER_INFO', value);
    }
  }
};

function cacheUserInfo(info) {
  try {
    sessionStorage.setItem('userInfo', JSON.stringify(info));
  } catch (e) {
  }
}
function getCacheUserInfo() {
  try {
    return JSON.parse(sessionStorage.getItem('userInfo') || '{}') || {};
  } catch (e) {
    return {};
  }
}
