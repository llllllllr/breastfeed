//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    isTakePhoto: false,
    takeInterval: '',
    value: '',
    src: '',
  },

  TakePhotoInterval: function () {
    var number = setInterval(this.takePhoto, 5000)
    this.setData(
      {
        takeInterval: number
      }
    )
    console.log(this.takeInterval)
  },

  setTakePhoto: function () {
    this.setData(
      {
        isTakePhoto: true
      }
    )
    console.log("setTakePhoto")
    setTimeout(this.TakePhotoInterval, 500);
  },

  takePhoto: function () {
    const ctx = wx.createCameraContext()

    ctx.takePhoto({
      quality: 'high',
      success: (res) => {
        console.log(res);
        this.setData({
          src: res.tempImagePath
        })
        wx.showLoading({
          title: '正在核验身份.....'
        })
        wx.uploadFile({
          //url: 'http://127.0.0.1:8887/face/register',
          url: app.globalData.serverUrl + '/face/register',
          filePath: res.tempImagePath,
          name: 'file',
          success: (res) => {
            console.log("success");
            console.log(res);
            wx.hideLoading();
            wx.showModal({
              title: '提示',
              content: '',
              showCancel: false
            })
            wx.redirectTo({
              url: '../test/test',//医生咨询页（刚刚的页面）
            })
          },
          fail: (res) => {
            console.log("fail"),
              console.log(res);
            wx.redirectTo({
              url: '../test/test',// 
            })
          }
        })
      }
    })


  },

  stopTakePhoto() {
    console.log("停止人脸识别");
    clearInterval(this.takeInterval);
    wx.navigateTo({
      url: '../signIn/signIn?object=/user',
    })
  }
  ,

  //事件处理函数
  bindViewTap: function () {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  onLoad: function () {
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else if (this.data.canIUse) {
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
    }
  },
  getUserInfo: function (e) {
    console.log(e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  },

})
