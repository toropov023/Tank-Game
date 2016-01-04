package ca.toropov.games.tank;

public class Bullet {
    static double speed = 7;  //all bullets have the same speed

    double targetX;
    double targetY;

    boolean bOff;

    double sx = 0;
    double sy = 0;

    double dx = 0;
    double dy = 0;

    double bx;
    double by;

    boolean dr1, dr2;


    Bullet(double tx, double ty, double mx, double my, boolean off) {
        bx = tx;
        by = ty;
        targetX = mx;
        targetY = my;
        bOff = off;
        setMove();
        speed = TankGame.bulletSpeed;
    }

    void setMove() {
        dx = targetX - bx;
        dy = targetY - by;
        double angle = Math.atan(dy / dx);

        sx = speed * Math.cos(angle);
        sy = speed * Math.sin(angle);

        if (targetX > TankGame.tx) {
            dr1 = true;
            dr2 = false;
        }

        if (targetX < TankGame.tx) {
            dr2 = true;
            dr1 = false;
        }
        move();
    }

    void move() {
        //has the bullet reached the end?
        if (bx < -10 || by < -10) {
            bOff = true;
        }
        if (bx > TankGame.WinWidth || by > TankGame.WinHeight) {
            bOff = true;
        }


        if (dr1) {
            bx += sx;
            by += sy;
        }
        if (dr2) {
            bx -= sx;
            by -= sy;
        }


    }

    double getX() {
        return bx;
    }

    double getY() {
        return by;
    }
}