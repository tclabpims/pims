<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<html>
<head>
    <title>图像采集</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
    <meta name="decorator" value="none" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/scripts/picture/history/history.css'/>"/>
    <script src="<c:url value='/scripts/picture/swfobject.js'/>"></script>
    <script src="<c:url value='/scripts/picture/history/history.js'/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles/ui.jqgrid.css'/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles/jquery-ui.css'/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles/bootstrap-datetimepicker.min.css'/>"/>
    <script type="text/javascript" src="<c:url value="/scripts/ace.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/ace-elements.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/bootstrap-tag.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/jquery-2.1.4.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/jquery-ui.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/layer/layer.js'/>"></script>
    <style type="text/css" media="screen">
        html, body  { height:100%; }
        body { margin:0; padding:0; overflow:auto; text-align:center;
            background-color: #ffffff; }
        object:focus { outline:none; }
        #flashContent { display:none; }
    </style>
</head>
<SCRIPT LANGUAGE="JavaScript">
    //此地址给摄像头插件调用
    function imgUploadPath() {
        return "<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/diagnosis/upload?sampleid="%>" + parent.GRID_SELECTED_ROW_SAMPLEID + "&samcustomerid=" + parent.GRID_SELECTED_ROW_SAMPCUSTOMERID + "&picpictureclass=" + parent.PIC_TAKING_FROM;
    }

    //此地址给文件上传插件调用
    function multifileUploadUrl() {
        return "<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/diagnosis/multiupload?sampleid="%>" + parent.GRID_SELECTED_ROW_SAMPLEID + "&samcustomerid=" + parent.GRID_SELECTED_ROW_SAMPCUSTOMERID + "&picpictureclass=" + parent.PIC_TAKING_FROM;
    }

    function createImgElement(src) {
        parent.createImgElement(src);
    }

</SCRIPT>
<body onunload="removeVideo()">
<div id="flashContent" style="display: none;">
    <p>
        To view this page ensure that Adobe Flash Player version
        11.1.0 or greater is installed.
    </p>
    <script type="text/javascript">
        var pageHost = ((document.location.protocol == "https:") ? "https://" : "http://");
        document.write("<a href='http://www.adobe.com/go/getflashplayer'><img src='"
                + pageHost + "www.adobe.com/images/shared/download_buttons/get_flash_player.gif' alt='Get Adobe Flash player' /></a>" );
    </script>
</div>
<%
    String swfPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +"/scripts/picture/";
    String swf = swfPath+"Main.swf";
    String install_ = swfPath + "playerProductInstall.swf";
%>
<script type="text/javascript">
        // For version detection, set to min. required Flash Player version, or 0 (or 0.0.0), for no version detection.
        var swfVersionStr = "11.1.0";
        // To use express install, set to playerProductInstall.swf, otherwise the empty string.
        var xiSwfUrlStr = "<%=install_%>";
        var flashvars = {};
        var params = {};
        params.quality = "high";
        params.bgcolor = "#ffffff";
        params.allowscriptaccess = "sameDomain";
        params.allowfullscreen = "true";
        params.wmode = "transparent";
        var attributes = {};
        attributes.id = "Main";
        attributes.name = "Main";
        attributes.align = "middle";
        swfobject.embedSWF(
                "<%=swf%>", "flashContent",
                "100%", "100%",
                swfVersionStr, xiSwfUrlStr,
                flashvars, params, attributes, function(){
                    layer.msg('视频控件已加载！', {
                        time: 1500 //自动关闭
                    });});
        // JavaScript enabled so display the flashContent div in case it is not replaced with a swf object.
        swfobject.createCSS("#flashContent", "display:block;text-align:left;");

    function removeVideo() {
        swfobject.removeSWF("Main");
    }

</script>
<noscript>
    <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100%" height="100%" id="Main">
        <param name="movie" value="<%=swf%>" />
        <param name="quality" value="high" />
        <param name="bgcolor" value="#ffffff" />
        <param name="allowScriptAccess" value="sameDomain" />
        <param name="allowFullScreen" value="true" />
        <!--[if !IE]>-->
        <object type="application/x-shockwave-flash" data="<%=swf%>" width="100%" height="100%">
            <param name="quality" value="high" />
            <param name="bgcolor" value="#ffffff" />
            <param name="allowScriptAccess" value="sameDomain" />
            <param name="allowFullScreen" value="true" />
            <!--<![endif]-->
            <!--[if gte IE 6]>-->
            <p>
                Either scripts and active content are not permitted to run or Adobe Flash Player version
                11.1.0 or greater is not installed.
            </p>
            <!--<![endif]-->
            <a href="http://www.adobe.com/go/getflashplayer">
                <img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash Player" />
            </a>
            <!--[if !IE]>-->
        </object>
        <!--<![endif]-->
    </object>
</noscript>
</body>
</html>
