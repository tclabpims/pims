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
	$("#slicode_bro").css("width",850);
	var width = $("#checkSlide").width()-20;
	var width1 = $("#checkSlide2").width();
	var width3=$("#div_main").width()-890;
	$("#diagnosis_result").css("width",width3);
	//var logyid = $("#logyid").val();
	var logyid =$('#logyid').val();
	var slide_code = $('slide_code');
	var customer_name = $('#customer_name').val();
	var patient_name = $('#patient_name').val();
	var sli_in_time = $('#sli_in_time').val();
	var customer_name = $('#customer_name').val();
	var sliid = $('#sliid').val();
	var current = $('#current').val();
	createNew1(width1);
	$("#new").jqGrid({
		//caption:"标本列表",
		url: "../othermanage/loanmanagement/ajax/slide",
		mtype: "GET",
		datatype: "json",
//		postData:{"logyid":logyid,"sli_code":sli_code,"customer_name":customer_name,"patient_name":patient_name,
//			"sli_in_time":sli_in_time,"sliid":sliid,"current":current},
		colNames: ['在库状态','病种类别','病理编号', '玻片编号','患者姓名','年龄','性别','制品日期'],
		colModel: [
        { name: 'slicurrent', index: 'slicurrent',align:'center',width:'90px',formatter:"select",editoptions:{value:"0:借阅中;1:在库;"}},
        { name: 'slipathologyid', align:'center',index: 'slipathologyid',width:'90px',formatter:"select",editoptions:{value:"181:常规病理;182:常规细胞学;183:骨髓细胞学;184:免疫组化;185:术中冰冻;186:外周血细胞;187:液基细胞学;188:HPV;"}},
        { name: 'pathologyid', align:'center',index: 'pathologyid',width:'90px'},
        { name: 'sliid', align:'center',index: 'sliid',width:'90px'},
        { name: 'slipatientname', align:'center',index: 'slipatientname',width:'90px'},
        { name: 'slipatientage',align:'center', index: 'slipatientage',width:'90px'},
        {name:'slipatientsex',align:'center',index:'slipatientsex',width:'90px',formatter:"select",editoptions:{value:"0:女;1:男;"}},
        {name:'slimadetime',align:'center',index:'slimadetime', width:'170px',formatter:function(cellvalue,options,row){
            if(cellvalue!=null){
                return CurentTime(new Date(cellvalue));
            }return '';}
        }],
//        formatter: "select", editoptions:{value:"0:借阅中;1:在库;"},
//        formatter:function(cellvalue, options, row){
//        if(cellvalue!=null){
////            return cellvalue;
////        }
//        return CurentTime(new Date(cellvalue))}return '';}},
//        {name:'sliintime',width:'80px',formatter:function(cellvalue, options, row){
//        if(cellvalue!=null){
//        return CurentTime(new Date(cellvalue))}return '';}},
//        {name:'slimanagername',index:'slimanagername',width:'80px'}
//		],
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
			//$("#new").setSelection(1);
		},
		ondblClickRow: function (id) {
			fillInfo1(id);
//			alert(id);
		},
		onCellSelect:function(id){
			fillInfo1(id);
		},
		// onSelectRow:function(id){
		// 	fillInfo1(id);
		// },
		multiselect: true,   //默认选中
		viewrecords: true,
		height:390,
		width:width,
		shrinkToFit:false,
		autoScroll: true,
		rownumbers: true, // 显示行号
		rowNum: 10,
		rowList:[10,20,30],
		rownumWidth: 35, // the width of the row numbers columns
		pager: "#pager"
	});
//	createNew2();
	$("#pager_left").remove();
});



