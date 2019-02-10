package com.sillygames.sJump.networking;

import com.sillygames.sJump.controls.InputController;
import com.sillygames.sJump.networking.messages.ControlsMessage;
import com.sillygames.sJump.pool.MessageObjectPool;

public class ControlsSender {
    
    public ControlsMessage sendControls(InputController controls) {
        // left | right | up | down | jump | shoot
        ControlsMessage message = MessageObjectPool.instance.
                controlsMessagePool.obtain();
        message.buttonPresses = (byte) ((controls.buttonX() ? 1 : 0) |
                ((controls.buttonA()    ? 1 : 0) << 1) |
                ((controls.axisDown()   ? 1 : 0) << 2) |
                ((controls.axisUp()     ? 1 : 0) << 3) |
                ((controls.axisRight()  ? 1 : 0) << 4) |
                ((controls.axisLeft()   ? 1 : 0) << 5) |
                ((controls.buttonB()    ? 1 : 0) << 6) );
        return message;
    }

}
