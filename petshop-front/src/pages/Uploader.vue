<template>
  <div :class="disable ? 'disabled-upload' : ''">
    <el-upload ref="upload" :action="uploadUrl" :disabled="disable" :accept="accept" :file-list="fileList" :limit="limit" :with-credentials="true" list-type="picture-card" :auto-upload="false" :on-remove="onRemove" :on-change="onChange" :on-success="onSuccess" :on-error="onError">
      <i slot="default" class="el-icon-plus" />
      <div slot="file" slot-scope="{file}">
        <div v-if="simpleShow" style="margin: 6px;">{{ file.name || file.filename }}</div>
        <img v-if="!simpleShow && file.status !== 'fail'" class="el-upload-list__item-thumbnail" :src="file.filename ? `${downloadUrl}?fileName=${file.filename}` : file.url" alt="">
        <span class="el-upload-list__item-actions">
            <span v-if="!simpleShow" class="el-upload-list__item-preview" @click="handlePictureCardPreview(file)">
              <i class="el-icon-zoom-in"></i>
            </span>
            <span v-if="!disabled" class="el-upload-list__item-delete" @click="handleDownload(file)">
              <i class="el-icon-download"></i>
            </span>
            <span v-if="!disabled" class="el-upload-list__item-delete" @click="handleRemove(file)">
              <i class="el-icon-delete"></i>
            </span>
          </span>
      </div>
    </el-upload>
    <el-dialog :visible.sync="dialogVisible" :append-to-body="true">
      <img width="100%" :src="dialogImageUrl" alt="">
    </el-dialog>
  </div>
</template>
<script>
import {delPetFile, downloadUrl, uploadUrl} from "@/api/file";
import Const from "@/utils/Const";
import {handError} from "@/utils/request";

export default {
  props: {
    simpleShow: {
      type: Boolean,
      default: false
    },
    accept: {
      type: String,
      default: ''
    },
    limit: {
      type: Number,
      default: 4
    },
    fileList: {
      type: Array,
      default: () => []
    },
    params: {
      type: Object,
      default: () => {}
    },
  },
  data() {
    return {
      id: '',
      delFileList: [],
      downloadUrl,
      disabled: false,
      dialogImageUrl: '',
      dialogVisible: false,
      uploadFiles: []
    }
  },
  computed: {
    uploadUrl() {
      const { id, type } = this.params || {}
      return `${uploadUrl}?id=${id || this.id}&type=${type || Const.FILE_TYPE.IMG}`
    },
    disable() {
      return this.limit && (this.uploadFiles || []).length >= this.limit
    }
  },
  watch: {
    fileList() {
      this.uploadFiles = this.fileList
    }
  },
  methods: {
    upload(id = null) {
      this.id = id
      return new Promise((resolve, reject) => {
        this.$nextTick(async () => {
          try {
            this.uploadIng = true
            await this.clearRemoveList()
            this.$refs.upload.submit()
            if (this.$refs.upload.uploadFiles.some(item => item.status === 'ready')) {
              this.uploadRsolve = resolve
              this.uploadReject = reject
            } else {
              resolve(this.$refs.upload.uploadFiles)
            }
          } catch (e) {
            reject(e)
          }
        })
      })
    },
    onRemove() {
      this.refreshUploadFiles()
    },
    onChange() {
      this.refreshUploadFiles()
    },
    onSuccess(res, file) {
      this.uploadIng = false
      const { code, message, data } = res || {}
      if (code === Const.STATUS.SUCCESS_CODE) {
        file.fileId = data;
      } else {
        file && (file.status = 'ready')
        return this.onError(message)
      }
      if (this.uploadRsolve && this.$refs.upload && this.$refs.upload.uploadFiles.every(file => file.status === 'success')) {
        this.uploadRsolve(null);
      }

      this.refreshUploadFiles()
    },
    onError(err) {
      handError(typeof err === 'string' ? 'file upload fail！' : err);
      this.uploadIng = false
      this.uploadReject && this.uploadReject();

      this.refreshUploadFiles()
    },
    handleRemove(file) {
      if (file.fileid) {
        this.delFileList.push(file)
      }
      this.$refs.upload.handleRemove(file);
    },
    refreshUploadFiles() {
      this.uploadFiles = this.$refs.upload.uploadFiles
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.filename ? `${downloadUrl}?fileName=${file.filename}` : file.url;
      this.dialogVisible = true;
    },
    handleDownload(file) {
      window.open(file.filename ? `${downloadUrl}?fileName=${file.filename}` : file.url, '_blank')
    },
    clearRemoveList() {
      return new Promise(async (resolve) => {
        while(this.delFileList.length > 0) {
          const { fileid } = this.delFileList.pop()
          fileid && await delPetFile(fileid)
        }
        resolve()
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.disabled-upload {
  ::v-deep .el-upload {
    display: none;
  }
}
</style>
