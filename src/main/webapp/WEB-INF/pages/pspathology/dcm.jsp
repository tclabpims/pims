<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<title><fmt:message key="menu.DiseaseCategoriesMaintenance"/></title>
<!--<meta name="menu" content="SampleSet"/>-->
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />
    <!--script type="name/javascript" src="../scripts/bootstrap.min.js"></script-->
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
    <script type="text/javascript" src="../scripts/validform/Validform.min.js"></script>
    <script type="text/javascript" src="../scripts/layer/layer.js"></script>
    <script type="text/javascript" src="../scripts/pspathology/pspathology.js"></script>
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
    .table-bordered>thead>tr>td, .table-bordered>thead>tr>th{
        background-color: #F5F5F6;
    }
    .ui-autocomplete {
        z-index: 99999999;
    }
</style>
<div class="row" id="toolbar">
    <div id="mainTable" class="col-xs-12">
        <div  style="padding-top: 5px;">
            <div class="col-xs-7">
            <button type="button" class="btn btn-sm btn-primary " title="添加病种" onclick="AddSection()">
                <i class="ace-icon fa fa-fire bigger-110"></i>
                <fmt:message key="button.add" />
            </button>
            <button type="button" class="btn btn-sm  btn-success" title="编辑病种" onclick="editSection()">
                <i class="ace-icon fa fa-pencil-square bigger-110"></i>
                <fmt:message key="button.edit" />
            </button>
            <button type="button" class="btn btn-sm btn-danger" title="删除病种" onclick="deleteSection()">
                <i class="ace-icon fa fa-times bigger-110"></i>
                <fmt:message key="button.delete" />
            </button>
            <div class="input-group col-sm-3 " style="float: right;width:28%;">
                <input type="text" id="query" class="form-control search-query" placeholder="输入编号或名称" />
                <span class="input-group-btn">
					<button type="button" class="btn btn-purple btn-sm" onclick="search()">
						<fmt:message key="button.search"/>
						<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
					</button>
				</span>
            </div>
            </div>

            <div  style="padding-top: 5px;float: right">
                <button type="button" class="btn btn-sm btn-primary " title="添加报告单格式" onclick="reportFormat(1)">
                    <i class="ace-icon fa fa-fire bigger-110"></i>
                    <fmt:message key="button.add" />
                </button>
                <button type="button" class="btn btn-sm  btn-success" title="编辑报告单格式" onclick="reportFormat(2)">
                    <i class="ace-icon fa fa-pencil-square bigger-110"></i>
                    <fmt:message key="button.edit" />
                </button>
                <button type="button" class="btn btn-sm btn-danger" title="删除报告单格式" onclick="reportFormat(3)">
                    <i class="ace-icon fa fa-times bigger-110"></i>
                    <fmt:message key="button.delete" />
                </button>
            </div>
        </div>
    </div>
</div>

<div class="row" id="maincontent">
    <div class="col-xs-7 leftContent">
        <table id="sectionList"></table>
        <div id="pager"></div>
    </div>
    <div class="col-xs-5 rightContent">
        <table id="sectionCode"></table>
        <div id="rightPager"></div>
    </div>
</div>


