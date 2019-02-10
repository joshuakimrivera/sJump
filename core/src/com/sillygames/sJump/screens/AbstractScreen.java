package com.sillygames.sJump.screens;

import com.badlogic.gdx.Screen;
import com.sillygames.sJump.gameCore;

public abstract class AbstractScreen implements Screen {
    
    gameCore game;
    
    public AbstractScreen(gameCore game) {
        this.game = game;
    }

}
