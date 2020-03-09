const app = getApp();
Page({
  // 初始页面数据
  data: {
    doctorId:'', //医生的标识符
    userId:'',
    oid:'', //咨询订单的标识符
    scrollTop: 0,
    list: []
  },
  // 监听页面加载
  onLoad: function(options) {
    console.log('参数：',options)
    this.setData({
      doctorId:options.doctorId,
      userId: app.globalData.userInfor.userId,
      oid:options.oid
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
      var data = JSON.parse(msg.data);
      console.log('转化后的消息为:', data)
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
  send: function() {
    
    // 判断发送内容是否为空
    if (this.message) {
      var msg = {
        fromUserId:this.data.userId,
        toUserId:this.data.doctorId,
        messageType:0,
        messageContent:this.message,
        time: app.jsDateFormatter(new Date()),
        oid:'6742d3461985435b85fa18482d534224'
      }
      var msgStr = JSON.stringify(msg);
      console.log(msgStr)
      wx.sendSocketMessage({
        data: msgStr,
      })
      // 我自己的消息
      console.log(this.data.list)
      var list = this.data.list
      list.push({
        id: this.count++,
        content: this.message,
        role: 'me'
      })
      this.setData({
        list: list
      })
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
  // 聊天内容始终显示在最低端
  rollingBottom(e) {
    wx.createSelectorQuery().selectAll('.list').boundingClientRect(rects => {
      rects.forEach(rect => {
        this.setData({
          scrollTop: rect.bottom
        })
      })
    }).exec()
  }
})