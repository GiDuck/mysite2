package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.douzone.mvc.util.DBManager;
import com.douzone.mysite.vo.ReplyVo;
import com.douzone.mysite.vo.UserVo;
import com.mysql.cj.xdevapi.Statement;

public class ReplyDao {

	private Connection conn;
	private PreparedStatement pstmt;
	private Statement stmt;
	private ResultSet rs;

	public boolean insert(ReplyVo vo) {

		String sql = "INSERT INTO tbl_reply VALUES (0, ?, ?, ?, ?, ?)";
		try {

			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, vo.getRp_refBo());
			pstmt.setLong(2, vo.getRp_writer().getNo());
			pstmt.setString(3, vo.getRp_content());
			pstmt.setTimestamp(4, vo.getRp_timestamp());
			pstmt.setLong(5, vo.getRp_ref());
			pstmt.executeUpdate();
			return true;

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {

			DBManager.disconnect(conn, pstmt, null, rs);

		}

		return false;
	}

	public List<ReplyVo> select(long boNum) {

		String sql = "SELECT rp.*, (select name from tbl_user where no = rp.rp_writer)  FROM TBL_REPLY rp WHERE rp_refBo = ?  order by rp_timestamp desc";
		List<ReplyVo> replies = new ArrayList<>();
		try {

			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, boNum);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				ReplyVo vo = new ReplyVo();
				vo.setRp_num(rs.getLong(1));
				vo.setRp_refBo(rs.getLong(2));
				UserVo user = new UserVo();
				user.setNo(rs.getLong(3));
				vo.setRp_content(rs.getString(4));
				vo.setRp_timestamp(rs.getTimestamp(5));
				vo.setRp_ref(rs.getLong(6));
				user.setName(rs.getString(7));
				vo.setRp_writer(user);

				replies.add(vo);

			}

			return replies;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.disconnect(conn, pstmt, null, rs);


		}

		return null;
	}

	public ReplyVo selectSingleReply(long rpNum) {

		String sql = "SELECT rp.*, (select name from tbl_user where no = rp.rp_writer)  FROM TBL_REPLY rp WHERE rp_num = ?";
		try {

			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, rpNum);
			rs = pstmt.executeQuery();
			ReplyVo vo = new ReplyVo();

			while (rs.next()) {

				vo.setRp_num(rs.getLong(1));
				vo.setRp_refBo(rs.getLong(2));
				UserVo user = new UserVo();
				user.setNo(rs.getLong(3));
				vo.setRp_content(rs.getString(4));
				vo.setRp_timestamp(rs.getTimestamp(5));
				vo.setRp_ref(rs.getLong(6));
				user.setName(rs.getString(7));
				vo.setRp_writer(user);

			}

			return vo;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			DBManager.disconnect(conn, pstmt, null, rs);


		}

		return null;

	}

	public boolean update(ReplyVo vo) {

		String sql = "UPDATE TBL_REPLY SET rp_content = ? where rp_num = ? ";
		try {

			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getRp_content());
			pstmt.setLong(2, vo.getRp_num());
			int count = pstmt.executeUpdate();
			if (count > 0) {

				return true;

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			DBManager.disconnect(conn, pstmt, null, rs);


		}

		return false;
	}
	
	
	
	public boolean delete(long num) {
		
		String sql = "DELETE FROM TBL_REPLY WHERE rp_num = ? ";
		try {

			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, num);
			int count = pstmt.executeUpdate();
			if (count > 0) {

				return true;

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			DBManager.disconnect(conn, pstmt, null, rs);

		}

		return false;
		
		
	}

}
