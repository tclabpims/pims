var nowrow = "";
var GRID_SELECTED_ROW_SAMPLEID = "";
var GRID_SELECTED_ROW_SAMPCUSTOMERID = "";
var PIC_TAKING_FROM = 1;
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
	//var id = $("#new").jqGrid('getGridParam', 'selrow');
	var ids = $("#new").jqGrid('getDataIDs');
	var minId = Math.min.apply(Math,ids);
	if(id == minId){
		id = Math.max.apply(Math,ids);
		//$("#new").setSelection(id);
	}else{
		id = parseInt(id) - 1;
		//$("#new").setSelection(id);
	}
	fillInfo(id);
}
/**
 * 下一个
 */
function downSample() {
	id = nowrow;
	clearData();
	//var id = $("#new").jqGrid('getGridParam', 'selrow');
	var ids = $("#new").jqGrid('getDataIDs');
	var maxId = Math.max.apply(Math,ids);
	if(id == maxId){
		id = Math.min.apply(Math,ids);
		//$("#new").setSelection(id);
	}else{
		id = parseInt(id) + 1;
		//$("#new").setSelection(id);
	}
	fillInfo(id);
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
			samjjsj:$("#samjjsj").val(),
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

	var clientHeight= $(window).innerHeight();
	var height = $("#formDialog").height() - $('#search_div_1').height()+70;
	var width = $('#search_div_1').width()-5;
	var width1 = $("#div_main").width();

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
	var send_doctor = $('#send_doctor').val();//补医嘱
	//var req_sts = $('#req_sts').val();//取材状态
	var req_sts = $("input[name='req_sts']:checked").val();//包埋状态
	$("#new").jqGrid({
		url: "../pathologysample/pieces/ajax/sample",
		mtype: "GET",
		datatype: "json",
		postData:{"req_code":req_code,"patient_name":patient_name,"send_hosptail":send_hosptail,"req_bf_time":req_bf_time,
			"req_af_time":req_af_time,"send_dept":send_dept,"send_doctor":send_doctor,"req_sts":req_sts,"logyid":logyid},
		colNames: ['ID','取材状态', '病理号', '送检医生','送检医院','病人名','补取医嘱','客户ID'],
		colModel: [
			{name:'sampleid',hidden:true},
			{ name: 'samsamplestatus', index: 'samsamplestatus',formatter: "select", editoptions:{value:"0:未取材;1:已取材;2:已包埋;3:已切片;4:已初诊;5:已审核;6:已发送;7:会诊中:8:报告已打印"}},
			{ name: 'sampathologycode', index: 'sampathologycode'},
			{ name: 'samsenddoctorname', index: 'samsenddoctorname'},
			{ name: 'samsendhospital', index: 'samsendhospital'},
			{ name: 'sampatientname', index: 'sampatientname'},
			{ name: 'samsamplestatus', index: 'samsamplestatus'},
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
		height:390,
		width: width,
		shrinkToFit:false,
		autoScroll: true,
		rowNum: 10,
		rowList:[10,20,30],
		rownumbers: true, // 显示行号
		rownumWidth: 30, // the width of the row numbers columns
		pager: "#pager"
	});
	createNew2();
	$("#pager_left").remove();
	//巨检描述
	$("#jjsj").autocomplete({
		source: function( request, response ) {
			$.ajax({
				url: "../template/ajax/item",
				dataType: "json",
				data: {
					name : request.term,//名称
					sampathologyid:$("#sampathologyid").val()//病种类别
				},
				success: function( data ) {
					response( $.map( data, function( result ) {
						return {
							label: result.id + " : " + result.name,
							value: result.name,
							id : result.id
						}
					}));
				}
			});
		},
		minLength: 0,
		select: function( event, ui ) {
			$( "#jjsj" ).val(ui.item.id);
			$( "#samjjsj" ).val(ui.item.value);
			//return false;
		}
	})
		.data( "ui-autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li>" )
			.append( "<a style='font-size:12px;font-family: 微软雅黑;'>" + item.id + "," + item.value+ "</a>" )
			.appendTo( ul );
	};
});
/**
 * 初始化材块列表
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
			{ name: 'pieparts', index: 'pieparts',editable:true},//取材部位
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
			//canChange(rowid,1);
			var rec = jQuery("#new1").jqGrid('getRowData', rowid);
			if (rec.piestate > "0") {
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
	//var req_sts = $('#req_sts').val();
	var req_sts = $("input[name='req_sts']:checked").val();//包埋状态
	jQuery("#new").jqGrid("clearGridData");
	jQuery("#new").jqGrid('setGridParam',{
		url: "../pathologysample/pieces/ajax/sample",
		//发送数据
		postData : {"req_code":req_code,"patient_name":patient_name,"send_hosptail":send_hosptail,"req_bf_time":req_bf_time,
			"req_af_time":req_af_time,"send_dept":send_dept,"send_doctor":send_doctor,"req_sts":req_sts},
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
	getSamplePicures(rowData.sampleid);
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
			$("#samjjsj").val(data.samjjsj);
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
		pieparts:"",
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

function searchDoctor(id) {
	var ids = $("#new1").jqGrid('getDataIDs');
	$.each(ids, function (key, val) {
		var rowData = $("#new1").jqGrid("getRowData", ids[key]);
		if(rowData.pieceid == null || rowData.pieceid == ""){
			if(id == "1"){
				jQuery("#new1").jqGrid('setRowData', ids[key], {piedoctorid:$("#doctor_id").val()});
				jQuery("#new1").jqGrid('setRowData', ids[key], {piedoctorname:$("#doctor_id").find("option:selected").text()});
			}else if(id == "2"){
				jQuery("#new1").jqGrid('setRowData', ids[key], {pierecorderid:$("#input_user").val()});
				jQuery("#new1").jqGrid('setRowData', ids[key], {pierecordername:$("#input_user").find("option:selected").text()});
			}
		}
	});
}

function saveAsTemplate(v, obj) {
	$('#temkey').val('');
	$('#tempinyin').val('');
	$('#temfivestroke').val('');
	$('#temspellcode').val('');
	$('#temsort').val('');
	$("#FN").val('0');$("#SN").val('0');$("#TN").val('0');
	$("#temcontent").val(document.getElementById(obj).value);
	layer.open({
		type: 1,
		area: ['800px','500px'],
		fix: false, //不固定
		maxmin: true,
		shade:0.5,
		title: "模板",
		content: $('#templateForm'),
		btn:["确定","取消"],
		yes: function(index, layero){
			$.post('../template/edit', {
				temcustomerid:$('#samcustomerid').val(),//客户代码
				tempathologyid:$('#sampathologyid').val(),//病种id
				temtype:$("#owner").is(':checked')==true?1:0,//模板类型(0系统公用,1用户私有)
				temclass:0,//模板类型(0大体所见模板，1病理所见模板，2病理诊断模板)
				temownerid:$("#owner").is(':checked')==true?$("#local_userid").val():"9999999999",//归属用户
				temownername:$("#owner").is(':checked')==true?$("#local_username").val():"System",//归属用户名称
				temcontent:$('#temcontent').val(),//模板内容
				temkey:$('#temkey').val(),//关键字
				tempinyin:$('#tempinyin').val(),//关键字拼音码
				temfivestroke:$('#temfivestroke').val(),//关键字五笔码
				temspellcode:$('#temspellcode').val(),//简码
				temsort:"A"+$("#FN").val()+$("#SN").val()+$("#TN").val(),//排序号
				temusetimes:0//使用次数
			},function(data){
				layer.close(index);
			});
		}
	})

}
/**
 * 图像采集
 * @returns {boolean}
 */
