<template>
  <md-toolbar
    id="toolbar"
    md-elevation="0"
    class="md-absolute md-white"
    :class="extraNavClasses"
    :color-on-scroll="colorOnScroll"
  >
    <div class="md-toolbar-row md-collapse-lateral">
      <div class="md-toolbar-section-start">
        <h3 class="md-title page-title" @click="$router.push({ path: '/' })">Pet mall platform system</h3>

        <md-list>
          <md-list-item href="#/"><p>Home</p></md-list-item>
          <md-list-item href="#/search"><p>Search</p></md-list-item>
          <md-list-item href="#/cart"><p>cart</p></md-list-item>
          <md-list-item v-if="!showLogin && isLogin" href="#/user/detail"><p>User Center</p></md-list-item>
        </md-list>
      </div>
      <div class="md-toolbar-section-end">
        <md-button
          class="md-just-icon md-simple md-toolbar-toggle"
          :class="{ toggled: toggledClass }"
          @click="toggleNavbarMobile()"
        >
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </md-button>

        <div class="md-collapse">
          <div class="md-collapse-wrapper">
            <mobile-menu nav-mobile-section-start="true">
              <md-list-item href="#/search" >
                <i class="material-icons">view_day</i>
                <p>Search</p>
              </md-list-item>
              <md-list-item href="#/cart" >
                <i class="material-icons">content_paste</i>
                <p>Cart</p>
              </md-list-item>
              <md-list-item v-if="!showLogin && isLogin" href="#/user/detail" >
                <i class="material-icons">account_circle</i>
                <p>User Center</p>
              </md-list-item>
            </mobile-menu>
            <md-list v-if="showLogin">
              <md-list-item href="#/login" >
                <i class="material-icons">fingerprint</i>
                <!--<p class="hidden-lg">Twitter</p>-->
                <p>Login</p>
                <md-tooltip md-direction="bottom">Login pet system</md-tooltip
                >
              </md-list-item>
            </md-list>
            <li v-if="showInfo" class="md-list-item">
              <a
                href="javascript:void(0)"
                class="md-list-item-router md-list-item-container md-button-clean dropdown"
              >
                <div class="md-list-item-content">
                  <drop-down direction="down">
                    <md-button
                      slot="title"
                      class="md-button md-button-link md-simple dropdown-toggle"
                      data-toggle="dropdown"
                    >
                      <md-avatar>
                        <scale-img :file="avatarFile" :rate="1" />
                      </md-avatar>
                      <p>&nbsp;&nbsp;{{ userName  }}</p>
                    </md-button>
                    <ul class="dropdown-menu">
                      <li>
                        <a href="#/manage/pet">
                          <i class="material-icons">content_paste</i>
                          <p>Manage</p>
                        </a>
                      </li>
                      <li>
                        <a href="#/user/detail/edit">
                          <i class="material-icons">person</i>
                          <p>Edit Info</p>
                        </a>
                      </li>
                      <li>
                        <a href="javascript:;" @click.stop="exit">
                          <i class="material-icons">fingerprint</i>
                          <p>Logout</p>
                        </a>
                      </li>
                      <li>
                        <a href="#/user/detail">
                          <i class="material-icons">account_circle</i>
                          <p>User Center</p>
                        </a>
                      </li>
                    </ul>
                  </drop-down>
                </div>
              </a>
            </li>
          </div>
        </div>
      </div>
    </div>
  </md-toolbar>
</template>

<script>
import {mapActions, mapGetters, mapState} from "vuex";

let resizeTimeout;
function resizeThrottler(actualResizeHandler) {
  // ignore resize events as long as an actualResizeHandler execution is in the queue
  if (!resizeTimeout) {
    resizeTimeout = setTimeout(() => {
      resizeTimeout = null;
      actualResizeHandler();

      // The actualResizeHandler will execute at a rate of 15fps
    }, 66);
  }
}

import MobileMenu from "@/layout/MobileMenu";
import ScaleImg from "@/views/components/ScaleImg";
export default {
  components: {
    MobileMenu, ScaleImg
  },
  props: {
    type: {
      type: String,
      default: "white",
      validator(value) {
        return [
          "white",
          "default",
          "primary",
          "danger",
          "success",
          "warning",
          "info"
        ].includes(value);
      }
    },
    colorOnScroll: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      ...mapActions({ logout: 'user/logout' }),
      extraNavClasses: "",
      toggledClass: false
    };
  },
  computed: {
    ...mapGetters(['isLogin']),
    ...mapState({ userName: state => state.user.info.name, avatarFile: state => state.user.info.avatarFile }),
    showLogin() {
      return this.$route.name !== 'login' && !Boolean(this.userName)
    },
    showInfo() {
      return Boolean(this.userName)
    }
  },
  methods: {
    bodyClick() {
      let bodyClick = document.getElementById("bodyClick");

      if (bodyClick === null) {
        let body = document.querySelector("body");
        let elem = document.createElement("div");
        elem.setAttribute("id", "bodyClick");
        body.appendChild(elem);

        let bodyClick = document.getElementById("bodyClick");
        bodyClick.addEventListener("click", this.toggleNavbarMobile);
      } else {
        bodyClick.remove();
      }
    },
    toggleNavbarMobile() {
      this.NavbarStore.showNavbar = !this.NavbarStore.showNavbar;
      this.toggledClass = !this.toggledClass;
      this.bodyClick();
    },
    handleScroll() {
      let scrollValue =
        document.body.scrollTop || document.documentElement.scrollTop;
      let navbarColor = document.getElementById("toolbar");
      this.currentScrollValue = scrollValue;
      if (this.colorOnScroll > 0 && scrollValue > this.colorOnScroll) {
        this.extraNavClasses = `md-${this.type}`;
        // navbarColor.classList.remove("md-transparent");
      } else {
        if (this.extraNavClasses) {
          this.extraNavClasses = `md-${this.type}`;
          // navbarColor.classList.add("md-transparent");
        }
      }
    },
    scrollListener() {
      resizeThrottler(this.handleScroll);
    },
    scrollToElement() {
      let element_id = document.getElementById("downloadSection");
      if (element_id) {
        element_id.scrollIntoView({ block: "end", behavior: "smooth" });
      }
    },
    exit() {
      this.logout();
      this.$router.push({ path: '/login' });
    }
  },
  mounted() {
    document.addEventListener("scroll", this.scrollListener);
  },
  beforeDestroy() {
    document.removeEventListener("scroll", this.scrollListener);
  },
};
</script>

<style lang="scss" scoped>
  .page-title {
    cursor: pointer;
    font-size: 30px!important;
    font-weight: bold!important;
    color: #555555 !important;
  }
  @media screen and (max-width: 600px) {
    .page-title {
      font-size: 20px !important;
    }
  }
  .md-toolbar {
    padding-top: 0!important;
  }
  .dropdown-menu a {
    display: flex;
  }
</style>
