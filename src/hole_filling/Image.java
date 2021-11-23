package hole_filling;

import hole_filling.exceptions.FailedToExtractFileFormatException;
import hole_filling.interfaces.IRgbToGrayscaleFunc;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/***
 * This class represents an RGB image. Also, it loads the image to buffer when initialized.
 * Ths class also contains the functionality of converting RGB image to GRAYSCALE
 */
public class Image {
    protected String imagePath;
    protected BufferedImage imageBuffer;
    protected Pixel[][] grayscalePixels;

    /***
     *
     * @param imagePath - path to RGB image
     */
    public Image(String imagePath) throws IOException {
        this.imagePath = imagePath;
        imageBuffer = IOImage.loadImageToBuffer(imagePath);
    }

    /**
     *
     * @return String to image path
     */
    public String getImagePath() {
        return imagePath;
    }

    /***
     *
     * @return BufferedImage that's holds the image buffer
     */
    public BufferedImage getImageBuffer() {
        return imageBuffer;
    }

    /***
     * set image buffer
     * @param imageBuffer
     */
    public void setImageBuffer(BufferedImage imageBuffer) {
        this.imageBuffer = imageBuffer;
    }

    /***
     *
     * @return Pixel[][] with image pixels
     */
    public Pixel[][] getGrayscalePixels() {
        return grayscalePixels;
    }


    /**
     * Convert an RGB image to grayscale by rgb2GrayFunc
     * @param rgb2GrayFunc - IRgbToGrayscaleFunc interface implementation
     **/
    public void bufferedImageToGrayscalePixels(IRgbToGrayscaleFunc rgb2GrayFunc){
        int width = imageBuffer.getWidth();
        int height = imageBuffer.getHeight();
        grayscalePixels = new Pixel[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color pixelColor = new Color(imageBuffer.getRGB(j, i));
                float value = rgb2GrayFunc.rgbToGrayscale(pixelColor);
                grayscalePixels[i][j] = new Pixel(i, j, value);
            }
        }
    }
}

