function stuInfo(id,name){
	jQuery("#hisList").jqGrid("clearGridData");
	jQuery("#hisList").jqGrid("setGridParam",{url:"../pb/sxpb/hisdata?id="+id}).setCaption(name+"  排班信息").trigger("reloadGrid");
}

function sectionInfo(week,year){
	jQuery("#sectionCount").jqGrid("clearGridData");
	jQuery("#sectionCount").jqGrid("setGridParam",{url:"../pb/sxpb/sectionCount?year="+year+"&week="+week}).setCaption("第"+week+"周  科室排班数量统计").trigger("reloadGrid");
}

function getHisList(id){
	jQuery("#hisList").jqGrid({
		url:"../pb/sxpb/hisdata?id="+id,
		datatype:"json",
		colNames:['科室','次数','学校规章'],
		colModel:[
		          {name:'section',index:'section',width:80,sortable:false},
		          {name:'num',index:'num',width:40,sortable:false},
		          {name:'schoolnum',index:'schoolnum',width:40,sortable:false}
		          ],
		rowNum:20,
		height:'100%',
		rownumbers:true,
		caption : "学生排班统计",
		viewrecords:true,
		jsonReader:{repeatitems:false},
		mtype:"GET",
		pager:'#pager',
		
	}).trigger("reloadGrid");
	jQuery("#hisList").jqGrid('navGrid','#pager',{edit:false,add:false,del:false,search:false,refresh:false});
}


function getSectionCount(week){
	jQuery("#sectionCount").jqGrid({
		url:"../pb/sxpb/sectionCount?year=2016&week="+week,
		datatype:"json",
		colNames:['生化室','免疫室','分子诊断','急诊','病房','门诊','内分泌','细菌室','骨髓室','输血科','手工生化','病理','术前四项','血液科','感染科','科研论文','实习机动','急诊科'],
		colModel:[
		          {name:'213',index:'sh',width:40,sortable:false},
		          {name:'214',index:'my',width:40,sortable:false},
		          {name:'215',index:'fz',width:40,sortable:false},
		          {name:'216',index:'jz',width:40,sortable:false},
		          {name:'217',index:'bf',width:40,sortable:false},
		          {name:'218',index:'mz',width:40,sortable:false},
		          {name:'220',index:'nfm',width:40,sortable:false},
		          {name:'219',index:'xj',width:40,sortable:false},
		          {name:'298',index:'gs',width:40,sortable:false},
		          {name:'299',index:'sx',width:40,sortable:false},
		          {name:'301',index:'sgsh',width:40,sortable:false},
		          {name:'302',index:'bl',width:40,sortable:false},
		          {name:'303',index:'sh',width:40,sortable:false},
		          {name:'304',index:'sh',width:40,sortable:false},
		          {name:'305',index:'sh',width:40,sortable:false},
		          {name:'306',index:'sh',width:40,sortable:false},
		          {name:'307',index:'sh',width:40,sortable:false},
		          {name:'314',index:'sh',width:40,sortable:false}
		          ],
		rowNum:20,
		width: $("#sectiondataPanal").width()*0.9,
		height:'100%',
		rownumbers:true,
		caption : "第"+week+"周 科室排班数量统计",
		jsonReader:{repeatitems:false},
		mtype:"GET",
//		pager:'#sectionPager',
		
	}).trigger("reloadGrid");
//	jQuery("#sectionCount").jqGrid('navGrid','#sectionPager',{edit:false,add:false,del:false,search:false,refresh:false});
}



