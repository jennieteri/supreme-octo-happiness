package kr.or.ddit.servlet03;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImageFormServlet extends HttpServlet{
	private File folder;
	private ServletContext application;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config); 
		application = getServletContext();
		//하나의 코드를 만들더라고 변경할 때 유연하게 대처하려고 한번 만든 코드를 바꾸지 않으려고 
		//xml에 경로를 만들어 놓았음 
		folder = Optional.ofNullable(application.getInitParameter("folderQN")) //파라미터 값이 없으면 가져다 버리고 
				.map(qn ->this.getClass().getResource(qn)) //원래 데이터를 다른 형태로 바꾸는것 map
				//체인 메소드 메소드를 연결한다. 반환하는 값이 객체인데 이게 람다식임?
				// folderQN 을qn을 받아서 메소드 실행해서 반환값이 나온것을 밑에서 줄줄이 받아서 결국에 그 받은 반환값을 folder로 받는다 
				//매핑할 떄 사용하는 메소드가 map  
				//null값이 반환되면 밑에 껏이 실행이 안됨
				.map (url->url.getFile())
				.map(rp-> new File(rp))
				.orElseThrow(()->new ServletException("폴더가 존재하지 않음."));
		System.out.println(folder.getAbsolutePath());
		
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// data -> information -> content
		String[] fileNames = folder.list((d,n)->Optional.ofNullable(application.getMimeType(n))
												 .map(m->m.startsWith("image/")) //값이 있는지 확인 
												 .orElse(false)
				);
		
		//가변형 파라미터 암꺼나 사용할 수 있음 배열도 사용할 수 있음  
		String pattern = "<option>%s</option>";
		String options = Arrays.stream(fileNames)
		//반복문이 필요한게 아니라 매핑이 필요한거임 그래서 for 대신 map을 사용함 
		// sugar syntax : 코드의 간결성을 지향하는 경향.
					.map(n->String.format(pattern,n))
					.collect(Collectors.joining("\n"));
					//여러개를 collect로 모아놓음 
		
		
	StringBuffer html = new StringBuffer();
	html.append("<html>                                      ");
	html.append("<body>                                      ");
	html.append("<form method='get' action ='./streaming.hw'>");
	html.append("<select name= 'image' onchange ='this.form.submit()'>                      ");
	html.append(options);
	html.append("</select>                                   ");
	html.append("</form>                                     ");
	html.append("</body>                                     ");
	html.append("</html>                                     ");
	
	resp.setContentType("text/html;charset='utf-8'");
	resp.getWriter().print(html);
	}

}
