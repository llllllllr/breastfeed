

Page({
  data: {
    videoId: -1,
    video:{},
    course_series: "8个关键发育点，养出漂亮宝宝"
  },
  onLoad: function (opstions) {
    var vId = opstions.id;
    this.setData({
      videoId: vId
    })
    this.getVideo(vId)
  },
  getVideo:function(vId){
     console.log(vId)
      var that = this;
      wx.request({
        url: 'http://localhost:8887/vedio/getone',
        method:'GET',
        data:{
          id:vId
        },
        success:function(res){
          that.setData({
            video:res.data.data
          })
          
          console.log(11111111111111111111111111111111111)
               console.log(res)
        }
      })
  }

})