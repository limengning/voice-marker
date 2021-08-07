<template>
  <div style="min-width: 1200px">
    <el-space v-if="file">
      <el-button-group>
        <el-button
          v-for="m in MODE_ENUM"
          :icon="m.icon"
          :key="m.id"
          :class="{ active: mode === m.id }"
          @click="handleModeChange(m.id)"
          :aria-label="m.text"
        >
        </el-button>
      </el-button-group>
      <el-button-group>
        <el-button
          v-if="playing"
          icon="el-icon-video-pause"
          @click="handlePlay"
        >
        </el-button>
        <el-button v-else icon="el-icon-video-play" @click="handlePlay">
        </el-button>
        <el-button icon="el-icon-close" @click="handleStop"> </el-button>
      </el-button-group>
      <label>{{ file.name }}</label>
    </el-space>
    <div class="wave-container">
      <div ref="waveform"></div>
      <div ref="waveTimeline"></div>
    </div>
    <region-list ref="regionList"></region-list>
  </div>
</template>

<script>
import RegionList from './RegionList'
const WaveSurfer = window.WaveSurfer
const MODE_ENUM = {
  DEFAULT: { text: '默认模式', icon: 'el-icon-thumb', id: 1 },
  REGION: { text: '选区模式', icon: 'el-icon-crop', id: 2 }
}
const slimRegion = (region) => {
  return {
    id: region.id,
    start: region.start,
    end: region.end,
    locked: false
  }
}
let wavesurfer
export default {
  components: {
    RegionList
  },
  data() {
    return {
      file: null,
      mode: MODE_ENUM.DEFAULT.id,
      MODE_ENUM: MODE_ENUM,
      playing: false
    }
  },
  created() {
    this.$nextTick(() => {
      wavesurfer = WaveSurfer.create({
        container: this.$refs.waveform,
        waveColor: 'violet',
        mediaControls: true,
        height: 128,
        plugins: [
          WaveSurfer.regions.create({}),
          WaveSurfer.timeline.create({
            container: this.$refs.waveTimeline
          })
        ]
      })
      this.$refs.regionList.registWavesurfer(wavesurfer)
      wavesurfer.on('destroy', () => {
        wavesurfer = null
      })
      wavesurfer.on('region-created', (region) => {
        this.$refs.regionList.handleRegionCreated(slimRegion(region))
      })
      wavesurfer.on('region-update-end', (region) => {
        this.$refs.regionList.handleRegionUpdateEnd(slimRegion(region))
      })
      wavesurfer.on('region-click', (region) => {
        this.handleRegionClick(region.id)
      })
      wavesurfer.on('play', () => {
        this.playing = true
      })
      wavesurfer.on('pause', () => {
        this.playing = false
      })
    })
  },
  unmounted() {
    this.dispose()
  },
  methods: {
    loadFile(file) {
      if (!this.file || this.file.src !== file.src) {
        this.file = file
        wavesurfer.load(file.src)
        wavesurfer.clearRegions()
        this.mode = MODE_ENUM.DEFAULT.id
        this.playing = false
        this.$refs.regionList.clear()
      }
    },
    handleRegionClick(id) {
      this.$refs.regionList.toggleSelection(id)
    },
    handleModeChange(mode) {
      this.mode = mode
      wavesurfer.regions.disableDragSelection()
      if (mode === MODE_ENUM.REGION.id) {
        wavesurfer.regions.enableDragSelection({})
      }
    },
    handlePlay() {
      if (wavesurfer) {
        wavesurfer.playPause()
      }
    },
    handleStop() {
      if (wavesurfer) {
        wavesurfer.stop()
      }
    },
    dispose() {
      if (wavesurfer) {
        wavesurfer.destroy()
      }
    }
  }
}
</script>

<style>
.wave-container {
  margin: 20px auto;
}
</style>