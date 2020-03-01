
Page({
  data: {
  },
  onchange: function () {
    console.log('用户点击确定')
    wx.navigateTo({
      url: '../obstetrics/index',//跳转的路径
    })
  },

  onservice:function(){
    wx.navigateTo({
      url: '../doctors_choose/index',//跳转的路径
    })
  }

 
})