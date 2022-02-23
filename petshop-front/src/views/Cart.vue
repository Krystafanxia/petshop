<template>
  <div class="wrapper">
    <div class="main main-raised">
      <nav-tabs-card no-label class="simple-tab">
        <template slot="content">
          <md-tabs class="md-primary" md-card md-alignment="left">
            <md-tab md-label="Check Out">
              <div v-if="!productList || productList.length === 0" class="info-content">
                <md-empty-state :md-size="20" md-description="Nothing in There" />
              </div>
              <div v-else class="info-content">
                <!--table-->
                <md-table v-model="productList" :md-selected-value="defaultSelected" @md-selected="choose">
                  <md-table-row slot="md-table-row" md-selectable="multiple" slot-scope="{ item }">
                    <md-table-cell md-label="Product Name" class="avatar pet-table-cell-header">
                      <number-picker :min="0" :value="item.count" @increase="() => increaseToCart(item.id)"  @reduce="() => reduceFromCart(item.id)" />
                      <img :src="item.pic || '/static/img/dog.jpeg'" alt="">
                      {{ item.petname }}
                    </md-table-cell>
                    <md-table-cell md-label="Delivery">{{ item.delivery }}</md-table-cell>
                    <md-table-cell md-label="Address Line ">{{ item.address  }}</md-table-cell>
                    <md-table-cell md-label="Price">{{ item.price  }}</md-table-cell>
                  </md-table-row>
                </md-table>

                <div v-if="selected && selected.length > 0" class="checkout-btn-group">
                  <div class="md-display-1">Total :£{{ selected.reduce((pre, next) => (pre || 0) + ((next || {}).price * ((next || {}).count || 1) || 0), 0) }}</div>
                  <div class="btn-group">
                    <md-button class="gray-btn" @click="continueShopping">Continue to shopping</md-button>
                    <md-button class="dark-btn" @click="showPay">Pay</md-button>
                  </div>
                </div>
              </div>

              <!--md-card-actions>
                <md-button class="dark-btn bottom-btn">shopping cart</md-button>
              </md-card-actions!-->
            </md-tab>
          </md-tabs>

          <transition appear enter-active-class="show-enter" leave-active-class="hidden-leave">
            <div v-if="isShowPay" v-click-outside="hidePay" class="pay-confirm">
              <el-scrollbar>
                <div class="md-layout">
                  <div class="md-layout-item">
                    <div class="md-headline">Customer Information</div>
                    <div class="md-field">First Name: {{ userInfo.firstname }}</div>
                    <div class="md-field">Last Name: {{ userInfo.lastname }}</div>
                    <div class="md-field">E-mail: {{ userInfo.email }}</div>
                    <div class="md-field">Address Line: {{ userInfo.address }},</div>
                    <!--div class="md-field">Zip: 02139</div!-->
                    <div class="md-field">Phone Number: {{ userInfo.phone }}</div>
                    <div class="md-field">City: {{ userInfo.city }}</div>
                    <div class="md-field">Country: {{ userInfo.country }}</div>
                    <!--div class="md-field">State/Province/Region: London</div!-->
                    <!--div class="md-field">Post Code: E2Q 2QD</div!-->

                    <md-button class="dark-btn" @click="doPay">Pay</md-button>
                  </div>
                  <div class="md-layout-item">
                    <div class="md-headline">Payment Details</div>
                    <div class="md-field">Card Number: 1230 6596 1235</div>
                    <div class="md-field">Card Name:   Bob</div>
                    <div class="md-field">Visa: CITI</div>
                    <div class="md-field">Valid Date: 2021-12-10</div>
                    <div class="md-field">CVC Code: 1230</div>
                  </div>
                </div>
              </el-scrollbar>
            </div>
          </transition>
        </template>
      </nav-tabs-card>

    </div>
  </div>
</template>

<script>
import { NavTabsCard } from "@/components";
import {mapActions, mapState} from "vuex";
import {getMyAccount, payForPet} from "@/api/pay";
import {SucMsg} from "@/utils/message";
import {queryPets} from "@/api/pet";
import NumberPicker from "@/components/NumberPicker";

