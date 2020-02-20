
//富文本解析器
var WxParse = require('../../wxParse/wxParse.js');
Page({
  data: {
    currentArticleId: 0,
    article: {},
    coll:-1,
  },
  onLoad: function (options) {
    var that = this;
    var articleId = options.id;
    this.setData({
      currentArticleId: articleId
    })
    //获取单篇文章数据
    wx.request({
      url: 'http://localhost:8887/article/getone',
      method: 'get',
      data: {
        id: articleId
      },
      success: function (res) {
        //console.log(res.data.data)
        that.setData({
          article: res.data.data
        })
        //异步加载问题！！
        var content = that.data.article.content;
        console.log(content)
        WxParse.wxParse('content', 'html', content, that, 25)
        //console.log(that.data.article)
      }
    })
    //本来顺序写，但其实wx.request还没有获取到信息，此时看到的
    //article还是空的。注意异步
    //console.log(that.data.article)

  },

  //收藏功能，后端接口未测试，等待和“我的”联调
  coll:function(){
    var preColl = this.data.coll;
    var newColl = (-1) * preColl;
    if(newColl ==  1)
    {
      wx.showToast({
        title: '收藏成功！',
        icon:'success'
      })
    }
    else{
      wx.showToast({
        title: '已取消收藏！',
        icon:'none'
      })
    }
    this.setData({
      coll:newColl
    })
  }
})