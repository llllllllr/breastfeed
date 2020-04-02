var QiniuUploader = require('../../assert/js/qiniuUploader.js')
var showUtil = require('../../assert/util.js')
const app = getApp();
Page({
  data: {
    doctorId: '',//医生的标识符
    doctorName: '',//医生姓名
    doctorImg: '',//医生图片
    question: '',//所有问题
    name: '', //联系姓名
    phone: '',//联系人电话
    consultCost: 5, //咨询费用
    height: 20,
    focus: false,
    imgList: [],
    modalName: null,
    imgToken: '',
    imageURL: '',
    userid: -1,
    doctorOpenId: ''   //医生微信小程序标识符
  },
  onLoad: function (options) {

    //
    this.getImgToken();
    if(app.globalData.userInfor.userId == null){
         showUtil.showToLogion();
    }
    console.log('问卷接收参数：', options)
    wx.showLoading({
      title: '加载中',
    })
    console.log('医生id参数：', options)
    this.setData({
      doctorId: options.doctorId,
      doctorName: options.doctorName,
      doctorImg: options.doctorImg,
      doctorOpenId: options.openId
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

  onchange: function (e) {
    console.log('参数：', e)
    console.log('用户点击确定')

    //判断用户输入是否正确
    if (this.data.phone.length == 0 || this.data.question.length == 0) {
      this.showModal("请输入正确的电话号码和详细的病症！")
      return;
    }
    
    var now = new Date();
    var createTime = app.jsDateFormatter(now);
    var that = this;
    console.log('咨询订单参数：' + createTime + ' ' + this.data.doctorId )
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
        consultCost: this.data.consultCost,
        imgUrls: this.data.imgList.toString(),
        userOpenId: app.globalData.userInfor.openId,
        doctorOpenId: this.data.doctorOpenId,
      },
      success(res) {
        console.log('生成订单成功:', res)
        that.sendSubMessage()
        if (res.data.status == 1) {
          wx.navigateTo({
            url: '../consult_chatroom/consult_chatroom?doctorId=' + that.data.doctorId + '&oid=' + res.data.data + '&doctorImg=' + that.data.doctorImg,

          })
        }
        else
          //弹出 错误消息 提示框
          that.showModal(res.data.msg)

      },
      fail(res) {
        console.log('生成订单失败:', res)
      }
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
  getImgToken: function () {
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
  //选择图片并上传
  ChooseImage() {
    var that = this;
    var qiniu_key = this.data.userid + "_consult_" + Date.parse(new Date()) / 1000 + ".jpg";
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

  sendSubMessage() {
    var tempId = app.globalData.sendToDoctortmpId;
    wx.requestSubscribeMessage({
      tmplIds: [tempId],
      success: function (res) {
        if (res[tempId] === 'accept') {
          wx.showToast({
            title: '订阅OK！',
          })
        }
        console.log('订阅消息成功', res)
        //成功
      },
      fail(err) {
        //失败
        console.error('订阅消息失败：', err);
      }
    })

  }
})