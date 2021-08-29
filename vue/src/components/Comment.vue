<template>
  <el-drawer
    title="标注信息"
    v-model="drawer"
    :before-close="handleSave"
    destroy-on-close
  >
    <el-card>
      <el-form label-position="top">
        <el-form-item
          v-for="field in markFields"
          :key="field.id"
          :label="field.fieldDisplayText"
        >
          <mark-form-field
            v-if="edit"
            v-model="form[field.fieldName]"
            :field="field"
          >
          </mark-form-field>
          <span v-else>{{ record[field.fieldName] }}</span>
        </el-form-item>
      </el-form>
    </el-card>
  </el-drawer>
</template>

<script>
import { mapGetters } from 'vuex'
import MarkFormField from './MarkFormField'

let resolve
export default {
  components: {
    MarkFormField
  },
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
  },
  computed: {
    ...mapGetters('workplace', ['markFields'])
  }
}
</script>

<style>
</style>