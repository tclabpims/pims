<%@ page import="com.smart.model.user.User" %>
<%@ page import="com.pims.webapp.controller.WebControllerUtil" %>
<%@ page import="com.smart.model.user.UserBussinessRelate" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="com.pims.service.pimssyspathology.PimsSysPathologyManager" %>
<%@ page import="com.alibaba.fastjson.JSONArray" %>
<%@ page import="com.alibaba.fastjson.JSONObject" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: 909436637@qq.com
  Date: 2016/10/6
  Time: 10:01
--%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<html>
<head>
    <title>病理诊断</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles/ui.jqgrid.css'/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles/jquery-ui.css'/>"/>
    <link rel="stylesheet" href="<c:url value='/styles/ztree/zTreeStyle.css'/>"/>
    <!--script type="name/javascript" src="../scripts/bootstrap.min.js"></script-->
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
    <script src="<c:url value='/scripts/jquery.ztree.all-3.5.js'/>"></script>
    <script type="text/javascript" src="../scripts/validform/Validform.min.js"></script>
    <script type="text/javascript" src="../scripts/layer/layer.js"></script>
    <script type="text/javascript" src="../scripts/pspathology/diagnosis.js"></script>
</head>
<style>
    .ui-jqgrid {
        border: 1px solid #ddd;
    }

    ::-webkit-scrollbar-track {
        background-color: #F5F5F5
    }

    ::-webkit-scrollbar {
        width: 6px;
        background-color: #F5F5F5
    }

    ::-webkit-scrollbar-thumb {
        background-color: #999
    }

    .table-bordered > thead > tr > td, .table-bordered > thead > tr > th {
        background-color: #F5F5F6;
    }

    .ui-autocomplete {
        z-index: 99999999;
    }

    ul.tabs{
        margin: 0px;
        padding: 0px;
        list-style: none;
    }
    ul.tabs li{
        background: none;
        color: #222;
        display: inline-block;
        padding: 10px 15px;
        cursor: pointer;
    }

    ul.tabs li.current{
        background: #ededed;
        color: #222;
    }

    .tab-content{
        display: none;
        background: #ededed;
        padding: 15px;
    }

    .tab-content.current{
        display: inherit;
    }
</style>
<SCRIPT LANGUAGE="JavaScript">
    var zTreeObj;
    // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
    /*******************************
     * ztree 设置参数
     ********************************/
    var setting = {
        data: {
            simpleData: {
                enable: true
            }
        },
        async: {
            enable: true,
            url: "../reqfield/treequery",
            dataType: "json",//默认text
            contentType: "application/json",
            type: "get"//默认post
        },
        check: {
            enable: true,
            chkStyle: "checkbox"
        },
        callback: {
            onClick: function (event, treeId, treeNode, clickFlag) {
                if(treeNode.id == 0){clearData();$('#saveButton').attr("disabled", true);return false;}
                $.post('../reqfield/fieldconfig', {
                    fieldid: treeNode.id
                }, function (data) {
                    $('#fieelementtype').val(data.fieelementtype);
                    $('#fieelementid').val(data.fieelementid);
                    $('#fieelementname').val(data.fieelementname);
                    $('#fieshowlevel').val(data.fieshowlevel);
                    $('#fiepelementid').val(data.fiepelementid);
                    $('#fiedefaultvalue').val(data.fiedefaultvalue);
                    $('#fieshoworder').val(data.fieshoworder);
                    $('#fieuseflag').val(data.fieuseflag);
                    $('#fieremark').val(data.fieremark);
                    $('#fieldid').val(treeNode.id);
                    $('#saveButton').attr("disabled", false);
                });
            }
        },
        view: {}
    };
    // zTree 的数据属性，深入使用请参考 API 文档（zTreeNode 节点数据详解）
    var zNodes = [];
    $(document).ready(function () {
        zTreeObj = $.fn.zTree.init($("#tree"), setting, zNodes);
    });
