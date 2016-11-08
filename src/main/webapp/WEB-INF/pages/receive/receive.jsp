<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<head>
    <title>站内信</title>
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/bootstrap-datetimepicker.min.css'/>" />
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/bootstrap.min.css'/>" />
	<script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
	<script type="text/javascript" src="../scripts/bootstrap.min.js"></script>
	<script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
	<script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
	<script type="text/javascript" src="<c:url value="/scripts/ace.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/ace-elements.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/bootstrap-tag.min.js"/>"></script>
    <script type="text/javascript" src="../scripts/layer/layer.js"></script>
    <script type="text/javascript" src="../scripts/receive/receive.js"></script>
	<script type="text/javascript" src="../scripts/bootstrap-datetimepicker.min.js"></script>
	<script type="text/javascript" src="../scripts/jquery.zclip.min.js"></script>
	<style>
		.ui-autocomplete {z-index: 99999999;  }
		.div_div {float:left;margin:20px 35px 11px 8px;text-align:center;color: #808080;font-size: 12px;  }
		.div_img{cursor:pointer;display: block;margin-bottom:11px;}
		.div_1{background-color: #F9F9F9;height: 106px;border:1px solid #E0E0E0}
		.img_style{width: 18px;height: 23px}
		.label_style{font-size: 12px;color: #323232;height: 24px;text-align:right;}
		.input_style{height: 24px;font-size: 12px!important;}
		.ui-jqgrid-sortable{text-align: center;}
		.ui-jqgrid-hbox{padding-right: 0px!important;}
	</style>
</head>
<body style="font-family:'Microsoft YaHei',微软雅黑,'MicrosoftJhengHei'!important;">
	<h5 style="font-size: 14px;"><strong>&nbsp;基本信息</strong></h5>
	<div class="row widget-main" style="background-color: #E8E8E8;border:1px solid #E0E0E0;" id="div_0">
		<div style="margin-top: 10px;">
			<table style="margin-bottom: 5px">
				<span class="input_style">发送时间:</span>
				<input type="hidden" id="req_sts" value="0"/>
				<input type="text" class="form_datetime input_style" id="req_bf_time"/>
				<span class="input_style">-</span>
				<input type="text" class="form_datetime input_style" id="req_af_time"/>
				<span style="float: right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
				<span>
					<button type="button" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;float: right" onclick="searchList()">
						<span style="color: white;">查询</span>
					</button>
				</span>
			</table>
		</div>
	</div>
	<table>
		<h5 style="font-size: 14px;"><strong>&nbsp;消息一览</strong></h5>
	</table>
	<ul id="tabs" class="nav nav-tabs">
		<li class="active">
			<a href="0" data-toggle="tab">
				未接收
			</a>
		</li>
		<li>
			<a href="1" data-toggle="tab">
				已接收
			</a>
		</li>
		<li>
			<a href="" data-toggle="tab">
				全部
			</a>
		</li>
	</ul>
	<div class="row" id="main_id">
		<div>
			<div class="widget-body" style="overflow:auto;">
				<div class="widget-main no-padding">
					<table id="new" class="table">
					</table>
					<div id="pager"></div>
				</div>
			</div>
		</div>
	</div>

<div id="formDialog" style="display:none;" class="col-sm-12">
	<form class="form-horizontal" style="background-color: #F9F9F9;height: 430px;border:1px solid #E0E0E0;" action="#" method="post" id="sampleForm" >
		<div class="form-group" style="margin-top: 10px;margin-bottom: 5px">
			<label class="col-sm-2 label_style">消息内容:</label>
			<div class="col-sm-10">
				<textarea id="mescontent" style="height: 55px;font-size:12px;width: 80%"></textarea>
			</div>
		</div>
		<div class="form-group" style="margin-bottom: 5px;">
			<label class="col-sm-2 label_style">发布人:</label>
			<div class="col-sm-4 ">
				<input type="text" class="input_style" id="messendername" readonly/>
			</div>
			<label style="font-size: 13px;"  class="col-sm-2 label_style">发布时间:</label>
			<div class="col-sm-3">
				<input type="text" class="form_datetime1 input_style" id="meshandletime"/>
			</div>
		</div>
	</form>
</div>
</body>