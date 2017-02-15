<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<head>
    <title>病理统计管理</title>
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
    <script type="text/javascript" src="../scripts/report/countreport.js"></script>
	<script type="text/javascript" src="../scripts/validform/Validform.min.js"></script>
	<script type="text/javascript" src="../scripts/bootstrap-datetimepicker.min.js"></script>
	<script src="<c:url value="/scripts/LodopFuncs.js"/>"></script>
	<style>
		.ui-autocomplete {z-index: 99999999;}
		/*.div_div {float:left;margin:20px 35px 11px 8px;text-align:center;color: #808080;font-size: 12px;  }*/
		/*.div_img{cursor:pointer;display: block;margin-bottom:11px;}*/
		/*.div_1{background-color: #F9F9F9;height: 106px;border:1px solid #E0E0E0}*/
		.img_style{width: 18px;height: 23px}
		.label_style{font-size: 12px;color: #323232;height: 24px;text-align:right;}
		.input_style{height: 24px;font-size: 12px!important; padding-left: 15px}
		.ui-jqgrid-sortable{text-align: center;}
		.ui-jqgrid-hbox{padding-right: 0px!important;}
		.input_check{vertical-align:middle;height: 17px;margin-top: 0px!important;}
		#jqgh_bldjb_cb{
			text-align: center;
			padding-bottom: 20px;
		}
	</style>
