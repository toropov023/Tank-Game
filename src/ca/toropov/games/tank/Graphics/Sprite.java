package ca.toropov.games.tank.Graphics;

import lombok.RequiredArgsConstructor;

import java.awt.*;

@RequiredArgsConstructor
public class Sprite {
    private final Image image;

    public int getWidth() {
        return image.getWidth(null);
    }

    public int getHeight() {
        return image.getHeight(null);
    }

    public void draw(Graphics g, double x, double y) {
        g.drawImage(image, (int) Math.round(x), (int) Math.round(y), null);
    }
}
