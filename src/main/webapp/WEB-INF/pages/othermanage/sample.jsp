<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<head>
	<title><fmt:message key="menu.AdviceGlassPrint"/></title>
	<link rel="stylesheet" type="text/css" href="<c:url value='/styles/ui.jqgrid.css'/>"/>
	<link rel="stylesheet" type="text/css" href="<c:url value='/styles/bootstrap-datetimepicker.min.css'/>"/>
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
	<script type="text/javascript" src="../scripts/othermanage/orderprint.js"></script>
	<script type="text/javascript" src="../scripts/validform/Validform.min.js"></script>
	<script type="text/javascript" src="../scripts/bootstrap-datetimepicker.min.js"></script>
	<%--<script type="text/javascript" src="../scripts/consultation/cons1.js"></script>--%>
	<script src="<c:url value='/scripts/LodopFuncs.js'/>"></script>
	<style>
		/*select {*/
		/*height:34px;*/
		/*}*/
		.ui-autocomplete {
			z-index: 99999998;
		}
		.datetimepicker{
			z-index:1000000000;
		}
		/*label {*/
		/*font-size: 12px;*/
		/*}*/
		/*span{*/
		/*font-size: 12px;*/
		/*}*/
		/*.input_style{*/
		/*height: 28px;*/
		/*}*/
		.ui-jqgrid-sortable {
			text-align: center;
		}

		td {
			border-right: 1px solid #E0E0E0;
		}
		.form_datetime{
			z-index:99999999 !important;
		}
		.input_style {
			height: 24px;
			font-size: 12px !important;
		}
		#newsTable {
			border: 1px solid #E0E0E0;
			width: 100%;
			margin-top: 10px
		}
		.ui-jqgrid {
			border: 1px solid #ddd;
		}

		::-webkit-scrollbar-track {
			background-color: #F5F5F5
		}

		::-webkit-scrollbar {
			width: 6px;
			background-color: #F5F5F5
		}

		::-webkit-scrollbar-thumb {
			background-color: #999
		}

		.table-bordered > thead > tr > td, .table-bordered > thead > tr > th {
			background-color: #F5F5F6;
		}

		.ui-autocomplete {
			z-index: 99999998;
		}

		ul.tabs {
			margin: 0px;
			padding: 0px;
			list-style: none;
		}

		ul.tabs li {
			background: none;
			color: #222;
			display: inline-block;
			padding: 10px 15px;
			cursor: pointer;
		}

		ul.tabs li.current {
			background: #ededed;
			color: #222;
		}

		.tab-content {
			display: none;
			background: #ededed;
			padding: 15px;
		}

		.tab-content.current {
			display: inherit;
		}
		.ui-timepicker-div .ui-widget-header { margin-bottom: 8px; }
		.ui-timepicker-div dl { text-align: left; }
		.ui-timepicker-div dl dt { height: 25px; margin-bottom: -25px; }
		.ui-timepicker-div dl dd { margin: 0 10px 10px 65px; }
		.ui-timepicker-div td { font-size: 90%; }
		#deptandresult {
			width:100%;
			height:300px;
			overflow:scroll;
		}
		.result{
			position: relative;
			width:100%;
			height:100px;
		}
		.ta{
			resize:none;
			width:100%;
			height:100px;
		}
	</style>
</head>
<body>
<div id="maincontent">
	<div class="col-xs-12">
		<div>
			<div style="display:inline-block">
				<label>医嘱类型：</label>
				<select id="q_specialCheck" >
					<option value="">--请选择--</option>
				</select>
			</div>
			<div style="display:inline-block">
				<label>申请年月：</label>
				<input type="text" class="form_datetime1 input_style" id="applybftime"/>
			</div>
			<div style="display:inline-block">
				<input type="text" class="form_datetime1 input_style" id="applyaftime"/>
			</div>
		</div>
		<div>
			<div style="display:inline-block">
				<label>病理编号：</label>
				<input type="text" class="input_style" id="pathologycode"/>
			</div>
			<div style="display:inline-block">
				<label>申请医生：</label>
				<input id="chirequsername" style="border: 1px solid #b5b5b5;background:none!important;"></div>
		</div>
		<div>
			<div style="display:inline-block">
				<label>患者姓名：</label>
				<input type="text" class="input_style" id="patientname"/>
			</div>
			<div style="display:inline-block">
				<label>打印状态：</label>
				<select id="isprinted">
					<option value="">--请选择--</option>
					<option value="1">--已打印--</option>
					<option value="0">--未打印--</option>
				</select>
				<button type="button" class="btn btn-sm btn-primary" title="查询" onclick="getSearchList()">
					查询
				</button>
				<button type="button"  class="btn btn-sm btn-primary" title="打印" onclick="startPrint()">
					玻片打印
				</button>
			</div>
		</div>
	</div>
	<div id="tabs-2">
	<div class="row" id="materialGrid">
		<div class="col-xs-12">
			<table id="new"></table>
			<div id="pager2"></div>
		</div>
	</div>
</div>
</div>
</body>


