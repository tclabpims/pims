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
/**
 * 根据申请单据补充登记单信息
 * @param id
 */
function getSampleData(id) {
	$.get("../pimspathology/get",{id:id},function(data) {
		if(data != "") {
			$("#sampleid").val("");//标本id
			$("#saminspectionid").val("");//标本条码号
			$("#sampathologycode").val(data.requisitionno);//病理编号
			$("#samcustomerid").val(data.reqcustomerid);//客户id
			$("#samcustomercode").val(data.reqcustomercode);//客户id
			$("#sampathologyid").val(data.reqpathologyid);//病种类别id
			$("#samsource").val(data.reqsource);//是否外送标本(0本院,1外送)
			$("#samotherid").val("");//第三方条码号
			$("#samrequistionid").val(data.requisitionno);//申请单号(多个申请单号之间用逗号隔开)
			$("#sampatientid").val(data.reqpatientid);//患者唯一号(病案号)
			$("#saminpatientid").val(data.reqinpatientid);//就诊id(患者每一次来院的id)
			$("#saminpatientno").val(data.reqinpatientno);//住院序号(住院次数)
			$("#sampatientnumber").val(data.reqpatientnumber);//住院卡号/门诊卡号
			$("#sampatienttype").val(data.reqpatienttype);//患者类型(病人类型： 1门诊,2住院,3体检,4婚检,5科研,6特勤,7其他)
			$("#sampatientname").val(data.reqpatientname);//姓名
			$("#sampatientsex").val(data.reqpatientsex);//患者性别(1男,2女,3未知)
			$("#sampatientage").val(data.reqpatientage);//年龄
			$("#sampatientagetype").val(data.reqpatagetype);//年龄类型(1年、2岁、3月、4周、5日、6小时)
			$("#sampatientbed").val(data.reqfirstn);//患者床号
			$("#samsampleclass").val("1");//标本种类
			$("#samsamplename").val(data.sjcl);//标本名称(,多个检查项目名称之间用逗号隔开)
			$("#sampopuser").val(data.reqitemids);//标本检查项目id(多个检验目的之间用逗号隔开)
			$("#samisemergency").val(data.reqisemergency);//是否加急(0不加急,1加急)
			$("#samisdecacified").val("");//是否脱钙(0没有脱钙,1已脱钙)
			$("#samissamplingall").val("");//是否全取(0,未全取,1全取)
			$("#samsamplestatus").val("0");//标本状态(0已登记,1已取材,2包埋,3已切片,4已初诊,5已审核,6已发送,7会诊中,8报告已打印)
			$("#samreqtime").val(CurentTime(new Date(data.reqdate)));//申请时间
			$("#samreqdocid").val(data.reqdoctorid);//申请医生id
			$("#samreqdocname").val(data.reqdoctorname);//申请医生姓名
			$("#samsendtime").val(CurentTime(new Date(data.reqplanexectime)));//送检时间
			$("#samsenddoctorid").val(data.reqdoctorid);//送检医生id
			$("#samsenddoctorname").val(data.reqdoctorname);//送检医生姓名----
			$("#samsendhospital").val(data.reqsendhospital);//送检单位名称
			$("#samsendphone").val(data.reqsendphone);//送检联系电话
			$("#samdigcode").val("");//诊疗小组代码
			$("#samdeptcode").val(data.reqdeptcode);//科室代码
			$("#samdeptname").val(data.reqdeptname);//科室名称
			$("#samwardcode").val(data.reqwardcode);//病区代码
			$("#samwardname").val(data.reqwardname);//病区名称
			$("#sampatientdignoses").val(data.reqpatdiagnosis);//临床诊断
			$("#sampatientidcardno").val(data.reqpatidcard);//身份证号
			$("#sampatientaddress").val(data.reqpataddress);//联系地址
			$("#sampatientphoneno").val(data.reqpattelephone);//联系电话
			$("#sampatientcompany").val(data.reqpatcompany);//工作单位
			$("#samismenopause").val(data.reqismenopause);//是否绝经
			$("#reqlastmenstruation").val(data.reqlastmenstruation);//末次月经时间
			$("#samischarged").val(data.reqchargestatus);//收费状态(0未收费,1已收费)
			$("#samreceivertime").val(CurentTime(new Date(data.reqcreatetime)));//接收时间
			$("#samreceiverid").val(data.reqcreateuser);//接收人员id
			$("#samreceivername").val("");//接收人员姓名
			$("#samregisttime").val(CurentTime(new Date()));//登记时间
			$("#samregisterid").val($("#local_userid").val());//登记人员id
			$("#samregistername").val($("#local_username").val());//登记人员姓名
			$("#saminitiallytime").val("");//初诊时间
			$("#saminitiallyuserid").val("");//初诊人员id
			$("#saminitiallyusername").val("");//初诊人员姓名
			$("#samisdeleted").val("0");//删除标志（0正常，1已删除）
			$("#samcreatetime").val(CurentTime(new Date()));//创建时间
			$("#samcreateuser").val($("#local_userid").val());//创建人
			$("#samthirdv").val(data.reqremark);//手术所见
			if(data.reqfirstv == "1"){
				$("input[name='samfirstv'][value='1']").attr("checked",true);
			}else{
				$("input[name='samfirstv'][value='2']").attr("checked",true);
			}
		} else {
			layer.msg("该申请单不存在！", {icon: 0, time: 1000});
		}
	});
}
/**
 * 保存数据
 * $("input[name='samfirstv']:checked").val(),
 * $("#reqdeptcode").find("option:selected").text(),
 */
