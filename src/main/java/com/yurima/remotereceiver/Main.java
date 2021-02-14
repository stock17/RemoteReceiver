package com.yurima.remotereceiver;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The class creates and starts Receiver process
 */
public class Main {

    private static final int DEFAULT_PORT = 9876;
    private static final int MAX_PORT = 9876;

    public static void main(String[] args) throws IOException {
        int port = getPort(args);
        Receiver receiver = new Receiver(port, new Executor());
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(receiver);
    }

    private static int getPort(String[] args) {
        if (args.length < 1){
            return DEFAULT_PORT;
        }
        String stringPort = args[0];
        try{
           int intPort = Integer.parseInt(stringPort);
           if (intPort < 0 || intPort > MAX_PORT){
               throw new IllegalArgumentException("Incorrect port value");
           }
           return intPort;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.out.println("Incorrect port format. The default port " + DEFAULT_PORT + " will be used.");
            return DEFAULT_PORT;
        }
    }
}
