/**
 * 当前显示数据所在的行
 * @type {string}
 */
var nowrow = "";
/**
 * 当前页面状态
 * @type {string}
 */
var addstates = "";
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
			//$("#sampathologycode").val(data.requisitionno);//病理编号
			$("#sampathologycode").val("");//病理编号
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

function clickOther() {
	$("#saveButton1").click();
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
	addstates = "0";
	$("#hisbutton").removeAttr("disabled");//将按钮可用
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
	$("#sampatientsex").val("0");//患者性别(1男,2女,3未知)
	$("#sampatientage").val("");//年龄
	$("#sampatientagetype").val("1");//年龄类型(1年、2岁、3月、4周、5日、6小时)
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
	addstates = "1";
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
				$("#hisbutton").removeAttr("disabled");//将按钮可用
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
	jQuery("#new2").jqGrid("clearGridData");
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
		showAllError:true,
		ajaxPost:true,
		beforeSubmit:function(curform){
			//在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。
			//这里明确return false的话表单将不会提交;
			saveInfo();
		},
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
	$("#samdeptname").autocomplete({
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
	var height = $("#formDialog").height() - $('#search_div_1').height() + 230;
    var width = $('#div_2').width();
    var width1 = $("#formDialog").width();
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
	var req_sts = $("input[name='req_sts']:checked").val();//包埋状态
    createNew1(width1);
	$("#new").jqGrid({
		//caption:"标本列表",
		url: "../pathologysample/sample/ajax/sample",
		mtype: "GET",
		datatype: "json",
		postData:{"req_code":req_code,"patient_name":patient_name,"send_hosptail":send_hosptail,"req_bf_time":req_bf_time,
			"req_af_time":req_af_time,"send_dept":send_dept,"send_doctor":send_doctor,"req_sts":req_sts,"logyid":logyid},
		colNames: ['ID','条形码', '病理号', '送检医生','送检医院','病人名','病理状态','性别','年龄','年龄类型','临床诊断','申请时间','送检时间','登记时间'],
		colModel: [
			{name:'sampleid',hidden:true},
			{ name: 'saminspectionid', index: 'saminspectionid',width:'100px'},
			{ name: 'sampathologycode', index: 'sampathologycode',width:'100px'},
			{ name: 'samsenddoctorname', index: 'samsenddoctorname',width:'100px'},
			{ name: 'samsendhospital', index: 'samsendhospital',width:'100px'},
			{ name: 'sampatientname', index: 'sampatientname',width:'100px'},
			{ name: 'samsamplestatus', index: 'samsamplestatus',formatter: "select", editoptions:{value:"0:已登记;1:已取材;2:已包埋;3:已切片;4:已初诊;5:已审核;" +
			"6:已发送;7:会诊中:8:报告已打印"},width:'100px'},
			{name:'sampatientsex',hidden:true},
			{name:'sampatientage',hidden:true},
			{name:'sampatientagetype',hidden:true},
			{name:'sampatientdignoses',hidden:true},
			{name:'samreqtime',hidden:true,formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))}},
			{name:'samsendtime',hidden:true,formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))}},
			{name:'samregisttime',hidden:true,formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))}},
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
			    nowrow = "1";
				fillInfo1(1);
			}
			//$("#new").setSelection(1);
		},
		gridComplete:function(){

		    //$("#pager").children().children().children().children().attr("id").remove();
			var rowIds = $("#new").jqGrid('getDataIDs');
			for(var k = 0; k<rowIds.length; k++) {
				var rowData = $("#new").jqGrid('getRowData', rowIds[k]);
				if (rowData.samsamplestatus == '1') {
					//获取每行下的TD更改CSS
					//第一种写法
                    // alert($("#"+rowIds[k]).parent().parent().attr("id"));
					//$("#"+rowIds[k]).css("background-color", "red");
					$("#new").children().children("tr[id='"+rowIds[k]+"']").children("td").eq(0).css("background-color","red");
                    // $("#"+rowIds[k]).children("td").eq(2).css("background-color", "red");
					//$("#"+rowIds[k]).find("tr").css("background-color", "red");
				}
			}
		},
		ondblClickRow: function (id) {
			fillInfo1(id);
		},
		onCellSelect:function(id){
			fillInfo1(id);
		},
		// onSelectRow:function(id){
		// 	fillInfo1(id);
		// },
		multiselect: true,
		viewrecords: true,
		height:height,
        width:width,
        shrinkToFit:false,
        autoScroll: true,
		//autowidth: true,
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
		colNames: ['选择','详情','ID','临床申请', '病种类别', '申请年月','病人姓名','送检医院','送检科室',"送检医生"],
		colModel: [
			{ name: 'selectinfo', index: 'selectinfo', sortable: false, align: "center", width: "70px" },
			{ name: 'showinfo', index: 'showinfo', sortable: false, align: "center", width: "70px" },
			{name:'requisitionid',hidden:true},
			{ name: 'requisitionno', index: 'requisitionno'},
			{ name: 'reqpathologyid', index: 'reqpathologyid',formatter: "select", editoptions:{value:gettypes()}},
			{ name: 'reqdate', index: 'reqdate',formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))}},
			{ name: 'reqpatientname', index: 'reqpatientname'},
			{ name: 'reqsendhospital', index: 'reqsendhospital'},
			{ name: 'reqdeptname', index: 'reqdeptname'},
			{ name: 'reqdoctorname', index: 'reqdoctorname'}
		],
		gridComplete: function () {
			var ids = jQuery("#new1").jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var id = ids[i];
				var showinfo = "<button onclick='showInfo("+id+")' >详情</button>";
				var selectinfo = "<button onclick='fillInfo("+id+")' >选择</button>";
				jQuery("#new1").jqGrid('setRowData', ids[i], { selectinfo: selectinfo, showinfo: showinfo });
			}
		},
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		// ondblClickRow: function (id) {
		// 	fillInfo(id);
		// },
		// onSelectRow:function(id){
		// 	fillInfo(id);
		// },
		viewrecords: true,
		height:100,
        width:width1,
		autowidth: true,
		rowNum: 10,
		//rowList:[10,20,30],
		rownumbers: true, // 显示行号
		rownumWidth: 35, // the width of the row numbers columns
		pager: "#pager1"
	});
    createNew2();
    $("#pager_left").remove();
});
/**
 * 初始化收费项目列表
 */
