package com.huson.cocosgame.core.engine.game.action;

import com.huson.cocosgame.core.statemachine.action.Action;
import com.huson.cocosgame.core.statemachine.impl.BeiMiExtentionTransitionConfigurer;
import com.huson.cocosgame.core.statemachine.message.Message;

public class EventAction<T,S> implements Action<T, S>{

	@Override
	public void execute(Message<T> message, BeiMiExtentionTransitionConfigurer<T,S> configurer) {
		
	}
}
