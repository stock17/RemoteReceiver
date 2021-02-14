package com.yurima.remotereceiver;

import com.yurima.jwinapi.Jwinapi;
import com.yurima.remoteutils.Command;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The class receives messages (commands) from RemoteManager service and execute them
 */
public class Receiver {

    private final int port;
    private final Executor executor = new Executor();

    private ServerSocket serverSocket;
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    public Receiver(int port) {
        this.port = port;
    }

    /**
     * The method creates a server socket and accepts connections.
     */
    public void connect()  {
        while (true) {
            try {
                serverSocket = new ServerSocket(this.port);
                socket = serverSocket.accept();
                System.out.println("Connected");
                outputStream = socket.getOutputStream();
                inputStream = socket.getInputStream();
                objectOutputStream = new ObjectOutputStream((outputStream));
                objectInputStream = new ObjectInputStream((inputStream));
                objectOutputStream.writeObject(new Command(Command.Type.VOLUME_LEVEL, Jwinapi.getVolumeLevel()));
                objectOutputStream.flush();
                Command command;
                while ((command = (Command) objectInputStream.readObject()) != null) {
                    executor.execute(command);
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    closeAll();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean closeAll() throws IOException {
        if (objectInputStream != null) objectInputStream.close();
        if (inputStream != null) inputStream.close();
        if (objectOutputStream != null) objectOutputStream.close();
        if (outputStream != null) outputStream.close();
        if (socket != null) socket.close();
        if (serverSocket != null) serverSocket.close();
        return true;
    }
}
