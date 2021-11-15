import java.awt.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            // parsing args
            int cType = Integer.parseInt(args[4]);
            float e = Float.parseFloat(args[3]);
            int z = Integer.parseInt(args[2]);
            String maskPath = args[1];
            String imagePath = args[0];

            if (cType != 4 && cType != 8) {
                throw new InvalidPixelConnectivity();
            }

            RgbToGrayscaleFunc Rgb2GrayFunc = (Color c) -> (float) (((c.getRed() + c.getGreen() + c.getBlue())/3.0)/255);
            WeightFunc weightFunc = (Pixel u, Pixel v) -> (float) (1 / (Math.pow(MathCalculator.euclideanDist(u, v), z) + e));

            HoledImage holedImage = new HoledImage(imagePath, maskPath, z, e, cType, Rgb2GrayFunc, weightFunc);
            HoleFillingCalculator.fillHole(holedImage);
            holedImage.save(imagePath);
        }
        catch (InvalidPixelConnectivity e) {
            System.out.println("Invalid connectivity type");
            System.exit(1);
        }
        catch (IOException e) {
            System.out.println(e.fillInStackTrace());
            System.exit(1);
        }
    }

}

class InvalidPixelConnectivity extends Exception { }