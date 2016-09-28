<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="ElectronicApplyManage.title"/></title>
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />
    	
    <script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
	<script type="text/javascript" src="../scripts/bootstrap.min.js"></script>
	<script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
	<script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
	<script type="text/javascript" src="<c:url value="/scripts/ace.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/ace-elements.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/bootstrap-tag.min.js"/>"></script>
    <script type="text/javascript" src="../scripts/layer/layer.js"></script>
    <script type="text/javascript" src="../scripts/his/input.js"></script>
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
	<div class="input-group" style="float: left;">
		<span class="input-group-addon">电子申请单</span>
		<input type="text" class="form-control" placeholder="申请单号" id="req_code" value="" onkeypress="receive(this,event)"/>
		<span class="input-group-addon">病人姓名</span>
		<input type="text" class="form-control" value="" id="patient_name"/>
		<span class="input-group-addon">送检医院</span>
		<input type="text" class="form-control" value="" id="send_hosptail"/>
		<span class="input-group-addon">申请年月</span>
		<input type="text" class="form-control" placeholder="" value="" id="req_bf_time"/>
		<input type="text" class="form-control" placeholder="" value="" id="req_af_time"/>
		<span class="input-group-addon">送检科室</span>
		<input type="text" class="form-control" value="" id="send_dept"/>
		<span class="input-group-addon">送检医生</span>
		<input type="text" class="form-control" id="send_doctor"/>
		<span class="input-group-addon">申请状态</span>
		<select id="req_sts">
			<option value="">全  部</option>
			<option value="">已申请</option>
			<option value="">已登记</option>
			<option value="">已完成</option>
			<option value="">已延迟</option>
		</select>
		<span class="input-group-btn">
			<button type="button" class="btn btn-info btn-sm">
				<span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
				查询
			</button>
		</span>
	</div>
</div>
<div class="row">
<div class="col-sm-12">
	<div class="widget-box widget-color-green">
		<div class="widget-body" style="height:700px;overflow:auto;">
			<div class="widget-main no-padding">
				<table id="new" class="table table-striped table-bordered table-hover">
				</table>
			</div>
		</div>
	</div>
</div>
</div>

<div id="formDialog" style="display:none;">
	<form class="form-horizontal" role="form" style="margin-top:5px;" id="sampleForm">
		<div class="form-group" style="margin-right:0px;margin-left:0px;">
			<label class="col-sm-1 control-label no-padding-right" for="stayhospitaimode">在院方式</label>
			<div class="col-sm-2">
				<select class="col-sm-12" id="stayhospitalmode">
					<option value="1">门诊</option>
					<option value="2">住院</option>
					<option value="3">体检</option>
					<option value="4">血库</option>
					<option value="5">外单位</option>
					<option value="6">药物验证</option>
					<option value="7">科研</option>
					<option value="8">电子档案</option>
				</select>
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="doctadviseno">医嘱号</label>
			<div class="col-sm-2">
				<input type="text" class="col-sm-12" id="doctadviseno" onkeypress="getData(this,event)"></input>
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="sampleno">样本号</label>
			<div class="col-sm-2">
				<input type="text" class="col-sm-12" id="sampleno" onkeypress="getData(this,event)" value="${sampleno}"></input>
				<input type="hidden" id="hiddenSegment" value="${segment}"/>
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="patientid">就诊卡号</label>
			<div class="col-sm-2">
				<input type="text" class="col-sm-12" id="patientid" onkeypress="getPatient(this,event)"></input>
			</div>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;">
			<label class="col-sm-1 control-label no-padding-right" for="patientname">姓&nbsp;名</label>
			<div class="col-sm-2">
				<input type="text" class="col-sm-12" id="patientname"></input>
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
					<input type="text" id="age" style="float:left;width:75%"></input>
					<select  style="float:left;width:25%" id="ageunit">
						<option value="岁">岁</option>
						<option value="月">月</option>
						<option value="天">天</option>
					</select>
				</span>
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="diagnostic">诊&nbsp;断</label>
			<div class="col-sm-2">
				<input type="text" class="col-sm-12" id="diagnostic"></input>
			</div>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;">
			<label class="col-sm-1 control-label no-padding-right" for="section">科&nbsp;室</label>
			<div class="col-sm-2">
				<input type="text" class="col-sm-12" id="section"></input>
				<input type="text" id="sectionCode" style="display:none;"></input>
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="sampletype">样本类型</label>
			<div class="col-sm-2">
				<select class="col-sm-12" id="sampletype">
					<c:forEach var="sType" items="${typelist}">
						<option value='<c:out value="${sType.sign}" />'><c:out value="${sType.value}" /></option>
					</c:forEach>
				</select>
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="requester">送检医生</label>
			<div class="col-sm-2">
				<input type="text" class="col-sm-12" id="requester"></input>
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="fee">收费金额</label>
			<div class="col-sm-2">
				<input type="text" class="col-sm-12" id="fee"></input>
			</div>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;">
			<label class="col-sm-1 control-label no-padding-right" for="feestatus">收费状态</label>
			<div class="col-sm-2">
				<input type="text" class="col-sm-12" id="feestatus"></input>
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="executetime">采样时间</label>
			<div class="col-sm-2">
				<input type="text" class="col-sm-12 input-mask-date" id="executetime"></input>
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="receivetime">接收时间</label>
			<div class="col-sm-2">
				<input type="text" class="col-sm-12 input-mask-date" id="receivetime" value='${receivetime}'></input>
			</div>
			<div class="col-sm-3">&nbsp;
			</div>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;">
			<label class="col-sm-1 control-label no-padding-right" for="examinaim">检验目的</label>
			<div class="col-sm-8" id="examTag">
				<input type="text" name="examinaim" id="examinaim" placeholder="输入检验目的的中文、拼音" class="col-sm-12"/>
			</div>
			<input type="text" id="ylxh" style="display:none;"></input>
			<div class="col-sm-1">&nbsp;</div>
			<div class="col-sm-2">
				<button type="button" class="btn btn-sm btn-success" title="提交样本信息" onclick="sample()">
					<i class="ace-icon fa fa-print bigger-110"></i>
					提交
				</button>
				<button type="button" class="btn btn-sm btn-info" title="清空样本信息" onclick="clearData()">
					<i class="ace-icon fa fa-undo bigger-110"></i>
					清空
				</button>
			</div>
		</div>
	</form>
	<input type="hidden" id="operate"/>
</div>
</body>