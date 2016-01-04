package ca.toropov.games.tank;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

//####################################
//	Name		|	Tank Shooter Game
//	Made By 	|	Kirill Toropov
//	Created 	|	June 2013
//	Version 	|	1.0
//	Last Update	|	June 19, 2013
//####################################

public class TankGame implements MouseListener, MouseMotionListener, KeyListener {

    public static void main(String[] args) {
        new TankGame();
    }

    JPanel main;  // the main JPanel

    /*
   #############
   # variables #
   #############*/
    //fonts
    Font scoreFont = new Font("Arial", Font.PLAIN, 20);
    Font upgradeFont = new Font("Arial", Font.PLAIN, 30);

    //GAME COUNTERS
    static int interval = 200; //interval between each bullet
    static long tCount = 0;  //tank life
    static long eCount = 0;       //how many enemies killed?
    static boolean upgrade;
    static long lastUpgrade = 0;

    //IMAGES
    private BufferedImage tBody;
    private BufferedImage zombie;
    BufferedImage background;

    //enemy location range
    int eRange = 5000;
    int eRange2 = 0;

    //rotation var
    static double rt = 0;

    //booleans
    boolean onOff = false;

    //timer
    Timer timer;
    int t_speed = 1; //speed of timer repeats (ms)
    int t_pause = 1000;  //initial delay (ms)

    //keys
    static int key = 0;

    //window sizes
    static int WinWidth = 0;
    static int WinHeight = 0;

    //x and y position of the mouse
    static int my = 0;
    static int mx = 0;

    //x and y position of the tank
    static double tx = 400;
    static double ty = 300;

    //x and y position of zombies (array)
    static int zomb_num = 1000;
    static double[] ex = new double[zomb_num];
    static double[] ey = new double[zomb_num];
    static boolean[] eOff = new boolean[zomb_num];

    //BULLETs
    static double bulletSpeed = 7;

    static ArrayList<Bullet> bullets = new ArrayList<>();
    static int targetX, targetY;
    static long lastFire = System.currentTimeMillis(); //last bullet shot
    static boolean bOff; //did it hit something?
    static boolean shooting;  //are you still shooting?

    TankGame() {

        //LOAD IMAGE
        try {
            tBody = ImageIO.read(new File("./images/tank.png"));
            zombie = ImageIO.read(new File("./images/zomb.png"));
            background = ImageIO.read(new File("./images/background.jpg"));
        } catch (IOException ex) {
            // handle exception...
        }

        //Setup timers
        TimerListener tl = new TimerListener();
        timer = new Timer(t_speed, tl);
        timer.setInitialDelay(t_pause);
        timer.start();

        JFrame window = new JFrame("Tank game");
        main = new GrPanel();
        window.add(main);

        //add all listeners
        main.addMouseMotionListener(this);
        main.addMouseListener(this);
        main.addKeyListener(this);

        //window setup
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //set window size to full screen
        Toolkit tk = Toolkit.getDefaultToolkit();
        WinWidth = ((int) tk.getScreenSize().getWidth());
        WinHeight = ((int) tk.getScreenSize().getHeight());
        window.setSize(WinWidth, WinHeight - 50);

        tCount = WinWidth - 100;

        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setResizable(false);

        //PUT ZOMBIES IN FOUR COURNERS (IN RANDOM) POSITIONS
        for (int i = 0; i < zomb_num; i++) {
            int rnd4 = (int) (Math.random() * 4);
            int rnd5 = (int) (Math.random() * 5);

            if (rnd5 == 0) eRange2 = eRange;
            else eRange2 = eRange * 2;

            if (rnd4 == 0) {  //left top
                ey[i] = 0 - (Math.random() * eRange2);
                ex[i] = 0 - (Math.random() * eRange2);
            }
            if (rnd4 == 1) {  //right top
                ey[i] = 0 - (Math.random() * eRange2);
                ex[i] = WinWidth + (Math.random() * eRange2);
            }
            if (rnd4 == 2) {  //right bottom
                ey[i] = WinHeight + (Math.random() * eRange2);
                ex[i] = WinWidth + (Math.random() * eRange2);
            }
            if (rnd4 == 3) {  //left bottom
                ey[i] = WinHeight + (Math.random() * eRange2);
                ex[i] = 0 - (Math.random() * eRange2);
            }

        }
    }

    //JPanel inner class:
    private class GrPanel extends JPanel {
        //paint components
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            // g2.setRenderingHints(Graphics2D.ANTIALIASING,Graphics2D.ANTIALIAS_ON);

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
   /*
#############
# DRAW TANK #
#############
*/


