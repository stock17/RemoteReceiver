package com.yurima.remotereceiver;

public class Main {

    private static int DEFAULT_PORT = 9876;
    public static void main(String[] args) {
        int currentPort = getPort(args);
        Receiver receiver = new Receiver(currentPort);
        receiver.connect();
    }

    private static int getPort(String[] args) {
        if (args.length < 1){
            return DEFAULT_PORT;
        }
        int currentPort = DEFAULT_PORT;
        String stringPort = args[0];
        try{
           int intPort = Integer.parseInt(stringPort);
           if (intPort < 0 || intPort > 65535){
               throw new IllegalArgumentException("Incorrect port value");
           }
           currentPort = intPort;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.out.println("Incorrect port format. The default port " + DEFAULT_PORT + " will be used.");
            return DEFAULT_PORT;
        }
        return currentPort;
    }
}
