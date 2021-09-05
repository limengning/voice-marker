<template>
  <el-dialog v-model="dialogVisible" destroy-on-close>
    <el-table :data="projects" size="mini">
      <el-table-column prop="name">
        <template #default="scope">
          <el-space>
            <el-button type="text" @click="handleGoto(scope.row.id)">
              {{ scope.row.name }}
            </el-button>
          </el-space>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      v-model:page-size="pager.pageSize"
      v-model:current-page="pager.pageIndex"
      :total="total"
      @size-change="loadData"
      @current-change="loadData"
    >
    </el-pagination>
  </el-dialog>
</template>

<script>
import { getProjects } from '@/api/project'

export default {
  data() {
    return {
      dialogVisible: false,
      projects: [],
      pager: {
        pageIndex: 1,
        pageSize: 10
      },
      total: 0
    }
  },
  methods: {
    async loadData() {
      const param = { ...this.pager }
      const resp = await getProjects(param)
      this.projects = resp.records
      this.total = resp.total
    },
    open() {
      this.loadData().then(() => (this.dialogVisible = true))
    },
    handleGoto(id) {
      this.dialogVisible = false
      this.$router.push({ name: 'workspace', params: { projectId: id } })
    }
  }
}
</script>

<style>
</style>