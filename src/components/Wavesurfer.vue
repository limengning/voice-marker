<template>
  <div>
    <div ref="waveform"></div>
    <el-button-group>
      <el-button
        :disabled="!file"
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
    locked: false,
    comment: {}
  }
}
let wavesurfer
export default {
  components: {
    RegionList
  },
  data() {
    return {
      regions: [],
      file: null,
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
        mediaControls: true,
        height: 256,
        plugins: [WaveSurfer.regions.create({})]
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
    })
  },
  unmounted() {
    this.dispose()
  },
  methods: {
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
    dispose() {
      if (wavesurfer) {
        wavesurfer.destroy()
      }
    },
    loadFile(file) {
      if (!this.file || this.file.src !== file.src) {
        this.file = file
        wavesurfer.load(file.src)
        this.regions = []
        this.regionSelection = []
        this.mode = MODE_ENUM.DEFAULT.id
      }
    }
  }
}
</script>

<style>
</style>