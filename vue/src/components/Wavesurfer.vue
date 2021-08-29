<template>
  <div style="min-width: 1200px">
    <el-space v-if="file">
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

export default {
  components: {
    RegionList
  },
  props: {
    file: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      wavesurfer: null,
      mode: MODE_ENUM.DEFAULT.id,
      MODE_ENUM: MODE_ENUM,
      playing: false
    }
  },
  created() {
    this.$nextTick(() => {
      this.wavesurfer = WaveSurfer.create({
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
      this.wavesurfer.on('destroy', () => {
        this.wavesurfer = null
      })
      this.wavesurfer.on('region-created', (region) => {
        this.$refs.regionList.handleRegionCreated(region)
      })
      this.wavesurfer.on('region-update-end', (region) => {
        this.$refs.regionList.handleRegionUpdateEnd(region)
      })
      this.wavesurfer.on('region-click', (region) => {
        this.handleRegionClick(region.id)
      })
      this.wavesurfer.on('play', () => {
        this.playing = true
      })
      this.wavesurfer.on('pause', () => {
        this.playing = false
      })
      this.$refs.regionList.registWavesurfer(this.wavesurfer)
      this.loadFile()
    })
  },
  unmounted() {
    this.dispose()
  },
  methods: {
    loadFile() {
      this.wavesurfer.load(this.file.src)
      this.mode = MODE_ENUM.DEFAULT.id
      this.playing = false
      this.handleLoadMarks()
    },
    handleRegionClick(id) {
      this.$refs.regionList.toggleSelection(id)
    },
    handleModeChange(mode) {
      this.mode = mode
      this.wavesurfer.regions.disableDragSelection()
      if (mode === MODE_ENUM.REGION.id) {
        this.wavesurfer.regions.enableDragSelection({})
      }
    },
    handlePlay() {
      if (this.wavesurfer) {
        this.wavesurfer.playPause()
      }
    },
    handleStop() {
      if (this.wavesurfer) {
        this.wavesurfer.stop()
      }
    },
    dispose() {
      if (this.wavesurfer) {
        this.wavesurfer.destroy()
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