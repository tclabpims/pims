<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="menu.producerManage"/></title>
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
    <script type="text/javascript" src="../scripts/pathologysample/producer.js"></script>
	<script type="text/javascript" src="../scripts/validform/Validform.min.js"></script>
	<script type="text/javascript" src="../scripts/bootstrap-datetimepicker.min.js"></script>
	<script src="<c:url value="/scripts/LodopFuncs.js"/>"></script>
	<script type="text/javascript" src="../scripts/pathologysample/fee.js"></script>
	<style>
		.ui-autocomplete {z-index: 99999999;}
		/*.div_div {float:left;margin:20px 35px 11px 8px;text-align:center;color: #808080;font-size: 12px;  }*/
		/*.div_img{cursor:pointer;display: block;margin-bottom:11px;}*/
		/*.div_1{background-color: #F9F9F9;height: 106px;border:1px solid #E0E0E0}*/
		.img_style{width: 18px;height: 23px}
		.label_style{font-size: 12px;color: #323232;height: 24px;text-align:right;}
		.input_style{height: 24px;font-size: 12px!important;}
		.ui-jqgrid-sortable{text-align: center;}
		.ui-jqgrid-hbox{padding-right: 0px!important;}
	</style>
</head>
<body  style="font-family:'Microsoft YaHei',微软雅黑,'MicrosoftJhengHei'!important;">
	<div class="div_1" id="div_1">
		<botton  class="div_img btn btn-sm btn-primary" onclick="upSample()">
			<i class="ace-icon fa fa-arrow-left bigger-110"></i>
			上一个
		</botton>
		<botton  class="div_img btn btn-sm btn-info" onclick="downSample()">
			<i class="ace-icon fa fa-arrow-right bigger-110"></i>
			下一个
		</botton>
		<botton  class="div_img btn btn-sm btn-warning" onclick="printCode()">
			<i class="ace-icon fa fa-print bigger-110"></i>
			打印
		</botton>
		<botton  class="div_img btn btn-sm btn-success" id="saveButton">
			<i class="ace-icon fa fa-leaf bigger-110"></i>
			制片
		</botton>
	</div>
	<div class="row" id="userGrid1" style="display: none;">
		<div class="col-xs-12">
			<table id="sectionList4"></table>
			<div id="pager4"></div>
		</div>
	</div>
	<div>
		<div style="margin-top: 5px">
			<h5 style="float: left;width: 33%;font-size: 14px;background-color:#82af6f;margin-right:0.8%; min-height:38px;color: #ffffff;line-height: 38px;text-indent: 20px;margin-top:0px!important;" ><strong style="font-weight: nonrmal">工作列表</strong></h5>
			<h5 style="float: left;width: 66%;font-size: 14px;margin-bottom: 12px;min-height: 38px;color: #ffffff;background-color:#82af6f;line-height: 38px;text-indent: 20px;margin-top:0px!important;"><strong style="font-weight: nonrmal">制片管理</strong>
			</h5>
		</div>
		<div class="col-sm-4 leftContent" id="div_2">
			<div id="search_div_1" style="background-color: #F9F9F9;height: 121px;border:1px solid #E0E0E0;">
				<div style="margin-top:10px;">
					<table style="margin-bottom: 5px;">
						<span class="input_style">&nbsp;病种类别:&nbsp;</span>
						<select id="logyid" class="input_style">
							<%out.println((String) request.getAttribute("logyids"));%>
						</select>
					</table>
					<table style="margin-bottom: 5px;">
						<span class="input_style">&nbsp;登记年月:&nbsp;</span>
						<input type="hidden" id="req_sts" value="2">
						<input type="hidden" id="req_code" value="1">
						<input type="hidden" id="local_userid" value="${local_userid}"/><!--当前登录用户id-->
						<input type="hidden" id="local_username" value="${local_username}"/><!--当前登录用户名称-->
						<input type="text" class="form_datetime input_style" value="${sevenday}" id="req_bf_time"/>
						<span class="input_style">-</span>
						<input type="text" class="form_datetime input_style" value="${receivetime}"  id="req_af_time"/>
					</table>
					<table style="margin-bottom: 5px;">
						<span class="input_style">&nbsp;病理号码:&nbsp;</span>
						<input type="text" id="send_dept" class="input_style" value="${code}"/>
					</table>
					<table style="margin-bottom: 5px;">
						<span class="input_style">&nbsp;病人姓名:&nbsp;</span>
						<input type="text" id="patient_name" class="input_style"/>
						<button type="button" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;" onclick="searchList()">
							<span style="color: white;">查询</span>
						</button>
					</table>
				</div>
			</div>
			<ul id="tabss" class="nav nav-tabs">
				<li class="active">
					<a href="2" data-toggle="tab">
						未制片
					</a>
				</li>
				<li>
					<a href="3" data-toggle="tab">
						已制片
					</a>
				</li>
			</ul>
			<div class="widget-main no-padding">
				<table id="new" class="table-striped">
				</table>
				<div id="pager"></div>
			</div>
		</div>
		<div class="col-sm-8 rightContent" id="formDialog">
			<form class="form-horizontal" action="#" method="post" id="sampleForm" >
				<div style="background-color: #F9F9F9;height: 235px;border:1px solid #E0E0E0;" id="div_main">
					<div class="form-group" style="margin-top:10px;margin-bottom: 5px;">
						<label class="col-sm-2 label_style" for="sampathologycode">病理编号:</label>
						<div class="col-sm-4">
							<input type="hidden" id="sampathologyid"><!--病种类别-->
							<input type="hidden" id="sampleid"><!--标本id-->
							<input type="hidden" id="samcustomerid"><!--客户id-->
							<input type="hidden" id="samsamplestatus"><!--标本状态-->
							<input type="text" class="input_style" id="sampathologycode" name="sampathologycode" readonly/>
						</div>
						<label class="col-sm-2 label_style" >送检医生:</label>
						<div class="col-sm-4 ">
							<input class="input_style" type="text" id="samsenddoctorname" readonly/>
						</div>
					</div>
					<div class="form-group" style="margin-bottom: 5px;">
						<label class="col-sm-2 label_style" for="sampatientname">病人姓名:</label>
						<div class="col-sm-4 ">
							<input class="input_style" type="text" id="sampatientname" name="sampatientname" readonly/>
						</div>
						<label class="col-sm-2 label_style">送检科室:</label>
						<div class="col-sm-4">
							<input  class="input_style" type="text" id="samdeptname" readonly/>
						</div>
					</div>
					<div class="form-group" style="margin-bottom: 5px;">
						<label class="col-sm-2 label_style" >住院号:</label>
						<div class="col-sm-4 ">
							<input class="input_style" type="text" id="sampatientnumber" readonly/>
						</div>
						<label class="col-sm-2 label_style" for="samsamplename">送检材料:</label>
						<div class="col-sm-4">
							<input class="input_style" type="text" id="samsamplename" readonly/>
						</div>
					</div>
					<div class="form-group" style="margin-bottom: 5px;">
						<label class="col-sm-2 label_style" >床号:</label>
						<div class="col-sm-4 ">
							<input class="input_style" type="text" id="sampatientbed" readonly/>
						</div>
						<label class="col-sm-2 label_style" for="sampatientsex">性&nbsp;别:</label>
						<div class="col-sm-4">
							<select class="col-sm-8 input_style" id="sampatientsex" disabled>
								<option value="1">男</option>
								<option value="2">女</option>
								<option value="3">未知</option>
							</select>
						</div>
					</div>
					<div class="form-group" style="margin-bottom: 5px;">
						<label class="col-sm-2 label_style" for="sampatientdignoses">临床诊断:</label>
						<div class="col-sm-10">
							<input type="text" id="sampatientdignoses" readonly class="col-sm-10 input_style"/>
						</div>
					</div>
					<div class="form-group" style="margin-bottom: 5px;">
						<label class="col-sm-2 label_style" for="samthirdv">手术所见:</label>
						<div class="col-sm-10">
							<input type="text" id="samthirdv" readonly class="col-sm-10 input_style"/>
						</div>
					</div>
				</div>
				<div style="margin-top: 14px;height:1px;background-color: #108CCF;"></div>
				<div class="widget-main no-padding">
					<h5><strong style="font-size: 14px;">&nbsp;制片列表</strong>
						<button type="button" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;height: 25px;" id="addrow1" onclick="addRow()">
							<span style="color: white">追加行</span>
						</button>
						<button type="button"  style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;height: 25px;" onclick="delRow()">
							<span style="color: white">删除行</span>
						</button>
						<button type="button"  style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;height: 25px" onclick="saveInfo(0)">
							<span style="color: white">保存</span>
						</button>
					</h5>
				</div>
				<div class="widget-main no-padding">
					<table id="new1" class="table-striped">
					</table>
				</div>
			</form>
		</div>
	</div>
</body>