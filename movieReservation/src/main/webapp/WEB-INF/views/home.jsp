<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE HTML>
<html>
<head>
	<title>Home</title>
</head>
<body>

	<a href="http://192.168.1.149/LoginForm" >로그인폼 이동</a><br />
	<P>  Now Time : ${Access} </P>
	<section id="movieZone"></section>
</body>
<script> 
	let dayStr = "${Access}";
	let day = (dayStr.split(" "))[0].split("-");
	let now = new Date();
	
	now.setFullYear(parseInt(day[0]), parseInt(day[1])-1, parseInt(day[2]));
	now.setDate(now.getDate()+30);
	let section = document.getElementById("movieZone");
	let movieList = JSON.parse('${jsonData}');	
	let record = parseInt(movieList.length/3);
	record = (movieList.length%3 > 0)? record + 1: record;
	for(rIndex=0; rIndex < record; rIndex++){
		let div = document.createElement('Div');
		div.style.display = "inline-flex";
		div.setAttribute("name", "line");
		section.appendChild(div);
	}

	for(index=0; index < movieList.length; index++){
		let rDivIndex = parseInt(index/3);
		let mvDiv = document.createElement('Div');
		mvDiv.style.width = "400px";
		mvDiv.style.height = "400px";
		mvDiv.style.margin = "0px 10px 20px 0px";                
		mvDiv.style.backgroundImage = "url(/resources/image/" + movieList[index].mvImage + ")"
		// mvDiv.style.backgroundSize = "contain";
		mvDiv.style.cursor = "pointer";
		let mvCode = movieList[index].mvCode;
		mvDiv.addEventListener('click', function(){divClick(mvCode);});
		let line = document.getElementsByName("line")[rDivIndex];
		line.appendChild(mvDiv);
	}
	
function divClick(code){
    var form = document.createElement("form");
    form.action="toDate";
    form.method="post";
    
    var mvCode =document.createElement("input");
    mvCode.type = "hidden";
    mvCode.name = "mvCode";
    mvCode.value = code;
    form.appendChild(mvCode);
   
	var sCode = document.createElement("input");
	sCode.type = "hidden";
	sCode.name = "sCode";
	sCode.value = "toDate";
	form.appendChild(sCode);
   
    document.body.appendChild(form);
    form.submit();
}
	
</script>
</html>
