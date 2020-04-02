// pages/consult_detail/consult_detail.js
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    oid:'',
    doctorId:'',
    status:'',
    userId:'',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log('订单详情参数:',options)
    this.setData({
      oid:options.oid,
      doctorId:options.doctorId,
      status:options.status,
      userId:options.userId,
    })
  },

  //弹出订阅消息提示
  sendSubMessage() {
    var tempId = app.globalData.sendToDoctortmpId;
    wx.requestSubscribeMessage({
      tmplIds: [tempId],
      success: function (res) {
        if (res[tempId] === 'accept') {
          wx.showToast({
            title: '订阅OK！',
          })
        }
        console.log('订阅消息成功', res)
        //成功
      },
      fail(err) {
        //失败
        console.error('订阅消息失败：', err);
      }
    })

  },

  to_consult_chatroom(){
    this.sendSubMessage()
    wx.navigateTo({
      url: '../consult_chatroom/consult_chatroom?doctorId=' + this.data.doctorId + '&oid=' + this.data.oid + '&userId=' + this.data.userId,
    })
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