/**********************************************
 * Project: tank
 * Author: Bo Yu
 * Date: 3/2/18
 * Time: 1:37 PM
 ***********************************************/

package tankv4_stages;

public class Bullet implements Runnable {
    private int x;
    private int y;
    private int direct;
    private int speed = 8;
    private boolean isAlive = false;

    public Bullet (int x, int y, int d, int s) {
        setX(x);
        setY(y);
        setDirect(d);
        setSpeed(s);
        isAlive = true;
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

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    @Override
    public void run() {
        while (true) {

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            switch (getDirect()) {
                case 0:
                    setY(getY() - getSpeed());
                    break;
                case 1:
                    setY(getY() + getSpeed());
                    break;
                case 2:
                    setX(getX() - getSpeed());
                    break;
                case 3:
                    setX(getX() + getSpeed());
                    break;
            }

//            System.out.println("Shot? " + getX() + " " + getY());

            if (x < -1 || x > TankApp.getGameWidth() || y < -1 || y > TankApp.getGameHeight()) {
                isAlive = false;
                break;
            }
        }
    }
}
