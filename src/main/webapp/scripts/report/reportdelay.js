var nowrow = "";
var GRID_SELECTED_ROW_SAMPLEID = "";
var GRID_SELECTED_ROW_SAMPCUSTOMERID = "";
var PIC_TAKING_FROM = 1;
var withpiece = "";
/**
 * 设置颜色
 * @param id
 */
function setcolor(id){
	var ids = $("#new").getDataIDs();
	$.each(ids, function (key, val) {
		$("#new").children().children("tr[id='"+ids[key]+"']").removeClass("ui-state-highlight");
	});
	$("#new").children().children("tr[id='"+id+"']").addClass("ui-state-highlight");
}
/**
 * 上一个
 */
function upSample(){
	id = nowrow;
	clearData();
	var ids = $("#new").jqGrid('getDataIDs');
	var minId = Math.min.apply(Math,ids);
	if(id == minId){
		id = Math.max.apply(Math,ids);
	}else{
		id = parseInt(id) - 1;
	}
	fillInfo(id);
}
/**
 * 下一个
 */
function downSample() {
	id = nowrow;
	clearData();
	var ids = $("#new").jqGrid('getDataIDs');
	var maxId = Math.max.apply(Math,ids);
	if(id == maxId){
		id = Math.min.apply(Math,ids);
	}else{
		id = parseInt(id) + 1;
	}
	fillInfo(id);
}
/**
 * 清除数据
 */
function clearData() {
    $('#sampleForm')[0].reset();
	jQuery("#new1").jqGrid("clearGridData");
}
/**
 * 初始化
 */
$(function() {
	$(".form_datetime").datetimepicker({
		minView: "month", //选择日期后，不会再跳转去选择时分秒
		format: "yyyy-mm-dd", //选择日期后，文本框显示的日期格式
		language: 'zh-CN', //汉化
		todayBtn:  1,
		autoclose:true //选择日期后自动关闭
	});
	$("#infotab").css('display','block');
	$('#tabPanel a:first').tab('show');//初始化显示哪个tab
	$('#tabPanel a').click(function (e) {
		$('#tabPanel').find('li').each(function(){
			$($(this).find('a').attr("href")).css('display','none');
		});
		e.preventDefault();//阻止a链接的跳转行为
		$($(this).attr("href")).css('display','block');
		$(this).tab('show');//显示当前选中的链接及关联的content
	});
	var width1 = $("#formDialog").width()-20;
	withpiece = width1*0.66;
	createNew1("",withpiece);
	var req_bf_time = $('#req_bf_time').val();//送检FROM
	var req_af_time = $('#req_af_time').val();//送检TO
	var req_code = $('#req_code').val();//病理号码
	var patient_name = $('#patient_name').val();//病人姓名
	var send_doctor = $('#send_doctor').val();//送检医生
	var send_dept = $('#send_dept').val();//送检科室
	var send_hosptail = $('#send_hosptail').val();//送检医院
	var piedoctorname = $('#piedoctorname').val();//取材医生
	$("#new").jqGrid({
		url: "../report/reportdelay/list",
		mtype: "GET",
		datatype: "json",
		postData:{"req_bf_time":req_bf_time,
					"req_af_time":req_af_time,
					"req_code":req_code,
					"patient_name":patient_name,
					"send_doctor":send_doctor,
					"send_dept":send_dept,
					"send_hosptail":send_hosptail,
					"piedoctorname":piedoctorname},
		colNames: ['标本ID','病种类别', '病理号', '病人姓名','年龄','性别','送检日期','预计报告','申请医生','延迟原因','病理状态','送检医生','初步诊断意见','病理诊断',
			'申请时间','延迟天数'],
		colModel: [
			{name:'sampleid',hidden:true},//标本ID
			{ name: 'sampathologyid', index: 'sampathologyid',formatter: "select", editoptions:{value:gettypes()},align:'center'},//病种类别
			{ name: 'sampathologycode', index: 'sampathologycode',align:'center'},//病理号
			{ name: 'sampatientname', index: 'sampatientname',align:'center'},//病人姓名
			{ name: 'sampatientage', index: 'sampatientage',formatter:function(cellvalue, options, row){
				var cell1 = cellvalue.charAt(cellvalue.length - 1);
				if(cell1 == "1"){
					cell1="岁";
				}else if(cell1 == "2"){
					cell1="月";
				}else if(cell1 == "4"){
					cell1="周";
				}else if(cell1 == "5"){
					cell1="日";
				}else if(cell1 == "6"){
					cell1="小时";
				}
				return cellvalue.substr(0,cellvalue.length-1)+cell1;
			},align:'center'},//年龄
			{ name: 'sampatientsex', index: 'sampatientsex',formatter: "select", editoptions:{value:"0:男;1:女;2:未知"},align:'center'},//性别
			{ name: 'samsendtime', index: 'samsendtime',formatter:function(cellvalue, options, row)
			{if(cellvalue == "" || cellvalue == null){return ""}return CurentTime(new Date(cellvalue))},align:'center'},//送检日期
			{ name: 'delreporttime', index: 'delreporttime',formatter:function(cellvalue, options, row)
			{if(cellvalue == "" || cellvalue == null){return ""}return CurentTime(new Date(cellvalue))},align:'center'},//预计报告
			{ name: 'deldoctor', index: 'deldoctor',align:'center'},//申请医生
			{ name: 'delreason', index: 'delreason',align:'center'},//延迟原因
			{ name: 'samsamplestatus', index: 'samsamplestatus',formatter: "select", editoptions:{value:"0:未取材;1:已取材;2:已包埋;3:已切片;4:已初诊;" +
			"5:已审核;6:已发送;7:会诊中:8:报告已打印"},align:'center'},//病理状态
			{name:'samsenddoctorname',hidden:true},//送检医生
			{name:'deldiagnosis',hidden:true},//初步诊断意见
			{name:'restestresult',hidden:true},//病理诊断
			{name:'delcreatetime',hidden:true,formatter:function(cellvalue, options, row)
			{if(cellvalue == "" || cellvalue == null){return ""}return CurentTime(new Date(cellvalue))}},//申请时间
			{name:'deldays',hidden:true}//延迟天数
		],
		beforeSelectRow: function (rowid, e) {
			return $(e.target).is('input[type=checkbox]');
		},
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
			var ids = $("#new").jqGrid('getDataIDs');
			if(ids != null && ids != ""){
				fillInfo(1);
			}
			//$("#new").setSelection(1);
		},
		ondblClickRow: function (id) {
			fillInfo(id);
		},
		// onSelectRow:function(id){
		// 	fillInfo();
		// },
		onCellSelect:function(id){
			fillInfo(id);
		},
		multiselect: true,
		viewrecords: true,
		height:200,
		width: width1,
		// shrinkToFit:false,
		// autoScroll: true,
		rowNum: 10,
		rowList:[10,20,30],
		rownumbers: true, // 显示行号
		rownumWidth: 30, // the width of the row numbers columns
		pager: "#pager"
	});
	// $("#pager_left").remove();
});
/**
 * 初始化取材列表
 * @param reqid
 */
