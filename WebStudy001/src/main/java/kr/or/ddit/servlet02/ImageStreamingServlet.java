package kr.or.ddit.servlet02;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ImageStreamingServlet extends HttpServlet{
	//콜백함수 함수를 등록하기만 하고 어떤 이벤트가 발생했거나 특정 시점에 도달했을 때 시스템에서 호출하는 함수!
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//classpath resource -> web resource 로 서비스 
		//기본 요청 방법 doGet 
		String imageFileName = req.getParameter("image");
		if(imageFileName == null || imageFileName.trim().length()==0) { //null 포인트 피해가고 있음 
			throw new ServletException("필수 파라미터 누락"); 
		}
		
		//jpg file mime type검색 
		ServletContext application = getServletContext();
		resp.setContentType("image/jpeg"); //jpg file mime type
		String mime = application.getMimeType(imageFileName);
		resp.setContentType(mime);
		
		//classpath resource -> web resource 로 서비스. 
		String imageLogicalPath= "/kr/or/ddit/images/"+imageFileName;
		InputStream is = this.getClass().getResourceAsStream(imageLogicalPath);
		if(is == null) {
			throw new ServletException("이미지 파일이 없음"); //존재하는 이미지여부 확인 
		}
		OutputStream out = resp.getOutputStream();//출력 스트림 (출력하기 위한)
		byte[] buffer  = new byte[1024];
		int cnt = -1; 
		
		while ((cnt = is.read(buffer))!=-1) { // -1을 EOF 문자라고 함 , //EOF를 만나기 전까지 반복
			out.write(buffer, 0, cnt); //읽을떄에는 read 기록할 때에는 write
			//null포인트는 점 앞에 있는 녀석임 
		}
		
		
		
	}
	

}
