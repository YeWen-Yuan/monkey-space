<script setup>

import FileList from "@/components/FileList.vue";
import Clip from "@/components/Clip.vue";
import {ElMessage, ElMessageBox} from "element-plus";
import {checkLoginKeyApi, delApi} from "@/api/workSpace.api.js";
import InfoDialog from "@/components/InfoDialog.vue";
import {showDialog, urlCheck} from '@/assets/into.hock.js'
import {ref} from "vue";
import {useRoute, useRouter} from "vue-router";

let visible = ref(null);
let route = useRoute()
let router = useRouter();

intoCheck()

function intoCheck() {
  // 判断URL是否合法
  let access_check = urlCheck();
  if (access_check) {
    let access_showDialog = showDialog()
    // 判断是否显示弹窗
    console.log('access_showDialog', access_showDialog)
    if (access_showDialog) {
      visible.value = true;
    } else {
      checkLoginKeyApi(route.query.id, localStorage.getItem("key")).then(res => {
        if (res.data) {
          visible.value = false;
        } else {
          ElMessage.error('链接已失效！')
          router.push('/')
        }
      }).catch(err => {
        ElMessageBox.alert('未知异常', '提示', {
          confirmButtonText: '确定',
          callback: action => {
            router.push('/')
          }
        })
      })
    }
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
    delApi(data).then(res => {
      if (res.data.code === 200) {
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
