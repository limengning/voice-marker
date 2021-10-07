<template>
  <el-dialog
    title="标注项设置"
    v-model="dialogVisible"
    width="810px"
    :before-close="handleClose"
  >
    <el-button size="mini" @click="handleAdd">添加</el-button>
    <el-table size="mini" :data="fields">
      <el-table-column type="index" width="50"> </el-table-column>
      <el-table-column label="名称" prop="fieldName" width="240">
        <template #default="scope">
          <el-input size="mini" v-model="scope.row.fieldName" />
        </template>
      </el-table-column>
      <el-table-column label="类型" prop="fieldType" width="240">
        <template #default="scope">
          <el-space>
            <el-select
              size="mini"
              v-model="scope.row.fieldType"
              :class="{ short: scope.row.fieldType === 'SELECT' }"
            >
              <el-option
                v-for="item in fieldTypes"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
            <el-button
              size="mini"
              v-if="scope.row.fieldType === 'SELECT'"
              type="text"
              @click="handleSetOptions(scope.row)"
            >
              设置下拉项
            </el-button>
          </el-space>
        </template>
      </el-table-column>
      <el-table-column width="60">
        <template #default="scope">
          <div>
            <el-button
              size="mini"
              @click="handleDelete(scope.$index)"
              icon="el-icon-delete"
            >
            </el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <template #footer>
      <span class="dialog-footer">
        <el-button size="mini" @click="handleClose">取 消</el-button>
        <el-button size="mini" type="primary" @click="handleSave">
          保 存
        </el-button>
      </span>
    </template>
  </el-dialog>
  <mark-form-option ref="dropdownOption"></mark-form-option>
</template>

<script>
import { getMarkFields, saveMarkFields } from '@/api/project'
import MarkFormOption from './MarkFormOption'
const fieldTypes = [
  { label: '文本框', value: 'INPUT' },
  { label: '文本域', value: 'TEXT' },
  { label: '勾选', value: 'CHECKBOX' },
  { label: '下拉', value: 'SELECT' },
  { label: '标签', value: 'TAG' }
]
let resolve
export default {
  components: {
    MarkFormOption
  },
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
      getMarkFields(project.id).then((resp) => (this.fields = resp))
      return new Promise((res) => {
        resolve = res
      })
    },
    handleSave() {
      saveMarkFields(this.project.id, this.fields).then(() => {
        this.$message.success('保存成功')
        this.handleClose()
        resolve(this.fields)
      })
    },
    handleClose() {
      this.dialogVisible = false
    },
    handleAdd() {
      this.fields.push({ fieldType: fieldTypes[0].value })
    },
    handleDelete(index) {
      this.fields.splice(index, 1)
    },
    handleSetOptions(field) {
      this.$refs.dropdownOption.open(field)
    }
  }
}
</script>
<style>
.short {
  width: 120px;
}
</style>