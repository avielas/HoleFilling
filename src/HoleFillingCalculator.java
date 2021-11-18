import hole_filling.Pixel;

import java.util.HashSet;
import java.util.Set;


public class HoleFillingCalculator implements IHoleFillingCalculator {

    /**
     *
     * @param image
     */
    public static void fillHole(HoledImage image) {
        Set<Pixel> B;
        if(image.getOptimizedToNComplexity())
            //TODO - to call boundary tracing
            B = new HashSet<>();
        else
            B = image.getHole().getBoundary();

        for (Pixel h : image.getHole().getPixels()) {
            h.setVal(calcPixel(h, B, image.getHole().getWeightFunc()));
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
     * @return
     */
    private static float calcPixel(Pixel h, Set<Pixel> B, IWeightFunc w) {
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