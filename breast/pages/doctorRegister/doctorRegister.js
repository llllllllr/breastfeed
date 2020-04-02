import md5 from '../../assert/js/md5.js';
var QiniuUploader = require('../../assert/js/qiniuUploader.js');
const app = getApp();
Page({
  /**
   * 页面的初始数据
   */
  data: {
    object:'',
    imgList: [],
    imgToken:''
  },


  onLoad(){
      this.getToken()
  },
  //显示模态框，errmsg-表单错误信息
  showModal(errmsg) {
    wx.showModal({
      title: '温馨提示',
      content: errmsg,
      showCancel: false
    })
  },

  formSubmit: function (e) {
    var that = this;
    var dataList = e.detail.value;
    var name = dataList.name;
    var licenseNumber = dataList.licenseNumber;
    var userName = dataList.userName;
    var expertIn = dataList.expertIn;
    var salt = app.globalData.salt;
    var userPassword = dataList.userPassword;
    var openId = app.globalData.openId;
    console.log('openId:',openId)
    //密码校验
    if (userPassword == undefined || userPassword.length < 6) {
      this.showModal("请检查密码输入是否大于6位");
      return;
    }

//密码用 md5 加密
    var str = "" + salt.charAt(0) + salt.charAt(4) + userPassword + 
    salt.charAt(7) + salt.charAt(4) + salt.charAt(6);
    var psd = md5(str);

    // console.log("图片列表",this.data.imgList)
    // console.log('加密后密码:',psd)
    
    // console.log('表单数据：' , dataList);

    wx.request({
      url: getApp().globalData.serverUrl + '/doctor/register',
      method:'GET',
      data:{
        name:name,
        userName:userName,
        userPassword:psd,
        licenseNumber: licenseNumber,
        expertIn: expertIn,
        imgUrl:that.data.imgList[0],
        openId:openId
      },
      success:function(res){
        console.log('请求成功:',res)
        if(res.data.status == 0){
          that.showModal(res.data.msg)
        } else {
            wx.navigateTo({
              url: '../signIn/signIn',
            })
        }
      },

      fail:function(res){
        console.log('请求失败:', res)
        that.showModal("系统错误，请稍后再试！")
      }

    })

  },
   //上传图片的token
   getToken: function () {
    var that = this;
    wx.request({
      url: getApp().globalData.serverUrl + '/article/getToken',
      method: 'GET',
      data: {
        bucket: 'wdtc'
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
        console.log("aaaa" + that.data.imgToken)
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
 
})

