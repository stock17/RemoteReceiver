package com.yurima.remotereceiver;

import com.yurima.jwinapi.Jwinapi;
import com.yurima.remoteutils.Command;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The class receives messages (commands) from RemoteManager service sends them to execute
 */
public class Receiver implements Runnable {

    private final int port;
    private final Executor executor;
    private ServerSocket serverSocket;

    public Receiver(int port, Executor executor) {
        this.port = port;
        this.executor = executor;
    }

    /**
     * The method creates a server socket and accepts connections.
     */
    @Override
    public void run(){
        try {
            serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        while (true) {
            try (Socket socket = serverSocket.accept();
                 ObjectOutputStream oos = new ObjectOutputStream((socket.getOutputStream()));
                 ObjectInputStream ois = new ObjectInputStream((socket.getInputStream()));
            ) {
                returnVolumeLevel(oos);
                receiveCommands(ois);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void receiveCommands(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        Command command;
        while ((command = (Command) ois.readObject()) != null) {
            executor.execute(command);
        }
    }

    private void returnVolumeLevel(ObjectOutputStream oos) throws IOException {
        oos.writeObject(new Command(Command.Type.VOLUME_LEVEL, Jwinapi.getVolumeLevel()));
        oos.flush();
    }
}
