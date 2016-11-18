var nowrow = "";//当前显示数据所在的行
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
function changeimgclick(num) {//1包埋 取消包埋
	if(num == 1){
		nowrow = 2;
		if (typeof document.addEventListener == "undefined") {
			document.getElementById("saveButton").detachEvent("onclick",saveInfo);
			document.getElementById("saveButton").attachEvent("onclick",saveInfo);
			document.getElementById("resetbutton").detachEvent("onclick",saveInfo);
		} else {
			document.getElementById("saveButton").removeEventListener("click",saveInfo,false);
			document.getElementById("saveButton").addEventListener("click",saveInfo,false);
			document.getElementById("resetbutton").removeEventListener("click",saveInfo,false);
		}
		$("#saveButton").css("cursor","pointer");
		$("#resetbutton").css("cursor","default");
	}else{
		nowrow = 1;
		if (typeof document.addEventListener == "undefined") {
			document.getElementById("saveButton").detachEvent("onclick",saveInfo);
			document.getElementById("resetbutton").detachEvent("onclick",saveInfo);
			document.getElementById("resetbutton").attachEvent("onclick",saveInfo);
		} else {
			document.getElementById("saveButton").removeEventListener("click",saveInfo,false);
			document.getElementById("resetbutton").removeEventListener("click",saveInfo,false);
			document.getElementById("resetbutton").addEventListener("click",saveInfo,false);
		}
		$("#saveButton").css("cursor","default");
		$("#resetbutton").css("cursor","pointer");
	}
}
/**
 * 包埋
 */
function saveInfo() {
	var num = nowrow;
	var rowdatas = $('#new1').jqGrid('getRowData');
	var selectedIds = $("#new").jqGrid("getGridParam","selarrrow");
	var arr = new Array();
	if(selectedIds.length > 0){
		$(selectedIds).each(function () {
				var rowData1 = $("#new").jqGrid('getRowData',this.toString());
				arr.push(rowData1);
			}
		);
	}else{
		alert("未选择任何数据!");
		return;
	}
	var post = true;
	if(post) {
		$.post("../pathologysample/paraffin/editSample", {
				states:"1",
				savenum:num,
				samples:JSON.stringify(arr),
				slides:JSON.stringify(rowdatas)
			},
			function(data) {
				if(data.success) {
					layer.msg(data.message, {icon: 1, time: 1000});
					location.reload();
				} else {
					layer.msg(data.message, {icon:2, time: 1000});
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
 * 查询包埋状态
 */
function searchSts(states){
	var req_sts = states;
	//var req_sts = $("#req_sts").val();
	if(req_sts == "1"){
		changeimgclick(1);
		// $("#resetbutton").attr({"disabled":true});
		// $("#saveButton").removeAttr("disabled");//将按钮可用
	}else{
		changeimgclick(2);
		// $("#saveButton").attr({"disabled":true});
		// $("#resetbutton").removeAttr("disabled");//将按钮可用
	}
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
		var hrefval = $(this).attr("href");
		searchSts(hrefval);
		$("#req_sts").val(hrefval);
		searchList();
		e.preventDefault();
	});
	//$("#resetbutton").attr({"disabled":true});
	changeimgclick(1);
	var clientHeight= $(window).innerHeight();
	var height = $("#formDialog").height() - $('#search_div_1').height()+120;
	var width = $('#search_div_1').width()-5;
	var width1 = $("#sampleForm").width()-5;

	createNew1("",width1);
	//var logyid = $("#logyid").val();
	var logyid ="";
	var send_hosptail = "";
	var req_code = $('#req_code').val();
	var patient_name = $('#patient_name').val();//病人姓名
	//var send_hosptail = $('#send_hosptail').val();
	var req_bf_time = $('#req_bf_time').val();
	var req_af_time = $('#req_af_time').val();
	var send_dept = $('#send_dept').val();//病理号
	//var send_doctor = $('#send_doctor').val();//内部医嘱
	var send_doctor = "";//内部医嘱
	if($("#send_doctor").is(':checked')){
		send_doctor = "1";
	}
	// var req_sts = $("input[name='req_sts']:checked").val();//包埋状态
	var req_sts = $('#req_sts').val();//包埋状态

	$("#new").jqGrid({
		url: "../pathologysample/paraffin/ajax/sample",
		mtype: "GET",
		datatype: "json",
		postData:{"req_code":req_code,"patient_name":patient_name,"send_hosptail":send_hosptail,"req_bf_time":req_bf_time,
			"req_af_time":req_af_time,"send_dept":send_dept,"send_doctor":send_doctor,"req_sts":req_sts,"logyid":logyid},
		colNames: ['材块ID','标本id','病理状态', '病理号','材块编号','特殊要求','白片数','病理状态','补取医嘱','材块数','白片数', '取材部位','特殊要求', '取材时间','取材医生ID','取材医生','序号','包埋状态'],
		colModel: [
			{name:'pieceid',hidden:true},//材块ID
			{name:'sampleid',hidden:true},//标本id
			{name:'samsamplestatus',hidden:true},//病理状态
			{ name: 'piepathologycode', index: 'piepathologycode'},//病理号
			{ name: 'piecode', index: 'piecode'},//材块编号
			{ name: 'piespecial', index: 'piespecial'},//特殊要求
			{ name: 'pienullslidenum', index: 'pienullslidenum'},//白片数
			{ name: 'pieisembed', index: 'pieisembed',formatter: "select", editoptions:{value:"0:未包埋;1:已包埋"},width:70},//病理状态
			{ name: 'piefirstv', index: 'piefirstv'},//补取医嘱
			{name:'piecounts',hidden:true},//材块数
			{name:'pienullslidenum',hidden:true},//白片数
			{name:'pieparts',hidden:true},//取材部位
			{name:'piespecial',hidden:true},//特殊要求
			{name:'piesamplingtime',hidden:true,formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))}},//取材时间
			{name:'piedoctorid',hidden:true},//取材医生ID
			{name:'piedoctorname',hidden:true},//取材医生
			{name:'piesamplingno',hidden:true},//序号
			{name:'pieisembed',hidden:true},//包埋状态
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
		onSelectRow:function(id,status){

		},
		onCellSelect:function(id){
			fillInfo(id);
		},
		multiselect: true,
		viewrecords: true,
		height:height,
		width: width,
		shrinkToFit:false,
		autoScroll: true,
		rowNum: 10,
		rowList:[10,20,30],
		rownumbers: true, // 显示行号
		rownumWidth: 30, // the width of the row numbers columns
		pager: "#pager"
	});
	$("#pager_left").remove();
});
/**
 * 初始化包埋列表
 * @param reqid
 */
