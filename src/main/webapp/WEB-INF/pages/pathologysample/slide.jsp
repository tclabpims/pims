<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="ElectronicApplyManage.title"/></title>
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
<div class="row widget-main" id="div_1">
		<div>
			<button type="button" class="btn btn-sm btn-danger" title="切片" id="saveButton" onclick="saveInfo(3)">
				<i class="ace-icon fa fa-fire bigger-110"></i>
				切片
			</button>
			<button type="button" class="btn btn-sm btn-danger" title="取消切片" id="resetbutton" onclick="saveInfo(2)">
				<i class="ace-icon fa fa-fire bigger-110"></i>
				取消切片
			</button>
		</div>
</div>
<div class="row">
	<div class="col-sm-4 leftContent" id="div_2">
		<div class="input-group" style="float: left;">
			<span class="input-group-addon">包埋年月</span>
			<input type="hidden" id="local_name" value="${local_username}"/>
			<input type="text" class="form_datetime form-control" placeholder="" value="${sevenday}" id="req_bf_time"/>
			<span class="input-group-addon">-</span>
			<input type="text" class="form_datetime form-control" placeholder="" value="${receivetime}"  id="req_af_time"/>
		</div>
		<div class="input-group" style="float: left;">
			<span class="input-group-addon">病理号</span>
			<input type="text" class="form-control" value="" id="send_dept"/>
			<span class="input-group-addon">病人姓名</span>
			<input type="text" class="form-control" value="" id="patient_name"/>
		</div>
		<div class="input-group" style="float: left;">
			<span class="input-group-addon">切片状态</span>
			<select class="form-control col-sm-8" id="req_sts" onchange="searchSts()">
				<option value="0">待切片</option>
				<option value="1">已切片</option>
			</select>
		</div>
		<div class="input-group" style="float: left;">
			<span class="input-group-addon">打印状态</span>
			<select class="form-control col-sm-8" id="send_hosptail">
				<option value="" >全  部</option>
				<option value="0">待打印</option>
				<option value="1">已打印</option>
			</select>
		</div>
		<div class="input-group" style="float: left;">
			<span class="input-group-addon ">内部医嘱</span>
			<input type="checkbox" class="form-control" id="send_doctor" value="1"/>
			<span class="input-group-addon ">预留白片</span>
			<input type="checkbox" class="form-control" id="req_code" value="1"/>
			<span class="input-group-btn">
				<button type="button" class="btn btn-sm btn-success" title="查询信息" onclick="searchList()">
					<i class="ace-icon fa fa-print bigger-110"></i>
					查询
				</button>
			</span>
		</div>
		<div class="widget-main no-padding">
			<table id="new" class="table table-striped table-bordered table-hover">
			</table>
			<div id="pager"></div>
		</div>
	</div>
	<div class="col-sm-8 rightContent" id="formDialog" style="border-style: solid;border-color: #0A246A;border-width: 1px">
	<form class="form-horizontal"  action="#" method="post" id="sampleForm" >
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
					<option value="1">男</option>
					<option value="2">女</option>
					<option value="3">未知</option>
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
		<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
			<label class="control-label" style="text-align: left;">&nbsp;&nbsp;&nbsp;&nbsp;玻片列表</label>
		</div>
		<div class="form-group " style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
			<table id="new1" class="table table-striped table-bordered table-hover">
			</table>
		</div>
	</form>
</div>
</div>
</body>