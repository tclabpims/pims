function deleteYlxh(obj) {
	var fee = $("#fee").val();
	$("#fee").val(parseInt(fee) - parseInt(obj.id));
}

function receive(obj,event) {
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
			var sampleno = $("#sampleno_text").val();
			$.get("../sample/ajax/receive",{id:id,sampleno:sampleno},function(data) {
				var data = JSON.parse(data);
				if(data.success == 1) {
					layer.msg(data.message, {icon: 2, time: 1000});
				} else if(data.success == 2) {
					layer.msg(data.message, {icon: 2, time: 1000});
					var html = "";
					html += "<tr><td><b>医嘱号</b></td><td>" + data.id + "</td></tr>";
					html += "<tr><td><b>样本号</b></td><td>" + data.sampleno + "</td></tr>";
					html += "<tr><td><b>姓名</b></td><td>" + data.pname + "</td></tr>";
					html += "<tr><td><b>就诊卡号</b></td><td>" + data.pid + "</td></tr>";
					html += "<tr><td><b>性别</b></td><td>" + data.sex + "</td></tr>";
					html += "<tr><td><b>年龄</b></td><td>" + data.age + "</td></tr>";
					html += "<tr><td><b>诊断</b></td><td>" + data.diag + "</td></tr>";
					html += "<tr><td><b>检验目的</b></td><td>" + data.exam + "</td></tr>";
					html += "<tr><td><b>检验时间</b></td><td>" + data.receivetime + "</td></tr>";
					$("#now").html(html);
				} else {
					var html = "";
					html += "<tr><td><b>医嘱号</b></td><td>" + data.id + "</td></tr>";
					html += "<tr><td><b>样本号</b></td><td>" + data.sampleno + "</td></tr>";
					html += "<tr><td><b>姓名</b></td><td>" + data.pname + "</td></tr>";
					html += "<tr><td><b>就诊卡号</b></td><td>" + data.pid + "</td></tr>";
					html += "<tr><td><b>性别</b></td><td>" + data.sex + "</td></tr>";
					html += "<tr><td><b>年龄</b></td><td>" + data.age + "</td></tr>";
					html += "<tr><td><b>诊断</b></td><td>" + data.diag + "</td></tr>";
					html += "<tr><td><b>检验目的</b></td><td>" + data.exam + "</td></tr>";
					html += "<tr><td><b>检验时间</b></td><td>" + data.receivetime + "</td></tr>";
					$("#now").html(html);
					var rowData = {
						id:data.id,
						sampleno:data.sampleno,
						shm:data.shm,
						pname:data.pname,
						section:data.section,
						bed:data.bed,
						sex:data.sex,
						age:data.age,
						receivetime:data.receivetime,
						exam:data.exam,
						sampletype:data.sampletype,
						pid:data.pid,
						feestatus:data.feestatus,
						diag:data.diag,
						cycle:data.cycle,
						requester:data.requester,
						part:data.part,
						requestmode:data.requestmode,
						fee:data.fee
					};
					var ids = $('#new').jqGrid('getDataIDs');
		            var newId = parseInt(ids[ids.length - 1] || 0) + 1;
					$("#new").jqGrid('addRowData', newId, rowData);
					layer.msg(data.message, {icon: 1, time: 1000});
				}
			});
			break;
	}
}

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

function getSampleData(id) {
	$.get("../pimspathology/get",{id:id},function(data) {
		if(data != "") {
			var data = JSON.parse(data);
			$("#requisitionid").val(data.requisitionid);
			$("#requisitionno").val(data.requisitionno);
			$("#reqitemnames").val(data.reqitemnames);
			$("#reqpathologyid").val(data.reqpathologyid);
			$("#patientname").val(data.patientname);
			$("#patientname").val(data.patientname);
			$("#sex").val(data.sex);
			$("#age").val(data.age);
			$("#diagnostic").val(data.diagnostic);
			var $tag_obj = $('#examinaim').data('tag');
			$tag_obj.clear();
			var ylxhMap = data.ylxhMap;
			var feeMap = data.feeMap;
			for(var key in ylxhMap) {
				$tag_obj.add(ylxhMap[key], key, feeMap[key]);
			}
			$("#sampletype").val(data.sampletype);
			$("#fee").val(data.fee);
			$("#feestatus").val(data.feestatus);
			$("#requester").val(data.requester);
			$("#receivetime").val(data.receivetime);
			$("#executetime").val(data.executetime);
		} else {
			if(type == 1) {
				layer.msg("医嘱号为" + id + "的标本不存在！", {icon: 0, time: 1000});
			} else {
				layer.msg("样本号为" + id + "的标本不存在！", {icon: 0, time: 1000});
			}
		}
	});
}

