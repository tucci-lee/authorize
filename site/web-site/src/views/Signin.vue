<template>
  <div class="box">
    <div class="formBox">
      <el-tabs>
        <el-tab-pane label="密码登录">
          <el-form size="large" :model="accountSigninBody" :rules="accountSigninRules" ref="accountSigninForm">
            <el-form-item prop="username">
              <el-input v-model="accountSigninBody.username" placeholder="手机号"></el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input v-model="accountSigninBody.password" placeholder="密码" type="password"
                        @keyup.enter="accountSignin()"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" class="horizontally" @click="accountSignin()">登录</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="验证码登录">
          <el-form size="large" :model="smsSigninBody" :rules="smsSigninRules" ref="smsSigninForm">
            <el-form-item prop="phone">
              <el-input v-model="smsSigninBody.phone" placeholder="手机号"></el-input>
            </el-form-item>
            <el-row>
              <el-col :span="14">
                <el-form-item prop="captcha">
                  <el-input v-model="smsSigninBody.captcha" placeholder="验证码"
                            @keyup.enter="smsSignin()"></el-input>
                </el-form-item>
              </el-col>
              <el-col :offset="1" :span="9">
                <el-button type="primary" plain :disabled="smsBtn.disabled" @click="sendSmsCaptcha()"
                           class="horizontally">
                  {{ smsBtn.text }}
                </el-button>
              </el-col>
            </el-row>
            <el-form-item>
              <el-button type="primary" class="horizontally" @click="smsSignin()">登录</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
import {query, html} from "@/plugins/urls";
import {sendSmsCaptcha, smsSignin, accountSignin, verify} from "@/plugins/request";
import {smsCaptchaSignin} from "@/plugins/captcha";

export default {
  name: "Signin",
  data() {
    return {
      redirectUri: '',
      smsBtn: {
        text: '发送验证码',
        disabled: false,
      },
      accountSigninBody: {
        username: '',
        password: '',
      },
      smsSigninBody: {
        phone: '',
        captcha: '',
      },
      accountSigninRules: {
        username: [
          {required: true, message: '手机号不能为空', trigger: 'blur'},
          {pattern: '^1[3-9][0-9]{9}$', message: '手机号格式错误', trigger: 'blur'}
        ],
        password: [{required: true, message: '密码不能为空', trigger: 'blur'}],
      },
      smsSigninRules: {
        phone: [
          {required: true, message: '手机号不能为空', trigger: 'blur'},
          {pattern: '^1[3-9][0-9]{9}$', message: '手机号格式错误', trigger: 'blur'}
        ],
        captcha: [{required: true, message: '验证码不能为空', trigger: 'blur'}],
      }
    }
  },
  created() {
    this.redirectUri = this.$route.query[query.redirectUri];
    if (!/^(http|https):\/\/(.+\.)?2cci.cn/.test(this.redirectUri)) {
      this.redirectUri = html.profileHtml;
    }
    this.verify();
  },
  methods: {
    verify() {
      verify().then(resp => {
        if (resp.status) {
          window.location.href = this.redirectUri;
        }
      });
    },
    /**
     * 发送短信验证码
     */
    sendSmsCaptcha() {
      this.$refs['smsSigninForm'].validateField('phone', valid => {
        if (!valid) {
          return;
        }
        let body = {'phone': this.smsSigninBody.phone, 'type': smsCaptchaSignin};
        sendSmsCaptcha(body).then(resp => {
          if (!resp.status) {
            return;
          }
          // 发送短信验证码按钮禁用并显示剩余时间
          this.smsBtn.disabled = true;
          let num = 60;
          let interval = setInterval(() => {
            if (num === 0) {
              this.smsBtn.text = '重新发送';
              this.smsBtn.disabled = false;
              clearInterval(interval);
              return;
            }
            this.smsBtn.text = num;
            num--;
          }, 1000);
        })
      })
    },
    /**
     * 短信登录
     */
    smsSignin() {
      this.$refs['smsSigninForm'].validate(valid => {
        if (!valid) {
          return;
        }
        smsSignin(this.smsSigninBody).then(resp => {
          if (resp.status) {
            window.location.href = this.redirectUri;
          }
        })
      })
    },
    /**
     * 账号密码登录
     */
    accountSignin() {
      // 校验
      this.$refs['accountSigninForm'].validate(valid => {
        if (!valid) {
          return false;
        }
        // 登陆请求
        accountSignin(this.accountSigninBody).then(resp => {
          if (resp.status) {
            window.location.href = this.redirectUri;
          }
        })
      });
    }
  }
}
</script>

<style scoped>
.box {
  height: 70%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.formBox {
  width: 350px;
  border-radius: 10px;
  padding: 30px;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 20px 2px rgba(0, 0, 0, 0.1);
}

.horizontally {
  width: 100%;
}
</style>