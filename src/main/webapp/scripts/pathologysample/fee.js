var LASTEDITROW = "";
var LASTEDITCELL = "";
/**
 * 计费调整
 */
function hisChange() {
	//alert(addstates);
	var sampleid = $("#sampleid").val();
	if(sampleid == null || sampleid == ""){
		layer.msg("该病理标本未登记，无法进行计费调整!", {icon:2, time: 1000});
		return;
	}else{
		jQuery("#feediv").jqGrid('setGridParam',{
			url: "../pathologysample/sample/ajax/fee",
			//发送数据
			postData:{"feesampleid":sampleid,"feesource":"0"}
		}).trigger('reloadGrid');//重新载入
		// $("#sectionList3").jqGrid({
		// 	caption: "&nbsp;&nbsp;计费管理",
		// 	url: "../pathologysample/sample/ajax/fee",
		// 	mtype: "GET",
		// 	datatype: "json",
		// 	postData:{"feesampleid":sampleid,"feesource":"0"},
		// 	width:900,
		// 	height:600,
		// 	colNames: ['id','收费项目', '单价','数量','金额','状态','记录人','记录时间','发送人','发送时间','客户id','标本号','病种id','病理编号','费用来源',
		// 		'费用状态','统计类别','中文名称','英文名称','his项目id','his项目名称','his单价','计费人员id','发送人员id'],
		// 	colModel: [
		// 		{ name: 'feeid', index: 'feeid',hidden: true },//收费id
		// 		{ name: 'feenamech', index: 'feenamech',editable:true,editoptions: {dataInit: function (elem) {myAutocomplete(elem);}},
		// 			// edittype: "select",formatter: "select",editoptions:{value:getfeelist(), dataEvents: [
		// 			// {type: 'change',fn: function(e) {
		// 			// 	jQuery("#new1").jqGrid('setRowData', $(this).parent().parent().attr('id'), {reqmmaterialname:$(this).find("option:selected").text()});
		// 			// 	jQuery("#new1").jqGrid('setRowData', $(this).parent().parent().attr('id'), {feeitemid:$(this).val()});
		// 			// }}]},
		// 			width: 80},//收费项目
		// 		{ name: 'feeprince', index: 'feeprince', width: 60},//单价
		// 		{ name: 'feeamount', index: 'feeamount',editable:true, width: 60,editoptions: {
		// 			dataEvents: [
		// 				{type: 'change',fn: function(e) {
		// 					var rowdata = jQuery("#sectionList3").jqGrid('getRowData', $(this).parent().parent().attr('id'));
		// 					jQuery("#sectionList3").jqGrid('setRowData', $(this).parent().parent().attr('id'), {feecost:$(this).val()*rowdata.feeprince});
		// 				}}]
		// 			}},//数量
		// 		{ name: 'feecost', index: 'feecost', width: 60},//金额
		// 		{ name: 'feestate', index: 'feestate',formatter: "select", editoptions:{value:"0:已保存;1:已计费;2:已发送;3:发送失败"}, width: 60},//状态
		// 		{ name: 'feeusername', index: 'feeusername', width: 60},//记录人
		// 		{ name: 'feetime', index: 'feetime', width: 60,formatter:function(cellvalue, options, row){if(cellvalue == null || cellvalue == "")
		// 			return "";
		// 			return CurentTime(new Date(cellvalue));}},//记录时间
		// 		{ name: 'feesendusername', index: 'feesendusername', width: 60},//发送人
		// 		{ name: 'feesendtime', index: 'feesendtime', width: 60,formatter:function(cellvalue, options, row){if(cellvalue == null || cellvalue == "")
		// 			return "";
		// 			return CurentTime(new Date(cellvalue));}},//发送时间
		// 		{ name: 'feecustomerid', index: 'feecustomerid',hidden: true },//客户id
		// 		{ name: 'feesampleid', index: 'feesampleid',hidden: true },//标本号
		// 		{ name: 'feepathologyid', index: 'feepathologyid',hidden: true },//病种id
		// 		{ name: 'feepathologycode', index: 'feepathologycode',hidden: true },//病理编号
		// 		{ name: 'feesource', index: 'feesource',hidden: true },//费用来源
		// 		{ name: 'feestate', index: 'feestate',hidden: true },//费用状态
		// 		{ name: 'feecategory', index: 'feecategory',hidden: true },//统计类别
		// 		{ name: 'feeitemid', index: 'feeitemid',hidden: true },//中文名称
		// 		{ name: 'feenameen', index: 'feenameen',hidden: true },//英文名称
		// 		{ name: 'feehisitemid', index: 'feehisitemid',hidden: true },//his项目id
		// 		{ name: 'feehisname', index: 'feehisname',hidden: true },//his项目名称
		// 		{ name: 'feehisprice', index: 'feehisprice',hidden: true },//his单价
		// 		{ name: 'feeuserid', index: 'feeuserid',hidden: true },//计费人员id
		// 		{ name: 'feesenduserid', index: 'feesenduserid',hidden: true },//发送人员id
		// 	],
		// 	loadComplete : function() {
		// 		var table = this;
		// 		setTimeout(function(){
		// 			updatePagerIcons(table);
		// 		}, 0);
		// 	},
		//    beforeEditCell: function (rowid, cellname, value, iRow, iCol) {
		//        var rec = jQuery("#sectionList3").jqGrid('getRowData', rowid);
		//        if (rec.feestate == "1" || rec.feestate == "2") {
		//            setTimeout(function () {
		//                jQuery("#sectionList3").jqGrid('restoreCell', iRow, iCol);
		//                //===>或者设置为只读
		//                //$('#' + rowid + '_amount').attr('readonly', true);
		//            }, 1);
		//        }
		//    },
		// 	ondblClickRow: function (id) {
		// 	},
		// 	multiselect: true,
		// 	viewrecords: true,
		// 	cellsubmit: "clientArray",
		// 	cellEdit:true,
		// 	shrinkToFit: true,
		// 	altRows:true,
		// 	height: 'auto',
		// 	// rowNum: 10,
		// 	// rowList:[10,20,30],
		// 	// rownumbers: true, // 显示行号
		// 	// rownumWidth: 35, // the width of the row numbers columns
		// 	// pager: "#pager3",
		// 	onSelectRow: function(id){
		//
		// 	}
		// });
		layer.open({
			type: 1,
			area: ['900px','600px'],
			fix: false, //不固定
			maxmin: true,
			multiselect: true,
			rownumbers : true,
			shade:0.5,
			title: "计费",
			content: $('#feeGrid')
		})
	}

}
/**
 * 初始化
 */
