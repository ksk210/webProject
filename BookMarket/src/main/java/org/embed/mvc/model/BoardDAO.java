package org.embed.mvc.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.embed.mvc.database.DBConnection;

public class BoardDAO {

   private static BoardDAO instance;
   
   private BoardDAO() {
      // TODO Auto-generated constructor stub
   }
   
   public static BoardDAO getInstance() {
      if (instance == null) {
         instance = new BoardDAO();
      }
      
      return instance;
   }
   
   public int getListCount(String items, String text) {
      int x = 0;
      
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      
      String sql;
      
      if (items == null && text == null) {
         sql = "SELECT count(*) FROM bbs";
      } else {
         sql = "SELECT count(*) FROM bbs WHERE " + items + " like '%" + text + "%'";
      }
      
      try {
         conn = DBConnection.getConnection();
         pstmt = conn.prepareStatement(sql);
         rs = pstmt.executeQuery();
         
         if (rs.next()) {
            x = rs.getInt(1);
         }
      } catch (Exception e) {
         // TODO: handle exception
         System.out.println("getListCount() 에러 : " + e);
      } finally {
         try {
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
         } catch (Exception e2) {
            // TODO: handle exception
            e2.printStackTrace();
         }
      }
      
      return x;
   }
   
   public ArrayList<BoardDTO> getBoardList(int page, int limit, String items, String text){
      
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      
      ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
      int total_record = getListCount(items, text);
      int start = (page - 1) * limit;
      int index = start + 1;
      String sql;
      
      if (items == null && text == null) {
         sql = "SELECT * FROM bbs ORDER BY num DESC";
      } else {
         sql = "SELECT * FROM bbs WHERE " + items + " like '%" + text + "%' ORDER BY num DESC";
      }
      
      try {
         
         conn = DBConnection.getConnection();
         pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
         rs = pstmt.executeQuery();
         
         while (rs.absolute(index)) {
            
            BoardDTO board = new BoardDTO();
            board.setNum(rs.getInt("num"));
            board.setId(rs.getString("id"));
            board.setName(rs.getString("name"));
            board.setSubject(rs.getString("subject"));
            board.setContent(rs.getString("content"));
            board.setRegist_day(rs.getString("regist_day"));
            board.setHit(rs.getInt("hit"));
            board.setIp(rs.getString("ip"));
            
            list.add(board);
            
            if (index < (start + limit) && index <= total_record) {
               index++;
            }else {
               break;
            }
         }
         
         return list;
      } catch (Exception e) {
         // TODO: handle exception
         e.printStackTrace();
      } finally {
         try {
            if(rs != null) rs.close();   
            if(pstmt != null) pstmt.close();   
            if(conn != null) conn.close();   
         } catch (Exception e2) {
            // TODO: handle exception
            e2.printStackTrace();
         }
      }
      
      return null;
   }
   
   public String getLoginNameById(String id) {
      
      String name = null;
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      
      String sql = "SELECT * FROM member WHERE id = ? ";
      
      try {
         conn = DBConnection.getConnection();
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, id);
         rs = pstmt.executeQuery();
         
         if (rs.next()) {
            name = rs.getString("name");
            
            return name;
         }
      } catch (Exception e) {
         // TODO: handle exception
         System.out.println("getLoginNameById() 에러 : " + e);
      } finally {
         try {
            if(rs != null) rs.close();   
            if(pstmt != null) pstmt.close();   
            if(conn != null) conn.close();   
         } catch (Exception e2) {
            // TODO: handle exception
            e2.printStackTrace();
         }
      }
      
