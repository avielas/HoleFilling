import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.CvType;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;


public class tmp {

    // Compulsory
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    public static void OpenCVBasicExample(){
        System.out.println("Welcome to OpenCV " + Core.VERSION);
        Mat m = new Mat(5, 10, CvType.CV_8UC1, new Scalar(0));
        System.out.println("OpenCV Mat: " + m);
        Mat mr1 = m.row(1);
        mr1.setTo(new Scalar(1));
        Mat mc5 = m.col(5);
        mc5.setTo(new Scalar(5));
        System.out.println("OpenCV Mat data:\n" + m.dump());
    }

    public static void convertRgbToGrey(String path, String imageName){
        try {
            File input = new File(path + imageName);
            BufferedImage srcImage = ImageIO.read(input);

            byte[] srcData = ((DataBufferByte) srcImage.getRaster().getDataBuffer()).getData();
            Mat src = new Mat(srcImage.getHeight(), srcImage.getWidth(), CvType.CV_8UC3);
            src.put(0, 0, srcData);

            Mat dst = new Mat(srcImage.getHeight(),srcImage.getWidth(),CvType.CV_8UC1);
            Imgproc.cvtColor(src, dst, Imgproc.COLOR_RGB2GRAY);
            byte[] dstData = new byte[dst.rows() * dst.cols() * (int)(dst.elemSize())];
            dst.get(0, 0, dstData);
            BufferedImage dstImage = new BufferedImage(dst.cols(),dst.rows(), BufferedImage.TYPE_BYTE_GRAY);
            dstImage.getRaster().setDataElements(0, 0, dst.cols(), dst.rows(), dstData);

            File ouptut = new File(path + "Grayscale" + imageName);
            ImageIO.write(dstImage, "jpg", ouptut);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args){
//        OpenCVBasicExample();

        String path = "C:\\Users\\aviel\\Downloads\\Hole_Filling\\";
        String imageName = "Lenna.png";
        convertRgbToGrey(path, imageName);
        Pixel[][] pixels = null;

        try {
            String maskName = "Mask.png";
            File input = new File(path + maskName);
            BufferedImage buffer = ImageIO.read(input);
            pixels = new Pixel[buffer.getHeight()][buffer.getWidth()];

            byte[] data = ((DataBufferByte) buffer.getRaster().getDataBuffer()).getData();
            Mat mat = new Mat(buffer.getHeight(), buffer.getWidth(), CvType.CV_8UC4);
            mat.put(0, 0, data);

            int countTot = 0;
            int countNotMinus1 = 0;

            // Modify image pixels
            for(int i=0; i<512; i++)
                for(int j=0; j<512; j++) {
                    float value;
                    countTot++;
                    int pixel = buffer.getRGB(i, j);
                    if(pixel != -1){
                        value = -1f;
                        countNotMinus1++;
                    }
                    else {
                        Color color = new Color(pixel, true);
                        //Retrieving the R G B values
                        int red = color.getRed();
                        int green = color.getGreen();
                        int blue = color.getBlue();
                        //Modifying the RGB values
//                    green = 150;
//                    blue = 150;
                        //Creating new Color object
                        color = new Color(red, green, blue);
                        float greyscale = rgbToGrayscale(color);
                        value = greyscale;
                        //Setting new Color object to the image
//                    buffer.setRGB(i, j, color.getRGB());
                    }
                    pixels[i][j] = new Pixel(i,j,value);
                }

            System.out.println();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static float rgbToGrayscale(Color c) {
        float avg = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
        return avg / 255;
    }

}
