import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Lap trinh on 5/13/2017.
 */
public class Boom {
    int x;
    int y;
    int index;
    int time = 500;
    int countDraw;
    boolean explosion = false;
    Image left = new ImageIcon(getClass().getResource
            ("/boom_bang_left.PNG")).getImage();
    Image right = new ImageIcon(getClass().getResource
            ("/boom_bang_right.PNG")).getImage();
    Image up = new ImageIcon(getClass().getResource
            ("/boom_bang_up.PNG")).getImage();
    Image down = new ImageIcon(getClass().getResource
            ("/boom_bang_down.PNG")).getImage();
    Image mid = new ImageIcon(getClass().getResource
            ("/boom_bang_mid_01.png")).getImage();
    Image[] images = {
            new ImageIcon(getClass().getResource
                    ("/boom_01.PNG")).getImage(),
            new ImageIcon(getClass().getResource
                    ("/boom_02.PNG")).getImage(),
            new ImageIcon(getClass().getResource
                    ("/boom_03.PNG")).getImage(),
            new ImageIcon(getClass().getResource
                    ("/boom_04.PNG")).getImage(),
            new ImageIcon(getClass().getResource
                    ("/boom_05.PNG")).getImage(),
            new ImageIcon(getClass().getResource
                    ("/boom_06.PNG")).getImage(),
            new ImageIcon(getClass().getResource
                    ("/boom_07.PNG")).getImage(),
            new ImageIcon(getClass().getResource
                    ("/boom_08.PNG")).getImage()
    };

    public Boom(int x, int y) {
        this.x = x;
        this.y = y;
    }

    boolean draw(Graphics g) {
        time--;
        if (time > 0) {
            countDraw++;
            if (countDraw >= 10) {
                index++;
                if (index >= images.length) {
                    index = 0;
                }
                countDraw = 0;
            }
            g.drawImage(images[index], x, y, null);
        } else if (time > -100) {
            g.drawImage(mid, x, y, null);
            g.drawImage(left, x - left.getWidth(null), y, null);
            g.drawImage(right, x + right.getWidth(null), y, null);
            g.drawImage(up, x, y - up.getHeight(null), null);
            g.drawImage(down, x, y + down.getHeight(null), null);
        } else {
            return true;
        }
        return false;
    }

    Rectangle getRectMid() {
        Rectangle rectangle = new Rectangle(x, y, mid.getWidth(null), mid.getHeight(null));
        return rectangle;
    }

    Rectangle getRectLeft() {
        int w = left.getWidth(null);
        int h = left.getHeight(null);
        Rectangle rectangle = new Rectangle(x - w, y, w, h);
        return rectangle;
    }

    Rectangle getRectRight() {
        int w = right.getWidth(null);
        int h = right.getHeight(null);
        Rectangle rectangle = new Rectangle(x + w, y, w, h);
        return rectangle;
    }

    Rectangle getRectUp() {
        int w = up.getWidth(null);
        int h = up.getHeight(null);
        Rectangle rectangle = new Rectangle(x, y - h, w, h);
        return rectangle;
    }

    Rectangle getRectDown() {
        int w = down.getWidth(null);
        int h = down.getHeight(null);
        Rectangle rectangle = new Rectangle(x, y + h, w, h);
        return rectangle;
    }

    boolean explosion(ArrayList<Map> arrMap, ArrayList<Boss> arrBoss, Player player) {
        if (time>=0){
            return true;
        }
        if (explosion == false){
            SoundManager.getClip("boom_bang.wav").start();
            explosion = true;
        }
        for (int i = arrMap.size() - 1; i >= 0; i--) {
            int bit = arrMap.get(i).bit;
            if (bit==1 || bit==4) {
                boolean check = checkExplosion(arrMap.get(i).getRect());
                if (check == false) {
                    arrMap.remove(i);
                }
            }
        }
        for (int i = arrBoss.size() - 1; i >= 0; i--) {
            boolean check = checkExplosion(arrBoss.get(i).getRect());
            if (check == false) {
                arrBoss.remove(i);
            }
        }
        return checkExplosion(player.getRect());
    }

    boolean checkExplosion(Rectangle rectangle) {
        Rectangle rect = getRectMid().intersection(rectangle);
        if (rect.isEmpty() == false) {
            return false;
        }
        rect = getRectLeft().intersection(rectangle);
        if (rect.isEmpty() == false) {
            return false;
        }
        rect = getRectRight().intersection(rectangle);
        if (rect.isEmpty() == false) {
            return false;
        }
        rect = getRectUp().intersection(rectangle);
        if (rect.isEmpty() == false) {
            return false;
        }
        rect = getRectDown().intersection(rectangle);
        if (rect.isEmpty() == false) {
            return false;
        }
        return true;
    }
}
