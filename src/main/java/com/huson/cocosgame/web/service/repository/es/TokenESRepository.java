package com.huson.cocosgame.web.service.repository.es;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import com.huson.cocosgame.web.model.Token;

public  interface TokenESRepository extends ElasticsearchCrudRepository<Token, String>
{
//	  Token findById(String id);
	
	  List<Token> findByUserid(String userId);
}
