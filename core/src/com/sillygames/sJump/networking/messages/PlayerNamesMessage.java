package com.sillygames.sJump.networking.messages;

import java.util.HashMap;

public class PlayerNamesMessage {
    
    public HashMap<Short, String> players;
    
    public PlayerNamesMessage() {
        players = new HashMap<Short, String>();
    }

}
