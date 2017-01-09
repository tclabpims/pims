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
function changeimgclick(num) {//1切片 取消包埋
	if(num == 1){
		nowrow = 3;
		if (typeof document.addEventListener == "undefined") {
			document.getElementById("saveButton").detachEvent("onclick",saveInfo);
			document.getElementById("saveButton").attachEvent("onclick",saveInfo);
			document.getElementById("resetbutton").detachEvent("onclick",saveInfo);
			document.getElementById("printslide").detachEvent("onclick",printCode);
		} else {
			document.getElementById("saveButton").removeEventListener("click",saveInfo,false);
			document.getElementById("saveButton").addEventListener("click",saveInfo,false);
			document.getElementById("resetbutton").removeEventListener("click",saveInfo,false);
			document.getElementById("printslide").removeEventListener("onclick",printCode,false);
		}
		$("#saveButton").css("cursor","pointer");
		$("#resetbutton").css("cursor","default");
		$("#printslide").css("cursor","default");
	}else{
		nowrow = 2;
		if (typeof document.addEventListener == "undefined") {
			document.getElementById("saveButton").detachEvent("onclick",saveInfo);
			document.getElementById("resetbutton").detachEvent("onclick",saveInfo);
			document.getElementById("resetbutton").attachEvent("onclick",saveInfo);
			document.getElementById("printslide").detachEvent("onclick",printCode);
			document.getElementById("printslide").attachEvent("onclick",printCode);
		} else {
			document.getElementById("saveButton").removeEventListener("click",saveInfo,false);
			document.getElementById("resetbutton").removeEventListener("click",saveInfo,false);
			document.getElementById("resetbutton").addEventListener("click",saveInfo,false);
			document.getElementById("printslide").removeEventListener("click",printCode,false);
			document.getElementById("printslide").addEventListener("click",printCode,false);
		}
		$("#saveButton").css("cursor","default");
		$("#resetbutton").css("cursor","pointer");
		$("#printslide").css("cursor","pointer");
	}
}
/**
 * 切片
 */
