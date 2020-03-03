function getUserId(token) {
  var that = this;
  var serverUrl = getApp().globalData.serverUrl;
  console.log(serverUrl)
  var cookie = wx.getStorageSync('userToken');
  console.log(cookie)
  if (cookie) {
    wx.request({
      header: {
        cookie: cookie
      },
      url: serverUrl + '/user/check',
      method: 'GET',
      success: function (res) {
        console.log('1111111111111--->')
        console.log( res)
        //认证成功，得到userId
        if(res.data.data.status == 1)
        {
          getApp().globalData.userid=res.data.data
          return res.data.data.data
        }
        else 
          return -1;
      }
    })
  }
}

module.exports={
  getUserId:getUserId
}