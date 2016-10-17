<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.pims.service.pimssyspathology.PimsSysPathologyManager" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="com.alibaba.fastjson.JSONArray" %>
<%@ page import="com.alibaba.fastjson.JSONObject" %>
<%@ page import="com.pims.webapp.controller.WebControllerUtil" %>
<%@ page import="com.smart.model.user.User" %>
<%@ page import="com.smart.model.user.UserBussinessRelate" %>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
function labChange() {
}

var baseUrl = "<%=request.getContextPath()%>";
$(function() {
	if( ${pageContext.request.remoteUser != null} ) {
		//var nowUrl = window.location.pathname;
		var url = "<%=request.getContextPath()%>/users/ajax/pimsuserInfo";
		//if(nowUrl.indexOf("pb")>=0){
		//	url = "<%=request.getContextPath()%>/users/ajax/hospital?ispb=1";
		//}
		//$.ajax({
	    //    type : "GET",
	    //    url : url,
	    //    success : function(data) {
	    //       var json = jQuery.parseJSON(data);
	           //$("#userText").html(json.name);
	           //for(var key in json.labMap) {
	        	//   $("#labSelect").append("<li onclick='labChange(this)'><a title='" + key + "'>" + json.labMap[key] + "</a></li>");
	           //}
	           //$("#pathologyLibId").val(json.pathologyLibId);
	           //$("#labText").html(json.lab);
	           //$("#pathologyLib").append(json.pathologyLib);
	           //$("#pathologyLib").css("display","block");
	    //    }
	    //});
	}

	var menuName = getQueryStringByName("m");

    if(menuName != ""){
        $("#navigationBar").html("当前模块:"+decodeURI(menuName));
    }
});

function getQueryStringByName(name){
    var result = location.search.match(new RegExp("[\?\&]" + name+ "=([^\&]+)","i"));
    if(result == null || result.length < 1){
        return "";

    }
    return result[1];
}

function changePathologyType(){
    var choose_ = $("#pathologyType").val();
    $.ajax({
        type : "GET",
        data:{pid:choose_},
        url : "/pspathology/dcm/cpt",
        success : function(data) {
            location.reload();
        }
    });
}
</script>

<style>
.navbar {
    min-height: 40px;
    font-size: 15px;
}
.navbar-collapse.collapse {
		height: 35px;
	}
.navbar-nav>li>a {
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
            <span class="form-control" style="width:55px;" ><a href="/lab/login"><fmt:message key="login.title"/></a></span>
            <span class="form-control" style="width:100px;"><a href="/lab/pb/pbcx"><fmt:message key="menu.pb.pbcx"/></a></span>
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
<input type="hidden" id="pathologyLibId" />
<div id="hospital" class="collapse navbar-collapse" style="float:right;font-size:14px;color:white">
    我的未处理工作：<a href="#" style="color: #ffffff">（10）</a>|
    当前病种库：
        <select onchange="changePathologyType()" id="pathologyType">
            <%
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
            %>
        </select>
</div>
</div>
</menu:useMenuDisplayer>
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
