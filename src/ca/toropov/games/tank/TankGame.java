package ca.toropov.games.tank;

import lombok.Getter;

import java.util.logging.Logger;

public class TankGame {
    @Getter
    private static TankGame instance;
    @Getter
    private final SystemTimer systemTimer = new SystemTimer(60);
    @Getter
    private boolean running;
    @Getter
    private Logger logger = Logger.getLogger("TankGame");

    public TankGame(){
        instance = this;
    }

    public static void main(String... args){
        new TankGame().enable();
    }

    private void enable() {
        running = true;
    }

    public void render() {
        //TODO
    }
}
