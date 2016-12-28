var nowrow = "";//当前显示数据所在的行
var addstates = "";//当前页面状态

/**
 * 回车事件
 * @param obj
 * @param event
 */
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
			$.get("../pimspathology/getpatientlist", {"brjzxh": obj.value}, function (data) {
				var records = data.records;
				if(records == undefined){

				}else if (records == 1) {
					var rows = data.rows;
					fillpatinetinfo(rows[0]);
					// $("#sampatientname").val(rows[0].patient_name);//姓名
					// $("#sampatientphoneno").val(rows[0].phone_no);//电话
					// $("#sampatientbed").val(rows[0].patient_bed);//床号
					// $("#sampatientaddress").val(rows[0].patient_address);//联系地址
					// $("#sampatientsex").val(rows[0].patient_sex);//性别
					// $("#sampatientage").val(rows[0].patient_age);//年龄
					// // $("#reqpatagetype").val(rows[0].patient_age_type);//年龄类型
					// $("#sampatientagetype option").each(function () {
					// 	if($(this).text() == rows[0].patient_age_type){
					// 		$(this).attr("selected", "selected");
					// 	}
					// });
					// $("#samwardcode").val(rows[0].patient_ward);//送检病区id
					// $("#samwardname").val(rows[0].patient_ward_name);//送检病区名称
					// $("#samdeptcode").val(rows[0].patient_dept);//送检科室ID
					// $("#samdeptname").val(rows[0].patient_dept_name);//送检科室名称
					// $("#samsendhospital").val(1);//送检医院
					// $("#sampatientdignoses").val(rows[0].lczd);//临床诊断
					// $("#sampatienttype").val(rows[0].patient_type);//患者类型
					// $("#saminpatientid").val(rows[0].inpatient_id);//就诊id

				}else{
					jQuery("#new22").jqGrid("clearGridData");
					jQuery("#new22").jqGrid('setGridParam',{
						url: "../pimspathology/getpatientlist",
						//发送数据
						postData : {"brjzxh":obj.value}
					}).trigger('reloadGrid');//重新载入
					layer.open({
						type: 1,
						area: ['1000px','600px'],
						skin: 'layui-layer-molv',
						fix: false, //不固定
						maxmin: false,
						shade:0.6,
						title: "申请信息录入",
						content: $("#formDialog11")
					});
				}
			});
			break;
	}
}
/**
 * 创建病人信息列表
 * @param reqid
 */
function createNew22(brjzxh){
	$("#new22").jqGrid({
		url:"../pimspathology/getpatientlist",
		datatype: "json",
		mtype:"GET",
		height: 500,
		width: 1000,
		postData:{"brjzxh":brjzxh},
		colNames: ['ID','住院号','病人姓名','性别','年龄','年龄类型', '住院科室','住院病区','床号','临床诊断','电话','患者类型','联系地址','会诊ID','住院病区ID','住院科室ID'],
		colModel: [
			{name:'key_no',index:'key_no'},//ID
			{name:'patient_id',index:'patient_id'},//住院号
			{name:'patient_name',index:'patient_name'},//病人姓名
			{ name: 'patient_sex', index: 'patient_sex',formatter:'select',editoptions:{value:"1:男;2:女;3:未知"}},//性别
			{ name: 'patient_age', index: 'patient_age'},//年龄
			{ name: 'patient_age_type', index: 'patient_age_type'},//年龄
			{name:'patient_dept_name',index:'patient_dept_name'},//住院科室名称
			{name:'patient_ward_name',index:'patient_ward_name'},//住院病区名称
			{name:'patient_bed',index:'patient_bed'},//床号
			{name:'lczd',index:'lczd'},//临床诊断
			{name:'phone_no',hidden:true},//电话
			{name:'patient_type',hidden:true},//患者类型
			{name:'patient_address',hidden:true},//会诊ID
			{name:'inpatient_id',hidden:true},//联系地址
			{name:'patient_ward',hidden:true},//住院病区ID
			{name:'patient_dept',hidden:true}//住院科室ID
		],
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		viewrecords: true,
		rownumbers : true,
		ondblClickRow: function (id) {
			var rowData = $("#new22").jqGrid('getRowData',id);
			fillpatinetinfo(rowData);
			var index = layer.index; //获取窗口索引
			layer.close(index);
			//layer.close();
		},
	});
}


