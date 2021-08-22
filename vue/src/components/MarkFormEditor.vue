<template>
  <el-dialog
    title="标注表单字段编辑"
    v-model="dialogVisible"
    width="810px"
    :before-close="handleClose"
  >
    <el-button size="mini" @click="handleAdd">添加</el-button>
    <el-table size="mini" :data="fields">
      <el-table-column type="index" width="50"> </el-table-column>
      <el-table-column label="显示名" prop="fieldDisplayText" width="240">
        <template #default="scope">
          <el-input size="mini" v-model="scope.row.fieldDisplayText" />
        </template>
      </el-table-column>
      <el-table-column label="字段名" prop="fieldName" width="240">
        <template #default="scope">
          <el-input size="mini" v-model="scope.row.fieldName" />
        </template>
      </el-table-column>
      <el-table-column label="类型" prop="fieldType" width="120">
        <template #default="scope">
          <el-select size="mini" v-model="scope.row.fieldType">
            <el-option
              v-for="item in fieldTypes"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column label="是否必填" prop="required" width="120">
        <template #default="scope">
          <el-checkbox size="mini" v-model="scope.row.required" />
        </template>
      </el-table-column>
    </el-table>
    <template #footer>
      <span class="dialog-footer">
        <el-button size="mini" @click="handleClose">取 消</el-button>
        <el-button size="mini" type="primary" @click="handleSave"
          >保 存</el-button
        >
      </span>
    </template>
  </el-dialog>
</template>

<script>
import { getFields, saveFields, saveFieldsByProject } from '@/api/markForm'
const fieldTypes = [
  { label: '文本框', value: 'INPUT' },
  { label: '文本域', value: 'TEXT' },
  { label: '标签', value: 'TAG' },
  { label: '开关', value: 'SWITCH' },
  { label: '单选', value: 'RADIO' },
  { label: '多选', value: 'CHECKBOX' },
  { label: '下拉', value: 'SELECT' },
  { label: '打分', value: 'RATE' }
]
let resolve
export default {
  data() {
    return {
      dialogVisible: false,
      project: null,
      fields: [],
      fieldTypes: fieldTypes
    }
  },
  methods: {
    open(project) {
      this.dialogVisible = true
      this.project = project
      if (project.markFormId) {
        getFields(project.markFormId).then((resp) => (this.fields = resp))
      }
      return new Promise((res) => {
        resolve = res
      })
    },
    handleSave() {
      if (this.project.markFormId) {
        saveFields(this.project.markFormId, this.fields).then(() => {
          resolve(this.project.markFormId)
          this.$message.success('保存成功')
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
    },
    handleAdd() {
      this.fields.push({ fieldType: 1 })
    }
  }
}
</script>

<style>
</style>