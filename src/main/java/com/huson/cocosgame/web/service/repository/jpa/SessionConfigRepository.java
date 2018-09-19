package com.huson.cocosgame.web.service.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.huson.cocosgame.web.model.GameConfig;

public  interface SessionConfigRepository  extends JpaRepository<GameConfig, String>
{
	 GameConfig findByOrgi(String orgi);
}

