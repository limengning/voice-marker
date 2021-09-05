<template>
  <div class="container">
    <el-card :body-style="{ padding: 0 }">
      <el-container>
        <el-aside width="600px">
          <banner />
        </el-aside>
        <el-main class="main">
          <el-space direction="vertical">
            <el-button icon="el-icon-folder-add">新建项目</el-button>
            <el-button icon="el-icon-folder-opened">打开项目</el-button>
          </el-space>
        </el-main>
      </el-container>
    </el-card>
  </div>
</template>

<script>
import Banner from '@/components/Banner'

export default {
  components: {
    Banner
  }
}
</script>

<style>
.container {
  display: flex;
  justify-content: center;
  padding-top: 160px;
}

.main {
  display: flex;
  height: 400px;
  justify-content: center;
  flex-direction: column;
  background: var(--el-background-color-base);
}
</style>