<div id="addDialog" style="display: none;" class="col-xs-12">
    <form class="form-horizontal" id="addSectionForm" action="#" method="post">
        <div style="padding-top: 5px;">
            <div style="">
                <input type="hidden" name="pathologyid" id="pathologyid" value="0"/>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="patnamech"> 病种名称(中文) </label>
                    <div class="col-xs-8" style="">
                        <input type="text" id="patnamech" name= "patnamech" placeholder="病种名称(中文)" class="col-xs-8" datatype="*2-255" />
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="patnameen"> 病种名称(英文) </label>
                    <div class="col-xs-8">
                        <input type="text" id="patnameen" name="patnameen" placeholder="病种名称(英文)" class="col-xs-8" datatype="*2-255"/>
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="patreporttitle"> 报告单抬头名称 </label>
                    <div class="col-xs-8">
                        <input type="text" id="patreporttitle" name="patreporttitle" placeholder="报告单抬头名称" class="col-xs-8" datatype="*2-255"/>
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="patreportremark"> 报告单备注内容 </label>
                    <div class="col-xs-8">
                        <textarea type="text" cols="10" id="patreportremark" name="patreportremark" placeholder="报告单备注内容" class="col-xs-8" datatype="*2-255"></textarea>
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="patdefaultdiagnosis"> 病理诊断内容(默认) </label>
                    <div class="col-xs-8">
                        <textarea type="text" cols="10" id="patdefaultdiagnosis" name="patdefaultdiagnosis" placeholder="病理诊断内容(默认)" class="col-xs-8" datatype="*2-255"></textarea>
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="patcoddingprechar"> 病理编号前缀 </label>
                    <div class="col-xs-8">
                        <input type="text" id="patcoddingprechar" name="patcoddingprechar" placeholder="病理编号前缀" class="col-xs-8" datatype="*2-16"/>
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="patcoddinglength"> 病理编号长度 </label>
                    <div class="col-xs-8">
                        <input type="text" id="patcoddinglength" name="patcoddinglength" placeholder="病理编号长度" class="col-xs-8" datatype="n1-2"/>
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="patstartcodding"> 编号起始号 </label>
                    <div class="col-xs-8">
                        <input type="text" id="patstartcodding" name="patstartcodding" placeholder="编号起始号" class="col-xs-8" datatype="n2-16"/>
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="patsort"> 排序号 </label>
                    <div class="col-xs-8">
                        A<select id="FN" name="FN">
                        <option value="0">0</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                    </select>
                        <select id="SN" name="SN">
                            <option value="0">0</option>
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                            <option value="6">6</option>
                            <option value="7">7</option>
                            <option value="8">8</option>
                            <option value="9">9</option>
                        </select>
                        <select id="TN" name="TN">
                            <option value="0">0</option>
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                            <option value="6">6</option>
                            <option value="7">7</option>
                            <option value="8">8</option>
                            <option value="9">9</option>
                        </select>
                        <input type="hidden" id="patsort" name="patsort"/>
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="patuseflag"> 使用状态</label>
                    <div class="col-xs-8">
                        <select type="text" id="patuseflag" name="patuseflag" class="col-xs-3">
                            <option value="0">使用</option>
                            <option value="1">停用</option>
                        </select>
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="patuseflag"> 是否取材</label>
                    <div class="col-xs-8">
                        <select type="text" id="patissampling" name="patissampling" class="col-xs-2">
                            <option value="0">是</option>
                            <option value="1">否</option>
                            <option value="2">无</option>
                        </select>
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="patuseflag"> 是否特检</label>
                    <div class="col-xs-8">
                        <select type="text" id="patisspecialcheck" name="patisspecialcheck" class="col-xs-2">
                            <option value="0">是</option>
                            <option value="1">否</option>
                        </select>
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="patuseflag"> 是否包埋</label>
                    <div class="col-xs-8">
                        <select type="text" id="patfirstn" name="patfirstn" class="col-xs-2">
                            <option value="0">是</option>
                            <option value="1">否</option>
                        </select>
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="patclass"> 病种分类</label>
                    <div class="col-xs-8">
                        <select type="text" id="patclass" name="patclass" class="col-xs-4">
                            <option value="1">常规细胞学</option>
                            <option value="2">液基细胞学</option>
                            <option value="3">免疫组化</option>
                            <option value="4">病理会诊</option>
                            <option value="5">常规病理</option>
                            <option value="6">术中冰冻</option>
                            <option value="7">HPV</option>
                            <option value="8">外周血细胞</option>
                            <option value="9">骨髓细胞学</option>
                        </select>
                    </div>
                </div>
            </div>
            <input type="hidden" id="segment" name="segment"/>
        </div>
    </form>
</div>
<div id="addFormatDialog" style="display: none;" class="col-xs-12">
    <form class="form-horizontal" id="formateform" action="#" method="post">
        <div style="padding-top: 5px;">
            <div style="">
                <input type="hidden" name="formateid" id="formateid" value="0"/>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="formname"> 格式名称 </label>
                    <div class="col-xs-8" style="">
                        <input type="text" id="formname" name= "formname" placeholder="格式名称" class="col-xs-8" datatype="*2-255" />
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="formweburl"> 报告格式URL </label>
                    <div class="col-xs-8">
                        <textarea type="text" cols="10" id="formweburl" name="formweburl" placeholder="报告格式URL" class="col-xs-8"></textarea>
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="formpicturenum"> 图片数量 </label>
                    <div class="col-xs-8">
                        <input type="text" id="formpicturenum" name="formpicturenum" placeholder="图片数量" class="col-xs-8" datatype="*1-2"/>
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right"> 排序号 </label>
                    <div class="col-xs-8">
                        A<select id="FN1" name="FN1">
                        <option value="0">0</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                    </select>
                        <select id="SN1" name="SN1">
                            <option value="0">0</option>
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                            <option value="6">6</option>
                            <option value="7">7</option>
                            <option value="8">8</option>
                            <option value="9">9</option>
                        </select>
                        <select id="TN1" name="TN1">
                            <option value="0">0</option>
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                            <option value="6">6</option>
                            <option value="7">7</option>
                            <option value="8">8</option>
                            <option value="9">9</option>
                        </select>
                        <input type="hidden" id="formsort" name="formsort"/>
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="formuseflag"> 是否启用</label>
                    <div class="col-xs-8">
                        <select type="text" id="formuseflag" name="formuseflag" class="col-xs-3">
                            <option value="0">是</option>
                            <option value="1">否</option>
                        </select>
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="formisdefault"> 是否默认</label>
                    <div class="col-xs-8">
                        <select type="text" id="formisdefault" name="formisdefault" class="col-xs-2">
                            <option value="0">是</option>
                            <option value="1">否</option>
                        </select>
                    </div>
                </div>
                <div class="form-group" style="margin-left:0px;margin-right:0px;">
                    <label class="col-xs-3 control-label no-padding-right" for="formremark"> 备注 </label>
                    <div class="col-xs-8">
                        <textarea type="text" cols="10" id="formremark" name="formremark" placeholder="备注" class="col-xs-8"></textarea>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>