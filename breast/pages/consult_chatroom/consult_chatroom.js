const app = getApp();
Page({
  // 初始页面数据
  data: {
    doctorId:'', //医生的标识符
    oid:'',
    userId:'',
    oid:'', //咨询订单的标识符
    scrollTop: 0,
    consultOrder:{},
    InputBottom: 0,
    list: [],
    doctorImg:'', //医生的头像
  },
  // 监听页面加载
  onLoad: function(options) {
    // console.log(options)
    this.setData({
      oid:'0457878947894500acf3dbfc35d6fd04',
      doctorId:options.doctorId,
      userId: app.globalData.userInfor.userId,
      oid:options.oid,
      doctorImg:options.doctorImg
    })
    wx.showToast({
      title: '连接中',
      icon: 'loading'
    })
    wx.connectSocket({
      // 本地服务器地址
      url: 'ws://localhost:8887/websocket/' + app.globalData.userInfor.userId + '/' + this.data.doctorId,
    })
    // 连接成功
    wx.onSocketOpen(function() {
      console.log('连接成功');
    })
    
    wx.onSocketMessage(msg => {
      console.log('收到消息为:',msg)
      //将 从 服务器 得到 的 消息数据 进行 解析 为 JSON 数组
      var data = JSON.parse(msg.data);
      console.log('转化后的消息为:', data)

//判断是否是 发送完消息后 服务器的 反馈 消息
  if(data.status != undefined)
      if(data.status == 1)
          return;
        else
        {
          console.log(data.msg)
        wx.showModal({
          title: '温馨提示',
          content: "系统错误！请稍后再发送！",
          showCancel: false
        })
          return;
        }

      var newList = this.data.list;
      //将消息加入表中
      for(var i = 0 ; i < data.length ; i++)
          newList.push(data[i]);

        this.setData({
          list:newList
        });
      
      console.log('设置后的list：',this.data.list)

      this.rollingBottom()
    })
     
  },
  // 发送内容
  count: 0,
  massage: '',
  //点击发送 消息 按钮
  send: function() {
    
    // 判断发送内容是否为空
    if (this.message) {
      var msg = {
        fromUserId:this.data.userId,
        toUserId:this.data.doctorId,
        messageType:0,                 // 消息类型  文字 图片
        messageContent:this.message,
        time: app.jsDateFormatter(new Date()),   //时间
        oid:this.data.oid  //咨询 所属 的 咨询订单
      }
      //将 msg 对象 转化为 JSON 字符串
      var msgStr = JSON.stringify(msg);
      console.log(msgStr)
      wx.sendSocketMessage({
        data: msgStr,
      })
      // 将自己的 消息 放入 聊天列表
      console.log(this.data.list)
      var newList = this.data.list;
      newList.push(msg)

      this.setData({
        list: newList
      });
      console.log(this.data.list)
      this.rollingBottom()
    } else {
      // 弹出提示框
      wx.showToast({
        title: '消息不能为空哦~',
        icon: 'none',
        duration: 2000
      })
    }
  },
  // 监听input值的改变
  bindChange(res) {
    this.message = res.detail.value
  },
  // 页面卸载，关闭连接
  onUnload() {
    wx.closeSocket();
    wx.onSocketClose(function(res){
      wx.showToast({
        title: '连接已断开~',
        icon: 'none',
        duration: 2000
      })
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