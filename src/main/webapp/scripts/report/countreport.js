/**
 * 初始化
 */
$(function() {
    $(document).bind("keydown",function(e){
        e=window.event||e;
        if(e.keyCode==116){
            e.keyCode = 0;
            location.reload(true);
            return false;
        }
    });

	// $("#rztj1").attr('checked', 'checked');
	// $("#bbytj1").attr('checked', 'checked');
	// $("#sftj1").attr('checked', 'checked');
	// $("#gzltj1").attr('checked', 'checked');
	// $("#ltj1").attr('checked', 'checked');
	// $("#fltj1").attr('checked', 'checked');
	// $("#bbdjb1").attr('checked', 'checked');
	// $("#bgqsb1").attr('checked', 'checked');
	// $("#jbnlfb1").attr('checked', 'checked');
	// $("#bdzddz1").attr('checked', 'checked');
	// $("#hztj1").attr('checked', 'checked');
	$(".form_datetime").datetimepicker({
		minView: "month", //选择日期后，不会再跳转去选择时分秒
		format: "yyyy-mm-dd", //选择日期后，文本框显示的日期格式
		language: 'zh-CN', //汉化
		todayBtn:  1,
		autoclose:true //选择日期后自动关闭
	});
	var width1 = $("#div_2").width()-50;
	var req_bf_time = $('#req_bf_time').val();//统计年月
	var req_af_time = $('#req_af_time').val();//统计年月
	$("#rztj_new0").jqGrid({
		url: "../report/rztj",
		mtype: "GET",
		datatype: "json",
		postData:{"req_bf_time":req_bf_time,"req_af_time":req_af_time},
		colNames: ['已登记','已取材', '已包埋', '已切片','已初诊','已审核','已发送','会诊中','已打印'],
		colModel: [
			{name:'samsamplestatus0',index:'samsamplestatus0',align:'center'},//已登记
			{name:'samsamplestatus1',index:'samsamplestatus1',align:'center'},//已取材
			{name:'samsamplestatus2',index:'samsamplestatus2',align:'center'},//已包埋
			{name:'samsamplestatus3',index:'samsamplestatus3',align:'center'},//已切片
			{name:'samsamplestatus4',index:'samsamplestatus4',align:'center'},//已初诊
			{name:'samsamplestatus5',index:'samsamplestatus5',align:'center'},//已审核
			{name:'samsamplestatus6',index:'samsamplestatus6',align:'center'},//已发送
			{name:'samsamplestatus7',index:'samsamplestatus7',align:'center'},//会诊中
			{name:'samsamplestatus8',index:'samsamplestatus8',align:'center'}//已打印
		],
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		viewrecords: true,
		height:50,
		width: width1
	});
	$("#rztj_new1").jqGrid({
		url: "../report/rztjinfo",
		mtype: "GET",
		datatype: "json",
		postData:{"req_bf_time":req_bf_time,"req_af_time":req_af_time},
		colNames: ['病种类别','已登记','已取材', '已包埋', '已切片','已初诊','已审核','已发送','会诊中','已打印'],
		colModel: [
			{name:'sampathologyid',index:'sampathologyid',align:'center',formatter:'select',editoptions:{value:gettypes()}},//已登记
			{name:'samsamplestatus0',index:'samsamplestatus0',align:'center'},//已登记
			{name:'samsamplestatus1',index:'samsamplestatus1',align:'center'},//已取材
			{name:'samsamplestatus2',index:'samsamplestatus2',align:'center'},//已包埋
			{name:'samsamplestatus3',index:'samsamplestatus3',align:'center'},//已切片
			{name:'samsamplestatus4',index:'samsamplestatus4',align:'center'},//已初诊
			{name:'samsamplestatus5',index:'samsamplestatus5',align:'center'},//已审核
			{name:'samsamplestatus6',index:'samsamplestatus6',align:'center'},//已发送
			{name:'samsamplestatus7',index:'samsamplestatus7',align:'center'},//会诊中
			{name:'samsamplestatus8',index:'samsamplestatus8',align:'center'}//已打印
		],
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		viewrecords: true,
		height:130,
		width: width1
	});
	var width2 = width1/3;
	$("#bbytj_new0").jqGrid({
		url: "../report/bblytj",
		mtype: "GET",
		datatype: "json",
		postData:{"req_bf_time":req_bf_time,"req_af_time":req_af_time,"req_code":"1"},
		colNames: ['送检单位','数量'],
		colModel: [
			{name:'name',index:'name',align:'center'},//送检单位
			{name:'nums',index:'nums',align:'center'}//数量
		],
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		viewrecords: true,
		height:150,
		rownumbers: true, // 显示行号
		rownumWidth: 30, // the width of the row numbers columns
		width: width2
	});

	// var width2 = width1/3;
	// $("#bbytj_new0").jqGrid({
	// 	url: "../report/bblytj",
	// 	mtype: "GET",
	// 	datatype: "json",
	// 	postData:{"req_bf_time":req_bf_time,"req_af_time":req_af_time,"req_code":"1"},
	// 	colNames: ['送检单位','数量'],
	// 	colModel: [
	// 		{name:'name',index:'name',align:'center'},//送检单位
	// 		{name:'nums',index:'nums',align:'center'}//数量
	// 	],
	// 	loadComplete : function() {
	// 		var table = this;
	// 		setTimeout(function(){
	// 			updatePagerIcons(table);
	// 		}, 0);
	// 	},
	// 	viewrecords: true,
	// 	height:150,
	// 	rownumbers: true, // 显示行号
	// 	rownumWidth: 30, // the width of the row numbers columns
	// 	width: width2
	// });
	$("#bbytj_new1").jqGrid({
		url: "../report/bblytj",
		mtype: "GET",
		datatype: "json",
		postData:{"req_bf_time":req_bf_time,"req_af_time":req_af_time,"req_code":"2"},
		colNames: ['送检科室','数量'],
		colModel: [
			{name:'name',index:'name',align:'center'},//送检科室
			{name:'nums',index:'nums',align:'center'}//数量
		],
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		viewrecords: true,
		height:150,
		rownumbers: true, // 显示行号
		rownumWidth: 30, // the width of the row numbers columns
		width: width2
	});
	$("#bbytj_new2").jqGrid({
		url: "../report/bblytj",
		mtype: "GET",
		datatype: "json",
		postData:{"req_bf_time":req_bf_time,"req_af_time":req_af_time,"req_code":"3"},
		colNames: ['送检医生','数量'],
		colModel: [
			{name:'name',index:'name',align:'center'},//送检医生
			{name:'nums',index:'nums',align:'center'}//数量
		],
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		viewrecords: true,
		height:150,
		rownumbers: true, // 显示行号
		rownumWidth: 30, // the width of the row numbers columns
		width: width2
	});
	$("#sftj_new0").jqGrid({
		url: "../report/sftj",
		mtype: "GET",
		datatype: "json",
		postData:{"req_bf_time":req_bf_time,"req_af_time":req_af_time,"req_code":"1"},
		colNames: ['送检医生','金额'],
		colModel: [
			{name:'name',index:'name',align:'center'},//送检医生
			{name:'prices',index:'prices',align:'center'}//金额
		],
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		viewrecords: true,
		height:150,
		rownumbers: true, // 显示行号
		rownumWidth: 30, // the width of the row numbers columns
		width: width2
	});
	$("#sftj_new1").jqGrid({
		url: "../report/sftj",
		mtype: "GET",
		datatype: "json",
		postData:{"req_bf_time":req_bf_time,"req_af_time":req_af_time,"req_code":"2"},
		colNames: ['送检科室','金额'],
		colModel: [
			{name:'name',index:'name',align:'center'},//送检科室
			{name:'prices',index:'prices',align:'center'}//金额
		],
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		viewrecords: true,
		height:150,
		rownumbers: true, // 显示行号
		rownumWidth: 30, // the width of the row numbers columns
		width: width2
	});
	$("#sftj_new2").jqGrid({
		url: "../report/sftj",
		mtype: "GET",
		datatype: "json",
		postData:{"req_bf_time":req_bf_time,"req_af_time":req_af_time,"req_code":"3"},
		colNames: ['送检医生','金额'],
		colModel: [
			{name:'name',index:'name',align:'center'},//送检医生
			{name:'prices',index:'prices',align:'center'}//金额
		],
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		viewrecords: true,
		height:150,
		rownumbers: true, // 显示行号
		rownumWidth: 30, // the width of the row numbers columns
		width: width2
	});
	$("#sftj_new3").jqGrid({
		url: "../report/sftj",
		mtype: "GET",
		datatype: "json",
		postData:{"req_bf_time":req_bf_time,"req_af_time":req_af_time,"req_code":"4"},
		colNames: ['费用列表','金额'],
		colModel: [
			{name:'name',index:'name',align:'center'},//费用列表
			{name:'prices',index:'prices',align:'center'}//金额
		],
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		viewrecords: true,
		height:150,
		rownumbers: true, // 显示行号
		rownumWidth: 30, // the width of the row numbers columns
		width: width2
	});
	$("#sftj_new4").jqGrid({
		url: "../report/sftj",
		mtype: "GET",
		datatype: "json",
		postData:{"req_bf_time":req_bf_time,"req_af_time":req_af_time,"req_code":"5"},
		colNames: ['收费明细项目','金额'],
		colModel: [
			{name:'name',index:'name',align:'center'},//收费明细项目
			{name:'prices',index:'prices',align:'center'}//金额
		],
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		viewrecords: true,
		height:150,
		rownumbers: true, // 显示行号
		rownumWidth: 30, // the width of the row numbers columns
		width: width2
	});
	$("#sftj_new5").jqGrid({
		url: "../report/sftj",
		mtype: "GET",
		datatype: "json",
		postData:{"req_bf_time":req_bf_time,"req_af_time":req_af_time,"req_code":"6"},
		colNames: ['报告医生','金额'],
		colModel: [
			{name:'name',index:'name',align:'center'},//报告医生
			{name:'prices',index:'prices',align:'center'}//金额
		],
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		viewrecords: true,
		height:150,
		rownumbers: true, // 显示行号
		rownumWidth: 30, // the width of the row numbers columns
		width: width2
	});

	var width3=$("#gzltj").width();
	// $("#gzltj_new0").jqGrid({
     //   		url: "../report/rztjinfo",
     //   		mtype: "GET",
     //   		datatype: "json",
     //   		postData:{"req_bf_time":req_bf_time,"req_af_time":req_af_time},
     //   		colNames: ['医生姓名','取材','初查','审核'],
     //   		colModel: [
     //   			{name:'sampathologyid',index:'sampathologyid',align:'center',formatter:'select',editoptions:{value:gettypes()}},//已登记
     //   			{name:'samsamplestatus0',index:'samsamplestatus0',align:'center'},//已取材
     //   			{name:'samsamplestatus1',index:'samsamplestatus1',align:'center'},//已包埋
     //   			{name:'samsamplestatus2',index:'samsamplestatus2',align:'center'}//已切片
     //   		],
     //   		loadComplete : function() {
     //   			var table = this;
     //   			setTimeout(function(){
     //   				updatePagerIcons(table);
     //   			}, 0);
     //   		},
     //   		viewrecords: true,
     //   		height:130,
     //   		width: width3
	// });
	// alert(width3);
    // $("#gzltj_new1").jqGrid({
     //    url: "../report/rztjinfo",
     //    mtype: "GET",
     //    datatype: "json",
     //    postData:{"req_bf_time":req_bf_time,"req_af_time":req_af_time},
     //    colNames: ['技师姓名','包埋','切片'],
     //    colModel: [
     //        {name:'sampathologyid',index:'sampathologyid',align:'center',formatter:'select',editoptions:{value:gettypes()}},//已登记
     //        {name:'samsamplestatus0',index:'samsamplestatus0',align:'center'},//已取材
     //        {name:'samsamplestatus1',index:'samsamplestatus1',align:'center'}//已包埋
     //    ],
     //    loadComplete : function() {
     //        var table = this;
     //        setTimeout(function(){
     //            updatePagerIcons(table);
     //        }, 0);
     //    },
     //    viewrecords: true,
     //    height:130,
     //    width: width3
    // });
	var req_sts = 2;
    $("#bhg").jqGrid({
        //caption:"标本列表",
        url: "../pathologysample/sample/ajax/sample",
        mtype: "GET",
        datatype: "json",
        postData:{"req_bf_time":req_bf_time,"req_af_time":req_af_time,"req_sts":2},
        colNames: ['ID', '病理编号','患者姓名','送检单位','送检科室', '送检医生','申请时间','合格状态','病理状态','性别','年龄','年龄类型','临床诊断','送检时间','登记时间'],
        colModel: [
            {name:'sampleid'},
            { name: 'sampathologycode', index: 'sampathologycode',width:'120px', align: "center"},
            { name: 'sampatientname', index: 'sampatientname',width:'100px', align: "center"},
            { name: 'samsendhospital', index: 'samsendhospital',width:'150px', align: "center"},
            { name: 'samdeptname', index: 'samdeptname',width:'170px', align: "center"},
            { name: 'samsenddoctorname', index: 'samsenddoctorname',width:'100px', align: "center"},
            {name:'samreqtime',formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))}},
            { name: 'samsecondv', index: 'samsecondv',width:'100px', align: "center",formatter:"select",editoptions:{value:"1:合格;2:不合格"}},
            { name: 'samsamplestatus', index: 'samsamplestatus',formatter: "select", editoptions:{value:"0:已登记;1:已取材;2:已包埋;3:已切片;4:已初诊;5:已审核;" +
            "6:已发送;7:会诊中:8:报告已打印"},width:'100px', align: "center"},
            {name:'sampatientsex',formatter:"select",editoptions:{value:"1:男;2:女;3::未知"}},
            {name:'sampatientage'},
            {name:'sampatientagetype',hidden:true},
            {name:'sampatientdignoses'},
            {name:'samsendtime',formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))}},
            {name:'samregisttime',formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))}},
        ],
        beforeSelectRow: function (rowid, e) {
            return $(e.target).is('input[type=checkbox]');
        },
        loadComplete : function() {
            var table = this;
            setTimeout(function(){
                updatePagerIcons(table);
            }, 0);
            // var ids = $("#new").jqGrid('getDataIDs');
            // if(ids != null && ids != ""){
            //     nowrow = "1";
            // 	fillInfo1(1);
            // }

            //$("#new").setSelection(1);
        },
        // gridComplete:function(){
        //     getColor();
        //     //$("#pager").children().children().children().children().attr("id").remove();
        //     var rowIds = $("#new").jqGrid('getDataIDs');
        //     for(var k = 0; k<rowIds.length; k++) {
        //         var rowData = $("#new").jqGrid('getRowData', rowIds[k]);
        //         if (rowData.samsamplestatus == '1') {
        //             //获取每行下的TD更改CSS
        //             //第一种写法
        //             // alert($("#"+rowIds[k]).parent().parent().attr("id"));
        //             //$("#"+rowIds[k]).css("background-color", "red");
        //             $("#new").children().children("tr[id='"+rowIds[k]+"']").children("td").eq(0).css("background-color","red");
        //             // $("#"+rowIds[k]).children("td").eq(2).css("background-color", "red");
        //             //$("#"+rowIds[k]).find("tr").css("background-color", "red");
        //         }
        //     }
        // },
        ondblClickRow: function (id) {
            // fillInfo1(id);
        },
        onCellSelect:function(id){
            // fillInfo1(id);
        },
        // onSelectRow:function(id){
        // 	fillInfo1(id);
        // },
        multiselect: true,
        viewrecords: true,
        height:150,
        width:width1,
        shrinkToFit:false,
        autoScroll: true,
        //autowidth: true,
        rowNum: 500,
        rowList:[500,1000,1500],
        rownumbers: true, // 显示行号
        rownumWidth: 30, // the width of the row numbers columns
        pager: "#pager"
    });

    $("#bldjb").jqGrid({
        url: "../report/diagnosisreport/list",
        mtype: "GET",
        datatype: "json",
        postData:{"req_bf_time":req_bf_time,
            "req_af_time":req_af_time,
            "req_sts":5},
        colNames: ['标本ID','病种类别', '病理号', '病人姓名','年龄','住院号','床号','性别','病理状态','送检日期','诊断日期','送检医生','送检科室','送检医院','取材医生',
            '切片医生','诊断医生','病理诊断','免疫组化','特殊染色','分子病理'],
        colModel: [
            {name:'sampleid',hidden:true},//标本ID
            { name: 'sampathologyid', index: 'sampathologyid',formatter: "select", editoptions:{value:gettypes()},width:'100px'},//病种类别
            { name: 'sampathologycode', index: 'sampathologycode',width:'100px'},//病理号
            { name: 'sampatientname', index: 'sampatientname',width:'100px'},//病人姓名
            { name: 'sampatientage', index: 'sampatientage',formatter:function(cellvalue, options, row){
                var cell1 = cellvalue.charAt(cellvalue.length - 1);
                if(cell1 == "1"){
                    cell1="岁";
                }else if(cell1 == "2"){
                    cell1="月";
                }else if(cell1 == "4"){
                    cell1="周";
                }else if(cell1 == "5"){
                    cell1="日";
                }else if(cell1 == "6"){
                    cell1="小时";
                }
                return cellvalue.substr(0,cellvalue.length-1)+cell1;
            },width:'100px'},//年龄
            { name: 'sampatientnumber', index: 'sampatientnumber',width:'100px'},//住院号
            { name: 'sampatientbed', index: 'sampatientbed',width:'100px'},//床号
            { name: 'sampatientsex', index: 'sampatientsex',formatter: "select", editoptions:{value:"0:男;1:女;2:未知"},width:'100px'},//性别
            { name: 'samsamplestatus', index: 'samsamplestatus',formatter: "select", editoptions:{value:"0:未取材;1:已取材;2:已包埋;3:已切片;4:已初诊;" +
            "5:已审核;6:已发送;7:会诊中;8:报告已打印"},width:'100px'},//病理状态
            { name: 'samsendtime', index: 'samsendtime',formatter:function(cellvalue, options, row)
            {if(cellvalue == "" || cellvalue == null){return ""}return CurentTime(new Date(cellvalue))},width:'100px'},//送检日期
            { name: 'saminitiallytime', index: 'saminitiallytime',formatter:function(cellvalue, options, row)
            {if(cellvalue == "" || cellvalue == null){return ""}return CurentTime(new Date(cellvalue))},width:'100px'},//诊断日期
            { name: 'samsenddoctorname', index: 'samsenddoctorname',width:'100px'},//送检医生
            { name: 'samdeptname', index: 'samdeptname',width:'100px'},//送检科室
            { name: 'samsendhospital', index: 'samsendhospital',width:'100px'},//送检医院
            { name: 'piedoctorname', index: 'piedoctorname',width:'100px'},//取材医生
            { name: 'parsectioneddoctor', index: 'parsectioneddoctor',width:'100px'},//切片医生
            { name: 'saminitiallyusername', index: 'saminitiallyusername',width:'100px'},//诊断医生
            { name: 'restestresult', index: 'restestresult',width:'100px'},//病理诊断
            { name: 'myzhnum', index: 'myzhnum',formatter:function(cellvalue, options, row){if(cellvalue > 0){return "有"}return "无"},width:'100px'},//免疫组化
            { name: 'tsrsnum', index: 'tsrsnum',formatter:function(cellvalue, options, row){if(cellvalue > 0){return "有"}return "无"},width:'100px'},//特殊染色
            { name: 'fzblnum', index: 'fzblnum',formatter:function(cellvalue, options, row){if(cellvalue > 0){return "有"}return "无"},width:'100px'}//分子病理
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
        // onSelectRow:function(id){
        // 	fillInfo();
        // },
        onCellSelect:function(id){
            fillInfo(id);
        },
        multiselect: true,
        viewrecords: true,
        height:150,
        width: width1,
        shrinkToFit:false,
        autoScroll: true,
        rowNum: 100,
        rowList:[100,200,300,400,500],
        rownumbers: true, // 显示行号
        rownumWidth: 30, // the width of the row numbers columns
        pager: "#pager"
    });
    $("#gzl_new0").jqGrid({
        url: "../report/qcgzl",
        mtype: "GET",
        datatype: "json",
        postData:{"req_bf_time":req_bf_time,"req_af_time":req_af_time},
        colNames: ['取材医生','工作量'],
        colModel: [
            {name:'name',index:'name',align:'center'},//送检医生
            {name:'num',index:'prices',align:'center'}//金额
        ],
        loadComplete : function() {
            var table = this;
            setTimeout(function(){
                updatePagerIcons(table);
            }, 0);
        },
        viewrecords: true,
        height:150,
        rownumbers: true, // 显示行号
        rownumWidth: 30, // the width of the row numbers columns
        width: width2
    });
    $("#gzl_new1").jqGrid({
        url: "../report/ccgzl",
        mtype: "GET",
        datatype: "json",
        postData:{"req_bf_time":req_bf_time,"req_af_time":req_af_time},
        colNames: ['初查医生','工作量'],
        colModel: [
            {name:'name',index:'name',align:'center'},//送检医生
            {name:'num',index:'num',align:'center'}//金额
        ],
        loadComplete : function() {
            var table = this;
            setTimeout(function(){
                updatePagerIcons(table);
            }, 0);
        },
        viewrecords: true,
        height:150,
        rownumbers: true, // 显示行号
        rownumWidth: 30, // the width of the row numbers columns
        width: width2
    });
    $("#gzl_new2").jqGrid({
        url: "../report/scgzl",
        mtype: "GET",
        datatype: "json",
        postData:{"req_bf_time":req_bf_time,"req_af_time":req_af_time},
        colNames: ['审查医生','工作量'],
        colModel: [
            {name:'name',index:'name',align:'center'},//送检医生
            {name:'num',index:'num',align:'center'}//金额
        ],
        loadComplete : function() {
            var table = this;
            setTimeout(function(){
                updatePagerIcons(table);
            }, 0);
        },
        viewrecords: true,
        height:150,
        rownumbers: true, // 显示行号
        rownumWidth: 30, // the width of the row numbers columns
        width: width2
    });
    $("#gzl_new3").jqGrid({
        url: "../report/Bmgzl",
        mtype: "GET",
        datatype: "json",
        postData:{"req_bf_time":req_bf_time,"req_af_time":req_af_time},
        colNames: ['包埋技师','工作量'],
        colModel: [
            {name:'name',index:'name',align:'center'},//送检医生
            {name:'num',index:'num',align:'center'}//金额
        ],
        loadComplete : function() {
            var table = this;
            setTimeout(function(){
                updatePagerIcons(table);
            }, 0);
        },
        viewrecords: true,
        height:150,
        rownumbers: true, // 显示行号
        rownumWidth: 30, // the width of the row numbers columns
        width: width2
    });
    $("#gzl_new4").jqGrid({
        url: "../report/Qpgzl",
        mtype: "GET",
        datatype: "json",
        postData:{"req_bf_time":req_bf_time,"req_af_time":req_af_time},
        colNames: ['切片技师','工作量'],
        colModel: [
            {name:'name',index:'name',align:'center'},//送检医生
            {name:'num',index:'num',align:'center'}//金额
        ],
        loadComplete : function() {
            var table = this;
            setTimeout(function(){
                updatePagerIcons(table);
            }, 0);
        },
        viewrecords: true,
        height:150,
        rownumbers: true, // 显示行号
        rownumWidth: 30, // the width of the row numbers columns
        width: width2
    });
    $("#ksgzl_new").jqGrid({
        url: "../report/ksgzl",
        mtype: "GET",
        datatype: "json",
        postData:{"req_bf_time":req_bf_time,"req_af_time":req_af_time},
        colNames: ['科室','免疫组化','特殊染色','分子病理'],
        colModel: [
            {name:'id',index:'id',align:'center'},//送检医生
            {name:'myzh',index:'myzh',align:'center'},//送检医生
            {name:'tsrs',index:'tsrs',align:'center'},//送检医生
            {name:'fzbl',index:'fzbl',align:'center'}//金额
        ],
        loadComplete : function() {
            var table = this;
            setTimeout(function(){
                updatePagerIcons(table);
            }, 0);
        },
        viewrecords: true,
        height:150,
        rownumbers: true, // 显示行号
        rownumWidth: 30, // the width of the row numbers columns
        width: width2
    });

    $("#bdjg_new0").jqGrid({
        url: "../report/jgdz",
        mtype: "GET",
        datatype: "json",
        postData:{"req_bf_time":req_bf_time,"req_af_time":req_af_time},
        colNames: ['患者姓名','部位','常规病理诊断结果','常规诊断时间','部位','冰冻病理诊断结果','冰冻诊断时间'],
        colModel: [
            {name:'sampatientname1',index:'sampatientname1',align:'center'},//送检医生
            {name:'samplename1',index:'samplename1',align:'center'},//送检医生
            {name:'restestresult1',index:'result1',align:'center'},//送检医生
            {name:'resinputtime1',index:'time1',align:'center'},//送检医生
            {name:'samplename2',index:'samplename2',align:'center'},//送检医生
            {name:'restestresult2',index:'result2',align:'center'},//送检医生
            {name:'resinputtime2',index:'time2',align:'center'}//金额
        ],
        loadComplete : function() {
            var table = this;
            setTimeout(function(){
                updatePagerIcons(table);
            }, 0);
        },
        viewrecords: true,
        height:150,
        rownumbers: true, // 显示行号
        rownumWidth: 30, // the width of the row numbers columns
        width: width1
    });

    $("#bgqsd").jqGrid({
        url: "../report/diagnosisreport/list",
        mtype: "GET",
        datatype: "json",
        postData:{"req_bf_time":req_bf_time,
            "req_af_time":req_af_time,
            "req_sts":6},
        colNames: ['标本ID','病种类别', '病理号', '病人姓名','年龄','住院号','床号','性别','病理状态','送检日期','诊断日期','送检医生','送检科室','送检医院','取材医生',
            '切片医生','诊断医生','病理诊断','免疫组化','特殊染色','分子病理'],
        colModel: [
            {name:'sampleid',hidden:true},//标本ID
            { name: 'sampathologyid', index: 'sampathologyid',formatter: "select", editoptions:{value:gettypes()},width:'100px'},//病种类别
            { name: 'sampathologycode', index: 'sampathologycode',width:'100px'},//病理号
            { name: 'sampatientname', index: 'sampatientname',width:'100px'},//病人姓名
            { name: 'sampatientage', index: 'sampatientage',formatter:function(cellvalue, options, row){
                var cell1 = cellvalue.charAt(cellvalue.length - 1);
                if(cell1 == "1"){
                    cell1="岁";
                }else if(cell1 == "2"){
                    cell1="月";
                }else if(cell1 == "4"){
                    cell1="周";
                }else if(cell1 == "5"){
                    cell1="日";
                }else if(cell1 == "6"){
                    cell1="小时";
                }
                return cellvalue.substr(0,cellvalue.length-1)+cell1;
            },width:'100px'},//年龄
            { name: 'sampatientnumber', index: 'sampatientnumber',width:'100px'},//住院号
            { name: 'sampatientbed', index: 'sampatientbed',width:'100px'},//床号
            { name: 'sampatientsex', index: 'sampatientsex',formatter: "select", editoptions:{value:"0:男;1:女;2:未知"},width:'100px'},//性别
            { name: 'samsamplestatus', index: 'samsamplestatus',formatter: "select", editoptions:{value:"0:未取材;1:已取材;2:已包埋;3:已切片;4:已初诊;" +
            "5:已审核;6:已发送;7:会诊中;8:报告已打印"},width:'100px'},//病理状态
            { name: 'samsendtime', index: 'samsendtime',formatter:function(cellvalue, options, row)
            {if(cellvalue == "" || cellvalue == null){return ""}return CurentTime(new Date(cellvalue))},width:'100px'},//送检日期
            { name: 'saminitiallytime', index: 'saminitiallytime',formatter:function(cellvalue, options, row)
            {if(cellvalue == "" || cellvalue == null){return ""}return CurentTime(new Date(cellvalue))},width:'100px'},//诊断日期
            { name: 'samsenddoctorname', index: 'samsenddoctorname',width:'100px'},//送检医生
            { name: 'samdeptname', index: 'samdeptname',width:'100px'},//送检科室
            { name: 'samsendhospital', index: 'samsendhospital',width:'100px'},//送检医院
            { name: 'piedoctorname', index: 'piedoctorname',width:'100px'},//取材医生
            { name: 'parsectioneddoctor', index: 'parsectioneddoctor',width:'100px'},//切片医生
            { name: 'saminitiallyusername', index: 'saminitiallyusername',width:'100px'},//诊断医生
            { name: 'restestresult', index: 'restestresult',width:'100px'},//病理诊断
            { name: 'myzhnum', index: 'myzhnum',formatter:function(cellvalue, options, row){if(cellvalue > 0){return "有"}return "无"},width:'100px'},//免疫组化
            { name: 'tsrsnum', index: 'tsrsnum',formatter:function(cellvalue, options, row){if(cellvalue > 0){return "有"}return "无"},width:'100px'},//特殊染色
            { name: 'fzblnum', index: 'fzblnum',formatter:function(cellvalue, options, row){if(cellvalue > 0){return "有"}return "无"},width:'100px'}//分子病理
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
        // onSelectRow:function(id){
        // 	fillInfo();
        // },
        onCellSelect:function(id){
            fillInfo(id);
        },
        multiselect: true,
        viewrecords: true,
        height:150,
        width: width1,
        shrinkToFit:false,
        autoScroll: true,
        rowNum: 100,
        rowList:[100,200,300,400,500],
        rownumbers: true, // 显示行号
        rownumWidth: 30, // the width of the row numbers columns
        pager: "#pager"
    });

    $(".sevenday").html($("#req_bf_time").val());
    $(".receivetime").html($("#req_af_time").val());
});
/**
 * 查询数据
 */
