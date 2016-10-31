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
    <script type="text/javascript" src="../scripts/pathologysample/pieces.js"></script>
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
<div class="row" id="div_1">
		<div>
			<button type="button" class="btn-sm btn-danger" title="上一个" onclick="upSample()">
				<i class="ace-icon fa fa-times bigger-110"></i>
				上一个
			</button>
			<button type="button" class="btn-sm btn-danger" title="下一个" onclick="downSample()">
				<i class="ace-icon fa fa-fire bigger-110"></i>
				下一个
			</button>
			<button type="button" class="btn-sm btn-info" title="打印" onclick="print()">
				<i class="ace-icon fa fa-print bigger-110"></i>
				打印
			</button>
			<button type="button" class="btn-sm btn-danger" title="取材" id="saveButton" onclick="saveInfo(1)">
				<i class="ace-icon fa fa-fire bigger-110"></i>
				取材
			</button>
			<button type="button" class="btn-sm btn-info" title="计费调整" onclick="hisChange()">
				<i class="ace-icon fa fa-print bigger-110"></i>
				计费调整
			</button>
			<button type="button" class="btn-sm  btn-success" title="图像采集" onclick="imgCollect()">
				<i class="ace-icon fa fa-pencil-square bigger-110"></i>
				图像采集
			</button>
			<button type="button" class="btn-sm btn-danger" title="列表打印" onclick="listPrint()">
				<i class="ace-icon fa fa-times bigger-110"></i>
				列表打印
			</button>
		</div>
</div>
<div class="row">
	<div>
		<h5 style="float: left;width: 33%"><strong>&nbsp;工作列表</strong></h5>
		<h5 style="float: left;width: 50%"><strong>&nbsp;取材管理</strong>
			<span>&nbsp;&nbsp;取材医生:</span>
			<select id="doctor_id" style="width: 20%">
				<option value="1">张三</option>
				<option value="2">李四</option>
				<option value="3">王五</option>
			</select>
			<span>&nbsp;&nbsp;录入人员:</span>
			<select id="input_user"style="width: 20%">
				<option value="1">张三</option>
				<option value="2">李四</option>
				<option value="3">王五</option>
			</select>
		</h5>
		<h5 style="float: left;width: 17%"><strong>&nbsp;图像采集</strong></h5>
	</div>
	<div class="widget-box widget-color-green"></div>
	<div class="col-sm-4 leftContent" id="div_2">
		<div style="background-color: #E8E8E8">
		<table>
			<span>&nbsp;登记年月:&nbsp;</span>
			<input type="text" class="form_datetime" value="${sevenday}" id="req_bf_time"/>
			<span>-</span>
			<input type="text" class="form_datetime" value="${receivetime}"  id="req_af_time"/>
		</table>
		<table>
			<span >&nbsp;&nbsp;病 理 号:&nbsp;</span>
			<input type="text" id="send_dept"/>
		</table>
		<table>
			<span>&nbsp;病人姓名:&nbsp;</span>
			<input type="text" id="patient_name"/>
		</table>
		<table>
			<span>&nbsp;取材状态:</span>
			<input type="hidden" id="req_sts"/>&nbsp;
			<input type="radio" name="req_name" value="1"/>&nbsp;未取&nbsp;
			<input type="radio" name="req_name" value="2"/>&nbsp;已取&nbsp;
		</table>
		<table>
			<span>&nbsp;补取医嘱:</span>&nbsp;&nbsp;
			<input type="checkbox" id="send_doctor" value="1"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<span>
				<button type="button" class="btn-sm btn-success" title="查询信息" onclick="searchList()">
					<i class="ace-icon fa fa-print bigger-110"></i>
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
	<div class="col-sm-6 rightContent" id="formDialog" style="border-style: solid;border-color: #0A246A;border-width: 1px">
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
			<label class="col-sm-2 control-label no-padding-right" >标本状态:</label>
			<div class="col-sm-4 ">
				<input type="checkbox" id="samissamplingall" value="1"/>全取
				<input type="checkbox" id="samisdecacified" value="1"/>脱钙
			</div>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
			<label class="col-sm-2 control-label no-padding-right" for="sampatientsex">性&nbsp;别:</label>
			<div class="col-sm-4">
				<select class="col-sm-10" id="sampatientsex" disabled>
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
			<label class="control-label" style="text-align: left;">&nbsp;&nbsp;&nbsp;&nbsp;材块信息</label>
			<button type="button" class="btn btn-sm btn-primary " title="追加行" id="addrow1" onclick="addRow()">
				<i class="ace-icon fa fa-fire bigger-110"></i>
				追加行
			</button>
			<button type="button" class="btn btn-sm btn-danger" title="删除行" onclick="delRow()">
				<i class="ace-icon fa fa-times bigger-110"></i>
				删除行
			</button>
			<button type="button" class="btn btn-sm btn-danger" title="保存" onclick="saveInfo(0)">
				<i class="ace-icon fa fa-fire bigger-110"></i>
				保存
			</button>
		</div>
		<div class="form-group " style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
			<table id="new1" class="table table-striped table-bordered table-hover">
			</table>
		</div>
	</form>
</div>
	<div class="col-sm-2 rightContent" style="border-style: solid;border-color: #0A246A;border-width: 1px">
	</div>
</div>
</body>