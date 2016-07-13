function lostSubmit() {
	var captcha = $("table#lost input#captcha").val();
	var password = $("table#lost input#password").val();
	
	$("table#lost input#captcha").val("");
	$("table#lost input#password").val("");
	$("table#lost input#password").focus();
	$.post("/karablog/lost", {
		captcha : captcha,
		password : password
	}, function(data, status) {
		if (status == "success" && data == "success") {
			alert("操作成功！");
		} else {
			alert("操作失败！");
		}
		
		{ // 2016-4-1 20:27:46 功能：提交时刷新验证码
			$("table#lost img#captcha").attr("src", "/karablog/getCaptcha?"+Math.random());
		} // *****************************************
	});
}

function changeSubmit() {
	var captcha = $("table#change input#captcha").val();
	var curpassword = $("table#change input#curpassword").val();
	var newpassword = $("table#change input#newpassword").val();
	var repassword = $("table#change input#repassword").val();
	
	$("table#change input#captcha").val("");
	$("table#change input#curpassword").val("");
	$("table#change input#newpassword").val("");
	$("table#change input#repassword").val("");
	$("table#change input#curpassword").focus();
	$.post("/karablog/change", {
		captcha : captcha,
		curpassword : curpassword,
		newpassword : newpassword,
		repassword : repassword
	}, function(data, status) {
		if (status == "success" && data == "success") {
			alert("操作成功！");
		} else {
			alert("操作失败！");
		}
		
		{ // 2016-4-1 20:27:46 功能：提交时刷新验证码
			$("table#change img#captcha").attr("src", "/karablog/getCaptcha?"+Math.random());
		} // *****************************************
	});
}

function pickup() {
	var captcha = $("table#pickup input#captcha").val();
	var school = $("table#pickup input#school").val();
	var stuid = $("table#pickup input#stuid").val();
	var name = $("table#pickup input#name").val();
	var cardid = $("table#pickup input#cardid").val();
	
	$("table#pickup input#captcha").val("");
	$("table#pickup input#school").val("");
	$("table#pickup input#stuid").val("");
	$("table#pickup input#name").val("");
	$("table#pickup input#cardid").val("");
	$("table#pickup input#school").focus();
	$.post("/karablog/pickup", {
		captcha : captcha,
		school : school,
		stuid : stuid,
		name : name,
		cardid : cardid
	}, function(data, status) {
		if (status == "success" && data == "success") {
			alert("操作成功！");
		} else {
			alert("操作失败！");
		}
		
		{ // 2016-4-1 20:27:46 功能：提交时刷新验证码 *********************
			$("table#pickup img#captcha").attr("src", "/karablog/getCaptcha?"+Math.random());
		} // *******************************************************
	});
}
