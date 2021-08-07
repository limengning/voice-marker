<template>
  <el-form label-position="left" inline>
    <el-form-item label="文本">
      <el-input v-model="form.text" v-if="edit"></el-input>
      <span v-else>{{ record.text }}</span>
    </el-form-item>
  </el-form>
  <el-button @click="handleSave">保存</el-button>
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
    this.form = { ...this.record.comment }
  },
  methods: {
    handleSave() {
      this.$emit('save', { id: this.record.id, comment: this.form })
      this.form = {}
    }
  }
}
</script>

<style>
</style>