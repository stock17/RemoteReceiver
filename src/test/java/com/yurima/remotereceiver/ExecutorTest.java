package com.yurima.remotereceiver;

import com.yurima.jwinapi.Jwinapi;
import com.yurima.remoteutils.Command;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExecutorTest {

    @Test
    void whenReceivedVolumeLevel_whenCorrect() {
        double value = 0.33;
        Executor executor = new Executor();
        Command command = new Command(Command.Type.VOLUME_LEVEL, value);
        executor.execute(command);
        assertEquals(value, Jwinapi.getVolumeLevel(), 0.01);
    }
}