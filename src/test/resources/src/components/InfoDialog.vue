<script setup>
import {computed, reactive, defineModel, defineProps} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";
import {useRoute, useRouter} from "vue-router";
import {createApi, intoWorkSpaceCheckApi, isValidInvitationCodeApi} from "@/api/workSpace.api.js";

let props = defineProps(['type']);

const title = computed(() => {
  return props.type === 'create' ? '邀请码' : props.type === 'into' ? '空间码' : '未知'
})

const placeholder = computed(() => {
  return props.type === 'create' ? '自定义空间码' : props.type === 'into' ? '请输入空间码' : '未知'
})

let visible = defineModel('visible', {
  type: Boolean,
  default: false
})

const dialog = reactive({
  code: '',
  invitationCode: '',
})
let data = useRoute();
let router = useRouter();

function createWorkSpace() {
  createApi(dialog.invitationCode, dialog.code).then(res => {
    let data = res.data;
    // create id
    // 把code保存到剪贴板
    let code = dialog.code;
    navigator.clipboard.writeText(code).then(() => {
      console.log("复制成功")
    }, () => {
      ElMessageBox.alert('空间码为' + code + ',自动复制失败,请手动复制！', '提示', {
        confirmButtonText: '确定'
      })
    })
    localStorage.setItem("key", data.key)
    localStorage.setItem("hasLogin", "true")
    closeDialog()
    router.push({
      path: '/work-space',
      query: {
        id: data.id
      }
    })
  }).catch(err => {
    ElMessageBox.alert('未知异常', '提示', {
      confirmButtonText: '确定'
    })
  })
}

function intoWorkSpace() {
  // 继续调用接口
  intoWorkSpaceCheckApi(data.query.id, dialog.code).then(res => {
    console.log(res)
    if (res.data.login) {
      localStorage.setItem("key", res.data.key)
      localStorage.setItem("hasLogin", "true")
      closeDialog()
      router.push({
        path: '/work-space',
        query: {
          id:data.query.id
        }
      })
    } else {
      ElMessageBox.alert('空间码不存在', '提示', {
        confirmButtonText: '确定'
      })
    }
  })
}

function createSpace() {
  if (dialog.code.length === 0) {
    ElMessage.warning('空间码不能为空!')
    return
  }
  if (props.type === 'create') {
    if (dialog.invitationCode.length === 0) {
      ElMessage.warning('邀请码不能为空!')
      return
    }
    createWorkSpace()
  } else if (props.type === 'into') {
    intoWorkSpace()
  } else {
    ElMessage.error('未知操作!')
    router.push('/')
  }
}

function createSpaceCode() {
  if (dialog.invitationCode.length === 0) {
    ElMessage.warning('邀请码不能为空!')
    return
  }
  isValidInvitationCodeApi(dialog.invitationCode).then(res => {
    if (res.data) {
      // 随机生成一个6位数的邀请码英文字母+数字
      let code = ''
      for (let i = 0; i < 6; i++) {
        code += Math.random() > 0.5 ? String.fromCharCode(Math.floor(Math.random() * 26) + 65) : Math.floor(Math.random() * 10)
      }
      dialog.code = code
    } else {
      ElMessage.warning('邀请码不正确!')
      dialog.invitationCode = ''
    }
  })
}

function textFilter() {
  dialog.code = dialog.code.replace(/[^a-zA-Z0-9]/g, '')
  dialog.code = dialog.code.toUpperCase()
}

const handleClose = (done) => {
  dialog.code = ''
  dialog.invitationCode = ''
  dialog.type = ''
  done()
}

function closeDialog() {
  console.log('closeDialog')
  visible.value = false
  if (props.type === 'into') {
    router.push('/')
  }
}
</script>

<template>
  <el-dialog v-model="visible" :title="title" width="270" :before-close="handleClose" :close-on-press-escape="false"
             :close-on-click-modal="false">
    <el-form size="large" style="margin-top: 20px">
      <el-form-item label="邀请码" v-if="type === 'create'">
        <el-input v-model="dialog.invitationCode"></el-input>
      </el-form-item>
      <el-form-item v-if="type==='create'">
        <el-button type="primary" style="width: 100%;" plain @click="createSpaceCode">创建空间码(随机)</el-button>
      </el-form-item>
      <el-form-item label="空间码">
        <el-input v-model="dialog.code" @input="textFilter" :placeholder="placeholder"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="closeDialog">取消</el-button>
        <el-button type="primary" @click="createSpace">确定</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<style scoped>

</style>
