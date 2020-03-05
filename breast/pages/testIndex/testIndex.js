

var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
      testList:[],
      userInfo:[],
      userid:-1,
      score:0,
  },

  onLoad: function (options) {
    
    //获取用户的userid
    this.getUserId();
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
      url: '../question/question',
      data:{
        id:id,
        userid:this.data.userid
      }
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
  //检验token,获取用户id
  getUserId: function () {
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
          console.log(res)
          //认证成功，得到userId
          if (res.data.status == 1) {
            that.setData({
              userid: res.data.data
            })
            that.ifColl();
          }
          else {
            wx.showToast({
              title: '要登录才能获取积分',
            })
          }
        }
      })
    }
  }

})