function createNew1(reqid,width1){
	$("#new1").jqGrid({
		url:"../pathologysample/pieces/ajax/getItem",
		datatype: "json",
		mtype:"GET",
		height: 170,
		width:width1,
		postData:{"reqId":reqid},
		colNames: ['样本ID','材块ID','蜡块ID','病理编号','蜡块名称','蜡块序号','蜡块标签','蜡块条码号','材块编号','材块数','白片数', '取材部位',
			'是否已切片','切片医生','切片时间','是否已打印标签','标签打印人员','标签打印时间','剩余处理','特殊要求', '取材时间','取材医生',
			'包埋时间','包埋医生ID','包埋医生', '包埋状态','材块ID'],
		colModel: [
			{name:'parsampleid',hidden:true},//样本ID
			{name:'parpieceids',hidden:true},//材块ID
			{name:'paraffinid',hidden:true},//蜡块ID
			{name:'parpathologycode',hidden:true},//病理编号
			{name:'parname',hidden:true},//蜡块名称
			{name:'parparaffinno',hidden:true},//蜡块序号
			{name:'parparaffincode',hidden:true},//蜡块标签
			{name:'parbarcode',hidden:true},//蜡块条码号
			{ name: 'piecode', index: 'piecode'},//材块编号
			{ name: 'parpiececount', index: 'parpiececount'},//材块数
			{ name: 'parnullslidenum', index: 'parnullslidenum'},//白片数
			{ name: 'parpieceparts', index: 'parpieceparts'},//取材部位
			{name:'parissectioned',hidden:true},//是否已切片
			{name:'parsectioneddoctor',hidden:true},//切片医生
			{name:'parsectionedtime',hidden:true},//切片时间
			{name:'parisprintlabel',hidden:true},//是否已打印标签
			{name:'parprintuser',hidden:true},//标签打印人员
			{name:'parprinttime',hidden:true},//标签打印时间
			{name:'parremaining',hidden:true},//剩余处理
			{ name: 'piespecial', index: 'piespecial'},//特殊要求
			{ name: 'piesamplingtime', index: 'piesamplingtime',formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))}},//取材时间
			{ name: 'piedoctorname', index: 'piedoctorname'},//取材医生
			{ name: 'pieembedtime', index: 'pieembedtime',formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))}},//包埋时间
			{name:'pieembeddoctorid',hidden:true},//包埋医生ID
			{ name: 'pieembeddoctorname', index: 'pieembeddoctorname'},//包埋医生
			{ name: 'pieisembed', index: 'pieisembed',formatter: "select", editoptions:{value:"0:未包埋;1:已包埋"}},//包埋状态
			{name:'pieceid',hidden:true}//材块ID
			],
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		beforeEditCell:function(rowid,cellname,v,iRow,iCol){
			canChange(rowid,1);
		},
		viewrecords: true,
		rownumbers : true,
		ondblClickRow: function (id) {
			var rowData = $("#new1").jqGrid('getRowData',id);
			$('#sampleForm')[0].reset();
			getSampleData(rowData.parsampleid);
		},
		onSelectRow:function(id,status){
			var rowData = $("#new1").jqGrid('getRowData',id);
			$('#sampleForm')[0].reset();
			getSampleData(rowData.parsampleid);
		},
		onSelectAll:function(aRowids,status){
			var rowIds = $("#new1").jqGrid('getDataIDs');
			for(var k = 0; k<rowIds.length; k++) {
				var curRowData = jQuery("#new1").jqGrid('getRowData', rowIds[k]);
				var curChk = $("#"+rowIds[k]+"").find(":checkbox");
				if(status){
					curChk.attr('checked', 'true');   //设置所有checkbox被选中
				}else{
					curChk.attr('checked', 'false');   //设置所有checkbox被选中
				}
			}
		}
	});
}
/**
 * 查询数据
 */
