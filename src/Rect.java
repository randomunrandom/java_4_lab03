import java.io.InvalidClassException;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Rect {
    private int A_x;
    private int A_y;
    private int B_x;
    private int B_y;

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

   public Rect conjunction(Rect B){
        if (this.isInside(B)) {
            return B;
        }
        Rect r = new Rect(0, 0, 0, 0);
        r.A_x = (this.A_x < B.A_x) ? B.A_x : this.A_x;
        r.A_y = (this.A_y < B.A_y) ? this.A_y : B.A_y;
        r.B_x = (this.B_x < B.B_x) ? B.B_x : this.B_x;
        r.B_y = (this.B_y < B.B_y) ? this.B_y : B.B_y;

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

class Main {
    public static void main(String[] args) {
        Rect r = new Rect(0, 10, 10, 0);
//        System.out.println(r.toString());
        r.Move(-5, 0);
        System.out.println(r.toString());

        Rect d = new Rect(6, -1, 7, -2);
        System.out.println(d.toString());
        Rect u = r.union(d);
        System.out.println(u.toString());
        Rect c = r.conjunction(d);
        System.out.println(c);
    }
}