function saveInfo() {
	var msg = "";
	var post = true;
	if(post) {
		$.post("../pathologysample/sample/editSample", {
			samfirstv:$("input[name='samfirstv']:checked").val(),
			samsenddoctorname:$("#samsenddoctorid").find("option:selected").text(),
            samsenddoctorname:$("#samsenddoctorname").val(),
			samsecondv:$("input[name='samsecondv']:checked").val(),
			sampleid:$("#sampleid").val(),//标本id
			saminspectionid:$("#saminspectionid").val(),//标本条码号
			sampathologycode:$("#sampathologycode").val(),//病理编号
			samcustomerid:$("#samcustomerid").val(),//客户id
			samcustomercode:$("#samcustomercode").val(),//客户id
			sampathologyid:$("#sampathologyid").val(),//病种类别id
			samsource:$("#samsource").val(),//是否外送标本(0本院,1外送)
			samotherid:$("#samotherid").val(),//第三方条码号
			samrequistionid:$("#samrequistionid").val(),//申请单号(多个申请单号之间用逗号隔开)
			sampatientid:$("#sampatientid").val(),//患者唯一号(病案号)
			saminpatientid:$("#saminpatientid").val(),//就诊id(患者每一次来院的id)
			saminpatientno:$("#saminpatientno").val(),//住院序号(住院次数)
			sampatientnumber:$("#sampatientnumber").val(),//住院卡号/门诊卡号
			sampatienttype:$("#sampatienttype").val(),//患者类型(病人类型： 1门诊,2住院,3体检,4婚检,5科研,6特勤,7其他)
			sampatientname:$("#sampatientname").val(),//姓名
			sampatientsex:$("#sampatientsex").val(),//患者性别(1男,2女,3未知)
			sampatientage:$("#sampatientage").val(),//年龄
			sampatientagetype:$("#sampatientagetype").val(),//年龄类型(1年、2岁、3月、4周、5日、6小时)
			sampatientbed:$("#sampatientbed").val(),//患者床号
			samsampleclass:$("#samsampleclass").val(),//标本种类
			samsamplename:$("#samsamplename").val(),//标本名称(,多个检查项目名称之间用逗号隔开)
			sampopuser:$("#sampopuser").val(),//标本检查项目id(多个检验目的之间用逗号隔开)
			samisemergency:$("#samisemergency").val(),//是否加急(0不加急,1加急)
			samisdecacified:$("#samisdecacified").val(),//是否脱钙(0没有脱钙,1已脱钙)
			samissamplingall:$("#samissamplingall").val(),//是否全取(0,未全取,1全取)
			samsamplestatus:$("#samsamplestatus").val(),//标本状态(0已登记,1已取材,2包埋,3已切片,4已初诊,5已审核,6已发送,7会诊中,8报告已打印)
			samreqtime:$("#samreqtime").val(),//申请时间
			samreqdocid:$("#samreqdocid").val(),//申请医生id
			samreqdocname:$("#samreqdocname").val(),//申请医生姓名
			samsendtime:$("#samsendtime").val(),//送检时间
			samsenddoctorid:$("#samsenddoctorid").val(),//送检医生id
			//samsenddoctorname:$("#samsenddoctorname").val(),//送检医生姓名----
			samsendhospital:$("#samsendhospital").val(),//送检单位名称
			samsendphone:$("#samsendphone").val(),//送检联系电话
			samdigcode:$("#samdigcode").val(),//诊疗小组代码
			samdeptcode:$("#samdeptcode").val(),//科室代码
			samdeptname:$("#samdeptname").val(),//科室名称
			samwardcode:$("#samwardcode").val(),//病区代码
			samwardname:$("#samwardname").val(),//病区名称
			sampatientdignoses:$("#sampatientdignoses").val(),//临床诊断
			sampatientidcardno:$("#sampatientidcardno").val(),//身份证号
			sampatientaddress:$("#sampatientaddress").val(),//联系地址
			sampatientphoneno:$("#sampatientphoneno").val(),//联系电话
			sampatientcompany:$("#sampatientcompany").val(),//工作单位
			samismenopause:$("#samismenopause").val(),//是否绝经
			reqlastmenstruation:$("#reqlastmenstruation").val(),//末次月经时间
			samischarged:$("#samischarged").val(),//收费状态(0未收费,1已收费)
			samreceivertime:$("#samreceivertime").val(),//接收时间
			samreceiverid:$("#samreceiverid").val(),//接收人员id
			samreceivername:$("#samreceivername").val(),//接收人员姓名
			samregisttime:$("#samregisttime").val(),//登记时间
			samregisterid:$("#samregisterid").val(),//登记人员id
			samregistername:$("#samregistername").val(),//登记人员姓名
			saminitiallytime:$("#saminitiallytime").val(),//初诊时间
			saminitiallyuserid:$("#saminitiallyuserid").val(),//初诊人员id
			saminitiallyusername:$("#saminitiallyusername").val(),//初诊人员姓名
			samisdeleted:$("#samisdeleted").val(),//删除标志（0正常，1已删除）
			samthirdv:$("#samthirdv").val(),//手术所见
			samfirstn:$("#samfirstn").val(),//组织袋数
			samcreatetime:$("#samcreatetime").val(),//创建时间
			samcreateuser:$("#samcreateuser").val()//创建人
		},
		function(data) {
			if(data.success) {
				layer.msg(data.message, {icon: 1, time: 1000});
				location.reload();
			} else {
				layer.msg(data.message, {icon:2, time: 1000});
			}
		});
	} else {
		layer.msg(msg, {icon: 2, time: 1000});
	}
}
/**
 * 新增标本单
 */
