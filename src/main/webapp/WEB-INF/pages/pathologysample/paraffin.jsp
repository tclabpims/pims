<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="menu.embeddingManage"/></title>
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
    <script type="text/javascript" src="../scripts/pathologysample/paraffin.js"></script>
	<script type="text/javascript" src="../scripts/validform/Validform.min.js"></script>
	<script type="text/javascript" src="../scripts/bootstrap-datetimepicker.min.js"></script>
	<style>
		.ui-autocomplete {z-index: 99999999;}
		.div_div {float:left;margin:20px 35px 11px 8px;text-align:center;color: #808080;font-size: 12px;  }
		.div_img{cursor:pointer;display: block;margin-bottom:11px;}
		.div_1{background-color: #F9F9F9;height: 106px;border:1px solid #E0E0E0}
		.img_style{width: 18px;height: 23px}
		.label_style{font-size: 13px;color: #323232;height: 20px;text-align:right;}
		.input_style{height: 20px;font-size: 13px!important;}
		.ui-jqgrid-sortable{text-align: center;}
		.ui-jqgrid-hbox{padding-right: 0px!important;}
		#div_2{
			float: left;
			width: 25%;
			padding-left: 0px;
		}
		#formDialog{
			width: 74%;
			float: left;
			margin-left: 1%;
		}
	</style>
</head>
<body  style="font-family:'Microsoft YaHei',微软雅黑,'MicrosoftJhengHei'!important;">
	<div class="div_1" id="div_1" style="background:white;border:none;height:30px;">
            <button type="button" class="btn btn-sm btn-primary " title="包埋" id="saveButton">
                <i class="ace-icon fa fa-building bigger-110"></i>
                包埋
            </button>
            <button type="button" class="btn btn-sm btn-info " title="取消包埋" id="resetbutton">
                <i class="ace-icon fa fa-building-o bigger-110"></i>
                取消包埋
            </button>
		<!--<div class="div_div"><img src="/styles/imagepims/para.png" class="div_img"  id="saveButton">包埋</div>
		<div class="div_div"><img src="/styles/imagepims/canclepara.png" class="div_img" id="resetbutton">取消包埋</div>-->
	</div>
	<div>
		<div class="leftContent" id="div_2">
			<div id="search_div_1">
				<div class="widget-box widget-color-green ui-sortable-handle">
					<div class="widget-header">
						<div class="widget-title">工作列表</div>
						<div class="widget-toolbar">
							<a href="#" data-action="collapse" onclick="showandhiden(this)">
								<i class="ace-icon fa fa-chevron-up">隐藏</i>
							</a>
						</div>
					</div>
					<div class="widget-body" style="background-color: #F9F9F9;padding-top: 10px;padding-bottom: 10px">
						<div style="margin-bottom: 3px;">
							<span style="width: 30%;" class="input_style">&nbsp;&nbsp;病种类别:</span>
							<select id="logyid" class="input_style">
								<%out.println((String) request.getAttribute("logyids"));%>
							</select>
						</div>
						<div style="margin-bottom: 3px;">
							<span class="input_style">&nbsp;&nbsp;病理号码:</span>
							<input type="text" id="send_dept" class="input_style" value="${code}"/>
						</div>
						<div  style="margin-bottom: 3px;">
							<span class="input_style">&nbsp;&nbsp;取材年月:</span>
							<input type="hidden" id="req_sts" value="0">
							<input type="hidden" id="local_username" value="${local_username}"/>
							<input type="hidden" id="local_userid" value="${local_userid}"/>
							<%--<input type="hidden" id="logyid" value="${logyid}"/>--%>
							<input type="hidden" id="send_hosptail" value="${send_hosptail}"/>
							<input type="text" class="form_datetime input_style" value="${sevenday}" id="req_bf_time"/>
							<span class="input_style">-</span>
							<input type="text" class="form_datetime input_style" value="${receivetime}"  id="req_af_time"/>
						</div>
						<div style="margin-bottom: 0px;">
							<span class="input_style">&nbsp;&nbsp;病人姓名:</span>
							<input type="text" id="patient_name" class="input_style"/>
							<span class="input_style">&nbsp;&nbsp;内部医嘱:</span>
							<input type="checkbox" id="send_doctor" value="1"/>&nbsp;有&nbsp;
						<span>
							<button type="button" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;float: right;right: 5px" onclick="searchList()">
								<span style="color: white;">查询</span>
							</button>
						</span>
						</div>
					</div>

				</div>
			</div>
			<ul id="tabss" class="nav nav-tabs">
				<li class="active">
					<a href="0" data-toggle="tab">
						待包埋
					</a>
				</li>
				<li>
					<a href="1" data-toggle="tab">
						已包埋
					</a>
				</li>
			</ul>
			<div class="widget-main no-padding">
				<table id="new" class="table-striped">
				</table>
				<div id="pager"></div>
			</div>
		</div>
		<div class="rightContent" id="formDialog">
			<form class="form-horizontal"  action="#" method="post" id="sampleForm" >
				<div  class="widget-box widget-color-green ui-sortable-handle">
					<div class="widget-header">
						<div class="widget-title">包埋管理</div>
						<div class="widget-toolbar">
							<a href="#" data-action="collapse" onclick="showandhiden(this)">
								<i class="ace-icon fa fa-chevron-up">隐藏</i>
							</a>
						</div>
					</div>
					<div class="widget-body" style="background-color: #F9F9F9;padding-top: 10px;padding-bottom: 10px;">
						<div class="form-group" style="margin-bottom: 0px;">
							<label class="col-sm-1 label_style" for="sampathologycode">病理编号:</label>
							<div class="col-sm-2">
								<input type="hidden" id="sampleid"><!--标本id-->
								<input type="hidden" id="samcustomerid"><!--客户id-->
								<input type="hidden" id="samsamplestatus"><!--标本状态-->
								<input class="input_style" type="text" id="sampathologycode" name="sampathologycode" readonly/>
							</div>
							<label class="col-sm-1 label_style" >送检医生:</label>
							<div class="col-sm-2 ">
								<input class="input_style"  type="text" id="samsenddoctorname" readonly/>
							</div>
							<label class="col-sm-1 label_style">送检科室:</label>
							<div class="col-sm-2">
								<input  class="input_style" type="text" id="samdeptname" readonly/>
							</div>
							<label class="col-sm-1 label_style" for="samsamplename">送检材料:</label>
							<div class="col-sm-2">
								<input class="input_style col-sm-10" type="text" id="samsamplename" readonly/>
							</div>
						</div>
						<div class="form-group" style="margin-bottom: 0px;">
							<label class="col-sm-1 label_style" for="sampatientname">病人姓名:</label>
							<div class="col-sm-2 ">
								<input class="input_style" type="text" id="sampatientname" name="sampatientname" readonly/>
							</div>
							<label class="col-sm-1 label_style" >住院号:</label>
							<div class="col-sm-2 ">
								<input class="input_style" type="text" id="sampatientnumber" readonly/>
							</div>
							<label class="col-sm-1 label_style" >床号:</label>
							<div class="col-sm-2 ">
								<input class="input_style" type="text" id="sampatientbed" readonly/>
							</div>
							<label class="col-sm-1 label_style" for="sampatientsex">性&nbsp;别:</label>
							<div class="col-sm-2">
								<select class="col-sm-10 input_style" id="sampatientsex" disabled style="background-color:#f5f5f5;color: #666666;padding: 0">
									<option value="1">男</option>
									<option value="2">女</option>
									<option value="3">未知</option>
								</select>
							</div>
						</div>
						<div class="form-group" style="margin-bottom: 0px;">
							<label class="col-sm-1 label_style" for="sampatientdignoses">临床诊断:</label>
							<div class="col-sm-11">
								<input type="text" id="sampatientdignoses" readonly class="col-sm-10 input_style"/>
							</div>
						</div>
						<div class="form-group" style="margin-bottom: 0px;">
							<label class="col-sm-1 label_style" for="samthirdv">手术所见:</label>
							<div class="col-sm-11">
								<input type="text" id="samthirdv" readonly class="col-sm-10 input_style"/>
							</div>
						</div>
					</div>

				</div>
				<div style="margin-top: 14px;height:1px;background-color: #108CCF;"></div>
				<h5><strong style="font-size: 14px;">&nbsp;蜡块列表</strong></h5>
				<div class="widget-main no-padding">
					<table id="new1" class="table-striped">
					</table>
				</div>
			</form>
		</div>
	</div>
</body>