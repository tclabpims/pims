$(function() {
	//表单校验
	$("#comsponsoredtime").val("");
	$("#confinishedtime").val("");
	$("#logyids").val("");
	$("#sampathologyid").val("");
	$("#sampatientname").val("");
	$("#samsendhospitalid").val("");
	$("#hospitalname").val("");
	$("#samsendhospital").val("");
	$("#samdeptcode").val("");
	$("#samdeptname1").val("");
	$("#samdeptname").val("");
	$("#samsenddoctorid1").val("");
	$("#samsenddoctorname").val("");
	$("#samsenddoctorname1").val("");
	$("#conconsultationstate").val("");
	$("#samsenddoctorid2").val("");
	$("#consponsoredusername").val("");
	$("#samsenddoctorname2").val("");
	$("#samsenddoctorid3").val("");
	$("#detdoctorname").val("");
	$("#samsenddoctorname3").val("");
	$("#remarks").val("");


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

    $("#samsendhospital").autocomplete({
    		source: function( request, response ) {
    			$.ajax({
    				url: "../basadata/ajax/item",
    				dataType: "json",
    				data: {
    					name : request.term,//名称
    					bddatatype:4,//送检医院
    //					bdcustomerid:$("#lcal_hosptail").val()//账号所属医院
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
    			//return false;
    		}
    	}).data("ui-autocomplete")._renderItem = function( ul, item ) {
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
//					bdcustomerid:$("#lcal_hosptail").val()//账号所属医院
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
	}).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li>" )
			.append( "<a style='font-size:12px;font-family: 微软雅黑;'>" + item.id + "," + item.value+ "</a>" )
			.appendTo( ul );
	};
	//送检医生
	$("#samsenddoctorname1").autocomplete({
		source: function( request, response ) {
			$.ajax({
                    url: "../basadata/ajax/item",
				dataType: "json",
				data: {
					name : request.term,//名称
					bddatatype:3,//送检医生
//					bdcustomerid:$("#lcal_hosptail").val()//账号所属医院
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
	}).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li>" )
			.append( "<a style='font-size:12px;font-family: 微软雅黑;'>" + item.id + "," + item.value+ "</a>" )
			.appendTo( ul );
	};

	$("#samsenddoctorname2").autocomplete({
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
    $("#samsenddoctorname3").autocomplete({
            source: function( request, response ) {
                $.ajax({
                        url: "../basadata/ajax/item",
                    dataType: "json",
                    data: {
                        name : request.term,//名称
                        bddatatype:3,//送检医生
//                        bdcustomerid:$("#lcal_hosptail").val()//账号所属医院
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
	$("#slicode_bro").css("width",850);
	var width = $("#checkSlide2").width()-20;
	var width1 = $("#checkSlide2").width();
	var width3=$("#div_main").width()-890;
	$("#diagnosis_result").css("width",width3);
	//var logyid = $("#logyid").val();
	$("#new").jqGrid({
		//caption:"标本列表",
        url:"../report/consultation/ajax/slide",
		mtype: "GET",
		datatype: "json",
//		postData:{"sli_code":sli_code,"customer_name":customer_name,"patient_name":patient_name,
//			"sli_in_time":sli_in_time,"sliid":sliid,"current":current},
		colNames: ['sampleid','会诊状态','病种类别','病理编号', '患者姓名','年龄','性别','病理状态','送检单位','送检科室','送检医生','审核医生','审核时间','发起时间','完成时间'],
		colModel: [
        { name: 'sampleid', align:'center',hidden:true,index: 'sampleid',width:'90px'},
        { name: 'conconsultationstate', align:'center',index: 'conconsultationstate',width:'90px',formatter:"select",editoptions:{value:"0:会诊中;1:会诊终了;2:会诊取消;"}},
        { name: 'patnamech', align:'center',index: 'patnamech',width:'110px'},
        { name: 'sampathologycode', align:'center',index: 'sampathologycode',width:'110px'},
        { name: 'sampatientname',align:'center', index: 'sampatientname',width:'110px'},
        { name: 'sampatientage',align:'center', index: 'sampatientage',width:'50px'},
        { name: 'sampatientsex',align:'center', index: 'sampatientsex',width:'50px',formatter:"select",editoptions:{value:"0:女;1:男;"}},
        { name: 'samsamplestatus',align:'center', index: 'samsamplestatus',width:'70px'},
        { name: 'samsendhospital',align:'center', index: 'samsendhospital',width:'110px'},
        { name: 'samdeptname',align:'center', index: 'samdeptname',width:'110px'},
        { name: 'samsenddoctorname',align:'center', index: 'samsenddoctorname',width:'110px'},
//        { name: 'restestresult',align:'center',hidden:true,index: 'restestresult',width:'110px'},
        { name: 'samauditer',align:'center', index: 'samsenddoctorname',width:'110px'},
        { name: 'samauditedtime',align:'center',hidden:true, index: 'samsenddoctorname',width:'110px',formatter:function(cellvalue,options,row){if(cellvalue!=null){return CurentTime(new Date(cellvalue));}return '';}},
        { name: 'consponsoredtime',align:'center',hidden:true, index: 'samsenddoctorname',width:'110px',formatter:function(cellvalue,options,row){if(cellvalue!=null){return CurentTime(new Date(cellvalue));}return '';}},
        { name: 'confinishedtime',align:'center',hidden:true, index: 'samsenddoctorname',width:'110px',formatter:function(cellvalue,options,row){if(cellvalue!=null){return CurentTime(new Date(cellvalue));}return '';}},
        ],
		beforeSelectRow: function (rowid, e) {
			return $(e.target).is('input[type=checkbox]');},
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

		},
		ondblClickRow: function (id) {
			fillInfo1(id);

		},
		onCellSelect:function(id){
			fillInfo1(id);
		},
		multiselect: true,   //默认选中
		viewrecords: true,
		height:390,
		width:width,
		shrinkToFit:false,
		autoScroll: true,
		rownumbers: true, // 显示行号
		rowNum: 10,
		rowList:[10,20,30],
		rownumWidth: 35, // the width of the row numbers columns
		pager: "#pager"
	});
	$("#pager_left").remove();
    createNew1();






});

function getSearchList(){

    var consponsoredtime=$("#consponsoredtime").val();
    var confinishedtime=$("#confinishedtime").val();
    var logyids=$("#logyids").val();
    var sampathologycode=$("#sampathologycode").val();
    var sampatientname=$("#sampatientname").val();
    var samsendhospital=$("#samsendhospital").val();
    var samdeptname=$("#samdeptname").val();
    var samsenddoctorname=$("#samsenddoctorname1").val();
    var conconsultationstate=$("#conconsultationstate").val();
    var consponsoredusername=$("#samsenddoctorname2").val();
    var confinisheduserid=$("#detdoctorname").val();
    var remarks=$("#remarks").val();
//    var =$("#").val;
//    var =$("#").val;
//    var =$("#").val;
//    var =$("#").val;
    jQuery("#new").jqGrid("clearGridData");
    jQuery("#new").jqGrid("setGridParam",{
    url:"../report/consultation/ajax/slide",
    mtype: "GET",
    datatype: "json",
    postData:{"consponsoredtime":consponsoredtime,
              "confinishedtime":confinishedtime,
              "logyids":logyids,
              "sampathologycode":sampathologycode,
              "sampatientname":sampatientname,
              "samsendhospital":samsendhospital,
              "samdeptcode":samdeptname,
              "samsenddoctorname":samsenddoctorname,
              "conconsultationstate":conconsultationstate,
              "consponsoredusername":consponsoredusername,
              "confinisheduserid":confinisheduserid,
              "remarks":remarks
             },
             page:1
    }).trigger('reloadGrid');
}

function fillval(id,name,obj) {
	var optiontext = $("#"+obj).find("option:selected").text();
	var string1 = optiontext.substring(0,optiontext.indexOf(':'));
	var string2 = optiontext.substr(optiontext.indexOf(':')+1);
//	alert(string1);
//    alert(string2);
	$("#"+id).val(string1);
	$("#"+name).val(string2);
	$("#"+name).focus();
}

function show(o){
    for(var i = 1;i<=6;i++){
        $("#page"+i).css("display","none");
    }
    $("#page"+o).css("display","block");
}


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

function fillInfo1(id){
	setcolor(id);
	nowrow = id;
	clearData();
	var rowData = $("#new").jqGrid('getRowData',id);
	var idss=rowData.sampleid;
//	if (id == null || id == 0) {
//		layer.msg('请先选择数据', {icon: 2, time: 1000});
//		return false;
//	}
	fill(rowData);
	getItem(idss);
}

function createNew1(rowData){

    $("#new1").jqGrid({
        //caption:"材块列表",
//        url: "../report/consultation/ajax/piece",
//        mtype: "POST",
        datatype: "json",
//        postData:{"pathologyid":pathologycode},
        colNames: ['病理号','序号','材块数','取材部位','取材医生','录入员','取材时间'],
        colModel: [
            { name: 'piepathologycode',align:'center',index: 'piepathologycode',width:100},
            { name: 'piesamplingno',align:'center',index: 'piesamplingno',width:50},
            { name: 'piecounts',align:'center',index: 'piecounts',width:50},
            { name: 'pieparts',align:'center',index: 'pieparts',width:75},
            { name: 'piedoctorname',align:'center',index: 'piedoctorname',width:75},
            { name: 'pierecordername',align:'center',index: 'pierecordername',width:75},
            { name: 'piesamplingtime',align:'center',index: 'piesamplingtime',formatter:function(cellvalue, options, row){return CurentTime(new Date(cellvalue))},width:100}
        ],
        loadComplete : function() {
            var table = this;
            setTimeout(function(){
                updatePagerIcons(table);
            }, 0);
        },
        viewrecords: true,
        height:150,
         width:750,
//        autowidth: true,
//         shrinkToFit:false,
         autoScroll: true,
        // rowNum: 10,
        // rowList:[10,20,30],
        rownumbers: true, // 显示行号
        // rownumWidth: 10, // the width of the row numbers columns
         pager: "#pager2"
    });

    for(var i = 37;i<=39;i++){
        $("#Item"+i).jqGrid({
//            mtype: "POST",
//            url: "../report/consultation/ajax/getItem",
            datatype: "json",
//            postData:{"id":i,
//                      "sampleid":sampleid,
//            },
            colNames: ['checkid', '蜡块编号', '项目名称', '结果', '申请医生', '状态', '申请时间', 'chenameen', 'cheischarge'],
            colModel: [
                {name: 'checkid', index: 'checkid', hidden: true},
                {name: 'paraffincode', index: 'paraffincode', width: 120},
                {name: 'chenamech', index: 'chenamech', width: 120},
                {
                    name: 'chetestresult',
                    index: 'chetestresult',
                    width: 120,
                    editable: true,
                    edittype: 'text',
                    editrules: {edithidden: true, required: true}
                },
                {name: 'checreateuser', index: 'checreateuser', width: 80},
                {
                    name: 'finishstatus',
                    index: 'finishstatus',
                    width: 60,
                    formatter: "select",
                    editoptions: {value: "0:未完成;1:已完成;"}
                },
                {name: 'checreatetime', index: 'checreatetime', width: 100,formatter:function(cellvalue,options,row){if(cellvalue!=null){return CurentTime(new Date(cellvalue));}return '';}},
                {name: 'chenameen', index: 'chenameen', hidden: true},
                {name: 'cheischarge', index: 'cheischarge', hidden: true}
            ],
            multiselect: true,
            shrinkToFit: true,
            scrollOffset: 2,
            rownumbers: true,
            height:150,
            width:750,

        });
    }
}

function fill(rowData){
//    console.log(rowData);
    var sampleid = rowData.sampleid;
    var pathologycode = rowData.sampathologycode;
    var id=rowData.sampleid;
//    console.log(pathologycode);
    var sex="";
    if(rowData.sampatientsex=='1'){
        sex="男";
    }else{
        sex="女";
    }

    $("#jbxx1").val("病理编号:"+rowData.sampathologycode+";   患者姓名:"+rowData.sampatientname+";   性别:"+sex+";   年龄:"+rowData.sampatientage+"岁;   审核医生:"+rowData.samauditer+";   审核时间:"+rowData.samauditedtime);

    $.ajax({
        type:"post",
        async:false,
        url: "../report/consultation/ajax/getResult",
        data: {
            id:id
        },
        success: function(data) {
            $("#blzd1").val(data);
        }
    });

    jQuery("#new1").jqGrid("clearGridData");
    jQuery("#new1").jqGrid('setGridParam',{
        url: "../report/consultation/ajax/piece",
        mtype: "POST",
        postData:{"pathologyid":pathologycode},
        //发送数据
        page : 1
    }).trigger('reloadGrid');//重新载入

    for(var i =37;i<=39;i++){
        jQuery("#Item"+i).jqGrid("clearGridData");
        jQuery("#Item"+i).jqGrid('setGridParam',{
                url: "../report/consultation/ajax/getItem",
                mtype: "POST",
                postData:{
                "id":i,
                "sampleid":sampleid},
                //发送数据
                page : 1
        }).trigger('reloadGrid');//重新载入
    }


    var html="";
    $.ajax({
        type:"POST",
        async:false,
        url:"../report/consultation/ajax/getreqinfo",
        dataType: "json",
        data:{"pathologycode":pathologycode},
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
                        html +="&nbsp;&nbsp;<textarea id='text_"+i+"' style='overflow-y:visible;height:50px;font-size:12px;width: 100%'>"+data[i].detadvice+ "</textarea>&nbsp;&nbsp;</div>";
                    }
                }
            }
        }
    });
        $("#page1").html(html);

}

function setcolor(id){
	var ids = $("#new").getDataIDs();
	$.each(ids, function (key, val) {
		$("#new").children().children("tr[id='"+ids[key]+"']").removeClass("ui-state-highlight");
	});
	$("#new").children().children("tr[id='"+id+"']").addClass("ui-state-highlight");
}

function clearData() {
    $('#sampleForm')[0].reset();
}

function getItem(sampleid){
//    alert(sampleid);
    for(var i=37;i<=39;i++){
        $.ajax({
            type:"post",
            async:false,
            url:"../report/consultation/ajax/getItem",
            data:{id:i,
                  sampleid:sampleid
            },
            success:function(data){
            var row = data.rows;
            if(row==null&&i==37){
                $("#ap4").css("display","none");
            	return;
            }else if(row!=null&&i==37){
//                console.log(row[0].chenamech);
				$("#ap4").css("display","block");
            }
			if(row==null&&i==38){
				$("#ap6").css("display","none");
				return;
			}else if(row!=null&&i==38){
//                console.log(row[0].chenamech);
				$("#ap6").css("display","block");
			}
				var a = "";
				for(var j=0;j<row.length;j++){
					a +=row[j].chenamech+",";
				}
            	$("#item"+i).val(a);

            }
    });
    }
}

function daochu() {
    var consponsoredtime=$("#consponsoredtime").val();
    var confinishedtime=$("#confinishedtime").val();
    var logyids=$("#logyids").val();
    var sampathologycode=$("#sampathologycode").val();
    var sampatientname=$("#sampatientname").val();
    var samsendhospital=$("#samsendhospital").val();
    var samdeptname=$("#samdeptname").val();
    var samsenddoctorname=$("#samsenddoctorname1").val();
    var conconsultationstate=$("#conconsultationstate").val();
    var consponsoredusername=$("#samsenddoctorname2").val();
    var confinisheduserid=$("#detdoctorname").val();
    var remarks=$("#remarks").val();
    var array =[];
    $('input[name="xx"]:checked').each(function(){
        array.push($(this).val());
    });
	// alert(array);
    window.location.href = "../report/consultation/dc?consponsoredtime"+consponsoredtime+
	"&confinishedtime"+confinishedtime+"&logyids"+logyids+"&sampathologycode"+sampathologycode+
	"&sampatientname"+sampatientname+"&samsendhospital"+samsendhospital+"&samdeptname"+samdeptname+
	"&samsenddoctorname"+samsenddoctorname+"&conconsultationstate"+conconsultationstate+
	"&consponsoredusername"+consponsoredusername+"&confinisheduserid"+confinisheduserid+
	"&remarks"+remarks+"&array"+array.join();
}

function setrow() {
    layer.open({
        type: 1,
        area: ['300px','420px'],
        fix: false, //不固定
        maxmin: false,
        shade:0.6,
        title: "借阅",
        btn:["保存","取消"],
        content: $('#setrow')
    });
}