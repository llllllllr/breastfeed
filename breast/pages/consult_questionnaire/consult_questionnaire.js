// pages/service/index.js
const app = getApp();
Page({
  data: {
    doctorId:'',//医生的标识符
    question:'',//所有问题
    imagePaths:'/aaa', //上传图片列表    //图片的上传处理没有完成
    name:'', //联系姓名
    phone:'',//联系人电话
    consultCost:5  //咨询费用
  },

  onLoad: function (options) {
    console.log('医生id参数：',options)
    this.setData({
      doctorId:options.id
    })
  },

//设置值
  handlerQuestion(e){
    console.log(e);
    this.setData({
      question:e.detail.value
    })
  },
  handlerName(e){
    console.log(e);
    this.setData({
      name: e.detail.value
    })
  },
  handlerPhone(e){
    console.log(e);
    this.setData({
      phone: e.detail.value
    })
  },

  onchange: function () {
    console.log('用户点击确定')
    var now = new Date();
    var createTime = app.jsDateFormatter(now);
    console.log('咨询订单参数：' + createTime +  ' ' + this.data.doctorId + ' ' + app.globalData.userInfor.userId)
    //创建 咨询订单
    wx.request({
      url: app.globalData.serverUrl + '/addConsultOrder',
      method:'POST',
      header: {
        'content-type': 'application/x-www-form-urlencoded' // 默认值
      },
      data:{
        doctorId:this.data.doctorId,
        userId: app.globalData.userInfor.userId,
        createTime:createTime,
        lastingTime: 60 * 60 * 24 * 1000,
        contact:this.data.name,
        contactPhone:this.data.phone,
        symptomDescription:this.data.question,
        consultCost:this.data.consultCost
      },
      success(res){
        console.log('返回参数:', res)
      },
      fail(res){
        console.log('返回参数:',res)
      }
    })
    wx.navigateTo({
      // url: '../consult_chatroom/consult_chatroom?question=' + this.data.question + '&name=' + this.data.name +
      // '&imagePaths=' + this.data.imagePaths + '&phone=' + this.data.phone,//跳转的路径
      url: '../consult_chatroom/consult_chatroom?doctorId=' + this.data.doctorId

    })
  },


})