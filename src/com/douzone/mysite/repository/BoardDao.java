package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.douzone.mvc.util.DBManager;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;

public class BoardDao {

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private Statement stmt = null;
	private ResultSet rs = null;

	public boolean delete(long boNum) {

		try {
			conn = DBManager.getConnection();

			String sql = "delete from tbl_reply where rp_refBo = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, boNum);
			pstmt.executeUpdate();

			sql = "select bo_dept from tbl_board where bo_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, boNum);
			rs = pstmt.executeQuery();

			long dept = -1L;

			if (rs.next()) {

				dept = rs.getLong(1);

			}

			if (dept == 0) {

				sql = "delete from tbl_board where bo_ref = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, boNum);
				pstmt.executeUpdate();

			} else {

				sql = "delete from tbl_board where bo_num = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, boNum);
				pstmt.executeUpdate();

			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {

			DBManager.disconnect(conn, pstmt, stmt, rs);

		}

		return false;
	}

	public boolean insert(BoardVo vo) {

		try {
			String sql = null;
			conn = DBManager.getConnection();

			sql = "update tbl_board set bo_order = bo_order + 1 where bo_ref = ? and bo_order >= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, vo.getBo_ref());
			pstmt.setLong(2, vo.getBo_order() + 1);
			pstmt.executeUpdate();

			if (vo.getBo_ref() > 0) {
				sql = "insert into tbl_board values (?, ?, ?, ?, ?, ?, ?, "
						+ "(select max(a.bo_dept)+1 from tbl_board a where bo_ref = ?), ?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setLong(6, vo.getBo_ref());
				pstmt.setString(7, vo.getBo_title());
				pstmt.setLong(8, vo.getBo_ref());
				pstmt.setLong(9, vo.getBo_order() + 1);

			} else {

				sql = "insert into tbl_board values (?, ?, ?, ?, ?, 0, ?, 0, 0)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(6, vo.getBo_title());

			}

			pstmt.setLong(1, 0);
			pstmt.setLong(2, vo.getBo_writer().getNo());
			pstmt.setString(3, vo.getBo_content());
			pstmt.setTimestamp(4, vo.getBo_timestamp());
			pstmt.setLong(5, 0);

			pstmt.executeUpdate();

			if (vo.getBo_ref() <= 0) {

				sql = "SELECT LAST_INSERT_ID()";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();

				long bo_ref = 0;
				if (rs.next()) {
					bo_ref = rs.getLong(1);

				}

				sql = "update tbl_board set bo_ref = ? where bo_num=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, bo_ref);
				pstmt.setLong(2, bo_ref);
				pstmt.executeUpdate();

			}

			return true;

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {

			DBManager.disconnect(conn, pstmt, stmt, rs);

		}

		return false;

	}

	public boolean update(BoardVo vo) {

		String sql = "update tbl_board set bo_title = ?, bo_content = ? where bo_num = ?";
		try {

			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getBo_title());
			pstmt.setString(2, vo.getBo_content());
			pstmt.setLong(3, vo.getBo_num());
			pstmt.executeUpdate();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.disconnect(conn, pstmt, stmt, rs);

		}
		return false;

	}

	public List<BoardVo> getAllBoard(long prefix, long limit, List<String> keywords) {

		try {
			String sql = "select bo.*, (select name from tbl_user where no = bo.bo_writer) as writer_name from tbl_board bo order by bo_ref desc, bo_dept asc, bo_order asc limit ? , ?";

			if (keywords.size() > 0) {

				sql = "select bo.*, (select name from tbl_user where no = bo.bo_writer) as writer_name from tbl_board bo where bo.bo_title REGEXP('";

				for (int i = 0; i < keywords.size(); ++i) {

					if (keywords.get(i).trim().length() == 0)
						continue;

					sql += keywords.get(i);

					if (i < keywords.size() - 1) {

						sql += "|";
					}

				}

				sql += "')";

				sql += " order by bo_ref desc, bo_dept asc, bo_order asc limit ? , ?";


			}

			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, prefix);
			pstmt.setLong(2, limit);
			rs = pstmt.executeQuery();

			List<BoardVo> boards = new ArrayList<>();

			while (rs.next()) {

				BoardVo vo = new BoardVo();
				vo.setBo_num(rs.getLong(1));
				UserVo user = new UserVo();
				user.setNo(rs.getLong(2));
				vo.setBo_content(rs.getString(3));
				vo.setBo_timestamp(rs.getTimestamp(4));
				vo.setBo_count(rs.getLong(5));
				vo.setBo_ref(rs.getLong(6));
				vo.setBo_title(rs.getString(7));
				vo.setBo_dept(rs.getLong(8));
				vo.setBo_order(rs.getLong(9));
				user.setName(rs.getString(10));
				vo.setBo_writer(user);

				boards.add(vo);

			}

			return boards;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.disconnect(conn, pstmt, stmt, rs);

		}
		return null;

	}

	public BoardVo getSelectedBoard(long num) {

		try {
			String sql = "select * from tbl_board where bo_num = ?";
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, num);
			rs = pstmt.executeQuery();

			BoardVo vo = new BoardVo();
			while (rs.next()) {

				vo.setBo_num(rs.getLong(1));
				UserVo user = new UserVo();
				user.setNo(rs.getLong(2));
				vo.setBo_writer(user);
				vo.setBo_content(rs.getString(3));
				vo.setBo_timestamp(rs.getTimestamp(4));
				vo.setBo_count(rs.getLong(5));
				vo.setBo_ref(rs.getLong(6));
				vo.setBo_title(rs.getString(7));
				vo.setBo_dept(rs.getLong(8));
				vo.setBo_order(rs.getLong(9));

				return vo;

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.disconnect(conn, pstmt, stmt, rs);

		}

		return null;

	}

	public boolean counting(long boNum) {

		try {
			String sql = "update tbl_board set bo_count = bo_count+1 where bo_num = ?";
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, boNum);
			pstmt.executeUpdate();
			return true;

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			DBManager.disconnect(conn, pstmt, stmt, rs);

		}

		return false;
	}

	public long getTotalBoardNum(List<String> keywords) {

		long totalCount = 0;
		try {
			String sql = "select count(*) from tbl_board";
			
			if (keywords.size() > 0) {

				sql = "select count(*) from tbl_board where bo_title REGEXP('";

				for (int i = 0; i < keywords.size(); ++i) {

					if (keywords.get(i).trim().length() == 0)
						continue;

					sql += keywords.get(i);

					if (i < keywords.size() - 1) {

						sql += "|";
					}

				}

				sql += "')";

			}
			
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				totalCount = rs.getLong(1);
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			DBManager.disconnect(conn, pstmt, stmt, rs);

		}

		return totalCount;

	}
	
	
}
