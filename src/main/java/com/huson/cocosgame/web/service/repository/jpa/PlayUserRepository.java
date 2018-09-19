package com.huson.cocosgame.web.service.repository.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.huson.cocosgame.web.model.PlayUser;

public  interface PlayUserRepository extends JpaRepository<PlayUser, String>
{
//	 PlayUser findById(String paramString);

	 PlayUser findByUsername(String username);

	 PlayUser findByEmail(String email);

	 PlayUser findByUsernameAndPassword(String username, String password);

	 Page<PlayUser> findByDatastatus(boolean datastatus , String orgi, Pageable paramPageable);

	 Page<PlayUser> findByDatastatusAndUsername(boolean datastatus , String orgi ,String username ,Pageable paramPageable);
}
