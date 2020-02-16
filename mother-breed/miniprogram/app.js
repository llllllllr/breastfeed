//app.js
App({

  //微课class
  mini_class: [{
    class_id: 1,
    url: 'http://localhost:1314/class1.mp4',
  }, {
    class_id: 2,
      url: 'http://wxsnsdy.tc.qq.com/105/20210/snsdyvideodownload?filekey=30280201010421301f0201690402534804102ca905ce620b1241b726bc41dcff44e00204012882540400&bizid=1023&hy=SH&fileparam=302c020101042530230204136ffd93020457e3c4ff02024ef202031e8d7f02030f42400204045a320a0201000400',
  }, {

    class_id: 3,
    url: 'http://localhost:1314/class1.mp4',
  }, {
    class_id: 4,
    url: 'http://localhost:1314/class1.mp4',
  }],

    globalData: {
    class_num: null,
    url:null
  },
  onLaunch: function () {
    //调用API从本地缓存中获取数据
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs.slice(0, 40))
  }
})
