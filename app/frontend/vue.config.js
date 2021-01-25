module.exports = {
  css: {
    loaderOptions: {
      sass: {
        prependData: '@import "@/assets/style/_variables.scss"',
      },
    },
  },
  devServer: {
    proxy: 'http://app-supermarket:8085',
  }
}
