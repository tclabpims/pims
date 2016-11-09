<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/base/base.css'/>" />
<script type="text/javascript" src="../scripts/layer/layer.js"></script>
<script type="text/javascript">
    var baseUrl = "<%=request.getContextPath()%>";
    $(function () {
        var menuName = getQueryStringByName("m");
        if (menuName != "") {
            $("#navigationBar").html("当前位置:" + decodeURI(menuName));
        }
    });
    function getQueryStringByName(name) {
        var result = location.search.match(new RegExp("[\?\&]" + name + "=([^\&]+)", "i"));
        if (result == null || result.length < 1) {
            return "";
        }
        //alert(result);
        return result[1];
    }
</script>
<script type="text/javascript" src="../scripts/message/quick_links.js"></script>
<script type="text/javascript" src="../scripts/message/common.js"></script>

<style>
    .dropdown.hover{background-color: inherit}
</style>

<div style="width:100%;float:left;height: 30px;background-color: #ffffff">
    <div style="float:left;padding-left: 15px;font-size:14px;color: #808080" id="navigationBar"></div>
    <div style="float:right;padding-right:15px;font-size:14px;">
        <a href="#" style="background-color: green;border-radius:5px;float:left;margin-top: 2px;">&nbsp;
            <img src="/styles/imagepims/nookwork.png">
            <span style="color: #ffffff">我的未处理工作</span>&nbsp;<span style="color: red">(5)</span>&nbsp;
        </a>
        <ul style="float:left;list-style: none;margin-right: 20px">
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" style="color: blue"><img src="/styles/imagepims/set.png">设置</a>
                <ul class="dropdown-menu">
                    <li><a href="#">打印机设定</a></li>
                    <li><a href="#">病种再设定</a></li>
                    <li><a href="#">修改密码</a></li>
                    <li><a href="#">系统帮助</a></li>
                    <li><a href="#">联系我们</a></li>
                </ul>
            </li>
        </ul>
        <a href="/logout"><img src="/styles/imagepims/lgout.png">退出</a>
    </div>
</div>
