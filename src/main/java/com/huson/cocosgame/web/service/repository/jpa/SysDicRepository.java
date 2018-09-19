package com.huson.cocosgame.web.service.repository.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.huson.cocosgame.web.model.SysDic;

public  interface SysDicRepository extends JpaRepository<SysDic, String>{
	
//		 SysDic findById(String id);

		 SysDic findByCode(String code);

		 Page<SysDic> findAll(Pageable paramPageable);

		 List<SysDic> findByCodeOrName(String code , String name);

		 List<SysDic> findByDicidAndName(String dicid , String name);

		 Page<SysDic> findByParentid(String type , Pageable paramPageable);

		List<SysDic> findByParentid(String type);

		 List<SysDic> findByDicid(String id);

		 int countByName(String name);
}
