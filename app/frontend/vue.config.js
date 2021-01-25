module.exports = {
  css: {
    loaderOptions: {
      sass: {
        prependData: '@import "@/assets/style/_variables.scss"',
      },
    },
  },
  devServer: {
    proxy: 'http://localhost:8085',
  }
}