function loanbtn() {
    var ids = $("#new").jqGrid('getGridParam','selarrrow');
    if(ids.length==0){
            return layer.alert("请先选择玻片！");
        }
    var x = document.getElementById("sliloancustomername").value;
    var y = document.getElementById("sliloancustomerid").value;
    var z = document.getElementById("sliintime").value;

    for(var i=0;i<ids.length;i++){
    var rowData = $("#new").jqGrid('getRowData',ids[i]);
    if(rowData.slicurrent=='1'){
        if(x!=''&&y!=null){
            $.get("../othermanage/loanmanagement/ajax/loan",
                 {
                "sliid":rowData.sliid,
                "customer_name":x,
                "slicustomerid":y,
                "sliintime":z
                },function(data){
                         layer.msg(data.message, {icon: 0, time: 1000});
                         jQuery("#new").jqGrid("clearGridData");
                                   jQuery("#new").jqGrid('setGridParam',{
                                       url: "../othermanage/loanmanagement/ajax/slide",
                                       //发送数据
                               //		success: function(logyid) {
                               //                            console.log(logyid);
                               //                        },
                                       page : 1
                                   }).trigger('reloadGrid');//重新载入
                      });
        }else{
        return layer.alert("请填写借阅人和预计归还日期！");
        }
    }else{return layer.alert("玻片暂时无法借阅！");}
    }

          layer.closeAll();
          document.getElementById("sliloancustomerid").value='';
          document.getElementById("sliloancustomername").value='';
          document.getElementById("sliintime").value='';
}

function cancellayer(){
    document.getElementById("sliloancustomerid").value='';
    document.getElementById("sliloancustomername").value='';
    document.getElementById("sliintime").value='';
    document.getElementById("slireturncustomerid").value='';
    document.getElementById("slidept").value='';
    document.getElementById("sliresult").value='';
    layer.closeAll();
}

function returnbtn(){
    var ids = $("#new3").jqGrid('getGridParam','selarrrow');

     if(ids.length==0){
            return layer.alert("请选择要归还的玻片！");
        }
    for(var i=0;i<ids.length;i++){
    var rowData = $("#new3").jqGrid('getRowData',ids[0]);

    if(ids.length>=2){
        layer.alert("一次只能还一块玻片！");
        return;
    }
    var dept = document.getElementById("slidept").value;
    var result = document.getElementById("sliresult").value;

    if(dept==''){
        layer.alert("请填写诊断机构！");
        return;
    }else {
        if(result==''){
            layer.alert("请填写诊断结果");
            return;
        }
    }
       $.post("../othermanage/loanmanagement/ajax/return0",{
                  sliid:rowData.sliid,
                  slicustomername:rowData.slicustomername,
                  slicustormerid:rowData.slicustomerid,
                  slidept:$("#slidept").val(),
                  sliresult:$("#sliresult").val()
                   },
                   function(m){
                        layer.msg(data.message, {icon: 0, time: 1000});
                   }
                   );
       $.post("../othermanage/loanmanagement/ajax/return3",{
                   sliid:rowData.sliid
                   },
                   function(data){
                        jQuery("#new").jqGrid("clearGridData");
                        jQuery("#new").jqGrid('setGridParam',{
                        url: "../othermanage/loanmanagement/ajax/slide",
                        //发送数据
                        //		success: function(logyid) {
                        //                            console.log(logyid);
                        //                        },
                        page : 1
                        }).trigger('reloadGrid');//重新载入
                   });
       document.getElementById("slidept").value='';
       document.getElementById("sliresult").value='';
    }

    layer.closeAll();
    document.getElementById("slireturncustomerid").value='';
    document.getElementById("slidept").value='';
    document.getElementById("sliresult").value='';
}

//         $.ajax({
//            url : '../othermanage/loanmanagement/ajax/loan',
//            data : {
//            'sliid':$("#samdeptcode").val(),
//            'customer_name':$("#samdeptname").val(),
//            'sli_in_time':$("#samsendhospital").val()
//            }
//            dataType : 'json',
//            type : 'post',
//            error : function(e) {
//                $.ligerDialog.error('出现未知错误');
//            }
//        });
////    alert($("#samsendhospital").val());

/**
 * 初始化收费项目列表
 */
