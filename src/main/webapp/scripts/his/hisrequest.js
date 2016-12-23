var NOW_LOGYID = ($("#logylibid").val()==null || $("#logylibid").val() == "")?"181":$("#logylibid").val();
var LASTEDITROW = "";
var LASTEDITCELL = "";
var SENDINFO ="";//常规还是细胞
function NoSubmit(ev){
	if( ev.keyCode == 13 ){
		return false;
	}
	return true;
}
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
						$("#reqpatientname").val(rows[0].patient_name);//姓名
						$("#reqpatientsex").val(rows[0].patient_sex);//性别
						// $("#reqpatientsex option").each(function () {
						// 	if($(this).text() == rows[0].patient_sex){
						// 		$(this).attr("selected", "selected");
						// 	}
						// });
                        changeSexinfo();
						$("#reqpatientage").val(rows[0].patient_age);//年龄
						// $("#reqpatagetype").val(rows[0].patient_age_type);//年龄类型
						$("#reqpatagetype option").each(function () {
							if($(this).text() == rows[0].patient_age_type){
								$(this).attr("selected", "selected");
							}
						});
						$("#reqpattelephone").val(rows[0].phone_no);//电话
						$("#reqpatienttype").val(rows[0].patient_type);//患者类型
						$("#reqpatientwardcode").val(rows[0].patient_ward_name);//病区
						$("#reqpatientdeptcode").val(rows[0].patient_dept_name);//科室
						$("#reqfirstn").val(rows[0].patient_bed);//床号
						$("#reqpataddress").val(rows[0].patient_address);//联系地址
						$("#reqpatdiagnosis").val(rows[0].lczd);//临床诊断
				}else{
					jQuery("#new2").jqGrid("clearGridData");
					jQuery("#new2").jqGrid('setGridParam',{
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
						content: $("#formDialog1")
					});
				}
			});
			break;
	}
}
/**
 * 双击事件事件
 * @param obj
 * @param event
 */
function getYxxinfo(yxjclx,objid) {
	if($("#reqpatientnumber").val() == null || $("#reqpatientnumber").val() == ""){
		layer.msg("请先选择病人信息!", {icon:2, time: 1000});
	}else{
		$.get("../pimspathology/getyxxlist", {"brjzxh": $("#reqpatientnumber").val(),"yxjclx":yxjclx}, function (data) {
			var records = data.records;
			if(records == undefined){

			}else if (records == 1) {
				var rows = data.rows;
				$("#"+objid).val(rows[0].yxzdnr);
			}else{
				jQuery("#new3").jqGrid("clearGridData");
				jQuery("#new3").jqGrid('setGridParam',{
					url: "../pimspathology/getyxxlist",
					//发送数据
					postData : {"brjzxh":$("#reqpatientnumber").val(),"yxjclx":yxjclx}
				}).trigger('reloadGrid');//重新载入
				layer.open({
					type: 1,
					area: ['1000px','600px'],
					skin: 'layui-layer-molv',
					fix: false, //不固定
					maxmin: false,
					shade:0.6,
					title: "申请信息录入",
					content: $("#formDialog2")
				});
			}
		});
	}
}
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
 * 上一个
 */
function upSample(){
	clearData();
	var id = nowrow;
	var ids = $("#new").jqGrid('getDataIDs');
	var minId = Math.min.apply(Math,ids);
	if(id == minId){
		id = Math.max.apply(Math,ids);
	}else{
		id = parseInt(id) - 1;
	}
	fillInfo(id);
}
/**
 * 下一个
 */
function downSample() {
	clearData();
	var id = nowrow;
	var ids = $("#new").jqGrid('getDataIDs');
	var maxId = Math.max.apply(Math,ids);
	if(id == maxId){
		id = Math.min.apply(Math,ids);
	}else{
		id = parseInt(id) + 1;
	}
	fillInfo(id);
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
			$("#reqdate").val(data.reqdate ==null?"":CurentTime(new Date(data.reqdate)));//申请日期
			$("#reqinspectionid").val(data.reqinspectionid);//标本条码号
			$("#reqdatechar").val(data.reqdatechar);//申请日期（8位日期字符）
			$("#reqdeptcode").val(data.reqdeptcode);//申请科室
			$("#reqdeptname").val(data.reqdeptname);//申请科室名称
			$("#reqwardcode").val(data.reqwardcode);//申请病区
			$("#reqwardname").val(data.reqwardname);//申请病区名称
			$("#reqdoctorid").val(data.reqdoctorid);//申请医生id
			$("#reqdoctorname").val(data.reqdoctorname);//申请医生姓名
			$("#reqplanexectime").val(data.reqplanexectime ==null?"":CurentTime(new Date(data.reqplanexectime)));//执行日期时间
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
			$("#reqlastmenstruation").val(data.reqlastmenstruation ==null?"":CurentTime(new Date(data.reqlastmenstruation)));//末次月经时间
			$("#reqpatcompany").val(data.reqpatcompany);//工作单位（检查要求）
			$("#reqsendhisorder").val(data.reqsendhisorder);//是否回写医嘱(0未发送,1已发送)
			$("#reqsampleid").val(data.reqsampleid);//标本id(申请接收后回写)
			$("#reqisdeleted").val(data.reqisdeleted);//是否删除(0正常，1已删除)
			$("#reqprintuser").val(data.reqprintuser);//申请单打印人员id
			$("#reqprintusername").val(data.reqprintusername);//申请单打印人员姓名
			$("#reqprinttime").val(data.reqprinttime ==null?"":data.reqprinttime);//申请单打印时间
			$("#reqsendtime").val(data.reqsendtime ==null?"":data.reqsendtime);//回写时间
			$("#reqremark").val(data.reqremark);//备注信息
			$("#reqfirstv").val(data.reqfirstv);//预留字段1(第一个varchar预留字段)
			$("#reqsecondv").val(data.reqsecondv);//预留字段2(第二个varchar预留字段)
			$("#reqthirdv").val(data.reqthirdv);//预留字段3(第二个varchar预留字段)
			$("#reqfirstd").val(data.reqfirstd == null?"":CurentTime(new Date(data.reqfirstd)));//预留字段4(第一个datetime预留字段)
			$("#reqsecondd").val(data.reqsecondd ==null?"":CurentTime(new Date(data.reqsecondd)));//预留字段6(第二个datetime预留字段)
			$("#reqfirstn").val(data.reqfirstn);//预留字段6(第一个numberic预留字段)
			$("#reqcreateuser").val(data.reqcreateuser);//创建人员
			$("#reqcreatetime").val(data.reqcreatetime ==null?"":CurentTime(new Date(data.reqcreatetime)));//创建时间
			if($("#reqtype").val() == "1"){//是否手术
				$("#reqtype1").attr("checked",true);
				$("[name='ssxx']").css("display","block");
			}else{
				$("#reqtype1").attr("checked",false);
				$("[name='ssxx']").css("display","none");
			}
			$("#reqprevious").val(data.reqprevious);//婚史
            $("#reqmenses").val(data.reqmenses ==null?"":CurentTime(new Date(data.reqmenses)));//月经初潮
            $("#reqcycle").val(data.reqcycle);//周期
            $("#reqcesarean").val(data.reqcesarean);//产史
            $("#reqpatientdeptcode").val(data.reqpatientdeptcode);//病人所在科室
            $("#reqpatientwardcode").val(data.reqpatientwardcode);//病人所在病区
			$("#reqxray").val(data.reqxray);//X光
			$("#reqct").val(data.reqct);//CT
			$("#reqbultrasonic").val(data.reqbultrasonic);//B超
            changeSexinfo();
		} else {
			layer.msg("该申请单不存在！", {icon: 0, time: 1000});
		}
	});
}
/**
 * 保存信息
 */
