package src.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public Connection connect() {
        Connection conn = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(

                "jdbc:mysql://localhost:3306/miniproject4",
                "root",
                "10041004"

            );
            //System.out.println("데이터베이스 연결 성공");
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
            throw new RuntimeException("데이터베이스 연결 실패;");
        }
        return conn;
    }
}
