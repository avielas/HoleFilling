package hole_filling;

import hole_filling.exceptions.FailedToExtractFileFormatException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class IOImage {
    /***
     * Load the image
     * @return BufferedImage
     */
    public static BufferedImage loadImageToBuffer(String imagePath) throws IOException {
        try {
            return ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            throw e;
        }
    }

    /***
     * Save the image to the same directory as the source image with adding '_FILLED_HOLE' to its name
     * @param imagePath
     * @param grayscalePixels
     * @throws IOException
     * @throws FailedToExtractFileFormatException
     */
    public static void save(String imagePath, Pixel[][] grayscalePixels) throws IOException, FailedToExtractFileFormatException {
        Path path = Paths.get(imagePath);
        String fileName = path.getFileName().toString();
        String directory = path.getParent().toString();
        String format;
        int idx = fileName.lastIndexOf('.');
        if (idx > 0) {
            format = fileName.substring(idx+1);
            fileName = fileName.substring(0, idx);
        }
        else{
            throw new FailedToExtractFileFormatException();
        }

        BufferedImage filledImage = new BufferedImage(grayscalePixels[0].length, grayscalePixels.length, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < grayscalePixels.length; i++) {
            for (int j = 0; j < grayscalePixels[0].length; j++) {
                float holePixelColor = grayscalePixels[i][j].getVal();
                Color c = new Color(holePixelColor, holePixelColor, holePixelColor);
                filledImage.setRGB(j, i, c.getRGB());
            }
        }
        File output = new File( directory + "\\" + fileName + "_FILLED_HOLE." + format);
        ImageIO.write(filledImage, format, output);
        System.out.println("Saved image in the source directory.");
    }
}
