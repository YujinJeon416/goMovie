<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>날짜/시간 선택</title>
	<link href="/resources/css/kotlin.css" rel="stylesheet" />
</head>
<body onLoad="init()">
	<P>Now Time : ${Access}</P>
	<section id="movieZone">
		<div id="movieInfo"></div>
		<div class="flex_container">${days }</div>
		<div id="selectTime"></div>
	</section>
</body>
<script>
	function init() {
		let days = document.getElementsByName("days");
		let mvSelling = JSON.parse('${selling}');
		for (i = 0; i < days.length; i++) {
			let btn = days[i].value;
			days[i].style.cursor = 'pointer';
			if (!mvSelling.includes(btn)) { // mvSelling에 days[i].value와 일치하는 값이 없으면
				days[i].style.background = '#808080';// 버튼색 회색
				days[i].style.color = '#000000';// 버튼 글자색 검정색
				days[i].style.cursor = 'default';
			}
		}

		let movieInfo = document.getElementById("movieInfo");
		/* Append movieInfo Div */
		let movie = JSON.parse('${movieData}');

		let mvImage = document.createElement('Div');
		mvImage.style.width = "400px";
		mvImage.style.height = "400px";
		mvImage.style.margin = "0px 10px 20px 0px";
		mvImage.style.backgroundImage = "url(/resources/image/"
				+ movie[0].mvImage + ")";
		movieInfo.appendChild(mvImage);

		let mvTitle = document.createElement('Div');
		mvTitle.textContent = movie[0].mvName;
		mvImage.className = "movie";
		movieInfo.appendChild(mvTitle);

		let mvGrade = document.createElement('Div');
		if (movie[0].mvGrade = 'K') {
			mvGrade.textContent = "전체상영가";
		} else if (movie[0].mvGrade = 'Y') {
			mvGrade.textContent = "12세 이상 관람가";
		} else if (movie[0].mvGrade = 'M') {
			mvGrade.textContent = "15세 이상 관람가";
		} else {
			mvGrade.textContent = "19세 이상 관람가";
		}

		mvGrade.className = "movie";
		movieInfo.appendChild(mvGrade);

		let mvStatus = document.createElement('Div');
		if (movie[0].mvStatus = 'I') {
			mvStatus.textContent = "상영중";
		} else {
			mvStatus.textContent = "상영예정";
		}
		mvStatus.className = "movie";
		movieInfo.appendChild(mvStatus);

		let mvComments = document.createElement('Div');
		mvComments.textContent = movie[0].mvComments;
		mvComments.className = "movie";
		movieInfo.appendChild(mvComments);
	}

	function toNext(d) {
		let mvSelling = JSON.parse('${selling}');
		if (!mvSelling.includes(d)) {
			alert(d + "에 상영하지 않는 영화입니다.");
		} else {
			let movie = JSON.parse('${movieData}');

			let request = new XMLHttpRequest();

			request.onreadystatechange = function() {
				if (this.readyState == 4 & this.status == 200) {
					let jsonData = decodeURIComponent(request.response);
					parseIng(jsonData, d);
				}
			};
			request.open("POST", "toTime", true);
			request.setRequestHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			request.send("sCode=toTime&mvCode=" + movie[0].mvCode + "&d=" + d);
		}
	}

	function parseIng(jsonData, d) {
		let st = document.getElementById("selectTime");
		
		/* 이전에 누른 버튼 내용 = st에 내용이 있으면 지운다 */
		while (st.firstChild) {
			st.removeChild(st.firstChild);
		}
		
		st.innerHTML += d + "의 상영일정은 다음과 같습니다.";
		/* 상영 시간 append*/
		let mvIng = JSON.parse(jsonData);
		for (i = 0; i < mvIng.length; i++) {
			let frame = document.createElement('Div');			
			let time = mvIng[i].TIME;
			let sc = mvIng[i].SI_SCREEN;
			
			let timeBtn = document.createElement('button');
			timeBtn.innerHTML = "상영시간: " + time.substring(0,2) + "시 " + time.substring(2,4) + "분" + "(" + sc + "관)";
			frame.appendChild(timeBtn);
					
			let mvCode = mvIng[0].mvCode;
			
			/* 날짜와 시간을 DB의 시간 형식으로 변환 */
			let sendDate = d.split("-").join("");
			let sendTime = time;
			let dateTime = sendDate + sendTime;
			
			timeBtn.style.cursor = 'pointer';
			frame.addEventListener('click', function() {timeClick(mvCode, sc, dateTime);});
			st.appendChild(frame);
		}
	}
	
	function timeClick(code, sc, dateTime){
	    var form = document.createElement("form");
	    form.action="toSeat?sCode=toSeat&th=1&mvCode="+code+"&SI_SCREEN="+sc+"&TIME="+dateTime;
	    form.method="post";
	   
	    document.body.appendChild(form);
	    form.submit();
	}

</script>
</html>
