<%@ include file="/common/taglibs.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport"
          content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <title>查看图片列表</title>
</head>
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
<script type="text/javascript">
  function deleteImage(url) {
      $.post("${ctx}/love/delete-image?url=" + url,function(data){
          alert("Data: " + data);
          location.reload();
      });
  }

</script>
<body>
<c:forEach var="loveImage" items="${loveImageList}">
    <div> <img width="200" height="150" src="${ctx}${loveImage.imgUrl}">
        <a href="javascript:void(0);" onclick="deleteImage('${loveImage.imgUrl}')" >删除</a></div>
</c:forEach>

</body>
</html>
