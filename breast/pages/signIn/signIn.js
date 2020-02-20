

var app = getApp();
Page({

  data: {
    modalName:null,
    modalContent:""
  },

  onLoad: function (options) {

  },
 //显示模态框，errmsg-表单错误信息
 showModal(errmsg) {
  this.setData({
    modalContent:errmsg,
    modalName:"Modal"
  })
},

//隐藏模态框
hideModal(e) {
  this.setData({
    modalName: null
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
  checkLogin:function(username,password){
    var that = this;
    wx.request({
      url: app.globalData.serverUrl + 'user/sign',
      method:"GET",
      data:{
        userName:username,
        userPassword:password
      },
      success:function(res){
        console.log(res)
      }
      

    })
  }
})