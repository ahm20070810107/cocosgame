package com.huson.cocosgame.core.statemachine;

import com.huson.cocosgame.core.statemachine.config.StateConfigurer;
import com.huson.cocosgame.core.statemachine.config.StateMachineTransitionConfigurer;
import com.huson.cocosgame.core.statemachine.impl.BeiMiStateContext;
import com.huson.cocosgame.core.statemachine.impl.BeiMiTransitionConfigurer;
import org.springframework.stereotype.Component;

@Component
public class BeiMiStateMachine<T,S> {
	/**
	 * 
	 */
	private StateConfigurer<String,String> config = new BeiMiStateContext<>();
	private StateMachineTransitionConfigurer<T,S> transitions = new BeiMiTransitionConfigurer<>() ;
	
	public StateConfigurer<String,String> getConfig() {
		return config;
	}
	public void setConfig(StateConfigurer<String,String> config) {
		this.config = config;
	}
	public StateMachineTransitionConfigurer<T,S> getTransitions() {
		return transitions;
	}
	public void setTransitions(
			StateMachineTransitionConfigurer<T,S> transitions) {
		this.transitions = transitions;
	}
}
