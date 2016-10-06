<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="ElectronicApplyManage.title"/></title>
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />
	<link rel="stylesheet" type="text/css" media="all" href="../scripts/his/jquery.autocomplete.css"/>

	<script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
	<script type="text/javascript" src="../scripts/bootstrap.min.js"></script>
	<script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
	<script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
	<script type="text/javascript" src="<c:url value="/scripts/ace.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/ace-elements.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/bootstrap-tag.min.js"/>"></script>
    <script type="text/javascript" src="../scripts/layer/layer.js"></script>
    <script type="text/javascript" src="../scripts/his/his_main.js"></script>
	<!--<script src="../scripts/his/jquery.autocomplete.js" type="text/javascript"></script>-->
</head>
<style>
	select {
		height:34px;
	}
</style>
<body>
<div class="row widget-main">
		<div>
			<button type="button" class="btn btn-sm btn-primary " title="新增申请" onclick="addSample()">
				<i class="ace-icon fa fa-fire bigger-110"></i>
				新增
			</button>
			<button type="button" class="btn btn-sm  btn-success" title="修改申请" onclick="editSample()">
				<i class="ace-icon fa fa-pencil-square bigger-110"></i>
				修改
			</button>
			<button type="button" class="btn btn-sm btn-danger" title="删除申请" onclick="deleteSample()">
				<i class="ace-icon fa fa-times bigger-110"></i>
				删除
			</button>
			<button type="button" class="btn btn-sm btn-info" title="打印签收单" onclick="print()" disabled>
				<i class="ace-icon fa fa-print bigger-110"></i>
				打印
			</button>
		</div>
</div>
<div class="row widget-main">
	<div class="input-group" style="float: left;">
		<span class="input-group-addon">电子申请单</span>
		<input type="text" class="form-control" placeholder="申请单号" id="req_code" value="" onkeypress="receive(this,event)"/>
		<span class="input-group-addon">病人姓名</span>
		<input type="text" class="form-control" value="" id="patient_name"/>
		<span class="input-group-addon">送检医院</span>
		<input type="text" class="form-control" value="" id="send_hosptail"/>
		<span class="input-group-addon">申请年月</span>
		<input type="text" class="form-control" placeholder="" value="${receivetime}" id="req_bf_time"/>
		<span class="input-group-addon">-</span>
		<input type="text" class="form-control" placeholder="" value="${receivetime}" id="req_af_time"/>
	</div>
	<div class="input-group" style="float: left;">
		<span class="input-group-addon">送检科室</span>
		<input type="text" class="form-control" value="" id="send_dept"/>
		<span class="input-group-addon">送检医生</span>
		<input type="text" class="form-control" id="send_doctor"/>
		<span class="input-group-addon">申请状态</span>
		<select class="form-control" id="req_sts">
			<option value="">全  部</option>
			<option value="">已申请</option>
			<option value="">已登记</option>
			<option value="">已完成</option>
			<option value="">已延迟</option>
		</select>
		<span class="input-group-btn">
			<button type="button" class="btn btn-sm btn-success" title="提交样本信息" onclick="searchList()">
				<i class="ace-icon fa fa-print bigger-110"></i>
				查询
			</button>
		</span>
	</div>
</div>
<div class="row">
	<div class="widget-box widget-color-green">
		<div class="widget-body" style="height:700px;overflow:auto;">
			<div class="widget-main no-padding">
				<table id="new" class="table table-striped table-bordered table-hover">
				</table>
			</div>
		</div>
	</div>
</div>

