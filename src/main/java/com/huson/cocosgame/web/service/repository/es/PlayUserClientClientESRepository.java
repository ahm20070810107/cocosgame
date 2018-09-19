package com.huson.cocosgame.web.service.repository.es;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import com.huson.cocosgame.web.model.PlayUserClient;

public  interface PlayUserClientClientESRepository  extends ElasticsearchCrudRepository<PlayUserClient, String>{
	
//            PlayUserClient findById(String paramString);

            PlayUserClient findByUsername(String username);

            int countByUsername(String username);

            Page<PlayUserClient> findByDatastatus(boolean datastatus , String orgi, Pageable paramPageable);

            Page<PlayUserClient> findByDatastatusAndUsername(boolean datastatus , String orgi ,String username ,Pageable paramPageable);
}
