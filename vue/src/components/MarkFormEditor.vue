<template>
  <el-dialog
    title="标注表单字段编辑"
    v-model="dialogVisible"
    width="30%"
    :before-close="handleClose"
  >
    <el-button>添加</el-button>
    <el-table :data="fields">
      <el-table-column type="index" width="50"> </el-table-column>
      <el-table-column label="字段名" width="120"></el-table-column>
      <el-table-column label="显示名" width="120"></el-table-column>
      <el-table-column label="类型" width="120"></el-table-column>
      <el-table-column label="是否必填" width="120"></el-table-column>
      <el-table-column label="编辑" width="120"></el-table-column>
    </el-table>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="handleClose">取 消</el-button>
        <el-button type="primary" @click="handleSave">保存</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import { getFields, saveFields, saveFieldsByProject } from '@/api/markForm'

let resolve
export default {
  data() {
    return {
      dialogVisible: false,
      project: null,
      fields: []
    }
  },
  methods: {
    open(project) {
      this.dialogVisible = true
      this.project = project
      if (project.markFormId) {
        getFields(project.markFormId).then((resp) => (this.fields = resp))
      }
      return Promise((res) => {
        resolve = res
      })
    },
    handleSave() {
      if (this.project.markFormId) {
        saveFields(this.project.markFormId, this.fields).then(() => {
          resolve(this.project.markFormId)
          this.handleClose()
        })
      } else {
        saveFieldsByProject(this.project.id, this.fields).then((id) => {
          resolve(id)
          this.handleClose()
        })
      }
    },
    handleClose() {
      this.dialogVisible = false
    }
  }
}
</script>

<style>
</style>