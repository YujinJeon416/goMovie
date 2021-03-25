<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Send Mail</title>
</head>
<body>
	<table><!-- 실제로 만들 때는 table 말고 딴거 씁시다 -->
		<tr>
			<td>Receiver</td>
			<td><input type="text" name="mailReceiver"/></td>
		</tr>
		<tr>
			<td>Subject</td>
			<td><input type="text" name="mailSubject"/></td>
		</tr>
	</table>
	<textarea name="mailContent"></textarea>
	<input type="button" value="SEND" onClick="sendMail()"/>
</body>
<script>
	function sendMail(){
		let receiver = document.getElementsByName("mailReceiver")[0];
		let subject = document.getElementsByName("mailSubject")[0];
		let content = document.getElementsByName("mailContent")[0];
		
		let form = document.createElement("form");
		form.action = "SendMail";
		form.method = "post";
		
		form.appendChild(content);
		form.appendChild(receiver);
		form.appendChild(subject);
		
		document.body.appendChild(form);
		form.submit();
	}
</script>
</html>