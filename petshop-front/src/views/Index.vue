<template>
  <div class="wrapper">
    <div class="main main-raised">
      <Search />

      <carousel v-if="banners && banners.length > 0" v-model="activeIndex" adjustableHeight :center-mode="true" :navigation-enabled="true" navigation-prev-label="〈" navigation-next-label="〉" :per-page="1" class="part index-carousel">
        <slide v-for="banner in banners" :key="banner.id" class="slide">
          <scale-img :file="banner.file" @click.native="() => goDetail(banner.id)" />
        </slide>
        <i class="fa fa-angle-left" />
        <i class="fa fa-angle-right" />
      </carousel>

      <pet-list v-loading="loading" title="Daily Recommendation" :list="petList" :large="true" class="pet-list" />
    </div>
  </div>
</template>

<script>
import { Carousel, Slide } from 'vue-carousel'
import Search from "@/views/components/Search";
import PetList from "@/views/components/PetList";
import {queryPets} from "@/api/pet";
import Const from "@/utils/Const";
import ScaleImg from "@/views/components/ScaleImg";

export default {
  components: {
    PetList,
    Search,
    Carousel,
    Slide,
    ScaleImg
  },
  name: "index",
  bodyClass: "index-page",
  props: {
    image: {
      type: String,
      default: require("@/assets/img/vue-mk-header.jpg")
    }
  },
  data() {
    return {
      loading: false,
      petList: [],
      activeIndex: 0,
      firstname: null,
      email: null,
      password: null,
      leafShow: false
    };
  },
  computed: {
    headerStyle() {
      return {
        backgroundImage: `url(${this.image})`
      };
    },
    signupImage() {
      return {
        backgroundImage: `url(${this.signup})`
      };
    },
    banners() {
      return this.petList.filter(item => (item.files || []).some(file => file.filetype === Const.FILE_TYPE.BANNER))
        .map(item => ({ ...item, file: item.files.find(file => file.filetype === Const.FILE_TYPE.BANNER)}))
    }
  },
  created() {
    this.pageData()
  },
  mounted() {
    this.leafActive();
    window.dispatchEvent(new Event('resize'))
    window.addEventListener("resize", this.leafActive);
  },
  beforeDestroy() {
    window.removeEventListener("resize", this.leafActive);
  },
  methods: {
    leafActive() {
      this.leafShow = window.innerWidth >= 768;
    },
    pageData() {
      this.loading = true
      queryPets().then(res => {
        this.loading = false
        this.petList = res || []
      }).catch(() => (this.loading = false))
    },
    goDetail(id) {
      this.$router.push({ path: '/product/detail', query: { id }})
    }
  }
};
</script>
<style lang="scss">
.section-download {
  .md-button + .md-button {
    margin-left: 5px;
  }
}

@media all and (min-width: 991px) {
  .btn-container {
    display: flex;
  }
}
</style>
<style lang="scss" scoped>
  .slide {
    width: 100%;
    max-height: 747px;
    overflow: hidden;
    & img {
      width: 100%;
    }
  }
  .index-carousel ::v-deep {
    .VueCarousel-inner {
      max-height: 747px!important;
      height: 68vh!important;
    }
    @media screen and (max-width: 1200px){
      .VueCarousel-navigation {
        display: none;
      }
    }
    .VueCarousel-navigation {
      position: absolute;
      z-index: 9999;
      width: 100%;
      top: 50%;
      .VueCarousel-navigation-prev, .VueCarousel-navigation-next {
        font-weight: 100;
        font-size: 120px;
      }
      .VueCarousel-navigation-prev {
        left: -40px!important;
        transform: translateY(-50%) translateX(-100%) scaleY(.5);
      }
      .VueCarousel-navigation-next {
        right: -40px!important;
        transform: translateY(-50%) translateX(100%) scaleY(.5);
      }
    }
  }
.pet-list {
  min-height: 20vh;
}
</style>
