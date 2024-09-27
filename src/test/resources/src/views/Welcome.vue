<script setup>
import mk from '@/assets/mk.png'
import InfoDialog from "@/components/InfoDialog.vue";
import {reactive} from "vue";

let dialog = reactive({
  visible: false,
  type: ''
})

function workspace(type) {
  dialog.type = type
  dialog.visible = true
}

localStorage.removeItem('key')
localStorage.removeItem('hasLogin')
</script>

<template>
  <div class="center">
    <div class="man">
      <el-image :src="mk" style="width: 100px;"></el-image>
      <div class="js">
        <div>Welcome Monkey Space!</div>
        <div>这是个本地与远程共享剪贴板和文件的地方</div>
      </div>
      <div class="op">
        <el-button size="large" type="primary" @click="workspace('create')">创建一个工作空间</el-button>
        <!--      <el-button size="large" type="primary" plain @click="workspace('into')">进入一个工作空间</el-button>-->
      </div>
    </div>
  </div>
  <info-dialog v-model:visible="dialog.visible" :type="dialog.type"></info-dialog>
</template>

<style scoped>
.center {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 90vh;
}

.man {
  text-align: center;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.js {
  font-size: 20px;
}

.js div:nth-child(1) {
  background-attachment: scroll;
  background-clip: text;
  background-color: rgba(0, 0, 0, 0);
  background-image: -webkit-linear-gradient(120deg, rgb(189, 52, 254) 30%, rgb(65, 209, 255));
  background-origin: padding-box;
  background-position-x: 0%;
  background-position-y: 0%;
  background-repeat: repeat;
  background-size: auto;
  box-sizing: border-box;
  color: rgba(0, 0, 0, 0);
  display: inline-block;
  font-family: "Punctuation SC", Inter, ui-sans-serif, system-ui, "PingFang SC", "Noto Sans CJK SC", "Noto Sans SC", "Heiti SC", "Microsoft YaHei", DengXian, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", "Noto Color Emoji";
  font-optical-sizing: auto;
  font-size: 56px;
  font-synthesis-small-caps: none;
  font-synthesis-style: auto;
  font-synthesis-weight: none;
  font-weight: 700;
  height: auto;
  letter-spacing: -0.4px;
  line-height: 64px;
  overflow-wrap: break-word;
  text-rendering: optimizelegibility;
  text-size-adjust: 100%;
  text-wrap: wrap;
  white-space-collapse: preserve;
  width: auto;
  -webkit-font-smoothing: antialiased;
  -webkit-text-fill-color: rgba(0, 0, 0, 0);
}

.js div:nth-child(2) {
  font-size: 25px;
  margin-top: 20px;
}

.op {
  text-align: center;
  margin-top: 50px;
}

</style>
