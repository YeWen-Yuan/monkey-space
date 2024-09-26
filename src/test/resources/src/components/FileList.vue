<template>
  <el-upload
      v-model:file-list="fileList"
      class="upload-demo"
      :on-preview="handlePreview"
      :on-remove="handleRemove"
      :before-remove="beforeRemove"
      :limit="3"
      :on-exceed="handleExceed">
    <el-button type="primary">上传</el-button>
    <template #tip>
      <div class="el-upload__tip">
        jpg/png files with a size less than 500KB.
      </div>
    </template>
    <template #file="scope">
      <div class="clip">
        <div>{{ scope.file.name }}</div>
        <el-space class="margin-top-20">
          <el-link type="success" @click="scope.handleRemove(scope.file)">下载</el-link>
          <el-link type="danger" @click="scope.handleRemove(scope.file)">删除</el-link>
        </el-space>
      </div>
    </template>
  </el-upload>
</template>
<script lang="ts" setup>
import {ref} from 'vue'
import {ElMessage, ElMessageBox} from 'element-plus'

import type {UploadProps, UploadUserFile} from 'element-plus'

const fileList = ref<UploadUserFile[]>([
  {
    name: 'element-plus-logo.svg',
    url: 'https://element-plus.org/images/element-plus-logo.svg',
  },
  {
    name: 'element-plus-logo2.svg',
    url: 'https://element-plus.org/images/element-plus-logo.svg',
  },
])

const handleRemove: UploadProps['onRemove'] = (file, uploadFiles) => {
  console.log(file, uploadFiles)
}

const handlePreview: UploadProps['onPreview'] = (uploadFile) => {
  console.log(uploadFile)
}

const handleExceed: UploadProps['onExceed'] = (files, uploadFiles) => {
  ElMessage.warning(
      `The limit is 3, you selected ${files.length} files this time, add up to ${
          files.length + uploadFiles.length
      } totally`
  )
}

const beforeRemove: UploadProps['beforeRemove'] = (uploadFile, uploadFiles) => {
  return ElMessageBox.confirm(
      `Cancel the transfer of ${uploadFile.name} ?`
  ).then(
      () => true,
      () => false
  )
}
</script>

<style scoped>
.upload-demo {
  margin-bottom: 30px;
}
</style>