function fillpatinetinfo(data) {
	$("#sampatientname").val(data.patient_name);//姓名
	$("#sampatientphoneno").val(data.phone_no);//电话
	$("#sampatientbed").val(data.patient_bed);//床号
	$("#sampatientaddress").val(data.patient_address);//联系地址
	$("#sampatientsex").val(data.patient_sex);//性别
	$("#sampatientage").val(data.patient_age);//年龄
	// $("#reqpatagetype").val(rows[0].patient_age_type);//年龄类型
	$("#sampatientagetype option").each(function () {
		if($(this).text() == data.patient_age_type){
			$(this).attr("selected", "selected");
		}
	});
	$("#samwardcode").val(data.patient_ward);//送检病区id
	$("#samwardname").val(data.patient_ward_name);//送检病区名称
	$("#samdeptcode").val(data.patient_dept);//送检科室ID
	$("#samdeptname").val(data.patient_dept_name);//送检科室名称
	// $("#samsendhospital").val(1);//送检医院
	$("#sampatientdignoses").val(data.lczd);//临床诊断
	$("#sampatienttype").val(data.patient_type);//患者类型
	$("#saminpatientid").val(data.inpatient_id);//就诊id

}

function NoSubmit(ev){
    if( ev.keyCode == 13 ){
        return false;
    }
    return true;
}
function getreqData(obj,event) {//查询申请单

	var e=e||event;
	var key = event.keyCode;
	if(navigator.appName=="Netscape"){
		key=e.which;
	}else{
		key=event.keyCode;
	}
	switch(key){
		case 13 :
			//根据申请单号查询申请数据
			$.get("../pimspathology/codeisexist",{code:obj.value},function (data) {
				if(data.success) {
					clearData();
					getSampleData(data.message);
					changeimgclick(1);
					addstates="0";
					$('#sampleForm').find('input,textarea,select').removeAttr('disabled') ;
					$("#sampathologyid").attr({"disabled":"disabled"});
				} else {
					layer.msg(data.message, {icon:2, time: 1000});
				}
				}
			);
			break;
	}
}
function getData(obj,event) {//获取数据
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
			if(obj.id == 'samrequistionid') {
				//根据申请单号查询申请数据
			}
			break;
	}
}
function getSampleData(id) {//根据申请单据补充登记单信息
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
			$("#samreceivertime").val(CurentTime(new Date()));//接收时间data.reqcreatetime
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
			$("#samjcxm").val(data.reqitemnames);//检查项目
			if(data.reqfirstv == "1"){
				$("input[name='samfirstv'][value='1']").prop("checked",true);
			}else{
				$("input[name='samfirstv'][value='2']").prop("checked",true);
			}
			$("input[name='samsecondv'][value='1']").prop("checked",true);
		} else {
			layer.msg("该申请单不存在！", {icon: 0, time: 1000});
		}
	});
}
function clickOther() {//实现表单验证
	$("#saveButton1").click();
}
function changeimgclick(num) {//1新增 2 修改
	if(num == 1){
		if (typeof document.addEventListener == "undefined") {
			// document.getElementById("editButton").detachEvent("onclick",editSample);
			document.getElementById("deleteButton").detachEvent("onclick",deleteSample);
			document.getElementById("hisbutton").detachEvent("onclick",hisChange);
		} else {
			// document.getElementById("editButton").removeEventListener("click",editSample,false);
			document.getElementById("deleteButton").removeEventListener("click",deleteSample,false);
			document.getElementById("hisbutton").removeEventListener("click",hisChange,false);
		}
		// $("#editButton").css("cursor","default");
		$("#deleteButton").css("cursor","default");
		$("#hisbutton").css("cursor","default");
	}else if(num == 2){
		if (typeof document.addEventListener == "undefined") {
			// document.getElementById("editButton").detachEvent("onclick",editSample);
			// document.getElementById("editButton").attachEvent("onclick",editSample);
			document.getElementById("deleteButton").detachEvent("onclick",deleteSample);
			document.getElementById("deleteButton").attachEvent("onclick",deleteSample);
			document.getElementById("hisbutton").detachEvent("onclick",hisChange);
			document.getElementById("hisbutton").attachEvent("onclick",hisChange)
		} else {
			// document.getElementById("editButton").removeEventListener("click",editSample,false);
			// document.getElementById("editButton").addEventListener("click",editSample,false);
			document.getElementById("deleteButton").removeEventListener("click",deleteSample,false);
			document.getElementById("deleteButton").addEventListener("click",deleteSample,false);
			document.getElementById("hisbutton").removeEventListener("click",hisChange,false);
			document.getElementById("hisbutton").addEventListener("click",hisChange,false);
		}
		// $("#editButton").css("cursor","pointer");
		$("#deleteButton").css("cursor","pointer");
		$("#hisbutton").css("cursor","pointer");
	}
}
/**
 * 保存数据
 * $("input[name='samfirstv']:checked").val(),
 * $("#reqdeptcode").find("option:selected").text(),
 */
