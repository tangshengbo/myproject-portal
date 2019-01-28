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

        function jsonpCallback(data) {

        }

        $.ajax({
            // 请求方式
            type: "get",
            // 请求地址
            url: "http://localhost/jsonp",
            // 标志跨域请求
            // 跨域函数名的键值，即服务端提取函数名的钥匙（默认为callback）
            jsonp: "callbackParam",
            // 客户端与服务端约定的函数名称
            jsonpCallback: "jsonpCallback",
            // 请求成功的回调函数，json既为我们想要获得的数据
            success: function (json) {
               alert(json);
            },
            // 请求失败的回调函数
            error: function (e) {
                alert("error");
            }
        });
    </script>
</head>
<body>

<h1>myproject-portal</h1>
<form action="${ctx}/account/list" method="post">
    <input type="submit" value="Test">
</form>
<div id="result"></div>

<input type="button" id="addBtn" value="添加">
</body>
</html>


