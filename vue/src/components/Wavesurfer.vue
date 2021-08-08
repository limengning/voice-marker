<template>
  <div style="min-width: 1200px">
    <el-space v-if="file">
      <label>{{ file.name }}</label>
      <el-button-group>
        <el-button
          size="mini"
          v-for="m in MODE_ENUM"
          :key="m.id"
          :class="{ active: mode === m.id }"
          @click="handleModeChange(m.id)"
          :aria-label="m.text"
        >
          <span class="iconfont" :class="m.icon"></span>
        </el-button>
      </el-button-group>
      <el-button-group>
        <el-button
          size="mini"
          v-if="playing"
          icon="el-icon-video-pause"
          @click="handlePlay"
        >
        </el-button>
        <el-button
          size="mini"
          v-else
          icon="el-icon-video-play"
          @click="handlePlay"
        >
        </el-button>
        <el-button size="mini" @click="handleStop">
          <span class="iconfont icon-marker-stop"></span>
        </el-button>
      </el-button-group>
      <el-button-group>
        <el-button size="mini" @click="handleSave">
          <span class="iconfont icon-marker-save"></span>
        </el-button>
        <el-button
          size="mini"
          @click="handleLoadMarks"
          icon="el-icon-refresh"
        ></el-button>
      </el-button-group>
    </el-space>
    <el-space v-else>
      <label>未选择音频文件</label>
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
  DEFAULT: { text: '默认模式', icon: 'icon-marker-cursor', id: 1 },
  REGION: { text: '选区模式', icon: 'icon-marker-select', id: 2 }
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
      wavesurfer.on('destroy', () => {
        wavesurfer = null
      })
      wavesurfer.on('region-created', (region) => {
        this.$refs.regionList.handleRegionCreated(region)
      })
      wavesurfer.on('region-update-end', (region) => {
        this.$refs.regionList.handleRegionUpdateEnd(region)
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
      this.$refs.regionList.registWavesurfer(wavesurfer)
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
        this.mode = MODE_ENUM.DEFAULT.id
        this.playing = false
        this.handleLoadMarks()
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
    },
    handleSave() {
      this.$refs.regionList.save().then(() => {
        this.$message.success('保存成功')
      })
    },
    handleLoadMarks() {
      this.$refs.regionList.loadMarks(this.file.id)
    }
  }
}
</script>

<style>
.wave-container {
  margin: 20px auto;
}
</style>