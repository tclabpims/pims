<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/base/base.css'/>" />
<script type="text/javascript" src="../scripts/layer/layer.js"></script>
<script type="text/javascript">
    var time1;
    function labChange() {
    }

    var baseUrl = "<%=request.getContextPath()%>";

    $(document).ready(function () {
        $.get("../hpinfo/userrelatepathology", {}, function(data){
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
        });
    });
    $(function () {

        var menuName = getQueryStringByName("m");

        if (menuName != "") {
            $("#navigationBar").html("当前模块:" + decodeURI(menuName));
        }
        var num = 0;

        $.ajax({
            url: '../pathologysample/sample/ajax/num',
            dataType: 'json',
            cache: false,
            success: function(data){
                if(1 == data.success){
                    $("#span_id2").empty();
                    if(0 == data.num){

                    }else{
                        num = data.nonum;
                        //消息总数最大只显示 99
                        $("#span_id2").text("("+Math.min(99, num)+")");
                        time1 = setInterval(function(){ $("#span_id1").fadeOut(500).fadeIn(500); },1000);
                    }
                }
            }
        });
    });
    function spanfcus() {
        clearInterval(time1);
    }
    function spanblur() {
        time1 = setInterval(function(){ $("#span_id1").fadeOut(500).fadeIn(500); },1000);
    }


    function getQueryStringByName(name) {
        var result = location.search.match(new RegExp("[\?\&]" + name + "=([^\&]+)", "i"));
        if (result == null || result.length < 1) {
            return "";
        }
        //alert(result);
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
        padding-bottom: 7px;
        font-size: 16px!important;
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
            <menu:displayMenu name="Home"/>
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
        <div id="hospital" class="collapse navbar-collapse" style="float:right;font-size:14px;margin-top:8px;color:white">
            <%--我的未处理工作：<a href="#" style="color: #ffffff">（10）</a>|--%>
            病理库：
            <select style="appearance:none;-moz-appearance:none;-webkit-appearance:none;background-color:transparent;border: 0;font-size: 14px;color: #ffffff"
                    onchange="changePathologyType()" id="pathologyType">
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
<div style="width:100%;float:left;height: 30px;background-color: #ffffff">
    <div style="float:left;padding-left: 15px;font-size:14px;color: #808080" id="navigationBar"></div>
    <div style="float:right;padding-right:15px;font-size:14px;">
        <a  id="span_id1" href="/home" style="background-color: green;border-radius:5px;float:left;margin-top: 2px;" onmouseover="spanfcus()" onmouseleave="spanblur()">&nbsp;
            <img src="/styles/imagepims/nookwork.png">
            <span style="color: #ffffff">我的未处理工作</span>&nbsp;<span id="span_id2" style="color: red"></span>&nbsp;
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