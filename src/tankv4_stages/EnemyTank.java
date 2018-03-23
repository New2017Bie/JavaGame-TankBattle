/**********************************************
 * Project: tank
 * Author: Bo Yu
 * Date: 3/2/18
 * Time: 4:12 AM
 ***********************************************/

package tankv4_stages;

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
                        if (getX() >= et.getX() && getX() -28 <= et.getX() + 28
                                && getY() - 28<= et.getY() + 28
                                && getY() + 28>= et.getY() - 28 ) return true;
                        if (getX() + 28 >= et.getX() -28 && getX() <= et.getX()
                                && getY() - 28<= et.getY() + 28
                                && getY() + 28>= et.getY() - 28 ) return true;
                    }
                }
                break;
            case 2:
            case 3:
                // get all the enemy tanks
                for (int i = 0; i < ets.size(); i++) {
                    EnemyTank et = ets.get(i);
                    if (et!= this) {
                        if (getX() - 28 <= et.getX() + 28
                                && getX() + 28 >= et.getX() - 28
                                && getY() - 28 <= et.getY() + 28
                                && getY() + 28 >= et.getY() - 28 ) return true;
                        if (getX() + 28 >= et.getX()
                                && getX() <= et.getX()
                                && getY() - 28 <= et.getY() + 28
                                && getY() + 28 >= et.getY() - 28 ) return true;
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
                                Thread.sleep(rand.nextInt(100));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            setDirection(rand.nextInt(3) + 1);
                            if (getDirection() == 2) setDirection(3);
                            try {
                                Thread.sleep(rand.nextInt(300));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            for (int j = 0; j < rand.nextInt(5); j++) {
                                moveAhead();
                            }
                            continue;
                        }
                        moveAhead();
                        break;
                    case 1:
                        if (getY() + getSpeed()  + 25 > TankApp.getGameHeight() || isTouched()) {
                            try {
                                Thread.sleep(rand.nextInt(100));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            setDirection(rand.nextInt(4));
                            if (getDirection() == 1) setDirection(0);
                            try {
                                Thread.sleep(rand.nextInt(300));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            for (int j = 0; j < rand.nextInt(5); j++) {
                                moveAhead();
                            }

                            continue;
                        }
                        moveAhead();
                            break;
                    case 2:
                        if (getX() - getSpeed() -23 < 0 || isTouched()) {
                            try {
                                Thread.sleep(rand.nextInt(100));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            setDirection(rand.nextInt(4));
                            if (getDirection() == 2) setDirection(3);
                            try {
                                Thread.sleep(rand.nextInt(300));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            for (int j = 0; j < rand.nextInt(5); j++) {
                                moveAhead();
                            }

                            continue;
                        }
                        moveAhead();
                        break;
                    case 3:
                        if (getX() - getSpeed() + 23 > TankApp.getGameWidth() || isTouched()) {
                            try {
                                Thread.sleep(rand.nextInt(100));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            setDirection(rand.nextInt(4));

                            if (getDirection() == 3) setDirection(2);
                            try {
                                Thread.sleep(rand.nextInt(300));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            for (int j = 0; j < rand.nextInt(5); j++) {
                                moveAhead();
                            }

                            continue;
                        }
                        moveAhead();
                        break;
                }
                try {
                    Thread.sleep(rand.nextInt(50));
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
