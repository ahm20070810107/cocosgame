package com.huson.cocosgame.web.handler.apps.business.platform;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.huson.cocosgame.core.BMDataContext;
import com.huson.cocosgame.util.GameUtils;
import com.huson.cocosgame.util.Menu;
import com.huson.cocosgame.util.cache.CacheHelper;
import com.huson.cocosgame.web.handler.Handler;
import com.huson.cocosgame.web.model.BeiMiDic;
import com.huson.cocosgame.web.model.GamePlayway;
import com.huson.cocosgame.web.model.GameRoom;
import com.huson.cocosgame.web.model.PlayUser;
import com.huson.cocosgame.web.model.PlayUserClient;
import com.huson.cocosgame.web.service.repository.es.PlayUserESRepository;
import com.huson.cocosgame.web.service.repository.jpa.GamePlaywayRepository;
import com.huson.cocosgame.web.service.repository.jpa.GameRoomRepository;

@Controller
@RequestMapping("/apps/platform")
public class GameRoomController extends Handler{
	
	@Autowired
	private GameRoomRepository gameRoomRes ;
	
	@Autowired
	private PlayUserESRepository playUserRes;
	
	@Autowired
	private GamePlaywayRepository playwayRes;
	
	@RequestMapping({"/gameroom"})
	@Menu(type="platform", subtype="gameroom")
	public ModelAndView gameusers(ModelMap map , HttpServletRequest request , @Valid String id){
		Page<GameRoom> gameRoomList = gameRoomRes.findByOrgi(super.getOrgi(request), new PageRequest(super.getP(request), super.getPs(request))) ;
		List<String> playUsersList = new ArrayList<String>() ;
		for(GameRoom gameRoom : gameRoomList.getContent()){
			List<PlayUserClient> players = CacheHelper.getGamePlayerCacheBean().getCacheObject(gameRoom.getId(),gameRoom.getOrgi()) ;
			gameRoom.setPlayers(players.size());
			if(!Strings.isBlank(gameRoom.getMaster())){
				playUsersList.add(gameRoom.getMaster()) ;
			}
			if(!Strings.isBlank(gameRoom.getPlayway())){
				gameRoom.setGamePlayway((GamePlayway) CacheHelper.getSystemCacheBean().getCacheObject(gameRoom.getPlayway(), super.getOrgi(request)));
			}
		}
		if(playUsersList.size() > 0){
			for(PlayUser playUser : playUserRes.findAll(playUsersList) ){
				for(GameRoom gameRoom : gameRoomList.getContent()){
					if(playUser.getId().equals(gameRoom.getMaster())){
						gameRoom.setMasterUser(playUser); break ;
					}
				}
			}
		}
		map.addAttribute("gameRoomList", gameRoomList) ;
		
		map.addAttribute("gameModelList", BeiMiDic.getInstance().getDic(BMDataContext.BEIMI_SYSTEM_GAME_TYPE_DIC)) ;
		
		return request(super.createAppsTempletResponse("/apps/business/platform/game/room/index"));
	}
	

	@RequestMapping({"/gameroom/delete"})
	@Menu(type="platform", subtype="gameroom")
	public ModelAndView delete(ModelMap map , HttpServletRequest request , @Valid String id , @Valid String game){
		if(!Strings.isBlank(id)){
			GameRoom gameRoom = gameRoomRes.findByIdAndOrgi(id, super.getOrgi(request)) ;
			if(gameRoom!=null){
				gameRoomRes.delete(gameRoom);
			}
			GameUtils.removeGameRoom(gameRoom.getId(), super.getOrgi(request));
			CacheHelper.getGameRoomCacheBean().delete(gameRoom.getId(), super.getOrgi(request)) ;
			CacheHelper.getExpireCache().remove(gameRoom.getId());
			List<PlayUserClient> playerUsers = CacheHelper.getGamePlayerCacheBean().getCacheObject(id, super.getOrgi(request)) ;
			for(PlayUserClient tempPlayUser : playerUsers){
				CacheHelper.getRoomMappingCacheBean().delete(tempPlayUser.getId(), super.getOrgi(request)) ;
			}
			CacheHelper.getGamePlayerCacheBean().delete(gameRoom.getId()) ;
		}
		return request(super.createRequestPageTempletResponse("redirect:/apps/platform/gameroom.html"));
	}
	
}