function takingPicture() {
	var id = nowrow;
	if (id == null || id.length == 0) {
		layer.msg('请先选择标本', {icon: 2, time: 1000});
		return false;
	}
	layer.open({
		type: 2,
		title: '标本处理>取材管理>图像采集',
		shadeClose: false,
		shade: 0.5,
		closeBtn:false,
		maxmin: false, //开启最大化最小化按钮
		area: ['320px', '360px'],
		content: ["../diagnosis/camera"],
		btn:["关闭"],
		yes: function(index, layero){
			//swfobject.removeSWF("Main");
			layer.close(index); //如果设定了yes回调，需进行手工关闭
			// console.log(layero)
		}
	});
}

function createImgElement(src) {
	var ret = jQuery.parseJSON(src);
	var container = $("#imgContainer");
	var c = ret.continuous;
	if (c == "false") {
		container.empty();
	}
	var objNewDiv = $('<div>', {'id': 'mydiv', 'style': 'padding-bottom:5px'});
	objNewDiv.html("<img src='" + ret.src + "' width='220' onclick='removePicture(\"" + ret.name + "\")' height='150'>");
	container.append(objNewDiv);
}

function getSamplePicures(sampleId) {
	$.get("../diagnosis/pictures", {sampleid: sampleId, picpictureclass:PIC_TAKING_FROM}, function (data) {
		var ret = data;
		var container = $("#imgContainer");
		container.empty();
		for (var item in ret) {
			var objNewDiv = $('<div>', {'id': new Date().getTime(), 'style': 'padding-bottom:5px'});
			objNewDiv.html("<img src='" + ret[item] + "' name='" + item + "' onclick='removePicture(\"" + item + "\")' width='220' height='150'>");
			container.append(objNewDiv);
		}
	});
}

