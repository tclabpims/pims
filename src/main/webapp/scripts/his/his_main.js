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
			$("#requisitionid").val(data.requisitionid);
			$("#reqcustomercode").val(data.reqcustomercode);
			$("#reqpathologyid").val(data.reqpathologyid);
			$("#requisitionno").val(data.requisitionno);
			$("#reqsource").val(data.reqsource);
			$("#reqtype").val(data.reqtype);
			$("#reqdate").val(data.reqdate);
			$("#reqinspectionid").val(data.reqinspectionid);
			$("#reqdatechar").val(data.reqdatechar);
			$("#reqdeptcode").val(data.reqdeptcode);
			$("#reqdeptname").val(data.reqdeptname);
			$("#reqwardcode").val(data.reqwardcode);
			$("#reqwardname").val(data.reqwardname);
			$("#reqdoctorid").val(data.reqdoctorid);
			$("#reqdoctorname").val(data.reqdoctorname);
			$("#reqplanexectime").val(data.reqplanexectime);
			$("#reqdigcode").val(data.reqdigcode);
			$("#reqchargestatus").val(data.reqchargestatus);
			$("#reqsendhospital").val(data.reqsendhospital);
			$("#reqsendphone").val(data.reqsendphone);
			$("#reqstate").val(data.reqstate);
			$("#reqitemids").val(data.reqitemids);
			$("#reqitemnames").val(data.reqitemnames);
			$("#reqpatientid").val(data.reqpatientid);
			$("#reqinpatientid").val(data.reqinpatientid);
			$("#reqinpatientno").val(data.reqinpatientno);
			$("#reqpatienttype").val(data.reqpatienttype);
			$("#reqpatientnumber").val(data.reqpatientnumber);
			$("#reqpatientname").val(data.reqpatientname);
			$("#reqpatientsex").val(data.reqpatientsex);
			$("#reqpatientage").val(data.reqpatientage);
			$("#reqpatagetype").val(data.reqpatagetype);
			$("#reqpatbirthday").val(data.reqpatbirthday);
			$("#reqpatidcard").val(data.reqpatidcard);
			$("#reqpattelephone").val(data.reqpattelephone);
			$("#reqpataddress").val(data.reqpataddress);
			$("#reqpatdiagnosis").val(data.reqpatdiagnosis);
			$("#reqismenopause").val(data.reqismenopause);
			$("#reqlastmenstruation").val(data.reqlastmenstruation);
			$("#reqpatcompany").val(data.reqpatcompany);
			$("#reqsendhisorder").val(data.reqsendhisorder);
			$("#reqsampleid").val(data.reqsampleid);
			$("#reqisdeleted").val(data.reqisdeleted);
			$("#reqprintuser").val(data.reqprintuser);
			$("#reqprintusername").val(data.reqprintusername);
			$("#reqprinttime").val(data.reqprinttime);
			$("#reqsendtime").val(data.reqsendtime);
			$("#reqremark").val(data.reqremark);
			$("#reqfirstv").val(data.reqfirstv);
			$("#reqsecondv").val(data.reqsecondv);
			//$("#reqthirdv").val(data.reqthirdv);
			$("#reqfirstd").val(data.reqfirstd);
			$("#reqsecondd").val(data.reqsecondd);
			$("#reqfirstn").val(data.reqfirstn);
			$("#reqcreateuser").val(data.reqcreateuser);
			$("#reqcreatetime").val(data.reqcreatetime);
		} else {
			layer.msg("该申请单不存在！", {icon: 0, time: 1000});
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
	operate = $("#operate").val();
	if(operate == 'cancel') {
		$('#sampleForm')[0].reset();
		$('#examinaim').data('tag').clear();
	} else {
        var rowdatas = $('#new1').jqGrid('getRowData');
		var msg = "";
		var post = true;
		// alert(rowdatas);
		// return false;
		if(post) {
			$.post("../pimspathology/editSample", {
			    reqthirdv:JSON.stringify(rowdatas),
                requisitionid:$("#requisitionid").val(),
                reqcustomercode:$("#reqcustomercode").val(),
                reqpathologyid:$("#reqpathologyid").val(),
                requisitionno:$("#requisitionno").val(),
                reqsource:$("#reqsource").val(),
                reqtype:$("#reqtype").val(),
                reqdate:$("#reqdate").val(),
                reqinspectionid:$("#reqinspectionid").val(),
                reqdatechar:$("#reqdatechar").val(),
                reqdeptcode:$("#reqdeptcode").val(),
                reqdeptname:$("#reqdeptcode").find("option:selected").text(),
                reqwardcode:$("#reqwardcode").val(),
                reqwardname:$("#reqwardcode").find("option:selected").text(),
                reqdoctorid:$("#reqdoctorid").val(),
                reqdoctorname:$("#reqdoctorid").find("option:selected").text(),
                reqplanexectime:$("#reqplanexectime").val(),
                reqdigcode:$("#reqdigcode").val(),
                reqchargestatus:$("#reqchargestatus").val(),
                reqsendhospital:$("#reqsendhospital").val(),
                reqsendphone:$("#reqsendphone").val(),
                reqstate:$("#reqstate").val(),
                reqitemids:$("#reqitemids").val(),
                reqitemnames:$("#reqitemnames").val(),
                reqpatientid:$("#reqpatientid").val(),
                reqinpatientid:$("#reqinpatientid").val(),
                reqinpatientno:$("#reqinpatientno").val(),
                reqpatienttype:$("#reqpatienttype").val(),
                reqpatientnumber:$("#reqpatientnumber").val(),
                reqpatientname:$("#reqpatientname").val(),
                reqpatientsex:$("#reqpatientsex").val(),
                reqpatientage:$("#reqpatientage").val(),
                reqpatagetype:$("#reqpatagetype").val(),
                reqpatbirthday:$("#reqpatbirthday").val(),
                reqpatidcard:$("#reqpatidcard").val(),
                reqpattelephone:$("#reqpattelephone").val(),
                reqpataddress:$("#reqpataddress").val(),
                reqpatdiagnosis:$("#reqpatdiagnosis").val(),
                reqismenopause:$("#reqismenopause").val(),
                reqlastmenstruation:$("#reqlastmenstruation").val(),
                reqpatcompany:$("#reqpatcompany").val(),
                reqsendhisorder:$("#reqsendhisorder").val(),
                reqsampleid:$("#reqsampleid").val(),
                reqisdeleted:$("#reqisdeleted").val(),
                reqprintuser:$("#reqprintuser").val(),
                reqprintusername:$("#reqprintusername").val(),
                reqprinttime:$("#reqprinttime").val(),
                reqsendtime:$("#reqsendtime").val(),
                reqremark:$("#reqremark").val(),
                reqfirstv:$("#reqfirstv").val(),
                reqsecondv:$("#reqsecondv").val(),
                //reqthirdv:$("#reqthirdv").val(),
                reqfirstd:$("#reqfirstd").val(),
                reqsecondd:$("#reqsecondd").val(),
                reqfirstn:$("#reqfirstn").val(),
                reqcreateuser:$("#reqcreateuser").val(),
                reqcreatetime:$("#reqcreatetime").val()
			},
			function(data) {
				//var data = JSON.parse(data);
				if(data.success) {
					layer.msg(data.message, {icon: 1, time: 1000});
					layer.closeAll();
					// var rowData = {
					// 	requisitionid:data.requisitionid,
					// 	requisitionno:data.requisitionno,
					// 	reqitemnames:data.reqitemnames,
					// 	reqpathologyid:data.reqpathologyid
					// };
					// var ids = $('#new').jqGrid('getDataIDs');
                    // var newId = parseInt(ids[ids.length - 1] || 0) + 1;
					// $("#new").jqGrid('addRowData', newId, rowData);
					$("#new").trigger("reloadGrid");

				} else {
					layer.msg(data.message, {icon:2, time: 1000});
				}
			});
		} else {
			layer.msg(msg, {icon: 2, time: 1000});
		}
	}
}

