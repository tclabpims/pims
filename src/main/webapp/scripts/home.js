/**
 * 病种类别
 * @returns {string}
 */
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
 * 初始化
 */
$(function () {
    $("#new").jqGrid({
        url: "../pathologysample/sample/ajax/sample",
        mtype: "GET",
        datatype: "json",
        postData:{"req_code":"3"},
        colNames: ['ID','详细', '病种类型','病理号', '送检医生','送检医院','病人姓名'],
        colModel: [
            {name:'requisitionid',hidden:true},//ID
            { name: 'showinfo', index: 'showinfo', sortable: false, align: "center", width: "70px" },//详细
            { name: 'sampathologyid', index: 'sampathologyid',formatter: "select", editoptions:{value:gettypes1()},align:"center",width:'100px'},//病种类型
            { name: 'sampathologycode', index: 'sampathologycode',width:'100px', align: "center"},//病理号
            { name: 'samsenddoctorname', index: 'samsenddoctorname',width:'100px', align: "center"},//送检医生
            { name: 'samsendhospital', index: 'samsendhospital',width:'100px', align: "center"},//送检医院
            { name: 'sampatientname', index: 'sampatientname',width:'100px', align: "center"}//病人姓名
        ],
        gridComplete: function () {
            var ids = jQuery("#new").jqGrid('getDataIDs');
            for (var i = 0; i < ids.length; i++) {
                var id = ids[i];
                var showinfo = "<button style='border-radius:5px;border:1px solid #C2C2C2;' onclick='showInfo("+id+")' >详情</button>";
                jQuery("#new").jqGrid('setRowData', ids[i], { showinfo: showinfo });
            }
        },
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
        height:300,
        autowidth: true,
        rowNum: 10,
        rowList:[10,20,30],
        rownumbers: true, // 显示行号
        rownumWidth: 35, // the width of the row numbers columns
        pager: "#pager"
    });

    // $('#tabs a').click(function (e) {
    //     var hrefval = $(this).attr("href");
    //     $("#req_sts").val(hrefval);
    //     searchList();
    //     e.preventDefault();
    // });
});