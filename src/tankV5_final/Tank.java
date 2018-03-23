/**********************************************
 * Project: tank
 * Author: Bo Yu
 * Date: 3/1/18
 * Time: 1:37 PM
 ***********************************************/

package tankV5_final;

import java.util.Vector;

public class Tank {
    private int x = 0;
    private int y = 0;
    private int direction = 0;
    private int speed = 8;
    private int type = 0;
    private Bullet bullet = null;
    private Vector<Bullet> bv = new Vector<>();
    private boolean isAlive = true;

    public Tank (int x, int y, int type, int speed) {
        setX(x);
        setY(y);
        setType(type);
        setSpeed(speed);
    }

    public void fire() {
        Thread t = null;

        for (int i = bv.size() - 1; i >= 0; i--) {
            if (!bv.get(i).isAlive()) bv.remove(i);
        }

        if (bv.size() < 5) {
            // determine the direction
            switch (getDirection()) {
                case 0:
                    bullet = new Bullet(getX() - 1, getY() - 25, 0, 13);
                    break;
                case 1:
                    bullet = new Bullet(getX() - 1, getY() + 25, 1, 13);
                    break;
                case 2:
                    bullet = new Bullet(getX() - 25, getY() - 1, 2, 13);
                    break;
                case 3:
                    bullet = new Bullet(getX() + 25, getY() - 1, 3, 13);
                    break;
            }

            bv.add(bullet);
        }
        // no more than 5 bullets each time

        t = new Thread(bullet);
        t.start();
    }

    public void moveUp() {
        // Limit the tanks inside the game screen
        if (getY() - 25 - getSpeed() >= 0) setY(getY() - getSpeed());
    }

    public void moveDown() {
        if (getY() + 75 + getSpeed() <= TankApp.getGameHeight()) setY(getY() + getSpeed());
    }

    public void moveLeft() {
        if (getX() - 23 - getSpeed() >= 0) setX(getX() - getSpeed());
    }

    public void moveRight() {
        if (getX() + 23 + getSpeed() <= TankApp.getGameWidth()) setX(getX() + getSpeed());
    }

    public void moveAhead() {
        switch(getDirection()) {
            case 0:
                moveUp();
                break;
            case 1:
                moveDown();
                break;
            case 2:
                moveLeft();
                break;
            case 3:
                moveRight();
                break;
        }
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Vector<Bullet> getBv() {
        return bv;
    }

    public Bullet getBullet() {
        return bullet;
    }

    public void setBullet(Bullet bullet) {
        this.bullet = bullet;
    }

    public void removeBullet(int n) {
        bv.remove(n);
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
}
