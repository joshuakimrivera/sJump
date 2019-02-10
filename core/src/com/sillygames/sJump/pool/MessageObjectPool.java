package com.sillygames.sJump.pool;

import com.sillygames.sJump.helpers.Event;
import com.sillygames.sJump.networking.messages.AudioMessage;
import com.sillygames.sJump.networking.messages.ConnectMessage;
import com.sillygames.sJump.networking.messages.ControlsMessage;
import com.sillygames.sJump.networking.messages.EntityState;
import com.sillygames.sJump.networking.messages.GameStateMessage;

public class MessageObjectPool {
    
    public static MessageObjectPool instance = new MessageObjectPool();
    public Pool<ConnectMessage> connectMessagePool;
    public Pool<ControlsMessage> controlsMessagePool;
    public Pool<EntityState> entityStatePool;
    public Pool<GameStateMessage> gameStateMessagePool;
    public Pool<Event> eventPool;
    public Pool<AudioMessage> audioMessagePool;
    
    public MessageObjectPool() {
        connectMessagePool = new Pool<ConnectMessage>() {

            @Override
            protected ConnectMessage getNewObject() {
                return new ConnectMessage();
            }
            
        };
        
        controlsMessagePool = new Pool<ControlsMessage>() {

            @Override
            protected ControlsMessage getNewObject() {
                return new ControlsMessage();
            }
            
        };
        
        entityStatePool = new Pool<EntityState>() {

            @Override
            protected EntityState getNewObject() {
                return new EntityState();
            }
            
        };
        entityStatePool.setMax(1024);
        
        gameStateMessagePool = new Pool<GameStateMessage>() {
            
            @Override
            protected GameStateMessage getNewObject() {
                return new GameStateMessage();
            }
        };
        gameStateMessagePool.setMax(512);
        
        eventPool = new Pool<Event>() {
            
            @Override
            protected Event getNewObject() {
                return new Event();
            }
        };
        
        audioMessagePool = new Pool<AudioMessage>() {
            
            @Override
            protected AudioMessage getNewObject() {
                return new AudioMessage();
            }
        };
    }

}
