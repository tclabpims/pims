<%@ include file="/common/taglibs.jsp" %>
<head>
	
    <link rel="stylesheet" href="../styles/ztree/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="../scripts/jquery.ztree.all-3.5.js"></script>
    <link rel="stylesheet" href="../styles/jquery-ui.min.css" type="text/css">
    <script type="text/javascript" src="../scripts/jquery-ui.min.js"></script>
    <link rel="stylesheet" type="text/css"  href="<c:url value='../styles/ruleLib.css'/>" />
    <script type="text/javascript" src="../scripts/jquery.jstree.js"></script>
	<script type="text/javascript" src="../scripts/jquery.json-2.3.min.js"></script>
</head>

<script type="text/javascript">
	function navSearchBtnClick() {
		if ($("#nav-search-panel").css('display') == "none") {
			$("#nav-search-panel").css('display','block');
		} else {
			$("#nav-search-panel").css('display','none');
		}
	}
	function navRuleBtnClick() {
		if ($("#nav-rule-panel").css('display') == "none") {
			$("#nav-rule-panel").css('display','block');
		} else {
			$("#nav-rule-panel").css('display','none');
		}
	}
	
	$.widget("custom.catcomplete", $.ui.autocomplete, {
	
	    _renderMenu : function(ul, items) {
	        var that = this, currentCategory = "";
	        $.each(items, function(index, item) {
	            if (item.category != currentCategory) {
	                var type;
	                if (item.category == "T") {
	                    type = "\u7ed3\u679c";
	                } else if (item.category == "I") {
	                    type = "\u6307\u6807";
	                } else if (item.category == "R") {
	                    type = "\u89c4\u5219";
	                }
	                ul.append("<li class='ui-autocomplete-category'>" + type
	                        + "</li>");
	                currentCategory = item.category;
	            }
	            that._renderItemData(ul, item);
	        });
	    }
	});

	$(function() {
	
	    $("#globalsearchbox").catcomplete({
		    source : function(request, response) {
		        $.ajax({
		            url : "../ajax/description/getInfo",
		            dataType : "json",
		            data : {
		                maxRows : 12,
		                name : request.term
		            },
		            success : function(data) {
		
		                response($.map(data, function(item) {
		                    return {
		                        label : item.name,
		                        category : item.category,
		                        id : item.id
		                    }
		                }));
		
		                $("#globalsearchbox").removeClass(
		                        "ui-autocomplete-loading");
		
		            }
		        });
		    },
		    minLength : 2,
		    select : function(event, ui) {
		        var id = ui.item.id;
		        location.href = "../description/view?id=" + id;
		    }
		});
	});

	var setting1 = {
	    view : {
	        addHoverDom : addHoverDom,
	        removeHoverDom : removeHoverDom,
	        selectedMulti : false,
	        expandSpeed : ""
	    },
	    edit : {
	        drag : {
	            autoExpandTrigger : true,
	            prev : dropPrev,
	            inner : dropInner,
	            next : dropNext
	        },
	        enable : true,
	        editNameSelectAll : true
	    },
	    data : {
	        simpleData : {
	            enable : true
	        }
	    },
	    callback : {
	        beforeDrag : beforeDrag,
	        beforeDrop : beforeDrop,
	        beforeDragOpen : beforeDragOpen,
	        beforeEditName : beforeEditName,
	        beforeRemove : beforeRemove,
	        beforeRename : beforeRename,
	        onDrag : onDrag,
	        onDrop : onDrop,
	        onExpand : onExpand,
	        onRemove : onRemove,
	        onRename : onRename,
	        onClick : onClick
	    }
	};
	var setting2 = {
	    view : {
	        selectedMulti : false,
	        expandSpeed : ""
	    },
	    data : {
	        simpleData : {
	            enable : true
	        }
	    },
	    callback : {
	        onClick : onClick
	    }
	};
	function onClick(event, treeId, treeNode) {
	    location.href = "../description/list?bag=" + treeNode.id;
	}
	function dropPrev(treeId, nodes, targetNode) {
	    var pNode = targetNode.getParentNode();
	    if (pNode && pNode.dropInner === false) {
	        return false;
	    } else {
	        for ( var i = 0, l = curDragNodes.length; i < l; i++) {
	            var curPNode = curDragNodes[i].getParentNode();
	            if (curPNode && curPNode !== targetNode.getParentNode()
	                    && curPNode.childOuter === false) {
	                return false;
	            }
	        }
	    }
	    return true;
	}
	function dropInner(treeId, nodes, targetNode) {
	    if (targetNode && targetNode.dropInner === false) {
	        return false;
	    } else {
	        for ( var i = 0, l = curDragNodes.length; i < l; i++) {
	            if (!targetNode && curDragNodes[i].dropRoot === false) {
	                return false;
	            } else if (curDragNodes[i].parentTId
	                    && curDragNodes[i].getParentNode() !== targetNode
	                    && curDragNodes[i].getParentNode().childOuter === false) {
	                return false;
	            }
	        }
	    }
	    return true;
	}
	function dropNext(treeId, nodes, targetNode) {
	    var pNode = targetNode.getParentNode();
	    if (pNode && pNode.dropInner === false) {
	        return false;
	    } else {
	        for ( var i = 0, l = curDragNodes.length; i < l; i++) {
	            var curPNode = curDragNodes[i].getParentNode();
	            if (curPNode && curPNode !== targetNode.getParentNode()
	                    && curPNode.childOuter === false) {
	                return false;
	            }
	        }
	    }
	    return true;
	}
	var log, className = "dark", curDragNodes, autoExpandNode;
	function beforeDrag(treeId, treeNodes) {
	    className = (className === "dark" ? "" : "dark");
	    showLog("[ " + getTime() + " beforeDrag ]&nbsp;&nbsp;&nbsp;&nbsp; drag: "
	            + treeNodes.length + " nodes.");
	    for ( var i = 0, l = treeNodes.length; i < l; i++) {
	        if (treeNodes[i].drag === false) {
	            curDragNodes = null;
	            return false;
	        } else if (treeNodes[i].parentTId
	                && treeNodes[i].getParentNode().childDrag === false) {
	            curDragNodes = null;
	            return false;
	        }
	    }
	    curDragNodes = treeNodes;
	    return true;
	}
	function beforeDragOpen(treeId, treeNode) {
	    autoExpandNode = treeNode;
	    return true;
	}
	function beforeDrop(treeId, treeNodes, targetNode, moveType, isCopy) {
	    className = (className === "dark" ? "" : "dark");
	    showLog("[ " + getTime()
	            + " beforeDrop ]&nbsp;&nbsp;&nbsp;&nbsp; moveType:" + moveType);
	    showLog("target: " + (targetNode ? targetNode.name : "root") + "  -- is "
	            + (isCopy == null ? "cancel" : isCopy ? "copy" : "move"));
	    return true;
	}
	function beforeEditName(treeId, treeNode) {
	    className = (className === "dark" ? "" : "dark");
	    showLog("[ " + getTime() + " beforeEditName ]&nbsp;&nbsp;&nbsp;&nbsp; "
	            + treeNode.name);
	    var zTree = $.fn.zTree.getZTreeObj("tree");
	    zTree.selectNode(treeNode);
	    return true;
	}
	function beforeRemove(treeId, treeNode) {
	    className = (className === "dark" ? "" : "dark");
	    showLog("[ " + getTime() + " beforeRemove ]&nbsp;&nbsp;&nbsp;&nbsp; "
	            + treeNode.name);
	    var zTree = $.fn.zTree.getZTreeObj("tree");
	    zTree.selectNode(treeNode);
	    return confirm("\u786e\u8ba4\u5220\u9664 \u8282\u70b9 -- " + treeNode.name
	            + " \u5417\uff1f�");
	}
	function onRemove(e, treeId, treeNode) {
	    showLog("[ " + getTime() + " onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; "
	            + treeNode.name);
	    dataToController("remove", treeNode.id, treeNode.name);
	}
	function beforeRename(treeId, treeNode, newName) {
	    className = (className === "dark" ? "" : "dark");
	    showLog("[ " + getTime() + " beforeRename ]&nbsp;&nbsp;&nbsp;&nbsp; "
	            + treeNode.name);
	    if (newName.length == 0) {
	        alert("\u8282\u70b9\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a.");
	        var zTree = $.fn.zTree.getZTreeObj("tree");
	        setTimeout(function() {
	            zTree.editName(treeNode)
	        }, 10);
	        return false;
	    }
	    return true;
	}
	function onDrag(event, treeId, treeNodes) {
	    className = (className === "dark" ? "" : "dark");
	    showLog("[ " + getTime() + " onDrag ]&nbsp;&nbsp;&nbsp;&nbsp; drag: "
	            + treeNodes.length + " nodes.");
	}
	function onDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
	    className = (className === "dark" ? "" : "dark");
	    showLog("[ " + getTime() + " onDrop ]&nbsp;&nbsp;&nbsp;&nbsp; moveType:"
	            + moveType);
	    showLog("target: " + (targetNode ? targetNode.name : "root") + "  -- is "
	            + (isCopy == null ? "cancel" : isCopy ? "copy" : "move"));
	    dataToController("drag" + moveType, treeNodes[0].id, targetNode.id);
	}
	function onExpand(event, treeId, treeNode) {
	    if (treeNode === autoExpandNode) {
	        className = (className === "dark" ? "" : "dark");
	        showLog("[ " + getTime() + " onExpand ]&nbsp;&nbsp;&nbsp;&nbsp;"
	                + treeNode.name);
	    }
	}
	function onRename(e, treeId, treeNode) {
	    showLog("[ " + getTime() + " onRename ]&nbsp;&nbsp;&nbsp;&nbsp; "
	            + treeNode.name);
	    dataToController("rename", treeNode.id, treeNode.name);
	}
	function showLog(str) {
	    if (!log)
	        log = $("#log");
	    log.append("<li class='" + className + "'>" + str + "</li>");
	    if (log.children("li").length > 8) {
	        log.get(0).removeChild(log.children("li")[0]);
	    }
	}
	function getTime() {
	    var now = new Date(), h = now.getHours(), m = now.getMinutes(), s = now
	            .getSeconds(), ms = now.getMilliseconds();
	    return (h + ":" + m + ":" + s + " " + ms);
	}
	var newCount = 1;
	function addHoverDom(treeId, treeNode) {
	    var sObj = $("#" + treeNode.tId + "_span");
	    if (treeNode.editNameFlag || $("#addBtn_" + treeNode.id).length > 0)
	        return;
	    var addStr = "<span class='button add' id='addBtn_" + treeNode.id
	            + "' title='add node' onfocus='this.blur();'></span>";
	    sObj.after(addStr);
	    var btn = $("#addBtn_" + treeNode.id);
	    if (btn)
	        btn.bind("click", function() {
	            var zTree = $.fn.zTree.getZTreeObj("tree");
	            zTree.addNodes(treeNode, {
	                id : (100 + newCount),
	                pId : treeNode.id,
	                name : "new" + newCount
	            });
	            dataToController("add", treeNode.id, "new" + newCount);
	            newCount++;
	            
	            return false;
	        });
    }
    function removeHoverDom(treeId, treeNode) {
	    $("#addBtn_" + treeNode.id).unbind().remove();
    }
    function selectAll() {
	    var zTree = $.fn.zTree.getZTreeObj("tree");
	    zTree.setting.edit.editNameSelectAll = $("#selectAll").attr("checked");
	}
	function setTrigger() {
	    var zTree = $.fn.zTree.getZTreeObj("tree");
	    zTree.setting.edit.drag.autoExpandTrigger = $("#callbackTrigger").attr(
	            "checked");
	}
	function dataToController(action, id, name) {
	    $.ajax({
	        type : "POST",
	        url : "../ajax/description/editBag",
	        data : "action=" + action + "&id=" + id + "&name=" + name,
	        dataType : "html",
	        success : function() {
	            /*alert(action + " success!");*/
	        	getData();
	        }
	    });
	}
    function getData(){
    	$.ajax({
			async : true,
			cache : false,
			type : 'GET',
			url : "<c:url value='../ajax/description/getDesBag'/>",
			datatype : "json",
			error : function() {
				alert('data false');
			},
			success : function(data) {
				var o = jQuery.parseJSON(data);
				if (${pageContext.request.remoteUser=='admin'}) {
					$.fn.zTree.init($("#tree"), setting1, o).expandAll(true);
					$("#selectAll").bind("click", selectAll);
					$("#callbackTrigger").bind("change", {}, setTrigger);	
				} else {
					$.fn.zTree.init($("#tree"), setting2, o).expandAll(true);
				}
			}
		});
    }
	$(function(){
		getData();
	});
	
	