function createNew1(reqid){
    $("#new1").jqGrid({
        url:"../pimspathology/ajax/getmaterial",
        datatype: "json",
        mtype:"GET",
        height: 170,
        width: 540,
        postData:{"reqId":reqid},
        colNames: ['申请单ID','ID','切取部位', '送检材料'],
        colModel: [
            {name:'requisitionid',hidden:true},
            {name:'materialid',hidden:true},
            { name: 'reqmsamplingparts', index: 'reqmsamplingparts',editable:true,edittype: "select",formatter: "select", editoptions:{value:"1:输卵管;2:肝脏;3:肺"}},
            { name: 'reqmmaterialtype', index: 'reqmmaterialtype',editable:true,edittype: "select",formatter: "select", editoptions:{value:"1:输卵管切除组织;2:肝脏切除组织;3:肺切除组织"}}
        ],
        loadComplete : function() {
            var table = this;
            setTimeout(function(){
                updatePagerIcons(table);
            }, 0);
        },
        viewrecords: true,
        multiselect: true,
        cellsubmit: "clientArray",
        //autowidth: true,
        cellEdit:true,
        rownumbers : true,
        onSelectAll:function(aRowids,status){
            var rowIds = $("#new1").jqGrid('getDataIDs');
            //alert(rowIds);
            for(var k = 0; k<rowIds.length; k++) {
                var curRowData = jQuery("#new1").jqGrid('getRowData', rowIds[k]);
                var curChk = $("#"+rowIds[k]+"").find(":checkbox");
                if(status){
                    curChk.attr('checked', 'true');   //设置所有checkbox被选中
                }else{
                    curChk.attr('checked', 'false');   //设置所有checkbox被选中
                }
            }
        }
    });
}
function addSample() {
	clearData();
	$.get("../pimspathology/editSample", {},
		function(data) {
			if(data.success) {
				$("#requisitionno").val(date.maxcode);
			} else {
			}
		});
	layer.open({
		type: 1,
		area: ['1000px','600px'],
		skin: 'layui-layer-molv',
		fix: false, //不固定
		maxmin: false,
		shade:0.6,
		title: "申请信息录入",
		content: $("#formDialog")
	});
}

