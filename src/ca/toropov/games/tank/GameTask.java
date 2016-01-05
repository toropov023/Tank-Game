package ca.toropov.games.tank;

import lombok.Getter;
import lombok.Setter;

public abstract class GameTask {
    @Getter
    private int ticks = 0;
    @Getter
    @Setter
    private int lifeTime = -1;
    @Getter
    private boolean dead;

    public void run(double delta) {
        if (dead) {
            TankGame.getInstance().getLogger().severe("Running a dead game task");
            return;
        }

        ticks++;

        if (lifeTime > 0)
            lifeTime--;

        if (lifeTime == 0) {
            TankGame.getInstance().getSystemTimer().remove(this);

        } else
            tick(delta);
    }

    public abstract void tick(double delta);
}
