/**********************************************
 * Project: tankv4_stages
 * Author: Bo Yu
 * Date: 3/6/18
 * Time: 12:03 AM
 ***********************************************/

package tankV6_final;

import java.awt.*;

public class Material {
    private int x;
    private int y;
    private boolean alive = true;
    private boolean bulletThrough;
    private Color color;
    private int height;
    private int width;

    public Material(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Color getColor() {
        return color;
    }

    public void setBulletThrough(boolean bulletThrough) {
        this.bulletThrough = bulletThrough;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isBulletThrough() {
        return bulletThrough;
    }
}
