<template>
  <div class="wrapper">
    <div class="main main-raised">
      <md-card v-loading="loading" class="page-part card-page">
        <md-card-header class="detail-header md-layout md-gutter">
          <div class="md-layout-item detail-images">
            <div class="detail-main-image">
              <scale-img :file="zooImg || showImgList[0]" :can-play="true" />
            </div>
            <div class="detail-more-images md-layout md-gutter">
              <div v-for="index in 4" :key="`show_view_${index}`" class="md-layout-item">
                <scale-img v-if="showImgList[index - 1]" :file="showImgList[index - 1]" @choose="chooseShow" />
              </div>
            </div>
          </div>
          <div class="md-layout-item">
            <div class="md-headline">
              {{ detail.title || petType(detail.kind) }}
            </div>
            <div class="detail-info md-layout md-gutter md-alignment-center-center">
              <div class="md-layout-item">
                <div class="md-field">BREED_NAME: {{ detail.petname }}</div>
                <div class="md-field">AGE: {{ detail.age }}</div>
                <div class="md-field">COLOR: {{ detail.color }}</div>
              </div>
              <div class="md-layout-item md-size-20" style="min-width: 170px; white-space: nowrap">price: <font color="red" size="6">£ {{ detail.price }}</font></div>
            </div>
            <div class="detail-change-number">
              <font>quantity：</font><number-picker :min="0" :value="count" @increase="increaseToCart"  @reduce="reduceFromCart" />

              <div v-if="showSocialArea" class="md-layout-item social-area">
                <md-icon :md-src="videoPhoneIcon" @click.native="doVideoPhone" />
                <md-icon v-if="showAddAsFriend" :md-src="addFriendIng ? '' : addFriendIcon" @click.native="doAddFriend">
                  <md-progress-spinner v-if="addFriendIng" :md-diameter="10" :md-stroke="1" md-mode="indeterminate" />
                </md-icon>
              </div>
            </div>
            <div class="btn-group">
              <md-button class="dark-btn" @click="doPay">Pay</md-button>
              <md-button class="gray-btn" @click="increaseToCart">Add to shopping cart </md-button>
            </div>
          </div>
        </md-card-header>
        <md-card-content>
          <pet-list title="Others" :list="otherList" class="other-list" />
        </md-card-content>
      </md-card>

    </div>

    <md-dialog-alert :md-active.sync="isShowVideoPhone" md-title="Tip!" :md-content="`The seller phone is: <strong>${sellerInfo.phone}</strong>， and you can add as friend !`" md-confirm-text="Got it!" />
  </div>
</template>

<script>
import PetList from "@/views/components/PetList";
import NumberPicker from "@/components/NumberPicker";
import {mapActions, mapGetters, mapState} from "vuex";
import {queryPets, viewPet} from "@/api/pet";
import {addFriend, getFriends, userInfo} from "@/api/user";
import Const from "@/utils/Const";
import ScaleImg from "@/views/components/ScaleImg";
import addFriendIcon from '@/assets/svg/add-friend.svg';
import videoPhoneIcon from '@/assets/svg/video-phone.svg';
import {downloadUrl} from "@/api/file";
import {SucMsg} from "@/utils/message";

