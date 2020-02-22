Page({

  /**
   * 页面的初始数据
   */
  data: {
    index: 0,
    picker: ['孕期', '产期'],
    date: '2018-12-25',
    indext: 0,
    modalContent:'',
    pickert: ['顺产', '剖腹产','其它'],
    modalName:null,
    codename: '获取验证码', //按钮文字
    currentTime: 61, //倒计时
    disabled: false, //按钮是否禁用
    phone: '', //获取到的手机栏中的值
    VerificationCode: '',//接口获取到的验证码
    Code: '',//用户输入验证码
    userPassword: '',//密码
    passwordAgain: '',//确认密码
    creditId:'',//身份证号
    userName:'',//昵称
    age:'',//年龄
    job:'',//职业
    pregnantType:'',//怀孕类型
    pregnantWeek:'',//孕周
    confinementDate:'',//产期
    confinementWeek:'',//产周
    confinementType:'',//生产类型
    success: false,
    state: ''
  },


  //孕/产期选择
  PickerChange(e) {
    console.log(e);
    this.setData({
      index: e.detail.value
    })
  },

  //分娩方式选择
  PickerChanget(e) {
    console.log(e);
    this.setData({
      indext: e.detail.value
    })
  },
  //日期改变
  DateChange(e) {
    this.setData({
      date: e.detail.value
    })
  },

  //显示模态框，errmsg-表单错误信息
  showModal(errmsg) {
    this.setData({
      modalContent:errmsg,
      modalName:"Modal"
    })
  },

  //隐藏模态框
  hideModal(e) {
    this.setData({
      modalName: null
    })
  },

  //表单验证并上传到数据库
  formSubmit: function (e) {
    console.log('form发生了submit事件，携带数据为：', e.detail.value);
    var that = this;
    var dataList = e.detail.value;
    var password = dataList.password;
    var checkpwd = dataList.checkpassword;
    var age = dataList.age;
    var idNumber = dataList.idNumber;
    //密码校验
    if(password != undefined &&( password.length < 6 || password != checkpwd))
    {
         this.showModal("请检查密码输入是否大于6位且两次输入密码一致");
         return;
    }
    //正则表达式进行年龄校验
    const posReg = /^\d+$/;
    if(!posReg.test(age) || age<5 || age>100)
    {
     this.showModal("请输入合理的年龄");
     return;
    }
    //身份证校验
    const reg =/(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}$)/;
    if(!reg.test(idNumber))
     {this.showModal("请输入合理的身份证号码");return;}
    //孕周校验
    if(this.data == 0 && !posReg.test(dataList.pregnantWeek))
   {this.showModal("请输入合理的孕周");return;} 
   //如果用户选择 孕产期类型 为 孕期
   if(this.data.index == 0){
      wx.request({
        url: getApp().globalData.serverUrl + '/user/register',
        data: {
          userPassword:dataList.password,//密码
          creditId: idNumber,//身份证号
          userName: dataList.nickName,//昵称
          age: age,//年龄
          job: dataList.job,//职业
          pregnantType: this.data.index+1,//怀孕类型
          pregnantWeek: dataList.pregnantWeek,//孕周
        },
        header: {
          "content-type": "application/x-www-form-urlencoded"
        },
        success: function (res) {
          var status = res.data.status;
          console.log('返回的状态 为 ' + status);
          //status==1表示状态正常
          if(status == 1)
          {
            wx.showToast({
              title: '提交成功,进入人脸验证~',
              icon: 'loading',
              duration: 2000
            })
            that.setData({
              success: true
            })
            wx.navigateTo({
              url: 'index',
            })
          }
          //status不为1,显示错误信息
          console.log(res)
          {
            var str = res.data.data.msg;
            that.showModal(str);
          
          }
        },
        fail:function(res){
          console.log(res),
          console.log("fail")
        }
      })}
      else{
     wx.request({
       url: getApp().globalData.serverUrl + '/user/register',
       data: {
         userPassword: dataList.password,//密码
         creditId: idNumber,//身份证号
         userName: dataList.nickName,//昵称
         age: age,//年龄
         job: dataList.job,//职业
         pregnantType: this.data.index + 1,//怀孕类型
         confinementDate: this.data.date,//产期
         confinementWeek: dataList.confinementWeek,//产周
         confinementType: this.data.indext + 1,//生产类型
       },
       header: {
         "content-type": "application/x-www-form-urlencoded"
       },
       success: function (res) {
         var status = res.data.data.status;
         //status==1表示状态正常
         if (status == 1) {
           wx.showToast({
             title: '提交成功,进入人脸验证~',
             icon: 'loading',
             duration: 2000
           })
           that.setData({
             success: true
           })
           wx.navigateTo({
             url: 'index',
           })
         }
         //status不为1,显示错误信息
         console.log(res)
         {
           var str = res.data.data.msg
           that.showModal(str)
         }
       },
       fail: function (res) {
         console.log(res),
           console.log("fail")
       }
     })
      }
  },

  takePhoto() {
    const ctx = wx.createCameraContext()
    ctx.takePhoto({
      quality: 'high',
      success: (res) => {
        this.setData({
          src: res.tempImagePath
        })
      }
    })
  },
  

  getCode: function () {
      wx.request({
        data: {
        
        },
        /**********************************************/
        url: '',
        /*********************************************/
        success(res) {
          console.log(res.data.data)
          _this.setData({
            VerificationCode: res.data.data
          })
          var num = 61;
          var timer = setInterval(function () {
            num--;
            if (num <= 0) {
              clearInterval(timer);
              _this.setData({
                codename: '重新发送',
                disabled: false
              })

            } else {
              _this.setData({
                codename: num + "s"
              })
            }
          }, 1000)
        }
      })

  },
  
  
})

