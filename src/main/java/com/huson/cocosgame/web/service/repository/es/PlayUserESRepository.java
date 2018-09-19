package com.huson.cocosgame.web.service.repository.es;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import com.huson.cocosgame.web.model.PlayUser;

public  interface PlayUserESRepository
  extends ElasticsearchCrudRepository<PlayUser, String>
    {
//          PlayUser findById(String paramString);

          PlayUser findByUsername(String username);

          int countByUsername(String username);

          PlayUser findByMobileAndPassword(String username, String password);

          Page<PlayUser> findByOrgi(String orgi, Pageable page);
  
   }
