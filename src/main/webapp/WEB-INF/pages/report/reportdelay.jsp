<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<head>
    <title>病理报告查询</title>
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/bootstrap-datetimepicker.min.css'/>" />
	<script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
	<script type="text/javascript" src="../scripts/bootstrap.min.js"></script>
	<script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
	<script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
	<script type="text/javascript" src="<c:url value="/scripts/ace.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/ace-elements.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/bootstrap-tag.min.js"/>"></script>
    <script type="text/javascript" src="../scripts/layer/layer.js"></script>
    <script type="text/javascript" src="../scripts/report/reportdelay.js"></script>
	<script type="text/javascript" src="../scripts/validform/Validform.min.js"></script>
	<script type="text/javascript" src="../scripts/bootstrap-datetimepicker.min.js"></script>
	<script src="<c:url value="/scripts/LodopFuncs.js"/>"></script>
	<style>
		.ui-autocomplete {z-index: 99999999;}
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
	<div id="reportTemplateList" style="display:none;alignment: center">
		<div class="mainContent" style="text-align:center;">
			<select id="reportTemplateSelect">
			</select>
		</div>
	</div>
	<div class="div_1" id="div_1">
		<div class="div_div"><img src="/styles/imagepims/up.png" class="div_img" onclick="upSample()">上一个</div>
		<div class="div_div"><img src="/styles/imagepims/down.png" class="div_img" onclick="downSample()">下一个</div>
		<div class="div_div"><img src="/styles/imagepims/listprint.png" class="div_img" onclick="printCode()">列表打印</div>
	</div>
	<div style="height: 40px">
		<h5 style="float: left;font-size: 14px;margin-bottom: 5px;width: 100%"><strong>&nbsp;<img src="/styles/imagepims/reportdelay.png" class="img_style">&nbsp;延发报告管理</strong></h5>

	</div>
	<div>
		<div class="row widget-main" style="background-color: #E8E8E8;border:1px solid #E0E0E0;height: " id="div_0">
			<div style="margin-top: 10px;">
				<table style="margin-bottom: 5px">
					<span class="input_style">申请FROM:&nbsp;</span>
					<input type="text" class="form_datetime input_style" value="${sevenday}" id="req_bf_time"/>
					<span class="input_style">&nbsp;病&nbsp;理&nbsp;号:&nbsp;&nbsp;</span>
					<input type="text" class="input_style" id="req_code"/>
					<span class="input_style">&nbsp;送检科室:&nbsp;</span>
					<input type="text" class="input_style" id="send_dept"/>
					<span class="input_style">&nbsp;送检医生:&nbsp;</span>
					<input type="text" class="input_style" id="send_doctor"/>
				</table>
				<table style="margin-bottom: 5px">
					<span class="input_style">申&nbsp;请&nbsp;TO:&nbsp;&nbsp;&nbsp;&nbsp;</span>
					<input type="text" class="form_datetime input_style" value="${receivetime}"  id="req_af_time"/>
					<span class="input_style">&nbsp;病人姓名:&nbsp;</span>
					<input type="text" class="input_style" id="patient_name"/>
					<span class="input_style">&nbsp;送检医院:&nbsp;</span>
					<input type="text" class="input_style" id="send_hosptail"/>
					<span class="input_style">&nbsp;申请医生:&nbsp;</span>
					<input type="text" class="input_style" id="piedoctorname"/>
					<span style="float: right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
					<span>
					<button type="button" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;float: right" onclick="searchList()">
						<span style="color: white;">查询</span>
					</button>
				</span>
				</table>
			</div>
		</div>
		<div class="row" id="formDialog">
			<div class="widget-main no-padding">
				<table id="new" class="table-striped">
				</table>
				<div id="pager"></div>
			</div>
			<div class="col-sm-4 leftContent" >
				<form class="form-horizontal" id="sampleForm" >
					<div class="form-group" style="margin-top:10px;margin-bottom: 5px;">
						<label class="col-sm-2 label_style">基本信息:</label>
						<div class="col-sm-10">
							<textarea id="jbxx1" style="font-size:12px;height:70px;width:100%"></textarea>
						</div>
					</div>
					<div class="form-group" style="margin-bottom: 5px;">
						<label class="col-sm-2 label_style">临时诊断:</label>
						<div class="col-sm-10">
							<textarea id="lczd" style="font-size:12px;height:70px;width:100%"></textarea>
						</div>
					</div>
					<div class="form-group" style="margin-bottom: 5px;">
						<label class="col-sm-2 label_style">病理诊断:</label>
						<div class="col-sm-10">
							<textarea id="blzd" style="font-size:12px;height:70px;width:100%"></textarea>
						</div>
					</div>
				</form>
			</div>
			<div class="col-sm-8 rightContent" id="ctabs">
				<ul id="tabPanel" class="nav nav-tabs">
					<li class="active">
						<a href="#infotab" data-toggle="tab">
							取材信息
						</a>
					</li>
				</ul>
				<div id="infotab" class="tab-pane fade"  style="display: none;">
					<table id="new1">
					</table>
				</div>
				<div id="tabs-MYZH" class="tab-pane fade"  style="display: none">
					<div><table id="MYZHItem"></table></div>
				</div>
				<div id="tabs-TSRS"  class="tab-pane fade"  style="display: none">
					<div><table id="TSRSItem"></table></div>
				</div>
				<div id="tabs-FZBL" class="tab-pane fade"  style="display: none">
					<div><table id="FZBLItem"></table></div>
				</div>
			</div>
		</div>
	</div>
</body>