<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>首页</title>
    <script  type="text/javascript"  src="${ctx}/js/jquery.min.js"></script>
    <script  type="text/javascript"  src="${ctx}/js/jquery-1.7.2.js"></script>
    <script  type="text/javascript"  src="${ctx}/js/account/account.js"></script>
  </head>
  <body>
     <h1>myproject-portal</h1>
     <form action="${ctx}/account/list" method="post" >
         <input type="submit" value="Test">
     </form>
   <div id="result"></div>
     <input type="button" id="addBtn" value="添加">
  </body>
</html>
