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
    <title>系统收费项目对照信息</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles/ui.jqgrid.css'/>"/>
    <!--script type="name/javascript" src="../scripts/bootstrap.min.js"></script-->
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
    <script type="text/javascript" src="../scripts/validform/Validform.min.js"></script>
    <script type="text/javascript" src="../scripts/layer/layer.js"></script>
    <script type="text/javascript" src="../scripts/pspathology/chargeitemref.js"></script>
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
</style>
<div class="row" id="toolbar">
    <div id="mainTable" class="col-xs-12" style="padding-left:0px!important;">
        <div style="padding-top: 5px;">
            <div class="col-xs-12" style="padding-left:0px!important;">
                <button type="button" class="btn btn-sm btn-primary " title="添加收费项目对照信息" onclick="AddSection()">
                    <i class="ace-icon fa fa-fire bigger-110"></i>
                    添加收费项目对照信息
                </button>
                <button type="button" class="btn btn-sm  btn-success" title="修改收费项目对照信息" onclick="editSection()">
                    <i class="ace-icon fa fa-pencil-square bigger-110"></i>
                    修改收费项目对照信息
                </button>
                <button type="button" class="btn btn-sm btn-danger" title="删除收费项目对照信息" onclick="deleteSection()">
                    <i class="ace-icon fa fa-times bigger-110"></i>
                    删除收费项目对照信息
                </button>
                <div class="input-group col-sm-3 " style="float: right;">
                    <input type="text" id="query" class="form-control search-query" placeholder="输入收费项目名称"/>
                    <span class="input-group-btn">
					<button type="button" class="btn btn-purple btn-sm" onclick="search()">
						<fmt:message key="button.search"/>
						<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
					</button>
				</span>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="row" id="maincontent">
    <div class="col-xs-12 leftContent">
        <table id="sectionList"></table>
        <div id="pager"></div>
    </div>
</div>

<div  class="row" id="hospitalGrid" style="display: none;">
    <div class="col-xs-12 leftContent">
        <table id="sectionList2"></table>
        <div id="pager2"></div>
    </div>
</div>

<div  class="row" id="chargeItemGrid" style="display: none;">
    <div class="col-xs-12 leftContent">
        <table id="sectionList3"></table>
        <div id="pager3"></div>
    </div>
</div>

<div  class="row" id="hisChargeItemGrid" style="display: none;">
    <div class="input-group col-sm-3 " style="float: right;">
        <input type="text" id="hissfxmmc" class="form-control search-query" placeholder="输入收费项目名称"/>
        <span class="input-group-btn">
					<button type="button" class="btn btn-purple btn-sm" onclick="searchHisChargeItem()">
						<fmt:message key="button.search"/>
						<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
					</button>
				</span>
    </div>
    <div class="col-xs-6 leftContent">
        <table id="sectionList4"></table>
        <div id="pager4"></div>
    </div>
</div>

<div id="addDialog" style="display: none;" class="col-xs-12">
    <form class="form-horizontal" id="addSectionForm" action="#" method="post">
        <div style="padding-top: 5px;">
            <div style="">
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="chargeItemName"> 项目名称 </label>
                    <div class="col-xs-8" style="">
                        <input type="text" id="chargeItemName" name="chargeItemName" onclick="showItem()" placeholder="项目名称" class="col-xs-8"
                               datatype="*2-255"/>
                        <input type="hidden" id="chargeitemid" name="chargeitemid">
                        <input type="hidden" id="referenceid" name="referenceid">
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="customerName"> 客户名称 </label>
                    <div class="col-xs-8" style="">
                        <input type="text" id="customerName" name="customerName" onclick="showHospital()" placeholder="英文名称" class="col-xs-8"
                               datatype="*2-255"/>
                        <input type="hidden" id="customercode" name="customercode">
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="refhischargename"> HIS收费名称 </label>
                    <div class="col-xs-8" style="">
                        <input type="text" id="refhischargename" name="refhischargename" onclick="showHisCharge()" placeholder="HIS收费名称" class="col-xs-8"
                               datatype="*2-255"/>
                        <input type="hidden" id="refhischargeid" name="refhischargeid">
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="refhisprice"> HIS收费价格 </label>
                    <div class="col-xs-8" style="">
                        <input type="text" id="refhisprice" name="refhisprice" placeholder="HIS收费价格" class="col-xs-8"
                               datatype="*2-255"/>
                    </div>
                </div>

                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="refsendhis">是否同步至HIS</label>
                    <div class="col-xs-8">
                        <select type="text" id="refsendhis" name="refsendhis" class="col-xs-3">
                            <option value="1">已发送</option>
                            <option value="0">未发送</option>
                        </select>
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="refremark">备注 </label>
                    <div class="col-xs-8">
                    <textarea type="text" id="refremark" name="refremark" placeholder="备注" class="col-xs-8"
                              datatype="*2-255"></textarea>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
</html>