$(function() {
    createNew2();
});
/**
 * 初始化计费调整
 */
function createNew2() {
    var sampleid = $("#sampleid").val();
    $("#feediv").jqGrid({
        caption: "&nbsp;&nbsp;计费管理",
        url: "../pathologysample/sample/ajax/fee",
        mtype: "GET",
        datatype: "json",
        postData:{"feesampleid":sampleid,"feesource":"0"},
        width:900,
        height:600,
        colNames: ['id','收费项目', '单价','数量','金额','状态','记录人','记录时间','发送人','发送时间','客户id','标本号','病种id','病理编号','费用来源',
            '费用状态','统计类别','中文名称','英文名称','his项目id','his项目名称','his单价','计费人员id','发送人员id'],
        colModel: [
            { name: 'feeid', index: 'feeid',hidden: true },//收费id
            { name: 'feenamech', index: 'feenamech',editable:true,editoptions: {dataInit: function (elem) {myAutocomplete(elem);}, align: "center"},
                // edittype: "select",formatter: "select",editoptions:{value:getfeelist(), dataEvents: [
                // {type: 'change',fn: function(e) {
                // 	jQuery("#new1").jqGrid('setRowData', $(this).parent().parent().attr('id'), {reqmmaterialname:$(this).find("option:selected").text()});
                // 	jQuery("#new1").jqGrid('setRowData', $(this).parent().parent().attr('id'), {feeitemid:$(this).val()});
                // }}]},
                width: 80},//收费项目
            { name: 'feeprince', index: 'feeprince', width: 60, align: "center"},//单价
            { name: 'feeamount', index: 'feeamount',editable:true, width: 60,editoptions: {
                dataEvents: [
                    {type: 'change',fn: function(e) {
                        var rowdata = jQuery("#feediv").jqGrid('getRowData', $(this).parent().parent().attr('id'));
                        jQuery("#feediv").jqGrid('setRowData', $(this).parent().parent().attr('id'), {feecost:$(this).val()*rowdata.feeprince});
                    }},
                    {type: "keydown",fn : function(e) {
                        var key = e.charCode || e.keyCode || 0;
                        if(key === 13){
                            var rowdata = jQuery("#feediv").jqGrid('getRowData', $(this).parent().parent().attr('id'));
                            jQuery("#feediv").jqGrid('setRowData', $(this).parent().parent().attr('id'), {feecost:$(this).val()*rowdata.feeprince});
                            return false;
                        }
                        return this;
                        }
                    }]
            }, align: "center"},//数量
            { name: 'feecost', index: 'feecost', width: 60, align: "center"},//金额
            { name: 'feestate', index: 'feestate',formatter: "select", editoptions:{value:"0:已保存;1:已计费;2:已发送;3:发送失败"}, width: 60, align: "center"},//状态
            { name: 'feeusername', index: 'feeusername', width: 60, align: "center"},//记录人
            { name: 'feetime', index: 'feetime', width: 60,formatter:function(cellvalue, options, row){if(cellvalue == null || cellvalue == "")
                return "";
                return CurentTime(new Date(cellvalue));}},//记录时间
            { name: 'feesendusername', index: 'feesendusername', width: 60},//发送人
            { name: 'feesendtime', index: 'feesendtime', width: 60,formatter:function(cellvalue, options, row){if(cellvalue == null || cellvalue == "")
                return "";
                return CurentTime(new Date(cellvalue));}, align: "center"},//发送时间
            { name: 'feecustomerid', index: 'feecustomerid',hidden: true },//客户id
            { name: 'feesampleid', index: 'feesampleid',hidden: true },//标本号
            { name: 'feepathologyid', index: 'feepathologyid',hidden: true },//病种id
            { name: 'feepathologycode', index: 'feepathologycode',hidden: true },//病理编号
            { name: 'feesource', index: 'feesource',hidden: true },//费用来源
            { name: 'feestate', index: 'feestate',hidden: true },//费用状态
            { name: 'feecategory', index: 'feecategory',hidden: true },//统计类别
            { name: 'feeitemid', index: 'feeitemid',hidden: true },//中文名称
            { name: 'feenameen', index: 'feenameen',hidden: true },//英文名称
            { name: 'feehisitemid', index: 'feehisitemid',hidden: true },//his项目id
            { name: 'feehisname', index: 'feehisname',hidden: true },//his项目名称
            { name: 'feehisprice', index: 'feehisprice',hidden: true },//his单价
            { name: 'feeuserid', index: 'feeuserid',hidden: true },//计费人员id
            { name: 'feesenduserid', index: 'feesenduserid',hidden: true },//发送人员id
        ],
        loadComplete : function() {
            var table = this;
            setTimeout(function(){
                updatePagerIcons(table);
            }, 0);
        },
        beforeEditCell: function (rowid, cellname, value, iRow, iCol) {
            LASTEDITROW = iRow;
            LASTEDITCELL = iCol;
            var rec = jQuery("#feediv").jqGrid('getRowData', rowid);
            if (rec.feestate == "1" || rec.feestate == "2") {
                setTimeout(function () {
                    jQuery("#feediv").jqGrid('restoreCell', iRow, iCol);
                    //===>或者设置为只读
                    //$('#' + rowid + '_amount').attr('readonly', true);
                }, 1);
            }
        },
        ondblClickRow: function (id) {
        },
        multiselect: true,
        viewrecords: true,
        cellsubmit: "clientArray",
        cellEdit:true,
        shrinkToFit: true,
		rownumbers : true,
        // altRows:true,
        // height: 'auto',
        // rowNum: 10,
        // rowList:[10,20,30],
        // rownumbers: true, // 显示行号
        // rownumWidth: 35, // the width of the row numbers columns
        // pager: "#pager3",
        onSelectRow: function(id){

        }
    });
}
/**
 * 计费调整界面模糊匹配功能
 * @param elem
 */
