const app = getApp();
Page({
  // 初始页面数据
  data: {
    doctorId: '', //医生的标识符
    userId: '',
    oid: '', //咨询订单的标识符
    scrollTop: 0,
    consultOrder:{},
    InputBottom: 0,
    list: [],
    doctorImg: '', //医生的头像
    object:'',
    message:"", //消息内容
    userHeadPictureUrl: 'http://llllllllr.top/doctorRegister_1585797161.jpg',  //医生端 用户备用头像
  },
  // 监听页面加载
  onLoad: function (options) {
    console.log('参数：', options)
    var doctor = app.findDoctorById(options.doctorId);
    this.setData({
      object:app.globalData.object,
      doctorId: options.doctorId,
      userId: options.userId,
      oid: options.oid,
      doctorImg: doctor.imgUrl,      // ******  无法获取医生的头像地址 ， 可能是 请求需要时间 有延迟
    })
    wx.showToast({
      title: '连接中',
      icon: 'loading'
    })

    //判断是 普通用户 还是 医生 在线
    if(this.data.object == 'user')
      wx.connectSocket({
        // 本地服务器地址
        url: 'ws://localhost:8887/websocket/' + this.data.userId + '/' + this.data.doctorId + '/' + this.data.oid,
      })
      else
      wx.connectSocket({
        // 本地服务器地址
        url: 'ws://localhost:8887/websocket/' + this.data.doctorId + '/' + this.data.userId + '/' + this.data.oid,
      })


    // 连接成功
    wx.onSocketOpen(function () {
      console.log('连接成功');
    })

    wx.onSocketMessage(msg => {
      console.log('收到消息为:', msg)
      //将 从 服务器 得到 的 消息数据 进行 解析 为 JSON 数组
      var data = JSON.parse(msg.data);
      console.log('转化后的消息为:', data)

      //判断是否是 发送完消息后 服务器的 反馈 消息
      if (data.status != undefined)
        if (data.status == 1)
          return;
        else {
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
      for (var i = 0; i < data.length; i++){
        //格式化 日期 的格式
      var time = data[i].time;
      data[i].time = app.jsDateFormatter(new Date(time))
      newList.push(data[i]);
    }
      this.setData({
        list: newList
      });

      console.log('设置后的list：', this.data.list)

      this.rollingBottom()
    })
     
  },

//设置 消息内容
  handleMessage(e){
    console.log('消息内容:',e.detail.value)
    this.setData({
      message: e.detail.value
    })
  },

//设置 消息内容
  clearMessage(){
    this.setData({
      message:""
    })
  },

  // 发送内容
  //点击发送 消息 按钮
  send: function () {

    // 判断发送内容是否为空
    if (this.data.message.length != 0) {
      console.log('订单oid:',this.data.oid)
      //判断是 登录 用户 是 医生 还是 普通用户
      if(this.data.object == 'user')
        var msg = {
          fromUserId: this.data.userId,
          toUserId: this.data.doctorId,
          messageType: 0,                 // 消息类型  文字 图片
          messageContent: this.data.message,
          time: app.jsDateFormatter(new Date()),   //时间
          oid: this.data.oid  //咨询 所属 的 咨询订单
        }
        else
        var msg = {
          fromUserId: this.data.doctorId,
          toUserId: this.data.userId,
          messageType: 0,                 // 消息类型  文字 图片
          messageContent: this.data.message,
          time: app.jsDateFormatter(new Date()),   //时间
          oid: this.data.oid  //咨询 所属 的 咨询订单
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
      this.clearMessage();
      console.log(this.data.list)
      this.rollingBottom()
    } else {
      // 弹出提示框
      wx.showToast({
        title: '消息不能为空哦~',
        icon: 'none',
        duration: 2000
      })
      return;
    }
    //发送 消息通知
    this.sendMessageNotice()
  },

  // 页面卸载，关闭连接
  onUnload() {
    console.log('关闭连接，发送消息通知')
    
    wx.closeSocket();
    wx.onSocketClose(function (res) {
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
  },

  //发送 消息 通知 医生有用户消息
  sendMessageNotice() {
    var accessToken = '';
    var that = this;
  
    //先获取 access_token
    wx.request({
      url: 'https://api.weixin.qq.com/cgi-bin/token',
      method: 'GET',
      data: {
        grant_type: 'client_credential',
        appid: app.globalData.APP_ID,
        secret: app.globalData.APP_SECRET
      },
      success(res) {
        console.log('获取access_token成功：', res)
        accessToken = res.data.access_token

        //发送通知
        wx.request({
          url: 'https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=' + accessToken,
          method: 'POST',
          data: {
            touser: app.globalData.openId,
            template_id: app.globalData.sendToDoctortmpId,
            data: {
              date2: {
                "value": "2019年10月1日"
              },
              number6: {
                value: 666
              },
              thing4: {
                value: 444
              },
              thing9: {
                value: 999
              }
            },

          },
          success(res) {
            console.log('消息推送发送成功:', res)
          },
          fail(res) {
            console.log('消息推送发送失败:', res)
          }
        })
      }
    })

  },
  
  /*图片模块 */
  //上传图片的token
  getToken: function () {
    var that = this;
    wx.request({
      url: getApp().globalData.serverUrl + '/article/getToken',
      method: 'GET',
      data: {
        bucket: 'useravatarr'
      },
      success: function (res) {
        console.log(res)
        that.setData({
          imgToken: res.data
        })
      }
    })
  },
  //这里待修改，等提交再传云端
  ChooseImage() {
    var that = this;
    var qiniu_key = this.data.userid + "consult_" + Date.parse(new Date()) / 1000 + ".jpg";
    wx.chooseImage({
      count: 1, //默认9
      sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album'], //从相册选择

      success: function (res) {
        var filePath = res.tempFilePaths[0];
        console.log(that.data.imgToken)
        // 交给七牛上传
        var img0 = 'imgList[0]'
        QiniuUploader.upload(filePath, (res) => {
          that.setData({
            'imageURL': res.imageURL,
            [img0]: res.imageURL
          });

          console.log('file url is: ' + res.imageURL);
        }, (error) => {
          console.log('error: ' + error);
        }, {
            region: 'SCN',
            domain: 'http://q6le31s3c.bkt.clouddn.com/', // // bucket 域名，下载资源时用到。如果设置，会在 success callback 的 res 参数加上可以直接使用的 ImageURL 字段。否则需要自己拼接
            key: qiniu_key, // [非必须]自定义文件 key。如果不设置，默认为使用微信小程序 API 的临时文件名
            uptoken: that.data.imgToken, // 由其他程序生成七牛 uptoken
            uploadURL: 'https://up-z2.qiniup.com'
          }, (res) => {
            console.log('上传进度', res.progress)
            console.log('已经上传的数据长度', res.totalBytesSent)
            console.log('预期需要上传的数据总长度', res.totalBytesExpectedToSend)
            console.log("URLLL" + res.imageURL)
          }, () => {
            // 取消上传
          }, () => {
            // `before` 上传前执行的操作
          }, (err) => {
            // `complete` 上传接受后执行的操作(无论成功还是失败都执行)
            console.log("error")
          });

      }
    });
  },
  DelImg(e) {
    wx.showModal({
      title: '提示',
      content: '确定要删除吗？',
      cancelText: '取消',
      confirmText: '确定',
      confirmColor: "#d4237a",
      success: res => {
        if (res.confirm) {
          this.data.imgList.splice(e.currentTarget.dataset.index, 1);
          this.setData({
            imgList: this.data.imgList
          })
        }
      }
    })
  },


})
