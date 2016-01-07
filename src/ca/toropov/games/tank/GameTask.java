package ca.toropov.games.tank;

import ca.toropov.games.tank.states.GameState;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public abstract class GameTask {
    public final GameState gameState;
    @Getter
    private int ticks = 0;
    @Getter
    @Setter
    private int lifeTime = -1;
    @Getter
    private boolean dead;

    public void run() {
        if (dead) {
            TankGame.getInstance().getLogger().severe("Running a dead game task");
            return;
        }

        ticks++;

        if (lifeTime > 0)
            lifeTime--;

        if (lifeTime == 0) {
            gameState.getTasks().remove(this);

        } else
            tick();
    }

    public abstract void tick();
}
