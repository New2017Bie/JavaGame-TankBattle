/**********************************************
 * Project: tank
 * Author: Bo Yu
 * Date: 2/28/18
 * Time: 3:53 AM
 ***********************************************/

package tankv3_enemydies;

import javax.swing.*;
import java.awt.*;

public class TankDemo1 extends JFrame{

    MyPanel mp = null;
    static int screenWidth = 1200;
    static int screenHeight = 900;

    public TankDemo1 () {
        mp = new MyPanel();

        Thread mt = new Thread(mp);

        mt.start();
        mp.setBackground(Color.BLACK);
        this.add(mp);
        this.setVisible(true);
        this.setBounds(100, 100, screenWidth, screenHeight);
        this.addKeyListener(mp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        TankDemo1 td = new TankDemo1();
    }
}


