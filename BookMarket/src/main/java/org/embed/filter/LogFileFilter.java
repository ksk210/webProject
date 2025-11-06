package org.embed.filter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class LogFileFilter implements Filter{
	
	PrintWriter writer;
	
	private String getCurrentTime() {
		DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTimeInMillis(System.currentTimeMillis());
		
		return formatter.format(calendar.getTime());
	}

	private String getURLPath(ServletRequest request) {
			
			HttpServletRequest req;
			String currentPath = "";
			String queryString = "";
			
			if (request instanceof HttpServletRequest) {
				req = (HttpServletRequest)request;
				currentPath = req.getRequestURI();
				queryString = req.getQueryString();
				queryString = queryString == null ? "" : "?" + queryString;
			}
			
			return currentPath + queryString;
		}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("BookMarket 종료...");
		writer.close();
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		String clientAddr = req.getRemoteAddr();
		long start = System.currentTimeMillis();
		writer.printf("접속한 클라이언트 IP : %s %n", clientAddr);
		writer.println("접근한 URL 경로 : " + getURLPath(req));
		writer.printf("요청 처리 시작 시각 : %s %n", getCurrentTime());
		
		
		filterChain.doFilter(req, resp);
		
		long end = System.currentTimeMillis();
		writer.printf("요청 처리 종료 시각 : %s %n", getCurrentTime());
		writer.println("요청 처리 소요 시간 : " + (end - start) + "ms");
		writer.println("-----------------------------------");
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("BookMarket 초기화...");
		String filename = filterConfig.getInitParameter("filename");
		
		if (filename == null) {
			throw new ServletException("로그 파일의 이름을 찾을 수 없습니다.");
		}
		
		try {
			writer = new PrintWriter(new FileWriter(filename, true), true);
		} catch (Exception e) {
			// TODO: handle exception
			throw new ServletException("로그 파일을 열 수 없습니다.");
		}
	}

	
	
}









