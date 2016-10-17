function getData(obj,event) {
	var e=e||event;
    var key = event.keyCode;
    if(navigator.appName=="Netscape"){
		key=e.which;
	}else{
		key=event.keyCode;
	}
	switch(key){
		case 13 : 
			var id=obj.value;
			var type = 1;
			if(obj.id == 'sampleno') {
				type = 2;
			}
			getSampleData(id, type);
			break;
	}
}

function getSampleData(id) {
	$.get("../pimspathology/get",{id:id},function(data) {
		if(data != "") {
			$("#samisdeleted").val("0");
			$("#sampatientid").val(data.reqpatientid);
			$("#saminpatientid").val(data.reqinpatientid);
			$("#sampatienttype").val(data.reqpatienttype);
			$("#samsampleclass").val("0");
			$("#sampopuser").val("0");
			$("#samsamplestatus").val("0");
			$("#sampleid").val("");
			$("#samrequistionid").val(data.requisitionid);
			$("#sampathologyid").val(data.reqpathologyid);
			$("#samsource").val(data.reqsource);
			$("#samreceivertime").val(data.reqdate);
			$("#samsendtime").val(data.reqdate);
			$("#samdeptcode").val(data.reqdeptcode);
			$("#samsenddoctorid").val(data.reqdoctorid);
			$("#samsendhospital").val(data.reqsendhospital);
			$("#sampatientnumber").val(data.reqpatientnumber);
			$("#sampatientname").val(data.reqpatientname);
			$("#sampatientsex").val(data.reqpatientsex);
			$("#sampatientage").val(data.reqpatientage);
			$("#sampatientagetype").val(data.reqpatagetype);
			$("#sampatientphoneno").val(data.reqpattelephone);
			$("#sampatientaddress").val(data.reqpataddress);
			$("#sampatientdignoses").val(data.reqpatdiagnosis);
			$("#samreceivertime").val(data.reqcreatetime);
		} else {
			layer.msg("该申请单不存在！", {icon: 0, time: 1000});
		}
	});
}

function getPatient(obj,event) {
	var e=e||event;
    var key = event.keyCode;
    if(navigator.appName=="Netscape"){
		key=e.which;
	}else{
		key=event.keyCode;
	}
	switch(key){
		case 13 : 
			$.get("../sample/ajax/getpatient",{pid:obj.value},function(data) {
				var data = JSON.parse(data);
				if($("#doctadviseno").val() == "") {
					if(data.ispid) {
						$("#patientid").val(data.pid);
						$("#patientname").val(data.pname);
						$("#sex").val(data.sex);
						$("#age").val(data.age);
					} else{
						layer.msg("就诊卡号为" + obj.value + "的病人不存在！", {icon: 0, time: 1000})
					}
				} else {
					layer.msg("医嘱号已存在，无权限修改接诊卡号！", {icon: 0, time: 1000});
				}
			});
			break;
	}
}

function Pad(num, n) {
    return (Array(n).join(0) + num).slice(-n);
}

function isDate(str){
	if(!isNaN(str.substring(0,4)) && !isNaN(str.substring(4,6)) && !isNaN(str.substring(6,8))) {
		var year = parseInt(str.substring(0,4));
		var month = parseInt(str.substring(4,6))-1;
		var day = parseInt(str.substring(6,8));
		var date=new Date(year,month,day);
		if(date.getFullYear()==year&&date.getMonth()==month&&date.getDate()==day) {
			return true;
		} else {
			return false;
		}
	} else {
		return false;
	}
} 

