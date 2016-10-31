<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="ElectronicApplyManage.title"/></title>
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/bootstrap-datetimepicker.min.css'/>" />
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/bootstrap.min.css'/>" />
	<script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
	<script type="text/javascript" src="../scripts/bootstrap.min.js"></script>
	<script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
	<script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
	<script type="text/javascript" src="<c:url value="/scripts/ace.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/ace-elements.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/bootstrap-tag.min.js"/>"></script>
    <script type="text/javascript" src="../scripts/layer/layer.js"></script>
    <script type="text/javascript" src="../scripts/consultation/cons.js"></script>
	<script type="text/javascript" src="../scripts/validform/Validform.min.js"></script>
	<script type="text/javascript" src="../scripts/bootstrap-datetimepicker.min.js"></script>
	<script type="text/javascript" src="../scripts/jquery.zclip.min.js"></script>
</head>
<style>
	select {
		height:34px;
	}
	.ui-autocomplete {
		z-index: 99999999;
	}
    label {
        font-size: 13px;
    }
    .form-group{
        margin-bottom: 0px;
    }
</style>
<body>
<div class="row">
	<input type="hidden" id="samid" value="${sampleId}"/>
	<input type="hidden" id="consampleid" value="${sampleId}"/>
	<input type="hidden" id="consultationid" value="${consultationid}"/>
	<input type="hidden" id="detconsultationid" value="${consultationid}"/>
	<input type="hidden" id="detdoctorid" value="${local_userid}"/>
	<input type="hidden" id="consponsoreduserid" value="${consponsoreduserid}"/>
	<input type="hidden" id="conssts" value="${conconsultationstate}"/>
	<input type="hidden" id="local_userid" value="${local_userid}"/>
	<div class="col-sm-3 leftContent" id="div_2">
		<%--<div class="widget-main no-padding">--%>
			<%--<table id="new" class="table table-striped table-bordered table-hover">--%>
			<%--</table>--%>
			<%--<div id="pager"></div>--%>
		<%--</div>--%>
		<div>
			<div class="h4">会诊意见</div>
			<div id="hzyj">

			</div>

		</div>
	</div>
	<div class="col-sm-6 centerContent" id="formDialog" style="border-style: solid;border-color: #0A246A;border-width: 1px">
	<form class="form-horizontal"  action="#" method="post" id="sampleForm" >
			<ul id="tabs" class="nav nav-tabs">
				<li class="active">
					<a href="#maintab" data-toggle="tab">
						病人基本信息
					</a>
				</li>
				<li>
					<a href="#infotab" data-toggle="tab">
						取材信息
					</a>
				</li>
			</ul>
			<div id="maintab" class="tab-pane fade in active"  style="display: none;">
				<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
					<label class="col-sm-2 control-label no-padding-left">病理编号:</label>
					<div class="col-sm-4">
						<input type="hidden" id="sampleid"><!--标本id-->
						<input type="hidden" id="samcustomerid"><!--客户id-->
						<input type="hidden" id="samsamplestatus"><!--标本状态-->
						<input type="text" id="sampathologycode" name="sampathologycode" readonly/>
					</div>
					<label class="col-sm-2 control-label no-padding-right" >特检病理:</label>
					<div class="col-sm-4 ">
						<select class="col-sm-10" id="samisemergency" disabled="true">
							<option value="0">是</option>
							<option value="1">否</option>
						</select>
					</div>
				</div>
				<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
					<label class="col-sm-2 control-label no-padding-right">病人姓名:</label>
					<div class="col-sm-4 ">
						<input type="text" id="sampatientname" name="sampatientname" readonly/>
					</div>
					<label class="col-sm-2 control-label no-padding-right" >送检医生:</label>
					<div class="col-sm-4 ">
						<input  type="text" id="samsenddoctorname" readonly/>
					</div>
				</div>
				<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
					<label class="col-sm-2 control-label no-padding-right" >住院号:</label>
					<div class="col-sm-4 ">
						<input type="text" id="sampatientnumber" readonly/>
					</div>
					<label class="col-sm-2 control-label no-padding-right">送检科室:</label>
					<div class="col-sm-4">
						<input  type="text" id="samdeptname" readonly/>
					</div>
				</div>
				<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
					<label class="col-sm-2 control-label no-padding-right" >床号:</label>
					<div class="col-sm-4 ">
						<input type="text" id="sampatientbed" readonly/>
					</div>
					<label class="col-sm-2 control-label no-padding-right">送检医院:</label>
					<div class="col-sm-4">
						<input  type="text" id="samsendhospital" readonly/>
					</div>
				</div>
				<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
					<label class="col-sm-2 control-label no-padding-right">性&nbsp;别:</label>
					<div class="col-sm-4">
						<select class="col-sm-10" id="sampatientsex" disabled>
							<option value="1">男</option>
							<option value="2">女</option>
							<option value="3">未知</option>
						</select>
					</div>
					<label class="col-sm-2 control-label no-padding-right">送检材料:</label>
					<div class="col-sm-4">
						<input type="text" id="samsamplename" readonly/>
					</div>
				</div>
				<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
					<label class="col-sm-2 control-label no-padding-right">组织个数:</label>
					<div class="col-sm-4">
						<input type="text" id="samfirstn" readonly/>
					</div>
					<label class="col-sm-2 control-label no-padding-right">送检日期:</label>
					<div class="col-sm-4">
						<input type="text" id="samsendtime" readonly/>
					</div>
				</div>
				<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
					<label class="col-sm-2 control-label no-padding-right">蜡块数:</label>
					<div class="col-sm-4">
						<input type="text" id="nums" readonly/>
					</div>
					<label class="col-sm-2 control-label no-padding-right">接收日期:</label>
					<div class="col-sm-4">
						<input type="text" id="samreceivertime" readonly/>
					</div>
				</div>
				<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
					<label class="col-sm-2 control-label no-padding-right">临床诊断:</label>
					<div class="col-sm-10">
						<input type="text" id="sampatientdignoses" readonly class="col-sm-12"/>
					</div>
				</div>
			</div>
			<div id="infotab" class="tab-pane fade"  style="display: none;">
				<table id="new1">
				</table>
			</div>
		<div class="form-group"
				<c:if test="${consponsoreduserid != local_userid}"> style="margin-right:0px;margin-left:0px;margin-bottom: 0px;"</c:if>
				<c:if test="${consponsoreduserid == local_userid}"> style="display: none" </c:if>
				<c:if test="${conconsultationstate != '0'}"> readonly="readonly" </c:if>
		>
			<label class="col-sm-2 control-label no-padding-right">会诊意见:</label>
			<div class="col-sm-10">
				<textarea id="detadvice" style="width: 100%"></textarea>
			</div>
		</div>
		<div class="form-group"
				<c:if test="${consponsoreduserid == local_userid}"> style="margin-right:0px;margin-left:0px;margin-bottom: 0px;"</c:if>
			 	<c:if test="${consponsoreduserid != local_userid}"> style="display: none" </c:if>
				<c:if test="${conconsultationstate != '0'}"> readonly="readonly" </c:if>
		>
			<label class="col-sm-2 control-label no-padding-right">会诊状态:</label>
			<div class="col-sm-10">
				<input type="radio" name="hzstates" value="0"/>会诊中
				<input type="radio" name="hzstates" value="1"/>会诊终了
				<input type="radio" name="hzstates" value="2"/>会诊取消
			</div>
		</div>
		<div class="form-group"
				<c:if test="${conconsultationstate != '0'}"> style="display: none"  </c:if>
				<c:if test="${conconsultationstate == '0'}"> style="text-align:center; width:100%;height:100%;margin:0px;"  </c:if>
			 >
			<button type="button" class="btn btn-sm btn-danger" id="savebutton" onclick="saveInfo()">
				<i class="ace-icon fa fa-fire bigger-110"></i>保存并刷新
			</button>
		</div>


	</form>
</div>
	<div class="col-sm-3 rightContent" id="formDialog1" style="border-style: solid;border-color: #0A246A;border-width: 1px">
	</div>
</div>
</body>