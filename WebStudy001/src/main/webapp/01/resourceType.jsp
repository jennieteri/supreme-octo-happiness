<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>01/resourceType.jsp</title>
</head>
<body>
<h4><%= application.hashCode() %></h4>
<h4>contextParam1 : <%=application.getInitParameter("contextParam1") %></h4>
<h4>자원의 분류</h4>
<pre>
	1. 자원의 생성 시점과 서비스 대상에 따른 분류
		1) 정적자원 : 서버가 구동되기 전에 이미 생성되어 있는 파일, 파일 자체가 서비스 대상이 되는 컨텐츠 
			ex) *.html, *.js, *.css, *.pdf
		2) 동적자원 : 클라이언트의 요청에 의해 특정 어플리케이션이 실행되는 시점에 실행 결과를 서비스의 대상으로, 
			MIME 텍스트 : 실행 결과로 동적 생성되는 컨텐츠의 종류를 표현할 수 있는 문자열.
						maintype/subtype;charset = UTF-8 
			ex) /now1.do 요청에 의해 ServerTimeServlet이 실행되고 그 결과가 컨텐츠가 생성.(text.html)
			    /now2.do 요청에 의해 ServerTimeForJsonServlet이 실행되고 그 결과가 컨텐츠가 생성(application/json).
 	2. 자원의 위치와 접근 방법에 따른 분류 
 		1) File System resource : 파일 시스템 상의 절대 경로(물리 경로)를 통해 자원을 식별 
 			ex) D:\00.medias\movies\The_Power_of_Teamwork.mp4
 		2) Classpath resource : classpath 이후의 전체 경로를 의미하는 qualified name(논리 경로)의 형태로 자윈을 식별 
 			ex) /kr.or.ddit.images/cute1.PNG
 			ex) kr.or.ddit.servlet01.HelloServlet
 			클래스패쓰 이후에 경로인 퀄러파인드네임으로 접근 
 			클래스 패쓰 이후 퀄러파인드 네임 경로 (논리경로)
 		3) Web resource : 네트워크 상에서 url (논리 경로)을 통해서 식별할 수 있는 자원
 		   ex) http://localhost/WebStudy001/resources/images/cat1.jpg
 		       클라이언트 서버 시스템이 나와야함 
 		       
 		       http://localhost:80 까지 자원의 출처 = 자원의 오리진 (출처가 생략되어 있으면 동일한 출처를 사용하겠다.)
 		   1),2) 어플리케이션 필요 3) 어플리케이션 불필요 
	
</pre>
</body>
</html>