<%@ page language="java" pageEncoding="UTF-8"%>
<%--引入JSTL核心标签库 --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fun"%>

<!DOCTYPE html>
<html>
    <head>
        <title>显示用户信息</title>
        <style type="text/css">
            table,td{
                border: 1px solid;
                border-collapse: collapse;
            }
        </style>
    </head>
    <body>
        <table>
     
            <tr>
             SpringMVC
                <td width="100px">用户ID</td>
                <td width="100px">用户名</td>
                <td width="100px">用户金额</td>
                
            </tr>
             <%--遍历lstUsers集合中的User对象 --%>
            <c:forEach var="account" items="${accountList}" >
                <tr>
                    <td>${account.id}</td>
                    <td>${account.name}</td>
                    <td>${account.money}</td>
                    
                </tr>
            </c:forEach>
          
        </table>
    </body>
</html>