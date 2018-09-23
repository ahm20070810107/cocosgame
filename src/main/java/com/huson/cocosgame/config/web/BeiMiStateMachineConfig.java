package com.huson.cocosgame.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.huson.cocosgame.core.engine.game.BeiMiGameEnum;
import com.huson.cocosgame.core.engine.game.BeiMiGameEvent;
import com.huson.cocosgame.core.engine.game.action.AutoAction;
import com.huson.cocosgame.core.engine.game.action.EnoughAction;
import com.huson.cocosgame.core.engine.game.action.EnterAction;
import com.huson.cocosgame.core.engine.game.action.EventAction;
import com.huson.cocosgame.core.engine.game.action.JoinAction;
import com.huson.cocosgame.core.engine.game.action.PlayCardsAction;
import com.huson.cocosgame.core.engine.game.action.RaiseHandsAction;
import com.huson.cocosgame.core.statemachine.BeiMiStateMachine;
import com.huson.cocosgame.core.statemachine.config.StateConfigurer;
import com.huson.cocosgame.core.statemachine.config.StateMachineTransitionConfigurer;

@Configuration
public class BeiMiStateMachineConfig<T, S>  {
	
	@Bean
	public BeiMiStateMachine<String,String> create() throws Exception{
		BeiMiStateMachine<String,String> beiMiStateMachine = new BeiMiStateMachine<>();
		this.configure(beiMiStateMachine.getConfig());
		this.configure(beiMiStateMachine.getTransitions());
		return beiMiStateMachine;
	}
	
    public void configure(StateConfigurer<String,String> states)
            throws Exception {
        states
            .withStates()
                .initial(BeiMiGameEnum.NONE.toString())
                    .state(BeiMiGameEnum.CRERATED.toString())
                    .state(BeiMiGameEnum.WAITTING.toString())
                    .state(BeiMiGameEnum.READY.toString())
                    .state(BeiMiGameEnum.BEGIN.toString())
                    .state(BeiMiGameEnum.PLAY.toString())
                    .state(BeiMiGameEnum.END.toString());
	}

    public void configure(StateMachineTransitionConfigurer<String, String> transitions)
            throws Exception {
		/**
		 * 状态切换：BEGIN->WAITTING->READY->PLAY->END
		 */
        transitions
	        .withExternal()	
		    	.source(BeiMiGameEnum.NONE.toString()).target(BeiMiGameEnum.CRERATED.toString())
		    	.event(BeiMiGameEvent.ENTER.toString()).action(new EnterAction<>())
		    	.and()
		    .withExternal()	
	        	.source(BeiMiGameEnum.CRERATED.toString()).target(BeiMiGameEnum.WAITTING.toString())
	        	.event(BeiMiGameEvent.JOIN.toString()).action(new JoinAction<>())
	        	.and()
            .withExternal()	
                .source(BeiMiGameEnum.WAITTING.toString()).target(BeiMiGameEnum.READY.toString())
                .event(BeiMiGameEvent.ENOUGH.toString()).action(new EnoughAction<>())
                .and()
            .withExternal()
                .source(BeiMiGameEnum.READY.toString()).target(BeiMiGameEnum.BEGIN.toString())
				//抢地主
                .event(BeiMiGameEvent.AUTO.toString()).action(new AutoAction<>())
                .and()
            .withExternal()
                .source(BeiMiGameEnum.BEGIN.toString()).target(BeiMiGameEnum.LASTHANDS.toString())
                .event(BeiMiGameEvent.RAISEHANDS.toString()).action(new RaiseHandsAction<>())
                .and()
            .withExternal()
                .source(BeiMiGameEnum.LASTHANDS.toString()).target(BeiMiGameEnum.PLAY.toString())
                .event(BeiMiGameEvent.PLAYCARDS.toString()).action(new PlayCardsAction<>())
                .and()
            .withExternal()
                .source(BeiMiGameEnum.PLAY.toString()).target(BeiMiGameEnum.END.toString())
                .event(BeiMiGameEvent.ALLCARDS.toString()).action(new EventAction<>())
            ;
    }
}
