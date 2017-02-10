/**
 * Created by king on 2017/2/7.
 */
function getOrderItems(){
    $("#q_specialCheck").empty();
    $.get("../estitem/allorderitem", {}, function (data) {
        var ret = data;
        if (ret != null && ret.length > 0) {
            $("#q_specialCheck").append("<option value=''>请选择</option>");
            for (var i = 0; i < ret.length; i++) {
                $("#q_specialCheck").append("<option value='" + ret[i].testitemid + "'>" + ret[i].teschinesename + "</option>");
            }
        }
    })
}
$(function () {
    getOrderItems();
    var clientWidth = $(window).width();
    var clientHeight = $(window).innerHeight() - $("#divx").height() - 200;
    $(".form_datetime1").datetimepicker({
        minView: "month", //选择日期后，不会再跳转去选择时分秒
        format: "yyyy-mm-dd", //选择日期后，文本框显示的日期格式
        language: 'zh-CN', //汉化
        todayBtn:  1,
        autoclose:true //选择日期后自动关闭asd
    });
    $("#new").jqGrid({
        url: "../othermanage/sample/ajax/slide",
        mtype: "GET",
        datatype: "json",
//		postData:{"logyid":logyid,"sli_code":sli_code,"customer_name":customer_name,"patient_name":patient_name,
//			"sli_in_time":sli_in_time,"sliid":sliid,"current":current},
        colNames: ['医嘱类型','病理编号','患者姓名', '蜡块编号','医嘱项目','申请医生','申请日期','打印状态'],
        colModel: [
            { name: 'teschinesename', index: 'teschinesename',align:'center'},
            { name: 'slipathologycode', align:'center',index: 'slipathologycode'},
            { name: 'sampatientname', align:'center',index: 'sampatientname'},
            { name: 'sliparaffincode', align:'center',index: 'sliparaffincode'},
            { name: 'slitestitemname', align:'center',index: 'slitestitemname'},
            { name: 'chirequsername',align:'center', index: 'chirequsername'},
            {name:'chireqtime',align:'center',index:'chireqtime',formatter:function(cellvalue,options,row){
                if(cellvalue!=null){
                    return CurentTime(new Date(cellvalue));
                }return '';}},
            {name:'sliifprint',align:'center',index:'sliifprint',formatter:"select",editoptions:{value:"0:未打印;1:已打印;"}}],
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
            var ids = $("#new").jqGrid('getDataIDs');
            if(ids != null && ids != ""){
                nowrow = "1";
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
        height:clientHeight,
        width:clientWidth,
        shrinkToFit:false,
        autoScroll: true,
        rownumbers: true, // 显示行号
        rowNum: 10,
        rowList:[10,20,30],
        rownumWidth: 35, // the width of the row numbers columns
        pager: "#pager"
    });
});

function fillInfo1(id) {
    setcolor(id);
}

function setcolor(id){
    var ids = $("#new").getDataIDs();
    $.each(ids, function (key, val) {
        $("#new").children().children("tr[id='"+ids[key]+"']").removeClass("ui-state-highlight");
    });
    $("#new").children().children("tr[id='"+id+"']").addClass("ui-state-highlight");
}

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

function getSearchList(){
    var cheitemtype=$('#q_specialCheck option:selected').val();
    var sliifprint=$('#isprinted option:selected').val();
    var slipathologycode=$("#pathologycode").val();
    var chirequsername=$("#chirequsername").val();
    var sampatientname=$("#patientname").val();
    var applybftime=$("#applybftime").val();
    var applyaftime=$("#applyaftime").val();

    jQuery("#new").jqGrid("clearGridData");
    jQuery("#new").jqGrid('setGridParam',{
        url: "../othermanage/sample/ajax/slide",
        postData:{"cheitemtype":cheitemtype,
            "sliifprint":sliifprint,
            "slipathologycode":slipathologycode,
            "chirequsername":chirequsername,
            "sampatientname":sampatientname,
            "applybftime":applybftime,
            "applyaftime":applyaftime
        },
        //发送数据
        //		success: function(logyid) {
        //                            console.log(logyid);
        //                        },
        page : 1
    }).trigger('reloadGrid');//重新载入

}

function getTotal(sliparaffincode){
    var a = 0;
    $.ajax({
        type:"get",
        async:false,
        url:"../othermanage/sample/ajax/total",
        dataType: "json",
        data:{sliparaffincode:sliparaffincode},
        success:function(data){
            a = data;
        }
    });
    return a;
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
function CreateDataBill() {
    LODOP = getLodop();
    LODOP.PRINT_INIT("");
    LODOP.SET_PRINT_PAGESIZE(1,"80mm","24mm","A4");
    var ids = $("#new").jqGrid('getGridParam','selarrrow');
    for(i=0;i<ids.length;i++){
        var rowData = $("#new").jqGrid('getRowData',ids[i]);
        var sliparaffincode =rowData.sliparaffincode.split("-",1);
        var num = getTotal(sliparaffincode);
        var item = "";
        if(rowData.teschinesename=="免疫组化"){
            item="免组";
        }else{
            item=rowData.teschinesename;
        }
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
        LODOP.ADD_PRINT_TEXT("8mm",leftwidth1+"mm","80mm","4mm",rowData.sliparaffincode);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",7);
        LODOP.SET_PRINT_STYLEA(0,"Bold",1);
        LODOP.ADD_PRINT_TEXT("12mm",leftwidth1+"mm","80mm","10mm","("+rowData.slitestitemname+")");
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.SET_PRINT_STYLEA(0,"Bold",1);
		if(i%3 == 2 || i == ids.length -1){
			LODOP.PRINT();
			// LODOP.PREVIEW();
		}
    }

}
function startPrint() {
    CreateDataBill();
    //开始打印
//	 LODOP.PRINT();
//     LODOP.PREVIEW();
    // LODOP.PRINT_SETUP();
}

