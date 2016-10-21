/**
 * 切片
 */
function saveInfo(num) {
	var rowdatas = $('#new1').jqGrid('getRowData');
	var selectedIds = $("#new").jqGrid("getGridParam","selarrrow");
	var arr = new Array();
	if(selectedIds.length > 0){
		$(selectedIds).each(function () {
			var rowData1 = $("#new").jqGrid('getRowData',this.toString());
			arr.push(rowData1);
			}
		);
	}
	var post = true;
	if(post) {
		$.post("../pathologysample/slide/editSample", {
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
 * 查询切片状态
 */
function searchSts(){
	var req_sts = $("#req_sts").val();
	if(req_sts == "1"){
		$("#saveButton").attr({"disabled":true});
		$("#resetbutton").removeAttr("disabled");//将按钮可用
	}else{
		$("#resetbutton").attr({"disabled":true});
		$("#saveButton").removeAttr("disabled");//将按钮可用
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
	var patient_name = $('#patient_name').val();//病人姓名
	var send_hosptail = $('#send_hosptail').val();
	var req_bf_time = $('#req_bf_time').val();
	var req_af_time = $('#req_af_time').val();
	var send_dept = $('#send_dept').val();//病理号
	var send_doctor = "";//内部医嘱
	if($("#send_doctor").is(':checked')){
		req_code = "1";
	}
	var req_code = "";//预留白片
	if($("#req_code").is(':checked')){
		req_code = "1";
	}
	var req_sts = $('#req_sts').val();//切片状态
	$("#new").jqGrid({
		url: "../pathologysample/slide/ajax/sample",
		mtype: "GET",
		datatype: "json",
		postData:{"req_code":req_code,"patient_name":patient_name,"send_hosptail":send_hosptail,"req_bf_time":req_bf_time,
			"req_af_time":req_af_time,"send_dept":send_dept,"send_doctor":send_doctor,"req_sts":req_sts,"logyid":logyid},
		colNames: ['蜡块ID','标本id','病理状态','蜡块状态', '病理号','病人名','送检医生','送检科室','包埋医生', '包埋时间','切片医生', '切片时间','打印状态','白片数',
		'材块ID','材块编号','取材医生','取材时间','客户代码','客户代码','蜡块序号','取材部位','病种类别'],
		colModel: [
			{name:'paraffinid',hidden:true},
			{name:'sampleid',hidden:true},
			{name:'samsamplestatus',hidden:true},
			{ name: 'parissectioned', index: 'parissectioned',formatter: "select", editoptions:{value:"0:未切片;1:已切片"},width:100},
			{ name: 'sampathologycode', index: 'sampathologycode',width:100},
			{ name: 'sampatientname', index: 'sampatientname',width:100},
			{ name: 'samsenddoctorname', index: 'samsenddoctorname',width:100},
			{name:'samdeptname',index:'samdeptname',width:100},
			{name:'pieembeddoctorname',index:'pieembeddoctorname',width:100},
			{name:'pieembedtime',index:'pieembedtime',formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))},width:100},
			{name:'parsectioneddoctor',index:'parsectioneddoctor',width:100},
			{name:'parsectionedtime',index:'parsectionedtime',formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))},width:100},
			{name:'parisprintlabel',index:'parisprintlabel',width:100},
			{name:'parnullslidenum',hidden:true},
			{name:'pieceid',hidden:true},//材块ID
			{name:'piecode',hidden:true},//材块编号
			{name:'piedoctorname',hidden:true},//取材医生
			{name:'piesamplingtime',hidden:true,formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))}},//取材时间
			{name:'samcustomerid',hidden:true},//客户代码
			{name:'samcustomercode',hidden:true},//客户代码
			{name:'parparaffinno',hidden:true},//蜡块序号
			{name:'parpieceparts',hidden:true},//取材部位
			{name:'sampathologyid',hidden:true}//病种类别
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
				fillInfo(id);
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
		width:440,
		//autowidth: true,
		shrinkToFit:false,
		autoScroll: true,
		rowNum: 10,
		rowList:[10,20,30],
		rownumbers: true, // 显示行号
		rownumWidth: 10, // the width of the row numbers columns
		pager: "#pager"
	});
});
/**
 * 初始化玻片列表
 * @param reqid
 */
