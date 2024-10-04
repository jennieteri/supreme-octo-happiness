package kr.or.ddit.servlet03;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Optional;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImageStreamingServlet extends HttpServlet {

	private File folder;
	private ServletContext application;

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config); 
		application = getServletContext();
		//하나의 코드를 만들더라고 변경할 때 유연하게 대처하려고 한번 만든 코드를 바꾸지 않으려고 
		//xml에 경로를 만들어 놓았음 
		folder = Optional.ofNullable(application.getInitParameter("folderQN"))
				.map(qn ->this.getClass().getResource(qn))
				//매핑할 떄 사용하는 메소드가 map  
				//null값이 반환되면 밑에 껏이 실행이 안됨 
				.map (url->url.getFile())
				.map(rp-> new File(rp))
				.orElseThrow(()->new ServletException("폴더가 존재하지 않음."));
		System.out.println(folder.getAbsolutePath());


	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		File imageFile = Optional.ofNullable(req.getParameter("image"))
				.map(p->new File(folder,p))
				.filter(f->f.exists())
				.orElseThrow(()->new ServletException("필수 피라미터 누락")); //여기 까지가 파일 객체가 만들어짐 

		//try(Closable) {}catch(exception) {}finally {}, try with resource 구문 
		resp.setContentType(application.getMimeType(imageFile.getName()));
		try(
				OutputStream os = resp.getOutputStream();
				
				){
					Files.copy(imageFile.toPath(), os);
		}
	}
}
