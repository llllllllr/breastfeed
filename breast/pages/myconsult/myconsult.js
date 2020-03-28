// pages/myconsult/myconsult.js
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
     myconsult:true, //有无咨询显示不同
    consultOrderList:[],  //咨询订单
  },

//点击订单跳转到订单详情
  to_consult_detail(e) {
    console.log('myconsult 参数:',e)
    var doctorId = e.currentTarget.dataset.doctorid;     //用 data-###  后面参数名无论大小写系统全部转化为小写
    var oid = e.currentTarget.dataset.oid;
    var status = e.currentTarget.dataset.status;
    console.log('myconsult: doctorId ' + doctorId)
    wx.navigateTo({
      url: '../consult_detail/consult_detail?doctorId=' + doctorId + '&oid=' + oid + '&status=' + status,
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    wx.showLoading({
      title: '加载中',
      duration:1000
    }),

  //获取咨询订单数据
  wx.request({
    url: app.globalData.serverUrl + '/selectConsultOrderByUserId',
    method:"GET",
    data:{
      userId:app.globalData.userInfor.userId
    },
    success(res){
      console.log("获取咨询订单成功:",res)
      var newList = res.data.data;
      for(var i = 0; i < newList.length; i++)
        newList[i].createTime = app.jsDateFormatter(new Date(newList[i].createTime));

      that.setData({
        consultOrderList:newList
      })

      console.log("咨询订单", that.data.consultOrderList)
    },
    fail(res){
      console.log("获取咨询订单失败:",res)
      
    }

  })

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})