// pages/doctors_choose/index.js
var showUtil = require('../../assert/util.js')
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
    if(app.globalData.userInfor.userId == null)
         showUtil.showToLogion();
    this.fetchDoctorData();
  },

  fetchDoctorData: function () {  //获取医生列表
    let _this = this;
    wx.showToast({
      title: '加载中',
      icon: 'loading'
    })

    var newList = app.globalData.doctorList;
    //查询是否已经加载过医生的信息
    if(newList == null || newList.length == 0){
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
    }else{
      this.setData({
        //已经加载过，直接赋值
        doctorList:newList
      })
    }

  },

  to_consultque(e) {
    var index = e.currentTarget.dataset.index;
    var item = this.data.doctorList[index];
    //获取参数
    var doctorId = item.id;
    var doctorName = item.name;
    var doctorImg = item.imgUrl;
    var openId = item.openId;
    wx.navigateTo({
      url: '../consult_questionnaire/consult_questionnaire?doctorId=' + doctorId + '&doctorName=' + doctorName + 
      '&doctorImg=' + doctorImg + '&openId=' + openId,
    })
  },


//ornNL5BEStOvVApFPROGzx76jfas
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
