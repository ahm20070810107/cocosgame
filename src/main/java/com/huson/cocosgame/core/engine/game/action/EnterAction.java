package com.huson.cocosgame.core.engine.game.action;



import com.huson.cocosgame.core.BMDataContext;
import com.huson.cocosgame.core.engine.game.task.CreateAITask;
import com.huson.cocosgame.core.statemachine.action.Action;
import com.huson.cocosgame.core.statemachine.impl.BeiMiExtentionTransitionConfigurer;
import com.huson.cocosgame.core.statemachine.message.Message;
import com.huson.cocosgame.util.cache.CacheHelper;
import com.huson.cocosgame.web.model.GameRoom;
import org.apache.logging.log4j.util.Strings;

/**
 * 创建房间的人，房卡模式下的 房主， 大厅模式下的首个进入房间的人
 * @author iceworld
 *
 */
public class EnterAction<T,S> implements Action<T, S>{
	
	/**
	 * 进入房间后开启 5秒计时模式，计时结束后未撮合玩家成功 的，召唤机器人，
	 * 撮合成功的，立即开启游戏
	 */
	@Override
	public void execute(Message<T> message, BeiMiExtentionTransitionConfigurer<T,S> configurer) {
		String room = (String)message.getMessageHeaders().getHeaders().get("room") ;
		if(!Strings.isBlank(room)){
			GameRoom gameRoom = (GameRoom) CacheHelper.getGameRoomCacheBean().getCacheObject(room, BMDataContext.SYSTEM_ORGI) ; 
			if(gameRoom!=null){
				CacheHelper.getExpireCache().put(gameRoom.getOrgi(), new CreateAITask(5 , gameRoom , gameRoom.getOrgi()));
				
				/**
				 * 更新状态
				 */
				gameRoom.setStatus(configurer.getTarget().toString());
				CacheHelper.getGameRoomCacheBean().put(gameRoom.getId(), gameRoom, gameRoom.getOrgi());
			}
		}
	}
}
