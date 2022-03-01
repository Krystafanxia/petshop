<template>
  <div class="short-img" :style="style" @click="onClick">
    <video-player v-if="showVideo" class="video-player vjs-custom-skin" ref="videoPlayer" :playsinline="true" :options="playerOptions" />
  </div>
</template>
<script>
import Const from "@/utils/Const";
import {downloadUrl} from "@/api/file";
import {videoPlayer} from "vue-video-player";
import 'video.js/dist/video-js.css'

export default {
  components: {videoPlayer},
  props: {
    canPlay: Boolean,
    size: Number,
    rate: {
      type: Number,
      default: 1.36
    },
    file: {
      type: Object,
      default: () => {}
    },
    url: String,
    detail: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      playerOptions: null,
      width: '100%',
      height: 100
    }
  },
  computed: {
    style() {
      return `background-image: url(${this.shortImg}); height: ${this.computedSize(this.height)}; width: ${this.computedSize(this.width)};`
    },
    fileName() {
      return this.url || (this.file || {}).filename || (((this.detail || {}).files || []).find(item => item.filetype === Const.FILE_TYPE.IMG) || {}).filename
    },
    fileExtName() {
      return (this.fileName || '').split('.').pop() || ''
    },
    isVideo() {
      return Object.keys(Const.VIDEO_TYPE).includes(this.fileExtName);
    },
    showVideo() {
      return this.canPlay && this.isVideo
    },
    shortImg() {
      if (!this.canPlay && this.isVideo) {
        return '/static/img/default-video.jpeg'
      }
      if (!this.fileName) {
        return '/static/img/default-pet.jpeg'
      }
      return `${downloadUrl}?fileName=${this.fileName}`
    }
  },
  watch: {
    fileName(name) {
      if (name && this.isVideo) {
        this.playerOptions = { ...Const.VIDEO_OPTIONS, sources: [{ type: Const.VIDEO_TYPE[this.fileExtName], src: `${downloadUrl}?fileName=${name}` }]}
      }
    }
  },
  mounted() {
    this.refreshSize()
    setTimeout(this.refreshSize, 400)
    window.addEventListener('resize', this.refreshSize)
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.refreshSize)
  },
  methods: {
    refreshSize() {
      if (this.size) {
        this.width = this.size
        this.height = this.size / this.rate
      } else if (this.$el && this.$el.getBoundingClientRect) {
        const { width } = this.$el.getBoundingClientRect()
        this.height = width / this.rate
      }
    },
    onClick() {
      this.$emit('choose', { url: this.shortImg, detail: this.detail, file: this.file })
    },
    computedSize(val) {
      return ((val || '') + '').indexOf('%') > 0 ? val : `${val}px`
    }
  }
}
</script>
<style lang="scss" scoped>
.short-img {
  width: 100%;
  overflow: hidden;
  cursor: pointer;
  border-radius: 12px;
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;
}
::v-deep {
  .vjs-custom-skin {
    height: 100%;
    .video-js {
      height: 100%;
    }
  }
  .vjs-big-play-button {
    top: 50% !important;
    left: 50% !important;
    transform: translate(-50%, -50%) !important;
  }
}
</style>
