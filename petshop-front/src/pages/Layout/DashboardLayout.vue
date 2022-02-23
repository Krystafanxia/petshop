<template>
  <div class="wrapper" :class="{ 'nav-open': $sidebar.showSidebar }">
    <notifications></notifications>

    <side-bar
      title="pet store"
      :sidebar-item-color="sidebarBackground"
      :sidebar-background-image="sidebarBackgroundImage"
    >
      <mobile-menu slot="content"></mobile-menu>
      <sidebar-link v-if="isAdmin" to="/manage/user">
        <md-icon>person</md-icon>
        <p>Users</p>
      </sidebar-link>
      <sidebar-link to="/manage/pet">
        <md-icon>bubble_chart</md-icon>
        <p>Pets</p>
      </sidebar-link>
    </side-bar>

    <div class="main-panel">
      <top-navbar></top-navbar>

      <!--fixed-plugin
        :color.sync="sidebarBackground"
        :image.sync="sidebarBackgroundImage"
      >
      </fixed-plugin!-->

      <dashboard-content> </dashboard-content>

      <!--<content-footer v-if="!$route.meta.hideFooter"></content-footer>-->
      <main-footer />
    </div>
  </div>
</template>

<script>
import TopNavbar from "./TopNavbar.vue";
import ContentFooter from "./ContentFooter.vue";
import DashboardContent from "./Content.vue";
import MobileMenu from "@/pages/Layout/MobileMenu.vue";
import FixedPlugin from "./Extra/FixedPlugin.vue";
import MainFooter from "@/layout/MainFooter";
import {mapGetters} from "vuex";

export default {
  components: {
    TopNavbar,
    DashboardContent,
    MainFooter,
    MobileMenu,
  },
  data() {
    return {
      sidebarBackground: "green",
      sidebarBackgroundImage: require("@/assets/img/sidebar-2.jpg"),
    };
  },
  computed: {
  ...mapGetters(['isAdmin']),
  }
};
</script>
