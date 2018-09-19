package com.huson.cocosgame.web.service.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.huson.cocosgame.web.model.TableProperties;

public  interface TablePropertiesRepository extends JpaRepository<TableProperties, String>{
	
//	 TableProperties findById(String id);

	 List<TableProperties> findByDbtableid(String dbtableid) ;
}
