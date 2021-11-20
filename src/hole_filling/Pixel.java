package hole_filling;

/**
 * This class represents the pixel in an image, which holds a float value that's in range [0,1]
 *
 */

public class Pixel {
    protected int x;
    protected int y;
    protected float val;

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
