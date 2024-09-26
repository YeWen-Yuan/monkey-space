<script setup>
import {computed, reactive, defineModel, defineProps} from "vue";
import {ElMessageBox} from "element-plus";
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


function createSpace() {
  if (props.type === "create" && dialog.invitationCode.length === 0) {
    ElMessageBox.alert('邀请码不能为空', '提示', {
      confirmButtonText: '确定'
    })
    return
  }
  if (dialog.code.length === 0) {
    ElMessageBox.alert('空间码不能为空', '提示', {
      confirmButtonText: '确定'
    })
    return
  }
  if (props.type === 'create') {
    createApi(dialog.invitationCode, dialog.code).then(res => {
      let data = res.data;
      // create id
      // 把code保存到剪贴板
      let code = dialog.code;
      navigator.clipboard.writeText(code).then(() => {
        console.log("复制成功")
      }, () => {
        ElMessageBox.alert('空间码为' + code + ',自动复制失败', '提示', {
          confirmButtonText: '确定'
        })
      })
      closeDialog()
      router.push({
        path: '/work-space',
        query: {
          id: data
        }
      })
    })
  } else if (props.type === 'into') {
    // 继续调用接口
    intoWorkSpaceCheckApi(data.query.id, dialog.code).then(res => {
      if (res.data) {
        closeDialog()
      } else {
        ElMessageBox.alert('空间码不存在', '提示', {
          confirmButtonText: '确定'
        })
      }
    })
  } else {
    closeDialog()
  }
}

function createSpaceCode() {
  isValidInvitationCodeApi(dialog.invitationCode).then(res => {
    console.log(res.data)
    if (res.data) {
      // 随机生成一个6位数的邀请码英文字母+数字
      let code = ''
      for (let i = 0; i < 6; i++) {
        code += Math.random() > 0.5 ? String.fromCharCode(Math.floor(Math.random() * 26) + 65) : Math.floor(Math.random() * 10)
      }
      dialog.code = code
    } else {
      ElMessageBox.alert('邀请码不正确', '提示', {
        confirmButtonText: '确定'
      })
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
  <el-dialog v-model="visible" :title="title" width="260" :before-close="handleClose">
    <el-form size="large" style="margin-top: 20px">
      <el-form-item label="邀请码" v-if="type === 'create'">
        <el-input v-model="dialog.invitationCode"></el-input>
      </el-form-item>
      <el-form-item label="空间码" v-if="type==='create'">
        <el-button type="primary" plain @click="createSpaceCode">创建空间码(随机)</el-button>
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
