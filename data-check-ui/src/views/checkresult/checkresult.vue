<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="filters" :rules="queryFormRules" ref="filters">
 
				<el-form-item>
					<el-input placeholder="校验信息代码" v-model="filters.code" clearable></el-input>
				</el-form-item>
				
				<el-form-item label="执行日期：" prop="starttime">
					<div class="block">
						<el-date-picker v-model="filters.starttime" align="right" type="date" placeholder="选择日期" ></el-date-picker>
					
					</div>
				</el-form-item>
				<el-form-item label="至：" prop="endtime">
					<div class="block">						
						<el-date-picker v-model="filters.endtime" align="right" type="date" placeholder="选择日期" >
						</el-date-picker>
					</div>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="getCheckResults">查询</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<el-table :data="parties" highlight-current-row v-loading="listLoading"  style="width: 100%;" >
         <el-table-column  type="index" :index="indexMethod" min-width="70" label="序号"align="center"> 
			 </el-table-column>
			<el-table-column prop="appname" label="应用系统" min-width="120"align="center"sortable>
			</el-table-column>
			<el-table-column prop="code" label="校验信息代码" min-width="120"align="center" sortable>
			</el-table-column>
			<el-table-column prop="intent" label="校验目的" min-width="120"align="center" sortable >
			</el-table-column>
			<el-table-column prop="starttime" label="任务执行开始时间" min-width="140"align="center"sortable>
			</el-table-column>
			<el-table-column prop="endtime" label="任务执行结束时间" min-width="140" align="center"sortable>
		    </el-table-column>
			<el-table-column prop="reason" label="任务执行结果" min-width="120" align="center"sortable>
			</el-table-column>
			<el-table-column prop="count" label="报警条数" min-width="100" align="center"sortable>
		    </el-table-column>
		</el-table>    
			<el-col :span="24" class="toolbar">
			<el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" @prev-click="prevchange" @next-click="nextchange" :page-size="this.size" :total="this.total" style="float:right;">
			</el-pagination>
		</el-col>      
	</section>
</template>

<script>
	import util from '../../common/js/util';
	import http from '../../util/http.js';
	//import NProgress from 'nprogress'
	// import { getUserListPage, removeUser, batchRemoveUser, editUser, addUser } from '../../api/api';
		let url = {
		app:'http://'+http.host_port+'/datasource/appList'
	}
	export default {
		data() {
			return {
				filters: {
					//app:[],
				    appid:'',
					code: '',
					starttime:'',
					endtime:''
					
				},
				appName:'',
				appLists:[],
				tempList:[],
				parties: [],
				total: 0,
				page: 1,
				size:20,
				listLoading: false,
				sels: [],//列表选中列			
				queryFormRules: {
					starttime: [
						{required: true, 
						type:'date',
						 message: '请选择起始日期', 
						 trigger: 'blur'},
					],
					endtime: [
						{ required: true, 
						  type:'date',
						  message: '请选择结束日期', 
						  trigger: 'blur' },
					]
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
			  	nextchange(val) {
				this.page = val;
				this.getCheckResults();
			},
			prevchange(val) {
				this.page = val;
			this.getCheckResults();
			},
			handleCurrentChange(val) {
				this.page = val;
				this.getCheckResults();
			},
/*remoteMethod(query){
				if (query !== ''){
					this.listLoading = true;
					let para = {
						app_sname:query
					};
					if(this.tempList.length!=0){
					   this.appLists=this.tempList;
					   //console.debug(this.tempList);
					}else{
					this.$fetch(url.app,para).then((res) => {
						this.listLoading = false;
						this.appLists = res; 
						this.tempList=res;
						//localStorage.setItem('applists1',res);
						////console.debug(res);
					});}
				}else{
					this.$fetch(url.app).then((res) => {
						this.listLoading = false;
						this.appLists = res; 
					});
				};

			},*/
			//获取用户列表
			getCheckResults() {
				let para = {
//page: this.page,
					//appid: this.filters.appid,
					begin:(this.page-1)*this.size ,
				    end:this.size,
					code: this.filters.code,
					starttime: util.formatDate.format(this.filters.starttime,'yyyy-MM-dd hh:mm:ss'),
					endtime:util.formatDate.format(this.filters.endtime,'yyyy-MM-dd hh:mm:ss')
				};
				this.$fetch('http://'+http.host_port+'/checkresult/total',para).then((res)=>{  //获取分页所需的total
									this.total=parseInt(res);					
				});
				////console.debug(this.total)
			  // //console.debug(para);
			  // //console.debug(this.filters.endtime);
				this.listLoading = true;
				//NProgress.start();
				
				this.$fetch('http://'+http.host_port+'/checkresult/query',para).then((res) => {
					//this.total = res.total;
					this.parties = res;
					this.listLoading = false;
					////console.log(this.parties);
					//NProgress.done();
				});
			//	this.remoteMethod();	
			},			
		},
		mounted() {
			//this.remoteMethod();
		//	this.getCheckResults()
		}
	}

</script>

<style scoped>
 
</style>