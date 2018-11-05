<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<style type="text/css">
.tyi {padding-left: 50px;padding-top: 7px;}
.tycode {
width: 180px;
height: 44px;
border: 1px solid #d7d7d7;
padding-left: 10px;
line-height: 44px;
border-radius: 4px;
} 
.ima-style{
padding-top:8px;
float:right;
padding-right:145px;}
</style>
</head>
<body class="login">
<form action="${ctx}/login/index.do" method="post" id="loginForm">
	<div class="login-top">
		<div class="login-left">
			<img src="${adminctx}/images/logn1.png" width="250" height="340">
		</div>
		<div class="login-right">
			<div class="tto">
				<img src="${adminctx}/images/logo1.png">
			</div>
			<div class="li-y">
				用户登录 <span>UserLogin
				<c:if test="${authentication_error eq 1}">
					<font color="red" size="+0.5">用户名或者密码不正确!</font>
				</c:if>
				<c:if test="${authentication_error eq 2}">
				    <font color="red" size="+0.5">用户已锁定，请及时联系管理员!</font>
				</c:if>
				<c:if test="${authentication_error eq 3}">
				    <font color="red" size="+0.5">验证码输入错误!</font>
				</c:if>
				</span>
				<br/>
			</div>
			<div class="tyi">
				<input name="employeeId" type="text" class="ty">
			</div>
			<div class="tyi">
				<input name="password" type="password" class="tyy">
			</div>
			<div class="tyi">
				
				<input name="code" type="code" id="code" class="tycode"> 
				<i class="ima-style">
				<img src="${ctx}/authimg.img" onclick="this.src='${ctx}/authimg.img?k='+Math.random()" style="height:32px;width:76px;"/>
				</i>
				
			</div>
			<div class="tyii">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="37%">
							<a href="javascript: login();"><img src="${adminctx}/images/login-in.png"></a>
						</td>
						<td width="25%"></td>
						<td width="38%"></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</form>
</body>
<script type="text/javascript">

//登录绑定回车键
document.onkeydown = function(event){
	if((event.keyCode || event.which) == 13){
		login();
    }
}
 
function login() {
	if(checkLogin()){	
	   document.getElementById("loginForm").submit();
	}
}


function checkLogin(){
	var employeeId=document.getElementsByName("employeeId")[0].value;
	var password=document.getElementsByName("password")[0].value;
	var code=document.getElementsByName("code")[0].value;
	if(employeeId==''){
		alert("请输入工号！");
		return null;
	}
	if(password==''){
		alert("请输入密码！");
		return null;
	}
	if(code==''){
		alert("请输入验证码！");
		return null;
	}
	return true;
}

</script>
</html>
