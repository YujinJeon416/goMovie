# goMovie
팀프로젝트 #2 영화예매사이트

<개발도구>
- DB설계 - Oracle / SQL Developer
- IDE 도구 - Eclipse
- 웹 서버 - Apache Tomcat 9.0
- 언어 - JAVA / JavaScript / HTML

<프로젝트 소개>
- 가상의 영화관 웹페이지
- 상영중인 영화의 이미지를 메인 화면에 표시
- 사용자가 날짜/시간/좌석을 선택하여 영화예약

<담당 기능> : Reservation.java / dateChoice.jsp
- 영화 선택 시 오늘 날짜 포함 앞으로 6일간의 날짜를 ArrayList로 리턴하는 메소드 makeDate 생성
- makeDate를 파라미터로 하여 ArrayList의 원소 개수만큼 button을 생성하고 그 value를 makeDate의 i번째 날짜로 갖도록 String을 리턴하는 makeDateList 생성
- DB에 입력한 테스트 데이터가 몇 개의 날짜, 몇 개의 영화에만 적용되며 어느 날짜에 상영하는지를 프론트엔드에서 표시해 주지 못함이 불편함에 기능 추가 제안
- 해당 페이지에 영화 정보를 표시하기 위해 영화코드를 get방식으로 url에 전달함에 착안, 7개의 날짜와 영화코드를 파라미터로 하여 영화가 상영하는 날짜만 ArrayList로 표시하는 메소드 ifSelling 생성
- ifSelling을 json으로 만든 후, ModelAndView의 메소드 중 addObject를 사용하여 el태그 selling에 json 데이터를 추가
- 프론트엔드에서는 body가 load될 때 수행하는 자바스크립트 init 함수 작성
> json을 파싱한 값을 mvSelling이라는 let 변수에 할당  
> -> 버튼 클릭을 유도하게끔 style의 cursor를 pointer로 설정  
> -> 버튼의 값이 mvSelling에 포함되어 있지 않은 경우 style의 cursor를 default로 설정, 버튼의 배경색과 글자색도 회색/검정색으로 설정
