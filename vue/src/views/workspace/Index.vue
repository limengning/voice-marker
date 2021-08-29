<template>
  <el-container>
    <el-header>
      项目 - {{ this.project.name }}
      <el-button @click="handleTemplate">设置标注项</el-button>
    </el-header>
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
    <mark-form-editor ref="formEditor" />
  </el-container>
</template>

<script>
import Wavesurfer from '@/components/Wavesurfer.vue'
import AudioList from '@/components/AudioList.vue'
import MarkFormEditor from '@/components/MarkFormEditor.vue'
import { mapGetters, mapMutations } from 'vuex'

export default {
  components: {
    Wavesurfer,
    AudioList,
    MarkFormEditor
  },
  methods: {
    ...mapMutations('workplace', ['setMarkFields']),
    handleSelect(file) {
      this.$refs.wavesurfer.loadFile(file)
    },
    handleTemplate() {
      this.$refs.formEditor.open(this.project).then((fields) => {
        this.setMarkFields(fields)
      })
    }
  },
  computed: {
    ...mapGetters('workplace', ['project'])
  }
}
</script>

<style></style>
