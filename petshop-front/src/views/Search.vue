<template>
  <div class="wrapper">
    <div class="main main-raised">
      <Search @search="doSearch" />

      <nav-tabs-card no-label class="search-tab simple-tab card-page">
        <template slot="content">
          <el-tag v-if="keyword" closable class="keyword-tag" @close="cleanSearch">{{ keyword }}</el-tag>
          <md-tabs v-loading="loading" class="md-primary" md-alignment="left">
            <md-tab id="all" md-label="All Mall">
              <pet-list :list="petList" />
            </md-tab>
            <md-tab v-for="item in typesPageList" :key="item.kind" :id="item.kind" :md-label="`${item.kindName} Mall`">
              <pet-list :list="item.data" />
            </md-tab>
          </md-tabs>
        </template>
      </nav-tabs-card>

    </div>
  </div>
</template>

<script>
import Search from "@/views/components/Search";
import PetList from "@/views/components/PetList";
import { NavTabsCard } from "@/components";
import {queryPets} from "@/api/pet";
import Const from "@/utils/Const";

export default {
  components: {
    PetList,
    Search,
    NavTabsCard
  },
  name: "index",
  bodyClass: "index-page",
  data() {
    return {
      keyword: '',
      loading: false,
      petList: [],
      types: Const.PET_TYPE
    };
  },
  computed: {
    typesPageList() {
      const type = {}
      Object.keys(Const.PET_TYPE).forEach(key => type[key] = [])
      this.petList.forEach(item => type[item.kind].push(item))
      return Object.keys(type).map(key => ({ kindName: Const.PET_TYPE[key], kind: key, data: type[key] }))
    }
  },
  created() {
    this.keyword = this.$route.params.keyword || ''
    this.pageData(this.keyword ? { keyword: this.keyword } : {})
  },
  methods: {
    pageData(detail = {}) {
      this.loading = true
      queryPets(detail).then(res => {
        this.loading = false
        this.petList = res || []
      }).catch(() => (this.loading = false))
    },
    doSearch(keyword) {
      this.pageData({ petname: keyword })
      this.keyword = keyword || ''
    },
    cleanSearch() {
      this.keyword = ''
      this.pageData()
    }
  },
};
</script>
<style lang="scss" scoped>
.search-tab {
  margin-top: 0;
}
.keyword-tag {
  color: white;
  border: none;
  line-height: 22px;
  height: 22px;
  position: absolute;
  top: 11px;
  z-index: 999;
  border-radius: 30px;
  background-color: #0db778;
  ::v-deep .el-tag__close {
    &:hover {
      background-color: #ff40a1!important;
    }
    &:before {
      color: white!important;
    }
  }
}
</style>
