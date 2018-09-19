package com.huson.cocosgame.web.service.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.huson.cocosgame.web.model.Generation;

public interface GenerationRepository  extends JpaRepository<Generation, String>{
	 Generation findByOrgiAndModel(String orgi , String model);
	 List<Generation> findByOrgi(String orgi);
}

