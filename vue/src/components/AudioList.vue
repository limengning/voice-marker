<template>
  <div>
    <div class="upload-area">
      <label>文件名</label>
      <el-upload
        action=""
        :before-upload="handleBeforeUpload"
        :limit="1"
        accept="audio/*"
      >
        <el-button size="mini" icon="el-icon-upload2" type="primary">
        </el-button>
      </el-upload>
    </div>
    <el-table :data="files" stripe size="mini" :show-header="false">
      <el-table-column :width="200" prop="name" show-overflow-tooltip>
        <template #default="scope">
          <el-button size="mini" type="text" @click="handleSelect(scope.row)">
            {{ scope.row.name }}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column :width="50">
        <template #default="scope">
          <el-space>
            <el-dropdown size="mini">
              <span class="el-dropdown-link">
                <em class="el-icon-arrow-down el-icon--right"></em>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item>
                    <el-button
                      @click="handleRename(scope.row, scope.$index)"
                      size="mini"
                      type="text"
                    >
                      重命名
                    </el-button>
                  </el-dropdown-item>
                  <el-dropdown-item>
                    <el-button
                      @click="handleDelete(scope.row.id, scope.$index)"
                      size="mini"
                      type="text"
                    >
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
      small
      v-model:page-size="pager.pageSize"
      v-model:current-page="pager.pageIndex"
      layout="prev, next, total"
      :total="total"
      @size-change="loadData"
      @current-change="loadData"
    >
    </el-pagination>
    <rename-dialog ref="rename" />
  </div>
</template>

<script>
import RenameDialog from './RenameDialog'
import { getFiles, addFile, delFile } from '@/api/file'
import { mapGetters } from 'vuex'

export default {
  components: {
    RenameDialog
  },
  computed: {
    ...mapGetters('workplace', { projectId: 'projectId' })
  },
  data() {
    return {
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
      this.$emit('select', file)
    },
    handleBeforeUpload(file) {
      addFile(file, this.projectId).then((resp) => {
        this.files.push(resp)
        this.$message.success('上传成功')
      })
      return false
    },
    handleRename(row, index) {
      this.$refs.rename.rename(row.id, row.name).then((name) => {
        this.files[index].name = name
      })
    },
    handleDelete(id, index) {
      delFile(id).then(() => {
        this.files.splice(index, 1)
        this.total = this.total - 1
      })
    }
  }
}
</script>

<style>
.icon-marker-open {
  cursor: pointer;
  color: var(--el-color-primary);
}
.upload-area {
  display: flex;
  justify-content: space-between;
  padding: 0 10px;
  height: 30px;
}
.upload-area label {
  line-height: 30px;
  height: 30px;
  font-size: 14px;
}
</style>
