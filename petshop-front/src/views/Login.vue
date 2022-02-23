<template>
  <div class="wrapper">
    <div class="section page-header header-filter" :style="headerStyle">
      <div class="container">
        <div class="md-layout">
          <div
            class="md-layout-item md-size-33 md-small-size-66 md-xsmall-size-100 md-medium-size-40 mx-auto"
          >
            <login-card header-color="green" @keydown.enter.native.stop="doLogin">
              <h4 slot="title" class="card-title">Login</h4>
              <md-button
                slot="buttons"
                href="javascript:void(0)"
                class="md-just-icon md-simple md-white"
              >
                <i class="fab fa-facebook-square"></i>
              </md-button>
              <md-button
                slot="buttons"
                href="javascript:void(0)"
                class="md-just-icon md-simple md-white"
              >
                <i class="fab fa-twitter"></i>
              </md-button>
              <md-button
                slot="buttons"
                href="javascript:void(0)"
                class="md-just-icon md-simple md-white"
              >
                <i class="fab fa-google-plus-g"></i>
              </md-button>
              <p slot="description" class="description">Or Be Classical</p>
              <md-field class="md-form-group" slot="inputs">
                <md-icon>face</md-icon>
                <label>Account</label>
                <md-input v-model="account"></md-input>
              </md-field>
              <md-field class="md-form-group" slot="inputs">
                <md-icon>lock_outline</md-icon>
                <label>Password...</label>
                <md-input v-model="password" type="password"></md-input>
              </md-field>
              <md-button :disabled="loading" slot="footer" class="md-simple md-success md-lg" @click="doLogin">
                Get Started
                <md-progress-spinner v-if="loading" :md-diameter="10" :md-stroke="1" md-mode="indeterminate" />
              </md-button>
            </login-card>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { LoginCard } from "@/components";
import {login} from "@/api/user";
import {mapActions} from "vuex";
import {getAvatar} from "@/api/file";

export default {
  components: {
    LoginCard
  },
  bodyClass: "login-page",
  data() {
    return {
      account: null,
      password: null,
      loading: false,
      ...mapActions({ setUserInfo: 'user/setUserInfo' })
    };
  },
  props: {
    header: {
      type: String,
      default: require("@/assets/img/login-bg.jpeg")
    }
  },
  computed: {
    headerStyle() {
      return {
        height: '100vh',
        backgroundImage: `url(${this.header})`
      };
    }
  },
  methods: {
    doLogin() {
      if (this.loading) return
      const { account, password } = this
      this.loading = true
      login({ name: account, password }).then(res => {
        getAvatar(res.id).then(files => {
          this.loading = false
          this.setUserInfo({ ...(res || {}), avatarFile: (files || [])[0]})
          this.$router.push({ path: '/user/detail' })
          window.location.reload()
        })
      }).catch(() => (this.loading = false))
    }
  }
};
</script>

<style lang="css"></style>
