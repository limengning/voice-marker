<template>
  <div>
    <el-upload action="" :before-upload="handleBeforeUpload" :limit="1">
      <el-button size="small" type="primary">点击上传</el-button>
    </el-upload>
    <el-table :data="files">
      <el-table-column prop="name" label="文件名">
        <template #default="scope">
          <el-link :href="scope.row.src">{{ scope.row.name }}</el-link>
        </template>
      </el-table-column>
      <el-table-column label="加载" :width="80">
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
    <el-pagination
      small
      :page-size="pager.pageSize"
      :current-page="pager.pageIndex"
      layout="prev, next"
      :total="total"
      @size-change="loadData"
      @current-change="loadData"
    >
    </el-pagination>
  </div>
</template>

<script>
import { ElMessage } from 'element-plus'
import { getFiles, addFile } from '@/api'

export default {
  props: {
    projectId: {
      type: Number,
      required: true
    }
  },
  data() {
    return {
      selected: null,
      pager: {
        pageSize: 20,
        pageIndex: 1
      },
      files: [],
      total: 0
    }
  },
  created() {
    this.loadData()
  },
  methods: {
    async loadData() {
      const param = { projectId: this.projectId, ...this.pager }
      const resp = await getFiles(param)
      this.files = resp.records
      this.total = resp.total
    },
    handleSelect(file) {
      if (this.selected !== file.id) {
        this.selected = file.id
        this.$emit('select', file)
      }
    },
    handleBeforeUpload(file) {
      addFile(file, this.projectId).then((resp) => {
        this.files.push(resp)
        ElMessage.success('上传成功')
      })
      return false
    }
  }
}
</script>

<style>
</style>