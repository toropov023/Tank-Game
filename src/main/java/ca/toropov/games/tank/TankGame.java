package ca.toropov.games.tank;

import ca.toropov.games.tank.graphics.DisplayManager;
import ca.toropov.games.tank.entities.Entity;
import ca.toropov.games.tank.entities.EntityRegister;
import ca.toropov.games.tank.states.GameState;
import ca.toropov.games.tank.states.GameStateManager;
import ca.toropov.games.tank.states.ShootingState;
import ca.toropov.games.tank.utils.KeyRegister;
import lombok.Getter;

import java.awt.*;
import java.awt.event.KeyEvent;
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
    @Getter
    private GameStateManager stateManager;
    @Getter
    private KeyRegister keyRegister;

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
        displayManager = new DisplayManager("Tank Game");
        stateManager = new GameStateManager();
        keyRegister = new KeyRegister();

        displayManager.getFrame().addKeyListener(keyRegister);

        stateManager.startState(new ShootingState());

        systemTimer.startTicking();
    }

    public void render(double delta) {
        if(stateManager.getCurrent() != null)
            stateManager.getCurrent().render(displayManager.getGraphics2D(), delta);
        displayManager.display();
    }
}
