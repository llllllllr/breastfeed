// page/xinxi/xinxi.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    change: 0,
    judge: false,
    name: "鸿星",
    index_w: 5,
    index_j:1,
    region: ['广东省', '广州市', '海珠区'],
    yunzhou: ["1周", "2周", "3周", "4周", "5周", "6周", "7周", "8周", "9周", "10周", "11周", "12周", "13周", "14周", "15周", "16周", "17周", "18周", "19周", "20周", "21周", "22周", "23周", "24周", "25周", "26周", "27周", "28周", "29周", "30周", "31周", "32周", "33周", "34周", "35周", "36周", "37周", "38周", "39周", "40周",],
    job:['医生','教师','工程师','工人','销售员','公务员','司机','程序员','艺术家','警察','服务员','厨师','作家','其他']
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

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

  },
  bindPickerChange_week: function (e) {
    this.setData({
      index_w: e.detail.value
    })
  },
  bindPickerChange_job: function (e) {
    this.setData({
      index_j: e.detail.value
    })
  },
  bindPickerChange_area: function (e) {
    this.setData({
      region: e.detail.value
    })
  },
  name_tap: function () {
    this.setData({
      judge: true,
      change: 1
    })
  },
  job_tap: function () {
    this.setData({
      judge: true,
      change: 2
    })
  },
  cancle: function () {
    this.setData({
      judge: false,
    })
  },
  customBindInput: function (e) {
    if (this.data.change == 1) {
      this.setData({
        name: e.detail,
      })
    }
    else if (this.data.change == 2) {
      this.setData({
        job: e.detail,
      })
    }
  }
  ,
  confirm: function () {
    this.setData({
      judge: false,
    })
  }
})