function editSample() {
	clearData();
	var id = $("#new").jqGrid('getGridParam', 'selrow');
	var rowData = $("#new").jqGrid('getRowData',id);
    if (id == null || id == 0) {
        layer.msg('请先选择要修改的数据', {icon: 2, time: 1000});
        return false;
    }
	$.get("../pimspathology/canchange", {
			id:rowData.requisitionid
		},
		function(data) {
			if(data.success) {
				getSampleData(rowData.requisitionid);
				jQuery("#new1").jqGrid('setGridParam',{
					url: "../pimspathology/ajax/getmaterial",
					//发送数据
					postData : {"reqId":rowData.requisitionid}
				}).trigger('reloadGrid');//重新载入
				layer.open({
					type: 1,
					area: ['1000px','600px'],
					skin: 'layui-layer-molv',
					fix: false, //不固定
					maxmin: false,
					shade:0.6,
					title: "样本信息编辑",
					content: $("#formDialog")
				});
			}else{
				layer.msg(data.message, {icon:2, time: 1000});
			}
		});
}

function deleteSample() {
	var id = $("#new").jqGrid('getGridParam', 'selrow');
	var rowData = $("#new").jqGrid('getRowData',id);
    if (id == null || id == 0) {
        layer.msg('请先选择要删除的数据', {icon: 2, time: 1000});
        return false;
    }
	$.get("../pimspathology/canchange", {
			id:rowData.requisitionid
		},
		function(data) {
			if(data.success) {
				layer.confirm('确定删除选择数据？', {icon: 2, title:'警告'}, function(index){
					$.post('../pimspathology/deleteSample',{requisitionid:rowData.requisitionid},function(data) {
						layer.close(index);
						$("#new").trigger('reloadGrid');
					});
				});
			}else{
				layer.msg(data.message, {icon:2, time: 1000});
			}
		});
}

function clearData() {
    $('#sampleForm')[0].reset();
    jQuery("#new1").jqGrid("clearGridData");
}