function saveInfo() {
	var msg = "";
	var post = true;
	if(post) {
		$.post("../pathologysample/sample/editSample", {
			samisdeleted:$("#samisdeleted").val(),
			sampatientid:$("#sampatientid").val(),
			samregisttime:$("#samregisttime").val(),
			samregisterid:$("#samregisterid").val(),
			saminpatientid:$("#saminpatientid").val(),
			sampatienttype:$("#sampatienttype").val(),
			samsampleclass:$("#samsampleclass").val(),
			sampopuser:$("#sampopuser").val(),
			samsamplestatus:$("#samsamplestatus").val(),
			sampleid:$("#sampleid").val(),
			samcustomerid:$("#samcustomerid").val(),
			samcustomercode:$("#samsendhospital").val(),
			samsource:$("#samsource").val(),
			saminspectionid:$("#saminspectionid").val(),
			samrequistionid:$("#samrequistionid").val(),
			sampathologyid:$("#sampathologyid").val(),
			sampathologycode:$("#sampathologycode").val(),
			sampatientname:$("#sampatientname").val(),
			sampatientphoneno:$("#sampatientphoneno").val(),
			sampatientnumber:$("#sampatientnumber").val(),
			//reqdeptname:$("#reqdeptcode").find("option:selected").text(),
			sampatientbed:$("#sampatientbed").val(),
			sampatientaddress:$("#sampatientaddress").val(),
			sampatientsex:$("#sampatientsex").val(),
			sampatientage:$("#sampatientage").val(),
			sampatientagetype:$("#sampatientagetype").val(),
			samfirstv:$("input[name='samfirstv']:checked").val(),
			samsenddoctorid:$("#samsenddoctorid").val(),
			samsenddoctorname:$("#samsenddoctorid").find("option:selected").text(),
			samdeptcode:$("#samdeptcode").val(),
			samsendhospital:$("#samsendhospital").val(),
			samsendtime:$("#samsendtime").val(),
			samreceivertime:$("#samreceivertime").val(),
			samfirstn:$("#samfirstn").val(),
			samsecondv:$("input[name='samsecondv']:checked").val(),
			samremark:$("#samremark").val(),
			samsamplename:$("#samsamplename").val(),
			sampatientdignoses:$("#sampatientdignoses").val(),
			samthirdv:$("#samthirdv").val()
		},
		function(data) {
			if(data.success) {
				layer.msg(data.message, {icon: 1, time: 1000});
				location.reload();
			} else {
				layer.msg(data.message, {icon:2, time: 1000});
			}
		});
	} else {
		layer.msg(msg, {icon: 2, time: 1000});
	}
}

function addSample() {
	clearData();
	$('#sampleForm').find('input,textarea,select').removeAttr('disabled') ;
	$("#saveButton").removeAttr("disabled");//将按钮可用
	$("#editButton").attr({"disabled":"disabled"});
	$("#deleteButton").attr({"disabled":"disabled"});
	//$("#saveButton").attr({"disabled":"disabled"});
	// $('#sampleForm').find('input,textarea,select').removeAttr('disabled') ;
	//$("#btnzhuce").removeAttr("disabled");//将按钮可用
}

function editSample(){
	$.get("../pathologysample/sample/canchange", {
			id:$("#sampleid").val(),
			sts:"1"
		},
		function(data) {
			//var data = JSON.parse(data);
			if(data.success) {
				$('#sampleForm').find('input,textarea,select').removeAttr('disabled');
				$("#saveButton").removeAttr("disabled");//将按钮可用
				$("#addButton").removeAttr("disabled");//将按钮可用
				$("#editButton").removeAttr("disabled");//将按钮可用
				$("#deleteButton").removeAttr("disabled");//将按钮可用
			}else{
				layer.msg(data.message, {icon:2, time: 1000});
			}
		});
}

function deleteSample() {
	var id = $("#new").jqGrid('getGridParam', 'selrow');
	var rowData = $("#new").jqGrid('getRowData',id);
    if (id == null || id == 0) {
        layer.msg('请先选择要删除的数据', {icon: 2, time: 1000});
        return false;
    }
	layer.confirm('确定删除选择数据？', {icon: 2, title:'警告'}, function(index){
		$.get("../pathologysample/sample/canchange", {
				id:$("#sampleid").val(),
				sts:"2"
			},
			function(data) {
				//var data = JSON.parse(data);
				if(data.success) {
					$.post('../pathologysample/sample/deleteSample',{sampleid:rowData.sampleid},function(data) {
						layer.close(index);
						$("#new").trigger('reloadGrid');
					});
				}else{
					layer.msg(data.message, {icon:2, time: 1000});
				}
			});
	});
}

function clearData() {
    $('#sampleForm')[0].reset();
    //$("input").val("");
}

