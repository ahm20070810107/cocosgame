package com.huson.cocosgame.web.service.repository.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.huson.cocosgame.web.model.Role;
import com.huson.cocosgame.web.model.User;
import com.huson.cocosgame.web.model.UserRole;

public  interface UserRoleRepository  extends JpaRepository<UserRole, String>
{
	
	 Page<UserRole> findByOrgiAndRole(String orgi ,Role role,Pageable paramPageable);
	
	 List<UserRole> findByOrgiAndRole(String orgi ,Role role);
	
	 List<UserRole> findByOrgiAndUser(String orgi ,User user);
}

