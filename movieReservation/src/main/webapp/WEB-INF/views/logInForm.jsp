<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>
	<input type="text" name="mId" placeholder="아이디를 입력해주세요" />
	<input type="password" name="mPwd" placeholder="비번 입력해주세요" />
	<input type="button" value="로그인이동" onClick="moveLogInForm()" />
	
	<br /><br />
	${member.getMId() }<br />
	${member.getMName() }<br />
	${member.getMPhone() }<br />
	
</body>

<script>
function moveLogInForm() {
	var mId = document.getElementsByName("mId")[0];
	var mPwd = document.getElementsByName("mPwd")[0];
	var form = document.createElement("form");
	form.action = "LogIn";
	form.method = "POST";
	
	var SCode = document.createElement("input");
	SCode.type = "hidden";
	SCode.name = "SCode";
	SCode.value = "LogIn";
	form.appendChild(SCode);
	   
	form.appendChild(mId);
	form.appendChild(mPwd);
	document.body.appendChild(form);
	
	form.submit();
}
</script>

</html>


