import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import moment from "moment"
import {ElMessage} from "element-plus";

const app = createApp(App);
app.use(router);
app.config.globalProperties.$moment = moment;
app.config.globalProperties.$message = ElMessage;
app.mount('#app');