function addSample() {
	clearData();
	$('#sampleForm').find('input,textarea,select').removeAttr('disabled') ;
	$("#saveButton").removeAttr("disabled");//将按钮可用
	$("#editButton").attr({"disabled":"disabled"});
	$("#deleteButton").attr({"disabled":"disabled"});
	$("#sampleid").val("");//标本id
	$("#saminspectionid").val("");//标本条码号
	$("#sampathologycode").val("");//病理编号
	$("#samcustomerid").val($("#lcal_hosptail").val());//客户id reqcustomercode
	$("#samcustomercode").val($("#lcal_hosptail").val());//客户id reqcustomercode
	$("#sampathologyid").val($("#local_logyid").val());//病种类别id
	$("#samsource").val("0");//是否外送标本(0本院,1外送)
	$("#samotherid").val("");//第三方条码号
	$("#samrequistionid").val("");//申请单号(多个申请单号之间用逗号隔开)
	$("#sampatientid").val("1");//患者唯一号(病案号)
	$("#saminpatientid").val("1");//就诊id(患者每一次来院的id)
	$("#saminpatientno").val(0);//住院序号(住院次数)
	$("#sampatientnumber").val("1");//住院卡号/门诊卡号
	$("#sampatienttype").val("");//患者类型(病人类型： 1门诊,2住院,3体检,4婚检,5科研,6特勤,7其他)
	$("#sampatientname").val("");//姓名
	$("#sampatientsex").val("");//患者性别(1男,2女,3未知)
	$("#sampatientage").val("");//年龄
	$("#sampatientagetype").val("");//年龄类型(1年、2岁、3月、4周、5日、6小时)
	$("#sampatientbed").val("");//患者床号
	$("#samsampleclass").val("1");//标本种类
	$("#samsamplename").val("1");//标本名称(,多个检查项目名称之间用逗号隔开)
	$("#sampopuser").val("1");//标本检查项目id(多个检验目的之间用逗号隔开)
	$("#samisemergency").val("");//是否加急(0不加急,1加急)
	$("#samisdecacified").val("");//是否脱钙(0没有脱钙,1已脱钙)
	$("#samissamplingall").val("");//是否全取(0,未全取,1全取)
	$("#samsamplestatus").val("0");//标本状态(0已登记,1已取材,2包埋,3已切片,4已初诊,5已审核,6已发送,7会诊中,8报告已打印)
	$("#samreqtime").val("");//申请时间
	$("#samreqdocid").val("");//申请医生id
	$("#samreqdocname").val("");//申请医生姓名
	$("#samsendtime").val("");//送检时间
	$("#samsenddoctorid").val("");//送检医生id
	$("#samsenddoctorname").val("");//送检医生姓名----
	$("#samsendhospital").val("");//送检单位名称
	$("#samsendphone").val("");//送检联系电话
	$("#samdigcode").val("");//诊疗小组代码
	$("#samdeptcode").val("");//科室代码
	$("#samdeptname").val("");//科室名称
	$("#samwardcode").val("");//病区代码
	$("#samwardname").val("");//病区名称
	$("#sampatientdignoses").val("");//临床诊断
	$("#sampatientidcardno").val("");//身份证号
	$("#sampatientaddress").val("");//联系地址
	$("#sampatientphoneno").val("");//联系电话
	$("#sampatientcompany").val("");//工作单位
	$("#samismenopause").val("");//是否绝经
	$("#reqlastmenstruation").val("");//末次月经时间
	$("#samischarged").val("");//收费状态(0未收费,1已收费)
	$("#samreceivertime").val("");//接收时间
	$("#samreceiverid").val("");//接收人员id
	$("#samreceivername").val("");//接收人员姓名
	$("#samregisttime").val(CurentTime(new Date()));//登记时间
	$("#samregisterid").val($("#local_userid").val());//登记人员id
	$("#samregistername").val($("#local_username").val());//登记人员姓名
	$("#saminitiallytime").val("");//初诊时间
	$("#saminitiallyuserid").val("");//初诊人员id
	$("#saminitiallyusername").val("");//初诊人员姓名
	$("#samisdeleted").val("0");//删除标志（0正常，1已删除）
	$("#samcreatetime").val(CurentTime(new Date()));//创建时间
	$("#samcreateuser").val($("#local_userid").val());//创建人
}
/**
 *修改标本
 */
