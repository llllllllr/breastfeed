// pages/doctors_choose/index.js

const app = getApp();
Page({
  data: {
    showfilter: false, //是否显示下拉筛选
    consultOrderList: [], //医生列表
    scrolltop: null, //滚动位置
    page: 0,  //分页
  },
  onLoad: function () { //加载数据渲染页面
    this.getConsultOrder()
  },

  getConsultOrder(){
    var that = this;
    var orderList = app.globalData.consultOrder;
    if (orderList == null || orderList.length == 0)          //获取 app.js 中是否 又咨询订单的记录，有则使用，无则想服务器请求数据
      wx.request({
        method: 'GET',
        url: app.globalData.serverUrl + '/selectConsultOrderByDoctorId',
        data: {
          doctorId: app.globalData.userInfor.id
        },
        success(res) {
          console.log('医生获取咨询订单成功:', res.data.data)
          var newList = res.data.data;
          //遍历咨询列表 格式化 日期的格式  图片的格式
          for (var i = 0; newList != null && i < newList.length; i++){
            newList[i].createTime = app.jsDateFormatter(new Date(newList[i].createTime));
            newList[i].imgUrls = newList[i].imgUrls.split(",");
          }
          that.setData({
            consultOrderList: newList
          })

          app.globalData.consultOrderList = newList;
          console.log('app consultOrderList:', app.globalData.consultOrderList)
        },
        fail(res) {
          console.log('医生获取咨询订单失败:', res)
        }
      })
      else
        this.data.consultOrderList = orderList;
  },

  to_doctorConsultDetail(e){
    console.log('跳转参数：',e)
    wx.navigateTo({
      url: '../doctor_consult_detail/doctor_consult_detail?consultOrderId=' + e.currentTarget.dataset.id,
    })
  }

  
})
