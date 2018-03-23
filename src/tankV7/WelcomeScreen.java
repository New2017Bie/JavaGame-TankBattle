/**********************************************
 * Project: tankV5_final
 * Author: Bo Yu
 * Date: 3/7/18
 * Time: 1:14 AM
 ***********************************************/

package tankV7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class WelcomeScreen extends JLayeredPane {

    static boolean run = true;
    JLabel jl = new JLabel();
    JLabel jl1 = new JLabel();
    DrawWelcome dw = new DrawWelcome();

    public WelcomeScreen() {

        this.add(dw, 10);
        dw.setBounds(0, 0, TankApp.getGameWidth() + 250, TankApp.getGameHeight());
        dw.setVisible(true);
        dw.setOpaque(true);

        jl.setBounds(320, 310, 500, 100);
        jl.setForeground(Color.red);
        jl.setFont(new Font("BLACK", Font.ITALIC, 40));
        jl.setVisible(true);
        jl.setOpaque(false);
        jl.setText("Enter any key to start");
        jl.setBackground(Color.white);
        this.setVisible(true);
        this.setOpaque(true);
        this.add(jl, 0);

        java.util.Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                int timer = 0;
                while (run) {
                    if (timer % 3 != 0) {
                        jl.setText("Enter any key to start");
                    } else {
                        jl.setText("");
                    }
                    repaint();

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    timer++;
                }
            }
        }, 1000);
    }

    public void stage() {
        jl1.setFont(new Font("BLACK", Font.BOLD, 90) );
        jl1.setOpaque(true);
        jl1.setVisible(true);
        jl1.setHorizontalAlignment(SwingConstants.CENTER);
        jl1.setBackground(Color.BLACK);
        jl1.setForeground(Color.yellow);
        jl1.setBounds(0, 0, TankApp.getGameWidth() + 250, TankApp.getGameHeight() - 100);
        jl1.setText("Stage 1");

        this.setBackground(Color.BLACK);
        this.remove(dw);
        this.remove(jl);
        this.add(jl1, 20);
        this.repaint();
        this.revalidate();

        java.util.Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                int counter = 0;

                while (true) {
                    if (counter % 2 == 0) {
                        jl1.setText("");
                        counter++;
                    } else {
                        jl1.setHorizontalTextPosition(JLabel.CENTER);
                        jl1.setText("Stage 1");
                        counter++;
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 1000);
    }


}

class DrawWelcome extends JLabel {
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.darkGray);
        g.fillRect(0, 0, TankApp.getGameWidth() + 250, TankApp.getGameHeight());

        // "Tank Battle" and shadow
        g.setColor(Color.lightGray);
        g.setFont(new Font("BLACK", Font.BOLD, 90));
        g.drawString("Tank Battle", 254, 284);

        g.setColor(Color.YELLOW);
        g.drawString("Tank Battle", 250, 280);

        // Draw bricks
        g.setColor(new Color(190, 90, 25));
        for (int i = 0; i < 26; i++) {
            g.fill3DRect(i * 30 + 140, 130, 30, 30, true);
            g.fill3DRect(i * 30 + 140, 550, 30, 30, true);
        }

        for (int i = 0; i < 13; i++) {
            g.fill3DRect(140, 160 + i * 30, 30, 30, true);
            g.fill3DRect(890, 160 + i * 30, 30, 30, true);
        }

        drawTank(520, 450, g, 0, 0);
        drawTank(610, 450, g, 0, 1);
        drawTank(430, 450, g, 0, 2);
    }

    private void drawTank(int x, int y, Graphics g, int direction, int type) {
        // 0 as hero and 1 as enemy
        switch (type) {
            case 0:
                g.setColor(Color.ORANGE);
                break;
            case 1:
                g.setColor(Color.CYAN);
                break;
            case 2:
                g.setColor(Color.lightGray);
                break;
        }

        // draw tanks in 4 directions
        switch (direction) {
            case 0:
                g.fill3DRect(x - 23, y - 25, 10, 50, false);
                g.fill3DRect(x + 14, y - 25, 10, 50, false);
                g.fill3DRect(x - 13, y - 15, 27, 30, false);
                g.fillOval(x - 9, y - 10, 18, 18);
                g.fillRect(x - 1, y - 25, 3, 15);
                for (int i = 1; i < 5; i++) g.drawLine(x - 23, y - 25 + 10 * i, x - 14, y - 25 + 10 * i);
                for (int i = 1; i < 5; i++) g.drawLine(x + 14, y - 25 + 10 * i, x + 23, y - 25 + 10 * i);

                break;
            case 1:
                g.fill3DRect(x - 23, y - 25, 10, 50, false);
                g.fill3DRect(x + 14, y - 25, 10, 50, false);
                g.fill3DRect(x - 13, y - 15, 27, 30, false);
                g.fillOval(x - 9, y - 9, 18, 18);
                g.fillRect(x - 1, y + 10, 3, 15);
                for (int i = 1; i < 5; i++) g.drawLine(x - 23, y - 25 + 10 * i, x - 14, y - 25 + 10 * i);
                for (int i = 1; i < 5; i++) g.drawLine(x + 14, y - 25 + 10 * i, x + 23, y - 25 + 10 * i);

                break;
            case 2:
                g.fill3DRect(x - 25, y - 23, 50, 10, false);
                g.fill3DRect(x - 25, y + 14, 50, 10, false);
                g.fill3DRect(x - 15, y - 13, 30, 27, false);
                g.fillOval(x - 10, y - 9, 18, 18);
                g.fillRect(x - 25, y - 1, 15, 3);
                for (int i = 1; i < 5; i++) g.drawLine(x - 25 + 10 * i, y - 23, x - 25 + 10 * i, y - 14);
                for (int i = 1; i < 5; i++) g.drawLine(x - 25 + 10 * i, y + 14, x - 25 + 10 * i, y + 23);

                break;
            case 3:
                g.fill3DRect(x - 25, y - 23, 50, 10, false);
                g.fill3DRect(x - 25, y + 14, 50, 10, false);
                g.fill3DRect(x - 15, y - 13, 30, 27, false);
                g.fillOval(x - 9, y - 9, 18, 18);
                g.fillRect(x + 10, y - 1, 15, 3);
                for (int i = 1; i < 5; i++) g.drawLine(x - 25 + 10 * i, y - 23, x - 25 + 10 * i, y - 14);
                for (int i = 1; i < 5; i++) g.drawLine(x - 25 + 10 * i, y + 14, x - 25 + 10 * i, y + 23);

                break;
        }
    }
}