function editSample(){
	$.get("../pathologysample/sample/canchange", {
			id:$("#sampleid").val(),
			sts:"1"
		},
		function(data) {
			//var data = JSON.parse(data);
			if(data.success) {
				$('#sampleForm').find('input,textarea,select').removeAttr('disabled');
				$("#saveButton").removeAttr("disabled");//将按钮可用
				$("#addButton").removeAttr("disabled");//将按钮可用
				$("#editButton").removeAttr("disabled");//将按钮可用
				$("#deleteButton").removeAttr("disabled");//将按钮可用
			}else{
				layer.msg(data.message, {icon:2, time: 1000});
			}
		});
}
/**
 * 删除标本
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
		$.get("../pathologysample/sample/canchange", {
				id:$("#sampleid").val(),
				sts:"2"
			},
			function(data) {
				if(data.success) {
					$.post('../pathologysample/sample/deleteSample',{sampleid:rowData.sampleid},function(data) {
						layer.close(index);
						$("#new").trigger('reloadGrid');
						location.reload();
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
    //$("input").val("");
}
/**
 * 初始化
 */
$(function() {
	//表单校验
	$("#sampleForm").Validform({
		btnSubmit:"#saveButton",
		tiptype:4,
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
    $('#sampleForm').find('input,textarea,select').attr('disabled',true) ;
	//送检医院
	$("#samsendhospital").autocomplete({
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
			$( "#samsendhospital" ).val(ui.item.value);
			//return false;
		}
	})
		.data( "ui-autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li>" )
			.append( "<a style='font-size:12px;font-family: 微软雅黑;'>" + item.id + "," + item.value+ "</a>" )
			.appendTo( ul );
	};
	//送检科室
	$("#samdeptcode").autocomplete({
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
			$( "#samdeptcode" ).val(ui.item.id);
			$( "#samdeptname" ).val(ui.item.value);
			//return false;
		}
	})
		.data( "ui-autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li>" )
			.append( "<a style='font-size:12px;font-family: 微软雅黑;'>" + item.id + "," + item.value+ "</a>" )
			.appendTo( ul );
	};
	//送检医生
	$("#samsenddoctorname").autocomplete({
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
			$( "#samsenddoctorid" ).val(ui.item.id);
			$( "#samsenddoctorname" ).val(ui.item.value);
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
	//var logyid = $("#logyid").val();
	var logyid ="";
	var send_hosptail = "";
	var req_code = $('#req_code').val();
	var patient_name = $('#patient_name').val();
	//var send_hosptail = $('#send_hosptail').val();
	var req_bf_time = $('#req_bf_time').val();
	var req_af_time = $('#req_af_time').val();
	var send_dept = $('#send_dept').val();
	var send_doctor = $('#send_doctor').val();
	var req_sts = $('#req_sts').val();
	$("#new").jqGrid({
		//caption:"标本列表",
		url: "../pathologysample/sample/ajax/sample",
		mtype: "GET",
		datatype: "json",
		postData:{"req_code":req_code,"patient_name":patient_name,"send_hosptail":send_hosptail,"req_bf_time":req_bf_time,
			"req_af_time":req_af_time,"send_dept":send_dept,"send_doctor":send_doctor,"req_sts":req_sts,"logyid":logyid},
		colNames: ['ID','条形码', '病理号', '送检医生','送检医院','病人名','病理状态'],
		colModel: [
			{name:'sampleid',hidden:true},
			{ name: 'saminspectionid', index: 'saminspectionid'},
			{ name: 'sampathologycode', index: 'sampathologycode'},
			{ name: 'samsenddoctorname', index: 'samsenddoctorname'},
			{ name: 'samsendhospital', index: 'samsendhospital'},
			{ name: 'sampatientname', index: 'sampatientname'},
			{ name: 'samsamplestatus', index: 'samsamplestatus',formatter: "select", editoptions:{value:"0:已登记;1:已取材;2:已包埋;3:已切片;4:已初诊;5:已审核;6:已发送;7:会诊中:8:报告已打印"}}
		],
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
			$("#new").setSelection(1);
		},
		gridComplete:function(){
			var rowIds = $("#new").jqGrid('getDataIDs');
			for(var k = 0; k<rowIds.length; k++) {
				var rowData = $("#new").jqGrid('getRowData', rowIds[k]);
				//$("#"+rowIds[k]).find("tr").css("background-color", "red");
				if (rowData.samsamplestatus == '1' && $("#"+rowIds[k]).parent().parent().attr("id") == "new") {
					//获取每行下的TD更改CSS
					//第一种写法
                    // alert($("#"+rowIds[k]).parent().parent().attr("id"));
					$("#"+rowIds[k]).css("background-color", "red");
                    // $("#"+rowIds[k]).children("td").eq(2).css("background-color", "red");
					//$("#"+rowIds[k]).find("tr").css("background-color", "red");
				}
			}
            // $(rowIds).each(function () {
            //         var rowData1 = $("#new").jqGrid('getRowData',this.toString());
            //     $("#new tr td:nth-child(2)").css("background-color", "red");
            //         if(rowData1.samsamplestatus == '1'){
            //             alert($("#"+this.toString()).parent().attr("id"));
            //             //this.find("td").css("background-color", "red");
            //
            //         }
            //     }
            // );
		},
		ondblClickRow: function (id) {
			fillInfo1();
		},
		onSelectRow:function(id){
			fillInfo1();
		},
		viewrecords: true,
		height:300,
		autowidth: true,
		rowNum: 10,
		rowList:[10,20,30],
		rownumbers: true, // 显示行号
		rownumWidth: 35, // the width of the row numbers columns
		pager: "#pager"
	});
	$("#new1").jqGrid({
		url: "../pathologysample/sample/ajax/getreqinfo",
		mtype: "GET",
		datatype: "json",
		postData:{"req_code":req_code,"patient_name":patient_name,"send_hosptail":send_hosptail,"req_bf_time":req_bf_time,
			"req_af_time":req_af_time,"send_dept":send_dept,"send_doctor":send_doctor,"req_sts":req_sts,"logyid":logyid},
		colNames: ['ID','临床申请', '病种类别', '申请年月','病人姓名','送检医院','送检科室',"送检医生"],
		colModel: [
			{name:'requisitionid',hidden:true},
			{ name: 'requisitionno', index: 'requisitionno'},
			{ name: 'reqpathologyid', index: 'reqpathologyid',formatter: "select", editoptions:{value:gettypes()}},
			{ name: 'reqdate', index: 'reqdate'},
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
			fillInfo();
		},
		onSelectRow:function(id){
			fillInfo();
		},
		viewrecords: true,
		height:100,
		autowidth: true,
		rowNum: 10,
		//rowList:[10,20,30],
		rownumbers: true, // 显示行号
		rownumWidth: 35, // the width of the row numbers columns
		pager: "#pager1"
	});
});


