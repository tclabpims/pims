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
    <script type="text/javascript" src="../scripts/pathologysample/sample.js"></script>
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
</style>
<body>
<div class="row widget-main" id="div_1">
		<div>
			<button type="button" class="btn btn-sm btn-primary " title="新增标本" onclick="addSample()">
				<i class="ace-icon fa fa-fire bigger-110"></i>
				新增
			</button>
			<button type="button" class="btn btn-sm  btn-success" title="修改标本" onclick="editSample()">
				<i class="ace-icon fa fa-pencil-square bigger-110"></i>
				修改
			</button>
			<button type="button" class="btn btn-sm btn-danger" title="删除标本" onclick="deleteSample()">
				<i class="ace-icon fa fa-times bigger-110"></i>
				删除
			</button>
			<button type="button" class="btn btn-sm btn-danger" title="保存标本" id="saveButton" onclick="deleteSample()">
				<i class="ace-icon fa fa-fire bigger-110"></i>
				保存
			</button>
			<button type="button" class="btn btn-sm btn-info" title="打印" onclick="print()">
				<i class="ace-icon fa fa-print bigger-110"></i>
				打印
			</button>
			<button type="button" class="btn btn-sm btn-danger" title="上一个" onclick="upSample()">
				<i class="ace-icon fa fa-times bigger-110"></i>
				上一个
			</button>
			<button type="button" class="btn btn-sm btn-danger" title="下一个" onclick="downSample()">
				<i class="ace-icon fa fa-fire bigger-110"></i>
				下一个
			</button>
			<button type="button" class="btn btn-sm btn-info" title="计费调整" onclick="hisChange()">
				<i class="ace-icon fa fa-print bigger-110"></i>
				计费调整
			</button>
		</div>
</div>
<div class="row">
<div id="formDialog" class="col-sm-7 leftContent" style="border-style: solid;border-color: #0A246A;border-width: 1px">
	<form class="form-horizontal" role="form" style="margin-top:5px;" id="sampleForm" >
		<div class="form-group" style="margin-right:0px;margin-left:0px;">
			<label class="col-sm-1 control-label no-padding-left" for="saminspectionid">条形码:</label>
			<div class="col-sm-3">
				<input type="hidden" id="sampleid" value="${sampleid}"><!--标本id-->
				<input type="hidden" id="samcustomerid" value="${samcustomerid}"/><!--客户id-->
				<input type="hidden" id="samsource" value="${samsource}"/><!--是否外送-->
				<input type="text" id="saminspectionid" name="saminspectionid" onkeypress="getData(this,event)" value="${saminspectionid}"/>
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="samrequistionid">临床申请:</label>
			<div class="col-sm-3 ">
				<input type="text" id="samrequistionid" name="samrequistionid"  placeholder="临床申请" datatype="s1-16"/>
			</div>
			<label class="col-sm-1 control-label no-padding-right" >病种类别:</label>
			<div class="col-sm-3">
				<select class="col-sm-11" id="sampathologyid">
					<option value="1" <c:if test="${logylibid == 1 }"> selected</c:if>>常规病理</option>
					<option value="2" <c:if test="${logylibid == 2 }"> selected</c:if>>常规病理1</option>
					<option value="3" <c:if test="${logylibid == 3 }"> selected</c:if>>液基细胞学</option>
				</select>
			</div>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;">
			<label class="col-sm-1 control-label no-padding-right" >病理编号:</label>
			<div class="col-sm-3 ">
				<input type="text" id="sampathologycode" name="sampathologycode"  placeholder="病理编号" datatype="s1-16"/>
			</div>
			<label class="col-sm-1 control-label no-padding-right" >病人姓名:</label>
			<div class="col-sm-3 ">
				<input type="text" id="sampatientname" name="sampatientname"  placeholder="病人姓名" datatype="s1-16"/>
			</div>
			<label class="col-sm-1 control-label no-padding-right" >联系电话:</label>
			<div class="col-sm-3 ">
				<input type="text" id="sampatientphoneno" name="sampatientphoneno"/>
			</div>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;">
			<label class="col-sm-1 control-label no-padding-right" >住院号:</label>
			<div class="col-sm-3 ">
				<input type="text" id="sampatientnumber" name="sampatientnumber"/>
			</div>
			<label class="col-sm-1 control-label no-padding-right" >床号:</label>
			<div class="col-sm-3 ">
				<input type="text" id="sampatientbed" name="sampatientbed"  placeholder="床号"/>
			</div>
			<label class="col-sm-1 control-label no-padding-right" >联系地址:</label>
			<div class="col-sm-3">
				<input type="text" id="sampatientaddress" name="sampatientaddress"  placeholder="床号"/>
			</div>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;">
			<label class="col-sm-1 control-label no-padding-right" for="sampatientsex">性&nbsp;别:</label>
			<div class="col-sm-3">
				<select class="col-sm-11" id="sampatientsex">
					<option value="1">男</option>
					<option value="2">女</option>
					<option value="3">未知</option>
				</select>
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="sampatientage">年&nbsp;龄:</label>
			<div class="col-sm-3">
				<span class="input-icon input-icon-right" style="width:90%">
					<input type="text" id="sampatientage" style="float:left;width:60%"/>
					<select  style="float:left;width:40%" id="sampatientagetype">
						<option value="1">岁</option>
						<option value="2">月</option>
						<option value="4">周</option>
						<option value="5">日</option>
						<option value="6">小时</option>
					</select>
				</span>
			</div>
			<label class="col-sm-1 control-label no-padding-left" >知情书:</label>
			<div class="col-sm-3">
				&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio"  value="1" name="samfirstv"/>&nbsp;&nbsp;已签&nbsp;&nbsp;
				<input type="radio" value="2" name="samfirstv"/>&nbsp;&nbsp;未签
			</div>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;">
			<label class="col-sm-1 control-label no-padding-right" >送检医生:</label>
			<div class="col-sm-3 ">
				<select class="col-sm-11" id="samsenddoctorid">
					<option value="1">张三</option>
					<option value="2">李四</option>
					<option value="3">王五</option>
					<option value="4">赵六</option>
				</select>
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="samdeptcode">送检科室:</label>
			<div class="col-sm-3">
				<select class="col-sm-11" id="samdeptcode">
					<option value="1">1科室</option>
					<option value="2">2科室</option>
					<option value="3">3科室</option>
				</select>
			</div>
			<label class="col-sm-1 control-label no-padding-right" >送检医院:</label>
			<div class="col-sm-3 ">
				<select class="col-sm-11" id="samsendhospital">
					<option value="1">杭州国际医院</option>
					<option value="2">袍江医院</option>
					<option value="3">温州人民医院</option>
				</select>
			</div>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;">
			<label class="col-sm-1 control-label no-padding-right" >送检时间:</label>
			<div class="col-sm-3">
				<input type="text" class="form_datetime" value="${samsendtime}" id="samsendtime"/>
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="samreceivertime">接收日期:</label>
			<div class="col-sm-3">
				<input type="text" class="form_datetime" value="${samreceivertime}" id="samreceivertime"/>
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="samfirstn">组织袋数:</label>
			<div class="col-sm-3">
				<input type="text" id="samfirstn" />
			</div>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;">
			<label class="col-sm-1 control-label no-padding-right">标本状态:</label>
			<div class="col-sm-3">
				&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" value="1" name="samsecondv"/>&nbsp;&nbsp;合格&nbsp;&nbsp;
				<input type="radio" value="2" name="samsecondv"/>&nbsp;&nbsp;不合格
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="samremark">原因:</label>
			<div class="col-sm-7">
				<input type="text" id="samremark" class="col-sm-12"/>
			</div>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;">
			<label class="col-sm-1 control-label no-padding-right" for="samsamplename">送检材料:</label>
			<div class="col-sm-3">
				<textarea id="samsamplename"></textarea>
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="sampatientdignoses">临床诊断:</label>
			<div class="col-sm-3">
				<textarea id="sampatientdignoses"></textarea>
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="samthirdv">手术所见:</label>
			<div class="col-sm-3">
				<textarea id="samthirdv"></textarea>
			</div>
		</div>
	</form>
	<div class="widget-main no-padding">
		<p1>电子申请单列表</p1>
		<table id="new1" class="table table-striped table-bordered table-hover">
		</table>
	</div>
	<div class="widget-main no-padding">
		<p1>费用列表</p1>
		<table id="new2" class="table table-striped table-bordered table-hover">
		</table>
	</div>
