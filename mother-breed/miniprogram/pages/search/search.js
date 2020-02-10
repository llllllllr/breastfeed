
/* 其中里面的
http://localhost/search.php?keyword=
是服务器后端接口，用于接收小程序传过去的关键词*/
var util = require('../../utils/util.js')
const app = getApp()
Page({
  data: {
    logs: []
  },
  //历史记录 
  onLoad: function () {
    this.setData({
      logs: (wx.getStorageSync('logs') || []).map(function (log) {
        return util.formatTime(new Date(log))
      })
    })
  },
  //执行点击事件
  formSubmit: function (e) {

    //声明当天执行的
    var that = this;

    //获取表单所有name=keyword的值
    var formData = e.detail.value.keyword;

    //显示搜索中的提示
    wx.showLoading({
      title: '搜索中',
      icon: 'loading',
      duration:2000
    })

    //向搜索后端服务器发起请求
    wx.request({

      //URL
      url: 'http://localhost/search.php?keyword=' + formData,

      //发送的数据
      data: formData,

      //请求的数据时JSON格式
      header: {
        'Content-Type': 'application/json'
      },

      //请求成功
      success: function (res) {

        //控制台打印（开发调试用）
        console.log(res.data)

        //把所有结果存进一个名为re的数组
        that.setData({
          re: res.data,
        })

        //搜索成功后，隐藏搜索中的提示
        wx.hideLoading();
      }
    })
  },
})


// php文件内容
/*
<?php
header('Content-Type:application/json');

//获取表单数据
$keyword1 = $_GET["keyword"];

//过滤表单空格
$keyword2 = trim($keyword1);

//当表单提交空白数据时
if(empty($keyword2)){

    //构建数组
    $arr = array(
            "empty" => "表单不能为空"
        );

    //把数组转换为json
    $data = json_encode($arr);
    echo "[$data]";

}else{

//过滤表单特殊字符
$replace = array('!','@','#','$','%','^','&','*','(',')','_','-','+','=','{','}','[',']',';',':','"','<','>','?','/','|');
$keyword3 = str_replace($replace, '', $keyword2);

// 连接数据库
$con = mysql_connect("数据库地址","数据库账号","数据库密码");
if (!$con){die('Could not connect: ' . mysql_error());}

mysql_select_db("数据库名", $con);
mysql_query("SET NAMES UTF8");

//查询数据库
$result = mysql_query("SELECT * FROM 表名 WHERE 需要查询的字段 like '%$keyword3%' ORDER BY ID DESC");
$results = array();
//查询数据库是否存在这条记录
$exist = mysql_num_rows($result);
if ($exist) {
    //遍历输出
    while ($row = mysql_fetch_assoc($result)){
        $results[] = $row;
        }

    //输出JSON
    echo json_encode($results);

    //当查询无结果的时候
    }else{

        //构建数组
        $arr = array(
            "noresult" => "暂无结果"
        );

        //把数组转换为json
        $data = json_encode($arr);
        echo "[$data]";
}

//断开数据库连接
mysql_close($con);
}
?> */