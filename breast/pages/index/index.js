
//获取应用实例
const app = getApp()

Page({
  data: {
    //导航栏
    item: 0,
    tab: 0,
    //音频参数
    playList: [],
    audioCtx: null,
    play: {
      currentTime: '00:00',
      duration: '00:00',
      percent: 0,
      title: '',
      singer: '',
      coverImgUrl: '../../images/cover.jpg'
    },
    state: 'paused',
    playIndex: 0,
    //文章
    articleList: [],
   
    //视频
    videoIndex: null,
    videoList: [],
  },

  onLoad: function () {

    // wx.showLoading({
    //   title: '加载中....',
    // })
    // this.getArticleList();

    // this.getAudioList();
     
    //   this.getVedioList();
  },


  //图文---------------

  //获取文章列表
  getArticleList: function () {
    var that = this;
    var serverUrl = app.globalData.serverUrl;
    wx.request({
      url: serverUrl + '/article/getlist',
      method: 'GET',
      success: function (res) {
        // console.log(res.data.data)
        that.setData({
          articleList: res.data.data
        })
        wx.hideLoading();
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
 
  //跳转到搜索页面
  changeToSearch:function(){
       wx.navigateTo({
         url: '../search/search',
       })
  },

  //音频---------------
  
  audioCtx:null,//音频变量
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
    
        this.initMusic();
    
        //格式化时间
        function formatTime(time) {
          var minute = Math.floor(time / 60) % 60;
          var second = Math.floor(time) % 60;
          return (minute < 10 ? '0' + minute : minute) + ':' + (second < 10 ? '0' + second : second)
        }
  },
  //获取音频列表
  getAudioList: function(){

    var serverurl = app.globalData.serverUrl;
    var that = this;
    wx.request({
      url: serverurl + '/audio/getlist',
      method: 'GET',
      success: function (res) {
        that.setData({
          playList: res.data.data
        })
      },
      fail: function () {
        console.log("failllllllllllllllll")
      }

    })
  },

  //选择专辑封面切换
  choseMusic: function (e) {
    var index = parseInt(e.target.dataset.item);
    this.setMusic(index);
  },
 
  
  //加载页面时播放音频
  initMusic:function(){
    this.audioCtx.src = "http://llllllllr.top/%E5%BC%A0%E7%A2%A7%E6%99%A8&%…A8%E5%AE%97%E7%BA%AC+-+%E5%87%89%E5%87%89.mp32553";
    this.setData({
      'play.title': '母乳喂养的好处有哪些',
      'play.coverUrl': "http://llllllllr.top/zfx.jpg2162",
      'play.currentTime': '00:00',
      'play.duration': '00:00',
      'play.percent': 0,
    })
  },

  //切换音频
  setMusic: function (index) {
    var idx = parseInt(index);
    var music = this.data.playList[idx];
    console.log(music)
    this.audioCtx.src = music.audiourl;
    this.setData({
      playIndex: index,
      'play.title': music.title,
      'play.coverUrl': music.coverUrl,
      'play.currentTime': '00:00',
      'play.duration': '00:00',
      'play.percent': 0,
    })
    this.play();
  },
 
  //播放
  play: function () {
    this.audioCtx.play();
    this.setData({
      state: 'running'
    });
  },
  //停止
  pause: function () {
    this.audioCtx.pause();
    this.setData({
      state: 'paused'
    });
  },
  //下一首
  next: function () {
    //判断是否为列表的最后一首，若是则回到第一首

    var index = this.data.playIndex >= this.data.playList.length - 1 ? 0 : this.data.playIndex + 1;
    this.setMusic(index);
    //若当前状态为暂停则不立即播放，若是播放状态则播放///用于下一首的图标按钮点击
    if (this.data.state === 'running') this.play();
  },



  //视频---------------

  //获取视频列表
  getVedioList: function () {
    var serverurl = app.globalData.serverUrl;
    var that = this;
    wx.request({
      url: serverurl + '/vedio/getlist',
      method: 'GET',
      success: function (res) {
        console.log(res.data.data)
        that.setData({
          videoList: res.data.data,
        })
      }
    })
  },

  	//播放视频
	videoPlay(event) {
		var length = this.data.videoList.length;
		var index = event.currentTarget.dataset['index'];

		if (!this.data.videoIndex) { // 没有播放时播放视频
			this.setData({
				videoIndex: index
			})
			var videoContext = wx.createVideoContext('video' + index)
			videoContext.play()
		} else {
			//停止正在播放的视频
			var videoContextPrev = wx.createVideoContext('video' + this.data.videoIndex)
			videoContextPrev.stop()
			//将点击视频进行播放
			this.setData({
				videoIndex: index
			})
			var videoContextCurrent = wx.createVideoContext('video' + index)
			videoContextCurrent.play()
		}
	},


  //全局---------------

  //切换tab栏的样式
  changeItem: function (e) {
    var item = e.target.dataset.item;

    this.setData({
      item: e.target.dataset.item,
    });
    console.log(item)
  },
  //切换tab栏下面的内容
  changeTab: function (e) {
    var tab = this.data.tab;
    this.setData({
      tab: e.detail.current
    });
  },
})
