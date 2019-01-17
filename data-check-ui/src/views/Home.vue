<template>
	<el-row class="container">
		<el-col :span="24" class="header">
			<el-col :span="10" class="logo" :class="collapsed?'logo-collapse-width':'logo-width'">
				{{collapsed?'':sysName}}
			</el-col>
			<el-col :span="10">
				<div class="tools" @click.prevent="collapse">
					<i class="fa fa-align-justify"></i>
				</div>
			</el-col>
			<el-col :span="4" class="userinfo">
				<el-dropdown trigger="hover">
					<span class="el-dropdown-link userinfo-inner"><img :src="this.sysUserAvatar" /> {{sysUserName}}</span>
					<el-dropdown-menu slot="dropdown">
						<!--<el-dropdown-item>我的消息</el-dropdown-item>
						<el-dropdown-item>设置</el-dropdown-item>-->
						<el-dropdown-item divided @click.native="logout">退出登录</el-dropdown-item>
					</el-dropdown-menu>
				</el-dropdown>
			</el-col>
		</el-col>
		<el-col :span="24" class="main">
			<aside>
				<el-menu default-active="/users" class="el-menu-vertical-demo" @open="handleopen" @close="handleclose"  @select="handleselect" unique-opened router  :collapse="collapsed">
  				<template v-for="(item,index) in menus" v-if="!item.hidden">
					<el-submenu :index="index+''">
						<template slot="title"><i :class="item.iconType"></i><span>{{item.resource_Name}}</span></template>
						<el-menu-item v-for="child in item.children" :index="child.url" :key="child.resource_Id" v-if="!child.hidden">{{child.resource_Name}}</el-menu-item>
					</el-submenu>
				</template>
				</el-menu>
			</aside>
			<section class="content-container">
				<div class="grid-content bg-purple-light">
					<el-col :span="24" class="breadcrumb-container">
						<strong class="title">{{$route.name}}</strong>
						<el-breadcrumb separator="/" class="breadcrumb-inner">
							<el-breadcrumb-item v-for="item in $route.matched" :key="item.path">
								{{ item.name }}
							</el-breadcrumb-item>
						</el-breadcrumb>
					</el-col>
					<el-col :span="24" class="content-wrapper">
						<transition name="fade" mode="out-in">
							<router-view></router-view>
						</transition>
					</el-col>
				</div>
			</section>
		</el-col>
	</el-row>
</template>

<script>

import http from '../util/http.js';
	export default {
		data() {
			return {
				sysName:'ACP',
				collapsed:false,
				sysUserName: '',
				sysUserAvatar: '',
				form: {
					name: '',
					region: '',
					date1: '',
					date2: '',
					delivery: false,
					type: [],
					resource: '',
					desc: ''
				},
				menus:[]
			}
		},
		methods: {
			onSubmit() {
//////console.log('submit!');
			},
			handleopen() {
				//////console.log('handleopen');
			},
			handleclose() {
				//////console.log('handleclose');
			},
			handleselect: function (a, b) {
			},
			//退出登录
			logout: function () {
				var _this = this;
				this.$confirm('确认退出吗?', '提示', {
					//type: 'warning'
				}).then(() => {
					sessionStorage.removeItem('user');
					_this.$router.push('/login');
				}).catch(() => {

				});


			},
			//折叠导航栏
			collapse:function(){
				this.collapsed=!this.collapsed;
			},
			showMenu(i,status){
				this.$refs.menuCollapsed.getElementsByClassName('submenu-hook-'+i)[0].style.display=status?'block':'none';
			},
			calcMenuData:function(data){
				//data = JSON.parse(data);
				//////console.log(data[0].resource_Id);
				if(data!=null&&data.length){
					function isRoot(menu){
						return !menu.parent_Id
					}
					let roots=data.filter(isRoot);
					//////console.log(data[0].resource_Id);
					for(let i=0,len=roots.length;i<len;i++){
						roots[i].children=this.buildChildMenu(roots[i],data,1);
						//////console.log(roots[i].children.length);
					}
					let root_new = [];
					let index = 0;
					for(let i=0,len=roots.length;i<len;i++){
						if (roots[i].children.length>=1){
							root_new[index]=roots[i];
							index++;
						}
					}
					this.menus=root_new;
					//////console.log(this.menus);
					//////console.warn(this.menus);
				}
			},
			buildChildMenu:function(root,menus,level){
				let children=[];
				let index=0;
				for(let i=0,len=menus.length;i<len;i++){
					//////console.log(menus[i].parent_id);
					//////console.log(root.resource_Id);
					if(menus[i]&&menus[i].parent_id&&menus[i].parent_id===root.resource_Id){
					//////console.log(menus[i]);
						children[index]=menus[i];
						delete menus[i];
						if(!children[index].url&&level<4){
							children[index].children=buildChildMenu(children[index],menus,level+1);
						}
						index++;
					}
				}
				return children;
			}
		},
		created(){
			var user = sessionStorage.getItem('user');
			user = JSON.parse(user);
			//////console.log(user.username);
			let _para = {
				username : user.username
			};
			//this.$fetch('http://127.0.0.1:8888/Resource',_para).then(res=>{
			this.$fetch('http://'+http.host_port+'/Resource',_para).then(res=>{
			//////console.log(res);
                    if (res) {
                    	sessionStorage.setItem('menu', JSON.stringify(res));
                    	this.calcMenuData(res);
                    }
                });
		},
		mounted() {
			var user = sessionStorage.getItem('user');
			if (user) {
				user = JSON.parse(user);
				this.sysUserName = user.name || '';
				let img=user.sex==='男'?'static/img/boy.jpg':'static/img/girl.jpg';
				this.sysUserAvatar = user.avatar || img;
                // this.$router.push({ path: '/user' });
			}

		}
	}

