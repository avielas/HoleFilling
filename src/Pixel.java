/**
 * A Pixel obj which holds a float val that's in range [0,1]
 */

public class Pixel {
    private int x;
    private int y;
    private float val;

    public Pixel(int y, int x, float value) {
        this.x = x;
        this.y = y;
        this.val = value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setVal(float val) {
        this.val = val;
    }

    public float getVal() {
        return val;
    }
}
