import Login from './views/Login.vue'
import NotFound from './views/404.vue'
import Home from './views/Home.vue'
import Main from './views/Main.vue'
import Users from './views/users/List.vue'
import Reset from './views/users/Reset.vue'
import DataSource from './views/datasource/List.vue'
import CheckRule from './views/checkRule/checkRule.vue'
import PartyList from './views/party/PartyList.vue'
import Checkresult from './views/checkresult/checkresult.vue'

let routes = [
    {
        path: '/login',
        component: Login,
        name: '',
        hidden: true
    },
    {
        path: '/404',
        component: NotFound,
        name: '',
        hidden: true
    },
    //{ path: '/main', component: Main },
     {
        path: '/',
        component: Home,
        name: '用户中心',
        iconCls: 'el-icon-message',//图标样式class
        children: [
            { path: '/user/reset', component: Reset, name: '当前用户重置' },
            { path: '/user/list', component: Users, name: '系统用户列表' }
        ]
    },
    {
        path: '/',
        component: Home,
        name: '数据源管理',
        iconCls: 'el-icon-message',//图标样式class
        children: [
            { path: '/datasources', component: DataSource, name: '数据源列表' }
        ]
    },
    {
        path: '/',
        component: Home,
        name: '关系人信息管理',
        iconCls: 'fa fa-id-card-o',
        children: [
            { path: '/PartyList', component: PartyList, name: '关系人信息列表' }  
        ]
    },
     {
        path: '/',
        component: Home,
        name: '校验信息管理',
        iconCls: 'fa fa-id-card-o',
        children: [
            { path: '/checkRule', component: CheckRule, name: '校验信息列表' }  
        ]
    },
    {
        path: '/',
        component: Home,
        name: '校验结果信息管理',
        iconCls: 'fa fa-id-card-o',
        children: [
            { path: '/checkresult', component: Checkresult, name: '校验结果查询' }  
        ]
    },
    
     {
        path: '*',
        hidden: true,
        redirect: { path: '/404' }
    }
];

export default routes;