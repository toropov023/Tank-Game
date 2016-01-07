package ca.toropov.games.tank.states;

import ca.toropov.games.tank.GameTask;
import lombok.Getter;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: toropov
 * Date: 1/6/2016
 */
public abstract class GameState {
    @Getter
    private final Set<GameTask> tasks = new HashSet<>();

    /**
     * Starting the state.
     * @param previous previously running Game State
     */
    public abstract void onStart(GameState previous);

    /**
     * Ending the state.
     * @param next next Game State to be run
     */
    public abstract void onEnd(GameState next);

    /**
     * Called periodically to render any the necessary graphics.
     * @param graphics2D {@link Graphics2D} reference to draw onto
     */
    public abstract void render(Graphics2D graphics2D, double delta);

    /**
     * Called periodically to perform logic on all the GameTasks in the state.
     */
    public void run(){
        System.out.print("And we are!");
        tasks.forEach(GameTask::run);
    }
}
