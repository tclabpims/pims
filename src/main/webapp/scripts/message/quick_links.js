/**
 * 右侧快速操作
 * kongge@office.weiphone.com
 * 2012.06.07
*/
jQuery(function($){
	setInterval("reqnum()",10000);
	//创建DOM
	var 
	quickHTML = '<div class="quick_links_panel">' +
					'<div id="quick_links" class="quick_links">' +
						'<a href="#top" class="return_top">' +
							'<i class="top"></i>' +
							'<span>返回顶部</span>' +
						'</a>' +
						'<a href="#" onclick="findxx()" class="message_list" >' +
							'<i class="message"></i>' +
							'<span>新消息</span>' +
							'<em class="num" style="display:none"></em>' +
						'</a>' +
						'<a href="#" class="leave_message">' +
							'<i class="qa"></i>' +
							'<span>留言</span>' +
						'</a>' +
					'</div>' +
					'<div class="quick_toggle">' +
						'<a href="javascript:;" class="toggle" title="展开/收起">×</a>' +
					'</div>' +
				'</div>' +
				'<div id="quick_links_pop" class="quick_links_pop hide"></div>',
	quickShell = $(document.createElement('div')).html(quickHTML).addClass('quick_links_wrap'),
	quickLinks = quickShell.find('.quick_links');
	quickPanel = quickLinks.parent();
	quickShell.appendTo('body');


	
	//具体数据操作 
	var 
	quickPopXHR,
	loadingTmpl = '<div class="loading" style="padding:30px 80px"><i></i><span>Loading...</span></div>',
	popTmpl = '<div class="title"><h3><i></i><%=title%></h3></div><div class="pop_panel"><%=content%></div><div class="arrow"><i></i></div><div class="fix_bg"></div>',
	newMsgTmpl = '',
	quickPop = quickShell.find('#quick_links_pop'),
	quickDataFns = {
		// //站内消息
		// message_list: {
		// 	title: '新消息',
		// 	content: loadingTmpl,
		// 	init: function(ops){
		// 		//获取新消息
		// 		quickPopXHR = $.ajax({
		// 			url: '',
		// 			dataType: 'json',
		// 			cache: false,
		// 			success: function(data){
		// 				var html = ds.tmpl(newMsgTmpl, {
		// 					items: data
		// 				});
		// 				if(1 == data.success){
		// 					//重写总数
		// 					var shell = $('#quick_links a.message_list'), num = data.msgtotal ;
		// 					if(0 == num){
		// 						shell.find('em').remove();
		// 					}else{
		// 						shell.append('<em class="num"><b>'+ Math.min(99, num) +'</b></em>').show();
		// 					}
		//
		// 				}
		// 				quickPop.html(ds.tmpl(popTmpl, {
		// 					title: ops.title,
		// 					content: '<div class="links">'+ html +'</div>'
		// 				}));
		// 			}
		// 		});
		// 	}
		// },

		//给客服留言
		leave_message: {
			title: '留言 &nbsp;&nbsp;发送医生:&nbsp;&nbsp;' +
			'<div id="user_div" style="float: right"></div>&nbsp;&nbsp;&nbsp;&nbsp;',
			content: '<form action="./" method="post">' +
							'<div class="txt">' +
								'<textarea name="msg" id="msg" cols="15" rows="10"></textarea></br></br>' +
							'</div>' +
							'<div class="btns">' +
								'<button type="submit" class="btn-sm">' +
									'<span>提交</span>' +
								'</button>' +
							'</div>' +
						'</form>',
			init: function(ops){
				setTimeout(function(){
					quickPop.find('textarea').focus();
				}, 100);
				jQuery.ajax({
					type:'get',
					url: '../message/message/ajax/item',
					dataType:"json",
					error:function(value){
						ds.dialog.alert('获取用户失败');
					},
					success: function(value){
						var info = value.info;
						$("#user_div").html(info);


					}
				});
				//效验 & 提交数据
				var form = quickPop.find('form');
				//form.attr("action",'../message/message/editSample');
				form.bind('submit', function(e){
					e.preventDefault();
					var data = form.serialize();
					jQuery.ajax({
						type:'post',
						url: '../message/message/editSample',
						data:{"states":3,"userid":$("#user_id").val(),"username":$("#user_id").find("option:selected").text(),
							"info":$("#msg").val()},
						dataType:"json",
						error:function(value){
							ds.dialog.alert('留言失败');
						},
						success: function(value){
							var success = value.success;
							var info = value.info;
							if(success){
								hideQuickPop();
								showInfoTip(info, 'success');
							}else{
								ds.dialog.alert(info);
							}
						}
					});
				});
			}
		}
	};

	//showQuickPop
	var 
	prevPopType,
	prevTrigger,
	doc = $(document),
	popDisplayed = false,
	hideQuickPop = function(){
		if(prevTrigger){
			prevTrigger.removeClass('current');
		}
		popDisplayed = false;
		prevPopType = '';
		quickPop.hide();
	},
	showQuickPop = function(type){
		if(quickPopXHR && quickPopXHR.abort){
			quickPopXHR.abort();
		}
		if(type !== prevPopType){
			var fn = quickDataFns[type];
			quickPop.html(ds.tmpl(popTmpl, fn));
			fn.init.call(this, fn);
		}
		doc.unbind('click.quick_links').one('click.quick_links', hideQuickPop);

		quickPop[0].className = 'quick_links_pop quick_' + type;
		popDisplayed = true;
		prevPopType = type;
		quickPop.show();
	};
	quickShell.bind('click.quick_links', function(e){
		e.stopPropagation();
	});

	//通用事件处理
	var 
	view = $(window),
	quickLinkCollapsed = !!ds.getCookie('ql_collapse'),
	getHandlerType = function(className){
		return className.replace(/current/g, '').replace(/\s+/, '');
	},
	showPopFn = function(){
		var type = getHandlerType(this.className);
		if(popDisplayed && type === prevPopType){
			return hideQuickPop();
		}
		showQuickPop(this.className);
		if(prevTrigger){
			prevTrigger.removeClass('current');
		}
		prevTrigger = $(this).addClass('current');
	},
	quickHandlers = {
		//购物车，最近浏览，商品咨询
		my_qlinks: showPopFn,
		message_list: showPopFn,
		history_list: showPopFn,
		leave_message: showPopFn,
		//返回顶部
		return_top: function(){
			ds.scrollTo(0, 0);
			hideReturnTop();
		},
		toggle: function(){
			quickLinkCollapsed = !quickLinkCollapsed;

			quickShell[quickLinkCollapsed ? 'addClass' : 'removeClass']('quick_links_min');
			ds.setCookie('ql_collapse', quickLinkCollapsed ? '1' : '', 30);
		}
	};
	quickShell.delegate('a', 'click', function(e){
		var type = getHandlerType(this.className);
		if(type && quickHandlers[type]){
			quickHandlers[type].call(this);
			e.preventDefault();
		}
	});
	
	//Return top
	var scrollTimer, resizeTimer, minWidth = 1350;

	function resizeHandler(){
		clearTimeout(scrollTimer);
		scrollTimer = setTimeout(checkScroll, 160);
	}
	function checkResize(){
		quickShell[view.width() > 1340 ? 'removeClass' : 'addClass']('quick_links_dockright');
	}
	function scrollHandler(){
		clearTimeout(resizeTimer);
		resizeTimer = setTimeout(checkResize, 160);
	}
	function checkScroll(){
		view.scrollTop()>100 ? showReturnTop() : hideReturnTop();
	}
	function showReturnTop(){
		quickPanel.addClass('quick_links_allow_gotop');
	}
	function hideReturnTop(){
		quickPanel.removeClass('quick_links_allow_gotop');
	}

	view.bind('scroll.go_top', resizeHandler).bind('resize.quick_links', scrollHandler);
	quickLinkCollapsed && quickShell.addClass('quick_links_min');
	resizeHandler();
	scrollHandler();

});

//首次加载站内消息总数
jQuery(function($){
	var shell = $('#quick_links a.message_list');
	if(shell.find("em").length){
		
		$.ajax({
			url: '../receive/receive/ajax/num',
			dataType: 'json',
			cache: false,
			success: function(data){
				if(1 == data.success){
					if(0 == data.num){
						shell.find('em').remove();
					}else{
						var num = data.num;
						//消息总数最大只显示 99
						shell.append('<em class="num"><b>'+ Math.min(99, num) +'</b></em>').show();
					}
				}
			}
		});
	}
});

function reqnum() {
	var shell = $('#quick_links a.message_list');
	if(shell.find("em").length){

		$.ajax({
			url: '../receive/receive/ajax/num',
			dataType: 'json',
			cache: false,
			data:{"patient_name":$("#local_userid").val()},
			success: function(data){
				if(1 == data.success){
					if(0 == data.num){
						shell.find('em').remove();
					}else{
						var num = data.num;
						//消息总数最大只显示 99
						shell.append('<em class="num"><b>'+ Math.min(99, num) +'</b></em>').show();
					}
				}
			}
		});
	}
}

function findxx() {
	location.href='../receive/receive.jsp';
}
