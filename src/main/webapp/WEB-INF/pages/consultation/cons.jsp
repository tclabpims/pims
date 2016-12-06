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
	<style>
		.div_div {float:left;margin:20px 35px 11px 8px;text-align:center;color: #808080;font-size: 12px;  }
		.div_img{cursor:pointer;display: block;margin-bottom:11px;}
		.div_1{background-color: #F9F9F9;height: 106px;border:1px solid #E0E0E0}
		.img_style{width: 18px;height: 23px}
		.label_style{font-size: 12px;color: #323232;height: 24px;text-align:right;}
		.input_style{height: 24px;font-size: 12px!important;}
		.ui-jqgrid-sortable{text-align: center;}
		.ui-jqgrid-hbox{padding-right: 0px!important;}
	</style>
</head>
<body  style="font-family:'Microsoft YaHei',微软雅黑,'MicrosoftJhengHei'!important;">
	<div class="row">
		<input type="hidden" id="samid" value="${sampleId}"/>
		<input type="hidden" id="consampleid" value="${sampleId}"/>
		<input type="hidden" id="consultationid" value="${consultationid}"/>
		<input type="hidden" id="detconsultationid" value="${consultationid}"/>
		<input type="hidden" id="detdoctorid" value="${local_userid}"/>
		<input type="hidden" id="consponsoreduserid" value="${consponsoreduserid}"/>
		<input type="hidden" id="conssts" value="${conconsultationstate}"/>
		<input type="hidden" id="local_userid" value="${local_userid}"/>
		<h5 style="float: left;width: 25%;font-size: 14px;"><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;会诊意见&nbsp;&nbsp;&nbsp;&nbsp;</strong></h5>
		<h5 style="float: left;width: 50%;font-size: 14px;"><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;会诊案例&nbsp;&nbsp;&nbsp;&nbsp;</strong></h5>
		<h5 style="float: left;width: 25%;font-size: 14px;margin-bottom: 10px"><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;图像采集&nbsp;&nbsp;&nbsp;&nbsp;</strong></h5>
		<div class="col-sm-3 leftContent" id="div_2">
			<div style="background-color: #F9F9F9;border:1px solid #E0E0E0;height: 530px;overflow:auto;">
				<div id="hzyj">
				</div>
			</div>
		</div>
		<div class="col-sm-6 centerContent" id="formDialog">
			<form class="form-horizontal" style="background-color: #F9F9F9;border:1px solid #E0E0E0;" action="#" method="post" id="sampleForm" >
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
					<span style="float: right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
					<button type="button" style="border-radius:3px;float: right;" onclick="imageImport()">
						图像导入
					</button>
				</ul>
				<div id="maintab" class="tab-pane fade in active"  style="display: none;">
					<div class="form-group" style="margin-top:10px;margin-bottom: 5px;">
						<label class="col-sm-2 label_style">病理编号:</label>
						<div class="col-sm-4">
							<input type="hidden" id="sampleid"><!--标本id-->
							<input type="hidden" id="samcustomerid"><!--客户id-->
							<input type="hidden" id="samsamplestatus"><!--标本状态-->
							<input type="text" class="input_style" id="sampathologycode" name="sampathologycode" readonly/>
						</div>
						<label class="col-sm-2 label_style" >特检病理:</label>
						<div class="col-sm-4 ">
							<select class="col-sm-8 input_style" id="samisemergency" disabled="true">
								<option value="0">是</option>
								<option value="1">否</option>
							</select>
						</div>
					</div>
					<div class="form-group" style="margin-bottom: 5px;">
						<label class="col-sm-2 label_style">病人姓名:</label>
						<div class="col-sm-4 ">
							<input class="input_style" type="text" id="sampatientname" name="sampatientname" readonly/>
						</div>
						<label class="col-sm-2 label_style" >送检医生:</label>
						<div class="col-sm-4 ">
							<input  class="input_style" type="text" id="samsenddoctorname" readonly/>
						</div>
					</div>
					<div class="form-group" style="margin-bottom: 5px;">
						<label class="col-sm-2 label_style" >住院号:</label>
						<div class="col-sm-4 ">
							<input class="input_style" type="text" id="sampatientnumber" readonly/>
						</div>
						<label class="col-sm-2 label_style">送检科室:</label>
						<div class="col-sm-4">
							<input  class="input_style" type="text" id="samdeptname" readonly/>
						</div>
					</div>
					<div class="form-group" style="margin-bottom: 5px;">
						<label class="col-sm-2 label_style" >床号:</label>
						<div class="col-sm-4 ">
							<input class="input_style" type="text" id="sampatientbed" readonly/>
						</div>
						<label class="col-sm-2 label_style">送检医院:</label>
						<div class="col-sm-4">
							<input  class="input_style" type="text" id="samsendhospital" readonly/>
						</div>
					</div>
					<div class="form-group" style="margin-bottom: 5px;">
						<label class="col-sm-2 label_style">性&nbsp;别:</label>
						<div class="col-sm-4">
							<select class="col-sm-8 input_style" id="sampatientsex" disabled>
								<option value="1">男</option>
								<option value="2">女</option>
								<option value="3">未知</option>
							</select>
						</div>
						<label class="col-sm-2 label_style">送检材料:</label>
						<div class="col-sm-4">
							<input class="input_style" type="text" id="samsamplename" readonly/>
						</div>
					</div>
					<div class="form-group" style="margin-bottom: 5px;">
						<label class="col-sm-2 label_style">组织个数:</label>
						<div class="col-sm-4">
							<input class="input_style" type="text" id="samfirstn" readonly/>
						</div>
						<label class="col-sm-2 label_style">送检日期:</label>
						<div class="col-sm-4">
							<input class="input_style" type="text" id="samsendtime" readonly/>
						</div>
					</div>
					<div class="form-group" style="margin-bottom: 5px;">
						<label class="col-sm-2 label_style">蜡块数:</label>
						<div class="col-sm-4">
							<input class="input_style" type="text" id="nums" readonly/>
						</div>
						<label class="col-sm-2 label_style">接收日期:</label>
						<div class="col-sm-4">
							<input class="input_style" type="text" id="samreceivertime" readonly/>
						</div>
					</div>
					<div class="form-group" style="margin-bottom: 5px;">
						<label class="col-sm-2 label_style">临床诊断:</label>
						<div class="col-sm-10">
							<input type="text" id="sampatientdignoses" readonly class="col-sm-10 input_style"/>
						</div>
					</div>
				</div>
				<div id="infotab" class="tab-pane fade"  style="display: none;">
					<table id="new1">
					</table>
				</div>
				<div class="form-group"
						<c:if test="${consponsoreduserid != local_userid}"> style="margin-top:5px;"</c:if>
						<c:if test="${consponsoreduserid == local_userid}"> style="display: none" </c:if>
						<c:if test="${conconsultationstate != '0'}"> readonly="readonly" </c:if>
				>
					<label class="col-sm-2 label_style">会诊意见:</label>
					<div class="col-sm-10">
						<textarea id="detadvice" style="height:90px;font-size:12px;width: 90%"></textarea>
					</div>
				</div>
				<div class="form-group"
						<c:if test="${consponsoreduserid == local_userid}"> style="margin-top:5px;"</c:if>
						<c:if test="${consponsoreduserid != local_userid}"> style="display: none" </c:if>
						<c:if test="${conconsultationstate != '0'}"> readonly="readonly" </c:if>
				>
					<label class="col-sm-2 label_style">会诊状态:</label>
					<div class="col-sm-10 input_style">
						<input type="radio" name="hzstates" value="0"/>&nbsp;&nbsp;会诊中&nbsp;&nbsp;
						<input type="radio" name="hzstates" value="1"/>&nbsp;&nbsp;会诊终了&nbsp;&nbsp;
						<input type="radio" name="hzstates" value="2"/>&nbsp;&nbsp;会诊取消&nbsp;&nbsp;
					</div>
				</div>
				<div class="form-group"
						<c:if test="${conconsultationstate != '0'}"> style="display: none"  </c:if>
						<c:if test="${conconsultationstate == '0'}"> style="text-align:center; width:100%;height:100%;margin:0px;"  </c:if>
					 >
					<button type="button" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;" id="savebutton" onclick="saveInfo()">
						<span style="color: white;">保存并刷新</span>
					</button>
				</div>
			</form>
		</div>
		<div class="col-sm-3 rightContent" id="formDialog1">
		</div>
	</div>
</body>