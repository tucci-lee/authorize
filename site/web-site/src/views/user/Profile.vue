<template>
  <el-card>
    <el-row>
      <el-col :span="5">
        <el-avatar :size="130" :src="profileData.avatar" @click="openUpdateAvatar()"></el-avatar>
      </el-col>
      <el-col :span="10">
        <el-descriptions title="基本信息" :column="2">
          <el-descriptions-item label="账号ID:">{{ profileData.uid }}</el-descriptions-item>
          <el-descriptions-item label="创建时间:">{{ profileData.createTime }}</el-descriptions-item>
        </el-descriptions>
      </el-col>
    </el-row>
  </el-card>

  <el-dialog title="修改头像" width="20%" v-model="updateAvatarShow" @close="closeUpdateAvatar">
    <el-upload
        :auto-upload="false"
        accept="image/*"
        ref="avatarUpload"
        :http-request="updateAvatar"
        :limit="1"
        list-type="picture"
        :on-exceed="avatarExceed">
      <el-button size="small" type="primary">选取照片</el-button>
    </el-upload>
    <template #footer>
      <div>
        <el-button type="primary" @click="submitUpdateAvatar()">保存</el-button>
      </div>
    </template>
  </el-dialog>

  <el-card style="margin-top: 16px;">
    <el-row>
      <el-col :span="6">
        <el-form ref="updateProfileFrom" :model="profileData" :rules="updateProfileRules" label-width="auto">
          <el-form-item label="昵称" prop="nickname">
            <el-input v-model="profileData.nickname"></el-input>
          </el-form-item>
          <el-form-item label="生日">
            <el-date-picker v-model="profileData.birthday" type="date" value-format="YYYY-MM-DD"
                            placeholder="选择日期"/>
          </el-form-item>
          <el-form-item label="性别" prop="gender">
            <el-radio v-model="profileData.gender" :label="1">男</el-radio>
            <el-radio v-model="profileData.gender" :label="0">女</el-radio>
          </el-form-item>
          <el-form-item label="地区" prop="region">
            <el-cascader
                v-model="profileData.region"
                :props="regionProps"
            ></el-cascader>
          </el-form-item>
          <el-form-item label="个人介绍">
            <el-input type="textarea" v-model="profileData.introduction"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="updateProfile()">保存</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </el-card>
</template>

<script>
import {pubAvatar} from "@/plugins/mitt";
import {getProfile, getRegion, updateAvatar, updateProfile} from "@/plugins/request";

export default {
  name: "Profile",
  data() {
    return {
      profileData: {
        uid: null,
        avatar: null,
        username: null,
        nickname: null,
        birthday: null,
        province: null,
        introduction: null,
        createTime: null,
        region: null, // 地区联动帮定值
      },
      updateAvatarShow: false,  // 修改头像的选择框是否显示
      regionProps: {   // 地区联动卡配置
        lazy: true,
        lazyLoad(node, resolve) {
          let parentId = node.level === 0 ? 0 : node.value;
          getRegion(parentId).then(res => {
            let nodes = Array.from(res.data).map(item => ({
              value: item.regionId,
              label: item.name,
              leaf: node.level === 1,
            }));
            resolve(nodes)
          })
        }
      },
      updateProfileRules: {
        nickname: [
          {required: true, message: '昵称不能为空', trigger: 'blur'},
          {max: 20, message: '昵称最多20个字符', trigger: 'blur'},
        ],
        gender: [
          {required: true, message: '性别不能为空', trigger: 'blur'},
        ],
        region: [
          {
            type: 'array',
            required: true,
            len: 2,
            fields: {
              0: {required: true, message: '地区不能为空', trigger: 'blur'},
              1: {required: true, message: '地区不能为空', trigger: 'blur'}
            },
          }
        ],
      }
    }
  },
  created() {
    this.getProfile();
  },
  methods: {
    getProfile() {
      getProfile().then(resp => {
        if (!resp.status) {
          return;
        }
        let profileData = resp.data;
        this.profileData = profileData;
        this.profileData.createTime = this.$moment(parseInt(profileData.createTime)).format("YYYY-MM-DD HH:mm:ss");
        this.profileData.region = [profileData.province, profileData.city];
      });
    },
    /**
     * 头衔选择超出显示，覆盖上一个头像
     */
    avatarExceed(files) {
      this.$refs['avatarUpload'].clearFiles();
      this.$refs['avatarUpload'].handleStart(files[0])
    },
    /**
     * 自定义修改头像方法
     * @param options
     */
    updateAvatar(options) {
      let data = new FormData();
      data.append("file", options.file);
      updateAvatar(data).then(resp => {
        if (!resp.status) {
          return;
        }
        this.$message.success("修改成功");
        // 关闭上传组件
        this.closeUpdateAvatar();
        // 更新头像信息
        this.profileData.avatar = resp.data;
        // 发布头像信息
        pubAvatar(resp.data.avatar);
      })
    },
    openUpdateAvatar() {
      this.updateAvatarShow = true;
    },
    closeUpdateAvatar() {
      this.updateAvatarShow = false;
      // 清空文件列表
      this.$refs['avatarUpload'].clearFiles();
    },
    /**
     * 提交头像修改
     */
    submitUpdateAvatar() {
      this.$refs['avatarUpload'].submit()
    },
    updateProfile() {
      this.$refs['updateProfileFrom'].validate(valid => {
        if (!valid) {
          return false;
        }
        let body = {
          nickname: this.profileData.nickname,
          birthday: this.profileData.birthday,
          gender: this.profileData.gender,
          province: this.profileData.region[0],
          city: this.profileData.region[1],
          introduction: this.profileData.introduction,
        }
        updateProfile(body).then(resp => {
          if (resp.status) {
            this.$message.success("修改成功");
          }
        });
      })
    },
  }
}
</script>

<style scoped>
.avatarDiv {
  border-radius: 50%;
  width: 130px;
  height: 130px;
}
</style>