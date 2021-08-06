<template>
  <div ref="waveform"></div>
</template>

<script>
const WaveSurfer = window.WaveSurfer
export default {
  data() {
    return {
      wavesurfer: null
    }
  },
  created() {
    this.$nextTick(() => {
      this.wavesurfer = WaveSurfer.create({
        container: this.$refs.waveform,
        waveColor: 'violet',
        progressColor: 'purple',
        plugins: [
          WaveSurfer.regions.create({
            dragSelection: { slop: 100 }
          })
        ]
      })
      this.wavesurfer.load(
        'http://ia902606.us.archive.org/35/items/shortpoetry_047_librivox/song_cjrg_teasdale_64kb.mp3'
      )
    })
  }
}
</script>

<style>
</style>