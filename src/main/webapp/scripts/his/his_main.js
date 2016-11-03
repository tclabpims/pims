/**
 * 回车事件
 * @param obj
 * @param event
 */
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
			searchList();
			break;
	}
}
/**
 * 获取申请单详细信息
 * @param id
 */
function getSampleData(id) {
	$.get("../pimspathology/get",{id:id},function(data) {
		if(data != "") {
			$("#requisitionid").val(data.requisitionid);//申请id
			$("#reqcustomerid").val(data.reqcustomerid);//客户id
			$("#reqpathologyid").val(data.reqpathologyid);//病种类别id
			$("#requisitionno").val(data.requisitionno);//申请单号
			$("#reqsource").val(data.reqsource);//申请单来源(0手工登记 1第三方系统接收)
			$("#reqtype").val(data.reqtype);//申请类型(1住院，2门诊，3手术室)
			$("#reqdate").val(CurentTime(new Date(data.reqdate)));//申请日期
			$("#reqinspectionid").val(data.reqinspectionid);//标本条码号
			$("#reqdatechar").val(data.reqdatechar);//申请日期（8位日期字符）
			$("#reqdeptcode").val(data.reqdeptcode);//申请科室
			$("#reqdeptname").val(data.reqdeptname);//申请科室名称
			$("#reqwardcode").val(data.reqwardcode);//申请病区
			$("#reqwardname").val(data.reqwardname);//申请病区名称
			$("#reqdoctorid").val(data.reqdoctorid);//申请医生id
			$("#reqdoctorname").val(data.reqdoctorname);//申请医生姓名
			$("#reqplanexectime").val(CurentTime(new Date(data.reqplanexectime)));//执行日期时间
			$("#reqdigcode").val(data.reqdigcode);//诊疗小组代码或者费用归属科室代码
			$("#reqchargestatus").val(data.reqchargestatus);//收费状态(1已收费,0未收费)
			$("#reqsendhospital").val(data.reqsendhospital);//送检医院
			$("#reqisemergency").val(data.reqisemergency);//是否加急（0平 1加急）
			$("#reqsendphone").val(data.reqsendphone);//送检电话
			$("#reqstate").val(data.reqstate);//申请状态(0已保存,1已接收,9已删除)
			$("#reqitemids").val(data.reqitemids);//多个项目id之间用逗号隔开(项目id1,项目id2,项目id3)
			$("#reqitemnames").val(data.reqitemnames);//多个项目名称之间用逗号隔开(项目名称1,项目名称2,项目名称3)
			$("#reqpatientid").val(data.reqpatientid);//患者id唯一号(病案号)
			$("#reqinpatientid").val(data.reqinpatientid);//就诊id(患者每一次来院的就诊id)
			$("#reqinpatientno").val(data.reqinpatientno);//住院序号(住院次数)
			$("#reqpatienttype").val(data.reqpatienttype);//患者类型(病人类型： 1住院,2门诊,3体检,4婚检,5科研,6特勤,7其他)
			$("#reqpatientnumber").val(data.reqpatientnumber);//住院卡号/门诊卡号
			$("#reqpatientname").val(data.reqpatientname);//患者姓名
			$("#reqpatientsex").val(data.reqpatientsex);//患者性别(1男,2女,3未知)
			$("#reqpatientage").val(data.reqpatientage);//患者年龄
			$("#reqpatagetype").val(data.reqpatagetype);//年龄类型(1年、2岁、3月、4周、5日、6小时)
			$("#reqpatbirthday").val(data.reqpatbirthday);//出生日期
			$("#reqpatidcard").val(data.reqpatidcard);//身份证号
			$("#reqpattelephone").val(data.reqpattelephone);//患者联系电话
			$("#reqpataddress").val(data.reqpataddress);//联系地址
			$("#reqpatdiagnosis").val(data.reqpatdiagnosis);//患者临床诊断
			$("#reqismenopause").val(data.reqismenopause);//是否绝经
			$("#reqlastmenstruation").val(data.reqlastmenstruation);//末次月经时间
			$("#reqpatcompany").val(data.reqpatcompany);//工作单位（检查要求）
			$("#reqsendhisorder").val(data.reqsendhisorder);//是否回写医嘱(0未发送,1已发送)
			$("#reqsampleid").val(data.reqsampleid);//标本id(申请接收后回写)
			$("#reqisdeleted").val(data.reqisdeleted);//是否删除(0正常，1已删除)
			$("#reqprintuser").val(data.reqprintuser);//申请单打印人员id
			$("#reqprintusername").val(data.reqprintusername);//申请单打印人员姓名
			$("#reqprinttime").val(data.reqprinttime);//申请单打印时间
			$("#reqsendtime").val(data.reqsendtime);//回写时间
			$("#reqremark").val(data.reqremark);//备注信息
			$("#reqfirstv").val(data.reqfirstv);//预留字段1(第一个varchar预留字段)
			$("#reqsecondv").val(data.reqsecondv);//预留字段2(第二个varchar预留字段)
			$("#reqthirdv").val(data.reqthirdv);//预留字段3(第二个varchar预留字段)
			$("#reqfirstd").val(CurentTime(new Date(data.reqfirstd)));//预留字段4(第一个datetime预留字段)
			$("#reqsecondd").val(CurentTime(new Date(data.reqsecondd)));//预留字段6(第二个datetime预留字段)
			$("#reqfirstn").val(data.reqfirstn);//预留字段6(第一个numberic预留字段)
			$("#reqcreateuser").val(data.reqcreateuser);//创建人员
			$("#reqcreatetime").val(CurentTime(new Date(data.reqcreatetime)));//创建时间
		} else {
			layer.msg("该申请单不存在！", {icon: 0, time: 1000});
		}
	});
}
/**
 * 保存信息
 */