function createNew1(width) {
	$("#new2").jqGrid({
		url:"../othermanage/loanmanagement/ajax/rec",
		datatype: "json",
		mtype:"GET",
		height: 100,
		width:width,
		autowidth: true,
		colNames: ['状态','操作日期','申请人', '经手人','诊断机构','诊断结果'],
		colModel: [
			{name:'slicurrent',align:'center',index:'slicurrent',width:'90px',formatter:"select",editoptions:{value:"0:借阅;1:归还;"}},//项目名称
			{name:'slitime',align:'center',index:'slitime',width:'200px',formatter:function(cellvalue, options, row){
            			if(cellvalue!=null){
                        return CurentTime(new Date(cellvalue))}return '';}},//记录时间
			{name: 'slicustomername',width:'100px', align:'center',index: 'slicustomername'},//数量
	        {name:'slimanagername',width:'100px',align:'center',index:'slimanagername'},
	        {name:'slidept',index:'slidept',hidden:true},
	        {name:'sliresult',index:'sliresult',hidden:true}
			],
        loadComplete : function() {
        			var table = this;
        			setTimeout(function(){
        				updatePagerIcons(table);
        			}, 0);
        		},
        ondblClickRow: function (id) {
                	fillInfo2(id);
                },
        onCellSelect:function(id){
                	fillInfo2(id);
                },
        		//autowidth: true,
        		// shrinkToFit:false,
        		// autoScroll: true,
//        		multiselect: true,
                rownumbers: true, // 显示行号
        		viewrecords: true,
        		rownumbers : true

    });
    $("#new3").jqGrid({
    		datatype: "json",
    		mtype:"GET",
    		height: 100,
    		width:width,
    		autowidth: true,
    		colNames: ['选择','玻片编号','借阅人','借阅日期'],
            colModel: [
            {name:'1',hidden:true},
            {name:'sliid',index:'sliid',width:'85px'},//项目名称
            {name:'slicustomername',index:'slicustomername'},
            {name:'sliouttime',index:'sliouttime',formatter:function(cellvalue, options, row){
            if(cellvalue!=null){
return CurentTime(new Date(cellvalue))}return '';},width:'85px'}],
            loadComplete : function() {
            			var table = this;
            			setTimeout(function(){
            				updatePagerIcons(table);
            			}, 0);
            			var ids = $("#new3").jqGrid('getDataIDs');
                            if(ids != null && ids != ""){
                                nowrow = "1";
                                fillInfo3(1);
                            }
            		},
            ondblClickRow: function (id) {
                    	fillInfo3(id);
                    },
            onCellSelect:function(id){
                    	fillInfo3(id);
                    },
            		//autowidth: true,
            		// shrinkToFit:false,
            		// autoScroll: true,
            		multiselect: true,
            		viewrecords: true,
            		rownumbers : true

            });
}


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
    var ids = $("#new").jqGrid('getGridParam','selarrrow');
	var logyid =$('#logyid').val();
    var slipathologyid =$('#slipathologyid').val();
    var patient_name = $('#patient_name').val();
    var sliid = $('#sliid').val();
    var current = $('#current').val();//包埋状态
	jQuery("#new").jqGrid("clearGridData");
	jQuery("#new").jqGrid('setGridParam',{
		url: "../othermanage/loanmanagement/ajax/slide",
		mtype: "GET",
		//发送数据

		postData:{"logyid":logyid,"slipathologyid":slipathologyid,"patient_name":patient_name,
        			"sliid":sliid,"current":current},
		page : 1
	}).trigger('reloadGrid');//重新载入

}

function searchList2() {
     var x= $('#slireturncustomerid').val();
 	jQuery("#new3").jqGrid("clearGridData");
 	jQuery("#new3").jqGrid('setGridParam',{
 		url: "../othermanage/loanmanagement/ajax/slide",
 		//发送数据
 //		success: function(logyid) {
 //                            console.log(logyid);
 //                        },
 		postData:{"slicustomerid":x},
 		page : 1
 	}).trigger('reloadGrid');//重新载入

 }

