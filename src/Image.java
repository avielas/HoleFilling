import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    // TODO - to change this function !!
    public void save(String pathToSave) throws IOException {

        // extracts PATH+name and format
        Pattern p = Pattern.compile("(.*)\\.(.*)");
        Matcher m = p.matcher(pathToSave);
        m.find();
        String path = m.group(1);
        String format = m.group(2);

        BufferedImage filledImage = new BufferedImage(this.grayscalePixels[0].length, this.grayscalePixels.length, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < this.grayscalePixels.length; i++) {
            for (int j = 0; j < this.grayscalePixels[0].length; j++) {
                // iterates all pixels and creates the related color value
                float holePixelColor = this.grayscalePixels[i][j].getVal();
                Color c = new Color(holePixelColor, holePixelColor, holePixelColor);
                filledImage.setRGB(j, i, c.getRGB());
            }
        }
        File output = new File( path + "_FILLED." + format);
        ImageIO.write(filledImage, format, output);
        System.out.println("Saved image in source directory.");
    }
}
