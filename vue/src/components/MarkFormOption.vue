<template>
  <el-drawer v-model="drawer" title="设置下拉项" direction="rtl">
    <el-tag
      v-for="(tag, index) in dataSource"
      :key="index"
      closable
      :disable-transitions="false"
      @close="handleClose(tag)"
    >
      {{ tag }}
    </el-tag>
    <el-input
      v-if="inputVisible"
      ref="saveTagInput"
      v-model="inputValue"
      class="input-new-tag"
      size="mini"
      @keyup.enter="handleInputConfirm"
      @blur="handleInputConfirm"
    >
    </el-input>
    <el-button v-else class="button-new-tag" size="small" @click="showInput">
      + 添加
    </el-button>
  </el-drawer>
</template>

<script>
export default {
  data() {
    return {
      field: null,
      drawer: false,
      inputVisible: false,
      inputValue: ''
    }
  },
  methods: {
    open(field) {
      this.field = field
      this.drawer = true
    },
    handleClose(tag) {
      const dataSource = [...this.dataSource]
      dataSource.splice(dataSource.indexOf(tag), 1)
      this.field.dataSource = JSON.stringify(dataSource)
    },
    showInput() {
      this.inputVisible = true
      this.$nextTick(() => {
        this.$refs.saveTagInput.$refs.input.focus()
      })
    },
    handleInputConfirm() {
      const inputValue = this.inputValue
      if (inputValue) {
        const dataSource = [...this.dataSource]
        dataSource.push(inputValue)
        this.field.dataSource = JSON.stringify(dataSource)
      }
      this.inputVisible = false
      this.inputValue = ''
    }
  },
  computed: {
    dataSource() {
      if (this.field && this.field.dataSource) {
        return JSON.parse(this.field.dataSource)
      } else {
        return []
      }
    }
  }
}
</script>

<style>
.el-tag {
  display: block;
  margin: 5px 10px;
}
.el-tag .el-icon-close {
  top: 7px;
  right: 0;
  float: right;
}
.input-new-tag {
  display: block;
  margin: 5px 10px;
  width: auto;
}
</style>