function getPatient(obj,event) {
	var e=e||event;
    var key = event.keyCode;
    if(navigator.appName=="Netscape"){
		key=e.which;
	}else{
		key=event.keyCode;
	}
	switch(key){
		case 13 : 
			$.get("../sample/ajax/getpatient",{pid:obj.value},function(data) {
				var data = JSON.parse(data);
				if($("#doctadviseno").val() == "") {
					if(data.ispid) {
						$("#patientid").val(data.pid);
						$("#patientname").val(data.pname);
						$("#sex").val(data.sex);
						$("#age").val(data.age);
					} else{
						layer.msg("就诊卡号为" + obj.value + "的病人不存在！", {icon: 0, time: 1000})
					}
				} else {
					layer.msg("医嘱号已存在，无权限修改接诊卡号！", {icon: 0, time: 1000});
				}
			});
			break;
	}
}

function Pad(num, n) {
    return (Array(n).join(0) + num).slice(-n);
}

function isDate(str){
	if(!isNaN(str.substring(0,4)) && !isNaN(str.substring(4,6)) && !isNaN(str.substring(6,8))) {
		var year = parseInt(str.substring(0,4));
		var month = parseInt(str.substring(4,6))-1;
		var day = parseInt(str.substring(6,8));
		var date=new Date(year,month,day);
		if(date.getFullYear()==year&&date.getMonth()==month&&date.getDate()==day) {
			return true;
		} else {
			return false;
		}
	} else {
		return false;
	}
} 

function saveInfo() {

	var requisitionid,requisitionno,reqitemnames,reqpathologyid,patientname,sex,age,ageunit,reqpattelephone,reqirnpatientno,reqpatientnumber,reqfirstv,reqpataddress,reqsendhospital,reqdeptcode,
		reqdoctorid,reqdate,reqsendphone,reqplanexectime,reqsecondv,reqfirstn;
	operate = $("#operate").val();
	if(operate == 'cancel') {
		$('#sampleForm')[0].reset();
		$('#examinaim').data('tag').clear();
	} else {
		requisitionid = $("#requisitionid").val();
		requisitionno = $("#requisitionno").val();
		reqitemnames = $("#reqitemnames").val();
		reqpathologyid = $("#reqpathologyid").val();
		var msg = "";
		var post = true;
		if(post) {
			$.post("../pimspathology/editSample", {
					operate : operate,
					requisitionid : requisitionid,
					requisitionno : requisitionno,
					reqitemnames : reqitemnames,
					reqpathologyid : reqpathologyid
			},
			function(data) {
				var data = JSON.parse(data);
				if(data.success) {
					var rowData = {
						requisitionid:data.requisitionid,
						requisitionno:data.requisitionno,
						reqitemnames:data.reqitemnames,
						reqpathologyid:data.reqpathologyid
					};
					var ids = $('#new').jqGrid('getDataIDs');
		            var newId = parseInt(ids[ids.length - 1] || 0) + 1;
					$("#new").jqGrid('addRowData', newId, rowData);
					layer.msg(data.message, {icon: 1, time: 1000});
				} else {
					layer.msg(data.message, {icon:2, time: 1000});
				}
			});
		} else {
			layer.msg(msg, {icon: 2, time: 1000});
		}
	}
}

function addSample() {
	// $("#sampleno").val($("#sampleno_text").val());
	$("#operate").val("add");
	layer.open({
		type: 1,
		area: ['1000px','360px'],
		skin: 'layui-layer-molv',
		fix: false, //不固定
		maxmin: false,
		shade:0.6,
		title: "申请信息录入",
		content: $("#formDialog")
	});
}

