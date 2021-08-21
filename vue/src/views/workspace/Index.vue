<template>
  <el-container style="border: 1px solid #eee">
    <h2>项目 - {{this.project.name}}</h2>
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
import { mapGetters } from 'vuex'

export default {
  components: {
    Wavesurfer,
    AudioList
  },
  methods: {
    handleSelect(file) {
      this.$refs.wavesurfer.loadFile(file)
    }
  },
  computed: {
    ...mapGetters('workplace',['project'])
  }
}
</script>

<style></style>
