//cookie
import cookie from '../../assert/js/cookie.js';
//富文本解析器
import { wxParse } from '../../wxParse/wxParse.js';
Page({
  data: {
    currentArticleId: 0,
    article: {},
    coll: -1,
    userid: 0,
    articleID: -1,
  },
  onLoad: function (options) {
    var that = this;
    var articleId = options.id;
    this.setData({
      currentArticleId: articleId
    })
    //获取单篇文章数据
    wx.request({
      url: getApp().globalData.serverUrl+'/article/getone',
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
        wxParse('content', 'html', content, that, 25)
        //console.log(that.data.article)
      }
    })
    //本来顺序写，但其实wx.request还没有获取到信息，此时看到的
    //article还是空的。注意异步
    //console.log(that.data.article)
    this.getUserId();
  },


  //收藏功能，后端接口未测试，等待和“我的”联调
  coll: function () {
    if(this.data.userid <= 0)
    {
      wx.showToast({
        title: '请先登录！',
        icon:'none'
      })
      return;
    }
    
    var that = this;
    var preColl = this.data.coll;
    var newColl = (-1) * preColl;
    if (newColl == 1) {
      wx.request({
        url: getApp().globalData.serverUrl + '/coll/addcoll',
        method: 'GET',
        data: {
          userId: 456,
          collId: that.data.currentArticleId,
          type: 1
        },
        success: function (res) {
          if (res.data.status == 1) {
            wx.showToast({
              title: '收藏成功！',
              icon: 'success'
            })
          }
        }
      })
    }
    else {
      wx.request({
        url: getApp().globalData.serverUrl + '/coll/delcoll',
        data: {
          userId: this.data.userid,
          collId: this.data.currentArticleId,
        },
        success: function (res) {
          console.log(res)
          wx.showToast({
            title: '已取消收藏！',
            icon: 'none'
          })
        }
      })

    }
    this.setData({
      coll: newColl
    })
  },

  //初始化收藏
  ifColl: function () {
    var that = this;
    var serverUrl = getApp().globalData.serverUrl;
    wx.request({
      url: serverUrl + '/coll/ifcoll',
      data:
      {
        userId: this.data.userid,
        collId: this.data.currentArticleId,
        type: 1
      },
      success: function (res) {
        console.log(res)
        if (res.data.status == 1) {
          that.setData({
            coll: 1
          })
        }
        else {
          that.setData({
            coll: -1
          })
        }
      }
    })

  },
  //检验token,获取用户id
  getUserId: function () {
    var that = this;
    var serverUrl = getApp().globalData.serverUrl;
    console.log(serverUrl)
    var cookie = wx.getStorageSync('userToken');
    console.log(cookie)
    if (cookie) {
      wx.request({
        header: {
          cookie: cookie
        },
        url: serverUrl + '/user/check',
        method: 'GET',
        success: function (res) {
          console.log(res)
          //认证成功，得到userId
          if (res.data.status == 1) {
            that.setData({
              userid: res.data.data
            })
            that.ifColl();
          }
          else {
            wx.showToast({
              title: '请先登录',
            })
          }
        }
      })
    }
  }

})