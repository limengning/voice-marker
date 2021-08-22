<template>
  <el-dialog
    title="重命名"
    v-model="dialogVisible"
    :show-close="false"
    destroy-on-close
    :close-on-click-modal="false"
    :close-on-press-escape="false"
  >
    <el-card>
      <el-form :model="form" :rules="rules" @submit="handleCommit" ref="form">
        <el-form-item label="名称" :label-width="formLabelWidth" prop="name">
          <el-input v-model="form.name" autocomplete="off" />
        </el-form-item>
      </el-form>
    </el-card>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="handleCancel"> 取 消 </el-button>
        <el-button type="primary" @click="handleCommit"> 确 定 </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import { renameFile } from '@/api/file'

let resolve
export default {
  data() {
    return {
      dialogVisible: false,
      formLabelWidth: '120px',
      form: {},
      id: null,
      rules: {
        name: [
          { required: true, message: '请输入名称', trigger: 'blur' },
          { max: 15, message: '名称最长15个字符', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    rename(id, name) {
      this.id = id
      this.form = { name }
      this.dialogVisible = true
      return new Promise((res) => {
        resolve = res
      })
    },
    handleCancel() {
      this.dialogVisible = false
    },
    handleCommit(e) {
      e.preventDefault()
      this.$refs.form.validate((valid) => {
        if (valid) {
          const name = this.form.name
          renameFile(this.id, name).then(() => {
            this.dialogVisible = false
            resolve(name)
          })
        }
      })
    }
  }
}
</script>

<style>
</style>