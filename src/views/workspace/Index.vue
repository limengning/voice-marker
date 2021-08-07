<template>
  <el-container style="height: 500px; border: 1px solid #eee">
    <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
      <audio-list />
    </el-aside>
    <el-container>
      <wavesurfer />
    </el-container>
  </el-container>
</template>

<script>
import Wavesurfer from '@/components/Wavesurfer.vue'
import AudioList from '@/components/AudioList.vue'
export default {
  components: {
    Wavesurfer,
    AudioList
  }
}
</script>

<style>
</style>