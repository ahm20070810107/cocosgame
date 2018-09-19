package com.huson.cocosgame.core.statemachine.action;

import com.huson.cocosgame.core.statemachine.impl.BeiMiExtentionTransitionConfigurer;
import com.huson.cocosgame.core.statemachine.message.Message;

public interface Action<T,S> {
	void execute(Message<T> message , BeiMiExtentionTransitionConfigurer<T, S> configurer); 
}
