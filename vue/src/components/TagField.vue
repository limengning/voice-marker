<template>
  <el-tag
    :key="tag"
    v-for="tag in modelValue"
    closable
    :disable-transitions="false"
    @close="handleClose(tag)"
  >
    {{ tag }}
  </el-tag>
  <el-input
    class="input-new-tag"
    v-if="inputVisible"
    v-model="inputValue"
    ref="saveTagInput"
    :size="size"
    @keyup.enter="handleInputConfirm"
    @blur="handleInputConfirm"
  >
  </el-input>
  <el-button v-else class="button-new-tag" :size="size" @click="showInput"
    >+ 新标签</el-button
  >
</template>

<script>
export default {
  props: {
    modelValue: {
      type: Array,
      required: true,
      default: () => []
    },
    size: {
      type: String,
      required: false,
      default: 'small'
    }
  },
  data() {
    return {
      inputVisible: false,
      inputValue: ''
    }
  },
  methods: {
    handleClose(tag) {
      const newModelValue = [...this.modelValue].splice(
        this.modelValue.indexOf(tag),
        1
      )
      this.$emit('update:modelValue', newModelValue)
    },

    showInput() {
      this.inputVisible = true
      this.$nextTick(() => {
        this.$refs.saveTagInput.$refs.input.focus()
      })
    },

    handleInputConfirm() {
      let inputValue = this.inputValue
      if (inputValue) {
        const newModelValue = [...this.inputValue]
        this.$emit('update:modelValue', newModelValue)
      }
      this.inputVisible = false
      this.inputValue = ''
    }
  }
}
</script>

<style>
.el-tag + .el-tag {
  margin-left: 10px;
}
.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}
.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}
</style>