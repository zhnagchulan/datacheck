<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="filters">
				<el-form-item>
					<el-input placeholder="数据源名称或应用系统中文名称" v-model="filters.name" clearable></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="getList">查询</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="handleAdd">新增</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<el-table :data="datasources" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;" :row-class-name="showRow">
            <el-table-column  type="index" :index="indexMethod" min-width="30" label="序号"align="center"> 
			 </el-table-column>
			<el-table-column prop="owner" label="应用系统名称" width="130" align="center"sortable>
			</el-table-column>
			<el-table-column prop="code" label="数据源名称" width="150" align="center"sortable>
			</el-table-column>
			<el-table-column prop="dbType" label="数据库类型" width="120" align="center"sortable>
			</el-table-column>
			</el-table-column>
			<el-table-column prop="delete_flag" label="状态(1启用,2停用)" min-width="100" align="center" sortable>
			</el-table-column>
			<el-table-column prop="dbInfo" label="数据源IP" width="150" align="center"sortable>
			</el-table-column>
			<el-table-column prop="port" label="端口号" align="center"min-width="70">
			</el-table-column>
			<el-table-column prop="userName" label="数据库用户"align="center" width="140">
			</el-table-column>
			<el-table-column prop="sid" label="实例名" align="center"width="140">
			</el-table-column>
			<el-table-column label="操作"align="center" width="170">
				<template slot-scope="scope">
					<el-tooltip content="编辑" placement="top"  effect="light">
						<el-button type="primary" icon="el-icon-edit" circle @click="handleEdit(scope.$index, scope.row)"></el-button>
					</el-tooltip>
					<el-tooltip  content="停用" placement="top"  effect="light">
					<el-button type="danger" icon="el-icon-delete":disabled="scope.row.delete_flag==2" circle @click="handleDel(scope.$index, scope.row)"></el-button>
					</el-tooltip>
					<el-tooltip  content="启用" placement="top"  effect="light">
						<el-button type="success" icon="el-icon-circle-check":disabled="scope.row.delete_flag==1" circle @click="handleEable(scope.$index, scope.row)"></el-button>
					</el-tooltip>
				</template>
			</el-table-column>
		</el-table>
			<el-col :span="24" class="toolbar">
			<el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" @prev-click="prevchange" @next-click="nextchange" :page-size="this.size" :total="this.total" style="float:right;">
			</el-pagination>
		</el-col>      
		<!--新增界面-->
        <el-dialog title="新增数据源" :visible="addFormVisible" :show-close="false">
            <el-form :model="addForm" label-width="100px" :rules="addFormRules" ref="addForm">
        		<el-form-item label="数据源名称" prop="code">
            		<el-input v-model="addForm.code" auto-complete="off"></el-input>
        		</el-form-item>
        		<el-form-item label="数据源IP" prop="dbInfo">
            		<el-input v-model="addForm.dbInfo" auto-complete="off" ></el-input>
        		</el-form-item>
        		<el-form-item label="端口" prop="port">
            		<el-input v-model="addForm.port" auto-complete="off" @blur="checkNum(addForm)" ></el-input>
        		</el-form-item>
                <el-form-item label="实例名" prop="sid">
                    <el-input v-model="addForm.sid" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="用户名" prop="userName">
                    <el-input v-model="addForm.userName" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input type="password" v-model="addForm.password"></el-input>
                </el-form-item>
                <el-form-item label="数据库类型" prop="dbType">
                    <el-select v-model="addForm.dbType" filterable="true" placeholder="请选择">
                        <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.label">
                        </el-option>
                    </el-select>
                </el-form-item>
                 <el-form-item label="应用系统" prop="owner">
                    <el-select v-model="addForm.owner" filterable="true" placeholder="请选择">
                        <el-option v-for="item in this.apps" :key="item.id" :label="item.app_sname" :value="item.app_sname">
							<span style="float: left">{{ item.app_sname }}</span>
                        	<span style="float: right; color: #8492a6; font-size: 13px">{{ item.app_name }}</span>
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="addFormVisible = false">取消</el-button>
                <el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
            </div>
        </el-dialog>
		<!--修改界面-->
        <el-dialog title="修改数据源" :visible="editFormVisible" :show-close="false">
            <el-form :model="editForm" label-width="100px" :rules="editFormRules" ref="editForm">
        		<el-form-item label="数据源名称" prop="code">
            		<el-input v-model="editForm.code" auto-complete="off"  :disabled="true"></el-input>
        		</el-form-item>
        		<el-form-item label="数据源IP" prop="dbInfo">
            		<el-input v-model="editForm.dbInfo" auto-complete="off"></el-input>
        		</el-form-item>
        		<el-form-item label="端口" prop="port">
            		<el-input v-model="editForm.port" auto-complete="off"@blur="checkNum(editForm)"></el-input>
        		</el-form-item>
                <el-form-item label="实例名" prop="sid">
                    <el-input v-model="editForm.sid" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="用户名" prop="userName">
                    <el-input v-model="editForm.userName" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input type="password" v-model="editForm.password"></el-input>
                </el-form-item>
                <el-form-item label="数据库类型" prop="dbType">
                    <el-select v-model="editForm.dbType" placeholder="请选择">
                        <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.label">
                        </el-option>
                    </el-select>
                </el-form-item>
                 <el-form-item label="应用系统" prop="owner">
                    <el-select v-model="editForm.owner"filterable="true"placeholder="请选择" :disabled="true">
                        <el-option v-for="item in this.apps" :key="item.id" :label="item.app_sname" :value="item.app_sname">
							<span style="float: left">{{ item.app_sname }}</span>
                        	<span style="float: right; color: #8492a6; font-size: 13px">{{ item.app_name }}</span>
                        </el-option>
                    </el-select>
                </el-form-item>
              
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="editFormVisible = false">取消</el-button>
                <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
            </div>
        </el-dialog>
	</section>