function fillInfo1(id){
    document.getElementById("deptandresult").innerHTML = "";
	setcolor(id);
	nowrow = id;
	var rowData = $("#new").jqGrid('getRowData',id);
	document.getElementById("slicurrenta").value = rowData.slicurrent;
	document.getElementById("patientnamea").value = rowData.slipatientname;
	document.getElementById("slipathologyida").value = rowData.slipathologyid;
	document.getElementById("patientagea").value = rowData.slipatientage;
    document.getElementById("pathologyida").value = rowData.pathologyid;
    document.getElementById("patientsexa").value = rowData.slipatientsex;
	if (id == null || id == 0) {
		layer.msg('请先选择数据', {icon: 2, time: 1000});
		return false;
	}
    jQuery("#new2").jqGrid('setGridParam',{
        url: "../othermanage/loanmanagement/ajax/rec",
        //发送数据
        postData:{"sliid":rowData.sliid}
    }).trigger('reloadGrid');//重新载入
     //诊断机构诊断结果
     var obj = $("#new2").getGridParam("reccount");
//     alert(obj);
     for(var i=0;i<obj;i++){
        var rowData = $("#new2").jqGrid("getRowData",i);
        if(rowData.slicurrent=="1"){
            var objhead = document.createElement("div");
            objhead.className="widget-header";
            document.getElementById("deptandresult").appendChild(objhead);
            var objh = document.createElement("h6");
            objh.className="widget-title";
            objh.innerHTML=rowData.slidept+"诊断机构"+rowData.slitime;
            objhead.appendChild(objh);
            var objbody = document.createElement("div");
            objbody.className="widget-body result widget-main padding-4 scrollable ace-scroll";
            document.getElementById("deptandresult").appendChild(objbody);
            var objtext = document.createElement("textarea");
            objtext.className="ta";
            objtext.innerHTML=rowData.sliresult;
            objbody.appendChild(objtext);
        }
     }

}

function fillInfo2(id){

	setcolor1(id);
	var rowData = $("#new2").jqGrid('getRowData',id);
    document.getElementById("slidept2").innerHTML=rowData.slidept;
}

function fillInfo3(id){
    setcolor2(id);
    var rowData = $("#new3").jqGrid('getRowData',id);
    document.getElementById("slireturncustomername").value = rowData.slicustomername;
}

function setcolor(id){
	var ids = $("#new").getDataIDs();
	$.each(ids, function (key, val) {
		$("#new").children().children("tr[id='"+ids[key]+"']").removeClass("ui-state-highlight");
	});
	$("#new").children().children("tr[id='"+id+"']").addClass("ui-state-highlight");
}

function setcolor1(id){
	var ids = $("#new2").getDataIDs();
	$.each(ids, function (key, val) {
		$("#new2").children().children("tr[id='"+ids[key]+"']").removeClass("ui-state-highlight");
	});
	$("#new2").children().children("tr[id='"+id+"']").addClass("ui-state-highlight");
}

