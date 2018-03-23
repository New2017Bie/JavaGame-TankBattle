/**********************************************
 * Project: tank
 * Author: Bo Yu
 * Date: 3/2/18
 * Time: 4:12 AM
 ***********************************************/

package tankv3_enemydies;

import java.util.Random;
import java.util.Vector;

public class EnemyTank extends Tank implements Runnable {
    private static Vector<EnemyTank> ets = new Vector<>();

    public EnemyTank(int x, int y, int c, int s) {
        super(x, y, c, s);
    }

    public static Vector<EnemyTank> getEts() {
        return ets;
    }

    public void setEts(Vector<EnemyTank> ets) {
        this.ets = ets;
    }

    // determine if the enemy tank is touching other enemy tanks
    // setting all the numbers to 25 to prevent sudden turn error
    public boolean isTouched() {

        switch (getDirection()) {
            case 0:
            case 1:
                // get all the enemy tanks
                for (int i = 0; i < ets.size(); i++) {
                    EnemyTank et = ets.get(i);
                    if (et!= this) {
                        if (getX() >= et.getX() && getX() -25 <= et.getX() + 25
                                && getY() - 25<= et.getY() + 25
                                && getY() + 25>= et.getY() - 25 ) return true;
                        if (getX() + 25 >= et.getX() -25 && getX() <= et.getX()
                                && getY() - 25<= et.getY() + 25
                                && getY() + 25>= et.getY() - 25 ) return true;
                    }
                }
                break;
            case 2:
            case 3:
                // get all the enemy tanks
                for (int i = 0; i < ets.size(); i++) {
                    EnemyTank et = ets.get(i);
                    if (et!= this) {
                        if (getX() - 25 <= et.getX() + 25
                                && getX() + 25 >= et.getX() - 25
                                && getY() - 25 <= et.getY() + 25
                                && getY() + 25 >= et.getY() - 25 ) return true;
                        if (getX() + 25 >= et.getX()
                                && getX() <= et.getX()
                                && getY() - 25 <= et.getY() + 25
                                && getY() + 25 >= et.getY() - 25 ) return true;
                    }
                }
                break;
        }

        return false;
    }

    @Override
    public void run() {

        while (true) {
            Random rand = new Random();

            setDirection(rand.nextInt(4));

            for (int i = 0; i < rand.nextInt(1500) + 10; i++) {

                switch (getDirection()) {
                    case 0:
                        if (getY() - getSpeed() -25 < 0 || isTouched()) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            setDirection(1);
                            moveDown();
                            moveDown();
                            continue;
                        }
                        moveUp();
                        break;
                    case 1:
                        if (getY() + getSpeed()  + 25 > TankDemo1.screenHeight || isTouched()) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            setDirection(0);
                            moveUp();
                            moveUp();
                            continue;
                        }
                        moveDown();
                        break;
                    case 2:
                        if (getX() - getSpeed() -23 < 0 || isTouched()) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            setDirection(3);
                            moveRight();
                            moveRight();
                            continue;
                        }
                        moveLeft();
                        break;
                    case 3:
                        if (getX() - getSpeed() + 23 > TankDemo1.screenWidth || isTouched()) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            setDirection(2);
                            moveLeft();
                            moveLeft();
                            continue;
                        }
                        moveRight();
                        break;
                }

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!isAlive()) break;
        }
    }
}
