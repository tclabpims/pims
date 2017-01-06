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
    <script type="text/javascript" src="<c:url value='/scripts/jquery.webcam.min.js'/>"></script>

    <style type="text/css" media="screen">
        html, body  { height:100%; }
        body { margin:0; padding:0; overflow:auto; text-align:center;
            background-color: #ffffff; }
        object:focus { outline:none; }
        #flashContent { display:none; }
    </style>
</head>
<SCRIPT LANGUAGE="JavaScript">
    jQuery("#webcam").webcam({

        width: 320,
        height: 240,
        mode: "callback",
        swffile: "/scripts/jscam_canvas_only.swf", // canvas only doesn't implement a jpeg encoder, so the file is much smaller

        onTick: function(remain) {

            if (0 == remain) {
                jQuery("#status").text("Cheese!");
            } else {
                jQuery("#status").text(remain + " seconds remaining...");
            }
        },

        onSave: function(data) {

            var col = data.split(";");
            // Work with the picture. Picture-data is encoded as an array of arrays... Not really nice, though =/
        },

        onCapture: function () {
            webcam.save();

            // Show a flash for example
        },

        debug: function (type, string) {
            // Write debug information to console.log() or a div, ...
        },

        onLoad: function () {
            // Page load
            var cams = webcam.getCameraList();
            for(var i in cams) {
                jQuery("#cams").append("<li>" + cams[i] + "</li>");
            }
        }
    });

</SCRIPT>
<body>
<div id="webcam" ></div>

</body>
</html>
