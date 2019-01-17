<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="filters">
				<el-form-item>
					<el-input placeholder="姓名" v-model="filters.username" clearable></el-input>
				</el-form-item>
				<el-form-item>
					<el-input placeholder="工号" v-model="filters.id" clearable></el-input>
				</el-form-item>
				<el-form-item>
					<el-select v-model="filters.status" clearable placeholder="用户状态">
    					<el-option v-for="item in userStatus" :key="item.value" :label="item.label" :value="item.value"></el-option>
  					</el-select>
  				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="getUsers">查询</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="handleAdd">新增</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<el-table :data="users" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;" :row-class-name="showRow">
		<!--	<el-table-column type="selection"  :selectable="userCheck">
			</el-table-column>-->
			<el-table-column  type="index" :index="indexMethod" width="70" label="序号"align="center">
			</el-table-column>
			<el-table-column prop="id" label="工号" width="110" sortable>
			</el-table-column>
			<el-table-column prop="username" label="姓名" width="110" sortable>
			</el-table-column>
			<el-table-column prop="mobile" label="手机" width="110" sortable>
			</el-table-column>
			<el-table-column prop="email" label="邮箱" width="180" sortable>
			</el-table-column>
		
			<el-table-column prop="role" label="用户权限" min-width="50" sortable>
			</el-table-column>
			<el-table-column prop="status" :formatter="formatStatus" label="用户状态" min-width="50" sortable>
			</el-table-column>
			<el-table-column label="操作" width="200">
				<template slot-scope="scope">
					<el-tooltip content="编辑" placement="top"  effect="light">
						<el-button type="primary" icon="el-icon-edit" circle @click="handleEdit(scope.$index, scope.row)"></el-button>
					</el-tooltip>
					<el-tooltip content="删除" placement="top"  effect="light">
						<el-button type="danger" icon="el-icon-delete" circle @click="handleDel(scope.$index, scope.row)"></el-button>
					</el-tooltip>
				</template>
			</el-table-column>
		</el-table>
		

		
		<el-col :span="24" class="toolbar">
		<!--	<el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0">批量停用</el-button>-->
			<el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" @prev-click="prevchange" @next-click="nextchange" :page-size="this.size" :total="this.total" style="float:right;">
			</el-pagination>
				</el-col>
            
		<!--新增界面-->
        <el-dialog title="新增用户" :visible="addFormVisible" :show-close="false">
            <el-form :model="addForm" label-width="80px" :rules="addFormRules" ref="addForm">
        		<el-form-item label="工号" prop="id">
            		<el-input v-model="addForm.id" auto-complete="off"></el-input>
        		</el-form-item>
        		<el-form-item label="姓名">
            		<el-input v-model="addForm.username" auto-complete="off"></el-input>
        		</el-form-item>
                <el-form-item label="手机" prop="mobile">
                    <el-input v-model="addForm.mobile" auto-complete="off"maxlength="11"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="addForm.email" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="用户权限" prop="role">
                    <el-radio-group v-model="addForm.role">
                		<el-radio class="radio" label="1" v-if="user.role==1">超级管理员</el-radio>
                		<el-radio class="radio" label="2" v-if="user.role==1">应用系统管理员</el-radio>
                		<el-radio class="radio" label="3" v-if="user.role==2">普通用户</el-radio>
            		</el-radio-group>
                </el-form-item>
                <el-form-item label="应用系统" prop="app"v-if="addForm.role!=1"  >
                    <el-select v-model="editForm.app" filterable="true" multiple="true" :loading="listLoading" placeholder="请选择应用系统" >
                        <el-option v-for="item in appLists" :key="item.id" :label="item.app_sname" :value="item.id.toString()">
                        	<span style="float: left">{{ item.app_sname }}</span>
                        	<span style="float: right; color: #8492a6; font-size: 13px">{{ item.app_name }}</span>
                        </el-option>
                    </el-select>
                  <!--  <el-radio class="radio" v-model="editForm.app" :label="user.app" disabled v-if="user.app>0">{{ appName }}</el-radio>-->
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="addFormVisible = false">取消</el-button>
                <el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
            </div>
        </el-dialog>
		<!--修改界面-->
        <el-dialog title="编辑用户" :visible="editFormVisible" :show-close="false">
            <el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">
            	<el-form-item label="工号" prop="id">
            		<el-input v-model="editForm.id" auto-complete="off" :disabled="true"></el-input>
        		</el-form-item>
        		<el-form-item label="姓名" prop="username">
            		<el-input v-model="editForm.username" auto-complete="off"></el-input>
        		</el-form-item>
        		<el-form-item label="手机" prop="mobile">
                    <el-input v-model="editForm.mobile" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="editForm.email" auto-complete="off"></el-input>
                </el-form-item>
        		<el-form-item label="用户权限" prop="role">
                    <el-radio-group v-model="editForm.role">
                		<el-radio class="radio" label="超级管理员" v-if="user.role==1">超级管理员</el-radio>
                		<el-radio class="radio" label="应用系统管理员" v-if="user.role==1">应用系统管理员</el-radio>
                		<el-radio class="radio" label="普通用户" v-if="user.role==2">普通用户</el-radio>
						
            		</el-radio-group>
                </el-form-item>
                <el-form-item label="用户状态" prop="status">
                    <el-radio-group v-model="editForm.status">
                		<el-radio class="radio" :label="1">正常</el-radio>
                		<el-radio class="radio" :label="0">停用</el-radio>
            		</el-radio-group>
                </el-form-item>
                <el-form-item label="应用系统" prop="app"v-if="editForm.role!='超级管理员'">
                    <el-select v-model="editForm.app" filterable="true" multiple="true" :loading="listLoading" placeholder="请选择应用系统">
                        <el-option v-for="item in appLists" :key="item.id" :label="item.app_sname" :value="item.id.toString()">
                        	<span style="float: left">{{ item.app_sname }}</span>
                        	<span style="float: right; color: #8492a6; font-size: 13px">{{ item.app_name }}</span>
                        </el-option>
                    </el-select>
