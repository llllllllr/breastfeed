const formatTime = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()

  return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}
function formatDate(number, format) {
  var formateArr = ['Y', 'M', 'D', 'h', 'm', 's'];
  var returnArr = [];

  var date = new Date(number * 1000);
  returnArr.push(date.getFullYear());
  returnArr.push(formatNumber(date.getMonth() + 1));
  returnArr.push(formatNumber(date.getDate()));

  returnArr.push(formatNumber(date.getHours()));
  returnArr.push(formatNumber(date.getMinutes()));
  returnArr.push(formatNumber(date.getSeconds()));

  for (var i in returnArr) {
    format = format.replace(formateArr[i], returnArr[i]);
  }
  return format;
}
const formatNumber = n => {
  n = n.toString()
  return n[1] ? n : '0' + n
}

function formarNowDate() {
  var date = new Date();
  var year = date.getFullYear();
  var month = date.getMonth() + 1;
  var day = date.getDate();
  if (month < 10)
    month = "0" + month;
  if (day < 10)
    day = "0" + day;
  var nowDate = year + "-" + month + "-" + day;
  return nowDate;
}


function monthDiff(beforeDate, nowDate) {
  beforeDate = beforeDate.split("-");
  nowDate = nowDate.split("-");
  var beforeMonth_len = parseInt(beforeDate[0]) * 12 + parseInt(beforeDate[1]);
  var nowMonth_len = parseInt(nowDate[0]) * 12 + parseInt(nowDate[1]);
  return nowMonth_len - beforeMonth_len;
}

function showMsg(msg){
  wx.showModal({
    content:msg,
    confirmColor:"#d4237a",
    showCancel:'false'
  })
}
module.exports = {
  formatTime: formatTime,
  formatDate: formatDate,
  formarNowDate: formarNowDate,
  monthDiff:monthDiff,
  showMsg:showMsg
}
