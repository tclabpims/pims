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
	<script src="<c:url value="/scripts/LodopFuncs.js"/>"></script>
	<style>
		.ui-autocomplete {z-index: 99999999;}
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
<body  style="font-family:'Microsoft YaHei',微软雅黑,'MicrosoftJhengHei'!important;">
	<div class="div_1" id="div_1">
		<div class="div_div"><img src="/styles/imagepims/up.png" class="div_img" onclick="upSample()">上一个</div>
		<div class="div_div"><img src="/styles/imagepims/down.png" class="div_img" onclick="downSample()">下一个</div>
		<div class="div_div"><img src="/styles/imagepims/print.png" class="div_img" onclick="printCode()">打印</div>
		<div class="div_div"><img src="/styles/imagepims/piece.png" class="div_img" id="saveButton" onclick="saveInfo(1)">取材</div>
		<div class="div_div"><img src="/styles/imagepims/fee.png" class="div_img" id="hisbutton" onclick="hisChange()">计费调整</div>
		<div class="div_div"><img src="/styles/imagepims/imgget.png" class="div_img" onclick="takingPicture()">图像采集</div>
		<div class="div_div"><img src="/styles/imagepims/listprint.png" class="div_img" onclick="printCode()">列表打印</div>
	</div>
	<div  class="row" id="userGrid" style="display: none;">
		<h5 style="float: left;"><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;收费项目一览&nbsp;&nbsp;&nbsp;&nbsp;</strong>
			<button type="button" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;height: 25px;"  onclick="addRow1()">
				<span style="color: white">追加行</span>
			</button>
			<button type="button" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;height: 25px;"  onclick="delRow1()">
				<span style="color: white">删除行</span>
			</button>
			<button type="button" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;height: 25px;"  onclick="savefeeRow('0')">
				<span style="color: white">保存</span>
			</button>
			<button type="button" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;height: 25px;"  onclick="savefeeRow('1')">
				<span style="color: white">保存并发送</span>
			</button>
		</h5>
		<div class="col-xs-12 leftContent">
			<table id="sectionList3"></table>
		</div>
	</div>
	<div>
		<div>
			<h5 style="float: left;width: 34%;font-size: 14px;"><strong>&nbsp;<img src="/styles/imagepims/worklist.png" class="img_style">&nbsp;工作列表</strong></h5>
			<h5 style="float: left;width: 50%;font-size: 14px;"><strong>&nbsp;<img src="/styles/imagepims/piecemage.png" class="img_style">&nbsp;取材管理</strong>
				<span class="input_style">&nbsp;&nbsp;取材医生:</span>
				<select id="doctor_id" class="input_style" style="width: 20%" onchange="searchDoctor('1')">
					<%out.println(request.getAttribute("piecesname"));%>
				</select>
				<span class="input_style">&nbsp;&nbsp;录入人员:</span>
				<select id="input_user" class="input_style" style="width: 20%" onchange="searchDoctor('2')">
					<%out.println(request.getAttribute("piecesname"));%>
				</select>
			</h5>
			<h5 style="float: left;width: 16%;font-size: 14px;margin-bottom: 12px"><strong>&nbsp;<img src="/styles/imagepims/imgget0.png" class="img_style">&nbsp;图像采集</strong></h5>
		</div>
		<div class="col-sm-4 leftContent" id="div_2">
			<div id="search_div_1" style="background-color: #F9F9F9;height: 121px;border:1px solid #E0E0E0;">
				<div style="margin-top:10px;">
					<table style="margin-bottom: 5px;">
						<span class="input_style">&nbsp;登记年月:&nbsp;</span>
						<input type="hidden" id="req_sts" value="0">
						<input type="hidden" id="local_userid" value="${local_userid}"/><!--当前登录用户id-->
						<input type="hidden" id="local_username" value="${local_username}"/><!--当前登录用户名称-->
						<input type="text" class="form_datetime input_style" value="${sevenday}" id="req_bf_time"/>
						<span class="input_style">-</span>
						<input type="text" class="form_datetime input_style" value="${receivetime}"  id="req_af_time"/>
					</table>
					<table style="margin-bottom: 5px;">
						<span class="input_style">&nbsp;病理号码:&nbsp;</span>
						<input type="text" id="send_dept" class="input_style"/>
					</table>
					<table style="margin-bottom: 5px;">
						<span class="input_style">&nbsp;病人姓名:&nbsp;</span>
						<input type="text" id="patient_name" class="input_style"/>
					</table>
					<%--<table style="margin-bottom: 5px;">--%>
						<%--<div class="input_style">--%>
							<%--<span class="input_style">&nbsp;取材状态:&nbsp;</span>--%>
							<%--<input type="radio" name="req_sts" value="0" checked/>&nbsp;未取&nbsp;--%>
							<%--<input type="radio" name="req_sts" value="1"/>&nbsp;已取&nbsp;--%>
						<%--</div>--%>
					<%--</table>--%>
					<table style="margin-bottom: 5px;">
						<div class="input_style">
							<span class="input_style">&nbsp;补取医嘱:&nbsp;</span>&nbsp;&nbsp;
							<input type="checkbox" id="send_doctor" value="1"/>&nbsp;&nbsp;
							<span style="float: right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
							<span>
								<button type="button" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;float: right" onclick="searchList()">
									<span style="color: white;">查询</span>
								</button>
							</span>
						</div>
					</table>
				</div>
			</div>
			<ul id="tabss" class="nav nav-tabs">
				<li class="active">
					<a href="0" data-toggle="tab">
						未取材
					</a>
				</li>
				<li>
					<a href="1" data-toggle="tab">
						已取材
					</a>
				</li>
			</ul>
			<div class="widget-main no-padding">
				<table id="new" class="table-striped">
				</table>
				<div id="pager"></div>
			</div>
		</div>
		<div class="col-sm-6 rightContent" id="formDialog">
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
						<label class="col-sm-2 label_style" >标本状态:</label>
						<div class="col-sm-4 input_style">
							<input type="checkbox" id="samissamplingall" value="1"/>&nbsp;&nbsp;全取&nbsp;&nbsp;
							<input type="checkbox" id="samisdecacified" value="1"/>&nbsp;&nbsp;脱钙&nbsp;&nbsp;
						</div>
					</div>
					<div class="form-group" style="margin-bottom: 5px;">
						<label class="col-sm-2 label_style" for="sampatientsex">性&nbsp;别:</label>
						<div class="col-sm-4">
							<select class="col-sm-8 input_style" id="sampatientsex" disabled>
								<option value="0">男</option>
								<option value="1">女</option>
								<option value="2">未知</option>
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
					<h5 style="float: left;width: 60%;font-size: 14px;"><strong>&nbsp;巨检所见&nbsp;&nbsp;&nbsp;&nbsp;</strong>
						<input type="text" id="jjsj" style="width: 40%"/>
						<button type="button" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;height: 25px" onclick="saveAsTemplate(0,'samjjsj')">
							<span style="color: white">模版保存</span>
						</button>
					</h5>
				</div>
				<div class="widget-main no-padding">
					<textarea id="samjjsj" style="width: 100%;height: 90px"></textarea>
				</div>
				<div style="margin-top: 14px;height:1px;background-color: #108CCF;"></div>
				<div class="widget-main no-padding">
					<h5><strong style="font-size: 14px;">&nbsp;材块列表</strong>
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
		<div class="col-sm-2 rightContent">
			<div id="imgContainer"></div>
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