<template>
  <span>
    <el-input
      v-if="field.fieldType === 'INPUT'"
      v-model="value"
    />
    <el-input
      type="textarea"
      v-if="field.fieldType === 'TEXT'"
      v-model="value"
    />
    <el-checkbox
      v-if="field.fieldType === 'CHECKBOX'"
      v-model="value"
    />
    <el-select
      v-if="field.fieldType === 'SELECT'"
      v-model="value"
    >
      <el-option
        v-for="item in JSON.parse(field.dataSource)"
        :key="item.value"
        :label="item.label"
        :value="item.value"
      >
      </el-option>
    </el-select>
    <tag-field
      v-if="field.fieldType === 'TAG'"
      v-model="value"
    />
  </span>
</template>

<script>
import TagField from './TagField.vue'
export default {
  components: { TagField },
  props: ['field', 'modelValue'],
  emits: ['update:modelValue'],
  computed: {
    value: {
      get() {
        return this.modelValue
      },
      set(value) { 
        this.$emit('update:modelValue', value)
      }
    }
  }
}
</script>

<style>
</style>