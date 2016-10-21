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
    <script type="text/javascript" src="../scripts/his/his.js"></script>
	<%--<script type="text/javascript" src="../scripts/validform/Validform.min.js"></script>--%>
	<script type="text/javascript" src="../scripts/bootstrap-datetimepicker.min.js"></script>
</head>
<style>
	select {
		height:34px;
	}
	.ui-autocomplete {
		z-index: 99999999;
	}
    label {
        font-size: 13px;
    }
    .form-group{
        margin-bottom: 0px;
    }
	.class1 {background:red}
</style>
<body>
<div class="row widget-main" id="div_1">
	<div>
		<button type="button" class="btn btn-sm btn-primary " title="新增" onclick="addSample()">
			<i class="ace-icon fa fa-fire bigger-110"></i>
			新增
		</button>
		<button type="button" class="btn btn-sm  btn-success" title="修改" id="editButton" onclick="editSample()">
			<i class="ace-icon fa fa-pencil-square bigger-110"></i>
			修改
		</button>
		<button type="button" class="btn btn-sm btn-danger" title="删除" id="deleteButton" onclick="deleteSample()">
			<i class="ace-icon fa fa-times bigger-110"></i>
			删除
		</button>
		<button type="button" class="btn btn-sm btn-danger" title="保存" id="saveButton" onclick="saveInfo()">
			<i class="ace-icon fa fa-fire bigger-110"></i>
			保存
		</button>
		<button type="button" class="btn btn-sm btn-info" title="打印" onclick="print()">
			<i class="ace-icon fa fa-print bigger-110"></i>
			打印
		</button>
		<button type="button" class="btn btn-sm btn-danger" title="上一个" onclick="upSample()">
			<i class="ace-icon fa fa-times bigger-110"></i>
			上一个
		</button>
		<button type="button" class="btn btn-sm btn-danger" title="下一个" onclick="downSample()">
			<i class="ace-icon fa fa-fire bigger-110"></i>
			下一个
		</button>
	</div>
</div>
<div class="row">
<div id="formDialog" class="col-sm-7 leftContent" style="border-style: solid;border-color: #0A246A;border-width: 1px">
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
			<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" for="reqitemnames">检查项目:</label>
			<div class="col-sm-3 ">
				<input type="hidden" id="reqitemids"/>
				<input type="text" class="col-sm-9" id="reqitemnames" name="reqitemnames"  datatype="s1-16"/>
			</div>
			<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" >病种类别:</label>
			<div class="col-sm-3">
				<select class="col-sm-9" id="reqpathologyid" >
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
		<div class="form-group" style="margin-right:0px;margin-left:0px;margin-bottom: 0px;">
			<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" for="reqdoctorid">送检医生:</label>
			<div class="col-sm-3 ">
				<input type="hidden" id="reqdoctorid"/><!--申请医生姓名 -->
				<input   type="text" class="col-sm-9" id="reqdoctorname"/><!--申请医生姓名 -->
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
				<input   type="text" class="col-sm-9" id="reqsecondv"/><!--手术医生 -->
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
			<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" for="reqpatientsex">性别:</label>
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
			<label style="font-size: 13px;"  class="col-sm-1 control-label no-padding-right" for="samfirstn">床号:</label>
			<div class="col-sm-3">
				<input type="text" class="col-sm-9"  id="samfirstn" />
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
					<button type="button" class="btn btn-sm btn-primary " title="追加行" onclick="addRow()">
						<i class="ace-icon fa fa-fire bigger-110"></i>
						追加行
					</button>
					<button type="button" class="btn btn-sm btn-danger" title="删除行" onclick="delRow()">
						<i class="ace-icon fa fa-times bigger-110"></i>
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
<div class="col-sm-5 rightContent" id="div_2">
	<table>
	<div class="input-group" style="float: left;">
		<span class="input-group-addon ">申请单号</span>
		<input type="text" class="form-control" placeholder="申请单号" id="req_code" value="" onkeypress="receive(this,event)"/>
		<span class="input-group-addon">病人姓名</span>
		<input type="hidden" id="lcal_hosptail" value="${reqcustomerid}"/>
		<input type="hidden" id="local_logyid" value="${reqpathologyid}"/>
		<input type="hidden" id="local_userid" value="${local_userid}"/>
		<input type="hidden" id="local_username" value="${local_user}"/>
		<input type="text" class="form-control" value="" id="patient_name"/>
	</div>
	<div class="input-group" style="float: left;">
		<span class="input-group-addon">申请年月</span>
		<input type="text" class="form_datetime form-control" placeholder="" value="${sevenday}" id="req_bf_time"/>
		<span class="input-group-addon">-</span>
		<input type="text" class="form_datetime form-control" placeholder="" value="${receivetime}"  id="req_af_time"/>
	</div>
	<div class="input-group" style="float: left;">
		<span class="input-group-addon ">送检医院</span>
		<select class="form-control"  id="send_hosptail">
			<option value="">全部</option>
			<option value="1">杭州国际医院</option>
			<option value="2">袍江医院</option>
			<option value="3">温州人民医院</option>
		</select>
		<span class="input-group-addon">送检科室</span>
		<input type="text" class="form-control" value="" id="send_dept"/>
	</div>
	<div class="input-group" style="float: left;">
		<span class="input-group-addon ">送检医生</span>
		<select class="form-control"  id="send_doctor">
			<option value="">全部</option>
			<option value="1">张三</option>
			<option value="2">李四</option>
			<option value="3">王五</option>
			<option value="4">赵六</option>
		</select>
		<span class="input-group-addon">申请状态</span>
		<select class="form-control" id="req_sts">
			<option value="">全  部</option>
			<option value="">已申请</option>
			<option value="">已登记</option>
			<option value="">已完成</option>
			<option value="">已延迟</option>
		</select>
		<span class="input-group-btn">
			<button type="button" class="btn btn-sm btn-success" title="查询信息" onclick="searchList()">
				<i class="ace-icon fa fa-print bigger-110"></i>
				查询
			</button>
		</span>
	</div>
	<div class="row">
		<div class="widget-box widget-color-green">
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