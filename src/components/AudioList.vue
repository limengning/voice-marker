<template>
  <div>
    <el-table :data="files">
      <el-table-column prop="name" label="文件名">
        <template #default="scope">
          <el-link :href="scope.row.src">{{ scope.row.name }}</el-link>
        </template>
      </el-table-column>
      <el-table-column label="加载">
        <template #default="scope">
          <el-button
            size="mini"
            plain
            type="primary"
            icon="el-icon-headset"
            @click="handleSelect(scope.row)"
          >
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      selected: null,
      files: [
        {
          id: 1,
          name: '游子吟.mp3',
          src: '/voice/唐诗三百首朗读/02五言乐府036-046/046游子吟.mp3',
          size: '',
          duration: ''
        },
        {
          id: 2,
          name: '春望.mp3',
          src: '/voice/唐诗三百首朗读/05五言律诗91-170/106春望.mp3',
          size: '',
          duration: ''
        }
      ]
    }
  },
  methods: {
    handleSelect(file) {
      if (this.selected !== file.id) {
        this.selected = file.id
        this.$emit('select', file)
      }
    }
  }
}
</script>

<style>
</style>