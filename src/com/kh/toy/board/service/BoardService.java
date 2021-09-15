package com.kh.toy.board.service;

import com.kh.toy.board.model.dao.BoardDao;
import com.kh.toy.board.model.dto.Board;
import com.kh.toy.common.db.JDBCTemplate;

public class BoardService {
	
	private JDBCTemplate template = JDBCTemplate.getInstance();
	private BoardDao boardDao = new BoardDao();
}
