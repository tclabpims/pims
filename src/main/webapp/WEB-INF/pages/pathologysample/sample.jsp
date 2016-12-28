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
	<%--<link rel="stylesheet" type="text/css"  href="<c:url value='/scripts/select2-4.0.3/dist/css/select2.css'/>" />--%>
	<%--<script type="text/javascript" src="../scripts/select2-4.0.3/dist/js/select2.full.js"></script>--%>

	<style>
		.div_div {float:left;margin:20px 35px 11px 8px;text-align:center;color: #808080;font-size: 12px;  }
		.div_img{cursor:pointer;display: block;margin-bottom:11px;}
		.div_1{background-color: #F9F9F9;height: 106px;border:1px solid #E0E0E0}
		.img_style{width: 18px;height: 23px}
		.label_style{font-size: 12px;color: #323232;height: 24px;text-align:right;}
		.input_style{height: 24px;font-size: 12px!important;}
		.ui-autocomplete-input{height: 24px;font-size: 12px!important;border-color:#B5B5B5;border-width: 1px;color: #000000}
		.ui-jqgrid-sortable{text-align: center;}
		.ui-jqgrid-hbox{padding-right: 0px!important;}
		.ui-jqgrid-labels{border-right: 1px solid #E1E1E1}
		.btn-sm{padding:0 9px!important}
		tr button{background:#e9e9e9!important;padding:0 10px;border:1px solid #c2c2c2!important;}
	</style>
</head>
<body style="font-family:'Microsoft YaHei',微软雅黑,'MicrosoftJhengHei'!important;">
	<div class="div_1" id="div_1" style="background:white;border:none;height:30px;">
		<button type="button" class="btn btn-sm btn-primary " title="上一个" onclick="upSample()">
			<i class="ace-icon fa fa-arrow-left bigger-110"></i>
			上一个
		</button>
		<button type="button" class="btn btn-sm btn-info " title="下一个" onclick="downSample()">
			<i class="ace-icon fa fa-arrow-right bigger-110"></i>
			下一个
		</button>
		<button type="button" class="btn btn-sm btn-warning " title="新增" onclick="addSample()">
			<i class="ace-icon fa fa-plus-circle bigger-110"></i>
			新增
		</button>
		<%--<button type="button" class="btn btn-sm btn-info " title="修改" id="editButton">--%>
			<%--<i class="ace-icon fa fa-location-arrow bigger-110"></i>--%>
			<%--修改--%>
		<%--</button>--%>
		<button type="button" class="btn btn-sm btn-success " title="删除" id="deleteButton">
			<i class="ace-icon fa fa-times bigger-110"></i>
			删除
		</button>
		<button type="button" class="btn btn-sm btn-info " title="保存" id="saveButton" onclick="clickOther()">
			<i class="ace-icon fa fa-floppy-o bigger-110"></i>
			保存
		</button>
		<button type="button" class="btn btn-sm btn-primary " title="计费调整" id="hisbutton">
			<i class="ace-icon glyphicon glyphicon-pencil bigger-110"></i>
			计费调整
		</button>
		<button type="button" class="btn btn-sm btn-info " title="打印" onclick="printCode()">
			<i class="ace-icon fa fa-print bigger-110"></i>
			列表打印
		</button>
		<button type="button" class="btn btn-sm btn-info " title="打印" onclick="printCode1()">
			<i class="ace-icon fa fa-print bigger-110"></i>
			申请打印
		</button>
		<!--<div class="div_div"><img src="/styles/imagepims/add.png" class="div_img" onclick="addSample()">新增</div>
		<div class="div_div"><img src="/styles/imagepims/edit.png" class="div_img" id="editButton">修改</div>
		<div class="div_div"><img src="/styles/imagepims/delete.png" class="div_img" id="deleteButton">删除</div>
		<div class="div_div"><img src="/styles/imagepims/save.png" class="div_img" id="saveButton" onclick="clickOther()">保存</div>
		<div class="div_div"><img src="/styles/imagepims/print.png" class="div_img" onclick="printCode()">打印</div>
		<div class="div_div"><img src="/styles/imagepims/up.png" class="div_img" onclick="upSample()">上一个</div>
		<div class="div_div"><img src="/styles/imagepims/down.png" class="div_img" onclick="downSample()">下一个</div>
		<div class="div_div"><img src="/styles/imagepims/fee.png" class="div_img" id="hisbutton">计费调整</div>-->
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
		<div class="col-xs-12">
			<table id="feediv"></table>
		</div>
	</div>
	<div id="formDialog1" style="display:none;" class="col-sm-12">
		<form class="form-horizontal" action="#" id="sampleForm1">
			<div class="form-group" style="margin-top: 10px;margin-bottom: 5px">
				<label class="col-sm-1 label_style">住院/门诊号:</label>
				<div class="col-sm-2">
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
					<input type="text" class=" input_style" id="reqpatientnumber"  disabled/>
				</div>
				<label class=" col-sm-1 label_style" for="reqitemnames">姓名:</label>
				<div class="col-sm-2">
					<input type="text" class="input_style" id="reqpatientname"  disabled/>
				</div>
				<label class="col-sm-1 label_style" >性别:</label>
				<div class="col-sm-2">
					<select class="col-sm-11 input_style" id="reqpatientsex" disabled>
						<option value="1">男</option>
						<option value="2">女</option>
						<option value="3">未知</option>
					</select>
				</div>
				<label class="col-sm-1 label_style" >年龄:</label>
				<div class="col-sm-2">
					<span class="input_style">
						<input type="text" id="reqpatientage" class="input_style" style="float:left;width:50%"  disabled/>
						<select class="col-sm-11 input_style"  style="float:left;width: 50px;%" id="reqpatagetype" disabled>
							<option value="1">岁</option>
							<option value="2">月</option>
							<option value="4">周</option>
							<option value="5">日</option>
							<option value="6">小时</option>
						</select>
					</span>
				</div>
			</div>
			<div class="form-group" style="margin-bottom: 5px;">
				<label class="col-sm-1 label_style" for="reqpattelephone">电话:</label>
				<div class="col-sm-2">
					<input type="text"  class="input_style" id="reqpattelephone" disabled/>
				</div>
				<label class="col-sm-1 label_style" for="reqpatienttype">患者类型:</label>
				<div class="col-sm-2">
					<select class="col-sm-11 input_style" id="reqpatienttype" disabled><!--患者类型(病人类型： 1住院,2门诊,3体检,4婚检,5科研,6特勤,7其他)-->
						<option value="1">住院</option>
						<option value="2">门诊</option>
						<option value="3">体检</option>
						<option value="4">婚检</option>
						<option value="5">科研</option>
						<option value="6">特勤</option>
						<option value="7">其他</option>
					</select>
				</div>
				<label class="col-sm-1 label_style">病区:</label>
				<div class="col-sm-2">
					<input type="text"  class="input_style" id="reqpatientwardcode" disabled/>
				</div>
				<label class="col-sm-1 label_style">科室:</label>
				<div class="col-sm-2">
					<input type="text" class="input_style" id="reqpatientdeptcode" name="reqpatientdeptcode"  disabled/>
				</div>
			</div>
			<div class="form-group" style="margin-bottom: 5px;">
				<label class="col-sm-1 label_style">床号:</label>
				<div class="col-sm-2">
					<input type="text" class="input_style" id="reqfirstn" disabled/>
				</div>
				<label class="col-sm-1 label_style">联系地址:</label>
				<div class="col-sm-8">
					<input type="text" class="col-sm-7 input_style" id="reqpataddress" disabled/>
				</div>
			</div>
			<div class="form-group" style="margin-bottom: 5px;">
				<label class="col-sm-1 label_style">临床诊断:</label>
				<div class="col-sm-10">
					<textarea id="reqpatdiagnosis" style="height: 55px;font-size: 12px;width: 80%"  disabled></textarea>
				</div>
			</div>
			<div class="form-group" style="margin-bottom: 5px;">
				<label class="col-sm-1 label_style">是否手术:</label>
				<div class="col-sm-1">
					<input type="checkbox" id="reqtype1" value="1" onclick="isshoushu()">
				</div>
				<label class="col-sm-1 label_style" name="ssxx">手术时间:</label>
				<div class="col-sm-2">
					<input type="text" class="form_datetime1 input_style"  id="reqfirstd" name="ssxx"  disabled/>
				</div>
				<label class="col-sm-1 label_style" name="ssxx">手术医生:</label>
				<div class="col-sm-2">
					<input type="text" class="input_style" id="reqsecondv" name="ssxx"  disabled/><!--手术医生 -->
				</div>
				<label class="col-sm-1 label_style" name="ssxx">手术电话:</label>
				<div class="col-sm-2">
					<input type="text" class="input_style"  id="reqthirdv" name="ssxx"  disabled/>
				</div>
			</div>
			<div class="form-group" style="margin-bottom: 5px;" name="ssxx">
				<label class="col-sm-2 label_style"></label>
				<label class="col-sm-1 label_style">手术所见:</label>
				<div class="col-sm-8">
					<textarea id="reqremark" style="height: 55px;font-size: 12px;width: 90%"  disabled></textarea>
				</div>
			</div>
			<div class="form-group" style="z-index: 99999999;margin-bottom: 5px">
				<label class="col-sm-1 label_style">申请单号:</label>
				<div class="col-sm-2">
					<input type="text" class="input_style"  id="requisitionno" name="requisitionno" value="${requisitionno}" readonly/>
				</div>
				<label class=" col-sm-1 label_style">检查项目:</label>
				<div class="col-sm-2">
					<input type="hidden" id="reqitemids"/>
					<input type="text" class="input_style" id="reqitemnames"   disabled/>
				</div>
				<label class="col-sm-1 label_style" >病种类别:</label>
				<div class="col-sm-2">
					<select class="col-sm-9 input_style" id="reqpathologyid" disabled>
						<%out.println(request.getAttribute("logyids"));%>
					</select>
				</div>
				<label class="col-sm-1 label_style" >执行时间:</label>
				<div class="col-sm-2">
					<input type="text" class="form_datetime1 input_style" id="reqplanexectime"    disabled/>
				</div>
			</div>
			<div class="form-group" style="margin-bottom: 5px">
				<label class="col-sm-1 label_style">申请时间:</label>
				<div class="col-sm-2">
					<input type="text" class="input_style form_datetime1"  id="reqdate" name="reqdate" readonly/>
				</div>
				<label class="col-sm-1 label_style">申请医生:</label>
				<div class="col-sm-2">
					<input type="hidden" id="reqdoctorid"/><!--申请医生姓名 -->
					<input   type="text"  class="input_style" id="reqdoctorname"   disabled/><!--申请医生姓名 -->
				</div>
				<label class="col-sm-1 label_style">申请电话:</label>
				<div class="col-sm-2">
					<input type="text" class="input_style"  id="reqsendphone" name="reqsendphone"  disabled/>
				</div>
				<label class="col-sm-1 label_style">申请医院:</label>
				<div class="col-sm-2">
					<input type="text" class="input_style" id="reqsendhospital"   disabled/>
				</div>
			</div>
			<div class="form-group" style="margin-bottom: 5px">
				<label class="col-sm-1 label_style" for="reqdeptcode">申请科室:</label>
				<div class="col-sm-2">
					<input type="hidden" id="reqdeptcode"/><!--申请科室名称-->
					<input  type="text" class="input_style" id="reqdeptname"   disabled/><!--申请科室名称-->
				</div>
				<label class="col-sm-1 label_style">申请病区:</label>
				<div class="col-sm-2">
					<input type="hidden" id="reqwardcode"/>
					<input type="text" class="input_style" id="reqwardname"   disabled/><!-- 申请病区名称-->
				</div>
			</div>
			<div class="form-group" style="margin-bottom: 5px;">
				<label class="col-sm-1 label_style" for="reqpatcompany">检查要求:</label>
				<div class="col-sm-11">
					<textarea id="reqpatcompany" style="height: 55px;font-size: 12px;width: 80%" class="col-sm-9"   disabled></textarea>
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
			<div class="rightContent  col-sm-6" style="margin-top: 5px;">
				<div id="dynamic_div2"></div>
				<div id="sexinfo" style="display: none">
					<div class="form-group" style="margin: 0px 0px 0px 0px">
						<h5 style="float: left;font-size: 14px;">妇产科检查</h5>
					</div>
					<div class="form-group" style="margin-bottom: 5px">
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
					<div class="form-group"  style="margin-bottom: 5px">
						<label class="col-sm-2 label_style">产史:</label>
						<select class="col-sm-2 input_style" id="reqcesarean">
							<option value='0'>无</option>
							<option value='1'>一胎</option>
							<option value='2'>二胎</option>
							<option value='3'>三胎</option>
							<option value='4'>四胎</option>
						</select>
						<label class="col-sm-2 label_style">末次月经:</label>
						<input type="text" class="col-sm-2 input_style form_datetime" id="reqlastmenstruation1"/>
						<label class="col-sm-2 label_style">是否绝经:</label>
						<select class="col-sm-2 input_style" id="reqismenopause">
							<option value='0'>否</option>
							<option value='1'>是</option>
						</select>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="h5_1">
		<h5 style="float: left;width: 33%;background-clip:content-box;background-color:rgb(135,184,127);padding-right:15px;height:40px;font-size:15px;color:#fff;line-height:40px;margin-top:0px!important;"><strong>&nbsp;&nbsp;已登记标本一览</strong></h5>
        <h5  style="float: left;width: 67%;background-clip:content-box;background-color:rgb(135,184,127);height:40px;font-size:15px;color:#fff;line-height:40px;margin-top:0px!important;"><strong>&nbsp;&nbsp;标本登记</strong></h5>
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
			<div id = "search_div_1" style="background-color: #F9F9F9;height: 200px;border:1px solid #E0E0E0;">
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
					<%--<table style="margin-bottom: 5px">--%>
						<%--<span class="input_style">&nbsp;&nbsp;送检医院:&nbsp;&nbsp;</span>--%>
						<%--<input type="text" id="send_hosptail" class="input_style"/>--%>
					<%--</table>--%>
					<%--<table style="margin-bottom: 5px">--%>
						<%--<span class="input_style">&nbsp;&nbsp;送检医生:&nbsp;&nbsp;</span>--%>
						<%--<input type="text" id="send_doctor" class="input_style"/>--%>
					<%--</table>--%>
					<table style="margin-bottom: 5px">
						<span class="input_style">&nbsp;&nbsp;患者姓名:&nbsp;&nbsp;</span>
						<input type="text" id="patient_name" class="input_style"/>
					</table>
					<table style="margin-bottom: 5px;">
						<span class="input_style">&nbsp;&nbsp;合格状态:&nbsp;&nbsp;</span>
						<input type="radio"   value="1" name="req_sts" checked/>合格
						<input type="radio" value="2" name="req_sts"/>不合格&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</table>
					<table style="margin-bottom: 5px">
						<div class="input_style">
							<span style="width: 30%;" class="input_style">&nbsp;&nbsp;病理状态:&nbsp;&nbsp;</span>
							<select id="req_code" class="input_style">
								<option value="">全部</option>
								<option value="0">已登记</option>
								<option value="1">已取材</option>
								<option value="2">已包埋</option>
								<option value="3">已切片</option>
								<option value="4">已初诊</option>
								<option value="5">已审核</option>
								<option value="6">已发送</option>
								<option value="7">会诊中</option>
								<option value="8">报告已打印</option>
							</select>
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
			<form class="form-horizontal" style="background-color: #F9F9F9;height: 354px;border:1px solid #E0E0E0;"
				  action="#" method="post" id="sampleForm"  onkeypress="JavaScript:return NoSubmit(event);">
				<button type="submit"id="saveButton1" style="display:none;">保存</button>
				<div class="form-group" style="margin-top: 5px;margin-bottom: 5px">
					<label  class="label_style col-sm-1" for="saminspectionid"></label>
					<div class="col-sm-3">
					</div>
					<label class="label_style col-sm-1" for="samrequistionid">临床申请:</label>
					<div class="col-sm-3 ">
						<input class="input_style" type="text" id="samrequistionid" name="samrequistionid" onkeypress="getreqData(this,event)"/>
					</div>

				</div>
				<div class="form-group" style="margin-bottom: 5px">
					<label  class="label_style col-sm-1">病理编号:</label>
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
						<input type="hidden" id="sampiecedoctorid"/><!--首次取材医师既诊断医师ID-->
						<input type="hidden" id="sampiecedoctorname"/><!--首次取材医师既诊断医师-->
                        <input class="input_style" type="text" id="sampathologycode" name="sampathologycode" readonly/>
                    </div>
					<label class="label_style col-sm-1">检查项目:</label>
					<div class="col-sm-3 ">
						<input type="hidden" id="sampopuser"/><!--标本检查项目id-->
						<%--<input class="input_style" type="text" id="samjcxm" name="samjcxm" datatype="*"/>--%>
						<span style="overflow:hidden;" class="input_style  col-sm-8">
							<select class="input_style col-sm-12" id="samjcxm1" onchange="fillval('sampopuser','samjcxm','sampathologyid','samjcxm1')">
								<%out.print(request.getAttribute("samjcxm"));%>
							</select>
						</span>
						<input id="samjcxm"  name="samjcxm" datatype="*" style="position:absolute;left:5px;width: 55%" class="input_style" >
					</div>
					<label class="label_style col-sm-1" >病种类别:</label>
					<div class="col-sm-3">
						<select class="input_style col-sm-8" id="sampathologyid" disabled="disabled">
							<%out.println(request.getAttribute("logyids"));%>
						</select>
					</div>
				</div>
				<div class="form-group" style="margin-bottom: 5px">
					<label class="label_style col-sm-1" >条形码:</label>
					<div class="col-sm-3">
                        <input type="text" class="input_style" id="saminspectionid" name="saminspectionid" onkeypress="getData(this,event)" value="${saminspectionid}" readonly/>
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
						<input class="input_style" type="text" id="sampatientnumber" name="sampatientnumber" datatype="*"  onkeypress="getPatient(this,event)"/>
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
							<option value="1">男</option>
							<option value="2">女</option>
							<option value="3">未知</option>
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
						<span style="overflow:hidden;" class="input_style  col-sm-8">
							<select class="input_style  col-sm-12" id="samsenddoctorid1"
									onchange="fillval('samsenddoctorid','samsenddoctorname',null,'samsenddoctorid1')">
								<%out.print(request.getAttribute("samsenddoctorname"));%>
							</select>
						</span>
						<input id="samsenddoctorname"  name="samsenddoctorname" datatype="*" style="position:absolute;left:5px;width: 55%;" class="input_style">
					</div>
					<label class="col-sm-1 label_style" for="samdeptcode">送检科室:</label>
					<div class="col-sm-3">
						<input type="hidden" id="samdeptcode"/><!--申请科室名称-->
						<%--<input class="input_style" type="text" id="samdeptname" name="samdeptname" datatype="*"/><!--申请科室名称-->--%>
						<span style="overflow:hidden;" class="input_style  col-sm-8">
							<select class="input_style  col-sm-12" id="samdeptcode1"
									onchange="fillval('samdeptcode','samdeptname',null,'samdeptcode1')">
								<%out.print(request.getAttribute("samdeptname"));%>
							</select>
						</span>
						<input id="samdeptname"  name="samdeptname" datatype="*" style="position:absolute;left:5px;width: 55%;" class="input_style">
					</div>
					<label class="col-sm-1 label_style" >送检医院:</label>
					<div class="col-sm-3 ">
						<span style="overflow:hidden;" class="input_style  col-sm-8">
							<select class="input_style  col-sm-12" id="samsendhospitalid"
									onchange="fillval('samsendhospitalid','samsendhospital','samsource','samsendhospitalid')">
								<%out.print(request.getAttribute("samsendhospital"));%>
							</select>
						</span>
						<input id="samsendhospital"  name="samsendhospital" datatype="*" style="position:absolute;left:5px;width: 55%;" class="input_style">
					</div>
				</div>
				<div class="form-group" style="margin-bottom: 5px;">
					<label class="col-sm-1 label_style" >送检时间:</label>
					<div class="col-sm-3">
						<input  type="text" class="form_datetime1 input_style" value="${samsendtime}" id="samsendtime" name="samsendtime" datatype="*"/>
					</div>
					<label class="col-sm-1 label_style" for="samreceivertime">接收日期:</label>
					<div class="col-sm-3">
						<input type="text" class="input_style" value="${samreceivertime}" id="samreceivertime" readonly/>
					</div>
					<label class="col-sm-1 label_style" for="samfirstn">组织袋数:</label>
					<div class="col-sm-3">
						<input class="input_style" type="text" id="samfirstn"  name="samfirstn" datatype="n1-2"/>
					</div>
				</div>
				<div class="form-group" style="margin-bottom: 5px;">
					<label style="font-size: 13px;"  class="col-sm-1 label_style">合格状态:</label>
					<div class="col-sm-3 input_style">
						&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" value="1" name="samsecondv"/>&nbsp;&nbsp;合格&nbsp;&nbsp;
						<input type="radio" value="2" name="samsecondv"/>&nbsp;&nbsp;不合格
					</div>
					<label class="col-sm-1 label_style" for="samremark">原因:</label>
					<div class="col-sm-7">
						<input  type="text" id="samremark" class="col-sm-10 input_style"/>
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
	<div id="formDialog11" style="display:none;" class="col-sm-12">
		<div>
			<div class="widget-body" style="overflow:auto;">
				<div class="widget-main no-padding">
					<table id="new22" class="table">
					</table>
				</div>
			</div>
		</div>
	</div>
</body>