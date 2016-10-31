<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/base/base.css'/>" />
<script type="text/javascript" src="../scripts/layer/layer.js"></script>
<script type="text/javascript">
    function labChange() {
    }

    var baseUrl = "<%=request.getContextPath()%>";

    $(document).ready(function () {
        var url = "../hpinfo/userrelatepathology";
        $.ajax({
            type: "GET",
            url: url,
            success: function (data) {
                var rows = data.rows;
                if (rows.length > 0) {
                    for (var i = 0; i < rows.length; i++) {
                        var obj = rows[i];
                        $("#pathologyList").append("<option value='" + obj.pathologyid + "'>" + obj.patnamech + "</option>\n");
                        $("#pathologyType").append("<option value='" + obj.pathologyid + "'>" + obj.patnamech + "</option>\n");
                    }
                    var currentPathology = data.userdata;
                    if(currentPathology.pathologyLibId != null && jQuery.trim(currentPathology.pathologyLibId) != "") {
                        $("#pathologyType").val(currentPathology.pathologyLibId);
                    } else {
                    layer.open({
                        type: 1,
                        area: ['200px', '125px'],
                        fix: false, //不固定
                        maxmin: false,
                        shade: 0.6,
                        title: "选择病种",
                        content: $("#pathologyListContainer"),
                        btn: ["确定"],
                        yes: function (index, layero) {
                            var pathologyId = $("#pathologyList").val();
                            var pathologyName = $("#pathologyList").find("option:selected").text();
                            if (pathologyId != null && jQuery.trim(pathologyId) != "") {
                                $.post('../hpinfo/userpathology', {
                                    pathologyLibId: pathologyId,
                                    pathologyLib: pathologyName
                                }, function (data) {
                                    layer.close(index);
                                });
                            } else {
                                layer.close(index);
                            }
                        }
                    });
                    }
                } else {
                    layer.alert("请先添加病种",{icon:1,title:"提示"});
                    return false;
                }
            }
        });

    });
    $(function () {

        var menuName = getQueryStringByName("m");

        if (menuName != "") {
            $("#navigationBar").html("当前模块:" + decodeURI(menuName));
        }


    });

    function getQueryStringByName(name) {
        var result = location.search.match(new RegExp("[\?\&]" + name + "=([^\&]+)", "i"));
        if (result == null || result.length < 1) {
            return "";

        }
        return result[1];
    }

    function union() {
        $("#pathologyType").val($("#pathologyList").val());
    }

    function changePathologyType() {
        var pathologyId = $("#pathologyType").val();
        var pathologyName = $("#pathologyType").find("option:selected").text();
        if (pathologyId != null && jQuery.trim(pathologyId) != "") {
            $.post('../hpinfo/userpathology', {
                pathologyLibId: pathologyId,
                pathologyLib: pathologyName
            }, function (data) {
                location.reload();
            });
        }
    }
</script>
<script type="text/javascript" src="../scripts/message/quick_links.js"></script>
<script type="text/javascript" src="../scripts/message/common.js"></script>

<style>
    .navbar {
        min-height: 40px;
        font-size: 15px;
    }

    .navbar-collapse.collapse {
        height: 35px;
    }

    .navbar-nav > li > a {
        padding-top: 8px;
        padding-bottom: 7px
    }

    .navbar-brand {
        height: 30px;
        padding-top: 8px;
        padding-bottom: 7px;
    }
</style>

<menu:useMenuDisplayer name="Velocity" config="navbarMenu.vm" permissions="rolesAdapter">
    <div id="menuheader" class="collapse navbar-collapse" style="" id="navbar">
        <ul class="nav navbar-nav" style="">
            <c:if test="${empty pageContext.request.remoteUser}">
                <li class="active form-inline">
                    <span class="form-control" style="width:55px;"><a href="/lab/login"><fmt:message
                            key="login.title"/></a></span>
                    <span class="form-control" style="width:100px;"><a href="/lab/pb/pbcx"><fmt:message
                            key="menu.pb.pbcx"/></a></span>
                </li>
            </c:if>
            <menu:displayMenu name="SpecimenDispose"/>
            <menu:displayMenu name="PathologicalDiagnosis"/>
            <menu:displayMenu name="MedicalOrderTreatment"/>
            <menu:displayMenu name="InquiryStatistics"/>
            <menu:displayMenu name="OtherManage"/>
            <menu:displayMenu name="BasicData"/>
            <menu:displayMenu name="Individual"/>
            <menu:displayMenu name="ElectronicApplyManage"/>
        </ul>
        <input type="hidden" id="scode" value="210800"/>
        <input type="hidden" id="pathologyLibId"/>
        <div id="hospital" class="collapse navbar-collapse" style="float:right;font-size:14px;color:white">
            我的未处理工作：<a href="#" style="color: #ffffff">（10）</a>|
            当前病种库：
            <select onchange="changePathologyType()" id="pathologyType">
                    <%--<%
                        User user = WebControllerUtil.getAuthUser();
                        UserBussinessRelate ubr = user.getUserBussinessRelate();
                        ApplicationContext ctx= WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
                        PimsSysPathologyManager pimsSysPathologyManager = (PimsSysPathologyManager) ctx.getBean("pimsSysPathologyManager");
                        JSONArray items = pimsSysPathologyManager.getPathologyType();
                        for(Object obj : items) {
                            JSONObject o = (JSONObject)obj;
                            StringBuilder builder = new StringBuilder();
                            builder.append("<option value=\"")
                                    .append(o.get("pathologyLibId"))
                                    .append("\" ");
                            if(ubr.getPathologyLibId().equals(String.valueOf(o.get("pathologyLibId")))) {
                                builder.append(" selected ");
                            }
                            builder.append(">")
                                    .append(o.get("pathologyLib"))
                                    .append("</option>\n");
                            out.print(builder.toString());
                        }
                    %>--%>
            </select>
        </div>
    </div>
</menu:useMenuDisplayer>
<div id="pathologyListContainer" style="display:none;alignment: center">
    <div class="mainContent" style="text-align:center;">
        <select id="pathologyList" onchange="union()">
        </select>
    </div>
</div>
<div style="width:100%;height: 25px;float:left;color:white">
    <div style="float:left;padding-left: 15px;font-size:14px;" id="navigationBar"></div>
    <div style="float:right;padding-right:15px;font-size:14px;">
        <a href="/home" style="color: #ffffff">系统首页</a>
        |<a href="#" style="color: #ffffff">打印机设定</a>
        |<a href="#" style="color: #ffffff">修改密码</a>
        |<a href="/logout" style="color: #ffffff">退出登录</a>
        |<a href="#" style="color: #ffffff">系统帮助</a>
        |<a href="#" style="color: #ffffff">联系我们</a>
    </div>
</div>
