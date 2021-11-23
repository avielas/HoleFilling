package hole_filling;

import hole_filling.exceptions.ImagesAreWithDifferentSizeException;
import hole_filling.interfaces.IRgbToGrayscaleFunc;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This class represents an image with a hole (extends from Image). Also, it implements the functionality that's needed to carve out a hole in an image
 */
public class HoledImage extends Image{
    private Hole hole;
    private IRgbToGrayscaleFunc rgb2GrayFunc;
    private String maskPath;

    /**
     *
     * @param imagePath - path to RGB image
     * @param maskPath - path to RGB mask that must be as the same width/height of the RGB image
     * @param cType - connected-type (4 or 8) for ing the hole's boundary
     * @param rgb2GrayFunc - a function that defines the RGB2GRAY conversion for each pixel
     */
    public HoledImage(String imagePath, String maskPath, int cType, IRgbToGrayscaleFunc rgb2GrayFunc) throws ImagesAreWithDifferentSizeException, IOException {
        super(imagePath);
        this.rgb2GrayFunc = rgb2GrayFunc;
        hole = new Hole(cType);
        this.maskPath = maskPath;
        carveOutTheHole();
        findHole();
        findBoundary();
    }

    /**
     *
     * @return Hole class
     */
    public Hole getHole() {
        return hole;
    }

    /**
     * Implements the logic of carve out a hole in an image according the given mask, rgbToGrayscaleFunc and
     * given intensity value on 0.5
     */
    private void carveOutTheHole() throws IOException, ImagesAreWithDifferentSizeException {
        try {
            BufferedImage maskBuffer = ImageIO.read(new File(maskPath));
            int width = maskBuffer.getWidth();
            int height = maskBuffer.getHeight();

            if(imageBuffer.getWidth() != width || imageBuffer.getHeight() != height)
                throw new ImagesAreWithDifferentSizeException();

            grayscalePixels = new Pixel[height][width];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    float value;
                    Color c = new Color(maskBuffer.getRGB(j, i));
                    if (rgb2GrayFunc.rgbToGrayscale(c) > Consts.PIXEL_INTENSITY_VALUE) {
                        // if pixel (j,i) is not a 'hole pixel' convert its color to grayscale
                        // according to rgbToGrayscaleFunc
                        Color pixelColor = new Color(imageBuffer.getRGB(j, i));
                        value = rgb2GrayFunc.rgbToGrayscale(pixelColor);
                    } else {
                        value = Consts.HOLE_VALUE;
                    }
                    grayscalePixels[i][j] = new Pixel(i, j, value);
                }
            }
        } catch (IOException | ImagesAreWithDifferentSizeException e) {
            throw e;
        }
    }

    /***
     * Finds a hole inside an image assuming -1 is the hole pixel value
     */
    private void findHole(){
        for (int i = 0; i < grayscalePixels.length; i++) {
            for (int j = 0; j < grayscalePixels[0].length; j++) {
                if (grayscalePixels[i][j].getVal() == -1) {
                    hole.addToPixels(grayscalePixels[i][j]);
                }
            }
        }
    }

    /**
     * Find the boundary of the hole by connected-type (4/8)
     */
    private void findBoundary(){
        for (Pixel p : hole.getPixels()) {
            int x = p.getX();
            int y = p.getY();
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (hole.getConnectedType() == 4 && Math.abs(i) + Math.abs(j) == 2) {
                        // skip diagonal neighbors, in case of 4-connected type
                        continue;
                    }
                    if (!(i == 0 && j == 0) && !hole.isHole(grayscalePixels[y + i][x + j])) { //
                        hole.addToBoundary(grayscalePixels[y + i][x + j]);
                    }
                }
            }
        }
    }
}

