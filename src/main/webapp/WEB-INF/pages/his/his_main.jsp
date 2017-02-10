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
	<script type="text/javascript" src="../scripts/validform/Validform.min.js"></script>
	<script type="text/javascript" src="../scripts/bootstrap-datetimepicker.min.js"></script>
	<script src="<c:url value="/scripts/LodopFuncs.js"/>"></script>
	<script type="text/javascript" src="../scripts/his/hisrequest1.js"></script>
	<style>
		.ui-autocomplete {
			z-index: 99999999 !important;
			max-height: 200px;
			overflow-y: auto;
			/* 防止水平滚动条 */
			overflow-x: hidden;
		}
		.form_datetime{
			z-index: 99999999 !important;
		}
		.div_div {float:left;margin:20px 35px 11px 8px;text-align:center;color: #808080;font-size: 12px;  }
		.div_img{cursor:pointer;display: block;margin-bottom:11px;}
		.div_1{background-color: #F9F9F9;height: 106px;border:1px solid #E0E0E0}
		.img_style{width: 18px;height: 23px}
		.label_style{font-size: 13px;color: #323232;height: 20px;text-align:right;}
		.input_style{height: 20px;font-size: 13px!important;}
		.ui-jqgrid-sortable{text-align: center;}
		.ui-jqgrid-hbox{padding-right: 0px!important;}
	</style>
</head>
<body style="font-family:'Microsoft YaHei',微软雅黑,'MicrosoftJhengHei'!important;">
	<div id="h5_1">
		<h5 style="float: left;width: 100%;background-clip:content-box;background-color:rgb(135,184,127);height:20px;font-size:15px;
		color:#fff;line-height:20px;margin-top:0px!important;"><strong>&nbsp;&nbsp;申请登记</strong></h5>
	</div>
	<div>
		<div class="col-sm-12" id="formDialog">
			<input type="hidden" id="logylibid" value="${reqpathologyid}"/><!--当前病理库-->
			<input type="hidden" id="item_source" value="${reqsource}"/><!--申请单来源-->
			<input type="hidden" id="lcal_hosptail" value="${reqcustomerid}"/><!--账号所属医院-->
			<input type="hidden" id="local_user" value="${local_user}"/><!--用户姓名-->
			<input type="hidden" id="local_userid" value="${local_userid}"/><!--用户ID-->
			<input type="hidden" id="brjzxh" value="${brjzxh}"/><!--门诊唯一号-->
			<input type="hidden" id="patient_type" value="${patient_type}"/><!--患者类型-->
			<input type="hidden" id="reqitemids2" value="${reqitemids}"/><!--检查项目ID-->
			<input type="hidden" id="reqitemnames2" value="${reqitemnames}"/><!--检查项目名称-->
			<form class="form-horizontal" action="#" method="post" id="sampleForm" onkeypress="JavaScript:return NoSubmit(event);">
				<div style="margin: 0px 0px 0px 0px">
					<h5 style="float:left;font-size: 12px;width: 100%">
						<strong>&nbsp;患者基本信息</strong>&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" id="printcodeid" onclick="printCode()" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;
							width:120px;padding: 0 2px;height: 20px!important;" disabled="disabled">打印知情同意书</button>
					</h5>
				</div>
				<div class="form-group" style="margin-bottom: 0px">
					<div class="col-sm-2">
						<label class="col-sm-5 label_style">住院门诊号:</label>
						<div class="col-sm-7">
							<input type="hidden" id="requisitionid"/><!--申请id-->
							<input type="hidden" id="reqcustomerid"><!--客户id-->
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
							<input type="hidden" id="reqtype" value="1"/><!--申请类型(是否手术)-->
							<input type="hidden" id="reqinspectionid"/><!--病理编号-->
							<input type="text" class=" input_style col-sm-12" id="reqpatientnumber"  name="reqpatientnumber"  datatype="*" onkeypress="getPatient(this,event)"/>
						</div>
					</div>
					<div class="col-sm-2">
						<label class=" col-sm-4 label_style" for="reqitemnames">姓名:</label>
						<div class="col-sm-8">
							<input type="text" class="input_style col-sm-12" id="reqpatientname"  name="reqpatientname"  datatype="*"/>
						</div>
					</div>
					<div class="col-sm-2">
						<label class="col-sm-4 label_style" >性别:</label>
						<div class=" col-sm-8">
							<select class="col-sm-12 input_style" id="reqpatientsex" onchange="changeSexinfo()">
								<option value="1">男</option>
								<option value="2">女</option>
								<option value="3">未知</option>
							</select>
						</div>
					</div>
					<div class="col-sm-2">
						<label class="col-sm-4 label_style" >年龄:</label>
						<div class="col-sm-8">
							<span class="input_style">
								<input type="text" id="reqpatientage" class="input_style" style="float:left;width:60%"  name="reqpatientage"  datatype="n1-2"/>
								<select class="input_style"  style="float:left;width: 40%" id="reqpatagetype">
									<option value="1">岁</option>
									<option value="2">月</option>
									<option value="4">周</option>
									<option value="5">日</option>
									<option value="6">小时</option>
								</select>
							</span>
						</div>
					</div>
					<div class="col-sm-4">
						<label class="col-sm-2 label_style">联系地址:</label>
						<div class="col-sm-10">
							<input type="text" class="input_style col-sm-11" id="reqpataddress"/>
						</div>
					</div>
				</div>
				<div class="form-group" style="margin-bottom: 0px;">
					<div class="col-sm-2">
						<label class="col-sm-5 label_style" for="reqpatienttype">患者类型:</label>
						<div class="col-sm-7">
							<select class="input_style col-sm-12" id="reqpatienttype"><!--患者类型(病人类型： 1住院,2门诊,3体检,4婚检,5科研,6特勤,7其他)-->
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
					<div class="col-sm-2">
						<label class="col-sm-4 label_style">病区:</label>
						<div class="col-sm-8">
							<%--<input type="text"  class="input_style" id="reqpatientwardcode"/>--%>
							<span style="overflow:hidden;width: 105%" class="input_style  col-sm-12" >
								<select class="input_style col-sm-12" id="reqpatientwardcode1"
										onchange="fillval(null,'reqpatientwardcode',null,'reqpatientwardcode1')">
									<%out.print(request.getAttribute("reqwardname"));%>
								</select>
							</span>
							<input id="reqpatientwardcode"  name="reqpatientwardcode" datatype="*" style="position:absolute;left:5px;width: 82%;" class="input_style">
						</div>
					</div>
					<div class="col-sm-2">
						<label class="col-sm-4 label_style">科室:</label>
						<div class="col-sm-8">
							<%--<input type="text" class="input_style" id="reqpatientdeptcode" name="reqpatientdeptcode" datatype="*"/>--%>
							<span style="overflow:hidden;width: 105%" class="input_style  col-sm-12">
								<select class="input_style  col-sm-12" id="reqpatientdeptcode1"
										onchange="fillval(null,'reqpatientdeptcode',null,'reqpatientdeptcode1')">
									<%out.print(request.getAttribute("samdeptname"));%>
								</select>
							</span>
							<input id="reqpatientdeptcode"  name="reqpatientdeptcode" datatype="*" style="position:absolute;left:5px;width: 82%;" class="input_style">
						</div>
					</div>
					<div class="col-sm-2">
						<label class="col-sm-4 label_style">床号:</label>
						<div class="col-sm-8">
							<input type="text" class="input_style col-sm-12" id="reqfirstn" name="reqfirstn"/>
						</div>
					</div>
					<div class="col-sm-2">
						<label class="col-sm-4 label_style" for="reqpattelephone">电话:</label>
						<div class="col-sm-8">
							<input type="text"  class="input_style col-sm-12" id="reqpattelephone"/>
						</div>
					</div>
					<div class="col-sm-2">
						<label class="col-sm-4 label_style" id="reqfirstv1">同意书:</label>
						<div class="col-sm-8">
							<select id="reqfirstv" class="input_style col-sm-10" style="display: none" name="reqfirstv" >
								<option value=" "></option>
								<option value="2">未签</option>
								<option value="1">已签</option>
							</select>
						</div>
					</div>
				</div>
				<div class="form-group" style="margin-bottom: 0px;">
					<div class="col-sm-2">
						<label class="col-sm-5 label_style">临床诊断:</label>
						<div class="col-sm-7">
							<textarea id="reqpatdiagnosis" style="height: 55px;font-size: 12px;width: 800px" name="reqpatdiagnosis"  datatype="*">${lczdmc}</textarea>
						</div>
					</div>
				</div>
				<div class="form-group" style="margin-top: 3px;">
					<div class="col-sm-2">
						<label class="col-sm-5 label_style">是否手术:</label>
						<div class="col-sm-7">
							<input type="checkbox" id="reqtype1" value="1" onclick="isshoushu()" checked>
						</div>
					</div>
					<div class="col-sm-2">
						<label class="col-sm-4 label_style" name="ssxx">手术时间:</label>
						<div class="col-sm-8">
							<input type="text" class="form_datetime1 input_style col-sm-12"  id="reqfirstd" name="ssxx"/>
						</div>
					</div>
					<div class="col-sm-2">
						<label class="col-sm-4 label_style" name="ssxx">手术医生:</label>
						<div class="col-sm-8" name="ssxx">
							<%--<input type="text" class="input_style" id="reqsecondv" name="ssxx"/><!--手术医生 -->--%>
							<span style="overflow:hidden;width: 105%" class="input_style  col-sm-12">
								<select class="input_style col-sm-12" id="reqsecondv1"  onchange="fillval(null,'reqsecondv',null,'reqsecondv1')">
									<%out.print(request.getAttribute("samsenddoctorname"));%>
								</select>
							</span>
							<input id="reqsecondv"  style="position:absolute;left:5px;width: 82%" class="input_style" >
						</div>
					</div>
					<div class="col-sm-2">
						<label class="col-sm-4 label_style" name="ssxx">手术电话:</label>
						<div class="col-sm-8">
							<input type="text" class="input_style col-sm-12"  id="reqthirdv" name="ssxx"/>
						</div>
					</div>
					<div class="col-sm-4">
						<label class="col-sm-2 label_style" name="ssxx">手术所见:</label>
						<div class="col-sm-10">
							<%--<textarea id="reqremark" style="height: 55px;font-size: 12px;width: 90%"></textarea>--%>
							<input type="text" class="input_style col-sm-11" id="reqremark" name="ssxx"/>
						</div>
					</div>
				</div>
				<div style="margin-top: 14px;height:1px;background-color: #108CCF;"></div>
				<div style="margin: 0px 0px 0px 0px">
					<h5 style="float:left;font-size: 12px;width: 100%">
						<strong>病理检查申请信息</strong>
					</h5>
				</div>
				<div class="form-group" style="z-index: 99999999;margin-bottom: 0px">
					<div class="col-sm-2">
						<label class="col-sm-5 label_style">申请单号:</label>
						<div class="col-sm-7">
							<input type="text" class="input_style col-sm-12"  id="requisitionno" name="requisitionno" value="${requisitionno}" readonly/>
						</div>
					</div>
					<div class="col-sm-2">
						<label class=" col-sm-4 label_style">检查项目:</label>
						<div class="col-sm-8">
							<input type="hidden" id="reqitemids" value="${reqitemids}"/>
							<%--<input type="text" class="input_style" id="reqitemnames" name="reqitemnames"  datatype="s1-16"/>--%>
							<span style="overflow:hidden;width: 105%" class="input_style  col-sm-12">
						<select class="input_style col-sm-12" id="reqitemids1" onchange="fillval('reqitemids','reqitemnames','reqpathologyid','reqitemids1')">
							<%out.print(request.getAttribute("samjcxm"));%>
						</select>
					</span>
							<input id="reqitemnames"  name="reqitemnames" datatype="*" style="position:absolute;left:5px;width: 82%" class="input_style" ${reqitemnames}>
						</div>
					</div>
					<div class="col-sm-2">
						<label class="col-sm-4 label_style" >病种类别:</label>
						<div class="col-sm-8">
							<select class="col-sm-12 input_style" id="reqpathologyid" disabled>
								<%out.println(request.getAttribute("logyids"));%>
							</select>
						</div>
					</div>
					<div class="col-sm-2">
						<label class="col-sm-4 label_style" >执行时间:</label>
						<div class="col-sm-8">
							<input type="text" class="form_datetime1 input_style col-sm-12" id="reqplanexectime"  name="reqplanexectime"  datatype="*"/>
						</div>
					</div>
					<div class="col-sm-2">
						<label class="col-sm-4 label_style">申请时间:</label>
						<div class="col-sm-8">
							<input type="text" class="input_style col-sm-12"  id="reqdate" name="reqdate" readonly/>
						</div>
					</div>
					<div class="col-sm-2">
						<label class="col-sm-4 label_style">申请电话:</label>
						<div class="col-sm-8">
							<input type="text" class="input_style col-sm-11"  id="reqsendphone" name="reqsendphone"/>
						</div>
					</div>
				</div>
				<div class="form-group" style="margin-bottom: 0px">
					<div class="col-sm-2">
						<label class="col-sm-5 label_style">申请医生:</label>
						<div class="col-sm-7">
							<input type="hidden" id="reqdoctorid" value="${reqdoctorid1}"/><!--申请医生姓名 -->
							<%--<input   type="text"  class="input_style" id="reqdoctorname" name="reqdoctorname"  datatype="*"/><!--申请医生姓名 -->--%>
							<span style="overflow:hidden;width: 105%" class="input_style  col-sm-12">
								<select class="input_style col-sm-12" id="reqdoctorid1" onchange="fillval('reqdoctorid','reqdoctorname',null,'reqdoctorid1')">
									<%out.print(request.getAttribute("samsenddoctorname"));%>
								</select>
							</span>
							<input id="reqdoctorname"  name="reqdoctorname" datatype="*" style="position:absolute;left:5px;width: 82%" class="input_style" value="${reqdoctorname1}" >
						</div>
					</div>
					<div class="col-sm-2">
						<label class="col-sm-4 label_style">申请医院:</label>
						<div class="col-sm-8">
							<%--<input type="text" class="input_style" id="reqsendhospital" name="reqsendhospital"  datatype="*"/>--%>
							<span style="overflow:hidden;width: 105%" class="input_style  col-sm-12">
								<select class="input_style col-sm-12" id="reqsendhospital1" onchange="fillval(null,'reqsendhospital',null,'reqsendhospital1')">
									<%out.print(request.getAttribute("samsendhospital"));%>
								</select>
							</span>
							<input id="reqsendhospital"  name="reqsendhospital" datatype="*" style="position:absolute;left:5px;width: 82%" class="input_style" value="${reqsendhospital1}">
						</div>
					</div>
					<div class="col-sm-2">
						<label class="col-sm-4 label_style" for="reqdeptcode">申请科室:</label>
						<div class="col-sm-8">
							<input type="hidden" id="reqdeptcode" value="${reqdeptcode1}"/><!--申请科室名称-->
							<%--<input  type="text" class="input_style" id="reqdeptname" name="reqdeptname"  datatype="*"/><!--申请科室名称-->--%>
							<span style="overflow:hidden;width: 105%" class="input_style  col-sm-12">
								<select class="input_style col-sm-12" id="reqdeptcode1" onchange="fillval('reqdeptcode','reqdeptname',null,'reqdeptcode1')">
									<%out.print(request.getAttribute("samdeptname"));%>
								</select>
							</span>
							<input id="reqdeptname"  name="reqdeptname" datatype="*" style="position:absolute;left:5px;width: 82%" class="input_style" value="${reqdeptname1}">
						</div>
					</div>
					<div class="col-sm-2">
						<label class="col-sm-4 label_style">申请病区:</label>
						<div class="col-sm-8">
							<input type="hidden" id="reqwardcode" value="${reqwardcode1}"/>
							<%--<input type="text" class="input_style" id="reqwardname" name="reqwardname" datatype="*"/><!-- 申请病区名称-->--%>
							<span style="overflow:hidden;width: 105%" class="input_style  col-sm-12">
								<select class="input_style col-sm-12" id="reqwardname1" onchange="fillval('reqwardcode','reqwardname',null,'reqwardname1')">
									<%out.print(request.getAttribute("reqwardname"));%>
								</select>
							</span>
							<input id="reqwardname"  name="reqwardname" datatype="*" style="position:absolute;left:5px;width: 82%" class="input_style" value="${reqwardname1}">
						</div>
					</div>
					<div class="col-sm-4">
						<label class="col-sm-2 label_style" for="reqpatcompany">检查要求:</label>
						<div class="col-sm-10">
							<input type="text" class="input_style col-sm-9"  id="reqpatcompany" name="reqpatcompany" />

							<%--<textarea id="reqpatcompany" style="height: 55px;font-size: 12px;width: 80%" class="col-sm-9" ></textarea>--%>
							<span style="float: right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
							<button type="submit" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;
							float: right;width:50px;padding: 0 2px;height: 20px!important;" id="savebutton">
								<span style="color: white;">保存</span>
							</button>
						</div>
					</div>
				</div>
			</form>
			<div class="form-horizontal">
				<h5 style="float:left;font-size: 12px;width: 50%">
					<strong>&nbsp;送检材料</strong>
				</h5>
				<h5 style="float:left;font-size: 12px;width: 50%"><strong>&nbsp;增项信息</strong></h5>
				<div style="margin-top: 14px;height:1px;background-color: #108CCF;"></div>
				<div class="leftContent  col-sm-6" style="margin-top: 0px">
					<div class="form-group" style="margin-right:0px;margin-left:0px;margin-top: 0px;margin-bottom: 2px;display: none" id="divnew">
						<button type="button" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;" onclick="addRow()">
							<span style="color: white;">追加行</span>
						</button>
						<button type="button" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;" onclick="delRow()">
							<span style="color: white;">删除行</span>
						</button>
					</div>
					<div class="form-group " style="margin-right:0px;margin-left:0px;margin-top: 0px;margin-bottom: 0px;display: none" id="divnew1" >
						<table id="new1" class="table-striped">
						</table>
					</div>
					<div id="divnew2" style="display: none"></div>
				</div>
				<div class="rightContent  col-sm-6" style="margin-top: 5px;">
					<%--<div id="dynamic_div2">--%>

					<%--</div>--%>
					<form id="sampleForm1">


					<div class="form-group" style="margin-bottom: 0px">
						<label class="col-sm-2 label_style">X光:</label>
						<textarea id="reqxray" class="col-sm-8" style="height: 55px;font-size: 12px;width: 80%" class="col-sm-9" onclick="getYxxinfo('DX','reqxray')"></textarea>
						<%--<input type="text" class="col-sm-10 input_style" id="reqxray"/>--%>
					</div>
					<div class="form-group" style="margin-bottom: 0px">
						<label class="col-sm-2 label_style">CT:</label>
						<textarea id="reqct" class="col-sm-8" style="height: 55px;font-size: 12px;width: 80%" class="col-sm-9" onclick="getYxxinfo('CT','reqct')"></textarea>
						<%--<input type="text" class="col-sm-10 input_style" id="reqct"/>--%>
					</div>
					<div class="form-group" style="margin-bottom: 0px">
						<label class="col-sm-2 label_style">B超:</label>
						<textarea id="reqbultrasonic" class="col-sm-8" style="height: 55px;font-size: 12px;width: 80%" class="col-sm-9" onclick="getYxxinfo('US','reqbultrasonic')"></textarea>
						<%--<input type="text" class="col-sm-10 input_style" id="reqbultrasonic"/>--%>
					</div>
					<%--<div class="form-group" style="margin: 0px 0px 0px 0px">--%>
					<%--<h5 style="float: left;font-size: 14px;">影像学检查</h5>--%>
					<%--</div>--%>
					<%--<div class="form-group" style="margin-bottom: 0px">--%>
					<%--<label class="col-sm-2 label_style" for="X">X光:</label>--%>
					<%--<input type="text" class="col-sm-10 input_style" id="X"/>--%>
					<%--</div>--%>
					<%--<div class="form-group" style="margin-bottom: 0px">--%>
					<%--<label class="col-sm-2 label_style" for="CT">CT:</label>--%>
					<%--<input type="text" class="col-sm-10 input_style" id="CT"/>--%>
					<%--</div>--%>
					<%--<div class="form-group" style="margin-bottom: 0px">--%>
					<%--<label class="col-sm-2 label_style" for="B">B超:</label>--%>
					<%--<input type="text" class="col-sm-10 input_style" id="B"/>--%>
					<%--</div>--%>
					<div id="sexinfo" style="display: none">
						<div class="form-group" style="margin: 0px 0px 0px 0px">
							<h5 style="float: left;font-size: 12px;">妇产科检查</h5>
						</div>
						<div class="form-group" style="margin-bottom: 0px">
							<label class="col-sm-2 label_style">婚史:</label>
							<select class="col-sm-2 input_style" id="reqprevious">
								<option value='0'>未婚</option>
								<option value='1'>已婚</option>
							</select>
							<label class="col-sm-2 label_style">月经初潮:</label>
							<input type="text" class="col-sm-2 input_style form_datetime" id="reqmenses"/>
							<label class="col-sm-2 label_style">周期:</label>
							<input type="text" class="col-sm-2 input_style" id="reqcycle"/>
						</div>
						<div class="form-group"  style="margin-bottom: 0px">
							<label class="col-sm-2 label_style">产史:</label>
							<select class="col-sm-2 input_style" id="reqcesarean">
								<option value='0'>无</option>
								<option value='1'>一胎</option>
								<option value='2'>二胎</option>
								<option value='3'>三胎</option>
								<option value='4'>四胎</option>
							</select>
							<label class="col-sm-2 label_style">末次月经:</label>
							<input type="text" class="col-sm-2 input_style form_datetime" id="reqlastmenstruation"/>
							<label class="col-sm-2 label_style">是否绝经:</label>
							<select class="col-sm-2 input_style" id="reqismenopause">
								<option value='0'>否</option>
								<option value='1'>是</option>
							</select>
						</div>
					</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div id="formDialog1" style="display:none;" class="col-sm-12">
		<div>
			<div class="widget-body" style="overflow:auto;">
				<div class="widget-main no-padding">
					<table id="new2" class="table">
					</table>
				</div>
			</div>
		</div>
	</div>
	<div id="formDialog2" style="display:none;" class="col-sm-12">
		<div>
			<div class="widget-body" style="overflow:auto;">
				<div class="widget-main no-padding">
					<table id="new3" class="table">
					</table>
				</div>
			</div>
		</div>
	</div>
</body>