$(function() {
	//表单校验
	$("#sampleForm").Validform({
		btnSubmit:"#saveButton",
		tiptype:4,
		callback:function(){
		}
	});
	$(".form_datetime").datetimepicker({
		minView: "month", //选择日期后，不会再跳转去选择时分秒
		format: "yyyy-mm-dd", //选择日期后，文本框显示的日期格式
		language: 'zh-CN', //汉化
		todayBtn:  1,
		autoclose:true //选择日期后自动关闭
	});
	$(".form_datetime1").datetimepicker({
		//minView: "month", //选择日期后，不会再跳转去选择时分秒
		format: "yyyy-mm-dd hh:ii:ss", //选择日期后，文本框显示的日期格式
		language: 'zh-CN', //汉化
		todayBtn:  1,
		autoclose:true //选择日期后自动关闭
	});
	// $("#reqitemnames").autocomplete({
     //    source: function( request, response ) {
     //        $.ajax({
     //        	url: "../estitem/ajax/item",
     //            dataType: "json",
     //            data: {
     //                name : request.term
     //            },
     //            success: function( data ) {
     //            	response( $.map( data, function( result ) {
     //                    return {
     //                        label: result.id + " : " + result.name,
     //                        value: result.name,
     //                        id : result.id
     //                    }
     //                }));
     //            }
     //        });
     //    },
     //    minLength: 0,
     //    select: function( event, ui ) {
     //    	$( "#reqitemids" ).val(ui.item.id);
     //    	$( "#reqitemnames" ).val(ui.item.name);
     //        //return false;
	// 	}
	// })
	// .data( "ui-autocomplete" )._renderItem = function( ul, item ) {
	// 	 return $( "<li>" )
	// 		 .append( "<a style='font-size:12px;font-family: 微软雅黑;'>" + item.id + "," + item.value+ "</a>" )
	// 		 .appendTo( ul );
	//  };
	var clientHeight= $(window).innerHeight();
	var height =clientHeight-$('#div_1').height()- $('#div_2').height()-200;
	//var logyid = $("#logyid").val();
	var logyid ="";
	var send_hosptail = "";
	var req_code = $('#req_code').val();
	var patient_name = $('#patient_name').val();
	//var send_hosptail = $('#send_hosptail').val();
	var req_bf_time = $('#req_bf_time').val();
	var req_af_time = $('#req_af_time').val();
	var send_dept = $('#send_dept').val();
	var send_doctor = $('#send_doctor').val();
	var req_sts = $('#req_sts').val();
	$("#new").jqGrid({
		url: "../pathologysample/sample/ajax/sample",
		mtype: "GET",
		datatype: "json",
		postData:{"req_code":req_code,"patient_name":patient_name,"send_hosptail":send_hosptail,"req_bf_time":req_bf_time,
			"req_af_time":req_af_time,"send_dept":send_dept,"send_doctor":send_doctor,"req_sts":req_sts,"logyid":logyid},
		colNames: ['ID','条形码', '病理号', '送检医生','送检医院','病人名','病理状态'],
		colModel: [
			{name:'sampleid',hidden:true},
			{ name: 'saminspectionid', index: 'saminspectionid'},
			{ name: 'sampathologycode', index: 'sampathologycode'},
			{ name: 'samsenddoctorname', index: 'samsenddoctorname'},
			{ name: 'samsendhospital', index: 'samsendhospital'},
			{ name: 'sampatientname', index: 'sampatientname'},
			{ name: 'samsamplestatus', index: 'samsamplestatus',formatter: "select", editoptions:{value:"0:已登记;1:已取材;2:已包埋;3:已切片;4:已初诊;5:已审核;6:已发送;7:会诊中:8:报告已打印"}}
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
			fillInfo1();
		},
		viewrecords: true,
		height:300,
		autowidth: true,
		rowNum: 10,
		rowList:[10,20,30],
		rownumbers: true, // 显示行号
		rownumWidth: 35, // the width of the row numbers columns
		pager: "#pager"
	});
	$("#new1").jqGrid({
		url: "../pathologysample/sample/ajax/getreqinfo",
		mtype: "GET",
		datatype: "json",
		postData:{"req_code":req_code,"patient_name":patient_name,"send_hosptail":send_hosptail,"req_bf_time":req_bf_time,
			"req_af_time":req_af_time,"send_dept":send_dept,"send_doctor":send_doctor,"req_sts":req_sts,"logyid":logyid},
		colNames: ['ID','临床申请', '病种类别', '申请年月','病人姓名','送检医院','送检科室',"送检医生"],
		colModel: [
			{name:'requisitionid',hidden:true},
			{ name: 'requisitionno', index: 'requisitionno'},
			{ name: 'reqpathologyid', index: 'reqpathologyid',formatter: "select", editoptions:{value:"1:常规病理;2:常规病理1;3:液基细胞学"}},
			{ name: 'reqdate', index: 'reqdate'},
			{ name: 'reqpatientname', index: 'reqpatientname'},
			{ name: 'reqsendhospital', index: 'reqsendhospital'},
			{ name: 'reqdeptname', index: 'reqdeptname'},
			{ name: 'reqdoctorname', index: 'reqdoctorname'}
		],
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		ondblClickRow: function (id) {
			editSample();
		},
		onSelectRow:function(id){
			fillInfo();
		},
		viewrecords: true,
		height:100,
		autowidth: true,
		rowNum: 10,
		//rowList:[10,20,30],
		rownumbers: true, // 显示行号
		rownumWidth: 35, // the width of the row numbers columns
		pager: "#pager1"
	});

    //createNew1("");
});

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
		url: "../pimspathology/ajax/pathology",
		//发送数据
		postData : {"req_code":req_code,"patient_name":patient_name,"send_hosptail":send_hosptail,"req_bf_time":req_bf_time,
			"req_af_time":req_af_time,"send_dept":send_dept,"send_doctor":send_doctor,"req_sts":req_sts},
		page : 1
	}).trigger('reloadGrid');//重新载入
}

