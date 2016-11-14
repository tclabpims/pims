<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="home.title"/></title>
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
    <script type="text/javascript" src="../scripts/bootstrap-datetimepicker.min.js"></script>
    <meta name="menu" content="Home"/>
    <script src="<c:url value="/scripts/home.js"/>"></script>
<style>
    .img_style{width: 18px;height: 23px}
    .label_style1{font-size: 14px;margin-right: 40px;}
    .ui-jqgrid-sortable{text-align: center;}
    .label_style{font-size: 12px;color: #323232;height: 24px;text-align:right;}
    .input_style{height: 24px;font-size: 12px!important;}
    /*.a_style{color: #323232;cursor:pointer;border-bottom: 4px solid #0FCFA0;text-decoration: none;outline:none;}*/
</style>
</head>
<body class="home"  style="font-family:'Microsoft YaHei',微软雅黑,'MicrosoftJhengHei'!important;">
    <input type="hidden" id="lcal_hosptail" value="${send_hosptail}"/>
    <input type="hidden" id="local_logyid" value="${logyid}"/>
    <input type="hidden" id="local_userid" value="${local_userid}"/>
    <input type="hidden" id="local_username" value="${local_username}"/>
    <div>
        <h5  style="float: left;width: 50%;font-size: 14px;"><strong>&nbsp;<img src="/styles/imagepims/myinfo.png" class="img_style">&nbsp;我的相关信息</strong></h5>
        <h5 style="float: left;width: 50%;font-size: 14px;margin-bottom: 12px"><strong>&nbsp;<img src="/styles/imagepims/aboutme.png" class="img_style">&nbsp;我发起的</strong></h5>
    </div>
    <div style="margin-right: 0px;margin-left: 0px;" class="row" id="div_lable_1">
        <div style="width: 50%;float: left">
            <label class="label_style1">
                <a href="1" style="color: #323232;cursor:pointer;border-bottom: 4px solid #0FCFA0;text-decoration: none;outline:none;">
                    我的工作未处理(${noworkList})
                </a>
            </label>
            <label class="label_style1">
                <a href="2" style="color: #323232;cursor:pointer;text-decoration: none;outline:none;">
                    我的会诊(${mycons})
                </a>
            </label>
            <label class="label_style1">
                <a href="3" style="color: #323232;cursor:pointer;text-decoration: none;outline:none;">
                    我的留言(${mymessage})
                </a>
            </label>
            <label class="label_style1">
                <a href="4" style="color: #323232;cursor:pointer;text-decoration: none;outline:none;">
                    我的收藏(10)
                </a>
            </label>
            <label class="label_style1">
                <a href="5" style="color: #323232;cursor:pointer;text-decoration: none;outline:none;">
                    随访病例(10)
                </a>
            </label>
        </div>
        <div style="width: 50%;float: left;margin-bottom: 12px">
            <label class="label_style1">
                <a href="6" style="color: #323232;cursor:pointer;text-decoration: none;outline:none;">
                    发出会诊(${mysendcons})
                </a>
            </label>
            <label class="label_style1">
                <a href="7" style="color: #323232;cursor:pointer;text-decoration: none;outline:none;">
                    发出留言(${mysendmessage})
                </a>
            </label>
        </div>
    </div>
    <div id="show_div_1" style="display: none">
        <ul id="tabs" class="nav nav-tabs">
            <li class="active">
                <a href="3" data-toggle="tab">
                    未初查(${nocc})
                </a>
            </li>
            <li>
                <a href="4" data-toggle="tab">
                    未审核(${nosh})
                </a>
            </li>
            <li>
                <a href="6" data-toggle="tab">
                    未打印(${nody})
                </a>
            </li>
            <li>
                <a href="5" data-toggle="tab">
                    未发送(${nofs})
                </a>
            </li>
            <li>
                <a href="#infotab" data-toggle="tab">
                    未接收(${nojs})
                </a>
            </li>
            <li>
                <a href="#infotab" data-toggle="tab">
                    未签收(${noqs})
                </a>
            </li>
            <li>
                <a href="#infotab" data-toggle="tab">
                    未取材(${noqc})
                </a>
            </li>
        </ul>
    </div>
    <div class="row" id="show_div_1_1" style="display: none">
        <div>
            <div class="widget-body" style="overflow:auto;">
                <div class="widget-main no-padding">
                    <table id="new1" class="table">
                    </table>
                    <div id="pager1"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="row" id="show_div_2" style="display: none">
        <div>
            <div style="margin-top: 5px">
                <table style="margin-bottom: 10px">
                    <span class="input_style">&nbsp;&nbsp;发起年月:&nbsp;&nbsp;</span>
                    <input type="text" class="form_datetime input_style" value="${sevenday}" id="req_bf_time2"/>
                    <span>-</span>
                    <input type="text" class="form_datetime input_style" value="${receivetime}"  id="req_af_time2"/>
                    <span style="width: 30%;" class="input_style">&nbsp;&nbsp;状态:&nbsp;&nbsp;</span>
                    <select id="req_sts2" class="input_style">
                        <option value="">全部</option>
                        <option value="0" selected>会诊中</option>
                        <option value="1">会诊终了</option>
                        <option value="2">会诊取消</option>
                    </select>
                    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                    <button type="button" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;" onclick="searchList(2)">
                        <span style="color: white;">查询</span>
                    </button>
                </table>
            </div>

        </div>
        <div>
            <div class="widget-body" style="overflow:auto;">
                <div class="widget-main no-padding">
                    <table id="new2" class="table">
                    </table>
                    <div id="pager2"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="row" id="show_div_3" style="display: none">
        <div>
            <div style="margin-top: 5px">
                <table style="margin-bottom: 10px">
                    <span class="input_style">&nbsp;&nbsp;发起年月:&nbsp;&nbsp;</span>
                    <input type="text" class="form_datetime input_style" value="${sevenday}" id="req_bf_time3"/>
                    <span>-</span>
                    <input type="text" class="form_datetime input_style" value="${receivetime}"  id="req_af_time3"/>
                    <span style="width: 30%;" class="input_style">&nbsp;&nbsp;状态:&nbsp;&nbsp;</span>
                    <select id="req_sts3" class="input_style">
                        <option value="">全部</option>
                        <option value="0" selected>未读</option>
                        <option value="1">已读</option>
                    </select>
                    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                    <button type="button" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;" onclick="searchList(3)">
                        <span style="color: white;">查询</span>
                    </button>
                </table>
            </div>

        </div>
        <div>
            <div class="widget-body" style="overflow:auto;">
                <div class="widget-main no-padding">
                    <table id="new3" class="table">
                    </table>
                    <div id="pager3"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="row" style="margin-right: 0px;margin-left: 0px;" >
        <h5  style="float: left;font-size: 14px;margin-bottom: 12px"><strong>&nbsp;<img src="/styles/imagepims/sysnofinish.png" class="img_style">&nbsp;系统未处理信息</strong></h5>
    </div>
    <div>
        <ul id="tab1" class="nav nav-tabs">
            <li class="active">
                <a href="#maintab" data-toggle="tab">
                    未初查(${nocc})
                </a>
            </li>
            <li>
                <a href="#infotab" data-toggle="tab">
                    未审核(${nosh})
                </a>
            </li>
            <li>
                <a href="#infotab" data-toggle="tab">
                    未打印(${nody})
                </a>
            </li>
            <li>
                <a href="#infotab" data-toggle="tab">
                    未发送(${nofs})
                </a>
            </li>
            <li>
                <a href="#infotab" data-toggle="tab">
                    未接收(${nojs})
                </a>
            </li>
            <li>
                <a href="#infotab" data-toggle="tab">
                    未签收(${noqs})
                </a>
            </li>
            <li>
                <a href="#infotab" data-toggle="tab">
                    未取材(${noqc})
                </a>
            </li>
        </ul>
    </div>
    <div class="row">
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
    <div id="formDialog" style="display: none">
        <form class="form-horizontal" style="background-color: #F9F9F9;height: 299px;border:1px solid #E0E0E0;"  action="#" method="post" id="sampleForm" >
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
    </div>
    <div id="formDialog3" style="display:none;" class="col-sm-12">
        <form class="form-horizontal" style="background-color: #F9F9F9;height: 430px;border:1px solid #E0E0E0;" action="#" method="post" id="sampleForm" >
            <div class="form-group" style="margin-top: 10px;margin-bottom: 5px">
                <label class="col-sm-2 label_style">消息内容:</label>
                <div class="col-sm-10">
                    <textarea id="mescontent" style="height: 55px;font-size:12px;width: 80%"></textarea>
                </div>
            </div>
            <div class="form-group" style="margin-bottom: 5px;">
                <label class="col-sm-2 label_style">发布人:</label>
                <div class="col-sm-4 ">
                    <input type="text" class="input_style" id="messendername" readonly/>
                </div>
                <label style="font-size: 13px;"  class="col-sm-2 label_style">发布时间:</label>
                <div class="col-sm-3">
                    <input type="text" class="form_datetime1 input_style" id="meshandletime"/>
                </div>
            </div>
        </form>
    </div>
</body>