export default {
  components: { NavTabsCard, NumberPicker },
  name: "index",
  bodyClass: "index-page",
  data() {
    return {
      myAccountInfo: {},
      isShowPay: false,
      cache: {},
      selected: [],
      ...mapActions({ addPetToCart: 'cart/addPetToCart', delFromCart: 'cart/delFromCart', cleanFromCart: 'cart/cleanFromCart' })
      // productList: [{ name: 'test', price: 1111, pic: '', Delivery: 'SHUNFENG', AddressLine: '77 Massachusetts Avenue,' }, { name: 'test', price: 1111, pic: '', Delivery: 'SHUNFENG', AddressLine: '77 Massachusetts Avenue,' }]
    };
  },
  computed: {
    productList() {
      return this.petList.map(item => ({ ...item, ...(this.cache[item.id] || {}) }))
    },
    defaultSelected() {
      return [this.productList.find(item => item.id === this.$route.params.selectId)].filter(Boolean)
    },
    ...mapState({ userInfo: state => state.user.info, petList: state => state.cart.list })
  },
  watch: {
    petList: {
      handler(v) {
        (v || []).forEach(item => !this.cache[item.id] && queryPets({ id: item.id }).then(res => (res || []).length > 0 && this.$set(this.cache, `${item.id}`, res[0])))
      },
      immediate: true
    }
  },
  created() {
  },
  beforeDestroy() {
  },
  methods: {
    showPay() {
      this.isShowPay = true
      this.getMyAccount()
    },
    continueShopping() {
      this.$router.push({ path: '/search' })
    },
    hidePay() {
      this.isShowPay = false
    },
    choose(list) {
      this.selected = list
    },
    getMyAccount() {
      getMyAccount().then(res => (this.myAccountInfo = res))
    },
    doPay() {
      this.loading = true
      payForPet({ payList: this.selected }).then(res => {
        this.loading = false
        SucMsg('Pay success！')
        this.selected.forEach(item => this.cleanFromCart(item.id))
        this.hidePay()
      }).catch(() => (this.loading = false))
    },
    increaseToCart(id) {
      this.addPetToCart(id)
    },
    reduceFromCart(id) {
      this.delFromCart(id)
    }
  }
};
</script>
<style lang="scss" scoped>
.main {
  .simple-tab {
    min-height: calc(100vh - 128px);
  }
  .pet-table-cell-header ::v-deep .md-table-cell-container {
    display: flex;
    align-items: center;
    .number-picker {
      height: 24px;
      margin-right: 12px;
      .number-btn {
        width: 24px;
        height: 24px;
        line-height: 24px;
      }
      .md-input {
        color: gray;
        width: 40px;
      }
    }
  }
  .info-content {
    min-height: calc(100vh - 400px);
  }
  .checkout-btn-group {
    float: right;
    margin-top: 42px;
  }
  .pay-confirm {
    top: 0;
    right: 0;
    z-index: 999;
    height: 100%;
    width: 52%;
    padding: 20px;
    border-radius: 20px;
    position: absolute;
    background-color: white;
    box-shadow: 0 2px 2px 0 rgb(0 0 0 / 14%), 0 3px 1px -2px rgb(0 0 0 / 20%), 0 1px 5px 0 rgb(0 0 0 / 12%);
    ::v-deep {
      .el-scrollbar {
        height: 100%;
        overflow-y: auto;
        overflow-x: hidden;
      }
      .el-scrollbar__wrap {
        overflow-x: hidden;
      }
      .el-scrollbar__bar.is-horizontal {
        display: none;
      }
    }
    .md-field {
      margin: 0!important;
      &:after {
        display: none;
      }
    }
  }
  ::v-deep {

    tr.md-selected {
      border-left: 4px solid #77A7FF;
    }

    .md-table-content {
      border: 1px solid #EEF6FC;
    }

    .md-table-cell {
      text-align: left;

      img {
        max-height: 60px;
        width: auto;
        margin-right: 10px;
      }
    }

    .md-table.md-theme-default .md-table-row.md-selected {
      background-color: #F8FDFF !important;
    }

    .md-table-cell-selection .md-table-head-container {
      padding: 10px 0;
    }

    .md-table-cell-selection + th .md-table-head-label {
      padding-left: 8px;
    }

    .md-table-content thead {
      display: table-row-group !important;
    }

    .md-checkbox.md-theme-default .md-checkbox-container {
      border-color: #C8D6F0 !important;
    }

    .md-checkbox.md-theme-default.md-checked .md-checkbox-container {
      background-color: #6084FD !important;
      border-color: darken(#6084FD, 6%) !important;
    }

    .md-checkbox.md-checked .md-checkbox-container:after {
      border-color: white !important;
    }
  }
}
.show-enter {
  animation: show .5s ease 0s forwards;
}
.hidden-leave {
  animation: hide .5s ease 0s forwards;
}
@keyframes show {
  from {
    transform: translateX(600px);
  }
  to {
    transform: translateX(0);
  }
}
@keyframes hide {
  from {
    transform: translateX(0);
  }
  to {
    transform: translateX(600px);
  }
}
</style>
