package com.huson.cocosgame.util.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.huson.cocosgame.config.web.model.Game;
import com.huson.cocosgame.core.BMDataContext;
import com.huson.cocosgame.util.server.handler.GameEventHandler;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
  
@Component  
public class ServerRunner implements CommandLineRunner {  
    private final SocketIOServer server;
    private final SocketIONamespace gameSocketNameSpace ;
    
    @Autowired
    private Game game ;
    
    @Autowired  
    public ServerRunner(SocketIOServer server) {  
        this.server = server;  
        gameSocketNameSpace = server.addNamespace(BMDataContext.NameSpaceEnum.GAME.getNamespace())  ;
    }
    
    @Bean(name="gameNamespace")
    public SocketIONamespace getGameSocketIONameSpace(SocketIOServer server ){
    	gameSocketNameSpace.addListeners(new GameEventHandler(server , game));
    	return gameSocketNameSpace  ;
    }
    
    public void run(String... args) throws Exception { 
        server.start();  
        BMDataContext.setIMServerStatus(true);	//IMServer 启动成功
    }  
}  