package ca.toropov.games.tank.graphics;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.awt.*;

@RequiredArgsConstructor
public class Sprite {
    private final Image image;
    @Getter
    private double x, y = 0;

    public void move(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public int getWidth() {
        return image.getWidth(null);
    }

    public int getHeight() {
        return image.getHeight(null);
    }

    public void draw(Graphics g) {
        g.drawImage(image, (int) Math.round(x), (int) Math.round(y), null);
    }
}
