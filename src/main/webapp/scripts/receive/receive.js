var hrefval = "";//状态
/**
 * 获取详细信息
 * @param id
 */
function getSampleData(id,receid) {
	$.get("../message/message/get",{id:id},function(data) {
		if(data != "") {
			$.ajax({
				type:"post",
				async:false,
				url:"../receive/receive/editSample",
				dataType: "json",
				data:{id:receid},
				success:function(data){
					if (data.success) {
					}
				}
			});
			$("#mescontent").val(data.mescontent);
			$("#messendername").val(data.messendername);
			$("#meshandletime").val(CurentTime(new Date(data.meshandletime)));
			//$("#req_sts").val(0);
			searchList();
		} else {
			layer.msg("该申请单不存在！", {icon: 0, time: 1000});
		}
	});
}
/**
 * 查看数据
 * @returns {boolean}
 */
function viewSample() {
	clearData();
	var id = $("#new").jqGrid('getGridParam', 'selrow');
	var rowData = $("#new").jqGrid('getRowData',id);
    if (id == null || id == 0) {
        layer.msg('请先选择要修改的数据', {icon: 2, time: 1000});
        return false;
    }
	getSampleData(rowData.messageid,rowData.receivemessageid);
	layer.open({
		type: 1,
		area: ['500px','300px'],
		skin: 'layui-layer-molv',
		fix: false, //不固定
		maxmin: false,
		shade:0.6,
		title: "消息信息",
		content: $("#formDialog")
	});
}
/**
 * 清除数据
 */
function clearData() {
    $('#sampleForm')[0].reset();
}
/**
 * 初始化
 */
$(function() {
	$('#tabs a').click(function (e) {
		hrefval = $(this).attr("href");
		$("#req_sts").val(hrefval);
		searchList();
		e.preventDefault();
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

	var req_bf_time = $('#req_bf_time').val();
	var req_af_time = $('#req_af_time').val();
	var req_sts = $("#req_sts").val();
	$("#new").jqGrid({
		url: "../receive/receive/ajax/sample",
		mtype: "GET",
		datatype: "json",
		postData:{"req_bf_time":req_bf_time,"req_af_time":req_af_time,"req_sts":0},
		colNames: ['接收消息ID','消息ID','消息内容', '发布用户', '发布时间','状态'],
		colModel: [
			{name:'receivemessageid',hidden:true},
			{name:'messageid',hidden:true},
			{ name: 'mescontent', index: 'mescontent'},
			{ name: 'messendername', index: 'messendername'},
			{ name: 'meshandletime', index: 'meshandletime',formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))}},
			{ name: 'receivests', index: 'receivests',formatter: "select", editoptions:{value:"0:未读;1:已读"}}
		],
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		ondblClickRow: function (id) {
			viewSample();
		},
		viewrecords: true,
		height:350,
		autowidth: true,
		rowNum: 10,
		rowList:[10,20,30],
		rownumbers: true, // 显示行号
		rownumWidth: 35, // the width of the row numbers columns
		pager: "#pager"
	});
});

/**
 * 查询数据
 */
function searchList() {
	var req_bf_time = $('#req_bf_time').val();
	var req_af_time = $('#req_af_time').val();
	var req_sts = $("#req_sts").val();
	if(req_sts == null || req_sts == ""){
		req_sts ="";
	}
	jQuery("#new").jqGrid("clearGridData");
	jQuery("#new").jqGrid('setGridParam',{
		url: "../receive/receive/ajax/sample",
		//发送数据
		postData : {"req_bf_time":req_bf_time,"req_af_time":req_af_time,"req_sts":req_sts},
		page : 1
	}).trigger('reloadGrid');//重新载入
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