function saveInfo() {
	var msg = "";
	var post = true;
	if($("input[name='samsecondv']:checked").val() == "2" && $("#samremark").val() == ""){
		post = false;
		layer.msg("标本不合格,请描述原因!", {icon: 2, time: 2000});
		return;
	}
	if(addstates == "1"){
		$.get("../pathologysample/sample/canchange", {
				id:$("#sampleid").val(),
				sts:"1"
			},
			function(data) {
				//var data = JSON.parse(data);
				if(data.success) {
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
								samcreateuser:$("#samcreateuser").val(),//创建人
								samjcxm:$("#samjcxm").val(),//检查项目
								sampiecedoctorid:$("#sampiecedoctorid").val(),//首次取材医师既诊断医师ID
								sampiecedoctorname:$("#sampiecedoctorname").val(),//首次取材医师既诊断医师
								samremark:$("#samremark").val()//不合格原因
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
				}else{
					layer.msg(data.message, {icon:2, time: 1000});
					post = false;
					return;
				}
			});
	}else{
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
					samcreateuser:$("#samcreateuser").val(),//创建人
					samjcxm:$("#samjcxm").val(),//检查项目
					sampiecedoctorid:$("#sampiecedoctorid").val(),//首次取材医师既诊断医师ID
					sampiecedoctorname:$("#sampiecedoctorname").val(),//首次取材医师既诊断医师
					samremark:$("#samremark").val()//不合格原因
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


}
/**
 * 新增标本单
 */
function addSample() {
	addstates = "0";
	clearData();
	changeimgclick(1);
	$('#sampleForm').find('input,textarea,select').removeAttr('disabled') ;
	$("#sampathologyid").attr({"disabled":"disabled"});
	//$("#hisbutton").removeAttr("disabled");//将按钮可用
	// $("#saveButton").removeAttr("disabled");//将按钮可用
	// $("#editButton").attr({"disabled":"disabled"});
	// $("#deleteButton").attr({"disabled":"disabled"});
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
	$("#sampatientnumber").val();//住院卡号/门诊卡号
	$("#sampatienttype").val("");//患者类型(病人类型： 1门诊,2住院,3体检,4婚检,5科研,6特勤,7其他)
	$("#sampatientname").val("");//姓名
	$("#sampatientsex").val("1");//患者性别(1男,2女,3未知)
	$("#sampatientage").val("");//年龄
	$("#sampatientagetype").val("1");//年龄类型(1年、2岁、3月、4周、5日、6小时)
	$("#sampatientbed").val("");//患者床号
	$("#samsampleclass").val("1");//标本种类
	$("#samsamplename").val();//标本名称(,多个检查项目名称之间用逗号隔开)
	$("#sampopuser").val();//标本检查项目id(多个检验目的之间用逗号隔开)
	$("#samisemergency").val("");//是否加急(0不加急,1加急)
	$("#samisdecacified").val("");//是否脱钙(0没有脱钙,1已脱钙)
	$("#samissamplingall").val("");//是否全取(0,未全取,1全取)
	$("#samsamplestatus").val("0");//标本状态(0已登记,1已取材,2包埋,3已切片,4已初诊,5已审核,6已发送,7会诊中,8报告已打印)
	$("#samreqtime").val("");//申请时间
	$("#samreqdocid").val("");//申请医生id
	$("#samreqdocname").val("");//申请医生姓名
	$("#samsendtime").val(CurentTime(new Date()));//送检时间
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
	$("#samreceivertime").val(CurentTime(new Date()));//接收时间
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
	$("#samjcxm").val("");//检查项目
	$("input[name='samfirstv'][value='1']").attr("checked",true);//知情书
	$("input[name='samsecondv'][value='1']").attr("checked",true);合格状态
    $("#sampiecedoctorid").val("");//首次取材医师既诊断医师ID
    $("#sampiecedoctorname").val("");//首次取材医师既诊断医师
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
				$("#sampathologyid").attr({"disabled":"disabled"});
				$("#samjcxm").attr({"disabled":"disabled"});
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
				id:rowData.sampleid,
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

function fillval(id,name,anotherid,obj) {
	var optiontext = $("#"+obj).find("option:selected").text();
	var string1 = optiontext.substring(0,optiontext.indexOf(':'));
	var string2 = optiontext.substr(optiontext.indexOf(':')+1);
	$("#"+id).val($("#"+obj).val());
	$("#"+name).val(string2);
	if(anotherid != null){
		$("#"+anotherid).val(string1);
	}
	$("#"+name).focus();
}
/**
 * 初始化
 */
$(function() {
	//表单校验
	$("#sampleForm").Validform({
		// btnSubmit:"#saveButton",
		tiptype:4,
		// tiptype:function(msg,o){
		// 	if(msg != ""){
		// 		layer.msg(msg);
		// 	}
		// },
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
	$("#sample_id").css('display','block');
	$('#tabss a:first').tab('show');//初始化显示哪个tab
	$('#tabss a').click(function (e) {
		$('#tabss').find('li').each(function(){
			$($(this).find('a').attr("href")).css('display','none');
		});
		e.preventDefault();//阻止a链接的跳转行为
		$($(this).attr("href")).css('display','block');
		$(this).tab('show');//显示当前选中的链接及关联的content
	});


	$(".form_datetime").datetimepicker({
		minView: "month", //选择日期后，不会再跳转去选择时分秒
		format: "yyyy-mm-dd", //选择日期后，文本框显示的日期格式
		language: 'zh-CN', //汉化
		todayBtn:  1,
		endDate : new Date(),
		autoclose:true //选择日期后自动关闭
	});
	$(".form_datetime1").datetimepicker({
		//minView: "month", //选择日期后，不会再跳转去选择时分秒
		format: "yyyy-mm-dd hh:ii:ss", //选择日期后，文本框显示的日期格式
		language: 'zh-CN', //汉化
		todayBtn:  1,
		endDate : new Date(),
		autoclose:true //选择日期后自动关闭
	}).on('changeDate',function(ev){
		this.focus();
	});

    // $("#samsendtime").datetimepicker({
    //     // minView: "month", //选择日期后，不会再跳转去选择时分秒
    //     format: "yyyy-mm-dd hh:ii:ss", //选择日期后，文本框显示的日期格式
    //     language: 'zh-CN', //汉化
    //     todayBtn:  1,
    //     endDate : new Date(),
    //     autoclose:true //选择日期后自动关闭
    // }).on('changeDate',function(e){
    //     var startTime = $("#samsendtime").val();
    //     var endTime = $("#samreceivertime").val();
    //     if(endTime != null && endTime != "" && endTime < startTime){
		// 	$("#samreceivertime").val("");
    //     }
    //     $('#samreceivertime').datetimepicker('setStartDate',startTime);
		// this.focus();
    // });
    // $("#samreceivertime").datetimepicker({
    //     // minView: "month", //选择日期后，不会再跳转去选择时分秒
    //     format: "yyyy-mm-dd hh:ii:ss", //选择日期后，文本框显示的日期格式
    //     language: 'zh-CN', //汉化
    //     todayBtn:  1,
    //     endDate : new Date(),
    //     autoclose:true //选择日期后自动关闭
    // }).on('changeDate',function(e){
		// var startTime = $("#samsendtime").val();
		// var endTime = $("#samreceivertime").val();
		// if(startTime != null && startTime != "" && endTime < startTime){
		// 	$("#samsendtime").val("");
		// }
    //     $('#samsendtime').datetimepicker('setEndDate',endTime);
		// this.focus();
    // });



    // $('#sampleForm').find('input,textarea,select').attr('disabled',true) ;
    $('#samrequistionid').removeAttr("disabled");
	//检查项目
	$("#samjcxm").autocomplete({
		source: function( request, response ) {
			$.ajax({
				url: "../estitem/ajax/item",
				dataType: "json",
				data: {
					name : request.term,
					tesitemtype: 4
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
			$( "#sampopuser" ).val(ui.item.id);
			$( "#samjcxm" ).val(ui.item.value);
			$("#sampathologyid").val(ui.item.tespathologyid);
			//return false;
		}
	})
		.data( "ui-autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li>" )
			.append( "<a style='font-size:12px;font-family: 微软雅黑;'>" + item.id + "," + item.value+ "</a>" )
			.appendTo( ul );
	};
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
							id : result.id,
                            is_self:result.is_self
						}
					}));
				}
			});
		},
		minLength: 0,
		select: function( event, ui ) {
			//$( "#reqwardcode" ).val(ui.item.id);
			$( "#samsendhospital" ).val(ui.item.value);
            $("#samsource").val(ui.item.is_self);
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
	var clientHeight = $(window).innerHeight();

	var mainheight = clientHeight - $("#div_1").height() - $('#h5_1').height()-$("#tabss").height() - $("#search_div_1").height()-$("#menuheader").height()-120;
	if(mainheight < 340){
		mainheight = 340;
	}
	var reqheight = mainheight + $("#search_div_1").height()+ 40
	if(reqheight < 340){
		reqheight = 340;
	}
	var feeheight = reqheight - $("#sampleForm").height()-60;
    var width = $('#search_div_1').width();
    var width1 = $("#sampleForm").width();
	//alert($(window).innerWidth());
	var logyid = $("#logyid").val();
	// var logyid ="";
	var send_hosptail = "";
	var req_code = $('#req_code').val();
	var patient_name = $('#patient_name').val();
	//var send_hosptail = $('#send_hosptail').val();
	var req_bf_time = $('#req_bf_time').val();
	var req_af_time = $('#req_af_time').val();
	var send_dept = $('#send_dept').val();
	var send_doctor = $('#send_doctor').val();
	var req_sts = $("input[name='req_sts']:checked").val();//包埋状态
    createNew1(feeheight,width1);
	$("#new").jqGrid({
		//caption:"标本列表",
		url: "../pathologysample/sample/ajax/sample",
		mtype: "GET",
		datatype: "json",
		postData:{"req_code":req_code,"patient_name":patient_name,"send_hosptail":send_hosptail,"req_bf_time":req_bf_time,
			"req_af_time":req_af_time,"send_dept":send_dept,"send_doctor":send_doctor,"req_sts":req_sts,"logyid":logyid},
		colNames: ['ID', '病理编号','患者姓名','送检单位','送检科室', '送检医生','申请时间','合格状态','病理状态','性别','年龄','年龄类型','临床诊断','送检时间','登记时间'],
		colModel: [
			{name:'sampleid',hidden:true},
			{ name: 'sampathologycode', index: 'sampathologycode',width:'100px', align: "center"},
			{ name: 'sampatientname', index: 'sampatientname',width:'100px', align: "center"},
			{ name: 'samsendhospital', index: 'samsendhospital',width:'100px', align: "center"},
			{ name: 'samdeptname', index: 'samdeptname',width:'100px', align: "center"},
			{ name: 'samsenddoctorname', index: 'samsenddoctorname',width:'100px', align: "center"},
			{name:'samreqtime',hidden:true,formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))}},
			{ name: 'samsecondv', index: 'samsecondv',width:'100px', align: "center",formatter:"select",editoptions:{value:"1:合格;2:不合格"}},
			{ name: 'samsamplestatus', index: 'samsamplestatus',formatter: "select", editoptions:{value:"0:已登记;1:已取材;2:已包埋;3:已切片;4:已初诊;5:已审核;" +
			"6:已发送;7:会诊中:8:报告已打印"},width:'100px', align: "center"},
			{name:'sampatientsex',hidden:true},
			{name:'sampatientage',hidden:true},
			{name:'sampatientagetype',hidden:true},
			{name:'sampatientdignoses',hidden:true},
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
		height:mainheight,
        width:width,
        shrinkToFit:false,
        autoScroll: true,
		//autowidth: true,
		rowNum: 10,
		rowList:[20,30,40],
		rownumbers: true, // 显示行号
		rownumWidth: 30, // the width of the row numbers columns
		pager: "#pager"
	});

	$("#new1").jqGrid({
		url: "../pathologysample/sample/ajax/getreqinfo",
		mtype: "GET",
		datatype: "json",
		postData:{"logyid":$("#local_logyid").val()},
		colNames: ['选择','详情','ID','临床申请', '病种类别', '申请年月','病人姓名','送检医院','送检科室',"送检医生"],
		colModel: [
			{ name: 'selectinfo', index: 'selectinfo', sortable: false, align: "center", width: "70px" ,hidden:true},
			{ name: 'showinfo', index: 'showinfo', sortable: false, align: "center", width: "70px" },
			{name:'requisitionid',hidden:true},
			{ name: 'requisitionno', index: 'requisitionno', align: "center"},
			{ name: 'reqpathologyid', index: 'reqpathologyid',formatter: "select", editoptions:{value:gettypes()}, align: "center"},
			{ name: 'reqdate', index: 'reqdate',formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))}, align: "center"},
			{ name: 'reqpatientname', index: 'reqpatientname', align: "center"},
			{ name: 'reqsendhospital', index: 'reqsendhospital', align: "center"},
			{ name: 'reqdeptname', index: 'reqdeptname', align: "center"},
			{ name: 'reqdoctorname', index: 'reqdoctorname', align: "center"}
		],
		gridComplete: function () {
			var ids = jQuery("#new1").jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var id = ids[i];
				var showinfo = "<button style='border-radius:5px;border:1px solid #C2C2C2;' onclick='showInfo("+id+")' >详情</button>";
				var selectinfo = "<button style='border-radius:5px;border:1px solid #C2C2C2;' onclick='fillInfo("+id+")' >选择</button>";
				jQuery("#new1").jqGrid('setRowData', ids[i], { selectinfo: selectinfo, showinfo: showinfo });
			}
		},
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		ondblClickRow: function (id) {
			fillInfo(id);
		},
		onSelectRow:function(id){
			fillInfo(id);
		},
		viewrecords: true,
		height:reqheight,
        width:width,
		//autowidth: true,
		// rowNum: 20,
		shrinkToFit:false,
		autoScroll: true,
		// rowList:[10,20,30],
		rownumbers: true, // 显示行号
		rownumWidth: 30, // the width of the row numbers columns
		// pager: "#pager1"
	});

    // createNew2();
    $("#pager_left").remove();
	$("#pager1_left").remove();
	createNew22("");
});
/**
 * 初始化收费项目列表
 */
