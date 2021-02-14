package com.yurima.remotereceiver;

import com.yurima.remoteutils.Command;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static org.mockito.Mockito.*;

class ReceiverTest {
    private final int port = 9876;
    private final String localhost = "127.0.0.1";
    private Executor executor;


    @BeforeEach
    void setUp() {
        executor = mock(Executor.class);
    }

    @Test
    void whenGetCommand_whenExecute() throws IOException, InterruptedException {
        Receiver receiver = new Receiver(port, executor);
        new Thread(receiver).start();
        Socket socket = new Socket(localhost, port);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        Command command = new Command(Command.Type.VOLUME_LEVEL, 0.55);
        oos.writeObject(command);
        oos.flush();
        Thread.sleep(1000); // give control to receiver
        verify(executor, times(1)).execute(any(Command.class));
    }
}