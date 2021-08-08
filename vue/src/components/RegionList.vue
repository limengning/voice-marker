<template>
  <el-row :gutter="20">
    <el-col :span="12">
      <el-table
        :data="regions"
        ref="regionTable"
        :default-sort="{ prop: 'start' }"
        @selection-change="handleSelectionChange"
        stripe
      >
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column width="70">
          <template #default="scope">
            <el-button
              v-if="scope.row.locked"
              size="mini"
              icon="el-icon-lock"
              @click="handleRegionUnLock(scope.row)"
            >
            </el-button>
            <el-button
              v-else
              size="mini"
              icon="el-icon-unlock"
              @click="handleRegionLock(scope.row)"
            >
            </el-button>
          </template>
        </el-table-column>
        <el-table-column type="index" label="#"></el-table-column>

        <el-table-column prop="start" label="开始(s)" width="100">
          <template #default="scope">
            {{ scope.row.start.toFixed(2) }}
          </template>
        </el-table-column>
        <el-table-column prop="end" label="结束(s)" width="100">
          <template #default="scope"> {{ scope.row.end.toFixed(2) }} </template>
        </el-table-column>
        <el-table-column label="操作" width="180">
          <template #default="scope">
            <el-button
              size="mini"
              plain
              type="info"
              icon="el-icon-chat-line-square"
              @click="handleComment(scope.row)"
            >
            </el-button>
            <el-button
              size="mini"
              plain
              type="primary"
              icon="el-icon-video-play"
              @click="handleRegionPlay(scope.row.id)"
            >
            </el-button>
            <el-button
              v-if="!scope.row.locked"
              @click="handleRegionDelete(scope.row.id)"
              size="mini"
              type="danger"
              icon="el-icon-delete"
            >
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-col>
    <el-col :span="12">
      <comment
        v-if="record"
        :record="record"
        @save="handleCommentSave"
      ></comment>
    </el-col>
  </el-row>
</template>

<script>
import { ElMessage } from 'element-plus'
import { saveMarks } from '@/api'
import Comment from './Comment'
const regionDefaultColor = 'rgba(0, 0, 0, 0.1)'
const regionSelectColor = 'rgba(0, 0, 0, 0.3)'
let wavesurfer
export default {
  components: {
    Comment
  },
  data() {
    return {
      regions: [],
      record: null
    }
  },
  methods: {
    registWavesurfer(ws) {
      wavesurfer = ws
    },
    handleRegionPlay(id) {
      wavesurfer.regions.list[id].play()
    },
    toggleSelection(id) {
      if (id) {
        const region = this.regions.find((x) => x.id === id)
        if (region) this.$refs.regionTable.toggleRowSelection(region)
      } else {
        this.$refs.regionTable.clearSelection()
      }
    },
    handleSelectionChange(val) {
      const selectIds = val.map((x) => x.id)
      this.$emit('selectionChange', selectIds)
      for (const id in wavesurfer.regions.list) {
        const region = wavesurfer.regions.list[id]
        region.remove()
        region.color = selectIds.includes(id)
          ? regionSelectColor
          : regionDefaultColor
        wavesurfer.addRegion(region)
      }
    },
    handleRegionLock(region) {
      this.handleRegionLockState(region, true)
    },
    handleRegionUnLock(region) {
      this.handleRegionLockState(region, false)
    },
    handleRegionLockState(region, locked) {
      region.locked = locked
      wavesurfer.regions.list[region.id].drag = !locked
      wavesurfer.regions.list[region.id].resize = !locked
    },
    handleRegionDelete(id) {
      const index = this.regions.findIndex((x) => x.id === id)
      if (index === -1) return
      const region = this.regions[index]
      if (region.locked) return
      if (region.comment && Object.keys(region.comment).length) {
        console.log(Object.keys(region.comment))
        ElMessage.warning({
          message: '存在标注信息不能删除，请先清除标注信息',
          type: 'warning'
        })
        return
      }
      if (this.record && this.record.id === region.id) this.record = null
      this.regions.splice(index, 1)
      wavesurfer.regions.list[id].remove()
    },
    handleCommentSave(form) {
      const r = this.regions.find((x) => x.id === form.id)
      r.comment = form.comment
    },
    handleRegionCreated(region) {
      if (this.regions.findIndex((x) => x.id === region.id) == -1)
        this.regions.push(region)
    },
    handleRegionUpdateEnd(region) {
      const index = this.regions.findIndex((x) => x.id === region.id)
      this.regions[index] = region
      this.regions = [...this.regions]
    },
    handleComment(region) {
      this.record = region
    },
    clear() {
      this.regions = []
      this.record = null
    },
    save() {
      return saveMarks(this.regions)
    }
  }
}
</script>

<style>
</style>