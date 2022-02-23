<template>
  <md-card class="pet-list" :class="title ? 'list-title' : ''">
    <md-card-header v-if="title" class="part-title">
      {{ title }}
    </md-card-header>
    <md-card-content v-if="empty" class="pet-list-content">
      <md-empty-state :md-size="20" md-description="Nothing in There" />
    </md-card-content>
    <md-card-content v-else class="pet-list-content">
      <pet-item v-for="item in list" :key="item.id" :detail="item" />
      <pet-item v-for="i in 4" :key="`tmp_${i}`" />
    </md-card-content>
  </md-card>
</template>
<script>
import _ from 'lodash'
import PetItem from "@/views/components/PetItem";

export default {
  components: {
    PetItem,
  },
  props: {
    large: Boolean,
    title: String,
    list: Array
  },
  data() {
    return {
      empty: false
    }
  },
  computed: {
  },
  watch: {
    list(v) {
      this.empty = _.isEmpty(v)
    }
  }
}
</script>
<style lang="scss" scoped>
.pet-list {
  box-shadow: none;
  background-color: transparent!important;
  &:not(.list-title) {
    margin: 0;
  }
  .pet-list-content {
    padding: 0;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
  }
  @media screen and (max-width: 800px){
    .pet-list-content {
      justify-content: space-around;
    }
  }
  ::v-deep {
    .md-card {
      flex: 22.3% 0;
      box-shadow: none;
      background-color: transparent;
    }
    @media screen and (max-width: 800px){
      .md-card {
        flex: 31% 0;
      }
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
        margin: 10px 0;
      }
    }
  }
}
</style>
