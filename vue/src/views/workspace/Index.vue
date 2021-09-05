<template>
  <el-container>
    <el-aside width="300px" class="workspace-aside">
      <el-header class="workspace-header">
        <workspace-title />
      </el-header>
      <el-container class="file-list">
        <audio-list @select="handleSelect" />
      </el-container>
    </el-aside>
    <el-main class="workspace-main">
      <el-tabs v-model="openedTab" type="card" @tab-remove="handleFilesRemove">
        <el-tab-pane label="主页" :name="homeTabName" :closable="false">
          <welcome />
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
  </el-container>
</template>

<script>
import Wavesurfer from '@/components/Wavesurfer.vue'
import AudioList from '@/components/AudioList.vue'
import WorkspaceTitle from '@/components/WorkspaceTitle.vue'
import { mapGetters, mapActions } from 'vuex'
import Welcome from './Welcome'
const HOME_TAB_NAME = 'home'

export default {
  components: {
    Wavesurfer,
    AudioList,
    WorkspaceTitle,
    Welcome
  },
  props: ['projectId'],
  data() {
    return {
      files: [],
      homeTabName: HOME_TAB_NAME,
      openedTab: HOME_TAB_NAME
    }
  },
  methods: {
    ...mapActions('workplace', ['loadProject']),
    getTabName(file) {
      return 'file' + file.id
    },
    handleSelect(file) {
      const index = this.files.findIndex((f) => f.id === file.id)
      if (index !== -1) {
        this.openedTab = this.getTabName(this.files[index])
      } else {
        this.files.push(file)
        this.openedTab = this.getTabName(file)
      }
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
    ...mapGetters('workplace', ['hasProject'])
  },
  watch: {
    projectId(id) {
      this.loadProject(id)
    }
  }
}
</script>

<style>
.workspace-header {
  background: var(--el-text-color-primary);
  color: var(--el-color-info-light);
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