/**
 * 计费调整
 */
function hisChange() {
	//alert(addstates);
	var sampleid = $("#sampleid").val();
	if(sampleid == null || sampleid == ""){
		layer.msg("该病理标本未登记，无法进行计费调整!", {icon:2, time: 1000});
		return;
	}else{
		jQuery("#sectionList3").jqGrid('setGridParam',{
			url: "../pathologysample/sample/ajax/fee",
			//发送数据
			postData:{"feesampleid":sampleid,"feesource":"0"}
		}).trigger('reloadGrid');//重新载入
		// $("#sectionList3").jqGrid({
		// 	caption: "&nbsp;&nbsp;计费管理",
		// 	url: "../pathologysample/sample/ajax/fee",
		// 	mtype: "GET",
		// 	datatype: "json",
		// 	postData:{"feesampleid":sampleid,"feesource":"0"},
		// 	width:900,
		// 	height:600,
		// 	colNames: ['id','收费项目', '单价','数量','金额','状态','记录人','记录时间','发送人','发送时间','客户id','标本号','病种id','病理编号','费用来源',
		// 		'费用状态','统计类别','中文名称','英文名称','his项目id','his项目名称','his单价','计费人员id','发送人员id'],
		// 	colModel: [
		// 		{ name: 'feeid', index: 'feeid',hidden: true },//收费id
		// 		{ name: 'feenamech', index: 'feenamech',editable:true,editoptions: {dataInit: function (elem) {myAutocomplete(elem);}},
		// 			// edittype: "select",formatter: "select",editoptions:{value:getfeelist(), dataEvents: [
		// 			// {type: 'change',fn: function(e) {
		// 			// 	jQuery("#new1").jqGrid('setRowData', $(this).parent().parent().attr('id'), {reqmmaterialname:$(this).find("option:selected").text()});
		// 			// 	jQuery("#new1").jqGrid('setRowData', $(this).parent().parent().attr('id'), {feeitemid:$(this).val()});
		// 			// }}]},
		// 			width: 80},//收费项目
		// 		{ name: 'feeprince', index: 'feeprince', width: 60},//单价
		// 		{ name: 'feeamount', index: 'feeamount',editable:true, width: 60,editoptions: {
		// 			dataEvents: [
		// 				{type: 'change',fn: function(e) {
		// 					var rowdata = jQuery("#sectionList3").jqGrid('getRowData', $(this).parent().parent().attr('id'));
		// 					jQuery("#sectionList3").jqGrid('setRowData', $(this).parent().parent().attr('id'), {feecost:$(this).val()*rowdata.feeprince});
		// 				}}]
		// 			}},//数量
		// 		{ name: 'feecost', index: 'feecost', width: 60},//金额
		// 		{ name: 'feestate', index: 'feestate',formatter: "select", editoptions:{value:"0:已保存;1:已计费;2:已发送;3:发送失败"}, width: 60},//状态
		// 		{ name: 'feeusername', index: 'feeusername', width: 60},//记录人
		// 		{ name: 'feetime', index: 'feetime', width: 60,formatter:function(cellvalue, options, row){if(cellvalue == null || cellvalue == "")
		// 			return "";
		// 			return CurentTime(new Date(cellvalue));}},//记录时间
		// 		{ name: 'feesendusername', index: 'feesendusername', width: 60},//发送人
		// 		{ name: 'feesendtime', index: 'feesendtime', width: 60,formatter:function(cellvalue, options, row){if(cellvalue == null || cellvalue == "")
		// 			return "";
		// 			return CurentTime(new Date(cellvalue));}},//发送时间
		// 		{ name: 'feecustomerid', index: 'feecustomerid',hidden: true },//客户id
		// 		{ name: 'feesampleid', index: 'feesampleid',hidden: true },//标本号
		// 		{ name: 'feepathologyid', index: 'feepathologyid',hidden: true },//病种id
		// 		{ name: 'feepathologycode', index: 'feepathologycode',hidden: true },//病理编号
		// 		{ name: 'feesource', index: 'feesource',hidden: true },//费用来源
		// 		{ name: 'feestate', index: 'feestate',hidden: true },//费用状态
		// 		{ name: 'feecategory', index: 'feecategory',hidden: true },//统计类别
		// 		{ name: 'feeitemid', index: 'feeitemid',hidden: true },//中文名称
		// 		{ name: 'feenameen', index: 'feenameen',hidden: true },//英文名称
		// 		{ name: 'feehisitemid', index: 'feehisitemid',hidden: true },//his项目id
		// 		{ name: 'feehisname', index: 'feehisname',hidden: true },//his项目名称
		// 		{ name: 'feehisprice', index: 'feehisprice',hidden: true },//his单价
		// 		{ name: 'feeuserid', index: 'feeuserid',hidden: true },//计费人员id
		// 		{ name: 'feesenduserid', index: 'feesenduserid',hidden: true },//发送人员id
		// 	],
		// 	loadComplete : function() {
		// 		var table = this;
		// 		setTimeout(function(){
		// 			updatePagerIcons(table);
		// 		}, 0);
		// 	},
		//    beforeEditCell: function (rowid, cellname, value, iRow, iCol) {
		//        var rec = jQuery("#sectionList3").jqGrid('getRowData', rowid);
		//        if (rec.feestate == "1" || rec.feestate == "2") {
		//            setTimeout(function () {
		//                jQuery("#sectionList3").jqGrid('restoreCell', iRow, iCol);
		//                //===>或者设置为只读
		//                //$('#' + rowid + '_amount').attr('readonly', true);
		//            }, 1);
		//        }
		//    },
		// 	ondblClickRow: function (id) {
		// 	},
		// 	multiselect: true,
		// 	viewrecords: true,
		// 	cellsubmit: "clientArray",
		// 	cellEdit:true,
		// 	shrinkToFit: true,
		// 	altRows:true,
		// 	height: 'auto',
		// 	// rowNum: 10,
		// 	// rowList:[10,20,30],
		// 	// rownumbers: true, // 显示行号
		// 	// rownumWidth: 35, // the width of the row numbers columns
		// 	// pager: "#pager3",
		// 	onSelectRow: function(id){
		//
		// 	}
		// });
		layer.open({
			type: 1,
			area: ['900px','600px'],
			fix: false, //不固定
			maxmin: true,
			multiselect: true,
			rownumbers : true,
			shade:0.5,
			title: "计费",
			content: $('#userGrid')
		})
	}

}
/**
 * 计费调整界面模糊匹配功能
 * @param elem
 */
