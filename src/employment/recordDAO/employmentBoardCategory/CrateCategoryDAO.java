package src.employment.recordDAO.employmentBoardCategory;


import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import src.database.Database;


public class CrateCategoryDAO {
	
	private Database db = new Database();
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;

	// insert into employment_board_category ... values ...
	public void create(int maincategoryId, int subcategoryId, String categoryName) {
		
		// employment_board_id -> 자동 증가 컬럼.
		String sql = ""+
		"insert into employment_board_category ("
		+ "maincategory_id,"
		+ "subcategory_id,"
		+ "category_name"
		+ ") values ("
		+ "?, ?, ?"
		+ ");";
		
		try {
			
			conn = db.connect();
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, maincategoryId);
			pstmt.setInt(2, subcategoryId);
			pstmt.setString(3, categoryName);
			
			int rows = pstmt.executeUpdate();
			if (rows == 1) {
				System.out.println("성공적으로 저장되었습니다.");
			} else {
				System.out.println("저장에 실패하였습니다.");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {					
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
