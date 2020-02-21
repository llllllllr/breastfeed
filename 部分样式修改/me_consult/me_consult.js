// page/zixun/zixun.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    con_height: "1250rpx",
    current: 0,
    id: 3,
    zixun_ing: [
      {
        "id": "1",
        "question": "呼吸不顺畅怎么办？",
        "doctor": "钟南山",
        "outcome": "emmm",
        "time": "2020/01/04"
      },
      {
        "id": "2",
        "question": "呼吸不顺畅怎么办？",
        "doctor": "钟南山",
        "outcome": "emmm",
        "time": "2020/01/05"
      },
      {
        "id": "3",
        "question": "呼吸不顺畅怎么办？",
        "doctor": "钟南山",
        "outcome": "emmm",
        "time": "2020/01/07"
      },
    ],
    zixun_ed: [
      {
        "id": "1",
        "question": "呼吸不顺畅怎么办？",
        "doctor": "钟南山",
        "outcome": "emmm",
        "time": "2020/01/02"
      },
      {
        "id": "2",
        "question": "呼吸不顺畅怎么办？",
        "doctor": "钟南山",
        "outcome": "emmm",
        "time": "2020/01/09"
      },
      {
        "id": "3",
        "question": "呼吸不顺畅怎么办？",
        "doctor": "钟南山",
        "outcome": "emmm",
        "time": "2020/01/08"
      },
    ]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      bg_color1: "#000000",
      n1_color: "#FFFFFF"
    })
    if (this.data.count_all >= 7 || this.data.id == 3) this.setData({
      con_height: "100%"
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

  },

  read_ing: function (e) {
    var a = e.currentTarget.dataset.id;
    wx.navigateTo({
      url: '../zx_ing/' + a,
    })
  },
  read_ed: function (e) {
    var b = e.currentTarget.dataset.id;
    wx.navigateTo({
      url: '../zx_ed/' + b,
    })
  },

  tap_n1: function () {
    if (this.data.count_all >= 7 && this.data.id == 1) this.setData({
      con_height: "100%"
    })
    else this.setData({
      con_height: "1250rpx"
    })
    this.setData({
      current: 0,
      id: 3,
      bg_color1: "#000000",
      n1_color: "#FFFFFF",

      bg_color2: "#FFFFFF",
      n2_color: "#000000",

      bg_color3: "#FFFFFF",
      n3_color: "#000000"
    })

  },

  tap_n2: function () {
    if (this.data.count_zw >= 7 && this.data.id == 2) this.setData({
      con_height: "100%"
    })
    else this.setData({
      con_height: "1250rpx"
    })
    this.setData({
      current: 1,
      id: 1,
      bg_color2: "#000000",
      n2_color: "#FFFFFF",

      bg_color1: "#FFFFFF",
      n1_color: "#000000",

      bg_color3: "#FFFFFF",
      n3_color: "#000000"
    })
  },

  tap_n3: function () {
    if (this.data.count_qx >= 7 && this.data.id == 3) this.setData({
      con_height: "100%"
    })
    else this.setData({
      con_height: "1250rpx"
    })
    this.setData({
      current: 2,
      id: 2,
      bg_color3: "#000000",
      n3_color: "#FFFFFF",

      bg_color1: "#FFFFFF",
      n1_color: "#000000",

      bg_color2: "#FFFFFF",
      n2_color: "#000000"
    })
  },
})