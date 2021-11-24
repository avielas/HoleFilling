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

    /***
     * To enable use of contains() when checking if Pixel object is inside a set
     * @return
     */
    @Override
    public int hashCode() {
        return this.y * 10000 + this.x;
    }

    /***
     * I DEFINED THIS TO COMPARE JUST BY X & Y WITHOUT VALUE TO ENABLE COMPARING DirectedPixel to Pixel
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Pixel p = (Pixel) obj;
        return this.x == p.x && this.y == p.y;
    }
}
