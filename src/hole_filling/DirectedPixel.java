package hole_filling;

public class DirectedPixel extends Pixel {

    private Direction direction;

    public Direction getDirection() { return direction; }

    public void setDirection(Direction direction) { this.direction = direction; }

    public int getX() { return x; }

    public int getY() { return y; }

    public void setX(int x) { this.x = x; }

    public void setY(int y) { this.y = y; }

    public DirectedPixel(int x, int y, float val) {
        super(y, x, val);
        this.direction = Direction.NORTH;
    }

    public DirectedPixel(DirectedPixel refPoint) {
        super(refPoint.y, refPoint.x, refPoint.val);
        this.direction = refPoint.direction;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DirectedPixel other = (DirectedPixel) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        if (this.direction != other.direction) {
            return false;
        }
        return true;
    }
}