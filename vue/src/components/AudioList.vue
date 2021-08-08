<template>
  <div>
    <el-upload
      action=""
      :before-upload="handleBeforeUpload"
      :limit="1"
      accept="audio/*"
    >
      <el-button size="small" icon="el-icon-upload2" type="primary">
      </el-button>
    </el-upload>
    <el-table :data="files" stripe size="mini">
      <el-table-column prop="name" label="文件名" show-overflow-tooltip>
      </el-table-column>
      <el-table-column :width="40">
        <template #default="scope">
          <span
            @click="handleSelect(scope.row)"
            class="iconfont icon-marker-open"
          >
          </span>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      small
      v-model:page-size="pager.pageSize"
      v-model:current-page="pager.pageIndex"
      layout="prev, next, total"
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
.icon-marker-open {
  cursor: pointer;
  color: var(--el-color-primary);
}
</style>