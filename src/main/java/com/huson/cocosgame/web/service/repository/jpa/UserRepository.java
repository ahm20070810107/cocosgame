package com.huson.cocosgame.web.service.repository.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.huson.cocosgame.web.model.User;

public  interface UserRepository
  extends JpaRepository<User, String>
{
   User findByIdAndOrgi(String paramString, String orgi);
  
   User findByUsernameAndOrgi(String paramString, String orgi);
  
   User findByEmailAndOrgi(String paramString, String orgi);
  
   User findByUsernameAndPassword(String paramString1, String password);
  
   Page<User> findByOrgi(String orgi , Pageable paramPageable);
  
   Page<User> findByDatastatusAndOrgi(boolean datastatus , String orgi, Pageable paramPageable);
  
   Page<User> findByDatastatusAndOrgiAndUsernameLike(boolean datastatus , String orgi ,String username ,Pageable paramPageable);
  
   List<User> findByOrganAndOrgi(String paramString, String orgi);
  
   List<User> findByOrganAndDatastatusAndOrgi(String paramString , boolean datastatus, String orgi);
  
   List<User> findByOrgiAndDatastatus(String orgi , boolean datastatus);
  
   Page<User> findByOrgiAndAgent(String orgi , boolean agent , Pageable paramPageable);
  
   List<User> findByOrgiAndAgent(String orgi , boolean agent);
  
   long countByOrgiAndAgent(String orgi , boolean agent) ;
}