export default {
  components: { PetList, NumberPicker, ScaleImg },
  name: "index",
  bodyClass: "index-page",
  data() {
    return {
      addFriendIcon,
      videoPhoneIcon,
      detail: {},
      sellerInfo: {},
      zooImg: null,
      loading: false,
      addFriendIng: false,
      isShowVideoPhone: false,
      images: [],
      banners: [],
      petList: [],
      friendList: [],
      video: null,
      ...mapActions({ addPetToCart: 'cart/addPetToCart', delFromCart: 'cart/delFromCart' })
    };
  },
  computed: {
    id() {
      return this.$route.query.id
    },
    count() {
      return (this.cartList.find(item => this.detail.id === item.id) || {}).count || 0
    },
    otherList() {
      return (this.petList || []).filter(item => item.id !== this.detail.id).slice(0, 6)
    },
    showSocialArea() {
      return this.userId && this.userId !== this.detail.userid
    },
    showAddAsFriend() {
      return !(this.friendList || []).some(item => item.id === (this.detail || {}).userid)
    },
    showImgList() {
      return [this.video].concat(this.images).filter(Boolean)
    },
    ...mapGetters(['userId']),
    ...mapState({ cartList: state => state.cart.list })
  },
  watch: {
    id() {
      this.zooImg = undefined
      this.getDetail()
      this.pageData()
    }
  },
  created() {
    this.getDetail(true)
    this.pageData()
    this.refreshFriend()
  },
  activated() {
  },
  beforeDestroy() {
  },
  methods: {
    getDetail(record = false) {
      this.id && (this.loading = true, queryPets({ id: this.id }).then(res => {
        this.loading = false
        this.detail = (res || [])[0] || {}
        this.images = (this.detail.files || []).filter(item => item.filetype === Const.FILE_TYPE.IMG)
        this.banners = (this.detail.files || []).filter(item => item.filetype === Const.FILE_TYPE.BANNER)
        this.video = (this.detail.files || []).find(item => item.filetype === Const.FILE_TYPE.VIDEO)
        userInfo(this.detail.userid).then(res => (this.sellerInfo = res || {}))
        record && viewPet({ userid: this.userId, petid: this.id })
      }).catch(() => (this.loading = false)))
    },
    pageData() {
      this.loading = true
      queryPets().then(res => {
        this.loading = false
        this.petList = res || []
      }).catch(() => (this.loading = false))
    },
    chooseShow(detail) {
      this.zooImg = detail.file
    },
    doVideoPhone() {
      this.isShowVideoPhone = true
    },
    refreshFriend() {
      getFriends(this.userId).then(res => {
        this.addFriendIng = false
        this.friendList = res || []
      })
    },
    doAddFriend() {
      this.addFriendIng = true
      addFriend({userid: this.userId, friendid: this.detail.userid }).then(() => {
        this.refreshFriend()
        SucMsg('Add friend success!')
      }).catch(() => (this.addFriendIng = false))
    },
    doPay() {
      this.increaseToCart()
      this.$router.push({ name: 'cart', params: { selectId: this.detail.id }})
    },
    increaseToCart() {
      this.addPetToCart(this.detail.id)
    },
    reduceFromCart() {
      this.delFromCart(this.detail.id)
    },
    petType(key) {
      return Const.PET_TYPE[key] || ''
    }
  },
};
</script>
<style lang="scss" scoped>
  .detail-header {
    margin: 10px!important;
    box-shadow: none!important;
    background-color: transparent!important;
    .detail-images {
      min-width: 39.1%;
      max-width: 530px;
      flex: 0 1 39.1%;
    }
    img {
      cursor: pointer;
      border-radius: 9px;
    }
    .detail-more-images {
      margin-top: 20px;
      margin-right: 0;
      .md-layout-item {
        padding-right: 0;
      }
    }

    .detail-info {
      margin: 22px 0;
      background-color: #FFF2E8;
      .md-layout-item {
        padding: 4px 12px;
      }
      .md-field {
        font-size: 12px!important;
        margin: 0!important;
        padding: 4px 0!important;
        min-height: 0!important;
        &:after {
          display: none;
        }
      }
    }

    .detail-change-number {
      display: flex;
      align-items: center;
    }

    .social-area {
      padding: 0;
      i {
        float: right;
        cursor: pointer;
        margin-left: 18px;
        font-size: 22px!important;
        transition: all ease .2s;
        &:hover {
          transform: scale(1.2);
        }
      }
    }

    .btn-group {
      margin-top: 34px;
    }
  }

  .other-list ::v-deep {
    .md-card {
      flex: 15% 0;
    }
  }
</style>