function saveInfo() {
	var num =nowrow;
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
		alert("未选择数据!");
		return;
	}
	var post = true;
	if(num == 2){
		post  = false;
	}
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
					// location.reload();
				} else {
					layer.msg(data.message, {icon:2, time: 1000});
				}
				searchList();
			});
	}else{
		layer.confirm('确定取消制片？', {icon: 2, title:'警告'}, function(index){
			$.post("../pathologysample/slide/editSample", {
					states:"1",
					savenum:num,
					samples:JSON.stringify(arr),
					slides:JSON.stringify(rowdatas)
				},
				function(data) {
					if(data.success) {
						layer.msg(data.message, {icon: 1, time: 1000});
						// location.reload();
					} else {
						layer.msg(data.message, {icon:2, time: 1000});
					}
					searchList();
				});
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
function searchSts(states){
	var req_sts = states;
	//var req_sts = $("#req_sts").val();
	if(req_sts == "1"){
		changeimgclick(2);
		// $("#saveButton").attr({"disabled":true});
		// $("#resetbutton").removeAttr("disabled");//将按钮可用
	}else{
		// $("#resetbutton").attr({"disabled":true});
		// $("#saveButton").removeAttr("disabled");//将按钮可用
		changeimgclick(1);
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
	// $("#resetbutton").attr({"disabled":true});
	changeimgclick(1);
	var clientHeight= $(window).innerHeight();
//	var height = $("#formDialog").height() - $('#search_div_1').height()+405;
    height = clientHeight- $("#div_1").height()-$("tabss").height()-$("#head").height() - $('#search_div_1').height()-24-35-42-50;
	if(height < 340){
        height = 340;
    }
	var width = $('#search_div_1').width()-5;
	var width1 = $("#sampleForm").width()-5;

	createNew1("",width1);
	//var logyid = $("#logyid").val();
	var logyid =$("#logyid").val();
	var patient_name = $('#patient_name').val();//病人姓名
	//var send_hosptail = $('#send_hosptail').val();
	var req_bf_time = $('#req_bf_time').val();
	var req_af_time = $('#req_af_time').val();
	var send_dept = $('#send_dept').val();//病理号
	var send_doctor = "";//内部医嘱
	if($("#send_hosptail1").is(':checked') && $("#send_hosptail2").is(':checked')){
		$('#send_hosptail').val("");
	}else if($("#send_hosptail1").is(':checked')){
		$('#send_hosptail').val("0");
	}else if($("#send_hosptail2").is(':checked')){
		$('#send_hosptail').val("1");
	}else{
		$('#send_hosptail').val("");
	}
	var send_hosptail = $('#send_hosptail').val();
	if($("#send_doctor").is(':checked')){
		req_code = "1";
	}
	var req_code = "";//预留白片
	if($("#req_code").is(':checked')){
		req_code = "1";
	}
	// var req_sts = $("input[name='req_sts']:checked").val();//包埋状态
	var req_sts = $('#req_sts').val();//切片状态
	$("#new").jqGrid({
		url: "../pathologysample/slide/ajax/sample",
		mtype: "GET",
		datatype: "json",
		postData:{"req_code":req_code,"patient_name":patient_name,"send_hosptail":send_hosptail,"req_bf_time":req_bf_time,
			"req_af_time":req_af_time,"send_dept":send_dept,"send_doctor":send_doctor,"req_sts":req_sts,"logyid":logyid},
		colNames: ['蜡块ID','标本id','病理状态','蜡块状态', '病理号','患者姓名','送检医生','送检科室','包埋医生', '包埋时间','切片医生', '切片时间','打印状态','白片数',
		'材块ID','材块编号','取材医生','取材时间','客户代码','客户代码','蜡块序号','取材部位','病种类别'],
		colModel: [
			{name:'paraffinid',hidden:true},
			{name:'sampleid',hidden:true},
			{name:'samsamplestatus',hidden:true},
			{ name: 'parissectioned', index: 'parissectioned',formatter: "select", editoptions:{value:"0:未切片;1:已切片"},width:100,align:"center"},
			{ name: 'sampathologycode', index: 'sampathologycode',width:100,align:"center"},
			{ name: 'sampatientname', index: 'sampatientname',width:100,align:"center"},
			{ name: 'samsenddoctorname', index: 'samsenddoctorname',width:100,align:"center"},
			{name:'samdeptname',index:'samdeptname',width:100,align:"center"},
			{name:'pieembeddoctorname',index:'pieembeddoctorname',width:100,align:"center"},
			{name:'pieembedtime',index:'pieembedtime',formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))},width:100,align:"center"},
			{name:'parsectioneddoctor',index:'parsectioneddoctor',width:100,align:"center"},
			{name:'parsectionedtime',index:'parsectionedtime',formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))},width:100,align:"center"},
			{name:'parisprintlabel',index:'parisprintlabel',width:100,align:"center"},
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
				//fillInfo(id);
		},
		onCellSelect:function(id){
			fillInfo(id);
		},
		onSelectAll:function(aRowids,status){
			// if(status){
			// 	fillInfo(0);   //选中
			// }else{
			// 	clearData();   //取消选中
			// }
		},
		multiselect: true,
		viewrecords: true,
		height:height,
		width: width,
		//autowidth: true,
		shrinkToFit:false,
		autoScroll: true,
		rowNum: 500,
		rowList:[500,1000,1500],
		rownumbers: true, // 显示行号
		rownumWidth: 35, // the width of the row numbers columns
		pager: "#pager"
	});
	$("#pager_left").remove();
});
/**
 * 初始化玻片列表
 * @param reqid
 */
