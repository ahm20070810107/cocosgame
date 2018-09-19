package com.huson.cocosgame.web.service.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.huson.cocosgame.web.model.Role;

public  interface RoleRepository
  extends JpaRepository<Role, String>
{
   Role findByIdAndOrgi(String paramString, String orgi);
  
   List<Role> findByOrgi(String orgi);
  
   Role findByNameAndOrgi(String paramString, String orgi);
}

