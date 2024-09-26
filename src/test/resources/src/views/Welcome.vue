<script setup>
import mk from '@/assets/mk.png'
import InfoDialog from "@/components/InfoDialog.vue";
import {reactive} from "vue";

let dialog = reactive({
  visible: false,
  type: ''
})

function workspace(type) {
  console.log('workspace')
  dialog.type = type
  if (type === 'create') {
    dialog.visible = true
  } else if (type === 'into') {
    dialog.visible = true
  }
}
</script>

<template>
  <div class="man">
    <div class="js">
      <el-image :src="mk" style="width: 100px;"></el-image>
      <div>Welcome Monkey Space!</div>
      <div>这是个本地与远程共享剪贴板和文件的地方</div>
    </div>
    <div class="op">
      <el-button size="large" type="primary"  @click="workspace('create')">创建一个工作空间</el-button>
      <!--      <el-button size="large" type="primary" plain @click="workspace('into')">进入一个工作空间</el-button>-->
    </div>
  </div>
  <info-dialog v-model:visible="dialog.visible" :type="dialog.type"></info-dialog>
</template>

<style scoped>
.js {
  text-align: center;
  margin-top: 300px;
  font-size: 20px;
}

.js div:nth-child(2) {
  font-size: 56px;
  font-weight: bold;
  margin-bottom: 20px;
  background-clip: text;
  background:-webkit-linear-gradient(120deg, #bd34fe 30%, #41d1ff);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

.op {
  text-align: center;
  margin-top: 50px;
}

</style>
