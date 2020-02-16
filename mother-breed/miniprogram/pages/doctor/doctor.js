//index.js
//获取应用实例
// pages/service/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {

  },

  onmr: function (event) {
    console.log('用户点击确定')
    wx.navigateTo({
      url: '../consult_ questionnaire/index',//跳转的路径
    })
  },
  onxs: function (event) {
    console.log('用户点击确定')
    wx.navigateTo({
      url: '../consult_newborn/index',//跳转的路径
    })
  },
  onfk: function (event) {
    console.log('用户点击确定')
    wx.navigateTo({
      url: '../consult_obstetrics/index',//跳转的路径
    })
  },
  onck: function (event) {
    console.log('用户点击确定')
    wx.navigateTo({
      url: '../consult_ questionnaire/index',//跳转的路径
    })
  },


  /**,
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

});
