<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<head>
    <title><fmt:message key="SpecimenReg.title"/></title>
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
	<script src="<c:url value="/scripts/LodopFuncs.js"/>"></script>
	<script type="text/javascript" src="../scripts/pathologysample/fee.js"></script>
	<style>
		.div_div {float:left;margin:20px 35px 11px 8px;text-align:center;color: #808080;font-size: 12px;  }
		.div_img{cursor:pointer;display: block;margin-bottom:11px;}
		.div_1{background-color: #F9F9F9;height: 106px;border:1px solid #E0E0E0}
		.img_style{width: 18px;height: 23px}
		.label_style{font-size: 12px;color: #323232;height: 24px;text-align:right;}
		.input_style{height: 24px;font-size: 12px!important;}
		.ui-jqgrid-sortable{text-align: center;}
		.ui-jqgrid-hbox{padding-right: 0px!important;}
		.ui-jqgrid-labels{border-right: 1px solid #E1E1E1}
	</style>
</head>
<body style="font-family:'Microsoft YaHei',微软雅黑,'MicrosoftJhengHei'!important;">
	<div class="div_1" id="div_1">
			<div class="div_div"><img src="/styles/imagepims/add.png" class="div_img" onclick="addSample()">新增</div>
			<div class="div_div"><img src="/styles/imagepims/edit.png" class="div_img" id="editButton">修改</div>
			<div class="div_div"><img src="/styles/imagepims/delete.png" class="div_img" id="deleteButton">删除</div>
			<div class="div_div"><img src="/styles/imagepims/save.png" class="div_img" id="saveButton" onclick="saveInfo()">保存</div>
			<div class="div_div"><img src="/styles/imagepims/print.png" class="div_img" onclick="printCode()">打印</div>
			<div class="div_div"><img src="/styles/imagepims/up.png" class="div_img" onclick="upSample()">上一个</div>
			<div class="div_div"><img src="/styles/imagepims/down.png" class="div_img" onclick="downSample()">下一个</div>
			<div class="div_div"><img src="/styles/imagepims/fee.png" class="div_img" id="hisbutton">计费调整</div>
	</div>
	<div  class="row" id="userGrid" style="display: none;">
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
		<div class="col-xs-12">
			<table id="feediv"></table>
		</div>
	</div>
	<div id="formDialog1" style="display:none;" class="col-sm-12">
		<form class="form-horizontal" style="background-color: #F9F9F9;border:1px solid #E0E0E0;"  action="#" method="post" id="sampleForm1" >
			<div class="form-group" style="margin-top:10px;margin-bottom: 5px;">
				<label class="col-sm-1 label_style" for="requisitionno">申请单号:</label>
				<div class="col-sm-3">
					<input type="hidden" id="requisitionid"/><!--申请id-->
					<input type="hidden" id="reqcustomerid"><!--客户id-->
					<input type="hidden" id="reqcustomercode"><!--客户id-->
					<input type="hidden" id="reqsource"/><!--申请单来源-->
					<input type="hidden" id="reqdatechar"/><!--申请日期（8位日期字符）-->
					<input type="hidden" id="reqdigcode"/><!-- 诊疗小组代码或者费用归属科室代码-->
					<input type="hidden" id="reqchargestatus"/><!--收费状态(1已收费,0未收费) -->
					<input type="hidden" id="reqisemergency"/><!-- 是否加急（0平 1加急）-->
					<input type="hidden" id="reqstate"/><!--申请状态(0已保存,1已接收,9已删除) -->
					<%--<input type="hidden" id="reqwardname"/><!-- 申请病区名称-->--%>
					<input type="hidden" id="reqpatientid"/><!--患者id唯一号(病案号) -->
					<input type="hidden" id="reqinpatientid"/><!--就诊id(患者每一次来院的就诊id) -->
					<input type="hidden" id="reqinpatientno"/><!-- 住院序号(住院次数)-->
					<input type="hidden" id="reqpatbirthday"/><!--出生日期-->
					<input type="hidden" id="reqpatidcard"/><!-- 身份证号-->
					<input type="hidden" id="reqisdeleted"/><!--是否删除(0正常，1已删除) -->
					<input type="hidden" id="reqcreateuser"/><!--创建人员-->
					<input type="hidden" id="reqcreatetime"/><!-- 创建时间-->
					<input type="text" class="col-sm-9 input_style"  id="requisitionno" name="requisitionno" value="${requisitionno}" readonly/>
				</div>
				<label class=" col-sm-1 label_style" for="reqitemnames">检查项目:</label>
				<div class="col-sm-3 ">
					<input type="hidden" id="reqitemids"/>
					<input type="text" class="col-sm-9 input_style" id="reqitemnames" name="reqitemnames"  datatype="s1-16"/>
				</div>
				<label class="col-sm-1 label_style" >病种类别:</label>
				<div class="col-sm-3">
					<select class="col-sm-9 input_style" id="reqpathologyid" disabled>
						<%out.println(request.getAttribute("logyids"));%>
					</select>
				</div>
			</div>
			<div class="form-group" style="margin-bottom: 5px;">
				<label class="col-sm-1 label_style" for="reqtype">申请类型:</label>
				<div class="col-sm-3 ">
					<select class="col-sm-9 input_style" id="reqtype">
						<option value="1">住院</option>
						<option value="2">门诊</option>
						<option value="3">手术室</option>
					</select>
				</div>
				<label class="col-sm-1 label_style">申请病区:</label>
				<div class="col-sm-3 ">
					<input type="hidden" id="reqwardcode"/>
					<input type="text" class="col-sm-9 input_style" id="reqwardname" name="reqwardname"/><!-- 申请病区名称-->
				</div>
				<label class="col-sm-1 label_style" for="reqpatienttype">患者类型:</label>
				<div class="col-sm-3 ">
					<select class="col-sm-9 input_style" id="reqpatienttype"><!--患者类型(病人类型： 1住院,2门诊,3体检,4婚检,5科研,6特勤,7其他)-->
						<option value="1">住院</option>
						<option value="2">门诊</option>
						<option value="3">体检</option>
						<option value="4">婚检</option>
						<option value="5">科研</option>
						<option value="6">特勤</option>
						<option value="7">其他</option>
					</select>
				</div>
			</div>
			<div class="form-group" style="margin-bottom: 5px;">
				<label class="col-sm-1 label_style" for="reqinspectionid">病理编号:</label>
				<div class="col-sm-3 ">
					<input type="text" class="col-sm-9 input_style"  id="reqinspectionid" name="reqinspectionid" readonly/>
				</div>
				<label class="col-sm-1 label_style">送检医院:</label>
				<div class="col-sm-3 ">
					<input type="text" class="col-sm-9 input_style" id="reqsendhospital"/>
				</div>
				<label class="col-sm-1 label_style" for="reqdeptcode">送检科室:</label>
				<div class="col-sm-3 ">
					<input type="hidden" id="reqdeptcode"/><!--申请科室名称-->
					<input  type="text" class="col-sm-9 input_style" id="reqdeptname"/><!--申请科室名称-->
				</div>
			</div>
			<div class="form-group" style="z-index: 99999999;margin-bottom: 5px;">
				<label class="col-sm-1 label_style" for="reqdoctorid">送检医生:</label>
				<div class="col-sm-3 ">
					<input type="hidden" id="reqdoctorid"/><!--申请医生姓名 -->
					<input   type="text"  class="col-sm-9 input_style" id="reqdoctorname"/><!--申请医生姓名 -->
				</div>
				<label class="col-sm-1 label_style" for="reqsendphone">送检电话:</label>
				<div class="col-sm-3 ">
					<input type="text" class="col-sm-9 input_style"  id="reqsendphone" name="reqsendphone"/>
				</div>
				<label class="col-sm-1 label_style" >送检时间:</label>
				<div class="col-sm-3">
					<input type="text" class="col-sm-9 form_datetime1 input_style" id="reqdate" />
				</div>
			</div>
			<div class="form-group" style="margin-bottom: 5px;">
				<label class="col-sm-1 label_style" for="reqplanexectime">接收时间:</label>
				<div class="col-sm-3">
					<input type="text" class="col-sm-9 input_style"  id="reqplanexectime" name="reqplanexectime" readonly/>
				</div>
				<label class="col-sm-1 label_style" for="reqsecondv">手术医生:</label>
				<div class="col-sm-3">
					<input   type="text" class="col-sm-9 input_style" id="reqsecondv"/><!--手术医生 -->
				</div>
				<label class="col-sm-1 label_style" for="reqthirdv">手术电话:</label>
				<div class="col-sm-3">
					<input type="text" class="col-sm-9 input_style"  id="reqthirdv" name="reqthirdv"/>
				</div>
			</div>
			<div class="form-group" style="margin-bottom: 5px;">
				<label class="col-sm-1 label_style" for="reqfirstd">手术时间:</label>
				<div class="col-sm-3 ">
					<input type="text" class="col-sm-9 form_datetime1 input_style"  id="reqfirstd" name="reqfirstd"/>
				</div>
				<label class="col-sm-1 label_style" for="reqremark">手术所见:</label>
				<div class="col-sm-3 ">
					<input type="text" class="col-sm-9 input_style"  id="reqremark" name="reqremark"/>
				</div>
			</div>
			<div class="form-group" style="margin-bottom: 5px;">
				<label class="col-sm-1 label_style" fpr="reqfirstv">知情书:</label>
				<div class="col-sm-3">
					<select class="col-sm-9 input_style" id="reqfirstv">
						<option value="1">已签</option>
						<option value="2">未签</option>
					</select>
				</div>
				<label class="col-sm-1 label_style" for="reqpatientname">病人姓名:</label>
				<div class="col-sm-3">
					<input type="text" class="col-sm-9 input_style"  id="reqpatientname"/>
				</div>
				<label class="col-sm-1 label_style">性别:</label>
				<div class="col-sm-3">
					<select class="col-sm-9 input_style" id="reqpatientsex">
						<option value="0">男</option>
						<option value="1">女</option>
						<option value="">未知</option>
					</select>
				</div>
			</div>
			<div class="form-group" style="margin-bottom: 5px;">
				<label class="col-sm-1 label_style" >年龄:</label>
				<div class="col-sm-3">
				<span class="input_style" style="width:75%">
					<input type="text" class="input_style" id="reqpatientage" style="float:left;width:50%"/>
					<select  class="input_style" style="float:left;width:25%" id="reqpatagetype">
						<option value="1">岁</option>
						<option value="2">月</option>
						<option value="4">周</option>
						<option value="5">日</option>
						<option value="6">小时</option>
					</select>
				</span>
				</div>
				<label class="col-sm-1 label_style" for="reqpatientnumber">住院号:</label>
				<div class="col-sm-3">
					<input type="text"  class="col-sm-9 input_style" id="reqpatientnumber"/>
				</div>
				<label class="col-sm-1 label_style" for="reqfirstn">床号:</label>
				<div class="col-sm-3">
					<input type="text" class="col-sm-9 input_style"  id="reqfirstn" />
				</div>
			</div>
			<div class="form-group" style="margin-bottom: 5px;">
				<label class="col-sm-1 label_style" for="reqpattelephone">联系电话:</label>
				<div class="col-sm-3">
					<input type="text"  class="col-sm-9 input_style" id="reqpattelephone"/>
				</div>
				<label class="col-sm-1 label_style">邮编:</label>
				<div class="col-sm-3">
					<input type="text" class="col-sm-9 input_style"  class="form_datetime1"/>
				</div>
				<label class="col-sm-1 label_style" for="reqpataddress">联系地址:</label>
				<div class="col-sm-3">
					<input type="text" class="col-sm-9 input_style"  id="reqpataddress" />
				</div>
			</div>
			<div class="form-group" style="margin-bottom: 5px;">
				<label class="col-sm-1 label_style" for="reqpatdiagnosis">临床诊断:</label>
				<div class="col-sm-11">
					<textarea id="reqpatdiagnosis" style="height: 55px;font-size: 12px;width: 80%" class="col-sm-9" ></textarea>
				</div>
			</div>
			<div class="form-group" style="margin-bottom: 5px;">
				<label class="col-sm-1 label_style" for="reqpatcompany">检查要求:</label>
				<div class="col-sm-11">
					<textarea id="reqpatcompany" style="height: 55px;font-size: 12px;width: 80%" class="col-sm-9" ></textarea>
				</div>
			</div>
		</form>
		<div class="form-horizontal">
			<h5 style="font-size: 14px;"><strong>&nbsp;电子申请单列表</strong></h5>
			<div style="margin-top: 14px;height:1px;background-color: #108CCF;"></div>
				<div class="col-sm-5 leftContent" style="margin-top: 5px">
					<h5 style="font-size: 14px;"><strong>&nbsp;组织信息</strong></h5>
					<div class="form-group " style="margin-right:0px;margin-left:0px;">
						<table id="new3" class="table-striped">
						</table>
					</div>
				</div>
				<div class="col-sm-7 rightContent" style="margin-top: 5px">
					<div class="form-group" style="margin-bottom: 5px">
						<label class="col-sm-2 label_style" for="X">X光:</label>
						<input type="text" class="col-sm-4 input_style" id="X"/>
						<label class="col-sm-2 label_style" for="CT">CT:</label>
						<input type="text" class="col-sm-4 input_style" id="CT"/>
					</div>
					<div class="form-group" style="margin-bottom: 5px">
						<label class="col-sm-2 label_style" for="B">B超:</label>
						<input type="text" class="col-sm-4 input_style" id="B"/>
						<label class="col-sm-2 label_style" for="previous">婚史:</label>
						<input type="text" class="col-sm-4 input_style" id="previous"/>
					</div>
					<div class="form-group" style="margin-bottom: 5px">
						<label class="col-sm-2 label_style" for="menses">月经初潮:</label>
						<input type="text" class="col-sm-4 input_style" id="menses"/>
						<label class="col-sm-2 label_style" for="cycle">周期:</label>
						<input type="text" class="col-sm-4 input_style" id="cycle"/>
					</div>
					<div class="form-group" style="margin-bottom: 5px">
						<label class="col-sm-2 label_style" for="cesarean">产史:</label>
						<input type="text" class="col-sm-4 input_style" id="cesarean"/>
						<label class="col-sm-2 label_style" for="endmenses">末次月经:</label>
						<input type="text" class="col-sm-4 input_style" id="endmenses"/>
					</div>
				</div>
		</div>
	</div>
	<div>
		<h5 style="float: left;width: 34%;font-size: 14px;"><strong>&nbsp;<img src="/styles/imagepims/list.png" class="img_style">&nbsp;已登记标本一览</strong></h5>
		<h5  style="float: left;width: 66%;font-size: 14px;margin-bottom: 12px"><strong>&nbsp;<img src="/styles/imagepims/main.png" class="img_style">&nbsp;标本登记</strong></h5>
	</div>
	<ul id="tabss" class="nav nav-tabs">
		<li class="active">
			<a href="#sample_id" data-toggle="tab">
				已登记标本
			</a>
		</li>
		<li>
			<a href="#req_id" data-toggle="tab">
				电子申请单
			</a>
		</li>
	</ul>
	<div id="div_main">
		<div id="sample_id" class="row col-sm-4 leftContent" style="display: none;">
			<div id = "search_div_1" style="background-color: #F9F9F9;height: 254px;border:1px solid #E0E0E0;">
				<div style="margin-top: 10px">
					<table style="margin-bottom: 5px;">
						<span style="width: 30%;" class="input_style">&nbsp;&nbsp;病种类别:&nbsp;&nbsp;</span>
						<input type="hidden" id="lcal_hosptail" value="${send_hosptail}"/>
						<input type="hidden" id="local_logyid" value="${logyid}"/>
						<input type="hidden" id="local_userid" value="${local_userid}"/>
						<input type="hidden" id="local_username" value="${local_username}"/>
						<select id="logyid" class="input_style">
							<%out.println((String) request.getAttribute("logyids"));%>
						</select>
					</table>
					<table style="margin-bottom: 5px">
						<span class="input_style">&nbsp;&nbsp;登记年月:&nbsp;&nbsp;</span>
						<input type="text" class="form_datetime input_style" placeholder="" value="${sevenday}" id="req_bf_time"/>
						<span>-</span>
						<input type="text" class="form_datetime input_style" placeholder="" value="${receivetime}"  id="req_af_time"/>
					</table>
					<table style="margin-bottom: 5px">
						<span class="input_style">&nbsp;&nbsp;病理编号:&nbsp;&nbsp;</span>
						<input type="text" id="send_dept" class="input_style"/>
					</table>
					<table style="margin-bottom: 5px">
						<span class="input_style">&nbsp;&nbsp;送检医院:&nbsp;&nbsp;</span>
						<input type="text" id="send_hosptail" class="input_style"/>
					</table>
					<table style="margin-bottom: 5px">
						<span class="input_style">&nbsp;&nbsp;送检医生:&nbsp;&nbsp;</span>
						<input type="text" id="send_doctor" class="input_style"/>
					</table>
					<table style="margin-bottom: 5px">
						<span class="input_style">&nbsp;&nbsp;病人姓名:&nbsp;&nbsp;</span>
						<input type="text" id="patient_name" class="input_style"/>
					</table>
					<table style="margin-bottom: 5px">
						<div class="input_style">
							<span class="input_style">&nbsp;&nbsp;标本状态:&nbsp;&nbsp;</span>
							<input type="radio"   value="1" name="req_sts" checked/>合格
							<input type="radio" value="2" name="req_sts"/>不合格&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<span style="float: right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
							<button type="button" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;float: right" onclick="searchList()">
								<span style="color: white;">查询</span>
							</button>
						</div>
					</table>
				</div>

			</div>
			<table>
				<div class="row">
					<div>
						<div class="widget-body" style="overflow:auto;">
							<div class="widget-main no-padding">
								<table id="new" class="table-striped">
								</table>
								<div id="pager"></div>
							</div>
						</div>
					</div>
				</div>
			</table>
		</div>
		<div id="req_id" class="row col-sm-4 leftContent" style="display: none;margin-left: 5px;">
			<table>
				<div>
					<div>
						<div class="widget-body" style="overflow:auto;">
							<div class="widget-main no-padding">
								<table id="new1" class="table-striped">
								</table>
								<%--<div id="pager1"></div>--%>
							</div>
						</div>
					</div>
				</div>
			</table>
		</div>
		<div id="formDialog" style="float: right;margin-top: 0px" class="col-sm-8 rightContent" >
			<form class="form-horizontal" style="background-color: #F9F9F9;height: 299px;border:1px solid #E0E0E0;"  action="#" method="post" id="sampleForm" >
				<button type="submit"id="saveButton1" style="display:none;">保存</button>
				<div class="form-group" style="margin-top: 10px;margin-bottom: 5px">
					<label  class="label_style col-sm-1" for="saminspectionid">条形码:</label>
					<div class="col-sm-3">
						<input type="hidden" id="sampleid"><!--标本id-->
						<input type="hidden" id="samcustomerid"/><!--客户id-->
						<input type="hidden" id="samcustomercode" /><!--客户id-->
						<input type="hidden" id="samsource"/><!--是否外送-->
						<input type="hidden" id="samotherid"/><!--第三方条码号-->
						<input type="hidden" id="samisdeleted"/><!--删除标记-->
						<input type="hidden" id="sampatientid" /><!--患者唯一号(病案号)-->
						<input type="hidden" id="saminpatientid" /><!--就诊id(患者每一次来院的id)-->
						<input type="hidden" id="saminpatientno" /><!--住院序号(住院次数)-->
						<input type="hidden" id="sampatienttype"/><!--患者类型-->
						<input type="hidden" id="samsampleclass"/><!--标本种类-->
						<input type="hidden" id="sampopuser"/><!--标本检查项目id-->
						<input type="hidden" id="samisemergency"/><!--是否加急-->
						<input type="hidden" id="samisdecacified"/><!--是否脱钙-->
						<input type="hidden" id="samissamplingall"/><!--是否全取-->
						<input type="hidden" id="samsamplestatus"/><!--标本状态-->
						<input type="hidden" id="samreqtime"/><!--申请时间-->
						<input type="hidden" id="samreqdocid"/><!--申请医生id-->
						<input type="hidden" id="samreqdocname"/><!--申请医生姓名-->
						<input type="hidden" id="samsendphone"/><!--送检联系电话-->
						<input type="hidden" id="samdigcode"/><!--诊疗小组代码-->
						<input type="hidden" id="samwardcode"/><!--病区代码-->
						<input type="hidden" id="samwardname"/><!--病区名称-->
						<input type="hidden" id="sampatientidcardno"/><!--身份证号-->
						<input type="hidden" id="sampatientcompany"/><!--工作单位-->
						<input type="hidden" id="samismenopause"/><!--是否绝经-->
						<input type="hidden" id="reqlastmenstruation"/><!--末次月经时间-->
						<input type="hidden" id="samischarged"/><!--收费状态-->
						<input type="hidden" id="samreceiverid"/><!--接收人员id-->
						<input type="hidden" id="samreceivername"/><!--接收人员姓名-->
						<input type="hidden" id="saminitiallytime"/><!--初诊时间-->
						<input type="hidden" id="saminitiallyuserid"/><!--初诊人员id-->
						<input type="hidden" id="saminitiallyusername"/><!--初诊人员姓名-->
						<input type="hidden" id="samcreatetime"/><!--创建时间-->
						<input type="hidden" id="samcreateuser"/><!--创建人员-->
						<input type="hidden" id="samregisttime"/><!--登记时间-->
						<input type="hidden" id="samregisterid"/><!--登记人员-->
						<input type="hidden" id="samregistername"/><!--登记人员姓名-->
						<input type="text" class="input_style" id="saminspectionid" name="saminspectionid" onkeypress="getData(this,event)" value="${saminspectionid}" readonly/>
					</div>
					<label class="label_style col-sm-1" for="samrequistionid">临床申请:</label>
					<div class="col-sm-3 ">
						<input class="input_style" type="text" id="samrequistionid" name="samrequistionid" onkeypress="getData(this,event)"/>
					</div>
					<label class="label_style col-sm-1" >病种类别:</label>
					<div class="col-sm-3">
						<select class="input_style col-sm-8" id="sampathologyid" disabled="disabled">
							<%out.println(request.getAttribute("logyids"));%>
						</select>
					</div>
				</div>
				<div class="form-group" style="margin-bottom: 5px">
					<label class="label_style col-sm-1" >病理编号:</label>
					<div class="col-sm-3">
						<input class="input_style" type="text" id="sampathologycode" name="sampathologycode" readonly/>
					</div>
					<label class="label_style col-sm-1" >病人姓名:</label>
					<div class="col-sm-3 ">
						<input class="input_style" type="text" id="sampatientname" name="sampatientname" datatype="*" />
					</div>
					<label class="label_style col-sm-1" >联系电话:</label>
					<div class="col-sm-3 ">
						<input class="input_style" type="text" id="sampatientphoneno" name="sampatientphoneno"/>
					</div>
				</div>
				<div class="form-group" style="margin-bottom: 5px;">
					<label class="col-sm-1 label_style" >住院号:</label>
					<div class="col-sm-3 ">
						<input class="input_style" type="text" id="sampatientnumber" name="sampatientnumber" datatype="*"/>
					</div>
					<label class="col-sm-1 label_style" >床号:</label>
					<div class="col-sm-3 ">
						<input class="input_style" type="text" id="sampatientbed" name="sampatientbed"  placeholder="床号" datatype="*"/>
					</div>
					<label class="col-sm-1 label_style" >联系地址:</label>
					<div class="col-sm-3">
						<input class="input_style" type="text" id="sampatientaddress" name="sampatientaddress"/>
					</div>
				</div>
				<div class="form-group" style="margin-bottom: 5px;">
					<label class="col-sm-1 label_style" for="sampatientsex">性&nbsp;别:</label>
					<div class="col-sm-3">
						<select class=" input_style col-sm-8" id="sampatientsex">
							<option value="0">男</option>
							<option value="1">女</option>
							<option value="2">未知</option>
						</select>
					</div>
					<label class="col-sm-1 label_style" for="sampatientage">年&nbsp;龄:</label>
					<div class="col-sm-3">
						<span class="label_style" style="width:90%">
							<input class="input_style" type="text" id="sampatientage" style="float:left;width:40%" name="sampatientage" datatype="n1-2"/>
							<select class="input_style" style="float:left;width:25%" id="sampatientagetype">
								<option value="1">岁</option>
								<option value="2">月</option>
								<option value="4">周</option>
								<option value="5">日</option>
								<option value="6">小时</option>
							</select>
						</span>
					</div>
					<label class="col-sm-1 label_style" >知情书:</label>
					<div class="col-sm-3 input_style">
						&nbsp;&nbsp;&nbsp;&nbsp;<input  type="radio"  value="1" name="samfirstv"/>&nbsp;&nbsp;已签&nbsp;&nbsp;
						<input type="radio" value="2" name="samfirstv"/>&nbsp;&nbsp;未签
					</div>
				</div>
				<div class="form-group" style="margin-bottom: 5px;">
					<label class="col-sm-1 label_style" >送检医生:</label>
					<div class="col-sm-3 ">
						<input type="hidden" id="samsenddoctorid"/>
						<input  class="input_style" type="text" id="samsenddoctorname" name="samsenddoctorname" datatype="*"/>
					</div>
					<label class="col-sm-1 label_style" for="samdeptcode">送检科室:</label>
					<div class="col-sm-3">
						<input type="hidden" id="samdeptcode"/><!--申请科室名称-->
						<input class="input_style" type="text" id="samdeptname" name="samdeptname" datatype="*"/><!--申请科室名称-->
					</div>
					<label class="col-sm-1 label_style" >送检医院:</label>
					<div class="col-sm-3 ">
						<input class="input_style" type="text" id="samsendhospital" name="samsendhospital" datatype="*"/>
					</div>
				</div>
				<div class="form-group" style="margin-bottom: 5px;">
					<label class="col-sm-1 label_style" >送检时间:</label>
					<div class="col-sm-3">
						<input  type="text" class="form_datetime1 input_style" value="${samsendtime}" id="samsendtime" name="samsendtime" datatype="*"/>
					</div>
					<label class="col-sm-1 label_style" for="samreceivertime">接收日期:</label>
					<div class="col-sm-3">
						<input type="text" class="form_datetime1 input_style" value="${samreceivertime}" id="samreceivertime" name="samreceivertime" datatype="*"/>
					</div>
					<label class="col-sm-1 label_style" for="samfirstn">组织袋数:</label>
					<div class="col-sm-3">
						<input class="input_style" type="text" id="samfirstn"  name="samfirstn" datatype="n1-2"/>
					</div>
				</div>
				<div class="form-group" style="margin-bottom: 5px;">
					<label style="font-size: 13px;"  class="col-sm-1 label_style">标本状态:</label>
					<div class="col-sm-3 input_style">
						&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" value="1" name="samsecondv"/>&nbsp;&nbsp;合格&nbsp;&nbsp;
						<input type="radio" value="2" name="samsecondv"/>&nbsp;&nbsp;不合格
					</div>
					<label class="col-sm-1 label_style" for="samremark">原因:</label>
					<div class="col-sm-7">
						<input  type="text" id="samremark" class="col-sm-10 label_style"/>
					</div>
				</div>
				<div class="form-group" style="margin-bottom: 5px;">
					<label class="col-sm-1 label_style" for="samsamplename">送检材料:</label>
					<div class="col-sm-3">
						<textarea id="samsamplename" style="height: 55px;font-size: 12px" name="samsamplename" datatype="*"></textarea>
					</div>
					<label class="col-sm-1 label_style" for="sampatientdignoses">临床诊断:</label>
					<div class="col-sm-3">
						<textarea id="sampatientdignoses" style="height: 55px;font-size: 12px" name="sampatientdignoses" datatype="*"></textarea>
					</div>
					<label class="col-sm-1 label_style" for="samthirdv">手术所见:</label>
					<div class="col-sm-3">
						<textarea id="samthirdv" style="height: 55px;font-size: 12px"></textarea>
					</div>
				</div>
			</form>
			<div style="margin-top: 14px;height:1px;background-color: #108CCF;"></div>
			<div class="widget-main no-padding">
				<h5 style="font-size: 14px;"><strong>&nbsp;费用列表</strong></h5>
				<table id="new2" class="table table-striped table-bordered table-hover">
				</table>
			</div>
		</div>
	</div>
</body>