import babelpolyfill from 'babel-polyfill'
import Vue from 'vue'
import App from './App'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
//import './assets/theme/theme-green/index.css'
import store from './vuex/store'
import Vuex from 'vuex'

import router from './router'
import 'font-awesome/css/font-awesome.min.css'
import {post,fetch,patch,put} from './util/http'
import axios from 'axios'
import VueAxios from 'vue-axios'
Vue.use(VueAxios,axios);
Vue.use(ElementUI)
Vue.use(Vuex)

//定义全局变量
Vue.prototype.$post=post;
Vue.prototype.$fetch=fetch;
Vue.prototype.$patch=patch;
Vue.prototype.$put=put;


new Vue({
  //el: '#app',
  //template: '<App/>',
  router,
  store,
  //components: { App }
  render: h => h(App)
}).$mount('#app')

