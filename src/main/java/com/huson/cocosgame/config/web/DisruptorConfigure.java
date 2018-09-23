package com.huson.cocosgame.config.web;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.huson.cocosgame.util.disruptor.UserDataEventFactory;
import com.huson.cocosgame.util.disruptor.UserEventHandler;
import com.huson.cocosgame.util.event.UserDataEvent;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

@Component
public class DisruptorConfigure {
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Bean(name="disruptor")   
    public Disruptor<UserDataEvent> disruptor() {   

	 ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,new BasicThreadFactory.Builder().namingPattern("disruptor-name-%d").build());
	 UserDataEventFactory factory = new UserDataEventFactory();
	 Disruptor<UserDataEvent> disruptor = new Disruptor<UserDataEvent>(factory, 1024, executorService, ProducerType.SINGLE , new SleepingWaitStrategy());
	 disruptor.setDefaultExceptionHandler(new BeiMiExceptionHandler());
	 disruptor.handleEventsWith(new UserEventHandler());
	 disruptor.start();
	 return disruptor;
    }  
}
