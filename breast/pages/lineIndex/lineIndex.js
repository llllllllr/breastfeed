var util = require('../../utils/util.js')
Page({

  /**
   * 页面的初始数据
   */
  data: {
    startDate:'2015-03-01',
    endDate:'2020-03-01',
    date: '2018-12-25',
  },

  //身高 42-55 43-56
  //体重 2-5 2-5.2
  onLoad: function (options) {
      this.setDate();      
  },

  formSubmit(e){
       console.log(e.detail.value)
      var fromValue = e.detail.value; 
      var height = parseFloat(fromValue.height);
      var weight = parseFloat(fromValue.weight);
      // console.log(weight)
      if(fromValue.radioG == "") {
        util.showMsg("请选择宝宝性别");
        return;
      }else{
             if(isNaN(weight) ||weight <2.0 || weight > 5.5){
               util.showMsg("正常宝宝出生时体重\n应该在2.0-5.5千克之间，请输入合理数据");
               return;
             }
             if(isNaN(height)  ||height <42 || weight > 57){
              util.showMsg("正常宝宝出生时身长\n应该42-56厘米之间，请输入合理数据");
              return;
             }
      }

     //跳转到生长曲线页面 求月份差
    var monthDiff = util.monthDiff(this.data.date,this.data.endDate)
    wx.navigateTo({
      url: '../lineDetail/lineDetail?monthDiff='+monthDiff + '&gender=' + fromValue.radioG,
    })
  },
  //设置日期
  setDate(){
      var nowDate = util.formarNowDate();
      var startD = nowDate.split("-");
      var beforeY = startD[0]-5;
      var resDate = beforeY + "-" + startD[1] + "-" + startD[2];
      this.setData({
        date:nowDate,
        startDate:resDate,
        endDate:nowDate
      })
  },

  DateChange(e) {
    this.setData({
      date: e.detail.value
    })
  },
})