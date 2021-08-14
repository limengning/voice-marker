<template>
  <div>
    <el-button @click="handleAdd">新建</el-button>
  </div>
  <div>
    <el-table :data="projects">
      <el-table-column prop="name" label="名称"></el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-space>
            <el-button type="text" @click="handleEnter(scope.row)">
              进入
            </el-button>
            <el-dropdown>
              <span class="el-dropdown-link">
                更多<em class="el-icon-arrow-down el-icon--right"></em>
              </span>
              <template #dropdown>
                <el-dropdown-menu
                  ><el-dropdown-item>
                    <el-button @click="handleEdit(scope.row)" type="text">
                      编辑
                    </el-button>
                  </el-dropdown-item>
                  <el-dropdown-item>
                    <el-button @click="handleDelete(scope.row)" type="text">
                      删除
                    </el-button>
                  </el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
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
    <detail ref="detail"></detail>
  </div>
</template>

<script>
import { delProject, getProjects } from '@/api/project'
import Detail from './Detail'
export default {
  components: { Detail },
  name: 'Index',
  data() {
    return {
      projects: [],
      pager: {
        pageIndex: 1,
        pageSize: 10
      },
      total: 0
    }
  },
  created() {
    this.loadData()
  },
  methods: {
    loadData() {
      const param = { ...this.pager }
      getProjects(param).then((resp) => {
        this.projects = resp.records
        this.total = resp.total
      })
    },
    handleAdd() {
      this.$refs.detail.open().then(() => {
        this.loadData()
      })
    },
    handleEnter(project) {
      this.$router.push({ name: 'workspace', params: { id: project.id } })
    },
    handleEdit(project) {
      this.$refs.detail.open(project).then(() => {
        this.loadData()
      })
    },
    handleDelete(project) {
      this.$confirm('此操作将永久删除该项目, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delProject(project.id).then(() => this.loadData())
      })
    }
  }
}
</script>

<style></style>
