package kr.or.ddit.servlet01;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZonedDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/now1.do")
public class ServerTimeServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//생성자 대신 생성자를 생성할 때에 팩토리 메소드를 사용함
		ZonedDateTime now = ZonedDateTime.now();
		//가지고 있는 것을 콘텐츠로 바꿀거임
		//동적 자원 now  
		StringBuffer html = new StringBuffer();
		html.append("<html> ");
		html.append("<body> ");
		html.append("현재 서버의 시간: " +now.toString());
		html.append("</body>");
		html.append("</html>");
		resp.setContentType("text/html;charset=utf-8");
		//동적인 컨텐츠를 서블릿하기 위해서 인데 어떤 컨텐츠 인지를 모르니깐 MIME사용한다.
		//사용형태가 달라짐 MIME charset 을 안 썼더니 문자열이 ?? 이지랄로 나옴 
		//content는 달라진게 없음요

		PrintWriter out = resp.getWriter();
		// data: 가공하기 전의 raw (dao - data access object)
		// information: data 를 가공해 만들어진 로직의 결과물(service - 레시피) 
		// content: 클라이언트 상황에 맞게 표현된 메세지 (포장, 배달)
		// process 처리과정 
		out.print(html);
		
	}

}
