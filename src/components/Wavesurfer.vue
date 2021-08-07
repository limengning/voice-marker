<template>
  <div>
    <div ref="waveform"></div>
    <el-radio-group v-model="mode" @change="handleModeChange">
      <el-radio-button v-for="m in MODE_ENUM" :label="m" :key="m">
      </el-radio-button>
    </el-radio-group>

    <el-table
      :data="regions"
      ref="regionTable"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column type="index"></el-table-column>
      <el-table-column prop="id" label="id" width="180"></el-table-column>
      <el-table-column prop="start" label="开始" width="180"></el-table-column>
      <el-table-column prop="end" label="结束" width="180"></el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button
            v-if="scope.row.locked"
            size="mini"
            @click="handleRegionUnLock(scope.row)"
          >
            解锁
          </el-button>
          <el-button v-else size="mini" @click="handleRegionLock(scope.row)">
            锁定
          </el-button>
          <el-button
            v-if="!scope.row.locked"
            size="mini"
            type="danger"
            @click="handleRegionDelete(scope.row)"
            >删除</el-button
          >
          <el-button size="mini" @click="handleRegionPlay(scope.row)">
            播放
          </el-button>
          <el-input size="mini" v-model="scope.row.text" />
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
  DEFAULT: '默认模式',
  REGION: '选区模式'
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
      mode: MODE_ENUM.DEFAULT,
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
      wavesurfer.regions.disableDragSelection()
      if (mode === MODE_ENUM.REGION) {
        wavesurfer.regions.enableDragSelection({})
      }
    }
  }
}
</script>

<style>
</style>