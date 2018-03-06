<%@ include file="/common/taglibs.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <title>Love</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/love/love_image.css"/>
</head>
<script type="text/javascript" src="${ctx}/js/love/love_image.js"></script>

<body>
<div id="screen"></div>
<div id="bankImages">
    <c:forEach var="loveImage" items="${loveImageList}">
        <img alt="" src="${ctx}/${loveImage.imgUrl}">
    </c:forEach>
    <%--<img alt="" src="${ctx}/img/3d/wi23.jpg">
    <img alt="" src="${ctx}/img/3d/wt06.jpg">
    <img alt="" src="${ctx}/img/3d/wt47.jpg">
    <img alt="" src="${ctx}/img/3d/wt16.jpg">

    <img alt="" src="${ctx}/img/3d/wt43.jpg">
    <img alt="" src="${ctx}/img/3d/wt19.jpg">
    <img alt="" src="${ctx}/img/3d/wt27.jpg">
    <img alt="" src="${ctx}/img/3d/wt46.jpg">

    <img alt="" src="${ctx}/img/3d/wt14.jpg">
    <img alt="" src="${ctx}/img/3d/wt21.jpg">
    <img alt="" src="${ctx}/img/3d/wt35.jpg">
    <img alt="" src="${ctx}/img/3d/wt48.jpg">

    <img alt="" src="${ctx}/img/3d/wt55.jpg">
    <img alt="" src="${ctx}/img/3d/wt40.jpg">
    <img alt="" src="${ctx}/img/3d/wt53.jpg">
    <img alt="" src="${ctx}/img/3d/wt25.jpg">--%>

</div>

<script type="text/javascript">
    /* ==== start script ==== */
    onresize = tv.resize;
    tv.init();
</script>
<br>

</body>
</html>