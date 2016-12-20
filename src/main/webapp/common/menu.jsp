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
                        $("#span_id2").text(Math.min(99, num));
                        time1 = setInterval(function(){ $("#span_id1").fadeOut(5000).fadeIn(5000); },10000);
                    }
                }
            }
        });
    });
    function spanfcus() {
        clearInterval(time1);
    }
    function spanblur() {
        time1 = setInterval(function(){ $("#span_id1").fadeOut(5000).fadeIn(5000); },10000);
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
    /*.navbar {
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
    }*/
    .navbar {
            min-height: 40px;
            font-size: 15px;
        }
        .navbar-collapse.collapse {
            height: 35px;
            padding:0 12px;
        }
        .navbar-nav > li > a {
            padding-top: 8px;
            padding-bottom: 7px;
            font-size: 15px!important;
        }
        .navbar-brand {
            height: 30px;
            padding-top: 8px;
            padding-bottom: 7px;
        }
     /*改变的导航栏样式*/
        #head .navbar-header img{
            margin-top:10px!important;
        }
        #menuheader>ul>li{
            font-weight:normal;
            margin-top:20px;
        }
        #menuheader>ul>li ul{
            border:1px solid #04a0f3;
            border-radius:5px!important;
            text-align:center;
            background:#f6fbfe!important;
        }
        #menuheader>ul>li ul li a{
            display:block!important;
            border-bottom:1px solid #cee7fd;
            color:#6a6a6a;
            background:#f6fbfe!important;
        }
        #menuheader ul>li ul li:last-child a{
             border-bottom:none;
             color:#6a6a6a;
        }
        #menuheader>ul>li>a{
            background:transparent!important;
            border-radius:5px 5px 0 0;
            color:white!important;
            font-weight:normal!important;
        }
        #menuheader ul>li:hover>a{
            border-radius:5px 5px 0 0;
            color:#323232!important;
            font-weight:normal;
            background:white!important;
        }
        #menuheader ul>li:hover ul{
            display:block;
        }
        #menuheader ul>li ul li:hover a{
            color:#0a93e1!important;
            display:block!important;
            background:#f6fbfe!important;
        }
        #hospital{
             margin-top:28px!important;
        }
        .dropdown-menu{
            min-width:100px!important;
        }
        .dropdown-menu li{
            padding:0 15px;
        }
        .nav-tabs.nav li a{
            color:#313131;
            border:none;
            border-radius:5px 5px 0 0!important;
            background:none;
        }
        .nav-tabs.nav li.active a{
            background:#49a7fe!important;
            color:white!important;
            box-shadow:none;
            border:none;
        }
        .nav-tabs.nav{
            margin:10px 0 14px 0!important;
            border-bottom:1px solid #40a2fb!important;
        }
        #search_div_1{
            min-height:136px!important;
        }
        button{
            height:26px!important;
        }
        #search_div_1 button{
            background:#4190f7!important;
            padding:0 16px;
            font-size:12px;
            border:1px solid #2274e4;
        }
        h5 button{
            height:26px;
            background:#e9e9e9!important;
            padding:0 10px;
            border:1px solid #c2c2c2!important;
        }
        h5 button span{
            color:#393939!important;
        }
        h5 button:last-child{
            background:#4190f7!important;
            border:1px solid #2274e4!important;
        }
        h5 button:last-child span{
            color:white!important;
        }
        #diagnosisInfoForm button{
             height:26px;
             background:#e9e9e9!important;
             padding:0 10px;
             border:1px solid #c2c2c2!important;
             border-radius:3px;
        }
        #diagnosisInfoForm button span{
            color:#393939!important;
        }
        #ctabs button{
             height:26px;
             background:#e9e9e9!important;
             padding:0 10px;
             border:1px solid #c2c2c2!important;
             border-radius:3px;
        }
        /*.table-striped>tbody>tr:nth-of-type(odd) {
            background-color: #eff8fd;
        }
        .table-striped>tbody>tr:nth-of-type(even) {
            background-color: #fff;
        }
        .ui-jqgrid-btable>tr:nth-of-type(odd) {
             background-color: #eff8fd!important;
        }
        .ui-jqgrid-btable>tr:nth-of-type(even) {
             background-color: #fff!important;
        }
        .ui-jqgrid-btable .ui-widget-content.ui-state-highlight{
            background:#E4EFC9!important;
        }*/
        .div_div{
            margin:10px 22px 0 13px!important;
        }
        .form-group{
            padding-left:10px
        }
        .img_style{
            width:20px!important;
            height:25px!important;
        }
        input:focus{
            border:1px solid #40a2fb!important;
        }
        textarea:focus{
            border:1px solid #40a2fb!important;
        }
        select:focus{
            border:1px solid #40a2fb!important;
        }
        .datetimepicker.datetimepicker-dropdown-bottom-right.dropdown-menu{
            border:1px solid #3fa1fa!important;
            padding:0!important;
        }
        .table-condensed{
            border-collapse: separate;
            border-spacing: 2px;
        }
        table th.switch,.prev,.next{
            width:100%;
            color:white;
            background:#3fa1fa!important;
        }
        .today{
            color:#40a3ff;
            background:#ddeffd;
        }
        td.day.old{
            border:1px solid #e7e8ec;
            background:white;
            color:#cecece!important;
        }
        td.day{
            border:1px solid #d7eafb;
            background:#e9f6ff;
            color:#77bcff;
        }
        td.day.active{
            background:#65b3fd!important;
        }
        td.day.new{
            border:1px solid #e7e8ec;
            background:white;
            color:#cecece!important;
        }
        .navbar-header img{
            margin:0 16px 0 20px!important;
        }
        #search_div_1{
            min-width:280px!important;
            padding:0!important;
        }
        #search_div_1 button{
            width:58px;
            position:relative;
            right:-12px;
            top:0px;
        }
        .col-sm-4.input_style{
            width:80px!important;
        }
        .col-sm-2 {
            padding-right:0!important;
        }
        .col-sm-6.input_style{
            height:20px!important;
        }
        .ui-widget-header,.ui-state-default.ui-corner-top{
            background:white;
        }
        .ui-widget-header,.ui-state-default, .ui-widget-content .ui-state-default, .ui-widget-header .ui-state-default, .ui-button, html .ui-button.ui-state-disabled:hover, html .ui-button.ui-state-disabled:active{
            border:none!important;
        }
        .ui-tabs-anchor{
            border:none!important;
            background:none!important;
            color:black!important;
            font-family:"微软雅黑";
        }
        #pathologyType{
            background:rgb(67, 142, 185)!important;
        }
        #pathologyType:focus{
            border:1px solid transparent!important;
        }
        .navbar .navbar-nav>.dropdown>a:focus{
            background:transparent!important;
        }
        .dropdown.open ul{
            display:none;
        }
        .form-control{
            height:26px!important;
        }