</SCRIPT>
<div class="row" id="toolbar">
    <div id="mainTable" class="col-xs-12">
        <div style="padding-top: 5px;">
            <div class="col-xs-12">
                <button type="button" class="btn btn-sm btn-primary " title="保存全部" onclick="AddSection()">
                    保存
                </button>
                <button type="button" class="btn btn-sm btn-primary" title="上一个" onclick="deleteSection()">
                上一个
            </button>
                <button type="button" class="btn btn-sm btn-primary" title="下一个" onclick="deleteSection()">
                下一个
            </button>
                <button type="button" class="btn btn-sm btn-primary" title="计费调整" onclick="deleteSection()">
                    计费调整
                </button>
                <button type="button" class="btn btn-sm btn-primary" title="图像采集" onclick="takingPicture()">
                    图像采集
                </button>
                <button type="button" class="btn btn-sm btn-primary" title="图像导入" onclick="deleteSection()">
                    图像导入
                </button>
                <button type="button" class="btn btn-sm btn-primary" title="预览" onclick="deleteSection()">
                    预览
                </button>
                <button type="button" class="btn btn-sm btn-primary" title="打印" onclick="deleteSection()">
                    打印
                </button>
                <button type="button" class="btn btn-sm btn-primary" title="发送" onclick="deleteSection()">
                    发送
                </button>
                <button type="button" class="btn btn-sm btn-primary" title="导入" onclick="deleteSection()">
                    导入
                </button>
                <button type="button" class="btn btn-sm btn-primary" title="导出" onclick="deleteSection()">
                    导出
                </button>
                <button type="button" class="btn btn-sm btn-primary" title="抄送管理" onclick="deleteSection()">
                    抄送管理
                </button>
                <button type="button" class="btn btn-sm btn-primary" title="发起会诊" onclick="deleteSection()">
                    发起会诊
                </button>
                <button type="button" class="btn btn-sm btn-primary" title="加入随访病例" onclick="deleteSection()">
                    加入随访病例
                </button>
                <button type="button" class="btn btn-sm btn-primary" title="加入我的收藏" onclick="deleteSection()">
                    加入我的收藏
                </button>
            </div>
        </div>
    </div>
</div>

