<template>
  <div class="left-option">
    <h3>个人中心</h3>
  </div>
  <el-space class="right-option">
    <el-dropdown>
      <span class="pointer">中文</span>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item>中文</el-dropdown-item>
          <el-dropdown-item>English</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>

    <el-dropdown>
      <el-avatar class="pointer"
                 :src="avatar"></el-avatar>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item @click="signout()">退出</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </el-space>
</template>

<script>
import {subAvatar} from "@/plugins/mitt";
import {html} from "@/plugins/urls";
import {getProfile, signout} from "@/plugins/request";

export default {
  name: "Header",
  data() {
    return {
      avatar: null,
    }
  },
  created() {
    this.getProfile();
    this.registerListener();
  },
  methods: {
    getProfile() {
      getProfile().then(resp => {
        if (!resp.status) {
          return;
        }
        this.avatar = resp.data.avatar;
      });
    },
    /**
     * 登出
     */
    signout() {
      signout().then(res => {
        if (res.status) {
          window.location.href = html.signinHtml;
        }
      })
    },
    /**
     * 注册监听器，监听头像的改变
     */
    registerListener() {
      subAvatar((data) => {
        this.avatar = data;
      });
    },
  }
}
</script>

<style scoped>
.left-option {
  height: 100%;
  display: flex;
  align-items: center;
  float: left;
  color: #ff6a00;
}

.right-option {
  height: 100%;
  float: right;
}

.pointer {
  cursor: pointer;
}
</style>