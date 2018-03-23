/**********************************************
 * Project: tank
 * Author: Bo Yu
 * Date: 3/2/18
 * Time: 4:12 AM
 ***********************************************/

package tankV5_final;

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
    public Tank isTouched() {

        switch (getDirection()) {
            case 0:
            case 1:
                // get all the enemy tanks
                for (int i = 0; i < ets.size(); i++) {
                    EnemyTank et = ets.get(i);
                    if (et!= this) {
                        if (getX() >= et.getX() && getX() -28 <= et.getX() + 28
                                && getY() - 28<= et.getY() + 28
                                && getY() + 28>= et.getY() - 28 ) return et;
                        if (getX() + 28 >= et.getX() -28 && getX() <= et.getX()
                                && getY() - 28<= et.getY() + 28
                                && getY() + 28>= et.getY() - 28 ) return et;
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
                                && getY() + 28 >= et.getY() - 28 ) return et;
                        if (getX() + 28 >= et.getX()
                                && getX() <= et.getX()
                                && getY() - 28 <= et.getY() + 28
                                && getY() + 28 >= et.getY() - 28 ) return et;
                    }
                }
                break;
        }

        return null;
    }

    public void driveAway(Tank t) {
        if (getX() >= t.getX() && getY() >= t.getY()) setDirection(1);
        else if (getX() >= t.getX() && getY() <= t.getY()) setDirection(3);
        else if (getX() <= t.getX() && getY() <= t.getY()) setDirection(2);
        else setDirection(1);
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        while (isAlive()) {
            Random rand = new Random();

            setDirection(rand.nextInt(4));

            for (int i = 0; i < rand.nextInt(1500) + 10; i++) {

                switch (getDirection()) {
                    case 0:
                        if (isTouched() == null) {
                            if (getY() - getSpeed() - 25 <= 0) {
                                setDirection(1);
                                moveAhead();
                            }
                        } else {
                            if (getY() - getSpeed() - 25 <= 0) {
                                setDirection(1);
                                moveAhead();
                            }

                            driveAway(isTouched());
                        }
                        moveAhead();
                        break;
                    case 1:
                        if (isTouched() == null) {
                            if (getY() + getSpeed() + 25 > TankApp.getGameHeight()) {
                                setDirection(0);
                                moveAhead();
                            }
                        } else {
                            if (getY() + getSpeed() + 25 > TankApp.getGameHeight()) {
                                setDirection(0);
                                moveAhead();
                            }

                            driveAway(isTouched());
                        }
                        moveAhead();
                        break;
                    case 2:
                        if (isTouched() == null) {

                            if (getX() - getSpeed() - 23 < 0) {
                                setDirection(3);
                                moveAhead();
                            }
                        } else {
                            if (getX() - getSpeed() - 23 < 0) {
                                setDirection(3);
                                moveAhead();
                            }

                            driveAway(isTouched());
                        }

                        moveAhead();
                        break;
                    case 3:
                        if (isTouched() == null) {
                            if (getX() - getSpeed() + 23 > TankApp.getGameWidth()) {
                                setDirection(2);
                                moveAhead();
                            }
                        } else {
                            if (getX() - getSpeed() + 23 > TankApp.getGameWidth()) {
                                setDirection(2);
                                moveAhead();
                            }
                            driveAway(isTouched());
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
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!isAlive()) break;
        }
    }
}
