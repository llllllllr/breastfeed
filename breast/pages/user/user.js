const app = getApp();
Page({

  data: {

  },

  onLoad: function (options) {

  },

  //退出登录 清除存储在本地的信息
  logOut(){
    wx.removeStorageSync('userToken');
    wx.removeStorageSync('userTokenDate');
    wx.removeStorageSync('doctorToken');
    wx.removeStorageSync('doctorTokenDate');
    app.clearPrivacyData()
  },

  chagetoLogin:function(){
    wx.navigateTo({
      url: '../signIndex/signIndex',
    })
  },
  changeToBabyLine:function(){
       wx.navigateTo({
         url: '../lineIndex/lineIndex',
       })
  },
  changeToques:function(){
     wx.navigateTo({
       url: '../myconsult/myconsult',
     })
  },
  changeTocoll:function()
  {
    var that = this;
    var serverUrl = app.globalData.serverUrl;
    console.log(serverUrl)
    var cookie = wx.getStorageSync('userToken');
    console.log(cookie)
    if(cookie )
    {
      wx.request({
        header:{
          cookie:cookie
        },
        url:serverUrl+ '/user/check',
        method:'GET',
        success:function(res){
          console.log(res)
          //认证成功，得到userId
          if(res.data.data )
          {
              wx.navigateTo({
                url: '../mycoll/mycoll?userid='+res.data.data,
              })
          }
        }
      })
    }
    else{
      wx.showModal({
        title: '提示',
        content: '请先登录',
        confirmColor:"#d4237a",
        confirmText	:'去登录',
        success (res) {
          if (res.confirm) {
            wx.navigateTo({
              url: '../signIndex/signIndex',
            })
          } else if (res.cancel) {
            console.log('用户点击取消')
          }
        }
      })
    }
  }

})