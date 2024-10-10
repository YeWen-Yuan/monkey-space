<template>
  <el-upload
      v-model:file-list="fileList"
      :limit="1"
      ref="upload"
      :auto-upload="false"
      :on-exceed="handleExceed">
    <template #trigger>
     <el-button type="primary">选择文件</el-button>
    </template>
    <el-button class="ml-3" type="success" v-if="fileList.length>0" @click="uploadFile">
      上传
    </el-button>
  </el-upload>

  <div class="clip" v-for="file in fileLists">
    <div>{{ file.fileName }}</div>
    <el-space class="margin-top-20">
      <el-link type="success" @click="handleDownload(file)">下载</el-link>
      <el-link type="danger" @click="handleRemove(file)">删除</el-link>
    </el-space>
  </div>
</template>
<script lang="ts" setup>
import {ref} from 'vue'
import {fileUploadApi,deleteFileApi,downloadFileApi} from '@/api/file.api.js'
import {defineEmits} from 'vue'

import type {UploadProps, UploadUserFile} from 'element-plus'

let fileLists = defineModel('fileList', {
  type: Array,
  default: () => []
})

const fileList = ref<UploadUserFile[]>([])

let emits = defineEmits(['remove']);

const handleRemove = (file) => {
  console.log(file)
  deleteFileApi(file).then(res=>{
    emits('remove')
  })
}

import {genFileId} from 'element-plus'
import type {UploadInstance, UploadRawFile} from 'element-plus'

let showUploadBtn = ref(false)


const upload = ref<UploadInstance>()
const handleExceed: UploadProps['onExceed'] = (files, uploadFiles) => {
  upload.value!.clearFiles()
  const file = files[0] as UploadRawFile
  file.uid = genFileId()
  upload.value!.handleStart(file)
  showUploadBtn.value = true
}

function handleDownload(file) {
  console.log(file)
  downloadFileApi(file)
}

function uploadFile() {
  let valueElement = fileList.value[0];
  console.log(valueElement);
  console.log(upload.value)
  fileUploadApi(valueElement.raw).then(response => {
    console.log(response);
    emits('remove')
  })
}
</script>

<style scoped>
.upload-demo {
  margin-bottom: 30px;
}
</style>
