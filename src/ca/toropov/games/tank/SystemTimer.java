package ca.toropov.games.tank;

public class SystemTimer {
    final double GAME_HERTZ = 20.0;
    final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
    final int MAX_UPDATES_BEFORE_RENDER = 5;
    double lastUpdateTime = System.nanoTime();
    double lastRenderTime = System.nanoTime();

    final double TARGET_FPS;
    final double TARGET_TIME_BETWEEN_RENDERS;

    public SystemTimer(int fps) {
        this.TARGET_FPS = fps;
        this.TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;
    }

    public void startTicking() {
        while (TankGame.getInstance().isRunning()) {
            double now = System.nanoTime();
            int updateCount = 0;

            while (now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER) {
                if (TankGame.getInstance().getStateManager().hasState()) {
                    TankGame.getInstance().getStateManager().getCurrent().run();
                }
                lastUpdateTime += TIME_BETWEEN_UPDATES;
                updateCount++;
            }

            if (now - lastUpdateTime > TIME_BETWEEN_UPDATES) {
                lastUpdateTime = now - TIME_BETWEEN_UPDATES;
            }

            double delta = Math.min(1.0D, ((now - lastUpdateTime) / TIME_BETWEEN_UPDATES));
            TankGame.getInstance().render(delta);
            lastRenderTime = now;

            while (now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
                Thread.yield();

                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                now = System.nanoTime();
            }
        }
    }
}
