<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<!DOCTYPE HTML>
<html>
<head>
<title>좌석 선택</title>
<link href="/resources/css/seat.css" rel="stylesheet" />
</head>
<body onLoad="init()">
	<section id="seatZone"></section>
</body>
<script>
	function init() {
		let sZone = document.getElementById("seatZone");
		let seatInfo = JSON.parse('${seat}');

		let rows;
		let cols; // 아래에서 고객이 볼 좌석번호 생성 위해 필드 변수로 선언

		for (rowIndex = 0; rowIndex < 20; rowIndex++) {
			rows = document.createElement("div"); // 서버단이라면 위에 올려놓고 재활용할 수 있게 하는 게 낫다.
			// 사용자 pc에서 작동하는 프론트엔드 자바스크립트이기 때문에 for문 안에서만 살아있도록 하는 방법을 사용해도 무방하다.
			rows.className = "row";
			for (colIndex = 0; colIndex < 20; colIndex++) {
				cols = document.createElement("div");
				cols.setAttribute("name", "seat");
				cols.className = "able";

				rows.appendChild(cols);
			}
			sZone.appendChild(rows);
		}

		/* Inactive Seat 배치 */
		let seat = document.getElementsByName("seat");

		for (index = 0; index < seatInfo.length; index++) {
			seat[seatInfo[index].seatNum - 1].className = (seatInfo[index].seatType == "H") ? "hall"
					: ((seatInfo[index].seatType == "R") ? "reserve" : "repair");
		}

		/* 하다가 그만둔 것... 다음에 다시 해 보자 */
		// alert(sZone.childElementCount); // rows 개수 체크 -> 20
		// alert(rows.childElementCount); // cols 개수 체크 -> 20

		// 		for (index = 0; index < seatInfo.length; index++) {
		// 			if (seat[index].className == "able") {
		// // 				let str;
		// // 				for (line = 0; line < sZone.childElementCount / 2; line++) {
		// // 					str = String.fromCharCode(line + 65) + line;
		// // 				}
		// 				seat[index].setAttribute("onClick", "selectSeat(" + (index + 1) + ", " + str + ")");
		// 			}
		// 		}
		
		
		/* Active Seat 배치 */
		let rowChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		let cnt = -1;
		for (index = 0; index < seat.length; index++) {
			if (seat[index].className != "hall") {
				cnt++;
				let first = rowChar.substr(parseInt(cnt / 10), 1);
				let second = (cnt % 10) + 1;
				if (seat[index].className == "able") {
					seat[index].setAttribute("onClick", "selectSeat("
							+ (index + 1) + ", '" + first + second + "')");
				}
			}
		}
	}

	function selectSeat(sNumber, sReal) {
		let type = prompt("성인:A 청소년:J 어린이:C");
		alert(sNumber + " : " + sReal + " : " + type);
	}
</script>
</html>