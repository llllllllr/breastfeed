

// var util = require('../../utils/doctors.js');
const app = getApp();
Page({
  data: {
    doctordetail: null,
    doctorId:''
  },
  onLoad: function (options) {
    this.setData({
      // doctordetail: {
      //   "id": i + 1,
      //   "state": util.doctors[i].state,
      //   "name": util.doctors[i].name,
      //   "city": util.doctors[i].other,
      //   "introduce": util.doctors[i].introduce,
      //   "tag": util.doctors[i].department,
      //   "position": util.doctors[i].position,
      //   "hospital": util.doctors[i].hospital,
      //   "imgurl": "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1582303282122&di=9c16774ef40258e3a5b5576fdfb3f77f&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F7274e8be5b2c7f7ac56ecb34c2e45181c167ecb6d44a-feXDfj_fw658"
      // }
      doctorId:options.id,
      doctordetail: app.findDoctorById(options.id),
    }),
    console.log('医生的信息:',this.data.doctordetail),
    console.log('记录医生的标识符：',this.data.doctorId),
    wx.setNavigationBarTitle({
      title: this.data.doctordetail.name
    })
  },
  onchange: function () {
    console.log('用户点击立即咨询')
    wx.navigateTo({
      //url: '../consult_chatroom/index?id={{doctordetail.id}}',//跳转的路径
      url: '../consult_questionnaire/consult_questionnaire?id=' + this.data.doctordetail.id,
    })
  },

})