function myAutocomplete(elem) {
	//收费项目
	$(elem).autocomplete({
		source: function( request, response ) {
			$.ajax({
				url: "../chargeitem/info",
				dataType: "json",
				data: {
					// name : request.term,//名称
					// bddatatype:4,//送检医院
					// bdcustomerid:$("#lcal_hosptail").val()//账号所属医院
				},
				success: function( data ) {
					response( $.map( data, function( result ) {
						return {
							label: result.feenamech + " : " + result.feenameen,
							value: result.feenamech,//中文名称
							id : result.feeitemid,//收费项目ID
							feenameen : result.feenameen,//英文名称
							feeprince : result.feeprince,//单价
							feecategory : result.feecategory,//所属统计类
							feehisitemid : result.feehisitemid,//his项目id
							feehisname : result.feehisname,//his项目名称
							feehisprice : result.feehisprice//his单价
						}
					}));
				}
			});
		},
		minLength: 0,
		select: function( event, ui ) {
			jQuery("#sectionList3").jqGrid('setRowData', $(elem).parent().parent().attr("id"), {feeitemid:ui.item.id});//收费项目ID
			jQuery("#sectionList3").jqGrid('setRowData', $(elem).parent().parent().attr("id"), {feenamech:ui.item.name});//中文名称
			jQuery("#sectionList3").jqGrid('setRowData', $(elem).parent().parent().attr("id"), {feenameen:ui.item.feenameen});//英文名称
			jQuery("#sectionList3").jqGrid('setRowData', $(elem).parent().parent().attr("id"), {feeprince:ui.item.feeprince});//单价
			jQuery("#sectionList3").jqGrid('setRowData', $(elem).parent().parent().attr("id"), {feecategory:ui.item.feecategory});//所属统计类
			jQuery("#sectionList3").jqGrid('setRowData', $(elem).parent().parent().attr("id"), {feehisitemid:ui.item.feehisitemid});//his项目id
			jQuery("#sectionList3").jqGrid('setRowData', $(elem).parent().parent().attr("id"), {feehisname:ui.item.feehisname});//his项目名称
			jQuery("#sectionList3").jqGrid('setRowData', $(elem).parent().parent().attr("id"), {feehisprice:ui.item.feehisprice});//his单价
			jQuery("#sectionList3").jqGrid('setRowData', $(elem).parent().parent().attr("id"), {feeamount:""});//数量
			jQuery("#sectionList3").jqGrid('setRowData', $(elem).parent().parent().attr("id"), {feecost:""});//金额
		}
	})
		.data( "ui-autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li>" )
			.append( "<a style='font-size:12px;font-family: 微软雅黑;'>" + item.id + "," + item.value+ "</a>" )
			.appendTo( ul );
	};
}
/**
 * 增加行
 */
