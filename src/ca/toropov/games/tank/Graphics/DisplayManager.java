package ca.toropov.games.tank.Graphics;

import lombok.Getter;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class DisplayManager extends Canvas {
    private BufferStrategy strategy;
    @Getter
    private boolean enabled = false;

    public DisplayManager() {
        strategy = getBufferStrategy();

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        super.setSize(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
        super.setBackground(Color.BLACK);
    }

    public Graphics2D getGraphics() {
        return (Graphics2D) strategy.getDrawGraphics();
    }

    public void display() {
        strategy.getDrawGraphics().dispose();
        strategy.show();
    }
}
