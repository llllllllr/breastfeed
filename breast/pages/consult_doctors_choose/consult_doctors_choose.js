// pages/doctors_choose/index.js
const app = getApp();
Page({
  data: {
    showfilter: false, //是否显示下拉筛选
    doctorList: [], //医生列表
    scrolltop: null, //滚动位置
    page: 0  //分页
  },
  onLoad: function () { //加载数据渲染页面
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
      url:app.globalData.serverUrl + '/doctor/getAllDoctor',
      method:'GET',
      success(res){
        _this.setData({
          doctorList:res.data.data
        })
        console.log('成功获取医生信息：', _this.data.doctorList);
        app.globalData.doctorList = _this.data.doctorList;
        console.log('设置全局医生信息:', app.globalData.doctorList);
      },
      fail(res){
        console.log('获取医生信息失败！')
      }
    })

  },


  scrollHandle: function (e) { //滚动事件
    this.setData({
      scrolltop: e.detail.scrollTop
    })
  },
  goToTop: function () { //回到顶部
    this.setData({
      scrolltop: 0
    })
  },
  scrollLoading: function () { //滚动加载
    this.fetchDoctorData();
  },
  onPullDownRefresh: function () { //下拉刷新
    this.setData({
      page: 0,
      doctorlist: []
    })
    this.fetchDoctorData();
    setTimeout(() => {
      wx.stopPullDownRefresh()
    }, 1000)
  }
})