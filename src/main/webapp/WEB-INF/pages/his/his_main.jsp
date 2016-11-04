<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="ElectronicApplyManage.title"/></title>
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/bootstrap-datetimepicker.min.css'/>" />
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/bootstrap.min.css'/>" />
	<script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="../scripts/bootstrap.min.js"></script>
	<script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
	<script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
	<script type="text/javascript" src="<c:url value="/scripts/ace.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/ace-elements.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/bootstrap-tag.min.js"/>"></script>
    <script type="text/javascript" src="../scripts/layer/layer.js"></script>
    <script type="text/javascript" src="../scripts/his/his_main.js"></script>
	<script type="text/javascript" src="../scripts/validform/Validform.min.js"></script>
	<script type="text/javascript" src="../scripts/bootstrap-datetimepicker.min.js"></script>
	<script type="text/javascript" src="../scripts/jquery.zclip.min.js"></script>
	<script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
	<style>
		select {
			height:34px;
		}
		.ui-autocomplete {
			z-index: 99999999 !important;
			max-height: 200px;
			overflow-y: auto;
			/* 防止水平滚动条 */
			overflow-x: hidden;
		}
	</style>
</head>
<body>
	<h5><strong>&nbsp;基本信息</strong></h5>
	<div class="row widget-main" style="height: 85px;background-color: #E8E8E8" id="div_0">
		<table>
			<span>申请单号:</span>
			<input type="hidden" id="req_sts" value="0"/>
			<input type="text" style="height: 28px" id="req_code" onkeypress="receive(this,event)"/>
			<span >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
			<span >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
			<span >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
			<span >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
			<span >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
			<span >病人姓名:</span>
			<input type="text" style="height: 28px" id="patient_name"/>
			<span >&nbsp;&nbsp;&nbsp;&nbsp;</span>
			<span>送检医院:</span>
			<input type="text" style="height: 28px" id="send_hosptail"/>
			<input type="hidden" id="logylibid" value="${logylibid}"/><!--当前病理库-->
			<input type="hidden" id="item_source" value="${reqsource}"/><!--申请单来源-->
			<input type="hidden" id="lcal_hosptail" value="${reqcustomerid}"/><!--账号所属医院-->
			<input type="hidden" id="local_user" value="${local_user}"/><!--用户姓名-->
			<input type="hidden" id="local_userid" value="${local_userid}"/><!--用户ID-->
		</table>
		<table>
			<span>申请年月:</span>
			<input type="text" class="form_datetime" style="height: 28px" value="${sevenday}" id="req_bf_time"/>
			<span>-</span>
			<input type="text" class="form_datetime" style="height: 28px" value="${receivetime}"  id="req_af_time"/>
			<span >&nbsp;&nbsp;&nbsp;&nbsp;</span>
			<span>送检科室:</span>
			<input type="text" style="height: 28px" id="send_dept"/>
			<span >&nbsp;&nbsp;&nbsp;&nbsp;</span>
			<span>送检医生:</span>
			<input type="text" style="height: 28px" id="send_doctor"/>
			<button type="button" class="btn-sm btn-info" style="float: right" onclick="searchList()">
				查询
			</button>
		</table>
	</div>
	<table>
		<h5><strong>&nbsp;工作安排一览</strong></h5>
		<button type="button" class="btn-sm btn-info" onclick="addSample()">
			新增
		</button>
		<button type="button" class="btn-sm  btn-info"  onclick="viewSample()">
			查看
		</button>
		<button type="button" class="btn-sm btn-info" onclick="deleteSample()">
			删除
		</button>
		<button type="button" class="btn-sm btn-info" onclick="print()">
			打印
		</button>
	</table>
	<ul id="tabs" class="nav nav-tabs">
		<li class="active">
			<a href="0" data-toggle="tab">
				已申请
			</a>
		</li>
		<li>
			<a href="1" data-toggle="tab">
				已登记
			</a>
		</li>
		<li>
			<a href="2" data-toggle="tab">
				已完成
			</a>
		</li>
		<li>
			<a href="3" data-toggle="tab">
				已延迟
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
				<input type="text" class="col-sm-9" id="reqwardname" name="reqwardname" datatype="*"/><!-- 申请病区名称-->
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
				<input type="text" class="col-sm-9" id="reqsendhospital" name="reqsendhospital"  datatype="*"/>
			</div>
			<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" for="reqdeptcode">送检科室:</label>
			<div class="col-sm-3 ">
				<input type="hidden" id="reqdeptcode"/><!--申请科室名称-->
				<input  type="text" class="col-sm-9" id="reqdeptname" name="reqdeptname"  datatype="*"/><!--申请科室名称-->
			</div>
		</div>
		<div class="form-group" style="z-index: 99999999;margin-right:0px;margin-left:0px;margin-bottom: 0px;">
			<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" for="reqdoctorid">送检医生:</label>
			<div class="col-sm-3 ">
				<input type="hidden" id="reqdoctorid"/><!--申请医生姓名 -->
				<input   type="text"  class="col-sm-9" id="reqdoctorname" name="reqdoctorname"  datatype="*"/><!--申请医生姓名 -->
			</div>
			<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" for="reqsendphone">送检电话:</label>
			<div class="col-sm-3 ">
				<input type="text" class="col-sm-9"  id="reqsendphone" name="reqsendphone"/>
			</div>
			<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" >送检时间:</label>
			<div class="col-sm-3">
				<input type="text" class="col-sm-9 form_datetime1" id="reqdate"  name="reqdate"  datatype="*"/>
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
				<input type="text" class="col-sm-9"  id="reqpatientname"  name="reqpatientname"  datatype="*"/>
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
					<input type="text" id="reqpatientage" style="float:left;width:60%"  name="reqpatientage"  datatype="n1-2"/>
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
				<input type="text"  class="col-sm-9" id="reqpatientnumber"  name="reqpatientnumber"  datatype="*"/>
			</div>
			<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" for="reqfirstn">床号:</label>
			<div class="col-sm-3">
				<input type="text" class="col-sm-9"  id="reqfirstn"   name="reqfirstn"  datatype="*"/>
			</div>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
			<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" for="reqpattelephone">联系电话:</label>
			<div class="col-sm-3">
				<input type="text"  class="col-sm-9" id="reqpattelephone"/>
			</div>
			<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" for="samreceivertime">邮编:</label>
			<div class="col-sm-3">
				<input type="text" class="col-sm-9"  class="form_datetime1" value="${samreceivertime}" id="samreceivertime"/>
			</div>
			<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" for="reqpataddress">联系地址:</label>
			<div class="col-sm-3">
				<input type="text" class="col-sm-9"  id="reqpataddress" />
			</div>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
			<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" for="reqpatdiagnosis">临床诊断:</label>
			<div class="col-sm-11">
				<textarea id="reqpatdiagnosis" style="height: 50px;width: 80%" class="col-sm-9"    name="reqpatdiagnosis"  datatype="*"></textarea>
			</div>
		</div>
		<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
			<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" for="reqpatcompany">检查要求:</label>
			<div class="col-sm-11">
				<textarea id="reqpatcompany" style="height: 50px;width: 80%" class="col-sm-9" ></textarea>
				<button type="submit" class="btn-sm btn-success" style="float: right"  id="savebutton">
					保存
				</button>
			</div>
		</div>
	</form>
	<div class="widget-main no-padding">
		<p1>电子申请单列表</p1>
		<div class="widget-box widget-color-green">
			<div class="widget-main no-padding  col-sm-5" style="margin-top: 5px">
				<div class="form-group" style="margin-right:0px;margin-left:0px;">
					<label class="control-label" style="text-align: left;">&nbsp;&nbsp;&nbsp;&nbsp;组织信息</label>
					<button type="button" class="btn-sm btn-info " onclick="addRow()">
						追加行
					</button>
					<button type="button" class="btn-sm btn-info" onclick="delRow()">
						删除行
					</button>
				</div>
				<div class="form-group " style="margin-right:0px;margin-left:0px;">
					<table id="new1" class="table table-striped table-bordered table-hover">
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
</body>

