// pages/service/index.js
Page({
  data: {
    questionsall: [],//所有问题
    uploadimgs: [] //上传图片列表
  },

  onLoad: function (options) {
    uploadimgs: []
  },

  onchange: function () {
    console.log('用户点击确定')
    wx.navigateTo({
      url: '../doctors_choose/index',//跳转的路径
    })
  },


})