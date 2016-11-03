<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="ElectronicApplyManage.title"/></title>
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
	<%--<script type="text/javascript" src="../scripts/consultation/cons1.js"></script>--%>
	<script src="<c:url value="/scripts/LodopFuncs.js"/>"></script>
	<style>
		/*select {*/
			/*height:34px;*/
		/*}*/
		.ui-autocomplete {
			z-index: 99999999;
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
	</style>
</head>
<script>
	$(window).resize(function () {          //当浏览器大小变化时
		alert($(window).height());          //浏览器时下窗口可视区域高度
		alert($(document).height());        //浏览器时下窗口文档的高度
		alert($(document.body).height());   //浏览器时下窗口文档body的高度
		alert($(document.body).outerHeight(true)); //浏览器时下窗口文档body的总高度 包括border padding margin
	});
</script>
<body>
	<div class="row" id="div_1">
		<div>
			<button type="button" class="btn-sm btn-info" onclick="addSample()">
				新增
			</button>
			<button type="button" class="btn-sm btn-info" id="editButton" onclick="editSample()">
				修改
			</button>
			<button type="button" class="btn-sm btn-info" id="deleteButton" onclick="deleteSample()">
				删除
			</button>
			<button type="button" class="btn-sm btn-info" id="saveButton" onclick="saveInfo()">
				保存
			</button>
			<button type="button" class="btn-sm btn-info" onclick="printCode()">
				打印
			</button>
			<button type="button" class="btn-sm btn-info" onclick="upSample()">
				上一个
			</button>
			<button type="button" class="btn-sm btn-info" onclick="downSample()">
				下一个
			</button>
			<button type="button" class="btn-sm btn-info" id="hisbutton" onclick="hisChange()">
				计费调整
			</button>
			<%--<button type="button" class="btn-sm btn-info" title="会诊管理" onclick="consMarage()">--%>
				<%--<i class="ace-icon fa fa-print bigger-110"></i>--%>
				<%--会诊管理--%>
			<%--</button>--%>
		</div>
	</div>
	<div  class="row" id="userGrid" style="display: none;">
		<h5 style="float: left;"><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;收费项目一览&nbsp;&nbsp;&nbsp;&nbsp;</strong></h5>
		<button type="button" class="btn-sm btn-info" onclick="addRow()">
			追加行
		</button>
		<button type="button" class="btn-sm btn-info" onclick="delRow()">
			删除行
		</button>
		<button type="button" class="btn-sm btn-info" onclick="savefeeRow('0')">
			保存
		</button>
		<button type="button" class="btn-sm btn-info" onclick="savefeeRow('1')">
			保存并发送
		</button>
		<div class="col-xs-12 leftContent">
			<table id="sectionList3"></table>
		</div>
	</div>
	<div id="formDialog1" style="display:none;" class="col-sm-12">
		<form class="form-horizontal"  action="#" method="post" id="sampleForm1" >
			<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
				<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-left" for="requisitionno">申请单号:</label>
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
					<input type="text" class="col-sm-9"  id="requisitionno" name="requisitionno" value="${requisitionno}" readonly/>
				</div>
				<label style="font-size: 13px;"  class=" col-sm-1 control-label no-padding-right" for="reqitemnames">检查项目:</label>
				<div class="col-sm-3 ">
					<input type="hidden" id="reqitemids"/>
					<input type="text" class="col-sm-9" id="reqitemnames" name="reqitemnames"  datatype="s1-16"/>
				</div>
				<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" >病种类别:</label>
				<div class="col-sm-3">
					<select class="col-sm-9" id="reqpathologyid" disabled>
						<%out.println(request.getAttribute("logyids"));%>
					</select>
				</div>
			</div>
			<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
				<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" for="reqtype">申请类型:</label>
				<div class="col-sm-3 ">
					<select class="col-sm-9" id="reqtype">
						<option value="1">住院</option>
						<option value="2">门诊</option>
						<option value="3">手术室</option>
					</select>
				</div>
				<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right">申请病区:</label>
				<div class="col-sm-3 ">
					<input type="hidden" id="reqwardcode"/>
					<input type="text" class="col-sm-9" id="reqwardname" name="reqwardname"/><!-- 申请病区名称-->
				</div>
				<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" for="reqpatienttype">患者类型:</label>
				<div class="col-sm-3 ">
					<select class="col-sm-9" id="reqpatienttype"><!--患者类型(病人类型： 1住院,2门诊,3体检,4婚检,5科研,6特勤,7其他)-->
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
			<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
				<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" for="reqinspectionid">病理编号:</label>
				<div class="col-sm-3 ">
					<input type="text" class="col-sm-9"  id="reqinspectionid" name="reqinspectionid" readonly/>
				</div>
				<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right">送检医院:</label>
				<div class="col-sm-3 ">
					<input type="text" class="col-sm-9" id="reqsendhospital"/>
				</div>
				<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" for="reqdeptcode">送检科室:</label>
				<div class="col-sm-3 ">
					<input type="hidden" id="reqdeptcode"/><!--申请科室名称-->
					<input  type="text" class="col-sm-9" id="reqdeptname"/><!--申请科室名称-->
				</div>
			</div>
			<div class="form-group" style="z-index: 99999999;margin-right:0px;margin-left:0px;margin-bottom: 0px;">
				<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" for="reqdoctorid">送检医生:</label>
				<div class="col-sm-3 ">
					<input type="hidden" id="reqdoctorid"/><!--申请医生姓名 -->
					<input   type="text"  class="col-sm-9" id="reqdoctorname"/><!--申请医生姓名 -->
				</div>
				<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" for="reqsendphone">送检电话:</label>
				<div class="col-sm-3 ">
					<input type="text" class="col-sm-9"  id="reqsendphone" name="reqsendphone"/>
				</div>
				<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" >送检时间:</label>
				<div class="col-sm-3">
					<input type="text" class="col-sm-9 form_datetime1" id="reqdate" />
				</div>
			</div>
			<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
				<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" for="reqplanexectime">接收时间:</label>
				<div class="col-sm-3">
					<input type="text" class="col-sm-9"  id="reqplanexectime" name="reqplanexectime" readonly/>
				</div>
				<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" for="reqsecondv">手术医生:</label>
				<div class="col-sm-3">
					<input   type="text" class="col-sm-9" style="max-height:200px;overflow-y:scroll;" id="reqsecondv"/><!--手术医生 -->
				</div>
				<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" for="reqthirdv">手术电话:</label>
				<div class="col-sm-3">
					<input type="text" class="col-sm-9"  id="reqthirdv" name="reqthirdv"/>
				</div>
			</div>
			<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
				<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" for="reqfirstd">手术时间:</label>
				<div class="col-sm-3 ">
					<input type="text" class="col-sm-9 form_datetime1"  id="reqfirstd" name="reqfirstd"/>
				</div>
				<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" for="reqremark">手术所见:</label>
				<div class="col-sm-3 ">
					<input type="text" class="col-sm-9"  id="reqremark" name="reqremark"/>
				</div>
			</div>
			<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
				<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" fpr="reqfirstv">知情书:</label>
				<div class="col-sm-3">
					<select class="col-sm-9" id="reqfirstv">
						<option value="1">已签</option>
						<option value="2">未签</option>
					</select>
				</div>
				<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" for="reqpatientname">病人姓名:</label>
				<div class="col-sm-3">
					<input type="text" class="col-sm-9"  id="reqpatientname"/>
				</div>
				<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right">性别:</label>
				<div class="col-sm-3">
					<select class="col-sm-9" id="reqpatientsex">
						<option value="0">男</option>
						<option value="1">女</option>
						<option value="">未知</option>
					</select>
				</div>
			</div>
			<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
				<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" >年龄:</label>
				<div class="col-sm-3">
				<span class="input-icon input-icon-right" style="width:75%">
					<input type="text" id="reqpatientage" style="float:left;width:60%"/>
					<select  style="float:left;width:40%" id="reqpatagetype">
						<option value="1">岁</option>
						<option value="2">月</option>
						<option value="4">周</option>
						<option value="5">日</option>
						<option value="6">小时</option>
					</select>
				</span>
				</div>
				<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" for="reqpatientnumber">住院号:</label>
				<div class="col-sm-3">
					<input type="text"  class="col-sm-9" id="reqpatientnumber"/>
				</div>
				<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" for="reqfirstn">床号:</label>
				<div class="col-sm-3">
					<input type="text" class="col-sm-9"  id="reqfirstn" />
				</div>
			</div>
			<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
				<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" for="reqpattelephone">联系电话:</label>
				<div class="col-sm-3">
					<input type="text"  class="col-sm-9" id="reqpattelephone"/>
				</div>
				<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right">邮编:</label>
				<div class="col-sm-3">
					<input type="text" class="col-sm-9"  class="form_datetime1"/>
				</div>
				<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" for="reqpataddress">联系地址:</label>
				<div class="col-sm-3">
					<input type="text" class="col-sm-9"  id="reqpataddress" />
				</div>
			</div>
			<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
				<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" for="reqpatdiagnosis">临床诊断:</label>
				<div class="col-sm-11">
					<textarea id="reqpatdiagnosis" style="height: 50px;width: 80%" class="col-sm-9" ></textarea>
				</div>
			</div>
			<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
				<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" for="reqpatcompany">检查要求:</label>
				<div class="col-sm-11">
					<textarea id="reqpatcompany" style="height: 50px;width: 80%" class="col-sm-9" ></textarea>
				</div>
			</div>
		</form>
		<div class="widget-main no-padding">
			<p1>电子申请单列表</p1>
			<div class="widget-box widget-color-green">
				<div class="widget-main no-padding  col-sm-5" style="margin-top: 5px">
					<div class="form-group" style="margin-right:0px;margin-left:0px;">
						<label class="control-label" style="text-align: left;">&nbsp;&nbsp;&nbsp;&nbsp;组织信息</label>
					</div>
					<div class="form-group " style="margin-right:0px;margin-left:0px;">
						<table id="new3" class="table table-striped table-bordered table-hover">
						</table>
					</div>
				</div>
				<div class="widget-main no-padding  col-sm-7" style="margin-top: 5px">
					<div class="form-group" style="margin-right:0px;margin-left:0px;">
						<label class="col-sm-2 control-label no-padding-right" for="X">X光:</label>
						<div class="col-sm-4">
							<input type="text" class="col-sm-12" id="X"/>
						</div>
						<label class="col-sm-2 control-label no-padding-right" for="CT">CT:</label>
						<div class="col-sm-4">
							<input type="text" class="col-sm-12 input-mask-date" id="CT"/>
						</div>
					</div>
					<div class="form-group" style="margin-right:0px;margin-left:0px;">
						<label class="col-sm-2 control-label no-padding-right" for="B">B超:</label>
						<div class="col-sm-4">
							<input type="text" class="col-sm-12" id="B"/>
						</div>
						<label class="col-sm-2 control-label no-padding-right" for="previous">婚史:</label>
						<div class="col-sm-4">
							<input type="text" class="col-sm-12 input-mask-date" id="previous"/>
						</div>
					</div>
					<div class="form-group" style="margin-right:0px;margin-left:0px;">
						<label class="col-sm-2 control-label no-padding-right" for="menses">月经初潮:</label>
						<div class="col-sm-4">
							<input type="text" class="col-sm-12" id="menses"/>
						</div>
						<label class="col-sm-2 control-label no-padding-right" for="cycle">周期:</label>
						<div class="col-sm-4">
							<input type="text" class="col-sm-12 input-mask-date" id="cycle"/>
						</div>
					</div>
					<div class="form-group" style="margin-right:0px;margin-left:0px;">
						<label class="col-sm-2 control-label no-padding-right" for="cesarean">产史:</label>
						<div class="col-sm-4">
							<input type="text" class="col-sm-12" id="cesarean"/>
						</div>
						<label class="col-sm-2 control-label no-padding-right" for="endmenses">末次月经</label>
						<div class="col-sm-4">
							<input type="text" class="col-sm-12 input-mask-date" id="endmenses"/>
						</div>
					</div>
				</div>
			</div>
			</table>
		</div>
	</div>
	<div class="row" id="div_main">
		<h5  style="float: left;width: 67%"><strong>&nbsp;标本登记</strong></h5><h5><strong>&nbsp;已登记标本一览</strong></h5>
		<%--<div class="widget-box widget-color-green"></div>--%>
		<div id="formDialog" class="col-sm-8 leftContent">
			<form class="form-horizontal"  style="background-color: #E8E8E8"  action="#" method="post" id="sampleForm" >
				<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
					<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-left" for="saminspectionid">条形码:</label>
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
						<input type="text" id="saminspectionid" name="saminspectionid" onkeypress="getData(this,event)" value="${saminspectionid}" readonly/>
					</div>
					<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" for="samrequistionid">临床申请:</label>
					<div class="col-sm-3 ">
						<input type="text" id="samrequistionid" name="samrequistionid"/>
					</div>
					<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" >病种类别:</label>
					<div class="col-sm-3">
						<select class="col-sm-9" id="sampathologyid" >
							<%String reslut = (String) request.getAttribute("logyids");
								out.println(reslut);
							%>
						</select>
					</div>
				</div>
				<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
					<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" >病理编号:</label>
					<div class="col-sm-3 ">
						<input type="text" id="sampathologycode" name="sampathologycode" readonly/>
					</div>
					<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" >病人姓名:</label>
					<div class="col-sm-3 ">
						<input type="text" id="sampatientname" name="sampatientname" />
					</div>
					<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" >联系电话:</label>
					<div class="col-sm-3 ">
						<input type="text" id="sampatientphoneno" name="sampatientphoneno"/>
					</div>
				</div>
				<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
					<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" >住院号:</label>
					<div class="col-sm-3 ">
						<input type="text" id="sampatientnumber" name="sampatientnumber"/>
					</div>
					<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" >床号:</label>
					<div class="col-sm-3 ">
						<input type="text" id="sampatientbed" name="sampatientbed"  placeholder="床号"/>
					</div>
					<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" >联系地址:</label>
					<div class="col-sm-3">
						<input type="text" id="sampatientaddress" name="sampatientaddress"/>
					</div>
				</div>
				<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
					<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" for="sampatientsex">性&nbsp;别:</label>
					<div class="col-sm-3">
						<select class="col-sm-9" id="sampatientsex">
							<option value="0">男</option>
							<option value="1">女</option>
							<option value="2">未知</option>
						</select>
					</div>
					<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" for="sampatientage">年&nbsp;龄:</label>
					<div class="col-sm-3">
						<span class="input-icon input-icon-right" style="width:90%">
							<input type="text" id="sampatientage" style="float:left;width:50%"/>
							<select  style="float:left;width:30%" id="sampatientagetype">
								<option value="1">岁</option>
								<option value="2">月</option>
								<option value="4">周</option>
								<option value="5">日</option>
								<option value="6">小时</option>
							</select>
						</span>
					</div>
					<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-left" >知情书:</label>
					<div class="col-sm-3">
						&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio"  value="1" name="samfirstv"/>&nbsp;&nbsp;已签&nbsp;&nbsp;
						<input type="radio" value="2" name="samfirstv"/>&nbsp;&nbsp;未签
					</div>
				</div>
				<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
					<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" >送检医生:</label>
					<div class="col-sm-3 ">
						<input type="hidden" id="samsenddoctorid"/>
						<input  type="text" id="samsenddoctorname"/>
					</div>
					<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" for="samdeptcode">送检科室:</label>
					<div class="col-sm-3">
						<input type="hidden" id="samdeptcode"/><!--申请科室名称-->
						<input  type="text" id="samdeptname"/><!--申请科室名称-->
					</div>
					<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" >送检医院:</label>
					<div class="col-sm-3 ">
						<input  type="text" id="samsendhospital"/>
					</div>
				</div>
				<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
					<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" >送检时间:</label>
					<div class="col-sm-3">
						<input type="text" class="form_datetime1" value="${samsendtime}" id="samsendtime"/>
					</div>
					<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" for="samreceivertime">接收日期:</label>
					<div class="col-sm-3">
						<input type="text" class="form_datetime1" value="${samreceivertime}" id="samreceivertime"/>
					</div>
					<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" for="samfirstn">组织袋数:</label>
					<div class="col-sm-3">
						<input type="text" id="samfirstn" />
					</div>
				</div>
				<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
					<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right">标本状态:</label>
					<div class="col-sm-3">
						&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" value="1" name="samsecondv"/>&nbsp;&nbsp;合格&nbsp;&nbsp;
						<input type="radio" value="2" name="samsecondv"/>&nbsp;&nbsp;不合格
					</div>
					<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" for="samremark">原因:</label>
					<div class="col-sm-7">
						<input type="text" id="samremark" class="col-sm-11"/>
					</div>
				</div>
				<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
					<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" for="samsamplename">送检材料:</label>
					<div class="col-sm-3">
						<textarea id="samsamplename"></textarea>
					</div>
					<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" for="sampatientdignoses">临床诊断:</label>
					<div class="col-sm-3">
						<textarea id="sampatientdignoses"></textarea>
					</div>
					<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" for="samthirdv">手术所见:</label>
					<div class="col-sm-3">
						<textarea id="samthirdv"></textarea>
					</div>
				</div>
			</form>
			<div class="widget-box widget-color-green"></div>
			<div class="widget-main no-padding">
				<h5><strong>&nbsp;电子申请单列表</strong></h5>
				<table id="new1" class="table table-striped table-bordered table-hover" style="height: 50px">
				</table>
				<div id="pager1"></div>
			</div>
			<div class="widget-box widget-color-green"></div>
			<div class="widget-main no-padding">
				<h5><strong>&nbsp;费用列表</strong></h5>
				<table id="new2" class="table table-striped table-bordered table-hover">
				</table>
			</div>
		</div>
		<div class="col-sm-4 rightContent" id="div_2">
			<div id = "search_div_1" style="background-color: #E8E8E8">
			<table>
				<span style="width: 30%;">&nbsp;&nbsp;病种类别:&nbsp;&nbsp;</span>
				<input type="hidden" id="lcal_hosptail" value="${send_hosptail}"/>
				<input type="hidden" id="local_logyid" value="${logyid}"/>
				<input type="hidden" id="local_userid" value="${local_userid}"/>
				<input type="hidden" id="local_username" value="${local_username}"/>
				<select id="logyid" class="input_style">
					<%out.println((String) request.getAttribute("logyids"));%>
				</select>
			</table>
			<table>
				<span>&nbsp;&nbsp;登记年月:&nbsp;&nbsp;</span>
				<input type="text" class="form_datetime input_style" placeholder="" value="${sevenday}" id="req_bf_time"/>
				<span>-</span>
				<input type="text" class="form_datetime input_style" placeholder="" value="${receivetime}"  id="req_af_time"/>
			</table>
			<table>
				<span>&nbsp;&nbsp;病理编号:&nbsp;&nbsp;</span>
				<input type="text" id="send_dept" class="input_style"/>
			</table>
			<table>
				<span>&nbsp;&nbsp;送检医院:&nbsp;&nbsp;</span>
				<input type="text" id="send_hosptail" class="input_style"/>
			</table>
			<table>
				<span >&nbsp;&nbsp;送检医生:&nbsp;&nbsp;</span>
				<input type="text" id="send_doctor" class="input_style"/>
			</table>
			<table>
				<span>&nbsp;&nbsp;病人姓名:&nbsp;&nbsp;</span>
				<input type="text" id="patient_name" class="input_style"/>
			</table>
			<table>
				<span>&nbsp;&nbsp;标本状态:&nbsp;&nbsp;</span>
				<input type="radio"   value="1" name="req_sts" checked/>合格
				<input type="radio" value="2" name="req_sts"/>不合格&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<button type="button" class="btn-sm btn-success input_style" title="查询信息" onclick="searchList()">
						查询
					</button>
			</table>
			</div>
			<table>
				<div class="row">
					<div>
						<div class="widget-body" style="overflow:auto;">
							<div class="widget-main no-padding">
								<table id="new" class="table table-striped table-bordered table-hover">
								</table>
								<div id="pager"></div>
							</div>
						</div>
					</div>
				</div>
			</table>
		</div>
	</div>
</body>