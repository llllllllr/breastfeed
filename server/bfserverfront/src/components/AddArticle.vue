<template>
  <div>
    <div style="margin: 20px;"></div>
    <div class="crumb">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/articlelist' }">文章列表</el-breadcrumb-item>
        <el-breadcrumb-item>添加文章</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="form">
      <el-form :label-position="labelPosition" label-width="50px" :model="formLabelAlign">
        <el-form-item label="标题" :rules="[{ required: true, message: '请输入标题', trigger: 'blur' }]">
          <el-input id="title" v-model="formLabelAlign.name"></el-input>
        </el-form-item>
        <el-form-item label="简介">
          <el-input id="subtitle" v-model="formLabelAlign.region"></el-input>
        </el-form-item>
        <span class="uploadImg">上传封面图</span>
        <el-upload
          class="avatar-uploader"
          ref="upload"
          action="http://upload-z2.qiniup.com"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
          :show-file-list="false"
          :data="form"
        >
          <img v-if="imageUrl" :src="imageUrl" class="avatar" />
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
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
    <el-button type="primary" class="submit" v-on:click="addArticle">提交</el-button>
  </div>
</template>


<script type="text/javascript" src="/wangEditor.min.js"></script>
<script src="https://cdn.bootcss.com/moment.js/2.24.0/locale/af.js"></script>
<script>
import Editor from "wangeditor";
import qs from "qs";
import * as iniu from "qiniu-js";
export default {
  data() {
    return {
      editor: "",
      labelPosition: "right",
      formLabelAlign: {
        name: "",
        region: "",
        type: "",
        message: "dadasdasdadas"
      },
      radio: "母乳喂养资料",
      imageUrl: "",
      returnUrl:"",
      form: {
        token: "token",
        key: ""
      }
    };
  },
  mounted() {
    this.$axios({
        method: "post",
        url: "http://localhost:8887/article/getToken",
        xhrFields: { withCredentials: true }
      }).then(res => {
        //alert(res.data)
        this.form.token = res.data;
      });  
    //初始化富文本编辑器
    this.editor = new Editor("#editor");
    this.editor.customConfig.uploadImgShowBase64 = true;
    //隐藏网络图片
    this.editor.customConfig.showLinkImg = false;
    this.editor.customConfig.debug = true;
    this.editor.customConfig.uploadImgServer =
      "http://localhost:8887/article/upload";
    this.editor.customConfig.uploadFileName = "multiple";
    this.editor.customConfig.pasteFilterStyle = false
    this.editor.customConfig.pasteTextHandle = function (content) {
      /**
       * 从word文档复制过来的文本的附加样式使得服务器返回错误
       * 先格式化复制的文本
       */
      if (content == '' && !content) return ''
      var str = content
      str = str.replace(/<xml>[\s\S]*?<\/xml>/ig, '')
      str = str.replace(/<style>[\s\S]*?<\/style>/ig, '')
      str = str.replace(/<\/?[^>]*>/g, '')
      str = str.replace(/[ | ]*\n/g, '\n')
      str = str.replace(/&nbsp;/ig, '')
      console.log('****', content)
      console.log('****', str)
      return str
    }
    this.editor.customConfig.uploadImgHooks = {
      fail: function(xhr, editor, result) {
        alert(result);
        // 图片上传并返回结果，但图片插入错误时触发
        // xhr 是 XMLHttpRequsngeet 对象，editor 是编辑器对象，result 是服务器端返回的结果
      }
    };
    this.editor.create();
  },
  methods: {
    //提交按钮响应函数
    addArticle: function() {
     // this.returnUrl="http://llllllllr.top/"+key;
     // alert(this.returnUrl)
      //获取标题，副标题，文本内容
      var title = document.getElementById("title").value;
      var subitle = document.getElementById("subtitle").value;
      var cont = this.editor.txt.html();
      alert(cont)
      //提交数据到服务器
      this.$axios({
        method: "get",
        url: "http://localhost:8887/article/addedit",
        params: {
          id: -1,
          title: title,
          subtitle: subitle,
          imgurl: this.returnUrl,
          content: cont,
          category: this.radio
        }
      }).then(res => {
        var resData = res.data;
        if (resData.status == 1) {
          this.$message.success(resData.msg);
          this.$router.push({ path: "/articlelist" });
        } else this.$message.error(resData.msg);
      });
    },
    handleAvatarSuccess(res, file) {
      //"上传封面图"隐藏
      document.getElementsByClassName("uploadImg")[0].style.display = "none";
      this.imageUrl = URL.createObjectURL(file.raw);
      
      alert(this.returnUrl);
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG或者PNG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }

      

      var myDate = new Date();
      var year = myDate.getFullYear();
      var month = myDate.getMonth();
      var day = myDate.getDay();
      var hour = myDate.getHours();
      var ms = myDate.getMilliseconds();

      this.form.key = file.name + year + month + day + hour + ms;
      //alert("type->"+file.type+"\nkey-->"+key + "\ntoken-->"+this.token)

      return true;
    }
  }
};
</script>  

<style scoped>
#editor {
  position: absolute;
  top: 210px;
  left: 320px;
  line-height: 15px;
}
.alert {
  line-height: 30px;
}
.form {
  position: absolute;
  width: 650px;
  left: 320px;
  top: 150px;
}
.el-main {
  line-height: 15px;
}
.category {
  position: absolute;
  top: 700px;

  margin-left: 150px;
}
.uploadImg {
  position: absolute;
  top: 120px;
  left: 85px;
  z-index: 100;
  color: gray;
  font-size: 12px;
}
.submit {
  position: absolute;
  top: 820px;
  left: 380px;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
  background: white;
  margin-left: 55px;
  position: absolute;
  top: 115px;
}

.el-upload-list__item-name {
  position: absolute;
  top: -89px;
  left: 180px;
}
.avatar {
  width: 120px;
  height: 120px;
  display: block;
  margin-left: 55px;
  position: absolute;
  top: 115px;
}
#editor {
  background-color: white;
  margin-left: 50px;
  margin-top: 200px;
}
.contentlabel {
  position: absolute;
  top: 230px;
  left: 320px;
}
.crumb {
  position: absolute;
  top: 120px;
  left: 330px;
}
</style>