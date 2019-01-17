import Mock from 'mockjs';


const DataSources = [];

for (let i = 0; i < 86; i++) {
  DataSources.push(Mock.mock({
    id: Mock.Random.guid(),
    code: Mock.Random.cname(),
    ip: Random.ip(),
    user:Random.last(),
    inst:Random.last(),
    owner:Random.last(),
    status:Mock.Random.integer(0, 1)
  }));
}
const DataSourcesData={
  "code": 200,
  "success": true,
  "msg": "处理成功",
  "data": DataSources
  }
export { DataSourcesData };
