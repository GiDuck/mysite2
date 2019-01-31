package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douzone.mvc.util.DBManager;
import com.douzone.mysite.vo.UserVo;

public class UserDao {
	public UserVo get(String email, String password) {
		UserVo result = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = DBManager.getConnection();

			String sql = " select no, name" + "   from tbL_user" + "  where email=?" + "    and password=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, email);
			pstmt.setString(2, password);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				long no = rs.getLong(1);
				String name = rs.getString(2);

				result = new UserVo();
				result.setNo(no);
				result.setName(name);
			}
		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			DBManager.disconnect(conn, pstmt, null, rs);

		}

		return result;
	}

	public UserVo get(Long no) {
		UserVo result = new UserVo();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = DBManager.getConnection();

			String sql = " select * from tbL_user where no = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				
				result.setNo(rs.getLong(1));
				result.setName(rs.getString(2));
				result.setEmail(rs.getString(3));
				result.setPassword(rs.getString(4));
				result.setGender(rs.getString(5));
				result.setJoinDate(rs.getString(6));
			}

			return result;

		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			DBManager.disconnect(conn, pstmt, null, rs);
		}

		return result;
	}

	public int insert(UserVo vo) {
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBManager.getConnection();

			String sql = " insert" + "   into tbL_user" + " values ( null, ?, ?, ?, ?, now() )";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());

			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			DBManager.disconnect(conn, pstmt, null, null);

		}

		return count;
	}

	public boolean update(UserVo vo) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "update tbL_user set name = ?, email = ?, " + "password = ? , gender = ? where no = ?";

		try {

			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());
			pstmt.setLong(5, vo.getNo());
			pstmt.executeUpdate();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.disconnect(conn, pstmt, null, null);

		}

		return false;

	}

}