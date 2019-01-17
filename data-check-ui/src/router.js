import babelpolyfill from 'babel-polyfill'
import Vue from 'vue'
import App from './App'
import {Message} from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
//import './assets/theme/theme-green/index.css'
import VueRouter from 'vue-router'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import routes from './routes'
import Mock from './mock'
//Mock.bootstrap();

Vue.use(VueRouter)

NProgress.configure({ showSpinner: false });

const router = new VueRouter({
  routes
})

router.beforeEach((to, from, next) => {
  NProgress.start();
  if (to.path == '/login') {
    sessionStorage.removeItem('user');
    sessionStorage.removeItem('stakeholders');
    sessionStorage.removeItem("systemNames");
  
   
  }
  let user = JSON.parse(sessionStorage.getItem('user'));
  if (!user && to.path != '/login') {
    Message({
          showClose: true,
          message: "登录状态信息过期,请重新登录",
          type: "error"
        });
    next({ path: '/login' })
  } else {
    next()
  }
})

router.afterEach(transition => {
NProgress.done();
});

export default router;

