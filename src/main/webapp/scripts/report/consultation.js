//$(function() {
//	//表单校验
//	$("#sampleForm").Validform({
//		btnSubmit:"#saveButton",
//		tiptype:4,
//		showAllError:true,
//		ajaxPost:true,
//		beforeSubmit:function(curform){
//			//在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。
//			//这里明确return false的话表单将不会提交;
//			saveInfo();
//		},
//		callback:function(){
//		}
//	});
//	$(".form_datetime").datetimepicker({
//		minView: "month", //选择日期后，不会再跳转去选择时分秒
//		format: "yyyy-mm-dd", //选择日期后，文本框显示的日期格式
//		language: 'zh-CN', //汉化
//		todayBtn:  1,
//		autoclose:true //选择日期后自动关闭
//	});
//	$(".form_datetime1").datetimepicker({
//		//minView: "month", //选择日期后，不会再跳转去选择时分秒
//		format: "yyyy-mm-dd hh:ii:ss", //选择日期后，文本框显示的日期格式
//		language: 'zh-CN', //汉化
//		todayBtn:  1,
//		autoclose:true //选择日期后自动关闭
//	});
//
//	var clientHeight= $(window).innerHeight();
//	var height = $("#formDialog").height() - $('#search_div_1').height() + 230;
//	$("#slicode_bro").css("width",850);
//	var width = $("#checkSlide2").width()-20;
//	var width1 = $("#checkSlide2").width();
//	var width3=$("#div_main").width()-890;
//	$("#diagnosis_result").css("width",width3);
//	//var logyid = $("#logyid").val();
//	$("#new").jqGrid({
//		//caption:"标本列表",
//        url:"../report/consultation/ajax/slide",
//		mtype: "GET",
//		datatype: "json",
////		postData:{"sli_code":sli_code,"customer_name":customer_name,"patient_name":patient_name,
////			"sli_in_time":sli_in_time,"sliid":sliid,"current":current},
//		colNames: ['会诊状态','病种类别','病理编号', '患者姓名','年龄','性别','病理状态','送检单位','送检科室','送检医生'],
//		colModel: [
//        { name: 'conconsultationstate', align:'center',index: 'conconsultationstate',width:'90px',formatter:"select",editoptions:{value:"0:无;1:有;"}},
//        { name: 'sampathologyid', align:'center',index: 'sampathologyid',width:'110px'},
//        { name: 'sampathologycode', align:'center',index: 'sampathologycode',width:'110px'},
//        { name: 'sampatientname',align:'center', index: 'sampatientname',width:'110px'},
//        { name: 'sampatientage',align:'center', index: 'sampatientage',width:'50px'},
//        { name: 'sampatientsex',align:'center', index: 'sampatientsex',width:'50px'},
//        { name: 'samsamplestatus',align:'center', index: 'samsamplestatus',width:'70px'},
//        { name: 'samsendhospital',align:'center', index: 'samsendhospital',width:'110px'},
//        { name: 'samdeptname',align:'center', index: 'samdeptname',width:'110px'},
//        { name: 'samsenddoctorname',align:'center', index: 'samsenddoctorname',width:'110px'}],
//		beforeSelectRow: function (rowid, e) {
//			return $(e.target).is('input[type=checkbox]');},
//		loadComplete : function() {
//			var table = this;
//			setTimeout(function(){
//				updatePagerIcons(table);
//			}, 0);
//			var ids = $("#new").jqGrid('getDataIDs');
//			if(ids != null && ids != ""){
//				nowrow = "1";
////				fillInfo1(1);
//			}
//
//		},
//		ondblClickRow: function (id) {
////			fillInfo1(id);
//
//		},
//		onCellSelect:function(id){
////			fillInfo1(id);
//		},
//		multiselect: true,   //默认选中
//		viewrecords: true,
//		height:390,
//		width:width,
//		shrinkToFit:false,
//		autoScroll: true,
//		rownumbers: true, // 显示行号
//		rowNum: 10,
//		rowList:[10,20,30],
//		rownumWidth: 35, // the width of the row numbers columns
//		pager: "#pager"
//	});
//	$("#pager_left").remove();
//});
//
//function getSearchList(){
//    var comsponsoredtime=$("#comsponsoredtime").val;
//    var confinishedtime=$("#confinishedtime").val;
//    var logyids=$("#logyids").val;
//    var sampathologyid=$("#sampathologyid").val;
//    var sampatientname=$("#sampatientname").val;
//    var samsendhospital=$("#samsendhospital").val;
//    var samdeptname=$("#samdeptname").val;
//    var samsenddoctorname=$("#samsenddoctorname").val;
//    var conconsultationstate=$("#conconsultationstate").val;
//    var samsenddoctorname=$("#samsenddoctorname").val;
//    var consponsoredusername=$("#consponsoredusername").val;
////    var =$("#").val;
////    var =$("#").val;
////    var =$("#").val;
////    var =$("#").val;
//    $.get("../report/consultation/ajax/slide",
//    {conconsultationstate:conconsultationstate,
//     sampathologyid:sampathologyid,
//     sampathologycode:sampathologycode,
//     sampatientname:sampatientname,
//     comsponsoredtime:comsponsoredtime,
//     confinishedtime:confinishedtime,
//     samsendhospital:samsendhospital,
//     samdeptname:samdeptname,
//     samsenddoctorname:samsenddoctorname,
//     consponsoredusername:consponsoredusername,
//     detdoctorname:detdoctorname
//    });
//}