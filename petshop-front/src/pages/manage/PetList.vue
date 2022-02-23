<template>
  <div class="content">
    <div class="md-layout">
      <div
        class="md-layout-item md-medium-size-100 md-xsmall-size-100 md-size-100"
      >
        <md-card>
          <md-card-header data-background-color="green" class="md-layout">
            <!--h4 class="title md-layout-item md-size-20">Pets</h4!-->
            <div class="md-layout-item ">
              <md-button class="md-mini md-info" @click="add">
                <md-icon>add</md-icon>
                <div>add</div>
              </md-button>
              <md-button :disabled="(selected ||[]).length !== 1" class="md-mini md-warning" @click="edit">
                <md-icon>edit</md-icon>
                <div>edit</div>
              </md-button>
              <md-button :disabled="(selected ||[]).length !== 1" class="md-mini md-danger" @click="del">
                <md-icon>delete</md-icon>
                <div>delete</div>
              </md-button>
            </div>
          </md-card-header>
          <md-card-content>
            <simple-table v-loading="loading" table-header-color="green" :list="petList" :props="props" :selected.sync="selected" />
          </md-card-content>
        </md-card>
      </div>

    </div>
  </div>
</template>

<script>
import { SimpleTable } from "@/components";
import {dropPet, getViewPets, queryPets} from "@/api/pet";
import loginUser from "@/pages/manage/loginUser";
import Const, {formatter} from "@/utils/Const";
import {SucMsg} from "@/utils/message";
import {mapGetters} from "vuex";

export default {
  mixins: [loginUser],
  components: {
    SimpleTable,
  },
  data() {
    return {
      loading: false,
      selected: [],
      petList: [],
      props: [
        { label: 'Name', key: 'petname' },
        { label: 'Kind', key: 'kind', formatter: formatter(Const.PET_TYPE) },
        { label: 'Status', key: 'status', formatter: formatter(Const.SALE_TYPE) },
        { label: 'PageViews', key: 'pageviews' },
        { label: 'Story', key: 'story' }
      ]
    }
  },
  computed: {
    ...mapGetters(['isAdmin', 'userId']),
  },
  created() {
    this.pageData()
  },
  methods: {
    pageData() {
      this.loading = true
      queryPets().then(res => {
        this.loading = false
        if (this.isAdmin) {
          this.petList = res || []
        } else {
          this.petList = (res || []).filter(item => item.userid === this.userId)
        }
      }).catch(() => (this.loading = false))
    },
    add() {
      this.$router.push({ path: '/manage/pet/add' });
    },
    edit() {
      this.$router.push({ path: '/manage/pet/edit', query: { id: this.selected[0].id } })
    },
    async del() {
      try {
        await this.$confirm('Sure delete select item?')
        this.loading = true
        await dropPet({id: this.selected[0].id})
        SucMsg('删除成功！')
        await this.pageData()
      } catch (e) {
        this.loading = false
      }
    }
  }
}
</script>
