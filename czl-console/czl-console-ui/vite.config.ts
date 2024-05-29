import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  server: {
    host: '0.0.0.0',
    port: 8080,
    open: true,
    // https: true,
    // 设置代理，根据我们项目实际情况配置
    proxy: {
      '/api': {
        target: 'http://10.88.10.44:1202/chaoyi',
        // changeOrigin: true,
        ws: true,
        // secure: false,
        rewrite: (path) => path.replace(/^\/api/, ''),
      },
    },
  },
})
