/**********************************************
 * Project: tankV5_final
 * Author: Bo Yu
 * Date: 3/6/18
 * Time: 12:58 AM
 ***********************************************/

package tankV5_final;

import java.awt.*;

public class Water extends Material {

    public Water(int x, int y, int w, int h) {
        super(x, y);
        setColor(new Color(17, 204, 255));
        setBulletThrough(true);
        setHeight(h);
        setWidth(w);
    }
}
