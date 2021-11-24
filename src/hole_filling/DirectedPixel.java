package hole_filling;

/**
 * This class represents directed pixel that's extends from Pixel and used for Boundary Tracing Algorithm. Each Pixel also has a direction that points to
 * the next step before going forward
 */
public class DirectedPixel extends Pixel {

    private Direction direction;

    public DirectedPixel(int x, int y, float val) {
        super(y, x, val);
        this.direction = Direction.UP;
    }

    public DirectedPixel(DirectedPixel refDirectedPixel) {
        super(refDirectedPixel.y, refDirectedPixel.x, refDirectedPixel.val);
        this.direction = refDirectedPixel.direction;
    }

    public Direction getDirection() { return direction; }

    public void setX(int x) { this.x = x; }

    public void setY(int y) { this.y = y; }

    public void rotateRight(){
        direction = direction.rotateRight();
    }

    public void rotateLeft(){
        direction = direction.rotateLeft();
    }

    @Override
    public boolean equals(Object obj) {
        final DirectedPixel other = (DirectedPixel) obj;

        return super.equals(other) && this.direction == other.direction;
    }
}