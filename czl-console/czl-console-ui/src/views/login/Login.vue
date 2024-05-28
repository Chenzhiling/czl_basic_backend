<template>
  <div class="login">
    <div class="box">
      <h2>快速开发系统</h2>
      <el-form
          size="small"
          ref="loginFormRef"
          style="max-width: 400px"
          :model="loginFormData"
          status-icon
          :rules="rules"
          label-width="40px"
      >
        <el-form-item label="账号" prop="userName">
          <el-input v-model="loginFormData.userName"></el-input>
        </el-form-item>

        <el-form-item label="密码" prop="password">
          <el-input v-model="loginFormData.password" type="password"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm(loginFormRef)">
            登录
          </el-button>
          <el-button type="primary" @click="resetForm(loginFormRef)">
            重置
          </el-button>
        </el-form-item>

      </el-form>
    </div>
  </div>
</template>

<script setup lang="ts">
//导入组合式api
import { reactive, ref } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'


//定义对象绑定表单
const loginFormRef = ref<FormInstance>()

//登录表单数据
const loginFormData = reactive({
  userName: '',
  password: '',
  code: ''
})

//验证用户名
const validateUserName = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('请输入用户名'))
  } else {
    callback()
  }
}
//验证密码
const validatePassword = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('请输入密码'))
  } else {
    callback()
  }
}

//输入验证对象
const rules = reactive<FormRules<typeof loginFormData>>({
  userName: [{ validator: validateUserName, trigger: 'blur' }],
  password: [{ validator: validatePassword, trigger: 'blur' }],
})
//提交按钮
const submitForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.validate((valid) => {
    if (valid) {
      console.log('submit!')
    } else {
      console.log('error submit!')
    }
  })
}
//取消按钮
const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}
</script>

<style scoped lang="scss">
//login表签的css
.login {
  width: 100vw;
  height: 100vh;
  //换颜色
  background: #134857;
  //弹性布局
  display: flex;
  //横轴对其方式
  justify-content: center;
  //纵轴对其方式
  align-items: center;
  .box{
    width: 400px;
    //height: 200px;
    border: 1px solid #ffff;
    padding: 20px;
    //把字体变白色
    :deep(.el-form-item__label){
      color: #ffff;
    }
    h2{
      color: #ffff;
      font-size: 30px;
      text-align: center;
      margin-bottom: 20px
    }
  }
}
</style>