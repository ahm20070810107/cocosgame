package com.huson.cocosgame.web.service.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.huson.cocosgame.web.model.GamePlayway;

public  interface GamePlaywayRepository  extends JpaRepository<GamePlayway, String>{
	
   GamePlayway findByIdAndOrgi(String id, String orgi);
  
   List<GamePlayway> findByOrgi(String orgi);
}
