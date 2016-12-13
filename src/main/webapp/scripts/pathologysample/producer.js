var hrefval = "";
var nowrow = "";
var GRID_SELECTED_ROW_SAMPLEID = "";
var GRID_SELECTED_ROW_SAMPCUSTOMERID = "";
var GRID_SELECTED_ROW_SAMPLECODE = "";
var PIC_TAKING_FROM = 1;
var LASTEDITROW1 = "";
var LASTEDITCELL1 = "";
var SAVENUM = "";
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
function changeimgclick(num) {//1切片 取消包埋
	if(num == 1){
		SAVENUM = 1;
		if (typeof document.addEventListener == "undefined") {
			document.getElementById("saveButton").detachEvent("onclick",saveInfo);
			document.getElementById("saveButton").attachEvent("onclick",saveInfo);
		} else {
			document.getElementById("saveButton").removeEventListener("click",saveInfo,false);
			document.getElementById("saveButton").addEventListener("click",saveInfo,false);
		}
		$("#saveButton").css("cursor","pointer");
	}else{
		SAVENUM = 0;
		if (typeof document.addEventListener == "undefined") {
			document.getElementById("saveButton").detachEvent("onclick",saveInfo);
		} else {
			document.getElementById("saveButton").removeEventListener("click",saveInfo,false);
		}
		$("#saveButton").css("cursor","default");
	}
}
/**
 * 查询制片状态
 */
function searchSts(states){
	var req_sts = states;
	if(req_sts == "2"){
		changeimgclick(1);
		// $("#saveButton").attr({"disabled":true});
		// $("#resetbutton").removeAttr("disabled");//将按钮可用
	}else{
		// $("#resetbutton").attr({"disabled":true});
		// $("#saveButton").removeAttr("disabled");//将按钮可用
		changeimgclick(2);
	}
}
/**
 * 制片
 */
function saveInfo(num) {
	num = SAVENUM;
	$("#new1").jqGrid("saveCell",LASTEDITROW1,LASTEDITCELL1);
	var post = true;
	var rowdatas;
	var selectedIds;
	var arr = new Array();
	var sampleid = "";
	if(num == 1){//批量制片
		selectedIds = $("#new").jqGrid("getGridParam","selarrrow");
		if(selectedIds.length > 0){
			$(selectedIds).each(function () {
					var rowData1 = $("#new").jqGrid('getRowData',this.toString());
					arr.push(rowData1);
				}
			);
		}else{
			layer.msg('未选择数据!', {icon: 2,time: 1000});
			post = false;
			return;
		}
	}else if(num == 0){//单个标本单独制片
		rowdatas = $('#new1').jqGrid('getRowData');
		sampleid = $("#sampleid").val();
	}
	if(post) {
		layer.open({
			type: 1,
			area: ['800px','500px'],
			fix: false, //不固定
			maxmin: true,
			multiselect: true,
			rownumbers : true,
			shade:0.5,
			title: "选择诊断医师",
			content: $('#userGrid1'),
			btn:["保存","取消"],
			yes: function(index, layero){
				var id = $('#sectionList4').jqGrid('getGridParam','selrow');
				var rowData = $("#sectionList4").jqGrid('getRowData',id);
				if(id > 0){
					$.post("../pathologysample/producer/editSample", {
							userid:rowData.id,
							username:rowData.name,
							states:1,
							savenum:num,
							sampleid:sampleid,
							samples:JSON.stringify(arr),
							slides:JSON.stringify(rowdatas)
						},
						function(data) {
							if(data.success) {
								layer.msg(data.message, {icon: 1, time: 1000});
								searchList();
							} else {
								layer.msg(data.message, {icon:2, time: 1000});
							}
						});
				}else{
					layer.msg('请先选择用户', {icon: 2,time: 1000});
					return false;
				}

			}
		});
	}
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
	$('#tabss a').click(function (e) {
		hrefval = $(this).attr("href");
		searchSts(hrefval);
		$("#req_sts").val(hrefval);
		searchList();
		e.preventDefault();
	});
	document.onkeydown = function(e){
		if(e.keyCode==13) {
			var ids=$("#new1").jqGrid("getGridParam","selarrrow");
			if(ids != null && ids != ""){
				addRow();
			}
		}
	}
	changeimgclick(1);
var clientHeight= $(window).innerHeight();
	var height = $("#formDialog").height() - $('#search_div_1').height()+70;
	var width = $('#search_div_1').width()-5;
	var width1 = $("#div_main").width();

	createNew1("",width1);

	var logyid = $("#logyid").val();
	var send_hosptail = "";
	var req_code = $('#req_code').val();
	var patient_name = $('#patient_name').val();//病人姓名
	//var send_hosptail = $('#send_hosptail').val();
	var req_bf_time = $('#req_bf_time').val();
	var req_af_time = $('#req_af_time').val();
	var send_dept = $('#send_dept').val();//病理号
	var send_doctor = "";//补医嘱
	if($("#send_doctor").is(":checked")){
		send_doctor = $('#send_doctor').val();//补医嘱
	}
	var req_sts = $('#req_sts').val();//取材状态
	//var req_sts = $("input[name='req_sts']:checked").val();//包埋状态
	$("#new").jqGrid({
		url: "../pathologysample/producer/ajax/sample",
		mtype: "GET",
		datatype: "json",
		postData:{"req_code":req_code,"patient_name":patient_name,"send_hosptail":send_hosptail,"req_bf_time":req_bf_time,
			"req_af_time":req_af_time,"send_dept":send_dept,"send_doctor":send_doctor,"req_sts":req_sts,"logyid":logyid},
		colNames: ['ID','制片状态', '病理号', '送检医生','送检医院','病人名','客户ID'],
		colModel: [
			{name:'sampleid',hidden:true},
			{ name: 'samsamplestatus', hidden: true},
			{ name: 'sampathologycode', index: 'sampathologycode'},
			{ name: 'samsenddoctorname', index: 'samsenddoctorname'},
			{ name: 'samsendhospital', index: 'samsendhospital'},
			{ name: 'sampatientname', index: 'sampatientname'},
			{ name: 'samcustomerid', index: 'samcustomerid',hidden:true}
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
		height:400,
		width: width,
		shrinkToFit:false,
		autoScroll: true,
		rowNum: 10,
		rowList:[10,20,30],
		rownumbers: true, // 显示行号
		rownumWidth: 30, // the width of the row numbers columns
		pager: "#pager"
	});
	// createNew2();
	$("#pager_left").remove();
});
/**
 * 初始化制片列表
 * @param reqid
 */
