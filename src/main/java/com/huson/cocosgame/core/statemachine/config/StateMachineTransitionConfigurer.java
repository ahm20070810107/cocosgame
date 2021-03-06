package com.huson.cocosgame.core.statemachine.config;

import com.huson.cocosgame.core.statemachine.impl.BeiMiExtentionTransitionConfigurer;


public interface StateMachineTransitionConfigurer<S,E> {
	/**
	 * Gets a configurer for external transition.
	 *
	 * @return {@link ExternalTransitionConfigurer} for chaining
	 * @throws Exception if configuration error happens
	 * @see #withLocal()
	 */
	ExternalTransitionConfigurer<S, E> withExternal() throws Exception;
	
	void apply(BeiMiExtentionTransitionConfigurer<S, E> transition);
	
	BeiMiExtentionTransitionConfigurer<S,E> transition(S event) ; 
}
