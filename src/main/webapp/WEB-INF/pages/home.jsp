<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="home.title"/></title>
    <meta name="menu" content="Home"/>
    
<style>
    .img_style{width: 18px;height: 23px}
    .label_style1{
        font-size: 14px;
        margin-right: 40px;
    }
</style>

</head>
<body class="home"  style="font-family:'Microsoft YaHei',微软雅黑,'MicrosoftJhengHei'!important;">
    <div>
        <h5  style="float: left;width: 50%;font-size: 14px;"><strong>&nbsp;<img src="/styles/imagepims/myinfo.png" class="img_style">&nbsp;我的相关信息</strong></h5>
        <h5 style="float: left;width: 50%;font-size: 14px;margin-bottom: 12px"><strong>&nbsp;<img src="/styles/imagepims/aboutme.png" class="img_style">&nbsp;邀请我的</strong></h5>
    </div>
    <div>
        <div style="width: 50%;float: left">
            <label class="label_style1">我的工作未处理(10)</label>
            <label class="label_style1">我的会诊(6)</label>
            <label class="label_style1">我的留言(10)</label>
            <label class="label_style1">我的收藏(10)</label>
            <label class="label_style1">随访病例(10)</label>
        </div>
        <div style="width: 50%;float: left">
            <label class="label_style1">我的会诊(10)</label>
            <label class="label_style1">我的留言(10)</label>
        </div>
    </div>
    <div>
        <ul id="tabs" class="nav nav-tabs">
            <li class="active">
                <a href="#maintab" data-toggle="tab">
                    未初查
                </a>
            </li>
            <li>
                <a href="#infotab" data-toggle="tab">
                    未审核
                </a>
            </li>
            <li>
                <a href="#infotab" data-toggle="tab">
                    未打印
                </a>
            </li>
            <li>
                <a href="#infotab" data-toggle="tab">
                    未发送
                </a>
            </li>
            <li>
                <a href="#infotab" data-toggle="tab">
                    未接收
                </a>
            </li>
            <li>
                <a href="#infotab" data-toggle="tab">
                    未签收
                </a>
            </li>
            <li>
                <a href="#infotab" data-toggle="tab">
                    未取材
                </a>
            </li>
        </ul>
    </div>

</body>
