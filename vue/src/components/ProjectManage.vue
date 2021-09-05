<template>
  <div class="content">
    <project-editor ref="editor" />
    <project-selector ref="selector" />
  </div>
</template>

<script>
import ProjectEditor from '@/components/ProjectEditor'
import ProjectSelector from '@/components/ProjectSelector'

export default {
  components: {
    ProjectEditor,
    ProjectSelector
  },
  methods: {
    add() {
      this.$refs.editor.open().then((id) => {
        this.$router.push({ name: 'workspace', params: { projectId: id } })
      })
    },
    edit(project) {
      if (project) {
        this.$refs.editor.open(project)
      } else {
        throw Error('项目不能为空')
      }
    },
    open() {
      this.$refs.selector.open()
    }
  }
}
</script>

<style>
.content {
  line-height: initial;
}
</style>