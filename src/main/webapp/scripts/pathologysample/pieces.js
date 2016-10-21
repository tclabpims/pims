/**
 * 上一个
 */
function upSample(){
	clearData();
	var id = $("#new").jqGrid('getGridParam', 'selrow');
	var ids = $("#new").jqGrid('getDataIDs');
	var minId = Math.min.apply(Math,ids);
	if(id == minId){
		id = Math.max.apply(Math,ids);
		$("#new").setSelection(id);
	}else{
		id = parseInt(id) - 1;
		$("#new").setSelection(id);
	}
}
/**
 * 下一个
 */
function downSample() {
	clearData();
	var id = $("#new").jqGrid('getGridParam', 'selrow');
	var ids = $("#new").jqGrid('getDataIDs');
	var maxId = Math.max.apply(Math,ids);
	if(id == maxId){
		id = Math.min.apply(Math,ids);
		$("#new").setSelection(id);
	}else{
		id = parseInt(id) + 1;
		$("#new").setSelection(id);
	}
}
/**
 * 取材
 */
function saveInfo(num) {
	var rowdatas = $('#new1').jqGrid('getRowData');
	if(rowdatas == null || rowdatas == ""){
		alert("请录入材块信息！！");
		return false;
	}
	var post = true;
	var samissamplingall = 0;
	var samisdecacified = 0;
	if($("#samissamplingall").is(':checked')){
		samissamplingall = 1;
	}
	if($("#samisdecacified").is(':checked')){
		samisdecacified = 1;
	}
	if(post) {
		$.post("../pathologysample/pieces/editSample", {
			states:1,
			savenum:num,
			sampleid:$("#sampleid").val(),
			samsamplestatus:$("#samsamplestatus").val(),
			sampathologycode:$("#sampathologycode").val(),
			samissamplingall:samissamplingall,
			samisdecacified:samisdecacified,
			pieceses:JSON.stringify(rowdatas)
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
	var send_doctor = $('#send_doctor').val();//补医嘱
	var req_sts = $('#req_sts').val();//取材状态
	$("#new").jqGrid({
		url: "../pathologysample/pieces/ajax/sample",
		mtype: "GET",
		datatype: "json",
		postData:{"req_code":req_code,"patient_name":patient_name,"send_hosptail":send_hosptail,"req_bf_time":req_bf_time,
			"req_af_time":req_af_time,"send_dept":send_dept,"send_doctor":send_doctor,"req_sts":req_sts,"logyid":logyid},
		colNames: ['ID','病理状态', '病理号', '送检医生','送检医院','病人名','补取医嘱'],
		colModel: [
			{name:'sampleid',hidden:true},
			{ name: 'samsamplestatus', index: 'samsamplestatus',formatter: "select", editoptions:{value:"0:已登记;1:已取材;2:已包埋;3:已切片;4:已初诊;5:已审核;6:已发送;7:会诊中:8:报告已打印"}},
			{ name: 'sampathologycode', index: 'sampathologycode'},
			{ name: 'samsenddoctorname', index: 'samsenddoctorname'},
			{ name: 'samsendhospital', index: 'samsendhospital'},
			{ name: 'sampatientname', index: 'sampatientname'},
			{ name: 'samsamplestatus', index: 'samsamplestatus'}
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
		onSelectRow:function(id){
			fillInfo();
		},
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
 * 初始化材块列表
 * @param reqid
 */
function createNew1(reqid){
	$("#new1").jqGrid({
		url:"../pathologysample/pieces/ajax/getItem",
		datatype: "json",
		mtype:"GET",
		height: 170,
		width:660,
		postData:{"reqId":reqid},
		colNames: ['材块条码编号','客户ID','材块ID','取材单位','病理号', '取材序号','材块数','白片数', '取材部位','取材医生ID','取材医生','录入员ID',
			'录入员', '取材时间','特殊要求', '取材状态','标记物','是否脱水','脱水时间','是否包埋','包埋时间','包埋人员id','包埋人员姓名','所属蜡块id'],
		colModel: [
			{name:'piecode',hidden:true},//材块条码编号
			{name:'piesampleid',hidden:true},//客户ID
			{name:'pieceid',hidden:true},//材块ID
			{name:'pieunit',hidden:true},//取材单位
			{ name: 'piepathologycode', index: 'piepathologycode'},//病理号
			{ name: 'piesamplingno', index: 'piesamplingno'},//取材序号
			{ name: 'piecounts', index: 'piecounts',editable:true,editrules: {edithidden:true,required:true,number:true,minValue:1,maxValue:100}},//材块数
			{ name: 'pienullslidenum', index: 'pienullslidenum',editable:true,editrules: {edithidden:true,required:true,number:true,minValue:0,maxValue:100}},//白片数
			{ name: 'pieparts', index: 'pieparts',editable:true,edittype: "select",formatter: "select", editoptions:{value:"1:肌腱;2:肺;3:肝脏"}},//取材部位
			{ name: 'piedoctorid', hidden:true},//取材医生ID
			{ name: 'piedoctorname', index: 'piedoctorname'},//取材医生
			{ name: 'pierecorderid', hidden:true},//录入员ID
			{ name: 'pierecordername', index: 'pierecordername'},//录入员
			{ name: 'piesamplingtime', index: 'piesamplingtime',formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))}},//取材时间
			{ name: 'piespecial', index: 'piespecial',editable:true},//特殊要求
			{ name: 'piestate', index: 'piestate',formatter: "select", editoptions:{value:"0:未取材;1:已取材;2:已包埋;3:已切片;4:已初诊;5:已审核"}},//取材状态
			{name:'piesign',hidden:true},//标记物
			{name:'pieisdeprivation',hidden:true},//是否脱水
			{name:'piedeprivationtime',hidden:true,formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))}},//脱水时间
			{name:'pieisembed',hidden:true},//是否包埋
			{name:'pieembedtime',hidden:true,formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))}},//包埋时间
			{name:'pieembeddoctorid',hidden:true},//包埋人员id
			{name:'pieembeddoctorname',hidden:true},//包埋人员姓名
			{name:'pieparaffinid',hidden:true}//所属蜡块id
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
		multiselect: true,
		cellsubmit: "clientArray",
		//autowidth: true,
		//shrinkToFit:false,
		//autoScroll: true,
		cellEdit:true,
		rownumbers : true,
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
		url: "../pathologysample/pieces/ajax/sample",
		//发送数据
		postData : {"req_code":req_code,"patient_name":patient_name,"send_hosptail":send_hosptail,"req_bf_time":req_bf_time,
			"req_af_time":req_af_time,"send_dept":send_dept,"send_doctor":send_doctor,"req_sts":req_sts},
		page : 1
	}).trigger('reloadGrid');//重新载入
}
function fillInfo(){
	clearData();
	var id = $("#new").jqGrid('getGridParam', 'selrow');
	var rowData = $("#new").jqGrid('getRowData',id);
	if (id == null || id == 0) {
		layer.msg('请先选择数据', {icon: 2, time: 1000});
		return false;
	}
	getSampleData(rowData.sampleid);
	// if($("#samissamplingall").is(':checked')){
	// 	$("#addrow1").attr({"disabled":true});
	// }else{
	// 	$("#addrow1").removeAttr("disabled");
	// }
	if($("#samsamplestatus").val() > 1){
		$("#addrow1").attr({"disabled":true});
	}else{
		$("#addrow1").removeAttr("disabled");
	}
	jQuery("#new1").jqGrid('setGridParam',{
		url: "../pathologysample/pieces/ajax/getItem",
		//发送数据
		postData : {"reqId":rowData.sampleid}
	}).trigger('reloadGrid');//重新载入
	//$('#sampleForm').find('input,textarea,select').attr('disabled',true) ;
	// $("#editButton").removeAttr("disabled");//将按钮可用
	// $("#editButton").removeAttr("disabled");//将按钮可用
	// $("#saveButton").attr({"disabled":"disabled"});
}
function getSampleData(id) {
	$.get("../pathologysample/pieces/get",{id:id},function(data) {
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
			// $("#samissamplingall").val(data.samissamplingall);
			// $("#samisdecacified").val(data.samisdecacified);
			$("#sampatientsex").val(data.sampatientsex);
			$("#sampatientdignoses").val(data.sampatientdignoses);
			$("#samthirdv").val(data.samthirdv);
			var samissamplingall = data.samissamplingall;
			var samisdecacified = data.samisdecacified;
			if(samisdecacified == 1){
				$("#samisdecacified").attr("checked",true);
			}
			if(samissamplingall == 1){
				$("#samissamplingall").attr("checked",true);
				$("#addrow1").attr({"disabled":true});
			}else{
				$("#addrow1").removeAttr("disabled");
			}
		} else {
			layer.msg("该申请单不存在！", {icon: 0, time: 1000});
		}
	});
}
function addRow(){
	var ids = $("#new1").jqGrid('getDataIDs');
	var maxId = 1;
	if(ids == null || ids == ""){
	}else{
		maxId = Math.max.apply(Math,ids)+1;
	}
	var  sampathologycode = $('#sampathologycode').val();
	var dataRow = {
		piecode:sampathologycode+"-"+maxId,
		piesampleid:$("#sampleid").val(),
		pieunit:1,
		pieceid: "",
		piepathologycode: sampathologycode,
		piesamplingno: maxId,
		piecounts: 1,
		pienullslidenum:0,
		pieparts:1,
		piedoctorid:$("#doctor_id").val() ,
		pierecorderid: $("#input_user").val(),
		piedoctorname:$("#doctor_id").find("option:selected").text(),
		pierecordername: $("#input_user").find("option:selected").text(),
		piesamplingtime:new Date(),
		piespecial: "",
		piestate:0,
		piesign:"",//标记物
		pieisdeprivation:"",//是否脱水
		piedeprivationtime:"",//脱水时间
		pieisembed:"0",//是否包埋
		pieembedtime:"",//包埋时间
		pieembeddoctorid:"",//包埋人员id
		pieembeddoctorname:"",//包埋人员姓名
		pieparaffinid:""//所属蜡块id
	};
	$("#new1").jqGrid("addRowData", maxId, dataRow, "last");
}
function delRow(){
	var selectedIds = $("#new1").jqGrid("getGridParam","selarrrow");
	var ids = $("#new1").jqGrid('getDataIDs');
	var maxId = Math.max.apply(Math,ids);
	if(selectedIds == null || selectedIds == ""){
		if(!maxId){
			alert("无数据可删除!");
			return;
		}else{
			var rowData = $("#new1").jqGrid('getRowData',maxId);
			if(rowData.pieceid == null || rowData.pieceid == ""){
				return false;
			}else {
				$.get("../pathologysample/pieces/canchange", {
						id: rowData.pieceid,
						sts: 2
					},
					function (data) {
						if (data.success) {
							$("#new1").jqGrid("delRowData", maxId);
						} else {
							layer.msg(data.message, {icon: 2, time: 1000});
							return;
						}
					});
			}
		}
	}else{
		var maxSelectId = Math.max.apply(Math,selectedIds);
		if(maxId > maxSelectId){
			alert("请从最后一条数据开始删除");
			return;
		}else{
			$(selectedIds).each(function () {
				var delrow = this.toString();
				var rowData = $("#new1").jqGrid('getRowData',delrow);
				if(rowData.pieceid == null || rowData.pieceid == ""){
					return false;
				}else {
					$.get("../pathologysample/pieces/canchange", {
							id: rowData.pieceid,
							sts: 2
						},
						function (data) {
							if (data.success) {
								$("#new1").jqGrid("delRowData", delrow);
							} else {
								layer.msg(data.message, {icon: 2, time: 1000});
								return;
							}
						});
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
		return false;
	}else {
		$.get("../pathologysample/pieces/canchange", {
				id: rowData.pieceid,
				sts: numsts
			},
			function (data) {
				if (data.success) {

				} else {
					layer.msg(data.message, {icon: 2, time: 1000});
					return;
				}
			});
	}
}