function searchList() {
	clearData();
	var req_code = $('#req_code').val();
	var patient_name = $('#patient_name').val();
	var send_hosptail = $('#send_hosptail').val();
	var req_bf_time = $('#req_bf_time').val();
	var req_af_time = $('#req_af_time').val();
	var send_dept = $('#send_dept').val();
	//var send_doctor = $('#send_doctor').val();
	var send_doctor = "";//内部医嘱
	if($("#send_doctor").is(':checked')){
		send_doctor = "1";
	}
	//var req_sts = $('#req_sts').val();
	// var req_sts = $("input[name='req_sts']:checked").val();//包埋状态
	var req_sts = $('#req_sts').val();//包埋状态
	jQuery("#new").jqGrid("clearGridData");
	jQuery("#new").jqGrid('setGridParam',{
		url: "../pathologysample/paraffin/ajax/sample",
		//发送数据
		postData : {"req_code":req_code,"patient_name":patient_name,"send_hosptail":send_hosptail,"req_bf_time":req_bf_time,
			"req_af_time":req_af_time,"send_dept":send_dept,"send_doctor":send_doctor,"req_sts":req_sts},
		page : 1
	}).trigger('reloadGrid');//重新载入
}
function fillInfo(id){
	setcolor(id);
	clearData();
	var rowData = $("#new").jqGrid('getRowData',id);
	getSampleData(rowData.sampleid);
	var dataIds = "'" + rowData.pieceid + "'";
	//var req_sts = $("input[name='req_sts']:checked").val();//包埋状态
	var req_sts = $("#req_sts").val();//包埋状态
	if(req_sts == "0"){
		var rowData2 = $("#new").jqGrid('getRowData',id);
		addRow(rowData2);
	}else{
		jQuery("#new1").jqGrid('setGridParam',{
			url: "../pathologysample/paraffin/ajax/getItem",
			//发送数据
			postData : {"reqId":dataIds}
		}).trigger('reloadGrid');//重新载入
	}

}
// function fillInfo(id){
// 	var selectedIds = $("#new").jqGrid('getDataIDs');
// 	var maxSelectId = Math.max.apply(Math,selectedIds);
// 	if(id > 0){
// 		selectedIds = $("#new").jqGrid("getGridParam","selarrrow");
// 		maxSelectId = Math.max.apply(Math,selectedIds);
// 	}
// 	clearData();
// 	var rowData = $("#new").jqGrid('getRowData',maxSelectId);
// 	getSampleData(rowData.sampleid);
// 	var dataIds = "";
// 	$(selectedIds).each(function () {
// 			var rowData1 = $("#new").jqGrid('getRowData',this.toString());
// 			if(dataIds == ""){
// 				dataIds = "'" + rowData1.pieceid + "'";
// 			}else{
// 				dataIds = dataIds +",'"+ rowData1.pieceid+"'";
// 			}
// 		}
// 	);
// 	var req_sts = $("#req_sts").val();
// 	if(req_sts == "1"){
// 		$(selectedIds).each(function () {
// 				var rowData2 = $("#new").jqGrid('getRowData',this.toString());
// 				addRow(rowData2);
// 			}
// 		);
// 	}else{
// 		jQuery("#new1").jqGrid('setGridParam',{
// 			url: "../pathologysample/paraffin/ajax/getItem",
// 			//发送数据
// 			postData : {"reqId":dataIds}
// 		}).trigger('reloadGrid');//重新载入
// 	}
//
// }
function getSampleData(id) {
	$.get("../pathologysample/paraffin/get",{id:id},function(data) {
		if(data != "") {
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
function addRow(data){
	//var selectedId = $("#new1").jqGrid("getGridParam", "selrow");
	var ids = $("#new1").jqGrid('getDataIDs');
	var maxId = 1;
	if(ids == null || ids == ""){
	}else{
		maxId = Math.max.apply(Math,ids)+1;
	}
	var  sampathologycode = $('#sampathologycode').val();
	var dataRow = {
		parsampleid:data.sampleid,//样本ID
		parpieceids:data.pieceid,//材块ID
		paraffinid:"",//蜡块ID
		parpathologycode:data.piepathologycode,//病理编号
		parname:data.piecode,//蜡块名称
		parparaffinno:data.piesamplingno,//蜡块序号
		parparaffincode:data.piecode,//蜡块标签
		parbarcode:"",//蜡块条码号
		piecode:data.piecode,//材块编号
		parpiececount:data.piecounts,//材块数
		parnullslidenum:data.pienullslidenum,//白片数
		parpieceparts:data.pieparts,//取材部位
		parissectioned:0,//是否已切片
		parsectioneddoctor:"",//切片医生
		parsectionedtime:"",//切片时间
		parisprintlabel:"",//是否已打印标签
		parprintuser:"",//标签打印人员
		parprinttime:"",//标签打印时间
		parremaining:"",//剩余处理
		piespecial:data.piespecial,//特殊要求
		piesamplingtime:data.piesamplingtime,//取材时间
		piedoctorname:data.piedoctorname,//取材医生
		pieembedtime:new Date(),//包埋时间
		pieembeddoctorid:$("#local_userid").val(),//包埋医生ID
		pieembeddoctorname:$("#local_username").val(),//包埋医生
		pieisembed:data.pieisembed,//包埋状态
		pieceid:data.pieceid//材块ID
	};
	// var rowid = 1;
	// if(Math.max.apply(Math,ids) > ids.length ){
	// 	rowid = Math.max.apply(Math,ids) + 1;
	// }else{
	// 	rowid = ids.length + 1;
	// }
	$("#new1").jqGrid("addRowData", maxId, dataRow, "last");
	// if (selectedId) {
	// 	$("#new1").jqGrid("addRowData", rowid, dataRow, "after", selectedId);
	// } else {
	// 	$("#new1").jqGrid("addRowData", rowid, dataRow, "last");
	// }
	//$('#plsfList').jqGrid('editRow', rowid, false);
}
function delRow(id){
	var selectIds = $("#new").jqGrid("getGridParam","selarrrow");
	alert(selectIds);
	if(selectIds == null || selectIds == ""){
		clearData();
	}else{
		var maxSelectId = Math.max.apply(Math,selectIds);
		var rowData = $("#new").jqGrid('getRowData',maxSelectId);
		$("#new1").jqGrid("delRowData", id);
		if(rowData.piesampleid != $("#sampleid").val()){
			$('#sampleForm')[0].reset();
			getSampleData(rowData.piesampleid);
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
function canChange(id,numsts){
	var rowData = $("#new1").jqGrid('getRowData',id);
	if(rowData.pieceid == null || rowData.pieceid == ""){

	}else {
		$.get("../pathologysample/pieces/canchange", {
				id: rowData.pieceid,
				sts: numsts
			},
			function (data) {
				if (data.success) {
					// layer.msg(data.message, {icon: 1, time: 1000});
					// location.reload();
				} else {
					layer.msg(data.message, {icon: 2, time: 1000});
					return;
				}
			});
	}
}


