<script setup>

import FileList from "@/components/FileList.vue";
import Clip from "@/components/Clip.vue";
import {useRoute, useRouter} from "vue-router";
import {ElMessage, ElMessageBox} from "element-plus";
import {delApi, isLoginApi} from "@/api/workSpace.api.js";
import InfoDialog from "@/components/InfoDialog.vue";
import {ref} from "vue";

let visible = ref(true);

let route = useRoute();
let data = route.query.id;
if (!data) {
  useRouter().push('/')
} else {
  checkCanBeInfoThisPage()
}


function checkCanBeInfoThisPage() {
  isLoginApi().then(res => {
    if (res.data.code === 200 && res.data) {
      // is login goto
      visible.value = false;
    } else {
      // not login
      visible.value = true
    }
  })
}

function delWorkSpace() {
  ElMessageBox.confirm('确定删除当前工作空间吗?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    delApi(data).then(res => {
      if (res.data.code === 200) {
        ElMessage.success('删除成功')
        useRouter().push('/')
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
        <el-button type="primary">设置</el-button>
        <el-button type="danger" @click="delWorkSpace">删除当前工作空间</el-button>
      </el-affix>
    </div>
    <div class="man">
      <el-row :gutter="224">
        <el-col :span="12">
          <h3>剪贴板</h3>
          <clip></clip>
        </el-col>
        <el-col :span="12">
          <h3>文件交换列表</h3>
          <file-list></file-list>
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