</template>

<script>
	import util from '../../common/js/util';
	import md5 from 'js-md5';
	
let Base64 = require('js-base64').Base64;
 import http from '../../util/http.js';

	//import NProgress from 'nprogress'
	// import { getUserListPage, removeUser, batchRemoveUser, editUser, addUser } from '../../api/api';
	
	let url={
		query:'http://'+http.host_port+'/datasource/query',
		update:'http://'+http.host_port+'/datasource/update',
		delete:'http://'+http.host_port+'/datasource/disabled',
		app:'http://'+http.host_port+'/datasource/appList',
		enable:'http://'+http.host_port+'/datasource/enable',
		total:'http://'+http.host_port+'/datasource/total'
	}
	export default {
		data() {
			return {
				filters: {
					name: ''
				},
        		options: [{
        			value: '选项1',
        			label: 'ORACLE'
        		}, {
        		    value: '选项2',
        		    label: 'MYSQL'
        		},{
        		    value: '选项3',
        		    label: 'MONGODB'
        		},{
        		    value: '选项4',
        		    label: 'SQLSERVER'
        		},{
        		    value: '选项5',
        		    label: 'PostgreSQL'
        		}],
        		apps: [],
        		user:[],
        		list:[],
        		appName:'',
				datasources: [],
				total: 0,
				page: 1,
				size:20,
				listLoading: false,
				//sels: [],//列表选中列

				editFormVisible: false,//编辑界面是否显示
				editLoading: false,
				editFormRules: {
					
					userName: [
						{ required: true, message: '请输入姓名', trigger: 'blur' }
					],
						code: [
						{ required: true, message: '请输入数据源名称', trigger: 'blur' }
					],
					dbInfo: [
						{ required: true, message: '请输入IP', trigger: 'blur' }
					],
					port: [
						{ required: true, message: '请输入端口', trigger: 'blur' }
					],
					sid: [
						{ required: true, message: '请输入实例名', trigger: 'blur' }
					],
					dbType: [
						{ required: true, message: '请选择数据库类型', trigger: 'blur' }
					],
					owner: [
						{ required: true, message: '请选择应用系统', trigger: 'blur' }
					]
				},
				//编辑界面数据
				editForm: {
					dbInfo: '',
					userName: '',
					sid: '',
					code: '',
					port: '',
					password: '',
					owner:'',
					delete_flag:''
				},
				centerDialogVisible:false,
				addFormVisible: false,//新增界面是否显示
				addLoading: false,
				addFormRules: {
					code: [
						{ required: true, message: '请输入数据源名称', trigger: 'blur' }
					],
					dbInfo: [
						{ required: true, message: '请输入IP', trigger: 'blur' }
					],
						userName: [
						{ required: true, message: '请输入姓名', trigger: 'blur' }
					],
					port: [
						{ required: true, message: '请输入端口', trigger: 'blur' }
					],
					sid: [
						{ required: true, message: '请输入实例名', trigger: 'blur' }
					],
					password: [
						{ required: true, message: '请输入密码', trigger: 'blur' }
					],
					dbType: [
						{ required: true, message: '请选择数据库类型', trigger: 'blur' }
					],
					owner: [
						{ required: true, message: '请选择应用系统', trigger: 'blur' }
					]
				},
				//新增界面数据
				addForm: {
					dbInfo: '',
					userName: '',
					sid: '',
					code: '',
					port: '',
					password: '',
					owner:'',
					dbType:''

				},
				tempApplist:[]

			}
		},
			
		methods: {
			checkNum(form){
						form.port=new String(form.port).replace(/[^\d]/g,'');
					
					},
			showRow: function({row, rowIndex}){
				return row.status == 1 ? '' : 'info';
			},
		
			
			indexMethod(index) {
        		return (this.page-1)*this.size+index+1 ;
      		},
			handleCurrentChange(val) {
				this.page = val;
				this.getList();
			},
			nextchange(val) {
				this.page = val;
				this.getList();
			},
			prevchange(val) {
				this.page = val;
		     	this.getList();
			},
			filterParams(obj){
				let _form = obj, _newPar = {}, testStr;
				//遍历对象
				for (let key in _form) {
				    testStr = null;
				    //如果属性的值不为空。
				    //注意，不要这样判断if (_form[key])。因为有些属性的值可能为0，到时候就会被过滤掉
				    if (_form[key] !== null && _form[key] !== "") {
				        //把值添加进新对象里面
				        _newPar[key]=_form[key].toString()
				    }
				}
				//返回对象
				return _newPar;
			},
		
			remoteMethod(query){
		// ////////console.debug(Base64.encode("444"));
	 //////////console.debug(Base64.decode(Base64.encode("444")));
				var user = sessionStorage.getItem('user');
				user = JSON.parse(user);
				////////console.debug(this.tempApplist);
				if(this.tempApplist.length==0){
				if (query !== ''){
					this.listLoading = true;
					let para = {
						user_id:user.id,
						app_sname:''
					};
					
					this.$fetch(url.app,para).then((res) => {
						this.listLoading = false;
						this.apps = res; 
					//	////////console.debug(res);
						this.tempApplist=res;
					});
				}else{
					this.$fetch(url.app,para).then((res) => {
						this.listLoading = false;
						this.apps = res; 
						this.tempApplist=res;
					});
				};}else{
					this.apps=this.tempApplist;
				}

			},
			//获取用户列表
			getList() {
				var user = sessionStorage.getItem('user');
				user = JSON.parse(user);
				this.user = user;
				//////////console.log(user);
				let para = Object.assign(this.filterParams(this.filters),{
					id:user.id,
					begin:(this.page-1)*this.size ,
				    end:this.size,
				});
					this.$fetch(url.total,para).then((res)=>{  //获取分页所需的total
									this.total=parseInt(res);	
										//////console.debug(parseInt(res));				
				});
				//////console.debug(this.total);
			
				//////////console.log(para);
				this.listLoading = true;
				//NProgress.start();
				this.$fetch(url.query,para).then((res) => {
					//this.total = res.total;
					//////////console.log(res[0].code);
				//	////////console.debug(res);
					this.datasources = res;
					this.listLoading = false;
					//NProgress.done();
				});
				this.remoteMethod();
			},
			//停用
			handleDel: function (index, row) {
				var del_tmp = Object.assign({}, row);
				this.$confirm('确认停用该记录吗?', '提示', {
					type: 'warning'
				}).then(() => {
					this.listLoading = true;
					//NProgress.start();
					let para = { id: del_tmp.id };
					//////////console.log(del_tmp.id);
					this.$fetch(url.delete,para).then((res) => {
						this.listLoading = false;
						//NProgress.done();
						this.$message({
							message: '停用成功',
							type: 'success'
						});
						this.getList();
					});
				}).catch(() => {

				});
			},
				handleEable: function (index, row) {
				this.$confirm('确认启用该记录吗?', '提示', {
					type: 'warning'
				}).then(() => {
					this.listLoading = true;
					//NProgress.start();
						var enable_tmp = Object.assign({}, row);
					let para = {id:enable_tmp.id};
					this.$fetch(url.enable,para).then((res) => {
						this.listLoading = false;	
					this.$message({
							message: '启用成功',
							type: 'success'
						});
						this.getList();
					});
				}).catch(() => {

				});
			},
			//显示编辑界面
			handleEdit: function (index, row) {
				this.editFormVisible = true;
				this.editForm = Object.assign({}, row);
				this.editForm.password=Base64.decode(this.editForm.password);
				//////console.debug(JSON.stringify(this.editForm));
				//this.editForm.owner = this.user.app;
				//////////console.log(this.editForm.owner[0]);
			/*	for(let key in this.apps){
					if(this.apps[key].id == this.editForm.owner[0]){
						this.appName = this.apps[key].app_sname;
						//////////console.log(this.appName);
					}
				}*/
			},
			//显示新增界面
			handleAdd: function () {
				this.addFormVisible = true;
				//this.addForm = {};
				//this.addForm.owner = this.user.app;
				//////////console.log(this.addForm.owner[0]);
				/*for(let key in this.apps){
					if(this.apps[key].id == this.addForm.owner[0]){
						this.appName = this.apps[key].app_sname;
						//////////console.log(this.appName);
					}
				}*/
			},
			//编辑
			editSubmit: function () {
				//var user = sessionStorage.getItem('user');
				//user = JSON.parse(user);
				this.$refs.editForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							this.editLoading = true;
							//NProgress.start();
							//this.editForm.owner=this.editForm.owner[0];
							//////////console.log(this.editForm);
							this.editForm.password=Base64.encode(this.editForm.password);
							let para = Object.assign({}, this.editForm);/*,{
								//user_id:user.id,
								password:Base64.encode(this.editForm.password),
							});*/
							//////////console.log(md5(this.editForm.password));
							//para.birth = (!para.birth || para.birth == '') ? '' : util.formatDate.format(new Date(para.birth), 'yyyy-MM-dd');
							this.$post(url.update,para).then((res) => {
								this.editLoading = false;
								//NProgress.done();
								this.$message({
									message: '提交成功',
									type: 'success'
								});
								this.$refs['editForm'].resetFields();
								this.editFormVisible = false;
								this.getList();
							});
						});
					}
				});
			},
			//新增
			addSubmit: function () {
				//var user = sessionStorage.getItem('user');
				//user = JSON.parse(user);
				this.$refs.addForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							this.addLoading = true;
							//NProgress.start();
						//	this.addForm.owner=this.addForm.owner[0];
							this.addForm.password=Base64.encode(this.addForm.password);
							let para = Object.assign({}, this.addForm);//,{
								//user_id:user.id,
								//password:Base64.encode(this.editForm.password)
							//});
							
							//para.birth = (!para.birth || para.birth == '') ? '' : util.formatDate.format(new Date(para.birth), 'yyyy-MM-dd');
							this.$post(url.update,para).then((res) => {
								this.addLoading = false;
								//NProgress.done();
								this.$message({
									message: '提交成功',
									type: 'success'
								});
								this.$refs['addForm'].resetFields();
								this.addFormVisible = false;
								this.getList();
							});
						});
					}
				});
			},
			selsChange: function (sels) {
				this.sels = sels;
			},
			
		},
		mounted() {
			this.getList();
		}
	}

</script>

<style scoped>
 
</style>