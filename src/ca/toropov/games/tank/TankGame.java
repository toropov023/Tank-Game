package ca.toropov.games.tank;

import ca.toropov.games.tank.graphics.DisplayManager;
import ca.toropov.games.tank.entities.Entity;
import ca.toropov.games.tank.entities.EntityRegister;
import ca.toropov.games.tank.states.GameState;
import ca.toropov.games.tank.states.GameStateManager;
import lombok.Getter;

import java.awt.*;
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

        //TODO tests
        GameState state = new GameState() {
            @Override
            public void onStart(GameState previous) {
                TankGame.getInstance().getLogger().info("Test state started!");
            }

            @Override
            public void onEnd(GameState next) {
                TankGame.getInstance().getLogger().info("Test state ended!");
            }

            @Override
            public void render(Graphics2D graphics2D, double delta) {
                TankGame.getInstance().getLogger().info("Test state rendered!");
            }

            @Override
            public void run() {
                super.run();
                TankGame.getInstance().getLogger().info("Test state ticked!");
            }
        };

        state.getTasks().add(new Entity(null, state) {
            @Override
            public void tick() {
                TankGame.getInstance().getLogger().info("Test Game Task is ticked!");
                this.kill();
                TankGame.getInstance().getStateManager().endCurrentState();
            }
        });

        stateManager.startState(state);

        systemTimer.startTicking();
    }

    public void render(double delta) {
        if(stateManager.getCurrent() != null)
            stateManager.getCurrent().render(displayManager.getGraphics2D(), delta);
        displayManager.display();
    }
}