function createNew1(reqid,width1){
	$("#new1").jqGrid({
		url:"../pathologysample/pieces/ajax/getItem",
		datatype: "json",
		mtype:"GET",
		height:130,
		width: width1,
		// shrinkToFit:false,
		// autoScroll: true,
		postData:{"reqId":reqid},
		colNames: ['病理号', '取材序号','材块数', '取材部位','取材医生','录入员', '取材时间'],
		colModel: [
			{ name: 'piepathologycode', index: 'piepathologycode'},//病理号
			{ name: 'piesamplingno', index: 'piesamplingno'},//取材序号
			{ name: 'piecounts', index: 'piecounts'},//材块数
			{ name: 'pieparts', index: 'pieparts'},//取材部位
			{ name: 'piedoctorname', index: 'piedoctorname'},//取材医生
			{ name: 'pierecordername', index: 'pierecordername'},//录入员
			{ name: 'piesamplingtime', index: 'piesamplingtime',formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))}}//取材时间
			],
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		viewrecords: true,
		//autowidth: true,
		//shrinkToFit:false,
		//autoScroll: true,
		rownumbers : true
	});
}
/**
 * 查询数据
 */
function searchList() {
	var req_bf_time = $('#req_bf_time').val();//送检FROM
	var req_af_time = $('#req_af_time').val();//送检TO
	var req_code = $('#req_code').val();//病理号码
	var patient_name = $('#patient_name').val();//病人姓名
	var send_doctor = $('#send_doctor').val();//送检医生
	var send_dept = $('#send_dept').val();//送检科室
	var send_hosptail = $('#send_hosptail').val();//送检医院
	var piedoctorname = $('#piedoctorname').val();//取材医生
	jQuery("#new").jqGrid("clearGridData");
	jQuery("#new").jqGrid('setGridParam',{
		url: "../report/reportdelay/list",
		//发送数据
		postData : {"req_bf_time":req_bf_time,
			"req_af_time":req_af_time,
			"req_code":req_code,
			"patient_name":patient_name,
			"send_doctor":send_doctor,
			"send_dept":send_dept,
			"send_hosptail":send_hosptail,
			"piedoctorname":piedoctorname},
		page : 1
	}).trigger('reloadGrid');//重新载入
}
function fillInfo(id){
	setcolor(id);
	nowrow = id;
	clearData();
	var rowData = $("#new").jqGrid('getRowData',id);
	if (id == null || id == 0) {
		layer.msg('请先选择数据', {icon: 2, time: 1000});
		return false;
	}
	getSampleData(rowData);
	jQuery("#new1").jqGrid('setGridParam',{
		url: "../pathologysample/pieces/ajax/getItem",
		//发送数据
		postData : {"reqId":rowData.sampleid}
	}).trigger('reloadGrid');//重新载入
}
function getSampleData(rowData) {
	var sampatientsex = rowData.sampatientsex;
	if(sampatientsex =="0"){
		sampatientsex="男"
	}else if(sampatientsex =="1"){
		sampatientsex="女"
	}else if(sampatientsex =="2"){
		sampatientsex="未知"
	}
	var sampatientage = rowData.sampatientage
	var cell1 = sampatientage.charAt(sampatientage.length - 1);
	if(cell1 == "1"){
		cell1="岁";
	}else if(cell1 == "2"){
		cell1="月";
	}else if(cell1 == "4"){
		cell1="周";
	}else if(cell1 == "5"){
		cell1="日";
	}else if(cell1 == "6"){
		cell1="小时";
	}
	sampatientage =  sampatientage.substr(0,sampatientage.length-1)+cell1;
	$("#jbxx1").val("病理号:"+rowData.sampathologycode+";姓名:"+rowData.sampatientname+";性别:"+sampatientsex+";年龄:"+sampatientage+";"+
			"预计报告:"+rowData.delreporttime+";延迟天数:"+rowData.deldays+"天;"+
	"送检时间:"+rowData.samsendtime+";送检医生:"+rowData.samsenddoctorname+";\n"
	+"申请时间:"+rowData.delcreatetime+";申请医生:"+rowData.deldoctor);
	$("#lczd").val(rowData.deldiagnosis);
	$("#blzd").val(rowData.restestresult);
	getOrderTabs(rowData.sampleid);
}

