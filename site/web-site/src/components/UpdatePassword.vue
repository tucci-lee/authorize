<template>
  <el-dialog title="修改密码" width="20%" v-model="dialogShow" @close="closeDialog">
    <el-form size="large" :model="updatePasswordBody" :rules="updatePasswordRules" ref="updatePasswordForm">
      <el-row>
        <el-col :span="14">
          <el-form-item prop="captcha">
            <el-input v-model="updatePasswordBody.captcha" placeholder="验证码"
                      @keyup.enter="updatePassword()"></el-input>
          </el-form-item>
        </el-col>
        <el-col :offset="1" :span="9">
          <el-button type="primary" plain :disabled="smsBtn.disabled" @click="sendSmsCaptcha()"
                     class="horizontally">
            {{ smsBtn.text }}
          </el-button>
        </el-col>
      </el-row>
      <el-form-item prop="password">
        <el-input v-model="updatePasswordBody.password" placeholder="新密码" type="password"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" class="horizontally" @click="updatePassword()">修改</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script>
import {smsCaptchaUpdatePassword} from "@/plugins/captcha";
import {sendAccountSmsCaptcha, updatePassword} from "@/plugins/request";

export default {
  name: "UpdatePassword",
  props: {
    show: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      dialogShow: this.show,
      smsBtn: {
        text: '发送验证码',
        disabled: false,
      },
      updatePasswordBody: {
        password: null,
        captcha: null,
      },
      updatePasswordRules: {
        password: [
          {required: true, message: '密码不能为空', trigger: 'blur'},
          {min: 6, message: '密码长度必须在6位以上', trigger: 'blur'},
        ],
        captcha: [
          {required: true, message: '验证码不能为空', trigger: 'blur'},
        ],
      },
    }
  },
  methods: {
    closeDialog() {
      this.dialogShow = false;
      this.$refs['updatePasswordForm'].resetFields();
    },
    updatePassword() {
      this.$refs['updatePasswordForm'].validate(valid => {
        if (!valid) {
          return;
        }
        updatePassword(this.updatePasswordBody).then(resp => {
          if (!resp.status) {
            return;
          }
          this.$message.success("修改成功");
          this.closeUpdatePassword();
        })
      })
    },
    sendSmsCaptcha() {
      let body = {'type': smsCaptchaUpdatePassword};
      sendAccountSmsCaptcha(body).then(resp => {
        if (!resp.status) {
          return;
        }
        // 发送短信验证码按钮禁用并显示剩余时间
        this.smsBtn.disabled = true;
        let num = 60;
        this.smsBtn.text = num;
        let interval = setInterval(() => {
          num--;
          this.smsBtn.text = num;
          if (num === 0) {
            this.smsBtn.text = '重新发送';
            this.smsBtn.disabled = false;
            clearInterval(interval);
          }
        }, 1000);
      })
    }
  },
  watch: {
    show(val) {
      this.dialogShow = val;
    },
  }
}
</script>

<style scoped>
.horizontally {
  width: 100%;
}
</style>