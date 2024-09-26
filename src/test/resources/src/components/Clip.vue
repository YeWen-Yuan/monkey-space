<script setup>
import {ref} from "vue";

let data = ref([
  'asdasdasdasdas',
  'adasdasdasdasdas'
]);

let text = ref('')

function addText() {
  if (!text.value) {
    // 获取系统剪贴板
    navigator.clipboard.readText().then(clipText => {
      data.value.unshift(clipText)
    })
  } else {
    data.value.unshift(text.value)
    text.value = ''
  }
}
</script>

<template>
  <div>
    <div>
      <el-input v-model="text" type="textarea" :rows="4" placeholder="添加"></el-input>
      <el-link type="success" @click="addText">添加</el-link>
    </div>
    <div v-for="item in data" class="clip">
      <div>{{ item }}</div>
      <el-link class="margin-top-20" type="success">复制</el-link>
    </div>
  </div>
</template>

<style>

</style>