            int iw = background.getWidth(this);
            int ih = background.getHeight(this);
            if (iw > 0 && ih > 0) {
                for (int x = 0; x < WinWidth; x += iw) {
                    for (int y = 0; y < WinHeight; y += ih) {
                        g.drawImage(background, x, y, iw, ih, this);
                    }
                }
            }

            //#1
            //draw zombies

            g2.setColor(Color.RED);
            for (int i = 0; i < zomb_num; i++) {
                if (!eOff[i]) {
                    //g2.fillOval((int)ex[i], (int)ey[i], 30,30);
                    g2.drawImage(zombie, (int) ex[i], (int) ey[i], null);

                }
            }

            //#2
            //draw bullets
            g2.setColor(Color.BLACK);
            for (Bullet b : bullets) {
                if (!b.bOff) {
                    g2.fillOval((int) b.getX(), (int) b.getY(), 10, 10);
                } else {
                    // attempt to delete bullets which are off screen
                    //bullets.remove(b);
                }
            }

            //#3
            int tx2 = (int) tx;
            int ty2 = (int) ty;

            if (rt > 2 * Math.PI) rt -= 2 * Math.PI;
            if (rt < 0) rt += 2 * Math.PI;

            //draw tank body
            g.translate(tx2, ty2);
            g2.rotate(rt);
            //g2.fill(tBody);
            g.drawImage(tBody, -32, -32, null);
            g2.rotate(-rt);
            g.translate(-tx2, -ty2);

            //DRAW ENEMY COUNT AND LIFE BAR

            g2.setColor(Color.white);
            g2.setFont(scoreFont);
            g2.drawString("SCORE: " + eCount, WinWidth - 300, 50);

            g2.drawString("HEALTH", 10, 30);
            g2.setColor(Color.GREEN);
            if (tCount <= WinWidth / 3) g2.setColor(Color.RED);
            g2.fillRect(90, 15, (int) tCount, 10);

