package ca.toropov.games.tank.states;

import ca.toropov.games.tank.SystemTimer;
import ca.toropov.games.tank.TankGame;
import lombok.Getter;
import lombok.NonNull;

/**
 * Author: toropov
 * Date: 1/6/2016
 */
public class GameStateManager {
    @Getter
    private GameState current;

    public void startState(@NonNull GameState state) {
        System.out.print(state);
        if (current != null) {
            current.onEnd(state);
        }

        System.out.print("Well?");
        TankGame.getInstance().getLogger().info("Starting a new Game State " + state.getClass().getSimpleName());

        state.onStart(current);
        current = state;
    }

    public boolean hasState(){
        return current != null;
    }

    public void endCurrentState(){
        if(current == null){
            TankGame.getInstance().getLogger().warning("Unable to end the current state: NULL");
        }

        current.onEnd(null);
        current = null;
    }
}