<template>
  <el-card style="text-align: left">
    <div>
      <b>登陆密码</b>
      <br/>
      <el-space wrap>
        <span>登陆密码长度必须在6位以上</span>
        <el-link type="primary" @click="openUpdatePassword">修改</el-link>
      </el-space>
    </div>
    <el-divider></el-divider>
    <div>
      <b>绑定手机</b>
      <br/>
      <el-space wrap>
        <span>您已绑定 [<el-link type="primary" @click="openUpdatePhone">{{ accountData.phone }}</el-link>]</span>
        <el-link type="primary" @click="openUpdatePhone">修改</el-link>
      </el-space>
    </div>
    <el-divider></el-divider>
    <div>
      <b>绑定邮箱</b>
      <br/>
      <el-space wrap>
        <span>您已绑定 [<el-link type="primary" @click="openUpdateEmail">{{ accountData.email }}</el-link>]</span>
        <el-link type="primary" @click="openUpdateEmail">修改</el-link>
      </el-space>
    </div>
  </el-card>
  <update-password :show="updatePasswordShow" @close="closeUpdatePassword"></update-password>
  <update-phone :show="updatePhoneShow" :phone="accountData.phone" @close="closeUpdatePhone"></update-phone>
  <update-email :show="updateEmailShow" :phone="accountData.phone" @close="closeUpdateEmail"></update-email>
</template>

<script>
import {subGetAccount} from "@/plugins/mitt";
import {getAccount} from "@/plugins/request";
import UpdatePassword from "@/components/UpdatePassword";
import UpdatePhone from "@/components/UpdatePhone";
import UpdateEmail from "@/components/UpdateEmail";

export default {
  name: "Account",
  components: {
    UpdatePassword, UpdatePhone, UpdateEmail
  },
  data() {
    return {
      updateEmailShow: false, // 修改邮箱显示
      updatePhoneShow: false, // 修改手机显示
      updatePasswordShow: false, // 修改密码显示
      accountData: {  // 显示账号信息
        phone: null,
        email: null,
      },
    }
  },
  created() {
    this.getAccount();
    this.registerListener();
  },
  methods: {
    /**
     * 获取账号信息
     */
    getAccount() {
      getAccount().then(resp => {
        if (!resp.status) {
          return;
        }
        this.accountData = resp.data;
      })
    },
    openUpdatePassword() {
      this.updatePasswordShow = true;
    },
    closeUpdatePassword() {
      this.updatePasswordShow = false;
    },
    openUpdatePhone() {
      this.updatePhoneShow = true;
    },
    closeUpdatePhone() {
      this.updatePhoneShow = false;
    },
    openUpdateEmail() {
      this.updateEmailShow = true;
    },
    closeUpdateEmail() {
      this.updateEmailShow = false;
    },
    /**
     * 注册监听
     * 1. 订阅手机/邮箱修改重新获取账号信息
     */
    registerListener() {
      subGetAccount(() => {
        this.getAccount();
      });
    }
  }
}
</script>

<style scoped>
div > span {
  font-size: 12px;
  color: rgb(153, 153, 153);
}

div > a {
  font-size: 12px;
}
</style>