var PIC_TAKING_FROM = 2;
var GRID_SELECTED_ROW_SAMPLEID;
var GRID_SELECTED_ROW_SAMPCUSTOMERID;
/**
 * 保存会诊结果
 */
function saveInfo() {
	// var rowdatas = $('#new1').jqGrid('getRowData');
	// var selectedIds = $("#new").jqGrid("getGridParam","selarrrow");
	// var arr = new Array();
	// if(selectedIds.length > 0){
	// 	$(selectedIds).each(function () {
	// 		var rowData1 = $("#new").jqGrid('getRowData',this.toString());
	// 		arr.push(rowData1);
	// 		}
	// 	);
	// }
    var states = $("input[name='hzstates']:checked").val();
    if(states == 1){
        $.post("../consultation/cons/conisfinish",{id:$("#consultationid").val()},
            function (data1) {
                if(data1.success){
                    savecons();
                }else{
                    layer.confirm('还有成员未会诊,是否继续？', {icon: 2, title:'警告'}, function(index) {
                        savecons();
                    });
                }
            }
        )
    }else{
        savecons();
    }



    // var post = true;
	// if(post) {
	//
	// }
}

function savecons() {
    $.post("../consultation/cons/editSample", {
            consampleid:$("#consampleid").val(),
            consultationid:$("#consultationid").val(),
            consponsoreduserid:$("#consponsoreduserid").val(),
            conconsultationstate:$("input[name='hzstates']:checked").val(),
            detconsultationid:$("#detconsultationid").val(),
            detdoctorid:$("#detdoctorid").val(),
            detadvice:$("#detadvice").val()
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

/**
 * 初始化
 */
$(function() {
	$("#maintab").css('display','block');
	$('#tabs a:first').tab('show');//初始化显示哪个tab
	$('#tabs a').click(function (e) {
		$('#tabs').find('li').each(function(){
			$($(this).find('a').attr("href")).css('display','none');
		});
		e.preventDefault();//阻止a链接的跳转行为
		$($(this).attr("href")).css('display','block');
		$(this).tab('show');//显示当前选中的链接及关联的content
	});
	$("#resetbutton").attr({"disabled":true});
	$.get("../consultation/cons/get",{id:$("#samid").val()},function(data) {
		if(data != "") {
			$("#sampleid").val(data.sampleid);
			$("#samcustomerid").val(data.samcustomerid);
			$("#samsamplestatus").val(data.samsamplestatus);
			$("#sampathologycode").val(data.sampathologycode);
			$("#samisemergency").val(data.samisemergency);
			$("#sampatientname").val(data.sampatientname);
			$("#samsenddoctorname").val(data.samsenddoctorname);
			$("#sampatientnumber").val(data.sampatientnumber);
			$("#samdeptname").val(data.samdeptname);
			$("#sampatientbed").val(data.sampatientbed);
			$("#samsendhospital").val(data.samsendhospital);
			$("#sampatientsex").val(data.sampatientsex);
			$("#samsamplename").val(data.samsamplename);
			$("#samfirstn").val(data.samfirstn);
			$("#samsendtime").val(CurentTime(new Date(data.samsendtime)));
			$("#nums").val(data.paranums);
			$("#samreceivertime").val(CurentTime(new Date(data.samreceivertime)));
			$("#sampatientdignoses").val(data.sampatientdignoses);
			if(data.consstate == "0"){
				$("input[name='hzstates'][value='0']").attr("checked",true);
			}else if(data.consstate == "1"){
				$("input[name='hzstates'][value='1']").attr("checked",true);
			}else if(data.consstate == "2"){
				$("input[name='hzstates'][value='2']").attr("checked",true);
			}
			GRID_SELECTED_ROW_SAMPLEID = $("#sampleid").val();
			GRID_SELECTED_ROW_SAMPCUSTOMERID = $("#samcustomerid").val();
			getSamplePicures($("#sampleid").val());
            var patClass = $("#patclass").val();
            if (patClass == 2) {
                $("#diagnosisInfoForm").css('display', 'none');
                $("#fullScreen").css('display', 'block');
                $("#yjcell").css('display', 'block');
            } else {
                $("#diagnosisInfoForm").css('display', 'block');
                $("#tabs-3").css('display','none');
                $("#fullScreen").css('display', 'none');
                $("#yjcell").css('display', 'none');
                insertTable(patClass)
            }
            getOrderTabs(GRID_SELECTED_ROW_SAMPLEID);
            $.get("../diagnosis/sampleresult", {sampleid: GRID_SELECTED_ROW_SAMPLEID, patClass: patClass}, function (data1) {
                if (patClass == 2) {
                    setYJXBDiagnosis(data1);
                } else {
                    var x = document.getElementById("diagnosisInfoForm");
                    for (itm in data1) {
                        var resultid = data1[itm].resultid;
                        var restestitemid = data1[itm].restestitemid;
                        for (var i = 0; i < x.length; i++) {
                            var e = x.elements[i];

                            if ($("#" + e.id).attr("rptItemId") == restestitemid) {
                                var reg=new RegExp("<br>","g");
                                $("#" + e.id).val(data1[itm].restestresult==null?"":data1[itm].restestresult.replace(reg,"\n"));
                                $("#" + e.id).attr("hiddenValue", resultid);
                                if(patClass == 7 && $("#" + e.id).attr("type") == "hidden") {
                                    setSelectedValue(data1[itm].restestresult);
                                }
                            }
                        }
                    }
                    var emptyResult = jQuery.isEmptyObject(data1);
                    if (emptyResult) {
                        for (var i = 0; i < x.length; i++) {
                            var e = x.elements[i];
                            if ($("#" + e.id).attr("rptItemId")) {
                                $("#" + e.id).val('');
                                if(e.placeholder.indexOf("巨检") >= 0){
                                    $("#" + e.id).val(samjjsj);
                                }
                                $("#" + e.id).attr("hiddenValue", "");
                            }
                        }
                    }
                }
            })
		} else {
			layer.msg("该申请单不存在！", {icon: 0, time: 1000});
		}
	});
	$("#new1").jqGrid({
		//caption:"材块列表",
		url: "../consultation/cons/ajax/peice",
		mtype: "GET",
		datatype: "json",
		postData:{"id":$("#samid").val()},
		colNames: ['病理号','序号','材块数','取材部位','取材医生','录入员','取材时间'],
		colModel: [
			{ name: 'piepathologycode', index: 'piepathologycode',width:100},
			{ name: 'piesamplingno', index: 'piesamplingno',width:50},
			{ name: 'piecounts', index: 'piecounts',width:50},
			{ name: 'pieparts', index: 'pieparts',width:100},
			{ name: 'piedoctorname', index: 'piedoctorname',width:100},
			{ name: 'pierecordername', index: 'pierecordername',width:100},
			{ name: 'piesamplingtime', index: 'piesamplingtime',formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))},width:200}
		],
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		viewrecords: true,
		//height:300,
		 width:700,
		//autowidth: true,
		// shrinkToFit:false,
		// autoScroll: true,
		// rowNum: 10,
		// rowList:[10,20,30],
		rownumbers: true, // 显示行号
		// rownumWidth: 10, // the width of the row numbers columns
		// pager: "#pager"
	});
	$("#new").jqGrid({
		caption:"医生列表",
		url: "../consultation/cons/ajax/doctor",
		mtype: "GET",
		datatype: "json",
		postData:{"id":$("#samid").val()},
		colNames: ['会诊医生','状态'],
		colModel: [
			{ name: 'detdoctorname', index: 'detdoctorname',width:150},
			{ name: 'detstate', index: 'detstate',formatter: "select", editoptions:{value:"0:未会诊;1:已会诊"},width:110}
		],
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		viewrecords: true,
		//height:700,
		width:337,
		//autowidth: true,
		shrinkToFit:false,
		autoScroll: true,
		// rowNum: 10,
		// rowList:[10,20,30],
		rownumbers: true, // 显示行号
		// rownumWidth: 10, // the width of the row numbers columns
		// pager: "#pager"
	});

	var html="";
	$.ajax({
		type:"post",
		async:false,
		url:"../consultation/cons/ajax/getreqinfo",
		dataType: "json",
		data:{"id":$("#consultationid").val()},
		success:function(data){
			if (data != null) {
				for(var i=0;i<data.length;i++){
					var j = i+1;
					if(i==0){
						html += "<div style=\"margin-top:10px;margin-bottom:5px;\">";
					}else{
						html += "<div style=\"margin-bottom:5px;\">";
					}
					if(data[i].detadvice == null || data[i].detadvice == ""){
						html += "<span class=\"input_style\" style=\"width: 100%;\"><strong>&nbsp;&nbsp;"+ j+"."+ data[i].detdoctorname+
							"还未发表会诊意见</strong></span></div>";
					}else{
						html += "<span class=\"input_style\" style=\"width: 100%;\"><strong>&nbsp;&nbsp;"+ j+"."+ data[i].detdoctorname+":"+ CurentTime(new Date(data[i].detconsultationtime))+
							"更新</strong><span style=\"float:right\">&nbsp;&nbsp;</span><button id='copy_input_"+
							i+"' style='float: right' onclick=saveValue('copy_input_"+i+"','text_"+i+"') class='copy'>复制</button></span>";
						html +="&nbsp;&nbsp;<textarea id='text_"+i+"' style='overflow-y:visible;height:100px;font-size:12px;width: 100%'>"+data[i].detadvice+ "</textarea>&nbsp;&nbsp;</div>";
					}
				}
			}
		}
	});
	$("#hzyj").html(html);

});

