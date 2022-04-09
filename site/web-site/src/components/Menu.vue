<template>
  <el-menu router style="border-right:0">
    <el-menu-item :index="profileHtml">
      <span>基本信息</span>
    </el-menu-item>
    <el-menu-item :index="accountHtml">
      <span>安全设置</span>
    </el-menu-item>
  </el-menu>
</template>

<script>
import {html} from "@/plugins/urls";

export default {
  name: "Menu",
  data() {
    return {
      profileHtml: html.profileHtml,
      accountHtml: html.accountHtml,
    }
  }
}
</script>

<style scoped>

</style>