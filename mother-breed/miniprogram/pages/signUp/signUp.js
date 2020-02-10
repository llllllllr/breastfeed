Page({

  /**
   * 页面的初始数据
   */
  data: {
    codename: '获取验证码', //按钮文字
    currentTime: 61, //倒计时
    disabled: false, //按钮是否禁用
    phone: '', //获取到的手机栏中的值
    VerificationCode: '',//接口获取到的验证码
    Code: '',//用户输入验证码
    NewChanges: '',//密码
    NewChangesAgain: '',//确认密码
    IDNumber:'',//身份证号
    UserName:'',//真实姓名
    success: false,
    state: ''
  },
  /**
    * 获取验证码
    */
  return_home: function (e) {
    wx.navigateTo({
      url: '/pages/login/login',
    })

  },
  handleInputPhone: function (e) {
    this.setData({
      phone: e.detail.value
    })
  },
  handleVerificationCode: function (e) {
    console.log(e);
    this.setData({
      Code: e.detail.value
    })
  },
  handleNewChanges: function (e) {
    console.log(e);
    this.setData({
      NewChanges: e.detail.value
    })
  },
  handleNewChangesAgain: function (e) {
    console.log(e);
    this.setData({
      NewChangesAgain: e.detail.value
    })

  },
  handleUserName: function (e) {
    console.log(e);
    this.setData({
      UserName: e.detail.value
    })

  },
  handleIDNumber: function (e) {
    console.log(e);
    this.setData({
      IDNumber: e.detail.value
    })

  },
  
  getCode: function () {
    var a = this.data.phone;
    var _this = this;
    var myreg = /^(14[0-9]|13[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$$/;
    if (this.data.phone == "") {
      wx.showToast({
        title: '手机号不能为空',
        icon: 'none',
        duration: 1000
      })
      return false;
    } else if (!myreg.test(this.data.phone)) {
      wx.showToast({
        title: '请输入正确的手机号',
        icon: 'none',
        duration: 1000
      })
      return false;
    } else {
      wx.request({
        data: {},
        /**********************************************/ 
        'url': 接口地址,
        /*********************************************/ 
        success(res) {
          console.log(res.data.data)
          _this.setData({
            VerificationCode: res.data.data
          })
          var num = 61;
          var timer = setInterval(function () {
            num--;
            if (num <= 0) {
              clearInterval(timer);
              _this.setData({
                codename: '重新发送',
                disabled: false
              })

            } else {
              _this.setData({
                codename: num + "s"
              })
            }
          }, 1000)
        }
      })

    }


  },
  //获取验证码
  getVerificationCode() {
    this.getCode();
    var _this = this 
    _this.setData({
      disabled: true
    })
  },
  takePhoto() {
    const ctx = wx.createCameraContext()
    ctx.takePhoto({
      quality: 'high',
      success: (res) => {
        this.setData({
          src: res.tempImagePath
        })
      }
    })
  },
  submit: function (e) {
    var that = this
    if (this.data.Code == '') {
      wx.showToast({
        title: '请输入验证码',
        image: '/images/error.png',
        duration: 2000
      })
      return
    } else if (this.data.Code != this.data.VerificationCode) {
      wx.showToast({
        title: '验证码错误',
        image: '/images/error.png',
        duration: 2000
      })
      return
    }
    else if (this.data.NewChanges == '') {
      wx.showToast({
        title: '请输入密码',
        image: '/images/error.png',
        duration: 2000
      })
      return
    } else if (this.data.NewChangesAgain != this.data.NewChanges) {
      wx.showToast({
        title: '两次密码不一致',
        image: '/images/error.png',
        duration: 2000
      })
      return
    } else {
      var that = this
      var phone = that.data.phone;
      wx.request({
        url: getApp().globalData.baseUrl + '/Coachs/insert',
        method: "POST",
        data: {
          coachid: phone,
          coachpassword: that.data.NewChanges
        },
        header: {
          "content-type": "application/x-www-form-urlencoded"
        },
        success: function (res) {
          wx.showToast({
            title: '提交成功~',
            icon: 'loading',
            duration: 2000
          })
          console.log(res)
          that.setData({
            success: true
          })
        }
      })
    }
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})