function createNew1(reqid,width1){
	$("#new1").jqGrid({
		url:"../pathologysample/producer/ajax/getItem",
		datatype: "json",
		mtype:"GET",
		height:130,
		width: width1,
		// shrinkToFit:false,
		// autoScroll: true,
		postData:{"reqId":reqid},
		colNames: ['样本ID','蜡块ID','玻片条码','玻片类型','玻片序号','印刷状态', '特检项目',
			'玻片id','客户代码','客户代码','病理编号','玻片来源','玻片使用状态','玻片号','蜡块序号','取材部位','是否内部医嘱'],
		colModel: [
			{name:'slisampleid',hidden:true},//样本ID
			{name:'sliparaffinid',hidden:true},//蜡块ID
			{name:'slislidebarcode',index:'slislidebarcode',align:'center'},//玻片条码
			{name:'slislidetype',index:'slislidetype',formatter: "select", editoptions:{value:"0:常规;1:白片"},align:'center'},//玻片类型
			{name:'slislideno',index:'slislideno',align:'center'},//玻片序号
			{ name: 'sliifprint', index: 'sliifprint',formatter: "select", editoptions:{value:"0:未打印;1:已打印"},width:100,align:'center'},//印刷状态
			{ name: 'slitestitemname', index: 'slitestitemname',width:100,align:'center'},//特检项目
			{name:'slideid',hidden:true},//玻片id
			{name:'slicustomerid',hidden:true},//客户代码
			{name:'slicustomercode',hidden:true},//客户代码
			{name:'slipathologycode',hidden:true},//病理编号
			{name:'slislidesource',hidden:true},//玻片来源
			{name:'sliuseflag',hidden:true},//玻片使用状态
			{name:'slislidecode',hidden:true},//玻片号
			{name:'sliparaffinno',hidden:true},//蜡块序号
			{name:'slisamplingparts',hidden:true},//取材部位
			{name:'slifirstn',hidden:true}//是否内部医嘱
		],
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		beforeEditCell:function(rowid,cellname,v,iRow,iCol){
			//canChange(rowid,1);
			LASTEDITROW1 = iRow;
			LASTEDITCELL1 = iCol;
			var rec = jQuery("#new1").jqGrid('getRowData', rowid);
			if (rec.piestate > "0" || rec.piefirstn != "") {
				setTimeout(function () {
					jQuery("#new1").jqGrid('restoreCell', iRow, iCol);
					//===>或者设置为只读
					//$('#' + rowid + '_amount').attr('readonly', true);
				}, 1);
			}
		},
		viewrecords: true,
		multiselect: true,
		cellsubmit: "clientArray",
		//autowidth: true,
		//shrinkToFit:false,
		//autoScroll: true,
		cellEdit:true,
		rownumbers : true
	});
}
/**
 * 查询数据
 */
