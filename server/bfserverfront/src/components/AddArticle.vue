<template>
<div >
    <div style="margin: 20px;"></div>
    <div class="form">
        <el-form :label-position="labelPosition" label-width="50px" :model="formLabelAlign">
        <el-form-item label="标题">
            <el-input id="title" v-model="formLabelAlign.name"></el-input>
        </el-form-item>
        <el-form-item  label="简介">
            <el-input  id="subtitle" v-model="formLabelAlign.region"></el-input>
        </el-form-item>
        </el-form>
    </div>
       <div id="editor"></div>
        <el-button type="primary" class="submit" v-on:click="test">提交</el-button>
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
            formLabelAlign: {
            name: '',
            region: '',
            type: '',
            message:'dadasdasdadas'
        }
        }
    },
    mounted() {
       
        this.editor= new Editor('#editor');
        this.editor.customConfig.uploadImgShowBase64 = true
        //隐藏网络图片
        this.editor.customConfig.showLinkImg = false;
        this.editor.customConfig.debug = true;
        this.editor.customConfig.uploadImgServer = 'http://localhost:8888/article/upload';
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
            alert("begin")
            var t=document.getElementById('title').value;
            var st = document.getElementById('subtitle').value;
            var cent = this.editor.txt.html()
            alert(cent)
            this.$axios({
                method:'get',
                url:"http://localhost:8888/article/add",
                params:{
                    title:t,
                    subtitle:st,
                    content:cent
                }
            }).then((res)=>{
                  alert(res)
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
    line-height: 15px
}
.form{
    position: absolute;
    width:650px;
    left: 320px;
    top: 150px;
}
.el-main{
    line-height: 15px;
}

.submit{
    position: absolute;
    top:650px;
    left:380px
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
</style>