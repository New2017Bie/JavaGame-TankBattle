/**********************************************
 * Project: tankV5_final
 * Author: Bo Yu
 * Date: 3/6/18
 * Time: 1:24 AM
 ***********************************************/

package tankV5_final;

import java.awt.*;

public class Commander extends Material {
    public Commander(int x, int y) {
        super(x, y);
        setBulletThrough(false);
        setHeight(50);
        setWidth(47);
    }
}
