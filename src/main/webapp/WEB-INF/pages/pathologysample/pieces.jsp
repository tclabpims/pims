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
<div class="row widget-main" id="div_1">
		<div>
			<button type="button" class="btn btn-sm btn-danger" title="上一个" onclick="upSample()">
				<i class="ace-icon fa fa-times bigger-110"></i>
				上一个
			</button>
			<button type="button" class="btn btn-sm btn-danger" title="下一个" onclick="downSample()">
				<i class="ace-icon fa fa-fire bigger-110"></i>
				下一个
			</button>
			<button type="button" class="btn btn-sm btn-info" title="打印" onclick="print()">
				<i class="ace-icon fa fa-print bigger-110"></i>
				打印
			</button>
			<button type="button" class="btn btn-sm btn-danger" title="取材" id="saveButton" onclick="saveInfo(1)">
				<i class="ace-icon fa fa-fire bigger-110"></i>
				取材
			</button>
			<button type="button" class="btn btn-sm btn-info" title="计费调整" onclick="hisChange()">
				<i class="ace-icon fa fa-print bigger-110"></i>
				计费调整
			</button>
			<button type="button" class="btn btn-sm  btn-success" title="图像采集" onclick="imgCollect()">
				<i class="ace-icon fa fa-pencil-square bigger-110"></i>
				图像采集
			</button>
			<button type="button" class="btn btn-sm btn-danger" title="列表打印" onclick="listPrint()">
				<i class="ace-icon fa fa-times bigger-110"></i>
				列表打印
			</button>
		</div>
</div>
<div class="row">
	<div class="col-sm-4 leftContent" id="div_2">
		<div class="input-group" style="float: left;">
			<span class="input-group-addon">登记年月</span>
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
			<span class="input-group-addon">取材状态</span>
			<select class="form-control col-sm-8" id="req_sts">
				<option value="">全  部</option>
				<option value="1">未取</option>
				<option value="2">已取</option>
			</select>
		</div>
		<div class="input-group" style="float: left;">
			<span class="input-group-addon ">补取医嘱</span>
			<input type="checkbox" class="form-control" id="send_doctor" value="1"/>
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
	<div class="col-sm-6 rightContent" id="formDialog" style="border-style: solid;border-color: #0A246A;border-width: 1px">
	<form class="form-horizontal"  action="#" method="post" id="sampleForm" >
		<div class="form-group" style="margin-right:0px;margin-left:0px;">
			<label class="col-sm-2 control-label no-padding-right" >取材医生:</label>
			<div class="col-sm-4">
				<select class="col-sm-10" id="doctor_id">
					<option value="1" <c:if test="${logylibid == 1 }"> selected</c:if>>张三</option>
					<option value="2" <c:if test="${logylibid == 2 }"> selected</c:if>>李四</option>
					<option value="3" <c:if test="${logylibid == 3 }"> selected</c:if>>王五</option>
				</select>
			</div>
			<label class="col-sm-2 control-label no-padding-right" >录入人员:</label>
			<div class="col-sm-4">
				<select class="col-sm-10" id="input_user">
					<option value="1" <c:if test="${logylibid == 1 }"> selected</c:if>>张三</option>
					<option value="2" <c:if test="${logylibid == 2 }"> selected</c:if>>李四</option>
					<option value="3" <c:if test="${logylibid == 3 }"> selected</c:if>>王五</option>
				</select>
			</div>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;">
			<label class="col-sm-2 control-label no-padding-left" for="sampathologycode">病理编号:</label>
			<div class="col-sm-4">
				<input type="hidden" id="sampleid"><!--标本id-->
				<input type="hidden" id="samcustomerid"><!--客户id-->
				<input type="hidden" id="samsamplestatus"><!--标本状态-->
				<input type="text" id="sampathologycode" name="sampathologycode" readonly/>
			</div>
			<label class="col-sm-2 control-label no-padding-right" >送检医生:</label>
			<div class="col-sm-4 ">
				<select class="col-sm-10" id="samsenddoctorid" disabled="true">
					<option value="1">张三</option>
					<option value="2">李四</option>
					<option value="3">王五</option>
					<option value="4">赵六</option>
				</select>
			</div>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;">
			<label class="col-sm-2 control-label no-padding-right" for="sampatientname">病人姓名:</label>
			<div class="col-sm-4 ">
				<input type="text" id="sampatientname" name="sampatientname" readonly/>
			</div>
			<label class="col-sm-2 control-label no-padding-right" for="samdeptcode">送检科室:</label>
			<div class="col-sm-4">
				<select class="col-sm-10" id="samdeptcode" disabled="true">
					<option value="1">1科室</option>
					<option value="2">2科室</option>
					<option value="3">3科室</option>
				</select>
			</div>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;">
			<label class="col-sm-2 control-label no-padding-right" >住院号:</label>
			<div class="col-sm-4 ">
				<input type="text" id="sampatientnumber" readonly/>
			</div>
			<label class="col-sm-2 control-label no-padding-right" for="samsamplename">送检材料:</label>
			<div class="col-sm-4">
				<input type="text" id="samsamplename" readonly/>
			</div>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;">
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
		<div class="form-group" style="margin-right:0px;margin-left:0px;">
			<label class="col-sm-2 control-label no-padding-right" for="sampatientsex">性&nbsp;别:</label>
			<div class="col-sm-4">
				<select class="col-sm-10" id="sampatientsex" disabled>
					<option value="1">男</option>
					<option value="2">女</option>
					<option value="3">未知</option>
				</select>
			</div>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;">
			<label class="col-sm-2 control-label no-padding-right" for="sampatientdignoses">临床诊断:</label>
			<div class="col-sm-10">
				<input type="text" id="sampatientdignoses" readonly class="col-sm-12"/>
			</div>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;">
			<label class="col-sm-2 control-label no-padding-right" for="samthirdv">手术所见:</label>
			<div class="col-sm-10">
				<input type="text" id="samthirdv" readonly class="col-sm-12"/>
			</div>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;">
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
		<div class="form-group " style="margin-right:0px;margin-left:0px;">
			<table id="new1" class="table table-striped table-bordered table-hover">
			</table>
		</div>
	</form>
</div>
	<div class="col-sm-2 rightContent" style="border-style: solid;border-color: #0A246A;border-width: 1px">
	</div>
</div>
</body>