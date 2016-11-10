<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="home.title"/></title>
    <meta name="menu" content="Home"/>
    <script src="<c:url value="/scripts/home.js"/>"></script>
<style>
    .img_style{width: 18px;height: 23px}
    .label_style1{font-size: 14px;margin-right: 40px;}
    /*.a_style{color: #323232;cursor:pointer;border-bottom: 4px solid #0FCFA0;text-decoration: none;outline:none;}*/
</style>
</head>
<body class="home"  style="font-family:'Microsoft YaHei',微软雅黑,'MicrosoftJhengHei'!important;">
    <div>
        <h5  style="float: left;width: 50%;font-size: 14px;"><strong>&nbsp;<img src="/styles/imagepims/myinfo.png" class="img_style">&nbsp;我的相关信息</strong></h5>
        <h5 style="float: left;width: 50%;font-size: 14px;margin-bottom: 12px"><strong>&nbsp;<img src="/styles/imagepims/aboutme.png" class="img_style">&nbsp;邀请我的</strong></h5>
    </div>
    <div style="margin-right: 0px;margin-left: 0px;" class="row">
        <div style="width: 50%;float: left">
            <label class="label_style1">
                <a href="#" style="color: #323232;cursor:pointer;border-bottom: 4px solid #0FCFA0;text-decoration: none;outline:none;" onclick="aa()">
                    我的工作未处理(${noworkList})
                </a>
            </label>
            <label class="label_style1">
                <a href="#" style="color: #323232;cursor:pointer;text-decoration: none;outline:none;" onclick="aa()">
                    我的会诊(${mycons})
                </a>
            </label>
            <label class="label_style1">
                <a href="#" style="color: #323232;cursor:pointer;text-decoration: none;outline:none;" onclick="aa()">
                    我的留言(10)
                </a>
            </label>
            <label class="label_style1">
                <a href="#" style="color: #323232;cursor:pointer;text-decoration: none;outline:none;" onclick="aa()">
                    我的收藏(10)
                </a>
            </label>
            <label class="label_style1">
                <a href="#" style="color: #323232;cursor:pointer;text-decoration: none;outline:none;" onclick="aa()">
                    随访病例(10)
                </a>
            </label>
        </div>
        <div style="width: 50%;float: left;margin-bottom: 12px">
            <label class="label_style1">
                <a href="#" style="color: #323232;cursor:pointer;text-decoration: none;outline:none;" onclick="aa()">
                    我的会诊(10)
                </a>
            </label>
            <label class="label_style1">
                <a href="#" style="color: #323232;cursor:pointer;text-decoration: none;outline:none;" onclick="aa()">
                    我的留言(10)
                </a>
            </label>
        </div>
    </div>
    <div>
        <ul id="tabs" class="nav nav-tabs">
            <li class="active">
                <a href="#maintab" data-toggle="tab">
                    未初查(${nocc})
                </a>
            </li>
            <li>
                <a href="#infotab" data-toggle="tab">
                    未审核(${nosh})
                </a>
            </li>
            <li>
                <a href="#infotab" data-toggle="tab">
                    未打印(${nody})
                </a>
            </li>
            <li>
                <a href="#infotab" data-toggle="tab">
                    未发送(${nofs})
                </a>
            </li>
            <li>
                <a href="#infotab" data-toggle="tab">
                    未接收(${nojs})
                </a>
            </li>
            <li>
                <a href="#infotab" data-toggle="tab">
                    未签收(${noqs})
                </a>
            </li>
            <li>
                <a href="#infotab" data-toggle="tab">
                    未取材(${noqc})
                </a>
            </li>
        </ul>
    </div>

</body>