function saveInfo() {
	var rowdatas = $('#new1').jqGrid('getRowData');
	var post = true;
	if(post) {
		$.post("../pimspathology/editSample", {
				material:JSON.stringify(rowdatas),
				requisitionid:$("#requisitionid").val(),//申请id
				reqcustomerid:$("#reqcustomerid").val(),//客户id
				reqpathologyid:$("#reqpathologyid").val(),//病种类别id
				requisitionno:$("#requisitionno").val(),//申请单号
				reqsource:$("#reqsource").val(),//申请单来源(0手工登记 1第三方系统接收)
				reqtype:$("#reqtype").val(),//申请类型(1住院，2门诊，3手术室)
				reqdate:$("#reqdate").val(),//申请日期
				reqinspectionid:$("#reqinspectionid").val(),//标本条码号
				reqdatechar:$("#reqdatechar").val(),//申请日期（8位日期字符）
				reqdeptcode:$("#reqdeptcode").val(),//申请科室
				reqdeptname:$("#reqdeptname").val(),//申请科室名称
				reqwardcode:$("#reqwardcode").val(),//申请病区
				reqwardname:$("#reqwardname").val(),//申请病区名称
				reqdoctorid:$("#reqdoctorid").val(),//申请医生id
				reqdoctorname:$("#reqdoctorname").val(),//申请医生姓名
				reqplanexectime:$("#reqplanexectime").val(),//执行日期时间
				reqdigcode:$("#reqdigcode").val(),//诊疗小组代码或者费用归属科室代码
				reqchargestatus:$("#reqchargestatus").val(),//收费状态(1已收费,0未收费)
				reqsendhospital:$("#reqsendhospital").val(),//送检医院
				reqisemergency:$("#reqisemergency").val(),//是否加急（0平 1加急）
				reqsendphone:$("#reqsendphone").val(),//送检电话
				reqstate:$("#reqstate").val(),//申请状态(0已保存,1已接收,9已删除)
				reqitemids:$("#reqitemids").val(),//多个项目id之间用逗号隔开(项目id1,项目id2,项目id3)
				reqitemnames:$("#reqitemnames").val(),//多个项目名称之间用逗号隔开(项目名称1,项目名称2,项目名称3)
				reqpatientid:$("#reqpatientid").val(),//患者id唯一号(病案号)
				reqinpatientid:$("#reqinpatientid").val(),//就诊id(患者每一次来院的就诊id)
				reqinpatientno:$("#reqinpatientno").val(),//住院序号(住院次数)
				reqpatienttype:$("#reqpatienttype").val(),//患者类型(病人类型： 1住院,2门诊,3体检,4婚检,5科研,6特勤,7其他)
				reqpatientnumber:$("#reqpatientnumber").val(),//住院卡号/门诊卡号
				reqpatientname:$("#reqpatientname").val(),//患者姓名
				reqpatientsex:$("#reqpatientsex").val(),//患者性别(1男,2女,3未知)
				reqpatientage:$("#reqpatientage").val(),//患者年龄
				reqpatagetype:$("#reqpatagetype").val(),//年龄类型(1年、2岁、3月、4周、5日、6小时)
				reqpatbirthday:$("#reqpatbirthday").val(),//出生日期
				reqpatidcard:$("#reqpatidcard").val(),//身份证号
				reqpattelephone:$("#reqpattelephone").val(),//患者联系电话
				reqpataddress:$("#reqpataddress").val(),//联系地址
				reqpatdiagnosis:$("#reqpatdiagnosis").val(),//患者临床诊断
				reqismenopause:$("#reqismenopause").val(),//是否绝经
				reqlastmenstruation:$("#reqlastmenstruation").val(),//末次月经时间
				reqpatcompany:$("#reqpatcompany").val(),//工作单位（检查要求）
				reqsendhisorder:$("#reqsendhisorder").val(),//是否回写医嘱(0未发送,1已发送)
				reqsampleid:$("#reqsampleid").val(),//标本id(申请接收后回写)
				reqisdeleted:$("#reqisdeleted").val(),//是否删除(0正常，1已删除)
				reqprintuser:$("#reqprintuser").val(),//申请单打印人员id
				reqprintusername:$("#reqprintusername").val(),//申请单打印人员姓名
				reqprinttime:$("#reqprinttime").val(),//申请单打印时间
				reqsendtime:$("#reqsendtime").val(),//回写时间
				reqremark:$("#reqremark").val(),//备注信息
				reqfirstv:$("#reqfirstv").val(),//预留字段1(第一个varchar预留字段)
				reqsecondv:$("#reqsecondv").val(),//预留字段2(第二个varchar预留字段)
				reqthirdv:$("#reqthirdv").val(),//预留字段3(第二个varchar预留字段)
				reqfirstd:$("#reqfirstd").val(),//预留字段4(第一个datetime预留字段)
				reqsecondd:$("#reqsecondd").val(),//预留字段6(第二个datetime预留字段)
				reqfirstn:$("#reqfirstn").val(),//预留字段6(第一个numberic预留字段)
				reqcreateuser:$("#reqcreateuser").val(),//创建人员
				reqcreatetime:$("#reqcreatetime").val(),//创建时间
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
/**
 * 创建取材材料单据
 * @param reqid
 */
function createNew1(reqid){
    $("#new1").jqGrid({
        url:"../pimspathology/ajax/getmaterial",
        datatype: "json",
        mtype:"GET",
        height: 170,
        width: 400,
        postData:{"reqId":reqid},
        colNames: ['ID','申请单ID','材料ID','切取部位', '送检材料','客户id','材料名称','取材特殊要求','备注信息','录入人员','录入时间'],
        colModel: [
			{name:'reqmid',hidden:true},//ID
            {name:'requisitionid',hidden:true},//申请单ID
            {name:'materialid',hidden:true},//ID
            { name: 'reqmsamplingparts', index: 'reqmsamplingparts',editable:true},//切取部位
            { name: 'reqmmaterialtype', index: 'reqmmaterialtype',editable:true,edittype: "select",formatter: "select",editoptions:{value:gettypes(), dataEvents: [
				{type: 'change',fn: function(e) {
					jQuery("#new1").jqGrid('setRowData', $(this).parent().parent().attr('id'), {reqmmaterialname:$(this).find("option:selected").text()});
					jQuery("#new1").jqGrid('setRowData', $(this).parent().parent().attr('id'), {materialid:$(this).val()});
				}}]}},//送检材料
			{name:'reqmcustomercode',hidden:true},//客户id
			{name:'reqmmaterialname',hidden:true},//材料名称
			{name:'reqmspecialrequirements',hidden:true},//取材特殊要求
			{name:'reqmremark',hidden:true},//备注信息
			{name:'reqmcreateuser',hidden:true},//录入人员
			{name:'reqmcreatetime',hidden:true}//录入时间
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
        cellEdit:true,
        rownumbers : true,
        onSelectAll:function(aRowids,status){
            var rowIds = $("#new1").jqGrid('getDataIDs');
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

function gettypes(){
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
/**
 * 新增单据
 */
function addSample() {
	clearData();
	$("#savebutton").removeAttr("disabled");
	$.get("../pimspathology/getcode", {},
		function(data) {
			if(data.success) {
				$("#requisitionno").val(data.maxcode);
			} else {
			}
		}
	);
	$('#sampleForm').find('input,textarea,select').removeAttr('disabled');
	$("#reqpathologyid").attr("disabled","disabled");
	$("#requisitionid").val("");//申请id
	$("#reqcustomerid").val($("#lcal_hosptail").val());//客户id
	//$("#reqpathologyid").val($("#local_logyid").val());//病种类别id
	//$("#requisitionno").val("");//申请单号
	$("#reqsource").val("0");//申请单来源(0手工登记 1第三方系统接收)
	//$("#reqtype").val("");//申请类型(1住院，2门诊，3手术室)
	$("#reqdate").val(CurentTime(new Date()));//申请日期
	$("#reqinspectionid").val("");//标本条码号
	$("#reqdatechar").val(CurentTime1(new Date()));//申请日期（8位日期字符）
	//$("#reqdeptcode").val("");//申请科室
	$("#reqdeptname").val("");//申请科室名称
	//$("#reqwardcode").val("");//申请病区
	$("#reqwardname").val("");//申请病区名称
	//$("#reqdoctorid").val("");//申请医生id
	$("#reqdoctorname").val("");//申请医生姓名
	$("#reqplanexectime").val(CurentTime(new Date()));//执行日期时间
	$("#reqdigcode").val("");//诊疗小组代码或者费用归属科室代码
	$("#reqchargestatus").val("");//收费状态(1已收费,0未收费)
	//$("#reqsendhospital").val("");//送检医院
	$("#reqisemergency").val("");//是否加急（0平 1加急）
	$("#reqsendphone").val("");//送检电话
	$("#reqstate").val("0");//申请状态(0已保存,1已接收,9已删除)
	$("#reqitemids").val("");//多个项目id之间用逗号隔开(项目id1,项目id2,项目id3)
	$("#reqitemnames").val("");//多个项目名称之间用逗号隔开(项目名称1,项目名称2,项目名称3)
	$("#reqpatientid").val("1");//患者id唯一号(病案号)
	$("#reqinpatientid").val("1");//就诊id(患者每一次来院的就诊id)
	$("#reqinpatientno").val("1");//住院序号(住院次数)
	//$("#reqpatienttype").val("");//患者类型(病人类型： 1住院,2门诊,3体检,4婚检,5科研,6特勤,7其他)
	$("#reqpatientnumber").val("");//住院卡号/门诊卡号
	$("#reqpatientname").val("");//患者姓名
	//$("#reqpatientsex").val("");//患者性别(1男,2女,3未知)
	$("#reqpatientage").val("");//患者年龄
	//$("#reqpatagetype").val("");//年龄类型(1年、2岁、3月、4周、5日、6小时)
	$("#reqpatbirthday").val("");//出生日期
	$("#reqpatidcard").val("");//身份证号
	$("#reqpattelephone").val("");//患者联系电话
	$("#reqpataddress").val("");//联系地址
	$("#reqpatdiagnosis").val("");//患者临床诊断
	$("#reqismenopause").val("");//是否绝经
	$("#reqlastmenstruation").val("");//末次月经时间
	$("#reqpatcompany").val("");//工作单位（检查要求）
	$("#reqsendhisorder").val("");//是否回写医嘱(0未发送,1已发送)
	$("#reqsampleid").val("");//标本id(申请接收后回写)
	$("#reqisdeleted").val("0");//是否删除(0正常，1已删除)
	$("#reqprintuser").val("");//申请单打印人员id
	$("#reqprintusername").val("");//申请单打印人员姓名
	$("#reqprinttime").val("");//申请单打印时间
	$("#reqsendtime").val("");//回写时间
	$("#reqremark").val("");//备注信息
	$("#reqfirstv").val("");//预留字段1(第一个varchar预留字段)
	$("#reqsecondv").val("");//预留字段2(第二个varchar预留字段)
	$("#reqthirdv").val("");//预留字段3(第二个varchar预留字段)
	$("#reqfirstd").val("");//预留字段4(第一个datetime预留字段)
	$("#reqsecondd").val("");//预留字段6(第二个datetime预留字段)
	$("#reqfirstn").val("");//预留字段6(第一个numberic预留字段)
	$("#reqcreateuser").val($("#local_userid").val());//创建人员
	$("#reqcreatetime").val(CurentTime(new Date()));//创建时间
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
/**
 * 修改数据
 * @returns {boolean}
 */
function viewSample() {
	$("#savebutton").attr("disabled","disabled");
	clearData();
	var id = $("#new").jqGrid('getGridParam', 'selrow');
	var rowData = $("#new").jqGrid('getRowData',id);
    if (id == null || id == 0) {
        layer.msg('请先选择要修改的数据', {icon: 2, time: 1000});
        return false;
    }
	getSampleData(rowData.requisitionid);
	$('#sampleForm').find('input,textarea,select').attr('disabled','disabled');
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
}
/**
 * 删除数据
 * @returns {boolean}
 */
function deleteSample() {
	var id = $("#new").jqGrid('getGridParam', 'selrow');
	var rowData = $("#new").jqGrid('getRowData',id);
	if (id == null || id == 0) {
		layer.msg('请先选择要删除的数据', {icon: 2, time: 1000});
		return false;
	}
	layer.confirm('确定删除选择数据？', {icon: 2, title:'警告'}, function(index){
		$.get("../pimspathology/canchange", {
				id:$("#requisitionid").val(),
				sts:"2"
			},
			function(data) {
				if(data.success) {
					$.post('../requisitionid/deleteSample',{requisitionid:rowData.requisitionid},function(data) {
						layer.close(index);
						$("#new").trigger('reloadGrid');
					});
				}else{
					layer.msg(data.message, {icon:2, time: 1000});
				}
			});
	});
}
/**
 * 清除数据
 */
function clearData() {
    $('#sampleForm')[0].reset();
    jQuery("#new1").jqGrid("clearGridData");
}
/**
 * 初始化
 */
$(function() {
	//表单校验
	// $("#sampleForm").Validform({
	// 	btnSubmit:"#addinfo",
	// 	tiptype:4,
	// 	callback:function(){
	// 	}
	// });
	$('#tabs a').click(function (e) {
		var hrefval = $(this).attr("href");
		$("#req_sts").val(hrefval);
		searchList();
		e.preventDefault();
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
	//检查项目
	$("#reqitemnames").autocomplete({
		source: function( request, response ) {
			$.ajax({
				url: "../estitem/ajax/item",
				dataType: "json",
				data: {
					name : request.term,
					tesitemtype: 1
				},
				success: function( data ) {
					response( $.map( data, function( result ) {
						return {
							label: result.id + " : " + result.name,
							value: result.name,
							id : result.id,
							tespathologyid:result.tespathologyid
						}
					}));
				}
			});
		},
		minLength: 0,
		select: function( event, ui ) {
			$( "#reqitemids" ).val(ui.item.id);
			$( "#reqitemnames" ).val(ui.item.value);
			$("#reqpathologyid").val(ui.item.tespathologyid);
			//return false;
		}
	})
		.data( "ui-autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li>" )
			.append( "<a style='font-size:12px;font-family: 微软雅黑;'>" + item.id + "," + item.value+ "</a>" )
			.appendTo( ul );
	};
	//申请病区
	$("#reqwardname").autocomplete({
		source: function( request, response ) {
			$.ajax({
				url: "../basadata/ajax/item",
				dataType: "json",
				data: {
					name : request.term,//名称
					bddatatype:1,//病区
					bdcustomerid:$("#lcal_hosptail").val()//账号所属医院
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
			$( "#reqwardcode" ).val(ui.item.id);
			$( "#reqwardname" ).val(ui.item.value);
			//return false;
		}
	})
		.data( "ui-autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li>" )
			.append( "<a style='font-size:12px;font-family: 微软雅黑;'>" + item.id + "," + item.value+ "</a>" )
			.appendTo( ul );
	};
	//送检医院
	$("#reqsendhospital").autocomplete({
		source: function( request, response ) {
			$.ajax({
				url: "../basadata/ajax/item",
				dataType: "json",
				data: {
					name : request.term,//名称
					bddatatype:4,//送检医院
					bdcustomerid:$("#lcal_hosptail").val()//账号所属医院
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
			//$( "#reqwardcode" ).val(ui.item.id);
			$( "#reqsendhospital" ).val(ui.item.value);
			//return false;
		}
	})
		.data( "ui-autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li>" )
			.append( "<a style='font-size:12px;font-family: 微软雅黑;'>" + item.id + "," + item.value+ "</a>" )
			.appendTo( ul );
	};
	//送检科室
	$("#reqdeptname").autocomplete({
		source: function( request, response ) {
			$.ajax({
				url: "../basadata/ajax/item",
				dataType: "json",
				data: {
					name : request.term,//名称
					bddatatype:2,//送检医院
					bdcustomerid:$("#lcal_hosptail").val()//账号所属医院
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
			$( "#reqdeptcode" ).val(ui.item.id);
			$( "#reqdeptname" ).val(ui.item.value);
			//return false;
		}
	})
		.data( "ui-autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li>" )
			.append( "<a style='font-size:12px;font-family: 微软雅黑;'>" + item.id + "," + item.value+ "</a>" )
			.appendTo( ul );
	};
	//送检医生
	$("#reqdoctorname").autocomplete({
		source: function( request, response ) {
			$.ajax({
				url: "../basadata/ajax/item",
				dataType: "json",
				data: {
					name : request.term,//名称
					bddatatype:3,//送检医生
					bdcustomerid:$("#lcal_hosptail").val()//账号所属医院
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
			$( "#reqdoctorid" ).val(ui.item.id);
			$( "#reqdoctorname" ).val(ui.item.value);
			//return false;
		}
	})
		.data( "ui-autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li>" )
			.append( "<a style='font-size:12px;font-family: 微软雅黑;'>" + item.id + "," + item.value+ "</a>" )
			.appendTo( ul );
	};
	//手术医生
	$("#reqsecondv").autocomplete({
		source: function( request, response ) {
			$.ajax({
				url: "../basadata/ajax/item",
				dataType: "json",
				data: {
					name : request.term,//名称
					bddatatype:3,//送检医生
					bdcustomerid:$("#lcal_hosptail").val()//账号所属医院
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
			//$( "#reqdoctorid" ).val(ui.item.id);
			$( "#reqsecondv" ).val(ui.item.value);
			//return false;
		}
	})
		.data( "ui-autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li>" )
			.append( "<a style='font-size:12px;font-family: 微软雅黑;'>" + item.id + "," + item.value+ "</a>" )
			.appendTo( ul );
	};

	var clientHeight= $(window).innerHeight();
	var height =clientHeight - $('#div_0').height()-320;
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
			{ name: 'reqpathologyid', index: 'reqpathologyid',formatter: "select", editoptions:{value:gettypes1()}},
			{ name: 'reqdate', index: 'reqdate',formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))}},
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
			viewSample();
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
 * 查询数据
 */
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
/**
 * 增加行
 */
function addRow(){
	var selectedId = $("#new1").jqGrid("getGridParam", "selrow");
    var  requisitionid = $('#requisitionid').val();
	var dataRow = {
		requisitionid:requisitionid,//申请单ID
		materialid:"",//ID
		reqmsamplingparts:"",//切取部位
		reqmmaterialtype:1,//送检材料
		reqmcustomercode:$("#all_hosptial").val(),//客户id
		reqmmaterialname:"",//材料名称
		reqmspecialrequirements:"",//取材特殊要求
		reqmremark:"",//备注信息
		reqmcreateuser:$("#local_userid").val(),//录入人员
		reqmcreatetime:CurentTime(new Date())//录入时间
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
/**
 * 删除行
 */
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

function CurentTime(now) {
	//var now = new Date();
	var year = now.getFullYear();       //年
	var month = now.getMonth() + 1;     //月
	var day = now.getDate();            //日
	var hh = now.getHours();            //时
	var mm = now.getMinutes();          //分
	var ss = now.getMilliseconds();    //秒
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

function CurentTime1(now) {
	//var now = new Date();
	var year = now.getFullYear();       //年
	var month = now.getMonth() + 1;     //月
	var day = now.getDate();            //日
	var hh = now.getHours();            //时
	var mm = now.getMinutes();          //分
	var ss = now.getMilliseconds();    //秒
	var clock = year;
	if(month < 10)
		clock += "0";
	clock += month;
	if(day < 10)
		clock += "0";
	clock += day;
	return(clock);
}