function fillInfo(){
	clearData();
	var id = $("#new1").jqGrid('getGridParam', 'selrow');
	var rowData = $("#new1").jqGrid('getRowData',id);
	if (id == null || id == 0) {
		layer.msg('请先选择数据', {icon: 2, time: 1000});
		return false;
	}
	getSampleData(rowData.requisitionid);
	// $('#sampleForm').find('input,textarea,select').attr('disabled',true) ;
	$("#editButton").attr({"disabled":"disabled"});
	$("#editButton").attr({"disabled":"disabled"});
	// $("#saveButton").attr({"disabled":"disabled"});
	$('#sampleForm').find('input,textarea,select').removeAttr('disabled') ;
	$("#saveButton").removeAttr("disabled");//将按钮可用
}
function fillInfo1(){
	clearData();
	var id = $("#new").jqGrid('getGridParam', 'selrow');
	var rowData = $("#new").jqGrid('getRowData',id);
	if (id == null || id == 0) {
		layer.msg('请先选择数据', {icon: 2, time: 1000});
		return false;
	}
	getSampleData1(rowData.sampleid);
	$('#sampleForm').find('input,textarea,select').attr('disabled',true) ;
	$("#editButton").removeAttr("disabled");//将按钮可用
	$("#editButton").removeAttr("disabled");//将按钮可用
	$("#saveButton").attr({"disabled":"disabled"});
}
function getSampleData1(id) {
	$.get("../pathologysample/sample/get",{id:id},function(data) {
		if(data != "") {
			$("#samregisttime").val(data.samregisttime);
			$("#samregisterid").val(data.samregisterid);
			$("#sampleid").val(data.sampleid);
			$("#samisdeleted").val(data.samisdeleted);
			$("#sampatientid").val(data.sampatientid);
			$("#saminpatientid").val(data.saminpatientid);
			$("#sampatienttype").val(data.sampatienttype);
			$("#samsampleclass").val(data.samsampleclass);
			$("#sampopuser").val(data.sampopuser);
			$("#samsamplestatus").val(data.samsamplestatus);
			$("#saminspectionid").val(data.saminspectionid);
			$("#sampathologycode").val(data.sampathologycode);
			$("#samrequistionid").val(data.samrequistionid);
			$("#sampathologyid").val(data.sampathologyid);
			$("#samsource").val(data.samsource);
			$("#samreceivertime").val(data.samreceivertime);
			$("#samsendtime").val(data.samsendtime);
			$("#samdeptcode").val(data.samdeptcode);
			$("#samsenddoctorid").val(data.samsenddoctorid);
			$("#samsendhospital").val(data.samsendhospital);
			$("#sampatientnumber").val(data.sampatientnumber);
			$("#sampatientname").val(data.sampatientname);
			$("#sampatientsex").val(data.sampatientsex);
			$("#sampatientage").val(data.sampatientage);
			$("#sampatientagetype").val(data.sampatientagetype);
			$("#sampatientphoneno").val(data.sampatientphoneno);
			$("#sampatientaddress").val(data.sampatientaddress);
			$("#sampatientdignoses").val(data.sampatientdignoses);
			$("#samreceivertime").val(data.samreceivertime);
			$("#sampatientbed").val(data.sampatientbed);
			$("#samfirstn").val(data.samfirstn);
			$("#samremark").val(data.samremark);
			$("#samsamplename").val(data.samsamplename);
			$("#sampatientdignoses").val(data.sampatientdignoses);
			$("#samthirdv").val(data.samthirdv);
			var samfirstv = data.samfirstv;
			var samsecondv = data.samsecondv;
			if(samfirstv == 1){
				$("input[name='samfirstv'][value='1']").attr("checked",true);
			}else{
				$("input[name='samfirstv'][value='2']").attr("checked",true);
			}
			if(samsecondv == 1){
				$("input[name='samsecondv'][value='1']").attr("checked",true);
			}else{
				$("input[name='samsecondv'][value='2']").attr("checked",true);
			}

		} else {
			layer.msg("该申请单不存在！", {icon: 0, time: 1000});
		}
	});
}
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