            if (upgrade) {
                g2.setColor(Color.RED);
                g2.setFont(upgradeFont);
                g2.drawString("WEAPON UPGRADE", WinWidth / 2 - 150, 80);
                if (System.currentTimeMillis() - lastUpgrade >= 5000) upgrade = false;
            }

        }
    }


    //all listener methods:
    public void mouseMoved(MouseEvent e) {
        mx = e.getX();
        my = e.getY();
    }

    public void mousePressed(MouseEvent e) {
        mx = e.getX();
        my = e.getY();
        bOff = false;
        shooting = true;
    }

    public void mouseReleased(MouseEvent e) {
        shooting = false;
    }

    public void mouseDragged(MouseEvent e) {
        mx = e.getX();
        my = e.getY();
    }

    public void mouseEntered(MouseEvent e) {
        main.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR)); //change the mouse cursor
        main.requestFocus();
    }

    boolean SHIFTON = false;

    public void keyPressed(KeyEvent e) {
        key = e.getKeyCode();
        //System.out.println("rt: "+rt);
        if (e.getKeyCode() == 16) SHIFTON = true;
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 16) SHIFTON = false; //just releasing the shift key
        else key = 0;
        // main.repaint();
    }

    //useless stuff##################################
    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }
    //###############################################

    //inner class for Timer Listener
    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //...Perform tasks...
            TankMove(SHIFTON);
            EnemyMove();
            if (shooting) Shoot();
            if (!shooting) RemoveBullets();
            for (Bullet b : bullets) {
                b.move();
            }
            GameLogic();
            main.repaint();
        }
    }

    //TANK MOVE FUNCTION
    static void TankMove(boolean fast) {
        int speed = 3;
        if (fast) speed = 5;

        //rotate
        if (key == 37 || key == 65) rt -= 0.05; //rotate left
        if (key == 39 || key == 68) rt += 0.05; //rotate right

        //Forwards
        if (key == 38 || key == 87) {
            //CHECK IF IT'S OFF THE SCREEN
            if (ty < 30 && (rt < 1.6 || rt > 4.65)) return; //check if it's off the top
            if (ty > WinHeight - 110 && (rt > 1.6 && rt < 4.65)) return; //check if it's off the bottom

            if (tx < 35 && rt > 3.1) return; //check if it's off the left
            if (tx > WinWidth - 40 && rt < 3.1) return; //check if it's off the right

            //if all good ->
            tx = tx + (Math.cos(rt - Math.PI / 2) * speed);
            ty = ty + (Math.sin(rt - Math.PI / 2) * speed);
        }

        //Backwards
        if (key == 40 || key == 83) {
            //CHECK IF IT'S OFF THE SCREEN
            if (ty < 30 && (rt > 1.6 && rt < 4.65)) return; //check if it's off the top
            if (ty > WinHeight - 110 && (rt < 1.6 || rt > 4.65)) return; //check if it's off the bottom

            if (tx < 35 && rt < 3.1) return; //check if it's off the left
            if (tx > WinWidth - 40 && rt > 3.1) return; //check if it's off the right

            //if all good ->
            tx = tx - (Math.cos(rt - Math.PI / 2) * speed);
            ty = ty - (Math.sin(rt - Math.PI / 2) * speed);
        }
    }

    //ENEMY MOVE FUNCTION
    static void EnemyMove() {
        double speed = 1;

        for (int i = 0; i < zomb_num; i++) {

            double dx = tx - ex[i];
            double dy = ty - ey[i];
            double angle = Math.atan(dy / dx);

            double sx = speed * Math.cos(angle);
            double sy = speed * Math.sin(angle);

            //check collision
            double dist = 25 + 5;  //tank radius + enemy radius
            boolean coll = (dx * dx + dy * dy <= dist * dist);
            if (coll == true) {
                if (!eOff[i]) tCount -= WinWidth / 10;
                eOff[i] = true;
            }


            if (ex[i] < tx) { //if its on the right of the tank
                ex[i] += sx;
                ey[i] += sy;
            }
            if (ex[i] > tx) {  //if its on the left of the tank
                ex[i] -= sx;
                ey[i] -= sy;
            }
        }
    }

    //SHOOT FUNCTION
    static void Shoot() {
        if (System.currentTimeMillis() - lastFire < interval) {
            return;
        }

        // if we waited long enough create a new Bullet object
        lastFire = System.currentTimeMillis();
        bOff = false;
        Bullet b = new Bullet(tx, ty, mx, my, bOff);
        bullets.add(b);
        //System.out.println("Bullets = " + bullets.size());
    }

    static void RemoveBullets() {
        // attempt to delete bullets which are off screen
        //IT WORKS! DON'T KNOW WHY...
        for (Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext(); ) {
            Bullet value = iterator.next();
            if (value.bOff) {
                iterator.remove();
            }
        }
    }

    static void GameLogic() {

        //COLLISION with bullet?
        for (int i = 0; i < zomb_num; i++) {
            for (Bullet b : bullets) {
                if (Math.abs(ex[i] - b.getX()) <= 30) {
                    if (Math.abs(ey[i] - b.getY()) <= 30) {
                        if (!b.bOff && !eOff[i]) {
                            eOff[i] = true;
                            b.bOff = true;
                            eCount += 100;
                        }
                    }
                }
            }
        }

        //DEAD?
        if (tCount <= 0) infoBox("GAME OVER!", "Game Over", 1);

        //WON?
        int winCount = 0;
        for (int i = 0; i < zomb_num; i++) {
            if (eOff[i]) winCount++;
        }
        if (winCount == zomb_num) infoBox("YOU WON!", "You won", 1);

        //decrease interval
        if (eCount == 5000) {
            interval = 180;
            bulletSpeed = 8;
            lastUpgrade = System.currentTimeMillis();
            upgrade = true;
        }
        if (eCount == 10000) {
            interval = 170;
            bulletSpeed = 9;
            lastUpgrade = System.currentTimeMillis();
            upgrade = true;
        }
        if (eCount == 20000) {
            interval = 160;
            bulletSpeed = 10;
            lastUpgrade = System.currentTimeMillis();
            upgrade = true;
        }
        if (eCount == 40000) {
            interval = 150;
            bulletSpeed = 11;
            lastUpgrade = System.currentTimeMillis();
            upgrade = true;
        }
        if (eCount == 50000) {
            interval = 120;
            bulletSpeed = 12;
            lastUpgrade = System.currentTimeMillis();
            upgrade = true;
        }
        if (eCount == 60000) {
            interval = 100;
            bulletSpeed = 12;
            lastUpgrade = System.currentTimeMillis();
            upgrade = true;
        }
        if (eCount == 70000) {
            interval = 80;
            bulletSpeed = 12;
            lastUpgrade = System.currentTimeMillis();
            upgrade = true;
        }
    }

    public static void infoBox(String infoMessage, String location, int id) {
        if (id == 1) JOptionPane.showMessageDialog(null, infoMessage, location, JOptionPane.WARNING_MESSAGE);
        if (id == 2) JOptionPane.showMessageDialog(null, infoMessage, location, JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }
    //end of the game
}
