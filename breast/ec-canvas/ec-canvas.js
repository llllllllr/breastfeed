import WxCanvas from './wx-canvas';
import * as echarts from './echarts';

var ctx;

Component({
  properties: {
    canvasId: {
      type: String,
      value: 'ec-canvas'
    },

    ec: {
      type: Object
    }
  },

  data: {
    isUseNewCanvas:true
  },

  ready: function () {
    if (!this.data.ec) {
      console.warn('组件需绑定 ec 变量，例：<ec-canvas id="mychart-dom-bar" '
        + 'canvas-id="mychart-bar" ec="{{ ec }}"></ec-canvas>');
      return;
    }

    if (!this.data.ec.lazyLoad) {
      this.init();
    }
  },

  methods: {
    init: function (callback) {
      const version = wx.version.version.split('.').map(n => parseInt(n, 10));
      const isValid = version[0] > 1 || (version[0] === 1 && version[1] > 9)
        || (version[0] === 1 && version[1] === 9 && version[2] >= 91);
      if (!isValid) {
        console.error('微信基础库版本过低，需大于等于 1.9.91。'
          + '参见：https://github.com/ecomfe/echarts-for-weixin'
          + '#%E5%BE%AE%E4%BF%A1%E7%89%88%E6%9C%AC%E8%A6%81%E6%B1%82');
        return;
      }

      ctx = wx.createCanvasContext(this.data.canvasId, this);
      const canvas = new WxCanvas(ctx, this.data.canvasId);

      echarts.setCanvasCreator(() => {
        return canvas;
      });

      var query = wx.createSelectorQuery().in(this);
      query.select('.ec-canvas').boundingClientRect(res => {
        if (typeof callback === 'function') {
          this.chart = callback(canvas, res.width, res.height);
        }
        else if (this.data.ec && typeof this.data.ec.onInit === 'function') {
          this.chart = this.data.ec.onInit(canvas, res.width, res.height);
        }
        else {
          this.triggerEvent('init', {
            canvas: canvas,
            width: res.width,
            height: res.height
          });
        }
      }).exec();
    },
//修改后的,生成图片
    canvasToTempFilePath(opt) {
      var that = this;
      console.log(that.data.canvasId);
      if (!opt.canvasId) {
        opt.canvasId = this.data.canvasId;
      }
      const system = wx.getSystemInfoSync().system;
      console.log(system);
     // console.log(/ios/i.test(system));
     // if (/ios/i.test(system)) {
        if (0) {
        ctx.draw(true, () => {          
          wx.canvasToTempFilePath(opt, this);          
        });  
      } else {//针对安卓机型异步获取已绘制图层
       // console.log(system);
      //  ctx.draw(true);
      // ctx.draw(true,()=>{
        //断点打印依旧不会执行setTimeout(() => {wx.canvasToTempFilePath(opt, this);}, 1000);}});
       // console.log('pppp');
       // setTimeout(() => {//延迟获取
          // wx.canvasToTempFilePath(opt, this);
          wx.canvasToTempFilePath({
            canvasId: opt.canvasId || that.data.canvasId,
            success: function (res) {
                var tempFilePath = res.tempFilePath;
                //保存图片
                wx.saveImageToPhotosAlbum({
                  filePath:tempFilePath,
                  success(res){
                    wx.showToast({
                      title:'图片保存中...',
                      icon:'loading',
                      duration:2000
                    });
                    setTimeout(function(){
                      wx.showToast({
                        title:'图片保存成功...',
                        icon:'loading',
                        duration:2000
                      });
                    },1000)
                  },
                  fail(res) {
                    if (res.errMsg === "saveImageToPhotosAlbum:fail auth deny" || res.errMsg == "saveImageToPhotosAlbum:fail:auth denied" || res.errMsg == "saveImageToPhotosAlbum:fail authorize no response") {
                      wx.showModal({
                        title: '提示',
                        content: '需要授权才可保存图片',
                        showCancel: false,
                        success(res) {
                          if (res.confirm) {
                            wx.openSetting({
                              success(settingdata) {
                                if (settingdata.authSetting["scope.writePhotosAlbum"]) {
                                  wx.showToast({
                                    title: '获取权限成功，再次点击可保存图片',
                                    icon: 'none',
                                    duration: 1000
                                  })
                                } else {
                                  wx.showToast({
                                    title: '获取权限失败',
                                    icon: 'none',
                                    duration: 1000
                                  })
                                }
                              },
                              fail() {
                                wx.showToast({
                                  title: '获取权限失败',
                                  icon: 'none',
                                  duration: 1000
                                })
                              }
                            })
                          }
                        }
                      })
                    }
                  }
                });
              //       that.setData({
              //     imagePath: tempFilePath,
              //     maskHidden: false
              //  });              
            },
            fail: function (res) {
                alert(res);
            }
        },this);
      // }, 200);
    // });
    }
  },

    touchStart(e) {
      if (this.chart && e.touches.length > 0) {
        var touch = e.touches[0];
        var handler = this.chart.getZr().handler;
        handler.dispatch('mousedown', {
          zrX: touch.x,
          zrY: touch.y
        });
        handler.dispatch('mousemove', {
          zrX: touch.x,
          zrY: touch.y
        });
        handler.processGesture(wrapTouch(e), 'start');
      }
    },

    touchMove(e) {
      if (this.chart && e.touches.length > 0) {
        var touch = e.touches[0];
        var handler = this.chart.getZr().handler;
        handler.dispatch('mousemove', {
          zrX: touch.x,
          zrY: touch.y
        });
        handler.processGesture(wrapTouch(e), 'change');
      }
    },

    touchEnd(e) {
      if (this.chart) {
        const touch = e.changedTouches ? e.changedTouches[0] : {};
        var handler = this.chart.getZr().handler;
        handler.dispatch('mouseup', {
          zrX: touch.x,
          zrY: touch.y
        });
        handler.dispatch('click', {
          zrX: touch.x,
          zrY: touch.y
        });
        handler.processGesture(wrapTouch(e), 'end');
      }
    }
  }
});

function wrapTouch(event) {
  for (let i = 0; i < event.touches.length; ++i) {
    const touch = event.touches[i];
    touch.offsetX = touch.x;
    touch.offsetY = touch.y;
  }
  return event;
}
