<template>
  <el-config-provider :locale="locale">
    <wavesurfer />
  </el-config-provider>
</template>

<script>
import zhCn from 'element-plus/lib/locale/lang/zh-cn'
import Wavesurfer from './components/Wavesurfer.vue'

export default {
  name: 'App',
  components: {
    Wavesurfer
  },
  data() {
    return {
      locale: zhCn
    }
  }
}
</script>
