import java.awt.*;
import java.util.*;

import static java.lang.Math.*;

public class Rect {
    int A_x;
    int A_y;
    int B_x;
    int B_y;

    Rect(int _A_x, int _A_y, int _B_x, int _B_y) {
//        if ((_A_x >= _B_x) || (_A_y <= _B_y)) {
//            System.out.println("Better error message");
//            throw new Error();
//        }
        A_x = _A_x;
        A_y = _A_y;
        B_x = _B_x;
        B_y = _B_y;
    }

    Rect union(Rect B) {
        Rect r = new Rect(0, 0, 0, 0);
        r.A_x = min(this.A_x, B.A_x);
        r.A_y = max(this.A_y, B.A_y);
        r.B_x = max(this.B_x, B.B_x);
        r.B_y = min(this.A_y, B.B_y);
        return r;
    }

    void Move(int dx, int dy) {
        this.A_x += dx;
        this.B_x += dx;
        this.A_y += dy;
        this.B_y += dy;
    }

    boolean isInside(int x, int y) {
        return (x>this.A_x) && (x<this.B_x) && (y<this.A_y) && (y>this.B_y);
    }

    boolean isInside(Rect B) {
        return (this.isInside(B.A_x, B.A_y)) && (this.isInside(B.B_x, B.B_y));
    }
    Rect conjunction(Rect B){
        if (this.isInside(B)) {
            return B;
        }
        Rect r = new Rect(0, 0, 0, 0);
        r.A_x = (this.A_x < B.A_x) ? B.A_x : this.A_x;
        r.A_y = (this.A_y < B.A_y) ? this.A_y : B.A_y;
        r.B_x = (this.B_x < B.B_x) ? this.B_x : B.B_x;
        r.B_y = (this.B_y < B.B_y) ? B.B_y : this.B_y;

        if ((r.A_x >= r.B_x) || (r.A_y <= r.B_y)) {
            System.out.println("conjunction ERROR");
            throw new Error();
        }

        return r;
    }

    public String toString() {
        return "Rect:\n\tA(" + this.A_x + ", " + this.A_y + "), B:(" + this.B_x + ", " + this.B_y + ")";
    }

}

class DrawRect extends Frame {
    private Rect[] r;
    private int H, W, Ox, Oy;
    DrawRect(Rect[] rects, int width, int height) {
        r = rects;
        W = width;
        H = height;
        Ox = W/2;
        Oy = H/2;
        setSize(H, W);
        setVisible(true);
    }

    public void paint(Graphics g) {
        System.out.println("DrawRect.paint");
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, W, H);
        int e = 70;
        for (Rect rect : r) {
            Random rand = new Random();
            Color randomColor = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
            g.setColor(randomColor);
            g.fillRect(Ox + e*rect.A_x, Oy - e*rect.A_y, e*(abs(rect.B_x - rect.A_x)), e*(abs(rect.B_y - rect.A_y)));
        }
        for(int i=Ox%e; i < W; i+=e) { // отрисовка оси OX
            g.setColor(Color.LIGHT_GRAY);
            g.drawLine(i, 0, i, H);
            g.setColor(Color.DARK_GRAY);
            g.drawLine(i, Oy-5, i, Oy+5);
            if (i/e - Ox/e != 0) g.drawString(Integer.toString(i/e-Ox/e), i-2, Oy+16);
        }
        for(int i=Oy%e; i < H; i+=e) { // отрисовка оси OY
            g.setColor(Color.LIGHT_GRAY);
            g.drawLine(0, i, W , i);
            g.setColor(Color.DARK_GRAY);
            g.drawLine(Ox-5, i,Ox+5 , i);
            g.drawString(Integer.toString(Oy / e - i / e), Ox - 16, i+4);
        }
        g.setColor(Color.DARK_GRAY); // отрисовка осей
        g.drawLine(0, Oy, W, Oy);
        g.drawLine(Ox, 0, Ox, H);
    }
}

class Main {
    public static void main(String[] args) {
        Rect r1 = new Rect(0, 5, 5, 2);
        System.out.println(r1);
//        r1.Move(-5, 0);
//        System.out.println(r1);
        Rect r2 =  new Rect(4, 3, 9, -2);
        System.out.println(r2);
        Rect u = r1.union(r2);
        System.out.println(u.toString());
        Rect c = r1.conjunction(r2);
        System.out.println(c);
        Rect[] rr = {u, r1, r2 ,c};
        DrawRect DR = new DrawRect(rr, 1366, 768);
    }
}
