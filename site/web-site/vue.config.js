const AutoImport = require("unplugin-auto-import/webpack");
const {ElementPlusResolver} = require("unplugin-vue-components/resolvers");
const Components = require("unplugin-vue-components/webpack");

// element-ui 按需引入
module.exports = {
    configureWebpack: {
        plugins: [
            AutoImport({
                resolvers: [ElementPlusResolver({importStyle: false})],
            }),
            Components({
                    resolvers: [ElementPlusResolver()]
                }
            ),
        ]
    }
}
