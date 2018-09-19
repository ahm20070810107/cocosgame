package com.huson.cocosgame.core.engine.game;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.huson.cocosgame.config.web.model.Game;
import com.huson.cocosgame.core.BMDataContext;
import com.huson.cocosgame.core.engine.game.task.AbstractTask;
import com.huson.cocosgame.core.engine.game.task.CreateAutoTask;
import com.huson.cocosgame.util.cache.CacheHelper;
import com.huson.cocosgame.util.client.NettyClients;
import com.huson.cocosgame.util.rules.model.Board;
import com.huson.cocosgame.util.rules.model.Player;
import com.huson.cocosgame.util.server.handler.BeiMiClient;
import com.huson.cocosgame.web.model.GameRoom;
import com.huson.cocosgame.web.model.PlayUserClient;

public class ActionTaskUtils {
	/**
	 * 
	 * @param times
	 * @param gameRoom
	 * @return
	 */
	public static AbstractTask createAutoTask(int times , GameRoom gameRoom){
		return new CreateAutoTask(times , gameRoom , gameRoom.getOrgi()) ;
	}
	/**
	 * 
	 * @return
	 */
	public static Game game(){
		return BMDataContext.getContext().getBean(Game.class) ;
	}
	public static void sendEvent(String event, Object data ,GameRoom gameRoom){
		List<PlayUserClient> players = CacheHelper.getGamePlayerCacheBean().getCacheObject(gameRoom.getId(), gameRoom.getOrgi()) ;
		for(PlayUserClient user : players){
			BeiMiClient client = NettyClients.getInstance().getClient(user.getId()) ;
			if(client!=null){
				client.getClient().sendEvent(event, data);
			}
		}
	}
	
	public static String json(Object data){
		return JSON.toJSONString(data!=null ? data : "") ;
	}
	/**
	 * 临时放这里，重构的时候 放到 游戏类型的 实现类里
	 * @param board
	 * @param player
	 * @return
	 */
	public static Board doCatch(Board board, Player player , boolean result){
		player.setAccept(result); //抢地主
		player.setDocatch(true);
		board.setDocatch(true);
		if(result){	//抢了地主
			board.setRatio(board.getRatio()*2);
			board.setBanker(player.getPlayuser());
		}
		return board ;
	}
}
