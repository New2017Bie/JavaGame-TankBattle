/**********************************************
 * Project: tankV5_final
 * Author: Bo Yu
 * Date: 3/6/18
 * Time: 12:47 AM
 ***********************************************/

package tankV7;

import java.awt.*;

public class Brick extends Material {

    public Brick(int x, int y) {
        super(x, y);
        setColor(new Color(190, 90, 25));
        setBulletThrough(false);
        setHeight(30);
        setWidth(30);
    }
}
