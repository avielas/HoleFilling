import java.util.Set;


public class HoleFillingCalculator implements IHoleFillingCalculator {

    /**
     *
     * @param image
     */
    public static void fillHole(HoledImage image) {

        float bMeanColor = calcBmeanColor(image.getHole().getBoundary());

        for (Pixel h : image.getHole().getPixels()) {
            h.setVal(calcColor(h, image.getHole().getBoundary(), image.getHole().getWeightFunc(), bMeanColor));
        }
    }

    private static float calcBmeanColor(Set<Pixel> B) {
        float sum = 0;
        for (Pixel b : B) {
            sum+=b.getVal();
        }
        return sum/B.size();
    }

    /**
     *
     * @param h
     * @param B
     * @param w
     * @param bMeanColor
     * @return
     */
    private static float calcColor(Pixel h, Set<Pixel> B, IWeightFunc w, float bMeanColor) {
//        float numerator = 0;
//        float denominator = 0;
//
//        for (Pixel b : B) {
//            float weight = w.weight(h, b);
//            numerator += weight * b.getVal();
//            denominator += weight;
//        }
//        return numerator / denominator;
        return bMeanColor;
    }
}