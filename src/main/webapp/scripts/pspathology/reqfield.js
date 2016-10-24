/**
 * Created by lenovo on 2016/10/6.
 */

/************************************
 *  添加病种
 *  add by zcw 2015-05-16
 * **********************************/
function AddSection() {
    clearData();
    var treeObj = $.fn.zTree.getZTreeObj("tree");
    var nodes = treeObj.getSelectedNodes();
    if (!nodes || nodes.length == 0) {
        layer.msg('请先选择节点', {icon: 2, time: 1000});
        return false;
    }
    $('#fiepelementid').val(nodes[0].id);
    $('#fieldid').val('');
}

function saveFieldData() {
    var treeObj = $.fn.zTree.getZTreeObj("tree");
    var nodes = treeObj.getSelectedNodes();
    if (!nodes || nodes.length == 0) {
        layer.msg('请先选择节点', {icon: 2, time: 1000});
        return false;
    }
    $('#fiepelementid').val(nodes[0].id);
    if ($('#fieelementid').val() == '' || $('#fieelementname').val() == '' || $('#fieshowlevel').val() == ''
        || $('#fiepelementid').val() == '' || $('#fieshoworder').val() == '') {
        layer.msg('信息填写不完整', {icon: 2, time: 1000});
        return false;
    }
    if ($('#fieldid').val() == '') {
        $.post('../reqfield/edit', {
            fieelementtype: $('#fieelementtype').val(), fieelementid: $('#fieelementid').val(),
            fieelementname: $('#fieelementname').val(), fieshowlevel: $('#fieshowlevel').val(),
            fiepelementid: $('#fiepelementid').val(), fiedefaultvalue: $('#fiedefaultvalue').val(),
            fieshoworder: $('#fieshoworder').val(), fieuseflag: $('#fieuseflag').val(),
            invokefunc: $('#invokefunc').val(), invokefuncbody: $('#invokefuncbody').val(),
            fieremark: $('#fieremark').val()
        }, function (data) {
            treeObj.addNodes(nodes[0], data, true);
            layer.msg('添加成功', {icon: 2, time: 1000});

        });
    } else {
        $.post('../reqfield/edit', {
            fieelementtype: $('#fieelementtype').val(), fieelementid: $('#fieelementid').val(),
            fieelementname: $('#fieelementname').val(), fieshowlevel: $('#fieshowlevel').val(),
            fiepelementid: $('#fiepelementid').val(), fiedefaultvalue: $('#fiedefaultvalue').val(),
            fieshoworder: $('#fieshoworder').val(), fieuseflag: $('#fieuseflag').val(),
            invokefunc: $('#invokefunc').val(), invokefuncbody: $('#invokefuncbody').val(),
            fieremark: $('#fieremark').val(), fieldid: $('#fieldid').val()
        }, function (data) {
            layer.msg('修改成功', {icon: 2, time: 1000});
        });
    }
}

function deleteSection() {
    var treeObj = $.fn.zTree.getZTreeObj("tree");
    var nodes = treeObj.getCheckedNodes(true);
    if (nodes == null || nodes.length == 0) {
        layer.msg('请先选择要删除的数据', {icon: 2, time: 1000});
        return false;
    }
    var param = [];
    var j = 0;
    for (var i = 0; i < nodes.length; i++) {
        if (nodes[i].id == 0) continue;
        param[j] = nodes[i].id;
        j++;
    }
    if (param.length == 0) return false;
    layer.confirm('确定删除选择的数据？', {icon: 2, title: '警告'}, function (index) {
        $.post('../reqfield/remove', {id: param.toString()}, function (data) {
            for (var i = 0; i < nodes.length; i++) {
                if (nodes[i].id == 0) continue;
                treeObj.removeNode(nodes[i], false);
            }
            layer.close(index);
            clearData();
        });
    });
}
/**
 * 查询科室
 */
function search() {
    var query = $('#query').val() || '';
    jQuery("#sectionList").jqGrid('setGridParam', {
        url: "../reqfield/query",
        //发送数据
        postData: {"query": query, "sidx": "fieldid"},
        page: 1
    }).trigger('reloadGrid');//重新载入
}

$(function () {
    //表单校验
    $("#addSectionForm").Validform({
        tiptype: 4,
        callback: function () {

        }
    });
    //keyPress 回车检索
    $("#query").keypress(function (e) {
        if (e.keyCode == 13) {
            search();
        }
    });
});
function clearData() {
    $('#fieelementtype').val('input');
    $('#fieelementid').val('');
    $('#fieelementname').val('');
    $('#fieshowlevel').val('');
    $('#fiepelementid').val('');
    $('#fiedefaultvalue').val('');
    $('#fieshoworder').val('');
    $('#fieuseflag').val('1');
    $('#fieremark').val('');
    $('#invokefunc').val('');
    $('#invokefuncbody').val('');
}
