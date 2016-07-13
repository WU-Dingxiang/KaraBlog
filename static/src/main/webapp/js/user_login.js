//等待页面加载完毕
$(document).ready(function() {
	// ----------------------- 全局变量！-----------------------
	// 最小输入长度
	var minInputLen = 4;
	// 提示语
	var inputEmptyAlerts = new Array();
	inputEmptyAlerts[0] = "账号不能为空！";
	inputEmptyAlerts[1] = "密码不能为空！";
	inputEmptyAlerts[2] = "验证码不能为空！";
	var inputShortAlert = "账号/密码长度不能少于" + minInputLen + "位数字";
	// ----------------------- 函数拓展！-----------------------
	// 正则检查
	$.extend({
		inputOnkeypress : function(event) {
			var keycode = event.keyCode || event.keycode || event.which;
			return /^\d$/.test(String.fromCharCode(keycode));
		}
	});
	// 正则替换
	$.extend({
		inputDigitRepalce : function(field) {
			field.value = field.value.replace(/\D+/g, "");
		}
	});
	// 正则检查+正则替换
	$.extend({
		inputOnpropertychange : function(field) {
			if (!/\D+/.test(field.value))
				return;
			$.inputDigitRepalce(field);
		}
	});
	// 检查当前内容是否为空
	$.extend({
		isThisValueEmpty : function(value) {
			return (value == null || value == "");
		}
	});
	// 检查当前内容最小输入长度
	$.extend({
		isThisValueTooShort : function(value) {
			return value.length < minInputLen;
		}
	});
	// 检查是否存在任意一个所有输入框为空
	$.extend({
		isAllInputFilled : function() {
			var result = true, i = 0, willfocus = null;
			$(":text,:password").each(function() {
				var value = $(this).val();
				if ($.isThisValueEmpty(value)) {
					alert(inputEmptyAlerts[i]);
					result = false;
					if (willfocus == null)
						willfocus = $(this);
				} else if ($.isThisValueTooShort(value)) {
					alert(inputShortAlert);
					result = false;
					if (willfocus == null)
						willfocus = $(this);
				}
				i++;
			});
			if (willfocus != null)
				willfocus.focus();
			return result;
		}
	});
	// ----------------------- 事件绑定！-----------------------
	// 表单提交
	$("form").submit(function() {
		// 输入是否为空？
		if ($.isAllInputFilled())
			return true;
		else
			return false;
	});
	// 刷新验证码
	$("#imgCaptcha").click(function() {
		$(this).attr("src", "/karablog/getCaptcha?"+Math.random());
	});
	// ----------------------- 直接执行！-----------------------
	// 设置焦点
	$("input:first").focus();
	// 设置输入限制
	$(":text,:password").attr("maxlength", "16");
	$(":text,:password").attr("onkeypress", "$.inputOnkeypress(event)");
	$(":text,:password").attr("oninput", "$.inputOnpropertychange(this)");
	$(":text,:password").attr("onblur", "$.inputOnkeypress(event)");

});