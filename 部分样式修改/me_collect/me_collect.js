// page/shoucang/shoucang.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    con_height: "1250rpx",
    id: 5,
    count_all: 8,
    count_tw: 3,
    count_yp: 1,
    count_sp: 1,
    count_wz: 3,
    tuwen: [
      {
        "id": "1",
        "photo": "../../images/cover.jpg",
        "title": "长得帅是一种怎样的体验？",
        "time": "2020/02/02",
      },
      {
        "id": "2",
        "photo": "../../images/cover.jpg",
        "title": "长得丑是一种怎样的体验？",
        "time": "2020/02/05",
      },
      {
        "id": "3",
        "photo": "../../images/cover.jpg",
        "title": "当程序员是一种怎样的体验？",
        "time": "2020/02/08",
      }
    ],
    wenzhang: [
      {
        "id": "1",
        "photo": "../../images/cover.jpg",
        "title": "怎么当上海贼王",
        "time": "2020/02/02",
      },
      {
        "id": "2",
        "photo": "../../images/cover.jpg",
        "title": "如何打开血轮眼？",
        "time": "2020/02/05",
      },
      {
        "id": "3",
        "photo": "../../images/cover.jpg",
        "title": "怎样学会螺旋丸？",
        "time": "2020/02/08",
      }
    ],
    shipin: [
      {
        "src": "http://wxsnsdy.tc.qq.com/105/20210/snsdyvideodownload?filekey=30280201010421301f0201690402534804102ca905ce620b1241b726bc41dcff44e00204012882540400&bizid=1023&hy=SH&fileparam=302c020101042530230204136ffd93020457e3c4ff02024ef202031e8d7f02030f42400204045a320a0201000400",
        "time": "2020/02/16",
      },
    ],
    yinpin: [{
      "poster": "http://y.gtimg.cn/music/photo_new/T002R300x300M000003rsKF44GyaSk.jpg?max_age=2592000",
      "name": "此时此刻",
      "author": "许巍",
      "src": "http://ws.stream.qqmusic.qq.com/M500001VfvsJ21xFqb.mp3?guid=ffffffff82def4af4b12b3cd9337d5e7&uin=346897220&vkey=6292F51E1E384E06DCBDC9AB7C49FD713D632D313AC4858BACB8DDD29067D3C601481D36E62053BF8DFEAF74C0A5CCFADD6471160CAF3E6A&fromtag=46",
      "time": "2020/02/02"
    },
    ]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (this.data.count_all >= 6) this.setData({
      con_height: "100%"
    }) 
    else this.setData({
      con_height: "1250rpx"
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

  tw_tap: function (e) {
    var a = e.currentTarget.dataset.id;
    wx.navigateTo({
      url: '../sc1/' + a,
    })
  },
  yp_tap: function (e) {
    var b = e.currentTarget.dataset.id;
    wx.navigateTo({
      url: '../sc2/' + b,
    })
  },
  /*sp_tap: function (e) {
    var c = e.currentTarget.dataset.id;
    wx.navigateTo({
      url: '../sc3/'+c,
    })
  },*/
  wz_tap: function (e) {
    var d = e.currentTarget.dataset.id;
    wx.navigateTo({
      url: '../sc4/' + d,
    })
  },

  tap_tuwen: function () {
    if (this.data.count_tw >= 6) this.setData({
      con_height: "100%"
    })
    else this.setData({
      con_height: "1250rpx"
    })
    this.setData({
      id: 1,
      color_1: "#000000",
      color_2: "rgb(144, 182, 252)",
      color_3: "rgb(144, 182, 252)",
      color_4: "rgb(144, 182, 252)"
    })

  },

  tap_yinpin: function () {
    if (this.data.count_yp >= 6) this.setData({
      con_height: "100%"
    })
    else this.setData({
      con_height: "1250rpx"
    })
    this.setData({
      id: 2,
      color_2: "#000000",
      color_1: "rgb(144, 182, 252)",
      color_3: "rgb(144, 182, 252)",
      color_4: "rgb(144, 182, 252)"
    })

  },

  tap_shipin: function () {
    if (this.data.count_sp >= 6) this.setData({
      con_height: "100%"
    })
    else this.setData({
      con_height: "1250rpx"
    })
    this.setData({
      id: 3,
      color_3: "#000000",
      color_1: "rgb(144, 182, 252)",
      color_2: "rgb(144, 182, 252)",
      color_4: "rgb(144, 182, 252)"
    })

  },

  tap_wenzhang: function () {
    if (this.data.count_wz >= 6) this.setData({
      con_height: "100%"
    })
    else this.setData({
      con_height: "1250rpx"
    })
    this.setData({
      id: 4,
      color_4: "#000000",
      color_2: "rgb(144, 182, 252)",
      color_3: "rgb(144, 182, 252)",
      color_1: "rgb(144, 182, 252)"
    })

  },
})