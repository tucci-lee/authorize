<template>
  <el-dialog title="修改邮箱" width="20%" v-model="dialogShow" @close="closeDialog">
    <el-form size="large" v-show="phoneVerifyShow" :model="phoneVerifyBody" :rules="phoneVerifyRules"
             ref="phoneVerifyForm">
      <el-form-item>
        <el-input v-model="currentPhone" disabled></el-input>
      </el-form-item>
      <el-row>
        <el-col :span="14">
          <el-form-item prop="captcha">
            <el-input v-model="phoneVerifyBody.captcha" placeholder="验证码"
                      @keyup.enter="phoneVerify()"></el-input>
          </el-form-item>
        </el-col>
        <el-col :offset="1" :span="9">
          <el-button type="primary" plain :disabled="phoneVerifyBtn.disabled" @click="sendPhoneVerifySmsCaptcha()"
                     class="horizontally">
            {{ phoneVerifyBtn.text }}
          </el-button>
        </el-col>
      </el-row>
      <el-form-item>
        <el-button type="primary" class="horizontally" @click="phoneVerify()">手机验证</el-button>
      </el-form-item>
    </el-form>

    <el-form size="large" v-show="!phoneVerifyShow" :model="updateEmailBody" :rules="updateEmailRules"
             ref="updateEmailForm">
      <el-form-item prop="email">
        <el-input v-model="updateEmailBody.email" placeholder="新邮箱"></el-input>
      </el-form-item>
      <el-row>
        <el-col :span="14">
          <el-form-item prop="captcha">
            <el-input v-model="updateEmailBody.captcha" placeholder="验证码"
                      @keyup.enter="updateEmail()"></el-input>
          </el-form-item>
        </el-col>
        <el-col :offset="1" :span="9">
          <el-button type="primary" plain :disabled="updateEmailBtn.disabled" @click="sendUpdateEmailCaptcha()"
                     class="horizontally">
            {{ updateEmailBtn.text }}
          </el-button>
        </el-col>
      </el-row>
      <el-form-item>
        <el-button type="primary" class="horizontally" @click="updateEmail()">修改</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script>
import {pubGetAccount} from "@/plugins/mitt";
import {smsCaptchaUpdateEmailVerify, emailCaptchaUpdateEmail} from "@/plugins/captcha";
import {phoneVerify, sendAccountSmsCaptcha, sendEmailCaptcha, updateEmail} from "@/plugins/request";

export default {
  name: "UpdateEmail",
  props: {
    show: {
      type: Boolean,
      default: false
    },
    phone: String,
  },
  data() {
    return {
      dialogShow: this.show,
      currentPhone: this.phone,
      phoneVerifyShow: true,
      phoneVerifyBtn: {
        text: '发送验证码',
        disabled: false,
      },
      updateEmailBtn: {
        text: '发送验证码',
        disabled: false,
      },
      phoneVerifyBody: {
        type: smsCaptchaUpdateEmailVerify,
        captcha: null,
      },
      updateEmailBody: {
        token: null,
        email: null,
        captcha: null,
      },
      phoneVerifyRules: {
        captcha: [
          {required: true, message: '验证码不能为空', trigger: 'blur'},
        ],
      },
      updateEmailRules: {
        email: [
          {required: true, message: '邮箱不能为空', trigger: 'blur'},
          {type: 'email', message: '邮箱格式错误', trigger: 'blur'}
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
      this.$refs['phoneVerifyForm'].resetFields();
      this.$refs['updateEmailForm'].resetFields();
    },
    sendPhoneVerifySmsCaptcha() {
      let body = {'type': smsCaptchaUpdateEmailVerify};
      sendAccountSmsCaptcha(body).then(resp => {
        if (!resp.status) {
          return;
        }
        // 发送短信验证码按钮禁用并显示剩余时间
        this.phoneVerifyBtn.disabled = true;
        let num = 60;
        this.phoneVerifyBtn.text = num;
        let interval = setInterval(() => {
          num--;
          this.phoneVerifyBtn.text = num;
          if (num === 0) {
            this.phoneVerifyBtn.text = '重新发送';
            this.phoneVerifyBtn.disabled = false;
            clearInterval(interval);
          }
        }, 1000);
      })
    },
    phoneVerify() {
      this.$refs['phoneVerifyForm'].validate(valid => {
        if (!valid) {
          return;
        }
        phoneVerify(this.phoneVerifyBody).then(resp => {
          if (!resp.status) {
            return;
          }
          this.phoneVerifyShow = false;
          this.updateEmailBody.token = resp.data;
        })
      });
    },
    sendUpdateEmailCaptcha() {
      this.$refs['updateEmailForm'].validateField('email', valid => {
        if (!valid) {
          return;
        }
        let body = {'email': this.updateEmailBody.email, 'type': emailCaptchaUpdateEmail};
        sendEmailCaptcha(body).then(resp => {
          if (!resp.status) {
            return;
          }
          // 发送短信验证码按钮禁用并显示剩余时间
          this.updateEmailBtn.disabled = true;
          let num = 60;
          this.updateEmailBtn.text = num;
          let interval = setInterval(() => {
            num--;
            this.updateEmailBtn.text = num;
            if (num === 0) {
              this.updateEmailBtn.text = '重新发送';
              this.updateEmailBtn.disabled = false;
              clearInterval(interval);
            }
          }, 1000);
        })
      });
    },
    updateEmail() {
      this.$refs['updateEmailForm'].validate(valid => {
        if (!valid) {
          return;
        }
        updateEmail(this.updateEmailBody).then(resp => {
          if (!resp.status) {
            return;
          }
          this.$message.success('修改成功');
          this.phoneVerifyShow = true;
          this.closeDialog();
          // 广播更新用户账号信息
          pubGetAccount();
        })
      });
    },
  },

  watch: {
    show(val) {
      this.dialogShow = val;
    },
    phone(val) {
      this.currentPhone = val;
    }
  }
}
</script>

<style scoped>
.horizontally {
  width: 100%;
}
</style>