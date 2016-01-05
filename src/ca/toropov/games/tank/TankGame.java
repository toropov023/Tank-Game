package ca.toropov.games.tank;

import ca.toropov.games.tank.Graphics.DisplayManager;
import ca.toropov.games.tank.entities.Entity;
import ca.toropov.games.tank.entities.EntityRegister;
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
    private Logger logger;
    @Getter
    private EntityRegister entityRegister;
    @Getter
    private DisplayManager displayManager;

    public TankGame() {
        instance = this;
    }

    public static void main(String... args) {
        new TankGame().enable();
    }

    private void enable() {
        running = true;

        logger = Logger.getLogger("TankGame");
        entityRegister = new EntityRegister();
        displayManager = new DisplayManager();

    }

    public void registerEntity(Entity e) {
        systemTimer.add(e);
    }

    public void render() {
        displayManager.display();
    }
}
