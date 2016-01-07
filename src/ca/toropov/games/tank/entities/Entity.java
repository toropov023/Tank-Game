package ca.toropov.games.tank.entities;

import ca.toropov.games.tank.GameTask;
import ca.toropov.games.tank.graphics.Sprite;
import ca.toropov.games.tank.TankGame;
import ca.toropov.games.tank.states.GameState;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public abstract class Entity extends GameTask {
    private double x, y, motX, motY = 0;
    private Sprite sprite;

    public Entity(Sprite sprite, GameState state) {
        super(state);
        this.sprite = sprite;
    }

    public void draw(Graphics2D g){
        if(sprite != null)
            sprite.draw(g, x, y);
    }

    public void kill() {
        TankGame.getInstance().getEntityRegister().remove(this);
    }
}
