<script setup>

import FileList from "@/components/FileList.vue";
import Clip from "@/components/Clip.vue";
import {ElMessage, ElMessageBox} from "element-plus";
import {delApi, intoWorkSpaceCheckApi} from "@/api/workSpace.api.js";
import InfoDialog from "@/components/InfoDialog.vue";
import {urlCheck} from '@/assets/into.hock.js'
import {ref} from "vue";
import {useRoute, useRouter} from "vue-router";


let visible = ref(null);
let route = useRoute()
let router = useRouter();

let clipboardHistory = ref([])
let fileList = ref([])

intoCheck()

function intoCheck() {
  // 判断URL是否合法
  let route = useRoute();
  let data = route?.query.id;
  if (!!data) {
    intoWorkSpaceCheckApi(route.query.id, route.query.code).then(res => {
      if (res.code === 200) {
        clipboardHistory.value = res.data.data.clipboardHistory.map(item => item.text)
        fileList.value = res.data.data.fileList
        visible.value = false
      } else if (res.code === 5008) {
        visible.value = false
        ElMessage.error(res.message)
        router.push('/')
      } else if (res.code === 5006) {
        visible.value = false
        ElMessage.error(res.message)
        router.push('/')
      } else {
        visible.value = true
      }
    })
  } else {
    router.push('/')
  }
}

function delWorkSpace() {
  ElMessageBox.confirm('确定删除当前工作空间吗?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    delApi().then(res => {
      if (res.code === 200) {
        ElMessage.success('删除成功')
        router.push('/')
      } else {
        ElMessage.error('删除失败')
      }
    })
  }).catch(() => {
    ElMessage.info('已取消删除')
  })
}
</script>

<template>
  <div v-if="!visible">
    <div class="affix-container">
      <el-affix target=".affix-container" :offset="10">
<!--        <el-button type="primary">设置</el-button>-->
        <el-button type="danger" @click="delWorkSpace">删除当前工作空间</el-button>
      </el-affix>
    </div>
    <div class="man">
      <el-row :gutter="224">
        <el-col :span="12">
          <h3>剪贴板</h3>
          <clip v-model:clipboardHistory="clipboardHistory"></clip>
        </el-col>
        <el-col :span="12">
          <h3>文件交换列表</h3>
          <file-list v-model:fileList="fileList" @remove="intoCheck"></file-list>
        </el-col>
      </el-row>
    </div>
  </div>
  <info-dialog v-model:visible="visible" :type="'into'"></info-dialog>
</template>

<style scoped>
.man {
  padding: 20px 40px;
  max-width: 1280px;
  margin: 0 auto;
}

.affix-container {
  text-align: right;
  border-radius: 4px;
}
</style>
