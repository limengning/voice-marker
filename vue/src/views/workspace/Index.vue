<template>
  <el-container>
    <el-header> 项目 - {{ this.project.name }} </el-header>
    <el-container>
      <el-aside width="500px">
        <el-card>
          <audio-list @select="handleSelect" />
        </el-card>
      </el-aside>
      <el-main>
        <el-card>
          <wavesurfer ref="wavesurfer" />
        </el-card>
      </el-main>
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
    ...mapGetters('workplace', ['project'])
  }
}
</script>

<style></style>
