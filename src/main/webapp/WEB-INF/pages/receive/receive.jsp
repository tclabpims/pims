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
	<h5><strong>&nbsp;基本信息</strong></h5>
	<div class="row widget-main" style="height: 85px;background-color: #E8E8E8" id="div_0">
		<table>
			<span>发送时间:</span>
			<input type="hidden" id="req_sts"/>
			<input type="text" class="form_datetime" style="height: 28px" id="req_bf_time"/>
			<span>-</span>
			<input type="text" class="form_datetime" style="height: 28px" id="req_af_time"/>
			<span >&nbsp;&nbsp;&nbsp;&nbsp;</span>
			<button type="button" class="btn btn-xs btn-success" style="float: right" onclick="searchList()">
				查询
			</button>
		</table>
	</div>
	<table>
		<h5><strong>&nbsp;消息一览</strong></h5>
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
	<form class="form-horizontal"  action="#" method="post" id="sampleForm" >
		<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
			<label style="font-size: 13px;"  class="col-sm-2 control-label no-padding-right">消息内容:</label>
			<div class="col-sm-10">
				<textarea id="mescontent" style="height: 50px;width: 100%"></textarea>
			</div>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
			<label style="font-size: 13px;"  class="col-sm-2 control-label no-padding-right">发布人:</label>
			<div class="col-sm-4 ">
				<input type="text"  id="messendername" readonly/>
			</div>
			<label style="font-size: 13px;"  class="col-sm-2 control-label no-padding-right">发布时间:</label>
			<div class="col-sm-3">
				<input type="text" class="form_datetime1" id="meshandletime"/>
			</div>
		</div>
	</form>
</div>
</body>