function setcolor2(id){
	var ids = $("#new3").getDataIDs();
	$.each(ids, function (key, val) {
		$("#new3").children().children("tr[id='"+ids[key]+"']").removeClass("ui-state-highlight");
	});
	$("#new3").children().children("tr[id='"+id+"']").addClass("ui-state-highlight");
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
    LODOP.ADD_PRINT_TEXT("8mm","8mm","50mm","5mm","树兰(杭州)医院--玻片列表");
    LODOP.SET_PRINT_STYLEA(0,"FontSize",10);
    LODOP.SET_PRINT_STYLEA(0,"Bold",1);
    LODOP.ADD_PRINT_TEXTA("nameText","15mm","25mm","29mm","3mm","在库状态");
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Bold",1);
    LODOP.ADD_PRINT_TEXTA("nameText","15mm","44mm","29mm","3mm","病种类型");
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Bold",1);
    LODOP.ADD_PRINT_TEXTA("nameText","15mm","65mm","29mm","3mm","病理编号");
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Bold",1);
    LODOP.ADD_PRINT_TEXTA("nameText","15mm","94mm","29mm","3mm","玻片编号");
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Bold",1);
    LODOP.ADD_PRINT_TEXTA("nameText","15mm","120mm","29mm","3mm","患者姓名");
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Bold",1);
    LODOP.ADD_PRINT_TEXTA("nameText","15mm","140mm","15mm","3mm","年龄");
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Bold",1);
    LODOP.ADD_PRINT_TEXTA("nameText","15mm","154mm","15mm","3mm","性别");
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Bold",1);
    LODOP.ADD_PRINT_TEXTA("nameText","15mm","170mm","29mm","3mm","制品日期");
    LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
    LODOP.SET_PRINT_STYLEA(0,"Bold",1);
    for(var i=0;i<ids.length;i++){
	    var rowData = $("#new").jqGrid('getRowData',ids[i]);
	    var topheight1 = i*4+20;
	    if(rowData.slicurrent=="0"){
	        rowData.slicurrent="借阅中";
	    }else{
	        rowData.slicurrent="在库";
	    }
	    if(rowData.slipatientsex=="0"){
            rowData.slipatientsex="女";
        }else{
            rowData.slipatientsex="男";
        }
        if(rowData.slipathologyid=="181"){
            rowData.slipathologyid="常规病理";
        }else if(rowData.slipathologyid=="182"){
            rowData.slipathologyid="常规细胞学";
        }else if(rowData.slipathologyid=="183"){
            rowData.slipathologyid="骨髓细胞学";
        }else if(rowData.slipathologyid=="184"){
            rowData.slipathologyid="免疫组化";
        }else if(rowData.slipathologyid=="185"){
            rowData.slipathologyid="术中冰冻";
        }else if(rowData.slipathologyid=="186"){
            rowData.slipathologyid="外周血细胞";
        }else if(rowData.slipathologyid=="187"){
            rowData.slipathologyid="液基细胞学";
        }else if(rowData.slipathologyid=="188"){
            rowData.slipathologyid="HPV";
        }

	    LODOP.ADD_PRINT_TEXTA("nameText",topheight1+"mm","27mm","29mm","3mm",rowData.slicurrent);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.SET_PRINT_STYLEA(0,"Bold",1);
        LODOP.ADD_PRINT_TEXTA("nameText",topheight1+"mm","42mm","29mm","3mm",rowData.slipathologyid);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.SET_PRINT_STYLEA(0,"Bold",1);
        LODOP.ADD_PRINT_TEXTA("nameText",topheight1+"mm","62mm","29mm","3mm",rowData.pathologyid);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.SET_PRINT_STYLEA(0,"Bold",1);
        LODOP.ADD_PRINT_TEXTA("nameText",topheight1+"mm","88mm","29mm","3mm",rowData.sliid);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.SET_PRINT_STYLEA(0,"Bold",1);
        LODOP.ADD_PRINT_TEXTA("nameText",topheight1+"mm","122mm","29mm","3mm",rowData.slipatientname);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.SET_PRINT_STYLEA(0,"Bold",1);
        LODOP.ADD_PRINT_TEXTA("nameText",topheight1+"mm","141mm","15mm","3mm",rowData.slipatientage);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.SET_PRINT_STYLEA(0,"Bold",1);
        LODOP.ADD_PRINT_TEXTA("nameText",topheight1+"mm","156mm","15mm","3mm",rowData.slipatientsex);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.SET_PRINT_STYLEA(0,"Bold",1);
        LODOP.ADD_PRINT_TEXTA("nameText",topheight1+"mm","168mm","29mm","3mm",rowData.slimadetime);
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
//    document.getElementById("loanSlidePage").style.display="block";
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
//    document.getElementById("returnSlidePage").style.display="block";
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


