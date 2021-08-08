<template>
  <el-drawer
    title="标注信息"
    v-model="drawer"
    :before-close="handleSave"
    destroy-on-close
  >
    <el-form label-position="top">
      <el-form-item label="文本">
        <el-input v-model="form.text" v-if="edit"></el-input>
        <span v-else>{{ record.text }}</span>
      </el-form-item>
    </el-form>
  </el-drawer>
</template>

<script>
let resolve
export default {
  data() {
    return {
      record: null,
      drawer: false,
      edit: true,
      form: {}
    }
  },
  methods: {
    open(record) {
      this.record = record
      this.setFormValue(record)
      this.drawer = true
      return new Promise((res) => {
        resolve = res
      })
    },
    handleSave(done) {
      resolve({ id: this.record.id, comment: this.form })
      done()
    },
    setFormValue(val) {
      this.form = val ? { ...val.comment } : {}
    }
  }
}
</script>

<style>
</style>