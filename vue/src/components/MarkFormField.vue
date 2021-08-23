<template>
  <span>
    <el-input
      v-if="field.fieldType === 'INPUT'"
      :model-value="value"
      @change="handleChange"
    />
    <el-input
      type="textarea"
      v-if="field.fieldType === 'TEXT'"
      :model-value="value"
      @change="handleChange"
    />
    <el-checkbox
      v-if="field.fieldType === 'CHECKBOX'"
      :model-value="value"
      @change="handleChange"
    />
    <el-select
      v-if="field.fieldType === 'SELECT'"
      :model-value="value"
      @change="handleChange"
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
      :model-value="value"
      @change="handleChange"
    />
  </span>
</template>

<script>
import TagField from './TagField.vue'
export default {
  components: { TagField },
  props: {
    field: Object,
    modelValue: Object
  },
  methods: {
    handleChange(value) {
      this.$emit('update:modelValue', value)
    }
  }
}
</script>

<style>
</style>