<div id="formDialog" style="display:none;">
	<form class="form-horizontal" role="form" style="margin-top:5px;" id="sampleForm">
		<div class="form-group" style="margin-right:0px;margin-left:0px;">
			<label class="col-sm-1 control-label no-padding-right" for="requisitionno">申请单号</label>
			<div class="col-sm-2">
				<input type="hidden" id="requisitionid" value="${requisitionid}"/>
				<input type="text" class="col-sm-12" id="requisitionno" onkeypress="getData(this,event)" value="${requisitionno}"/>
			</div>
			<label class="col-sm-1 control-label no-padding-right" >检查项目</label>
			<div class="col-sm-2">
				<select class="col-sm-12" id="reqitemnames">
					<option value="1">111</option>
					<option value="2">222</option>
					<option value="3">333</option>
					<option value="4">444</option>
				</select>
			</div>
			<label class="col-sm-1 control-label no-padding-right" >病种类别</label>
			<div class="col-sm-2">
				<select class="col-sm-12" id="reqpathologyid">
					<option value="1">常规病理</option>
					<option value="2">常规病理</option>
					<option value="3">液基细胞学</option>
				</select>
			</div>
			<label class="col-sm-3 control-label no-padding-right">
				<button type="button" class="btn btn-sm btn-success" title="提交信息" onclick="saveInfo()">
					<i class="ace-icon fa fa-print bigger-110"></i>
					提交
				</button>
			</label>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;">
			<label class="col-sm-1 control-label no-padding-right" for="patientname">姓&nbsp;名</label>
			<div class="col-sm-2">
				<input type="text" class="col-sm-12" id="patientname"/>
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="sex">性&nbsp;别</label>
			<div class="col-sm-2">
				<select class="col-sm-12" id="sex">
					<option value="1">男</option>
					<option value="2">女</option>
					<option value="3">未知</option>
				</select>
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="age">年&nbsp;龄</label>
			<div class="col-sm-2">
				<span class="input-icon input-icon-right" style="width:100%">
					<input type="text" id="age" style="float:left;width:75%"/>
					<select  style="float:left;width:25%" id="ageunit">
						<option value="岁">岁</option>
						<option value="月">月</option>
						<option value="天">天</option>
					</select>
				</span>
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="reqpattelephone">联系电话</label>
			<div class="col-sm-2">
				<input type="text" class="col-sm-12" id="reqpattelephone"/>
			</div>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;">
			<label class="col-sm-1 control-label no-padding-right" for="reqirnpatientno">住院号</label>
			<div class="col-sm-2">
				<input type="text" class="col-sm-12" id="reqirnpatientno"/>
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="reqpatientnumber">床&nbsp;号</label>
			<div class="col-sm-2">
				<input type="text" class="col-sm-12" id="reqpatientnumber"/>
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="reqfirstv">邮&nbsp;编</label>
			<div class="col-sm-2">
				<input type="text" class="col-sm-12" id="reqfirstv"/>
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="reqpataddress">联系地址</label>
			<div class="col-sm-2">
				<input type="text" class="col-sm-12" id="reqpataddress"/>
			</div>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;">
			<label class="col-sm-1 control-label no-padding-right" for="reqsendhospital">送检医院</label>
			<div class="col-sm-2">
				<input type="text" class="col-sm-12" id="reqsendhospital"/>
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="reqdeptcode">送检科室</label>
			<div class="col-sm-2">
				<input type="text" class="col-sm-12" id="reqdeptcode"/>
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="reqdoctorid">送检医生</label>
			<div class="col-sm-2">
				<input type="text" class="col-sm-12" id="reqdoctorid"/>
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="reqdate">送检时间</label>
			<div class="col-sm-2">
				<input type="text" class="col-sm-12" id="reqdate"/>
			</div>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;">
			<label class="col-sm-1 control-label no-padding-right" for="reqsendphone">送检电话</label>
			<div class="col-sm-2">
				<input type="text" class="col-sm-12" id="reqsendphone"/>
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="reqplanexectime">接收时间</label>
			<div class="col-sm-2">
				<input type="text" class="col-sm-12 input-mask-date" id="reqplanexectime"/>
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="reqsecondv">手术医生</label>
			<div class="col-sm-2">
				<input type="text" class="col-sm-12 input-mask-date" id="reqsecondv" />
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="reqfirstn">手术电话</label>
			<div class="col-sm-2">
				<input type="text" class="col-sm-12 input-mask-date" id="reqfirstn" />
			</div>
		</div>
	</form>
	<input type="hidden" id="operate"/>
</div>
</body>