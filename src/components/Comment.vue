<template>
  <el-card header="标注信息">
    <el-form label-position="top">
      <el-form-item label="文本">
        <el-input v-model="form.text" v-if="edit"></el-input>
        <span v-else>{{ record.text }}</span>
      </el-form-item>
    </el-form>
    <el-button @click="handleSave">保存</el-button>
    <el-button @click="handleClear">清除</el-button>
  </el-card>
</template>

<script>
export default {
  props: {
    record: Object
  },
  emits: ['save'],
  data() {
    return {
      edit: true,
      form: {}
    }
  },
  created() {
    this.setFormValue(this.record)
  },
  watch: {
    record(val) {
      this.setFormValue(val)
    }
  },
  methods: {
    handleSave() {
      this.$emit('save', { id: this.record.id, comment: this.form })
    },
    handleClear() {
      this.form = {}
      this.handleSave()
    },
    setFormValue(val) {
      this.form = val ? { ...val.comment } : {}
    }
  }
}
</script>

<style>
</style>