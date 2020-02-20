

var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
      testList:[],
      userInfo:[]
  },

  onLoad: function (options) {

    this.getTests();
    this.login();


  },

  getTests:function(){
      var serverUrl = app.globalData.serverUrl;
      var that = this;
      wx.request({
        url: serverUrl + "/test/getTestList",
        method:"GET",
        success:function(res){
            console.log(res.data.data)
             that.setData({
               testList:res.data.data
             })
        }
      })
  },
  chageToOp:function(event)
  {
    var index  = event.currentTarget.dataset.index;
    console.log(index);
    var id = this.data.testList[index].id;
    console.log("id--"+id);
    wx.navigateTo({
      url: '../question/question?id=' +  id,
    })
  },
  login:function(){
        var that =this;
        wx.getSetting({
          success(res) {
            if (!res.authSetting['scope.record']) {
              wx.authorize({
                scope: 'scope.record',
                success () {
                   that.getUserInfo();
                }
              })
            }else{
              that.getUserInfo();
            }
          }
        })
     
  },
  getUserInfo:function(){
       // 获取用户信息
       wx.getSetting({
        success: res => {
          if (res.authSetting['scope.userInfo']) {
            // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
            wx.getUserInfo({
              success: res => {
                // 可以将 res 发送给后台解码出 unionId
                that.data.userInfo = res.userInfo    
                
                // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
                // 所以此处加入 callback 以防止这种情况
                if (this.userInfoReadyCallback) {
                  this.userInfoReadyCallback(res)
                }
              }
            })
          }
        }
      })
  }

})