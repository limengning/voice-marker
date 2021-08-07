const path = require('path')
function resolve(dir) {
  return path.join(__dirname, dir)
}
module.exports = {
  devServer: {
    port: 6060
  },
  chainWebpack: config => {
    config.resolve.alias.set('@', resolve('src'))
  }
}
