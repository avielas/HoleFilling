package hole_filling;

/**
 * This class represents the math calculator library
 */
public class MathCalculator {
    /**
     * calculate the euclidean distance between 2 pixels
     * @param p1 - pixel 1
     * @param p2 - pixel 2
     * @return float value
     */
    public static float euclideanDist(Pixel p1, Pixel p2) {
        float x = p1.getX() - p2.getX();
        float y = p1.getY() - p2.getY();
        return (float) (Math.sqrt(Math.pow(x,2) + Math.pow(y,2)));
    }
}
