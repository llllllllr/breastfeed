<template>
<div >
    <div style="margin: 20px;"></div>
    <div class="crumb">
  <el-breadcrumb separator-class="el-icon-arrow-right">
  <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
  <el-breadcrumb-item :to="{ path: '/articlelist' }">文章列表</el-breadcrumb-item>
  <el-breadcrumb-item >修改文章</el-breadcrumb-item>
  </el-breadcrumb>  
    </div>
    <div class="form">
        <el-form :label-position="labelPosition" label-width="50px" :model="formLabelAlign">
        <el-form-item label="标题">
            <el-input id="title" v-model="formLabelAlign.title"></el-input>
        </el-form-item>
        <el-form-item  label="简介">
            <el-input  id="subtitle" v-model="formLabelAlign.description"></el-input>
        </el-form-item>
        </el-form>
    </div>
       <div id="editor"></div>
       <div class="category">
       <template>
        <el-radio v-model="radio" label="母乳喂养资料" border size="medium">母乳喂养资料</el-radio>
        <el-radio v-model="radio" label="新生儿护理资料" border size="medium">新生儿护理资料</el-radio>
         <el-radio v-model="radio" label="孕期保健知识" border size="medium">孕期保健知识</el-radio>
        <el-radio v-model="radio" label="其它" border size="medium">其它</el-radio>
        </template>
       </div>
        <el-button type="primary" class="submit" v-on:click="edit">提交</el-button>
</div>
</template>


<script type="text/javascript" src="/wangEditor.min.js"></script>
<script>
import Editor from 'wangeditor'
import qs from 'qs'
import * as iniu from 'qiniu-js'
export default {
    data() {
        return {
            editor:'',
            labelPosition: 'right',
            formLabelAlign:[],
             radio: ''
        }
    },
    created(){
        
        var item = this.$route.params.id
         this.test()
      
    },
    mounted() {
       
        //wangeditor编辑器
        this.editor= new Editor('#editor');
       
        //七牛云图片上传
        this.editor.customConfig.uploadImgShowBase64 = true
        //隐藏网络图片
        this.editor.customConfig.showLinkImg = false;
        this.editor.customConfig.debug = true;
        this.editor.customConfig.uploadImgServer = 'http://localhost:8887/article/upload';
        this.editor.customConfig.uploadFileName = 'multiple';
        this.editor.customConfig.uploadImgHooks ={
              fail: function (xhr, editor, result) {
                  alert(result)
        // 图片上传并返回结果，但图片插入错误时触发
        // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象，result 是服务器端返回的结果
    },
        };
        this.editor.create()
    },
    methods:{
        test:function(){
            var g_articleList =[]
              this.$axios({
                method:'get',
                url:"http://localhost:8887/article/getone",
                params:{
                    id:this.$route.params.id
                }
            }).then((res)=>{
                   this.formLabelAlign = res.data.data;   
                   this.editor.txt.html(this.formLabelAlign.content)     
                   this.radio=res.data.data.category
                  console.log(res.data)
            })
        },
        edit:function(){
            
            var id = this.$route.params.id;
            var newtitle=document.getElementById('title').value;
            var newsubtitle = document.getElementById('subtitle').value;
            var cont = this.editor.txt.html();
            alert(id);
             this.$axios({
                method:'post',
                url:"http://localhost:8887/article/addedit",
                params:{
                    id:id,
                    title:newtitle,
                    subtitle:newsubtitle,
                    content:cont,
                    category:this.radio
                }
            }).then((res)=>{
                   var resData=res.data;
                  if(resData.status==1){
                        this.$message.success(resData.msg);
                        this.$router.push({path:'/articlelist'})
                  }
                  else
                      this.$message.error(resData.msg);
            })
        }
  }
}
 
        
</script>  

<style scoped>

#editor{

    position: absolute;
    top:270px;
    left: 320px;
    line-height: 15px;
    
}
.form{
    position: absolute;
    width:500px;
    left: 320px;
    top: 150px;
}
.el-main{
    line-height: 15px;
}
.category{
    margin-top: 450px;
    margin-left: 150px
}
.submit{
    position: absolute;
    top: 720px;
    left: 370px;
}

#editor{
    background-color: white;
    margin-left:50px
}
.contentlabel{
    position: absolute;
    top: 230px;
    left: 320px;
}
.crumb{
  margin-top: 50px;
  margin-left: 110px
}
</style>