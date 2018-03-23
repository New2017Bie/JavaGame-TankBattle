/**********************************************
 * Project: tank
 * Author: Bo Yu
 * Date: 3/1/18
 * Time: 1:37 PM
 ***********************************************/

package tankv2;

import java.util.Vector;

public class Tank {
    private int x = 0;
    private int y = 0;
    private int direction = 0;
    private int speed = 1;
    private int color = 0;
    private Bullet bullet = null;
    private Vector<Bullet> bv = new Vector<>();

    public Tank (int x, int y, int color, int speed) {
        setY(y);
        setX(x);
        setColor(color);
        setSpeed(speed);
    }

    public void shot() {
        Thread t = null;

        switch (getDirection()) {
            case 0:
                bullet = new Bullet(getX() - 1, getY() - 25, 0, 8);
                break;
            case 1:
                bullet = new Bullet(getX() - 1, getY() + 25, 1, 8);
                break;
            case 2:
                bullet = new Bullet(getX() -25, getY() - 1, 2, 8);
                break;
            case 3:
                bullet = new Bullet(getX() + 25, getY() - 1, 3, 8);
                break;
        }

        bv.add(bullet);

        t = new Thread(bullet);
        t.start();
    }

    public void moveUp() {
        setY(getY() - getSpeed());
    }

    public void moveDown() {
        setY(getY() + getSpeed());
    }

    public void moveLeft() {
        setX(getX() - getSpeed());
    }

    public void moveRight() {
        setX(getX() + getSpeed());
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Vector<Bullet> getBv() {
        return bv;
    }

    public Bullet getBullet() {
        return bullet;
    }
}
