//app.js
App({
  onLaunch: function () {
    wx.getSystemInfo({
      success: e => {
        this.globalData.StatusBar = e.statusBarHeight;
        let capsule = wx.getMenuButtonBoundingClientRect();
		if (capsule) {
		 	this.globalData.Custom = capsule;
			this.globalData.CustomBar = capsule.bottom + capsule.top - e.statusBarHeight;
		} else {
			this.globalData.CustomBar = e.statusBarHeight + 50;
		}
      }
    }),
    this.tokenSign()
  },
  globalData: {
    serverUrl:'http://localhost:8887',
    salt : "fdsfvxnmcvnew68sa5d54ds",
    userid:-3
  },

  //判断是否存在 token  若有则自动登录
  tokenSign(){
    var doctorToken = wx.getStorageSync('doctorToken');
    var doctorTokenDate = wx.getStorageSync('doctorTokenDate');
    var userToken = wx.getStorageSync('userToken');
    var userTokenDate = wx.getStorageSync('userTokenDate');
    var now = (new Date()).getTime();
    console.log(doctorToken + ' ' + doctorTokenDate + ' ' + userToken + ' ' + userTokenDate + ' ' + now);
    if(doctorToken != '' && doctorToken != null){
      console.log('doctorToken 不为空！');
      //token没有过期
      if(doctorTokenDate - now > 0){
        wx.request({
          url: this.globalData.serverUrl + '/doctor/tokenSign',
          method:"POST",
          header: {
            'content-type': 'application/x-www-form-urlencoded' // 默认值
          },
          data:{
            doctor_token:doctorToken,
          },
          success:function(res){
            console.log('token 登录成功')
          },
          fail:function(res){
            console.log('token 登录失败')
          }
        })
      }else{
        //token 过期了 将该值去除
        console.log('doctorToken 过期了 去除!')
        wx.removeStorageSync('doctorToken');
        wx.removeStorageSync('doctorTokenDate');
      }
    }else
      if (userToken != '' && userToken != null) {
        console.log('userToken 不为空！');
        //token没有过期
        if (userTokenDate - now > 0) {
          wx.request({
            url: this.globalData.serverUrl + '/user/tokenSign',
            method: "POST",
            header: {
              'content-type': 'application/x-www-form-urlencoded' // 默认值
            },
            data: {
              user_token: userToken,
            },
            success: function (res) {
              console.log('token 登录成功')
            },
            fail: function (res) {
              console.log('token 登录失败')
            }
          })
        } else {
          //token 过期了 将该值去除
          console.log('userToken 过期了 去除!')
          wx.removeStorageSync('userToken');
          wx.removeStorageSync('userTokenDate');
        }
      }
  }

})