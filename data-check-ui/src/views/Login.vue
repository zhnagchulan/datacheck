<template>
  <section>
  <el-form :model="ruleForm2" :rules="rules2" ref="ruleForm2" label-position="left" label-width="0px" class="demo-ruleForm login-container">
    <h3 class="title">系统登录</h3>
    <p style="text-align:center;color:red;" v-show="showTips">{{Tips}}</p>
    <el-form-item prop="account">
      <el-input type="text" v-model="ruleForm2.account" auto-complete="off" placeholder="工号或用户名"></el-input>
    </el-form-item>
    <el-form-item prop="checkPass">
      <el-input type="password" v-model="ruleForm2.checkPass" auto-complete="off" placeholder="密码"></el-input>
    </el-form-item>
    <el-checkbox v-model="checked"  class="remember">记住密码</el-checkbox>
    <el-button type="text" style="padding:0px 0px 35px 135px;" @click="handleRegistry">没有账号？点击注册</el-button>
    <el-form-item style="width:100%;">
      <el-button type="primary" style="width:100%;" @click.native.prevent="handleSubmit2" :loading="logining">登录</el-button>
      <!--<el-button @click.native.prevent="handleReset2">重置</el-button>-->
    </el-form-item>
  </el-form>
  <!--注册界面-->
  <el-dialog title="注册用户" :visible="registryFormVisible" :show-close="false">
    <el-form :model="registryForm" label-width="80px" :rules="registryFormRules" ref="registryForm">
            <el-form-item label="工号" prop="id">
                <el-input v-model="registryForm.id" auto-complete="off" @blur="checkAccount()"placeholder="工号"></el-input>
            </el-form-item>
            <el-form-item label="姓名">
                <el-input v-model="registryForm.username" auto-complete="off"placeholder="姓名"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="pwd">
              <el-input type="password" v-model="registryForm.pwd" auto-complete="off" placeholder="密码"></el-input>
            </el-form-item>
            <el-form-item label="手机" prop="mobile">
              <el-input v-model="registryForm.mobile" auto-complete="off"placeholder="手机"></el-input>
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="registryForm.email" auto-complete="off"placeholder="邮箱"></el-input>
            </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="registryFormVisible = false">取消</el-button>
                <el-button type="primary" @click.native="registrySubmit" :loading="registryLoading">注册</el-button>
            </div>
        </el-dialog>
  
  </section>
</template>

<script>
//所有管理员添加的账户初始密码为123456
  //import NProgress from 'nprogress'
  import md5 from 'js-md5';
   import http from '../util/http.js';
  
  let url = {
      //login:'http://127.0.0.1:8888/login',
      //registry:'http://127.0.0.1:8888/registry'
      login:'http://'+ http.host_port+'/login',
      registry:'http://'+ http.host_port+'/registry',
      checkAccount:'http://'+ http.host_port+'/checkAccount'
  }
  export default {
    data() {
      return {
        showTips:false,
        Tips:'',
        logining: false,
        ruleForm2: {
          account: '',
          checkPass: ''
        },
        rules2: {
          account: [
            { required: true, message: '请输入账号', trigger: 'blur' },
            //{ validator: validaePass }
          ],
          checkPass: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            //{ validator: validaePass2 }
          ]
        },
        checked: false,
        registryFormVisible: false,
        registryLoading: false,
        registryFormRules: {
          id: [
            { required: true, message: '请输入工号', trigger: 'blur' }
          ],
          pwd: [
            { required: true, message: '请输入密码', trigger: 'blur' }
          ],
          mobile: [
            { required: true, message: '请输入手机号', trigger: 'blur' }
          ],
          email: [
            { required: true, message: '请输入邮箱', trigger: 'blur' }
          ]
        },
        registryForm:{
          id:'',
          pwd:'',
          username:'',
          mobile:'',
          email:''
        }
      };
    },
    methods: {
     
      checkAccount(){ //注册新账户时检查是否已经注册，防止出现主键重复s
                   this.$fetch(url.checkAccount,{user_id:this.registryForm.id}) .then(res => {
                 // ////console.debug(res);   
             if(res==this.registryForm.id){
                  this.$message({
                  message: '该用户名已注册，请更改其用户名再试！',
                  type: 'warning',
                  duration:'5000'
                });
                this.registryForm.id='';
                }
      });},
      //显示新增界面
      handleRegistry: function () {
        this.registryFormVisible = true;
        this.registryForm = {};
      },
      handleReset2() {
        this.$refs.ruleForm2.resetFields();
      },
      registrySubmit(){
        this.$refs.registryForm.validate((valid) => {
          if (valid) {
            this.registryLoading = true;
            let para = Object.assign({}, this.registryForm,{
              pwd: md5(this.registryForm.pwd)
            });
            this.$post(url.registry,para).then(res => {
              this.registryLoading = false;
              if(res!=null){
                //sessionStorage.setItem('user', JSON.stringify(res));
                this.$message({
                  message: '注册成功',
                  type: 'success'
                });
                this.$refs['registryForm'].resetFields();
                this.registryFormVisible = false;
                this.$router.push({ path: '/login' });
              }
            });
          }
        });
      },

      handleSubmit2(ev) {
        var _this = this;
        this.$refs.ruleForm2.validate((valid) => {
          if (valid) {
            //_this.$router.replace('/table');
            this.logining = true;
            //NProgress.start();
            var loginParams = { username: this.ruleForm2.account, password: md5(this.ruleForm2.checkPass) };
            //////console.log(loginParams);
            //this.$post('/login',loginParams)
            //axios.$post('http://'+host+'/getInfo',loginParams)
            this.$fetch(url.login,loginParams)
            .then(res => {
              //////console.log(res);
              this.logining = false;
              //NProgress.done();
              if(res!=null){
                sessionStorage.setItem('user', JSON.stringify(res));
                this.$router.push({ path: '/user/reset' });
              }else{
                this.Tips = "该用户名或密码错误，请重新输入！";
                this.showTips = true;
              }
            });
          } else {
//////console.log('error submit!!');
            return false;
          }
        });
      }
    }
  }

</script>

<style lang="scss" scoped>
  .login-container {
    /*box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.06), 0 1px 0px 0 rgba(0, 0, 0, 0.02);*/
    -webkit-border-radius: 5px;
    border-radius: 5px;
    -moz-border-radius: 5px;
    background-clip: padding-box;
    margin: 180px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
    .title {
      margin: 0px auto 40px auto;
      text-align: center;
      color: #505458;
    }
    .remember {
      margin: 0px 0px 35px 0px;
    }
  }
</style>