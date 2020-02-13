
//富文本解析器
var WxParse = require('../../wxParse/wxParse.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    currentArticleId: 0,
    article: {}
  },

  /**
   * 生命周期函数--监听页面加载
   */
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

  }

})