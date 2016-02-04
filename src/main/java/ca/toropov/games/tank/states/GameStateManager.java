package ca.toropov.games.tank.states;

import ca.toropov.games.tank.TankGame;
import lombok.Getter;
import lombok.NonNull;

import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;

/**
 * Author: toropov
 * Date: 1/6/2016
 */
public class GameStateManager {
    @Getter
    private GameState current;

    /**
     * Starts a new {@link GameState} and stops the currently running (if present).
     * <p>
     * Automatically registered with the JFrame if class implements any of the following:
     * <ul>
     *  <li>{@link java.awt.event.MouseListener}
     *  <li>{@link java.awt.event.MouseMotionListener}
     *  <li>{@link java.awt.event.MouseWheelListener}
     *  <li>{@link java.awt.event.KeyListener}
     * </ul>
     * @param state GameState instance.
     */
    public void startState(@NonNull GameState state) {
        if (current != null) {
            current.onEnd(state);
            revokeListeners(current);
        }

        TankGame.getInstance().getLogger().info("Starting a new Game State " + state.getClass().getSimpleName());

        registerListeners(state);
        state.onStart(current);
        current = state;
    }

    private void registerListeners(GameState state) {
        JFrame frame = TankGame.getInstance().getDisplayManager().getFrame();

        if (state instanceof MouseListener)
            frame.addMouseListener((MouseListener) state);
        if (state instanceof MouseMotionListener)
            frame.addMouseMotionListener((MouseMotionListener) state);
        if (state instanceof MouseWheelListener)
            frame.addMouseWheelListener((MouseWheelListener) state);
        if (state instanceof KeyListener)
            frame.addKeyListener((KeyListener) state);
    }

    private void revokeListeners(GameState state){
        JFrame frame = TankGame.getInstance().getDisplayManager().getFrame();

        if (state instanceof MouseListener)
            frame.removeMouseListener((MouseListener) state);
        if (state instanceof MouseMotionListener)
            frame.removeMouseMotionListener((MouseMotionListener) state);
        if (state instanceof MouseWheelListener)
            frame.removeMouseWheelListener((MouseWheelListener) state);
        if (state instanceof KeyListener)
            frame.removeKeyListener((KeyListener) state);
    }

    public boolean hasState() {
        return current != null;
    }

    public void endCurrentState() {
        if (current == null) {
            TankGame.getInstance().getLogger().warning("Unable to end the current state: NULL");
        }

        current.onEnd(null);
        current = null;
    }
}