
import Vue from "vue";
import App from "./App.vue";
import store from "./store";
import router from "./routes/routes";
import GlobalComponents from "./globalComponents";
import GlobalDirectives from "./globalDirectives";
import Notifications from "./components/NotificationPlugin";
import MaterialKit from "./plugins/material-kit";
import SideBar from "@/components/SidebarPlugin";
import VueMaterial from "vue-material";
import Chartist from "chartist";
import "vue-material/dist/vue-material.min.css";
import "@/assets/scss/material-kit.scss";
import "./assets/scss/material-dashboard.scss";
import Element from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

// library auto imports
import "es6-promise/auto";

Vue.prototype.$Chartist = Chartist;
Vue.config.productionTip = false;
Vue.use(Element);
Vue.use(SideBar);
Vue.use(VueMaterial);
Vue.use(GlobalComponents);
Vue.use(GlobalDirectives);
Vue.use(Notifications);
Vue.use(MaterialKit);

const NavbarStore = {
  showNavbar: false
};

Vue.mixin({
  data() {
    return {
      NavbarStore
    };
  }
});

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");

