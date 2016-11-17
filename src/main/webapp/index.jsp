<%@ page language="java" pageEncoding="UTF-8"%>
<%--引入JSTL核心标签库 --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>$Title$</title>
  </head>
  <body>
     <h1>myproject-portal</h1>
     <form action="${ctx}/account/list" method="post" >
         <input type="submit" value="Test">
     </form>
  </body>
</html>