function addRow1(){
	var selectedId = $("#sectionList3").jqGrid("getGridParam", "selrow");
	var dataRow = {
		feeid:"",//收费id
		feeitemid:"",//收费项目
		feeprince:"",//单价
		feeamount:"",//数量
		feecost:"",//金额
		feestate:"0",//状态
		feeusername:$("#local_username").val(),//记录人
		feetime:new Date(),//记录时间
		feesendusername:"",//发送人
		feesendtime:"",//发送时间
		feecustomerid:$("#samcustomerid").val(),//客户id
		feesampleid:$("#sampleid").val(),//标本号
		feepathologyid:$("#sampathologyid").val(),//病种id
		feepathologycode:$("#sampathologycode").val(),//病理编号
		feesource:"2",//费用来源
		feestate:"0",//费用状态
		feecategory:"",//统计类别
		feenamech:"",//中文名称
		feenameen:"",//英文名称
		feehisitemid:"",//his项目id
		feehisname:"",//his项目名称
		feehisprice:"",//his单价
		feeuserid:$("#local_userid").val(),//计费人员id
		feesenduserid:"",//发送人员id
	};
	var ids = $("#sectionList3").jqGrid('getDataIDs');
	var rowid = 1;
	if(Math.max.apply(Math,ids) > ids.length ){
		rowid = Math.max.apply(Math,ids) + 1;
	}else{
		rowid = ids.length + 1;
	}
	if (selectedId) {
		$("#sectionList3").jqGrid("addRowData", rowid, dataRow, "after", selectedId);
	} else {
		$("#sectionList3").jqGrid("addRowData", rowid, dataRow, "last");
	}
	//$('#plsfList').jqGrid('editRow', rowid, false);
}
/**
 * 删除行
 */
