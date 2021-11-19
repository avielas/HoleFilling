import hole_filling.*;

import java.awt.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {

            if(args.length == 1 && (args[0].equals("--help") || args[0].equals("-h"))){
                System.out.println("Usage: [image path] [mask path] [z] [e] [pixel connectivity: 4/8] [true/false]");
                System.exit(1);
            }

            if(args.length != 6){
                System.out.println("Missing arguments (should be 5)\nUsage: [image path] [mask path] [z] [e] [pixel connectivity: 4/8] [true/false]");
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

            IRgbToGrayscaleFunc Rgb2GrayFunc = (Color c) -> (float) (((c.getRed() + c.getGreen() + c.getBlue())/3.0)/255);
            IWeightFunc weightFunc = (Pixel u, Pixel v) -> (float) (1 / (Math.pow(MathCalculator.euclideanDist(u, v), z) + e));

            HoledImage holedImage = new HoledImage(imagePath, maskPath, cType, Rgb2GrayFunc, weightFunc, isOptimized);
            HoleFillingCalculator.fillHole(holedImage);
            holedImage.save(imagePath);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(e.fillInStackTrace());
            System.exit(1);
        }
        catch (InvalidPixelConnectivity e) {
            System.out.println("Invalid connectivity type");
            System.exit(1);
        }
        catch(NumberFormatException e) {
            // for catch exception if parseFloat/parseFloat failed
            System.out.println(e.fillInStackTrace());
            System.exit(1);
        }
        catch (ImagesAreWithDifferentSizeException e){
            System.out.println(e.fillInStackTrace());
            System.exit(1);
        }
        catch (IOException e) {
            System.out.println(e.fillInStackTrace());
            System.exit(1);
        } catch (FailedToExtractFileFormatException e) {
            System.out.println(e.fillInStackTrace());
            System.exit(1);
        }
    }
}

class InvalidPixelConnectivity extends Exception { }