<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <script src="${pageContext.request.contextPath}/js/jquery.min.js" type="text/javascript"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>
生成的验证码：<img id="changeCaptcha" src="${pageContext.request.contextPath}/captcha/getCaptchaCode"> <a href="javascript:changeCaptcha()">看不清，换一张</a>
<br>
<br>
请输入验证码：<input id="captchaCode" type="text"> <input type="button" value="提交验证" onclick="checkCaptcha()">
</body>
<script type="text/javascript">
    //获取验证码图片
    function changeCaptcha(){
        $("#changeCaptcha").attr("src","${pageContext.request.contextPath}/captcha/getCaptchaCode");
    }
    //验证输入的验证码
    function checkCaptcha(){
        var captchaCode = $("#captchaCode").val();
        $.ajax({
            type:'post',
            async : false,
            url:'${pageContext.request.contextPath}/captcha/checkCaptchaCode',
            data:{"captchaCode" : captchaCode},
            success:function(res){
                alert(res);
            }
        });
    }
</script>
</html>