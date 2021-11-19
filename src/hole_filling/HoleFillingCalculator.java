package hole_filling;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class HoleFillingCalculator implements IHoleFillingCalculator {

    /**
     * Utility function which ...
     * @param image - bla bla bla
     */
    public static void fillHole(HoledImage image) {
        Set<Pixel> B;
        List<Pixel> orderedByNeighborsB;

        if(image.getOptimizedToNComplexity()) {
            orderedByNeighborsB = SquareTracing.getBoundary(image);
            B = calcBmeanColor(Consts.K, orderedByNeighborsB);
        }
        else
            B = image.getHole().getBoundary();

        for (Pixel h : image.getHole().getPixels()) {
            h.setVal(calcPixel(h, B, image.getHole().getWeightFunc()));
        }
    }

    /**
     * ha ha ha
     * @param sections
     * @param B
     * @return
     */
    private static Set<Pixel> calcBmeanColor(int sections, List<Pixel> B) {
        Set<Pixel> bMeanColorL = new HashSet<>();
        int n = B.size();
        int sectionSize = n/sections;
        float sum = 0;
        int count = 0, countTot = 0;

        for (Pixel b : B) {
            sum+=b.getVal();
            count++;
            countTot++;
            if(count == sectionSize || countTot == n){
                float avr = sum/count;
                bMeanColorL.add(new Pixel(b.getY(), b.getX(), avr));
                sum = 0;
                count = 0;
            }
        }
        return bMeanColorL;
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