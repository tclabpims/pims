<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="menu.samplingManage"/></title>
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
	<style>
		select {
			height:34px;
		}
		.ui-autocomplete {
			z-index: 99999999;
		}
		.div_div {
			float:left;
			margin:10px 5px;
			text-align:center;
		}
	</style>
</head>
<body style="text-align: center;">
	<div class="row" id="div_1"style="background-color: #E8E8E8;" >
		<div class="div_div"><img src="/styles/imagepims/up.png" style="cursor:pointer;display: block" onclick="upSample()">上一个</div>
		<div class="div_div"><img src="/styles/imagepims/down.png" style="cursor:pointer;display: block" onclick="downSample()">下一个</div>
		<div class="div_div"><img src="/styles/imagepims/print.png" style="cursor:pointer;display: block" onclick="printCode()">打印</div>
		<div class="div_div"><img src="/styles/imagepims/piece.png" style="cursor:pointer;display: block" id="saveButton" onclick="saveInfo(1)">取材</div>
		<div class="div_div"><img src="/styles/imagepims/fee.png" style="cursor:pointer;display: block" id="hisbutton">计费调整</div>
		<div class="div_div"><img src="/styles/imagepims/imgget.png" style="cursor:pointer;display: block " onclick="imgCollect()">图像采集</div>
		<div class="div_div"><img src="/styles/imagepims/listprint.png" style="cursor:pointer;display: block" onclick="listPrint()">列表打印</div>
	</div>
<div class="row">
	<div>
		<h5 style="float: left;width: 33%"><strong>&nbsp;<img src="/styles/imagepims/worklist.png">&nbsp;工作列表</strong></h5>
		<h5 style="float: left;width: 50%"><strong>&nbsp;<img src="/styles/imagepims/piecemage.png">&nbsp;取材管理</strong>
			<span>&nbsp;&nbsp;取材医生:</span>
			<select id="doctor_id" style="width: 20%" onchange="searchDoctor('1')">
				<%out.println(request.getAttribute("piecesname"));%>
			</select>
			<span>&nbsp;&nbsp;录入人员:</span>
			<select id="input_user"style="width: 20%" onchange="searchDoctor('2')">
				<%out.println(request.getAttribute("piecesname"));%>
			</select>
		</h5>
		<h5 style="float: left;width: 17%"><strong>&nbsp;<img src="/styles/imagepims/imgget0.png">&nbsp;图像采集</strong></h5>
	</div>
	<div class="col-sm-4 leftContent" id="div_2">
		<div id="search_div_1" style="background-color: #E8E8E8">
		<table>
			<span>&nbsp;登记年月:&nbsp;</span>
			<input type="hidden" id="local_userid" value="${local_userid}"/><!--当前登录用户id-->
			<input type="hidden" id="local_username" value="${local_username}"/><!--当前登录用户名称-->
			<input type="text" class="form_datetime" value="${sevenday}" id="req_bf_time"/>
			<span>-</span>
			<input type="text" class="form_datetime" value="${receivetime}"  id="req_af_time"/>
		</table>
		<table>
			<span >&nbsp;病理号码:&nbsp;</span>
			<input type="text" id="send_dept"/>
		</table>
		<table>
			<span>&nbsp;病人姓名:&nbsp;</span>
			<input type="text" id="patient_name"/>
		</table>
		<table>
			<span>&nbsp;取材状态:&nbsp;</span>
			<input type="radio" name="req_sts" value="0" checked/>&nbsp;未取&nbsp;
			<input type="radio" name="req_sts" value="1"/>&nbsp;已取&nbsp;
		</table>
		<table>
			<span>&nbsp;补取医嘱:&nbsp;</span>&nbsp;&nbsp;
			<input type="checkbox" id="send_doctor" value="1"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<span>
				<button type="button" class="btn btn-xs  btn-info" onclick="searchList()">
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
	<div class="col-sm-6 rightContent" id="formDialog">
		<form class="form-horizontal"  action="#" method="post" id="sampleForm" >
			<div style="background-color: #E8E8E8">
			<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
				<label class="col-sm-2 control-label no-padding-left" for="sampathologycode">病理编号:</label>
				<div class="col-sm-4">
					<input type="hidden" id="sampathologyid"><!--病种类别-->
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
			<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
				<h5 style="float: left"><strong>&nbsp;材块列表&nbsp;&nbsp;</strong>
				<button type="button" class="btn btn-xs btn-info" id="addrow1" onclick="addRow()">
					追加行
				</button>
				<button type="button" class="btn btn-xs btn-info" onclick="delRow()">
					删除行
				</button>
				<button type="button" class="btn btn-xs btn-info" onclick="saveInfo(0)">
					保存
				</button>
				</h5>
			</div>
			<div class="form-group " style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
				<table id="new1" class="table table-striped table-bordered table-hover">
				</table>
			</div>
			<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
				<h5 style="float: left;width: 60%"><strong>&nbsp;巨检所见&nbsp;&nbsp;&nbsp;&nbsp;</strong>
					<input type="text" id="jjsj" style="width: 40%"/>
					<button type="button" class="btn btn-xs btn-info" onclick="saveAsTemplate(0,'samjjsj')">
						模版保存
					</button>
				</h5>
			</div>
			<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
				<textarea id="samjjsj" style="width: 100%;height: 90px"></textarea>
			</div>
		</form>
	</div>
	<div class="col-sm-2 rightContent">
	</div>
</div>
<div style="text-align: left;margin-left:5px;display:none" id="templateForm">
	<div style="text-align: left">
		<div>
			<div class="form-group" style="margin-top:5px;display:inline">
				<label class="control-label no-padding-right">关键字：</label>
				<input id="temkey" type="text">
			</div>
			<div class="form-group" style="margin-top:5px;display:inline">
				<label class="control-label no-padding-right">私有模板:</label>
				<input type="checkbox" checked id="owner">
			</div>
		</div>
		<div>
			<div class="form-group" style="margin-top:5px;display:inline">
				<label class="control-label no-padding-right">拼音码：</label>
				<input id="tempinyin" type="text">
			</div>
			<div class="form-group" style="margin-top:5px;display:inline">
				<label class="control-label no-padding-right">五笔码:</label>
				<input type="text" checked id="temfivestroke">
			</div>
		</div>
		<div>
			<div class="form-group" style="margin-top:5px;display:inline">
				<label class="control-label no-padding-right">简&nbsp;&nbsp;&nbsp; 码：</label>
				<input id="temspellcode" type="text">
			</div>
			<div class="form-group" style="margin-top:5px;display:inline">
				<label class="control-label no-padding-right">排序号:</label>
				A<select id="FN" name="FN">
				<option value="0">0</option>
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5">5</option>
				<option value="6">6</option>
				<option value="7">7</option>
				<option value="8">8</option>
				<option value="9">9</option>
			</select>
				<select id="SN" name="SN">
					<option value="0">0</option>
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5">5</option>
					<option value="6">6</option>
					<option value="7">7</option>
					<option value="8">8</option>
					<option value="9">9</option>
				</select>
				<select id="TN" name="TN">
					<option value="0">0</option>
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5">5</option>
					<option value="6">6</option>
					<option value="7">7</option>
					<option value="8">8</option>
					<option value="9">9</option>
				</select>
				<input type="hidden" id="temsort" name="temsort"/>
			</div>
		</div>
		<div class="form-group" style="margin-top:5px;">
			<label class="control-label no-padding-right">模板内容：</label>
			<textarea id="temcontent" cols="80" rows="3"></textarea>
		</div>
	</div>
</div>
</body>