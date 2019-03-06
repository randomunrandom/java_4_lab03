import java.awt.*;
import java.io.InvalidClassException;

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

    public Rect union(Rect B) {
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
    Rect[] r;
    int w;
    int h;
    DrawRect(Rect[] i_r, int i_w, int i_h) {
        w = i_w;
        h = i_h;
        r = i_r;
        setSize(h, w);
        setVisible(true);
    }

    public void paint(Graphics g) {
        g.drawLine(0, h/2, w, h/2);
        g.drawLine(w/2, 0, w/2, h);
        Color[] c = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
        System.out.println("X");
        int e = 10;
        for(int i=0; i < w/e; i++) {
            g.drawLine(i*e, h/2-3, i*e, h/2+3);
            System.out.println(i);
            g.drawString(Integer.toString(i-w/(2*e)), i*e, h/2+13);
        }
        for(int i=0; i<r.length; i++) {
            g.setColor(c[i]);
            g.fillRect(w/2 + r[i].A_x, h/2-r[i].A_y, abs(r[i].B_x - r[i].A_x), abs(r[i].B_y - r[i].A_y));
        }
    }
}

class Main {
    public static void main(String[] args) {
        Rect r1 = new Rect(0, 50, 50, 20);
        System.out.println(r1);
//        r1.Move(-5, 0);
//        System.out.println(r1);
        Rect r2 =  new Rect(40, 30, 90, 20);
        System.out.println(r2);
        Rect u = r1.union(r2);
        System.out.println(u.toString());
        Rect c = r1.conjunction(r2);
        System.out.println(c);
        Rect[] rr = {u, r1, r2 ,c};
        DrawRect DR = new DrawRect(rr, 500, 500);
    }
}
