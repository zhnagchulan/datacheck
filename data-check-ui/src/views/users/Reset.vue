<template>
	<section>
		<el-container style="padding-bottom: 0px height: 400px; border: 1px solid #eee">
			<el-main>
				<el-form :model="editForm" label-width="50px" :rules="editFormRules" ref="editForm">
        		<el-form-item label="工号" prop="id">
            		<el-input v-model="editForm.id" :disabled="true" style="width:500px" auto-complete="off"></el-input>
        		</el-form-item>
        		<el-form-item label="姓名" prop="username">
            		<el-input v-model="editForm.username" style="width:500px" auto-complete="off"></el-input>
        		</el-form-item>
        		<el-form-item label="密码" prop="pwd">
            		<el-input type='password' v-model="editForm.pwd" style="width:500px" auto-complete="off"></el-input>
        		</el-form-item>
                <el-form-item label="手机" prop="mobile">
                    <el-input v-model="editForm.mobile" style="width:500px" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="editForm.email" style="width:500px"  auto-complete="off"></el-input>
                </el-form-item>
            </el-form>
            <el-button type="primary" @click.native="editSubmit" :loading="editLoading">用户重置</el-button>
			</el-main>	
		</el-container>         
	</section>
</template>

<script>
	import util from '../../common/js/util';
	 import http from '../../util/http.js';
	//import NProgress from 'nprogress'
	
	// import { getUserListPage, removeUser, batchRemoveUser, editUser, addUser } from '../../api/api';
	let url = {
		//get:'http://127.0.0.1:8888/user/get',
		//edit:'http://127.0.0.1:8888/user/edit'
		get:'http://'+http.host_port+'/user/get',
		edit:'http://'+http.host_port+'/user/edit'
	}
	export default {
		data() {
			return {
				userStatus: [{
          			value: '1',
          			label: '正常'
        		}, {
          			value: '2',
          			label: '停用'
        		}],
				listLoading: false,

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
						pwd: [
						{ required: true, message: '请输入密码', trigger: 'blur' },
						
					]
					
				},
				//编辑界面数据
				editForm: {
					id: '',
					username: '',
					mobile: '',
					email:'',
					pwd:''
				},
			}
		},
		methods: {
			//获取用户列表
			getUser() {
				var user = sessionStorage.getItem('user');
			    user = JSON.parse(user);
			    //console.log(user);
				//this.editForm = user;

				let para = {
					id: user.id,
				};
				this.listLoading = true;
				//NProgress.start();
				this.$fetch(url.get,para).then((res) => {
					this.editForm = res;
					this.listLoading = false;
					sessionStorage.setItem('user', JSON.stringify(res));
					//NProgress.done();
				});
			},
			//编辑
			editSubmit: function () {
				this.$refs.editForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认修改个人信息吗？', '提示', {}).then(() => {
							this.editLoading = true;
							//NProgress.start();
							let para = Object.assign({}, this.editForm);
							//para.birth = (!para.birth || para.birth == '') ? '' : util.formatDate.format(new Date(para.birth), 'yyyy-MM-dd');
							this.$post(url.edit,para).then((res) => {
								this.editLoading = false;
								//NProgress.done();
								this.$message({
									message: '提交成功',
									type: 'success'
								});
								this.$refs['editForm'].resetFields();
								this.editFormVisible = false;
								this.getUser();
							});
						});
					}
				});
			},
		},
		mounted() {
			this.getUser();
		}
	}

</script>

<style scoped>
 
</style>