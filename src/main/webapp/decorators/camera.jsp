<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<html>
<head>
    <title>图像采集</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles/ui.jqgrid.css'/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles/jquery-ui.css'/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles/bootstrap-datetimepicker.min.css'/>"/>
    <script type="text/javascript" src="<c:url value="/scripts/ace.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/ace-elements.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/bootstrap-tag.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/jquery-2.1.4.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/jquery-ui.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/bootstrap-datetimepicker.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/i18n/grid.locale-cn.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/jquery.jqGrid.js'/>"></script>
    <script src="<c:url value='/scripts/ajaxfileupload-new.js'/>"></script>
    <script src="<c:url value='/scripts/LodopFuncs.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/validform/Validform.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/layer/layer.js'/>"></script>

    <style type="text/css" media="screen">
        html, body  { height:100%; }
        body { margin:0; padding:0; overflow:auto; text-align:center;
            background-color: #ffffff; }
        object:focus { outline:none; }
    </style>
</head>
<SCRIPT LANGUAGE="JavaScript">
    window.addEventListener("DOMContentLoaded", function () {
        try { document.createElement("canvas").getContext("2d"); } catch (e) { alert("not support canvas!") }
        var video = document.getElementById("video"),
                canvas = document.getElementById("canvas"),
                context = canvas.getContext("2d");
        navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.msGetUserMedia;

        if (navigator.getUserMedia)
            navigator.getUserMedia(
                    { "video": true },
                    function (stream) {
                        if (video.mozSrcObject !== undefined)video.mozSrcObject = stream;
                        else video.src = ((window.URL || window.webkitURL || window.mozURL || window.msURL) && window.URL.createObjectURL(stream)) || stream;
                        video.play();
                    },
                    function (error) {
                        //if(error.PERMISSION_DENIED)console.log("用户拒绝了浏览器请求媒体的权限",error.code);
                        //if(error.NOT_SUPPORTED_ERROR)console.log("当前浏览器不支持拍照功能",error.code);
                        //if(error.MANDATORY_UNSATISFIED_ERROR)console.log("指定的媒体类型未接收到媒体流",error.code);
                        alert("Video capture error: " + error.code);
                    }
            );
        else alert("Native device media streaming (getUserMedia) not supported in this browser");

        $('#snap').on('click', function () {
//            context.drawImage(video, 0, 0, canvas.width = video.videoWidth, canvas.height = video.videoHeight);
            context.drawImage(video, 0, 0, canvas.width = 640, canvas.height = 480);

            var checkvale = $("#continuousBox").is(':checked')?"true":"false";
            var nowshow = parseInt($("#nowshow").val());
             $.post('../diagnosis/upload',
                     { "img": canvas.toDataURL().substr(22),
                         "sampleid":$("#nowsampleid").val(),
                         "samcustomerid":$("#nowcustomerid").val(),
                         "continuous":checkvale,
                         "picpictureclass":"2"
                     },
                     function (data) {
                       var result =  parent.childselect(checkvale,nowshow);
                         if(checkvale == "false" && result > 0){
                              $("#nowsampleid").val(result);
                              $("#nowshow").val(nowshow+1);
                         }
             });
        });
    }, false);


</SCRIPT>
<body>
    <div>
        <input type="hidden" id="nowsampleid" value="${sampleId}">
        <input type="hidden" id="nowcustomerid" value="${customerId}">
        <input type="hidden" id="nowshow" value="${nowshow}">
        <div>
            <input type="checkbox" id="continuousBox"><span style="font-size: 14px">连拍</span></input>
            <button id="snap">拍照</button>
        </div>
        <div class="container">
        <video id="video" width="560px" height="420px" style="margin-top: 0px;margin-bottom: 0px"></video>
        <canvas id="canvas" style="display: none"></canvas>
        </div>
    </div>
</body>
</html>
