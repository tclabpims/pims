/**
 * 包埋
 */
function saveInfo(num) {
	var rowdatas = $('#new1').jqGrid('getRowData');
	var post = true;
	if(post) {
		$.post("../pathologysample/paraffin/editSample", {
			savenum:num,
			//sampleid:$("#sampleid").val(),
			samthirdv:JSON.stringify(rowdatas)
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
function searchSts(){
	var req_sts = $("#req_sts").val();
	if(req_sts == "1"){
		$("#resetbutton").attr({"disabled":true});
		$("#saveButton").removeAttr("disabled");//将按钮可用
	}else{
		$("#saveButton").attr({"disabled":true});
		$("#resetbutton").removeAttr("disabled");//将按钮可用
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
	$("#resetbutton").attr({"disabled":true});
	createNew1("");
	var clientHeight= $(window).innerHeight();
	var height =clientHeight-$('#div_1').height()- $('#div_2').height()-200;
	//var logyid = $("#logyid").val();
	var logyid ="";
	var send_hosptail = "";
	var req_code = $('#req_code').val();
	var patient_name = $('#patient_name').val();//病人姓名
	//var send_hosptail = $('#send_hosptail').val();
	var req_bf_time = $('#req_bf_time').val();
	var req_af_time = $('#req_af_time').val();
	var send_dept = $('#send_dept').val();//病理号
	var send_doctor = $('#send_doctor').val();//内部医嘱
	var req_sts = $('#req_sts').val();//包埋状态
	$("#new").jqGrid({
		url: "../pathologysample/paraffin/ajax/sample",
		mtype: "GET",
		datatype: "json",
		postData:{"req_code":req_code,"patient_name":patient_name,"send_hosptail":send_hosptail,"req_bf_time":req_bf_time,
			"req_af_time":req_af_time,"send_dept":send_dept,"send_doctor":send_doctor,"req_sts":req_sts,"logyid":logyid},
		colNames: ['材块ID','标本id','病理状态', '病理号','材块编号','补取医嘱','材块数','白片数', '取材部位','特殊要求', '取材时间','取材医生ID','取材医生','序号'],
		colModel: [
			{name:'pieceid',hidden:true},
			{name:'piesampleid',hidden:true},
			{ name: 'piestate', index: 'piestate',formatter: "select", editoptions:{value:"1:未包埋;2:已包埋"}},
			{ name: 'piepathologycode', index: 'piepathologycode'},
			{ name: 'piecode', index: 'piecode'},
			{ name: 'piefirstv', index: 'piefirstv'},
			{name:'piecounts',hidden:true},
			{name:'pienullslidenum',hidden:true},
			{name:'pieparts',hidden:true},
			{name:'piespecial',hidden:true},
			{name:'piesamplingtime',hidden:true,formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))}},
			{name:'piedoctorid',hidden:true},
			{name:'piedoctorname',hidden:true},
			{name:'piesamplingno',hidden:true}
		],
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
			$("#new").setSelection(1);
		},
		ondblClickRow: function (id) {
		},
		onSelectRow:function(id,status){
			if(status){
				fillInfo(id);
			}else{
				//delRow(id);
				fillInfo(id);
			}

		},
		onSelectAll:function(aRowids,status){
			if(status){
				fillInfo(0);   //选中
			}else{
				clearData();   //取消选中
			}
		},
		multiselect: true,
		viewrecords: true,
		height:300,
		autowidth: true,
		rowNum: 10,
		rowList:[10,20,30],
		rownumbers: true, // 显示行号
		rownumWidth: 10, // the width of the row numbers columns
		pager: "#pager"
	});
});
/**
 * 初始化包埋列表
 * @param reqid
 */
