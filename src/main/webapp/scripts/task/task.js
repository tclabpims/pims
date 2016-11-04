/**
 * 回车事件
 * @param obj
 * @param event
 */
function receive(obj,event) {
	var e=e||event;
    var key = event.keyCode;
    if(navigator.appName=="Netscape"){
		key=e.which;
	}else{
		key=event.keyCode;
	}
	switch(key){
		case 13 :
			searchList();
			break;
	}
}
/**
 * 保存信息
 */
function saveInfo() {
	operate = $("#operate").val();
	if(operate == 'cancel') {
		$('#sampleForm')[0].reset();
		$('#examinaim').data('tag').clear();
	} else {
        var rowdatas = $('#new1').jqGrid('getRowData');
		var post = true;
		if(post) {
			$.post("../pimspathology/editSample", {
			    material:JSON.stringify(rowdatas),
                requisitionid:$("#requisitionid").val(),
                reqcustomercode:$("#reqcustomercode").val(),
                reqpathologyid:$("#reqpathologyid").val(),
                requisitionno:$("#requisitionno").val(),
                reqsource:$("#reqsource").val(),
                reqtype:$("#reqtype").val(),
                reqdate:$("#reqdate").val(),
                reqinspectionid:$("#reqinspectionid").val(),
                reqdatechar:$("#reqdatechar").val(),
                reqdeptcode:$("#reqdeptcode").val(),
                reqdeptname:$("#reqdeptcode").find("option:selected").text(),
                reqwardcode:$("#reqwardcode").val(),
                reqwardname:$("#reqwardcode").find("option:selected").text(),
                reqdoctorid:$("#reqdoctorid").val(),
                reqdoctorname:$("#reqdoctorid").find("option:selected").text(),
                reqplanexectime:$("#reqplanexectime").val(),
                reqdigcode:$("#reqdigcode").val(),
                reqchargestatus:$("#reqchargestatus").val(),
                reqsendhospital:$("#reqsendhospital").val(),
                reqsendphone:$("#reqsendphone").val(),
                reqstate:$("#reqstate").val(),
                reqitemids:$("#reqitemids").val(),
                reqitemnames:$("#reqitemnames").val(),
                reqpatientid:$("#reqpatientid").val(),
                reqinpatientid:$("#reqinpatientid").val(),
                reqinpatientno:$("#reqinpatientno").val(),
                reqpatienttype:$("#reqpatienttype").val(),
                reqpatientnumber:$("#reqpatientnumber").val(),
                reqpatientname:$("#reqpatientname").val(),
                reqpatientsex:$("#reqpatientsex").val(),
                reqpatientage:$("#reqpatientage").val(),
                reqpatagetype:$("#reqpatagetype").val(),
                reqpatbirthday:$("#reqpatbirthday").val(),
                reqpatidcard:$("#reqpatidcard").val(),
                reqpattelephone:$("#reqpattelephone").val(),
                reqpataddress:$("#reqpataddress").val(),
                reqpatdiagnosis:$("#reqpatdiagnosis").val(),
                reqismenopause:$("#reqismenopause").val(),
                reqlastmenstruation:$("#reqlastmenstruation").val(),
                reqpatcompany:$("#reqpatcompany").val(),
                reqsendhisorder:$("#reqsendhisorder").val(),
                reqsampleid:$("#reqsampleid").val(),
                reqisdeleted:$("#reqisdeleted").val(),
                reqprintuser:$("#reqprintuser").val(),
                reqprintusername:$("#reqprintusername").val(),
                reqprinttime:$("#reqprinttime").val(),
                reqsendtime:$("#reqsendtime").val(),
                reqremark:$("#reqremark").val(),
                reqfirstv:$("#reqfirstv").val(),
                reqsecondv:$("#reqsecondv").val(),
                //reqthirdv:$("#reqthirdv").val(),
                reqfirstd:$("#reqfirstd").val(),
                reqsecondd:$("#reqsecondd").val(),
                reqfirstn:$("#reqfirstn").val(),
                reqcreateuser:$("#reqcreateuser").val(),
                reqcreatetime:$("#reqcreatetime").val()
			},
			function(data) {
				if(data.success) {
					layer.msg(data.message, {icon: 1, time: 1000});
					layer.closeAll();
					$("#new").trigger("reloadGrid");
				} else {
					layer.msg(data.message, {icon:2, time: 1000});
				}
			});
		} else {
			layer.msg(msg, {icon: 2, time: 1000});
		}
	}
}


