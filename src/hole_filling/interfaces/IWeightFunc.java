package hole_filling.interfaces;

import hole_filling.Pixel;

@FunctionalInterface
/**
 *
 */
public interface IWeightFunc {
    float weight(Pixel p1, Pixel p2);
}

