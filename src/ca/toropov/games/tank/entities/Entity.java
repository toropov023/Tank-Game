package ca.toropov.games.tank.entities;

import ca.toropov.games.tank.GameTask;
import ca.toropov.games.tank.TankGame;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public abstract class Entity extends GameTask {
    private double x, y, motX, motY = 0;

    public Entity() {
        TankGame.getInstance().registerEntity(this);
    }

    public abstract void draw(Graphics2D g);

    public void kill() {
        TankGame.getInstance().getEntityRegister().remove(this);
    }
}
