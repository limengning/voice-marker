<template>
  <div>
    <el-dialog
      :title="title"
      v-model="dialogVisible"
      :show-close="false"
      destroy-on-close
      :close-on-click-modal="false"
      :close-on-press-escape="false"
    >
      <el-form :model="form" :rules="rules" @submit="handleCommit" ref="form">
        <el-form-item
          label="项目名称"
          :label-width="formLabelWidth"
          prop="name"
        >
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="项目描述"
          :label-width="formLabelWidth"
          prop="description"
        >
          <el-input
            type="textarea"
            v-model="form.description"
            autocomplete="off"
          >
          </el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="handleCancel"> 取 消 </el-button>
          <el-button type="primary" @click="handleCommit"> 确 定 </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { mapActions } from 'vuex'
let resolve
export default {
  data() {
    return {
      formLabelWidth: '120px',
      rules: {
        name: [
          { required: true, message: '请输入项目名称', trigger: 'blur' },
          { max: 15, message: '项目名称最长15个字符', trigger: 'blur' }
        ],
        description: [
          { max: 200, message: '项目描述最长200个字符', trigger: 'blur' }
        ]
      },
      form: {},
      id: null,
      dialogVisible: false
    }
  },
  computed: {
    title() {
      return this.id ? '编辑项目' : '添加项目'
    }
  },
  methods: {
    ...mapActions('workplace', ['saveProject']),
    open(proj) {
      proj = proj || {}
      this.id = proj.id || null
      this.form = proj
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
      this.$refs.form.validate(async (valid) => {
        if (valid) {
          this.saveProject(this.form).then((id) => {
            if (id) {
              this.dialogVisible = false
              resolve(id)
            }
          })
        }
      })
    }
  }
}
</script>

<style></style>
