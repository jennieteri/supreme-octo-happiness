package kr.or.ddit.servlet01;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HelloServlet
 */
@WebServlet("/hello.do")
//접근가능한 웹이 되려면 우리가 만들어줘야함 
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelloServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = System.getProperty("username");
		String password = System.getProperty("password");
		String tomcatPath = System.getProperty("catalina.base");
		//데이터를 가공해서 클라이언트가 보기 좋게 한다 => 컨텐츠 
		
		//출ㄹㄱ(문자 기반으로 출력 - stream byte 단위, writer char 단위)
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<pre>"); //탭키 엔터키 등등 입력값 누른대로 들어감 
		out.printf("username: %s\n",username);
		out.printf("password: %s\n",password);
		out.printf("tomcatPath: %s\n",tomcatPath);
		out.println("</pre>"); //탭키 엔터키 등등 입력값 누른대로 들어감 
		out.println("</body>");
		out.println("</html>");
		
	}

}
