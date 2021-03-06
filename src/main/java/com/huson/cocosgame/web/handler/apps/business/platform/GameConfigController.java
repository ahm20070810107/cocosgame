package com.huson.cocosgame.web.handler.apps.business.platform;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.huson.cocosgame.util.Menu;
import com.huson.cocosgame.web.handler.Handler;
import com.huson.cocosgame.web.model.BeiMiDic;
import com.huson.cocosgame.web.model.GameAccountConfig;
import com.huson.cocosgame.web.model.SysDic;
import com.huson.cocosgame.web.service.repository.jpa.GameAccountConfigRepository;

@Controller
@RequestMapping("/apps/platform")
public class GameConfigController extends Handler{
	
	@Autowired
	private GameAccountConfigRepository accountRes ;
	
	@RequestMapping({"/config/account"})
	@Menu(type="platform", subtype="account")
	public ModelAndView account(ModelMap map , HttpServletRequest request , @Valid String id){
		List<GameAccountConfig> accountList = accountRes.findByOrgi(super.getOrgi(request)) ;
		if(accountList.size() > 0){
			map.addAttribute("accountConfig", accountList.get(0)) ;
		}
		return request(super.createAppsTempletResponse("/apps/business/platform/desktop/account"));
	}
	
	@RequestMapping({"/config/account/save"})
	@Menu(type="platform", subtype="account")
	public ModelAndView account(ModelMap map , HttpServletRequest request , @Valid GameAccountConfig account){
		List<GameAccountConfig> accountList = accountRes.findByOrgi(super.getOrgi(request)) ;
		if(accountList.size() > 0){
			GameAccountConfig tempConfig = accountList.get(0) ;
			account.setId(tempConfig.getId());
		}
		account.setOrgi(super.getOrgi(request));
		account.setCreater(super.getUser(request).getId());
		account.setCreatetime(new Date());
		accountRes.save(account) ;
		return request(super.createRequestPageTempletResponse("redirect:/apps/platform/config/account.html"));
	}
	
	
	@RequestMapping({"/config"})
	@Menu(type="platform", subtype="platform")
	public ModelAndView content(ModelMap map , HttpServletRequest request , @Valid String id){
		if(!Strings.isBlank(id)){
			SysDic dic = BeiMiDic.getInstance().getDicItem(id) ;
			map.addAttribute("gameType", dic) ;
		}
		return request(super.createAppsTempletResponse("/apps/business/platform/desktop/config"));
	}
}
