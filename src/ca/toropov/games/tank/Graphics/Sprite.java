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

    public void draw(Graphics g, int x, int y) {
        g.drawImage(image, x, y, null);
    }
}
