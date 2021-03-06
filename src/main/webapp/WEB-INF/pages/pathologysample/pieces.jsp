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
	<script type="text/javascript" src="../scripts/pathologysample/fee.js"></script>

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
		.btn-sm{padding:0 9px!important}
		#div_2{
			width: 25%;
			float: left;
		}
		#imgContainer{
			border:1px #c5c5c5 solid;
			min-height: 300px;
		}
		#formDialog{
			width: 74%;
			float: left;
			margin-left: 1%;
		}
		#gview_templateList{
			left: 15px;
		}
		#templateForm label{
			display: inline-block;
			width: 70px;
			text-align: right;
		}
	</style>
</head>
<body  style="font-family:'Microsoft YaHei',微软雅黑,'MicrosoftJhengHei'!important;">
	<div class="div_1" id="div_1" style="background:white;border:none;height:30px;">
            <button type="button" class="btn btn-sm btn-primary " title="上一个" onclick="upSample()">
                <i class="ace-icon fa fa-arrow-left bigger-110"></i>
                上一个
            </button>
            <button type="button" class="btn btn-sm btn-info " title="下一个" onclick="downSample()">
                <i class="ace-icon fa fa-arrow-right bigger-110"></i>
                下一个
            </button>
            <button type="button" class="btn btn-sm btn-warning " title="打印" onclick="printCode()">
                <i class="ace-icon fa fa-print bigger-110"></i>
                打印
            </button>
            <button type="button" class="btn btn-sm btn-success " title="取材" id="saveButton" onclick="saveInfo(1)">
                <i class="ace-icon fa fa-magic bigger-110"></i>
                取材
            </button>
            <button type="button" class="btn btn-sm btn-info " title="计费调整" id="hisbutton">
                <i class="ace-icon glyphicon glyphicon-pencil bigger-110"></i>
                计费调整
            </button>
            <%--<button type="button" class="btn btn-sm btn-primary " title="图像采集" onclick="takingPicture()">--%>
                <%--<i class="ace-icon fa fa-picture-o bigger-110"></i>--%>
                <%--图像采集--%>
            <%--</button>--%>
            <button type="button" class="btn btn-sm btn-info " title="列表打印" onclick="printCode()">
                <i class="ace-icon fa fa-file-text-o bigger-110"></i>
                列表打印
            </button>
		<!--<div class="div_div"><img src="/styles/imagepims/up.png" class="div_img" onclick="upSample()">上一个</div>
		<div class="div_div"><img src="/styles/imagepims/down.png" class="div_img" onclick="downSample()">下一个</div>
		<div class="div_div"><img src="/styles/imagepims/print.png" class="div_img" onclick="printCode()">打印</div>
		<div class="div_div"><img src="/styles/imagepims/piece.png" class="div_img" id="saveButton" onclick="saveInfo(1)">取材</div>
		<div class="div_div"><img src="/styles/imagepims/fee.png" class="div_img" id="hisbutton" onclick="hisChange()">计费调整</div>
		<div class="div_div"><img src="/styles/imagepims/imgget.png" class="div_img" onclick="takingPicture()">图像采集</div>
		<div class="div_div"><img src="/styles/imagepims/listprint.png" class="div_img" onclick="printCode()">列表打印</div>-->
	</div>
	<div  class="row" id="feeGrid" style="display: none;">
		<h5 style="float: left;"><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;收费项目一览&nbsp;&nbsp;&nbsp;&nbsp;</strong>
			<button type="button" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;height: 25px;"  onclick="addfeeRow()">
				<span style="color: white">追加行</span>
			</button>
			<button type="button" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;height: 25px;"  onclick="delfeeRow()">
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
			<table id="feediv"></table>
		</div>
	</div>
	<div>
		<div>
			<%--<h5 style="float: left;width: 74%;background-clip:content-box;background-color:rgb(135,184,127);height:40px;font-size:15px;color:#fff;line-height:40px;margin-top:0px!important;margin-left: 1%"><strong>&nbsp;&nbsp;取材管理</strong>--%>
				<%--<span class="input_style">&nbsp;&nbsp;取材医生:</span>--%>
				<%--<select id="doctor_id" class="input_style" style="width: 20%" onchange="searchDoctor('1')">--%>
					<%--<%out.println(request.getAttribute("piecesname"));%>--%>
				<%--</select>--%>
				<%--<span class="input_style">&nbsp;&nbsp;录入人员:</span>--%>
				<%--<select id="input_user" class="input_style" style="width: 20%" onchange="searchDoctor('2')">--%>
					<%--<%out.println(request.getAttribute("piecesname"));%>--%>
				<%--</select>--%>
			<%--</h5>--%>
			<%--<h5 style="float: left;width: 16.3%;background-clip:content-box;background-color:rgb(135,184,127);height:40px;font-size:15px;color:#fff;line-height:40px;margin-top:0px!important;"><strong>&nbsp;&nbsp;图像采集</strong></h5>--%>
		</div>
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
							<span class="input_style">&nbsp;病种类别:&nbsp;</span>
							<select id="logyid" class="input_style">
								<%out.println((String) request.getAttribute("logyids"));%>
							</select>
						</div>
						<div style="margin-bottom: 3px;">
							<span class="input_style">&nbsp;病理号码:&nbsp;</span>
							<input type="text" id="send_dept" class="input_style" value="${code}"/>
						</div>
						<div style="margin-bottom: 3px;">
							<span class="input_style">&nbsp;登记年月:&nbsp;</span>
							<input type="hidden" id="req_sts" value="0">
							<input type="hidden" id="local_userid" value="${local_userid}"/><!--当前登录用户id-->
							<input type="hidden" id="local_username" value="${local_username}"/><!--当前登录用户名称-->
							<input type="text" class="form_datetime input_style" value="${sevenday}" id="req_bf_time"/>
							<span class="input_style">-</span>
							<input type="text" class="form_datetime input_style" value="${receivetime}"  id="req_af_time"/>
						</div>
						<div style="margin-bottom: 0px;">
							<span class="input_style">&nbsp;病人姓名:&nbsp;</span>
							<input type="text" id="patient_name" class="input_style"/>
							<span class="input_style">&nbsp;补取医嘱:&nbsp;</span>&nbsp;&nbsp;
							<input type="checkbox" id="send_doctor" value="1"/>&nbsp;&nbsp;
						<span>
								<button type="button" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;
							float: right;width:50px;padding: 0 2px;height: 20px!important;right: 5px" onclick="searchList()">
									<span style="color: white;">查询</span>
								</button>
							</span>
						</div>
					</div>

					<%--<table style="margin-bottom: 0px;">--%>
						<%--<div class="input_style">--%>
							<%--<span class="input_style">&nbsp;取材状态:&nbsp;</span>--%>
							<%--<input type="radio" name="req_sts" value="0" checked/>&nbsp;未取&nbsp;--%>
							<%--<input type="radio" name="req_sts" value="1"/>&nbsp;已取&nbsp;--%>
						<%--</div>--%>
					<%--</table>--%>
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
		<div class="rightContent" id="formDialog">
			<form class="form-horizontal" action="#" method="post" id="sampleForm" >
				<div class="widget-box widget-color-green ui-sortable-handle" id="div_main">
					<div class="widget-header">
						<div class="widget-title">取材管理</div>
						<span class="input_style">&nbsp;&nbsp;取材医生:</span>
						<select id="doctor_id" class="input_style" style="width: 20%" onchange="searchDoctor('1')">
							<%out.println(request.getAttribute("piecesname"));%>
						</select>
						<span class="input_style">&nbsp;&nbsp;录入人员:</span>
						<select id="input_user" class="input_style" style="width: 20%" onchange="searchDoctor('2')">
							<%out.println(request.getAttribute("piecesname"));%>
						</select>
						<div class="widget-toolbar">
							<a href="#" data-action="collapse" onclick="showandhiden(this)">
								<i class="ace-icon fa fa-chevron-up">隐藏</i>
							</a>
						</div>
					</div>
					<div class="widget-body" style="background-color: #F9F9F9;padding-top: 10px;padding-bottom: 10px">
						<div class="form-group" style="margin-bottom: 0px;">
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
							<label class="col-sm-1 label_style" >标本状态:</label>
							<div class="col-sm-1 input_style">
								<input type="checkbox" id="samissamplingall" value="1"/>&nbsp;全取&nbsp;
							</div>
							<div class="col-sm-1 input_style">
								<input type="checkbox" id="samisdecacified" value="1"/>&nbsp;脱钙&nbsp;
							</div>
							<label class="col-sm-1 label_style" for="samthirdv">手术所见:</label>
							<div class="col-sm-8">
								<input type="text" id="samthirdv" readonly class="col-sm-11 input_style"/>
							</div>
						</div>
						<div class="form-group" style="margin-bottom: 0px;">
							<label class="col-sm-1 label_style" for="sampatientdignoses">临床诊断:</label>
							<div class="col-sm-11">
								<input type="text" id="sampatientdignoses" readonly class="col-sm-11 input_style"/>
							</div>
						</div>
					</div>

				</div>
				<div style="margin-top: 14px;height:1px;background-color: #108CCF;"></div>
				<div class="widget-main no-padding">
					<h5 style="float: left;width: 60%;font-size: 14px;"><strong>&nbsp;巨检所见&nbsp;&nbsp;&nbsp;&nbsp;</strong>
						<%--<input type="text" id="jjsj" style="width: 40%"/>--%>
						&nbsp;&nbsp;<button type="button" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;height: 25px" onclick="showTemplate(0,'jujiansuojian')">
							<span style="color: white">从模版选择</span>
						</button>
						<button type="button" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;height: 25px" onclick="saveAsTemplate(0,'samjjsj')">
							<span style="color: white">模版保存</span>
						</button>
					</h5>
				</div>
				<div class="widget-main no-padding">
					<textarea id="samjjsj" style="width: 100%;height: 50px"></textarea>
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
		<%--<div class="col-sm-2 rightContent">--%>
			<%--<div id="imgContainer"></div>--%>
		<%--</div>--%>
	</div>
	<div style="text-align: left;margin-left:5px;display:none;padding-top: 10px" id="templateForm">
		<div style="text-align: left">
			<div>
				<div class="form-group" style="margin-top:5px;display:inline">
					<label class="control-label no-padding-right">模板名称：</label>
					<input id="temfirstv" type="text">
				</div>
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
					<label class="control-label no-padding-right">五笔码：</label>
					<input type="text" checked id="temfivestroke">
				</div>
			</div>
			<div>
				<div class="form-group" style="margin-top:5px;display:inline">
					<label class="control-label no-padding-right">简码：</label>
					<input id="temspellcode" type="text">
				</div>
				<div class="form-group" style="margin-top:5px;display:inline">
					<label class="control-label no-padding-right">排序号：</label>
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
				<label class="control-label no-padding-right" style="vertical-align: top">模板内容：</label>
				<textarea id="temcontent" cols="80" rows="3"></textarea>
			</div>
		</div>
	</div>
	<div class="row" style="display:none" id="templateGrid">
		<div class="col-xs-12 leftContent">
			<table id="templateList"></table>
			<div id="templatePager"></div>
		</div>
	</div>
</body>