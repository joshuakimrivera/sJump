package com.sillygames.sJump.networking.messages;

import com.sillygames.sJump.pool.Poolable;

public class ControlsMessage implements Poolable{
    // left | right | up | down | jump | shoot
    public byte buttonPresses = 0;
    
    public boolean shoot() {
        return (buttonPresses & 0x01) > 0; 
    }
    
    public boolean jump() {
        return (buttonPresses & 0x02) > 0; 
    }
    
    public boolean down() {
        return (buttonPresses & 0x04) > 0; 
    }
    
    public boolean up() {
        return (buttonPresses & 0x08) > 0; 
    }
    
    public boolean right() {
        return (buttonPresses & 0x10) > 0; 
    }
    
    public boolean left() {
        return (buttonPresses & 0x20) > 0; 
    }
    
    public boolean throwBomb() {
        return (buttonPresses & 0x40) > 0; 
    }

    @Override
    public void reset() {
        buttonPresses = 0;
    }
}