</div>
<div class="col-sm-5 rightContent" id="div_2">
	<table>
	<div class="input-group" style="float: left;">
		<span class="input-group-addon ">病种类别</span>
		<select class="form-control"  id="logyid">
			<option value="1" <c:if test="${logyid == 1}">selected</c:if>>常规病理</option>
			<option value="2" <c:if test="${logyid == 2}">selected</c:if>>常规细胞学</option>
			<option value="3" <c:if test="${logyid == 3}">selected</c:if>>液基细胞学</option>
			<option value="4" <c:if test="${logyid == 4}">selected</c:if>>骨髓活检</option>
			<option value="5" <c:if test="${logyid == 5}">selected</c:if>>肺穿刺</option>
		</select>
		<span class="input-group-addon">条形码</span>
		<input type="text" class="form-control" value="" id="req_code"/>
	</div>
	<div class="input-group" style="float: left;">
		<span class="input-group-addon">登记年月</span>
		<input type="text" class="form_datetime form-control" placeholder="" value="${sevenday}" id="req_bf_time"/>
		<span class="input-group-addon">-</span>
		<input type="text" class="form_datetime form-control" placeholder="" value="${receivetime}"  id="req_af_time"/>
	</div>
	<div class="input-group" style="float: left;">
		<span class="input-group-addon">病理号</span>
		<input type="text" class="form-control" value="" id="send_dept"/>
		<span class="input-group-addon ">送检医院</span>
		<select class="form-control"  id="send_hosptail">
			<option value="1" <c:if test="${send_hosptail == 1}">selected</c:if>>杭州国际医院</option>
			<option value="2" <c:if test="${send_hosptail == 1}">selected</c:if>>袍江医院</option>
			<option value="3" <c:if test="${send_hosptail == 1}">selected</c:if>>温州人民医院</option>
		</select>
	</div>
	<div class="input-group" style="float: left;">
		<span class="input-group-addon ">送检医生</span>
		<select class="form-control"  id="send_doctor">
			<option value="">全部</option>
			<option value="1">张三</option>
			<option value="2">李四</option>
			<option value="3">王五</option>
			<option value="4">赵六</option>
		</select>
		<span class="input-group-addon">病人姓名</span>
		<input type="text" class="form-control" value="" id="patient_name"/>
	</div>
	<div class="input-group" style="float: left;">
		<span class="input-group-addon">标本状态</span>
		<select class="form-control" id="req_sts">
			<option value="">全  部</option>
			<option value="1">合格</option>
			<option value="2">不合格</option>
		</select>
		<span class="input-group-btn">
			<button type="button" class="btn btn-sm btn-success" title="查询信息" onclick="searchList()">
				<i class="ace-icon fa fa-print bigger-110"></i>
				查询
			</button>
		</span>
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
	</table>
</div>
</div>
</body>