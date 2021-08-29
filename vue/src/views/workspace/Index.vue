<template>
  <el-container>
    <el-aside width="300px">
      <el-header>
        项目 - {{ this.project.name }}
        <el-button @click="handleTemplate">设置标注项</el-button>
      </el-header>
      <el-container>
        <audio-list @select="handleSelect" />
      </el-container>
    </el-aside>
    <el-main>
      <el-tabs
        v-model="openedFile"
        type="card"
        closable
        @tab-remove="handleFilesRemove"
      >
        <el-tab-pane
          v-for="file in files"
          :key="file.id"
          :label="file.name"
          :name="getTabName(file)"
        >
          <wavesurfer ref="wavesurfer" :file="file" />
        </el-tab-pane>
      </el-tabs>
    </el-main>
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
  data() {
    return {
      files: [],
      openedFile: ''
    }
  },
  methods: {
    getTabName(file) {
      return 'file' + file.id
    },
    ...mapMutations('workplace', ['setMarkFields']),
    handleSelect(file) {
      const index = this.files.findIndex((f) => f.id === file.id)
      if (index !== -1) {
        this.openedFile = this.getTabName(this.files[index])
      } else {
        this.files.push(file)
        this.openedFile = this.getTabName(file)
      }
      console.log(this.files)
    },
    handleTemplate() {
      this.$refs.formEditor.open(this.project).then((fields) => {
        this.setMarkFields(fields)
      })
    },
    handleFilesRemove(name) {
      const index = this.files.findIndex((f) => this.getTabName(f) === name)
      if (index !== -1) {
        this.files.splice(index, 1)
        console.log(this.files)
      }
    }
  },
  computed: {
    ...mapGetters('workplace', ['project'])
  }
}
</script>

<style></style>
