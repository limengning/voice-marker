<template>
  <div class="title">
    <label>项目 - {{ this.project.name }}</label>
    <span>
      <el-dropdown @command="handleSettings">
        <el-icon class="setting-icon"><setting /></el-icon>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item :command="handleTemplate">
              设置标注项
            </el-dropdown-item>
            <el-dropdown-item :command="handleEdit">
              编辑项目
            </el-dropdown-item>
            <el-dropdown-item :command="handleMore">
              切换项目
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </span>
  </div>
  <mark-form-editor ref="formEditor" />
</template>

<script>
import { mapGetters, mapMutations } from 'vuex'
import { Setting } from '@element-plus/icons'
import MarkFormEditor from '@/components/MarkFormEditor.vue'

export default {
  components: {
    MarkFormEditor,
    Setting
  },
  computed: {
    ...mapGetters('workplace', ['project'])
  },
  methods: {
    ...mapMutations('workplace', ['setMarkFields']),
    handleSettings(command) {
      command()
    },
    handleTemplate() {
      this.$refs.formEditor.open(this.project).then((fields) => {
        this.setMarkFields(fields)
      })
    },
    handleEdit() {},
    handleMore() {}
  }
}
</script>

<style>
.title {
  display: flex;
  justify-content: space-between;
}
.setting-icon {
  color: #fff;
  font-size: 1.2rem;
  margin-top: 1.2rem;
}
</style>