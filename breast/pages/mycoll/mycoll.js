// pages/mycoll/mycoll.js
Page({

  data: {
    userId:0,
    artileList:[],
    showNone:0
  },

  onLoad: function (options) {
    console.log(options.userid)
    this.setData({
      userId:options.userid
    })
   this.getColls();
  },
   //得到收藏列表
   getColls:function(){
     var that = this;
     wx.request({
       url: getApp().globalData.serverUrl + '/coll/getcolls',
       data:{
         userId:this.data.userId,
         type:1
       },
       success:function(res){
           console.log(res)
           that.setData({
             artileList:res.data.data,
           })
           if(res.data.data.length <= 0 || !res.data.data)
           that.setData({
             showNone:1
           })
       }
         
     })
   }
   
})