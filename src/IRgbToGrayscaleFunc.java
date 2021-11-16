import java.awt.*;

// TODO - why we need FunctionalInterface annotation ?
@FunctionalInterface
public interface IRgbToGrayscaleFunc {
    float rgbToGrayscale(Color c);
}