$(function() {
	//表单校验
	// $("#sampleForm").Validform({
	// 	btnSubmit:"#addinfo",
	// 	tiptype:4,
	// 	callback:function(){
	// 	}
	// });
	$(".form_datetime").datetimepicker({
		minView: "month", //选择日期后，不会再跳转去选择时分秒
		format: "yyyy-mm-dd", //选择日期后，文本框显示的日期格式
		language: 'zh-CN', //汉化
		todayBtn:  1,
		autoclose:true //选择日期后自动关闭
	});
	$("#reqitemnames").autocomplete({
        source: function( request, response ) {
            $.ajax({
            	url: "../estitem/ajax/item",
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
        minLength: 0,
        select: function( event, ui ) {
        	$( "#reqitemids" ).val(ui.item.id);
        	$( "#reqitemnames" ).val(ui.item.name);
            //return false;
		}
	})
	.data( "ui-autocomplete" )._renderItem = function( ul, item ) {
		 return $( "<li>" )
			 .append( "<a style='font-size:12px;font-family: 微软雅黑;'>" + item.id + "," + item.value+ "</a>" )
			 .appendTo( ul );
	 };
	var clientHeight= $(window).innerHeight();
	var height =clientHeight-$('#div_1').height()- $('#div_2').height()-200;
	var req_code = $('#req_code').val();
	var patient_name = $('#patient_name').val();
	var send_hosptail = $('#send_hosptail').val();
	var req_bf_time = $('#req_bf_time').val();
	var req_af_time = $('#req_af_time').val();
	var send_dept = $('#send_dept').val();
	var send_doctor = $('#send_doctor').val();
	var req_sts = $('#req_sts').val();
	$("#new").jqGrid({
		url: "../pimspathology/ajax/pathology",
		mtype: "GET",
		datatype: "json",
		postData:{"req_code":req_code,"patient_name":patient_name,"send_hosptail":send_hosptail,"req_bf_time":req_bf_time,
			"req_af_time":req_af_time,"send_dept":send_dept,"send_doctor":send_doctor,"req_sts":req_sts},
		colNames: ['ID','临床申请', '病种类别', '申请年月','病人姓名','送检医院','送检科室','送检医生'],
		colModel: [
			{name:'requisitionid',hidden:true},
			{ name: 'requisitionno', index: 'requisitionno'},
			{ name: 'reqpathologyid', index: 'reqpathologyid'},
			{ name: 'reqdate', index: 'reqdate',formatter:function(cellvalue, options, row){return new Date(cellvalue).toLocaleString()},formatoptions:{newformat: 'y-m-d'}},
			{ name: 'reqpatientname', index: 'reqpatientname'},
			{ name: 'reqsendhospital', index: 'reqsendhospital'},
			{ name: 'reqdeptname', index: 'reqdeptname'},
			{ name: 'reqdoctorname', index: 'reqdoctorname'}
		],
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		ondblClickRow: function (id) {
			editSample();
		},
		viewrecords: true,
		height:height,
		autowidth: true,
		rowNum: 10,
		rowList:[10,20,30],
		rownumbers: true, // 显示行号
		rownumWidth: 35, // the width of the row numbers columns
		pager: "#pager"
	});
    createNew1("");
});

function searchList() {
	var req_code = $('#req_code').val();
	var patient_name = $('#patient_name').val();
	var send_hosptail = $('#send_hosptail').val();
	var req_bf_time = $('#req_bf_time').val();
	var req_af_time = $('#req_af_time').val();
	var send_dept = $('#send_dept').val();
	var send_doctor = $('#send_doctor').val();
	var req_sts = $('#req_sts').val();
	jQuery("#new").jqGrid("clearGridData");
	jQuery("#new").jqGrid('setGridParam',{
		url: "../pimspathology/ajax/pathology",
		//发送数据
		postData : {"req_code":req_code,"patient_name":patient_name,"send_hosptail":send_hosptail,"req_bf_time":req_bf_time,
			"req_af_time":req_af_time,"send_dept":send_dept,"send_doctor":send_doctor,"req_sts":req_sts},
		page : 1
	}).trigger('reloadGrid');//重新载入
}

function addRow(){
	var selectedId = $("#new1").jqGrid("getGridParam", "selrow");
    var  requisitionid = $('#requisitionid').val();
	var dataRow = {
        requisitionid:requisitionid,
		materialid: "",
		reqmsamplingparts: 1,
		reqmmaterialtype:1
	};
	var ids = $("#new1").jqGrid('getDataIDs');
	var rowid = 1;
	if(Math.max.apply(Math,ids) > ids.length ){
		rowid = Math.max.apply(Math,ids) + 1;
	}else{
		rowid = ids.length + 1;
	}
	if (selectedId) {
		$("#new1").jqGrid("addRowData", rowid, dataRow, "after", selectedId);
	} else {
		$("#new1").jqGrid("addRowData", rowid, dataRow, "last");
	}
	$('#plsfList').jqGrid('editRow', rowid, false);
}
function delRow(){
	var selectedId = $("#new1").jqGrid("getGridParam","selrow");
	if(!selectedId){
		alert("请选择要删除的行!");
		return;
	}else{
		$("#new1").jqGrid("delRowData", selectedId);
	}
}

function changethId() {
    var reqitemids = $('#reqitemids').val();
    $.ajax(
        {
            url:"/changeCity.action?province="+reqitemids,
            dataType:"json",
            cache:false,
            success:function(arr){
                var html = "";
                for(var i=0;i<arr.length;i++){
                    var o = arr[i];
                    var code = o.code;
                    var name = o.name;
                    html += "<option value='"+code+"'>"+name+"</option>";
                }
                $(city).html(html);
            }
        }
    )
}
