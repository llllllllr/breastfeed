

var app = getApp();
Page({

  data: {
     word:'',
     artileList:[],
     showNone:1
  },

  onLoad: function (options) {
     console.log(options)
     this.setData({
       word:options.word
     })
    this.search();
     
  },
  

  //从服务器获取搜索结果
  search:function(){
    var that = this;
    var serverUrl = app.globalData.serverUrl;
    wx.request({
      url: serverUrl +  '/article/search',
      method:'GET',
      header:{  
        'content-type':'application/json'
     },
      data:{
        content:this.data.word
      },
      success:function(res){
        if(res.data.data.lenth == 0)
           {
             that.setData({
               showNone:0
             })
           }
        that.setData({
          artileList:res.data.data
        })
      }
    })
  },

  //跳转到文章详情
  chageToArticle:function(event){
      var idx = event.currentTarget.dataset.index;
      console.log(idx)
      var id = this.data.artileList[idx].id;
      wx.navigateTo({
        url: '../article_detail/article_detail?id=' + id,
      })
  }

 
})