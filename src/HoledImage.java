import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HoledImage extends Image{
    private Hole hole;
    private IRgbToGrayscaleFunc rgbToGrayscaleFunc;
    private String maskPath;

    /**
     *
     * @param imagePath
     * @param maskPath
     * @param cType
     * @param rgb2GrayFunc
     * @param weightFunc
     */
    public HoledImage(String imagePath, String maskPath, int cType, IRgbToGrayscaleFunc rgb2GrayFunc, IWeightFunc weightFunc) throws ImageAndMaskAreWithDifferentSize {
        super(imagePath);
        rgbToGrayscaleFunc = rgb2GrayFunc;
        hole = new Hole(cType, weightFunc);
        this.maskPath = maskPath;
        carveOutTheHole();
        findHole();
        findBoundary();
    }

    public Hole getHole() {
        return hole;
    }

    /**
     *
     */
    private void carveOutTheHole(){
        try {
            BufferedImage maskBuffer = ImageIO.read(new File(maskPath));
            int width = maskBuffer.getWidth();
            int height = maskBuffer.getHeight();

            if(bufferedImage.getWidth() != width || bufferedImage.getHeight() != height)
                throw new ImageAndMaskAreWithDifferentSize();

            grayscalePixels = new Pixel[height][width];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    float value;
                    Color c = new Color(maskBuffer.getRGB(j, i));
                    if (rgbToGrayscaleFunc.rgbToGrayscale(c) > Consts.PIXEL_INTENSITY_VALUE) {
                        // if pixel (j,i) is not a 'hole pixel' convert its color to grayscale
                        Color pixelColor = new Color(bufferedImage.getRGB(j, i));
                        value = rgbToGrayscaleFunc.rgbToGrayscale(pixelColor);
                    } else {
                        value = Consts.HOLE_VALUE;
                    }
                    grayscalePixels[i][j] = new Pixel(i, j, value);
                }
            }
        } catch (IOException | ImageAndMaskAreWithDifferentSize e) {
            System.out.println(e.fillInStackTrace());
            System.exit(1);
        }
    }

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
     * complexity is O(n^2)
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

class ImageAndMaskAreWithDifferentSize extends Exception { }
