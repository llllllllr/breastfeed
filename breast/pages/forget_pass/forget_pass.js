// pages/forget_pass/forget_pass.js
import md5 from '../../assert/js/md5.js';
var app = getApp();
Page({

  data: {
    object:''    //user 为普通用户 doctor 为医生
  },

  onLoad: function (options) {
    console.log('忘记密码页面获得参数:',options)
    this.setData({
      object:options.object
    })
  },

  //显示模态框，errmsg-表单错误信息
  showModal(errmsg) {
    wx.showModal({
      title: '温馨提示',
      content: errmsg,
      showCancel: false
    })
  },

  formSubmit: function (e){
    var that = this;
    //表单数据
    var formList = e.detail.value;
    console.log(formList);
    var cardNum = formList.cardNum;
    var password = formList.password;
    var repassword = formList.repassword;

    //证件号码校验
    if (cardNum == null || cardNum.length < 1) {
      this.showModal("请输入正确的证件号码");
      return;
    }

    //密码校验
    if (password != undefined && (password.length < 6 || password != repassword)) {
      this.showModal("请检查密码输入是否大于6位且两次输入密码一致");
      return;
    }

    //将明文密码 用 md5 算法加密
    var salt = app.globalData.salt;
    var str = "" + salt.charAt(0) + salt.charAt(4) + password + salt.charAt(7) + salt.charAt(4) + salt.charAt(6);
    var psd = md5(str);

    console.log('请求路径:', app.globalData.serverUrl + '/' + this.data.object + '/findPassword')

    wx.request({
      method:'GET',
      url:app.globalData.serverUrl + '/' + this.data.object + '/findPassword',
      data:{
        licenseNumber: cardNum,
        password: psd
      },
      success(res){
        console.log('修改密码成功 ',res)
        if(res.data.status != 1)
          that.showModal("密码修改失败！请输入正确的证件号码！")
        else
          that.showModal("密码修改成功!")
      },
      fail(res){
        console.log('修改密码失败 ',res)
        that.showModal("密码修改失败！请稍后再试")
      }
    })
  }

  

})