function createNew1(reqid,width1){
    var clientHeight= $("html").innerHeight();
    height1 = height+$("#search_div_1").height()+35+$("#tabss").height()+24-206-51;
  	if(height1 < 340){
        height1 = 340;
    }
	$("#new1").jqGrid({
		url:"../pathologysample/slide/ajax/getItem",
		datatype: "json",
		mtype:"GET",
		height: height1,
		width:width1,
		postData:{"reqId":reqid},
		colNames: ['样本ID','材块ID','蜡块ID','蜡块ID','材块编号','玻片类型','玻片序号','取材医生','取材时间','包埋医生', '包埋时间','切片医生', '切片时间','切片状态','印刷状态','分类', '特检项目',
		'玻片id','客户代码','客户代码','玻片条码号','病理编号','玻片来源','玻片使用状态','玻片号','蜡块序号','取材部位','是否内部医嘱'],
		colModel: [
			{name:'slisampleid',hidden:true},//样本ID
			{name:'pieceid',hidden:true},//材块ID
			{name:'sliparaffinid',hidden:true},//蜡块ID
			{name:'paraffinid',hidden:true},//蜡块ID
			{name:'sliparaffincode',index:'sliparaffincode',align:"center"},//材块编号
			{name:'slislidetype',index:'slislidetype',formatter: "select", editoptions:{value:"0:常规;1:白片"},align:"center"},//玻片类型
			{name:'slislideno',index:'slislideno',align:"center"},//玻片序号
			{name:'piedoctorname',index:'piedoctorname',align:"center"},//取材医生
			{ name: 'piesamplingtime', index: 'piesamplingtime',formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))},width:100,align:"center"},//取材时间
			{ name: 'pieembeddoctorname', index: 'pieembeddoctorname',width:100,align:"center"},//包埋医生
			{ name: 'pieembedtime', index: 'pieembedtime',formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))},width:100,align:"center"},//包埋时间
			{ name: 'parsectioneddoctor', index: 'parsectioneddoctor',width:100,align:"center"},//切片医生
			{ name: 'parsectionedtime', index: 'parsectionedtime',formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))},width:100,align:"center"},//切片时间
			{ name: 'parissectioned', index: 'parissectioned',formatter: "select", editoptions:{value:"0:未切片;1:已切片"},width:100,align:"center"},//切片状态
			{ name: 'sliifprint', index: 'sliifprint',formatter: "select", editoptions:{value:"0:未打印;1:已打印"},width:100,align:"center"},//印刷状态
			{ name: 'sampathologyid', index: 'sampathologyid',formatter: "select", editoptions:{value:gettypes()},width:100,align:"center"},//分类
			{ name: 'slitestitemname', index: 'slitestitemname',width:100,align:"center"},//特检项目
			{name:'slideid',hidden:true},//玻片id
			{name:'slicustomerid',hidden:true},//客户代码
			{name:'slicustomercode',hidden:true},//客户代码
			{name:'slislidebarcode',hidden:true},//玻片条码号
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
			canChange(rowid,1);
		},
		shrinkToFit:false,
		autoScroll: true,
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

