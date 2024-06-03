<template>
  <div class="index">
    <div class="left">
      <h2>快速开发系统</h2>
      <el-menu
          router="true"
          active-text-color="#ffd04b"
          background-color="#545c64"
          default-active="2"
          text-color="#fff"
      >
        <el-sub-menu index="1">
          <template #title>
            <el-icon><Setting /></el-icon>
            <span>系统管理</span>
          </template>
          <el-menu-item index="/index/user">
            <el-icon><User /></el-icon>
            用户管理
          </el-menu-item>
          <el-menu-item index="/index/role">
            <el-icon><User /></el-icon>
            角色管理
          </el-menu-item>
        </el-sub-menu>

        <el-sub-menu index="2">
          <template #title>
            <el-icon><Monitor /></el-icon>
            <span>日志管理</span>
          </template>
          <el-menu-item index="/index/backendLog">
            <el-icon><Monitor /></el-icon>
            后台日志
          </el-menu-item>
          <el-menu-item index="/index/operateLog">
            <el-icon><Monitor /></el-icon>
          接口日志
          </el-menu-item>
        </el-sub-menu>
      </el-menu>
    </div>

    <div class="right">
      <div class="top">
        <el-menu
            :ellipsis="false"
            mode="horizontal"
            :default-active="activeIndex2"
            background-color="#337ecc"
            text-color="#000000"
            active-text-color="#ffd04b"
        >
          <el-menu-item index="1">首页</el-menu-item>
          <el-menu-item index="2">消息</el-menu-item>
          <el-sub-menu index="3">
            <template #title>个人中心</template>
            <el-menu-item index="3-1">个人信息</el-menu-item>
            <el-menu-item index="3-2">修改密码</el-menu-item>
            <el-menu-item index="3-3">退出登录</el-menu-item>
          </el-sub-menu>
        </el-menu>
      </div>
      <div class="content">
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import {House,User,Monitor,Setting} from '@element-plus/icons-vue'
</script>

<style scoped lang="scss ">
.index{
  width: 100vw;
  height: 100vh;
  display: flex;
  .left{
    width: 300px;
    background-color: green;
    color: black;
    .el-memu{
      border-right: none;
    }
    h2{
      font-size: 30px;
      text-align: center;
      height: 60px;
      line-height: 60px;
    }
  }
  .right{
    flex: 1;
    display: flex;
    flex-direction: column;
    .top{
      height: 60px;
      background-color: darkgray;
      display: flex;
      justify-content: flex-end;

    }
    .content{
      flex: 1;
    }

  }
}
</style>