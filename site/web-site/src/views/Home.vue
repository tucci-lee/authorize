<template>
  <el-container class="container">
    <el-header class="header">
      <Header></Header>
    </el-header>
    <el-container>
      <el-aside>
        <Menu></Menu>
      </el-aside>
      <el-main class="main">
        <router-view/>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import Header from "@/components/Header";
import Menu from '@/components/Menu';

export default {
  name: "Home",
  components: {
    Header, Menu
  },
}
</script>

<style scoped>
.container {
  height: 100%;
}

.header {
  box-shadow: 0 2px 4px 0 #888888;
  z-index: 999;
}

.main {
  background-color: #fafafa;
  height: 100%;
}
</style>