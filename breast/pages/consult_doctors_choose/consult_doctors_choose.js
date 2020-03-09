// pages/doctors_choose/index.js
const app = getApp();
Page({
  data: {
    showfilter: false, //是否显示下拉筛选
    doctorList: [], //医生列表
    scrolltop: null, //滚动位置
    page: 0,  //分页
    openId: '',
  },
  onLoad: function () { //加载数据渲染页面
    this.openid()
    this.fetchDoctorData();
  },

  fetchDoctorData: function () {  //获取医生列表
    let _this = this;
    wx.showToast({
      title: '加载中',
      icon: 'loading'
    })

    const newlist = [];
    wx.request({
      url: app.globalData.serverUrl + '/doctor/getAllDoctor',
      method: 'GET',
      success(res) {
        _this.setData({
          doctorList: res.data.data
        })
        console.log('成功获取医生信息：', _this.data.doctorList);
        app.globalData.doctorList = _this.data.doctorList;
        console.log('设置全局医生信息:', app.globalData.doctorList);
      },
      fail(res) {
        console.log('获取医生信息失败！')
      }
    })

  },

  to_consultque(e) {
    console.log("参数", e)
    //获取参数
    var doctorId = e.currentTarget.dataset.id;
    var doctorName = e.currentTarget.dataset.name;
    var doctorImg = e.currentTarget.dataset.imgurl;
    wx.navigateTo({
      url: '../consult_questionnaire/consult_questionnaire?doctorId=' + doctorId + '&doctorName=' + doctorName +
        '&doctorImg=' + doctorImg,
    })
  },

  openid: function () {
    var that = this;
    wx.login({
      success: function (res) {
        if (res.code) {
          //获取openId
          wx.request({
            url: 'https://api.weixin.qq.com/sns/jscode2session',
            data: {
              //小程序唯一标识
              appid: 'wxcb08e1f414685d6c',
              //小程序的 app secret
              secret: '889c82d0d898a3d83f176a5e9b44d697',
              grant_type: 'authorization_code',
              js_code: res.code
            },
            method: 'GET',
            header: { 'content-type': 'application/json' },
            success: function (openIdRes) {
              console.info("登录成功返回的openId：" + openIdRes.data.openid);
              that.setData({
                openId: openIdRes.data.openid
              })
             // that.send()
              // 判断openId是否获取成功
              if (openIdRes.data.openid != null & openIdRes.data.openid != undefined) {
                // 有一点需要注意 询问用户 是否授权 那提示 是这API发出的
                wx.getUserInfo({
                  success: function (data) {
                    // 自定义操作
                    // 绑定数据，渲染页面
                    that.setData({

                    });
                  },
                  fail: function (failData) {
                    console.info("用户拒绝授权");
                  }
                });
              } else {
                console.info("获取用户openId失败");
              }
            },
            fail: function (error) {
              console.info("获取用户openId失败");
              console.info(error);
            }
          })
        }
      }
    });
  },
 

})
  // scrollHandle: function (e) { //滚动事件
  //   this.setData({
  //     scrolltop: e.detail.scrollTop
  //   })
  // },
  // goToTop: function () { //回到顶部
  //   this.setData({
  //     scrolltop: 0
  //   })
  // },
  // scrollLoading: function () { //滚动加载
  //   this.fetchDoctorData();
  // },
  // onPullDownRefresh: function () { //下拉刷新
  //   this.setData({
  //     page: 0,
  //     doctorlist: []
  //   })
  //   this.fetchDoctorData();
  //   setTimeout(() => {
  //     wx.stopPullDownRefresh()
  //   }, 1000)
  // }
// })