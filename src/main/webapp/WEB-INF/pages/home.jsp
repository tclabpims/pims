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
                <a href="1,3" data-toggle="tab">
                    未初查(${nocc})
                </a>
            </li>
            <li>
                <a href="1,4" data-toggle="tab">
                    未审核(${nosh})
                </a>
            </li>
            <li>
                <a href="1,6" data-toggle="tab">
                    未打印(${nody})
                </a>
            </li>
            <li>
                <a href="1,5" data-toggle="tab">
                    未发送(${nofs})
                </a>
            </li>
            <li>
                <a href="2_1,2" data-toggle="tab">
                    未签收(${noqs})
                </a>
            </li>
            <li>
                <a href="2_1,0,1" data-toggle="tab">
                    未补取(${noqc})
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
    <div class="row" id="show_div_1_2" style="display: none">
        <div>
            <div class="widget-body" style="overflow:auto;">
                <div class="widget-main no-padding">
                    <table id="new2_1" class="table">
                    </table>
                    <div id="pager2_1"></div>
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
    <div class="row" id="show_div_6" style="display: none">
        <div>
            <div style="margin-top: 5px">
                <table style="margin-bottom: 10px">
                    <span class="input_style">&nbsp;&nbsp;发起年月:&nbsp;&nbsp;</span>
                    <input type="text" class="form_datetime input_style" value="${sevenday}" id="req_bf_time6"/>
                    <span>-</span>
                    <input type="text" class="form_datetime input_style" value="${receivetime}"  id="req_af_time6"/>
                    <span style="width: 30%;" class="input_style">&nbsp;&nbsp;状态:&nbsp;&nbsp;</span>
                    <select id="req_sts6" class="input_style">
                        <option value="">全部</option>
                        <option value="0" selected>会诊中</option>
                        <option value="1">会诊终了</option>
                        <option value="2">会诊取消</option>
                    </select>
                    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                    <button type="button" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;" onclick="searchList(6)">
                        <span style="color: white;">查询</span>
                    </button>
                </table>
            </div>

        </div>
        <div>
            <div class="widget-body" style="overflow:auto;">
                <div class="widget-main no-padding">
                    <table id="new6" class="table">
                    </table>
                    <div id="pager6"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="row" id="show_div_7" style="display: none">
        <div>
            <div style="margin-top: 5px">
                <table style="margin-bottom: 10px">
                    <span class="input_style">&nbsp;&nbsp;发起年月:&nbsp;&nbsp;</span>
                    <input type="text" class="form_datetime input_style" value="${sevenday}" id="req_bf_time7"/>
                    <span>-</span>
                    <input type="text" class="form_datetime input_style" value="${receivetime}"  id="req_af_time7"/>
                    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                    <button type="button" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;" onclick="searchList(7)">
                        <span style="color: white;">查询</span>
                    </button>
                </table>
            </div>

        </div>
        <div>
            <div class="widget-body" style="overflow:auto;">
                <div class="widget-main no-padding">
                    <table id="new7" class="table">
                    </table>
                    <div id="pager7"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="row" style="margin-right: 0px;margin-left: 0px;" >
        <h5  style="float: left;font-size: 14px;margin-bottom: 12px"><strong>&nbsp;<img src="/styles/imagepims/sysnofinish.png" class="img_style">&nbsp;系统未处理信息</strong></h5>
    </div>
    <div>
        <ul id="sys" class="nav nav-tabs">
            <li class="active">
                <a href="0" data-toggle="tab">
                    未取材(${nosyscq})
                </a>
            </li>
            <li>
                <a href="1" data-toggle="tab">
                    未包埋(${nosysbm})
                </a>
            </li>
            <li>
                <a href="2" data-toggle="tab">
                    未切片(${nosysqp})
                </a>
            </li>
            <li>
                <a href="3,3" data-toggle="tab">
                    未初查(${nosyscc})
                </a>
            </li>
            <li>
                <a href="3,4" data-toggle="tab">
                    未审核(${nosyssh})
                </a>
            </li>
            <li>
                <a href="3,6" data-toggle="tab">
                    未打印(${nosysdy})
                </a>
            </li>
            <li>
                <a href="3,5" data-toggle="tab">
                    未发送(${nosysfs})
                </a>
            </li>
            <li>
                <a href="4,0,0" data-toggle="tab">
                    未接收(${nosysjs})
                </a>
            </li>
            <li>
                <a href="4,1" data-toggle="tab">
                    未完成(${nosyswc})
                </a>
            </li>
            <li>
                <a href="4,2" data-toggle="tab">
                    未签收(${nosysqs})
                </a>
            </li>
            <li>
                <a href="4,0,1" data-toggle="tab">
                    未补取(${nosysbq})
                </a>
            </li>
        </ul>
    </div>
    <div class="row" id="sys_0" style="display: none">
        <div>
            <div class="widget-body" style="overflow:auto;">
                <div class="widget-main no-padding">
                    <table id="sysnew0" class="table">
                    </table>
                    <div id="syspager0"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="row" id="sys_1" style="display: none">
        <div>
            <div class="widget-body" style="overflow:auto;">
                <div class="widget-main no-padding">
                    <table id="sysnew1" class="table">
                    </table>
                    <div id="syspager1"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="row" id="sys_2" style="display: none">
        <div>
            <div class="widget-body" style="overflow:auto;">
                <div class="widget-main no-padding">
                    <table id="sysnew2" class="table">
                    </table>
                    <div id="syspager2"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="row" id="sys_3" style="display: none">
        <div>
            <div class="widget-body" style="overflow:auto;">
                <div class="widget-main no-padding">
                    <table id="sysnew3" class="table">
                    </table>
                    <div id="syspager3"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="row" id="sys_4" style="display: none">
        <div>
            <div class="widget-body" style="overflow:auto;">
                <div class="widget-main no-padding">
                    <table id="sysnew4" class="table">
                    </table>
                    <div id="syspager4"></div>
                </div>
            </div>
        </div>
    </div>
    <div id="formDialog3" style="display:none;" class="col-sm-12">
        <form class="form-horizontal" style="background-color: #F9F9F9;height: 430px;border:1px solid #E0E0E0;" action="#" method="post" id="sampleForm3" >
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