function myAutocomplete(elem) {
	//收费项目
	$(elem).autocomplete({
		source: function( request, response ) {
			$.ajax({
				url: "../chargeitem/info",
				dataType: "json",
				data: {
					// name : request.term,//名称
					// bddatatype:4,//送检医院
					// bdcustomerid:$("#lcal_hosptail").val()//账号所属医院
				},
				success: function( data ) {
					response( $.map( data, function( result ) {
						return {
							label: result.feenamech + " : " + result.feenameen,
							value: result.feenamech,//中文名称
							id : result.feeitemid,//收费项目ID
							feenameen : result.feenameen,//英文名称
							feeprince : result.feeprince,//单价
							feecategory : result.feecategory,//所属统计类
							feehisitemid : result.feehisitemid,//his项目id
							feehisname : result.feehisname,//his项目名称
							feehisprice : result.feehisprice//his单价
						}
					}));
				}
			});
		},
		minLength: 0,
		select: function( event, ui ) {
			jQuery("#feediv").jqGrid('setRowData', $(elem).parent().parent().attr("id"), {feeitemid:ui.item.id});//收费项目ID
			jQuery("#feediv").jqGrid('setRowData', $(elem).parent().parent().attr("id"), {feenamech:ui.item.name});//中文名称
			jQuery("#feediv").jqGrid('setRowData', $(elem).parent().parent().attr("id"), {feenameen:ui.item.feenameen});//英文名称
			jQuery("#feediv").jqGrid('setRowData', $(elem).parent().parent().attr("id"), {feeprince:ui.item.feeprince});//单价
			jQuery("#feediv").jqGrid('setRowData', $(elem).parent().parent().attr("id"), {feecategory:ui.item.feecategory});//所属统计类
			jQuery("#feediv").jqGrid('setRowData', $(elem).parent().parent().attr("id"), {feehisitemid:ui.item.feehisitemid});//his项目id
			jQuery("#feediv").jqGrid('setRowData', $(elem).parent().parent().attr("id"), {feehisname:ui.item.feehisname});//his项目名称
			jQuery("#feediv").jqGrid('setRowData', $(elem).parent().parent().attr("id"), {feehisprice:ui.item.feehisprice});//his单价
            jQuery("#feediv").jqGrid('setRowData', $(elem).parent().parent().attr("id"), {feeamount:1});//数量
            jQuery("#feediv").jqGrid('setRowData', $(elem).parent().parent().attr("id"), {feecost:ui.item.feeprince});//金额
		}
	})
		.data( "ui-autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li>" )
			.append( "<a style='font-size:12px;font-family: 微软雅黑;'>" + item.id + "," + item.value+ "</a>" )
			.appendTo( ul );
	};
}

