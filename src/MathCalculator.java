/**
 *
 */
public class MathCalculator {
    /**
     *
     * @param p1
     * @param p2
     * @return
     */
    public static float euclideanDist(Pixel p1, Pixel p2) {
        float x = p1.getX() - p2.getX();
        float y = p1.getY() - p2.getY();
        return (float) (Math.sqrt(Math.pow(x,2) + Math.pow(y,2)));
    }
}
