package hole_filling;

import java.util.Set;

/**
 *
 */
public interface IHoleFillingCalculator {
    /**
     * bi bi bi
     * @param image
     * @throws NotImplementedFunctionException
     */
    static void fillHole(HoledImage image) throws NotImplementedFunctionException { throw new NotImplementedFunctionException(); }

    /**
     * ba ba ba
     * @param h
     * @param B
     * @param w
     * @return
     * @throws NotImplementedFunctionException
     */
    static float calcColor(Pixel h, Set<Pixel> B, IWeightFunc w) throws NotImplementedFunctionException { throw new NotImplementedFunctionException();}
}

class NotImplementedFunctionException extends Exception { }