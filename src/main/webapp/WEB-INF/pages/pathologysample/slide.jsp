<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="menu.sectionManage"/></title>
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
    <script type="text/javascript" src="../scripts/pathologysample/slide.js"></script>
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
<body style="font-family:'Microsoft YaHei',微软雅黑,'MicrosoftJhengHei'!important;">
	<div class="div_1" id="div_1">
		<div class="div_div"><img src="/styles/imagepims/slide.png" class="div_img"   id="saveButton">切片</div>
		<div class="div_div"><img src="/styles/imagepims/cancleslide.png" class="div_img"  id="resetbutton">取消切片</div>
		<div class="div_div"><img src="/styles/imagepims/print.png" class="div_img" onclick="printCode()" >打印</div>
	</div>
	<h5 style="float: left;font-size: 14px;width: 34%"><strong>&nbsp;<img src="/styles/imagepims/worklist.png">&nbsp;&nbsp;工作列表</strong></h5>
	<h5 style="font-size: 14px;margin-bottom: 12px"><strong>&nbsp;<img src="/styles/imagepims/slidemarage.png">&nbsp;&nbsp;切片管理</strong></h5>
	<div>
		<div class="col-sm-4 leftContent" id="div_2">
			<div id="search_div_1" style="background-color: #F9F9F9;height: 180px;border:1px solid #E0E0E0;">
				<div style="margin-top:10px;">
					<table style="margin-bottom: 5px;">
						<span class="input_style">&nbsp;包埋年月:&nbsp;</span>
						<input type="hidden" id="req_sts" value="0">
						<input type="hidden" id="local_name" value="${local_username}"/>
						<input type="text" class="form_datetime input_style" value="${sevenday}" id="req_bf_time"/>
						<span class="input_style">-</span>
						<input type="text" class="form_datetime input_style" value="${receivetime}"  id="req_af_time"/>
					</table>
					<table style="margin-bottom: 5px;">
						<span class="input_style">&nbsp;病理编号:&nbsp;</span>
						<input type="text" id="send_dept" class="input_style"/>
					</table>
					<table style="margin-bottom: 5px;">
						<span class="input_style">&nbsp;病人姓名:&nbsp;</span>
						<input type="text" id="patient_name" class="input_style"/>
					</table>
					<%--<table  style="margin-bottom: 5px;">--%>
						<%--<span class="input_style">&nbsp;切片状态:&nbsp;</span>--%>
						<%--<input type="radio" name="req_sts" value="0" checked onclick="searchSts('0')">&nbsp;待切片&nbsp;--%>
						<%--<input type="radio" name="req_sts" value="1" onclick="searchSts('1')">&nbsp;已切片&nbsp;--%>
					<%--</table>--%>
					<table style="margin-bottom: 5px;">
						<div class="input_style">
							<span>&nbsp;打印状态:&nbsp;</span>
							<input type="hidden" id="send_hosptail">
							<input type="checkbox" name="send_hosptail1" value="0">&nbsp;待打印&nbsp;
							<input type="checkbox" name="send_hosptail2" value="1">&nbsp;已打印&nbsp;
						</div>
					</table>
					<table style="margin-bottom: 5px;">
						<div class="input_style">
							<span class="input_style">&nbsp;内部医嘱:&nbsp;</span>
							<input type="checkbox" id="send_doctor" value="1"/>
						</div>
					</table>
					<table style="margin-bottom: 5px;">
						<div class="input_style">
							<span class="input_style">&nbsp;预留白片:&nbsp;</span>
							<input type="checkbox"  id="req_code" value="1"/>
							<span style="float: right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
							<span>
								<button type="button" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;float: right" onclick="searchList()">
									<span style="color: white;">查询</span>
								</button>
							</span>
						</div>
					</table>
				</div>
			</div>
			<ul id="tabss" class="nav nav-tabs">
				<li class="active">
					<a href="0" data-toggle="tab">
						待切片
					</a>
				</li>
				<li>
					<a href="1" data-toggle="tab">
						已切片
					</a>
				</li>
			</ul>
			<div class="widget-main no-padding">
				<table id="new" class="table-striped">
				</table>
				<div id="pager"></div>
			</div>
		</div>
		<div class="col-sm-8 rightContent">
			<form class="form-horizontal"  action="#" method="post" id="sampleForm" >
				<div style="background-color: #F9F9F9;height: 206px;border:1px solid #E0E0E0;">
					<div class="form-group" style="margin-top:10px;margin-bottom: 5px;">
						<label class="col-sm-2 label_style" for="sampathologycode">病理编号:</label>
						<div class="col-sm-4">
							<input type="hidden" id="sampleid"><!--标本id-->
							<input type="hidden" id="samcustomerid"><!--客户id-->
							<input type="hidden" id="samsamplestatus"><!--标本状态-->
							<input class="input_style" type="text" id="sampathologycode" name="sampathologycode" readonly/>
						</div>
						<label class="col-sm-2 label_style" >送检医生:</label>
						<div class="col-sm-4 ">
							<input  class="input_style" type="text" id="samsenddoctorname" readonly/>
						</div>
					</div>
					<div class="form-group" style="margin-bottom: 5px;">
						<label class="col-sm-2 label_style" for="sampatientname">病人姓名:</label>
						<div class="col-sm-4 ">
							<input class="input_style" type="text" id="sampatientname" name="sampatientname" readonly/>
						</div>
						<label class="col-sm-2 label_style">送检科室:</label>
						<div class="col-sm-4">
							<input  class="input_style" type="text" id="samdeptname" readonly/>
						</div>
					</div>
					<div class="form-group" style="margin-bottom: 5px;">
						<label class="col-sm-2 label_style" >住院号:</label>
						<div class="col-sm-4 ">
							<input class="input_style" type="text" id="sampatientnumber" readonly/>
						</div>
						<label class="col-sm-2 label_style" for="samsamplename">送检材料:</label>
						<div class="col-sm-4">
							<input class="input_style" type="text" id="samsamplename" readonly/>
						</div>
					</div>
					<div class="form-group" style="margin-bottom: 5px;">
						<label class="col-sm-2 label_style" >床号:</label>
						<div class="col-sm-4 ">
							<input class="input_style" type="text" id="sampatientbed" readonly/>
						</div>
						<label class="col-sm-2 label_style" for="sampatientsex">性&nbsp;别:</label>
						<div class="col-sm-4">
							<select class="col-sm-6 input_style" id="sampatientsex" disabled>
								<option value="0">男</option>
								<option value="1">女</option>
								<option value="2">未知</option>
							</select>
						</div>
					</div>
					<div class="form-group"  style="margin-bottom: 5px;">
						<label class="col-sm-2 label_style" for="sampatientdignoses">临床诊断:</label>
						<div class="col-sm-10">
							<input type="text" id="sampatientdignoses" readonly class="col-sm-10 input_style"/>
						</div>
					</div>
					<div class="form-group" style="margin-bottom: 5px;">
						<label class="col-sm-2 label_style" for="samthirdv">手术所见:</label>
						<div class="col-sm-10">
							<input type="text" id="samthirdv" readonly class="col-sm-10 input_style"/>
						</div>
					</div>
				</div>
				<div style="margin-top: 14px;height:1px;background-color: #108CCF;"></div>
				<h5><strong style="font-size: 14px;">&nbsp;玻片列表</strong></h5>
				<div class="widget-main no-padding">
					<table id="new1" class="table-striped">
					</table>
				</div>
			</form>
		</div>
	</div>
</body>