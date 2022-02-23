<template>
  <div id="material-kit">
    <router-view v-if="isMange" />
    <div v-else :class="{ 'nav-open': NavbarStore.showNavbar }">
      <router-view name="header" />
      <div>
        <router-view />

        <md-button v-if="showShoppingCartBtn" class="dark-btn shopping-fixed-bth" @click="goShoppingCart">shopping cart</md-button>
      </div>
      <router-view name="footer" />
    </div>

    <Notifications />
  </div>
</template>

<script>
export default {
  computed: {
    showShoppingCartBtn() {
      return ['/search', '/product/detail', '/user/detail'].includes(this.$route.path)
    },
    isMange() {
      return this.$route.path.indexOf('/manage') === 0
    }
  },
  methods: {
    goShoppingCart() {
      this.$router.push({ path: '/cart' })
    }
  }
};
</script>
