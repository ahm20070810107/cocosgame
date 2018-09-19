package com.huson.cocosgame.web.service.repository.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.huson.cocosgame.web.model.Organ;
import com.huson.cocosgame.web.model.OrganRole;
import com.huson.cocosgame.web.model.Role;

public  interface OrganRoleRepository  extends JpaRepository<OrganRole, String>
{
	
	 Page<OrganRole> findByOrgiAndRole(String orgi ,Role role,Pageable paramPageable);
	
	 List<OrganRole> findByOrgiAndRole(String orgi ,Role role);

	 List<OrganRole> findByOrgiAndOrgan(String orgi ,Organ organ);
}

