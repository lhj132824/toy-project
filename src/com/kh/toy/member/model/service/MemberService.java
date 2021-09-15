package com.kh.toy.member.model.service;


import java.sql.Connection;
import java.util.List;

import com.kh.toy.common.db.JDBCTemplate;
import com.kh.toy.common.http.HttpConnector;
import com.kh.toy.common.http.RequestParams;
import com.kh.toy.common.mail.MailSender;
import com.kh.toy.member.model.dao.MemberDao;
import com.kh.toy.member.model.dto.Member;


//Service
//어플리케이션의 비지니스 로직을 작성
//사용자의 요청을 컨트롤러로 부터 위임받아 해당 요청을 처리하기 위해 필요한 핵심적인 작업을 진행
//작업을 수행하기 위해 데이터베이스에 저장된 데이터가 필요하면 Dao에게 요청
//비지니스로직을 Service가 담당하기 때문에 Transaction관리를 Service가 담당
//commit/rollback을 service가 담당

//Connection 객체 생성, close처리
//commit과 rollback 
//SQLException에 대한 예외처리(rollback)
public class MemberService {

	private MemberDao memberDao = new MemberDao();
	private JDBCTemplate template = JDBCTemplate.getInstance();
	
	
	
	
	public Member memberAuthenticate(String userId, String password) {
		Connection conn = template.getConnection();
		Member member = null;
		try {
			member = memberDao.memberAuthenticate(userId,password,conn);
		} finally {
			template.close(conn);
		}
		return member;
	}

	
	
	
	public Member selectMemberById(String userId) {
		Connection conn = template.getConnection();
		Member member = null;
		try {
			member = memberDao.selectMemberById(userId, conn);
		} finally {
			template.close(conn);
		}
		return member;
	}

	
	
	
	public List<Member> selectMemberList() {
		Connection conn = template.getConnection();
		List<Member> memberList = null;
		try {
			memberList = memberDao.selectMemberList(conn);
		} finally {
			template.close(conn);
		}
		return memberList;
	}
	

	
	
	public int insertMember(Member member){
		Connection conn = template.getConnection();
		int res = 0;
		try {
			res = memberDao.insertMember(member, conn);
			template.commit(conn);
		} catch (Exception e) {
			template.rollback(conn);
			throw e;
		} finally {
			template.close(conn);
		}
		return res;
	}
		
	
	
	
	public int alterPass2(Member member) {
		Connection conn = template.getConnection();
		int res = 0;
		try {
			res = memberDao.alterPass3(member, conn);
			template.commit(conn);
		} catch (Exception e) {
			template.rollback(conn);
			throw e;
		} finally {
			template.close(conn);
		}
		return res;
	}

	
	
	
	public int deleteMem2(Member member) {
		Connection conn = template.getConnection();
		int res = 0;
		try {
			res = memberDao.deleteMem3(member, conn);
			template.commit(conn);
		} catch (Exception e) {
			template.rollback(conn);
			throw e;
		} finally {
			template.close(conn);
		}
		return res;
	}



	public void authenticateByEmail(Member member, String persistToken) {
		MailSender mailSender = new MailSender();
		HttpConnector conn = new HttpConnector();
		
		String queryString = conn.urlEncodedForm(RequestParams.builder()
				.param("mailTemplate", "join-auth-mail")
				.param("userId", member.getUserId())
				.param("persistToken", persistToken).build());
		
		String response = conn.get("http://localhost:9090/mail?"+queryString);
		mailSender.sendEmail(member.getEmail(), "회원가입을 축하합니다.", response);
	}




	
	
	
	

}//끝