function getOrderTabs(sampleId) {
	var ul = $("#tabPanel");
	$.get("../order/getrequestedorder", {sampleId: sampleId}, function (data) {
		var ret = data.rows;
		var myzh = document.getElementById("order_MYZH");
		var fzbl = document.getElementById("order_FZBL");
		var tsrs = document.getElementById("order_TSRS");
		if (myzh != null)myzh.parentNode.removeChild(myzh);
		if (fzbl != null)fzbl.parentNode.removeChild(fzbl);
		if (tsrs != null)tsrs.parentNode.removeChild(tsrs);
		if (ret != null && ret.length > 0) {
			for (var i = 0; i < ret.length; i++) {
				if (ret[i].tesenglishname == "MYZH" || ret[i].tesenglishname == "FZBL" || ret[i].tesenglishname == "TSRS") {
					var li_ = "<li id=\"order_" + ret[i].tesenglishname + "\"><a href=\"#tabs-" + ret[i].tesenglishname + "\" data-toggle=\"tab\">" + ret[i].teschinesename + "</a></li>";
					ul.append(li_);
					initItemList(ret[i].tesenglishname, sampleId, ret[i].testitemid);
				}
			}
		}
		// $("#ctabs").tabs("refresh");
		$("#infotab").css('display','block');
		$('#tabPanel a:first').tab('show');//初始化显示哪个tab
		$('#tabPanel a').click(function (e) {
			$('#tabPanel').find('li').each(function(){
				$($(this).find('a').attr("href")).css('display','none');
			});
			e.preventDefault();//阻止a链接的跳转行为
			$($(this).attr("href")).css('display','block');
			$(this).tab('show');//显示当前选中的链接及关联的content
		});
	});
}

