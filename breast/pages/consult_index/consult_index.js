//index.js
//获取应用实例
// pages/service/index.js

// gynecology 妇科问题
// obstetrics 产科问题
// newborn 新生儿问题
// 
const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
  },

  onmr: function (event) {
    console.log('用户点击确定');
    if (app.globalData.userInfor != null && app.globalData.userInfor.userId != '') {
      wx.navigateTo({
        url: '../consult_help/consult_help',//跳转的路径
      })
    } else {
      console.log('跳转失败，用户未登录!');
    }
 
  },
  onxs: function (event) {
    console.log('用户点击确定');
    if (app.globalData.userInfor != null && app.globalData.userInfor.userId != ''){
    wx.navigateTo({
      url: '../consult_newborn/consult_newborn',//跳转的路径
    })
    } else {
      console.log('跳转失败，用户未登录!');
    }
  }
  ,
  onfk: function (event) {
    console.log('用户点击确定', app.globalData.user)
    if (app.globalData.userInfor != null && app.globalData.userInfor.userId != '') {
      wx.navigateTo({
        url: '../consult_obstetrics/consult_obstetrics',//跳转的路径
      })
    } else {
      console.log('跳转失败，用户未登录!');
    }
  
  },
  onck: function (event) {
    console.log('用户点击确定')
    wx.navigateTo({
      url: '../consult_ questionnaire/consult_ questionnaire',//跳转的路径
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
