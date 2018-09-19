package com.huson.cocosgame.core.engine.game.action;



import com.huson.cocosgame.core.BMDataContext;
import com.huson.cocosgame.core.engine.game.task.CreatePlayCardsTask;
import com.huson.cocosgame.core.statemachine.action.Action;
import com.huson.cocosgame.core.statemachine.impl.BeiMiExtentionTransitionConfigurer;
import com.huson.cocosgame.core.statemachine.message.Message;
import com.huson.cocosgame.util.cache.CacheHelper;
import com.huson.cocosgame.util.rules.model.Board;
import com.huson.cocosgame.util.rules.model.TakeCards;
import com.huson.cocosgame.web.model.GameRoom;
import org.apache.logging.log4j.util.Strings;

/**
 * 凑够了，开牌
 * @author iceworld
 *
 */
public class PlayCardsAction<T,S> implements Action<T, S>{
	@Override
	public void execute(Message<T> message , BeiMiExtentionTransitionConfigurer<T,S> configurer) {
		String room = (String)message.getMessageHeaders().getHeaders().get("room") ;
		if(!Strings.isBlank(room)){
			GameRoom gameRoom = (GameRoom) CacheHelper.getGameRoomCacheBean().getCacheObject(room, BMDataContext.SYSTEM_ORGI) ; 
			if(gameRoom!=null){
				Board board = (Board) CacheHelper.getBoardCacheBean().getCacheObject(gameRoom.getId(), gameRoom.getOrgi()) ;
				int interval = (int) message.getMessageHeaders().getHeaders().get("interval") ;
				String nextPlayer = board.getBanker();
				TakeCards lastTakeCards = board.getLast() ;
				if(lastTakeCards!=null){
					nextPlayer = lastTakeCards.getNextplayer() ;
				}
				CacheHelper.getExpireCache().put(gameRoom.getRoomid(), new CreatePlayCardsTask(interval , nextPlayer , gameRoom , gameRoom.getOrgi()));
			}
		}
	}
}
