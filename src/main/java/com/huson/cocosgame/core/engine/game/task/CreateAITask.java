package com.huson.cocosgame.core.engine.game.task;

import java.util.List;

import org.cache2k.expiry.ValueWithExpiryTime;

import com.huson.cocosgame.core.BMDataContext;
import com.huson.cocosgame.core.engine.game.BeiMiGameEvent;
import com.huson.cocosgame.core.engine.game.BeiMiGameTask;
import com.huson.cocosgame.util.GameUtils;
import com.huson.cocosgame.util.cache.CacheHelper;
import com.huson.cocosgame.web.model.GameRoom;
import com.huson.cocosgame.web.model.PlayUser;
import com.huson.cocosgame.web.model.PlayUserClient;
import com.corundumstudio.socketio.SocketIOServer;

public class CreateAITask extends AbstractTask implements ValueWithExpiryTime  , BeiMiGameTask{

	private long timer  ;
	private GameRoom gameRoom = null ;
	private String orgi ;
	
	public CreateAITask(long timer , GameRoom gameRoom, String orgi){
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
		//执行生成AI
		GameUtils.removeGameRoom(gameRoom.getId(), orgi);
		List<PlayUserClient> playerList = CacheHelper.getGamePlayerCacheBean().getCacheObject(gameRoom.getId(), gameRoom.getOrgi()) ;
		int aicount = gameRoom.getPlayers() - playerList.size() ;
		if(aicount>0){
			for(int i=0 ; i<aicount ; i++){
				PlayUserClient playerUser = GameUtils.create(new PlayUser() , BMDataContext.PlayerTypeEnum.AI.toString()) ;
				playerUser.setPlayerindex(gameRoom.getPlayers() - playerList.size());
				CacheHelper.getGamePlayerCacheBean().put(gameRoom.getId(), playerUser, orgi); //将用户加入到 room ， MultiCache
				BMDataContext.getContext().getBean(SocketIOServer.class).getRoomOperations(gameRoom.getId()).sendEvent("joinroom",super.json(playerUser));
				playerList.add(playerUser) ;
			}
			/**
			 * 发送一个 Enough 事件
			 */
			game.change(gameRoom , BeiMiGameEvent.ENOUGH.toString());	//通知状态机 , 此处应由状态机处理异步执行
		}
	}
}
