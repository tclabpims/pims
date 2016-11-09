<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="home.title"/></title>
    <meta name="menu" content="Home"/>
    
<style>
    .img_style{width: 18px;height: 23px}
</style>

</head>
<body class="home"  style="font-family:'Microsoft YaHei',微软雅黑,'MicrosoftJhengHei'!important;">
    <div>
        <h5  style="float: left;width: 50%;font-size: 14px;"><strong>&nbsp;<img src="/styles/imagepims/myinfo.png" class="img_style">&nbsp;我的相关信息</strong></h5>
        <h5 style="float: left;width: 50%;font-size: 14px;margin-bottom: 12px"><strong>&nbsp;<img src="/styles/imagepims/aboutme.png" class="img_style">&nbsp;邀请我的</strong></h5>
    </div>

    <div>
        <ul id="tabs" class="nav nav-tabs">
            <li class="active">
                <a href="#maintab" data-toggle="tab">
                    我的工作未处理
                </a>
            </li>
            <li>
                <a href="#infotab" data-toggle="tab">
                    我的会诊
                </a>
            </li>
            <li>
                <a href="#infotab" data-toggle="tab">
                    我的留言
                </a>
            </li>
            <li>
                <a href="#infotab" data-toggle="tab">
                    我的收藏
                </a>
            </li>
        </ul>
    </div>

</body>
