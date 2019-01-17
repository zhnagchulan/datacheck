<template>
	<section>
		<!--工具条-->
		
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="filters">
				<el-form-item label="系统名称" prop="" >
            <el-select v-model="system"filterable="true" placeholder="请选择" @focus="changesystem()" loading-text="加载中...">
             <el-option
              v-for="system in systemsforselect"
             :key="system.value"
             :label="system.label"
             :value="system.label">
             </el-option>
            </el-select>
        		</el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="checkRulesBysys">按系统查询</el-button>
				</el-form-item>
				<el-form-item label="校验规则代码">
					<el-input  v-model="filters.checkRule_code" clearable></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="getcheckRules">查询</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="handleAdd">新建校验规则</el-button>
				</el-form-item>
			</el-form>
		</el-col>
		

		<!--列表-->
		<el-table   :data="tabledata" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;" >
			<el-table-column type="selection">
			</el-table-column>
			<el-table-column  type="index" :index="indexMethod" min-width="70" label="序号"align="center"> 
			</el-table-column>
			<el-table-column prop="app_id" label="系统名称" min-width="150" align="center" sortable >
			</el-table-column>
			<el-table-column prop="checkRule_code" label="校验规则代码" min-width="130" align="center" sortable>
			</el-table-column>
			<el-table-column prop="is_enable" label="状态(1启用,2停用)" width="100" align="center" sortable>
			</el-table-column>
			<el-table-column prop="interval" label="执行间隔（分钟）" min-width="100" align="center">
			</el-table-column>
			<el-table-column prop="frequency" label="总执行次数(次)" min-width="100"align="center" >
			</el-table-column>
			<el-table-column prop="next_time" label="下一次执行时间" min-width="160"align="center" >
			</el-table-column>
			<el-table-column prop="intent" label="sql执行目的" min-width="170" align="center">
			</el-table-column>
			<el-table-column prop="first_time" label="第一次执行的时间" min-width="160" align="center" >
			</el-table-column>
			<el-table-column label="操作" width="170" align="center">
				<template slot-scope="scope">
					<el-tooltip  content="编辑" placement="top"  effect="light">
						<el-button type="primary" icon="el-icon-edit" circle @click="handleEdit(scope.$index, scope.row)"></el-button>
					</el-tooltip>
					<!--停用按钮设计到数据库的级联删除问题，当执行删除时，所有此校验规则产生的执行结果都将删除，为保留执行结果作为历史数据供查询
					不启用删除按钮
					 -->
					<!--<el-tooltip content="删除" placement="top"  effect="light" >
						<el-button type="danger" icon="el-icon-delete" circle @click="handleDel(scope.$index, scope.row)"></el-button>
					</el-tooltip>-->
					<el-tooltip  content="启用" placement="top"  effect="light">
						<el-button type="success" icon="el-icon-circle-check" circle @click="handleEable(scope.$index, scope.row)" :disabled="scope.row.is_enable==1"></el-button>
					</el-tooltip>
				</template>
			</el-table-column>
		</el-table>

		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-button type="danger" @click="batchdisable" :disabled="this.sels.length==0" >批量停用</el-button>
			<el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" @prev-click="prevchange" @next-click="nextchange" :page-size="this.size" :total="this.total" style="float:right;">
			</el-pagination>
		</el-col>

		<!--新增界面-->
        <el-dialog title="新增校验规则" :visible="addFormVisible" :show-close="false" v-loading="listLoading">
            <el-form :model="addForm" label-width="140px" :rules="addFormRules" ref="addForm">
        		<el-form-item label="系统名称" prop="">
            <el-select v-model="addForm.sys_name" filterable="true" placeholder="请选择" @change="listeningchange">
             <el-option
              v-for="app_id in systemnameStatus"
             :key="app_id.value"
             :label="app_id.label"
             :value="app_id.label">
             </el-option>
            </el-select>
        		</el-form-item>
        		 <el-form-item label="数据源" prop="datasource_code">
             <el-select v-model="addForm.datasource_code" filterable="true" placeholder="请选择">
             <el-option
              v-for="datasource in datasourceStatus"
             :key="datasource.value"
             :label="datasource.label"
             :value="datasource.label">
             </el-option>
            </el-select>		
        		</el-form-item>
        		<el-form-item label="状态标示" prop="is_enable">
            <el-select v-model="addForm.is_enable" placeholder="请选择">
             <el-option
              v-for="is_enable in statusStatus"
             :key="is_enable.value"
             :label="is_enable.label"
             :value="is_enable.value">
             </el-option>
            </el-select>
        		</el-form-item>
        		
        		<el-form-item label="关系人" prop="workerNames">
            		<el-checkbox-group v-model="addForm.workerNames">
                		    <el-checkbox v-for="stakeholder in stakeholders_For_selection"
							 :key="stakeholder.value"
							 :label="stakeholder.label"
							 ></el-checkbox>
            		</el-checkbox-group>
            </el-form-item>
            <el-form-item label="校验规则" prop="sql_statement">
            		<el-input
                type="textarea"
                :autosize="{ minRows: 2, maxRows: 10}"
                v-model="addForm.sql_statement" @blur="checkSql(addForm)">
            </el-input>
        		</el-form-item>
        		<el-form-item label="规则校验目的" prop="intent">
            	<el-input
                type="textarea"
                :autosize="{ minRows: 2, maxRows: 10}"
                v-model="addForm.intent">
            </el-input>
        		</el-form-item>
        		<el-form-item label="第一次执行时间" prop="first_time"style="width:50%;" >
            		<el-date-picker
                     v-model="addForm.first_time"
                     type="datetime"
                     placeholder="选择日期时间" @blur="checkTime(addForm)">
                </el-date-picker>
        		</el-form-item>
        		<el-form-item label="执行间隔（分钟）" prop="interval" style="width: 50%;"  >
            		<el-input v-model="addForm.interval" auto-complete="off" @blur="checkNum(addForm)" maxlength="9"></el-input>
				 </el-form-item>	
			<el-form-item label="总执行次数(次)" prop="frequency" style="width: 50%;"  >
            		<el-input maxlength="9"  v-model="addForm.frequency" auto-complete="off" @blur="checkNum(addForm)"></el-input>
            </el-form-item>
            <el-form-item label="短信/邮件通知" prop="is_message" style="width:50%">
             <el-select v-model="addForm.is_message" placeholder="请选择">
             <el-option
              v-for="alarm in alarmStatus"
             :key="alarm.value"
             :label="alarm.label"
             :value="alarm.value">
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
        <el-dialog title="校验规则编辑" :visible="editFormVisible" :show-close="false" v-loading="listLoading">
            <el-form :model="editForm" label-width="140px" :rules="editFormRules" ref="editForm">
        		<el-form-item label="系统名称" prop="app_id">
            <el-select v-model="editForm.app_id" placeholder="请选择" :disabled="true">
            </el-select>
        		</el-form-item>
        		 <el-form-item label="数据源" prop="datasource_code">
             <el-select v-model="editForm.datasource_code" placeholder="请选择" :disabled="true"> 
            </el-select>		
        		</el-form-item>
        		<el-form-item label="状态标示" prop="is_enable">
            <el-select v-model="editForm.is_enable" placeholder="请选择">
             <el-option
              v-for="is_enable in statusStatus"
             :key="is_enable.value"
             :label="is_enable.label"
             :value="is_enable.value">
             </el-option>
            </el-select>
        	</el-form-item>
        		<el-form-item label="关系人" prop="workerNames">
            		<el-checkbox-group v-model="editForm.workerNames">
                		     <el-checkbox v-for="stakeholder in stakeholders_For_selection"
							 :key="stakeholder.value"
							 :label="stakeholder.label" >
							 </el-checkbox>
            		</el-checkbox-group>
            </el-form-item>
            <el-form-item label="校验规则" prop="sql_statement">
            		<el-input
                type="textarea"
                :autosize="{ minRows: 2, maxRows: 10}"
                v-model="editForm.sql_statement"  @blur="checkSql(editForm)">
            </el-input>
        		</el-form-item>
        		<el-form-item label="规则校验目的" prop="intent">
            		<el-input
                type="textarea"
                :autosize="{ minRows: 2, maxRows: 10}"
                v-model="editForm.intent">
            </el-input>
        		</el-form-item>
        		<el-form-item label="第一次执行时间" prop="first_time"  style="width: 50%;">
            		<el-date-picker
                     v-model="editForm.first_time"
                     type="datetime"
                     placeholder="选择日期时间" @blur="checkTime(editForm)">
                </el-date-picker>
        		</el-form-item>
        		<el-form-item label="执行间隔（分钟）" prop="interval" style="width: 50%;" >
            		<el-input  v-model ="editForm.interval"   @blur="checkNum(editForm)"maxlength="9"></el-input>
				</el-form-item>
			<el-form-item label="总执行次数(次)" prop="frequency" style="width: 50%;" >
            		<el-input v-model="editForm.frequency"@blur="checkNum(editForm)" maxlength="9"></el-input>
            </el-form-item>
            <el-form-item label="短信/邮件通知" prop="is_message" style="width: 50%;">
             <el-select v-model="editForm.is_message" placeholder="请选择">
             <el-option
              v-for="alarm in alarmStatus"
             :key="alarm.value"
             :label="alarm.label"
             :value="alarm.value">
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
	import http from '../../util/http.js';
	//import NProgress from 'nprogress'
	// import { getUserListPage, removeUser, batchRemoveUser, editUser, addUser } from '../../api/api';

	export default {
		
		data() {
			return {
				flag:'1',
				system:'',
                systemsforselect:[],
			//顶部工具条搜索选项
				filters: {checkRule_code:'',user_id:'',size:'',begin:0,end:0,sys_name:''},
        //通知选项		
		alarmStatus: [{value: '1',label: '是'}, {value: '2',label: '否'}],
        //数据源选项
        datasourceStatus:[],
        //系统名称选项
        systemnameStatus: [],
        //状态标示
        statusStatus: [{value: '1',	label: '启用' }, {value: '2',label: '停用' }],
		checkRules: [],total: 0,page: 1,size: 20,listLoading: false,sels: [],//列表选中列  
		editFormVisible: false,//编辑界面是否显示
				editLoading: false,
				editFormRules: {
					workerNames:[
						{ required: true, message: '请选择关系人', trigger: 'blur' }
					],
					app_id: [
						{ required: true, message: '请选择系统名称', trigger: 'blur' }
					],
					is_enable: [
						{ required: true, message: '请选择状态标示', trigger: 'blur' }
					],
					sql_statement: [
						{ required: true, message: '请输入校验规则', trigger: 'blur'}
					],
					intent: [
						{ required: true, message: '请输入规则校验目的', trigger: 'blur' }
					],
					first_time: [
						{ required: true, message: '请输入第一次执行时间', trigger: 'blur' }
					],
					interval: [
						{ required: true, message: '请输入执行时间间隔', trigger: 'blur'}
					],
					frequency: [
						{ required: true, message: '请输入执行频率', trigger: 'blur' }
					],
					is_message: [
						{ required: true, message: '请选择是否通知', trigger: 'blur' }
					],
					datasource_code: [
						{ required: true, message: '请选择数据源', trigger: 'blur' }
					]
				},

				stakeholders_For_selection:[],
				//编辑界面数据
				editForm: {},
				centerDialogVisible:false,
				addFormVisible: false,//新增界面是否显示
				addLoading: false,
				addFormRules: {
					workerNames:[
						{ required: true, message: '请选择关系人', trigger: 'blur' }
					],
					app_id: [
						{ required: true, message: '请选择系统名称', trigger: 'blur' }
					],
					is_enable: [
						{ required: true, message: '请选择状态标示', trigger: 'blur' }
					],
					sql_statement: [
						{ required: true, message: '请输入校验规则', trigger: 'blur' }
					],
					intent: [
						{ required: true, message: '请输入规则校验目的', trigger: 'blur' }
					],
					first_time: [
						{ required: true, message: '请输入第一次执行时间', trigger: 'blur' }
					],
					interval: [
						{ required: true, message: '请输入执行时间间隔', trigger: 'blur' }
					],
					frequency: [
						{ required: true, message: '请输入执行频率', trigger: 'blur' }
					],
					is_message: [
						{ required: true, message: '请选择是否通知', trigger: 'blur' }
					],
					datasource_code: [
						{ required: true, message: '请选择数据源', trigger: 'blur' }
					]
				},
				//列表数据
				tabledata: [],
				//新增界面数据
				addForm: {
					user_id:'',sys_name: '',datasource_code: '',	is_enable: '',sql_statement: '',intent: '',
					first_time: '',interval: '',frequency:'99999999',is_message: '',	workerNames:[]				
					
				}
			}
		},
		methods: {

		
               changesystem(){
                     if( sessionStorage.getItem("systemNames")!=null){
                            this.systemsforselect= JSON.parse( sessionStorage.getItem("systemNames"));
			 }else{ 
				this.$post('http://'+http.host_port+'/checkRules/querySystems',
				{user_id:JSON.parse(sessionStorage.getItem("user")).id})
				.then((res)=>{
						 var systems=[];
						if(res==null){
							this.$message({
								message:'获取系统选项失败，请联系网站维护人员',
								type:'error',
								duration:'5000'
								
							});} else{
							systems=res;
							  var array=[];
							  for (let index = 0; index <systems.length; index++) {
									 array.push( {label:systems[index],value:index} );
										 };
					  // //console.debug(array);
					   this.systemsforselect= array;
				sessionStorage.setItem('systemNames',JSON.stringify(array));
				
				        
				}});
}

			   },
			   //检查sql语句是否是select语句，在页面用户填完sql语句时触发检查
			   checkSql(SQL){
                       
						if( !new String(SQL.sql_statement).toLowerCase().startsWith('select')){
							this.$message({
							message: '请输入正确的select语句',
							type: 'warning'
						});
						SQL.sql_statement='';
						}
							
					},
                    checkTime(time){
					
						if(new Date(time.first_time).getTime()<new Date().getTime()){
							this.$message({
							message: '请选择正确的时间',
							type: 'warning'
						});
						time.first_time='';
						}
							
					},

					//检查frequency和interval是否是数字，将其中的字母去掉。如‘234dd’方法触发后设置成‘234’，在页面用户输入完间隔和执行次数后触发
					checkNum(form){
						form.interval=new String(form.interval).replace(/[^\d]/g,'');
						form.frequency=new String(form.frequency).replace(/[^\d]/g,'');
					
					},
			listeningchange(){

				/*if(this.addForm.app_id==1){
					//console.debug("ffff");
                  	this.datasourceStatus=[{
        		value: '1',
        		label: '保全'
        }, {
        		value: '2',
        		label: '反洗钱'
		}];
}*/
				//在页面每改变系统的选项，所选择的系统下的数据源跟着更新在数据源下拉选项框中	
this.$post('http://'+http.host_port+'/checkRules/getdatasources',
				{sys_name:this.addForm.sys_name})
				.then((res)=>{
						 var datasources=[];
						if(res==null){
							this.$message({
								message:'获取系统数据源失败，请联系网站维护人员',
								type:'error',
                                duration:'5000'
							});} else{
							datasources=res;
							  var array=[];
							  for (let index = 0; index <datasources.length; index++) {
									 array.push( {label:datasources[index],value:index} );
										 };
			        //   //console.debug(array);
							this.datasourceStatus=array;
			   	
				}});
	       //  //console.debug(this.datasourceStatus);


			},
			
			
			indexMethod(index) {
        		return (this.page-1)*this.size+index+1 ;
      		},
			nextchange(val) {
				this.page = val;
				this.getcheckRules();
			},
			prevchange(val) {
				this.page = val;
				this.getcheckRules();
			},
			handleCurrentChange(val) {
				this.page = val;
				this.getcheckRules();
			},
			/*获取校验规则列表
			*(1)按条件查询，如果校验规则代码为空则返回当前用户所创建的所有校验规则，否则返回指定的校验规则
			*（2）按系统名称查询，返回被选中系统下的所有校验规则
			*/
			getcheckRules() {
				this.filters.user_id=JSON.parse(sessionStorage.getItem("user")).id;
				let para = this.filters;
				this.listLoading = true;
				let  querySome='http://'+http.host_port+'/checkRules/querySome';
				let queryBySysName='http://'+http.host_port+'/checkRules/queryBySysName';
			
				//NProgress.start();
				//设置分页参数
				if(this.filters.checkRule_code==''){
					this.filters.begin=(this.page-1)*this.size ;
					this.filters.end=this.size;
					let para='';
					
				  if(this.flag==='1'){ para={user_id:this.filters.user_id,sys_name:''}}else{para={sys_name:this.system,
				  user_id:this.filters.user_id}};//如果flag为1则是按条件查询，为2则按系统名称查询
				
				this.$fetch('http://'+http.host_port+'/checkRules/total',para).then((res)=>{  //获取分页所需的total
									this.total=parseInt(res);					
				});}
				var url='';
				 if(this.flag==='1'){
					 url=querySome;}
				 else{
					 url=queryBySysName;
					para.sys_name=this.system;
					 }
				 
		
				this.$post(url,para).then((res) => {
					var array=res
				//	//console.debug(res);
				
					this.tabledata=[];	//清空当前tabledata数据

				
					array.forEach(element => {  	//把获取到的校验规则遍历到tabledata中
						var ele={
                               app_id: element.system_name,
                               checkRule_code: element.checkRule_code,
							   is_enable:element.is_enable,
							   interval:element.intervals,
							   frequency:element.frequency,
                               first_time:element.first_time,
							   intent:element.intent,
							   next_time:element.next_time,
							   intent:element.intent,
							   sql_statement:element.sql_statement,
							   datasource_code: element.datasource_code,
				  			   is_message: element.is_message
						 }
						 
						 this.tabledata.push(ele);
					});

					this.checkRules = res;
					//显示加载动态画面
					this.listLoading = false;
					
					//NProgress.done();
				});
			},
			handleEable: function (index, row) {
				this.$confirm('确认启用该记录吗?', '提示', {
					type: 'warning'
				}).then(() => {
					this.listLoading = true;
					//NProgress.start();
					let para = { checkRule_code:row.checkRule_code};
					this.$fetch('http://'+http.host_port+'/checkRules/enable',para).then((res) => {
						this.listLoading = false;
						//NProgress.done();
					
			
						this.$message({
							message: '启用成功',
							type: 'success'
						});
						this.getcheckRules();
					});
				}).catch(() => {

				});
			},
			//删除
			handleDel: function (index, row) {
				this.$confirm('确认删除该记录吗?', '提示', {
					type: 'warning'
				}).then(() => {
					this.listLoading = true;
					//NProgress.start();
					let para = { checkRule_code:row.checkRule_code,
					              user_id:JSON.parse(sessionStorage.getItem("user")).id
					};
					this.$post('http://'+http.host_port+'/checkRules/delete',para).then((res) => {
						this.listLoading = false;
						//NProgress.done();
						this.$message({
							message: '删除成功',
							type: 'success'
						});
						this.getcheckRules();
					});
				}).catch(() => {

				});
			},
			//显示编辑界面，并初始化编辑页面
			handleEdit: function (index, row) {
				
				 this.editForm={
					 user_id:'',
				checkRule_code:row.checkRule_code,
				frequency:row.frequency,
				app_id:row.app_id,
				datasource_code:row.datasource_code,
				is_enable:row.is_enable,
				is_message:row.is_message,
				first_time:row.first_time,
				sql_statement:row.sql_statement,
				interval:row.interval,
			   workerNames:[],
				intent:row.intent}
				//页面显示已经存在的关系人列表供选择
				this.$fetch('http://'+http.host_port+'/checkRules/querystakeholdersOfcheckRule',{checkRule_code:row.checkRule_code})
				.then((res)=>{
						 var stakeholderNames=[];
						if(res==null){
							this.$message({
								message:'获取关系人列表失败，请联系网站维护人员',
								type:'error'
								
							});} else{	
						this.editForm.workerNames=res;   
				}});
				this.inition();
				this.editFormVisible = true;
				
			},
			//显示新增界面，并初始化系统下拉框和数据源下拉框
			handleAdd: function () {
		  this.inition();
		 	this.addFormVisible = true;
			},
		
		inition(){
			 /**
			  * 初始化关系人多选项：
			  * （1）如果sessionStorage中stakeholders不为空，则从sessionStorage中获取关系人多选项
			  * 否则，从后台读取关系人构成多选项
			  * （2）优点：减少从磁盘读取数据的次数，提升反应速度
			  * （3）缺点：占用较大内存
			  * （4）本段代码后面校验规则数据源选项动态生成，校验规则系统选项动态生成思路类似
			  * （5）如发现页面存在有的关系人，数据源，应用系统没显示，退出当前用户重新登陆，因为有缓存在，退出会清除缓存重新获取数据
			  * 
			  * @author 源哥最帅！！
			  * 
			  */
			 ////console.debug(util.formatDate.format(new Date(),'yyyy-MM-dd hh:mm:ss'));
             if( sessionStorage.getItem("stakeholders")!=null){
							this.stakeholders_For_selection= JSON.parse( sessionStorage.getItem("stakeholders"));
						////console.debug(JSON.parse(sessionStorage.getItem("user")).id);
			 }else{
				this.$fetch('http://'+http.host_port+'/checkRules/getAllstakeholders',null)
				.then((res)=>{
						 var stakeholderNames=[];
						if(res==null){
							this.$message({
								message:'获取关系人列表失败，请联系网站维护人员',
								type:'error'
								
							});} else{
							stakeholderNames=res;
							  var array=[];
							  //遍历获取到的关系人到array中
							  for (let index = 0; index < stakeholderNames.length; index++) {
									 array.push( {label:stakeholderNames[index],value:index} );
										 };
				//把关系人列表存入sessionStorage
				sessionStorage.setItem('stakeholders',JSON.stringify(array));
				////console.log(sessionStorage.getItem('stakeholders'));
				this.stakeholders_For_selection=[];
				//stakeholders_For_selection进行初始化
                this.stakeholders_For_selection= JSON.parse( sessionStorage.getItem("stakeholders"));
			            	
				}});
}
				//var stakeholderNames=['11','44','fg'];
				//初始化系统下拉框选项
//sessionStorage.removeItem("systemNames");
			if( sessionStorage.getItem("systemNames")!=null){
                            this.systemnameStatus= JSON.parse( sessionStorage.getItem("systemNames"));
			 }else{ 
				this.$post('http://'+http.host_port+'/checkRules/querySystems',
				{user_id:JSON.parse(sessionStorage.getItem("user")).id})
				.then((res)=>{
						 var systems=[];
						if(res==null){
							this.$message({
								message:'获取系统选项失败，请联系网站维护人员',
								type:'error'
								
							});} else{
							systems=res;
							  var array=[];
							  for (let index = 0; index <systems.length; index++) {
									 array.push( {label:systems[index],value:index} );
										 };
			           ////console.debug(array);
				sessionStorage.setItem('systemNames',JSON.stringify(array));
				this.systemnameStatus=[];
				this.systemnameStatus= JSON.parse( sessionStorage.getItem("systemNames"));
				        
				}});
}
	},
			//编辑
			editSubmit: function () {
				this.$refs.editForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认更新吗？', '提示', {}).then(() => {
                            
							this.editLoading = true;
							//NProgress.start(
								let temp=this.editForm;
								temp.workerNames=temp.workerNames.toString();
								temp.user_id=JSON.parse(sessionStorage.getItem("user")).id; 
								let para=JSON.parse(JSON.stringify(temp));
						
							this.$post('http://'+http.host_port+'/checkRules/update',para).then((res) => {
								this.editLoading = false;
								//NProgress.done();
								this.$message({
									message: '更新成功',
									type: 'success'
								});
								this.$refs['editForm'].resetFields();
								this.editFormVisible = false;
								this.getcheckRules();
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
							let temp=this.addForm;
								temp.workerNames=temp.workerNames.toString();
								temp.user_id=JSON.parse(sessionStorage.getItem("user")).id; 
								let para=JSON.parse(JSON.stringify(temp));
								para.first_time=util.formatDate.format(new Date(para.first_time), 'yyyy-MM-dd hh:mm:ss');
						//	//console.debug(para);
							this.$post('http://'+http.host_port+'/checkRules/add',para).then((res) => {
								this.addLoading = false;
								//NProgress.done();
								var mess='提交成功，并成功启用该校验规则';
								if(para.is_enable=="2"){ mess='提交成功，请点击"启用"按钮启用该校验规则';}
								this.$message({
									message: mess,
									type: 'success',
									duration:'5000'
								});
								this.$refs['addForm'].resetFields();
								this.addFormVisible = false;
								this.getcheckRules();
							});
						});
					}
				});
			},
			selsChange: function (sels) {
				this.sels =sels;
				////console.debug(this.sels.map(item => item.checkRule_code).toString());
			},
				checkRulesBysys:function(){
					this.flag='2';
				this.getcheckRules();
				this.flag='1';
			},
			//批量停用
			batchdisable: function () {
				var checkRule_codes = this.sels.map(item => item.checkRule_code).toString();
				this.$confirm('确认停用选中校验规则吗？', '提示', {
					type: 'warning'
				}).then(() => {
					this.listLoading = true;
					//NProgress.start();
					let para = { checkRule_codes:checkRule_codes};
					this.$fetch('http://'+http.host_port+'/checkRules/batchdisable',para).then((res) => {
						this.listLoading = false;
						//NProgress.done();
						this.$message({
							message: '停用成功',
							type: 'success'
						});
						this.getcheckRules();
					});
				}).catch(() => {

				});
			}
		},
		
		mounted() {
			this.getcheckRules();
		}
	}

</script>

<style scoped>
 
</style>