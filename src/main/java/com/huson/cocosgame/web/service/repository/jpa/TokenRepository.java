package com.huson.cocosgame.web.service.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.huson.cocosgame.web.model.Token;

public  interface TokenRepository extends JpaRepository<Token, String>
{
//	    Token findById(String id);
	
	    List<Token> findByUserid(String userid);
}
