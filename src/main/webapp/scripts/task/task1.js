/**
 * 抄送管理
 */
function csMarage(states) {
	var result = true;
	//判断该病理是否已经发起了抄送
	var selectedIds = $("#sectionList").jqGrid("getGridParam","selarrrow");
	// var sampleid = $("#sampleid").val();
	var arr = new Array();
	if(selectedIds.length > 0){
		$(selectedIds).each(function () {
				var rowData1 = $("#sectionList").jqGrid('getRowData',this.toString());
				if(rowData1.sampathologystatus != 2 && states == 0){//抄送
					layer.msg("只有初查的标本才允许抄送!",{icon:2,time:1000});
					result = false;
					return;
				}
				if(rowData1.sampathologystatus > 1 && states == 1){//转送
					layer.msg("只有未初查的标本才允许转送!",{icon:2,time:1000});
					result = false;
					return;
				}
				arr.push(rowData1);
			}
		);
	}else{
		var rowData = $("#sectionList").jqGrid('getRowData', crno);
		if(rowData == null || rowData ==""){
			result = false;
			if(states == 0){
				layer.msg("请选择病理标本再发起抄送!",{icon:2,time:1000});
			}else if(states == 1){
				layer.msg("请选择病理标本再发起转送!",{icon:2,time:1000});
			}
			return;
		}
		if(rowData.sampathologystatus != 2 && states == 0){//抄送
			layer.msg("只有初查的标本才允许抄送!",{icon:2,time:1000});
			result = false;
			return;
		}
		if(rowData.sampathologystatus > 1 && states == 1){//转送
			layer.msg("只有未初查的标本才允许转送!",{icon:2,time:1000});
			result = false;
			return;
		}
			arr.push(rowData);


	}
	var states = states;
	// if(sampleid == null || sampleid == ""){
	// 	alert("请选择病理标本再发起抄送!");
	// 	return;
	// }
	if(result){
		$.post("../task/task/isExistsTask", {
				tasks:JSON.stringify(arr),
				states:states
			},
			function(data) {
				if(data.success) {//已发起过抄送
					if(states == 0){
						layer.msg("标本不允许发起多次抄送!", {icon: 2,time: 1000});

					}else if(states == 1){
						layer.msg("标本不允许发起多次转送!", {icon: 2,time: 1000});

					}
					// layer.msg(data.message, {icon: 2,time: 1000});
					return false;
					//location.href='../consultation/cons.jsp?id='+ sampleid;
				} else {
					//弹出用户选择窗口选择用户
					layer.open({
						type: 1,
						area: ['800px','500px'],
						fix: false, //不固定
						maxmin: true,
						multiselect: true,
						rownumbers : true,
						shade:0.5,
						title: "用户列表",
						content: $('#userGrid1'),
						btn:["保存","取消"],
						yes: function(index, layero){
							var id = $('#sectionList4').jqGrid('getGridParam','selrow');
							var rowData = $("#sectionList4").jqGrid('getRowData',id);
							// var rowData1 = $("#sectionList3").jqGrid('getRowData',this.toString());
							// var selectedIds = $("#sectionList4").jqGrid("getGridParam","selarrrow");
							// var arr = new Array();
							if(id > 0){
								// $(selectedIds).each(function () {
								// 		var rowData1 = $("#sectionList3").jqGrid('getRowData',this.toString());
								// 		arr.push(rowData1);
								// 	}
								// );
								$.post("../task/task/addTasks", {
										states:states,
										userid:rowData.id,
										username:rowData.name,
										// userlist:JSON.stringify(arr),
										tasks:JSON.stringify(arr)
										// samcustomerid:$("#samcustomerid").val(),
										// sampathologycode:$("#sampathologycode").val()
									},
									function(data) {
										if(data.success) {
											layer.close(index);
											// layer.msg(data.message, {icon:1, time: 1000});
											if(states == 0){
												layer.msg("发起抄送成功!", {icon: 2,time: 1000});

											}else if(states == 1){
												layer.msg("发起转送成功!", {icon: 2,time: 1000});

											}
											// location.href='../consultation/cons.jsp?id='+ sampleid;
										} else {
											layer.msg(data.message, {icon:2, time: 1000});
										}
									});
							}else{
								layer.msg('请先选择用户', {icon: 2,time: 1000});
								return false;
							}

						}
					})
				}
			});
	}

}
$(function () {
	$("#sectionList4").jqGrid({
		caption: "用户列表",
		url: "../pimsuser/userlist",
		mtype: "GET",
		datatype: "json",
		postData:{"states":0},
		width:800,
		height:550,
		colNames: ['id','姓名', '登录账号'],
		colModel: [
			{ name: 'id', index: 'id', width: 30, hidden: true },
			{ name: 'name', index: 'name', width: 30},
			{ name: 'username', index: 'phone', width: 60}
		],
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		ondblClickRow: function (id) {
		},
		// multiselect: true,
		viewrecords: true,
		shrinkToFit: true,
		altRows:true,
		height: 'auto',
        rowNum: 100,
        rowList:[100,200,300,400,500],
		rownumbers: true, // 显示行号
		rownumWidth: 35, // the width of the row numbers columns
		pager: "#pager4",
		onSelectRow: function(id){

		}
	});
});