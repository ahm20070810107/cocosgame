package com.huson.cocosgame.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.huson.cocosgame.config.web.model.Game;
import com.huson.cocosgame.core.statemachine.BeiMiStateMachine;
import com.huson.cocosgame.core.statemachine.impl.BeiMiMachineHandler;

@Configuration
public class BeiMiStateMachineHandlerConfig {
	
	@Autowired
	private BeiMiStateMachine<String,String> beiMiStateMachine;
	
    @Bean
    public Game persist() {
        return new Game(persistStateMachineHandler());
    }

    public BeiMiMachineHandler persistStateMachineHandler() {
        return new BeiMiMachineHandler(this.beiMiStateMachine);
    }
}
