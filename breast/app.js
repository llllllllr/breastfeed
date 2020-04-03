//app.js
const url_pre = 'http://api.tszh.wiwcc.com';
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
    //查询是否存有 token => 登录
    this.tokenSign()
    //加载 微信小程序 唯一标识符
    this.getOpenId(),
    //请求医生列表
    this.getDoctorList()
  },

  globalData: {
    //serverUrl:'https://www.llllllllr.top/breast-0.0.1-SNAPSHOT',
    serverUrl:'http://localhost:8887',
    salt : "fdsfvxnmcvnew68sa5d54ds",
    userId:-3,
    object:'',  //登录的用户群体：1.医生 2.普通用户
    userInfor:null,   //记录用户的信息
    doctorList:[],
    //不同账号需要更改 密匙 否则 获取 openid将出错
    APP_ID: 'wx3d0c29a20a305f28',
    APP_SECRET: '685ef10637631ae8e3db77e000f22f9e',
    openId:'',//微信小程序用户标识符
    sendToDoctortmpId:'88nsGfDmdMA314-IC3nC2ILmfSX_TgW2GarmChOIOFc',
    // sendToDoctortmpId:'zCX36fb1ceNmJY__fb6g_8F-Bu0ApylFvz4aMLILfjE',
    
  },

//从服务端获取医生信息
  getDoctorList(){
    var that = this;

    wx.request({
      url: this.globalData.serverUrl + '/doctor/getAllDoctor',
      method: 'GET',
      success(res) {
        that.globalData.doctorList = res.data.data
        console.log('成功获取医生信息：', that.globalData.doctorList);
      },
      fail(res) {
        console.log('获取医生信息失败！')
      }
    })
  },

  //根据 医生的id 查询 doctorList 返回医生具体信息
  findDoctorById(n){
    console.log('app doctorList:',this.globalData.doctorList)

    for (var i = 0; i < this.globalData.doctorList.length; i++)
      if (this.globalData.doctorList[i].id == n) {
        console.log('app.js :', this.globalData.doctorList[i])
        return this.globalData.doctorList[i];
      }
  },

  // js 格式化 date 对象，输出格式为 yyyy-MM-dd HH:mm:ss 字符串
  jsDateFormatter(dateInput) {  // dateInput 是一个 js 的 Date 对象
    var year = dateInput.getFullYear();
    var month = dateInput.getMonth() + 1;
    var theDate = dateInput.getDate();

    var hour = dateInput.getHours();
    var minute = dateInput.getMinutes();
    var second = dateInput.getSeconds();

    if(month < 10) {
      month = '0' + month;
    }

        if(theDate < 10) {
      theDate = '0' + theDate;
    }

        if(hour < 10) {
      hour = '0' + hour;
    }

        if(minute < 10) {
      minute = '0' + minute;
    }

        if(second < 10) {
      second = '0' + second;
    }

        return year + "-" + month + "-" + theDate + " " + hour + ":" + minute + ":" + second;
  },
 

  //判断是否存在 token  若有则自动登录
  tokenSign(){
    var that = this;
    //分别获取 用户的token  和 医生的token
    var doctorToken = wx.getStorageSync('doctorToken');
    var doctorTokenDate = wx.getStorageSync('doctorTokenDate');
    var userToken = wx.getStorageSync('userToken');
    var userTokenDate = wx.getStorageSync('userTokenDate');

    //当前时间
    var now = (new Date()).getTime();
    console.log(doctorToken + ' ' + doctorTokenDate + ' ' + userToken + ' ' + userTokenDate + ' ' + now);

    if(doctorToken != '' && doctorToken != null){
      console.log('doctorToken 不为空！');
      //判断token 是否 过期
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
            //设置 返回 的用户数据
            that.globalData.object = 'doctor';
            that.globalData.userInfor = res.data.data;
            console.log('doctorToken 登录成功', that.globalData.userInfor);
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
              //设置 返回 的用户数据
              that.globalData.object = 'user';
              that.globalData.userInfor = res.data.data;
              console.log('userToken 登录成功', that.globalData.userInfor);
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
  },
  //获取  微信小程序 中 用户的唯一标识符
    getOpenId(){
      var code = '';
      var that = this;
      wx.login({
        success(res) {
          console.log('参数：', res)
          code = res.code,
            wx.request({
              url: 'https://api.weixin.qq.com/sns/jscode2session',
              method: 'GET',
              data: {
                appid: that.globalData.APP_ID,
                secret: that.globalData.APP_SECRET,
                grant_type: 'authorization_code',
                js_code: code
              },
              success(res) {
                console.log('success:', res)
                that.globalData.openId=res.data.openid
                console.log('app global openid:',that.globalData.openId)
              },
              fail(res) {
                console.log('fail:', res)

              }
            })

        }
      })
      
    },

})