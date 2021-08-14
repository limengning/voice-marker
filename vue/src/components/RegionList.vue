<template>
  <el-row :gutter="20">
    <el-col :span="24">
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
        <el-table-column label="操作" width="260">
          <template #default="scope">
            <el-button
              size="mini"
              plain
              type="primary"
              icon="el-icon-video-play"
              @click="handleRegionPlay(scope.row.id)"
            >
            </el-button>
            <el-button
              size="mini"
              plain
              type="info"
              icon="el-icon-chat-line-square"
              @click="handleComment(scope.row)"
            >
            </el-button>

            <el-dropdown>
              <span class="el-dropdown-link">
                更多<em class="el-icon-arrow-down el-icon--right"></em>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item>
                    <el-button
                      v-if="!scope.row.locked"
                      @click="handleCommentClear(scope.row)"
                      size="mini"
                      type="text"
                    >
                      清除标注
                    </el-button>
                  </el-dropdown-item>
                  <el-dropdown-item>
                    <el-button
                      v-if="!scope.row.locked"
                      @click="handleRegionDelete(scope.row.id)"
                      size="mini"
                      type="text"
                    >
                      删除
                    </el-button>
                  </el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </template>
        </el-table-column>
      </el-table>
    </el-col>
  </el-row>
  <comment ref="marker"></comment>
</template>

<script>
import { saveMarks, getMarks } from '@/api/mark'
import Comment from './Comment'

let fileId
function slimRegion(region) {
  return {
    id: region.id,
    start: region.start,
    end: region.end,
    locked: false
  }
}
function toRequest(region) {
  return {
    regionId: region.id,
    start: region.start,
    end: region.end,
    locked: region.locked,
    fileId: fileId,
    comment: region.comment ? JSON.stringify(region.comment) : null
  }
}
function fromResponse(region) {
  return {
    id: region.regionId,
    start: region.start,
    end: region.end,
    locked: region.locked,
    comment: region.comment ? JSON.parse(region.comment) : null
  }
}
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
      region.locked = true
      this.handleRegionLockState(region.id, true)
    },
    handleRegionUnLock(region) {
      region.locked = false
      this.handleRegionLockState(region.id, false)
    },
    handleRegionLockState(regionId, locked) {
      wavesurfer.regions.list[regionId].drag = !locked
      wavesurfer.regions.list[regionId].resize = !locked
    },
    handleRegionDelete(id) {
      const index = this.regions.findIndex((x) => x.id === id)
      if (index === -1) return
      const region = this.regions[index]
      if (region.locked) return
      if (region.comment) {
        this.$message.warning({
          message: '存在标注信息不能删除，请先清除标注信息',
          type: 'warning'
        })
        return
      }
      if (this.record && this.record.id === region.id) this.record = null
      this.regions.splice(index, 1)
      wavesurfer.regions.list[id].remove()
    },
    handleRegionCreated(region) {
      region = slimRegion(region)
      if (this.regions.findIndex((x) => x.id === region.id) == -1) {
        this.regions.push(region)
      }
    },
    handleRegionUpdateEnd(region) {
      region = slimRegion(region)
      const index = this.regions.findIndex((x) => x.id === region.id)
      this.regions[index] = region
      this.regions = [...this.regions]
    },
    handleComment(region) {
      this.$refs.marker.open(region).then((form) => {
        const r = this.regions.find((x) => x.id === form.id)
        r.comment = form.comment
      })
    },
    handleCommentClear(region) {
      region.comment = null
    },
    save() {
      return saveMarks(this.regions.map(toRequest), fileId)
    },
    loadMarks(paramFileId) {
      wavesurfer.clearRegions()
      this.regions = []
      fileId = paramFileId
      return getMarks(fileId)
        .then((resp) => {
          this.regions = resp.map(fromResponse)
          for (const r of this.regions) {
            wavesurfer.addRegion(r)
            this.handleRegionLockState(r.id, r.locked)
          }
          this.$message.success('完成标注加载')
        })
        .catch(() => this.$message.error('加载标注失败'))
    }
  }
}
</script>

<style>
</style>