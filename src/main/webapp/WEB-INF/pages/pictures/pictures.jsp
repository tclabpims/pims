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
    <script type="text/javascript" src="../scripts/pictures/pictures.js"></script>
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
<div class="row">
	<div class="col-sm-3 leftContent" id="div_2">
		<div class="input-group" style="float: left;">
			<span class="input-group-addon">会诊时间</span>
			<input type="hidden" id="local_name" value="${local_username}"/>
			<input type="text" class="form_datetime form-control" placeholder="" value="${sevenday}" id="req_bf_time"/>
			<span class="input-group-addon">-</span>
			<input type="text" class="form_datetime form-control" placeholder="" value="${receivetime}"  id="req_af_time"/>
			<span class="input-group-addon">会诊状态</span>
			<select class="form-control col-sm-8" id="send_hosptail">
				<option value="" >全  部</option>
				<option value="1">会诊中</option>
				<option value="2">会诊终了</option>
				<option value="0">会诊取消</option>
			</select>
			<span class="input-group-btn">
				<button type="button" class="btn btn-sm btn-success" onclick="searchList()">
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
	<div class="col-sm-5" id="formDialog" style="border-style: solid;border-color: #0A246A;border-width: 1px">
	<form class="form-horizontal"  action="#" method="post" id="sampleForm" >
		<div>
			<ul>
				<li>病人基本信息</li>
				<li>取材信息</li>
			</ul>
		</div>
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
				<select class="col-sm-10" id="samcreateuser" disabled="true">
					<option value="1">是</option>
					<option value="2">否</option>
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
				<select class="col-sm-10" id="samsenddoctorid" disabled="true">
					<option value="1">张三</option>
					<option value="2">李四</option>
					<option value="3">王五</option>
					<option value="4">赵六</option>
				</select>
			</div>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
			<label class="col-sm-2 control-label no-padding-right" >住院号:</label>
			<div class="col-sm-4 ">
				<input type="text" id="sampatientnumber" readonly/>
			</div>
			<label class="col-sm-2 control-label no-padding-right">送检科室:</label>
			<div class="col-sm-4">
				<select class="col-sm-10" id="samdeptcode" disabled="true">
					<option value="1">1科室</option>
					<option value="2">2科室</option>
					<option value="3">3科室</option>
				</select>
			</div>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
			<label class="col-sm-2 control-label no-padding-right" >床号:</label>
			<div class="col-sm-4 ">
				<input type="text" id="sampatientbed" readonly/>
			</div>
			<label class="col-sm-2 control-label no-padding-right">送检医院:</label>
			<div class="col-sm-4">
				<select class="col-sm-10" id="samsendhospital" disabled="true">
					<option value="1">1科室</option>
					<option value="2">2科室</option>
					<option value="3">3科室</option>
				</select>
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
		<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
			<label class="col-sm-2 control-label no-padding-right">病理诊断:</label>
			<div class="col-sm-10">
				<input type="text" id="sampatientdignoses" readonly class="col-sm-12"/>
			</div>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
			<label class="col-sm-2 control-label no-padding-right">免组染色:</label>
			<div class="col-sm-10">
				<input type="text" id="sampatientdignoses" readonly class="col-sm-12"/>
			</div>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
			<label class="col-sm-2 control-label no-padding-right">HPV结果:</label>
			<div class="col-sm-10">
				<input type="text" id="sampatientdignoses" readonly class="col-sm-12"/>
			</div>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
			<label class="col-sm-2 control-label no-padding-right">HPV结果:</label>
			<div class="col-sm-10">
				<input type="text" id="sampatientdignoses" readonly class="col-sm-12"/>
			</div>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
			<label class="col-sm-2 control-label no-padding-right">会诊意见:</label>
			<div class="col-sm-10">
				<input type="text" id="sampatientdignoses" readonly class="col-sm-12"/>
			</div>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
			<label class="col-sm-2 control-label no-padding-right">会诊结束:</label>
			<div class="col-sm-10">
				<input type="text" id="sampatientdignoses" readonly class="col-sm-12"/>
			</div>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
			<label class="col-sm-2 control-label no-padding-right">会诊状态:</label>
			<div class="col-sm-10">
				<input type="text" id="sampatientdignoses" readonly class="col-sm-12"/>
			</div>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
			<button type="button" class="btn btn-sm btn-danger" id="savebutton" onclick="saveInfo()">
				<i class="ace-icon fa fa-fire bigger-110"></i>
				保存
			</button>
		</div>
	</form>
</div>
	<div class="col-sm-2" id="formDialog1" style="border-style: solid;border-color: #0A246A;border-width: 1px">
	</div>
	<div class="col-sm-2" id="formDialog2" style="border-style: solid;border-color: #0A246A;border-width: 1px">
	</div>
</div>
</body>