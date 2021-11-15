
public class MathCalculator {
    public static float euclideanDist(Pixel p1, Pixel p2) {
        float x = p1.getX() - p2.getX();
        float y = p1.getY() - p2.getY();
        return (float) (Math.sqrt(x * x + y * y));
    }
}