function createNew1(width) {
	$("#new2").jqGrid({
		url:"../pathologysample/sample/ajax/fee",
		datatype: "json",
		mtype:"GET",
		height: 100,
        width:width,
		autowidth: true,
		postData:{"feesampleid":$("#sampleid").val(),"feesource":"0"},
		colNames: ['ID','项目名称','单价','数量', '金额','记录人员','记录时间','发送状态'],
		colModel: [
			{name:'feeid',hidden:true},//ID
			{name:'feenamech',index:'feenamech'},//项目名称
			{name:'feeprince',index:'feeprince'},//单价
			{ name: 'feeamount', index: 'feeamount'},//数量
			{ name: 'feecost', index: 'feecost'},//金额
			{name:'feeusername',index:'feeusername'},//记录人员
			{name:'feetime',index:'feetime',formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))}},//记录时间
			{name:'feestate',index:'feestate',formatter: "select", editoptions:{value:"0:已保存;1:已计费;2:已发送;3:发送失败"}}//发送状态
		],
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		viewrecords: true,
		rownumbers : true
	});
}
/**
 * 初始化计费调整
 */
function createNew2() {
    var sampleid = $("#sampleid").val();
    $("#sectionList3").jqGrid({
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
            { name: 'feenamech', index: 'feenamech',editable:true,editoptions: {dataInit: function (elem) {myAutocomplete(elem);}},
                // edittype: "select",formatter: "select",editoptions:{value:getfeelist(), dataEvents: [
                // {type: 'change',fn: function(e) {
                // 	jQuery("#new1").jqGrid('setRowData', $(this).parent().parent().attr('id'), {reqmmaterialname:$(this).find("option:selected").text()});
                // 	jQuery("#new1").jqGrid('setRowData', $(this).parent().parent().attr('id'), {feeitemid:$(this).val()});
                // }}]},
                width: 80},//收费项目
            { name: 'feeprince', index: 'feeprince', width: 60},//单价
            { name: 'feeamount', index: 'feeamount',editable:true, width: 60,editoptions: {
                dataEvents: [
                    {type: 'change',fn: function(e) {
                        var rowdata = jQuery("#sectionList3").jqGrid('getRowData', $(this).parent().parent().attr('id'));
                        jQuery("#sectionList3").jqGrid('setRowData', $(this).parent().parent().attr('id'), {feecost:$(this).val()*rowdata.feeprince});
                    }}]
            }},//数量
            { name: 'feecost', index: 'feecost', width: 60},//金额
            { name: 'feestate', index: 'feestate',formatter: "select", editoptions:{value:"0:已保存;1:已计费;2:已发送;3:发送失败"}, width: 60},//状态
            { name: 'feeusername', index: 'feeusername', width: 60},//记录人
            { name: 'feetime', index: 'feetime', width: 60,formatter:function(cellvalue, options, row){if(cellvalue == null || cellvalue == "")
                return "";
                return CurentTime(new Date(cellvalue));}},//记录时间
            { name: 'feesendusername', index: 'feesendusername', width: 60},//发送人
            { name: 'feesendtime', index: 'feesendtime', width: 60,formatter:function(cellvalue, options, row){if(cellvalue == null || cellvalue == "")
                return "";
                return CurentTime(new Date(cellvalue));}},//发送时间
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
            var rec = jQuery("#sectionList3").jqGrid('getRowData', rowid);
            if (rec.feestate == "1" || rec.feestate == "2") {
                setTimeout(function () {
                    jQuery("#sectionList3").jqGrid('restoreCell', iRow, iCol);
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
        altRows:true,
        height: 'auto',
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
 * 计费调整
 */
function hisChange() {
	//alert(addstates);
	var sampleid = $("#sampleid").val();
	if(sampleid == null || sampleid == ""){
        layer.msg("该病理标本未登记，无法进行计费调整!", {icon:2, time: 1000});
		return;
	}else{
        jQuery("#sectionList3").jqGrid('setGridParam',{
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
			content: $('#userGrid')
		})
	}
	
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
			jQuery("#sectionList3").jqGrid('setRowData', $(elem).parent().parent().attr("id"), {feeitemid:ui.item.id});//收费项目ID
			jQuery("#sectionList3").jqGrid('setRowData', $(elem).parent().parent().attr("id"), {feenamech:ui.item.name});//中文名称
			jQuery("#sectionList3").jqGrid('setRowData', $(elem).parent().parent().attr("id"), {feenameen:ui.item.feenameen});//英文名称
			jQuery("#sectionList3").jqGrid('setRowData', $(elem).parent().parent().attr("id"), {feeprince:ui.item.feeprince});//单价
			jQuery("#sectionList3").jqGrid('setRowData', $(elem).parent().parent().attr("id"), {feecategory:ui.item.feecategory});//所属统计类
			jQuery("#sectionList3").jqGrid('setRowData', $(elem).parent().parent().attr("id"), {feehisitemid:ui.item.feehisitemid});//his项目id
			jQuery("#sectionList3").jqGrid('setRowData', $(elem).parent().parent().attr("id"), {feehisname:ui.item.feehisname});//his项目名称
			jQuery("#sectionList3").jqGrid('setRowData', $(elem).parent().parent().attr("id"), {feehisprice:ui.item.feehisprice});//his单价
            jQuery("#sectionList3").jqGrid('setRowData', $(elem).parent().parent().attr("id"), {feeamount:""});//数量
            jQuery("#sectionList3").jqGrid('setRowData', $(elem).parent().parent().attr("id"), {feecost:""});//金额
		}
	})
		.data( "ui-autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li>" )
			.append( "<a style='font-size:12px;font-family: 微软雅黑;'>" + item.id + "," + item.value+ "</a>" )
			.appendTo( ul );
	};
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
/**
 * 查询功能
 */
function searchList() {
	var req_code = $('#req_code').val();
	var patient_name = $('#patient_name').val();
	var send_hosptail = $('#send_hosptail').val();
	var req_bf_time = $('#req_bf_time').val();
	var req_af_time = $('#req_af_time').val();
	var send_dept = $('#send_dept').val();
	var send_doctor = $('#send_doctor').val();
	//var req_sts = $('#req_sts').val();
	var req_sts = $("input[name='req_sts']:checked").val();//包埋状态
	jQuery("#new").jqGrid("clearGridData");
	jQuery("#new").jqGrid('setGridParam',{
		url: "../pathologysample/sample/ajax/sample",
		//发送数据
		postData : {"req_code":req_code,"patient_name":patient_name,"send_hosptail":send_hosptail,"req_bf_time":req_bf_time,
			"req_af_time":req_af_time,"send_dept":send_dept,"send_doctor":send_doctor,"req_sts":req_sts},
		page : 1
	}).trigger('reloadGrid');//重新载入
}
/**
 * 选择电子申请单
 * @param id
 * @returns {boolean}
 */
function fillInfo(id){
	clearData();
	//var id = $("#new1").jqGrid('getGridParam', 'selrow');
	var rowData = $("#new1").jqGrid('getRowData',id);
	if (id == null || id == 0) {
		layer.msg('请先选择数据', {icon: 2, time: 1000});
		return false;
	}
	getSampleData(rowData.requisitionid);
	// $('#sampleForm').find('input,textarea,select').attr('disabled',true) ;
	$("#editButton").attr({"disabled":"disabled"});
	$("#deleteButton").attr({"disabled":"disabled"});
	$("#hisbutton").removeAttr("disabled");
	addstates="0";
	$('#sampleForm').find('input,textarea,select').removeAttr('disabled') ;
	$("#saveButton").removeAttr("disabled");//将按钮可用
	$("#sampathologycode").attr({"disabled":"disabled"});
	$("#samrequistionid").attr({"disabled":"disabled"});
	$("#sampathologyid").attr({"disabled":"disabled"});
}
/**
 * 选择标本信息
 * @param id
 * @returns {boolean}
 */
function fillInfo1(id){
	nowrow = id;
	clearData();
	//$("#new").children().children().remove_css("background-color","yellow");
	//$("#new").children().children("tr[id='"+id+"']").css("background-color","yellow");
	//var id = $("#new").jqGrid('getGridParam', 'selrow');
	var rowData = $("#new").jqGrid('getRowData',id);
	if (id == null || id == 0) {
		layer.msg('请先选择数据', {icon: 2, time: 1000});
		return false;
	}
	getSampleData1(rowData.sampleid);
    jQuery("#new2").jqGrid('setGridParam',{
        url: "../pathologysample/sample/ajax/fee",
        //发送数据
        postData:{"feesampleid":rowData.sampleid,"feesource":"0"}
    }).trigger('reloadGrid');//重新载入
	$('#sampleForm').find('input,textarea,select').attr('disabled',true) ;
	$("#editButton").removeAttr("disabled");//将按钮可用
	$("#editButton").removeAttr("disabled");//将按钮可用
	$("#saveButton").attr({"disabled":"disabled"});
	$("#hisbutton").attr({"disabled":"disabled"});
}
/**
 * 填充标本详细信息
 * @param id
 */
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
			$("#samreqtime").val(CurentTime(new Date(data.samreqtime)));//申请时间
			$("#samreqdocid").val(data.samreqdocid);//申请医生id
			$("#samreqdocname").val(data.samreqdocname);//申请医生姓名
			$("#samsendtime").val(CurentTime(new Date(data.samsendtime)));//送检时间
			$("#samsenddoctorid").val(data.samsenddoctorid);//送检医生id
			$("#samsenddoctorname").val(data.samsenddoctorname);//送检医生姓名----
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
			$("#samreceivertime").val(CurentTime(new Date(data.samreceivertime)));//接收时间
			$("#samreceiverid").val(data.samreceiverid);//接收人员id
			$("#samreceivername").val(data.samreceivername);//接收人员姓名
			$("#samregisttime").val(CurentTime(new Date(data.samregisttime)));//登记时间
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
            $("#samcreatetime").val(CurentTime(new Date(data.samcreatetime)));//创建时间
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
/**
 * 上一个
 */
function upSample(){
	clearData();
	//var id = $("#new").jqGrid('getGridParam', 'selrow');
	var id = nowrow;
	var ids = $("#new").jqGrid('getDataIDs');
	var minId = Math.min.apply(Math,ids);
	if(id == minId){
		id = Math.max.apply(Math,ids);
		//$("#new").setSelection(id);
	}else{
		id = parseInt(id) - 1;
		//$("#new").setSelection(id);
	}
	fillInfo1(id);
}
/**
 * 下一个
 */
function downSample() {
	clearData();
	//var id = $("#new").jqGrid('getGridParam', 'selrow');
	var id = nowrow;
	var ids = $("#new").jqGrid('getDataIDs');
	var maxId = Math.max.apply(Math,ids);
	if(id == maxId){
		id = Math.min.apply(Math,ids);
		//$("#new").setSelection(id);
	}else{
		id = parseInt(id) + 1;
		//$("#new").setSelection(id);
	}
	fillInfo1(id);
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
/**
 * 查看申请单详细信息
 * @param id
 */
function showInfo(id){
	$('#sampleForm1').find('input,textarea,select').attr('disabled','disabled');
	$('#sampleForm1')[0].reset();
	jQuery("#new3").jqGrid("clearGridData");
	var rowData = $("#new1").jqGrid('getRowData',id);
	$.get("../pimspathology/get",{id:rowData.requisitionid},function(data) {
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
	$("#new3").jqGrid({
		url:"../pimspathology/ajax/getmaterial",
		datatype: "json",
		mtype:"GET",
		height: 170,
		width: 400,
		postData:{"reqId":rowData.requisitionid},
		colNames: ['ID','申请单ID','材料ID','切取部位', '送检材料','客户id','材料名称','取材特殊要求','备注信息','录入人员','录入时间'],
		colModel: [
			{name:'reqmid',hidden:true},//ID
			{name:'requisitionid',hidden:true},//申请单ID
			{name:'materialid',hidden:true},//ID
			{ name: 'reqmsamplingparts', index: 'reqmsamplingparts'},//切取部位
			{ name: 'reqmmaterialtype', index: 'reqmmaterialtype',edittype: "select",formatter: "select",editoptions:{value:gettypes1()}},//送检材料
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
		rownumbers : true
	});
	layer.open({
		type: 1,
		area: ['1000px','600px'],
		skin: 'layui-layer-molv',
		fix: false, //不固定
		maxmin: false,
		shade:0.6,
		title: "样本信息编辑",
		content: $("#formDialog1")
	});

}

/**
 * 增加行
 */
function addRow(){
	var selectedId = $("#sectionList3").jqGrid("getGridParam", "selrow");
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
		feecustomerid:$("#samcustomerid").val(),//客户id
		feesampleid:$("#sampleid").val(),//标本号
		feepathologyid:$("#sampathologyid").val(),//病种id
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
	var ids = $("#sectionList3").jqGrid('getDataIDs');
	var rowid = 1;
	if(Math.max.apply(Math,ids) > ids.length ){
		rowid = Math.max.apply(Math,ids) + 1;
	}else{
		rowid = ids.length + 1;
	}
	if (selectedId) {
		$("#sectionList3").jqGrid("addRowData", rowid, dataRow, "after", selectedId);
	} else {
		$("#sectionList3").jqGrid("addRowData", rowid, dataRow, "last");
	}
	//$('#plsfList').jqGrid('editRow', rowid, false);
}
/**
 * 删除行
 */
function delRow(){
	// var selectedId = $("#sectionList3").jqGrid("getGridParam","selarrrow");
	// if(selectedIds == null || selectedIds == ""){
	// 	alert("请选择要删除的行!");
	// 	return;
	// }else{
	// 	$("#sectionList3").jqGrid("delRowData", selectedId);
	// }
    var selectedIds = $("#sectionList3").jqGrid("getGridParam","selarrrow");
    if(selectedIds == null || selectedIds == ""){
        layer.msg("请选择要删除的行!", {icon:2, time: 1000});
        return;
    }else{
        $(selectedIds).each(function () {
                var delrow = this.toString();
                var rowData = $("#sectionList3").jqGrid('getRowData',delrow);
                if(rowData.feestate == "0" || rowData.feestate == "3"){
                    $("#sectionList3").jqGrid("delRowData", delrow);
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
    var rowdatas = $('#sectionList3').jqGrid('getRowData');
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
/**
 * 送检材料
 * @returns {string}
 */
function gettypes1(){
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
		saveDatas.push(rowData);
		startPrint(rowData);
	});
	// $.ajax({
	// 	type: "POST",
	// 	async: false,
	// 	url: "../nursestation/inexecute/printRequestList",
	// 	dataType: "json",
	// 	contentType: "application/json",
	// 	data: JSON.stringify(saveDatas),
	// 	success: function (data) {
	// 		var printDatas = data.printOrders
	// 		var noPrintDatas = data.noPrintOrders;
	// 		for (i = 0; i < printDatas.length; i++) {
	// 			startPrint(printDatas[i]);
	// 		}
	// 		for (i = 0; i < noPrintDatas.length; i++) {
    //
	// 		}
	// 	}
    //
	// });
	//刷新当前节点数据
	// var zTree = $.fn.zTree.getZTreeObj("tree");
	// var nodes = zTree.getSelectedNodes();
	// if (nodes.length > 0) {
	// 	zTree.selectNode(nodes[0]);
	// 	zTree.setting.callback.onClick(null, zTree.setting.treeId, nodes[0]);//调用事件
	// }
}

var LODOP; //声明为全局变量

function Preview() {//打印预览
	LODOP = getLodop();
	CreateDataBill(data)
	LODOP.PREVIEW();
};
function Setup() {//打印维护
	LODOP = getLodop();
	LODOP.PRINT_SETUP();
};

function CreateDataBill(data) {
	if(data && data!=null){
		var patientInfo = data.sampatientname + " "+ data.sampatientsex + " "+ data.sampatientage+data.sampatientagetype
		if(data.samisemergency != 0) {
			patientInfo += " 急";
		}
		var exam = data.testName + "  "+ data.sampleType;
		var patientInfo1 = data.patientCode +"  "+data.hosSectionName;
		var labInfo = data.labDepartment + " " + data.sampleType + " " + data.container+data.volume;
		var sex = "";
		if(data.sampatientsex == '0'){sex = '男'}else if(data.sampatientsex == '1'){sex = '女'}else{sex = '未知'}
		var ageUnit = "";
		if(data.sampatientagetype == '1'){
			ageUnit = "岁";
		}else if(data.sampatientagetype == '2'){
			ageUnit = "月";
		}else if(data.sampatientagetype == '4'){
			ageUnit = "周";
		}else if(data.sampatientagetype == '5'){
			ageUnit = "日";
		}else if(data.sampatientagetype == '6'){
			ageUnit = "小时";
		}
		LODOP = getLodop();
		LODOP.PRINT_INIT("");
		LODOP.SET_PRINT_PAGESIZE(0,980,1100,"A4");
		LODOP.ADD_PRINT_IMAGE(10,10,80,80,"<img src='../images/shulan.png' style='width:80px;'/>");
		LODOP.ADD_PRINT_TEXT(10,100,230,35,"树兰（杭州）医院");
		LODOP.SET_PRINT_STYLEA(0,"FontSize",20);
		LODOP.ADD_PRINT_TEXT(45,100,230,35,"浙江大学国际医院");
		LODOP.SET_PRINT_STYLEA(0,"FontSize",20);
		LODOP.ADD_PRINT_BARCODEA("patientCode","21.98mm","27.01mm","46.57mm",40,"128B",data.sampathologycode);
		LODOP.SET_PRINT_STYLEA(0,"Horient",2);
		LODOP.ADD_PRINT_TEXTA("nameText","33.00mm","12.46mm",45,20,"姓名：");
		LODOP.ADD_PRINT_TEXTA("name","33.00mm","23.31mm",90,20,data.sampatientname);
		LODOP.SET_PRINT_STYLEA(0,"Bold",1);
		LODOP.ADD_PRINT_TEXTA("sexText","33.00mm","46.86mm",45,20,"性别：");
		LODOP.ADD_PRINT_TEXTA("sex","33.00mm","58.5mm",30,20,sex);
		LODOP.SET_PRINT_STYLEA(0,"Bold",1);
		LODOP.ADD_PRINT_TEXTA("ageText","33.00mm","65.91mm",45,20,"年龄：");
		LODOP.ADD_PRINT_TEXTA("age","33.00mm","77.55mm",40,20,data.sampatientage + ageUnit);
		LODOP.SET_PRINT_STYLEA(0,"Bold",1);
		LODOP.ADD_PRINT_TEXTA("examText","38.00mm","5.85mm",70,20,"临床诊断：");
		LODOP.ADD_PRINT_TEXTA("exam","38.00mm","23.31mm",300,20,data.sampatientdignoses);
		LODOP.SET_PRINT_STYLEA(0,"Bold",1);
		LODOP.ADD_PRINT_TEXTA("requestTimeText","43.00mm","5.85mm",70,20,"申请时间：");
		LODOP.ADD_PRINT_TEXTA("requestTime","43.00mm","23.31mm",300,20,data.samreqtime);
		LODOP.ADD_PRINT_TEXTA("requesterText","48.00mm","5.85mm",70,20,"送检时间：");
		LODOP.ADD_PRINT_TEXTA("requester","48.00mm","23.31mm",300,20,data.samsendtime);
		LODOP.ADD_PRINT_TEXTA("executeTimeText","53.00mm","5.85mm",70,20,"登记时间：");
		LODOP.ADD_PRINT_TEXTA("executeTime","53.00mm","23.31mm",300,20,data.samregisttime);
		LODOP.ADD_PRINT_TEXTA("reportTimeText","58.00mm","5.58mm",70,20,"报告时间：");
		LODOP.ADD_PRINT_TEXTA("reportTime","58.00mm","23.31mm",300,20,data.reportTime);
		LODOP.ADD_PRINT_TEXTA("reportPlaceText","63.00mm","5.58mm",70,20,"取单地点：");
		LODOP.ADD_PRINT_TEXTA("reportPlace","63.00mm","23.31mm",300,20,data.reportPlace);
		LODOP.ADD_PRINT_TEXTA("tip1","68.00mm","5.58mm",360,20,"*法定节假日(如:春节等)仪器故障报告时间顺延*");
		LODOP.ADD_PRINT_TEXTA("tip2","73.00mm","5.58mm",360,20,"*抽血时请携带就诊卡，凭此单或就诊卡取检验报告*");

		LODOP.ADD_PRINT_TEXTA("patientinfo","78mm","2.85mm",180,20,patientInfo);
		LODOP.ADD_PRINT_TEXTA("testinfo","82mm","2.95mm",180,20,data.testName);
		LODOP.ADD_PRINT_BARCODEA("barcode","86mm","2.85mm","46mm",30,"128B",data.barcode);
		LODOP.SET_PRINT_STYLEA(0,"ShowBarText",0);
		LODOP.ADD_PRINT_TEXTA("code","94mm","8.2mm",150,20,"*"+data.sampleNo+"*");
		LODOP.SET_PRINT_STYLEA(0,"Bold",1);
		LODOP.ADD_PRINT_TEXTA("patientinfo1","98mm","2.95mm",180,20,patientInfo1);
		LODOP.ADD_PRINT_TEXTA("labInfo","102mm","2.95mm",180,20, labInfo);
		LODOP.ADD_PRINT_TEXTA("datetime","106mm","2.95mm",180,20,"采集时间 "+data.executeTime);

		LODOP.ADD_PRINT_TEXTA("patientinfo","78mm","51.25mm",180,20,patientInfo);
		LODOP.ADD_PRINT_TEXTA("testinfo","82mm","51.25mm",180,20,data.testName);
		LODOP.ADD_PRINT_BARCODEA("barcode","86mm","51.25mm","46mm",30,"128B",data.barcode);
		LODOP.SET_PRINT_STYLEA(0,"ShowBarText",0);
		LODOP.ADD_PRINT_TEXTA("code","94mm","55.95mm",150,20,"*"+data.sampleNo+"*");
		LODOP.SET_PRINT_STYLEA(0,"Bold",1);
		LODOP.ADD_PRINT_TEXTA("patientinfo1","98mm","51.25mm",180,20,patientInfo1);
		LODOP.ADD_PRINT_TEXTA("labInfo","102mm","51.25mm",180,20, labInfo);
		LODOP.ADD_PRINT_TEXTA("datetime","106mm","51.25mm",180,20,"采集时间 "+data.executeTime);

	}
}
function startPrint(data) {
	CreateDataBill(data);
	//开始打印
	// LODOP.PRINT();
	LODOP.PREVIEW();
}
