<template>
  <md-card class="pet-item" :class="large ? 'large' : ''" @click.native="getDetail">
    <md-card-media v-if="detail">
      <scale-img :detail="detail" />
    </md-card-media>
    <md-card-header v-if="detail">
      <div class="md-field">BREED_NAME: {{ detail.petname }}</div>
      <div class="md-field">AGE: breed {{ detail.age }}</div>
      <div class="md-field">PRICE: {{ detail.price }} RMB</div>
      <div class="md-field more-action" @click="getDetail">More</div>
    </md-card-header>
  </md-card>
</template>
<script>
import ScaleImg from "@/views/components/ScaleImg";
export default {
  components: {ScaleImg},
  props: {
    large: Boolean,
    detail: Object
  },
  data() {
    return {
      height: 242
    }
  },
  methods: {
    getDetail() {
      this.detail.id !== this.$route.query.id && this.$router.push({ path: '/product/detail', query: { id: this.detail.id } })
    }
  }
}
</script>
<style lang="scss" scoped>
.pet-item {
  margin: 12px 0 0 0;
  ::v-deep {
    .short-img {
      cursor: pointer;
    }
    .md-card-header {
      padding: 0;
      margin: 0;
      color: #1f2e54;
      box-shadow: none;
      background-color: transparent;

      &.part-title {
        font-size: 30px;
        font-weight: bold;
      }
    }

    .md-field {
      font-size: 12px;
      margin: 0!important;
      padding: 0!important;
      min-height: 24px!important;
      &:first-child {
        padding: 12px 0 0 0!important;
      }
      &:after {
        display: none;
      }
    }

    .more-action {
      color: #034AFF;
      cursor: pointer;

      &:hover {
        color: lighten(#034AFF, 6%)
      }

      &:active {
        color: darken(#034AFF, 6%)
      }
    }
  }
}
</style>
