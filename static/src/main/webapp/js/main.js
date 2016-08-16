//等待页面加载完毕
$(document).ready(function() {
	// ------------------ 全局变量 ------------------------
	var htmlSelected = -1;
	var pageSelected = -1;

	// ------------------ 函数扩展 ------------------------
	// 定时器
	$.extend({
		cssNavfirst : function() {
			if (htmlSelected > 0) {
				var li = $("ul#menu li:eq(" + (htmlSelected - 1) + ")");
				li.css("border-bottom-width", "4px");
			}
			window.setTimeout($.cssNavfirst, 1);
		}
	});
	// 清除所有下划线
	$.extend({
		clearUnderline : function(index) {
			$("ul#menu li").css("border-bottom-width", "0px");
			htmlSelected = index;
		}
	});
	// 根据 AJAX 返回的内容刷新divDisplay
	$.extend({
		getDisplay : function(data, status) {
			if ("success" == status && data != "samepage") {
				$("div#display").html(data);
				
				{ // 2016-4-1 20:35:17 ********************
					// 功能1：进入新界面立即刷新验证码
					$("table img#captcha").attr("src", "/blog-web/getCaptcha?"+Math.random());
					// 功能2：动态绑定验证码点击刷新
					$("table img#captcha").click(function() {
						$(this).attr("src", "/blog-web/getCaptcha?"+Math.random());
					});					
				} // **************************************
			}
		}
	});
	// 美化表格
	$.extend({
		cssTable : function(table) {
			table.css("border-spacing", "0");
			table.css("width", "1000px");
			table.css("margin", "0 auto");
			table.css("text-align", "left");
			table.css("font-size", "16px");
			table.find("th").css("border-bottom", "3px solid");
			table.find("th").css("padding", "10px 8px");
			table.find("td").css("border-bottom", "1px solid");
			table.find("td").css("padding", "9px 8px");
		}
	});
	// 美化菜单
	$.extend({
		cssMenu : function(menu) {
			menu.css("font-size", "14px");
			menu.css("margin-top", "50px");
			menu.css("margin-bottom", "20px");
			menu.find("li").css("display", "inline");
			menu.find("li").css("padding", "10px");
			menu.find("li").css("margin", "5px");
			menu.find("li").css("border", "1px solid");
			menu.find("li").css("cursor", "pointer");
			var select = menu.find("li#pageIndex" + pageSelected);
			select.css("border", "0px solid");
			select.css("cursor", "auto");
			select.css("color", "black");
		}
	});
	// 跳转table页码
	$.extend({
		goLogPage : function(_pageIndex) {
			if (htmlSelected != 2 || pageSelected != _pageIndex) {
				pageSelected = _pageIndex;
				$.post("/blog-web/seeLog", {
					pageIndex : _pageIndex
				}, function(data, status) {
					$.getDisplay(data, status);
					$.cssTable($("table#display"));
					$.cssMenu($("ul#pagemenu"));
				});
			}
		}
	});
	// 跳转table页码
	$.extend({
		goWaterPage : function(_pageIndex) {
			if (htmlSelected != 4 || pageSelected != _pageIndex) {
				pageSelected = _pageIndex;
				$.post("/blog-web/seeWater", {
					pageIndex : _pageIndex
				}, function(data, status) {
					$.getDisplay(data, status);
					$.cssTable($("table#display"));
					$.cssMenu($("ul#pagemenu"));
				});
			}
		}
	});
	// 跳转html页面
	$.extend({
		goHtml : function(htmlIndex, htmlName) {
			if (htmlSelected != htmlIndex) {
				$.post("/blog-web/getHtml", {
					htmlName : htmlName
				}, function(data, status) {
					$.getDisplay(data, status);
					$.cssTable($("table#display"));
				});
				$.clearUnderline(htmlIndex);
			}
		}
	});

	// ------------------ 绑定事件 ------------------------
	// 基本信息
	$("li#basic").click(function() {
		$.goHtml(1, "basic_info.html");
	});
	// 交易记录
	$("li#history").click(function() {
		if (htmlSelected != 2) {
			$.goLogPage(1);
			$.clearUnderline(2);
		}
	});
	// 水控记录
	$("li#water").click(function() {
		if (htmlSelected != 4) {
			$.goWaterPage(1);
			$.clearUnderline(4);
		}
	});
	// 挂失
	$("li#lost").click(function() {
		$.goHtml(5, "lost.html");
	});
	// 修改查询密码
	$("li#change").click(function() {
		$.goHtml(6, "change.html");
	});
	// 拾获校园卡
	$("li#pickup").click(function() {
		$.goHtml(7, "pickup.html");
	});
	// 退出
	$("li#exit").click(function() {
		$.post("/blog-web/user_logout", function() {
			location.href = "/blog-web";
		});
	});
	// 导航栏:选中时
	$("ul#menu li").mouseenter(function() {
		$(this).css("border-bottom-width", "4px");
	});
	// 导航栏:未选中
	$("ul#menu li").mouseleave(function() {
		$(this).css("border-bottom-width", "0px");
	});

	// --------------------- 直接执行 -----------------------
	// 默认界面
	$.goHtml(0, "info.html");
	// 开启定时器
	$.cssNavfirst();
});