package com.sillygames.sJump.networking;

import java.util.ArrayList;
import java.util.HashMap;

import org.objenesis.instantiator.ObjectInstantiator;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryonet.EndPoint;
import com.sillygames.sJump.networking.messages.AudioMessage;
import com.sillygames.sJump.networking.messages.ClientDetailsMessage;
import com.sillygames.sJump.networking.messages.ConnectMessage;
import com.sillygames.sJump.networking.messages.ControlsMessage;
import com.sillygames.sJump.networking.messages.EntityState;
import com.sillygames.sJump.networking.messages.GameStateMessage;
import com.sillygames.sJump.networking.messages.PlayerNamesMessage;
import com.sillygames.sJump.networking.messages.ServerStatusMessage;
import com.sillygames.sJump.pool.MessageObjectPool;

public class NetworkRegisterer {
    
    static public void register (EndPoint endPoint) {
        Registration registration;
        Kryo kryo = endPoint.getKryo();
        registration = kryo.register(ConnectMessage.class);
        registration.setInstantiator(new ObjectInstantiator<ConnectMessage>() {
            @Override
            public ConnectMessage newInstance() {
                return MessageObjectPool.instance.connectMessagePool.obtain();
            }
        });
        
        registration = kryo.register(ControlsMessage.class);
        registration.setInstantiator(new 
                ObjectInstantiator<ControlsMessage>() {
            
            @Override
            public ControlsMessage newInstance() {
                return MessageObjectPool.instance.controlsMessagePool.obtain();
            }
            
        });
        
        registration = kryo.register(EntityState.class);
        registration.setInstantiator(new 
                ObjectInstantiator<EntityState>() {
            
            @Override
            public EntityState newInstance() {
                return MessageObjectPool.instance.entityStatePool.obtain();
            }
            
        });
        
        registration = kryo.register(GameStateMessage.class);
        registration.setInstantiator(new 
                ObjectInstantiator<GameStateMessage>() {
            
            @Override
            public GameStateMessage newInstance() {
                return MessageObjectPool.instance.gameStateMessagePool.obtain();
            }
            
        });
        
        registration = kryo.register(AudioMessage.class);
        registration.setInstantiator(new 
                ObjectInstantiator<AudioMessage>() {
            
            @Override
            public AudioMessage newInstance() {
                return MessageObjectPool.instance.audioMessagePool.obtain();
            }
            
        });
        
        kryo.register(PlayerNamesMessage.class);
        kryo.register(ClientDetailsMessage.class);
        kryo.register(ServerStatusMessage.class);
        kryo.register(ServerStatusMessage.Status.class);
        kryo.register(ArrayList.class);
        kryo.register(Vector2.class);
        kryo.register(String.class);
        kryo.register(HashMap.class);
    }
    
}