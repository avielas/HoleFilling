package hole_filling;

import hole_filling.interfaces.IHoleFillingLib;
import hole_filling.interfaces.IWeightFunc;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class represents the image processing library functionality which fills a hole in an image
 */
public class HoleFillingLib implements IHoleFillingLib {

    /**
     * Utility function, which fills a hole in an image. The complexity is O(n*m) by default when
     * n is the hole's pixels quantity and m is the hole's boundary
     * @param image - HoledImage object
     * @param weightFunc - weight function implementation which fills hole algorithm must use
     * @param isOptimized - optimized algorithm that approximates the result in O(n) to a high degree of accuracy
     */
    public static void fillHole(HoledImage image, IWeightFunc weightFunc, boolean isOptimized) {
        Set<Pixel> B;
        List<Pixel> orderedByNeighborsB;

        if(isOptimized) {
            orderedByNeighborsB = SquareTracing.getBoundary(image);
            B = calcBAverageColors(Consts.K, orderedByNeighborsB);
        }
        else
            B = image.getHole().getBoundary();

        for (Pixel h : image.getHole().getPixels()) {
            h.setVal(calcPixel(h, B, weightFunc));
        }
    }

    /**
     * calculate the B average colors. see solution to Q2 for more explanation
     * @param sections - is Consts.K by default
     * @param B - ordered boundary pixels - neighbor by neighbor
     * @return Set<Pixel> average pixel values of hole boundary
     */
    private static Set<Pixel> calcBAverageColors(int sections, List<Pixel> B) {
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