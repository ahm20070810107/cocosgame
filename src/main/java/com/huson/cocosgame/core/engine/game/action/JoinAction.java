package com.huson.cocosgame.core.engine.game.action;

import java.util.List;



import com.huson.cocosgame.config.web.model.Game;
import com.huson.cocosgame.core.BMDataContext;
import com.huson.cocosgame.core.engine.game.BeiMiGameEnum;
import com.huson.cocosgame.core.engine.game.BeiMiGameEvent;
import com.huson.cocosgame.core.statemachine.action.Action;
import com.huson.cocosgame.core.statemachine.impl.BeiMiExtentionTransitionConfigurer;
import com.huson.cocosgame.core.statemachine.message.Message;
import com.huson.cocosgame.util.cache.CacheHelper;
import com.huson.cocosgame.web.model.GameRoom;
import com.huson.cocosgame.web.model.PlayUserClient;
import org.apache.logging.log4j.util.Strings;

/**
 * 创建房间的人，房卡模式下的 房主， 大厅模式下的首个进入房间的人
 * @author iceworld
 *
 */
public class JoinAction<T,S> implements Action<T, S>{
	
	/**
	 * JOIN事件，检查是否 凑齐一桌子，如果凑齐了，直接开始，并取消计时器
	 * 如果不够一桌子，啥也不做，等人活等计时器到事件
	 * 撮合成功的，立即开启游戏
	 * 通知所有成员的消息在 GameEventHandler里处理了
	 * 
	 */
	@Override
	public void execute(Message<T> message, BeiMiExtentionTransitionConfigurer<T,S> configurer) {
		String room = (String)message.getMessageHeaders().getHeaders().get("room") ;
		if(!Strings.isBlank(room)){
			GameRoom gameRoom = (GameRoom) CacheHelper.getGameRoomCacheBean().getCacheObject(room, BMDataContext.SYSTEM_ORGI) ; 
			if(gameRoom!=null){
				List<PlayUserClient> playerList = CacheHelper.getGamePlayerCacheBean().getCacheObject(gameRoom.getRoomid(), gameRoom.getOrgi()) ;
				if(gameRoom.getPlayers() == playerList.size()){
					//结束撮合，可以开始玩游戏了
					/**
					 * 更新状态
					 */
					gameRoom.setStatus(BeiMiGameEnum.READY.toString());
					/**
					 * 发送一个 Enough 事件
					 */
					BMDataContext.getContext().getBean(Game.class).change(gameRoom , BeiMiGameEvent.ENOUGH.toString());	//通知状态机 , 此处应由状态机处理异步执行
				}else{
					/**
					 * 啥也不干，等着
					 */
					gameRoom.setStatus(BeiMiGameEnum.WAITTING.toString());
				}
				CacheHelper.getGameRoomCacheBean().put(gameRoom.getId(), gameRoom, gameRoom.getOrgi());
			}
		}
	}
}
