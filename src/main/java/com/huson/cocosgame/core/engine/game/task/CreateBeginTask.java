package com.huson.cocosgame.core.engine.game.task;

import java.util.List;

import org.cache2k.expiry.ValueWithExpiryTime;

import com.huson.cocosgame.core.engine.game.BeiMiGameEvent;
import com.huson.cocosgame.core.engine.game.BeiMiGameTask;
import com.huson.cocosgame.core.engine.game.impl.UserBoard;
import com.huson.cocosgame.util.GameUtils;
import com.huson.cocosgame.util.cache.CacheHelper;
import com.huson.cocosgame.util.client.NettyClients;
import com.huson.cocosgame.util.rules.model.Board;
import com.huson.cocosgame.web.model.GameRoom;
import com.huson.cocosgame.web.model.PlayUserClient;

public class CreateBeginTask extends AbstractTask implements ValueWithExpiryTime  , BeiMiGameTask{

	private long timer  ;
	private GameRoom gameRoom = null ;
	private String orgi ;
	
	public CreateBeginTask(long timer , GameRoom gameRoom, String orgi){
		super();
		this.timer = timer ;
		this.gameRoom = gameRoom ;
		this.orgi = orgi ;
	}
	@Override
	public long getCacheExpiryTime() {
		return System.currentTimeMillis()+timer*1000;	//5秒后执行
	}
	
	public void execute(){
		List<PlayUserClient> playerList = CacheHelper.getGamePlayerCacheBean().getCacheObject(gameRoom.getId(), orgi) ;
		/**
		 * 
		 * 顺手 把牌发了，注：此处应根据 GameRoom的类型获取 发牌方式
		 */
		Board board = GameUtils.playDizhuGame(playerList, gameRoom, null, gameRoom.getCardsnum()) ;
		CacheHelper.getBoardCacheBean().put(gameRoom.getId(), board, gameRoom.getOrgi());
		for(Object temp : playerList){
			PlayUserClient playerUser = (PlayUserClient) temp ;
			/**
			 * 每个人收到的 牌面不同，所以不用 ROOM发送广播消息，而是用 遍历房间里所有成员发送消息的方式
			 */
			NettyClients.getInstance().sendGameEventMessage(playerUser.getId(), "play",super.json(new UserBoard(board , playerUser.getId())));
		}
		
		CacheHelper.getGameRoomCacheBean().put(gameRoom.getId(), gameRoom, gameRoom.getOrgi());
		
		
		/**
		 * 发送一个 Begin 事件
		 */
		game.change(gameRoom , BeiMiGameEvent.AUTO.toString() , 2);	//通知状态机 , 此处应由状态机处理异步执行
	}
}
