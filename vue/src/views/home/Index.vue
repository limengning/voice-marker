<template>
  <div class="container">
    <el-card :body-style="{ padding: 0 }">
      <el-container>
        <el-aside width="600px">
          <banner />
        </el-aside>
        <el-main class="main">
          <el-space direction="vertical">
            <el-button icon="el-icon-folder-add" @click="handleAdd">
              新建项目
            </el-button>
            <el-button icon="el-icon-folder-opened" @click="handleOpen">
              打开项目
            </el-button>
          </el-space>
        </el-main>
      </el-container>
    </el-card>
    <project-manage ref="manage" />
  </div>
</template>

<script>
import Banner from '@/components/Banner'
import ProjectManage from '@/components/ProjectManage'

export default {
  components: {
    Banner,
    ProjectManage
  },
  methods: {
    handleAdd() {
      this.$refs.manage.add()
    },
    handleOpen() {
      this.$refs.manage.open()
    }
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