package ca.toropov.games.tank.graphics;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class DisplayManager extends Canvas {
    private BufferStrategy strategy;
    @Getter
    private boolean enabled = false;
    @Getter
    private JFrame frame;

    public DisplayManager(String title) {
        super.setBackground(Color.BLACK);

        this.frame = new JFrame(title);
        frame.getContentPane().add(this);
        setIgnoreRepaint(true);

        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);

        createBufferStrategy(2);
        strategy = getBufferStrategy();
    }

    public Graphics2D getGraphics2D() {
        return (Graphics2D) strategy.getDrawGraphics();
    }

    public void display() {
        strategy.getDrawGraphics().dispose();
        strategy.show();
    }
}
