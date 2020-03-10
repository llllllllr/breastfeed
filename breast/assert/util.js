//异步无法自动返回消息
function getUserId(tokenName, callBack) {
  var that = this;
  var serverUrl = getApp().globalData.serverUrl;
  var cookie = wx.getStorageSync(tokenName);
  if (cookie) {
    wx.request({
      header: {
        cookie: cookie
      },
      url: serverUrl + '/user/check',
      method: 'GET',
      success: function (res) {
        callBack(res.data)
      }
    })
  } else {
    that.showToLogion()
  }
}
function GetUserOpenID(userID, setOpenId) {
  var serverUrl = getApp().globalData.serverUrl;
    wx.request({
      url: serverUrl + '/user/openId',
      data:{
        userId:userID
      },
      method: 'GET',
      success: function (res) {
        setOpenId(res.data)
      }
    })
  
}

function getACtoken(sendTodoctor){
    var appData = getApp().globalData;
    wx.request({
      url: 'https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid='+appData.APP_ID+'&secret='+appData.APP_SECRET,
      method:'GET',
      success:function(res){
          sendTodoctor(res.data.access_token)
      }
    })
}
function showModal_(content,confirmText,cancelText,callBack){
  wx.showModal({
    title: '提示',
    content: content,
    confirmColor: "#d4237a",
    confirmText: confirmText,
    cancelText:cancelText,
    success(res) {
      if (res.confirm) {
          callBack('确定')
      } else if (res.cancel) {
        console.log('用户点击取消')
      }
    }
  })
}
function showToLogion() {
  wx.showModal({
    title: '提示',
    content: '请先登录',
    confirmColor: "#d4237a",
    confirmText: '去登录',
    success(res) {
      if (res.confirm) {
        wx.navigateTo({
          url: '/pages/signIndex/signIndex',
        })
      } else if (res.cancel) {
        console.log('用户点击取消')
      }
    }
  })
}
module.exports = {
  getUserId: getUserId,
  showToLogion: showToLogion,
  GetUserOpenID: GetUserOpenID,
  getACtoken:getACtoken,
  showModal_:showModal_

}