function saveValue(buttonid,textid) {
	$("#"+ buttonid).zclip({
		path: "../scripts/ZeroClipboard.swf",
		copy: function(){
			return $('#'+textid).val();
		},
		afterCopy: function(){//复制成功
			layer.msg('复制成功', {icon: 1, time: 1000});
		}
	});
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

function importImg() {
	layer.open({
		type: 1,
		title: '病理诊断>图像导入',
		shadeClose: true,
		shade: false,
		maxmin: true, //开启最大化最小化按钮
		area: ['260px', '300px'],
		content: $('#uploadDialog'),
		btn: ["确定", "取消"],
		yes: function (index, layero) {
			if ($("#imgFile").val().length > 0) {
				ajaxFileUpload();
				layer.close(index);
			}
			else {
				layer.msg('请先选择图片', {icon: 2, time: 1000});
				return false;
			}
		}
	});
}

function ajaxFileUpload() {
	$.ajaxFileUpload({
		url: '../diagnosis/uploadimg',
		secureuri: false,
		fileElementId: ["imgFile"],
		dataType: 'json',
		data: {
			sampleid: GRID_SELECTED_ROW_SAMPLEID,
			samcustomerid: GRID_SELECTED_ROW_SAMPCUSTOMERID,
			picpictureclass: PIC_TAKING_FROM
		},
		success: function (data, status) {
            getSamplePicures(GRID_SELECTED_ROW_SAMPLEID);
			layer.alert('上传成功！');
		},
		error: function (data, status, e) {

		}
	});
	return false;
}

function getSamplePicures(sampleId) {
	$.get("../diagnosis/pictures", {sampleid: sampleId, picpictureclass: PIC_TAKING_FROM}, function (data) {
		var ret = data;
		var container = $("#imgContainer");
		container.empty();
		for (var item in ret) {
			var objNewDiv = $('<div>', {'id': new Date().getTime(), 'style': 'padding-bottom:5px'});
			objNewDiv.html("<img src='" + ret[item] + "' name='" + item + "' onclick='removePicture(\"" + item + "\")' width='220' height='150'>");
			container.append(objNewDiv);
		}
	});
}

function removePicture(pictureName) {
	layer.msg('需要删除图片吗？', {
		time: 3000, //自动关闭
		btn: ['是', '否'],
		yes: function (index) {
			$.get("../diagnosis/removePicture", {
				name: pictureName,
				sampleid: GRID_SELECTED_ROW_SAMPLEID
			}, function (data) {
				var es = document.getElementsByName(pictureName);
				var pn = es[0].parentNode;
				pn.parentNode.removeChild(pn);
			});
			layer.close(index);
		}
	});
}

function insertTable(patClass) {
    if (patClass == 7) {
        if($('#dnaTestTable').length == 0) {
            var div1 = $('#diagnosisInfoForm').children('div');
            if(div1.length > 0) {
                var lastChild = div1[div1.length-1];
                var str='<div id="dnaTestTable"><table width="80%" border="1" cellspacing="0" style="font-size: 12px">' +
                    '<tr>' +
                    '<td align="center" height="14px"><strong>HPV型组</strong></td>' +
                    '<td align="center"><strong>亚型</strong></td>' +
                    '<td align="center"><strong>结果</strong></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td align="left" height="14px">A5/A6</td>' +
                    '<td align="left">51,56,66</td>' +
                    '<td align="left">' +
                    '<Select id="A5A6Result" onchange="setSelectView(\'A5A6Result\')">' +
                    '<option></option>' +
                    '<option value="阴性" style="color:#000">阴性</option>' +
                    '<option value="阳性" style="color:red">阳性</option>' +
                    '</select>' +
                    '</td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td align="left" height="14px">A7</td>' +
                    '<td align="left">18,39,45,59,68</td>' +
                    '<td align="left">' +
                    '<Select id="A7Result" onchange="setSelectView(\'A7Result\')">' +
                    '<option></option>' +
                    '<option value="阴性" style="color:#000">阴性</option>' +
                    '<option value="阳性" style="color:red">阳性</option>' +
                    '</select>' +
                    '</td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td align="left" height="14px">A9</td>' +
                    '<td align="left">16,31,33,35,52,58</td>' +
                    '<td align="left">' +
                    '<Select id="A9Result" onchange="setSelectView(\'A9Result\')">' +
                    '<option></option>' +
                    '<option value="阴性" style="color:#000">阴性</option>' +
                    '<option value="阳性" style="color:red">阳性</option>' +
                    '</select>' +
                    '</td>' +
                    '</tr>' +
                    '</table>' +
                    '</div>';
                $(str).insertBefore(lastChild);
            }
        }
    } else {
        var tableDiv = document.getElementById("dnaTestTable");
        if(tableDiv != null) {
            tableDiv.parentNode.removeChild(tableDiv);
        }
    }
}

function getOrderTabs(sampleId) {
    var ul = $("#tabs");
    $.get("../order/getrequestedorder", {sampleId: sampleId}, function (data) {
        var ret = data.rows;
        var myzh = document.getElementById("order_MYZH");
        var fzbl = document.getElementById("order_FZBL");
        var tsrs = document.getElementById("order_TSRS");
        if (myzh != null)myzh.parentNode.removeChild(myzh);
        if (fzbl != null)fzbl.parentNode.removeChild(fzbl);
        if (tsrs != null)tsrs.parentNode.removeChild(tsrs);
        if (ret != null && ret.length > 0) {
            for (var i = 0; i < ret.length; i++) {
                if (ret[i].tesenglishname == "MYZH" || ret[i].tesenglishname == "FZBL" || ret[i].tesenglishname == "TSRS") {
                    var li_ = "<li itemId=\"order_" + ret[i].testitemid + "\" id=\"order_" + ret[i].tesenglishname + "\" tesischarge=\"" + ret[i].tesischarge + "\"><a data-toggle='tab' href=\"#tabs-" + ret[i].tesenglishname + "\">" + ret[i].teschinesename + "</a></li>";
                    //alert(li_);
                    ul.append(li_);
                    //$("#"+ret[i].tesenglishname+"Item").jqGrid('GridUnload');
                    initItemList(ret[i].tesenglishname, sampleId, ret[i].testitemid);
                }
            }
        }
        $("#ctabs").tabs("refresh");
    });
}
function initItemList(n, sampleId, testItemId) {
    $("#" + n + "Item").jqGrid({
        mtype: "GET",
        url: "../order/getcheckitems?sampleId=" + sampleId + "&testItemId=" + testItemId,
        datatype: "json",
        cellEdit: true,
        cellsubmit: 'clientArray',
        colNames: ['checkid', '蜡块编号', '项目名称', '结果', '申请医生', '状态', '申请时间', 'chenameen', 'cheischarge'],
        colModel: [
            {name: 'checkid', index: 'checkid', hidden: true},
            {name: 'chiparaffincode', index: 'chiparaffincode', width: 90},
            {name: 'chenamech', index: 'chenamech', width: 120},
            {
                name: 'chetestresult',
                index: 'chetestresult',
                width: 120,
                editable: true,
                edittype: 'text',
                editrules: {edithidden: true, required: true}
            },
            {name: 'checreateuser', index: 'checreateuser', width: 80},
            {
                name: 'finishstatus',
                index: 'finishstatus',
                width: 60,
                formatter: "select",
                editoptions: {value: "0:未完成;1:已完成;"}
            },
            {name: 'checreatetime', index: 'checreatetime', width: 100},
            {name: 'chenameen', index: 'chenameen', hidden: true},
            {name: 'cheischarge', index: 'cheischarge', hidden: true}
        ],
        multiselect: true,
        shrinkToFit: true,
        scrollOffset: 2,
        rownumbers: true
    });
}

function setYJXBDiagnosis(data) {
    var emptyResult = jQuery.isEmptyObject(data);
    if (emptyResult) {
        for (var i = 0; i <= 3; i++) {
            var e = $("#textarea" + i);
            e.val('');
            e.attr("hiddenValue", "");
        }
        for (var j = 1; j <= 40; j++) {
            $("#c" + j).attr("checked", false);
        }
        return;
    }
    for (itm in data) {
        var resultid = data[itm].resultid;
        e = $("#" + itm);
        e.val(data[itm].restestresult);
        e.attr("hiddenValue", resultid);
    }
    setCheckStatus($("#textarea0").val());
}

function setCheckStatus(checkedIdStr) {
    /*for(var j = 1; j <= 40; j++) {
     $("#c"+j).attr("checked", false);
     }*/
    var array_ = checkedIdStr.split(",");
    for (var i = 0; i < array_.length; i++) {
        $("#" + array_[i]).attr("checked", true);
    }
}

function setSelectedValue(jsonStr) {
    var json = JSON.parse(jsonStr);
    $("#A5A6Result").val(json.A5A6Result);
    $("#A7Result").val(json.A7Result);
    $("#A9Result").val(json.A9Result);
    if($("#A5A6Result").val() == "阳性") {
        $("#A5A6Result").css("color","red");
    }
    if($("#A7Result").val() == "阳性") {
        $("#A7Result").css("color","red");
    }
    if($("#A9Result").val() == "阳性") {
        $("#A9Result").css("color","red");
    }
    if($("#A5A6Result").val() == "阳性" || $("#A7Result").val() == "阳性" || $("#A9Result").val() == "阳性" ) {
        $("textarea[printOrder=1]").val("阳性");
        $("textarea[printOrder=1]").css("color", "red");
    }
}



