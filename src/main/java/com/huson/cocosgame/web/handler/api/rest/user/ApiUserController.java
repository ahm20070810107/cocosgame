package com.huson.cocosgame.web.handler.api.rest.user;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.huson.cocosgame.util.MessageEnum;
import com.huson.cocosgame.web.handler.Handler;
import com.huson.cocosgame.web.model.PlayUser;
import com.huson.cocosgame.web.model.ResultData;
import com.huson.cocosgame.web.service.repository.es.PlayUserESRepository;
import com.huson.cocosgame.web.service.repository.jpa.PlayUserRepository;

@RestController
@RequestMapping("/api/player")
public class ApiUserController extends Handler{

	@Autowired
	private PlayUserESRepository playUserESRes;
	
	@Autowired
	private PlayUserRepository playUserRes ;

	@RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ResultData> get(HttpServletRequest request , @RequestParam String id) {
    	PlayUser player = null ;
    	if(!Strings.isBlank(id) && playUserESRes.exists(id)){
    		player = playUserESRes.findOne(id) ;
    	}
    	return new ResponseEntity<>(new ResultData( player!=null , player != null ? MessageEnum.USER_GET_SUCCESS : MessageEnum.USER_NOT_EXIST, player), HttpStatus.OK);
    }
	
}