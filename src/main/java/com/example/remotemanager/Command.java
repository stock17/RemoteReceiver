package main.java.com.example.remotemanager;


import java.io.Serializable;

public class Command implements Serializable {
    private Command.Type type;
    private double value;

    public Command(Type type, double value) {
        this.type = type;
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public double getValue() {
        return value;
    }

    public enum Type {
        VOLUME_LEVEL,
        SLEEP,
        PRESS_SPACE_KEY,
        PRESS_LEFT_KEY,
        PRESS_RIGHT_KEY
    }
}
