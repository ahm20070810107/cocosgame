package com.huson.cocosgame.web.service.repository.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.huson.cocosgame.web.model.GameRoom;

 public interface GameRoomRepository  extends JpaRepository<GameRoom, String>{
	
   GameRoom findByIdAndOrgi(String id, String orgi);
  
   Page<GameRoom> findByOrgi(String orgi , Pageable page);
  
   List<GameRoom> findByRoomidAndOrgi(String roomid, String orgi);
}
