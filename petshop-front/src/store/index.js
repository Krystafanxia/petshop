import Vue from 'vue';
import Vuex from 'vuex';
import user from './modules/user';
import cart from './modules/cart';
import Const from "@/utils/Const";
Vue.use(Vuex);
const store = new Vuex.Store({
  modules: { user, cart },
  getters: {
    isLogin: state => state.user.info && state.user.info.id,
    userId: state => (state.user.info || {}).id,
    token: state => (state.user.info || {}).token,
    isAdmin: state => (state.user.info || {}).role === Const.USER_TYPE.ADMIN
  }
});

export default store;
