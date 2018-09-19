package com.huson.cocosgame.web.service.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.huson.cocosgame.web.model.GameModel;

public  interface GameModelRepository  extends JpaRepository<GameModel, String>{
	
   GameModel findByIdAndOrgi(String id, String orgi);
  
   List<GameModel> findByOrgiAndGame(String orgi , String game);
}
