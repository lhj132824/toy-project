package com.kh.toy.member.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kh.toy.common.db.JDBCTemplate;
import com.kh.toy.common.exception.DataAccessException;
import com.kh.toy.member.model.dto.Member;



//DAO(DATA ACCESS OBJECT)
//DBMS에 접근해 데이터의 조회, 수정, 삽입, 삭제 요청을 보내는 클래스
//DAO의 메서드는 하나의 메서드 당 하나의 쿼리만 처리하도록 작성
public class MemberDao {
	
	private JDBCTemplate template = JDBCTemplate.getInstance();

	public Member memberAuthenticate(String userId, String password, Connection conn)  {
		Member member = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;
		String query = "select * from member where user_id = ? and password = ?";
		
		try {
			pstm = conn.prepareStatement(query);
			pstm.setString(1, userId);
			pstm.setString(2, password);
			rset = pstm.executeQuery();
			
			if(rset.next()) {
				member = convertAllToMember(rset);
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}finally {
			template.close(rset, pstm);

		}
		
		return member;
	}

	
	
	//메뉴5 id로 회원정보 조회
	public Member selectMemberById(String userId, Connection conn) {
		Member member = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;
		String query = "select * from member where user_id = ?";
		try {
			pstm = conn.prepareStatement(query);
			pstm.setString(1, userId);
			rset = pstm.executeQuery();
			
			if(rset.next()) {
				member = convertAllToMember(rset);
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(rset, pstm);
		}
		return member;
	}

	
	
	//메뉴1번 전체회원정보 조회
	public List<Member> selectMemberList(Connection conn) {
		List<Member> memberList = new ArrayList<Member>();
		Member member = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;
		String query = "select * from member";
		try {
			pstm = conn.prepareStatement(query);
			rset = pstm.executeQuery();
			
			while(rset.next()) {
				member = convertAllToMember(rset);
				memberList.add(member);
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(rset, pstm);
		}	
		return memberList;
	}

	
	
	//메뉴2번 새회원 추가
	public int insertMember(Member member, Connection conn) {
		int res = 0;
		PreparedStatement pstm = null;
		String query = "insert into member(user_id,password,email,tell)"
				+ " values(?, ?, ?, ?)";
		try {
			pstm = conn.prepareStatement(query);
			pstm.setString(1, member.getUserId());
			pstm.setString(2, member.getPassword());
			pstm.setString(3, member.getEmail());
			pstm.setString(4, member.getTell());
			res = pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(pstm);
		}
		return res;
	}

	
	
	//메뉴3번 비밀번호 변경
	public int alterPass3(Member member, Connection conn) {
		int res = 0;
		PreparedStatement pstm = null;
		String query = "update member set password = ? where user_id = ?";
		try {
			pstm = conn.prepareStatement(query);
			pstm.setString(1, member.getPassword());
			pstm.setString(2, member.getUserId());
			res = pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(pstm);
		}
		return res;
	}

	
	
	//메뉴4번 회원탈퇴
	public int deleteMem3(Member member, Connection conn) {
		int res = 0;
		PreparedStatement pstm = null;
		String query = "delete from member where user_id = ?";
		try {
			pstm = conn.prepareStatement(query);
			pstm.setString(1, member.getUserId());
			res = pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			template.close(pstm);
		}
		return res;
	}
	
	
	
	private Member convertAllToMember(ResultSet rset) throws SQLException {
		Member member = new Member();
		member.setUserId(rset.getString("user_id"));
		member.setPassword(rset.getString("password"));
		member.setEmail(rset.getString("email"));
		member.setGrade(rset.getString("grade"));
		member.setIsLeave(rset.getInt("is_leave"));
		member.setRegDate(rset.getDate("reg_date"));
		member.setRentableDate(rset.getDate("rentable_date"));
		member.setTell(rset.getString("tell"));
		return member;
	}
	
	private Member convertRowToMember(String[] columns, ResultSet rset) throws SQLException {
		Member member = new Member();
		
		for (int i = 0; i < columns.length; i++) {
			String column = columns[i].toLowerCase();
			column = column.trim();
			
			switch (column) {
			case "user_id": member.setUserId(rset.getString("user_id")); break;
			case "password": member.setPassword(rset.getString("password")); break;
			case "email": member.setEmail(rset.getString("email")); break;
			case "grade": member.setGrade(rset.getString("grade")); break;
			case "is_leave": member.setIsLeave(rset.getInt("is_leave")); break;
			case "reg_date": member.setRegDate(rset.getDate("reg_date")); break;
			case "rentable_date": member.setRentableDate(rset.getDate("rentable_date")); break;
			case "tell": member.setTell(rset.getString("tell")); break;
			default : throw new SQLException("부적절한 컬럼명을 전달하였습니다."); //예외처리
			}
		}
		return member;
	}
	
	//메뉴1번 전체회원정보 조회 - 원하는 컬럼들만 뽑기!!
		public List<Member> selectMemberList2() {
			List<Member> memberList = new ArrayList<Member>();
			Member member = null;
			Connection conn = null;
			PreparedStatement pstm = null;
			ResultSet rset = null;
			
			String columns = "user_id, email, tell, grade";
			String query = "select " + columns + " from member";

			conn = template.getConnection();
			
			try {
				pstm = conn.prepareStatement(query);
				rset = pstm.executeQuery();
				
				while(rset.next()) {
					member = convertRowToMember(columns.split(","), rset);
					memberList.add(member);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				template.close(rset, pstm, conn);
			}	
			return memberList;
		}

	
	
	
	
	
}
