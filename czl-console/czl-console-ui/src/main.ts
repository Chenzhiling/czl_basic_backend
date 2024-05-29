import { createApp } from 'vue'
import './style.css'
import App from './App.vue'

//导入路由器
import router from "./router";

//导入element-plus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import {createPinia} from "pinia";

createApp(App)
    .use(router)
    //使用Pinia
    .use(createPinia())
    .use(ElementPlus)
    .mount('#app')
