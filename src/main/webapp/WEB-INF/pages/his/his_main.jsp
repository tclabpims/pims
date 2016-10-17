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
    <script type="text/javascript" src="../scripts/his/his_main.js"></script>
	<%--<script type="text/javascript" src="../scripts/validform/Validform.min.js"></script>--%>
	<script type="text/javascript" src="../scripts/bootstrap-datetimepicker.min.js"></script>
</head>
<style>
	select {
		height:34px;
	}
	.ui-autocomplete {
		z-index: 99999999;
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
		<span class="input-group-addon ">申请单号</span>
		<input type="text" class="form-control" placeholder="申请单号" id="req_code" value="" onkeypress="receive(this,event)"/>
		<span class="input-group-addon">病人姓名</span>
		<input type="text" class="form-control" value="" id="patient_name"/>
		<span class="input-group-addon">申请年月</span>
		<input type="text" class="form_datetime form-control" placeholder="" value="${receivetime}" id="req_bf_time"/>
		<span class="input-group-addon">-</span>
		<input type="text" class="form_datetime form-control" placeholder="" value="${receivetime}"  id="req_af_time"/>
	</div>
	<div class="input-group" style="float: left;">
		<span class="input-group-addon">送检医院</span>
		<input type="text" class="form-control" value="" id="send_hosptail"/>
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
			<button type="submit" class="btn btn-sm btn-success" title="查询信息" onclick="searchList()">
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
	<form class="form-horizontal" role="form" style="margin-top:5px;" id="sampleForm" >
		<div class="form-group" style="margin-right:0px;margin-left:0px;">
			<label class="col-sm-1 control-label no-padding-right" for="requisitionno">申请单号</label>
			<div class="col-sm-2">
                <input type="hidden" id="reqcustomercode" value="${reqcustomerid}"><!--客户id-->
				<input type="hidden" id="requisitionid" value="${requisitionid}"/><!--申请id-->
				<input type="hidden" id="reqsource" value="${reqsource}"/><!--申请单来源-->
				<input type="hidden" id="reqdatechar" value="${reqdatechar}"/>
				<input type="hidden" id="reqstate" value="${reqstate}"/>
				<input type="hidden" id="reqdate" value="${reqdate}"/>
				<input type="hidden" id="reqisdeleted" value="${reqisdeleted}"/>
				<input type="hidden" id="reqinpatientid" value="${reqinpatientid}"/>

				<input type="hidden" id="reqplanexectime" value="${reqplanexectime}"/>
				<input type="hidden" id="reqcreateuser"  value="${reqcreateuser}"/>
				<input type="hidden" id="reqcreatetime" value="${reqcreatetime}"/>
				<input type="text" class="col-sm-12" id="requisitionno" name="requisitionno" onkeypress="getData(this,event)" value="${requisitionno}"/>
			</div>
			<label class="col-sm-1 control-label no-padding-right" >检查项目</label>
			<div class="col-sm-2 ">
				<%--<select class="col-sm-12" id="reqitemids">--%>
					<%--<c:forEach var="test" items="${testList}">--%>
						<%--<option value="${test.testitemid}">${test.teschinesename}</option>--%>
					<%--</c:forEach>--%>
				</select>
				<input type="hidden" id="reqitemids"/>
				<input type="text" id="reqitemnames" name="reqitemnames"  placeholder="检查项目" datatype="s1-16"/>
			</div>
			<label class="col-sm-1 control-label no-padding-right" >病种类别</label>
			<div class="col-sm-2">
				<select class="col-sm-12" id="reqpathologyid">
					<option value="1" <c:if test="${logylibid == 1 }"> selected</c:if>>常规病理</option>
					<option value="2" <c:if test="${logylibid == 2 }"> selected</c:if>>常规病理</option>
					<option value="3" <c:if test="${logylibid == 3 }"> selected</c:if>>液基细胞学</option>
				</select>
			</div>
			<label class="col-sm-2 control-label no-padding-center">
				<button type="button" class="btn btn-sm btn-success" id="addinfo" title="提交信息" onclick="saveInfo()">
					<i class="ace-icon fa fa-print bigger-110"></i>
					提交
				</button>
			</label>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;">
			<label class="col-sm-1 control-label no-padding-right" >申请类型</label>
			<div class="col-sm-2">
				<select class="col-sm-12" id="reqtype">
					<option value="1">住院</option>
					<option value="2">门诊</option>
					<option value="3">手术室</option>
				</select>
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="requisitionno">申请科室</label>
			<div class="col-sm-2">
				<select class="col-sm-12" id="reqdeptcode">
					<option value="1">1科室</option>
					<option value="2">2科室</option>
					<option value="3">3科室</option>
				</select>
			</div>
			<label class="col-sm-1 control-label no-padding-right" >申请病区</label>
			<div class="col-sm-2 ">
				<select class="col-sm-12" id="reqwardcode">
					<option value="1">1病区</option>
					<option value="2">2病区</option>
					<option value="3">3病区</option>
				</select>
			</div>
			<label class="col-sm-1 control-label no-padding-right" >申请医生</label>
			<div class="col-sm-2">
				<select class="col-sm-12" id="reqdoctorid">
					<option value="1">张三</option>
					<option value="2">李四</option>
					<option value="3">王五</option>
					<option value="4">赵六</option>
				</select>
			</div>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;">
			<label class="col-sm-1 control-label no-padding-right" for="reqpatientid">病案号</label>
			<div class="col-sm-2">
				<input type="text" class="col-sm-12" id="reqpatientid" />
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="reqinpatientno">住院序号</label>
			<div class="col-sm-2">
				<input type="text" class="col-sm-12" id="reqinpatientno" />
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="reqpatienttype">患者类型</label>
			<div class="col-sm-2">
				<select  style="float:left;width:100%" id="reqpatienttype">
					<option value="1">住院</option>
					<option value="2">门诊</option>
					<option value="3">体检</option>
					<option value="4">婚检</option>
					<option value="5">科研</option>
					<option value="6">特勤</option>
					<option value="7">其他</option>
				</select>
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="reqpatientnumber">住院卡号</label>
			<div class="col-sm-2">
				<input type="text" class="col-sm-12" id="reqpatientnumber"/>
			</div>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;">
			<label class="col-sm-1 control-label no-padding-right" for="reqpatientname">姓&nbsp;名</label>
			<div class="col-sm-2">
				<input type="text" class="col-sm-12" id="reqpatientname" name="reqpatientname" placeholder="姓名"   datatype="s1-16"/>
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
					<input type="text" id="reqpatientage" style="float:left;width:60%"/>
					<select  style="float:left;width:40%" id="reqpatagetype">
						<option value="1">岁</option>
						<option value="2">月</option>
						<option value="4">周</option>
						<option value="5">日</option>
						<option value="6">小时</option>
					</select>
				</span>
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="reqpatbirthday">出生日期</label>
			<div class="col-sm-2">
				<input type="text" class=" col-sm-12" id="reqpatbirthday"/>
			</div>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;">
			<label class="col-sm-1 control-label no-padding-right" for="reqpatidcard">身份证号</label>
			<div class="col-sm-2">
				<input type="text" class="col-sm-12" id="reqpatidcard"/>
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="reqpattelephone">联系电话</label>
			<div class="col-sm-2">
				<input type="text" class="col-sm-12" id="reqpattelephone"/>
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="reqpataddress">联系地址</label>
			<div class="col-sm-2">
				<input type="text" class="col-sm-12" id="reqpataddress"/>
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="reqpatdiagnosis">临床诊断</label>
			<div class="col-sm-2">
				<input type="text" class="col-sm-12" id="reqpatdiagnosis"/>
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