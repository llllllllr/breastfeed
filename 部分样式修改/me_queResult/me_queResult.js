// page/jieguo/jieguo.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    n:1,
    type1:"自我效能量表",
    type2:"情绪调查量表",
    con_height: "1250rpx",
    count_zw: 3,     //统计自我效能量表显示个数
    count_qx: 3,    //统计情绪调查量显示个数          用于决定container的height是100%还是固定rpx
    count_all:6,    //统计所有显示个数
    jieguo: [
      {
        "id": "1",
        "type": "自我效能量表",
        "score": "20",
        "advice": "你很棒噢！",
        "time": "2020/03/01"
      },
      {
        "id": "2",
        "type": "自我效能量表",
        "score": "22",
        "advice": "你很棒棒噢！",
        "time": "2020/03/04"
      },
      {
        "id": "3",
        "type": "自我效能量表",
        "score": "20",
        "advice": "你真棒！",
        "time": "2020/03/06"
      },
 
      {
        "id": "4",
        "type": "情绪调查量表",
        "score": "16",
        "advice": "欧力给！",
        "time": "2020/02/08"
      },
      {
        "id": "5",
        "type": "情绪调查量表",
        "score": "18",
        "advice": "噶油！",
        "time": "2020/01/16"
      },
      {
        "id": "6",
        "type": "情绪调查量表",
        "score": "20",
        "advice": "你真棒！",
        "time": "2020/02/25"
      },
    ]
  },

  onLoad: function (options) {
    this.setData({
      bg_color1: "#000000",
      n1_color: "#FFFFFF",

      bg_color2: "#FFFFFF",
      n2_color: "#000000",

      bg_color3: "#FFFFFF",
      n3_color: "#000000"
    })
    if (this.data.count_all >= 7 || this.data.id == 3) this.setData({
      con_height: "100%"
    })

  },
 
question:function(e){
  var id=0;
  var idx=e.currentTarget.dataset.id-1;//下标
  // wxml中表的顺序也是根据data中表内容的顺序来显示的，所以js中的可以与wxml的数据对应
  // 从而获得此刻框中的问卷表名称
  var string = this.data.jieguo[idx].type;
  console.log(string);
  // 判断string中是否有此字符串
  if (string.indexOf("自我") >= 0) {
   id=1;
  }
  if (string.indexOf("情绪") >= 0) {
    id = 2;
  }

  console.log("id_____"+id);
  wx.navigateTo({
    url: '../me_questionaire/me_questionaire?questionId=' + id+'&ansID='+idx,     //ansID为问卷的ID，到时根据idx来找问卷的答案    
  })
},
  tap_n1: function () {
    if (this.data.count_all >= 7) this.setData({
      con_height: "100%"
    })
    else this.setData({
      con_height: "1350rpx"
    })
    this.setData({
      n: 1,
      bg_color1: "#000000",
      n1_color: "#FFFFFF",

      bg_color2: "#FFFFFF",
      n2_color: "#000000",

      bg_color3: "#FFFFFF",
      n3_color: "#000000"
    })

  },

  tap_n2: function () {
    if (this.data.count_zw >= 7) this.setData({
      con_height: "100%"
    })
    else this.setData({
      con_height: "1350rpx"
    })
    this.setData({
      n: 2,
      bg_color2: "#000000",
      n2_color: "#FFFFFF",

      bg_color1: "#FFFFFF",
      n1_color: "#000000",

      bg_color3: "#FFFFFF",
      n3_color: "#000000"
    })
  },

  tap_n3: function () {
    if (this.data.count_qx >= 7) this.setData({
      con_height: "100%"
    })
    else this.setData({
      con_height: "1350rpx"
    })
    this.setData({
      n: 3,
      bg_color3: "#000000",
      n3_color: "#FFFFFF",

      bg_color1: "#FFFFFF",
      n1_color: "#000000",

      bg_color2: "#FFFFFF",
      n2_color: "#000000"
    })
  },
})