function gettypes(){
	//动态生成select内容
	var str="";
	$.ajax({
		type:"post",
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
/**
 * 查询数据
 */
function searchList() {
	clearData();
	var patient_name = $('#patient_name').val();
	if($("#send_hosptail1").is(':checked') && $("#send_hosptail2").is(':checked')){
		$('#send_hosptail').val("");
	}else if($("#send_hosptail1").is(':checked')){
		$('#send_hosptail').val("0");
	}else if($("#send_hosptail2").is(':checked')){
		$('#send_hosptail').val("1");
	}else{
		$('#send_hosptail').val("");
	}
	var logyid = $("#logyid").val();
	var send_hosptail = $('#send_hosptail').val();
	var req_bf_time = $('#req_bf_time').val();
	var req_af_time = $('#req_af_time').val();
	var send_dept = $('#send_dept').val();
	// var req_sts = $("input[name='req_sts']:checked").val();//包埋状态
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
			"req_af_time":req_af_time,"send_dept":send_dept,"send_doctor":send_doctor,"req_sts":req_sts,"logyid":logyid},
		page : 1
	}).trigger('reloadGrid');//重新载入
}
function fillInfo(id){
	setcolor(id);
	clearData();
	var rowData = $("#new").jqGrid('getRowData',id);
	getSampleData(rowData.sampleid);
	var dataIds = "'" + rowData.paraffinid + "'";
	var req_sts = $("#req_sts").val();
	// var req_sts = $("input[name='req_sts']:checked").val();//包埋状态
	if(req_sts == "0"){
		addRow(rowData);
	}else{
		jQuery("#new1").jqGrid('setGridParam',{
			url: "../pathologysample/slide/ajax/getItem",
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
// 				dataIds = "'" + rowData1.paraffinid + "'";
// 			}else{
// 				dataIds = dataIds +",'"+ rowData1.paraffinid+"'";
// 			}
// 		}
// 	);
// 	var req_sts = $("#req_sts").val();
// 	if(req_sts == "0"){
// 		$(selectedIds).each(function () {
// 				var rowData2 = $("#new").jqGrid('getRowData',this.toString());
// 				addRow(rowData2);
// 			}
// 		);
// 	}else{
// 		jQuery("#new1").jqGrid('setGridParam',{
// 			url: "../pathologysample/slide/ajax/getItem",
// 			//发送数据
// 			postData : {"reqId":dataIds}
// 		}).trigger('reloadGrid');//重新载入
// 	}
//
// }
function getSampleData(id) {
	$.get("../pathologysample/slide/get",{id:id},function(data) {
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
	var bpnum = parseInt(data.parnullslidenum);
	var dataRow = {
		slisampleid:data.sampleid,//样本ID
		pieceid:data.pieceid,//材块ID
		sliparaffinid: data.paraffinid,//蜡块ID
		paraffinid: data.paraffinid,//蜡块ID
		sliparaffincode:data.piecode,//材块编号
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
		slisamplingparts: data.parpieceparts,//取材部位
		slifirstn:""//补取医嘱
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
				sliparaffincode:data.piecode,//材块编号
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
				slisamplingparts: data.parpieceparts,//取材部位
				slifirstn:""//补取医嘱
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
	LODOP.SET_PRINT_PAGESIZE(1,"80mm","24mm","A4");
	for(i=0;i<datas.labOrders.length;i++){
		var data = datas.labOrders[i];
		// var topheight1 = Math.floor(i/3)*24+ 3;
		// var topheight2 = Math.floor(i/3)*24+ 8;
		var leftwidth1 = 3;
		if(i<3){
			if(i%3 == 0){
				leftwidth1 = 3;
			}else if(i%3 == 1){
				leftwidth1 = 30;
			}else if(i%3 == 2){
				leftwidth1 = 57;
			}
		}else{
			if(i%3 == 0){
				leftwidth1 = 1;
			}else if(i%3 == 1){
				leftwidth1 = 28;
			}else if(i%3 == 2){
				leftwidth1 = 55;
			}
		}

		LODOP.ADD_PRINT_TEXT("3mm",leftwidth1+"mm","27mm","5mm","浙大国际医院");
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.SET_PRINT_STYLEA(0,"Bold",1);
		// LODOP.ADD_PRINT_BARCODEA("patientCode","21.98mm","27.01mm","46.57mm",40,"128B",data.sampathologycode); slisamplingparts
		// LODOP.SET_PRINT_STYLEA(0,"Horient",2);
		if(data.barcode.length >15){
			LODOP.ADD_PRINT_TEXT("8mm",leftwidth1+"mm","27mm","8mm",data.barcode);
			LODOP.SET_PRINT_STYLEA(0,"FontSize",7);
			LODOP.SET_PRINT_STYLEA(0,"Bold",1);
			if(data.slisamplingparts != null && data.slisamplingparts != ""){
				LODOP.ADD_PRINT_TEXT("16mm",leftwidth1+"mm","24mm","10mm",data.slisamplingparts);
				LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
				LODOP.SET_PRINT_STYLEA(0,"Bold",1);
			}
		}else{
			LODOP.ADD_PRINT_TEXT("8mm",leftwidth1+"mm","27mm","4mm",data.barcode);
			LODOP.SET_PRINT_STYLEA(0,"FontSize",7);
			LODOP.SET_PRINT_STYLEA(0,"Bold",1);
			if(data.slisamplingparts != null && data.slisamplingparts != ""){
				LODOP.ADD_PRINT_TEXT("12mm",leftwidth1+"mm","24mm","10mm",data.slisamplingparts);
				LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
				LODOP.SET_PRINT_STYLEA(0,"Bold",1);
			}
		}

		if(i%3 == 2 || i == datas.labOrders.length -1){
			LODOP.PRINT();
			// LODOP.PREVIEW();
		}
	}

}
function startPrint(data) {
	CreateDataBill(data);
	//开始打印
	// LODOP.PRINT();
	// LODOP.PREVIEW();
	// LODOP.PRINT_SETUP();
}



