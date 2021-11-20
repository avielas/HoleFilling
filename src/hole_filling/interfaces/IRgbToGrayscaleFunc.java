package hole_filling.interfaces;

import java.awt.*;

@FunctionalInterface
public interface IRgbToGrayscaleFunc {
    float rgbToGrayscale(Color c);
}

