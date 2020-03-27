// pages/forget_pass/forget_pass.js
Page({

  data: {
  
       role:0,//0-用户 1-医生
  },
  onLoad: function (options) {

       
  },
  formSubmit: function (e){
    //表单数据
    var formList = e.detail.value;
    console.log(formList);
    var nickName = formList.cardNum;
    var password = formList.password;
    var repassword = formList.repassword;
  }
})