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
		.label_style{font-size: 13px;color: #323232;height: 20px;text-align:right;}
		.input_style{height: 20px;font-size: 13px!important;width: 150px}
		.ui-jqgrid-sortable{text-align: center;}
		.ui-jqgrid-hbox{padding-right: 0px!important;}
		#div_2{
			width: 25%;
			float: left;
			padding-left: 0;
		}
		#formDialog{
			width: 74%;
			margin-left: 1%;
			float: left;
		}
		#jqgh_new1_cb{
			text-align: center;
			padding-bottom: 23px;
		}
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
		<botton  class="div_img btn btn-sm btn-warning" id="saveButton">
			<i class="ace-icon fa fa-leaf bigger-110"></i>
			制片
		</botton>
		<botton  class="div_img btn btn-sm btn-success" id="resetbutton">
			<i class="ace-icon fa fa-leaf bigger-110"></i>
			取消制片
		</botton>
		<botton  class="div_img btn btn-sm btn-info" id="printslide">
			<i class="ace-icon fa fa-print bigger-110"></i>
			制片打印
		</botton>
		<botton  class="div_img btn btn-sm btn-primary" onclick="printList()">
			<i class="ace-icon fa fa-print bigger-110"></i>
			列表打印
		</botton>
	</div>
	<div class="row" id="userGrid1" style="display: none;">
		<div class="col-xs-12">
			<table id="sectionList4"></table>
			<div id="pager4"></div>
		</div>
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
					<div class="widget-body" style="padding-top: 10px;padding-bottom: 10px;background-color: #f9f9f9">
						<div style="margin-bottom: 3px;">
							<span style="width: 30%;" class="input_style">&nbsp;病种类别:&nbsp;</span>
							<select id="logyid" class="input_style">
								<%out.println((String) request.getAttribute("logyids"));%>
							</select>
						</div>
						<div style="margin-bottom: 3px;">
							<span class="input_style">&nbsp;病理编号:&nbsp;</span>
							<input type="text" id="send_dept" class="input_style" value="${code}"/>
						</div>
						<div style="margin-bottom: 3px;">
							<span class="input_style">&nbsp;登记年月:&nbsp;</span>
							<input type="hidden" id="req_sts" value="2">
							<input type="hidden" id="req_code" value="1">
							<input type="hidden" id="local_userid" value="${local_userid}"/><!--当前登录用户id-->
							<input type="hidden" id="local_username" value="${local_username}"/><!--当前登录用户名称-->
							<input type="text" class="form_datetime input_style" value="${sevenday}" id="req_bf_time"/>
							<span class="input_style">-</span>
							<input type="text" class="form_datetime input_style" value="${receivetime}"  id="req_af_time"/>
						</div>
						<div style="margin-bottom: 3px;">
							<span class="input_style">&nbsp;打印状态:&nbsp;</span>
							<input type="hidden" id="send_hosptail">
							<input type="checkbox" id="send_hosptail1" value="0">&nbsp;待打印&nbsp;
							<input type="checkbox" id="send_hosptail2" value="1">&nbsp;已打印&nbsp;
						</div>
						<div style="margin-bottom: 3px;">
							<span class="input_style">&nbsp;患者姓名:&nbsp;</span>
							<input type="text" id="patient_name" class="input_style"/>
						</div>
						<div style="margin-bottom: 3px;">
							<button type="button" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;
							float: right;width:50px;padding: 0 2px;height: 20px!important;right: 5px;top: -20px" onclick="searchList()">
								<span style="color: white;">查询</span>
							</button>
						</div>
					</div>

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
		<div class=" rightContent" id="formDialog">
			<form class="form-horizontal" action="#" method="post" id="sampleForm" >
				<div class="widget-box widget-color-green ui-sortable-handle" id="div_main">
					<div class="widget-header">
						<div class="widget-title">工作列表</div>
						<div class="widget-toolbar">
							<a href="#" data-action="collapse" onclick="showandhiden(this)">
								<i class="ace-icon fa fa-chevron-up">隐藏</i>
							</a>
						</div>
					</div>
					<div class="widget-body"  style="padding-top: 10px;padding-bottom: 10px;background-color: #f9f9f9">
						<div class="form-group" style="margin-top:10px;margin-bottom: 0px;">
							<label class="col-sm-1 label_style" for="sampathologycode">病理编号:</label>
							<div class="col-sm-2">
								<input type="hidden" id="sampathologyid"><!--病种类别-->
								<input type="hidden" id="sampleid"><!--标本id-->
								<input type="hidden" id="samcustomerid"><!--客户id-->
								<input type="hidden" id="samsamplestatus"><!--标本状态-->
								<input type="text" class="input_style" id="sampathologycode" name="sampathologycode" readonly/>
							</div>
							<label class="col-sm-1 label_style" >送检医生:</label>
							<div class="col-sm-2 ">
								<input class="input_style" type="text" id="samsenddoctorname" readonly/>
							</div>
							<label class="col-sm-1 label_style">送检科室:</label>
							<div class="col-sm-2">
								<input  class="input_style" type="text" id="samdeptname" readonly/>
							</div>
							<label class="col-sm-1 label_style">送检单位:</label>
							<div class="col-sm-2">
								<input  class="input_style col-sm-10" type="text" id="samsendhospital" readonly/>
							</div>
						</div>
						<div class="form-group" style="margin-bottom: 0px;">
							<label class="col-sm-1 label_style" for="samsamplename">送检材料:</label>
							<div class="col-sm-2">
								<input class="input_style" type="text" id="samsamplename" readonly/>
							</div>
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
								<input class="input_style col-sm-10" type="text" id="sampatientbed" readonly/>
							</div>
						</div>
						<div class="form-group" style="margin-bottom: 0px;">
							<label class="col-sm-1 label_style">年龄:</label>
							<div class="col-sm-2 ">
								<input class="input_style" type="text" id="sampatientage" name="sampatientage" style="float:left;width:60%;" readonly/>
								<select class="input_style" style="float:left;width:40%" id="sampatientagetype">
									<option value="1">岁</option>
									<option value="2">月</option>
									<option value="4">周</option>
									<option value="5">日</option>
									<option value="6">小时</option>
								</select>
							</div>
							<label class="col-sm-1 label_style" for="sampatientsex">性&nbsp;别:</label>
							<div class="col-sm-2">
								<select class=" input_style col-sm-12" id="sampatientsex" disabled style="background-color:#f5f5f5;color: #666666;padding: 0">
									<option value="1">男</option>
									<option value="2">女</option>
									<option value="3">未知</option>
								</select>
							</div>
							<label class="col-sm-1 label_style" for="samthirdv">手术所见:</label>
							<div class="col-sm-5">
								<input type="text" id="samthirdv" readonly class="col-sm-10 input_style"/>
							</div>
						</div>
						<div class="form-group" style="margin-bottom: 0px;">
							<label class="col-sm-1 label_style" for="sampatientdignoses">临床诊断:</label>
							<div class="col-sm-11">
								<input type="text" id="sampatientdignoses" readonly class="col-sm-10 input_style"/>
							</div>
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
							<span style="color: white">制片</span>
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
	<div id="slidepinfo" style="display:none;" class="col-sm-12">

	</div>
</body>