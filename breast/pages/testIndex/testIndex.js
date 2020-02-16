

var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {

  },

  onLoad: function (options) {
    var serverUrl =app.globalData.serverUrl;
    wx.request({
      
      url: serverUrl + '/question/getQustions',
      method:'GET',
      data:{
        tid:1
      },
      success:function(res){
        console.log(res)
      }
    })
  },

})