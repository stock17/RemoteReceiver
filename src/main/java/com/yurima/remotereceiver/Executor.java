package com.yurima.remotereceiver;

import com.yurima.jwinapi.Jwinapi;
import com.yurima.remoteutils.Command;

/**
 * The class executes the received commands
 */
public class Executor {

    /**
     * The method executes the received commands
     *
     * @param command received {@link com.yurima.remoteutils.Command}
     */
    void execute(Command command) {
        switch (command.getType()) {
            case VOLUME_LEVEL:
                Jwinapi.setVolumeLevel(command.getValue());
                break;
            case PRESS_KEY:
                Jwinapi.pressKey((int)command.getValue());
                break;
            case SLEEP:
                Jwinapi.sleepMode();
                break;
        }
    }
}