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
	<script type="text/javascript" src="../scripts/validform/Validform.min.js"></script>
	<!--<script src="../scripts/his/jquery.autocomplete.js" type="text/javascript"></script>-->
</head>
<style>
	select {
		height:34px;
	}
</style>
<body>
<div class="row widget-main" id="div_1">
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
			<button type="button" class="btn btn-sm btn-info" title="打印签收单" onclick="print()">
				<i class="ace-icon fa fa-print bigger-110"></i>
				打印
			</button>
		</div>
</div>
<div class="row widget-main" id="div_2">
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
			<button type="button" class="btn btn-sm btn-success" title="查询信息" onclick="searchList()">
				<i class="ace-icon fa fa-print bigger-110"></i>
				查询
			</button>
		</span>
	</div>
</div>
<div class="row">
	<div class="widget-box widget-color-green">
		<div class="widget-body" style="overflow:auto;">
			<div class="widget-main no-padding">
				<table id="new" class="table table-striped table-bordered table-hover">
				</table>
				<div id="pager"></div>
			</div>
		</div>
	</div>
</div>

<div id="formDialog" style="display:none;" class="col-sm-12">
	<form class="form-horizontal" role="form" style="margin-top:5px;" id="sampleForm">
		<div class="form-group" style="margin-right:0px;margin-left:0px;">
			<label class="col-sm-1 control-label no-padding-right" for="requisitionno">申请单号</label>
			<div class="col-sm-2">
                <input type="hidden" id="reqcustomerid" value="${reqcustomerid}">
				<input type="hidden" id="requisitionid" value="${requisitionid}"/>
				<input type="text" class="col-sm-12" id="requisitionno" name="requisitionno" onkeypress="getData(this,event)" value="${requisitionno}"/>
			</div>
			<label class="col-sm-1 control-label no-padding-right" >检查项目</label>
			<div class="col-sm-2">
				<select class="col-sm-12" id="reqitemids" onchange="changethId()">
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
			<label class="col-sm-2 control-label no-padding-center">
				<button type="submit" class="btn btn-sm btn-success" id="addinfo" title="提交信息" onclick="saveInfo()">
					<i class="ace-icon fa fa-print bigger-110"></i>
					提交
				</button>
			</label>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;">
			<label class="col-sm-1 control-label no-padding-right" for="reqpatientname">姓&nbsp;名</label>
			<div class="col-sm-2">
				<input type="text" class="col-sm-12" id="reqpatientname" name="reqpatientname" placeholder="姓名" datatype="s1-16" errormsg="至少1个字符,最多16个字符！" />
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="reqpatientsex">性&nbsp;别</label>
			<div class="col-sm-2">
				<select class="col-sm-12" id="reqpatientsex">
					<option value="1">男</option>
					<option value="2">女</option>
					<option value="3">未知</option>
				</select>
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="reqpatientage">年&nbsp;龄</label>
			<div class="col-sm-2">
				<span class="input-icon input-icon-right" style="width:100%">
					<input type="text" id="reqpatientage" style="float:left;width:75%"/>
					<select  style="float:left;width:25%" id="reqpatagetype">
						<option value="1">岁</option>
						<option value="2">月</option>
						<option value="4">周</option>
						<option value="5">日</option>
						<option value="6">小时</option>
					</select>
				</span>
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="reqpattelephone">联系电话</label>
			<div class="col-sm-2">
				<input type="text" class="col-sm-12" id="reqpattelephone"/>
			</div>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;">
			<label class="col-sm-1 control-label no-padding-right" for="reqpatientnumber">住院号</label>
			<div class="col-sm-2">
				<input type="text" class="col-sm-12" id="reqpatientnumber"/>
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="reqwardcode">床&nbsp;号</label>
			<div class="col-sm-2">
				<input type="text" class="col-sm-12" id="reqwardcode"/>
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
			<label class="col-sm-1 control-label no-padding-right" for="reqdeptname">送检科室</label>
			<div class="col-sm-2">
				<input type="text" class="col-sm-12" id="reqdeptname"/>
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="reqdoctorname">送检医生</label>
			<div class="col-sm-2">
				<input type="text" class="col-sm-12" id="reqdoctorname"/>
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="reqdatechar">送检时间</label>
			<div class="col-sm-2">
				<input type="text" class="col-sm-12" id="reqdatechar"/>
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
			<label class="col-sm-1 control-label no-padding-right" for="reqpatdiagnosis">临床诊断</label>
			<div class="col-sm-5">
				<input type="text" class="col-sm-12 input-mask-date" id="reqpatdiagnosis" />
			</div>
		</div>
		<div class="row col-sm-12">
			<div class="widget-box widget-color-green">
					<div class="widget-main no-padding  col-sm-7" style="margin-top: 5px">
						<div class="form-group" style="margin-right:0px;margin-left:0px;">
							<label class="control-label" style="text-align: left;">&nbsp;&nbsp;&nbsp;&nbsp;组织信息</label>
							<button type="button" class="btn btn-sm btn-primary " title="追加行" onclick="addRow()">
								<i class="ace-icon fa fa-fire bigger-110"></i>
								追加行
							</button>
							<button type="button" class="btn btn-sm btn-danger" title="删除行" onclick="delRow()">
								<i class="ace-icon fa fa-times bigger-110"></i>
								删除行
							</button>
						</div>
						<div class="form-group " style="margin-right:0px;margin-left:0px;">
							<table id="new1" class="table table-striped table-bordered table-hover">
							</table>
						</div>
					</div>
					<div class="widget-main no-padding  col-sm-5" style="margin-top: 5px">
						<div class="form-group" style="margin-right:0px;margin-left:0px;">
							<label class="col-sm-12 control-label" style="text-align: left;">影像学检查</label>
						</div>
						<div class="form-group" style="margin-right:0px;margin-left:0px;">
							<label class="col-sm-2 control-label no-padding-right" for="X">X 光</label>
							<div class="col-sm-4">
								<input type="text" class="col-sm-12" id="X"/>
							</div>
							<label class="col-sm-2 control-label no-padding-right" for="CT">C T</label>
							<div class="col-sm-4">
								<input type="text" class="col-sm-12 input-mask-date" id="CT"/>
							</div>
						</div>
						<div class="form-group" style="margin-right:0px;margin-left:0px;">
							<label class="col-sm-2 control-label no-padding-right" for="B">B 超</label>
							<div class="col-sm-4">
								<input type="text" class="col-sm-12" id="B"/>
							</div>
							<label class="col-sm-2 control-label no-padding-right" for="previous">婚 史</label>
							<div class="col-sm-4">
								<input type="text" class="col-sm-12 input-mask-date" id="previous"/>
							</div>
						</div>
						<div class="form-group" style="margin-right:0px;margin-left:0px;">
							<label class="col-sm-2 control-label no-padding-right" for="menses">月经初潮</label>
							<div class="col-sm-4">
								<input type="text" class="col-sm-12" id="menses"/>
							</div>
							<label class="col-sm-2 control-label no-padding-right" for="cycle">周 期</label>
							<div class="col-sm-4">
								<input type="text" class="col-sm-12 input-mask-date" id="cycle"/>
							</div>
						</div>
						<div class="form-group" style="margin-right:0px;margin-left:0px;">
							<label class="col-sm-2 control-label no-padding-right" for="cesarean">产 史</label>
							<div class="col-sm-4">
								<input type="text" class="col-sm-12" id="cesarean"/>
							</div>
							<label class="col-sm-2 control-label no-padding-right" for="endmenses">末次月经</label>
							<div class="col-sm-4">
								<input type="text" class="col-sm-12 input-mask-date" id="endmenses"/>
							</div>
						</div>
					</div>
			</div>
		</div>
	</form>
	<input type="hidden" id="operate"/>
</div>
</body>