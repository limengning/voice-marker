<template>
  <div class="title">
    <label>项目 - {{ this.project.name }}</label>
    <span>
      <el-dropdown @command="handleSettings">
        <el-icon class="setting-icon"><setting /></el-icon>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item :command="'handleTemplate'">
              设置标注项
            </el-dropdown-item>
            <el-dropdown-item :command="'handleEdit'">
              编辑项目
            </el-dropdown-item>
            <el-dropdown-item :command="'handleExport'">
              导出项目
            </el-dropdown-item>
            <el-dropdown-item divided :command="'handleAdd'">
              新建项目
            </el-dropdown-item>
            <el-dropdown-item :command="'handleOpen'">
              打开项目
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </span>
  </div>
  <mark-form-editor ref="formEditor" />
  <project-manage ref="manage" />
</template>

<script>
import { mapGetters, mapMutations } from 'vuex'
import { Setting } from '@element-plus/icons'
import MarkFormEditor from '@/components/MarkFormEditor.vue'
import ProjectManage from '@/components/ProjectManage'
import { exportProject } from '@/api/project'

export default {
  components: {
    MarkFormEditor,
    ProjectManage,
    Setting
  },
  computed: {
    ...mapGetters('workplace', ['project'])
  },
  methods: {
    ...mapMutations('workplace', ['setMarkFields']),
    handleSettings(command) {
      this[command]()
    },
    handleTemplate() {
      this.$refs.formEditor.open(this.project).then((fields) => {
        this.setMarkFields(fields)
      })
    },
    handleEdit() {
      this.$refs.manage.edit(this.project)
    },
    handleAdd() {
      this.$refs.manage.add()
    },
    handleOpen() {
      this.$refs.manage.open()
    },
    handleExport() {
      exportProject(this.project.id)
    }
  }
}
</script>

<style>
.title {
  display: flex;
  justify-content: space-between;
}
.title label {
  line-height: var(--el-header-height);
}
.setting-icon {
  color: #fff;
  font-size: 1.2rem;
  margin-top: 1.2rem;
}
</style>