function editSample() {
	// $("#sampleno").val($("#sampleno_text").val());
	$("#operate").val("edit");
	var id = $("#new").jqGrid('getGridParam', 'selrow');
    if (id == null || id == 0) {
        layer.msg('请先选择要修改的数据', {icon: 2, time: 1000});
        return false;
    }
    getSampleData(id,1);
	layer.open({
		type: 1,
		area: ['1000px','360px'],
		skin: 'layui-layer-molv',
		fix: false, //不固定
		maxmin: false,
		shade:0.6,
		title: "样本信息编辑",
		content: $("#formDialog")
	});
}

function deleteSample() {
	// $("#sampleno").val($("#sampleno_text").val());
	$("#operate").val("delete");
	var id = $("#new").jqGrid('getGridParam', 'selrow');
    if (id == null || id == 0) {
        layer.msg('请先选择要删除的数据', {icon: 2, time: 1000});
        return false;
    }
    getSampleData(id,1);
	layer.open({
		type: 1,
		area: ['1000px','360px'],
		skin: 'layui-layer-molv',
		fix: false, //不固定
		maxmin: false,
		shade:0.6,
		title: "样本信息删除",
		content: $("#formDialog")
	});
}

function clearData() {
	$('#sampleForm')[0].reset();
	$('#examinaim').data('tag').clear();
}

$(function() {
    /**
	$("#sampletype").val("C");
	
	$("#section").autocomplete({
        source: function( request, response ) {
            $.ajax({
            	url: "../ajax/searchSection",
                dataType: "json",
                data: {
                    name : request.term
                },
                success: function( data ) {
                	response( $.map( data, function( result ) {
                        return {
                            label: result.id + " : " + result.name,
                            value: result.name,
                            id : result.id
                        }
                    }));
                }
            });
        },
        minLength: 1,
        select: function( event, ui ) {
        	$( "#sectionCode" ).val(ui.item.id);
        	$( "#section" ).val(ui.item.value);
            return false;
		}
	});
	
	$("#requester").autocomplete({
        source: function( request, response ) {
            $.ajax({
            	url: "../ajax/searchContactInfo",
                dataType: "json",
                data: {
                    name : request.term
                },
                success: function( data ) {
                	response( $.map( data, function( result ) {
                        return {
                            label: result.id + " : " + result.name,
                            value: result.name,
                            id : result.id
                        }
                    }));
                }
            });
        },
        minLength: 1
	});
	**/
	$("#new").jqGrid({
		url: "../pimspathology/ajax/pathology",
		mtype: "GET",
		datatype: "json",
		colNames: ['ID','临床申请', '病种类别', '申请年月','病人姓名','送检医院','送检科室','送检医生'],
		colModel: [
			{name:'requisitionid',hidden:true},
			{ name: 'requisitionno', index: 'requisitionno'},
			{ name: 'reqpathologyid', index: 'reqpathologyid'},
			{ name: 'reqdate', index: 'reqdate'},
			{ name: 'reqpatientname', index: 'reqpatientname'},
			{ name: 'reqsendhospital', index: 'reqsendhospital'},
			{ name: 'reqdeptname', index: 'reqdeptname'},
			{ name: 'reqdoctorname', index: 'reqdoctorname'}
		],
		viewrecords: true,
		height:"100%",
		rowNum:-1
	});
});

function removeAllChild(pnode)
{
	var childs=pnode.childNodes;
	for(var i=childs.length-1;i>=0;i--){
		pnode.removeChild(childs.item(i));
	}
}

function searchList() {
	var pnode = document.getElementById("new");
	removeAllChild(pnode);
	alert(pnode);
	// $("#new").jqGrid({
    //     url: "../pimspathology/ajax/pathology",
    //     mtype: "GET",
    //     datatype: "json",
    //     colNames: ['ID','临床申请', '病种类别', '申请年月','病人姓名','送检医院','送检科室','送检医生'],
    //     colModel: [
		// 	{name:'requisitionid',hidden:true},
    //         { name: 'requisitionno', index: 'requisitionno'},
    //         { name: 'reqpathologyid', index: 'reqpathologyid'},
    //         { name: 'reqdate', index: 'reqdate'},
    //         { name: 'reqpatientname', index: 'reqpatientname'},
    //         { name: 'reqsendhospital', index: 'reqsendhospital'},
    //         { name: 'reqdeptname', index: 'reqdeptname'},
    //         { name: 'reqdoctorname', index: 'reqdoctorname'}
    //     ],
    //     viewrecords: true,
    //     height:"100%",
    //     rowNum:-1
    // });
}