package com.sillygames.sJump.networking.messages;

import java.util.ArrayList;
import com.sillygames.sJump.pool.Poolable;

public class GameStateMessage implements Poolable{
    
    public ArrayList<EntityState> states;
    public long time;
    
    public GameStateMessage() {
        states = new ArrayList<EntityState>();
    }
    
    public void addNewState(EntityState state) {
        states.add(state);
    }

    @Override
    public void reset() {
        states.clear();
    }

}
