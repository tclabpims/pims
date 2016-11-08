/**
 * 会诊管理
 */
function consMarage() {
	//判断该病理是否已经发起了会诊
	var sampleid = $("#sampleid").val();
	if(sampleid == null || sampleid == ""){
		alert("请选择病理标本再发起会诊!");
		return;
	}
	$.post("../consultation/cons/isexist", {
			id:sampleid
		},
		function(data) {
			if(data.success) {//已发起过会诊
				location.href='../consultation/cons.jsp?id='+ sampleid;
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
					content: $('#userGrid'),
					btn:["保存","取消"],
					yes: function(index, layero){
						//var id = $('#sectionList3').jqGrid('getGridParam','selrow');
						var selectedIds = $("#sectionList3").jqGrid("getGridParam","selarrrow");
						var arr = new Array();
						if(selectedIds.length > 0){
							$(selectedIds).each(function () {
									var rowData1 = $("#sectionList3").jqGrid('getRowData',this.toString());
									arr.push(rowData1);
								}
							);
							$.post("../consultation/cons/addCons", {
									userlist:JSON.stringify(arr),
									sampleid:sampleid,
									samcustomerid:$("#samcustomerid").val(),
									sampathologycode:$("#sampathologycode").val()
								},
								function(data) {
									if(data.success) {
										layer.close(index);
										location.href='../consultation/cons.jsp?id='+ sampleid;
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
$(function () {
	$("#sectionList3").jqGrid({
		caption: "用户列表",
		url: "../pimsuser/userlist",
		mtype: "GET",
		datatype: "json",
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
		multiselect: true,
		viewrecords: true,
		shrinkToFit: true,
		altRows:true,
		height: 'auto',
		rowNum: 10,
		rowList:[10,20,30],
		rownumbers: true, // 显示行号
		rownumWidth: 35, // the width of the row numbers columns
		pager: "#pager3",
		onSelectRow: function(id){

		}
	});
});