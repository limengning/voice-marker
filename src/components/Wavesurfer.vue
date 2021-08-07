<template>
  <div>
    <div ref="waveform"></div>
    <el-button-group>
      <el-button
        v-for="m in MODE_ENUM"
        :icon="m.icon"
        :key="m.id"
        :class="{ active: mode === m.id }"
        @click="handleModeChange(m.id)"
        :aria-label="m.text"
      >
        {{ m.text }}
      </el-button>
    </el-button-group>

    <el-table
      :data="regions"
      ref="regionTable"
      @selection-change="handleSelectionChange"
      stripe
    >
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column type="index" label="#"></el-table-column>
      <el-table-column width="80">
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
      <el-table-column prop="start" label="开始" width="180">
        <template #default="scope">
          {{ scope.row.start.toFixed(2) }}s
        </template>
      </el-table-column>
      <el-table-column prop="end" label="结束" width="180">
        <template #default="scope">
          {{ scope.row.end.toFixed(2) }}s
        </template></el-table-column
      >
      <el-table-column label="操作">
        <template #default="scope">
          <el-button
            size="mini"
            plain
            type="primary"
            icon="el-icon-video-play"
            @click="handleRegionPlay(scope.row)"
          >
          </el-button>
          <el-popconfirm
            title="确定删除这段选区吗？"
            v-if="!scope.row.locked"
            @confirm="handleRegionDelete(scope.row)"
          >
            <template #reference>
              <el-button size="mini" type="danger" icon="el-icon-delete">
              </el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
const WaveSurfer = window.WaveSurfer
const regionDefaultColor = 'rgba(0, 0, 0, 0.1)'
const regionSelectColor = 'rgba(0, 0, 0, 0.3)'
const MODE_ENUM = {
  DEFAULT: { text: '默认模式', icon: 'el-icon-thumb', id: 1 },
  REGION: { text: '选区模式', icon: 'el-icon-crop', id: 2 }
}
const slimRegion = (region) => {
  return {
    id: region.id,
    start: region.start,
    end: region.end,
    locked: false,
    text: ''
  }
}
let wavesurfer
export default {
  data() {
    return {
      regions: [],
      regionSelection: [],
      mode: MODE_ENUM.DEFAULT.id,
      MODE_ENUM: MODE_ENUM
    }
  },
  created() {
    this.$nextTick(() => {
      wavesurfer = WaveSurfer.create({
        container: this.$refs.waveform,
        waveColor: 'violet',
        progressColor: 'purple',
        plugins: [WaveSurfer.regions.create({})]
      })
      wavesurfer.load('/voice/唐诗三百首朗读/02五言乐府036-046/046游子吟.mp3')
      wavesurfer.on('region-created', (region) => {
        this.handleRegionCreated(slimRegion(region))
      })
      wavesurfer.on('region-update-end', (region) => {
        this.handleRegionUpdateEnd(slimRegion(region))
      })
      wavesurfer.on('region-click', (region) => {
        this.handleRegionClick(region.id)
      })
    })
  },
  methods: {
    handleRegionCreated(region) {
      if (this.regions.findIndex((x) => x.id === region.id) == -1)
        this.regions.push(region)
    },
    handleRegionUpdateEnd(region) {
      const index = this.regions.findIndex((x) => x.id === region.id)
      this.regions[index] = region
      this.regions = [...this.regions]
    },
    handleRegionClick(id) {
      console.log(id)
      this.toggleSelection(id)
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
    handleRegionDelete(region) {
      if (region.locked) {
        return
      }
      wavesurfer.regions.list[region.id].remove()
      const index = this.regions.findIndex((x) => x.id === region.id)
      this.regions.splice(index, 1)
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
      this.regionSelection = val
      const selectIds = val.map((x) => x.id)
      for (const id in wavesurfer.regions.list) {
        const region = wavesurfer.regions.list[id]
        region.remove()
        region.color = selectIds.includes(id)
          ? regionSelectColor
          : regionDefaultColor
        wavesurfer.addRegion(region)
      }
      console.log(wavesurfer.regions.list)
    },
    handleRegionPlay(region) {
      wavesurfer.regions.list[region.id].play()
    },
    handleModeChange(mode) {
      this.mode = mode
      wavesurfer.regions.disableDragSelection()
      if (mode === MODE_ENUM.REGION.id) {
        wavesurfer.regions.enableDragSelection({})
      }
    }
  }
}
</script>

<style>
</style>