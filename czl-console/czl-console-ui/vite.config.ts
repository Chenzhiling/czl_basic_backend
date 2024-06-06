import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'
export default defineConfig({
  plugins: [vue()],
  resolve: {
    //配置@path组件
    alias: {
      '@': path.resolve(__dirname, './src')
    }
  },
  server: {
    host: '0.0.0.0',
    port: 8080,
    open: true,
    // https: true,
    // 设置代理，根据我们项目实际情况配置
    proxy: {
      '/api': {
        target: 'http://10.88.10.44:6789/czl',
        // changeOrigin: true,
        ws: true,
        // secure: false,
        rewrite: (path) => path.replace(/^\/api/, ''),
      },
    },
  },
})
