var WxSearch = require('../../wxSearchView/wxSearchView.js');

const app = getApp()
Page({
  data: {
    hot:[],
  },
 
  onLoad: function () {
    // 2 搜索栏初始化
    var that = this;

    //从客户端查询热搜词
    var serverurl = app.globalData.serverUrl;
    wx.request({
      url: serverurl+'/article/getHotWords',
      method:'GET',
      header:{  
        'content-type':'application/json'
     },
      success:function(res){
        console.log(res.data.data)
         var hotwords = res.data.data;
         that.setData({
           hot:hotwords
         })
         WxSearch.init(
          that,  // 本页面一个引用
          hotwords,
          hotwords,// 搜索匹配，[]表示不使用
          that.mySearchFunction, // 提供一个搜索回调函数
          that.myGobackFunction //提供一个返回回调函数
        );
      }
    })
   
    },
    wxSearchInput: WxSearch.wxSearchInput,  // 输入变化时的操作
    wxSearchKeyTap: WxSearch.wxSearchKeyTap,  // 点击提示或者关键字、历史记录时的操作
    wxSearchDeleteAll: WxSearch.wxSearchDeleteAll,// 删除所有的历史记录
    wxSearchConfirm: WxSearch.wxSearchConfirm,  // 搜索函数
    wxSearchClear: WxSearch.wxSearchClear,  // 清空函数
  
    // 4 搜索回调函数  
    mySearchFunction: function(value) {
      console.log(value)
      wx.redirectTo({
        url: '../searchResult/searchResult?word='+value
      })
    },
  
    // 5 返回回调函数
    myGobackFunction: function () {
      // do your job here
      // 示例：返回
      wx.redirectTo({
        url: '../index/index?searchValue=返回'  
      })
    }
  
  })





