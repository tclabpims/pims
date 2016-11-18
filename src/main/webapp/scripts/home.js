/**
 * 病种类别
 * @returns {string}
 */
function gettypes1(){
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
 * 初始化
 */
$(function () {
    /**
     * 日期控件
     */
    $(".form_datetime").datetimepicker({
        minView: "month", //选择日期后，不会再跳转去选择时分秒
        format: "yyyy-mm-dd", //选择日期后，文本框显示的日期格式
        language: 'zh-CN', //汉化
        todayBtn:  1,
        autoclose:true //选择日期后自动关闭
    });
    /**
     * 初始化页面
     */
    $("#show_div_1").css('display','block');
    $("#show_div_1_1").css('display','block');
    $('#div_lable_1 a:first').tab('show');//初始化显示哪个tab
    $('#div_lable_1 a').click(function (e) {
        $('#div_lable_1 a').each(function(){
            $("#show_div_"+ $(this).attr("href")).css('display','none');
            if($(this).attr("href") == 1){
                $("#show_div_1_1").css('display','none');
                $("#show_div_1_2").css('display','none');
            }
            $(this).css("border-bottom","0px");
        });
        e.preventDefault();//阻止a链接的跳转行为
        $("#show_div_"+ $(this).attr("href")).css('display','block');
        if($(this).attr("href") == 1){
            $("#show_div_1_1").css('display','block');
            //$("#show_div_1_2").css('display','block');
        }
        $(this).css("border-bottom","4px solid #0FCFA0");
        $(this).tab('show');//显示当前选中的链接及关联的content
    });
    /**
     * 我的未处理工作
     */
    $('#show_div_1 a').click(function (e) {
        var hrefval = $(this).attr("href").split(",");
        jQuery("#new"+hrefval[0]).jqGrid("clearGridData");
        if(hrefval[0] == 1){
            $("#show_div_1_1").css('display','block');
            $("#show_div_1_2").css('display','none');
            jQuery("#new1").jqGrid('setGridParam',{
                url: "../pathologysample/sample/ajax/samplelist",
                postData : {"req_sts":hrefval[1],"patient_name":$("#local_userid").val()},
                page : 1
            }).trigger('reloadGrid');//重新载入
        }else if(hrefval[0] == "2_1"){
            $("#show_div_1_1").css('display','none');
            $("#show_div_1_2").css('display','block');
            var req_code = "";
            if(hrefval.length == 3){
                req_code = hrefval[2];
            }
            jQuery("#new2_1").jqGrid('setGridParam',{
                url: "../order/orderlist",
                postData : {"req_sts":hrefval[1],"req_code":req_code,"patient_name":$("#local_userid").val()},
                page : 1
            }).trigger('reloadGrid');//重新载入
        }
        e.preventDefault();
    });
    /**
     * 系统未处理信息
     */
    $("#sys_0").css('display','block');
    $('#sys a').click(function (e) {
        $('#sys a').each(function(){
            var href =  $(this).attr("href").split(",");
            $("#sys_"+ href[0]).css('display','none');
        });
        e.preventDefault();//阻止a链接的跳转行为
        var href = $(this).attr("href").split(",");
        $("#sys_"+ href[0]).css('display','block');
        jQuery("#sysnew"+href[0]).jqGrid("clearGridData");
        if(href[0] == 0){//未取材
            jQuery("#sysnew0").jqGrid('setGridParam',{
                url: "../pathologysample/sample/ajax/sample",
                postData : {"req_code":0},
                page : 1
            }).trigger('reloadGrid');//重新载入
        }else if(href[0] == 1){//未包埋
            jQuery("#sysnew1").jqGrid('setGridParam',{
                url: "../pathologysample/paraffin/ajax/sample",
                postData : {"req_sts":0},
                page : 1
            }).trigger('reloadGrid');//重新载入
        }else if(href[0] == 2){//未切片
            jQuery("#sysnew2").jqGrid('setGridParam',{
                url: "../pathologysample/slide/ajax/sample",
                postData : {"req_sts":0},
                page : 1
            }).trigger('reloadGrid');//重新载入
        }else if(href[0] == 3){//未初查未审核未打印未发送
            jQuery("#sysnew3").jqGrid('setGridParam',{
                url: "../pathologysample/sample/ajax/samplelist",
                postData : {"req_sts":href[1]},
                page : 1
            }).trigger('reloadGrid');//重新载入
        }else if(href[0] == 4){//未接收未签收未补取
            var req_code = "";
            if(href.length == 3){
                req_code = href[2];
            }
            jQuery("#sysnew4").jqGrid('setGridParam',{
                url: "../order/orderlist",
                postData : {"req_sts":href[1],"req_code":req_code},
                page : 1
            }).trigger('reloadGrid');//重新载入
        }

    });

    var width = $("#tabs").width();
    /**
     * 系统未取材
     */
    $("#sysnew0").jqGrid({
        url: "../pathologysample/sample/ajax/sample",
        mtype: "GET",
        datatype: "json",
        postData:{"req_code":"0"},
        colNames: ['ID', '病种类型','病理号', '送检医生','送检医院','病人姓名','是否全取','是否脱钙'],
        colModel: [
            {name:'sampleid',hidden:true},//ID
            { name: 'sampathologyid', index: 'sampathologyid',formatter: "select", editoptions:{value:gettypes1()},align:"center",width:'100px'},//病种类型
            { name: 'sampathologycode', index: 'sampathologycode',width:'100px', align: "center"},//病理号
            { name: 'samsenddoctorname', index: 'samsenddoctorname',width:'100px', align: "center"},//送检医生
            { name: 'samsendhospital', index: 'samsendhospital',width:'100px', align: "center"},//送检医院
            { name: 'sampatientname', index: 'sampatientname',width:'100px', align: "center"},//病人姓名
            { name: 'samissamplingall', index: 'samissamplingall',width:'100px', align: "center",formatter:"select",editoptions:{value:"0:否;1:是"}},//是否全取
            { name: 'samisdecacified', index: 'samisdecacified',width:'100px', align: "center",formatter:"select",editoptions:{value:"0:否;1:是"}}//是否脱钙
        ],
        gridComplete: function () {
        },
        loadComplete : function() {
            var table = this;
            setTimeout(function(){
                updatePagerIcons(table);
            }, 0);
        },
        ondblClickRow: function (id) {
            var rowData = $("#sysnew0").jqGrid('getRowData',id);
            location.href='../pathologysample/pieces.jsp?m=取材管理&id='+ rowData.sampleid;
        },
        viewrecords: true,
        height:320,
        width:width,
        //autowidth: true,
        rowNum: 10,
        rowList:[10,20,30],
        rownumbers: true, // 显示行号
        rownumWidth: 35, // 行号宽度
        pager: "#syspager0"
    });
    /**
     * 系统未包埋
     */
    $("#sysnew1").jqGrid({
        url: "../pathologysample/paraffin/ajax/sample",
        mtype: "GET",
        datatype: "json",
        postData:{"req_sts":"0"},
        colNames: ['材块ID','标本id','病理状态','病种类别', '病理号','材块编号','特殊要求','白片数','病理状态','补取医嘱','材块数','白片数',
            '取材部位','特殊要求', '取材时间','取材医生ID','取材医生','序号','包埋状态'],
        colModel: [
            {name:'pieceid',hidden:true},//材块ID
            {name:'sampleid',hidden:true},//标本id
            {name:'samsamplestatus',hidden:true},//病理状态
            { name: 'sampathologyid', index: 'sampathologyid',formatter: "select", editoptions:{value:gettypes1()},align:"center",width:'100px'},//病种类型
            { name: 'piepathologycode', index: 'piepathologycode'},//病理号
            { name: 'piecode', index: 'piecode'},//材块编号
            { name: 'piespecial', index: 'piespecial'},//特殊要求
            { name: 'pienullslidenum', index: 'pienullslidenum'},//白片数
            { name: 'pieisembed', index: 'pieisembed',formatter: "select", editoptions:{value:"0:未包埋;1:已包埋"},width:70},//病理状态
            { name: 'piefirstn', index: 'piefirstn'},//补取医嘱
            {name:'piecounts',hidden:true},//材块数
            {name:'pienullslidenum',hidden:true},//白片数
            {name:'pieparts',hidden:true},//取材部位
            {name:'piespecial',hidden:true},//特殊要求
            {name:'piesamplingtime',hidden:true,formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))}},//取材时间
            {name:'piedoctorid',hidden:true},//取材医生ID
            {name:'piedoctorname',hidden:true},//取材医生
            {name:'piesamplingno',hidden:true},//序号
            {name:'pieisembed',hidden:true},//包埋状态
        ],
        gridComplete: function () {
        },
        loadComplete : function() {
            var table = this;
            setTimeout(function(){
                updatePagerIcons(table);
            }, 0);
        },
        ondblClickRow: function (id) {
            var rowData = $("#sysnew1").jqGrid('getRowData',id);
            location.href='../pathologysample/paraffin?m=包埋管理&id='+ rowData.sampleid;
        },
        viewrecords: true,
        height:320,
        width:width,
        //autowidth: true,
        rowNum: 10,
        rowList:[10,20,30],
        rownumbers: true, // 显示行号
        rownumWidth: 35, // 行号宽度
        pager: "#syspager1"
    });
    /**
     * 系统未切片
     */
    $("#sysnew2").jqGrid({
        url: "../pathologysample/slide/ajax/sample",
        mtype: "GET",
        datatype: "json",
        postData:{"req_sts":"0"},
        colNames: ['蜡块ID','标本id','病理状态','蜡块状态', '病理号','病人名','送检医生','送检科室','包埋医生', '包埋时间','切片医生', '切片时间','打印状态','白片数',
            '材块ID','材块编号','取材医生','取材时间','客户代码','客户代码','蜡块序号','取材部位','病种类别'],
        colModel: [
            {name:'paraffinid',hidden:true},
            {name:'sampleid',hidden:true},
            {name:'samsamplestatus',hidden:true},
            { name: 'parissectioned', index: 'parissectioned',formatter: "select", editoptions:{value:"0:未切片;1:已切片"},width:100},
            { name: 'sampathologycode', index: 'sampathologycode',width:100},
            { name: 'sampatientname', index: 'sampatientname',width:100},
            { name: 'samsenddoctorname', index: 'samsenddoctorname',width:100},
            {name:'samdeptname',index:'samdeptname',width:100},
            {name:'pieembeddoctorname',index:'pieembeddoctorname',width:100},
            {name:'pieembedtime',index:'pieembedtime',formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))},width:100},
            {name:'parsectioneddoctor',index:'parsectioneddoctor',width:100},
            {name:'parsectionedtime',index:'parsectionedtime',formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))},width:100},
            {name:'parisprintlabel',index:'parisprintlabel',width:100},
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
        gridComplete: function () {
        },
        loadComplete : function() {
            var table = this;
            setTimeout(function(){
                updatePagerIcons(table);
            }, 0);
        },
        ondblClickRow: function (id) {
            var rowData = $("#sysnew2").jqGrid('getRowData',id);
            location.href='../pathologysample/slide?m=切片管理&id='+ rowData.sampleid;
        },
        viewrecords: true,
        height:320,
        width:width,
        //autowidth: true,
        rowNum: 10,
        rowList:[10,20,30],
        rownumbers: true, // 显示行号
        rownumWidth: 35, // 行号宽度
        pager: "#syspager2"
    });
    /**
     * 系统未初查未审核未打印未发送
     */
    $("#sysnew3").jqGrid({
        url: "../pathologysample/sample/ajax/samplelist",
        mtype: "GET",
        datatype: "json",
        postData:{"req_sts":"3"},
        colNames: ['ID', '病种类型','病理号', '送检医生','送检医院','病人姓名','取材医生','取材时间'],
        colModel: [
            {name:'sampleid',hidden:true},//ID
            { name: 'sampathologyid', index: 'sampathologyid',formatter: "select", editoptions:{value:gettypes1()},align:"center",width:'100px'},//病种类型
            { name: 'sampathologycode', index: 'sampathologycode',width:'100px', align: "center"},//病理号
            { name: 'samsenddoctorname', index: 'samsenddoctorname',width:'100px', align: "center"},//送检医生
            { name: 'samsendhospital', index: 'samsendhospital',width:'100px', align: "center"},//送检医院
            { name: 'sampatientname', index: 'sampatientname',width:'100px', align: "center"},//病人姓名
            { name: 'piedoctorname', index: 'piedoctorname',width:'100px', align: "center"},//取材医生
            { name: 'piesamplingtime', index: 'piesamplingtime',width:'100px', align: "center",formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))}}//取材时间
        ],
        gridComplete: function () {
        },
        loadComplete : function() {
            var table = this;
            setTimeout(function(){
                updatePagerIcons(table);
            }, 0);
        },
        ondblClickRow: function (id) {
            var rowData = $("#sysnew2").jqGrid('getRowData',id);
            location.href='../diagnosis/diagnosis.jsp?m=病理诊断&id='+ rowData.sampleid;
        },
        viewrecords: true,
        height:320,
        width:width,
        //autowidth: true,
        rowNum: 10,
        rowList:[10,20,30],
        rownumbers: true, // 显示行号
        rownumWidth: 35, // 行号宽度
        pager: "#syspager3"
    });

    /**
     * 系统未接收、未完成、未签收，未补取
     */
    $("#sysnew4").jqGrid({
        url: "../order/orderlist",
        mtype: "GET",
        datatype: "json",
        postData:{"req_sts":"0","req_code":"0"},
        colNames: ['ID', '病种类型','病理号', '医嘱号','病人姓名','申请医生','接收者','签收者'],
        colModel: [
            {name:'sampleid',hidden:true},//ID
            { name: 'sampathologyid', index: 'sampathologyid',formatter: "select", editoptions:{value:gettypes1()},align:"center",width:'100px'},//病种类型
            { name: 'sampathologycode', index: 'sampathologycode',width:'100px', align: "center"},//病理号
            { name: 'ordercode', index: 'ordercode',width:'100px', align: "center"},//医嘱号
            { name: 'sampatientname', index: 'sampatientname',width:'100px', align: "center"},//病人姓名
            { name: 'ordorderuser', index: 'ordorderuser',width:'100px', align: "center"},//申请医生
            { name: 'ordacceptorname', index: 'ordacceptorname',width:'100px', align: "center"},//接收者
            { name: 'ordorderuser', index: 'ordorderuser',width:'100px', align: "center"}//签收者
        ],
        gridComplete: function () {
        },
        loadComplete : function() {
            var table = this;
            setTimeout(function(){
                updatePagerIcons(table);
            }, 0);
        },
        ondblClickRow: function (id) {
            var rowData = $("#sysnew4").jqGrid('getRowData',id);
            location.href='../diagnosis/diagnosis.jsp?m=病理诊断&id='+ rowData.sampleid;
        },
        viewrecords: true,
        height:320,
        width:width,
        //autowidth: true,
        rowNum: 10,
        rowList:[10,20,30],
        rownumbers: true, // 显示行号
        rownumWidth: 35, // 行号宽度
        pager: "#syspager4"
    });
    /**
     * 我的未处理
     */
    $("#new1").jqGrid({
        url: "../pathologysample/sample/ajax/samplelist",
        mtype: "GET",
        datatype: "json",
        postData:{"req_sts":"3","patient_name":$("#local_userid").val()},
        colNames: ['ID', '病种类型','病理号', '送检医生','送检医院','病人姓名','取材医生','取材时间'],
        colModel: [
            {name:'sampleid',hidden:true},//ID
            { name: 'sampathologyid', index: 'sampathologyid',formatter: "select", editoptions:{value:gettypes1()},align:"center",width:'100px'},//病种类型
            { name: 'sampathologycode', index: 'sampathologycode',width:'100px', align: "center"},//病理号
            { name: 'samsenddoctorname', index: 'samsenddoctorname',width:'100px', align: "center"},//送检医生
            { name: 'samsendhospital', index: 'samsendhospital',width:'100px', align: "center"},//送检医院
            { name: 'sampatientname', index: 'sampatientname',width:'100px', align: "center"},//病人姓名
            { name: 'piedoctorname', index: 'piedoctorname',width:'100px', align: "center"},//取材医生
            { name: 'piesamplingtime', index: 'piesamplingtime',width:'100px', align: "center",formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))}}//取材时间
        ],
        gridComplete: function () {
        },
        loadComplete : function() {
            var table = this;
            setTimeout(function(){
                updatePagerIcons(table);
            }, 0);
        },
        ondblClickRow: function (id) {
            var rowData = $("#new1").jqGrid('getRowData',id);
            location.href='../diagnosis/diagnosis.jsp?m=病理诊断&id='+ rowData.sampleid;
        },
        viewrecords: true,
        height:320,
        width:width,
        //autowidth: true,
        rowNum: 10,
        rowList:[10,20,30],
        rownumbers: true, // 显示行号
        rownumWidth: 35, // 行号宽度
        pager: "#pager1"
    });
    /**
     * 未签收，未补取
     */
    $("#new2_1").jqGrid({
        url: "../order/orderlist",
        mtype: "GET",
        datatype: "json",
        postData:{"req_sts":"2","patient_name":$("#local_userid").val()},
        colNames: ['ID', '病种类型','病理号', '医嘱号','病人姓名','申请医生','接收者','签收者'],
        colModel: [
            {name:'sampleid',hidden:true},//ID
            { name: 'sampathologyid', index: 'sampathologyid',formatter: "select", editoptions:{value:gettypes1()},align:"center",width:'100px'},//病种类型
            { name: 'sampathologycode', index: 'sampathologycode',width:'100px', align: "center"},//病理号
            { name: 'ordercode', index: 'ordercode',width:'100px', align: "center"},//医嘱号
            { name: 'sampatientname', index: 'sampatientname',width:'100px', align: "center"},//病人姓名
            { name: 'ordorderuser', index: 'ordorderuser',width:'100px', align: "center"},//申请医生
            { name: 'ordacceptorname', index: 'ordacceptorname',width:'100px', align: "center"},//接收者
            { name: 'ordorderuser', index: 'ordorderuser',width:'100px', align: "center"}//签收者
        ],
        gridComplete: function () {
        },
        loadComplete : function() {
            var table = this;
            setTimeout(function(){
                updatePagerIcons(table);
            }, 0);
        },
        ondblClickRow: function (id) {
            var rowData = $("#new2_1").jqGrid('getRowData',id);
            location.href='../diagnosis/diagnosis.jsp?m=病理诊断&id='+ rowData.sampleid;
        },
        viewrecords: true,
        height:320,
        width:width,
        //autowidth: true,
        rowNum: 10,
        rowList:[10,20,30],
        rownumbers: true, // 显示行号
        rownumWidth: 35, // 行号宽度
        pager: "#pager2_1"
    });
    /**
     * 我的会诊
     */
    $("#new2").jqGrid({
        url: "../consdetail/detail/ajax/sample",
        mtype: "GET",
        datatype: "json",
        postData:{"req_sts":"0","req_bf_time":$("#req_bf_time2").val(),"req_af_time":$("#req_af_time2").val(),"patient_name":$("#local_userid").val()},
        colNames: ['ID','样本ID', '病理号', '发起人','发起时间','会诊状态'],
        colModel: [
            {name:'consultationid',hidden:true},//ID
            {name:'consampleid',hidden:true},//样本ID
            { name: 'conpathologycode', index: 'conpathologycode',width:'100px', align: "center"},//病理号
            { name: 'consponsoredusername', index: 'consponsoredusername',width:'100px', align: "center"},//发起人
            { name: 'consponsoredtime', index: 'consponsoredtime',width:'100px', align: "center",formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))}},//发起时间
            { name: 'conconsultationstate',index: 'conconsultationstate',width:'100px',align:"center",formatter:"select",editoptions:{value:"0:会诊中;1:会诊终了;2:会诊取消"}}//会诊状态
        ],
        gridComplete: function () {
        },
        loadComplete : function() {
            var table = this;
            setTimeout(function(){
                updatePagerIcons(table);
            }, 0);
        },
        ondblClickRow: function (id) {
            var rowData = $("#new2").jqGrid('getRowData',id);
            location.href='../consultation/cons.jsp?m=会诊管理&id='+ rowData.consampleid;
        },
        viewrecords: true,
        height:320,
        width:width,
        //autowidth: true,
        rowNum: 10,
        rowList:[10,20,30],
        rownumbers: true, // 显示行号
        rownumWidth: 35, // 行号宽度
        pager: "#pager2"
    });
    /**
     * 我的留言
     */
    $("#new3").jqGrid({
        url: "../receive/receive/ajax/sample",
        mtype: "GET",
        datatype: "json",
        postData:{"req_sts":"0","req_bf_time":$("#req_bf_time3").val(),"req_af_time":$("#req_af_time3").val(),"patient_name":$("#local_userid").val()},
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
            viewSample(3,id);
        },
        viewrecords: true,
        height:320,
        width:width,
        //autowidth: true,
        rowNum: 10,
        rowList:[10,20,30],
        rownumbers: true, // 显示行号
        rownumWidth: 35, // 行号宽度
        pager: "#pager3"
    });
    /**
     * 我发起的会诊
     */
    $("#new6").jqGrid({
        url: "../consultation/cons/ajax/sample",
        mtype: "GET",
        datatype: "json",
        postData:{"req_sts":"0","req_bf_time":$("#req_bf_time6").val(),"req_af_time":$("#req_af_time6").val(),"patient_name":$("#local_userid").val()},
        colNames: ['ID','样本ID',
            '病理号', '发起人','发起时间','会诊状态'],
        colModel: [
            {name:'consultationid',hidden:true},//ID
            {name:'consampleid',hidden:true},//样本ID
            { name: 'conpathologycode', index: 'conpathologycode',width:'100px', align: "center"},//病理号
            { name: 'consponsoredusername', index: 'consponsoredusername',width:'100px', align: "center"},//发起人
            { name: 'consponsoredtime', index: 'consponsoredtime',width:'100px', align: "center",formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))}},//发起时间
            { name: 'conconsultationstate',index: 'conconsultationstate',width:'100px',align:"center",formatter:"select",editoptions:{value:"0:会诊中;1:会诊终了;2:会诊取消"}}//会诊状态
        ],
        gridComplete: function () {
        },
        loadComplete : function() {
            var table = this;
            setTimeout(function(){
                updatePagerIcons(table);
            }, 0);
        },
        ondblClickRow: function (id) {
            var rowData = $("#new6").jqGrid('getRowData',id);
            location.href='../consultation/cons.jsp?m=会诊管理&id='+ rowData.consampleid;
        },
        viewrecords: true,
        height:320,
        width:width,
        //autowidth: true,
        rowNum: 10,
        rowList:[10,20,30],
        rownumbers: true, // 显示行号
        rownumWidth: 35, // 行号宽度
        pager: "#pager6"
    });
    /**
     * 我发起的留言
     */
    $("#new7").jqGrid({
        url: "../message/message/ajax/sample",
        mtype: "GET",
        datatype: "json",
        postData:{"req_bf_time":$("#req_bf_time7").val(),"req_af_time":$("#req_af_time7").val(),"patient_name":$("#local_userid").val()},
        colNames: ['消息ID','消息内容', '发布用户', '发布时间'],
        colModel: [
            {name:'messageid',hidden:true},
            { name: 'mescontent', index: 'mescontent'},
            { name: 'messendername', index: 'messendername'},
            { name: 'meshandletime', index: 'meshandletime',formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))}}
        ],
        loadComplete : function() {
            var table = this;
            setTimeout(function(){
                updatePagerIcons(table);
            }, 0);
        },
        ondblClickRow: function (id) {
            viewSample(7,id);
        },
        viewrecords: true,
        height:320,
        width:width,
        //autowidth: true,
        rowNum: 10,
        rowList:[10,20,30],
        rownumbers: true, // 显示行号
        rownumWidth: 35, // 行号宽度
        pager: "#pager7"
    });
});

