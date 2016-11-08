
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
	var post = true;
	if(post) {
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
			{ name: 'piesamplingtime', index: 'piesamplingtime',formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))},width:100}
		],
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		viewrecords: true,
		//height:300,
		 width:650,
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


