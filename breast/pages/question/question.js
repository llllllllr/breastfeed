
var app = getApp();
Page({


  data: {
    tid:0,
    currentIndex: 0,
    title: "",
    type: 0,
    options: [],
    resData: []
  },
  onLoad: function (options) {
    var id = options.id;
    this.getQuestion(id)
    this.setData({
      tid:id
    })
  },

  //获取问卷题目
  getQuestion: function (id) {
    var that = this;
    var serverUrl = app.globalData.serverUrl;
    wx.request({
      url: serverUrl + '/question/getQustions',
      method: 'GET',
      data: {
        tid: id
      },
      success: function (res) {
        that.setData({
          resData: res.data.data,
          currentIndex: 0
        })
        that.initData();
      }
    })
  },

  initData: function () {
    //返回题目数组
    var questionList = this.data.resData;
    //index--第几题
    var index = this.data.currentIndex;
    console.log(index)
    //选项
    var optionList = new Array();
    optionList = questionList[index].qOpstions.split('|');
    //选项处理

    for (var i = 0; i < optionList.length; i++) {
      var opStr = "options[" + i + "].option";
      var selectStr = "options[" + i + "].selected";
      this.setData({
        [opStr]: optionList[i],
        [selectStr]: -1
      })
    }
    this.setData({
      title: questionList[index].qContent,
      type: questionList[index].qType,
    })

    console.log(this.data.options[3].option)
  },
  selectOp: function (event) {
    console.log(event)
    var idx = event.currentTarget.dataset.index;
    var before_select = this.data.options[idx].selected;
    var str = "options[" + idx + "].selected";
    this.setData({
      [str]: before_select * (-1)
    })
  },
  nextOp: function () {
    var that = this;
    var serverUrl = app.globalData.serverUrl;
    //如果已经是最后一题
    if (this.data.currentIndex == this.data.resData.length - 1) {
        wx.showToast({
        title: '积分+10!',
      })

     wx.navigateTo({
       url: '../testIndex/testIndex',
     })
    } 
    else{    
      var options = this.data.options;
      var str="";
      for(var i=0;i<options.length;i++)
      {
         if(options[i].selected == 1)
              str  += options[i].option + "|";
      }
      //存到数据库
      wx.request({
        url: serverUrl+ '/user/insertAnswers',
        method:"GET",
        data:{
          userid:2,
          tid:this.data.tid,
          answers:str
        },
        success:function(res){
           console.log(res)
        }
  
      })

      var oldIdx = this.data.currentIndex;
       this.setData({
         currentIndex:oldIdx+1
       })
    }
    this.initData();
  },
})

