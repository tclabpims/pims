var hrefval = "";
var nowrow = "";
var GRID_SELECTED_ROW_SAMPLEID = "";
var GRID_SELECTED_ROW_SAMPCUSTOMERID = "";
var PIC_TAKING_FROM = 1;
var LASTEDITROW1 = "";
var LASTEDITCELL1 = "";
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
	$("#new1").jqGrid("saveCell",LASTEDITROW1,LASTEDITCELL1);
	if($("#doctor_id").val() == $("#input_user").val()){
		layer.msg("取材医生与录入人员不允许为同一人!", {icon: 2, time: 1000});
		return;
	}
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
					if(num == 0){
						layer.msg("保存成功!", {icon: 1, time: 1000});
					}else{
						layer.msg(data.message, {icon: 1, time: 1000});
					}
					searchList();
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
	// $("#samissamplingall").attr("checked",false);
	// $("#samisdecacified").attr("checked",false);
	$("#samissamplingall").removeAttr("checked");
	$("#samisdecacified").removeAttr("checked");
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
	var send_doctor = "";//补医嘱
	if($("#send_doctor").is(":checked")){
		send_doctor = $('#send_doctor').val();//补医嘱
	}
	var req_sts = $('#req_sts').val();//取材状态
	//var req_sts = $("input[name='req_sts']:checked").val();//包埋状态
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
			'录入员', '取材时间','特殊要求', '取材状态','标记物','是否脱水','脱水时间','是否包埋','包埋时间','包埋人员id','包埋人员姓名','所属蜡块id','补取医嘱'],
		colModel: [
			{name:'piecode',hidden:true},//材块条码编号
			{name:'piesampleid',hidden:true},//客户ID
			{name:'pieceid',hidden:true},//材块ID
			{name:'pieunit',hidden:true},//取材单位
			{ name: 'piepathologycode', index: 'piepathologycode',align:"center",width:100},//病理号
			{ name: 'piesamplingno', index: 'piesamplingno',align:"center",width:70},//取材序号
			{ name: 'piecounts', index: 'piecounts',editable:true,editrules: {edithidden:true,required:true,number:true,minValue:1,maxValue:100},align:"center",width:70},//材块数
			{ name: 'pienullslidenum', index: 'pienullslidenum',editable:true,editrules: {edithidden:true,required:true,number:true,minValue:0,maxValue:100},align:"center",width:70},//白片数
			{ name: 'pieparts', index: 'pieparts',editable:true,align:"center",width:100},//取材部位
			{ name: 'piedoctorid', hidden:true},//取材医生ID
			{ name: 'piedoctorname', index: 'piedoctorname',align:"center",width:100},//取材医生
			{ name: 'pierecorderid', hidden:true},//录入员ID
			{ name: 'pierecordername', index: 'pierecordername',align:"center",width:100},//录入员
			{ name: 'piesamplingtime', index: 'piesamplingtime',formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))},align:"center",width:100},//取材时间
			{ name: 'piespecial', index: 'piespecial',editable:true,align:"center",width:100},//特殊要求
			{ name: 'piestate', index: 'piestate',formatter: "select", editoptions:{value:"0:未取材;1:已取材;2:已包埋;3:已切片;4:已初诊;5:已审核"},align:"center",width:100},//取材状态
			{name:'piesign',hidden:true},//标记物
			{name:'pieisdeprivation',hidden:true},//是否脱水
			{name:'piedeprivationtime',hidden:true,formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))}},//脱水时间
			{name:'pieisembed',hidden:true},//是否包埋
			{name:'pieembedtime',hidden:true,formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))}},//包埋时间
			{name:'pieembeddoctorid',hidden:true},//包埋人员id
			{name:'pieembeddoctorname',hidden:true},//包埋人员姓名
			{name:'pieparaffinid',hidden:true},//所属蜡块id
			{name:'piefirstn',hidden:true}//补取医嘱
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
		shrinkToFit:false,
		autoScroll: true,
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
	// var send_doctor = $('#send_doctor').val();
	var send_doctor = "";//补医嘱
	if($("#send_doctor").is(":checked")){
		send_doctor = $('#send_doctor').val();//补医嘱
	}
	var req_sts = $('#req_sts').val();
	// var req_sts = $("input[name='req_sts']:checked").val();//包埋状态
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
				$("#samisdecacified").prop("checked",true);
			}
			if(samissamplingall == 1){
				$("#samissamplingall").prop("checked",true);
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
	var jjinfo = "";
	var ids = $("#new1").jqGrid('getDataIDs');
	var maxId = 1;
	if(ids == null || ids == ""){
	}else{
		var rowData = $("#new1").jqGrid('getRowData',Math.max.apply(Math,ids));
		jjinfo = rowData.pieparts;
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
		// pieparts:jjinfo,
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
		pieparaffinid:"",//所属蜡块id
		piefirstn:""//补取医嘱
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
				$("#new1").jqGrid("delRowData", maxId);
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
					$("#new1").jqGrid("delRowData", delrow);
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

function removePicture(pictureName) {
	var rowData = $("#new").jqGrid('getRowData',nowrow);
	layer.msg('需要删除图片吗？', {
		time: 3000, //自动关闭
		btn: ['是', '否'],
		yes: function (index) {
			$.get("../diagnosis/removePicture", {
				name: pictureName,
				sampleid: rowData.sampleid
			}, function (data) {
				var es = document.getElementsByName(pictureName);
				var pn = es[0].parentNode;
				pn.parentNode.removeChild(pn);
			});
			layer.close(index);
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
		//startPrint(rowData);
	});
	$.post("../pathologysample/pieces/printcode",{samples:JSON.stringify(saveDatas)},function(data){
		data = jQuery.parseJSON(data);
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
function CreateDataBill(data) {
	if(data && data!=null){
		var divhtml = "<table border=\"1\" style=\"font-size:12px;border-collapse:collapse;border:solid 1px\" bordercolor=\"#000000\">";
		divhtml +="<tr>" +
					"<td width='100px'>病理编号</td>" +
					"<td width='70px'>是否全取</td>" +
					"<td width='70px'>是否脱钙</td>" +
					"<td width='100px'>巨检所见</td>" +
					"<td width='50px'>取材序号</td>" +
					// "<td>材块数</td>" +
					"<td width='50px'>白片数</td>" +
					"<td width='70px'>取材部位</td>" +
					"<td width='70px'>取材医生</td>" +
					// "<td>录入员</td>" +
					"<td width='100px'>取材时间</td>" +
					"<td width='70px'>特殊要求</td>" +
				"</tr>";
		for(i=0;i<data.labOrders.length;i++){
			var datas = data.labOrders[i];
			var samjjsj =  (datas.samjjsj==undefined || datas.samjjsj==null || datas.samjjsj=='null')?"":datas.samjjsj;
			var piespecial =  (datas.piespecial==undefined || datas.piespecial==null || datas.samjjsj=='null')?"":datas.piespecial;
			var pieparts = (datas.pieparts==undefined || datas.pieparts==null || datas.samjjsj=='null')?"":datas.pieparts;
			divhtml +="<tr>" +
						"<td>"+datas.piepathologycode+"</td>" +
						"<td>"+datas.samissamplingall+"</td>" +
						"<td>"+datas.samisdecacified+"</td>" +
						"<td>"+samjjsj+"</td>" +
						"<td>"+datas.piesamplingno+"</td>" +
						// "<td>"+datas.piecounts+"</td>" +
						"<td>"+datas.pienullslidenum+"</td>" +
						"<td>"+pieparts+"</td>" +
						"<td>"+datas.piedoctorname+"</td>" +
						// "<td>"+datas.pierecordername+"</td>" +
						"<td>"+datas.piesamplingtime+"</td>" +
						"<td>"+piespecial+"</td>" +
				     "</tr>";
		}
		divhtml += "</table>";
		LODOP = getLodop();
		LODOP.PRINT_INIT("标本打印");
		LODOP.SET_PRINT_PAGESIZE(1, "210mm", "297mm", "A4") ; //A4纸张纵向打印
		LODOP.SET_PRINT_STYLE("FontSize", 9);
		LODOP.ADD_PRINT_HTM("10mm","10mm","210mm","297mm",divhtml);

	}
}
function startPrint(data) {
	CreateDataBill(data);
	//开始打印
	// LODOP.PRINT();
	LODOP.PREVIEW();
}



