import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Image {
    protected String imagePath;
    protected BufferedImage bufferedImage;
    protected Pixel[][] grayscalePixels;

    /**
     *
     * @param imagePath
     */
    public Image(String imagePath){
        this.imagePath = imagePath;
        loadImageToBuffer();
    }

    public String getImagePath() {
        return imagePath;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public Pixel[][] getGrayscalePixels() {
        return grayscalePixels;
    }

    private void loadImageToBuffer(){
        try {
            bufferedImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.out.println(e.fillInStackTrace());
            System.exit(1);
        }
    }

    /**
     *
     * @param rgbToGrayscaleFunc
     **/
    public void bufferedImageToGrayscalePixels(IRgbToGrayscaleFunc rgbToGrayscaleFunc){
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        grayscalePixels = new Pixel[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color pixelColor = new Color(bufferedImage.getRGB(j, i));
                float value = rgbToGrayscaleFunc.rgbToGrayscale(pixelColor);
                grayscalePixels[i][j] = new Pixel(i, j, value);
            }
        }
    }

    /**
     *
     * @param pathToSave
     * @throws IOException
     **/
    public void save(String pathToSave) throws IOException, FailedToExtractFileFormat {
        Path path = Paths.get(pathToSave);
        String fileName = path.getFileName().toString();
        String directory = path.getParent().toString();
        String format;
        int idx = fileName.lastIndexOf('.');
        if (idx > 0) {
            format = fileName.substring(idx+1);
            fileName = fileName.substring(0, idx);
        }
        else{
            throw new FailedToExtractFileFormat();
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

class FailedToExtractFileFormat extends Exception { }