function searchList() {
	var req_code = $('#req_code').val();
	var patient_name = $('#patient_name').val();
	var send_hosptail = $('#send_hosptail').val();
	var req_bf_time = $('#req_bf_time').val();
	var req_af_time = $('#req_af_time').val();
	var send_dept = $('#send_dept').val();
	var logyid = $("#logyid").val();
	// var send_doctor = $('#send_doctor').val();
	var send_doctor = "";//补医嘱
	if($("#send_doctor").is(":checked")){
		send_doctor = $('#send_doctor').val();//补医嘱
	}
	var req_sts = $('#req_sts').val();
	// var req_sts = $("input[name='req_sts']:checked").val();//包埋状态
	jQuery("#new").jqGrid("clearGridData");
	jQuery("#new").jqGrid('setGridParam',{
		url: "../pathologysample/producer/ajax/sample",
		//发送数据
		postData : {"req_code":req_code,"patient_name":patient_name,"send_hosptail":send_hosptail,"req_bf_time":req_bf_time,
			"req_af_time":req_af_time,"send_dept":send_dept,"send_doctor":send_doctor,"req_sts":req_sts,"logyid":logyid},
		page : 1
	}).trigger('reloadGrid');//重新载入
}
function fillInfo(id){
	setcolor(id);
	nowrow = id;
	clearData();
	//var id = $("#new").jqGrid('getGridParam', 'selrow');
	var rowData = $("#new").jqGrid('getRowData',id);
	GRID_SELECTED_ROW_SAMPLEID = rowData.sampleid;
	GRID_SELECTED_ROW_SAMPCUSTOMERID = rowData.samcustomerid;
	GRID_SELECTED_ROW_SAMPLECODE = rowData.sampathologycode;
	if (id == null || id == 0) {
		layer.msg('请先选择数据', {icon: 2, time: 1000});
		return false;
	}
	getSampleData(rowData.sampleid);
	var req_sts = $("#req_sts").val();
	if(req_sts == "2"){
		addRow();
	}else{
		jQuery("#new1").jqGrid('setGridParam',{
			url: "../pathologysample/producer/ajax/getItem",
			//发送数据
			postData : {"reqId":rowData.sampleid}
		}).trigger('reloadGrid');//重新载入
	}

}
function getSampleData(id) {
	$.get("../pathologysample/pieces/get",{id:id},function(data) {
		if(data != "") {
			$("#samcustomerid").val(data.samcustomerid);
			$("#sampathologyid").val(data.sampathologyid);
			$("#sampathologycode").val(data.sampathologycode);
			$("#sampleid").val(data.sampleid);
			$("#samsamplestatus").val(data.samsamplestatus);
			$("#samsenddoctorname").val(data.samsenddoctorname);
			$("#sampatientname").val(data.sampatientname);
			$("#samdeptname").val(data.samdeptname);
			$("#sampatientnumber").val(data.sampatientnumber);
			$("#samsamplename").val(data.samsamplename);
			$("#sampatientbed").val(data.sampatientbed);
			$("#sampatientsex").val(data.sampatientsex);
			$("#sampatientdignoses").val(data.sampatientdignoses);
			$("#samthirdv").val(data.samthirdv);
		} else {
			layer.msg("该申请单不存在！", {icon: 0, time: 1000});
		}
	});
}
function addRow(){
	//var rowData1 = $("#new").jqGrid('getRowData',nowrow);
	var jjinfo = "";
	var ids = $("#new1").jqGrid('getDataIDs');
	var maxId = 1;
	if(ids == null || ids == ""){
	}else{
		var rowData = $("#new1").jqGrid('getRowData',Math.max.apply(Math,ids));
		jjinfo = rowData.pieparts;
		maxId = Math.max.apply(Math,ids)+1;
	}
	var dataRow = {
		slisampleid:GRID_SELECTED_ROW_SAMPLEID,//样本ID
		sliparaffinid:"",//蜡块ID
		slislidebarcode:GRID_SELECTED_ROW_SAMPLECODE+"-"+maxId,//材块编号
		slislidetype:0,//玻片类型
		slislideno:maxId,//玻片序号
		sliifprint:0,//印刷状态
		slitestitemname:"",//特检项目
		slideid:"",//玻片id
		slicustomerid:GRID_SELECTED_ROW_SAMPCUSTOMERID,//客户代码
		slipathologycode:GRID_SELECTED_ROW_SAMPLECODE,//病理编号
		slislidesource:3,//玻片来源
		sliuseflag:0,//玻片使用状态
		slislidecode:"",//玻片号
		sliparaffinno:0,//蜡块序号
		slisamplingparts:"",//取材部位
		slifirstn:""//是否内部医嘱
	};
	$("#new1").jqGrid("addRowData", maxId, dataRow, "last");
}
function delRow(){
	var selectedIds = $("#new1").jqGrid("getGridParam","selarrrow");
	var ids = $("#new1").jqGrid('getDataIDs');
	var maxId = Math.max.apply(Math,ids);
	if(selectedIds == null || selectedIds == ""){
		if(!maxId){
			layer.msg("无数据可删除!", {icon: 2, time: 1000});
			return;
		}else if(maxId == 1){
			layer.msg("至少保留一条制片信息!", {icon: 2, time: 1000});
			return;
		}else{
			var rowData = $("#new1").jqGrid('getRowData',maxId);
			if(rowData.pieceid == null || rowData.pieceid == ""){
				$("#new1").jqGrid("delRowData", maxId);
			}else if(rowData.sliuseflag == 1){
				layer.msg("该片已被使用无法删除!", {icon: 2, time: 1000});
				return;
			}
		}
	}else{
		var maxSelectId = Math.max.apply(Math,selectedIds);
		if(maxId > maxSelectId){
			layer.msg("请从最后一条数据开始删除!", {icon: 2, time: 1000});
			return;
		}else{
			$(selectedIds).each(function () {
				var delrow = this.toString();
				var rowData = $("#new1").jqGrid('getRowData',delrow);
				if(delrow == "1"){
					layer.msg("至少保留一条制片信息!", {icon: 2, time: 1000});
					return;
				}else if(rowData.pieceid == null || rowData.pieceid == ""){
					$("#new1").jqGrid("delRowData", delrow);
				}else if(rowData.sliuseflag == 1){
					layer.msg("该片已被使用无法删除!", {icon: 2, time: 1000});
					return;
				}
				}
			);
		}
	}
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
		saveDatas.push(rowData);
		// startPrint(rowData);
	});
	$.post("../pathologysample/slide/printcode",{samples:JSON.stringify(saveDatas)},function(data){
		data = jQuery.parseJSON(data);
		// console.log(data);
		startPrint(data);
		// for(i=0;i<data.labOrders.length;i++){
		// 	startPrint(data.labOrders[i]);
		// }
	});
}
var LODOP; //声明为全局变量

