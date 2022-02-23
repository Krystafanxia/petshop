<template>
  <div class="content">
    <md-card>
      <md-card-header data-background-color="green">
        <h4 class="title">{{ id ? 'Edit' : 'Add' }} Pet</h4>
      </md-card-header>

      <md-card-content v-loading="loading">
        <el-form ref="form" :model="detail" :rules="rules" class="md-layout">
          <div class="md-layout-item md-small-size-100 md-size-33">
            <el-form-item prop="petname">
              <md-field>
                <label>Pet Name</label>
                <md-input v-model="detail.petname" @change="validField('petname')" />
              </md-field>
            </el-form-item>
          </div>
          <div class="md-layout-item md-small-size-100 md-size-33">
            <el-form-item prop="price">
              <md-field>
                <label>Sale Price</label>
                <md-input v-model="detail.price" type="number" @change="validField('price')" />
              </md-field>
            </el-form-item>
          </div>

          <div class="md-layout-item md-small-size-100 md-size-100">
            <el-form-item prop="story">
              <md-field>
                <label>Pet Story</label>
                <md-textarea v-model="detail.story" md-counter="80" @change="validField('story')" />
              </md-field>
            </el-form-item>
          </div>

          <div class="md-layout-item md-small-size-100 md-size-50">
            <md-field>
              <label>Kind</label>
              <md-select v-model="detail.kind">
                <md-option v-for="item in kindOptions" :key="item.key" :value="item.key">{{ item.label }}</md-option>
              </md-select>
            </md-field>
          </div>
          <div class="md-layout-item md-small-size-100 md-size-50">
            <md-field>
              <label>Status</label>
              <md-select v-model="detail.status">
                <md-option v-for="item in statusOptions" :key="item.key" :value="item.key">{{ item.label }}</md-option>
              </md-select>
            </md-field>
          </div>
          <div class="md-layout-item md-size-100">
            <label class="wild-title">Banners (limit 1)</label>
            <Uploader ref="banner" :limit="1" :params="bannerParam" :file-list="banners" />
          </div>
          <div class="md-layout-item md-size-100">
            <label class="wild-title">Pictures (limit 4)</label>
            <Uploader ref="images" :file-list="images" />
          </div>
          <div class="md-layout-item md-size-100">
            <label class="wild-title">Video (limit 1)</label>
            <Uploader ref="videos" :limit="1" :simple-show="true" :params="videoParam" :file-list="videos" :accept="'video/mp4, video/ogg, video/webm'" />
          </div>

          <div class="md-layout-item md-size-100 text-right">
            <md-button :disabled="loading" class="md-raised md-success" @click="save">
              Save
              <md-progress-spinner v-if="loading" :md-diameter="10" :md-stroke="1" md-mode="indeterminate" />
            </md-button>
          </div>
        </el-form>
      </md-card-content>
    </md-card>
  </div>
</template>
<script>
import Const, {defaultKey, options} from "@/utils/Const";
import {addPet, modifyPet, queryPets, uploadUrl} from "@/api/pet";
import {ErrMsg, SucMsg} from "@/utils/message";
import Uploader from "@/pages/Uploader";

export default {
  name: "editPetForm",
  components: { Uploader },
  props: {
  },
  data() {
    return {
      newId: '',
      loading: false,
      images: [],
      banners: [],
      videos: [],
      detail: {
        petname: '',
        story: '',
        price: '',
        pageviews: '',
        kind: defaultKey(Const.PET_TYPE),
        status: defaultKey(Const.SALE_TYPE)
      },
      rules: {
        petname: [{ required: true, message: 'please input name!', trigger: ['change', 'blur']}],
        story: [{ required: true, message: 'please input story!', trigger: ['change', 'blur']}],
        price: [{ required: true, message: 'please input price!', trigger: ['change', 'blur']}]
      },
      kindOptions: options(Const.PET_TYPE),
      statusOptions: options(Const.SALE_TYPE)
    };
  },
  computed: {
    id() {
      return this.$route.query.id || this.newId
    },
    bannerParam() {
      return { type: Const.FILE_TYPE.BANNER }
    },

    videoParam() {
      return { type: Const.FILE_TYPE.VIDEO }
    }
  },
  watch: {
  },
  created() {
    this.getDetail()
  },
  methods: {
    validField(key) {
      this.$refs.form.validateField(key)
    },
    getDetail() {
      this.id && (this.loading = true, queryPets({ id: this.id }).then(res => {
        this.loading = false
        this.detail = (res || [])[0] || {}
        this.images = (this.detail.files || []).filter(item => item.filetype === Const.FILE_TYPE.IMG)
        this.banners = (this.detail.files || []).filter(item => item.filetype === Const.FILE_TYPE.BANNER)
        this.videos = (this.detail.files || []).filter(item => item.filetype === Const.FILE_TYPE.VIDEO)
      }).catch(() => (this.loading = false)))
    },
    async save() {
      this.loading = true
      try {
        await this.$refs.form.validate()
        // await this.upload()
        const data = { ...this.detail }
        if (this.id) {
          await modifyPet(data)
        } else {
          this.newId = await addPet(data)
        }
        await this.$refs.banner.upload(this.id);
        await this.$refs.images.upload(this.id);
        await this.$refs.videos.upload(this.id);
        this.$router.back()
        SucMsg(this.id ? 'Edit pet info success!' : 'Add new pet success!')
      } catch (e) {
        console.log(e)
      }
      this.loading = false
    },
  }
};
</script>
<style lang="scss" scoped>
  ::v-deep {
    .el-form-item__error {
      top: 71%;
    }
    .wild-title {
      display: block;
      margin: 12px 0 8px 0;
    }
  }
</style>
