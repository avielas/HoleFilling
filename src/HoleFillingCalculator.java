import java.util.Set;


public class HoleFillingCalculator implements IHoleFillingCalculator {

    public static void fillHole(HoledImage image) {
        for (Pixel h : image.getHole().getPixels()) {
            h.setVal(calcColor(h, image.getHole().getBoundary(), image.getHole().getWeightFunc()));
        }
    }

    private static float calcColor(Pixel h, Set<Pixel> B, WeightFunc w) {
        float numerator = 0;
        float denominator = 0;

        for (Pixel b : B) {
            float weight = w.weight(h, b);
            numerator += weight * b.getVal();
            denominator += weight;
        }
        return numerator / denominator;
    }
}