function delRow1(){
	// var selectedId = $("#sectionList3").jqGrid("getGridParam","selarrrow");
	// if(selectedIds == null || selectedIds == ""){
	// 	alert("请选择要删除的行!");
	// 	return;
	// }else{
	// 	$("#sectionList3").jqGrid("delRowData", selectedId);
	// }
	var selectedIds = $("#sectionList3").jqGrid("getGridParam","selarrrow");
	if(selectedIds == null || selectedIds == ""){
		layer.msg("请选择要删除的行!", {icon:2, time: 1000});
		return;
	}else{
		$(selectedIds).each(function () {
				var delrow = this.toString();
				var rowData = $("#sectionList3").jqGrid('getRowData',delrow);
				if(rowData.feestate == "0" || rowData.feestate == "3"){
					$("#sectionList3").jqGrid("delRowData", delrow);
				}else if(rowData.feestate == "1" || rowData.feestate == "2") {
					layer.msg("已计费或已发送数据无法删除!", {icon:2, time: 1000});
					return;
				}
			}
		);
	}
}
/**
 * 保存计费信息
 */
function savefeeRow(states) {
	var rowdatas = $('#sectionList3').jqGrid('getRowData');
	$.post("../pathologysample/sample/savefee", {
			fees:JSON.stringify(rowdatas),
			states:states
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
/**
 * 初始化计费调整
 */
function createNew2() {
	var sampleid = $("#sampleid").val();
	$("#sectionList3").jqGrid({
		caption: "&nbsp;&nbsp;计费管理",
		url: "../pathologysample/sample/ajax/fee",
		mtype: "GET",
		datatype: "json",
		postData:{"feesampleid":sampleid,"feesource":"0"},
		width:900,
		height:600,
		colNames: ['id','收费项目', '单价','数量','金额','状态','记录人','记录时间','发送人','发送时间','客户id','标本号','病种id','病理编号','费用来源',
			'费用状态','统计类别','中文名称','英文名称','his项目id','his项目名称','his单价','计费人员id','发送人员id'],
		colModel: [
			{ name: 'feeid', index: 'feeid',hidden: true },//收费id
			{ name: 'feenamech', index: 'feenamech',editable:true,editoptions: {dataInit: function (elem) {myAutocomplete(elem);}, align: "center"},
				// edittype: "select",formatter: "select",editoptions:{value:getfeelist(), dataEvents: [
				// {type: 'change',fn: function(e) {
				// 	jQuery("#new1").jqGrid('setRowData', $(this).parent().parent().attr('id'), {reqmmaterialname:$(this).find("option:selected").text()});
				// 	jQuery("#new1").jqGrid('setRowData', $(this).parent().parent().attr('id'), {feeitemid:$(this).val()});
				// }}]},
				width: 80},//收费项目
			{ name: 'feeprince', index: 'feeprince', width: 60, align: "center"},//单价
			{ name: 'feeamount', index: 'feeamount',editable:true, width: 60,editoptions: {
				dataEvents: [
					{type: 'change',fn: function(e) {
						var rowdata = jQuery("#sectionList3").jqGrid('getRowData', $(this).parent().parent().attr('id'));
						jQuery("#sectionList3").jqGrid('setRowData', $(this).parent().parent().attr('id'), {feecost:$(this).val()*rowdata.feeprince});
					}}]
			}, align: "center"},//数量
			{ name: 'feecost', index: 'feecost', width: 60, align: "center"},//金额
			{ name: 'feestate', index: 'feestate',formatter: "select", editoptions:{value:"0:已保存;1:已计费;2:已发送;3:发送失败"}, width: 60, align: "center"},//状态
			{ name: 'feeusername', index: 'feeusername', width: 60, align: "center"},//记录人
			{ name: 'feetime', index: 'feetime', width: 100,formatter:function(cellvalue, options, row){if(cellvalue == null || cellvalue == "")
				return "";
				return CurentTime(new Date(cellvalue));}},//记录时间
			{ name: 'feesendusername', index: 'feesendusername', width: 60},//发送人
			{ name: 'feesendtime', index: 'feesendtime', width: 100,formatter:function(cellvalue, options, row){if(cellvalue == null || cellvalue == "")
				return "";
				return CurentTime(new Date(cellvalue));}, align: "center"},//发送时间
			{ name: 'feecustomerid', index: 'feecustomerid',hidden: true },//客户id
			{ name: 'feesampleid', index: 'feesampleid',hidden: true },//标本号
			{ name: 'feepathologyid', index: 'feepathologyid',hidden: true },//病种id
			{ name: 'feepathologycode', index: 'feepathologycode',hidden: true },//病理编号
			{ name: 'feesource', index: 'feesource',hidden: true },//费用来源
			{ name: 'feestate', index: 'feestate',hidden: true },//费用状态
			{ name: 'feecategory', index: 'feecategory',hidden: true },//统计类别
			{ name: 'feeitemid', index: 'feeitemid',hidden: true },//中文名称
			{ name: 'feenameen', index: 'feenameen',hidden: true },//英文名称
			{ name: 'feehisitemid', index: 'feehisitemid',hidden: true },//his项目id
			{ name: 'feehisname', index: 'feehisname',hidden: true },//his项目名称
			{ name: 'feehisprice', index: 'feehisprice',hidden: true },//his单价
			{ name: 'feeuserid', index: 'feeuserid',hidden: true },//计费人员id
			{ name: 'feesenduserid', index: 'feesenduserid',hidden: true },//发送人员id
		],
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		beforeEditCell: function (rowid, cellname, value, iRow, iCol) {
			var rec = jQuery("#sectionList3").jqGrid('getRowData', rowid);
			if (rec.feestate == "1" || rec.feestate == "2") {
				setTimeout(function () {
					jQuery("#sectionList3").jqGrid('restoreCell', iRow, iCol);
					//===>或者设置为只读
					//$('#' + rowid + '_amount').attr('readonly', true);
				}, 1);
			}
		},
		ondblClickRow: function (id) {
		},
		multiselect: true,
		viewrecords: true,
		cellsubmit: "clientArray",
		cellEdit:true,
		shrinkToFit: true,
		altRows:true,
		height: 'auto',
		// rowNum: 10,
		// rowList:[10,20,30],
		// rownumbers: true, // 显示行号
		// rownumWidth: 35, // the width of the row numbers columns
		// pager: "#pager3",
		onSelectRow: function(id){

		}
	});
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
		startPrint(rowData);
	});
	// $.ajax({
	// 	type: "POST",
	// 	async: false,
	// 	url: "../nursestation/inexecute/printRequestList",
	// 	dataType: "json",
	// 	contentType: "application/json",
	// 	data: JSON.stringify(saveDatas),
	// 	success: function (data) {
	// 		var printDatas = data.printOrders
	// 		var noPrintDatas = data.noPrintOrders;
	// 		for (i = 0; i < printDatas.length; i++) {
	// 			startPrint(printDatas[i]);
	// 		}
	// 		for (i = 0; i < noPrintDatas.length; i++) {
	//
	// 		}
	// 	}
	//
	// });
	//刷新当前节点数据
	// var zTree = $.fn.zTree.getZTreeObj("tree");
	// var nodes = zTree.getSelectedNodes();
	// if (nodes.length > 0) {
	// 	zTree.selectNode(nodes[0]);
	// 	zTree.setting.callback.onClick(null, zTree.setting.treeId, nodes[0]);//调用事件
	// }
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
function CreateDataBill(data) {
	if(data && data!=null){
		var sex = "";
		if(data.sampatientsex == '0'){sex = '男'}else if(data.sampatientsex == '1'){sex = '女'}else{sex = '未知'}
		var ageUnit = "";
		if(data.sampatientagetype == '1'){
			ageUnit = "岁";
		}else if(data.sampatientagetype == '2'){
			ageUnit = "月";
		}else if(data.sampatientagetype == '4'){
			ageUnit = "周";
		}else if(data.sampatientagetype == '5'){
			ageUnit = "日";
		}else if(data.sampatientagetype == '6'){
			ageUnit = "小时";
		}
		LODOP = getLodop();
		LODOP.PRINT_INIT("");
		LODOP.SET_PRINT_PAGESIZE(0,520,400,"A4");
		// LODOP.ADD_PRINT_IMAGE(10,10,80,80,"<img src='../images/shulan.png' style='width:80px;'/>");
		LODOP.ADD_PRINT_TEXT(10,100,230,35,"树兰（杭州）医院");
		LODOP.SET_PRINT_STYLEA(0,"FontSize",20);
		LODOP.ADD_PRINT_TEXT(45,100,230,35,"浙江大学国际医院");
		LODOP.SET_PRINT_STYLEA(0,"FontSize",20);
		LODOP.ADD_PRINT_BARCODEA("patientCode","21.98mm","27.01mm","46.57mm",40,"128B",data.sampathologycode);
		LODOP.SET_PRINT_STYLEA(0,"Horient",2);
		LODOP.ADD_PRINT_TEXTA("nameText","33.00mm","12.46mm",45,20,"姓名：");
		LODOP.ADD_PRINT_TEXTA("name","33.00mm","23.31mm",90,20,data.sampatientname);
		LODOP.SET_PRINT_STYLEA(0,"Bold",1);
		LODOP.ADD_PRINT_TEXTA("sexText","33.00mm","46.86mm",45,20,"性别：");
		LODOP.ADD_PRINT_TEXTA("sex","33.00mm","58.5mm",30,20,sex);
		LODOP.SET_PRINT_STYLEA(0,"Bold",1);
		LODOP.ADD_PRINT_TEXTA("ageText","33.00mm","65.91mm",45,20,"年龄：");
		LODOP.ADD_PRINT_TEXTA("age","33.00mm","77.55mm",40,20,data.sampatientage + ageUnit);
		LODOP.SET_PRINT_STYLEA(0,"Bold",1);
		LODOP.ADD_PRINT_TEXTA("examText","38.00mm","5.85mm",70,20,"临床诊断：");
		LODOP.ADD_PRINT_TEXTA("exam","38.00mm","23.31mm",300,20,data.sampatientdignoses);
		LODOP.SET_PRINT_STYLEA(0,"Bold",1);
		LODOP.ADD_PRINT_TEXTA("requestTimeText","43.00mm","5.85mm",70,20,"申请时间：");
		LODOP.ADD_PRINT_TEXTA("requestTime","43.00mm","23.31mm",300,20,data.samreqtime);
		LODOP.ADD_PRINT_TEXTA("requesterText","48.00mm","5.85mm",70,20,"送检时间：");
		LODOP.ADD_PRINT_TEXTA("requester","48.00mm","23.31mm",300,20,data.samsendtime);
		LODOP.ADD_PRINT_TEXTA("executeTimeText","53.00mm","5.85mm",70,20,"登记时间：");
		LODOP.ADD_PRINT_TEXTA("executeTime","53.00mm","23.31mm",300,20,data.samregisttime);

	}
}
function startPrint(data) {
	CreateDataBill(data);
	//开始打印
	LODOP.PRINT();
//LODOP.PREVIEW();
}



