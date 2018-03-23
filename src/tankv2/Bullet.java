/**********************************************
 * Project: tank
 * Author: Bo Yu
 * Date: 3/2/18
 * Time: 1:37 PM
 ***********************************************/

package tankv2;

public class Bullet implements Runnable {
    private int x;
    private int y;
    private int direct;
    private int speed;
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

    @Override
    public void run() {
        while (true) {

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            switch (getDirect()) {
                case 0:
                    setY(getY() - getSpeed() );
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

            System.out.println("Shot? " + getX() + " " + getY());

            if (x < -1 || x > 600 || y < -1 || y > 600) {
                isAlive = false;
                break;
            }
        }
    }
}