function saveInfo1(post,arrs,rowdatas,arrs1) {
	if(post) {
		$.post("../pimspathology/editSample", {
				arrs:JSON.stringify(arrs),
                arrs1:JSON.stringify(arrs1),
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
                reqprevious:$("#reqprevious").val(),//婚史
                reqmenses:$("#reqmenses").val(),//月经初潮
                reqcycle:$("#reqcycle").val(),//周期
                reqcesarean:$("#reqcesarean").val(),//产史
                reqpatientdeptcode:$("#reqpatientdeptcode").val(),//病人所在科室
                reqpatientwardcode:$("#reqpatientwardcode").val(),//病人所在病区
				reqxray:$("#reqxray").val(),//X光
				reqct:$("#reqct").val(),//CT
				reqbultrasonic:$("#reqbultrasonic").val()//B超
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
function saveInfo() {
	$("#new1").jqGrid("saveCell",LASTEDITROW,LASTEDITCELL);
    var arrs1 = new Array();
    var arrs = new Array();
    var rowdatas;
	// var rowdatas = $('#new1').jqGrid('getRowData');
	var post = true;
	jQuery(rowdatas).each(function(){
		if(this.reqmsamplingparts == null || this.reqmsamplingparts == "" || this.reqmmaterialtype == null || this.reqmmaterialtype == ""){
			post = false;
			layer.msg("请补充完整组织信息！", {icon: 2, time: 1000});
			return false;
		}
	});
	if($("#reqtype1").is(":checked")){
		if($("#reqfirstd").val() == null || $("#reqfirstd").val() == ""
			|| $("#reqsecondv").val() == null || $("#reqsecondv").val() == ""
			|| $("#reqthirdv").val() == null || $("#reqthirdv").val() == ""
			|| $("#reqremark").val() == null || $("#reqremark").val() == ""){
			post = false;
			layer.msg("勾选做手术时，请将手术信息填写完整!", {icon: 2, time: 2000});
			return false;
		}
	}
    if(SENDINFO == 0){
        rowdatas  = $('#new1').jqGrid('getRowData');
        saveInfo1(post,arrs,rowdatas,arrs1);
    }else if(SENDINFO == 1){
        $.ajax({
            type:'get',
            url: '../pimspathology/ajax/item',
            data:{"logyid":$("#reqpathologyid").val(),"req_code":$("#lcal_hosptail").val(),"req_sts":5},
            dataType:"json",
            error:function(value){
				saveInfo1(post,arrs,rowdatas,arrs1);
            },
            success: function(obj){
            	//alert(obj);
                $.each(obj,function(n,value) {
                    var arr = {};
                    arr["id"] = value.fieelementid;
                    if($("#"+ value.fieelementid).is(':checked')){
                        arr["value"] = "1";
                    }else{
                        arr["value"] = "0";
                    }
                    arrs1.push(arr);
                });
                if(post){
                    $.ajax({
                        type:'get',
                        url: '../pimspathology/ajax/item',
                        data:{"logyid":$("#reqpathologyid").val(),"req_code":$("#lcal_hosptail").val(),"req_sts":2},
                        dataType:"json",
                        error:function(value){
                            saveInfo1(post,arrs,rowdatas,arrs1);
                        },
                        success: function(obj){
                            $.each(obj,function(n,value) {
                                var arr = {};
                                arr["id"] = value.fieelementid;
                                arr["value"] = $("#"+ value.fieelementid).val();
                                // alert(arr);
                                arrs.push(arr);
                            });
                            saveInfo1(post,arrs,rowdatas,arrs1);
                        }
                    });
                }
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
		beforeEditCell: function (rowid, cellname, value, iRow, iCol) {
			LASTEDITROW = iRow;
			LASTEDITCELL = iCol;
		},
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

/**
 * 创建病人信息列表
 * @param reqid
 */
function createNew2(brjzxh){
	$("#new2").jqGrid({
		url:"../pimspathology/getpatientlist",
		datatype: "json",
		mtype:"GET",
		height: 500,
		width: 1000,
		postData:{"brjzxh":brjzxh},
		colNames: ['ID','住院号','病人姓名','性别','年龄','年龄类型', '住院科室','住院病区','床号','临床诊断','电话','患者类型','联系地址'],
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
			{name:'patient_address',hidden:true}//联系地址
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
			var rowData = $("#new2").jqGrid('getRowData',id);
			$("#reqpatientname").val(rowData.patient_name);//姓名
			$("#reqpatientsex").val(rowData.patient_sex);//性别
            // alert(rowData.patient_sex);
			// $("#reqpatientsex option").each(function () {
			// 	if($(this).text() == rowData.patient_sex){
			// 		$(this).attr("selected", "selected");
			// 	}
			// });
            changeSexinfo();
			$("#reqpatientage").val(rowData.patient_age);//年龄
			// $("#reqpatagetype").val(rowData.patient_age_type);//年龄类型
			$("#reqpatagetype option").each(function () {
				if($(this).text() == rowData.patient_age_type){
					$(this).attr("selected", "selected");
				}
			});
			$("#reqpattelephone").val(rowData.phone_no);//电话
			$("#reqpatienttype").val(rowData.patient_type);//患者类型
			$("#reqpatientwardcode").val(rowData.patient_ward_name);//病区
			$("#reqpatientdeptcode").val(rowData.patient_dept_name);//科室
			$("#reqfirstn").val(rowData.patient_bed);//床号
			$("#reqpataddress").val(rowData.patient_address);//联系地址
			$("#reqpatdiagnosis").val(rowData.lczd);//临床诊断
			var index = layer.index; //获取窗口索引
			layer.close(index);
			//layer.close();
		},
	});
}

/**
 * 创建病人信息列表
 * @param reqid
 */
function createNew3(){
	$("#new3").jqGrid({
		url:"../pimspathology/getyxxlist",
		datatype: "json",
		mtype:"GET",
		height: 500,
		width: 1000,
		// postData:{"brjzxh":brjzxh},
		colNames: ['检测样本号码','组织机构代码','受检病人床号','检查目的名称','检查项目名称','检查部位内容', '影像所见内容','影像诊断内容','临床诊断名称','病史摘要内容','yxjclx'],
		colModel: [
			{name:'jcybid',index:'jcybid',align:"center"},//检测样本号码
			{name:'zzjgdm',index:'zzjgdm',align:"center"},//组织机构代码
			{name:'sjbrch',index:'sjbrch',align:"center"},//受检病人床号
			{ name: 'jcmdmc', index: 'jcmdmc',align:"center"},//检查目的名称
			{ name: 'jcxmmc', index: 'jcxmmc',align:"center"},//检查项目名称
			{ name: 'jcbwnr', index: 'jcbwnr',align:"center"},//检查部位内容
			{name:'yxsjnr',index:'yxsjnr',align:"center"},//影像所见内容
			{name:'yxzdnr',index:'yxzdnr',align:"center"},//影像诊断内容
			{name:'lczdmc',index:'lczdmc',align:"center"},//临床诊断名称
			{name:'bszynr',index:'bszynr',align:"center"},//病史摘要内容
			{name:'yxjclx',hidden:true,align:"center"}//病史摘要内容
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
			var rowData = $("#new3").jqGrid('getRowData',id);
			if(rowData.yxjclx == 'DX'){
				$("#reqxray").val(rowData.yxzdnr);
			}else if(rowData.yxjclx == 'CT'){
				$("#reqct").val(rowData.yxzdnr);
			}else if(rowData.yxjclx == 'US'){
				$("#reqbultrasonic").val(rowData.yxzdnr);
			}
			var index = layer.index; //获取窗口索引
			layer.close(index);
		},
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
	$.get("../pimspathology/getcode", {},
		function(data) {
			if(data.success) {
				$("#requisitionno").val(data.maxcode);
			} else {
			}
		}
	);
	$("#requisitionid").val("");//申请id
	$("#reqcustomerid").val($("#lcal_hosptail").val());//客户id
	//$("#reqpathologyid").val($("#local_logyid").val());//病种类别id
	//$("#requisitionno").val("");//申请单号
	$("#reqsource").val("0");//申请单来源(0手工登记 1第三方系统接收)
	$("#reqtype").val("1");//申请类型(1住院，2门诊，3手术室)
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
	$("#reqxray").val("");//X光
	$("#reqct").val("");//CT
	$("#reqbultrasonic").val("");//B超
	$("#reqtype1").attr("checked",true);
	$("[name='ssxx']").css("display","block");
	getdynamicdiv($("#logylibid").val(),0,$("#requisitionid").val());
}
function getdynamicdiv(logyid,num,reqid) {
	$.get("../pimspathology/getpathologyinfo", {"id":logyid},
		function(data) {
			if(data.patissampling == 0) {
                SENDINFO = 0;
                $("#divnew").css("display","block");
				$("#divnew1").css("display","block");
				$("#divnew2").css("display","none");
			} else {
                SENDINFO = 1;
                $("#divnew").css("display","none");
				$("#divnew1").css("display","none");
				$("#divnew2").css("display","block");
				$("#divnew2").empty();
				if(num == 0){//新增
					$.ajax({
						type:'get',
						url: '../pimspathology/ajax/item',
						data:{"logyid":logyid,"req_code":$("#lcal_hosptail").val(),"req_sts":"5"},
						dataType:"json",
						success: function(obj){
							var html1 = "";
							if(obj != null && obj != ""){
								$.each(obj,function(n,value) {
									if(n%3 == 0){
										html1 +="<div class=\"form-group \" style=\"margin-bottom: 5px\">";
										html1 +="<input type=\""+value.fieelementtype+"\" class=\"col-sm-1 label_style\" id=\""+value.fieelementid+"\"/>";
										html1 +="<label class=\"col-sm-2 input_style\" style=\"line-height: 24px\">"+value.fieelementname+"</label>";
									}else if(n%3 == 1){
										html1 +="<input type=\""+value.fieelementtype+"\" class=\"col-sm-1 label_style\" id=\""+value.fieelementid+"\"/>";
										html1 +="<label class=\"col-sm-3 input_style\" style=\"line-height: 24px\">"+value.fieelementname+"</label>";
									}else if(n%3 == 2){
										html1 +="<input type=\""+value.fieelementtype+"\" class=\"col-sm-1 label_style\" id=\""+value.fieelementid+"\"/>";
										html1 +="<div class='col-sm-4'><label class=\"col-sm-12 input_style\" style=\"line-height: 24px\">"+value.fieelementname+"</label></div>";
										html1 +="</div>";
									}
								});
								$("#divnew2").html(html1);
							}

						}
					});
				}else if(num ==1){//查看
					$.ajax({
						type:'get',
						url: '../pimspathology/ajax/reqdata',
						data:{"id":reqid,"reqffirstv":1},
						dataType:"json",
						success: function(obj){
                            var html1 = "";
                            var checkstates = "";
							if(obj != null && obj != ""){
								$.each(obj,function(n,value) {
								    if(value.reqfvalue == "1"){
                                        checkstates = "checked";
                                    }else{
                                        checkstates="";
                                    }
                                    if(n%3 == 0){
                                        html1 +="<div class=\"form-group \" style=\"margin-bottom: 5px\">";
                                        html1 +="<input type=\""+value.fieelementtype+"\" class=\"col-sm-1 label_style\" id=\""+value.fieelementid+"\"" +checkstates+
                                            "/>";

                                        html1 +="<label class=\"col-sm-2 input_style\" style=\"line-height: 24px\">"+value.fieelementname+"</label>";
                                    }else if(n%3 == 1){
                                        html1 +="<input type=\""+value.fieelementtype+"\" class=\"col-sm-1 label_style\" id=\""+value.fieelementid+"\"" +checkstates+
                                            "/>";
                                        html1 +="<label class=\"col-sm-3 input_style\" style=\"line-height: 24px\">"+value.fieelementname+"</label>";
                                    }else if(n%3 == 2){
                                        html1 +="<input type=\""+value.fieelementtype+"\" class=\"col-sm-1 label_style\" id=\""+value.fieelementid+"\"" +checkstates+
                                            "/>";
                                        html1 +="<div class='col-sm-4'><label class=\"col-sm-12 input_style\" style=\"line-height: 24px\">"+value.fieelementname+"</label></div>";
                                        html1 +="</div>";
                                    }
								});
								$("#divnew2").html(html1);
							}
						}
					});
				}
			}
		}
	);
	if(logyid == "185"){
		$("#printcodeid").removeAttr("disabled");
		$("#reqfirstv").css("display","block");
        $("#reqfirstv").attr("datatype","*");
		$("#reqfirstv1").css("display","block");
	}else {
		$("#printcodeid").attr("disabled","disabled");
		$("#reqfirstv").css("display","none");
		$("#reqfirstv1").css("display","none");
        $("#reqfirstv").removeAttr("datatype");
	}
	$("#dynamic_div2").empty();
	var html = "";
	if(num == 0){//新增
		$.ajax({
			type:'get',
			url: '../pimspathology/ajax/item',
			data:{"logyid":logyid,"req_code":$("#lcal_hosptail").val(),"req_sts":"2"},
			dataType:"json",
			// error:function(value){
			// 	ds.dialog.alert('加载失败');
			// },
			success: function(obj){
				var fieremark = "";
				var maxnum = 0;
				if(obj != null && obj != ""){
					$.each(obj,function(n,value) {
						// if(fieremark != value.fieremark){
						// 	fieremark = value.fieremark;
						// 	html +="<div class=\"form-group\" style=\"margin: 0px 0px 0px 0px\"> <h5 style=\"float: left;font-size: 14px;\">"+value.fieremark+"</h5></div>";
						// }
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
			data:{"id":reqid,"reqffirstv":0},
			dataType:"json",
			// error:function(value){
			// 	ds.dialog.alert('加载失败');
			// },
			success: function(obj){
				var fieremark = "";
				var maxnum = 0;
				if(obj != null && obj != ""){
					$.each(obj,function(n,value) {
						// if(fieremark != value.fieremark){
						// 	fieremark = value.fieremark;
						// 	html +="<div class=\"form-group\" style=\"margin: 0px 0px 0px 0px\"> <h5 style=\"float: left;font-size: 14px;\">"+value.fieremark+"</h5></div>";
						// }
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
 * 修改数据
 * @returns {boolean}
 */
function fillInfo(id) {
	setcolor(id);
	clearData();
	nowrow = id;
	var rowData = $("#new").jqGrid('getRowData',id);
    if (id == null || id == 0) {
        layer.msg('请先选择要修改的数据', {icon: 2, time: 1000});
        return false;
    }
	getSampleData(rowData.requisitionid);
	jQuery("#new1").jqGrid('setGridParam',{
		url: "../pimspathology/ajax/getmaterial",
		//发送数据
		postData : {"reqId":rowData.requisitionid}
	}).trigger('reloadGrid');//重新载入
	getdynamicdiv(rowData.reqpathologyid,1,rowData.requisitionid);
}
/**
 * 删除数据
 * @returns {boolean}
 */
function deleteSample() {
	var id = $("#new").jqGrid('getGridParam', 'selrow');
	var rowData = $("#new").jqGrid('getRowData',id);
	if (id == null || id == 0) {
		layer.msg('请先选择要删除的数据!', {icon: 2, time: 1000});
		return false;
	}else{
		if(rowData.reqstate > 0){
			layer.msg('该单据处于非申请状态,无法删除!', {icon: 2, time: 1000});
			return;
		}else if(rowData.reqcreateuser != $("#local_userid").val()){
			layer.msg('您无法删除别人申请的单据!', {icon: 2, time: 1000});
			return;
		}else{
			layer.confirm('确定删除选择数据？', {icon: 2, title:'警告'}, function(index){
				$.get("../pimspathology/canchange", {
						id:rowData.requisitionid,
						sts:"2"
					},
					function(data) {
						if(data.success) {
							$.post('../pimspathology/deleteSample',{requisitionid:rowData.requisitionid},function(data) {
								layer.close(index);
								$("#new").trigger('reloadGrid');
							});
						}else{
							layer.msg(data.message, {icon:2, time: 1000});
						}
					});
			});
		}
	}

}
/**
 * 清除数据
 */
function clearData() {
	// $("#sampleForm").Validform.resetForm();
    $('#sampleForm')[0].reset();
	$('#sampleForm1')[0].reset();
    jQuery("#new1").jqGrid("clearGridData");
}

function fillval(id,name,anotherid,obj) {
	var optiontext = $("#"+obj).find("option:selected").text();
	var string1 = optiontext.substring(0,optiontext.indexOf(':'));
	var string2 = optiontext.substr(optiontext.indexOf(':')+1);
	if(id != null){
		$("#"+id).val($("#"+obj).val());
	}
	$("#"+name).val(string2);
	if(anotherid != null){
		$("#"+anotherid).val(string1);
		if(anotherid == "reqpathologyid"){
			if(NOW_LOGYID != string1){
				NOW_LOGYID = string1;
				getdynamicdiv(NOW_LOGYID,0,$("#requisitionid").val());
			}
		}
	}
	$("#"+name).focus();
}
/**
 * 初始化
 */
$(function() {
	//表单校验
	$("#sampleForm").Validform({
		btnSubmit:"#addinfo",
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
		startDate: new Date(),
		todayBtn:  1,
		autoclose:true //选择日期后自动关闭
	}).on('changeDate',function(ev){
		this.focus();
	});
	//病区
	$("#reqpatientwardcode").autocomplete({
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
			$( "#reqpatientwardcode" ).val(ui.item.value);
			//return false;
		}
	})
		.data( "ui-autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li>" )
			.append( "<a style='font-size:12px;font-family: 微软雅黑;'>" + item.id + "," + item.value+ "</a>" )
			.appendTo( ul );
	};
	//科室
	$("#reqpatientdeptcode").autocomplete({
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
			$( "#reqpatientdeptcode" ).val(ui.item.value);
			//return false;
		}
	})
		.data( "ui-autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li>" )
			.append( "<a style='font-size:12px;font-family: 微软雅黑;'>" + item.id + "," + item.value+ "</a>" )
			.appendTo( ul );
	};
	//检查项目
	$("#reqitemnames").autocomplete({
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
			$( "#reqitemids" ).val(ui.item.id);
			$( "#reqitemnames" ).val(ui.item.value);
			if(NOW_LOGYID != ui.item.tespathologyid){
				NOW_LOGYID = ui.item.tespathologyid;
				getdynamicdiv(NOW_LOGYID,0,$("#requisitionid").val());
			}
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
	var width = $('#search_div_1').width();
	var width1 = $("#sampleForm").width();
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
		colNames: ['ID','临床申请', '病种类别', '申请年月','病人姓名','送检医院','送检科室','送检医生','创建人','申请状态','性别','年龄','年龄类型'],
		colModel: [
			{name:'requisitionid',hidden:true},
			{ name: 'requisitionno', index: 'requisitionno'},
			{ name: 'reqpathologyid', index: 'reqpathologyid',formatter: "select", editoptions:{value:gettypes1()}},
			{ name: 'reqdate', index: 'reqdate',formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))}},
			{ name: 'reqpatientname', index: 'reqpatientname'},
			{ name: 'reqsendhospital', index: 'reqsendhospital'},
			{ name: 'reqdeptname', index: 'reqdeptname'},
			{ name: 'reqdoctorname', index: 'reqdoctorname'},
			{name:'reqcreateuser',hidden:true},
			{name:'reqstate',hidden:true},
			{name:'reqpatientsex',hidden:true},
			{name:'reqpatientage',hidden:true},
			{name:'reqpatagetype',hidden:true},
		],
		beforeSelectRow: function (rowid, e) {
			return $(e.target).is('input[type=checkbox]');
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
		onCellSelect:function(id){
			fillInfo(id);
		},
		multiselect: true,
		viewrecords: true,
		height:height,
		with:width,
		shrinkToFit:false,
		autoScroll: true,
		autowidth: true,
		rowNum: 10,
		rowList:[10,20,30],
		rownumbers: true, // 显示行号
		rownumWidth: 35, // the width of the row numbers columns
		pager: "#pager"
	});
    createNew1("");
	createNew2("");
	createNew3();
	$("#pager_left").remove();
	if($("#brjzxh").val() != "" && $("#patient_type").val() != ""){
		$.get("../pimspathology/getpatientinfo", {"brjzxh": $("#brjzxh").val(),"patient_type":$("#patient_type").val()}, function (data) {
			var records = data.records;
			if(records == undefined){

			}else if (records >= 1) {
				var rows = data.rows;
				$("#reqpatientnumber").val(rows[0].patient_id);//住院号
				$("#reqpatientname").val(rows[0].patient_name);//姓名
				$("#reqpatientsex").val(rows[0].patient_sex);//性别
				changeSexinfo();
				$("#reqpatientage").val(rows[0].patient_age);//年龄
				// $("#reqpatagetype").val(rows[0].patient_age_type);//年龄类型
				$("#reqpatagetype option").each(function () {
					if($(this).text() == rows[0].patient_age_type){
						$(this).attr("selected", "selected");
					}
				});
				$("#reqpattelephone").val(rows[0].phone_no);//电话
				$("#reqpatienttype").val(rows[0].patient_type);//患者类型
				$("#reqpatientwardcode").val(rows[0].patient_ward_name);//病区
				$("#reqpatientdeptcode").val(rows[0].patient_dept_name);//科室
				$("#reqfirstn").val(rows[0].patient_bed);//床号
				$("#reqpataddress").val(rows[0].patient_address);//联系地址
				if($("#reqpatdiagnosis").val() == ""){
					$("#reqpatdiagnosis").val(rows[0].lczd);//临床诊断
				}
				$("#reqdate").val(CurentTime(new Date()));//申请时间
				$("#reqdatechar").val(CurentTime1(new Date()));//申请时间字符串
				$("#reqcustomerid").val($("#lcal_hosptail").val());//客户id
				$("#reqsource").val("0");//申请单来源(0手工登记 1第三方系统接收)
				$("#reqplanexectime").val(CurentTime(new Date()));//执行日期时间
				$("#reqpatientid").val(rows[0].key_no);//患者id唯一号(病案号)
				$("#reqinpatientid").val(rows[0].inpatient_id);//就诊id(患者每一次来院的就诊id)
				$("#reqpatbirthday").val(rows[0].patient_birth);//出生日期
				$("#reqisdeleted").val("0");//是否删除(0正常，1已删除)
				$("#reqcreateuser").val($("#local_userid").val());//创建人员
				$("#reqcreatetime").val(CurentTime(new Date()));//创建时间
				if(rows[0].patient_type == 1) {
					$("#reqtype1").attr("checked", true);
					$("[name='ssxx']").css("display", "block");
				}else{
					$("#reqtype1").attr("checked", false);
					$("[name='ssxx']").css("display", "none")
				}
				getdynamicdiv($("#reqpathologyid").val(),0,null);
			}
		});
	}
});

function gettypes1(){
	//动态生成select内容
	var str="";
	$.ajax({
		type:"get",
		async:false,
		url:"../hpinfo/userid",
		dataType: "json",
        data: {
            id : $("#lcal_hosptail").val()//账号所属医院
        },
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
	var reqmmaterialname = "";
	$.ajax({
		type:"post",
		async:false,
		url:"../reqmaterial/info",
		dataType: "json",
		success:function(data){
			if (data != null) {
				reqmmaterialname = data[0].name;
			}
		}
	});
	var dataRow = {
		requisitionid:requisitionid,//申请单ID
		materialid:"",//ID
		reqmsamplingparts:"",//切取部位
		reqmmaterialtype:1,//送检材料
		reqmcustomercode:$("#all_hosptial").val(),//客户id
		reqmmaterialname:reqmmaterialname,//材料名称
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

function CurentTime1(now) {
	//var now = new Date();
	var year = now.getFullYear();       //年
	var month = now.getMonth() + 1;     //月
	var day = now.getDate();            //日
	var hh = now.getHours();            //时
	var mm = now.getMinutes();          //分
	var ss = now.getSeconds();    //秒
	var clock = year;
	if(month < 10)
		clock += "0";
	clock += month;
	if(day < 10)
		clock += "0";
	clock += day;
	return(clock);
}

function isshoushu(){
	if(!$("#reqtype1").is(':checked')){
		$("#reqtype").val("0");
		$("[name='ssxx']").css("display","none");
	}else{
		$("#reqtype").val("1");
		$("[name='ssxx']").css("display","block");
	}
}
function changeSexinfo() {
    if($("#reqpatientsex").val() == "2"){
        $("#sexinfo").css("display","block");
    }else{
        $("#sexinfo").css("display","none");
    }
}
var LODOP; //声明为全局变量
function printCode(){
	$.get("../pimspathology/report/printzqs", {
		"id": $("#reqpathologyid").val(),
        "hosptail":$("#lcal_hosptail").val()==""?"1":$("#lcal_hosptail").val()
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
	});
}
function print(url) {
	LODOP = getLodop();
	LODOP.PRINT_INIT("知情书打印");
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
function printSlide(){
    var id = $("#new").jqGrid('getGridParam', 'selrow');
    var rowData = $("#new").jqGrid('getRowData',id);
    if (id == null || id == 0) {
        layer.msg('请先选择要打印的数据', {icon: 2, time: 1000});
        return;
    }
    $.get("../pimspathology/getpathologyinfo", {"id":rowData.reqpathologyid},
        function(data) {
            if(data.patissampling == 0) {
                $.get("../pimspathology/ajax/getmaterial", {"reqId":rowData.requisitionid},
                    function(data1) {
                        var rows = data1.rows;
						if(rows == undefined){
							layer.msg('该申请单无送检组织!', {icon: 2, time: 1000});
							return;
						}else if(rows.length > 0){
                            printSlideCode(rows,rowData);
                        }else {
                            layer.msg('该申请单无送检组织!', {icon: 2, time: 1000});
                            return;
                        }
                    }
                );
            } else {
                layer.msg('该申请单无送检组织!', {icon: 2, time: 1000});
                return;
            }
        }
    );
}
function printSlideCode(datas,rowdatas) {
    for(i=0;i<datas.length;i++){
        var data = datas[i];
        LODOP = getLodop();
        LODOP.PRINT_INIT("");
        LODOP.SET_PRINT_PAGESIZE(1,"51mm","32mm","A4");
        LODOP.ADD_PRINT_TEXT("2mm","7mm","40mm","4mm","树兰(杭州)医院");
        LODOP.SET_PRINT_STYLEA(0,"FontSize",10);
        LODOP.SET_PRINT_STYLEA(0,"Bold",1);
        LODOP.ADD_PRINT_BARCODE("6mm","2mm","30mm","10mm","128B",rowdatas.requisitionno);
        LODOP.SET_PRINT_STYLEA(0,"Horient",2);
		LODOP.ADD_PRINT_TEXT("17mm","7mm","12mm","5mm","姓名:");
		LODOP.ADD_PRINT_TEXT("17mm","17mm","20mm","5mm",rowdatas.reqpatientname);
		LODOP.ADD_PRINT_TEXT("17mm","32mm","12mm","5mm","性别:");
		var sex = "";
		if(rowdatas.reqpatientsex == 1){
			sex = "男";
		}else if(rowdatas.reqpatientsex == 2){
			sex = "女";
		}
		LODOP.ADD_PRINT_TEXT("17mm","42mm","10mm","5mm",sex);
		// LODOP.ADD_PRINT_TEXT("19mm","35mm","12mm","5mm","年龄:");
		// var agetype = "";
		// if(rowdatas.reqpatagetype == 1){
		// 	agetype = "岁";
		// }else if(rowdatas.reqpatagetype == 2){
		// 	agetype = "月";
		// }else if(rowdatas.reqpatagetype == 4){
		// 	agetype = "周";
		// }else if(rowdatas.reqpatagetype == 5){
		// 	agetype = "日";
		// }else if(rowdatas.reqpatagetype == 6){
		// 	agetype = "小时";
		// }
		// LODOP.ADD_PRINT_TEXT("19mm","43mm","10mm","5mm",rowdatas.reqpatientage+agetype);
        LODOP.ADD_PRINT_TEXT("21mm","7mm","20mm","5mm","取材部位:");
		LODOP.ADD_PRINT_TEXT("21mm","27mm","20mm","5mm",datas[i].reqmsamplingparts);
        // LODOP.ADD_PRINT_TEXTA("nameText","20mm","18mm","17mm","3mm",datas[i].reqmsamplingparts);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.ADD_PRINT_TEXT("25mm","7mm","20mm","5mm","送检材料:");
        LODOP.ADD_PRINT_TEXT("25mm","27mm","20mm","5mm",datas[i].reqmmaterialname);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        //LODOP.PREVIEW();
		LODOP.PRINT();
    }

    // LODOP.PRINT();
}

function printCode1(){
    var id = $("#new").jqGrid('getGridParam', 'selrow');
    var rowData = $("#new").jqGrid('getRowData',id);
    if (id == null || id == 0) {
        layer.msg('请先选择要打印的数据', {icon: 2, time: 1000});
        return;
    }
    $.get("../pimspathology/report/printreq", {
        "id": rowData.requisitionid,
        "hosptail":$("#lcal_hosptail").val()==""?"1":$("#lcal_hosptail").val()
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







var CreatedOKLodop7766=null;

//====判断是否需要安装CLodop云打印服务器:====
function needCLodop(){
    try{
        var ua=navigator.userAgent;
        if (ua.match(/Windows\sPhone/i) !=null) return true;
        if (ua.match(/iPhone|iPod/i) != null) return true;
        if (ua.match(/Android/i) != null) return true;
        if (ua.match(/Edge\D?\d+/i) != null) return true;
        if (ua.match(/QQBrowser/i) != null) return false;
        var verTrident=ua.match(/Trident\D?\d+/i);
        var verIE=ua.match(/MSIE\D?\d+/i);
        var verOPR=ua.match(/OPR\D?\d+/i);
        var verFF=ua.match(/Firefox\D?\d+/i);
        var x64=ua.match(/x64/i);
        if ((verTrident==null)&&(verIE==null)&&(x64!==null))
            return true; else
        if ( verFF !== null) {
            verFF = verFF[0].match(/\d+/);
            if ( verFF[0] >= 42 ) return true;
        } else
        if ( verOPR !== null) {
            verOPR = verOPR[0].match(/\d+/);
            if ( verOPR[0] >= 32 ) return true;
        } else
        if ((verTrident==null)&&(verIE==null)) {
            var verChrome=ua.match(/Chrome\D?\d+/i);
            if ( verChrome !== null ) {
                verChrome = verChrome[0].match(/\d+/);
                if (verChrome[0]>=42) return true;
            };
        };
        return false;
    } catch(err) {return true;};
};

//====页面引用CLodop云打印必须的JS文件：====
if (needCLodop()) {
    var head = document.head || document.getElementsByTagName("head")[0] || document.documentElement;
    var oscript = document.createElement("script");
    oscript.src ="http://localhost:8000/CLodopfuncs.js?priority=1";
    head.insertBefore( oscript,head.firstChild );
    //本机浏览器的后补端口8001：
    oscript = document.createElement("script");
    oscript.src ="http://localhost:8001/CLodopfuncs.js?priority=2";
    head.insertBefore( oscript,head.firstChild );
};

//====获取LODOP对象的主过程：====
function getLodop(oOBJECT,oEMBED){
    var strHtmInstall="<br><font color='#FF00FF'>打印控件未安装!点击这里<a href='install_lodop32.exe' target='_self'>执行安装</a>,安装后请刷新页面或重新进入。</font>";
    var strHtmUpdate="<br><font color='#FF00FF'>打印控件需要升级!点击这里<a href='install_lodop32.exe' target='_self'>执行升级</a>,升级后请重新进入。</font>";
    var strHtm64_Install="<br><font color='#FF00FF'>打印控件未安装!点击这里<a href='install_lodop64.exe' target='_self'>执行安装</a>,安装后请刷新页面或重新进入。</font>";
    var strHtm64_Update="<br><font color='#FF00FF'>打印控件需要升级!点击这里<a href='install_lodop64.exe' target='_self'>执行升级</a>,升级后请重新进入。</font>";
    var strHtmFireFox="<br><br><font color='#FF00FF'>（注意：如曾安装过Lodop旧版附件npActiveXPLugin,请在【工具】->【附加组件】->【扩展】中先卸它）</font>";
    var strHtmChrome="<br><br><font color='#FF00FF'>(如果此前正常，仅因浏览器升级或重安装而出问题，需重新执行以上安装）</font>";
    var strCLodopInstall="<br><font color='#FF00FF'>CLodop云打印服务(localhost本地)未安装启动!点击这里<a href='CLodopPrint_Setup_for_Win32NT.exe' target='_self'>执行安装</a>,安装后请刷新页面。</font>";
    var strCLodopUpdate="<br><font color='#FF00FF'>CLodop云打印服务需升级!点击这里<a href='CLodopPrint_Setup_for_Win32NT.exe' target='_self'>执行升级</a>,升级后请刷新页面。</font>";
    var LODOP;
    try{
        var isIE = (navigator.userAgent.indexOf('MSIE')>=0) || (navigator.userAgent.indexOf('Trident')>=0);
        if (needCLodop()) {
            try{ LODOP=getCLodop();} catch(err) {};
            if (!LODOP && document.readyState!=="complete") {alert("C-Lodop没准备好，请稍后再试！"); return;};
            if (!LODOP) {
                if (isIE) document.write(strCLodopInstall); else
                    document.documentElement.innerHTML=strCLodopInstall+document.documentElement.innerHTML;
                return;
            } else {

                if (CLODOP.CVERSION<"2.0.5.3") {
                    if (isIE) document.write(strCLodopUpdate); else
                        document.documentElement.innerHTML=strCLodopUpdate+document.documentElement.innerHTML;
                };
                if (oEMBED && oEMBED.parentNode) oEMBED.parentNode.removeChild(oEMBED);
                if (oOBJECT && oOBJECT.parentNode) oOBJECT.parentNode.removeChild(oOBJECT);
            };
        } else {
            var is64IE  = isIE && (navigator.userAgent.indexOf('x64')>=0);
            //=====如果页面有Lodop就直接使用，没有则新建:==========
            if (oOBJECT!=undefined || oEMBED!=undefined) {
                if (isIE) LODOP=oOBJECT; else  LODOP=oEMBED;
            } else if (CreatedOKLodop7766==null){
                LODOP=document.createElement("object");
                LODOP.setAttribute("width",0);
                LODOP.setAttribute("height",0);
                LODOP.setAttribute("style","position:absolute;left:0px;top:-100px;width:0px;height:0px;");
                if (isIE) LODOP.setAttribute("classid","clsid:2105C259-1E0C-4534-8141-A753534CB4CA");
                else LODOP.setAttribute("type","application/x-print-lodop");
                document.documentElement.appendChild(LODOP);
                CreatedOKLodop7766=LODOP;
            } else LODOP=CreatedOKLodop7766;
            //=====Lodop插件未安装时提示下载地址:==========
            if ((LODOP==null)||(typeof(LODOP.VERSION)=="undefined")) {
                if (navigator.userAgent.indexOf('Chrome')>=0)
                    document.documentElement.innerHTML=strHtmChrome+document.documentElement.innerHTML;
                if (navigator.userAgent.indexOf('Firefox')>=0)
                    document.documentElement.innerHTML=strHtmFireFox+document.documentElement.innerHTML;
                if (is64IE) document.write(strHtm64_Install); else
                if (isIE)   document.write(strHtmInstall);    else
                    document.documentElement.innerHTML=strHtmInstall+document.documentElement.innerHTML;
                return LODOP;
            };
        };
        if (LODOP.VERSION<"6.2.0.4") {
            if (needCLodop())
                document.documentElement.innerHTML=strCLodopUpdate+document.documentElement.innerHTML; else
            if (is64IE) document.write(strHtm64_Update); else
            if (isIE) document.write(strHtmUpdate); else
                document.documentElement.innerHTML=strHtmUpdate+document.documentElement.innerHTML;
            return LODOP;
        };
        //===如下空白位置适合调用统一功能(如注册语句、语言选择等):===
        LODOP.SET_LICENSES("杭州同烁信息技术有限公司", "BD01DF8932E17DF1666BD1E7F683CA3B", "杭州同爍信息技術有限公司", "66C03A83361CEA25AAC8D4206D199A67");
        LODOP.SET_LICENSES("THIRD LICENSE", "", "Tongshuo Information Technology Co.,Ltd.", "FA66F8318320BC028F60D106EB94691C");
        //===========================================================
        return LODOP;
    } catch(err) {alert("getLodop出错:"+err);};
};