/**
 * 增加行
 */
function addfeeRow(){
	var selectedId = $("#feediv").jqGrid("getGridParam", "selrow");
	var dataRow = {
		feeid:"",//收费id
		feeitemid:"",//收费项目
		feeprince:"",//单价
		feeamount:"",//数量
		feecost:"",//金额
		feestate:"0",//状态
		feeusername:$("#local_username").val(),//记录人
		feetime:new Date(),//记录时间
		feesendusername:"",//发送人
		feesendtime:"",//发送时间
		feecustomerid:$("#samcustomerid")==null?$("#customerId").val():$("#samcustomerid").val(),//客户id
		feesampleid:$("#sampleid").val(),//标本号
		feepathologyid:$("#pathologyCode") == null?$("#sampathologyid").val():$("#pathologyCode").val(),//病种id
		feepathologycode:$("#sampathologycode").val(),//病理编号
		feesource:"2",//费用来源
		feestate:"0",//费用状态
		feecategory:"",//统计类别
		feenamech:"",//中文名称
		feenameen:"",//英文名称
		feehisitemid:"",//his项目id
		feehisname:"",//his项目名称
		feehisprice:"",//his单价
		feeuserid:$("#local_userid").val(),//计费人员id
		feesenduserid:"",//发送人员id
	};
	var ids = $("#feediv").jqGrid('getDataIDs');
	var rowid = 1;
	if(Math.max.apply(Math,ids) > ids.length ){
		rowid = Math.max.apply(Math,ids) + 1;
	}else{
		rowid = ids.length + 1;
	}
	if (selectedId) {
		$("#feediv").jqGrid("addRowData", rowid, dataRow, "after", selectedId);
	} else {
		$("#feediv").jqGrid("addRowData", rowid, dataRow, "last");
	}
	//$('#plsfList').jqGrid('editRow', rowid, false);
}
/**
 * 删除行
 */
function delfeeRow(){
	// var selectedId = $("#feediv").jqGrid("getGridParam","selarrrow");
	// if(selectedIds == null || selectedIds == ""){
	// 	alert("请选择要删除的行!");
	// 	return;
	// }else{
	// 	$("#feediv").jqGrid("delRowData", selectedId);
	// }
    var selectedIds = $("#feediv").jqGrid("getGridParam","selarrrow");
    if(selectedIds == null || selectedIds == ""){
        layer.msg("请选择要删除的行!", {icon:2, time: 1000});
        return;
    }else{
        $(selectedIds).each(function () {
                var delrow = this.toString();
                var rowData = $("#feediv").jqGrid('getRowData',delrow);
                if(rowData.feestate == "0" || rowData.feestate == "3"){
                    $("#feediv").jqGrid("delRowData", delrow);
                }else if(rowData.feestate == "1" || rowData.feestate == "2") {
                    layer.msg("已计费或已发送数据无法删除!", {icon:2, time: 1000});
                    return;
                }
            }
        );
    }
}
/**
 * 保存计费信息
 */
function savefeeRow(states) {
    $("#feediv").jqGrid("saveCell",LASTEDITROW,LASTEDITCELL);
    var rowdatas = $('#feediv').jqGrid('getRowData');
    var result = true;
    jQuery(rowdatas).each(function(){
        if(this.feenamech == null || this.feenamech == ""){
            result = false;
            layer.msg("请补充数据完整后再提交单据！", {icon: 2, time: 1000});
            return false;
        }
    });
    if(result){
        $.post("../pathologysample/sample/savefee", {
                fees:JSON.stringify(rowdatas),
                states:states
            },
            function(data) {
                if(data.success) {
                    layer.msg(data.message, {icon: 1, time: 1000});
                    location.reload();
                } else {
                    layer.msg(data.message, {icon:2, time: 1000});
                }
            });
    }
}
