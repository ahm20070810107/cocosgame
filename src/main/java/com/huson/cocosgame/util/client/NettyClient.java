package com.huson.cocosgame.util.client;

import com.huson.cocosgame.util.server.handler.BeiMiClient;

public interface NettyClient {
	
	public BeiMiClient getClient(String key) ;
	
	public void putClient(String key , BeiMiClient client) ;
	
	public void removeClient(String key) ;
}