$(function() {
	$("#pbdata").html($("#test1").val());
//	$("#date").val($("#month").val());
	$("#pagelist").html("第 "+$("#page").val()+" 页，共 "+$("#pages").val()+" 页");
	
	
	$("#date").datepicker({
		changeMonth: true,
	    changeYear: true,
		dateFormat: 'yy-mm',
		monthNamesShort: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
		dayNamesMin: ['日','一','二','三','四','五','六'],
		showButtonPanel: true, 
		onClose: function(dateText, inst) { 
		var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val(); 
		var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val(); 
		$(this).datepicker('setDate', new Date(year, month, 1)); 
		} 
	});
	
	$( "#from" ).datepicker({
		changeMonth: true,
		changeYear: true,
		dateFormat:"yy-mm",
		monthNamesShort: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
		dayNamesMin: ['日','一','二','三','四','五','六'],
		showButtonPanel: true, 
		onClose: function(dateText, inst) { 
		var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val(); 
		var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val(); 
		$(this).datepicker('setDate', new Date(year, month, 1)); 
//		$( "#to" ).datepicker( "option", "minDate", new Date(year, month, 1) );
		} 
		
	});
	$( "#to" ).datepicker({
		changeMonth: true,
		changeYear: true,
		dateFormat:"yy-mm",
		monthNamesShort: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
		dayNamesMin: ['日','一','二','三','四','五','六'],
		showButtonPanel: true, 
		onClose: function(dateText, inst) { 
			var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val(); 
			var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val(); 
			$(this).datepicker('setDate', new Date(year, month, 1));
//			$( "#from" ).datepicker( "option", "maxDate", new Date(year, month, 1) );
		} 
	});
	if($("#from").val()==null){
		$( "#from" ).val(new Date().Format("yyyy-MM"));
		$( "#to" ).val(new Date().Format("yyyy-MM"));
	}
	
	
	$("#changeMonth").click(function() {
		window.location.href="../pb/sxpb?from=" + $("#from").val()+"&to="+$("#to").val();
	});
	
	$("#ksCount").click(function() {
		window.open("../pb/kscount?from=" + $("#from").val()+"&to="+$("#to").val());
	});
	
	var page = $("#page").val();
	var pages = $("#pages").val();
	if(page==1){
		$("#preBtn").addClass("disabled");
	}
	if(page>=pages){
		$("#nextBtn").addClass("disabled");
	}
	$("#preBtn").click(function() {
		if(page!=1)
			window.location.href="../pb/sxpb?from=" + $("#from").val()+"&to="+$("#to").val()+"&page="+(--page)+"&week="+($("#week").val());
	});
	$("#nextBtn").click(function() {
		if(pages!=1)
			window.location.href="../pb/sxpb?from=" + $("#from").val()+"&to="+$("#to").val()+"&page="+(++page)+"&week="+($("#week").val());
	});
	
	
	$("#pbdata tr td").click(function(){
		var id=this.id;
		var shifts=$("#"+id).html();
		
		
		$.each($("#shiftSelect input"),function(name,v){
			if(v.checked){
				if(shifts.indexOf(v.value)>=0){
					shifts=shifts.replace(v.value,"");
				}else{
					shifts = shifts + v.value;
				}
			}
		});
		$("#"+id).html(shifts);
	});
	
	$("#pbdata tr th").click(function(){
		var id=this.id;
		var name = $(this).attr("name");
		if(name.indexOf("week")>=0){
			var item = $(this).parent();

			if($(item).hasClass("yellow"))
				$(item).removeClass("yellow");
			else
				$(item).addClass("yellow");
//			var color = $(item).css("background-color");
//			alert(color);
			
//			if(color=="rgb(135,206,250)")
//				$(item).css("background-color","");
//			else
//				$(item).css("background-color","yellow");
		}
		
	});
	
	
	
	$("#shiftBtn").click(function() {
		var ischecked = true;
		if (ischecked) {
			var text = "";
			var from = $("#from").val();
			var to = $("#to").val();
			$("td[name^='td']").each(function(i){
				var array = $(this).attr("id").split("-");
				var day = "";
				day = array[1];
				var month = array[2];
				var value = $(this).html();
				text = text + array[0] + ":" + month + ":" + day + ":" + value  +",";
			});
			
			$.post("../pb/sxpb/submit",{text:text,from:from,to:to},function(data) {
				alert("success!");
				window.location.href="../pb/sxpb?from=" + $("#from").val()+"&to="+$("#to").val()+"&page="+page;
			});
			
		}
	});
	
	getHisList("");
	getSectionCount($("#week").val());
});

Date.prototype.Format = function(fmt)   
{ //author: meizz   
  var o = {   
    "M+" : this.getMonth()+1,                 //月份   
    "d+" : this.getDate(),                    //日   
    "h+" : this.getHours(),                   //小时   
    "m+" : this.getMinutes(),                 //分   
    "s+" : this.getSeconds(),                 //秒   
    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
    "S"  : this.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
};