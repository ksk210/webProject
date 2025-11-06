package org.embed.mvc.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.embed.mvc.model.BoardDAO;
import org.embed.mvc.model.BoardDTO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BoardController extends HttpServlet {

   private static final long serialVersionUID = 1L;
   static final int LISTCOUNT = 5;
   
   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      // TODO Auto-generated method stub
      doPost(req, resp);
   }
   
   @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      // TODO Auto-generated method stub
      String requestURI = req.getRequestURI();
      String contextPath = req.getContextPath();
      String command = requestURI.substring(contextPath.length());
      
      resp.setContentType("text/html; charset=utf-8");
      req.setCharacterEncoding("utf-8");
      
      if (command.equals("/BoardListAction.do")) {
         requestBoardList(req);
         RequestDispatcher rd = req.getRequestDispatcher("./board/list.jsp");
         rd.forward(req, resp);
      } else if (command.equals("/BoardWriteForm.do")) {
         requestLoginName(req);
         RequestDispatcher rd = req.getRequestDispatcher("./board/writeForm.jsp");
         rd.forward(req, resp);
      } else if (command.equals("/BoardWriteAction.do")) {
         requestBoardWrite(req);
         RequestDispatcher rd = req.getRequestDispatcher("/BoardListAction.do");
         rd.forward(req, resp);
      } else if (command.equals("/BoardViewAction.do")) {
         requestBoardView(req);
         RequestDispatcher rd = req.getRequestDispatcher("/BoardView.do");
         rd.forward(req, resp);
      } else if (command.equals("/BoardView.do")) {
         RequestDispatcher rd = req.getRequestDispatcher("./board/view.jsp");
         rd.forward(req, resp);
      } else if (command.equals("/BoardUpdateAction.do")) {
    	  requestBoardUpdate(req);
    	  RequestDispatcher rd = req.getRequestDispatcher("/BoardListAction.do");
    	  rd.forward(req, resp);
      } else if (command.equals("/BoardDeleteAction.do")) {
    	  requestBoardDelete(req);
    	  RequestDispatcher rd = req.getRequestDispatcher("/BoardListAction.do");
    	  rd.forward(req, resp);
	}
      
      
   }
   
   public void requestBoardList(HttpServletRequest req) {
      
      BoardDAO dao = BoardDAO.getInstance();
      ArrayList<BoardDTO> boardList = new ArrayList<BoardDTO>();
      
      int pageNum = 1;
      int limit = LISTCOUNT;
      
      if (req.getParameter("pageNum") != null) {
         pageNum = Integer.parseInt(req.getParameter("pageNum"));
      }
      
      String items = req.getParameter("items");
      String text = req.getParameter("text");
      
      int total_record = dao.getListCount(items, text);
      boardList = dao.getBoardList(pageNum, limit, items, text);
      
      int total_page;
      
      if (total_record % limit == 0) {
         total_page = total_record / limit;
         Math.floor(total_page);
      } else {
         total_page = total_record / limit;
         Math.floor(total_page);
         total_page += 1;
      }
      
      req.setAttribute("pageNum", pageNum);
      req.setAttribute("total_page", total_page);
      req.setAttribute("total_record", total_record);
      req.setAttribute("boardList", boardList);
   }
   
   public void requestLoginName(HttpServletRequest req) {
      
      String id = req.getParameter("id");
      BoardDAO dao = BoardDAO.getInstance();
      String name = dao.getLoginNameById(id);
      req.setAttribute("name", name);
   }
   
   public void requestBoardWrite(HttpServletRequest req) {
      BoardDAO dao = BoardDAO.getInstance();
      BoardDTO board = new BoardDTO();
      board.setId(req.getParameter("id"));
      board.setName(req.getParameter("name"));
      board.setSubject(req.getParameter("subject"));
      board.setContent(req.getParameter("content"));
      
      System.out.println(req.getParameter("name"));
      System.out.println(req.getParameter("subject"));
      System.out.println(req.getParameter("content"));
      
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd(HH:mm:ss)");
      String regist_day = formatter.format(new Date());
      
      board.setHit(0);
      board.setRegist_day(regist_day);
      board.setIp(req.getRemoteAddr());
      
      dao.insertBoard(board);
   }
   
   public void requestBoardView(HttpServletRequest req) {
      BoardDAO dao = BoardDAO.getInstance();
      int num = Integer.parseInt(req.getParameter("num"));
      int PageNum = Integer.parseInt(req.getParameter("pageNum"));
      
      BoardDTO board = new BoardDTO();
      board = dao.getBoardByNum(num, PageNum);
      
      req.setAttribute("num", num);
      req.setAttribute("page", PageNum);
      req.setAttribute("board", board);
   }
   
   public void requestBoardUpdate(HttpServletRequest req) {
	   
	   int num = Integer.parseInt(req.getParameter("num"));
	   int pageNum = Integer.parseInt(req.getParameter("pageNum"));
	   
	   BoardDAO dao = BoardDAO.getInstance();
	   BoardDTO board = new BoardDTO();
	   board.setNum(num);
	   board.setName(req.getParameter("name"));
	   board.setSubject(req.getParameter("subject"));
	   board.setContent(req.getParameter("content"));
	   
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd(HH:mm:ss)");
	   String regist_day = formatter.format(new Date());
	   
	   board.setHit(0);
	   board.setRegist_day(regist_day);
	   board.setId(req.getRemoteAddr());
	   
	   dao.updateBoard(board);
   }
   
   public void requestBoardDelete(HttpServletRequest req) {
	   
	   int num = Integer.parseInt(req.getParameter("num"));
	   int pageNum = Integer.parseInt(req.getParameter("pageNum"));
	   
	   BoardDAO dao = BoardDAO.getInstance();
	   dao.deleteBoard(num);
   }
}