</head>
<body  style="font-family:'Microsoft YaHei',微软雅黑,'MicrosoftJhengHei'!important;">
	<div class="div_1" id="div_1">
		<botton class="btn btn-sm btn-primary"  class="div_img" onclick="reportExcel()">
			<i class="ace-icon fa fa-share"></i>
			文件导出
		</botton>
		<botton class="btn btn-sm btn-info"  class="div_img" onclick="printCode()">
			<i class="ace-icon fa fa-file-text-o"></i>
			文件打印
		</botton>
	</div>
	<%--<div class="row">--%>
		<%--<h5 style="float: left;width: 100%;font-size: 14px;"><strong>&nbsp;<img src="/styles/imagepims/countshow.png" class="img_style">&nbsp;统计一览</strong></h5>--%>
	<%--</div>--%>
	<div class="row" id="div_2" style="margin-left: 0px;margin-right: 0px">
		<div class="widget-box widget-color-green ui-sortable-handle">
			<div class="widget-header">
				<h6 class="widget-title">统计一览</h6>
				<%--<div class="widget-toolbar">--%>
					<%--<a href="#" data-action="collapse">--%>
						<%--<i class="ace-icon fa fa-chevron-up">隐藏</i>--%>
					<%--</a>--%>
				<%--</div>--%>
			</div>
			<div id="search_div_1" style="background-color: #F9F9F9;height:100px;border:1px solid #E0E0E0;">
			<div style="margin-top:10px;">
				<div style="margin-bottom: 5px;">
					<span class="input_style">&nbsp;&nbsp;统计年月:&nbsp;</span>
					<input type="text" class="form_datetime input_style" value="${sevenday}" id="req_bf_time"/>
					<span class="input_style">~</span>
					<input type="text" class="form_datetime input_style" value="${receivetime}"  id="req_af_time"/>
					<span class="input_style">&nbsp;文件格式:&nbsp;</span>
					<input type="checkbox" class="input_check" name="gs" id="excel" checked><span class="input_style">&nbsp;EXCEL&nbsp;</span>
					<input type="checkbox" class="input_check" name="gs"  id="txt" checked><span class="input_style">&nbsp;TXT&nbsp;</span>
					<input type="checkbox" class="input_check" name="gs" id="html" checked><span class="input_style">&nbsp;HTML&nbsp;</span>
					<input type="checkbox" class="input_check" name="gs" id="xml" checked><span class="input_style">&nbsp;XML&nbsp;</span>
				</div>
				<div style="margin-bottom: 5px;">&nbsp;
					<input type="checkbox" class="input_check" name="biao" value="1" id="rztj1"  checked><span class="input_style">&nbsp;日志统计&nbsp;</span>
					<input type="checkbox" class="input_check" name="biao" value="2" id="bbytj1" checked><span class="input_style">&nbsp;标本源统计&nbsp;</span>
					<input type="checkbox" class="input_check" name="biao" value="3" id="sftj1" checked><span class="input_style">&nbsp;收费统计&nbsp;&#12288;</span>
					<input type="checkbox" class="input_check" name="biao" value="4" id="gzltj1" checked><span class="input_style">&nbsp;工作量统计&nbsp;&#12288;</span>
					<input type="checkbox" class="input_check" name="biao" value="5" id="ltj1" checked><span class="input_style">&nbsp;率统计&nbsp;</span>
					<span style="float: right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
					<span>
						<button type="button" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;float: right;padding:0 0" onclick="searchList()">
							<span style="color: white;">查询</span>
						</button>
					</span>
				</div>
				<div style="margin-bottom: 5px;">&nbsp;
					<input type="checkbox" class="input_check" name="biao" value="6" id="fltj1" checked><span class="input_style">&nbsp;分类统计&nbsp;</span>
					<input type="checkbox" class="input_check" name="biao" value="7" id="bbdjb1" checked><span class="input_style">&nbsp;标本登记簿&nbsp;</span>
					<input type="checkbox" class="input_check" name="biao" value="8" id="bgqsb1" checked><span class="input_style">&nbsp;报告签收本&nbsp;</span>
					<input type="checkbox" class="input_check" name="biao" value="9" id="jbnlfb1" checked><span class="input_style">&nbsp;疾病年龄分布&nbsp;</span>
					<input type="checkbox" class="input_check" name="biao" value="10" id="bdzddz1" checked><span class="input_style">&nbsp;冰冻诊断对照&nbsp;</span>
					<input type="checkbox" class="input_check" name="biao" value="11" id="hztj1" checked><span class="input_style">&nbsp;会诊统计&nbsp;</span>
				</div>
			</div>
		</div>
		</div>
	</div>
	<div>
		<div id="rztj" style="display:block" class="widget-box widget-color-green ui-sortable-handle"><!--日志统计-->
			<div style="font-size: 16px;text-align:center;line-height: 35px " class="widget-header">工作日志统计报告</div>
			<span name="rqxx" class="input_style">统计日期:<div class="sevenday" style="display: inline-block"></div>~<div class="receivetime" style="display: inline-block"></div></span>
			<div class="widget-body" style="overflow:auto;margin-top: 5px">
				<div class="widget-main no-padding" style="margin-left: 5px">
					<table id="rztj_new0" class="table-striped">
					</table>
				</div>
			</div>
			<div class="widget-body" style="overflow:auto;margin-top: 5px">
				<div class="widget-main no-padding" style="margin-left: 5px">
					<table id="rztj_new1" class="table-striped">
					</table>
				</div>
			</div>
		</div>
		<div id="bbytj" style="display:block" class="widget-box widget-color-green ui-sortable-handle"><!--标本源统计-->
			<div style="font-size: 16px;text-align:center;line-height: 35px " class="widget-header">标本来源统计报告</div>
			<span name="rqxx" class="input_style">统计日期:<div class="sevenday" style="display: inline-block"></div>~<div class="receivetime" style="display: inline-block"></div></span>
			<div>
				<div class="widget-body col-sm-4" style="overflow:auto;margin-top: 5px">
					<div class="widget-main no-padding">
						<table id="bbytj_new0" class="table-striped">
						</table>
					</div>
				</div>
				<div class="widget-body col-sm-4" style="overflow:auto;margin-top: 5px">
					<div class="widget-main no-padding">
						<table id="bbytj_new1" class="table-striped">
						</table>
					</div>
				</div>
				<div class="widget-body col-sm-4" style="overflow:auto;margin-top: 5px">
					<div class="widget-main no-padding">
						<table id="bbytj_new2" class="table-striped">
						</table>
					</div>
				</div>
				<div style="clear:both"></div>
			</div>
		</div>
		<div id="sftj" style="display:block" class="widget-box widget-color-green ui-sortable-handle"><!--收费统计-->
			<div style="font-size: 16px;text-align:center;line-height: 35px " class="widget-header">收费统计报告</div>
			<span name="rqxx" class="input_style">统计日期:<div class="sevenday" style="display: inline-block"></div>~<div class="receivetime" style="display: inline-block"></div></span>
			<div>
				<div class="widget-body col-sm-4" style="overflow:auto;margin-top: 5px">
					<div class="widget-main no-padding">
						<table id="sftj_new0" class="table-striped">
						</table>
					</div>
				</div>
				<div class="widget-body col-sm-4" style="overflow:auto;margin-top: 5px">
					<div class="widget-main no-padding">
						<table id="sftj_new1" class="table-striped">
						</table>
					</div>
				</div>
				<div class="widget-body col-sm-4" style="overflow:auto;margin-top: 5px">
					<div class="widget-main no-padding">
						<table id="sftj_new2" class="table-striped">
						</table>
					</div>
				</div>

			</div>
			<div >
				<div class="widget-body col-sm-4" style="overflow:auto;margin-top: 20px">
					<div class="widget-main no-padding">
						<table id="sftj_new3" class="table-striped">
						</table>
					</div>
				</div>
				<div class="widget-body col-sm-4" style="overflow:auto;margin-top: 20px">
					<div class="widget-main no-padding">
						<table id="sftj_new4" class="table-striped">
						</table>
					</div>
				</div>
				<div class="widget-body col-sm-4" style="overflow:auto;margin-top: 20px">
					<div class="widget-main no-padding">
						<table id="sftj_new5" class="table-striped">
						</table>
					</div>
				</div>
			</div>
			<div style="clear:both"></div>
		</div>
		<div id="gzltj" style="display:block" class="widget-box widget-color-green ui-sortable-handle"><!--工作量统计-->
			<div style="font-size: 16px;text-align:center;line-height: 35px " class="widget-header">标本不合格表</div>

			<span name="rqxx" class="input_style">统计日期:<div class="sevenday" style="display: inline-block"></div>~<div class="receivetime" style="display: inline-block"></div></span>
			<div>
				<div class="widget-body" style="overflow:auto;margin-top: 5px">
					<div class="widget-main no-padding">
						<table id="bhg" class="table-striped">
						</table>
					</div>
				</div>
				<%--<div class="widget-body" style="overflow:auto;margin-top: 5px">--%>
					<%--<div class="widget-main no-padding">--%>
						<%--<table id="gzltj_new1" class="table-striped">--%>
						<%--</table>--%>
					<%--</div>--%>
				<%--</div>--%>
			<%--</div>--%>
			<%--<div>--%>
				<%--<div class="widget-body col-sm-6" style="overflow:auto;margin-top: 5px">--%>
					<%--<div class="widget-main no-padding">--%>
						<%--<table id="gzltj_new2" class="table-striped">--%>
						<%--</table>--%>
					<%--</div>--%>
				<%--</div>--%>
				<%--<div class="widget-body col-sm-6" style="overflow:auto;margin-top: 5px">--%>
					<%--<div class="widget-main no-padding">--%>
						<%--<table id="gzltj_new3" class="table-striped">--%>
						<%--</table>--%>
					<%--</div>--%>
				<%--</div>--%>
				<div style="clear:both"></div>
			</div>
		</div>
		<div id="ltj" style="display: block;" class="widget-box widget-color-green ui-sortable-handle">
			<div style="font-size: 16px;text-align:center;line-height: 35px " class="widget-header">病理登记薄</div>

			<span name="rqxx" class="input_style">统计日期:<div class="sevenday" style="display: inline-block"></div>~<div class="receivetime" style="display: inline-block"></div></span>
			<div>
			<div class="widget-body" style="overflow:auto;margin-top: 5px">
				<div class="widget-main no-padding">
					<table id="bldjb" class="table-striped">
					</table>
				</div>
			</div>
			<div class="widget-body" style="overflow:auto;margin-top: 5px">
				<div class="widget-main no-padding">
					<table id="ltj_new1" class="table-striped">
					</table>
				</div>
			</div>
			<div style="clear:both"></div>
		</div>
		</div>
		<div id="gzl" style="display:block" class="widget-box widget-color-green ui-sortable-handle"><!--分类统计-->
			<div style="font-size: 16px;text-align:center;line-height: 35px " class="widget-header">医生、技师工作量统计</div>

			<span name="rqxx" class="input_style">统计日期:<div class="sevenday" style="display: inline-block"></div>~<div class="receivetime" style="display: inline-block"></div></span>
			<div>
				<div class="widget-body col-sm-4" style="overflow:auto;margin-top: 20px">
			<div class="widget-main no-padding">
				<table id="gzl_new0" class="table-striped">
				</table>
			</div>
			</div>
			<div class="widget-body col-sm-4" style="overflow:auto;margin-top: 20px">
				<div class="widget-main no-padding">
					<table id="gzl_new1" class="table-striped">
					</table>
				</div>
			</div>
			<div class="widget-body col-sm-4" style="overflow:auto;margin-top: 20px">
				<div class="widget-main no-padding">
					<table id="gzl_new2" class="table-striped">
					</table>
				</div>
			</div>
			<div class="widget-body col-sm-4" style="overflow:auto;margin-top: 20px">
				<div class="widget-main no-padding">
					<table id="gzl_new3" class="table-striped">
					</table>
				</div>
			</div>
			<div class="widget-body col-sm-4" style="overflow:auto;margin-top: 20px">
				<div class="widget-main no-padding">
					<table id="gzl_new4" class="table-striped">
					</table>
				</div>
			</div>
				<div style="clear:both"></div>
			</div>
		</div>
		<div id="bbdjb" style="display:block" class="widget-box widget-color-green ui-sortable-handle"><!--标本登记簿-->
			<div style="font-size: 16px;text-align:center;line-height: 35px " class="widget-header">科室工作量统计</div>

			<span name="rqxx" class="input_style">统计日期:<div class="sevenday" style="display: inline-block"></div>~<div class="receivetime" style="display: inline-block"></div></span>
			<div>
			<div class="widget-body" style="overflow:auto;margin-top: 5px">
				<div class="widget-main no-padding">
					<table id="ksgzl_new" class="table-striped">
					</table>
				</div>
			</div>
			</div>
		</div>
		<div id="bgqsb" style="display:block" class="widget-box widget-color-green ui-sortable-handle"><!--报告签收本-->
			<div style="font-size: 16px;text-align:center;line-height: 35px " class="widget-header">冰冻与常规诊断对照表</div>
				<span name="rqxx" class="input_style">统计日期:<div class="sevenday" style="display: inline-block"></div>~<div class="receivetime" style="display: inline-block"></div></span>
					<div>
						<div class="widget-body" style="overflow:auto;margin-top: 5px">
							<div class="widget-main no-padding">
								<table id="bdjg_new0" class="table-striped">
								</table>
							</div>
						</div>
					</div>
		</div>
		<div id="jbnlfb" style="display:block" class="widget-box widget-color-green ui-sortable-handle"><!--疾病年龄分布-->
			<h5 style="width: 100%;font-size: 16px;text-align:center; ">工作日志统计报告</h5>
			<span name="rqxx" class="input_style">统计日期:<div class="sevenday" style="display: inline-block"></div>~<div class="receivetime" style="display: inline-block"></div></span>
				<div>
					<div class="widget-body" style="overflow:auto;margin-top: 5px">
						<div class="widget-main no-padding">
							<table id="jbnlfb_new0" class="table-striped">
							</table>
						</div>
					</div>
				</div>
		</div>
		<div id="bdzddz" style="display:block" class="widget-box widget-color-green ui-sortable-handle"><!--冰冻诊断对照-->
			<h5 style="width: 100%;font-size: 16px;text-align:center; ">工作日志统计报告</h5>
			<span name="rqxx" class="input_style">统计日期:<div class="sevenday" style="display: inline-block"></div>~<div class="receivetime" style="display: inline-block"></div></span>
			<div>
			<div class="widget-body" style="overflow:auto;margin-top: 5px">
				<div class="widget-main no-padding">
					<table id="bdzddz_new0" class="table-striped">
					</table>
				</div>
			</div>
			<div class="widget-body" style="overflow:auto;margin-top: 5px">
				<div class="widget-main no-padding">
					<table id="bdzddz_new1" class="table-striped">
					</table>
				</div>
			</div>
			</div>
		</div>
		<div id="hztj" style="display:block" class="widget-box widget-color-green ui-sortable-handle"><!--会诊统计-->
			<h5 style="width: 100%;font-size: 16px;text-align:center; ">工作日志统计报告</h5>
			<span name="rqxx" class="input_style">统计日期:<div class="sevenday" style="display: inline-block"></div>~<div class="receivetime" style="display: inline-block"></div></span>
			<div>
			<div class="widget-body" style="overflow:auto;margin-top: 5px">
				<div class="widget-main no-padding">
					<table id="hztj_new0" class="table-striped">
					</table>
				</div>
			</div>
			</div>
		</div>
	</div>

</body>