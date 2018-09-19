package com.huson.cocosgame.web.handler.api.rest.user;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huson.cocosgame.core.BMDataContext;
import com.huson.cocosgame.util.CacheConfigTools;
import com.huson.cocosgame.util.GameUtils;
import com.huson.cocosgame.util.IP;
import com.huson.cocosgame.util.IPTools;
import com.huson.cocosgame.util.MessageEnum;
import com.huson.cocosgame.util.UKTools;
import com.huson.cocosgame.util.cache.CacheHelper;
import com.huson.cocosgame.web.handler.Handler;
import com.huson.cocosgame.web.model.GameAccountConfig;
import com.huson.cocosgame.web.model.PlayUser;
import com.huson.cocosgame.web.model.PlayUserClient;
import com.huson.cocosgame.web.model.ResultData;
import com.huson.cocosgame.web.model.Token;
import com.huson.cocosgame.web.service.repository.es.PlayUserClientClientESRepository;
import com.huson.cocosgame.web.service.repository.es.PlayUserESRepository;
import com.huson.cocosgame.web.service.repository.es.TokenESRepository;
import com.huson.cocosgame.web.service.repository.jpa.PlayUserRepository;

@RestController
@RequestMapping("/api/guest")
public class GuestRegisterController extends Handler{

	@Autowired
	private PlayUserESRepository playUserESRes;
	
	@Autowired
	private PlayUserClientClientESRepository playUserClientRes ;
	
	@Autowired
	private PlayUserRepository playUserRes ;
	
	@Autowired
	private TokenESRepository tokenESRes ;

	@RequestMapping
    public ResponseEntity<ResultData> guest(HttpServletRequest request , @Valid String token) {
		PlayUserClient playUserClient = null ;
		Token userToken = null ;
		if(!Strings.isBlank(token)){
			userToken = tokenESRes.findById(token).get() ;
			if(userToken != null && !Strings.isBlank(userToken.getUserid()) && userToken.getExptime()!=null && userToken.getExptime().after(new Date())){
				//返回token， 并返回游客数据给游客
				playUserClient = playUserClientRes.findById(userToken.getUserid()).get() ;
				if(playUserClient!=null){
					playUserClient.setToken(userToken.getId());
				}
			}else{
				if(userToken!=null){
					tokenESRes.delete(userToken);
					userToken = null ;
				}
			}
		}
		if(playUserClient == null){
			try {
				String ip = UKTools.getIpAddr(request);
				IP ipdata = IPTools.getInstance().findGeography(ip);
				playUserClient = register(new PlayUser() , ipdata , request) ;
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		if(userToken == null){
			userToken = new Token();
			userToken.setId(UKTools.getUUID());
			userToken.setUserid(playUserClient.getId());
			userToken.setCreatetime(new Date());
			userToken.setOrgi(playUserClient.getOrgi());
			GameAccountConfig config = CacheConfigTools.getGameAccountConfig(BMDataContext.SYSTEM_ORGI) ;
    		if(config!=null && config.getExpdays() > 0){
    			userToken.setExptime(new Date(System.currentTimeMillis()+60*60*24*config.getExpdays()*1000));//默认有效期 ， 7天
    		}else{
    			userToken.setExptime(new Date(System.currentTimeMillis()+60*60*24*7*1000));//默认有效期 ， 7天
    		}
			userToken.setLastlogintime(new Date());
			userToken.setUpdatetime(new Date(0));
			
			tokenESRes.save(userToken) ;
		}
		playUserClient.setToken(userToken.getId());
		CacheHelper.getApiUserCacheBean().put(userToken.getId(),userToken, userToken.getOrgi());
		CacheHelper.getApiUserCacheBean().put(playUserClient.getId(),playUserClient, userToken.getOrgi());
        return new ResponseEntity<>(new ResultData( playUserClient!=null , playUserClient != null ? MessageEnum.USER_REGISTER_SUCCESS: MessageEnum.USER_REGISTER_FAILD_USERNAME , playUserClient , userToken), HttpStatus.OK);
    }
	/**
	 * 注册用户
	 * @param player
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public PlayUserClient register(PlayUser player , IP ipdata , HttpServletRequest request ) throws IllegalAccessException, InvocationTargetException{
		PlayUserClient playUserClient = GameUtils.create(player, ipdata, request) ;
		int users = playUserESRes.countByUsername(player.getUsername()) ;
		if(users == 0){
			UKTools.published(player , playUserESRes , playUserRes);
		}
		return playUserClient ;
	}
	
}