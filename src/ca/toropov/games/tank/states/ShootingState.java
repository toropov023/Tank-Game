package ca.toropov.games.tank.states;

import ca.toropov.games.tank.graphics.Sprite;
import ca.toropov.games.tank.utils.SpriteLoader;

import javax.swing.*;
import java.awt.*;

/**
 * Author: toropov
 * Date: 1/8/2016
 */
public class ShootingState extends GameState {
    /**
     * Starting the state.
     *
     * @param previous previously running Game State
     */
    @Override
    public void onStart(GameState previous) {
        Sprite bg = SpriteLoader.load("background.jpg");
        this.addSprite("bg", bg);
    }

    /**
     * Ending the state.
     *
     * @param next next Game State to be run
     */
    @Override
    public void onEnd(GameState next) {

    }

    /**
     * Called periodically to perform logic on all the GameTasks in the state.
     */
    @Override
    public void tick() {

    }
}
