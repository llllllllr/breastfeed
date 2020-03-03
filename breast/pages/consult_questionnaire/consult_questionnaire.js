// pages/service/index.js

var QiniuUploader = require('../../assert/js/qiniuUploader.js')
const app = getApp();
Page({
  data: {
    doctorId: '',//医生的标识符
    question: '',//所有问题
    imagePaths: '/aaa', //上传图片列表    //图片的上传处理没有完成
    name: '', //联系姓名
    phone: '',//联系人电话
    consultCost: 5, //咨询费用
    height: 20,
    focus: false,
    imgList: [],
    modalName: null,
    imgToken: '',
    imageURL: '',
    userid:-1
  },
  onLoad: function (options) {
    this.getToken()
    wx.showLoading({
      title: '加载中',
    })
    console.log('医生id参数：', options)
    this.setData({
      doctorId: options.id
    })
  },
  onReady: function () {
    wx.hideLoading({
      complete: (res) => { },
    })
  },

  bindButtonTap: function () {
    this.setData({
      focus: true
    })
  },
  bindTextAreaBlur: function (e) {
    console.log(e.detail.value)
  },
  bindFormSubmit: function (e) {
    console.log(e.detail.value.textarea)
  },


 //检验token,获取用户id
 getUserId: function () {
  var that = this;
  var serverUrl = getApp().globalData.serverUrl;
  console.log(serverUrl)
  var cookie = wx.getStorageSync('userToken');
  console.log(cookie)
  if (cookie) {
    wx.request({
      header: {
        cookie: cookie
      },
      url: serverUrl + '/user/check',
      method: 'GET',
      success: function (res) {
        console.log(res)
        //认证成功，得到userId
        if (res.data.status == 1) {
          that.setData({
            userid: res.data.data
          })
          that.ifColl();
        }
        else {
          wx.showToast({
            title: '请先登录',
          })
        }
      }
    })
  }
},
  //设置值
  handlerQuestion(e) {
    console.log(e);
    this.setData({
      question: e.detail.value
    })
  },
  handlerName(e) {
    console.log(e);
    this.setData({
      name: e.detail.value
    })
  },
  handlerPhone(e) {
    var phoneTest = ' ';
    if (!(/^1[3456789]\d{9}$/.test(e.detail.value))) {
      this.showModal("请输入合理的手机号，以便接收消息");
    }
    console.log(e);
    this.setData({
      phone: e.detail.value
    })
  },

  onchange: function () {
    console.log('用户点击确定')
    var now = new Date();
    var createTime = app.jsDateFormatter(now);
    console.log('咨询订单参数：' + createTime + ' ' + this.data.doctorId + ' ' + 456)
    //创建 咨询订单
    wx.request({
      url: app.globalData.serverUrl + '/addConsultOrder',
      method: 'POST',
      header: {
        'content-type': 'application/x-www-form-urlencoded' // 默认值
      },
      data: {
        doctorId: this.data.doctorId,
        userId: app.globalData.userInfor.userId,
        createTime: createTime,
        lastingTime: 60 * 60 * 24 * 1000,
        contact: this.data.name,
        contactPhone: this.data.phone,
        symptomDescription: this.data.question,
        consultCost: this.data.consultCost
      },
      success(res) {
        console.log('返回参数:', res)
      },
      fail(res) {
        console.log('返回参数:', res)
      }
    })
    wx.navigateTo({
      // url: '../consult_chatroom/consult_chatroom?question=' + this.data.question + '&name=' + this.data.name +
      // '&imagePaths=' + this.data.imagePaths + '&phone=' + this.data.phone,//跳转的路径
      url: '../consult_chatroom/consult_chatroom?doctorId=' + this.data.doctorId

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

  /*图片模块 */
  //上传图片的token
  getToken: function () {
    var that = this;
    wx.request({
      url: getApp().globalData.serverUrl + '/article/getToken',
      method: 'POST',
      data: {
        bucket: 'useravatarr'
      },
      success: function (res) {
        console.log(res)
        that.setData({
          imgToken: res.data
        })
      }
    })
  },
  //这里待修改，等提交再传云端
  ChooseImage() {
    var that = this;
    var qiniu_key = this.data.userid + "consult_" + Date.parse(new Date()) / 1000 + ".jpg";
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