function searchList() {
	var req_bf_time = $("#req_bf_time").val();//送检FROM
	var req_af_time = $("#req_af_time").val();//送检TO

    $(".sevenday").html($("#req_bf_time").val());
    $(".receivetime").html($("#req_af_time").val());
    // alert(req_af_time);
    // alert(req_bf_time);
	// document.getElementById("sevenday")=$("#req_bf_time");
	var req_sts = 2;
	jQuery("#rztj_new0").jqGrid("clearGridData");
	jQuery("#rztj_new0").jqGrid('setGridParam',{
		url: "../report/rztj",
		//发送数据
        mtype: "GET",
        datatype: "json",
		postData : {"req_bf_time":req_bf_time,"req_af_time":req_af_time}
	}).trigger('reloadGrid');//重新载入
	jQuery("#rztj_new1").jqGrid("clearGridData");
	jQuery("#rztj_new1").jqGrid('setGridParam',{
		url: "../report/rztjinfo",
		//发送数据
        mtype: "GET",
        datatype: "json",
		postData : {"req_bf_time":req_bf_time,"req_af_time":req_af_time}
	}).trigger('reloadGrid');//重新载入

	jQuery("#bbytj_new0").jqGrid("clearGridData");
    jQuery("#bbytj_new0").jqGrid('setGridParam',{
    	url: "../report/bblytj",
    //发送数据
        mtype: "GET",
        datatype: "json",
    	postData : {"req_bf_time":req_bf_time,"req_af_time":req_af_time,"req_code":"1"}
    }).trigger('reloadGrid');//重新载入
    jQuery("#bbytj_new1").jqGrid("clearGridData");
    jQuery("#bbytj_new1").jqGrid('setGridParam',{
    	url: "../report/bblytj",
    //发送数据
        mtype: "GET",
        datatype: "json",
    	postData : {"req_bf_time":req_bf_time,"req_af_time":req_af_time,"req_code":"2"}
    }).trigger('reloadGrid');//重新载入
    jQuery("#bbytj_new2").jqGrid("clearGridData");
    jQuery("#bbytj_new2").jqGrid('setGridParam',{
    	url: "../report/bblytj",
    //发送数据
        mtype: "GET",
        datatype: "json",
    	postData : {"req_bf_time":req_bf_time,"req_af_time":req_af_time,"req_code":"3"}
    }).trigger('reloadGrid');//重新载入
    jQuery("#bhg").jqGrid("clearGridData");
    jQuery("#bhg").jqGrid('setGridParam',{
        url: "../pathologysample/sample/ajax/sample",
        mtype: "GET",
        datatype: "json",
        postData : {"req_bf_time":req_bf_time,"req_af_time":req_af_time,"req_sts":req_sts}
    }).trigger('reloadGrid');//重新载入
    jQuery("#bldjb").jqGrid("clearGridData");
    jQuery("#bldjb").jqGrid('setGridParam',{
        url: "../report/diagnosisreport/list",
        mtype: "GET",
        datatype: "json",
        postData : {"req_bf_time":req_bf_time,"req_af_time":req_af_time,"req_sts":5}
    }).trigger('reloadGrid');//重新载入
	jQuery("#sftj_new0").jqGrid("clearGridData");
    jQuery("#sftj_new0").jqGrid('setGridParam',{
        url: "../report/sftj",
        mtype: "GET",
        datatype: "json",
        postData : {"req_bf_time":req_bf_time,"req_af_time":req_af_time,"req_code":1}
    }).trigger('reloadGrid');//重新载入
	jQuery("#sftj_new1").jqGrid("clearGridData");
    jQuery("#sftj_new1").jqGrid('setGridParam',{
        url: "../report/sftj",
        mtype: "GET",
        datatype: "json",
        postData : {"req_bf_time":req_bf_time,"req_af_time":req_af_time,"req_code":2}
    }).trigger('reloadGrid');//重新载入
	jQuery("#sftj_new2").jqGrid("clearGridData");
    jQuery("#sftj_new2").jqGrid('setGridParam',{
        url: "../report/sftj",
        mtype: "GET",
        datatype: "json",
        postData : {"req_bf_time":req_bf_time,"req_af_time":req_af_time,"req_code":3}
    }).trigger('reloadGrid');//重新载入
	jQuery("#sftj_new3").jqGrid("clearGridData");
    jQuery("#sftj_new3").jqGrid('setGridParam',{
        url: "../report/sftj",
        mtype: "GET",
        datatype: "json",
        postData : {"req_bf_time":req_bf_time,"req_af_time":req_af_time,"req_code":4}
    }).trigger('reloadGrid');//重新载入
	jQuery("#sftj_new4").jqGrid("clearGridData");
    jQuery("#sftj_new4").jqGrid('setGridParam',{
        url: "../report/sftj",
        mtype: "GET",
        datatype: "json",
        postData : {"req_bf_time":req_bf_time,"req_af_time":req_af_time,"req_code":5}
    }).trigger('reloadGrid');//重新载入
	jQuery("#sftj_new5").jqGrid("clearGridData");
    jQuery("#sftj_new5").jqGrid('setGridParam',{
        url: "../report/sftj",
        mtype: "GET",
        datatype: "json",
        postData : {"req_bf_time":req_bf_time,"req_af_time":req_af_time,"req_code":6}
    }).trigger('reloadGrid');//重新载入
	jQuery("#gzl_new0").jqGrid("clearGridData");
    jQuery("#gzl_new0").jqGrid('setGridParam',{
        url: "../report/qcgzl",
        mtype: "GET",
        datatype: "json",
        postData : {"req_bf_time":req_bf_time,"req_af_time":req_af_time}
    }).trigger('reloadGrid');//重新载入
	jQuery("#gzl_new1").jqGrid("clearGridData");
    jQuery("#gzl_new1").jqGrid('setGridParam',{
        url: "../report/ccgzl",
        mtype: "GET",
        datatype: "json",
        postData : {"req_bf_time":req_bf_time,"req_af_time":req_af_time}
    }).trigger('reloadGrid');//重新载入
	jQuery("#gzl_new2").jqGrid("clearGridData");
    jQuery("#gzl_new2").jqGrid('setGridParam',{
        url: "../report/scgzl",
        mtype: "GET",
        datatype: "json",
        postData : {"req_bf_time":req_bf_time,"req_af_time":req_af_time}
    }).trigger('reloadGrid');//重新载入
	jQuery("#gzl_new3").jqGrid("clearGridData");
    jQuery("#gzl_new3").jqGrid('setGridParam',{
        url: "../report/Bmgzl",
        mtype: "GET",
        datatype: "json",
        postData : {"req_bf_time":req_bf_time,"req_af_time":req_af_time}
    }).trigger('reloadGrid');//重新载入
	jQuery("#gzl_new4").jqGrid("clearGridData");
    jQuery("#gzl_new4").jqGrid('setGridParam',{
        url: "../report/Qpgzl",
        mtype: "GET",
        datatype: "json",
        postData : {"req_bf_time":req_bf_time,"req_af_time":req_af_time}
    }).trigger('reloadGrid');//重新载入
	jQuery("#ksgzl_new").jqGrid("clearGridData");
    jQuery("#ksgzl_new").jqGrid('setGridParam',{
        url: "../report/ksgzl",
        mtype: "GET",
        postData : {"req_bf_time":req_bf_time,"req_af_time":req_af_time}
    }).trigger('reloadGrid');//重新载入
    jQuery("#bdjg_new0").jqGrid("clearGridData");
    jQuery("#bdjg_new0").jqGrid('setGridParam',{
        url: "../report/jgdz",
        mtype: "GET",
        postData : {"req_bf_time":req_bf_time,"req_af_time":req_af_time}
    }).trigger('reloadGrid');//重新载入
    jQuery("#bgqsd").jqGrid("clearGridData");
    jQuery("#bgqsd").jqGrid('setGridParam',{
        url: "../report/diagnosisreport/list",
        mtype: "GET",
        postData : {"req_bf_time":req_bf_time,"req_af_time":req_af_time,"req_sts":6}
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
		reportView(1, null, null,rowData.sampleid);
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

function reportExcel() {
	var req_bf_time = $('#req_bf_time').val();//送检FROM
	var req_af_time = $('#req_af_time').val();//送检TO
    var array =[];
    $('input[name="biao"]:checked').each(function(){
        array.push($(this).val());
    });
	// getBiao();
	window.location.href = "../report/dc?req_bf_time="+req_bf_time+"&req_af_time="+req_af_time+"&array="+array.join();
}

function getBiao(){

    alert(array);
    $.ajax({
        url:"../report/dc",
        type: "POST",
        traditional: true,
        contentType: "application/x-www-form-urlencoded",
        data: {"array": JSON.stringify(array),},
        success : function(data) {
        }
    });
}

function show(id1,id2){
	if($("#"+id1+"").is(':checked')){
    	$("#"+id2+"").css("display","block");
    }else {
       $("#"+id2+"").css("display","none")
    }
}