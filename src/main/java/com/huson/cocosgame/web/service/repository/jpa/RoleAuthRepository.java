package com.huson.cocosgame.web.service.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.huson.cocosgame.web.model.RoleAuth;

public  interface RoleAuthRepository
  extends JpaRepository<RoleAuth, String>
{
   RoleAuth findByIdAndOrgi(String paramString, String orgi);
  
   List<RoleAuth> findByRoleidAndOrgi(String roleid , String orgi) ;
  
   List<RoleAuth> findAll(Specification<RoleAuth> spec) ;
  
   RoleAuth findByNameAndOrgi(String paramString, String orgi);
}

