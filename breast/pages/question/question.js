// pages/question/question.js
Page({

   
  data: {
     title:"分电视剧奋斗看来飞机迪斯科飞机砥砺奋进大撒大撒大大的说法是大师傅似的犯得上反对法地方",
     options:[
       {
         selected:-1,
         opstion:"没信心"
       },
       {
        selected:-1,
        opstion:"有一点信心"
      },
      {
        selected:-1,
        opstion:"非常非常非常没信心"
      },
      {
        selected:-1,
        opstion:"没信心"
      },
      {
        selected:-1,
        opstion:"没信心"
      },
      {
        selected:-1,
        opstion:"没信心"
      },
      {
       selected:-1,
       opstion:"有一点信心"
     },
     {
       selected:-1,
       opstion:"非常非常非常没信心"
     },
     {
       selected:-1,
       opstion:"没信心"
     },
     {
       selected:-1,
       opstion:"没信心"
     }
     ]
  },
  onLoad: function (options) {

  },
  
  selectOp:function(event)
  {
     console.log(event)
     var idx = event.currentTarget.dataset.index;
     var before_select = this.data.options[idx].selected;
     var str = "options[" + idx + "].selected";
     this.setData({
       [str]:before_select*(-1)
     })
  }
})