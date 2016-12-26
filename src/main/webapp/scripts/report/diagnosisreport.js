var hrefval = "";
var nowrow = "";
var GRID_SELECTED_ROW_SAMPLEID = "";
var GRID_SELECTED_ROW_SAMPCUSTOMERID = "";
var PIC_TAKING_FROM = 1;
var LASTEDITROW1 = "";
var LASTEDITCELL1 = "";
function showandhiden(obj) {
	if($(obj).children("i").attr("class").indexOf("fa-chevron-up") >=0){
		$(obj).children("i").removeClass("fa-chevron-up");
		$(obj).children("i").addClass("fa-chevron-down");
	}else{
		$(obj).children("i").removeClass("fa-chevron-down");
		$(obj).children("i").addClass("fa-chevron-up");
		$(obj).parent().parent().parent().removeClass("collapsed");
	}
}
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
	$("#maintab").css('display','block');
	$('#tabs a:first').tab('show');//初始化显示哪个tab
	$('#tabs a').click(function (e) {
		$('#tabs').find('li').each(function(){
			$($(this).find('a').attr("href")).css('display','none');
		});
		e.preventDefault();//阻止a链接的跳转行为
		$($(this).attr("href")).css('display','block');
		$(this).tab('show');//显示当前选中的链接及关联的content
	});
	var width1 = $("#formDialog").width();
	createNew1("",width1);
	var req_bf_time = $('#req_bf_time').val();//送检FROM
	var req_af_time = $('#req_af_time').val();//送检TO
	var logyid = $('#logyid').val();//病种类别
	var req_code = $('#req_code').val();//病理号码
	var patient_name = $('#patient_name').val();//病人姓名
	var sampatientnumber = $('#sampatientnumber').val();//住院号
	var sampatientbed = $('#sampatientbed').val();//床号
	var sampatientsex = $('#sampatientsex').val();//性别
	var send_doctor = $('#send_doctor').val();//送检医生
	var send_dept = $('#send_dept').val();//送检科室
	var send_hosptail = $('#send_hosptail').val();//送检医院
	var piedoctorname = $('#piedoctorname').val();//取材医生
	var parsectioneddoctor = $('#parsectioneddoctor').val();//切片医生
	var saminitiallyusername = $('#saminitiallyusername').val();//诊断医生
	var myzh = $('#myzh').val();//免疫组化
	var tsrs = $('#tsrs').val();//特殊染色
	var fzbl = $('#fzbl').val();//分子病理
	var blzd = $('#blzd').val();//病理诊断
	var qcbw = $('#qcbw').val();//取材部位
	var height2=$(window).height()-150-$("#sampleForm").height()-$("#head").height()-$("#div_1").height();
	$("#new").jqGrid({
		url: "../report/diagnosisreport/list",
		mtype: "GET",
		datatype: "json",
		postData:{"req_bf_time":req_bf_time,
					"req_af_time":req_af_time,
					"logyid":logyid,
					"req_code":req_code,
					"patient_name":patient_name,
					"sampatientnumber":sampatientnumber,
					"sampatientbed":sampatientbed,
					"sampatientsex":sampatientsex,
					"send_doctor":send_doctor,
					"send_dept":send_dept,
					"send_hosptail":send_hosptail,
					"piedoctorname":piedoctorname,
					"parsectioneddoctor":parsectioneddoctor,
					"saminitiallyusername":saminitiallyusername,
					"myzh":myzh,
					"tsrs":tsrs,
					"fzbl":fzbl,
					"blzd":blzd,
					"qcbw":qcbw},
		colNames: ['标本ID','病种类别', '病理号', '病人姓名','年龄','住院号','床号','性别','病理状态','送检日期','诊断日期','送检医生','送检科室','送检医院','取材医生',
			'切片医生','诊断医生','病理诊断','免疫组化','特殊染色','分子病理'],
		colModel: [
			{name:'sampleid',hidden:true},//标本ID
			{ name: 'sampathologyid', index: 'sampathologyid',formatter: "select", editoptions:{value:gettypes()},width:'100px'},//病种类别
			{ name: 'sampathologycode', index: 'sampathologycode',width:'100px'},//病理号
			{ name: 'sampatientname', index: 'sampatientname',width:'100px'},//病人姓名
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
			},width:'100px'},//年龄
			{ name: 'sampatientnumber', index: 'sampatientnumber',width:'100px'},//住院号
			{ name: 'sampatientbed', index: 'sampatientbed',width:'100px'},//床号
			{ name: 'sampatientsex', index: 'sampatientsex',formatter: "select", editoptions:{value:"1:男;2:女;3:未知"},width:'100px'},//性别
			{ name: 'samsamplestatus', index: 'samsamplestatus',formatter: "select", editoptions:{value:"0:未取材;1:已取材;2:已包埋;3:已切片;4:已初诊;" +
			"5:已审核;6:已发送;7:会诊中:8:报告已打印"},width:'100px'},//病理状态
			{ name: 'samsendtime', index: 'samsendtime',formatter:function(cellvalue, options, row)
			{if(cellvalue == "" || cellvalue == null){return ""}return CurentTime(new Date(cellvalue))},width:'100px'},//送检日期
			{ name: 'saminitiallytime', index: 'saminitiallytime',formatter:function(cellvalue, options, row)
			{if(cellvalue == "" || cellvalue == null){return ""}return CurentTime(new Date(cellvalue))},width:'100px'},//诊断日期
			{ name: 'samsenddoctorname', index: 'samsenddoctorname',width:'100px'},//送检医生
			{ name: 'samdeptname', index: 'samdeptname',width:'100px'},//送检科室
			{ name: 'samsendhospital', index: 'samsendhospital',width:'100px'},//送检医院
			{ name: 'piedoctorname', index: 'piedoctorname',width:'100px'},//取材医生
			{ name: 'parsectioneddoctor', index: 'parsectioneddoctor',width:'100px'},//切片医生
			{ name: 'saminitiallyusername', index: 'saminitiallyusername',width:'100px'},//诊断医生
			{ name: 'restestresult', index: 'restestresult',width:'100px'},//病理诊断
			{ name: 'samsamplestatus', index: 'samsamplestatus',formatter:function(cellvalue, options, row){if(cellvalue > 0){return "有"}return "无"},width:'100px'},//免疫组化
			{ name: 'samsamplestatus', index: 'samsamplestatus',formatter:function(cellvalue, options, row){if(cellvalue > 0){return "有"}return "无"},width:'100px'},//特殊染色
			{ name: 'samsamplestatus', index: 'samsamplestatus',formatter:function(cellvalue, options, row){if(cellvalue > 0){return "有"}return "无"},width:'100px'}//分子病理
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
		height:height2,
		width: width1,
		shrinkToFit:false,
		autoScroll: true,
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
	var logyid = $('#logyid').val();//病种类别
	var req_code = $('#req_code').val();//病理号码
	var patient_name = $('#patient_name').val();//病人姓名
	var sampatientnumber = $('#sampatientnumber').val();//住院号
	var sampatientbed = $('#sampatientbed').val();//床号
	var sampatientsex = $('#sampatientsex').val();//性别
	var send_doctor = $('#send_doctor').val();//送检医生
	var send_dept = $('#send_dept').val();//送检科室
	var send_hosptail = $('#send_hosptail').val();//送检医院
	var piedoctorname = $('#piedoctorname').val();//取材医生
	var parsectioneddoctor = $('#parsectioneddoctor').val();//切片医生
	var saminitiallyusername = $('#saminitiallyusername').val();//诊断医生
	var myzh = $('#myzh').val();//免疫组化
	var tsrs = $('#tsrs').val();//特殊染色
	var fzbl = $('#fzbl').val();//分子病理
	var blzd = $('#blzd').val();//病理诊断
	var qcbw = $('#qcbw').val();//取材部位
	jQuery("#new").jqGrid("clearGridData");
	jQuery("#new").jqGrid('setGridParam',{
		url: "../report/diagnosisreport/list",
		//发送数据
		postData : {"req_bf_time":req_bf_time,
			"req_af_time":req_af_time,
			"logyid":logyid,
			"req_code":req_code,
			"patient_name":patient_name,
			"sampatientnumber":sampatientnumber,
			"sampatientbed":sampatientbed,
			"sampatientsex":sampatientsex,
			"send_doctor":send_doctor,
			"send_dept":send_dept,
			"send_hosptail":send_hosptail,
			"piedoctorname":piedoctorname,
			"parsectioneddoctor":parsectioneddoctor,
			"saminitiallyusername":saminitiallyusername,
			"myzh":myzh,
			"tsrs":tsrs,
			"fzbl":fzbl,
			"blzd":blzd,
			"qcbw":qcbw},
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
	"送检时间:"+rowData.samsendtime+";送检医生:"+rowData.samsenddoctorname+";诊断时间:"+rowData.saminitiallytime+";诊断医生:"+rowData.saminitiallyusername);
	$("#blzd1").val(rowData.restestresult);
	$.get("../pathologysample/pieces/get",{id:rowData.sampleid},function(data) {

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

function reportExcel() {
	var req_bf_time = $('#req_bf_time').val();//送检FROM
	var req_af_time = $('#req_af_time').val();//送检TO
	var logyid = $('#logyid').val();//病种类别
	var req_code = $('#req_code').val();//病理号码
	var patient_name = $('#patient_name').val();//病人姓名
	var sampatientnumber = $('#sampatientnumber').val();//住院号
	var sampatientbed = $('#sampatientbed').val();//床号
	var sampatientsex = $('#sampatientsex').val();//性别
	var send_doctor = $('#send_doctor').val();//送检医生
	var send_dept = $('#send_dept').val();//送检科室
	var send_hosptail = $('#send_hosptail').val();//送检医院
	var piedoctorname = $('#piedoctorname').val();//取材医生
	var parsectioneddoctor = $('#parsectioneddoctor').val();//切片医生
	var saminitiallyusername = $('#saminitiallyusername').val();//诊断医生
	var myzh = $('#myzh').val();//免疫组化
	var tsrs = $('#tsrs').val();//特殊染色
	var fzbl = $('#fzbl').val();//分子病理
	var blzd = $('#blzd').val();//病理诊断
	var qcbw = $('#qcbw').val();//取材部位
	// $.get('../report/daochu',{"req_bf_time":req_bf_time,
	// 	"req_af_time":req_af_time,
	// 	"logyid":logyid,
	// 	"req_code":req_code,
	// 	"patient_name":patient_name,
	// 	"sampatientnumber":sampatientnumber,
	// 	"sampatientbed":sampatientbed,
	// 	"sampatientsex":sampatientsex,
	// 	"send_doctor":send_doctor,
	// 	"send_dept":send_dept,
	// 	"send_hosptail":send_hosptail,
	// 	"piedoctorname":piedoctorname,
	// 	"parsectioneddoctor":parsectioneddoctor,
	// 	"saminitiallyusername":saminitiallyusername,
	// 	"myzh":myzh,
	// 	"tsrs":tsrs,
	// 	"fzbl":fzbl,
	// 	"blzd":blzd,
	// 	"qcbw":qcbw});
	window.location.href = "../report/daochu?req_bf_time="+req_bf_time+"&req_af_time="+req_af_time+"&logyid="+logyid
		+"&req_code="+req_code+"&patient_name="+patient_name+"&sampatientnumber="+sampatientnumber+"&sampatientbed="+sampatientbed
		+"&sampatientsex="+sampatientsex+"&send_doctor="+send_doctor+"&send_dept="+send_dept+"&send_hosptail="+send_hosptail
		+"&piedoctorname="+piedoctorname+"&parsectioneddoctor="+parsectioneddoctor+"&saminitiallyusername="+saminitiallyusername
		+"&myzh="+myzh+"&tsrs="+tsrs+"&fzbl="+fzbl+"&blzd="+blzd+"&qcbw="+qcbw;
}


