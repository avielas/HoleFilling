package hole_filling.interfaces;

import hole_filling.HoledImage;
import hole_filling.Pixel;
import hole_filling.exceptions.NotImplementedFunctionException;

import java.util.Set;

/**
 *
 */
public interface IHoleFillingLib {
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