function createNew1(reqid){
	$("#new1").jqGrid({
		url:"../pathologysample/slide/ajax/getItem",
		datatype: "json",
		mtype:"GET",
		height: 280,
		width:880,
		postData:{"reqId":reqid},
		colNames: ['样本ID','材块ID','蜡块ID','蜡块ID','材块编号','玻片类型','玻片序号','取材医生','取材时间','包埋医生', '包埋时间','切片医生', '切片时间','切片状态','印刷状态','分类', '特检项目',
		'玻片id','客户代码','客户代码','玻片条码号','病理编号','玻片来源','玻片使用状态','玻片号','蜡块序号','取材部位'],
		colModel: [
			{name:'slisampleid',hidden:true},//样本ID
			{name:'pieceid',hidden:true},//材块ID
			{name:'sliparaffinid',hidden:true},//蜡块ID
			{name:'paraffinid',hidden:true},//蜡块ID
			{name:'sliparaffincode',index:'sliparaffincode'},//材块编号
			{name:'slislidetype',index:'slislidetype',formatter: "select", editoptions:{value:"0:常规;1:白片"}},//玻片类型
			{name:'slislideno',index:'slislideno'},//玻片序号
			{name:'piedoctorname',index:'piedoctorname'},//取材医生
			{ name: 'piesamplingtime', index: 'piesamplingtime',formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))},width:100},//取材时间
			{ name: 'pieembeddoctorname', index: 'pieembeddoctorname',width:100},//包埋医生
			{ name: 'pieembedtime', index: 'pieembedtime',formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))},width:100},//包埋时间
			{ name: 'parsectioneddoctor', index: 'parsectioneddoctor',width:100},//切片医生
			{ name: 'parsectionedtime', index: 'parsectionedtime',formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))},width:100},//切片时间
			{ name: 'parissectioned', index: 'parissectioned',formatter: "select", editoptions:{value:"0:未切片;1:已切片"},width:100},//切片状态
			{ name: 'sliifprint', index: 'sliifprint',formatter: "select", editoptions:{value:"0:未打印;1:已打印"},width:100},//印刷状态
			{ name: 'sampathologyid', index: 'sampathologyid',width:100},//分类
			{ name: 'slitestitemname', index: 'slitestitemname',width:100},//特检项目
			{name:'slideid',hidden:true},//玻片id
			{name:'slicustomerid',hidden:true},//客户代码
			{name:'slicustomercode',hidden:true},//客户代码
			{name:'slislidebarcode',hidden:true},//玻片条码号
			{name:'slipathologycode',hidden:true},//病理编号
			{name:'slislidesource',hidden:true},//玻片来源
			{name:'sliuseflag',hidden:true},//玻片使用状态
			{name:'slislidecode',hidden:true},//玻片号
			{name:'sliparaffinno',hidden:true},//蜡块序号
			{name:'slisamplingparts',hidden:true}//取材部位
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
			getSampleData(rowData.slisampleid);
		},
		onSelectRow:function(id,status){
			var rowData = $("#new1").jqGrid('getRowData',id);
			$('#sampleForm')[0].reset();
			getSampleData(rowData.slisampleid);
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
	var patient_name = $('#patient_name').val();
	var send_hosptail = $('#send_hosptail').val();
	var req_bf_time = $('#req_bf_time').val();
	var req_af_time = $('#req_af_time').val();
	var send_dept = $('#send_dept').val();
	var req_sts = $('#req_sts').val();
	var send_doctor = "";//内部医嘱
	if($("#send_doctor").is(':checked')){
		send_doctor = "1";
	}
	var req_code = "";//预留白片
	if($("#req_code").is(':checked')){
		req_code = "1";
	}
	jQuery("#new").jqGrid("clearGridData");
	jQuery("#new").jqGrid('setGridParam',{
		url: "../pathologysample/slide/ajax/sample",
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
	getSampleData(rowData.sampleid);
	var dataIds = "";
	$(selectedIds).each(function () {
			var rowData1 = $("#new").jqGrid('getRowData',this.toString());
			if(dataIds == ""){
				dataIds = "'" + rowData1.paraffinid + "'";
			}else{
				dataIds = dataIds +",'"+ rowData1.paraffinid+"'";
			}
		}
	);
	var req_sts = $("#req_sts").val();
	if(req_sts == "0"){
		$(selectedIds).each(function () {
				var rowData2 = $("#new").jqGrid('getRowData',this.toString());
				addRow(rowData2);
			}
		);
	}else{
		jQuery("#new1").jqGrid('setGridParam',{
			url: "../pathologysample/slide/ajax/getItem",
			//发送数据
			postData : {"reqId":dataIds}
		}).trigger('reloadGrid');//重新载入
	}

}
function getSampleData(id) {
	$.get("../pathologysample/slide/get",{id:id},function(data) {
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
	var bpnum = parseInt(data.parnullslidenum);
	var dataRow = {
		slisampleid:data.sampleid,//样本ID
		pieceid:data.pieceid,//材块ID
		sliparaffinid: data.paraffinid,//蜡块ID
		paraffinid: data.paraffinid,//蜡块ID
		sliparaffincode:data.paraffinid,//材块编号
		slislidetype:0,//玻片类型
		slislideno: 1,//玻片序号
		piedoctorname: data.piedoctorname,//取材医生
		piesamplingtime: data.piesamplingtime,//取材时间piesamplingtime
		pieembeddoctorname: data.pieembeddoctorname,//包埋医生
		pieembedtime:data.pieembedtime,//包埋时间
		parsectioneddoctor:$("#local_name").val(),//切片医生
		parsectionedtime: new Date(),//切片时间
		parissectioned:0,//切片状态
		sliifprint: 0,//印刷状态
		sampathologyid:data.sampathologyid,//分类
		slitestitemname: "",//特检项目
		slideid: "",//玻片id
		slicustomerid: data.samcustomerid,//客户代码
		slicustomercode: data.samcustomercode,//客户代码
		slislidebarcode:"",//玻片条码号
		slipathologycode:data.sampathologycode ,//病理编号
		slislidesource: 0,//玻片来源
		sliuseflag:0,//玻片使用状态
		slislidecode: data.paraffinid+"-1",//玻片号
		sliparaffinno:data.parparaffinno,//蜡块序号
		slisamplingparts: data.parpieceparts//取材部位
	};
	$("#new1").jqGrid("addRowData", maxId, dataRow, "last");
	if(bpnum > 0){
		for(var i=0;i<bpnum;i++){
			maxId = maxId + 1;
			var xh = 2 + i;
			var dataRow = {
				slisampleid:data.sampleid,//样本ID
				pieceid:data.pieceid,//材块ID
				sliparaffinid: data.paraffinid,//蜡块ID
				paraffinid: data.paraffinid,//蜡块ID
				sliparaffincode:data.paraffinid,//材块编号
				slislidetype:1,//玻片类型
				slislideno: xh,//玻片序号
				piedoctorname: data.piedoctorname,//取材医生
				piesamplingtime: data.piesamplingtime,//取材时间
				pieembeddoctorname: data.pieembeddoctorname,//包埋医生
				pieembedtime:data.pieembedtime,//包埋时间
				parsectioneddoctor:$("#local_name").val(),//切片医生
				parsectionedtime: new Date(),//切片时间
				parissectioned:0,//切片状态
				sliifprint: 0,//印刷状态
				sampathologyid:data.sampathologyid,//分类
				slitestitemname: "",//特检项目
				slideid: "",//玻片id
				slicustomerid: data.samcustomerid,//客户代码
				slicustomercode: data.samcustomercode,//客户代码
				slislidebarcode:"",//玻片条码号
				slipathologycode:data.sampathologycode ,//病理编号
				slislidesource: 0,//玻片来源
				sliuseflag:0,//玻片使用状态
				slislidecode: data.paraffinid+"-"+ xh,//玻片号
				sliparaffinno:data.parparaffinno,//蜡块序号
				slisamplingparts: data.parpieceparts//取材部位
			};
			$("#new1").jqGrid("addRowData", maxId, dataRow, "last");
		}
	}
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

