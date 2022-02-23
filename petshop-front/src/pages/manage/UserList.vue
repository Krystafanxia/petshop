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
            <simple-table v-loading="loading" table-header-color="green" :list="userList" :props="props" :selected.sync="selected" />
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
import {deleteUser, users} from "@/api/user";

export default {
  mixins: [loginUser],
  components: {
    SimpleTable,
  },
  data() {
    return {
      loading: false,
      selected: [],
      userList: [],
      props: [
        { label: 'FirstName', key: 'firstname' },
        { label: 'LastName', key: 'lastname' },
        { label: 'Age', key: 'age' },
        { label: 'Country', key: 'country' },
        { label: 'City', key: 'city' },
        { label: 'Phone', key: 'phone' },
        { label: 'Email', key: 'email' },
        { label: 'Address', key: 'address' },
        { label: 'Role', key: 'role', formatter: v => Object.keys(Const.USER_TYPE).find(key => Const.USER_TYPE[key] === v) }
      ]
    }
  },
  created() {
    this.pageData()
  },
  methods: {
    pageData() {
      this.loading = true
      users().then(res => {
        this.loading = false
        this.userList = res || []
      }).catch(() => (this.loading = false))
    },
    add() {
      this.$router.push({ path: '/manage/user/add' });
    },
    edit() {
      this.$router.push({ path: '/manage/user/edit', query: { id: this.selected[0].id } })
    },
    async del() {
      try {
        await this.$confirm('Sure delete select item?')
        this.loading = true
        await deleteUser(this.selected[0].id)
        SucMsg('删除成功！')
        await this.pageData()
      } catch (e) {}
      this.loading = false
    }
  }
}
</script>
