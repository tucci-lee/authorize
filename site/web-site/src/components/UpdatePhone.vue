<template>
  <el-dialog title="修改手机" width="20%" v-model="dialogShow" @close="closeDialog">
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

    <el-form size="large" v-show="!phoneVerifyShow" :model="updatePhoneBody" :rules="updatePhoneRules"
             ref="updatePhoneForm">
      <el-form-item prop="phone">
        <el-input v-model="updatePhoneBody.phone" placeholder="新手机"></el-input>
      </el-form-item>
      <el-row>
        <el-col :span="14">
          <el-form-item prop="captcha">
            <el-input v-model="updatePhoneBody.captcha" placeholder="验证码"
                      @keyup.enter="updatePhone()"></el-input>
          </el-form-item>
        </el-col>
        <el-col :offset="1" :span="9">
          <el-button type="primary" plain :disabled="updatePhoneBtn.disabled" @click="sendUpdatePhoneSmsCaptcha()"
                     class="horizontally">
            {{ updatePhoneBtn.text }}
          </el-button>
        </el-col>
      </el-row>
      <el-form-item>
        <el-button type="primary" class="horizontally" @click="updatePhone()">修改</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script>
import {pubGetAccount} from "@/plugins/mitt";
import {smsCaptchaUpdatePhone, smsCaptchaUpdatePhoneVerify,} from "@/plugins/captcha";
import {phoneVerify, sendAccountSmsCaptcha, sendSmsCaptcha, updatePhone} from "@/plugins/request";

export default {
  name: "UpdatePhone",
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
      updatePhoneBtn: {
        text: '发送验证码',
        disabled: false,
      },
      phoneVerifyBody: {
        type: smsCaptchaUpdatePhoneVerify,
        captcha: null,
      },
      updatePhoneBody: {
        token: null,
        phone: null,
        captcha: null,
      },
      phoneVerifyRules: {
        captcha: [
          {required: true, message: '验证码不能为空', trigger: 'blur'},
        ],
      },
      updatePhoneRules: {
        phone: [
          {required: true, message: '手机不能为空', trigger: 'blur'},
          {pattern: '^1[3-9][0-9]{9}$', message: '手机号格式错误', trigger: 'blur'}
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
      this.$refs['updatePhoneForm'].resetFields();
    },
    sendPhoneVerifySmsCaptcha() {
      let body = {'type': smsCaptchaUpdatePhoneVerify};
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
          this.updatePhoneBody.token = resp.data;
        })
      });
    },
    sendUpdatePhoneSmsCaptcha() {
      this.$refs['updatePhoneForm'].validateField('phone', valid => {
        if (!valid) {
          return;
        }
        let body = {'phone': this.updatePhoneBody.phone, 'type': smsCaptchaUpdatePhone};
        sendSmsCaptcha(body).then(resp => {
          if (!resp.status) {
            return;
          }
          // 发送短信验证码按钮禁用并显示剩余时间
          this.updatePhoneBtn.disabled = true;
          let num = 60;
          this.updatePhoneBtn.text = num;
          let interval = setInterval(() => {
            num--;
            this.updatePhoneBtn.text = num;
            if (num === 0) {
              this.updatePhoneBtn.text = '重新发送';
              this.updatePhoneBtn.disabled = false;
              clearInterval(interval);
            }
          }, 1000);
        })
      });
    },
    updatePhone() {
      this.$refs['updatePhoneForm'].validate(valid => {
        if (!valid) {
          return;
        }
        updatePhone(this.updatePhoneBody).then(resp => {
          if (!resp.status) {
            return;
          }
          this.$message.success('修改成功');
          this.phoneVerifyShow = true;
          this.closeDialog();
          // 发布更新用户账号信息
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