      return null;
   }
   
   public void insertBoard(BoardDTO board) {
      
      Connection conn = null;
      PreparedStatement pstmt = null;
      
      try {
         conn = DBConnection.getConnection();
         String sql = "INSERT INTO bbs VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, board.getNum());
         pstmt.setString(2, board.getId());
         pstmt.setString(3, board.getName());
         pstmt.setString(4, board.getSubject());
         pstmt.setString(5, board.getContent());
         pstmt.setString(6, board.getRegist_day());
         pstmt.setInt(7, board.getHit());
         pstmt.setString(8, board.getIp());
         
         int n = pstmt.executeUpdate();
         
      } catch (Exception e) {
         // TODO: handle exception
         System.out.println("insertBoard() 에러 : " + e);
      } finally {
         try {
            if(pstmt != null) pstmt.close();   
            if(conn != null) conn.close();   
         } catch (Exception e2) {
            // TODO: handle exception
            e2.printStackTrace();
         }
      }
   }
   
   public void updateHit(int num) {
      
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      
      try {
         conn = DBConnection.getConnection();
         String sql = "SELECT hit FROM bbs WHERE num = ?";
         
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, num);
         rs = pstmt.executeQuery();
         
         int hit = 0;
         
         if (rs.next()) {
            hit = rs.getInt("hit") + 1;
         }
         
         sql = "UPDATE bbs SET hit = ? WHERE num = ?";
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, hit);
         pstmt.setInt(2, num);
         
         int n = pstmt.executeUpdate();
      } catch (Exception e) {
         // TODO: handle exception
         System.out.println("updateHit() 에러 : " + e);
      } finally {
         try {
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();   
            if(conn != null) conn.close();   
         } catch (Exception e2) {
            // TODO: handle exception
            e2.printStackTrace();
         }
      }
   }
   
   public BoardDTO getBoardByNum(int num, int page) {
      
      BoardDTO board = null;
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      
      updateHit(num);
      String sql = "SELECT * FROM bbs WHERE num = ?";
      
      try {
         conn = DBConnection.getConnection();
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, num);
         rs = pstmt.executeQuery();
         
         if (rs.next()) {
            board = new BoardDTO();
            board.setNum(rs.getInt("num"));
            board.setId(rs.getString("id"));
            board.setName(rs.getString("name"));
            board.setSubject(rs.getString("subject"));
            board.setContent(rs.getString("content"));
            board.setRegist_day(rs.getString("regist_day"));
            board.setHit(rs.getInt("hit"));
            board.setIp(rs.getString("ip"));
         }
         
         return board;
      } catch (Exception e) {
         // TODO: handle exception
         System.out.println("getBoardByNum() 에러 : " + e);
      } finally {
         try {
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();   
            if(conn != null) conn.close();   
         } catch (Exception e2) {
            // TODO: handle exception
            e2.printStackTrace();
         }
      }
      
      return null;
   }
   
   public void updateBoard(BoardDTO board) {
      
      Connection conn = null;
      PreparedStatement pstmt = null;
      
      try {
         conn = DBConnection.getConnection();
         String sql = "UPDATE bbs SET name=?, subject=?, content=? WHERE num=?";
         
         pstmt = conn.prepareStatement(sql);
         conn.setAutoCommit(false);
         
         pstmt.setString(1, board.getName());
         pstmt.setString(2, board.getSubject());
         pstmt.setString(3, board.getContent());
         pstmt.setInt(4, board.getNum());
         
         int n = pstmt.executeUpdate();
         conn.commit();
      } catch (Exception e) {
         // TODO: handle exception
         System.out.println("updateBoard() 에러 : " + e);
      } finally {
         try {
            if(pstmt != null) pstmt.close();   
            if(conn != null) conn.close();   
         } catch (Exception e2) {
            // TODO: handle exception
            e2.printStackTrace();
         }
      }
   }
   
public void deleteBoard(int num) {
      
      Connection conn = null;
      PreparedStatement pstmt = null;
      
      try {
         conn = DBConnection.getConnection();
         String sql = "DELETE FROM bbs WHERE num=?";
         
         pstmt = conn.prepareStatement(sql);
         conn.setAutoCommit(false);
      
         pstmt.setInt(1, num);
         
         int n = pstmt.executeUpdate();
         conn.commit();
      } catch (Exception e) {
         // TODO: handle exception
         System.out.println("deleteBoard() 에러 : " + e);
      } finally {
         try {
            if(pstmt != null) pstmt.close();   
            if(conn != null) conn.close();   
         } catch (Exception e2) {
            // TODO: handle exception
            e2.printStackTrace();
         }
      }
   }
}


















