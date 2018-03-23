/**********************************************
 * Project: tankv3.enemydies
 * Author: Bo Yu
 * Date: 3/3/18
 * Time: 1:57 AM
 ***********************************************/

package tankv3_enemydies;

public class Explosion {
    private int x, y;
    private int timer = 9;
    private boolean exists = true;

    public Explosion(int x, int y) {
        setX(x);
        setY(y);
    }

    public void countDown() {
        if (timer > 0) {
            timer--;
        } else {
            this.exists = false;
        }
    }

    public int getTimer() {
        return timer;
    }

    public boolean isExists() {
        return exists;
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
}
