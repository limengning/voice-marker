<template>
  <el-container>
    <el-aside width="300px" class="workspace-aside">
      <el-header class="workspace-header">
        <div class="workspace-logo">
          项目 - {{ this.project.name }}
          <el-button @click="handleTemplate">设置标注项</el-button>
        </div>
      </el-header>
      <el-container class="file-list">
        <audio-list @select="handleSelect" />
      </el-container>
    </el-aside>
    <el-main class="workspace-main">
      <el-tabs v-model="openedTab" type="card" @tab-remove="handleFilesRemove">
        <el-tab-pane label="主页" :name="homeTabName" :closable="false">
          <home />
        </el-tab-pane>
        <el-tab-pane
          v-for="file in files"
          :key="file.id"
          :label="file.name"
          :name="getTabName(file)"
          closable
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
import Home from './Home'
import { mapGetters, mapMutations } from 'vuex'

const HOME_TAB_NAME = 'home'

export default {
  components: {
    Wavesurfer,
    AudioList,
    MarkFormEditor,
    Home
  },
  data() {
    return {
      files: [],
      homeTabName: HOME_TAB_NAME,
      openedTab: HOME_TAB_NAME
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
        this.openedTab = this.getTabName(this.files[index])
      } else {
        this.files.push(file)
        this.openedTab = this.getTabName(file)
      }
    },
    handleTemplate() {
      this.$refs.formEditor.open(this.project).then((fields) => {
        this.setMarkFields(fields)
      })
    },
    handleFilesRemove(name) {
      const index = this.files.findIndex((f) => this.getTabName(f) === name)
      if (index !== -1) {
        const deleted = this.files.splice(index, 1)
        if (this.files.length === 0) {
          this.openedTab = HOME_TAB_NAME
        } else {
          if (this.getTabName(deleted) === this.openedTab) {
            const selectedIndex = Math.max(this.files.length - 1, index)
            this.openedTab = this.getTabName(this.files[selectedIndex])
          }
        }
      }
    }
  },
  computed: {
    ...mapGetters('workplace', ['project']),
    tabs() {
      return [{}, this.files]
    }
  }
}
</script>

<style>
.workspace-header {
  background: var(--el-text-color-primary);
  color: var(--el-color-info-light);
}

.workspace-logo {
  height: var(--el-header-height);
  line-height: var(--el-header-height);
}
.workspace-main {
  padding: var(--el-main-padding) 0;
  height: calc(100vh);
}
.workspace-aside {
  border-right: 1px solid var(--el-border-color-light);
  width: auto;
  box-shadow: var(--el-box-shadow-light);
}
.file-list {
  padding: 10px 0;
}
</style>