/**
 * 查看消息
 * @returns {boolean}
 */
function viewSample(num,id) {
    var clearid = "#sampleForm3";
    clearData(clearid);
    var rowData = $("#new"+ num).jqGrid('getRowData',id);
    if (id == null || id == 0) {
        layer.msg('请先选择要查看的数据', {icon: 2, time: 1000});
        return false;
    }
    $.get("../message/message/get",{id:rowData.messageid},function(data) {
        if(data != "") {
            if(num == 3){
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
            }
            $("#mescontent").val(data.mescontent);
            $("#messendername").val(data.messendername);
            $("#meshandletime").val(CurentTime(new Date(data.meshandletime)));
            if(num == 3){
                searchList(num);
            }
        } else {
            layer.msg("该申请单不存在！", {icon: 0, time: 1000});
        }
    });
    layer.open({
        type: 1,
        area: ['800px','300px'],
        skin: 'layui-layer-molv',
        fix: false, //不固定
        maxmin: false,
        shade:0.6,
        title: "消息信息",
        content: $("#formDialog3")
    });
}

/**
 * 清除数据
 */
function clearData(id) {
    $(id)[0].reset();
}

/**
 * 查询数据
 */
function searchList(num) {
    var req_bf_time = $('#req_bf_time' + num).val();
    var req_af_time = $('#req_af_time' + num).val();
    var req_sts = $("#req_sts" + num).val();
    var patient_name = $("#local_userid").val();
    jQuery("#new" + num).jqGrid("clearGridData");
    if(num == 2){
        jQuery("#new" + num).jqGrid('setGridParam',{
            url: "../consdetail/detail/ajax/sample",
            //发送数据
            postData : {"req_bf_time":req_bf_time,"req_af_time":req_af_time,"req_sts":req_sts,"patient_name":patient_name},
            page : 1
        }).trigger('reloadGrid');//重新载入
    }else if(num == 3){
        jQuery("#new" + num).jqGrid('setGridParam',{
            url: "../receive/receive/ajax/sample",
            //发送数据
            postData : {"req_bf_time":req_bf_time,"req_af_time":req_af_time,"req_sts":req_sts,"patient_name":patient_name},
            page : 1
        }).trigger('reloadGrid');//重新载入
    }else if(num == 4){

    }else if(num == 5){

    }else if(num == 6){
        jQuery("#new" + num).jqGrid('setGridParam',{
            url: "../consultation/cons/ajax/sample",
            //发送数据
            postData : {"req_bf_time":req_bf_time,"req_af_time":req_af_time,"req_sts":req_sts,"patient_name":patient_name},
            page : 1
        }).trigger('reloadGrid');//重新载入
    }else if(num == 7){
        jQuery("#new" + num).jqGrid('setGridParam',{
            url: "../message/message/ajax/sample",
            //发送数据
            postData : {"req_bf_time":req_bf_time,"req_af_time":req_af_time,"patient_name":patient_name},
            page : 1
        }).trigger('reloadGrid');//重新载入
    }

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