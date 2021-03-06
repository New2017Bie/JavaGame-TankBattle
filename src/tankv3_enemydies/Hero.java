/**********************************************
 * Project: tank
 * Author: Bo Yu
 * Date: 3/1/18
 * Time: 1:38 PM
 ***********************************************/

package tankv3_enemydies;

public class Hero extends Tank {


    public Hero(int x, int y, int c, int s) {
        super(x,y, c, s);
    }

    public boolean isTouched() {

        switch (getDirection()) {
            case 0:
            case 1:
                // get all the enemy tanks
                for (int i = 0; i < EnemyTank.getEts().size(); i++) {
                    EnemyTank et = EnemyTank.getEts().get(i);
                    if (getX() >= et.getX() && getX() -25 <= et.getX() + 25
                            && getY() - 25<= et.getY() + 25
                            && getY() + 25>= et.getY() - 25 ) return true;
                    if (getX() + 25 >= et.getX() -25 && getX() <= et.getX()
                            && getY() - 25<= et.getY() + 25
                            && getY() + 25>= et.getY() - 25 ) return true;
                }
                break;
            case 2:
            case 3:
                // get all the enemy tanks
                for (int i = 0; i < EnemyTank.getEts().size(); i++) {
                    EnemyTank et = EnemyTank.getEts().get(i);
                    if (getX() - 25 <= et.getX() + 25
                            && getX() + 25 >= et.getX() - 25
                            && getY() - 25 <= et.getY() + 25
                            && getY() + 25 >= et.getY() - 25 ) return true;
                    if (getX() + 25 >= et.getX()
                            && getX() <= et.getX()
                            && getY() - 25 <= et.getY() + 25
                            && getY() + 25 >= et.getY() - 25 ) return true;
                }
                break;
        }

        return false;
    }
}