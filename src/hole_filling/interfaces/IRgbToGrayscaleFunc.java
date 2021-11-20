package hole_filling.interfaces;

import java.awt.Color;

@FunctionalInterface
/**
 * This interface represents the library RGB2GRAY function signature. You must provide an implementation to this function, for using the Hole Filling Library
 */
public interface IRgbToGrayscaleFunc {
    float rgbToGrayscale(Color c);
}

