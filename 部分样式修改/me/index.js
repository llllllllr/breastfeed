// page/index/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    // user_name: "hbnb",
    user_id: "1806500164",
    // user_photo: "/image/5.jpg",
    id_1: 1,         //自我能量表的个数
    id_2: 1   ,       //情绪调查表的个数
    question1:'自我效能量表',
    question2:'情绪调查量表'
  },


  question:function(e){
    var id = e.currentTarget.dataset.id;
    console.log(id);
    wx.navigateTo({
      url: '../me_questionaire/me_questionaire?questionId='+id,
    })
  },
 
  tap_xinxi: function () {
    wx.navigateTo({
      url: '../me_information/me_information',
    })
  },

  tap_shoucang: function () {
    wx.navigateTo({
      url: '../me_collect/me_collect',
    })
  },

  tap_zixun: function () {
    wx.navigateTo({
      url: '../me_consult/me_consult',
    })
  },

  tap_jieguo: function () {
    wx.navigateTo({
      url: '../me_queResult/me_queResult',
    })
  },

  tap_quxian: function () {
    wx.navigateTo({
      url: '../me_graph/me_graph',
    })
  }
})