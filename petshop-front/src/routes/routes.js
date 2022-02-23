import DashboardLayout from "@/pages/Layout/DashboardLayout.vue";

import PetList from "@/pages/manage/PetList.vue";
import UserList from "@/pages/manage/UserList.vue";
import EditPet from "@/pages/manage/EditPet.vue";
import EditUser from "@/pages/manage/EditUser.vue";
import Dashboard from "@/pages/Dashboard.vue";
import Vue from "vue";
import Router from "vue-router";
import Index from "../views/Index.vue";
import Search from "../views/Search.vue";
import Detail from "../views/Detail.vue";
import UserDetail from "../views/UserDetail.vue";
import EditMyInfo from "../views/EditMyInfo.vue";
import Cart from "../views/Cart.vue";
import Temp from "../views/Temp";
import Landing from "../views/Landing.vue";
import Login from "../views/Login.vue";
import Profile from "../views/Profile.vue";
import MainNavbar from "../layout/MainNavbar.vue";
import MainFooter from "../layout/MainFooter.vue";
import store from "@/store";

// const withFoodHeader = c => ({ default: c, header: MainNavbar, footer: MainFooter });
const withFoodHeader = c => ({ default: c, header: MainNavbar });
const props = { header: { colorOnScroll: 400 }, footer: { backgroundColor: "black" }};
const router = new Router({
  routes: [
    {
      path: "/",
      name: "index",
      components: withFoodHeader(Index),
      props
    },
    {
      path: "/search",
      name: "search",
      components: withFoodHeader(Search),
      props
    },
    {
      path: "/product/detail",
      name: "detail",
      components: withFoodHeader(Detail),
      props
    },
    {
      path: "/user/detail",
      name: "detail",
      components: withFoodHeader(UserDetail),
      props
    },
    {
      path: "/user/detail/edit",
      name: "editDetail",
      components: withFoodHeader(EditMyInfo),
      props
    },
    {
      path: "/cart",
      name: "cart",
      components: withFoodHeader(Cart),
      props
    },
    {
      path: "/tmp",
      name: "tmp",
      components: { default: Temp, header: MainNavbar, footer: MainFooter },
      props
    },
    {
      path: "/landing",
      name: "landing",
      components: { default: Landing, header: MainNavbar, footer: MainFooter },
      props
    },
    {
      path: "/login",
      name: "login",
      components: { default: Login, header: MainNavbar, footer: MainFooter },
      props
    },
    {
      path: "/profile",
      name: "profile",
      components: { default: Profile, header: MainNavbar, footer: MainFooter },
      props
    },
    {
      path: "/manage",
      component: DashboardLayout,
      children: [
        {
          path: "dashboard",
          name: "Dashboard",
          component: Dashboard,
        },

        {
          path: "pet",
          name: "petList",
          component: PetList,
        },

        {
          path: "pet/add",
          name: "addPet",
          component: EditPet,
        },
        {
          path: "pet/edit",
          name: "editPet",
          component: EditPet,
        },
        {
          path: "user",
          name: "userList",
          component: UserList,
        },
        {
          path: "user/edit",
          name: "editUser",
          component: EditUser,
        },
        {
          path: "user/add",
          name: "editUser",
          component: EditUser,
        }
      ],
    }
  ],
  scrollBehavior: to => {
    if (to.hash) {
      return { selector: to.hash };
    } else {
      return { x: 0, y: 0 };
    }
  }
});

router.beforeEach((to, from, next) => {
  if (to.path.indexOf('/manage') === 0 && !store.state.user.info.id) {
    return next('/login');
  }
  next();
});

Vue.use(Router);

export default router;