</script>

<style scoped lang="scss">
	@import '~scss_vars';
	
	.container {
		position: absolute;
		top: 0px;
		bottom: 0px;
		width: 100%;
		.header {
			height: 60px;
			line-height: 60px;
			background: $color-primary;
			color:#fff;
			.userinfo {
				text-align: right;
				padding-right: 35px;
				float: right;
				.userinfo-inner {
					cursor: pointer;
					color:#fff;
					img {
						width: 40px;
						height: 40px;
						border-radius: 20px;
						margin: 10px 0px 10px 10px;
						float: right;
					}
				}
			}
			.logo {
				//width:230px;
				height:60px;
				font-size: 22px;
				padding-left:20px;
				padding-right:20px;
				border-color: rgba(238,241,146,0.3);
				border-right-width: 1px;
				border-right-style: solid;
				img {
					width: 40px;
					float: left;
					margin: 10px 10px 10px 18px;
				}
				.txt {
					color:#fff;
				}
			}
			.logo-width{
				width:230px;
			}
			.logo-collapse-width{
				width:60px
			}
			.tools{
				padding: 0px 23px;
				width:14px;
				height: 60px;
				line-height: 60px;
				cursor: pointer;
			}
		}
		.main {
			display: flex;
			// background: #324057;
			position: absolute;
			top: 60px;
			bottom: 0px;
			overflow: hidden;
			aside {
				flex:0 0 230px;
				width: 230px;
				// position: absolute;
				// top: 0px;
				// bottom: 0px;
				.el-menu{
					height: 100%;
				}
				.collapsed{
					width:60px;
					.item{
						position: relative;
					}
					.submenu{
						position:absolute;
						top:0px;
						left:60px;
						z-index:99999;
						height:auto;
						display:none;
					}

				}
			}
			.menu-collapsed{
				flex:0 0 60px;
				width: 60px;
			}
			.menu-expanded{
				flex:0 0 230px;
				width: 230px;
			}
			.content-container {
				// background: #f1f2f7;
				flex:1;
				// position: absolute;
				// right: 0px;
				// top: 0px;
				// bottom: 0px;
				// left: 230px;
				overflow-y: scroll;
				padding: 20px;
				.breadcrumb-container {
					//margin-bottom: 15px;
					.title {
						width: 200px;
						float: left;
						color: #475669;
					}
					.breadcrumb-inner {
						float: right;
					}
				}
				.content-wrapper {
					background-color: #fff;
					box-sizing: border-box;
				}
			}
		}
	}
	.el-menu--collapse{
	  width: 59px;
	}
</style>