<div class="row" id="maincontent">
    <div class="col-xs-4">
        <div class="widget-box widget-color-green ui-sortable-handle">
            <div class="widget-header">
                <h6 class="widget-title">工作列表</h6>
                <div class="widget-toolbar">
                    <a href="#" data-action="collapse">
                        <i class="ace-icon fa fa-chevron-up">隐藏</i>
                    </a>
                </div>
            </div>

            <div class="widget-body" style="display: block;">
                <div class="widget-main padding-4 scrollable ace-scroll" style="position: relative;">
                    <div class="scroll-content">
                        <div class="content">
                            <div style="display:inline;"><label>病种类别：</label>
                                <select onchange="" id="sampathologyid">
                                    <option value="">--请选择--</option>
                                    <%
                                        User user = WebControllerUtil.getAuthUser();
                                        UserBussinessRelate ubr = user.getUserBussinessRelate();
                                        ApplicationContext ctx= WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
                                        PimsSysPathologyManager pimsSysPathologyManager = (PimsSysPathologyManager) ctx.getBean("pimsSysPathologyManager");
                                        JSONArray items = pimsSysPathologyManager.getPathologyType();
                                        for(Object obj : items) {
                                            JSONObject o = (JSONObject)obj;
                                            StringBuilder builder = new StringBuilder();
                                            builder.append("<option value=\"")
                                                    .append(o.get("pathologyLibId"))
                                                    .append("\" ");
                                            if(ubr.getPathologyLibId().equals(String.valueOf(o.get("pathologyLibId")))) {
                                                builder.append(" selected ");
                                            }
                                            builder.append(">")
                                                    .append(o.get("pathologyLib"))
                                                    .append("</option>\n");
                                            out.print(builder.toString());
                                        }
                                    %>
                                </select>
                            </div>
                            <div style="display:inline;">
                                <label>病理状态：</label>
                                <select id="samsamplestatus">
                                    <option value="">--请选择--</option>
                                    <option value="1">已取材</option>
                                    <option value="2">已包埋</option>
                                    <option value="3">已切片</option>
                                    <option value="4">已初诊</option>
                                    <option value="5">已审核</option>
                                    <option value="6">已发送</option>
                                    <option value="7">会诊中</option>
                                    <option value="8">报告已打印</option>
                                </select>
                            </div>
                            <div><label>切片年月：</label><input type="text" id="samplesectionfrom" style="width: 120px">~<input type="text" style="width: 120px" id="samplesectionto"></div>
                            <div style="display:inline;"><label>条形码：</label><input type="text" id="saminspectionidq" style="width: 120px"></div>
                            <div style="display:inline;"><label>病理号：</label><input type="text" id="sampathologycodeq" style="width: 120px"></div>
                            <div><label>病人名称：</label><input type="text" id="sampatientnameq" style="width: 120px"><button onclick="query()"> 查询 </button></div>
                        </div>
                    </div>
                </div>
            </div>
            <div style="display: block;">
                <div class="col-xs-12 leftContent">
                    <table id="sectionList"></table>
                    <div id="pager"></div>
                </div>
            </div>

        </div>
    </div>

    <div id="diagnosis" class="col-xs-6">
        <div class="widget-box widget-color-green ui-sortable-handle">
            <div class="widget-header">
                <h6 class="widget-title">病理诊断管理</h6>
                <div class="widget-toolbar">
                    <a href="#" data-action="collapse">
                        <i class="ace-icon fa fa-chevron-up">隐藏</i>
                    </a>
                </div>
            </div>
            <div style="display: block;">
                <div class="widget-main padding-4 scrollable ace-scroll" style="position: relative;">
                    <div style="display:inline;"><label>报告转送：</label><input type="text"> <button>转送</button></div>
                    <div style="display:inline;"><label>医嘱管理：</label><input type="text"> <button>申请</button></div>
                </div>
                <div id="tabs" style="margin: 0 auto;">
                    <ul class="tabs">
                        <li class="tab-link"><a href="#tabs-1">病人基本信息</a></li>
                        <li class="tab-link"><a href="#tabs-2">取材信息</a></li>
                    </ul>
                    <div id="tabs-1">
                        <div>
                            <div style="display: inline">病理号：<input type="text" style="width:120px" id="sampathologycode"></div>
                            <div style="display: inline">条形码：<input type="text" style="width:120px" id="saminspectionid"></div>
                            <div style="display: inline">会诊记录：无</div>
                        </div>
                        <div>
                            <div style="display: inline">病人姓名：<input type="text" style="width:120px" id="sampatientname">详细</div>
                            <div style="display: inline">性别：男</div>
                            <div style="display: inline">送检医生：<input type="text" style="width:120px" id="samsenddoctorid"></div>
                        </div>
                        <div>
                            <div style="display: inline">住院号：<input type="text" style="width:120px" id="sampatientnumber"></div>
                            <div style="display: inline">常规收费：<input type="text" style="width:120px" id=""></div>
                            <div style="display: inline">送检科室：<input type="text" style="width:120px" id="samdeptname"></div>
                        </div>
                        <div>
                            <div style="display: inline">床号：<input type="text" style="width:120px" id="sampatientbed"></div>
                            <div style="display: inline">末次月经：<input type="text" style="width:120px" id="reqlastmenstruation"></div>
                            <div style="display: inline">送检医院：<input type="text" style="width:120px" id="samsendhospital"></div>
                        </div>
                        <div>
                            <div style="display: inline">绝经：<input type="checkbox"></div>
                            <div style="display: inline">送检材料：<textarea type="text" id="samsamplename"></textarea></div>
                            <div style="display: inline">临床诊断：<textarea type="text" id="sampatientdignoses"></textarea></div>
                        </div>

                    </div>
                    <div id="tabs-2">
                        <div class="row" id="materialGrid">
                            <div class="col-xs-12 leftContent">
                                <table id="materialList"></table>
                                <div id="pager2"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="ctabs">
            <ul>
                <li><a href="#tabs-3">报告诊断</a></li>
                <li><a href="#tabs-4">免疫组化</a></li>
                <li><a href="#tabs-5">特殊染色</a></li>
                <li><a href="#tabs-6">分子病理</a></li>
            </ul>
            <div id="tabs-3">
                <div><label>巨检描述：</label><button>从模板选择</button><button>存为模板</button><textarea cols="80" rows="3"></textarea></div>
                <div><label>病理所见：</label><button>从模板选择</button><button>存为模板</button><textarea cols="80" rows="3"></textarea></div>
                <div><label>病理诊断：</label><button>从模板选择</button><button>存为模板</button><textarea cols="80" rows="3"></textarea></div>
                <div style="alignment: center"><button>保存</button></div>

            </div>
            <div id="tabs-4">
                <div>
                    <div style="display: inline">诊断报告：<select><option>病理所见模板</option></select><button> 模板保存</button></div>
                    <div style="display: inline"><select><option>病理报告模板</option></select><button> 模板保存</button></div>
                </div>
                <div><label>巨检描述：</label><textarea cols="80" rows="3"></textarea></div>
                <div><label>病理所见：</label><textarea cols="80" rows="3"></textarea></div>
                <div><label>病理诊断：</label><textarea cols="80" rows="3"></textarea></div>
                <div><label>免疫结果：</label><textarea cols="80" rows="3"></textarea></div>
                <div style="alignment: center"><button>保存</button></div>
            </div>
            <div id="tabs-5">
                <div>
                    <div style="display: inline">诊断报告：<select><option>病理所见模板</option></select><button> 模板保存</button></div>
                    <div style="display: inline"><select><option>病理报告模板</option></select><button> 模板保存</button></div>
                </div>
                <div><label>巨检描述：</label><textarea cols="80" rows="3"></textarea></div>
                <div><label>病理所见：</label><textarea cols="80" rows="3"></textarea></div>
                <div><label>病理诊断：</label><textarea cols="80" rows="3"></textarea></div>
                <div><label>特殊染色：</label><textarea cols="80" rows="3"></textarea></div>
                <div style="alignment: center"><button>保存</button></div>
            </div>
            <div id="tabs-6">
                <div>
                    <div style="display: inline">诊断报告：<select><option>病理所见模板</option></select><button> 模板保存</button></div>
                    <div style="display: inline"><select><option>病理报告模板</option></select><button> 模板保存</button></div>
                </div>
                <div><label>巨检描述：</label><textarea cols="80" rows="3"></textarea></div>
                <div><label>病理所见：</label><textarea cols="80" rows="3"></textarea></div>
                <div><label>病理诊断：</label><textarea cols="80" rows="3"></textarea></div>
                <div><label>分子病理：</label><textarea cols="80" rows="3"></textarea></div>
                <div style="alignment: center"><button>保存</button></div>
            </div>
            <div>
                <div>报告日期：<input type="text"> <button>延迟报告</button></div>
                <div>初查医生：<input type="text"> <button>初查签名</button><button>取消</button>确诊病种：<select><option>常规病种</option></select></div>
                <div>审核医生：<input type="text"> <button>审核签名</button><button>取消</button><input type="text"></div>
                <div>
                    <div><label>在线留言 </label><select><option>请选择留言对象</option></select><button>留言发送</button></div>
                    <div>
                        <textarea cols="80" rows="3"></textarea>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="takingPicture" class="col-xs-2">
        <div class="widget-box widget-color-green ui-sortable-handle">
            <div class="widget-header">
                <h6 class="widget-title">图像采集</h6>
                <div class="widget-toolbar">
                    <a href="#" data-action="collapse">
                        <i class="ace-icon fa fa-chevron-up">隐藏</i>
                    </a>
                </div>
            </div>
        </div>
</div>
</html>
