package hole_filling.interfaces;

import hole_filling.HoledImage;
import hole_filling.Pixel;
import hole_filling.exceptions.NotImplementedFunctionException;

import java.util.Set;

/**
 * This interface represents the hole filling library interface, that user should implement to create a same library with different implementation
 */

public interface IHoleFillingLib {
    /**
     * Utility function, which fills a hole in an image
     * @param image - holed image
     * @throws NotImplementedFunctionException
     */
    static void fillHole(HoledImage image) throws NotImplementedFunctionException { throw new NotImplementedFunctionException(); }

    /**
     * calculate the new hole pixel value according to the exercise given formula
     * @param h - the hole pixel
     * @param B - the boundary pixels
     * @param w - weight function
     * @return
     * @throws NotImplementedFunctionException
     */
    static float calcPixel(Pixel h, Set<Pixel> B, IWeightFunc w) throws NotImplementedFunctionException { throw new NotImplementedFunctionException();}
}

