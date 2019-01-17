import axios from "axios";
import qs from "qs";
import { Message } from "element-ui";
import router from "../router";


axios.defaults.timeout = 5000;
axios.defaults.baseURL ='';

/*请求后台数据的主机ip，统一定义在此，在各个<script></script>中引入调用
避免ip多次修改的繁琐*/
//export default {host_port:'www.autodatacheck.com:2018/data-check-app'}; for production
//export default {host_port:'10.21.37.20:8088/datacheck'};
export default {host_port:'10.21.42.173:2018/data-check-app'}; //生产环境地址
//http request 拦截器
axios.interceptors.request.use(
  config => {
    // const token = getCookie('名称');注意使用的时候需要引入cookie方法，推荐js-cookie
    config.data = JSON.stringify(config.data);
    config.headers = {
      'Content-Type':'application/json'
    }
    // if(token){
    //   config.params = {'token':token}
    // }
    return config;
  },
  error => {
    return Promise.reject(err);
  }
);


//http response 拦截器
axios.interceptors.response.use(
  response => {
    if(response.data.code ==401){
      router.push({
        path:"/login",
        querry:{redirect:router.currentRoute.fullPath}//从哪个页面跳转
      })
    }else if(response.data.code==200){
    	response.data=response.data.data;
    	return response;
    }else{

    	Message({
          showClose: true,
          message: `发生错误，错误码${response.data.code},错误消息${response.data.msg}`,
          type: "error"
        });
    }
    return response;
  },
  error => {
    return Promise.reject(error)
  }
)


/**
 * 封装get方法
 * @param url
 * @param data
 * @returns {Promise}
 */

export function fetch(url,params={}){
  return new Promise((resolve,reject) => {
    axios.get(url,{
      params:params
    })
    .then(response => {
      resolve(response.data);
    })
    .catch(err => {
      reject(err)
    })
  })
}


/**
 * 封装post请求
 * @param url
 * @param data
 * @returns {Promise}
 */

 export function post(url,data = {}){
   return new Promise((resolve,reject) => {
     axios.post(url,data)
          .then(response => {
            resolve(response.data);
          },err => {
            reject(err)
          })
   })
 }

 /**
 * 封装patch请求
 * @param url
 * @param data
 * @returns {Promise}
 */

export function patch(url,data = {}){
  return new Promise((resolve,reject) => {
    axios.patch(url,data)
         .then(response => {
           resolve(response.data);
         },err => {
           reject(err)
         })
  })
}

 /**
 * 封装put请求
 * @param url
 * @param data
 * @returns {Promise}
 */

export function put(url,data = {}){
  return new Promise((resolve,reject) => {
    axios.put(url,data)
         .then(response => {
           resolve(response.data);
         },err => {
           reject(err)
         })
  })
}
// export const requestLogin = (params) => {
// 	// return axios.post(`${base}/login`, params);
// };

// export const getUserResources = params => {
// 	return axios.post(`${base}/resource`, params);
// };


// export const getUserList = params => {
// 	return axios.get(`${base}/user/list`, {
// 		params: params
// 	});
// };

// export const getUserListPage = params => {
// 	return axios.get(`${base}/user/listpage`, {
// 		params: params
// 	});
// };

// export const removeUser = params => {
// 	return axios.get(`${base}/user/remove`, {
// 		params: params
// 	});
// };

// export const batchRemoveUser = params => {
// 	return axios.get(`${base}/user/batchremove`, {
// 		params: params
// 	});
// };

// export const editUser = params => {
// 	return axios.get(`${base}/user/edit`, {
// 		params: params
// 	});
// };

// export const addUser = params => {
// 	return axios.get(`${base}/user/add`, {
// 		params: params
// 	});
// };


// export const getDataSourceList = params => {
// 	return axios.get(`${base}/datasource/list`, {
// 		params: params
// 	});
// };

// export const getDataSourceListPage = params => {
// 	return axios.get(`${base}/datasource/listpage`, {
// 		params: params
// 	});
// };

// export const removeDataSource = params => {
// 	return axios.get(`${base}/datasource/remove`, {
// 		params: params
// 	});
// };


// export const editDataSource = params => {
// 	return axios.get(`${base}/datasource/edit`, {
// 		params: params
// 	});
// };

// export const addDataSource = params => {
// 	return axios.get(`${base}/datasource/add`, {
// 		params: params
// 	});
// };