<%@ include file="/common/taglibs.jsp" %>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>首页</title>
    <script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="${ctx}/js/account/account.js"></script>
    <%--<script type="text/javascript" src="https://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/appmsg/index435ac2.js"></script>--%>
    <script type="text/javascript">


        //        $.getJSON("http://localhost/findAll", function (data) {
        //            alert(data);
        //            console.log(data);
        //        });
        //
        // function jsCallback(data) {
        //     console.info(data);
        //     // alert(data);
        // }
        // 得到航班信息查询结果后的回调函数
        // var flightHandler = function(data){
        //     alert(eval(data).jsonp);
        // };
        // 提供jsonp服务的url地址（不管是什么类型的地址，最终生成的返回值都是一段javascript代码）
        // var url = "http://localhost/jsonp?callback=flightHandler";
        // // 创建script标签，设置其属性
        // var script = document.createElement('script');
        // script.setAttribute('src', url);
        // // 把script标签加入head，此时调用开始
        // document.getElementsByTagName('head')[0].appendChild(script);
        jQuery(document).ready(function () {
            $("#corsBtn").click(function () {
                $.ajax({
                    // 请求方式
                    type: "post",
                    // 请求地址
                    url: "http://localhost/cors",
                    // 标志跨域请求
                    dataType: "json",
                    success: function (json) {
                        console.info(json);
                    },
                    // 请求失败的回调函数
                    error: function (e) {
                        alert("error");
                    }
                })
            });

            $("#jsonpBtn").click(function () {
                $.ajax({
                    // 请求方式
                    type: "get",
                    // 请求地址
                    url: "http://localhost/jsonp",
                    // 标志跨域请求
                    dataType: "jsonp",
                    // 跨域函数名的键值，即服务端提取函数名的钥匙（默认为callback）
                    jsonp: "callback",
                    // // 客户端与服务端约定的函数名称
                    jsonpCallback: "jsCallback",
                    // 请求成功的回调函数，json既为我们想要获得的数据
                    success: function (json) {
                        console.info(json);
                        // alert(eval(json).jsonp);
                    },
                    // 请求失败的回调函数
                    error: function (e) {
                        alert("error");
                    }
                });
            });
        });
    </script>
</head>
<body>

<h1>myproject-portal</h1>
<form action="http://localhost/cors" method="post">
    <input type="submit" value="Test">
</form>
<div id="result"></div>

<input type="button" id="addBtn" value="添加">
<input type="button" id="jsonpBtn" value="Jsonp">
<input type="button" id="corsBtn" value="Cors">
</body>
</html>


