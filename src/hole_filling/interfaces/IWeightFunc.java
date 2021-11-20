package hole_filling.interfaces;

import hole_filling.Pixel;

@FunctionalInterface
/**
 * This interface represents the library weight function signature. You must provide an implementation to this function, for using the Hole Filling Library
 */
public interface IWeightFunc {
    float weight(Pixel p1, Pixel p2);
}

