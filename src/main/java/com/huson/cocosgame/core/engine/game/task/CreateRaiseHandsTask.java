package com.huson.cocosgame.core.engine.game.task;

import java.util.Arrays;

import org.apache.commons.lang.ArrayUtils;
import org.cache2k.expiry.ValueWithExpiryTime;

import com.huson.cocosgame.core.BMDataContext;
import com.huson.cocosgame.core.engine.game.BeiMiGameEvent;
import com.huson.cocosgame.core.engine.game.BeiMiGameTask;
import com.huson.cocosgame.core.engine.game.GameBoard;
import com.huson.cocosgame.util.GameUtils;
import com.huson.cocosgame.util.cache.CacheHelper;
import com.huson.cocosgame.util.rules.model.Board;
import com.huson.cocosgame.util.rules.model.Player;
import com.huson.cocosgame.web.model.GameRoom;
import com.huson.cocosgame.web.model.PlayUserClient;

public class CreateRaiseHandsTask extends AbstractTask implements ValueWithExpiryTime  , BeiMiGameTask{

	private long timer  ;
	private GameRoom gameRoom = null ;
	private String orgi ;
	
	public CreateRaiseHandsTask(long timer , GameRoom gameRoom, String orgi){
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
		/**
		 * 
		 * 顺手 把牌发了，注：此处应根据 GameRoom的类型获取 发牌方式
		 */
		Board board = (Board) CacheHelper.getBoardCacheBean().getCacheObject(gameRoom.getId(), gameRoom.getOrgi());
		Player lastHandsPlayer = null ;
		for(Player player : board.getPlayers()){
			if(player.getPlayuser().equals(board.getBanker())){//抢到地主的人
				byte[] lastHands = board.pollLastHands() ;
				board.setLasthands(lastHands);
				player.setCards(ArrayUtils.addAll(player.getCards(), lastHands)) ;//翻底牌 
				Arrays.sort(player.getCards());									  //重新排序
				player.setCards(GameUtils.reverseCards(player.getCards()));		  //从大到小 倒序
				lastHandsPlayer = player ;
				break ;
			}
		}
		/**
		 * 计算底牌倍率
		 */
		board.setRatio(board.getRatio() * board.calcRatio());
		
		/**
		 * 发送一个通知，翻底牌消息
		 */
		sendEvent("lasthands", super.json(new GameBoard(lastHandsPlayer.getPlayuser() , board.getLasthands(), board.getRatio())) , gameRoom) ;
		
		/**
		 * 更新牌局状态
		 */
		CacheHelper.getBoardCacheBean().put(gameRoom.getId(), board, orgi);
		/**
		 * 发送一个 开始打牌的事件 ， 判断当前出牌人是 玩家还是 AI，如果是 AI，则默认 1秒时间，如果是玩家，则超时时间是25秒
		 */
		PlayUserClient playUserClient = super.getPlayUserClient(gameRoom.getId(), lastHandsPlayer.getPlayuser(), orgi) ;
		
		if(BMDataContext.PlayerTypeEnum.NORMAL.toString().equals(playUserClient.getPlayertype())){
			game.change(gameRoom , BeiMiGameEvent.PLAYCARDS.toString() , 3);	//应该从 游戏后台配置参数中获取
		}else{
			game.change(gameRoom , BeiMiGameEvent.PLAYCARDS.toString() ,3);	//应该从游戏后台配置参数中获取
		}
	}
}
