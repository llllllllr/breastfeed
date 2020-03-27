import * as echarts from '../../ec-canvas/echarts';
var who = require('../../utils/who.js')
var baseLine = require('../../utils/data.js')
var chart = null;
function initChart(canvas, width, height) {
  chart = echarts.init(canvas, null, {
    width: width,
    height: height,
  });
  canvas.setChart(chart);

  // var option = who.setOption({ tite: "宝宝", sex: 1, babyname: "zx" }, baseLine.g_weight, {}, "weight")
  // chart.setOption(option);
  return chart;
}
Page({

  data: {
    option: null,
    ec: {
      onInit: initChart
    },
    monthDiff: 0,
    type: "weight",
    gender: '',
    baseData: null
  },

  onLoad: function (options) {
    var that = this;
    console.log(options.monthDiff);
    this.setData({
      monthDiff: options.monthDiff,
      gender: options.gender
    })
    wx.showLoading({
      title: "正在加载中。。",
      icon: 'loading',
      duration: 6000,
      })
    this.createCurve(this.data.type);
    setTimeout(() => {
      chart.setOption(that.data.option)
    }, 1000)
  },

 

  createCurve: function (type) {
    var baseData;
    if (this.data.gender == "1") {
      if (type == 'weight')
        baseData = baseLine.b_weight;
      //todo
      else if (type == 'height')
        baseData = baseLine.g_height;
    } else {
      if (type == 'weight')
        baseData = baseLine.g_weight;
      //todo
      else if (type == 'height')
        baseData = baseLine.g_height;
    }
    var opt = who.setOption({ tite: "宝宝", sex: 1, babyname: "zx" },
      baseData, {}, type, this.data.monthDiff);
    this.setData({
      option: opt
    })
  },
  resetType: function (canvas, width, height) {
    var that = this;
    var newType = this.data.type == 'weight' ? 'height' : 'weight';
    console.log(newType)
    this.setData({
      type: newType
    })
    this.createCurve(newType);
      chart.setOption(that.data.option, true)
    wx.showLoading({
      title: "正在加载中...",
      icon: 'loading',
      duration: 1000,
      })
    }
})