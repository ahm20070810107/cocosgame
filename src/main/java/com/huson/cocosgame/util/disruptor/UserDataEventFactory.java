package com.huson.cocosgame.util.disruptor;

import com.huson.cocosgame.util.event.UserDataEvent;
import com.lmax.disruptor.EventFactory;

public class UserDataEventFactory implements EventFactory<UserDataEvent>{

	@Override
	public UserDataEvent newInstance() {
		return new UserDataEvent();
	}
}
