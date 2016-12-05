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
	var width1 = $("#div_2").width()-50;
	var req_bf_time = $('#req_bf_time').val();//统计年月
	var req_af_time = $('#req_af_time').val();//统计年月
	$("#rztj_new0").jqGrid({
		url: "../report/rztj",
		mtype: "GET",
		datatype: "json",
		postData:{"req_bf_time":req_bf_time,"req_af_time":req_af_time},
		colNames: ['已登记','已取材', '已包埋', '已切片','已初诊','已审核','已发送','会诊中','已打印'],
		colModel: [
			{name:'samsamplestatus0',index:'samsamplestatus0',align:'center'},//已登记
			{name:'samsamplestatus1',index:'samsamplestatus1',align:'center'},//已取材
			{name:'samsamplestatus2',index:'samsamplestatus2',align:'center'},//已包埋
			{name:'samsamplestatus3',index:'samsamplestatus3',align:'center'},//已切片
			{name:'samsamplestatus4',index:'samsamplestatus4',align:'center'},//已初诊
			{name:'samsamplestatus5',index:'samsamplestatus5',align:'center'},//已审核
			{name:'samsamplestatus6',index:'samsamplestatus6',align:'center'},//已发送
			{name:'samsamplestatus7',index:'samsamplestatus7',align:'center'},//会诊中
			{name:'samsamplestatus8',index:'samsamplestatus8',align:'center'}//已打印
		],
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		viewrecords: true,
		height:50,
		width: width1
	});
	$("#rztj_new1").jqGrid({
		url: "../report/rztjinfo",
		mtype: "GET",
		datatype: "json",
		postData:{"req_bf_time":req_bf_time,"req_af_time":req_af_time},
		colNames: ['病种类别','已登记','已取材', '已包埋', '已切片','已初诊','已审核','已发送','会诊中','已打印'],
		colModel: [
			{name:'sampathologyid',index:'sampathologyid',align:'center',formatter:'select',editoptions:{value:gettypes()}},//已登记
			{name:'samsamplestatus0',index:'samsamplestatus0',align:'center'},//已登记
			{name:'samsamplestatus1',index:'samsamplestatus1',align:'center'},//已取材
			{name:'samsamplestatus2',index:'samsamplestatus2',align:'center'},//已包埋
			{name:'samsamplestatus3',index:'samsamplestatus3',align:'center'},//已切片
			{name:'samsamplestatus4',index:'samsamplestatus4',align:'center'},//已初诊
			{name:'samsamplestatus5',index:'samsamplestatus5',align:'center'},//已审核
			{name:'samsamplestatus6',index:'samsamplestatus6',align:'center'},//已发送
			{name:'samsamplestatus7',index:'samsamplestatus7',align:'center'},//会诊中
			{name:'samsamplestatus8',index:'samsamplestatus8',align:'center'}//已打印
		],
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		viewrecords: true,
		height:130,
		width: width1
	});
	var width2 = width1/3;
	$("#bbytj_new0").jqGrid({
		url: "../report/bblytj",
		mtype: "GET",
		datatype: "json",
		postData:{"req_bf_time":req_bf_time,"req_af_time":req_af_time,"req_code":"1"},
		colNames: ['送检单位','数量'],
		colModel: [
			{name:'name',index:'name',align:'center'},//送检单位
			{name:'nums',index:'nums',align:'center'}//数量
		],
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		viewrecords: true,
		height:150,
		rownumbers: true, // 显示行号
		rownumWidth: 30, // the width of the row numbers columns
		width: width2
	});
	$("#bbytj_new1").jqGrid({
		url: "../report/bblytj",
		mtype: "GET",
		datatype: "json",
		postData:{"req_bf_time":req_bf_time,"req_af_time":req_af_time,"req_code":"2"},
		colNames: ['送检科室','数量'],
		colModel: [
			{name:'name',index:'name',align:'center'},//送检科室
			{name:'nums',index:'nums',align:'center'}//数量
		],
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		viewrecords: true,
		height:150,
		rownumbers: true, // 显示行号
		rownumWidth: 30, // the width of the row numbers columns
		width: width2
	});
	$("#bbytj_new2").jqGrid({
		url: "../report/bblytj",
		mtype: "GET",
		datatype: "json",
		postData:{"req_bf_time":req_bf_time,"req_af_time":req_af_time,"req_code":"3"},
		colNames: ['送检医生','数量'],
		colModel: [
			{name:'name',index:'name',align:'center'},//送检医生
			{name:'nums',index:'nums',align:'center'}//数量
		],
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		viewrecords: true,
		height:150,
		rownumbers: true, // 显示行号
		rownumWidth: 30, // the width of the row numbers columns
		width: width2
	});
	$("#sftj_new0").jqGrid({
		url: "../report/sftj",
		mtype: "GET",
		datatype: "json",
		postData:{"req_bf_time":req_bf_time,"req_af_time":req_af_time,"req_code":"1"},
		colNames: ['送检医生','金额'],
		colModel: [
			{name:'name',index:'name',align:'center'},//送检医生
			{name:'prices',index:'prices',align:'center'}//金额
		],
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		viewrecords: true,
		height:150,
		rownumbers: true, // 显示行号
		rownumWidth: 30, // the width of the row numbers columns
		width: width2
	});
	$("#sftj_new1").jqGrid({
		url: "../report/sftj",
		mtype: "GET",
		datatype: "json",
		postData:{"req_bf_time":req_bf_time,"req_af_time":req_af_time,"req_code":"2"},
		colNames: ['送检科室','金额'],
		colModel: [
			{name:'name',index:'name',align:'center'},//送检科室
			{name:'prices',index:'prices',align:'center'}//金额
		],
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		viewrecords: true,
		height:150,
		rownumbers: true, // 显示行号
		rownumWidth: 30, // the width of the row numbers columns
		width: width2
	});
	$("#sftj_new2").jqGrid({
		url: "../report/sftj",
		mtype: "GET",
		datatype: "json",
		postData:{"req_bf_time":req_bf_time,"req_af_time":req_af_time,"req_code":"3"},
		colNames: ['送检医生','金额'],
		colModel: [
			{name:'name',index:'name',align:'center'},//送检医生
			{name:'prices',index:'prices',align:'center'}//金额
		],
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		viewrecords: true,
		height:150,
		rownumbers: true, // 显示行号
		rownumWidth: 30, // the width of the row numbers columns
		width: width2
	});
	$("#sftj_new3").jqGrid({
		url: "../report/sftj",
		mtype: "GET",
		datatype: "json",
		postData:{"req_bf_time":req_bf_time,"req_af_time":req_af_time,"req_code":"4"},
		colNames: ['费用列表','金额'],
		colModel: [
			{name:'name',index:'name',align:'center'},//费用列表
			{name:'prices',index:'prices',align:'center'}//金额
		],
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		viewrecords: true,
		height:150,
		rownumbers: true, // 显示行号
		rownumWidth: 30, // the width of the row numbers columns
		width: width2
	});
	$("#sftj_new4").jqGrid({
		url: "../report/sftj",
		mtype: "GET",
		datatype: "json",
		postData:{"req_bf_time":req_bf_time,"req_af_time":req_af_time,"req_code":"5"},
		colNames: ['收费明细项目','金额'],
		colModel: [
			{name:'name',index:'name',align:'center'},//收费明细项目
			{name:'prices',index:'prices',align:'center'}//金额
		],
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		viewrecords: true,
		height:150,
		rownumbers: true, // 显示行号
		rownumWidth: 30, // the width of the row numbers columns
		width: width2
	});
	$("#sftj_new5").jqGrid({
		url: "../report/sftj",
		mtype: "GET",
		datatype: "json",
		postData:{"req_bf_time":req_bf_time,"req_af_time":req_af_time,"req_code":"6"},
		colNames: ['报告医生','金额'],
		colModel: [
			{name:'name',index:'name',align:'center'},//报告医生
			{name:'prices',index:'prices',align:'center'}//金额
		],
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		viewrecords: true,
		height:150,
		rownumbers: true, // 显示行号
		rownumWidth: 30, // the width of the row numbers columns
		width: width2
	});
});
/**
 * 查询数据
 */
function searchList() {
	var req_bf_time = $('#req_bf_time').val();//送检FROM
	var req_af_time = $('#req_af_time').val();//送检TO
	jQuery("#rztj_new0").jqGrid("clearGridData");
	jQuery("#rztj_new0").jqGrid('setGridParam',{
		url: "../report/rztj",
		//发送数据
		postData : {"req_bf_time":req_bf_time,"req_af_time":req_af_time}
	}).trigger('reloadGrid');//重新载入
	jQuery("#rztj_new1").jqGrid("clearGridData");
	jQuery("#rztj_new1").jqGrid('setGridParam',{
		url: "../report/rztjinfo",
		//发送数据
		postData : {"req_bf_time":req_bf_time,"req_af_time":req_af_time}
	}).trigger('reloadGrid');//重新载入
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




