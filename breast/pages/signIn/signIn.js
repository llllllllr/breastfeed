import  md5  from '../../assert/js/md5.js';
var app = getApp();
Page({
  data: {
    object:'',
  },

  onLoad: function (options) {
    console.log(options.object),
    this.setData({
    object:options.object
    })

  },
  //显示模态框，errmsg-表单错误信息
  showModal(errmsg) {
    wx.showModal({
      title: '温馨提示',
      content: errmsg,
      confirmColor: "#d4237a",
      showCancel: false
    })
  },

  isEmptyStr:function(obj){
    if(typeof obj == "undefined" || obj == null || obj == "")
            return true;
    else 
      return false;
  },
  formSubmit: function (e){
    var formList = e.detail.value;
    var nickName = formList.nickName;
    var password = formList.password;
    console.log(formList);
    if(this.isEmptyStr(nickName) || this.isEmptyStr(password) )
       {
         this.showModal("请检查输入是否为空");
         return;//不返回的话会继续向下执行
       }
       this.checkLogin(nickName,password)
  },
  checkLogin:function(username,inputPass){
    wx.showLoading({
      title: '登录中...',
      duration:1000
    })
    var that = this;
    //密码不能在浏览器里明文传输，md5加密
    var salt = app.globalData.salt;
    var str = ""+salt.charAt(0)+salt.charAt(4) + inputPass +salt.charAt(7) + salt.charAt(4) + salt.charAt(6);
    var psd = md5(str);
   // console.log(psd)
    console.log(app.globalData.serverUrl + this.data.object + '/sign'),
    console.log(app.globalData.serverUrl + '/' + this.data.object + '/sign'),
    wx.request({
      url: app.globalData.serverUrl + '/' + this.data.object + '/sign',
      method:"GET",
      data:{
        userName:username,
        userPassword:psd
      },
      success:function(res){
        var responseData = res.data.data;
        console.log(res);

        //检查是否返回错误消息
       if(res.data.status == 0){
        console.log('登录错误消息:',res.data.msg)
         that.showModal(res.data.msg)
         return ;
       }else{
         //将 用户 或者 医生 的信息保存 再 app.js 的 globalData 中
         app.globalData.object = that.data.object;
         app.globalData.userInfor = responseData;
         console.log('用户登录成功，检查信息是否放入 app.js', app.globalData.userInfor)

         //保存 Cookie
         if (res && res.header && res.header['doctor_token'] && res.header['doctor_token_date']) {
           wx.setStorageSync('doctorToken', res.header['doctor_token']);   //保存Cookie到Storage
           wx.setStorageSync('doctorTokenDate', res.header['doctor_token_date']);
           console.log('保存doctorToken  ' + res.header['doctor_token'] + ' ' + res.header['doctor_token_date']);
         }
         else if (res && res.header && res.header['user_token'] && res.header['user_token_date']) {
           wx.setStorageSync('userToken', res.header['user_token']);   //保存Cookie到Storage
           wx.setStorageSync('userTokenDate', res.header['user_token_date']);
           console.log('保存userToken  ' + res.header['user_token']);
         }
       }

        console.log(that.data.object)
        //登录成功后跳转
        if (that.data.object == "doctor"){
          console.log("医生跳转到")
          //医生跳转到我的订单页面
          wx.switchTab({
            url: '../index/index',
          })
        }
        else {
          console.log("用户跳转到")
          //用户跳转到首页
          wx.switchTab({
            url: '../index/index',
          })
        }
      },
      fail(res){
        console.log('登录失败:',res)
        that.showModal("系统错误，请稍后再试！")
      }
    })
  },
  forgetPass:function() {
      wx.navigateTo({
        url: '../forget_pass/forget_pass?object=' + this.data.object,
      })
  }
})