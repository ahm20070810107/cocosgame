package com.huson.cocosgame.web.service.repository.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.huson.cocosgame.web.model.Organ;

public  interface OrganRepository
  extends JpaRepository<Organ, String>
{
   Organ findByIdAndOrgi(String paramString, String orgi);
  
   Page<Organ> findByOrgi(String orgi , Pageable paramPageable);
  
   Organ findByNameAndOrgi(String paramString, String orgi);
  
   List<Organ> findByOrgi(String orgi);
  
   List<Organ> findByOrgiAndSkill(String orgi , boolean skill);
}
