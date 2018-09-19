package com.huson.cocosgame.web.service.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.huson.cocosgame.web.model.SystemConfig;

public  interface SystemConfigRepository  extends JpaRepository<SystemConfig, String>
{
	 SystemConfig findByOrgi(String orgi);
}

