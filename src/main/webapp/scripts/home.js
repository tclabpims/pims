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
            $("#show_div_"+ $(this).attr("href")+"_"+ $(this).attr("href")).css('display','none');
            $(this).css("border-bottom","0px");
        });
        e.preventDefault();//阻止a链接的跳转行为
        $("#show_div_"+ $(this).attr("href")).css('display','block');
        $("#show_div_"+ $(this).attr("href")+"_"+ $(this).attr("href")).css('display','block');
        $(this).css("border-bottom","4px solid #0FCFA0");
        $(this).tab('show');//显示当前选中的链接及关联的content
    });
    /**
     * 我的未处理工作
     */
    $('#show_div_1 a').click(function (e) {
        var hrefval = $(this).attr("href");
        jQuery("#new1").jqGrid("clearGridData");
        jQuery("#new1").jqGrid('setGridParam',{
            url: "../pathologysample/sample/ajax/sample",
            //发送数据
            postData : {"req_code":hrefval},
            page : 1
        }).trigger('reloadGrid');//重新载入
        e.preventDefault();
    });

    var width = $("#tabs").width();
    /**
     * 系统未处理
     */
    $("#new").jqGrid({
        url: "../pathologysample/sample/ajax/sample",
        mtype: "GET",
        datatype: "json",
        postData:{"req_code":"3"},
        colNames: ['ID','详细', '病种类型','病理号', '送检医生','送检医院','病人姓名'],
        colModel: [
            {name:'requisitionid',hidden:true},//ID
            { name: 'showinfo', index: 'showinfo', sortable: false, align: "center", width: "70px" },//详细
            { name: 'sampathologyid', index: 'sampathologyid',formatter: "select", editoptions:{value:gettypes1()},align:"center",width:'100px'},//病种类型
            { name: 'sampathologycode', index: 'sampathologycode',width:'100px', align: "center"},//病理号
            { name: 'samsenddoctorname', index: 'samsenddoctorname',width:'100px', align: "center"},//送检医生
            { name: 'samsendhospital', index: 'samsendhospital',width:'100px', align: "center"},//送检医院
            { name: 'sampatientname', index: 'sampatientname',width:'100px', align: "center"}//病人姓名
        ],
        gridComplete: function () {
            var ids = jQuery("#new").jqGrid('getDataIDs');
            if(ids > 0){
                for (var i = 0; i < ids.length; i++) {
                    var id = ids[i];
                    var showinfo = "<button style='border-radius:5px;border:1px solid #C2C2C2;' onclick='showInfo("+id+")' >详情</button>";
                    jQuery("#new").jqGrid('setRowData', ids[i], { showinfo: showinfo });
                }
            }
        },
        loadComplete : function() {
            var table = this;
            setTimeout(function(){
                updatePagerIcons(table);
            }, 0);
        },
        ondblClickRow: function (id) {
            // viewSample();
        },
        viewrecords: true,
        height:300,
        width:width,
        //autowidth: true,
        rowNum: 10,
        rowList:[10,20,30],
        rownumbers: true, // 显示行号
        rownumWidth: 35, // 行号宽度
        pager: "#pager"
    });
    /**
     * 我的未处理
     */
    $("#new1").jqGrid({
        url: "../pathologysample/sample/ajax/sample",
        mtype: "GET",
        datatype: "json",
        postData:{"req_code":"3"},
        colNames: ['ID','详细', '病种类型','病理号', '送检医生','送检医院','病人姓名'],
        colModel: [
            {name:'requisitionid',hidden:true},//ID
            { name: 'showinfo', index: 'showinfo', sortable: false, align: "center", width: "70px" },//详细
            { name: 'sampathologyid', index: 'sampathologyid',formatter: "select", editoptions:{value:gettypes1()},align:"center",width:'100px'},//病种类型
            { name: 'sampathologycode', index: 'sampathologycode',width:'100px', align: "center"},//病理号
            { name: 'samsenddoctorname', index: 'samsenddoctorname',width:'100px', align: "center"},//送检医生
            { name: 'samsendhospital', index: 'samsendhospital',width:'100px', align: "center"},//送检医院
            { name: 'sampatientname', index: 'sampatientname',width:'100px', align: "center"}//病人姓名
        ],
        gridComplete: function () {
            var ids = jQuery("#new1").jqGrid('getDataIDs');
            if(ids > 0){
                for (var i = 0; i < ids.length; i++) {
                    var id = ids[i];
                    var showinfo = "<button style='border-radius:5px;border:1px solid #C2C2C2;' onclick='showInfo("+id+")' >详情</button>";
                    jQuery("#new1").jqGrid('setRowData', ids[i], { showinfo: showinfo });
                }
            }
        },
        loadComplete : function() {
            var table = this;
            setTimeout(function(){
                updatePagerIcons(table);
            }, 0);
        },
        ondblClickRow: function (id) {
            // viewSample();
        },
        viewrecords: true,
        height:300,
        width:width,
        //autowidth: true,
        rowNum: 10,
        rowList:[10,20,30],
        rownumbers: true, // 显示行号
        rownumWidth: 35, // 行号宽度
        pager: "#pager1"
    });
    /**
     * 我的会诊
     */
    $("#new2").jqGrid({
        url: "../consdetail/detail/ajax/sample",
        mtype: "GET",
        datatype: "json",
        postData:{"req_sts":"0","req_bf_time":$("#req_bf_time2").val(),"req_af_time":$("#req_af_time2").val(),"send_hosptail":$("#local_userid").val()},
        colNames: ['ID','样本ID',
            // '详细',
            '病理号', '发起人','发起时间','会诊状态'],
        colModel: [
            {name:'consultationid',hidden:true},//ID
            {name:'consampleid',hidden:true},//样本ID
            // { name: 'showinfo', index: 'showinfo', sortable: false, align: "center", width: "70px" },//详细
            { name: 'conpathologycode', index: 'conpathologycode',width:'100px', align: "center"},//病理号
            { name: 'consponsoredusername', index: 'consponsoredusername',width:'100px', align: "center"},//发起人
            { name: 'consponsoredtime', index: 'consponsoredtime',width:'100px', align: "center",formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))}},//发起时间
            { name: 'conconsultationstate',index: 'conconsultationstate',width:'100px',align:"center",formatter:"select",editoptions:{value:"0:会诊中;1:会诊终了;2:会诊取消"}}//会诊状态
        ],
        gridComplete: function () {
            // var ids = jQuery("#new2").jqGrid('getDataIDs');
            // if(ids > 0){
            //     for (var i = 0; i < ids.length; i++) {
            //         var id = ids[i];
            //         var showinfo = "<button style='border-radius:5px;border:1px solid #C2C2C2;' onclick='showCons("+id+")' >详情</button>";
            //         jQuery("#new2").jqGrid('setRowData', ids[i], { showinfo: showinfo });
            //     }
            // }
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
        height:300,
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
            viewSample(id);
        },
        viewrecords: true,
        height:300,
        width:width,
        //autowidth: true,
        rowNum: 10,
        rowList:[10,20,30],
        rownumbers: true, // 显示行号
        rownumWidth: 35, // 行号宽度
        pager: "#pager3"
    });
});

