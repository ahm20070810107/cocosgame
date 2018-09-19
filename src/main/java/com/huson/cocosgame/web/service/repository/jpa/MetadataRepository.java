package com.huson.cocosgame.web.service.repository.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.huson.cocosgame.web.model.MetadataTable;

public  interface MetadataRepository extends JpaRepository<MetadataTable, String>{
//	 MetadataTableable findById(String id);
	
	 MetadataTable findByTablename(String tablename);

	 Page<MetadataTable> findAll(Pageable paramPageable);
	
	 int countByTablename(String tableName) ;
}