<!--<el-radio class="radio" v-model="editForm.app" :label="user.app" disabled v-if="user.app>0">{{ appName }}</el-radio>-->
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
	import md5 from 'js-md5';
	import util from '../../common/js/util';
	 import http from '../../util/http.js';
	//import NProgress from 'nprogress'
	// import { getUserListPage, removeUser, batchRemoveUser, editUser, addUser } from '../../api/api';


	let url = {
		getLists:'http://'+http.host_port+'/user/getLists',
		addUser:'http://'+http.host_port+'/user/addUser',
		updateUsers:'http://'+http.host_port+'/user/updateUsers',
		deleteUser:'http://'+http.host_port+'/user/deleteUser',
		app:'http://'+http.host_port+'/datasource/appList',
		total:'http://'+http.host_port+'/user/total'
	
	}
	export default {
		data() {
			return {
				filters: {
					username: '',
					id: '',
					status: ''
				},
				userStatus: [{
          			value: '1',
          			label: '正常'
        		}, {
          			value: '0',
          			label: '停用'
        		}],
				users: [],
				user:[],
				total: 0,
				page: 1,
				size:20,
				listLoading: false,
				sels: [],//列表选中列
				appName:'',
				appLists:[],
				tempList:[],
				editFormVisible: false,//编辑界面是否显示
				editLoading: false,
				editFormRules: {
					id: [
						{ required: true, message: '请输入工号', trigger: 'blur' }
					],
					mobile: [
						{ required: true, message: '请输入手机号', trigger: 'blur' },
						 {validator:function(rule,value,callback){
                          if(/^1[34578]\d{9}$/.test(value) == false)
                            {callback(new Error("请输入正确的手机号"));}
                             else{callback();}
                         }, 
                         trigger: 'blur'}
					],
					email: [
						{ required: true, message: '请输入邮箱', trigger: 'blur' },
						 { type: 'email', 
						   message: '请输入正确的邮箱地址', 
						   trigger: ['blur', 'change'] }
					],
					role: [
						{ required: true, message: '请指定用户权限', trigger: 'blur' }
					],
					status: [
						{ required: true, message: '请指定用户状态', trigger: 'blur' }
					],
					
				},
				//编辑界面数据
				editForm: {
					id: '',
					username: '',
					mobile: '',
					email: 0,
					role: '',
					status:1,
					app:[],
					app_id:[]
				},
				centerDialogVisible:false,
				addFormVisible: false,//新增界面是否显示
				addLoading: false,
				addFormRules: {
					id: [
						{ required: true, message: '请输入工号', trigger: 'blur' }
					],
					mobile: [
						{ required: true, message: '请输入手机号', trigger: 'blur' },
						 {validator:function(rule,value,callback){
                          if(/^1[34578]\d{9}$/.test(value) == false)
                            {callback(new Error("请输入正确的手机号"));}
                             else{callback();}
                         }, 
                         trigger: 'blur'}
						
					],
					email: [
						{ required: true, message: '请输入邮箱', trigger: 'blur' },
						 { type: 'email', 
						   message: '请输入正确的邮箱地址', 
						   trigger: ['blur', 'change'] }
					],
					role: [
						{ required: true, message: '请指定用户权限', trigger: 'blur' }
					],
					
				},
				//新增界面数据
				addForm: {
					id:'',
					username: '',
					mobile: '',
					email: 0,
					role: '',
					app:[]
				}

			}
		},
		methods: {
			showRow: function({row, rowIndex}){
				return row.status == 1 ? '' : 'info';
			},
			userCheck: function(row,index){
				return row.status==1;
			},
			//性别显示转换
			formatStatus: function (row, column) {
				return row.status == 1 ? '正常' : row.status == 0 ? '停用' : '未知';
			},
			indexMethod(index) {
        		return (this.page-1)*this.size+index+1 ;
      		},
			handleCurrentChange(val) {
				this.page = val;
				this.getUsers();
			},
				nextchange(val) {
				this.page = val;
			this.getUsers();
			},
			prevchange(val) {
				this.page = val;
		        this.getUsers();
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
			remoteMethod_add(query){
				//var user = sessionStorage.getItem('user');
				//user = JSON.parse(user);
				if (query !== ''){
					this.listLoading = true;
					let para = {
						//user_id:user.id,
						app_sname:query
					};
					if(this.tempList.length!=0){
                       this.list =this.tempList.map(item => {
							return { value: item.app_sname, label: item.app_sname };
						});
						this.apps = this.tempList;
					}else{
					this.$fetch(url.app,para).then((res) => {
						this.listLoading = false;
					
						this.list = res.map(item => {
							return { value: item.app_sname, label: item.app_sname };
						});
                       	this.tempList=res;
						////console.debug(this.list);
						this.apps = res;
					    //this.apps = this.list.filter(item => {
					    	//return item.label.toLowerCase().indexOf(query.toLowerCase()) > -1;
					    //});
					    ////console.log(user);   
					});}
				}else{
					this.apps = [];
				};

			},
			remoteMethod_mod(query){
				//var user = sessionStorage.getItem('user');
				//user = JSON.parse(user);
				if (query !== ''){
					this.listLoading = true;
					let para = {
						user_id:this.editForm.id,
						app_sname:query
					};
						if(this.tempList.length!=0){
                      this.listLoading = false;
						this.apps=this.tempList;
					}else{
					this.$fetch(url.app,para).then((res) => {
						this.listLoading = false;
						this.apps = res;
						this.tempList=res;
					    //this.editForm.app = this.list.filter(item => {
					    	//return item.label.toLowerCase().indexOf(query.toLowerCase()) > -1;
					    //});
					    ////console.log(this.apps);   
					});}
				}else{
					//this.apps = this.editForm.app;
					////console.log(this.apps); 
				};

			},
			remoteMethod(query){
				if(this.tempList.length!=0){
                       this.appLists=this.tempList;
				}else{
				if (query != ''){
					this.listLoading = true;
					var uid='';
					if(JSON.parse(sessionStorage.getItem("user")).role==2){
							var uid=JSON.parse(sessionStorage.getItem("user")).id;
					}
				
					let para = {
						app_sname:query,
					user_id:uid
					};
				
					this.$fetch(url.app,para).then((res) => {
						this.listLoading = false;
						////console.debug(res);
						this.appLists=res;
						this.tempList=res;
					//	this.appLists = JSON.parse(JSON.stringify(res)); 

////console.debug(this.appLists);
					});
				}else{
					this.$fetch(url.app).then((res) => {
						this.listLoading = false;
						this.appLists = res; 
					});
				};
				}
			},
			//获取用户列表
			getUsers() {
				var user = sessionStorage.getItem('user');
				user = JSON.parse(user);
				this.user = user;
				//this.addForm.app = user.app;
				////console.log(this.addForm.app);
				////console.log(md5('123456'));
				//e10adc3949ba59abbe56e057f20f883e
				let para = Object.assign(this.filterParams(this.filters),{
					role:user.role,
					userid:user.id,
					begin:(this.page-1)*this.size ,
				    end:this.size
				});
					this.$fetch(url.total,para).then((res)=>{  //获取分页所需的total
									this.total=parseInt(res);	
////console.debug(this.total);
													
				});
				this.listLoading = true;
				//NProgress.start();
				this.$fetch(url.getLists,para).then((res) => {
					//this.total = res.total;
					this.users = res;
					this.listLoading = false;
					////console.debug(JSON.stringify(res));
					//NProgress.done();
				});
				this.remoteMethod();
			},
			//删除
			handleDel: function (index, row) {
				var del_tmp = Object.assign({}, row);
				this.$confirm('确认删除该记录吗?', '提示', {
					type: 'warning'
				}).then(() => {
					this.listLoading = true;
					//NProgress.start();
					let para = { id: del_tmp.id };
					this.$fetch(url.deleteUser,para).then((res) => {
						this.listLoading = false;
						//NProgress.done();
						this.$message({
							message: '删除成功',
							type: 'success'
						});
						this.getUsers();
					});
				}).catch(() => {

				});
			},
			//显示编辑界面

			handleEdit: function (index, row) {
				//this.apps = [];
			  
				this.editForm = Object.assign({}, row);
				//console.debug(this.editForm);
				//console.debug(row.app);
				/*
				*下面代码用于显示页面超级管理员为应用系统管理员分配系统时，在系统下拉框显示哪些系统已经分配了
				*/ 
				var temp=row.app;
				this.editForm.app=[];
				for(let key in temp){
					for (let index = 0; index < this.appLists.length; index++) {
						if(this.appLists[index].id == temp[key]){
						this.editForm.app.push(this.appLists[index].app_sname);
						//console.debug(this.editForm.app)
						break;
					
					}
					
							}
					}
			
						this.editFormVisible = true;
				//this.apps = this.editForm.app;
				////console.log(this.apps);
			},
			
			//显示新增界面
			handleAdd: function () {
               this.addFormVisible = true;
			},
			//编辑
			editSubmit: function () {
				this.$refs.editForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							this.editLoading = true;
							//NProgress.start();
							let para = Object.assign({}, this.editForm);
							//para.birth = (!para.birth || para.birth == '') ? '' : util.formatDate.format(new Date(para.birth), 'yyyy-MM-dd');
							this.$post(url.updateUsers,para).then((res) => {
								this.editLoading = false;
								//NProgress.done();
								this.$message({
									message: '提交成功',
									type: 'success'
								});
								this.$refs['editForm'].resetFields();
								this.editFormVisible = false;
								this.getUsers();
							});
						});
					}
				});
			},
			//新增
			addSubmit: function () {
				this.$refs.addForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							this.addLoading = true;
							//NProgress.start();
							let para = Object.assign({}, this.addForm);
							//para.birth = (!para.birth || para.birth == '') ? '' : util.formatDate.format(new Date(para.birth), 'yyyy-MM-dd');
							this.$post(url.addUser,para).then((res) => {
								this.addLoading = false;
								//NProgress.done();
								this.$message({
									message: '提交成功',
									type: 'success'
								});
								this.$refs['addForm'].resetFields();
								this.addFormVisible = false;
								this.getUsers();
							});
						});
					}
				});
			},
			selsChange: function (sels) {
				this.sels = sels;
			},
			//批量删除
			batchRemove: function () {
				var ids = this.sels.map(item => item.id).toString();
				this.$confirm('确认停用选中用户吗？', '提示', {
					type: 'warning'
				}).then(() => {
					this.listLoading = true;
					//NProgress.start();
					let para = { ids: ids };
					this.$fetch(url.deleteUser,para).then((res) => {
						this.listLoading = false;
						//NProgress.done();
						this.$message({
							message: '停用成功',
							type: 'success'
						});
						this.getUsers();
					});
				}).catch(() => {

				});
			}
		},
		mounted() {
			this.getUsers();
			this.remoteMethod();
		}
	}

</script>

<style scoped>
 
</style>