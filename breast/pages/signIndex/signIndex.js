const app = getApp();
Page({

  
  data: {
    up:"我是医生",
    down:"我是用户",
    position:0,//1-用户 2-医生
    isVisible:false,
  },

  
  onLoad: function (options) {

  },
  showModal(e) {
    this.setData({
      modalName: e.currentTarget.dataset.target
    })
  },
  hideModal(e) {
    this.setData({
      modalName: null
    })
  },
  
  onReady: function () {

  },

  showLogin:function(){
    this.setData({
      isVisible:true,
      up:"登录",
      down:"注册"
   })
  },
  BACK:function(){
      this.setData({
        up:"我是医生",
        down:"我是用户",
        isVisible:false
      })
  },

  UP:function(){
    if(this.data.up == "我是医生")
    {
      //设置用户身份
       this.setData({
         position:2
       })//显示登录注册
       this.showLogin();
       return;
    }
    
      if(this.data.up == "登录")
      {
        //跳转到用户登录界面
        if(this.data.position == 1)
        {
          wx.navigateTo({
            url: '../signIn/signIn?object=user',
          })
        }
        else 
        {
          //跳转到医生登录界面
          wx.navigateTo({
            url: '../signIn/signIn?object=doctor',
          })
        }
      }
    },

    DOWN:function(){
      if(this.data.down == '我是用户')
      {
        //设置用户身份
        this.setData({
          position:1
        })//显示登录注册
        this.showLogin();
        return;
      }

      if(this.data.down == "注册")
      {
        //跳转到用户登录界面
        if(this.data.position == 1)
        {
          wx.navigateTo({
            url: '../signUp/signUp',
          })
        }
        else 
        {
          //跳转到医生注册界面
          wx.navigateTo({
            url:'../doctorRegister/doctorRegister',
          })
         
        }
      }
     
    }
    
  
 
})