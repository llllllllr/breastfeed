// pages/doctorRegister/doctorRegister.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userName:'',
    userPassword:'',
    name:'',
    licenseNumber:'',
    object:''
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
        licenseNumber: licenseNumber
      },
      success:function(res){
        console.log('请求成功:',res)
      },

      fail:function(res){
        console.log('请求失败:', res)
      }

    })


  }

})