/**
 * 查看消息
 * @returns {boolean}
 */
function viewSample(id) {
    var clearid = "#sampleForm3";
    clearData(clearid);
    var rowData = $("#new3").jqGrid('getRowData',id);
    if (id == null || id == 0) {
        layer.msg('请先选择要查看的数据', {icon: 2, time: 1000});
        return false;
    }
    $.get("../message/message/get",{id:rowData.messageid},function(data) {
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
            searchList(3);
        } else {
            layer.msg("该申请单不存在！", {icon: 0, time: 1000});
        }
    });
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
    jQuery("#new" + num).jqGrid("clearGridData");
    if(num == 2){
        jQuery("#new" + num).jqGrid('setGridParam',{
            url: "../consdetail/detail/ajax/sample",
            //发送数据
            postData : {"req_bf_time":req_bf_time,"req_af_time":req_af_time,"req_sts":req_sts,"send_hosptail":$("#local_userid").val()},
            page : 1
        }).trigger('reloadGrid');//重新载入
    }else if(num == 3){
        jQuery("#new" + num).jqGrid('setGridParam',{
            url: "../receive/receive/ajax/sample",
            //发送数据
            postData : {"req_bf_time":req_bf_time,"req_af_time":req_af_time,"req_sts":req_sts,"patient_name":$("#local_userid").val()},
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