</style>

<menu:useMenuDisplayer name="Velocity" config="navbarMenu.vm" permissions="rolesAdapter">
    <div id="menuheader" class="collapse navbar-collapse" style="" id="navbar">
        <ul class="nav navbar-nav" style="">
            <c:if test="${empty pageContext.request.remoteUser}">
                <li class="active form-inline">
                    <span class="form-control" style="width:55px;"><a href="/lab/login"><fmt:message
                            key="login.title"/></a></span>
                    <%--<span class="form-control" style="width:100px;"><a href="/lab/pb/pbcx"><fmt:message--%>
                            <%--key="menu.pb.pbcx"/></a></span>--%>
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
        <a  id="span_id1" href="/home" style="background-color: #0fcfa0;border-radius:2.7rem;float:left;margin-top: 2px;text-decoration:none;" onmouseover="spanfcus()" onmouseleave="spanblur()">&nbsp;
            <img src="/styles/imagepims/nookwork.png">
            <span style="color: #ffffff">我的未处理工作</span>&nbsp;<span id="span_id2" style="padding:0 5px;color: white;background:#f35629;border-radius:2.7rem;"></span>&nbsp;
        </a>
        <ul style="float:left;list-style: none;margin-right: 20px">
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" style="color: #108bd1;text-decoration:none;"><img src="/styles/imagepims/set.png">设置</a>
                <ul class="dropdown-menu">
                    <li><a href="#">打印机设定</a></li>
                    <li><a href="#">病种再设定</a></li>
                    <li><a href="#">修改密码</a></li>
                    <li><a href="#">系统帮助</a></li>
                    <li><a href="#">联系我们</a></li>
                </ul>
            </li>
        </ul>
        <a href="/logout" style="text-decoration:none;color:#108bd1;"><img src="/styles/imagepims/lgout.png">退出</a>
    </div>
</div>