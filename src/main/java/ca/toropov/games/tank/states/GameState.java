package ca.toropov.games.tank.states;

import ca.toropov.games.tank.GameTask;
import ca.toropov.games.tank.TankGame;
import ca.toropov.games.tank.graphics.Sprite;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Author: toropov
 * Date: 1/6/2016
 */
public abstract class GameState implements EventListener {
    @Getter
    private final Set<GameTask> tasks = new HashSet<>();
    @Getter
    private final Map<String, Sprite> spriteMap = new HashMap<>();
    @Getter
    private JFrame jFrame = TankGame.getInstance().getDisplayManager().getFrame();

    /**
     * Starting the state.
     *
     * @param previous previously running Game State
     */
    public abstract void onStart(GameState previous);

    /**
     * Ending the state.
     *
     * @param next next Game State to be run
     */
    public abstract void onEnd(GameState next);

    /**
     * Called periodically to render any the necessary graphics.
     *
     * @param graphics2D {@link Graphics2D} reference to draw onto
     * @param delta      Interpolation time
     */
    public void render(Graphics2D graphics2D, double delta) {
        spriteMap.entrySet().forEach(s -> s.getValue().draw(graphics2D));
    }

    public void run() {
        tasks.forEach(GameTask::run);
    }

    /**
     * Called periodically to perform logic on all the GameTasks in the state.
     */
    public abstract void tick();

    /**
     * Add a sprite to get rendered on every frame.
     * @param id Sprite id. Used to refer to it back later on.
     * @param sprite {@link Sprite} object
     * @return Either previously allocated sprite with the id or null.
     */
    public Sprite addSprite(String id, Sprite sprite){
        return spriteMap.put(id, sprite);
    }
}