function Preview() {//打印预览
	LODOP = getLodop();
	CreateDataBill(data)
	LODOP.PREVIEW();
}
function Setup() {//打印维护
	LODOP = getLodop();
	LODOP.PRINT_SETUP();
}
function CreateDataBill(datas) {
	LODOP = getLodop();
	LODOP.PRINT_INIT("");
	LODOP.SET_PRINT_PAGESIZE(3,"97mm","17mm","A4");
	for(i=0;i<datas.labOrders.length;i++){
		var data = datas.labOrders[i];
		var topheight1 = Math.floor(i/3)*22+ 3;
		var topheight2 = Math.floor(i/3)*22+ 8;
		var leftwidth1 = i%3*32+3;
		LODOP.ADD_PRINT_TEXT(topheight1+"mm",leftwidth1+"mm","29mm","5mm","树兰(杭州)医院");
		LODOP.SET_PRINT_STYLEA(0,"FontSize",10);
		LODOP.SET_PRINT_STYLEA(0,"Bold",1);
		// LODOP.ADD_PRINT_BARCODEA("patientCode","21.98mm","27.01mm","46.57mm",40,"128B",data.sampathologycode);
		// LODOP.SET_PRINT_STYLEA(0,"Horient",2);
		LODOP.ADD_PRINT_TEXTA("nameText",topheight2+"mm",leftwidth1+"mm","29mm","3mm",data.barcode);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
	}

}
function startPrint(data) {
	CreateDataBill(data);
	//开始打印
	// LODOP.PRINT();
	LODOP.PREVIEW();
}

$(function () {
	$("#sectionList4").jqGrid({
		caption: "选择诊断医师",
		url: "../pimsuser/userlist",
		mtype: "GET",
		datatype: "json",
		postData:{"states":0},
		width:800,
		height:550,
		colNames: ['id','姓名', '登录账号'],
		colModel: [
			{ name: 'id', index: 'id', width: 30, hidden: true },
			{ name: 'name', index: 'name', width: 30},
			{ name: 'username', index: 'phone', width: 60}
		],
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		ondblClickRow: function (id) {
		},
		// multiselect: true,
		viewrecords: true,
		shrinkToFit: true,
		altRows:true,
		height: 'auto',
		rowNum: 10,
		rowList:[10,20,30],
		rownumbers: true, // 显示行号
		rownumWidth: 35, // the width of the row numbers columns
		pager: "#pager4",
		onSelectRow: function(id){

		}
	});
});


