function setOption(custumOpt, baseLine, data, type,monthDiff) {

   var len = baseLine.length;
  var start = 0;
  var end = 0;
  var title;
  if(monthDiff <= 12){
    start = 0;
    end = 13;
  } else if (monthDiff >12 && monthDiff <=36){
    start = 13;
    end=37;
  }else {
    start = 37;
    end = 61
  }
  var xyOpt = {
    xmin: start,
    xmax: end-1,
    ymin: Math.floor((baseLine[0].data[start][1])/5)*5,
    ymax: Math.ceil(baseLine[len-1].data[end-1][1]/5)*5,
  }
  var nameOpt = {
    xName: '年龄(月)',
    yName: '体重(千克)',
    nameLocation: 'middle',
    nameGap: 25,
    nameTextStyle: {
      fontSize: 14
    }
  };
  if (type == 'weight') {
    nameOpt.yName = '体重(千克)';
    title='体重生长曲线'
  } else if (type == 'height') {
    nameOpt.yName = '身长/身高(厘米)';
    title='身高生长曲线'
  }
  var curveBgColor = '#404A59';
  // var curveBgColor ="white"

  var seriesData = new Array();
  var legendData = new Array();

  for (var i = 0; i < len; i++) {
    legendData.push(baseLine[i].key);
    seriesData.push(
      {
        name: baseLine[i].key,
        type: 'line',
        smooth: true,
        data: baseLine[i].data.slice(start, end),
        symbolSize: 5,
        symbol: 'circle'
      }
    )
  };
  if (data.length > 0) {
    seriesData.push(
      {
        name: custumOpt.babyName,
        type: 'line',
        showSymbol: true,
        symbolSize: function (val) {
          if (val[2] == '' && val[0] == 0)
            return 0;
          else
            return 12;
        },
        clipOverflow: true,
        itemStyle: {
          normal: {
            color: "blue",
            lineStyle: {
              normal: {
                color: 'blue',
                width: 1
              }
            }
          }
        },
        data: data
      }
    )
  }
  return {
    textStyle: {
      color: '#fff'
    },
    backgroundColor: curveBgColor,
    animation:true,
    animationDuration:1000,
    title: {
      text: title,
      left: 'center',
      top: '6%',
      textStyle: {
        color: '#fff',
        fontSize: '12px'
      },
    },
    legend: {
      X: 300,
      bottom: '7%',
      textStyle: {
        color: '#ffd285',
      },
      data: legendData
    },
    tooltip: {
      show: true,
    },
    grid: {
      left: '6%',
      right: '6%',
      bottom: '25%',
      containLabel: true,
      show: true,

    },

    xAxis: [
      {
        type: 'value',
        min: xyOpt.xmin,
        max: xyOpt.xmax,
        boundaryGap: false,
        name: nameOpt.xName,
        nameLocation: nameOpt.nameLocation,
        nameGap: nameOpt.nameGap,
        nameTextStyle: nameOpt.nameTextStyle,
        "axisTick": {
          "show": false
        },
        axisLabel: {
          textStyle: {
            color: '#fff'
          },
          z: 1
        },
      },
    ],
    yAxis: [
      {
        type: 'value',
        min: xyOpt.ymin,
        max: xyOpt.ymax,
        name: nameOpt.yName,
        nameLocation: nameOpt.nameLocation,
        nameGap: nameOpt.nameGap,
        nameTextStyle: nameOpt.nameTextStyle,
        "axisLine": {
          lineStyle: {
            color: '#fff'
          }
        },
        splitLine: {
          show: true,
          lineStyle: {
            color: '#fff'
          }
        },
        "axisTick": {
          "show": false
        },
        axisLabel: {
          textStyle: {
            color: '#fff'
          },
        },

        z: 2
      },

    ],
    series: seriesData
  };
}




module.exports = {
  setOption: setOption
}