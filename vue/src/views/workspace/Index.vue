<template>
  <el-container style="border: 1px solid #eee">
    <el-aside width="500px">
      <audio-list @select="handleSelect" />
    </el-aside>
    <el-container>
      <wavesurfer ref="wavesurfer" />
    </el-container>
  </el-container>
</template>

<script>
import Wavesurfer from '@/components/Wavesurfer.vue'
import AudioList from '@/components/AudioList.vue'
export default {
  props: {
    projectId: {
      type: Number,
      required: true
    }
  },
  components: {
    Wavesurfer,
    AudioList
  },
  methods: {
    handleSelect(file) {
      this.$refs.wavesurfer.loadFile(file)
    }
  }
}
</script>

<style></style>
