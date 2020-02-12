 <template>
  <div class="container">
    <div class="form">
      <el-form :label-position="labelPosition" label-width="50px">
        <el-form-item label="标题">
          <el-input id="title" v-model="title"></el-input>
        </el-form-item>
      </el-form>
    </div>
          <span class="ui-text">上传封面图</span>
    <div class="upl-img">

    <el-upload
      class="avatar-uploader"
      action="http://upload-z2.qiniup.com"
      :show-file-list="false"
      :on-success="handleAvatarSuccess"
      :before-upload="beforeAvatarUpload"
      :data="form"
    >
      <img v-if="imageUrl" :src="imageUrl" class="avatar" />
      <i v-else class="el-icon-plus avatar-uploader-icon"></i>
    </el-upload>
    </div>
    <el-upload
  class="upload-mp3"
  action="http://upload-z2.qiniup.com"
  :limit="1"
  :before-upload="mp3_beforeAvatarUpload"
  :on-success="mp3_handleAvatarSuccess"
  :data="form"
  :show-file-list="false">
  <el-button size="small" type="primary">上传音频</el-button>
  <!-- <div slot="tip" class="el-upload__tip">只能上传MP3文件，且不超过2M</div> -->
</el-upload>
    <div class="category">
      <template>
        <el-radio v-model="radio" label="母乳喂养资料" border size="medium">母乳喂养资料</el-radio>
        <el-radio v-model="radio" label="新生儿护理资料" border size="medium">新生儿护理资料</el-radio>
        <el-radio v-model="radio" label="孕期保健知识" border size="medium">孕期保健知识</el-radio>
        <el-radio v-model="radio" label="其它" border size="medium">其它</el-radio>
      </template>
    </div>
    <el-button type="primary" class="submit" v-on:click="addAudio">提交</el-button>
  </div>
</template>



<script>
import Editor from "wangeditor";
import qs from "qs";
import * as iniu from "qiniu-js";
export default {
  data() {
    return {
      labelPosition: "right",
      audioUrl: "http://llllllllr.top/",
      title: "",
      radio: "母乳喂养资料",
      form: {
        token: "token",
        key: "key"
      },
      keysuffix:'',
      imageUrl:'',
      returnImgUrl:''
    };
  },
  mounted() {
   //获取token
      this.$axios({
        method: "post",
        url: "http://localhost:8887/article/getToken",
        xhrFields: { withCredentials: true }
      }).then(res => {
        this.form.token = res.data;
      });
 
  },
  methods: {

    //上传成功
    handleAvatarSuccess(res, file) {
      document.getElementsByClassName("ui-text")[0].style.display = "none";
      this.imageUrl = URL.createObjectURL(file.raw);
      //this.audioUrl = this.audioUrl + res.key;
      this.returnImgUrl="http://llllllllr.top/"+res.key;
      
    },
    mp3_handleAvatarSuccess(res, file){
          this.audioUrl = this.audioUrl + res.key;
          this.$message.success("上传成功！");
          
    },
    uploadProcess(event, file, fileList) {
      this.imgFlag = true;
      console.log(event.percent);
      this.percent = Math.floor(event.percent);
    },
    beforeAvatarUpload(file){
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG或者PNG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      this.readyForUpload();
      this.form.key = file.name+this.keysuffix
      alert(this.form.key)
      return isJPG && isLt2M;
    },
    mp3_beforeAvatarUpload(file) {
      const isMP4 = file.type == "audio/mp3";
      const isLt20M = file.size / 1024 / 1024 < 20;
      if (!isMP4) {
        this.$message.error("上传音频只能是 mp3 格式!");
        return false;
      }
      if (!isLt20M) {
        this.$message.error("上传音频大小不能超过20MB!");
        return false;
      }
      this.readyForUpload();
      this.form.key = file.name+this.keysuffix
    
      alert(isMP4 && isLt20M)
      return isMP4 && isLt20M;
    },
    
    //上传准备
    readyForUpload:function(){
 
    
      var myDate = new Date();
      var year = myDate.getFullYear();
      var month = myDate.getMonth();
      var day = myDate.getDay();
      var hour = myDate.getHours();
      var ms = myDate.getMilliseconds();
      this.keysuffix =  year + month + day + hour + ms;
      alert(this.keysuffix)
     
    },
    //添加音频
    addAudio: function() {
      var title = document.getElementById("title").value;

      this.$axios({
        method: "get",
        url: "http://localhost:8887/audio/add_editAudio",
        params: {
          id: -1,
          adourl: this.audioUrl,
          imgurl:this.returnImgUrl,
          title: title,
          category: this.radio
        }
      }).then(res => {
        console.log(res);
        var resData = res.data;
        if (resData.status == 1) {
           this.$message.success(resData.msg);
           this.$router.push({ path: "/audiolist" });
         // alert("成功");
        } else this.$message.error(resData.msg);
      });
    }
  }
};
</script>


<style>
.form {
  width: 700px;
  margin-left: 50px;
}

.category {
  margin-left: 100px;
  margin-top: 20px;
  position: absolute;
  height: 30px;
  top: 380px;
}
.container {
  margin-top: 60px;
}
.submit {
  position: absolute;
  top: 520px;
  left: 320px;
}

/* 上传封面 */

.ui-text{
  left:380px;
  top:220px;
  position:absolute;
  font-size: 12px;
  color: grey;
}
 .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    margin-left: 100px;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
    
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
  /* 上传封面结束 */

  /* 上传音频 */
  .upload-mp3{
    margin-left: 100px;
    margin-top: -120px;
  }
  .el-upload__tip{
    margin-top: -120px;
  }
  /* 上传音频结束 */
</style>