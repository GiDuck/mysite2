package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.douzone.mvc.util.AppProps;
import com.douzone.mvc.util.DBManager;
import com.douzone.mysite.vo.GuestbookVo;

public class GuestbookDao {
	
	
	public GuestbookDao() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int delete( GuestbookVo vo ) {
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBManager.getConnection();

			String sql = 
				" delete" + 
				"   from tbl_guestbook" + 
				"  where no=?" +
				"    and password=?";
			pstmt = conn.prepareStatement( sql );

			pstmt.setLong( 1, vo.getNo() );
			pstmt.setString( 2, vo.getPassword() );

			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			DBManager.disconnect(conn, pstmt, null, null);

		}

		return count;		
	}
	
	public int insert(GuestbookVo vo) {
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBManager.getConnection();

			String sql = 
				" insert" + 
				"   into tbl_guestbook" + 
				" values ( null, ?, ?, ?, now() )";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getMessage());

			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			DBManager.disconnect(conn, pstmt, null, null);

		}

		return count;
	}

	public List<GuestbookVo> getList() {
		List<GuestbookVo> list = new ArrayList<GuestbookVo>();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBManager.getConnection();

			// Statement 객체 생성
			stmt = conn.createStatement();

			// SQL문 실행
			String sql =
				"   select no," + 
				"          name," + 
				"	       message," + 
				"     	   date_format(reg_date, '%Y-%m-%d %h:%i:%s')" + 
				"     from tbl_guestbook" + 
				" order by reg_date desc";
			rs = stmt.executeQuery( sql );

			// 결과 가져오기(사용하기)
			while (rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String message = rs.getString(3);
				String regDate = rs.getString(4);

				GuestbookVo vo = new GuestbookVo();
				vo.setNo(no);
				vo.setName(name);
				vo.setMessage( message );
				vo.setRegDate( regDate );

				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			DBManager.disconnect(conn, null, stmt, rs);
		}

		return list;
	}

	
}
