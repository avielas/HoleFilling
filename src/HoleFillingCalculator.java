import java.util.ArrayList;
import java.util.Set;


public class HoleFillingCalculator implements IHoleFillingCalculator {

    /**
     *
     * @param image
     */
    public static void fillHole(HoledImage image) {
        ArrayList<Pixel> bMeanColor = calcBmeanColor(127, image.getHole().getBoundary());
        for (Pixel h : image.getHole().getPixels()) {
            h.setVal(calcColor(h, image.getHole().getWeightFunc(), bMeanColor));
        }
    }

    private static ArrayList<Pixel> calcBmeanColor(int sections, Set<Pixel> B) {
        ArrayList<Pixel> bMeanColorL = new ArrayList<>();
        int n = B.size();
        float sum = 0;
        int count = 0, countN = 0;

        for (Pixel b : B) {
            sum+=b.getVal();
            count++;
            countN++;
            if(count > n/sections || countN == n){
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
     *
     * @param w
     * @return
     */
    private static float calcColor(Pixel h, IWeightFunc w, ArrayList<Pixel> bMeanColor) {
        float numerator = 0;
        float denominator = 0;

        for (Pixel b : bMeanColor) {
            float weight = w.weight(h, b);
            numerator += weight * b.getVal();
            denominator += weight;
        }
        return numerator / denominator;
    }
}