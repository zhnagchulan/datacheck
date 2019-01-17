<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="filters">
				<el-form-item>
					<el-select placeholder="公司名称"filterable="true" v-model="filters.branch_name" clearable>
					  <el-option v-for="item in BranchName" :key="item.value" :label="item.label" :value="item.value"></el-option>
					</el-select>
				</el-form-item>
			
				<el-form-item>
					<el-input placeholder="工号" v-model="filters.workNumber" clearable></el-input>
				</el-form-item>
				
				<el-form-item>
					<el-input placeholder="姓名" v-model="filters.workerName" clearable></el-input>
				</el-form-item>
				
				<el-form-item>
					<el-input placeholder="手机号" v-model="filters.workerPhoNum" clearable></el-input>
				</el-form-item>
				
				<el-form-item>
					<el-input placeholder="邮箱" v-model="filters.workerEmail" clearable></el-input>
				</el-form-item>

				<el-form-item>
					<el-button type="primary" v-on:click="getUsers">查询</el-button>
				</el-form-item>
				
				<el-form-item>
					<el-button type="primary" @click="handleAdd">新增关系人</el-button>
				</el-form-item>
				
			</el-form>
		</el-col>

		<!--列表-->
		<el-table :data="parties" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;" >
			<el-table-column type="selection"  width="40">
			</el-table-column>
			 <el-table-column  type="index" :index="indexMethod" min-width="70" label="序号"align="center"> 
			 </el-table-column>
			<el-table-column prop="branch_name" label="公司名称" width="120" sortable>
			</el-table-column>
			<el-table-column prop="workNumber" label="工号" width="120" sortable>
			</el-table-column>
			<el-table-column prop="workerName" label="姓名" width="140" sortable>
			</el-table-column>
			<el-table-column prop="workerPhoNum" label="手机" width="140" sortable>
			</el-table-column>
			<el-table-column prop="workerEmail" label="邮箱" width="240" sortable>
		    </el-table-column>
			<el-table-column label="操作" width="170">
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

		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0">批量删除</el-button>   
			<el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" @prev-click="prevchange" @next-click="nextchange" :page-size="this.size" :total="this.total" style="float:right;">
			</el-pagination>
		</el-col>      
	

		<!--新增界面-->
        <el-dialog title="新增关系人" :visible="addFormVisible" :show-close="false">
            <el-form :model="addForm" label-width="80px" :rules="addFormRules" ref="addForm">
 			    <el-form-item label="公司名称" prop="branch_name">
            		<el-select v-model="addForm.branch_name" filterable="true" clearable>
					  <el-option v-for="item in BranchName" :key="item.value" :label="item.label" :value="item.value"></el-option>
					</el-select>
        		</el-form-item>            
                <el-form-item label="工号" prop="workNumber">
            		<el-input v-model="addForm.workNumber" auto-complete="off"></el-input>
        		</el-form-item>
        		<el-form-item label="姓名" prop="workerName">
            		<el-input v-model="addForm.workerName" auto-complete="off"></el-input>
        		</el-form-item>
                <el-form-item label="手机" prop="workerPhoNum">
                    <el-input v-model="addForm.workerPhoNum" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="邮箱"  prop="workerEmail">
                    <el-input v-model="addForm.workerEmail" auto-complete="off"></el-input>
                </el-form-item>
            </el-form>
            
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="addFormVisible = false">取消</el-button>
                <el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
            </div>
        </el-dialog>
        
		<!--修改界面-->
        <el-dialog title="修改关系人" :visible="editFormVisible" :show-close="false">
            <el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">
              <!--  
              <el-form-item label="工号" prop="workNumber">
            		<el-input v-model="editForm.workNumber" auto-complete="off"></el-input>
        		</el-form-item>
        		<el-form-item label="姓名" prop="workerName">
            		<el-input v-model="editForm.workerName" auto-complete="off"></el-input>
        		</el-form-item> 
        		-->
                <el-form-item label="手机" prop="workerPhoNum">
                    <el-input v-model="editForm.workerPhoNum" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="workerEmail">
                    <el-input v-model="editForm.workerEmail" auto-complete="off"></el-input>
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
	 import http from '../../util/http.js';
	 
	//import NProgress from 'nprogress'
	// import { getUserListPage, removeUser, batchRemoveUser, editUser, addUser } from '../../api/api';

	export default {
		data() {
			return {
				filters: {
				    workNumber:'',
				    branch_name:'',
					workerName: '',
					workerPhoNum:'',
					workerEmail:''
				},
				userStatus: [
				{
          			value: '1',
          			label: '正常'
        		}, 
        		{
          			value: '2',
          			label: '停用'
        		}],
        		BranchName:[
					{value: '研发中心',
				  label: '研发中心'},
				  {value: '数据中心',
          		label: '数据中心'},
        		{value: '北京分公司',
          		label: '北京分公司'},
        		{value: '天津分公司',
          		label: '天津分公司'},
        		{value: '河北分公司',
          		label: '河北分公司'},
        		{value: '山西分公司',
          		label: '山西分公司'},
        		{value: '内蒙古分公司',
          		label: '内蒙古分公司'},
        		{value: '辽宁分公司',
          		label: '辽宁分公司'},
        		{value: '大连分公司',
          		label: '大连分公司'},
        		{value: '吉林分公司',
          		label: '吉林分公司'},
        		{value: '黑龙江分公司',
          		label: '黑龙江分公司'},
        		{value: '上海分公司',
          		label: '上海分公司'},
        		{value: '江苏分公司',
          		label: '江苏分公司'},
        		{value: '浙江分公司',
          		label: '浙江分公司'},
        		{value: '宁波分公司',
          		label: '宁波分公司'},
        		{value: '安徽分公司',
          		label: '安徽分公司'},
        		{value: '福建分公司',
          		label: '福建分公司'},
        		{value: '厦门分公司',
          		label: '厦门分公司'},
        		{value: '江西分公司',
          		label: '江西分公司'},
        		{value: '山东分公司',
          		label: '山东分公司'},
        		{value: '青岛分公司',
          		label: '青岛分公司'},
        		{value: '河南分公司',
          		label: '河南分公司'},
        		{value: '湖北分公司',
          		label: '湖北分公司'},
        		{value: '湖南分公司',
          		label: '湖南分公司'},
        		{value: '广东分公司',
          		label: '广东分公司'},
        		{value: '深圳分公司',
          		label: '深圳分公司'},
        		{value: '广西分公司',
          		label: '广西分公司'},
        		{value: '海南分公司',
          		label: '海南分公司'},
        		{value: '四川分公司',
          		label: '四川分公司'},
        		{value: '贵州分公司',
          		label: '贵州分公司'},
        		{value: '云南分公司',
          		label: '云南分公司'},
        		{value: '陕西分公司',
          		label: '陕西分公司'},
        		{value: '甘肃分公司',
          		label: '甘肃分公司'},
        		{value: '青海分公司',
          		label: '青海分公司'},
        		{value: '宁夏分公司',
          		label: '宁夏分公司'},
        		{value: '新疆分公司',
          		label: '新疆分公司'},
        		{value: '重庆分公司',
          		label: '重庆分公司'},
        		{value: '西藏分公司',
          		label: '西藏分公司'},
          		{value: '总公司',
          		label: '总公司'}
        		],
				parties: [],
				total: 0,
				page: 1,
				size:20,
				listLoading: false,
				sels: [],//列表选中列

				editFormVisible: false,//编辑界面是否显示
				editLoading: false,
				editFormRules: {
					workerPhoNum: [
						{required: true, 
						 message: '请输入手机号', 
						 trigger: 'blur'},
                        {validator:function(rule,value,callback){
                          if(/^1[34578]\d{9}$/.test(value) == false)
                            {callback(new Error("请输入正确的手机号"));}
                             else{callback();}
                         }, 
                         trigger: 'blur'}
					],
					workerEmail: [
						{ required: true, 
						  message: '请输邮箱地址', 
						  trigger: 'blur' },
						 { type: 'email', 
						   message: '请输入正确的邮箱地址', 
						   trigger: ['blur', 'change'] }
					]
				},
				
				//编辑界面数据
				editForm: {
				    //workNumber:'',
					workerPhoNum: '',
					workerEmail: ''
				},
				centerDialogVisible:false,
				
				addFormVisible: false,//新增界面是否显示
				addLoading: false,
				addFormRules: {
				    branch_name: [
                        { required: true, 
                          message: '请选择机构', 
                          trigger: 'change' }
                    ],
                    workNumber: [
						{ required: true, 
						message: '请输入工号'}
					],
					workerName: [
						{ required: true, 
						message: '请输入姓名',
                        trigger: 'blur' },
						{ min: 1,
						  max: 16, 
						  message: '姓名长度在 1 到 16个字符', 
						  trigger: 'blur' }
					],
					workerPhoNum: [
					    {required: true, 
					      message: '请输入手机号', 
					      trigger: 'blur'},
                        {validator:function(rule,value,callback){
                          if(/^1[34578]\d{9}$/.test(value) == false)
                           {callback(new Error("请输入正确的手机号"));}
                           else{callback();}
                         }, 
                        trigger: 'blur'}
					],
					workerEmail: [
						{ required: true, 
						  message: '请输入邮箱', 
						  trigger: 'blur' },
						{ type: 'email', 
						  message: '请输入正确的邮箱地址', 
						  trigger: ['blur', 'change'] }
					]
				},
				
				//新增界面数据
				addForm: {
				    workNumber:'',
				    branch_name:'',
					workerName: '',
					workerPhoNum: '',
					workerEmail: ''
				}

			}
		},
		
		methods: {
			userCheck: function(row,index){
				return row.status==1;
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
			//获取用户列表
			getUsers() {
				let para = {
					page: this.page,
					branch_name: this.filters.branch_name,
					workNumber: this.filters.workNumber,
					workerName: this.filters.workerName,
					workerPhoNum: this.filters.workerPhoNum,
					workerEmail: this.filters.workerEmail,
					begin:(this.page-1)*this.size ,
				    end:this.size,
				};
					this.$fetch('http://'+http.host_port+'/party/total',para).then((res)=>{  //获取分页所需的total
									this.total=parseInt(res);	
													
				});
			
				this.listLoading = true;
				//NProgress.start();
				this.$fetch('http://'+http.host_port+'/party/listpage',para).then((res) => {
					//this.total = res.total
					this.parties = res;
					this.listLoading = false;
					////console.log(this.parties);
					//NProgress.done();
					
				});
			},
			
			//删除
			handleDel: function (index, row) {
			var del_tmp = Object.assign({}, row);
				this.$confirm('确认删除该记录吗?', '提示', {
					type: 'warning'
				}).then(() => {
					this.listLoading = true;
					let para = { workNumber: del_tmp.workNumber };
					this.$fetch('http://'+http.host_port+'/party/delete',para).then((res) => {
						this.listLoading = false;
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
				this.editFormVisible = true;
				this.editForm = Object.assign({}, row);
			},
			//显示新增界面
			handleAdd: function () {
				this.addFormVisible = true;
				this.addForm = {};
			},
			
			//编辑
			editSubmit: function () {
				this.$refs.editForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							this.editLoading = true;
							let para = Object.assign({}, this.editForm);
							this.$post('http://'+http.host_port+'/party/edit',para).then((res) => {
								this.editLoading = false;
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
							let para = Object.assign({}, this.addForm);
							this.$post('http://'+http.host_port+'/party/add',para).then((res) => {
								this.addLoading = false;
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
				var workNumbers = this.sels.map(item => item.workNumber).toString();
				this.$confirm('确认删除选中记录吗？', '提示', {
					type: 'warning'
				}).then(() => {
					this.listLoading = true;
					let para = { workNumbers: workNumbers };
					this.$fetch('http://'+http.host_port+'/party/batchremove',para).then((res) => {
						this.listLoading = false;
						this.$message({
							message: '删除成功',
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
		}
	}

</script>

<style scoped>
 
</style>