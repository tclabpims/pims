<%@ page import="org.apache.commons.lang3.StringEscapeUtils" %>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<head>
    <title><fmt:message key="ElectronicApplyManage.title"/></title>
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/bootstrap-datetimepicker.min.css'/>" />
	<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/bootstrap.min.css'/>" />
	<script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
	<script type="text/javascript" src="../scripts/bootstrap.min.js"></script>
	<script type="text/javascript" src="../scripts/i18n/grid.locale-cn.js"></script>
	<script type="text/javascript" src="../scripts/jquery.jqGrid.js"></script>
	<script type="text/javascript" src="<c:url value="/scripts/ace.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/ace-elements.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/bootstrap-tag.min.js"/>"></script>
    <script type="text/javascript" src="../scripts/layer/layer.js"></script>
    <script type="text/javascript" src="../scripts/consultation/cons.js"></script>
	<script type="text/javascript" src="../scripts/validform/Validform.min.js"></script>
	<script type="text/javascript" src="../scripts/bootstrap-datetimepicker.min.js"></script>
	<script type="text/javascript" src="../scripts/jquery.zclip.min.js"></script>
	<script src="<c:url value='/scripts/ajaxfileupload-new.js'/>"></script>
	<style>
		.div_div {float:left;margin:20px 35px 11px 8px;text-align:center;color: #808080;font-size: 12px;  }
		.div_img{cursor:pointer;display: block;margin-bottom:11px;}
		.div_1{background-color: #F9F9F9;height: 106px;border:1px solid #E0E0E0}
		.img_style{width: 18px;height: 23px}
		.label_style{font-size: 12px;color: #323232;height: 24px;text-align:right;}
		.input_style{height: 24px;font-size: 12px!important;}
		.ui-jqgrid-sortable{text-align: center;}
		.ui-jqgrid-hbox{padding-right: 0px!important;}
	</style>
</head>
<body  style="font-family:'Microsoft YaHei',微软雅黑,'MicrosoftJhengHei'!important;">
	<div class="row">
		<input type="hidden" id="samid" value="${sampleId}"/>
		<input type="hidden" id="consampleid" value="${sampleId}"/>
		<input type="hidden" id="consultationid" value="${consultationid}"/>
		<input type="hidden" id="detconsultationid" value="${consultationid}"/>
		<input type="hidden" id="detdoctorid" value="${local_userid}"/>
		<input type="hidden" id="consponsoreduserid" value="${consponsoreduserid}"/>
		<input type="hidden" id="conssts" value="${conconsultationstate}"/>
		<input type="hidden" id="local_userid" value="${local_userid}"/>
		<input type="hidden" id="patclass" value="${patclass}"/>
		<h5 style="float: left;width: 25%;font-size: 14px;"><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;会诊意见&nbsp;&nbsp;&nbsp;&nbsp;</strong></h5>
		<h5 style="float: left;width: 50%;font-size: 14px;"><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;会诊案例&nbsp;&nbsp;&nbsp;&nbsp;</strong></h5>
		<h5 style="float: left;width: 25%;font-size: 14px;margin-bottom: 10px"><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;图像采集&nbsp;&nbsp;&nbsp;&nbsp;</strong></h5>
		<div class="col-sm-3 leftContent" id="div_2">
			<div style="background-color: #F9F9F9;border:1px solid #E0E0E0;height: 530px;overflow:auto;">
				<div id="hzyj">
				</div>
			</div>
		</div>
		<div class="col-sm-7 centerContent" id="formDialog">
			<div class="form-horizontal" style="background-color: #F9F9F9;border:1px solid #E0E0E0;" action="#" method="post" id="sampleForm" >
				<ul id="tabs" class="nav nav-tabs">
					<li class="active">
						<a href="#maintab" data-toggle="tab">
							基本信息
						</a>
					</li>
					<li>
						<a href="#infotab" data-toggle="tab">
							取材信息
						</a>
					</li>
					<li>
						<a href="#tabs-3" data-toggle="tab">
							报告诊断
						</a>
					</li>
					<span style="float: right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
					<button type="button" style="border-radius:3px;float: right;" onclick="importImg()">
						图像导入
					</button>
				</ul>
				<div id="maintab" class="tab-pane fade in active"  style="display: none;">
					<div class="form-group" style="margin-top:10px;margin-bottom: 5px;">
						<label class="col-sm-2 label_style">病理编号:</label>
						<div class="col-sm-4">
							<input type="hidden" id="sampleid"><!--标本id-->
							<input type="hidden" id="samcustomerid"><!--客户id-->
							<input type="hidden" id="samsamplestatus"><!--标本状态-->
							<input type="text" class="input_style" id="sampathologycode" name="sampathologycode" readonly/>
						</div>
						<label class="col-sm-2 label_style" >特检病理:</label>
						<div class="col-sm-4 ">
							<select class="col-sm-8 input_style" id="samisemergency" disabled="true">
								<option value="0">是</option>
								<option value="1">否</option>
							</select>
						</div>
					</div>
					<div class="form-group" style="margin-bottom: 5px;">
						<label class="col-sm-2 label_style">病人姓名:</label>
						<div class="col-sm-4 ">
							<input class="input_style" type="text" id="sampatientname" name="sampatientname" readonly/>
						</div>
						<label class="col-sm-2 label_style" >送检医生:</label>
						<div class="col-sm-4 ">
							<input  class="input_style" type="text" id="samsenddoctorname" readonly/>
						</div>
					</div>
					<div class="form-group" style="margin-bottom: 5px;">
						<label class="col-sm-2 label_style" >住院号:</label>
						<div class="col-sm-4 ">
							<input class="input_style" type="text" id="sampatientnumber" readonly/>
						</div>
						<label class="col-sm-2 label_style">送检科室:</label>
						<div class="col-sm-4">
							<input  class="input_style" type="text" id="samdeptname" readonly/>
						</div>
					</div>
					<div class="form-group" style="margin-bottom: 5px;">
						<label class="col-sm-2 label_style" >床号:</label>
						<div class="col-sm-4 ">
							<input class="input_style" type="text" id="sampatientbed" readonly/>
						</div>
						<label class="col-sm-2 label_style">送检医院:</label>
						<div class="col-sm-4">
							<input  class="input_style" type="text" id="samsendhospital" readonly/>
						</div>
					</div>
					<div class="form-group" style="margin-bottom: 5px;">
						<label class="col-sm-2 label_style">性&nbsp;别:</label>
						<div class="col-sm-4">
							<select class="col-sm-8 input_style" id="sampatientsex" disabled>
								<option value="1">男</option>
								<option value="2">女</option>
								<option value="3">未知</option>
							</select>
						</div>
						<label class="col-sm-2 label_style">送检材料:</label>
						<div class="col-sm-4">
							<input class="input_style" type="text" id="samsamplename" readonly/>
						</div>
					</div>
					<div class="form-group" style="margin-bottom: 5px;">
						<label class="col-sm-2 label_style">组织个数:</label>
						<div class="col-sm-4">
							<input class="input_style" type="text" id="samfirstn" readonly/>
						</div>
						<label class="col-sm-2 label_style">送检日期:</label>
						<div class="col-sm-4">
							<input class="input_style" type="text" id="samsendtime" readonly/>
						</div>
					</div>
					<div class="form-group" style="margin-bottom: 5px;">
						<label class="col-sm-2 label_style">蜡块数:</label>
						<div class="col-sm-4">
							<input class="input_style" type="text" id="nums" readonly/>
						</div>
						<label class="col-sm-2 label_style">接收日期:</label>
						<div class="col-sm-4">
							<input class="input_style" type="text" id="samreceivertime" readonly/>
						</div>
					</div>
					<div class="form-group" style="margin-bottom: 5px;">
						<label class="col-sm-2 label_style">临床诊断:</label>
						<div class="col-sm-10">
							<input type="text" id="sampatientdignoses" readonly class="col-sm-10 input_style"/>
						</div>
					</div>
				</div>
				<div id="infotab" class="tab-pane fade"  style="display: none;">
					<table id="new1">
					</table>
				</div>
				<div id="tabs-3" style="overflow: auto;border: 0px" style="display: none;">
					<form id="diagnosisInfoForm" style="" onsubmit="return false;">
						<%=StringEscapeUtils.unescapeHtml4((String) request.getAttribute("diagnosisItems")) == null ? "请先配置当前病种相关的病理诊断字段、报告项目以及客户相关数据" : StringEscapeUtils.unescapeHtml4((String) request.getAttribute("diagnosisItems"))%>
					</form>
					<div style="display: none;" id="fullScreen"><button onclick="yjfullScreen()">全屏模式</button></div>
					<div id="yjcell" style="display: none;font-size:12px;width:750px;overflow: auto;padding-left:5px">
						<form id="radioForm">
							<table width="86%" border="0" cellspacing="0" style="font-size: 12px">
								<tr>
									<td colspan="3" align="left" height="14px"><strong>标本质量：</strong></td>
									<td align="left">&nbsp;</td>
									<td colspan="3"><strong>生物性病原体：</strong></td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td  height="14px" colspan="3" align="left"><input type="radio" name="sampleEvaluation" id="c1"  onchange="setCheckBoxStatus()"/>
										<label for="c1">满意</label>
									</td>
									<td width="26%" align="left"><input type="radio" name="sampleEvaluation"  onchange="setCheckBoxStatus()" id="c2"/>
										<label for="c2">不满意</label></td>
									<td colspan="2">&nbsp;</td>
									<td colspan="2"><input type="checkbox" name="notSee1" id="c3" onchange="setCheckboxStatus3()" />
										<label for="c3">未见</label></td>
								</tr>
								<tr>
									<td colspan="3" height="14px" align="left"><input type="radio" name="sampleWell" class="c1" onchange="setParentCheckboxStatus1()" id="c4" />
										<label for="c4">见宫颈管/移行区成份</label></td>
									<td colspan="2" align="left"><input type="radio" onchange="setParentCheckboxStatus2()" name="sampleNotGood" id="c6" class="c2" />
										<label for="c6">上皮细胞数量偏低</label></td>
									<td width="4%">&nbsp;</td>
									<td width="20%"><input type="checkbox" name="c9" id="c9" class="g33" onchange="setCheckboxStatus3()" />
										<label for="c9">提示HPV感染</label></td>
									<td width="12%"><input type="checkbox" name="c14" id="c14" class="g33" onchange="setCheckboxStatus3()"  />
										<label for="c14">放线菌</label></td>
								</tr>
								<tr>
									<td colspan="3" height="14px" align="left" width="20%"><input type="radio" name="sampleWell" class="c1" onchange="setParentCheckboxStatus1()" id="c5" />
										<label for="c5">未见宫颈管/移行区成份</label></td>
									<td colspan="3" align="left"><input type="radio" name="sampleNotGood" onchange="setParentCheckboxStatus2()" id="c7" class="c2"  />
										<label for="c7">炎性渗出物遮盖超过75%的鳞状细胞</label></td>
									<td><input type="checkbox" name="c10" id="c10" class="g33" onchange="setCheckboxStatus3()"  />
										<label for="c10">提示疱疹病毒感染</label></td>
									<td><input type="checkbox" name="c13" id="c13" class="g33"  onchange="setCheckboxStatus3()" />
										<label for="c13">线索细胞</label></td>
								</tr>
								<tr>
									<td width="9%" height="14px">&nbsp;</td>
									<td width="8%">&nbsp;</td>
									<td width="12%">&nbsp;</td>
									<td colspan="3"><input type="radio" name="sampleNotGood" onchange="setParentCheckboxStatus2()" id="c8" class="c2"  />
										<label for="c8">血液遮盖超过75%的鳞状细胞</label></td>
									<td><label for="c11">
										<input type="checkbox" name="c12" id="c12" class="g33"  onchange="setCheckboxStatus3()" />
										真菌(念珠菌等)</label></td>
									<td><input type="checkbox" name="c11" id="c11" class="g33"  onchange="setCheckboxStatus3()" />
										<label for="c11">滴虫</label></td>
								</tr>
							</table>
							<table width="100%" border="0" cellspacing="0">
								<tr>
									<td colspan="5" height="14px"><label for="c15"><strong>上皮细胞异常：</strong></label>
										<input type="checkbox" name="c15" id="c15"  onchange="changeCellSee()" />
										<label for="c15">未见</label></td>
									<td width="1%">&nbsp;</td>
									<td colspan="2">&nbsp;</td>
									<td width="7%">&nbsp;</td>
									<td colspan="4"><strong>非肿瘤性所见：</strong></td>
								</tr>
								<tr>
									<td width="10%" height="14px">腺细胞所见：</td>
									<td colspan="2">
										<input type="radio" name="xcell" id="c16" class="g1"/>
										<label for="c16">不典型的</label>
									</td>
									<td width="3%">&nbsp;</td>
									<td colspan="2">鳞状细胞：</td>
									<td colspan="5"><input type="radio" name="xcell4" onclick="makeResult();" id="c28" class="g5" />
										<label for="c28">不典型鳞状上皮细胞(ASC)</label></td>
									<td colspan="2"><input type="checkbox" name="c34" onclick="makeResult();" id="c34" />
										<label for="c34">反应性的细胞变化</label></td>
								</tr>
								<tr>
									<td height="14px">&nbsp;</td>
									<td width="2%">&nbsp;</td>
									<td colspan="4"><input type="radio" name="xcell1" onchange="setStatus1()" id="c17" class="g111" />
										<label for="c17">子宫颈内膜细胞，非特异</label></td>
									<td width="2%">&nbsp;</td>
									<td colspan="3"><input type="radio" onclick="makeResult();" name="xcell5" id="c29" class="g555" />
										<label for="c29">属意义不明者(ASC-US)</label></td>
									<td width="2%">&nbsp;</td>
									<td width="6%">&nbsp;</td>
									<td width="23%"><input type="checkbox" onclick="makeResult();" name="c35" id="c35" />
										<label for="c35">与炎症有关</label>
										<label for="s1"></label>
										<select name="s1" id="s1">
											<option value="轻度">轻度</option>
											<option value="中度">中度</option>
											<option value="重度">重度</option>
										</select></td>
								</tr>
								<tr>
									<td height="14px">&nbsp;</td>
									<td>&nbsp;</td>
									<td colspan="3"><input type="radio" name="xcell1" id="c18" onchange="setStatus1()" class="g111" />
										<label for="c18">子宫内膜细胞，非特异</label></td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td colspan="4"><input type="radio"  onclick="makeResult();" name="xcell5" id="c30" class="g555" />
										<label for="c30">不能排除高级别鳞状上皮内病变(ASC-H)</label></td>
									<td>&nbsp;</td>
									<td><input type="checkbox" name="c36"  onclick="makeResult();" id="c36" />
										<label for="c36">与放射有关</label></td>
								</tr>
								<tr>
									<td height="14px">&nbsp;</td>
									<td>&nbsp;</td>
									<td colspan="3"><input type="radio" name="xcell1" id="c19" onchange="setStatus1()" class="g111" />
										<label for="c19">腺细胞，非特异</label></td>
									<td>&nbsp;</td>
									<td colspan="5"><input type="radio" onclick="makeResult();" name="xcell4" id="c31" class="g6" />
										<label for="c31">低级别鳞状上皮内病变(L-SIL)</label></td>
									<td>&nbsp;</td>
									<td><input type="checkbox" onclick="makeResult();" name="c37" id="c37" />
										<label for="c37">与宫内节育器（IUD）有关</label></td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td colspan="4"><input type="radio" onclick="makeResult();" name="xcell" id="c20" class="g2" />
										<label for="c20">不典型的</label></td>
									<td>&nbsp;</td>
									<td colspan="4">（包括HPV/轻度异型性CIN I）</td>
									<td>&nbsp;</td>
									<td colspan="2"><input type="checkbox" onclick="makeResult();" name="c38" id="c38" />
										<label for="c38">萎缩</label></td>
								</tr>
								<tr>
									<td height="14px">&nbsp;</td>
									<td>&nbsp;</td>
									<td colspan="4"><input type="radio" name="xcell2" id="c21" onchange="setStatus2()" class="g222" />
										<label for="c21">子宫颈内膜细胞,偏向肿瘤性</label></td>
									<td colspan="4"><input type="radio" onclick="makeResult();" name="xcell4" id="c32" class="g7" />
										<label for="c32">高级别鳞状上皮内病变(H-SIL)</label></td>
									<td>&nbsp;</td>
									<td colspan="2">其它：
										<input type="checkbox" onclick="makeResult();" name="c39" id="c39" />
										<label for="c39">子宫内膜细胞出现在≥40岁妇女中（见注释）</label></td>
								</tr>
								<tr>
									<td height="14px">&nbsp;</td>
									<td>&nbsp;</td>
									<td colspan="3"><input type="radio" name="xcell2" id="c22" onchange="setStatus2()" class="g222" />
										<label for="c22">腺细胞,偏向肿瘤性</label></td>
									<td>&nbsp;</td>
									<td colspan="7">（包括中度和重度异型性/CIS/CINⅡ和CIN III）</td>
								</tr>
								<tr>
									<td height="14px">&nbsp;</td>
									<td colspan="4">
										<input type="radio" name="xcell" id="c23" onchange="setStatus3()" class="g3" />
										<label for="c23">子宫颈管原位癌</label>
									</td>
									<td>&nbsp;</td>
									<td colspan="2"><input type="radio" name="xcell4" onclick="makeResult();" id="c33" class="g8" />
										<label for="c33">鳞状细胞癌</label></td>
									<td>&nbsp;</td>
									<td width="6%">&nbsp;</td>
									<td>&nbsp;</td>
									<td colspan="2">&nbsp;</td>
								</tr>
								<tr>
									<td height="14px">&nbsp;</td>
									<td colspan="4"><input type="radio" onclick="makeResult();" name="xcell" id="c24" class="g4" />
										<label for="c24">腺癌</label></td>
									<td>&nbsp;</td>
									<td colspan="2">&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td colspan="2">&nbsp;</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td colspan="3"><input type="radio" name="xcell3" id="c25" onchange="setStatus4()" class="g444" />
										<label for="c25">子宫内膜腺癌</label></td>
									<td>&nbsp;</td>
									<td colspan="2">&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td colspan="2">&nbsp;</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td colspan="3"><input type="radio" name="xcell3" id="c26" onchange="setStatus4()" class="g444" />
										<label for="c26">子宫颈内膜腺癌</label></td>
									<td>&nbsp;</td>
									<td colspan="2">&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td colspan="2">&nbsp;</td>
								</tr>
								<tr>
									<td height="14px">&nbsp;</td>
									<td>&nbsp;</td>
									<td colspan="3"><input type="radio" name="xcell3" id="c27" onchange="setStatus4()" class="g444" />
										<label for="c27">子宫外腺癌</label></td>
									<td>&nbsp;</td>
									<td colspan="2">&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td colspan="2">&nbsp;</td>
								</tr>
							</table>
							<div> <label for="textarea1">判读意见/结果：</label></div>
							<div>
								<textarea name="textarea1" id="textarea1" hiddenValue="" placeholder="判读意见/结果" cols="80" rows="2"></textarea>
								<div>
									<input type="checkbox" name="c40" id="c40" onchange="setBtEnable()" />
									<label for="c40">Cervista高危型HPV DNA检测结果</label>
									<input type="button" disabled name="bt1" id="bt1" value="阴性" onclick="$('#textarea2').val('阴性')" class="btclass"/>
									<input type="button" disabled name="bt2" id="bt2" value="A5/A6阳性"  class="btclass" onclick="setHPVResult('A5/A6(51,56,66)阳性')"/>
									<input type="button" disabled name="bt3" id="bt3" value="A7阳性"  class="btclass" onclick="setHPVResult('A7(18,39,45,59,68)阳性')"/>
									<input type="button" disabled name="bt4" id="bt4" value="A9阳性"  class="btclass" onclick="setHPVResult('A9(16,31,33,35,52,58)阳性')"/>
								</div>
								<div>
									<textarea name="textarea2" id="textarea2" hiddenValue="" placeholder="Cervista高危型HPV DNA检测结果" cols="80" rows="2"></textarea>
								</div>
								<div><label for="textarea3">附注/建议：</label></div>
								<div>
									<input type="hidden" id="textarea0" hiddenValue="">
									<textarea name="textarea3" id="textarea3" hiddenValue="" placeholder="附注/建议" cols="80" rows="2"></textarea>
								</div>
							</div>
						</form>
					</div>
				</div>
				<div id="tabs-MYZH" style="display: none;border: 0">
					<div><table id="MYZHItem"></table></div>
				</div>
				<div id="tabs-TSRS"  style="display: none">
					<div><table id="TSRSItem"></table></div>
				</div>
				<div id="tabs-FZBL" style="display: none">
					<div><table id="FZBLItem"></table></div>
				</div>
				<div class="form-group"
						<c:if test="${consponsoreduserid != local_userid}"> style="margin-top:5px;"</c:if>
						<c:if test="${consponsoreduserid == local_userid}"> style="display: none" </c:if>
						<c:if test="${conconsultationstate != '0'}"> readonly="readonly" </c:if>
				>
					<label class="col-sm-2 label_style">会诊意见:</label>
					<div class="col-sm-10">
						<textarea id="detadvice" style="height:90px;font-size:12px;width: 90%"></textarea>
					</div>
				</div>
				<div class="form-group"
						<c:if test="${consponsoreduserid == local_userid}"> style="margin-top:5px;"</c:if>
						<c:if test="${consponsoreduserid != local_userid}"> style="display: none" </c:if>
						<c:if test="${conconsultationstate != '0'}"> readonly="readonly" </c:if>
				>
					<label class="col-sm-2 label_style">会诊状态:</label>
					<div class="col-sm-10 input_style">
						<input type="radio" name="hzstates" value="0"/>&nbsp;&nbsp;会诊中&nbsp;&nbsp;
						<input type="radio" name="hzstates" value="1"/>&nbsp;&nbsp;会诊终了&nbsp;&nbsp;
						<input type="radio" name="hzstates" value="2"/>&nbsp;&nbsp;会诊取消&nbsp;&nbsp;
					</div>
				</div>
				<div class="form-group"
						<c:if test="${conconsultationstate != '0'}"> style="display: none"  </c:if>
						<c:if test="${conconsultationstate == '0'}"> style="text-align:center; width:100%;height:100%;margin:0px;"  </c:if>
					 >
					<button type="button" style="border-radius:3px;border:1px solid #2274E4;background-color: #108CCF;" id="savebutton" onclick="saveInfo()">
						<span style="color: white;">保存并刷新</span>
					</button>
				</div>
			</div>
		</div>
		<div class="col-sm-2 rightContent" id="formDialog1">
			<div class="widget-box widget-color-green ui-sortable-handle">
				<div id="imgContainer" class="widget-body"></div>
			</div>
		</div>

		<div id="uploadDialog" style="text-align:left;display:none">
			<fieldset style="width:95%; margin-left:4px;">
				<div>
					<div id="more" style="float:left;">
						<div class="col-sm-12" style="margin-top:5px;">
							<input type="file" id="imgFile" name="imgFile" class="col-sm-10"/>
						</div>
					</div>
				</div>
			</fieldset>
		</div>

	</div>
</body>