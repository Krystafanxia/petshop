<template>
  <div class="wrapper">
    <div class="main main-raised">
      <nav-tabs-card no-label class="search-tab simple-tab">
        <template slot="content">
          <md-card-content v-loading="loading">
            <el-form ref="form" :model="detail" :rules="rules" class="md-layout">
              <div class="md-layout-item md-size-100">
                <el-form-item class="md-layout-item md-size-100">
                  <label class="wild-title">Avatar (limit 1)</label>
                  <Uploader ref="avatar" :limit="1" :params="avatarUploadParam" :file-list="avatars" />
                </el-form-item>
              </div>
              <el-form-item prop="firstname" class="md-layout-item md-size-33">
                <md-field>
                  <label>First Name</label>
                  <md-input v-model="detail.firstname" @change="validField('firstname')" />
                </md-field>
              </el-form-item>
              <el-form-item prop="lastname" class="md-layout-item md-size-33">
                <md-field>
                  <label>Last Name</label>
                  <md-input v-model="detail.lastname" @change="validField('lastname')" />
                </md-field>
              </el-form-item>
              <el-form-item prop="age" class="md-layout-item md-small-size-100 md-size-33">
                <md-field>
                  <label>Age</label>
                  <md-input v-model="detail.age" type="number" @change="validField('age')" />
                </md-field>
              </el-form-item>
              <el-form-item prop="phone" class="md-layout-item md-small-size-100 md-size-33">
                <md-field>
                  <label>Phone</label>
                  <md-input v-model="detail.phone" type="number" @change="validField('phone')" />
                </md-field>
              </el-form-item>
              <el-form-item prop="country" class="md-layout-item md-small-size-100 md-size-33">
                <md-field>
                  <label>Country</label>
                  <md-input v-model="detail.country" @change="validField('country')" />
                </md-field>
              </el-form-item>
              <el-form-item prop="city" class="md-layout-item md-small-size-100 md-size-33">
                <md-field>
                  <label>City</label>
                  <md-input v-model="detail.city" @change="validField('city')" />
                </md-field>
              </el-form-item>
              <el-form-item prop="email" class="md-layout-item md-small-size-100 md-size-33">
                <md-field>
                  <label>Email</label>
                  <md-input v-model="detail.email" @change="validField('email')" />
                </md-field>
              </el-form-item>
              <el-form-item prop="address" class="md-layout-item md-small-size-100 md-size-66">
                <md-field>
                  <label>Address Line</label>
                  <md-input v-model="detail.address" @change="validField('address')" />
                </md-field>
              </el-form-item>
            </el-form>

            <md-button class="dark-btn" @click="doSave">
              Save Info
              <md-progress-spinner v-if="submitIng" :md-diameter="10" :md-stroke="1" md-mode="indeterminate" />
            </md-button>
          </md-card-content>
        </template>
      </nav-tabs-card>
    </div>
  </div>
</template>

<script>
import { NavTabsCard } from "@/components";
import {getFriends, modifyUse, userInfo} from '@/api/user';
import {mapGetters, mapMutations} from "vuex";
import Uploader from "@/pages/Uploader";
import {SucMsg} from "@/utils/message";
import Const from "@/utils/Const";
import {getAvatar, getPetFiles} from "@/api/file";
export default {
  components: {
    NavTabsCard, Uploader
  },
  name: "index",
  bodyClass: "index-page",
  data() {
    return {
      detail: {},
      avatars: [],
      friendList: [],
      rules: {},
      loading: false,
      submitIng: false,
      avatarUploadParam: { type: Const.FILE_TYPE.AVATAR },
      ...mapMutations({ changeAvatarFile: 'user/CHANGE_AVATAR' })
    };
  },
  computed: {
    ...mapGetters(['userId'])
  },
  created() {
    this.getInfo()
  },
  methods: {
    validField(key) {
      this.$refs.form.validateField(key)
    },
    async getInfo() {
      try {
        this.loading = true
        await userInfo(this.userId).then(res => (this.detail = res || {}))
        await getAvatar(this.userId).then(res => this.avatars = res)
        await getFriends(this.userId).then(res => (this.friendList = res || []))
      } catch (e) {}
      this.loading = false
    },
    async doSave() {
      try {
        this.submitIng = true
        await this.$refs.avatar.upload(this.userId);
        await modifyUse(this.detail)
        const { response, filename } = this.$refs.avatar.uploadFiles[0]
        const newAvatar = ((response || {}).data || filename || '').split('/').pop()
        newAvatar && this.changeAvatarFile(newAvatar)
        SucMsg('Modify Success!')
      } catch (e) {}
      this.submitIng = false
    }
  }
};
</script>
<style lang="scss" scoped>
.search-tab {
  min-height: calc(100vh - 128px);
}
.user-detail {
  margin: 0!important;
  border: none!important;
  box-shadow: none!important;
  background-color: transparent!important;
  ::v-deep {
    .md-tabs-content {
      margin: 10px 44px;
    }
    .md-subhead {
      color: #666666;
      opacity: 1;
      margin: 10px 0;
    }
    .md-avatar {
      width: 110px;
      height: 110px;
      border-radius: 50%;
    }
    .md-layout-item {
      text-align: left;
    }
  }
}
.user-ext-info {
  display: flex;
  flex-wrap: wrap;
  margin-top: 22px;
  div {
    text-align: left;
    flex: 0 1 33%;
  }
}
.friend-list-title {
  text-align: left;
  padding-top: 22px;
}
.friend-list {
  display: flex;
  flex-wrap: wrap;
  .friend-item {
    margin: 12px;
    flex: 0 1 110px;
  }
}
</style>
