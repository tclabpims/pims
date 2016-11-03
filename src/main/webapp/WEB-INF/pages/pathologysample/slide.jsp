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
</head>
<body>
<div class="row" id="div_1">
		<div>
			<button type="button" class="btn-sm btn-info" id="saveButton" onclick="saveInfo(3)">
				切片
			</button>
			<button type="button" class="btn-sm btn-info" id="resetbutton" onclick="saveInfo(2)">
				取消切片
			</button>
			<button type="button" class="btn-sm btn-info" onclick="printCode()">
				打印
			</button>
		</div>
</div>
<div class="row">
	<div>
		<h5 style="float: left;width: 34%"><strong>&nbsp;工作列表</strong></h5>
		<h5 style="float: left;width: 66%"><strong>&nbsp;切片管理</strong></h5>
	</div>
	<div class="widget-box widget-color-green"></div>
	<div class="col-sm-4 leftContent" id="div_2">
		<div id="search_div_1" style="background-color: #E8E8E8">
			<table>
				<span>&nbsp;包埋年月:&nbsp;</span>
				<input type="hidden" id="local_name" value="${local_username}"/>
				<input type="text" class="form_datetime" value="${sevenday}" id="req_bf_time"/>
				<span>-</span>
				<input type="text" class="form_datetime" value="${receivetime}"  id="req_af_time"/>
			</table>
			<table>
				<span>&nbsp;病理编号:&nbsp;</span>
				<input type="text" id="send_dept"/>
			</table>
			<table>
				<span>&nbsp;病人姓名:&nbsp;</span>
				<input type="text" id="patient_name"/>
			</table>
			<table>
				<span>&nbsp;切片状态:&nbsp;</span>
				<input type="radio" name="req_sts" value="0" checked onclick="searchSts('0')">&nbsp;待切片&nbsp;
				<input type="radio" name="req_sts" value="1" onclick="searchSts('1')">&nbsp;已切片&nbsp;
			</table>
			<table>
				<span>&nbsp;打印状态:&nbsp;</span>
				<input type="hidden" id="send_hosptail">
				<input type="checkbox" name="send_hosptail1" value="0">&nbsp;待打印&nbsp;
				<input type="checkbox" name="send_hosptail2" value="1">&nbsp;已打印&nbsp;
			</table>
			<table>
				<span>&nbsp;内部医嘱:&nbsp;</span>
				<input type="checkbox" id="send_doctor" value="1"/>
			</table>
			<table>
				<span>&nbsp;预留白片:&nbsp;</span>
				<input type="checkbox"  id="req_code" value="1"/>
				<span>
					<button type="button" class="btn-sm btn-info" onclick="searchList()">
						查询
					</button>
				</span>
			</table>
		</div>
		<div class="widget-main no-padding">
			<table id="new" class="table table-striped table-bordered table-hover">
			</table>
			<div id="pager"></div>
		</div>
	</div>
	<div class="col-sm-8 rightContent">
	<form class="form-horizontal"  action="#" method="post" id="sampleForm" >
		<div style="background-color: #E8E8E8">
			<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
				<label class="col-sm-2 control-label no-padding-left" for="sampathologycode">病理编号:</label>
				<div class="col-sm-4">
					<input type="hidden" id="sampleid"><!--标本id-->
					<input type="hidden" id="samcustomerid"><!--客户id-->
					<input type="hidden" id="samsamplestatus"><!--标本状态-->
					<input type="text" id="sampathologycode" name="sampathologycode" readonly/>
				</div>
				<label class="col-sm-2 control-label no-padding-right" >送检医生:</label>
				<div class="col-sm-4 ">
					<input  type="text" id="samsenddoctorname" readonly/>
				</div>
			</div>
			<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
				<label class="col-sm-2 control-label no-padding-right" for="sampatientname">病人姓名:</label>
				<div class="col-sm-4 ">
					<input type="text" id="sampatientname" name="sampatientname" readonly/>
				</div>
				<label class="col-sm-2 control-label no-padding-right">送检科室:</label>
				<div class="col-sm-4">
					<input  type="text" id="samdeptname" readonly/>
				</div>
			</div>
			<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
				<label class="col-sm-2 control-label no-padding-right" >住院号:</label>
				<div class="col-sm-4 ">
					<input type="text" id="sampatientnumber" readonly/>
				</div>
				<label class="col-sm-2 control-label no-padding-right" for="samsamplename">送检材料:</label>
				<div class="col-sm-4">
					<input type="text" id="samsamplename" readonly/>
				</div>
			</div>
			<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
				<label class="col-sm-2 control-label no-padding-right" >床号:</label>
				<div class="col-sm-4 ">
					<input type="text" id="sampatientbed" readonly/>
				</div>
				<label class="col-sm-2 control-label no-padding-right" for="sampatientsex">性&nbsp;别:</label>
				<div class="col-sm-4">
					<select class="col-sm-7" id="sampatientsex" disabled>
						<option value="0">男</option>
						<option value="1">女</option>
						<option value="2">未知</option>
					</select>
				</div>
			</div>
			<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
				<label class="col-sm-2 control-label no-padding-right" for="sampatientdignoses">临床诊断:</label>
				<div class="col-sm-10">
					<input type="text" id="sampatientdignoses" readonly class="col-sm-12"/>
				</div>
			</div>
			<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
			<label class="col-sm-2 control-label no-padding-right" for="samthirdv">手术所见:</label>
			<div class="col-sm-10">
				<input type="text" id="samthirdv" readonly class="col-sm-12"/>
			</div>
		</div>
		</div>
		<h5 style="float: left;"><strong>&nbsp;玻片列表</strong></h5>
		<div class="form-group " style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
			<table id="new1" class="table table-striped table-bordered table-hover">
			</table>
		</div>
	</form>
</div>
</div>
</body>