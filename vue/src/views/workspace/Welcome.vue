<template>
  <div>
    <h3>欢迎使用语音标注平台</h3>
    <el-timeline>
      <el-timeline-item hide-timestamp placement="top">
        <el-card>
          <h4>1. 创建项目</h4>
          <p>创建一个项目来更好的归类语音文件和标注项</p>
        </el-card>
      </el-timeline-item>
      <el-timeline-item hide-timestamp placement="top">
        <el-card>
          <h4>2. 设置标注项</h4>
          <p>设置项目的标注项</p>
        </el-card>
      </el-timeline-item>
      <el-timeline-item hide-timestamp placement="top">
        <el-card>
          <h4>3. 上传文件</h4>
          <p>上传需要标注的语音文件，最大支持15M的音频文件</p>
        </el-card>
      </el-timeline-item>
      <el-timeline-item hide-timestamp placement="top">
        <el-card>
          <h4>4. 选择文件，开始工作</h4>
          <p>在左侧文件列表中选择需要标注的文件，开始你的标注工作吧</p>
        </el-card>
      </el-timeline-item>
    </el-timeline>
  </div>
</template>

<script>
export default {}
</script>

<style>
h3 {
  margin-left: 1em;
}
</style>