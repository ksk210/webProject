package org.embed.mvc.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

   public static Connection getConnection() {
      
      Connection conn = null;
      
      try{
         String url = "jdbc:mysql://localhost:3306/embed";
         String user = "root";
         String password = "1111";
         
         Class.forName("com.mysql.cj.jdbc.Driver");
         conn = DriverManager.getConnection(url, user, password);
               
      }catch(Exception e){
         System.out.println("데이터베이스 연결이 실패되었습니다.<br>");
         e.printStackTrace();
      }
      
      return conn;
   }
}
