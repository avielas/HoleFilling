import hole_filling.*;
import hole_filling.exceptions.*;
import hole_filling.interfaces.*;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class HoleFilling {

    public static void main(String[] args) {
        try {

            if(args.length == 1 && (args[0].equals("--help") || args[0].equals("-h"))){
                System.out.println("Usage: java HoleFilling [image path] [mask path] [z] [e] [pixel connectivity: 4/8] [approximates O(n) algorithm true/false]");
                System.exit(1);
            }

            if(args.length != 6){
                System.out.println("Missing arguments (should be 5)\nUsage: java HoleFilling [image path] [mask path] [z] [e] [pixel connectivity: 4/8] [approximates O(n) algorithm true/false]");
                System.exit(1);
            }

            String imagePath = args[0];
            String maskPath = args[1];
            int z = Integer.parseInt(args[2]);
            float e = Float.parseFloat(args[3]);
            int cType = Integer.parseInt(args[4]);
            boolean isOptimized = Boolean.parseBoolean(args[5]);

            if (cType != 4 && cType != 8) {
                throw new InvalidPixelConnectivity();
            }

            /*** Example of how to use HoleFilling library ***/
            IRgbToGrayscaleFunc rgb2GrayFunc = (Color c) -> (float) (((c.getRed() + c.getGreen() + c.getBlue())/3.0)/255);
            IWeightFunc weightFunc = (Pixel u, Pixel v) -> (float) (1 / (Math.pow(MathCalculator.euclideanDistance(u, v), z) + e));
            HoledImage holedImage = new HoledImage(imagePath, maskPath, cType, rgb2GrayFunc);
            HoleFillingLib.fillHole(holedImage, weightFunc, isOptimized);
            IOImage.save(holedImage.getImagePath(), holedImage.getGrayscalePixels());
            /************** End of the example ***************/
        }
        catch (ArrayIndexOutOfBoundsException | IOException | FailedToExtractFileFormatException | ImagesAreWithDifferentSizeException | NumberFormatException e) {
            System.out.println(e.fillInStackTrace());
            System.exit(1);
        }
        catch (InvalidPixelConnectivity e) {
            System.out.println("Invalid connectivity type");
            System.exit(1);
        }
    }
}

