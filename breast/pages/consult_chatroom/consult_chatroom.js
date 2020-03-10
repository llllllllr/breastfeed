const app = getApp();
Page({
  // 初始页面数据
  data: {
    doctorId:'', //医生的标识符
    oid:'',
    userId:'',
    scrollTop: 0,
    consultOrder:{},
    InputBottom: 0
  },
  // 监听页面加载
  onLoad: function(options) {
    // console.log(options)
    this.setData({
      oid:'0457878947894500acf3dbfc35d6fd04'
    })
     
  },
  
  onReady:function(){
     this.getConsultOrder()
  },
  getConsultOrder:function(){
    var that = this;
     wx.request({
       url: getApp().globalData.serverUrl +'/getByOid',
       data:{
         oid:this.data.oid
       },
       success:function(res){
         console.log(res)
         that.setData({
          consultOrder:res.data.data
         })
       }
     })
  }
})