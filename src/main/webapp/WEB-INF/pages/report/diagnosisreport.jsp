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
    <script type="text/javascript" src="../scripts/report/diagnosisreport.js"></script>
	<script type="text/javascript" src="../scripts/validform/Validform.min.js"></script>
	<script type="text/javascript" src="../scripts/bootstrap-datetimepicker.min.js"></script>
	<script src="<c:url value="/scripts/LodopFuncs.js"/>"></script>
	<style>
		.ui-autocomplete {z-index: 99999999;}
		/*.div_div {float:left;margin:20px 35px 11px 8px;text-align:center;color: #808080;font-size: 12px;  }*/
		/*.div_img{cursor:pointer;display: block;margin-bottom:11px;}*/
		/*.div_1{background-color: #F9F9F9;height: 106px;border:1px solid #E0E0E0}*/
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
		<button type="button" class="btn btn-sm btn-primary"  class="div_img" onclick="upSample()">
			<i class="ace-icon fa fa-arrow-left"></i>
			上一个
		</button>
		<button type="button" class="btn btn-sm btn-info" class="div_img" onclick="downSample()">
			<i class="ace-icon fa fa-arrow-right"></i>
			下一个
		</button>
		<button type="button" class="btn btn-sm btn-warning" class="div_img" onclick="printSet()">
			<i class="ace-icon fa fa-cog"></i>
			列表打印设置
		</button>
		<button type="button" class="btn btn-sm btn-success" class="div_img" onclick="printCode()">
			<i class="ace-icon fa fa-file-text-o"></i>
			列表打印
		</button>
		<button type="button" class="btn btn-sm btn-info" class="div_img" onclick="reportExcel()">
			<i class="ace-icon fa fa-share"></i>
			导出
		</button>
	</div>
	<div>
		<%--<div>--%>
			<%--<h5 style="float: left;width: 25%;font-size: 14px;"><strong>&nbsp;<img src="/styles/imagepims/reportsearch.png" class="img_style">&nbsp;报告查询条件</strong></h5>--%>
			<%--<h5 style="float: left;width: 75%;font-size: 14px;margin-bottom: 12px"><strong>&nbsp;<img src="/styles/imagepims/reportresult.png" class="img_style">&nbsp;报告查询结果</strong></h5>--%>
		<%--</div>--%>
		<div class="col-sm-3 leftContent" id="div_2">
			<div class="widget-box widget-color-green ui-sortable-handle">
				<div class="widget-header">
					<h6 class="widget-title">报告查询条件</h6>
					<div class="widget-toolbar">
						<a href="#" data-action="collapse">
							<i class="ace-icon fa fa-chevron-up">隐藏</i>
						</a>
					</div>
				</div>
				<div class="widget-body" style="display:block">
					<div id="search_div_1" style="background-color: #F9F9F9;height:470px;border:1px solid #E0E0E0;">
				<div style="margin-top:10px;">
					<div style="margin-bottom: 5px;">
						<span class="col-sm-4 input_style">&nbsp;送检FROM:&nbsp;</span>
						<input type="text" class="col-sm-6 form_datetime input_style" value="${sevenday}" id="req_bf_time"/>
						<div class="col-sm-2 ">
							<button type="button" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;float: right" onclick="searchList()">
								<span style="color: white;">查询</span>
							</button>
						</div>
					</div>
					<div style="margin-bottom: 5px;">
						<span class="col-sm-4 input_style">&nbsp;送检TO:&nbsp;</span>
						<input type="text" class="col-sm-6 form_datetime input_style" value="${receivetime}"  id="req_af_time"/>
					</div>
					<div style="margin-bottom: 5px;">
						<span class="col-sm-4 input_style">&nbsp;病种类别:&nbsp;</span>
						<select id="logyid" class="col-sm-6 input_style">
							<%out.println((String) request.getAttribute("logyids"));%>
						</select>
					</div>
					<div style="margin-bottom: 5px;">
						<span class="col-sm-4 input_style">&nbsp;病理号码:&nbsp;</span>
						<input type="text" id="req_code" class="col-sm-6 input_style"/>
					</div>
					<div style="margin-bottom: 5px;">
						<span class="col-sm-4 input_style">&nbsp;病人姓名:&nbsp;</span>
						<input type="text" id="patient_name" class="col-sm-6 input_style"/>
					</div>
					<div style="margin-bottom: 5px;">
						<span class="col-sm-4 input_style">&nbsp;住院号:&nbsp;</span>
						<input type="text" id="sampatientnumber" class="col-sm-6 input_style"/>
					</div>
					<div style="margin-bottom: 5px;">
						<span class="col-sm-4 input_style">&nbsp;床号:&nbsp;</span>
						<input type="text" id="sampatientbed" class="col-sm-6 input_style"/>
					</div>
					<div style="margin-bottom: 5px;">
						<span class="col-sm-4 input_style">&nbsp;性别:&nbsp;</span>
						<select class="col-sm-6  input_style" id="sampatientsex">
							<option value="">全部</option>
							<option value="1">男</option>
							<option value="2">女</option>
							<option value="3">未知</option>
						</select>
					</div>
					<div style="margin-bottom: 5px">
						<span class="col-sm-4 input_style">&nbsp;送检医生:&nbsp;</span>
						<input type="text" id="send_doctor" class="col-sm-6 input_style"/>
					</div>
					<div style="margin-bottom: 5px">
						<span class="col-sm-4 input_style">&nbsp;送检科室:&nbsp;</span>
						<input type="text" id="send_dept" class="col-sm-6 input_style"/>
					</div>
					<div style="margin-bottom: 5px">
						<span class="col-sm-4 input_style">&nbsp;送检医院:&nbsp;</span>
						<input type="text" id="send_hosptail" class="col-sm-6 input_style"/>
					</div>
					<div style="margin-bottom: 5px">
						<span class="col-sm-4 input_style">&nbsp;取材医生:&nbsp;</span>
						<input type="text" id="piedoctorname" class="col-sm-6 input_style"/>
					</div>
					<div style="margin-bottom: 5px">
						<span class="col-sm-4 input_style">&nbsp;切片医生:&nbsp;</span>
						<input type="text" id="parsectioneddoctor" class="col-sm-6 input_style"/>
					</div>
					<div style="margin-bottom: 5px">
						<span class="col-sm-4 input_style">&nbsp;诊断医生:&nbsp;</span>
						<input type="text" id="saminitiallyusername" class="col-sm-6 input_style"/>
					</div>
					<div style="margin-bottom: 5px">
						<span class="col-sm-4 input_style">&nbsp;免疫组化:&nbsp;</span>
						<select id="myzh" class="col-sm-6 input_style">
							<option value="">全部</option>
							<option value="0">有</option>
							<option value="1">无</option>
						</select>
					</div>
					<div style="margin-bottom: 5px">
						<span class="col-sm-4 input_style">&nbsp;特殊染色:&nbsp;</span>
						<select id="tsrs" class="col-sm-6 input_style">
							<option value="">全部</option>
							<option value="0">有</option>
							<option value="1">无</option>
						</select>
					</div>
					<div style="margin-bottom: 5px">
						<span class="col-sm-4 input_style">&nbsp;分子病理:&nbsp;</span>
						<select id="fzbl" class="col-sm-6 input_style">
							<option value="">全部</option>
							<option value="0">有</option>
							<option value="1">无</option>
						</select>
					</div>
					<div style="margin-bottom: 5px">
						<span class="col-sm-4 input_style">&nbsp;病理诊断:&nbsp;</span>
						<input type="text" id="blzd" class="col-sm-6 input_style"/>
					</div>
					<div style="margin-bottom: 5px;">
							<span class="col-sm-4 input_style">&nbsp;取材部位:&nbsp;</span>&nbsp;&nbsp;
							<input type="text" id="qcbw" class="col-sm-6 input_style"/>
							<span style="float: right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>

					</div>
				</div>
			</div>
				</div>
			</div>
		</div>
		<div class="col-sm-9 rightContent" id="formDialog">
			<div class="widget-box widget-color-green ui-sortable-handle">
			<div class="widget-header">
				<h6 class="widget-title">报告查询条件</h6>
				<div class="widget-toolbar">
					<a href="#" data-action="collapse">
						<i class="ace-icon fa fa-chevron-up">隐藏</i>
					</a>
				</div>
			</div>
			<div  style="display: block;">
				<div class="widget-main no-padding">
					<table id="new" class="table-striped">
					</table>
					<div id="pager"></div>
				</div>
			</div>
			</div>
			<ul id="tabs" class="nav nav-tabs">
				<li class="active">
					<a href="#maintab" data-toggle="tab">
						诊断信息
					</a>
				</li>
				<li>
					<a href="#infotab" data-toggle="tab">
						取材信息
					</a>
				</li>
			</ul>
			<div id="maintab" class="tab-pane fade in active"  style="display: none;">
				<form class="form-horizontal" action="#" method="post" id="sampleForm" >
					<div class="form-group" style="margin-top:10px;margin-bottom: 5px;">
						<label class="col-sm-1 label_style">基本信息:</label>
						<div class="col-sm-11">
							<textarea id="jbxx1" style="height: 50px;width: 90%"></textarea>
						</div>
					</div>
					<div class="form-group" style="margin-bottom: 5px;">
						<label class="col-sm-1 label_style" >病理诊断:</label>
						<div class="col-sm-5 ">
							<textarea id="blzd1" style="height: 50px;width: 80%"></textarea>
						</div>
						<label class="col-sm-1 label_style" >免疫组化:</label>
						<div class="col-sm-5 ">
							<textarea id="myzh1" style="height: 50px;width: 80%"></textarea>
						</div>
					</div>
					<div class="form-group" style="margin-bottom: 5px;">
						<label class="col-sm-1 label_style" >特殊染色:</label>
						<div class="col-sm-5 ">
							<textarea id="tsrs1" style="height: 50px;width: 80%"></textarea>
						</div>
						<label class="col-sm-1 label_style" >分子病理:</label>
						<div class="col-sm-5 ">
							<textarea id="fzbl1" style="height: 50px;width: 80%"></textarea>
						</div>
					</div>
				</form>
			</div>
			<div id="infotab" class="tab-pane fade"  style="display: none;">
				<table id="new1">
				</table>
			</div>
		</div>
	</div>
</body>