function createNew1(reqid){
	$("#new1").jqGrid({
		url:"../pathologysample/pieces/ajax/getItem",
		datatype: "json",
		mtype:"GET",
		height: 170,
		width:880,
		postData:{"reqId":reqid},
		colNames: ['样本ID','材块ID','蜡块ID','病理编号','蜡块名称','蜡块序号','材块编号','材块数','白片数', '取材部位','特殊要求', '取材时间','取材医生','包埋时间','包埋医生', '包埋状态'],
		colModel: [
			{name:'parsampleid',hidden:true},
			{name:'parpieceids',hidden:true},
			{name:'paraffinid',hidden:true},
			{name:'parpathologycode',hidden:true},
			{name:'parname',hidden:true},
			{name:'parparaffinno',hidden:true},
			{ name: 'piecode', index: 'piecode'},
			{ name: 'parpiececount', index: 'parpiececount'},
			{ name: 'parnullslidenum', index: 'parnullslidenum'},
			{ name: 'parpieceparts', index: 'parpieceparts',edittype: "select",formatter: "select", editoptions:{value:"1:肌腱;2:肺;3:肝脏"}},
			{ name: 'piespecial', index: 'piespecial'},
			{ name: 'piesamplingtime', index: 'piesamplingtime',formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))},
				formatoptions:{srcformat: 'Y-m-d H:i:s', newformat: 'Y-m-d H:i:s'}},
			{ name: 'piedoctorname', index: 'piedoctorname'},
			{ name: 'pieembedtime', index: 'pieembedtime',formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))},
				formatoptions:{srcformat: 'Y-m-d H:i:s', newformat: 'Y-m-d H:i:s'}},
			{ name: 'pieembeddoctorname', index: 'pieembeddoctorname'},
			{ name: 'parfirstn', index: 'parfirstn',formatter: "select", editoptions:{value:"0:未取材;1:未包埋;2:已包埋;3:已切片;4:已初诊;5:已审核"}}
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
	var send_doctor = $('#send_doctor').val();
	var req_sts = $('#req_sts').val();
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
	var selectedIds = $("#new").jqGrid('getDataIDs');
	var maxSelectId = Math.max.apply(Math,selectedIds);
	if(id > 0){
		selectedIds = $("#new").jqGrid("getGridParam","selarrrow");
		maxSelectId = Math.max.apply(Math,selectedIds);
	}
	clearData();
	var rowData = $("#new").jqGrid('getRowData',maxSelectId);
	getSampleData(rowData.piesampleid);
	var dataIds = "";
	$(selectedIds).each(function () {
			var rowData1 = $("#new").jqGrid('getRowData',this.toString());
			if(dataIds == ""){
				dataIds = "'" + rowData1.pieceid + "'";
			}else{
				dataIds = dataIds +",'"+ rowData1.pieceid+"'";
			}
		}
	);
	var req_sts = $("#req_sts").val();
	if(req_sts == "1"){
		$(selectedIds).each(function () {
				var rowData2 = $("#new").jqGrid('getRowData',this.toString());
				addRow(rowData2);
			}
		);
	}else{
		jQuery("#new1").jqGrid('setGridParam',{
			url: "../pathologysample/paraffin/ajax/getItem",
			//发送数据
			postData : {"reqId":dataIds}
		}).trigger('reloadGrid');//重新载入
	}

}
function getSampleData(id) {
	$.get("../pathologysample/paraffin/get",{id:id},function(data) {
		if(data != "") {
			$("#sampathologycode").val(data.sampathologycode);
			$("#sampleid").val(data.sampleid);
			$("#samsamplestatus").val(data.samsamplestatus);
			$("#samsenddoctorid").val(data.samsenddoctorid);
			$("#sampatientname").val(data.sampatientname);
			$("#samdeptcode").val(data.samdeptcode);
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
		parsampleid:data.piesampleid,
		parpieceids:data.pieceid,
		paraffinid: "",
		parpathologycode:data.piepathologycode,
		parname:data.piecode,
		parparaffinno: data.piesamplingno,
		piecode: data.piecode,
		parpiececount: data.piecounts,
		parnullslidenum: data.pienullslidenum,
		parpieceparts:data.pieparts,
		piespecial:data.piespecial ,
		piesamplingtime: data.piesamplingtime,
		piedoctorname:data.piedoctorname,
		pieembedtime: new Date(),
		pieembeddoctorname:$("#local_name").val(),
		parfirstn: 1
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
	var ss = now.getMilliseconds();    //秒
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


