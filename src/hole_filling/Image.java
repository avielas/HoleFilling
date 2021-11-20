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
    public Image(String imagePath){
        this.imagePath = imagePath;
        loadImageToBuffer();
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
     *
     * @return Pixel[][] with image pixels
     */
    public Pixel[][] getGrayscalePixels() {
        return grayscalePixels;
    }

    /***
     * Load the image to BufferedImage imageBuffer object
     */
    private void loadImageToBuffer(){
        try {
            imageBuffer = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.out.println(e.fillInStackTrace());
            System.exit(1);
        }
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

    /**
     * Save the image to the same directory as the source image with adding '_FILLED_HOLE' to its name
     * @throws IOException
     **/
    public void save() throws IOException, FailedToExtractFileFormatException {
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

