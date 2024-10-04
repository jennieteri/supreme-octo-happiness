package kr.or.ddit.servlet03;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZonedDateTime;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  alt +shift+ j
 *  서블릿 스펙 (why/ what / how)
 *  동적 자원을생성하기 위해서 서블릿을 사용하고 있음 
 *  사용 목적 : 웹상의 블특정 다수의 클라이언트가 발생시키는 다양한 요청을 처리하기 위해 
 *            동적 자원(컨텐츠)를 생성할 수 있는 자바 기반의 백 엔드 모듈이 필요함. 
 *  서블릿이란 ? 웹 상의 요청을 받고 , 분석하고 , 그에 적합한 동적 응답을 생성할 수 있는 자바 객체(가 만족해야 하는 조건에 대한 명세서)  
 *            --> HttpServlet    
 *  서블릿 개발 단계
 *  1. HttpServlet의 확장(extends) 클래스 구현 
 *  2. 필요에 따라 적절한 callback 메소드를 재정의 
 *  	*** 서블릿 컨테이너의 역할과 특성
 *  		: 서블릿 생명주기 관리자, 요청이 발생하면 해당 서블릿의 독점 콜백 메소드를 실행함.
 *  		 (서블릿 이 생성될 수도 있고 필요없다면 버릴 수 있는거 , 조건에 맞는것이 들어오면 그 서블릿을 연걸해주는것도 콘테이너 )
 * 			1) 서블릿 인스턴스를 생성하는 시점: lazy 에서 eager로 바꿀 때에 load-on-startup을 사용하는데 web.xml에 쓰기  <load-on-startup>1</load-on-startup>
 * 				- lazy-loading(지연로딩, 기본) : 서버가 구동되는 시점에 인스턴스를 생성하지 않고, 최초의 요청이 발생했을 때로 생성 시점을 미뤄둠 . 
 * 				- eager -loading(즉시로딩) : 서버가 구동되는 시점에 인스턴스를 미리 생성함.
 * 			2) 서블릿은 싱글톤으로 관리함. 
 * 			3) 서블릿의 생명주기 관리 과정에서 발생하는 이벤트에서 관련 콜백 메소드를 실행함.
 * 			- 생명주기 콜백 : init(서블릿이 생성되고 초기화 작업 종료 직후- 1번 실행(싱글톤)), destroy(서블릿 소멸 직전) 
 * 			- 요청 콜백 요청 들어올 때마다 반복적으로
 * 				service : 요청이 발생을 하면, 컨테이너가 직접 실행하는 메소드로, 
 * 							http method를 판단하고, 그에 맞는 doXXX 계열의 메소드를 호출함. 
 * 				doXXX : 하나의 http method 를 별개로 처리하기 위한 메소드.
 * 			4) 서블릿의 인스턴스 생성될 때 초기화 파라미터 전달함. 
 *  3. 서블릿 컨테이너가 서블릿을 관리 (운영)할 수 있는 설정 
 *   1) 컨테이너에 서블릿 클래스 등록
 *   	2.x: web.xml(배포 서술자 - deployment descriptor)에 servlet -> servlet-name, servlet-class 등록 
 *    	3.x: @WebServlet 마커 어노테이션 (마킹만 해놓은거 등록이 된거야 하고 표현이 된거임)
 *    
 *    어노테이션은 순서를 알 수가 없엇 불안정함 
 *   2) 서블릿 매핑을 통해 컨테이너가 서블릿을 실행할 수 있는 조건 설정 
 *   	2.x: web.xml에 servlet-mapping -> servlet- name, url-pattern
 *   	3.x: @WebServlet(url) 싱글벨류 어노테이션 (value가 생략되어 있음), 멀티벨류 어노테이션  -> 속성의 이름을 명시해야하냐 안하냐 차이
 *  	web.xml을 간소화 한게 ^ 이거 입니다. 
 *  4. 컨테이너 리로드 
 *  
 * *** 서블릿 스펙에서 제공되는 객체 
 * 1. HttpServletRequest : http 프로토콜로 패키징된 요청에 대한 정보를 캡슐화된 객체 
 * 2. HttpServeltResponse : http 프로토콜로 패키징된 응답에 대한 정보를 캡슐화된 객체 -요청을 받으면 생기고 응답이 나가면 사라짐 
 * 3. HttpSession :  한 클라이언트가 하나의 에이전트(브라우저) 프로그램으로 형성한 한 세션에 대한 정보를 캡슐화한 객체
 * 4. ServletContext : context(어플리케이션 자체)/ 하나의 웹 애플리케이션(컨텍스트)에 대한 정보를 캡슐화한 객체 
 * 						하나의 웹어플리케이션에 대해 *싱글톤으로 생성되는 객체. (하나만 관리 되기 때문에 전역변수로 사용가능) - 서버 구동되면 생성 서버 꺼지면 사라짐 
 * 하나의 어플리케이션을 context 라고 부름 
 * 서버 생명 주기가 다 다름 
 * 
 * 
 */
//@WebServlet(name = "ste", urlPatterns = {"/desc","/DESC"})
public class DescriptionServlet extends HttpServlet {


	private String param1Value;
	private ServletContext application;

	public DescriptionServlet() {
		super();
		System.out.printf("%s 객체 생성\n",this.getClass().getName());
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		
		super.init(config); //절대 지우지 않기
		application= getServletContext(); //싱글톤은 전역변수로 빼도 상관없음 
		//한번실행
		System.out.printf("%s 서블릿 초기화 완료 \n",this.getClass().getSimpleName());
		param1Value = config.getInitParameter("param1");
		if(param1Value != null && !param1Value.isEmpty()) { //받아오는 것이 문자열이면 비어있는지 먼저 확인해보기 
			System.out.printf("param1의 값 : %s\n",param1Value);
		}
	}

	

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//서비스가 doGet 보다 먼저 실행 요청이 들어올때마다 실행 
		//하나의 쓰레드 안에서 실행됨 
		System.out.println("요청 접수, http request method 판단 ");
		super.service(req, resp);
		System.out.println("요청 처리 완료");
		//doGet이 종료되고 service로 돌아옴 service 객체 안에서 doGet에서 호출
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.printf("Get 메소드 요청 처리,실행 쓰레드명: %s\n",Thread.currentThread().getName());
		//요청이 들어올때마다 실행
		//절대로 전역변수를 쓰면 안된다. (서블릿 안에서는 사용하지마라)

		ZonedDateTime now = ZonedDateTime.now();
		resp.setContentType("text/plain;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		out.println(now.toString());
		out.println(application.hashCode());
		out.println(application.getInitParameter("contextParam1"));
	}

	@Override
	public void destroy() {
		System.out.printf("%s 서블릿 소멸 \n",this.getClass().getSimpleName());
	}

	
	
	

}
