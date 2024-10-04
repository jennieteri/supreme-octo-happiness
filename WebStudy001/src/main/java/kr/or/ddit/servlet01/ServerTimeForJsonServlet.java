package kr.or.ddit.servlet01;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZonedDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/now2.do")
// 데이터는 같음 재료는 같음 
// 포장하는 결과만 다름 content가 다름 
// 왜 do로 바꿨을까요 ? html로 할 수 있는데 또 다른 방법이 뭐가 있는지 보려고 
public class ServerTimeForJsonServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//생성자 대신 생성자를 생성할 때에 팩토리 메소드를 사용함
		ZonedDateTime now = ZonedDateTime.now();
		String pattern = "\"now\": \"현재 시간 : %s\""; 
		// "{"+ now + "}" 이렇게 쓰기 너무 번거로워서 "\"는 따옴표 출력할 때 사용 
		// %s 는 String.format() 메서드를 사용할 떄 다른 값으로 대체되는 플레이스홀더 
		/*예를 들어, String.format(pattern, now.toString()) 부분에서 
		now.toString()이 "현재 시간 : %s"에서 %s 부분에 대체되어 결과적으로 
		"현재 시간 : [현재 시간]"과 같은 문자열이 만들어집니다.*/
		
		// 한글은 1byte만 표현할 수 없어서 깨짐요 
		StringBuffer json = new StringBuffer();
		json.append("{");
		json.append(String.format(pattern, now.toString()));
		//toString 문자열로 반환
		json.append("}");
		/*MIME(Multipurposed Internet Email Extension) text
		 main_type/sub_type; charset = utf-8(main_type이 문자열이라면)
		 ex) text/html, test/css , image/ jpeg*/
		resp.setContentType("application/json;charset=utf-8"); 
		//왜 text/html에서 바꿧을가 ? json 형식으로 바꾸기 위해 
		//내가 개발하고 있는 개발환경을 properties먼저 확인한 후 에 utf-8로 바꿩야함
		PrintWriter out = resp.getWriter();
		// data: 가공하기 전의 raw (dao - data access object)
		// information: data 를 가공해 만들어진 로직의 결과물(service - 레시피) 
		// content: 클라이언트 상황에 맞게 표현된 메세지 
		// process 처리과정 
		out.print(json);
		
	}

}
