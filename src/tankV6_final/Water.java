/**********************************************
 * Project: tankV5_final
 * Author: Bo Yu
 * Date: 3/6/18
 * Time: 12:58 AM
 ***********************************************/

package tankV6_final;

import java.awt.*;

public class Water extends Material {

    public Water(int x, int y, int w, int h) {
        super(x, y);
        setColor(new Color(17, 204, 255));
        setBulletThrough(true);
        setHeight(h);
        setWidth(w);
    }

//    public static void main(String[] args) {
//        Water w = new Water(0, 0, 0 ,0);
//        System.out.println(w.getClass().getSimpleName());
//    }
}
