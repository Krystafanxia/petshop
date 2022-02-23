<template>
  <div class="part">
    <md-field class="search-input" md-inline>
      <md-input v-model="keyword" placeholder="search" @keyup.enter.native.stop="search" />
      <i class="fas fa-search" @click="search" />
    </md-field>
  </div>
</template>
<script>
import {ErrMsg} from "@/utils/message";

export default {
  data() {
    return {
      keyword: ''
    }
  },
  methods: {
    search() {
      if (!this.keyword) {
        return ErrMsg("请输入关键字！")
      }
      this.$route.path !== '/search' && this.$router.push({ name: 'search', params: { keyword: this.keyword } })
      this.$emit('search', this.keyword)
    }
  }
}
</script>
<style lang="scss" scoped>
.md-field.search-input {
  height: 56px;
  padding: 9px 23px;
  line-height: 56px;
  border-radius: 24px;
  background-color: white;
  .fa-search {
    cursor: pointer;
    font-size: 22px;
  }
  &:after, &:before {
    display: none!important;
  }
}
</style>
