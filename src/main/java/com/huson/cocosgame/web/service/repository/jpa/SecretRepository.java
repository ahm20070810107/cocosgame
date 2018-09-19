package com.huson.cocosgame.web.service.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.huson.cocosgame.web.model.Secret;

public  interface SecretRepository  extends JpaRepository<Secret, String>{
	 List<Secret> findByOrgi(String orgi);
}

