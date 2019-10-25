package com.company;

import com.example.remotemanager.Command;
import com.mymos.jwinapi.Jwinapi;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;



public class Receiver {

    private static final int PORT = 9876;

    private ServerSocket serverSocket;
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    public void connect()  {

        while (true) {

            try {
                serverSocket = new ServerSocket(PORT);
                socket = serverSocket.accept();
                System.out.println("Connected");

                outputStream = socket.getOutputStream();
                inputStream = socket.getInputStream();

                objectOutputStream = new ObjectOutputStream((outputStream));
                objectInputStream = new ObjectInputStream((inputStream));

                objectOutputStream.writeDouble(Jwinapi.getVolumeLevel());
                objectOutputStream.flush();

                Command command;

                while ((command = (Command) objectInputStream.readObject()) != null) {
                    exec(command);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
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

    private void exec(Command command) {
        switch (command.getType()) {
            case VOLUME_LEVEL:
                Jwinapi.setVolumeLevel(command.getValue());
                break;
            case SLEEP:
                Jwinapi.sleepMode();
                break;
            case PRESS_SPACE_KEY:
                Jwinapi.pressSpaceKey();
                break;
            case PRESS_LEFT_KEY:
                Jwinapi.pressLeftKey();
                break;
            case PRESS_RIGHT_KEY:
                Jwinapi.pressRightKey();
                break;
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
