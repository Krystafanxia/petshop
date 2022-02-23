<template>
  <div class="wrapper">
    <div class="main main-raised">
      <nav-tabs-card v-loading="loading" no-label class="search-tab simple-tab">
        <template slot="content">
          <md-tabs class="md-primary user-detail" md-alignment="left">
            <md-tab md-label="Customer Information">
              <div class="md-layout">
                <div class="md-layout-item md-size-10" style="min-width: 140px;">
                  <md-avatar><scale-img :file="avatarFile" :rate="1" /></md-avatar>
                </div>

                <div class="md-layout-item">
                  <div class="md-subhead" style="margin-top: 0;">System account: <font color="#939393">{{ detail.name }}</font> &nbsp;&nbsp;&nbsp;&nbsp; Age： <font color="#939393">{{ detail.age }}</font> </div>
                  <div class="md-subhead">First Name：   <font color="#939393">{{ detail.firstname }}</font></div>
                  <div class="md-subhead">Last Name：   <font color="#939393">{{ detail.lastname }}</font></div>
                  <md-button class="md-info md-sm" :md-ripple="false" href="#/user/detail/edit">Edit My Info</md-button>
                  <!--<div class="md-subhead">System account：  {{ detail.firstname }}</div>-->
                </div>
              </div>

              <div class="user-ext-info">
                <div class="md-subhead">E-mail：  <font color="#939393">{{ detail.email }}</font></div>
                <div class="md-subhead">Phone Number： <font color="#939393">{{ detail.phone }}</font></div>
                <!--div class="md-subhead">State/Province/Region: {{ detail.country }}</div!-->
                <div class="md-subhead">Address Line 1: <font color="#939393">{{ detail.address }}</font></div>
                <div class="md-subhead">City: <font color="#939393">{{ detail.city }}</font></div>
                <!--div class="md-subhead">Post Code: E2Q 2QD</div>
                <div class="md-subhead">Zip: 02139</div!-->
                <div class="md-subhead">Country: <font color="#939393">{{ detail.country }}</font></div>
              </div>

              <div class="md-body-1 friend-list-title">Friends List</div>
              <div v-if="friendList && friendList.length > 0" class="friend-list">
                <div v-for="item in friendList" :key="item.id" class="friend-item">
                  <md-speed-dial md-direction="bottom">
                    <md-avatar><img src="@/assets/img/faces/avatar.jpg" alt=""></md-avatar>
                    <div class="md-subhead">{{ item.firstname }}</div>
                    <md-speed-dial-content>
                      <md-button class="md-icon-button md-round md-just-icon md-info md-video-phone">
                        <md-icon :md-src="videoPhoneIcon" @click.native="() => doVideoPhone(item)" />
                      </md-button>

                      <md-button class="md-icon-button md-round md-just-icon md-danger">
                        <md-icon @click.native="() => showDeleteDialog(item)">delete</md-icon>
                      </md-button>
                    </md-speed-dial-content>
                  </md-speed-dial>
                </div>
              </div>
              <div v-else class="friend-list">
                <md-empty-state :md-size="20" md-description="You need to add some friend first" />
              </div>
            </md-tab>
          </md-tabs>
        </template>
      </nav-tabs-card>
    </div>

    <md-dialog-alert :md-active.sync="isShowVideoPhone" md-title="Tip!" :md-content="`Phone is: <strong>${selectFriend.phone}</strong> !`" md-confirm-text="Got it!" />

    <md-dialog-confirm :md-active.sync="isShowDeleteConfirm" md-title="Sure delete selected friend ?"
      md-content="After deleted you can add in product <strong>detail page</strong> again."
      md-confirm-text="Confirm" class="confirm-del-friend" @md-confirm="doDelFriend" />
  </div>
</template>

<script>
import {NavTabsCard} from "@/components";
import {delFriend, getFriends, userInfo} from '@/api/user';
import {mapGetters, mapState} from "vuex";
import ScaleImg from "@/views/components/ScaleImg";
import videoPhoneIcon from '@/assets/svg/video-phone.svg';
import {SucMsg} from "@/utils/message";

export default {
  components: {
    NavTabsCard, ScaleImg
  },
  name: "index",
  bodyClass: "index-page",
  data() {
    return {
      loading: false,
      isShowVideoPhone: false,
      isShowDeleteConfirm: false,
      selectFriend: {},
      videoPhoneIcon,
      detail: {},
      friendList: []
    };
  },
  computed: {
    ...mapGetters(['userId']),
    ...mapState({ avatarFile: state => state.user.info.avatarFile })
  },
  created() {
    this.getInfo()
  },
  methods: {
    doVideoPhone(item) {
      this.selectFriend = item
      this.isShowVideoPhone = true
    },
    showDeleteDialog(item) {
      this.selectFriend = item
      this.isShowDeleteConfirm = true
    },
    async doDelFriend() {
      try {
        this.loading = true
        this.isShowDeleteConfirm = false
        await this.$confirm('Sure delete select friend ?')
        await delFriend(this.selectFriend.id)
        SucMsg('Delete success!')
        this.refreshFriend()
      } catch (e) {}
      this.loading = false
    },
    getInfo() {
      userInfo(this.userId).then(res => (this.detail = res || {}))
      this.refreshFriend()
    },
    refreshFriend() {
      getFriends(this.userId).then(res => (this.friendList = res || []))
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
  ::v-deep {
    .md-video-phone {
      path {
        fill: white;
      }
    }
    .md-speed-dial-content {
      position: absolute;
    }
  }
}
</style>
