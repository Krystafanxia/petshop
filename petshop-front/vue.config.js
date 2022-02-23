const CopyWebpackPlugin = require('copy-webpack-plugin');
module.exports = {
  assetsDir: 'static',
  productionSourceMap: false,
  devServer: {
    host: '0.0.0.0',
    port: 8089,
    disableHostCheck: true
  },
  css: {
    loaderOptions: {
      css: {
        sourceMap: process.env.NODE_ENV !== "production"
      }
    }
  },
  configureWebpack: {
    plugins: [
      new CopyWebpackPlugin({
        patterns:[{
          from: "./static",
          to: 'static'
        }]
      })
    ]
  }
};
