package com.huson.cocosgame.web.service.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.huson.cocosgame.web.model.GameAccountConfig;

public interface GameAccountConfigRepository extends JpaRepository<GameAccountConfig, String>
{
   List<GameAccountConfig> findByOrgi(String orgi);
}
