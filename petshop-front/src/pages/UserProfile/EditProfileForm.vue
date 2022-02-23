<template>
  <form>
    <md-card>
      <md-card-header :data-background-color="dataBackgroundColor">
        <h4 class="title">Edit User Profile</h4>
        <p class="category">Complete user's profile</p>
      </md-card-header>
      <md-card-content>
        <div v-loading="loading" class="md-layout">
          <div class="md-layout-item md-small-size-100 md-size-33">
            <md-field>
              <label>Name / Login Account</label>
              <md-input v-model="detail.name"></md-input>
            </md-field>
          </div>
          <div class="md-layout-item md-small-size-100 md-size-33">
            <md-field>
              <label>Password / Login Password</label>
              <md-input v-model="detail.password"></md-input>
            </md-field>
          </div>
          <div class="md-layout-item md-small-size-100 md-size-33">
            <md-field>
              <label>Role / User Type</label>
              <md-select v-model="detail.role">
                <md-option v-for="item in roleOptions" :key="item.key" :value="item.key">{{ item.label }}</md-option>
              </md-select>
            </md-field>
          </div>
          <div class="md-layout-item md-small-size-100 md-size-33">
            <md-field>
              <label>First Name</label>
              <md-input v-model="detail.firstname"></md-input>
            </md-field>
          </div>
          <div class="md-layout-item md-small-size-100 md-size-33">
            <md-field>
              <label>Last Name</label>
              <md-input v-model="detail.lastname" type="text"></md-input>
            </md-field>
          </div>
          <div class="md-layout-item md-small-size-100 md-size-33">
            <md-field>
              <label>Age</label>
              <md-input v-model="detail.age" type="number"></md-input>
            </md-field>
          </div>
          <div class="md-layout-item md-small-size-100 md-size-33">
            <md-field>
              <label>Email Address</label>
              <md-input v-model="detail.email" type="email"></md-input>
            </md-field>
          </div>
          <div class="md-layout-item md-small-size-100 md-size-66">
            <md-field>
              <label>Phone</label>
              <md-input v-model="detail.phone" type="number"></md-input>
            </md-field>
          </div>
          <div class="md-layout-item md-small-size-100 md-size-33">
            <md-field>
              <label>City</label>
              <md-input v-model="detail.city" type="text"></md-input>
            </md-field>
          </div>
          <div class="md-layout-item md-small-size-100 md-size-33">
            <md-field>
              <label>Country</label>
              <md-input v-model="detail.country" type="text"></md-input>
            </md-field>
          </div>
          <div class="md-layout-item md-small-size-100 md-size-33">
            <md-field>
              <label>Postal Code</label>
              <md-input v-model="detail.code" type="number" />
            </md-field>
          </div>
          <div class="md-layout-item md-small-size-100 md-size-100">
            <md-field>
              <label>Address</label>
              <md-input v-model="detail.address" type="text" />
            </md-field>
          </div>
          <div class="md-layout-item md-size-100 text-right">
            <md-button :disbaled="submitIng" class="md-raised md-success" @click="doSave">
              Save Profile
              <md-progress-spinner v-if="submitIng" :md-diameter="10" :md-stroke="1" md-mode="indeterminate" />
            </md-button>
          </div>
        </div>
      </md-card-content>
    </md-card>
  </form>
</template>
<script>
import {addUser, modifyUse, userInfo} from "@/api/user";
import {SucMsg} from "@/utils/message";
import Const, {defaultKey, options} from "@/utils/Const";

export default {
  name: "edit-profile-form",
  props: {
    dataBackgroundColor: {
      type: String,
      default: "",
    },
  },
  data() {
    return {
      loading: false,
      submitIng: false,
      detail: { role: Const.USER_TYPE.USER },
      roleOptions: Object.keys(Const.USER_TYPE).map(key => ({ label: key, value: Const.USER_TYPE[key], key: Const.USER_TYPE[key] }))
    };
  },
  computed: {
    id() {
      return this.$route.query.id
    }
  },
  created() {
    this.getDetail()
  },
  methods: {
    getDetail() {
      this.id && (this.loading = true, userInfo(this.id).then(res => {
        this.detail = res
        this.loading = false
      }).catch(() => (this.loading = false)))
    },
    async doSave() {
      try {
        this.submitIng = true
        if (!this.id) {
          await addUser(this.detail)
          SucMsg('Add User Success!')
        } else {
          await modifyUse(this.detail)
          SucMsg('Modify User Success!')
        }
        this.$router.back()
      } catch (e) {
        this.submitIng = false
      }
    }
  }
};
</script>
<style></style>
