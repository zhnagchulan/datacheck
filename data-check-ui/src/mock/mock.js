import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';
import Mock from 'mockjs';
import { LoginUsers, UsersData,Resources,DataSourcesData } from './data/user';
let _Users = UsersData;
let _DataSources=DataSourcesData;
export default {
  /**
   * mock bootstrap
   */
  bootstrap() {
    let mock = new MockAdapter(axios);

    // mock success request
    mock.onGet('/success').reply(200, {
      msg: 'success'
    });

    // mock error request
    mock.onGet('/error').reply(500, {
      msg: 'failure'
    });

    //登录
    mock.onPost('/login').reply(config => {
      let {username, password} = JSON.parse(config.data);
      return new Promise((resolve, reject) => {
        let user = null;
        setTimeout(() => {
          let hasUser = true;

          if (hasUser) {
            resolve([200,LoginUsers]);
          } else {
            resolve([200, { code: 500, msg: '账号或密码错误' }]);
          }
        }, 1000);
      });
    });

    //登录
    mock.onPost('/resource').reply(config => {
      let {clerkNo} = JSON.parse(config.data);
      return new Promise((resolve, reject) => {
        let user = null;
        setTimeout(() => {
          let hasUser = LoginUsers.data.clerkNo===clerkNo;
          hasUser=true;
          if (hasUser) {
            resolve([200,Resources]);
          } else {
            resolve([200, { code: 500, msg: '获得资源失败' }]);
          }
        }, 1000);
      });
    });

    //获取用户列表
    mock.onGet('/user/list').reply(config => {
      let {name} = config.params;
      let mockUsers=Object.assign({}, _Users);
      mockUsers.data = mockUsers.data.filter(user => {
        if (name && user.name.indexOf(name) == -1) return false;
        return true;
      });

      return new Promise((resolve, reject) => {
        setTimeout(() => {
          resolve([200, {
            users: mockUsers
          }]);
        }, 1000);
      });
    });

    //获取用户列表（分页）
    mock.onGet('/user/listpage').reply(config => {
      let {page, name} = config.params;
      let mockUsers=Object.assign({}, _Users);
      mockUsers.data = mockUsers.data.filter(user => {
        if (name && user.name.indexOf(name) == -1) return false;
        return true;
      });
      let total = mockUsers.data.length;
      let users= mockUsers.data.filter((u, index) => index < 20 * page && index >= 20 * (page - 1));
      mockUsers.data={
            total: total,
            users: users
          };
      return new Promise((resolve, reject) => {
        setTimeout(() => {
          resolve([200, mockUsers]);
        }, 1000);
      });
    });

    //删除用户
    mock.onGet('/user/remove').reply(config => {
      let { id } = config.params;
      _Users.data = _Users.data.filter(u => u.id !== id);
      return new Promise((resolve, reject) => {
        setTimeout(() => {
          resolve([200, {
            code: 200,
            msg: '删除成功'
          }]);
        }, 500);
      });
    });

    //批量删除用户
    mock.onGet('/user/batchremove').reply(config => {
      let { ids } = config.params;
      ids = ids.split(',');
      _Users.data = _Users.data.filter(u => !ids.includes(u.id));
      return new Promise((resolve, reject) => {
        setTimeout(() => {
          resolve([200, {
            code: 200,
            msg: '删除成功'
          }]);
        }, 500);
      });
    });

    //编辑用户
    mock.onGet('/user/edit').reply(config => {
      let { id, name, addr, age, birth, sex } = config.params;
      _Users.data.some(u => {
        if (u.id === id) {
          u.name = name;
          u.addr = addr;
          u.age = age;
          u.birth = birth;
          u.sex = sex;
          return true;
        }
      });
      return new Promise((resolve, reject) => {
        setTimeout(() => {
          resolve([200, {
            code: 200,
            msg: '编辑成功'
          }]);
        }, 500);
      });
    });

    //新增用户
    mock.onGet('/user/add').reply(config => {
      let { name, addr, age, birth, sex } = config.params;
      _Users.data.push({
        name: name,
        addr: addr,
        age: age,
        birth: birth,
        sex: sex
      });
      return new Promise((resolve, reject) => {
        setTimeout(() => {
          resolve([200, {
            code: 200,
            msg: '新增成功'
          }]);
        }, 500);
      });
    });


    //获取数据源列表
    mock.onGet('/datasource/list').reply(config => {
      let {code} = config.params;
      let mockDatasources=Object.assign({},_DataSourcesData);
      mockDatasources.data = mockDatasources.data.filter(datasource => {
        if (code && datasource.code.indexOf(code) == -1) return false;
        return true;
      });
      return new Promise((resolve, reject) => {
        setTimeout(() => {
          resolve([200, {
            users: mockDatasources
          }]);
        }, 1000);
      });
    });

    //获取数据源列表（分页）
    mock.onGet('/datasource/listpage').reply(config => {
      let {page, code} = config.params;
      let mockDatasources=Object.assign({},_DataSourcesData);
      mockDatasources.data = mockDatasources.data.filter(datasource => {
        if (code && datasource.code.indexOf(code) == -1) return false;
        return true;
      });
      let total = mockDatasources.data.length;
      mockDatasources.data = mockDatasources.data.filter((u, index) => index < 20 * page && index >= 20 * (page - 1));
      return new Promise((resolve, reject) => {
        setTimeout(() => {
          resolve([200, {
            total: total,
            datasources: mockDatasources
          }]);
        }, 1000);
      });
    });

    //删除用户
    mock.onGet('/datasource/remove').reply(config => {
      let { id } = config.params;
      _DataSourcesData.data = _DataSourcesData.data.filter(u => u.id !== id);
      return new Promise((resolve, reject) => {
        setTimeout(() => {
          resolve([200, {
            code: 200,
            msg: '删除成功'
          }]);
        }, 500);
      });
    });

    //编辑用户
    mock.onGet('/datasource/edit').reply(config => {
      let { id, code, ip, user, inst, password } = config.params;
      _DataSources.data.some(datasource => {
        if (datasource.id === id) {
          datasource.code = code;
          datasource.ip = ip;
          datasource.user = user;
          datasource.inst = inst;
          datasource.password=password;
          return true;
        }
      });
      return new Promise((resolve, reject) => {
        setTimeout(() => {
          resolve([200, {
            code: 200,
            msg: '编辑成功'
          }]);
        }, 500);
      });
    });

    //新增用户
    mock.onGet('/datasource/add').reply(config => {
      let { code, ip, user, inst, password } = config.params;
      _DataSources.data.push({
        id:Mock.Random.guid(),
        code: code,
        ip: ip,
        user: user,
        inst: inst,
        password: password
      });
      return new Promise((resolve, reject) => {
        setTimeout(() => {
          resolve([200, {
            code: 200,
            msg: '新增成功'
          }]);
        }, 500);
      });
    });
  }
};