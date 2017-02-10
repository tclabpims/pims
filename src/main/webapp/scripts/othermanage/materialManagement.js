/**
 * 当前显示数据所在的行
 * @type {string}
 */
var nowrow = "";
/**
 * 当前页面状态
 * @type {string}
 */
var addstates = "";
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

function clickOther() {
	$("#saveButton1").click();
}


/**
 * 清除数据
 */
//function clearData() {
//	$('#tabs-1')[0].reset();
//	jQuery("#new2").jqGrid("clearGridData");
//	//$("input").val("");
//}
/**
 * 初始化
 */
$(function() {
	//表单校验
	$("#sampleForm").Validform({
		btnSubmit:"#saveButton",
		tiptype:4,
		showAllError:true,
		ajaxPost:true,
		beforeSubmit:function(curform){
			//在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。
			//这里明确return false的话表单将不会提交;
			saveInfo();
		},
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

	var clientHeight= $(window).innerHeight();
	var height = $("#formDialog").height() - $('#search_div_1').height() + 230;
	var height2 =clientHeight-$("#head").height()- $('#search_div_1').height()-150;
	    	if(height2 < 340){
        	    height2 = 340;
        	}
	$("#slicode_bro").css("width",850);
	var width = $("#checkSlide").width()-20;
	var width1 = $("#checkSlide2").width();
	var width3=$("#div_main").width()-890;
	$("#diagnosis_result").css("width",width3);
	//var logyid = $("#logyid").val();
	$("#new").jqGrid({
		//caption:"标本列表",
        url:"../othermanage/materialManagement/ajax/mar",
		mtype: "GET",
		datatype: "json",
//		postData:{"sli_code":sli_code,"customer_name":customer_name,"patient_name":patient_name,
//			"sli_in_time":sli_in_time,"sliid":sliid,"current":current},
		colNames: ['库存状态','耗材名称','制造商', '登录者','登录日期','备注','id'],
		colModel: [
        { name: 'marishas', align:'center',index: 'marishas',width:'90px',formatter:"select",editoptions:{value:"0:无;1:有;"}},
        { name: 'marname', align:'center',index: 'marname',width:'110px'},
        { name: 'manufacter', align:'center',index: 'manufacter',width:'110px'},
        { name: 'loginuser',align:'center', index: 'loginuser',width:'110px'},
        {name:'loginintime',align:'center',index:'loginintime', width:'150px',formatter:function(cellvalue,options,row){
            if(cellvalue!=null){
                return CurentTime(new Date(cellvalue));
            }return '';}
        },
        {name:'remarks',index:'remarks',align:'center',width:'248px'},
        { name: 'marid',align:'center',hidden:true, index: 'marid',width:'110px'}],
		beforeSelectRow: function (rowid, e) {
			return $(e.target).is('input[type=checkbox]');},
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
			var ids = $("#new").jqGrid('getDataIDs');
			if(ids != null && ids != ""){
				nowrow = "1";
				fillInfo1(1);
			}

		},
		ondblClickRow: function (id) {
			fillInfo1(id);

		},
		onCellSelect:function(id){
			fillInfo1(id);
		},
		multiselect: true,   //默认选中
		viewrecords: true,
		height:height2,
		width:width,
		shrinkToFit:false,
		autoScroll: true,
		rownumbers: true, // 显示行号
		rowNum: 10,
		rowList:[10,20,30],
		rownumWidth: 35, // the width of the row numbers columns
		pager: "#pager"
	});
	$("#pager_left").remove();
});




/**
 * 获取病种类别
 * @returns {string}
 */
function gettypes(){
	//动态生成select内容
	var str="";
	$.ajax({
		type:"get",
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
 * 查询功能
 */
function searchList() {
    var marname = $('#marname').val();
    var current = $('#current').val();//包埋状态
	jQuery("#new").jqGrid("clearGridData");
	jQuery("#new").jqGrid('setGridParam',{
		url: "../othermanage/materialManagement/ajax/mar",
		mtype: "GET",
		//发送数据

		postData:{"marname":marname,"current":current},
		page : 1
	}).trigger('reloadGrid');//重新载入

}


function fillInfo1(id){
	setcolor(id);
	nowrow = id;
	var rowData = $("#new").jqGrid('getRowData',id);
	document.getElementById("marnamea").value = rowData.marname;
	document.getElementById("marid").value = rowData.marid;
	document.getElementById("loginuser").value = rowData.loginuser;
	document.getElementById("loginintime").value = rowData.loginintime;
    document.getElementById("manufacter").value = rowData.manufacter;
    document.getElementById("remarks").value = rowData.remarks;
    if(rowData.marishas=="1"){
        document.getElementById("marnothave").checked="false";
        document.getElementById("marishas").checked="true";
    }else if(rowData.marishas=="0"){
        document.getElementById("marishas").checked="false";
        document.getElementById("marnothave").checked="true";
    }

	if (id == null || id == 0) {
		layer.msg('请先选择数据', {icon: 2, time: 1000});
		return false;
	}

}



function setcolor(id){
	var ids = $("#new").getDataIDs();
	$.each(ids, function (key, val) {
		$("#new").children().children("tr[id='"+ids[key]+"']").removeClass("ui-state-highlight");
	});
	$("#new").children().children("tr[id='"+id+"']").addClass("ui-state-highlight");
}


/**
 * 填充标本详细信息
 * @param id
 */
function getSampleData1(id) {
	$.get("../pathologysample/sample/get",{id:id},function(data) {
		if(data != "") {
			$("#samsendhospital").val(data.samsendhospital);//送检单位名称
			$("#samdeptname").val(data.samdeptname);//科室名称
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
/**
 * 格式化时间
 * @param now
 * @returns {string}
 * @constructor
 */
function CurentTime(now) {
	//var now = new Date();
	var year = now.getFullYear();       //年
	var month = now.getMonth() + 1;     //月
	var day = now.getDate();            //日
	var hh = now.getHours();            //时
	var mm = now.getMinutes();          //分
	//var ss = now.getMilliseconds();    //秒
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
/**
 * 送检材料
 * @returns {string}
 */
function gettypes1(){
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

function printCode() {
	//打印标本条码号
    var ids = $("#new").jqGrid('getGridParam', 'selarrrow');
    if(ids.length==0){
        layer.msg("请选择打印数据!", {icon:2, time: 1000});
        return;
    }
    startPrint();
}

var LODOP; //声明为全局变量

function Preview() {//打印预览
	LODOP = getLodop();
	CreateDataBill();
	LODOP.PREVIEW();
}
function Setup() {//打印维护
	LODOP = getLodop();
	LODOP.PRINT_SETUP();
}
function CreateDataBill() {
    var ids = $("#new").jqGrid('getGridParam', 'selarrrow');
	LODOP = getLodop();
	LODOP.PRINT_INIT("");
	LODOP.SET_PRINT_PAGESIZE(3,"210mm","297mm","A4");
    LODOP.ADD_PRINT_TEXT("8mm","8mm","50mm","5mm","树兰(杭州)医院--耗材列表");
    LODOP.SET_PRINT_STYLEA(0,"FontSize",10);
    LODOP.SET_PRINT_STYLEA(0,"Bold",1);
    LODOP.ADD_PRINT_TEXTA("nameText","15mm","25mm","29mm","3mm","库存状态");
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Bold",1);
    LODOP.ADD_PRINT_TEXTA("nameText","15mm","50mm","29mm","3mm","耗材名称");
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Bold",1);
    LODOP.ADD_PRINT_TEXTA("nameText","15mm","77mm","29mm","3mm","制造商");
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Bold",1);
    LODOP.ADD_PRINT_TEXTA("nameText","15mm","100mm","29mm","3mm","登录者");
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Bold",1);
    LODOP.ADD_PRINT_TEXTA("nameText","15mm","125mm","29mm","3mm","登陆日期");
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Bold",1);
    LODOP.ADD_PRINT_TEXTA("nameText","15mm","150mm","15mm","3mm","备注");
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Bold",1);
    for(var i=0;i<ids.length;i++){
	    var rowData = $("#new").jqGrid('getRowData',ids[i]);
	    var topheight1 = i*4+20;
	    if(rowData.marishas=="0"){
	        rowData.marishas="借阅中";
	    }else{
	        rowData.marishas="在库";
	    }
	    LODOP.ADD_PRINT_TEXTA("nameText",topheight1+"mm","25mm","29mm","3mm",rowData.marishas);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.SET_PRINT_STYLEA(0,"Bold",1);
        LODOP.ADD_PRINT_TEXTA("nameText",topheight1+"mm","50mm","29mm","3mm",rowData.marname);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.SET_PRINT_STYLEA(0,"Bold",1);
        LODOP.ADD_PRINT_TEXTA("nameText",topheight1+"mm","77mm","29mm","3mm",rowData.manufacter);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.SET_PRINT_STYLEA(0,"Bold",1);
        LODOP.ADD_PRINT_TEXTA("nameText",topheight1+"mm","100mm","29mm","3mm",rowData.loginuser);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.SET_PRINT_STYLEA(0,"Bold",1);
        LODOP.ADD_PRINT_TEXTA("nameText",topheight1+"mm","125mm","29mm","3mm",rowData.loginintime);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.SET_PRINT_STYLEA(0,"Bold",1);
        LODOP.ADD_PRINT_TEXTA("nameText",topheight1+"mm","150mm","15mm","3mm",rowData.remarks);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.SET_PRINT_STYLEA(0,"Bold",1);
	}

}
function startPrint() {
	CreateDataBill();
	//开始打印
	// LODOP.PRINT();
	LODOP.PREVIEW();
	// LODOP.PRINT_SETUP();
}


function loanSlide(){
    document.getElementById("loanSlidePage").style.display="block";
    layer.open({
    			type: 1,
    			area: ['500px','220px'],
    			fix: false, //不固定
    			maxmin: false,
    			multiselect: true,
    			rownumbers : true,
    			shade:0.5,
    			title: "借阅",
    			content: $('#loanSlidePage')
    })
}

function returnSlide(){
    document.getElementById("returnSlidePage").style.display="block";
    layer.open({
        			type: 1,
        			area: ['900px','400px'],
        			fix: false, //不固定
        			maxmin: false,
        			multiselect: true,
        			rownumbers : true,
        			shade:0.5,
        			title: "归还",
        			content: $('#returnSlidePage')
    })
}

//改变标题
function upSlide(){
//	clearData();
	//var id = $("#new").jqGrid('getGridParam', 'selrow');
	var id = nowrow;
	var ids = $("#new").jqGrid('getDataIDs');
	var minId = Math.min.apply(Math,ids);
	if(id == minId){
		id = Math.max.apply(Math,ids);
		//$("#new").setSelection(id);
	}else{
		id = parseInt(id) - 1;
		//$("#new").setSelection(id);
	}
	setcolor(id);
	fillInfo1(id);
	//fillInfo1(id);
}

function downSlide() {
//	clearData();
	//var id = $("#new").jqGrid('getGridParam', 'selrow');
	var id = nowrow;
	var ids = $("#new").jqGrid('getDataIDs');
	var maxId = Math.max.apply(Math,ids);
	if(id == maxId){
		id = Math.min.apply(Math,ids);
		//$("#new").setSelection(id);
	}else{
		id = parseInt(id) + 1;
		//$("#new").setSelection(id);
	}
	setcolor(id);
	fillInfo1(id);
}

function checkedhas(){
    document.getElementById("ss").value=$("#marishas").val();
}

function checkedhas2(){
    document.getElementById("ss").value=$("#marnothave").val();
}

function newAdd(){
    document.getElementById("remarks").disabled=false;
    document.getElementById("marnamea").disabled=false;
    document.getElementById("manufacter").disabled=false;
    document.getElementById("marishas").disabled=false;
    document.getElementById("marnothave").disabled=false;
    document.getElementById("marid").value='';
    document.getElementById("remarks").value='';
    document.getElementById("marnamea").value='';
    document.getElementById("loginintime").value='';
    document.getElementById("manufacter").value='';
    document.getElementById("loginuser").value=document.getElementById("loginuser2").value;
    document.getElementById("loginintime").value=document.getElementById("loginintime2").value;
    document.getElementById("savemar").disabled=false;
}

function save(){
    var marname = $("#marnamea").val();
    var manufacter = $("#manufacter").val();
    var marishas = $("input[name='has']:checked").val();
    var remarks = $("#remarks").val();
    if(marname==''){
        return layer.alert("请填写耗材名称！");
    }
    if(manufacter==''){
        return layer.alert("请填写制造商！");
    }
    $.get("../othermanage/materialManagement/ajax/save",
    {
    "marname":marname,
    "manufacter":manufacter,
    "marishas":marishas,
    "remarks":remarks
    },
    function(data){
        layer.msg(data.message, {icon: 0, time: 1000});
        jQuery("#new").jqGrid("clearGridData");
        jQuery("#new").jqGrid('setGridParam',{
          url: "../othermanage/materialManagement/ajax/mar",
          page : 1
        }).trigger('reloadGrid');// 重新载入
    });


    document.getElementById("remarks").disabled=true;
    document.getElementById("marnamea").disabled=true;
    document.getElementById("manufacter").disabled=true;
    document.getElementById("marishas").disabled=true;
    document.getElementById("marnothave").disabled=true;
    document.getElementById("savemar").disabled=true;
}

function deletemar(){
    var ids = $("#new").jqGrid('getGridParam','selarrrow');
    if(ids.length==0){
       return layer.msg("请先选择要删除的数据！", {icon: 0, time: 1000});
    }else{
    for(var i=0;i<ids.length;i++){
    var rowData = $("#new").jqGrid('getRowData',ids[i]);
    var marid =rowData.marid;
    $.get("../othermanage/materialManagement/ajax/delete",
    {
    "marid":marid
    },
    function(data){
        document.getElementById("marid").value='';
        document.getElementById("remarks").value='';
        document.getElementById("marnamea").value='';
        document.getElementById("loginintime").value='';
        document.getElementById("loginuser").value='';
        document.getElementById("manufacter").value='';
        layer.msg(data.message, {icon: 0, time: 1000});
       jQuery("#new").jqGrid("clearGridData");
       jQuery("#new").jqGrid('setGridParam',{
           url: "../othermanage/materialManagement/ajax/mar",
           //发送数据
           //		success: function(logyid) {
           //                            console.log(logyid);
           //                        },
           page : 1
       }).trigger('reloadGrid');//重新载入

    });
    }
    }
}