function gettypes(){
    //动态生成select内容
    var str="";
    $.ajax({
        type:"post",
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
		url: "../pathologysample/sample/ajax/sample",
		//发送数据
		postData : {"req_code":req_code,"patient_name":patient_name,"send_hosptail":send_hosptail,"req_bf_time":req_bf_time,
			"req_af_time":req_af_time,"send_dept":send_dept,"send_doctor":send_doctor,"req_sts":req_sts},
		page : 1
	}).trigger('reloadGrid');//重新载入
}

function fillInfo(){
	clearData();
	var id = $("#new1").jqGrid('getGridParam', 'selrow');
	var rowData = $("#new1").jqGrid('getRowData',id);
	if (id == null || id == 0) {
		layer.msg('请先选择数据', {icon: 2, time: 1000});
		return false;
	}
	getSampleData(rowData.requisitionid);
	// $('#sampleForm').find('input,textarea,select').attr('disabled',true) ;
	$("#editButton").attr({"disabled":"disabled"});
	$("#editButton").attr({"disabled":"disabled"});
	// $("#saveButton").attr({"disabled":"disabled"});
	$('#sampleForm').find('input,textarea,select').removeAttr('disabled') ;
	$("#saveButton").removeAttr("disabled");//将按钮可用
	$("#sampathologycode").attr({"disabled":"disabled"});
	$("#samrequistionid").attr({"disabled":"disabled"});
	$("#sampathologyid").attr({"disabled":"disabled"});
}
function fillInfo1(){
	clearData();
	var id = $("#new").jqGrid('getGridParam', 'selrow');
	var rowData = $("#new").jqGrid('getRowData',id);
	if (id == null || id == 0) {
		layer.msg('请先选择数据', {icon: 2, time: 1000});
		return false;
	}
	getSampleData1(rowData.sampleid);
	$('#sampleForm').find('input,textarea,select').attr('disabled',true) ;
	$("#editButton").removeAttr("disabled");//将按钮可用
	$("#editButton").removeAttr("disabled");//将按钮可用
	$("#saveButton").attr({"disabled":"disabled"});
}
function getSampleData1(id) {
	$.get("../pathologysample/sample/get",{id:id},function(data) {
		if(data != "") {
			$("#sampleid").val(data.sampleid);//标本id
			$("#saminspectionid").val(data.saminspectionid);//标本条码号
			$("#sampathologycode").val(data.sampathologycode);//病理编号
			$("#samcustomerid").val(data.samcustomerid);//客户id reqcustomercode
			$("#samcustomercode").val(data.samcustomercode);//客户id reqcustomercode
			$("#sampathologyid").val(data.sampathologyid);//病种类别id
			$("#samsource").val(data.samsource);//是否外送标本(0本院,1外送)
			$("#samotherid").val(data.samotherid);//第三方条码号
			$("#samrequistionid").val(data.samrequistionid);//申请单号(多个申请单号之间用逗号隔开)
			$("#sampatientid").val(data.sampatientid);//患者唯一号(病案号)
			$("#saminpatientid").val(data.saminpatientid);//就诊id(患者每一次来院的id)
			$("#saminpatientno").val(data.saminpatientno);//住院序号(住院次数)
			$("#sampatientnumber").val(data.sampatientnumber);//住院卡号/门诊卡号
			$("#sampatienttype").val(data.sampatienttype);//患者类型(病人类型： 1门诊,2住院,3体检,4婚检,5科研,6特勤,7其他)
			$("#sampatientname").val(data.sampatientname);//姓名
			$("#sampatientsex").val(data.sampatientsex);//患者性别(1男,2女,3未知)
			$("#sampatientage").val(data.sampatientage);//年龄
			$("#sampatientagetype").val(data.sampatientagetype);//年龄类型(1年、2岁、3月、4周、5日、6小时)
			$("#sampatientbed").val(data.sampatientbed);//患者床号
			$("#samsampleclass").val(data.samsampleclass);//标本种类
			$("#samsamplename").val(data.samsamplename);//标本名称(,多个检查项目名称之间用逗号隔开)
			$("#sampopuser").val(data.sampopuser);//标本检查项目id(多个检验目的之间用逗号隔开)
			$("#samisemergency").val(data.samisemergency);//是否加急(0不加急,1加急)
			$("#samisdecacified").val(data.samisdecacified);//是否脱钙(0没有脱钙,1已脱钙)
			$("#samissamplingall").val(data.samissamplingall);//是否全取(0,未全取,1全取)
			$("#samsamplestatus").val(data.samsamplestatus);//标本状态(0已登记,1已取材,2包埋,3已切片,4已初诊,5已审核,6已发送,7会诊中,8报告已打印)
			$("#samreqtime").val(data.samreqtime);//申请时间
			$("#samreqdocid").val(data.samreqdocid);//申请医生id
			$("#samreqdocname").val(data.samreqdocname);//申请医生姓名
			$("#samsendtime").val(data.samsendtime);//送检时间
			$("#samsenddoctorid").val(data.samsenddoctorid);//送检医生id
			//$("#samsenddoctorname").val(data.samsenddoctorname);//送检医生姓名----
			$("#samsendhospital").val(data.samsendhospital);//送检单位名称
			$("#samsendphone").val(data.samsendphone);//送检联系电话
			$("#samdigcode").val(data.samdigcode);//诊疗小组代码
			$("#samdeptcode").val(data.samdeptcode);//科室代码
			$("#samdeptname").val(data.samdeptname);//科室名称
			$("#samwardcode").val(data.samwardcode);//病区代码
			$("#samwardname").val(data.samwardname);//病区名称
			$("#sampatientdignoses").val(data.sampatientdignoses);//临床诊断
			$("#sampatientidcardno").val(data.sampatientidcardno);//身份证号
			$("#sampatientaddress").val(data.sampatientaddress);//联系地址
			$("#sampatientphoneno").val(data.sampatientphoneno);//联系电话
			$("#sampatientcompany").val(data.sampatientcompany);//工作单位
			$("#samismenopause").val(data.samismenopause);//是否绝经
			$("#reqlastmenstruation").val(data.reqlastmenstruation);//末次月经时间
			$("#samischarged").val(data.reqlastmenstruation);//收费状态(0未收费,1已收费)
			$("#samreceivertime").val(data.samreceivertime);//接收时间
			$("#samreceiverid").val(data.samreceiverid);//接收人员id
			$("#samreceivername").val(data.samreceivername);//接收人员姓名
			$("#samregisttime").val(data.samregisttime);//登记时间
			$("#samregisterid").val(data.samregisterid);//登记人员id
			$("#samregistername").val(data.samregistername);//登记人员姓名
			$("#saminitiallytime").val(data.saminitiallytime);//初诊时间
			$("#saminitiallyuserid").val(data.saminitiallyuserid);//初诊人员id
			$("#saminitiallyusername").val(data.saminitiallyusername);//初诊人员姓名
			$("#samisdeleted").val(data.samisdeleted);//删除标志（0正常，1已删除）
			$("#samthirdv").val(data.samthirdv);//手术所见
			$("#samfirstn").val(data.samfirstn);//组织袋数
			$("#samthirdv").val(data.samthirdv);//手术所见
			$("#samfirstn").val(data.samfirstn);//组织袋数
            $("#samcreatetime").val(data.samcreatetime);//创建时间
            $("#samcreateuser").val(data.samcreateuser);//创建人员
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
function upSample(){
	clearData();
	var id = $("#new").jqGrid('getGridParam', 'selrow');
	var ids = $("#new").jqGrid('getDataIDs');
	var minId = Math.min.apply(Math,ids);
	if(id == minId){
		id = Math.max.apply(Math,ids);
		$("#new").setSelection(id);
	}else{
		id = parseInt(id) - 1;
		$("#new").setSelection(id);
	}
}
function downSample() {
	clearData();
	var id = $("#new").jqGrid('getGridParam', 'selrow');
	var ids = $("#new").jqGrid('getDataIDs');
	var maxId = Math.max.apply(Math,ids);
	if(id == maxId){
		id = Math.min.apply(Math,ids);
		$("#new").setSelection(id);
	}else{
		id = parseInt(id) + 1;
		$("#new").setSelection(id);
	}
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