function initItemList(n, sampleId, testItemId) {
	$("#" + n + "Item").jqGrid({
		mtype: "GET",
		url: "../order/getcheckitems?sampleId=" + sampleId + "&testItemId=" + testItemId,
		datatype: "json",
		colNames: ['checkid', '蜡块编号', '项目名称', '结果', '申请医生', '状态', '申请时间', 'chenameen', 'cheischarge'],
		colModel: [
			{name: 'checkid', index: 'checkid', hidden: true},
			{name: 'chiparaffincode', index: 'chiparaffincode', width: 90},
			{name: 'chenamech', index: 'chenamech', width: 120},
			{
				name: 'chetestresult',
				index: 'chetestresult',
				width: 120,
				editable: true,
				edittype: 'text',
				editrules: {edithidden: true, required: true}
			},
			{name: 'checreateuser', index: 'checreateuser', width: 80},
			{
				name: 'finishstatus',
				index: 'finishstatus',
				width: 60,
				formatter: "select",
				editoptions: {value: "0:未完成;1:已完成;"}
			},
			{name: 'checreatetime', index: 'checreatetime', width: 100},
			{name: 'chenameen', index: 'chenameen', hidden: true},
			{name: 'cheischarge', index: 'cheischarge', hidden: true}
		],
		// shrinkToFit: true,
		// scrollOffset: 2,
		width:withpiece,
		rownumbers: true
	});
}

function CurentTime(now) {
	//var now = new Date();
	var year = now.getFullYear();       //年
	var month = now.getMonth() + 1;     //月
	var day = now.getDate();            //日
	var hh = now.getHours();            //时
	var mm = now.getMinutes();          //分
	var ss = now.getSeconds();    //秒
	var clock = year + "-";
	if(month < 10)
		clock += "0";
	clock += month + "-";
	if(day < 10)
		clock += "0";
	clock += day + " ";
	if(hh < 10)
		clock += "0";
	clock += hh + ":";
	if (mm < 10) clock += '0';
	clock += mm + ":";
	if(ss < 10) clock +='0';
	clock += ss;
	return(clock);
}


function printCode() {
	//打印标本条码号
	var ids = $("#new").jqGrid('getGridParam', 'selarrrow');
	if(ids == null || ids == ""){
		layer.msg("请选择打印数据!", {icon:2, time: 1000});
		return;
	}
	var saveDatas = [];
	$.each(ids, function (key, val) {
		var rowData = $("#new").jqGrid("getRowData", ids[key]);
		reportView(1, null, null,rowData.sampleid);
	});
}

function reportView(v, showPicNum, templateUrl,samid) {
	$.get("../diagnosis/report/getTemplate", {"sampleid": samid}, function (data) {
			var rows = data.rows;
			if (rows.length > 0) {
				$("#reportTemplateSelect").empty();
				for (var i = 0; i < rows.length; i++) {
					$("#reportTemplateSelect").append("<option value='" + rows[i].formweburl + "' picNum='" + rows[i].formpicturenum + "'>" + rows[i].formname + "</option>");
				}
				var picNum = showPicNum == null?$("#reportTemplateSelect").find("option:first").attr("picNum"):showPicNum;
				var template = templateUrl== null?$("#reportTemplateSelect").find("option:first").val():templateUrl;
				$.get("../diagnosis/report/print", {
					"sampleid": samid,
					"templateUrl": template,
					"type": v,
					"picpictureclass": 2,
					"picNum": picNum
				}, function (data) {
					if (v == 1) {
						var rptView = layer.open({
							type: 2,
							title: "报告单预览",
							area: ['854px', '600px'],
							btn: ["打印", "切换模板", "关闭"],
							maxmin: true,
							shade: 0.5,
							content: data.url,
							yes: function (index1, layero1) {
								print(data.url);
								layer.close(index1);
							},
							btn2: function (index1, layero1) {
								layer.open(
									{
										type: 1,
										area: ['300px', '150px'],
										fix: false, //不固定
										maxmin: true,
										shade: 0.5,
										title: "选择报告打印模板",
										content: $('#reportTemplateList'),
										btn: ["确定", "取消"],
										yes:function(index2, layero2) {
											var selectedPicNum = $("#reportTemplateSelect").find("option:selected").attr("picNum");
											var template = $("#reportTemplateSelect").find("option:selected").val();
											layer.close(index2);
											layer.close(rptView);
											reportView(1, selectedPicNum, template,samid);
										}
									});
								return false;
							},
							btn3: function (index1, layero1) {
								layer.close(index1);
							}
						});
						layer.full(rptView);
					} else {
						print(data.url);
					}
				});
			} else {
				layer.alert("该病例关联的病种还没有设置报告模板，请设置后再操作！");
			}
		}
	);
}

/**
 * 获取病种类别
 * @returns {string}
 */
function gettypes(){
	//动态生成select内容
	var str="";
	$.ajax({
		type:"get",
		async:false,
		url:"../hpinfo/userid",
		dataType: "json",
		success:function(data){
			if (data != null) {
				for(var i=0;i<data.length;i++){
					if(i!=data.length-1){
						str+=data[i].id+":"+data[i].name+";";
					}else{
						str+=data[i].id+":"+data[i].name;
					}
				}
			}
		}
	});
	return str;
}





