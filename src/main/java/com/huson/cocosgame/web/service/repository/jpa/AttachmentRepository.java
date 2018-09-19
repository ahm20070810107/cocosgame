package com.huson.cocosgame.web.service.repository.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.huson.cocosgame.web.model.AttachmentFile;

public interface AttachmentRepository  extends JpaRepository<AttachmentFile, String>{
	
	 AttachmentFile findByIdAndOrgi(String id ,String orgi);
	
	 List<AttachmentFile> findByDataidAndOrgi(String dataid , String orgi);
	
	 List<AttachmentFile> findByModelidAndOrgi(String modelid , String orgi);
	
	 Page<AttachmentFile> findByOrgi(String orgi , Pageable page);
}

