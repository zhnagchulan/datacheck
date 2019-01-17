import Mock from 'mockjs';
const LoginUsers = {
  "code": 200,
  "success": true,
  "msg": "处理成功",
  'data':{
    'clerkNo': 'cy001',
    'username': 'admin',
    'password': '123456',
    // avatar: 'https://raw.githubusercontent.com/taylorchen709/markdown-images/master/vueadmin/user.png',
    'name': '张某某',
    'sex': '男'
  }
};
const Resources={
  "code": 200,
  "success": true,
  "msg": "处理成功",
  "data": {
    'branchName': '北京分公司',
    'branchNo': '110000',
    'clerkNo': 'cy001',
    'name': "张三",
    // 'avatar': 'https://raw.githubusercontent.com/taylorchen709/markdown-images/master/vueadmin/user.png',
    'sex': '女',
    'resources': [{
      'iconType': 'fa fa-cog fa-lg',
      'resourceId': '1',
      'resourceModType': 'M',
      'resourceName': '系统设置',
      'resourceType': 'S'
    },{
      'iconType': 'home',
      'resourceId': '11',
      'parentId':'1',
      'resourceModType': 'M',
      'resourceName': '数据源管理',
      'resourceType': 'S',
      'url':'/table'
    },{
      'iconType': 'fa fa-pencil-square-o fa-lg',
      'resourceId': '2',
      'resourceModType': 'M',
      'resourceName': '校验信息管理',
      'resourceType': 'S'
    },{
      'iconType': 'home',
      'resourceId': '21',
      'parentId':'2',
      'resourceModType': 'M',
      'resourceName': '关系人管理',
      'resourceType': 'S',
      'url':'/user'
    },{
      'iconType': 'home',
      'resourceId': '22',
      'parentId':'2',
      'resourceModType': 'M',
      'resourceName': '校验信息设置',
      'resourceType': 'S',
      'url':'/page4'
    },{
      'iconType': 'fa fa-list fa-lg',
      'resourceId': '3',
      'resourceModType': 'M',
      'resourceName': '校验结果',
      'resourceType': 'S'
    },{
      'iconType': '',
      'resourceId': '31',
      'parentId':'3',
      'resourceModType': 'M',
      'resourceName': '校验结果查询',
      'resourceType': 'S',
      'url':'/page5'
    },{
      'iconType': 'fa fa-id-card-o  fa-lg',
      'resourceId': '4',
      'resourceModType': 'M',
      'resourceName': '用户中心',
      'resourceType': 'S'
    },{
      'iconType': 'home',
      'resourceId': '41',
      'parentId':'4',
      'resourceModType': 'M',
      'resourceName': '系统用户管理',
      'resourceType': 'S',
      'url':'/users'
    },{
      'iconType': 'home',
      'resourceId': '42',
      'parentId':'4',
      'resourceModType': 'M',
      'resourceName': '重置密码',
      'resourceType': 'S',
      'url':'/page6'
    }]
  }
};

const Users = [];

for (let i = 0; i < 86; i++) {
  Users.push(Mock.mock({
    id: Mock.Random.guid(),
    name: Mock.Random.cname(),
    addr: Mock.mock('@county(true)'),
    'mobile|1':['13531544954','13632250649','15820292420','15999905612'], //在数组中随机找一个
     email:Mock.mock('@EMAIL()'), //随机生成一个邮箱
    sex: Mock.Random.integer(0, 1),
    status:Mock.Random.integer(0, 1)
  }));
}
const UsersData={
  "code": 200,
  "success": true,
  "msg": "处理成功",
  "data": Users
  }
export { LoginUsers, UsersData,Resources };