</script>
<style>
.ui-autocomplete {
	background-color:#ffffff;
}
</style>

<div class="border-radius-6 border-shadow" id="navContent" style="padding-bottom:30px;">
	<div class="tag-section">
		<a class="selbtn" href="javascript:navSearchBtnClick();"><div class="tag-item">
		<b class="tag-name" style="letter-spacing:5px; "><fmt:message key="search" /></b>
		</div></a>
	</div>
	<div id="nav-search-panel" class="tag-panel" style="padding:10px 10px 0 10px;display:block;">
		<p style="color:#777777;"><fmt:message key="nav.search" />
		<input id="globalsearchbox" placeholder="<fmt:message key="rule"/>,<fmt:message key="index"/>,<fmt:message key="result"/>" style="width:100%;margin-top:10px;"/>
	</div>
	<div class="tag-section">
		<a class="selbtn" href="javascript:navRuleBtnClick();"><div class="tag-item">
			<b class="tag-name" style="letter-spacing:5px; "><fmt:message key="rule" /></b>
			</div>
		</a>
	</div>
	<div id="nav-rule-panel" class="tag-panel" style="display:block;">
		<div style="margin:5px 0px 5px 5px;">
			
       		<div>
				<ul id="tree" class="ztree"></ul>
			</div>
       	</div>
	</div>
	
	
</div>