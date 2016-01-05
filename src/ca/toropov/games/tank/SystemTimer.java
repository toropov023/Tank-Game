package ca.toropov.games.tank;

import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class SystemTimer {
    private long lastLoopTime = System.nanoTime();
    private final int FPS;
    private final long OPTIMAL_TIME = 1000000000 / FPS;

    private Set<GameTask> tasks = new HashSet<>();

    public void add(GameTask gameTask) {
        tasks.add(gameTask);
    }

    public void remove(GameTask gameTask) {
        tasks.remove(gameTask);
    }

    public void startTicking() {
        while (TankGame.getInstance().isRunning()) {

            long now = System.nanoTime();
            long length = now - lastLoopTime;
            lastLoopTime = now;
            double delta = length / ((double) OPTIMAL_TIME);

            tasks.forEach(g -> g.run(delta));

            TankGame.getInstance().render();

            try {
                Thread.sleep((lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