function createNew1(height,width) {
	$("#new2").jqGrid({
		url:"../pathologysample/sample/ajax/fee",
		datatype: "json",
		mtype:"GET",
//		height: height,
        width:width,
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
		//autowidth: true,
		// shrinkToFit:false,
		// autoScroll: true,
		viewrecords: true,
		rownumbers : true
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
	var logyid = $("#logyid").val();
	//var req_sts = $('#req_sts').val();
	var req_sts = $("input[name='req_sts']:checked").val();//包埋状态
	jQuery("#new").jqGrid("clearGridData");
	jQuery("#new").jqGrid('setGridParam',{
		url: "../pathologysample/sample/ajax/sample",
		//发送数据
		postData : {"req_code":req_code,"patient_name":patient_name,"send_hosptail":send_hosptail,"req_bf_time":req_bf_time,
			"req_af_time":req_af_time,"send_dept":send_dept,"send_doctor":send_doctor,"req_sts":req_sts,"logyid":logyid},
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
	// $("#editButton").attr({"disabled":"disabled"});
	// $("#deleteButton").attr({"disabled":"disabled"});
	// $("#hisbutton").removeAttr("disabled");
	changeimgclick(1);
	addstates="0";
	$('#sampleForm').find('input,textarea,select').removeAttr('disabled') ;
	$("#sampathologyid").attr({"disabled":"disabled"});
	getdynamicdiv(rowData.requisitionid,1);
	// $("#saveButton").removeAttr("disabled");//将按钮可用
	// $("#sampathologycode").attr({"disabled":"disabled"});
	// $("#samrequistionid").attr({"disabled":"disabled"});
	// $("#sampathologyid").attr({"disabled":"disabled"});
}
/**
 * 选择标本信息
 * @param id
 * @returns {boolean}
 */
function fillInfo1(id){
	setcolor(id);
	nowrow = id;
	clearData();
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
	// $('#sampleForm').find('input,textarea,select').attr('disabled',true) ;
	// $("#editButton").removeAttr("disabled");//将按钮可用
	// $("#deleteButton").removeAttr("disabled");//将按钮可用
	// $("#saveButton").attr({"disabled":"disabled"});
	// $("#hisbutton").attr({"disabled":"disabled"});
	changeimgclick(2);
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
			$("#samjcxm").val(data.samjcxm);//检查项目
			var samfirstv = data.samfirstv;
			var samsecondv = data.samsecondv;
			if(samfirstv == 1){
				$("input[name='samfirstv'][value='1']").prop("checked",true);
			}else{
				$("input[name='samfirstv'][value='2']").prop("checked",true);
			}
			if(samsecondv == 1){
				$("input[name='samsecondv'][value='1']").prop("checked",true);
			}else{
				$("input[name='samsecondv'][value='2']").prop("checked",true);
			}
			$("#sampiecedoctorid").val(data.sampiecedoctorid);//首次取材医师既诊断医师ID
			$("#sampiecedoctorname").val(data.sampiecedoctorname);//首次取材医师既诊断医师
			$("#samremark").val(data.samremark);//不合格原因
		} else {
			layer.msg("该申请单不存在！", {icon: 0, time: 1000});
		}
	});
}
/**
 * 设置颜色
 * @param id
 */
function setcolor(id){
	var ids = $("#new").getDataIDs();
	$.each(ids, function (key, val) {
		$("#new").children().children("tr[id='"+ids[key]+"']").removeClass("ui-state-highlight");
	});
	$("#new").children().children("tr[id='"+id+"']").addClass("ui-state-highlight");
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
	setcolor(id);
	// $("#new").jqGrid('setSelection', id);
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
	setcolor(id);
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
			$("#reqlastmenstruation1").val(data.reqlastmenstruation);//末次月经时间
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
			if($("#reqtype").val() == "1"){//是否手术
				$("#reqtype1").attr("checked",true);
				$("[name='ssxx']").css("display","block");
			}else{
				$("#reqtype1").attr("checked",false);
				$("[name='ssxx']").css("display","none");
			}
			$("#reqprevious").val(data.reqprevious);//婚史
			$("#reqmenses").val(CurentTime(new Date(data.reqmenses)));//月经初潮
			$("#reqcycle").val(data.reqcycle);//周期
			$("#reqcesarean").val(data.reqcesarean);//产史
			$("#reqpatientdeptcode").val(data.reqpatientdeptcode);//病人所在科室
			$("#reqpatientwardcode").val(data.reqpatientwardcode);//病人所在病区
			changeSexinfo();
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
	getdynamicdiv(rowData.requisitionid,1);
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
function getdynamicdiv(logyid,num) {
	$("#dynamic_div2").empty();
	var html = "";
	if(num == 0){//新增
		$.ajax({
			type:'get',
			url: '../pimspathology/ajax/item',
			data:{"logyid":logyid,"req_code":$("#lcal_hosptail").val(),"req_sts":2},
			dataType:"json",
			// error:function(value){
			// 	ds.dialog.alert('加载失败');
			// },
			success: function(obj){
				var fieremark = "";
				var maxnum = 0;
				if(obj != null && obj != ""){
					$.each(obj,function(n,value) {
						if(fieremark != value.fieremark){
							fieremark = value.fieremark;
							html +="<div class=\"form-group\" style=\"margin: 0px 0px 0px 0px\"> <h5 style=\"float: left;font-size: 14px;\">"+value.fieremark+"</h5></div>";
						}
						if(maxnum == 0){
							html +="<div class=\"form-group\" style=\"margin-bottom: 5px;z-index: 99999999;\">";
						}
						maxnum += parseInt(value.fieldcss.substr(7))+parseInt(value.invokefunc.substr(7));
						html +="<label class=\""+value.fieldcss +"\">"+value.fieelementname+":</label><"+value.fieelementtype +" id = \""+value.fieelementid+"\" class=\""+value.invokefunc +"\"";
						if(value.fieelementtype == "input"){
							html +=" type= \"text\"";
						}
						html +=">";
						if(value.invokefuncbody != null && value.invokefuncbody != ""){
							html += value.invokefuncbody;
						}
						html +="</"+value.fieelementtype+">"
						if(maxnum == 12){
							html +="</div>";
							maxnum = 0;
						}
					});
					$("#dynamic_div2").html(html);
				}

			}
		});
	}else if(num ==1){//查看
		$.ajax({
			type:'get',
			url: '../pimspathology/ajax/reqdata',
			data:{"id":logyid},
			dataType:"json",
			// error:function(value){
			// 	ds.dialog.alert('加载失败');
			// },
			success: function(obj){
				var fieremark = "";
				var maxnum = 0;
				if(obj != null && obj != ""){
					$.each(obj,function(n,value) {
						if(fieremark != value.fieremark){
							fieremark = value.fieremark;
							html +="<div class=\"form-group\" style=\"margin: 0px 0px 0px 0px\"> <h5 style=\"float: left;font-size: 14px;\">"+value.fieremark+"</h5></div>";
						}
						if(maxnum == 0){
							html +="<div class=\"form-group\" style=\"margin-bottom: 5px;z-index: 99999999;\">";
						}
						maxnum += parseInt(value.fieldcss.substr(7))+parseInt(value.invokefunc.substr(7));
						html +="<label class=\""+value.fieldcss +"\">"+value.fieelementname+":</label><"+value.fieelementtype +" id = \""+value.fieelementid+"\" class=\""+value.invokefunc +"\"";
						if(value.fieelementtype == "input"){
							html +=" type= \"text\"";
						}
						if(value.fieelementtype == "textarea"){
							html +=">"+value.reqfvalue;
						}else{
							html +="value=\""+value.reqfvalue+"\">";
						}
						if(value.invokefuncbody != null && value.invokefuncbody != ""){
							html += value.invokefuncbody;
						}
						html +="</"+value.fieelementtype+">"
						if(maxnum == 12){
							html +="</div>";
							maxnum = 0;
						}
					});
					$("#dynamic_div2").html(html);
				}

			}
		});
	}


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
		var sex = "";
		if(data.sampatientsex == '1'){sex = '男'}else if(data.sampatientsex == '2'){sex = '女'}else{sex = '未知'}
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
		setPrintIndex();
		LODOP.SET_PRINT_PAGESIZE(0,520,400,"A4");
		// LODOP.ADD_PRINT_IMAGE(10,10,80,80,"<img src='../images/shulan.png' style='width:80px;'/>");
        LODOP.ADD_PRINT_TEXT("1mm","1mm","100mm","5mm","病理编号:" + data.sampathologycode);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
		LODOP.ADD_PRINT_TEXT("6mm","1mm","25mm","6mm","姓名:" + data.sampatientname);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
        LODOP.ADD_PRINT_TEXT("6mm","20mm","25mm","6mm","性别:"+ sex);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
        LODOP.ADD_PRINT_TEXT("6mm","35mm","25mm","6mm","年龄:"+ data.sampatientage + ageUnit);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
		LODOP.ADD_PRINT_BARCODEA("saminspectionid","11mm","1mm","46.57mm",40,"128B",data.saminspectionid);
        LODOP.ADD_PRINT_TEXTA("samreqtime","22mm","1mm",300,20,data.samreqtime);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
	}
}
function startPrint(data) {
	CreateDataBill(data);
	//开始打印
	// LODOP.PRINT();
	LODOP.PREVIEW();
}
/**
 * 获取浏览器名称及版本号
 * @returns {{appname: string, version: number}}
 */
function appInfo(){
	var browser = {appname: 'unknown', version: 0},
		userAgent = window.navigator.userAgent.toLowerCase();
	//IE,firefox,opera,chrome,netscape
	if ( /(msie|firefox|opera|chrome|netscape)\D+(\d[\d.]*)/.test( userAgent ) ){
		browser.appname = RegExp.$1;
		browser.version = RegExp.$2;
	} else if ( /version\D+(\d[\d.]*).*safari/.test( userAgent ) ){ // safari
		browser.appname = 'safari';
		browser.version = RegExp.$2;
	}
	return browser;
}

function changeSexinfo() {
	if($("#reqpatientsex").val() == "2"){
		$("#sexinfo").css("display","block");
	}else{
		$("#sexinfo").css("display","none");
	}
}


function printCode1(){
	var id = $("#new1").jqGrid('getGridParam', 'selrow');
	var rowData = $("#new1").jqGrid('getRowData',id);
	if (id == null || id == 0) {
		layer.msg('请先选择要打印的数据', {icon: 2, time: 1000});
		return;
	}
	$.get("../pimspathology/report/printreq", {
		"id": rowData.requisitionid,
		"hosptail":$("#lcal_hosptail").val()
	}, function (data) {
		var rptView = layer.open({
			type: 2,
			title: "报告单预览",
			area: ['854px', '600px'],
			btn: ["打印",  "关闭"],
			maxmin: true,
			shade: 0.5,
			content: data.url,
			yes: function (index1, layero1) {
				print(data.url);
				layer.close(index1);
			},
			btn2: function (index1, layero1) {
				layer.close(index1);
			}
		});
		layer.full(rptView);
		// print(data.url);
	});
}

function print(url) {
	LODOP = getLodop();
	LODOP.PRINT_INIT("申请单打印");
	$.get(url,function (data) {
		// LODOP.ADD_PRINT_HTM(10, 10, 794, 1123, $("#test").html());
		LODOP.ADD_PRINT_HTM(10, 10, 794, 1123, data);
		// LODOP.ADD_PRINT_URL(10, 10, 794, 1123, url);
		// LODOP.SET_PRINT_STYLEA(0, "HOrient", 3);
		// LODOP.SET_PRINT_STYLEA(0, "VOrient", 3);
		LODOP.PREVIEW();
		// LODOP.PRINT();
	})

}