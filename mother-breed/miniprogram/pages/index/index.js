//index.js

const app = getApp(); //获取应用实例
//         id: 1,
//         title: 'Dawn of us|极致舒缓',
//         singer: '王嘉尔',
//         src: 'http://localhost:1314/王嘉尔 - Dawn of us.mp3',
//         coverImgUrl: '../../images/cover.jpg'

// video_id: 'mpVideo0',
// url: 'http://localhost:1314/skeleton.mp4',
Page({
  data: {
    IsInfo: 1,
    IsPlay: 0,
    musicId: 0,
    item: 0,
    tab: 0,
    id: 0,

    state: 'paused',
    playIndex: 0,
    play: {
      currentTime: '00:00',
      duration: '00:00',
      percent: 0,
      title: '',
      singer: '',
      coverImgUrl: '../../images/cover.jpg'
    },
    //音频列表
    playList: [],
    //文章列表
    articleList: [],
    //视频列表
    videos: [],
    videoList:[]

  },

  //获取文章数据
  onLoad: function () {

    //文章
    this.getTitleList();
    //音频
    this.getAudioList();
    //视频
    this.getVedioList();
     
    
  },


 
  //获取文章列表
  getTitleList: function () {
    var that = this;
    wx.request({
      url: 'http://localhost:8887/article/getlist',
      method: 'GET',
      success: function (res) {
        //要用setData方法，直接使用等于号复制没有效果
        that.setData({
          articleList: res.data.data
        })
      }
    })
  },
  //获取音频列表
  getAudioList: function () {
    var that = this;
    wx.request({
      url: 'http://localhost:8887/audio/getlist',
      method: 'GET',
      success: function (res) {        
        that.setData({
          playList: res.data.data
        })
      }
    })
  },
    //获取视频列表
  getVedioList:function() {
      var that = this;
      wx.request({
        url: 'http://localhost:8887/vedio/getlist',
        method:'GET',
        success:function(res){
          that.setData({
            videoList:res.data.data,
          })
        }        
      })
  },
  //跳转到文章详情
  detailArticle: function (e) {
    var articleId = e.currentTarget.dataset.articleid
    wx.navigateTo({
      url: '../article_detail/article_detail?id=' + articleId,
    })
  },
    //微课频道播放视频
    play_class: function (event) {
      // app.globalData.class_num = e.currentTarget.dataset.item
      // var class_index = app.globalData.class_num;
      // var index = parseInt(class_index);
      // var playClass = app.mini_class[index].url;
      // app.globalData.url = playClass;
    
      var vedioId = event.currentTarget.dataset.item;
      console.log("ceshi " + vedioId);
      wx.navigateTo({
        url: '../miniclass/class?id='+vedioId,
      })
  
    },
  //**************************页面下拉顶部刷新功能**************************************//

  onPullDownRefresh: function () { //下拉刷新

    //显示顶部刷新图标
    wx.showNavigationBarLoading();

    var that = this;

    /****￥与后端协商￥*****/
    // wx.request({
    //   url: 'http://.......',
    //   method:'GET',
    //   header:{
    //     'content-type':'application/text'
    //   },
    //   success:function(res){
    //     that.setData({
    //       //.......
    //     });
    /****￥与后端协商end￥*****/

    //隐藏导航栏加载框
    wx.hideNavigationBarLoading();
    //停止下拉刷新
    wx.stopPullDownRefresh();
    // }
    // })
  },
  //*************************页面下拉顶部刷新功能end************************************//


  //*******************************音频播放功能**************************************//
  audioCtx: null, // 全局
  onReady: function () {

    //创建音频播放对象实例
    this.audioCtx = wx.createInnerAudioContext();

    var that = this;
    //音频播放失败
    this.audioCtx.onError(function () {
      console.log('播放失败:' + that.audioCtx.src)
    });
    //完整播放完
    this.audioCtx.onEnded(function () {
      //切换到下一首
      that.next();
    });
    this.audioCtx.onPlay(function () {

    });
    //自动更新播放速度
    this.audioCtx.onTimeUpdate(function () {
      that.setData({
        'play.duration': formatTime(that.audioCtx.duration),
        'play.currentTime': formatTime(that.audioCtx.currentTime),
        'play.percent': that.audioCtx.currentTime / that.audioCtx.duration * 100
      })
    });

    this.setMusic(0);

    //格式化时间
    function formatTime(time) {
      var minute = Math.floor(time / 60) % 60;
      var second = Math.floor(time) % 60;
      return (minute < 10 ? '0' + minute : minute) + ':' + (second < 10 ? '0' + second : second)
    }
  },
  handleVideoScroll: function () {

    const currentId = this.data.videos[this.data.currentPlayVideoIndex].video_id;
    // 关键代码
    // relativeToViewport 这里指定对比的就是viewport,viewport的意思就是document中的可视区域

    this.observerObj = wx.createIntersectionObserver().relativeToViewport();
    console.log('listen ' + currentId);
    // 监听目标视频跟viewport相交区域的变化
    this.observerObj.observe(`#${currentId}`, this.controlVideos);
  },

  setMusic: function (AudioId) {
    
    console.log(AudioId);
    var that = this;
    wx.request({
      url: 'http://localhost:8887/audio/getone/',
      method:'GET',
      data:{
        id:AudioId
      },
      success:function(res){
        console.log(12);
        console.log(res)
        var music = res.data.data;
        console.log(res)
        that.audioCtx.src = music.audiourl;
        that.setData({
           playIndex: AudioId,
          'play.title': music.title,
          // 'play.singer': music.singer,
          'play.coverImgUrl': music.coverUrl,
          'play.currentTime': '00:00',
          'play.duration': '00:00',
          'play.percent': 0,
        })
      }
    })
    
  },
  next: function () {
    //    //判断是否为列表的最后一首，若是则回到第一首

    var index = this.data.playIndex >= this.data.playList.length - 1 ? 0 : this.data.playIndex + 1;
    this.setMusic(index);
    //若当前状态为暂停则不立即播放，若是播放状态则播放///用于下一首的图标按钮点击
    if (this.data.state === 'running') this.play();
  },

  sliderChange: function (e) {

    var second = e.detail.value * this.audioCtx.duration / 100;
    this.audioCtx.seek(second);
  },
  play: function () {
    this.audioCtx.play();
    this.setData({
      state: 'running'
    });

  },
  pause: function () {
    this.audioCtx.pause();
    this.setData({
      state: 'paused'
    });

  },
  //*****************************音频播放功能end************************************* //

  //*******************************标签页切换功能*************************************//
  /**swiper切换用函数**/
  changeItem: function (e) {
    var item = this.data.item;
    this.setData({
      item: e.target.dataset.item,
      // tab:e.target.dataset.item
    });
    //
    // console.log(item);
  },
  changeTab: function (e) {
    var tab = this.data.tab;
    this.setData({
      tab: e.detail.current
    });
    console.log(tab);
  },

  //*******************************标签页切换功能end**********************************//

  //********************************************************************************//
  //切换到play.wxml播放页面 
  changeToPlay: function (e) {
    this.setData({
      IsInfo: 0, //***一定要用setData！！直接赋值即使值改变也不会有效果！！****/
      IsPlay: 1 //因为只有用了setData才会通知wxml去改变data中的值！
    })
  },

  //返回music.wxml音乐界面
  changeToMusic: function (e) {
    this.setData({
      IsInfo: 1, //***一定要用setData！！直接赋值即使值改变也不会有效果！！****/
      IsPlay: 0 //因为只有用了setData才会通知wxml去改变data中的值！
    })
  },
  //********************************************************************//


  //选择专辑封面切歌
  choseMusic: function (e) {
    var index = parseInt(e.target.dataset.item)
    console.log(index);
    this.setMusic(index);
  },


  // 转到图文详情页1/2/3
  imageText1: function () {
    wx.navigateTo({
      url: 'detailTuWen'
    })
  },
  imageText2: function () {
    wx.navigateTo({
      url: 'detailTuWen'
    })
  },
  imageText3: function () {
    wx.navigateTo({
      url: 'detailTuWen'
    })
  },

  // video视频页
  //暂无函数



  changeToSearch: function () {
    console.log("ceshi changeTosearch.........");
    wx.navigateTo({
      url: '../search/search',
    })
  },

});