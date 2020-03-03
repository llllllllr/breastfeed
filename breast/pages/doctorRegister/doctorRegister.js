
var QiniuUploader = require('../../assert/js/qiniuUploader.js')
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userName:'',
    userPassword:'',
    name:'',
    licenseNumber:'',
    object:'',
    imgList: [],
    imgToken:''
  },
//职业证号改变
  LicenseNumberChange(e) {
    console.log(e);
    this.setData({
      licenseNumber: e.detail.value
    })
  },

//真实姓名改变
  NameChange(e) {
    console.log(e);
    this.setData({
      name: e.detail.value
    })
  },

  //密码值变化
  UserPasswordChange(e) {
    console.log(e);
    this.setData({
      userPassword: e.detail.value
    })
  },

//用户名改变
  UserNameChange(e) {
    console.log(e);
    this.setData({
      userName: e.detail.value
    })
  },
  //显示模态框，errmsg-表单错误信息
  showModal(errmsg) {
    this.setData({
      modalContent: errmsg,
      modalName: "Modal"
    })
  },


  //隐藏模态框
  hideModal(e) {
    this.setData({
      modalName: null
    })
  },

  formSubmit: function (e) {
    var dataList = e.detail.value;
    var name = dataList.name;
    var licenseNumber = dataList.licenseNumber;
    var userName = dataList.userName;
    var userPassword = dataList.userPassword;
    var expertIn = dataList.expertIn;


    //密码校验
    if (userPassword == undefined || userPassword.length < 6 ) {
      this.showModal("请检查密码输入是否大于6位");
      return;
    }
    console.log('表单数据：' , dataList);

    wx.request({
      url: getApp().globalData.serverUrl + '/doctor/register',
      method:'GET',
      data:{
        name:name,
        userName:userName,
        userPassword:userPassword,
        licenseNumber: licenseNumber,
        expertIn: expertIn,
        imgUrl:"hhh"
      },
      success:function(res){
        console.log('请求成功:',res)
      },

      fail:function(res){
        console.log('请求失败:', res)
      }

    })

  },
   //上传图片的token
   getToken: function () {
    var that = this;
    wx.request({
      url: getApp().globalData.serverUrl + '/article/getToken',
      method: 'POST',
      data: {
        bucket: 'doctoravatarr'
      },
      success: function (res) {
        console.log(res)
        that.setData({
          imgToken: res.data
        })
      }
    })
  },
  ChooseImage() {
    var that = this;
    var qiniu_key =  "doctorRegister_" + Date.parse(new Date()) / 1000 + ".jpg";
    wx.chooseImage({
      count: 1, //默认9
      sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album'], //从相册选择
      success: function (res) {
        var filePath = res.tempFilePaths[0];
        console.log(that.data.imgToken)
        // 交给七牛上传
        var img0 = 'imgList[0]'
        QiniuUploader.upload(filePath, (res) => {
          that.setData({
            'imageURL': res.imageURL,
            [img0]: res.imageURL
          });

          console.log('file url is: ' + res.imageURL);
        }, (error) => {
          console.log('error: ' + error);
        }, {
          region: 'SCN',
          domain: 'http://llllllllr.top/', // // bucket 域名，下载资源时用到。如果设置，会在 success callback 的 res 参数加上可以直接使用的 ImageURL 字段。否则需要自己拼接
          key: qiniu_key, // [非必须]自定义文件 key。如果不设置，默认为使用微信小程序 API 的临时文件名
          uptoken: that.data.imgToken, // 由其他程序生成七牛 uptoken
          uploadURL: 'https://up-z2.qiniup.com'
        }, (res) => {
          console.log('上传进度', res.progress)
          console.log('已经上传的数据长度', res.totalBytesSent)
          console.log('预期需要上传的数据总长度', res.totalBytesExpectedToSend)
          console.log("URLLL" + res.imageURL)
        }, () => {
          // 取消上传
        }, () => {
          // `before` 上传前执行的操作
        }, (err) => {
          // `complete` 上传接受后执行的操作(无论成功还是失败都执行)
          console.log("error")
        });

      }
    });
  },
  ViewImage(e) {
    wx.previewImage({
      urls: this.data.imgList,
      current: e.currentTarget.dataset.url
    });
  },
  DelImg(e) {
    wx.showModal({
      title: '提示',
      content: '确定要删除吗？',
      cancelText: '取消',
      confirmText: '确定',
      confirmColor: "#d4237a",
      success: res => {
        if (res.confirm) {
          this.data.imgList.splice(e.currentTarget.dataset.index, 1);
          this.setData({
            imgList: this.data.imgList
          })
        }
      }
    })
  },
  textareaBInput(e) {
    this.setData({
      textareaBValue: e.detail.value
    })
  }
})