function gettypes(){
	//动态生成select内容
	var str="";
	$.ajax({
		type:"post",
		async:false,
		url:"../reqmaterial/info",
		dataType: "json",
		success:function(data){
			if (data != null) {
				//alert(data.length);
				///var jsonobj=eval(data);var length=data.length;
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
 * 查看数据
 * @returns {boolean}
 */
function viewSample(id) {
	//clearData();
	//var rowData = $("#new").jqGrid('getRowData',id);
	layer.open({
		type: 1,
		area: ['1000px','600px'],
		skin: 'layui-layer-molv',
		fix: false, //不固定
		maxmin: false,
		shade:0.6,
		title: "病理信息查看",
		content: $("#formDialog")
	});
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
	var clientWidth = $(window).innerWidth()-100;
	var height =clientHeight-$('#div_2').height()-300;
	var req_code = $('#req_code').val();
	var req_bf_time = $('#req_bf_time').val();
	var req_af_time = $('#req_af_time').val();
	var logyid = $("#logyid").val();
	$("#new").jqGrid({
		url: "../task/task/ajax/sample",
		mtype: "GET",
		datatype: "json",
		postData:{"req_code":req_code,"req_bf_time":req_bf_time,"req_af_time":req_af_time,"logyid":logyid},
		colNames: ['ID','病种类别', '申请年月','病理号','登记技师','取材医师','包埋技师','制片技师','诊断医师','抄送医师','状态'],
		colModel: [
			{name:'sampleid',hidden:true},
			{ name: 'sampathologyid', index: 'sampathologyid',formatter: "select", editoptions:{value:gettypes()}},
			{ name: 'samregisttime', index: 'samregisttime',formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))}},
			{ name: 'sampathologycode', index: 'sampathologycode'},
			{ name: 'samregistername', index: 'samregistername'},
			{ name: 'piedoctorname', index: 'piedoctorname'},
			{ name: 'pieembeddoctorname', index: 'pieembeddoctorname'},
			{ name: 'parsectioneddoctor', index: 'parsectioneddoctor'},
			{ name: 'saminitiallyusername', index: 'saminitiallyusername'},
			{ name: 'saminitiallyusername', index: 'saminitiallyusername'},
			{ name: 'saminitiallyusername', index: 'saminitiallyusername'}
		],
		beforeSelectRow: function (rowid, e) {
			return $(e.target).is('input[type=checkbox]');
		},
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		ondblClickRow: function (id) {
			viewSample(id);
		},
		onCellSelect:function(id){
			viewSample(id);
		},
		multiselect: true,
		viewrecords: true,
		height:height,
		//width:clientWidth,
		autowidth: true,
		rowNum: 10,
		rowList:[10,20,30],
		rownumbers: true, // 显示行号
		rownumWidth: 20, // the width of the row numbers columns
		pager: "#pager",
		//recordpos:'left',
		onSelectAll:function(aRowids,status){
			var rowIds = $("#new").jqGrid('getDataIDs');
			for(var k = 0; k<rowIds.length; k++) {
				var curRowData = jQuery("#new").jqGrid('getRowData', rowIds[k]);
				var curChk = $("#"+rowIds[k]+"").find(":checkbox");
				if(status){
					curChk.attr('checked', 'true');   //设置所有checkbox被选中
				}else{
					curChk.attr('checked', 'false');   //设置所有checkbox未选中
				}
			}
		}
	});
});
/**
 * 查询数据
 */
function searchList() {
	var req_code = $('#req_code').val();
	var req_bf_time = $('#req_bf_time').val();
	var req_af_time = $('#req_af_time').val();
	var logyid = $("#logyid").val();
	jQuery("#new").jqGrid("clearGridData");
	jQuery("#new").jqGrid('setGridParam',{
		url: "../task/task/ajax/sample",
		postData : {"req_code":req_code,"req_bf_time":req_bf_time,"req_af_time":req_af_time,"logyid":logyid},
		page : 1
	}).trigger('reloadGrid');//重新载入
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

function CurentTime1(now) {
	//var now = new Date();
	var year = now.getFullYear();       //年
	var month = now.getMonth() + 1;     //月
	var day = now.getDate();            //日
	var hh = now.getHours();            //时
	var mm = now.getMinutes();          //分
	var ss = now.getSeconds();    //秒
	var clock = year;
	if(month < 10)
		clock += "0";
	clock += month;
	if(day < 10)
		clock += "0";
	clock += day;
	return(clock);
}
